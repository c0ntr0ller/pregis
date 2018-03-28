package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.house_management.*;
import ru.gosuslugi.dom.schema.integration.individual_registry_base.ID;
import ru.gosuslugi.dom.schema.integration.organizations_registry_base.RegOrgVersionType;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.connectiondb.grad.account.AccountGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.AnswerYesOrNo;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.BasicInformation;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.DocumentType;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.Rooms;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseRecord;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSI;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.model.Organization;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.services.organizations.ExportOrgRegistry;
import ru.progmatik.java.web.servlets.listener.ClientDialogWindowObservable;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс, синхронизирует лицевые счета ГРАДа и ГИС ЖКХ.
 */
public final class UpdateAllAccountData implements ClientDialogWindowObservable {

    private static final Logger LOGGER = Logger.getLogger(UpdateAllAccountData.class);
    private static final int ACCOUNT_COUNTER_FOR_REQUEST = 25;
    private final AnswerProcessing answerProcessing;
    private final AccountGRADDAO accountGRADDAO;
    private final ArrayList<ImportAccountRequest.Account> accountsForCloseList = new ArrayList<>();
    private final ArrayList<ImportAccountRequest.Account> accountsNotFoundCloseList = new ArrayList<>();
    private int countAll;
    private int countAllGisJkh;
    private int countRemove;
    private int countAdded;
    private int countAddedToGRAD;
    private int errorState;

    public UpdateAllAccountData(final AnswerProcessing answerProcessing) throws SQLException {
        this.answerProcessing = answerProcessing;
        accountGRADDAO = new AccountGRADDAO(answerProcessing);
    }

    /**
     * Метод, получает из БД список всех выгруженных домов в ГИС ЖКХ, получает по ФИАС данные о лицевых счетах дома из ГИС ЖКХ.
     * Получает из БД по ИД этого же дома информацию о счетах, производит проверку, при необходимости закрывает счет в ГИС ЖКХ или выгружает новый.
     *
     * @param houseGradId идентификатор дома в БД Град или null, если нужно обработать все дома.
     */
    public int callUpdateAllAccountData(final Integer houseGradId) throws SQLException, PreGISException, ParseException {

        errorState = 1;

        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {

            final HouseGRADDAO graddao = new HouseGRADDAO(answerProcessing);

            final LinkedHashMap<String, HouseRecord> houseAddedGisJkh = graddao.getHouseRecords(houseGradId, connectionGRAD);

            //final ExportAccountData accountData = new ExportAccountData(answerProcessing);

            if (houseAddedGisJkh != null) {
                for (Map.Entry<String, HouseRecord> itemHouse : houseAddedGisJkh.entrySet()) {

                    clearCounts();

                    if (answerProcessing != null) {
                        answerProcessing.clearLabelForText();
                        answerProcessing.sendMessageToClient("");
                        answerProcessing.sendMessageToClient("Обрабатываю дом " + itemHouse.getValue().getAddresString() + "...");
                        answerProcessing.sendMessageToClient("Код дома по ФИАС: " + itemHouse.getKey());
                        answerProcessing.sendMessageToClient("Код дома в системе \"ГРАД\": " + itemHouse.getValue().getGrad_id());
                    }

//                    final GetStateResult stateResult = accountData.callExportAccountData(itemHouse.getKey());

                    final LinkedHashMap<BasicInformation, ImportAccountRequest.Account> accountListFromGrad = new LinkedHashMap<>();
                    // получаем лицеваые из Град
                    getAccountsFromGrad(itemHouse.getValue().getGrad_id(), connectionGRAD, accountListFromGrad);

                    // получаем лицевые из ГИС
                    final GetStateResult stateResult = HomeManagementAsyncPort.callExportAccountData(itemHouse.getKey(), answerProcessing);

                    countAll += accountListFromGrad.size();

                    if (stateResult == null){ // || stateResult.getExportAccountResult() == null || stateResult.getExportAccountResult().size() == 0) { // если не получили не однин лс.
                        errorState = 0;
                    } else if (stateResult.getErrorMessage() != null && stateResult.getErrorMessage().getErrorCode().equalsIgnoreCase("INT002012")) { // Если нет объектов для экспорта
                        checkAndSendAccountData(null, accountListFromGrad, itemHouse.getValue().getGrad_id(), connectionGRAD);
                    } else if((stateResult.getErrorMessage() != null) || (stateResult.getExportAccountResult() == null) || stateResult.getExportAccountResult().isEmpty()){
                        errorState = 0;
                    }else {
                        countAllGisJkh += stateResult.getExportAccountResult().size();
    //                    List<ExCportAccountResultType> accountsListFromGISJKH = exportAccountResult.getAccounts();
    ////                    ГИС ЖКХ отдаёт ответ по 50 ЛС.
    //                    while (countAllGisJkh % 50 == 0) {
    //                        exportAccountResult = accountData.callExportAccountData(itemHouse.getKey());
    //                        countAllGisJkh += exportAccountResult.getAccounts().size();
    //                        accountsListFromGISJKH.addAll(exportAccountResult.getAccounts());
    //                    }
                        checkAndSendAccountData(stateResult.getExportAccountResult(), accountListFromGrad, itemHouse.getValue().getGrad_id(), connectionGRAD);

                    }
                    printReport(itemHouse.getKey());
                }
            }
        }
        // лицевые на закрытие обрабатываются после ответа пользователя в go()
        if (accountsForCloseList.size() > 0) {
            setErrorState(0);
            if (answerProcessing != null) {
                answerProcessing.showQuestionToClient("В ГИС ЖКХ найдены лицевые счета клиентов (" + accountsForCloseList.size() +
                        "), которые дублируются. Желаете их закрыть?\n", this);
            }
        }

        return errorState;
    }

    /**
     * Метод, выводит пользователю информацию о количестве обработанных ЛС.
     * @param fias код дома по ФИАС.
     */
    private void printReport(final String fias) {
        if(answerProcessing != null) {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendMessageToClient(String.format("По дому с кодом ФИАС: %s", fias));
            answerProcessing.sendMessageToClient("Всего обработано записей: " + countAll + "\nИз них:");
            answerProcessing.sendMessageToClient("Всего лицевых счетов найдено в ГИС ЖКХ: " + countAllGisJkh);
            answerProcessing.sendMessageToClient("Лицевых счетов помечено на удаление: " + countRemove);
            answerProcessing.sendMessageToClient("Лицевых счетов добавлено в ГИС ЖКХ: " + countAdded);
            answerProcessing.sendMessageToClient("Идентификаторов добавлено в ГРАД: " + countAddedToGRAD);
        }
        LOGGER.debug("Всего лицевых счетов найдено в ГИС ЖКХ: " + countAllGisJkh);
        LOGGER.debug("Обработано записей - " + countAll);
        LOGGER.debug("Лицевых счетов помечено на удаление: " + countRemove + ", Лицевых счетов добавлено в ГИС ЖКХ: " + countAdded);
    }

    /**
     * Метод, сбрасывает счетчики.
     */
    private void clearCounts() {
        countAll = 0;
        countAllGisJkh = 0;
        countRemove = 0;
        countAdded = 0;
        countAddedToGRAD = 0;
    }

    /**
     * Метод, передаёт на проверку список абонентов из БД ГРАДа и список абонентов полученных из ГИС ЖКХ.
     * Заносит идентификаторы в БД и передаёт в ГИС ЖКХ новые данные.
     *
     * @param accountsListFromGISJKH полученный список абонентов из ГИС ЖКХ.
     * @param accountListFromGrad    список абонентов из БД ГРАД.
     * @param houseId                идентификатор дома в БД ГРАД
     * @param connection             подключение к БД ГРАД.
     */
    private void checkAndSendAccountData(final List<ExportAccountResultType> accountsListFromGISJKH,
                                         final LinkedHashMap<BasicInformation,ImportAccountRequest.Account> accountListFromGrad,
                                         final Integer houseId, final Connection connection) throws SQLException, PreGISException, ParseException {

        // для удобства создаем мапу с лицевыми счетами из ГИС. Ключ - ЛС из Града
        final LinkedHashMap<String, ExportAccountResultType> accountsMapFromGISJKH = new LinkedHashMap<>();
        if(accountsListFromGISJKH != null) {
            for (ExportAccountResultType exportAccountResultType : accountsListFromGISJKH) {
                if(exportAccountResultType.getClosed() == null)
                    accountsMapFromGISJKH.put(exportAccountResultType.getAccountNumber(), exportAccountResultType);
            }
        }
//        Ключ - TransportGUID, значение - Account
        final LinkedHashMap<String, ImportAccountRequest.Account> accountDataMap = new LinkedHashMap<>();

// TODO: проверить как заносятся закрытые ЛС
        if (accountsListFromGISJKH != null) {
            // сначала находим дубликаты только в ГИС с одинаковым ЛС
            checkDuplicateAccountDataGisJkh(accountsListFromGISJKH);

            checkIncorrectAccountGisJkh(accountsListFromGISJKH, accountListFromGrad);

            // бежим по ЛС из Града
            for (Map.Entry<BasicInformation, ImportAccountRequest.Account> entry : accountListFromGrad.entrySet()) {
                // если еще не входит в список на закрытие
                if (!isInClosedAccountList(entry.getKey().getNumberLS())) {
                    // получаем ACCOUNTUNIQNUM абонента
//                    final Integer abonId = accountGRADDAO.getAbonentIdFromGrad(entry.getKey(), connection);
                    if(entry.getKey().getGradID() > 0) {
                        // final String uniqueNumberFromDB = accountGRADDAO.getUnifiedAccountNumber(abonId, connection);

                        if(accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()) != null) {
                            // если в Граде у абонента ACCOUNTGUID тоже пустой
                            if (entry.getValue().getAccountGUID() == null ||
                                    // или при одинаковом ЛС разные GUID
                                    (accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()) != null &&
                                            !accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()).getAccountGUID().equalsIgnoreCase(entry.getValue().getAccountGUID())
                                    )
                                    ) {

                                // проверяем и впытаемся занести его ACCOUNTGUID в БД. если не занесли
                                if (!checkAccountDataIsAddedGrad(accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()), entry.getValue(),
                                        houseId, null, connection)) {

                                    addEntryToGISMap(accountDataMap, entry.getValue());
                                }
                            }else{
                                // если все совпадает (аккаунт и ИД ЛС), но разные плательщики (по ФИО или по орагнизации) - добавляем на обновление в ГИС
                                if(entry.getValue().getAccountGUID() != null &&
                                        accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()) != null &&
                                        entry.getValue().getPayerInfo() != null && entry.getValue().getPayerInfo().getInd() != null && // по частникам
                                        accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()).getAccountGUID().equalsIgnoreCase(entry.getValue().getAccountGUID()) &&
                                        (accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()).getPayerInfo() == null ||
                                                accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()).getPayerInfo().getInd() == null ||
                                                accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()).getPayerInfo().getInd().getFirstName() == null ||
                                                !accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()).getPayerInfo().getInd().getFirstName().equalsIgnoreCase(
                                                entry.getValue().getPayerInfo().getInd().getFirstName()) ||
                                                !accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()).getPayerInfo().getInd().getSurname().equalsIgnoreCase(
                                                        entry.getValue().getPayerInfo().getInd().getSurname()) ||
                                                (accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()).getPayerInfo().getInd().getPatronymic() != null &&
                                                        entry.getValue().getPayerInfo().getInd().getPatronymic() != null &&
                                                !accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()).getPayerInfo().getInd().getPatronymic().equalsIgnoreCase(
                                                        entry.getValue().getPayerInfo().getInd().getPatronymic())
                                                )

                                        ))
                                {
                                    addEntryToGISMap(accountDataMap, entry.getValue());
                                }else{
                                    if(entry.getValue().getAccountGUID() != null && //
                                            accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()) != null &&
                                            entry.getValue().getPayerInfo() != null &&
                                            entry.getValue().getPayerInfo().getOrg() != null && //по юрлицам
                                            accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()).getAccountGUID().equalsIgnoreCase(entry.getValue().getAccountGUID()) &&
                                            (accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()).getPayerInfo() == null ||
                                                    accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()).getPayerInfo().getOrg() == null ||
                                                    accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()).getPayerInfo().getOrg().getOrgVersionGUID() == null ||
                                                    !accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()).getPayerInfo().getOrg().getOrgVersionGUID().equalsIgnoreCase(
                                                            entry.getValue().getPayerInfo().getOrg().getOrgVersionGUID())

                                            ))
                                    {
                                        addEntryToGISMap(accountDataMap, entry.getValue());
                                    }
                                }
                            }

                        }else {
                            addEntryToGISMap(accountDataMap, entry.getValue());
                        }
                    }
                }
            }
        } else {// если в ГИС ничего нет - всё просто добавляем
            for (Map.Entry<BasicInformation, ImportAccountRequest.Account> entry : accountListFromGrad.entrySet()) {
                if (!isInClosedAccountList(entry.getKey().getNumberLS())) {
                    if(entry.getValue().getAccountGUID() == null) {
//                        final Integer abonId = accountGRADDAO.getAbonentIdFromGrad(entry.getKey(), connection);
                        if(entry.getKey().getGradID() > 0) {
//                            final String accountGUIDFromBase = accountGRADDAO.getAccountGUIDFromBase(abonId, connection);
//
//                            if (accountGUIDFromBase == null) {
//
                            addEntryToGISMap(accountDataMap, entry.getValue());
//                            }
                        }
                    }
                }
            }
        }
        if (!accountDataMap.isEmpty()) {
            sendAccountDataToGISJKH(accountDataMap, houseId, connection);
        } else {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendMessageToClient("Не найдены лицевые счета для выгрузки в ГИС ЖКХ.");
        }

    }

    /**
     * Вспомогательный метод для checkAndSendAccountData
     * @param accountDataMap ссылка на мапу для отсылки в ГИС
     * @param entryValue инфа по абоненту
     */
    private void addEntryToGISMap(LinkedHashMap<String, ImportAccountRequest.Account> accountDataMap, ImportAccountRequest.Account entryValue){
        final String transportGUID = OtherFormat.getRandomGUID();
        entryValue.setTransportGUID(transportGUID);
        accountDataMap.put(transportGUID, entryValue);
    }

    /**
     * Метод, находит дубликаты ЛС в ГИС ЖКХ, добавляет их в список, для дальнейшего закрытия.
     *
     * @param exportAccountResult данные получнные изи ГИС ЖКХ.
     * @throws PreGISException
     * @throws SQLException
     */
    private void checkDuplicateAccountDataGisJkh(final List<ExportAccountResultType> exportAccountResult) throws PreGISException, SQLException {
        for (int i = 0; i < exportAccountResult.size(); i++) {

            String ar1 = exportAccountResult.get(i).getAccountNumber();

            for (int j = i + 1; j < exportAccountResult.size(); j++) {

                String ar2 = exportAccountResult.get(j).getAccountNumber();

                if (ar1 != null && ar2 != null && !exportAccountResult.get(i).getAccountGUID().equals(exportAccountResult.get(j).getAccountGUID()) &&
                        ar1.equalsIgnoreCase(ar2) &&
                        (exportAccountResult.get(i).getClosed() == null && exportAccountResult.get(j).getClosed() == null)) {

                    addAccountDataForClose(exportAccountResult.get(j), "Изменение реквизитов лицевого счета", "Абонент продублирован в ГИС");
                }
            }
        }
    }

    /**
     * Бежим по списку из ГИС ЖКХ и по ЛС ищем абонента в списке Граде. Если не нашли - ставим на удаление, занесен в ГИС ошибочно
     * @param exportAccountResult
     * @throws PreGISException
     * @throws SQLException
     */
    private void checkIncorrectAccountGisJkh(final List<ExportAccountResultType> exportAccountResult,
                                             LinkedHashMap<BasicInformation,ImportAccountRequest.Account> accountListFromGrad) throws PreGISException, SQLException {
        for (ExportAccountResultType acc: exportAccountResult) {
            if(accountListFromGrad.get(acc.getAccountNumber()) == null && acc.getClosed() == null){
// TODO пока закоментил, позже разберемся с закрытием, так как не все помещения пока попали нормально                addAccountDataForClose(acc, "Ошибка ввода", "Абонент есть в ГИС, но нет в Град");
            }
        }
    }

    /**
     * Метод, ищет ЛС в ранее созданом списке дубликатов ЛС, если ЛС в нем не найден, то будет выгружен в ГИС ЖКХ
     * иначе не будет выгружен в ГИС ЖКХ.
     *
     * @param accountNumber номер лицевого счета абонента.
     * @return true - если абонент найден в списке и выгружать его не стоит в ГИС ЖКХ,
     * false - абонент не найден в списке дубликатов ГИС ЖКХ.
     */
    private boolean isInClosedAccountList(final String accountNumber) {

        for (ImportAccountRequest.Account account : accountsForCloseList) {
            if (accountNumber.equalsIgnoreCase(account.getAccountNumber())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод, добавляет в список абонентов, которые выгружены в ГИС ЖКХ более одного раза.
     *
     * @param account данные абонента.
     * @throws PreGISException возникнит ошибка, если не удастся загрузить справочник из ГИС ЖКХ.
     */
    private void addAccountDataForClose(final ExportAccountResultType account, final String nameElement, final String message) throws SQLException, PreGISException {

        for (ImportAccountRequest.Account accountItem : accountsForCloseList) {
            if (account.getAccountGUID().equalsIgnoreCase(accountItem.getAccountGUID())) {

                return;
            }
        }
// "Изменение реквизитов лицевого счета"
        final ReferenceNSI referenceNSI = new ReferenceNSI(answerProcessing);

        final ImportAccountRequest.Account accountClose = new ImportAccountRequest.Account();
        accountClose.setAccountGUID(account.getAccountGUID());
        accountClose.setClosed(new ClosedAccountAttributesType());
        accountClose.getClosed().setCloseDate(OtherFormat.getDateNow());
        accountClose.getClosed().setCloseReason(referenceNSI.getNsiRef("22", nameElement));
        accountClose.getClosed().setDescription("Данные признаны неверными.");
        accountClose.setCreationDate(account.getCreationDate());
        accountClose.setAccountNumber(account.getAccountNumber());
        accountClose.getAccommodation().addAll(convertAccommodation(account.getAccommodation()));
        accountClose.setPayerInfo(convertPayer(account.getPayerInfo()));
        accountClose.setTransportGUID(OtherFormat.getRandomGUID());
        accountClose.setTotalSquare(account.getTotalSquare());
//        accountClose.setResidentialSquare(account.getResidentialSquare());
//        accountClose.setHeatedArea(account.getHeatedArea());
        AccountGRADDAO.setIsAccount(accountClose);

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient(message + "; ЛС " + account.getAccountNumber());

        accountsForCloseList.add(accountClose);
    }

    /**
     * Метод конвертирует из типа AccountExportType.PayerInfo в AccountType.PayerInfo из-за идиотизма программеров
     * @param sPayer - входящий AccountExportType.PayerInfo
     * @return - результат конвертации
     */
    private AccountType.PayerInfo convertPayer(AccountExportType.PayerInfo sPayer){
        if(sPayer == null) return null;

        AccountType.PayerInfo rPayer = new AccountType.PayerInfo();
        AccountIndType rInd = new AccountIndType();
        ID rID = new ID();
        
        AccountIndExportType sInd = sPayer.getInd();
        AccountIndExportType.ID sID = null;
        
        if (sInd != null) {
            sID = sPayer.getInd().getID();
            if(sID != null) {
                rID.setIssueDate(sID.getIssueDate());
                rID.setNumber(sID.getNumber());
                rID.setSeries(sID.getSeries());
                rID.setType(sID.getType());
                rInd.setID(rID);
            }
            rInd.setDateOfBirth(sInd.getDateOfBirth());
            rInd.setSex(sInd.getSex());
            rInd.setSNILS(sInd.getSNILS());
            rInd.setFirstName(sInd.getFirstName());
            rInd.setPatronymic(sInd.getPatronymic());
            rInd.setSurname(sInd.getSurname());
            rPayer.setInd(rInd);
        }
        rPayer.setIsRenter(sPayer.isIsRenter());
        rPayer.setOrg(sPayer.getOrg());
        return rPayer;
    }

    /**
     * Метод конвертирует из типа List<AccountExportType.Accommodation> в List<AccountType.Accommodation> из-за идиотизма программеров
     * @param sAccList - входящий список типа List<AccountExportType.Accommodation>
     * @return - результат конвертации
     */
    private List<AccountType.Accommodation> convertAccommodation(List<AccountExportType.Accommodation> sAccList){
        List<AccountType.Accommodation> rAccList = new ArrayList<>();
        for (AccountExportType.Accommodation sAcc: sAccList
                ) {
            AccountType.Accommodation rAcc = new AccountType.Accommodation();
            rAcc.setFIASHouseGuid(sAcc.getFIASHouseGuid());
            if(sAcc.getLivingRoomGUID() == null || sAcc.getLivingRoomGUID().isEmpty()){
                rAcc.setPremisesGUID(sAcc.getPremisesGUID());
            }else {
                rAcc.setLivingRoomGUID(sAcc.getLivingRoomGUID());
            }
            rAcc.setSharePercent(sAcc.getSharePercent());
            if(rAcc.getSharePercent() == null) rAcc.setSharePercent(new BigDecimal(100)); // если ничего не пришло - ставим 100, иначе ГИС выдает ошибку
            rAccList.add(rAcc);
        }
        return rAccList;
    }

    /**
     * Метод, проверяет соответствие ЛС из БД и ГИС ЖКХ.
     * Если, по указанному ЛС найдены идентификаторы ГИС ЖКХ, то заносим их в БД ГРАДа.
     * Если, ЛС не найден в БД ГРАД, значит он не существует, такой счет будет помещен в таблицу "ACCOUNT_FOR_REMOVE".
     *
     * @param accountFromGISJKH абонент полученный из ГИС ЖКХ.
     * @param account                данные абонента полученные из БД ГРАДа.
     * @param houseId                ид дома в БД.
     */
    private boolean checkAccountDataIsAddedGrad(final ExportAccountResultType accountFromGISJKH,
                                                final ImportAccountRequest.Account account,
                                                final Integer houseId,
                                                final String uniqueNumberFromDB,
                                                final Connection connection)
            throws ParseException, SQLException, PreGISException {


        if (accountFromGISJKH.getClosed() == null) {
            if (account.getAccountNumber().equalsIgnoreCase(accountFromGISJKH.getAccountNumber())) { // если в БД есть элемент, добавляем идентификаторы в БД

//                Проверка откл.
//                System.err.println("GRAD: " + account.getAccountGUID());
//                System.err.println("GIS: " + accountFromGISJKH.getAccountGUID());

//                String uniqueNumberFromDB = accountGRADDAO.getUnifiedAccountNumber(
//                        accountGRADDAO.getAbonentIdFromGrad(houseId, account.getAccountNumber(), connection), connection);

                if (uniqueNumberFromDB != null &&
                        uniqueNumberFromDB.equalsIgnoreCase(accountFromGISJKH.getUnifiedAccountNumber())) { // Если есть уникальный идентификатор, сравниваем м ним
//
//                    Всё просто, если есть уникальный идентификатор, то заносим AccountGUID
                    if (account.getAccountGUID() == null ||
                            !account.getAccountGUID().equalsIgnoreCase(accountFromGISJKH.getAccountGUID())) {

                        setAccountToBase(houseId, accountFromGISJKH.getAccountNumber(), accountFromGISJKH.getAccountGUID(), accountFromGISJKH.getUnifiedAccountNumber(), connection);
//                        после 9.0.1.4 переименовали

                        return true;
//                    accountListFromGrad.remove(accountFromGISJKH.getAccountNumber()); // удалим найденные счета из списка
//                    }
//                } else { // если нет записи в БД, значит счет закрыт надо закрыть в ГИС ЖКХ, для этого добавим в таблицу для удаления потом закроем все не найденные счита в БД.
//                    removeAccount(houseId, accountFromGISJKH.getAccountNumber(), connection);
                    }
                } else { // Если нет уникального идентификатора, заносим всё на честное слово
                    setAccountToBase(houseId, accountFromGISJKH.getAccountNumber(), accountFromGISJKH.getAccountGUID(), accountFromGISJKH.getUnifiedAccountNumber(), connection);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Метод, отправляет данные по ЛС в ГИС ЖКХ.
     *
     * @param accountDataFromGrad ключ - транспортный идентификатор, значение - данные абонента для выгрузки.
     * @param houseId             идентификатор дома в БД ГРАД.
     * @param connection          подключение к БД ГРАД.
     * @throws PreGISException
     * @throws SQLException
     * @throws ParseException
     */
    private void sendAccountDataToGISJKH(final LinkedHashMap<String, ImportAccountRequest.Account> accountDataFromGrad,
                                         final Integer houseId,
                                         final Connection connection) throws PreGISException, SQLException, ParseException {

        // final ImportAccountData sendAccountToGis = new ImportAccountData(answerProcessing);

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Отправляем данные в ГИС ЖКХ...");

        GetStateResult result;
        int count = 0;

        final ArrayList<ImportAccountRequest.Account> accountsList = accountDataFromGrad.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toCollection(ArrayList::new));


        while (count < accountsList.size()) {

            answerProcessing.clearLabelForText();

            if (count + ACCOUNT_COUNTER_FOR_REQUEST > accountsList.size()) {
                result = HomeManagementAsyncPort.callImportAccountData(accountsList.subList(count, accountsList.size()), answerProcessing);
//                result = sendAccountToGis.callImportAccountData(accountsList.subList(count, accountsList.size()));
                count += ACCOUNT_COUNTER_FOR_REQUEST;
            } else {
                result = HomeManagementAsyncPort.callImportAccountData(accountsList.subList(count, count += ACCOUNT_COUNTER_FOR_REQUEST), answerProcessing);
            }

            if (result != null && result.getImportResult() != null) {

                for (ImportResult importResult : result.getImportResult()) {
                    for (ImportResult.CommonResult commonResult : importResult.getCommonResult()) {

                        if (commonResult.getError() == null || commonResult.getError().isEmpty()) {
                            countAdded++;
                            setAccountToBase(houseId,
                                    accountDataFromGrad.get(commonResult.getTransportGUID()).getAccountNumber(),
                                    commonResult.getGUID(),
                                    commonResult.getImportAccount().getUnifiedAccountNumber(),
                                    connection);

                        } else {
                            answerProcessing.sendMessageToClient("");
                            answerProcessing.sendMessageToClient("Код ошибки: " + commonResult.getError().get(0).getErrorCode());
                            answerProcessing.sendMessageToClient("Описание ошибки: " + commonResult.getError().get(0).getDescription());
                            setErrorState(0);
                        }
                    }
                }
            }
        }
    }


    /**
     * Метод, добавляет в таблицу данные абонента, которые необходимо удалить из ГИС ЖКХ.
     *
     * @param houseId       ид дома в БД.
     * @param accountNumber номер ЛС.
     */
    private void removeAccount(Integer houseId, String accountNumber, Connection connection) throws ParseException, SQLException, PreGISException {
        try (Connection connectionLocal = ConnectionDB.instance().getConnectionDB()) {
            AccountGRADDAO.addAccountForRemove(houseId, accountNumber, connectionLocal, connection, answerProcessing);
            countRemove++;
        }
    }

    /**
     * Метод, добавляет в БД данные абонента.
     *
     * @param houseId             ид дома в БД.
     * @param accountNumber       номер ЛС.
     * @param accountGUID         идентификатор ЛС в ГИС ЖКХ.
     * @param accountUniqueNumber уникальный номер ЛС, присвоенный ГИС ЖКХ.
     */
    private void setAccountToBase(Integer houseId, String accountNumber, String accountGUID, String accountUniqueNumber, Connection connection) throws ParseException, SQLException, PreGISException {

        if (AccountGRADDAO.setAccountGuidAndUniqueNumber(houseId, accountNumber, accountGUID, accountUniqueNumber, connection, answerProcessing)) {
//            answerProcessing.sendMessageToClient("");

//            if (accountGUID != null && accountUniqueNumber != null) {
//                answerProcessing.sendMessageToClient(String.format("Для лицевого счета № %s:\n" +
//                                "Добавлен идентификатор: %s\nДобавлен уникальный идентификатор: %s.",
//                        accountNumber, accountGUID, accountUniqueNumber));
//            } else if (accountGUID != null) {
//                answerProcessing.sendMessageToClient(String.format("Добавлен идентификатор: %s, для лицевого счета № %s", accountGUID, accountNumber));
//            } else if (accountUniqueNumber != null) {
//                answerProcessing.sendMessageToClient(String.format("Добавлен уникальный идентификатор: %s, для лицевого счета № %s", accountUniqueNumber, accountNumber));
//            }
            countAddedToGRAD++;
        } else {
            setErrorState(0);
        }
    }

    /**
     * Метод, формирует и отправляет запрос для закрытия ЛС, которые находятся в списке "accountsForCloseList".
     *
     * @param listForClose список абонентов для закрытия ЛС в ГИС ЖКХ.
     */
    private void closeAccountData(ArrayList<ImportAccountRequest.Account> listForClose) {

        if (!listForClose.isEmpty()) {

            answerProcessing.sendMessageToClient("Закрытие ЛС...");
            // ImportAccountData sendAccountToGis = new ImportAccountData(answerProcessing);
            GetStateResult result;
            int count = 0;

            while (count < listForClose.size()) {

                answerProcessing.sendMessageToClient("::clearLabelText");

                try {
                    if (count + 5 > listForClose.size()) {
                        result = HomeManagementAsyncPort.callImportAccountData(listForClose.subList(count, listForClose.size()), answerProcessing);
                        count += 5;
                    } else {
                        result = HomeManagementAsyncPort.callImportAccountData(listForClose.subList(count, count += 5), answerProcessing);
                    }

                    for (ImportResult importResult : result.getImportResult()) {
                        for (ImportResult.CommonResult commonResult : importResult.getCommonResult()) {
                            if (commonResult.getError() == null || commonResult.getError().isEmpty()) {
                                answerProcessing.sendMessageToClient("");
                                answerProcessing.sendMessageToClient(String.format("Уникальный номер абонента ГИС ЖКХ: %s, успешно закрыт.", commonResult.getUniqueNumber()));
                            } else {
                                answerProcessing.sendMessageToClient("");
                                answerProcessing.sendMessageToClient("Код ошибки: " + commonResult.getError().get(0).getErrorCode());
                                answerProcessing.sendMessageToClient("Описание ошибки: " + commonResult.getError().get(0).getDescription());
                                setErrorState(0);
                            }
                        }
                    }

                } catch (SQLException | PreGISException e) {
                    answerProcessing.sendErrorToClient("Закрытие ЛС", "\"Закрытие ЛС\"", LOGGER, e);
                }

            }
            accountsForCloseList.clear();
        }
        if (errorState == -1) {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendErrorToClientNotException("Возникла ошибка!\nОперация: \"Закрытие ЛС\" прервана!");
        } else if (errorState == 0) {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendErrorToClientNotException("Операция: \"Закрытие ЛС\" завершилась с ошибками!");
        } else if (errorState == 1) {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendOkMessageToClient("\"Закрытие ЛС\" успешно выполнено.");
        }
    }

    private void setErrorState(int errorState) {
        if (this.errorState > errorState) {
            this.errorState = errorState;
        }
    }

    /**
     * Метод, возвращает из БД Града, ассоциативный массив где:
     * ключ - ЛС, значение - класс Account пригодный для импорта ЛС в ГИС ЖКХ.
     * @param houseId идентификатор дома в БД Град.
     * @param connectionGrad объект пригодный для импорта ЛС в ГИС ЖКХ.
     * @return ассоциативный массив где:
     * ключ - ЛС, значение - класс Account пригодный для импорта ЛС в ГИС ЖКХ.
     * @throws ParseException
     * @throws SQLException
     * @throws PreGISException
     */
    private void getAccountsFromGrad(final int houseId,
                                      final Connection connectionGrad,
                                      final LinkedHashMap<BasicInformation, ImportAccountRequest.Account> mapAccount)
            throws ParseException, SQLException, PreGISException {


        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Формирую данные...");
        final ArrayList<BasicInformation> basicInformationList = AccountGRADDAO.getBasicInformation(houseId, connectionGrad, answerProcessing);

        LinkedHashMap<String, Rooms> roomsList = AccountGRADDAO.getRoomsMaps(houseId, connectionGrad);

        //        final ArrayList<Rooms> roomsList = getRooms(houseID, connection);
        final ReferenceNSI nsi = new ReferenceNSI(answerProcessing);
//        ExportOrgRegistry orgRegistry = new ExportOrgRegistry(answerProcessing);

//        LinkedHashMap<BasicInformation, ImportAccountRequest.Account> mapAccount = new LinkedHashMap<>();

        if (basicInformationList == null || roomsList == null) {
            answerProcessing.sendInformationToClientAndLog("Не найдены лицевые счета для дома с ИД: " + houseId + ".", LOGGER);
            return;
        }

        // получаем инфу из ГИС с идентификаторами организаций
        obtainOrgsVersionsIds(basicInformationList, connectionGrad);

        for (BasicInformation basicInformation : basicInformationList) {
            int count = 0;
            // for (int i = 0; i < roomsList.size(); i++) {

//                Формируем объект пригодный для импорта в ГИС ЖКХ.
//                По необходимости в дальнейшем нужно присвоить перед отправкой:
//                - если новый счет указать TransportGUID, удалить если есть AccountGUID.
//                - если счет к закрытию указать AccountGUID и isClosed.

            Rooms fRoom = roomsList.get(basicInformation.getNumberLS());
            String premisesGUID = null;
            String livingRoomGUID = null;
            if (fRoom != null) {
                // заранее получаем premisesGUID и livingRoomGUID, если не заданы - вообще не добавляем объект, потому что вызовет ошибку
                if(basicInformation.getPremisesGUID().isEmpty()) {
                    premisesGUID = AccountGRADDAO.getBuildingIdentifiersFromBase(basicInformation.getGradID(), "PREMISESGUID", connectionGrad);
                }else{
                    premisesGUID = basicInformation.getPremisesGUID();
                }

                livingRoomGUID = basicInformation.getLivingRoomGUID();
                // livingRoomGUID = AccountGRADDAO.getBuildingIdentifiersFromBase(basicInformation.getGradID(), "LIVINGROOMGUID", connectionGrad);

// дублирование сообщения ниже                if (premisesGUID == null && livingRoomGUID == null) {
//                    answerProcessing.sendInformationToClientAndLog("Для абонента ГРАД ИД "
//                            + basicInformation.getNumberLS() + " не найдено идентификатора помещения или комнаты", LOGGER);
//                }
            }
            if (fRoom != null && !(premisesGUID == null && livingRoomGUID == null)
                    && (basicInformation.getOgrnOrOgrnip().isEmpty() || // или частное лицо
                        (!basicInformation.getOgrnOrOgrnip().isEmpty() && !basicInformation.getOrgVersionGUID().isEmpty())) // или есть идентификатор
                    ) {
// debug                    answerProcessing.sendMessageToClient(basicInformation.getNumberLS() + " found!");
                ImportAccountRequest.Account account = new ImportAccountRequest.Account();
//                    account.setCreationDate(OtherFormat.getDateNow()); // может без даты можно? добавил при отправки нового счета
                account.setLivingPersonsNumber(basicInformation.getAmountLiving());
                account.setTotalSquare(new BigDecimal(basicInformation.getTotalArea()).setScale(2, BigDecimal.ROUND_DOWN));
                account.setResidentialSquare(new BigDecimal(basicInformation.getLivingSpace()).setScale(2, BigDecimal.ROUND_DOWN));
                if (basicInformation.getHeadtedArea() > 0.0) {
                    account.setHeatedArea(new BigDecimal(basicInformation.getHeadtedArea()).setScale(2, BigDecimal.ROUND_DOWN));
                }
//                    account.setClosed(); // проверить, если в ГИС ЖКХ есть, а в БД ГРАД нет, то установить в ExportAccountData.
                AccountType.Accommodation accommodation = new AccountType.Accommodation();
                if(livingRoomGUID == null || livingRoomGUID.isEmpty()) {
                    accommodation.setPremisesGUID(premisesGUID);
                }else {
//                    accommodation.setFIASHouseGuid(roomsList.get(i).getFias()); // Выдаё ошибку, по описанию можно выбирать.
                    accommodation.setLivingRoomGUID(livingRoomGUID); // Идентификатор комнаты
                }
                accommodation.setSharePercent(new BigDecimal(fRoom.getSharePay()).setScale(0, BigDecimal.ROUND_DOWN));
                if(accommodation.getSharePercent() == null) accommodation.setSharePercent(new BigDecimal(100)); // если ничего не пришло - ставим 100, иначе ГИС выдает ошибку

                account.getAccommodation().add(accommodation);
//                    account.setTransportGUID();  // указывается, если ЛС добавляется в первые.

//                    Сведения о плательщике
                account.setPayerInfo(new AccountType.PayerInfo());

                if (basicInformation.getOgrnOrOgrnip().isEmpty()) { // частное лицо (не юр и не ИП)
                    account.getPayerInfo().setInd(new AccountIndType());

                    if (basicInformation.getSurname() != null && !basicInformation.getSurname().trim().isEmpty()
                            && basicInformation.getName() != null && !basicInformation.getName().trim().isEmpty()) {

                        account.getPayerInfo().getInd().setSurname(basicInformation.getSurname());

                        account.getPayerInfo().getInd().setFirstName(basicInformation.getName());

                        if (basicInformation.getMiddleName() != null) {
                            account.getPayerInfo().getInd().setPatronymic(basicInformation.getMiddleName());
                        }


                        if (basicInformation.getNumberDocumentIdentity() != null) { // будем создавать только если есть номер документа!
                            account.getPayerInfo().getInd().setID(new ID()); // подгрузить справочник NSI 95

                            if (basicInformation.getTypeDocument() != null) {
                                account.getPayerInfo().getInd().getID().setType(nsi.getTypeDocumentNsiRef(basicInformation.getTypeDocument().getTypeDocument()));
                            } else {
                                account.getPayerInfo().getInd().getID().setType(nsi.getTypeDocumentNsiRef(DocumentType.getTypeDocument("паспорт").getTypeDocument()));
                            }

                            if (basicInformation.getNumberDocumentIdentity() != null && !basicInformation.getNumberDocumentIdentity().isEmpty()) {
                                account.getPayerInfo().getInd().getID().setNumber(basicInformation.getNumberDocumentIdentity());
                            } else {
                                account.getPayerInfo().getInd().getID().setNumber("000000");
                            }


                            if (basicInformation.getSeriesDocumentIdentity() != null && !basicInformation.getSeriesDocumentIdentity().isEmpty()) {
                                account.getPayerInfo().getInd().getID().setSeries(basicInformation.getSeriesDocumentIdentity());
                            } else {
                                account.getPayerInfo().getInd().getID().setSeries("0000");
                            }

                            if (basicInformation.getDateDocumentIdentity() != null) {
                                account.getPayerInfo().getInd().getID().setIssueDate(OtherFormat.getDateForXML(basicInformation.getDateDocumentIdentity()));
                            } else {
                                account.getPayerInfo().getInd().getID().setIssueDate(OtherFormat.getDateForXML((new SimpleDateFormat("dd.MM.yyyy")).parse("01.01.2017")));
                            }
                        }
                    }

                } else {
                    account.getPayerInfo().setOrg(new RegOrgVersionType());
                    account.getPayerInfo().getOrg().setOrgVersionGUID(basicInformation.getOrgVersionGUID());
//                        Есть возможность указать на VersionGUID из реестра организаций, вот только где его взять?
//                        if (basicInformation.getOgrnOrOgrnip() > 0) {
//                            ExportOrgRegistryRequest.SearchCriteria criteria = new ExportOrgRegistryRequest.SearchCriteria();
//
//                            if (basicInformation.getOgrnOrOgrnip() < 10000000000000L && basicInformation.getOgrnOrOgrnip() > 999999999999L) {
//                                criteria.setOGRN(String.valueOf(basicInformation.getOgrnOrOgrnip()));
//                            } else if (basicInformation.getOgrnOrOgrnip() > 99999999999999L) {
//                                criteria.setOGRNIP(String.valueOf(basicInformation.getOgrnOrOgrnip()));
//                            }
//                            ExportOrgRegistryResult result = orgRegistry.callExportOrgRegistry(orgRegistry.getExportOrgRegistryRequest(criteria));
                }

                if (basicInformation.getEmployer() == AnswerYesOrNo.YES) {
                    account.getPayerInfo().setIsRenter(true); // выдаёт ошибку если указать false
                }


                account.setAccountNumber(basicInformation.getNumberLS());
                if(basicInformation.getAccountGUID().isEmpty()) {
                    account.setAccountGUID(AccountGRADDAO.getAccountGUIDFromBase(basicInformation.getGradID(), connectionGrad));  // добавляется, если счет будет изменен или закрыт, если этого не будет перед отправкой затереть.
                }else{
                    account.setAccountGUID(basicInformation.getAccountGUID());
                }
                AccountGRADDAO.setIsAccount(account);
                account.setCreationDate(OtherFormat.getDateNow());

                //if (account.getAccountGUID() != null && !account.getAccountGUID().equals("")) {
                mapAccount.put(basicInformation, account);

                count++;
                //}
//                    roomsList.remove(i);
                // i--;
//                }
            }
            if (count == 1) {
                continue;
            } else if (count == 0) {
                String notFound = "Отсутствует";

                StringBuilder builder = new StringBuilder();
                builder.append(basicInformation.getSurname());
                builder.append(" ");
                builder.append(basicInformation.getName());
                builder.append(" ");
                builder.append(basicInformation.getMiddleName());
                builder.append(" ");

                String fio = builder.toString().replaceAll("null", "").trim();
                fio = fio.isEmpty() ? notFound : fio;

                if (basicInformation.getTypeDocument() == null || basicInformation.getTypeDocument().getTypeDocument() == null || basicInformation.getNumberDocumentIdentity() == null) {
                    answerProcessing.sendInformationToClientAndLog(String.format(
                            "\nДля абонента с ФИО: %s,\nНомер счета: %s.\nНе удалось найти соответствие \"Помещение\" в базе данных.",
                            fio, basicInformation.getNumberLS()), LOGGER);
                } else {
                    answerProcessing.sendInformationToClientAndLog(String.format(
                            "\nДля абонента с ФИО: %s,\nНомер счета: %s,\nТип документа: %s,\nНомер документа: %s.\nНе удалось найти соответствие \"Помещение\" в базе данных.",
                            fio, basicInformation.getNumberLS(), basicInformation.getTypeDocument().getTypeDocument(), basicInformation.getNumberDocumentIdentity()), LOGGER);
                }

            } else if (count >= 2) {
                answerProcessing.sendInformationToClientAndLog("Для счета - "
                        + basicInformation.getNumberLS() + " найдено более одного соответствия в базе данных.", LOGGER);
            }
        }
        return;
    }

    /** получаем инфу из ГИС с идентификаторами организаций
     *
     * @param basicInformationList - список информации об абонентах, полученных из Град, в котором необходимо установить значения
     */
    private void obtainOrgsVersionsIds(ArrayList<BasicInformation> basicInformationList, Connection connectionGrad) throws PreGISException {

        // список организаций без идентификаторов ГИС
        List<BasicInformation> basicInformationWOversionIDS = basicInformationList.stream()
                .filter(e -> !e.getOgrnOrOgrnip().isEmpty() && e.getOrgVersionGUID().isEmpty())
                .collect(Collectors.toList());

        if(!basicInformationWOversionIDS.isEmpty()) {
            List<String> orgsWOversionIDs = basicInformationWOversionIDS.stream()
                    .map(BasicInformation::getOgrnOrOgrnip)
                    .distinct()
                    .collect(Collectors.toList());

            answerProcessing.sendMessageToClient("Имеются абоненты, принадлежащие юр.лицам без идентификаторов. Производится запрос идентификаторов в ГИС");

            final ExportOrgRegistry exportOrgRegistry = new ExportOrgRegistry(answerProcessing);
            Map<String, Organization> ogrn2Org = exportOrgRegistry.getOrgsVersionFromGis(orgsWOversionIDs);

            if(ogrn2Org.isEmpty()){
                answerProcessing.sendMessageToClient("Информация по юр.лицам без идентификаторов не получена!");
            }else {
                answerProcessing.sendMessageToClient("Обновляется информация в Град по юр.лицам без идентификаторов");
            }

            for (BasicInformation basicInformation : basicInformationWOversionIDS) {

                String versionId = ogrn2Org.get(basicInformation.getOgrnOrOgrnip()).getOrgVersionGUID();

                if(versionId != null && !versionId.isEmpty()){
                    // обновляем инофрмацию об абоненте в памяти
                    basicInformation.setOrgVersionGUID(versionId);
                    // заносим в Град
                    AccountGRADDAO.setOrgVersionGUID(basicInformation.getGradID(), versionId, connectionGrad, answerProcessing);
                }
            }
        }
    }

    @Override
    public void go() {
        closeAccountData(accountsForCloseList);
    }

    @Override
    public void stop() {
        accountsForCloseList.clear();
    }
}
