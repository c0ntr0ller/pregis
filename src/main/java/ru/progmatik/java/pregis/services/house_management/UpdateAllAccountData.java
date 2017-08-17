package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.house_management.*;
import ru.gosuslugi.dom.schema.integration.individual_registry_base.ID;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.connectiondb.grad.account.AccountGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSI;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.web.servlets.listener.ClientDialogWindowObservable;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
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
    private static final int ACCOUNT_COUNTER_FOR_REQUEST = 5;
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
    public int updateAllAccountData(final Integer houseGradId) throws SQLException, PreGISException, ParseException {

        errorState = 1;

        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {

            final HouseGRADDAO graddao = new HouseGRADDAO(answerProcessing);

            final LinkedHashMap<String, Integer> houseAddedGisJkh = graddao.getListHouse(houseGradId, connectionGRAD);

            final ExportAccountData accountData = new ExportAccountData(answerProcessing);

            for (Map.Entry<String, Integer> itemHouse : houseAddedGisJkh.entrySet()) {

                clearCounts();

                if (answerProcessing != null) {
                    answerProcessing.clearLabelForText();
                    answerProcessing.sendMessageToClient("");
                    answerProcessing.sendMessageToClient("Обрабатываю дом...");
                    answerProcessing.sendMessageToClient("Код дома по ФИАС: " + itemHouse.getKey());
                    answerProcessing.sendMessageToClient("Код дома в системе \"ГРАД\": " + itemHouse.getValue());
                }

                final GetStateResult stateResult = accountData.callExportAccountData(itemHouse.getKey());
                final LinkedHashMap<String, ImportAccountRequest.Account> accountListFromGrad = getAccountsFromGrad(itemHouse.getValue(), connectionGRAD);
                countAll += accountListFromGrad.size();

                if (stateResult == null){ // || stateResult.getExportAccountResult() == null || stateResult.getExportAccountResult().size() == 0) { // если не получили не однин лс.
                    errorState = 0;
                } else if (stateResult.getErrorMessage() != null && stateResult.getErrorMessage().getErrorCode().equalsIgnoreCase("INT002012")) { // Если нет объектов для экспорта
                    checkAndSendAccountData(null, accountListFromGrad, itemHouse.getValue(), connectionGRAD);
                } else if(stateResult.getErrorMessage() != null || stateResult.getExportAccountResult() == null || stateResult.getExportAccountResult().size() == 0){
                    errorState = 0;
                }else {
                    countAllGisJkh += stateResult.getExportAccountResult().size();
//                    List<ExportAccountResultType> accountsListFromGISJKH = exportAccountResult.getAccounts();
////                    ГИС ЖКХ отдаёт ответ по 50 ЛС.
//                    while (countAllGisJkh % 50 == 0) {
//                        exportAccountResult = accountData.callExportAccountData(itemHouse.getKey());
//                        countAllGisJkh += exportAccountResult.getAccounts().size();
//                        accountsListFromGISJKH.addAll(exportAccountResult.getAccounts());
//                    }
                    checkAndSendAccountData(stateResult.getExportAccountResult(), accountListFromGrad, itemHouse.getValue(), connectionGRAD);

                }
                printReport(itemHouse.getKey());
            }
        }
        // лицевые еа закрытие обрабатываются после ответа пользователя в go()
        if (accountsForCloseList.size() > 0) {
            setErrorState(0);
            answerProcessing.showQuestionToClient("В ГИС ЖКХ найдены лицевые счета клиентов (" + accountsForCloseList.size() +
                    "), которые дублируются. Желаете их закрыть?\n", this);
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
                                         final LinkedHashMap<String,ImportAccountRequest.Account> accountListFromGrad,
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
            for (Map.Entry<String, ImportAccountRequest.Account> entry : accountListFromGrad.entrySet()) {
                // если еще не входит в список на закрытие
                if (!isInClosedAccountList(entry.getKey())) {
                    // получаем ACCOUNTUNIQNUM абонента
                    final Integer abonId = accountGRADDAO.getAbonentIdFromGrad(entry.getKey(), connection);
                    if(abonId > 0) {
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

                                    final String transportGUID = OtherFormat.getRandomGUID();
                                    entry.getValue().setTransportGUID(transportGUID);
                                    accountDataMap.put(transportGUID, entry.getValue());
                                }
                            }
                        }else {
                            final String transportGUID = OtherFormat.getRandomGUID();
                            entry.getValue().setTransportGUID(transportGUID);
                            accountDataMap.put(transportGUID, entry.getValue());
                        }
                    }
                }
            }
        } else {// если в ГИС ничего нет - всё просто добавляем
            for (Map.Entry<String, ImportAccountRequest.Account> entry : accountListFromGrad.entrySet()) {
                if (!isInClosedAccountList(entry.getKey())) {

                    final Integer abonId = accountGRADDAO.getAbonentIdFromGrad(entry.getKey(), connection);
                    if(abonId > 0) {
                        final String uniqueNumberFromDB = accountGRADDAO.getUnifiedAccountNumber(
                                accountGRADDAO.getAbonentIdFromGrad(entry.getKey(), connection), connection);

                        if (uniqueNumberFromDB == null && entry.getValue().getAccountGUID() == null) {

                            final String transportGUID = OtherFormat.getRandomGUID();
                            entry.getValue().setTransportGUID(transportGUID);
//                            entry.getValue().setAccountGUID(null);
                            accountDataMap.put(transportGUID, entry.getValue());
                        }
                    }
                }
            }
        }
        if (accountDataMap.size() > 0) {
            sendAccountDataToGISJKH(accountDataMap, houseId, connection);
        } else {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendMessageToClient("Не найдены лицевые счета для выгрузки в ГИС ЖКХ.");
        }

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
                                             LinkedHashMap<String,ImportAccountRequest.Account> accountListFromGrad) throws PreGISException, SQLException {
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
        accountGRADDAO.setIsAccount(accountClose);

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
            rAcc.setLivingRoomGUID(sAcc.getLivingRoomGUID());
            rAcc.setPremisesGUID(sAcc.getPremisesGUID());
            rAcc.setSharePercent(sAcc.getSharePercent());
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

                        setAccountToBase(houseId, accountFromGISJKH.getAccountNumber(), accountFromGISJKH.getAccountGUID(), null, connection);
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

        final ImportAccountData sendAccountToGis = new ImportAccountData(answerProcessing);

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Отправляем данные в ГИС ЖКХ...");

        ru.gosuslugi.dom.schema.integration.house_management.ImportResult result;
        int count = 0;

        final ArrayList<ImportAccountRequest.Account> accountsList = accountDataFromGrad.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toCollection(ArrayList::new));


        while (count < accountsList.size()) {

            answerProcessing.clearLabelForText();

            if (count + ACCOUNT_COUNTER_FOR_REQUEST > accountsList.size()) {
                result = sendAccountToGis.callImportAccountData(accountsList.subList(count, accountsList.size()));
                count += ACCOUNT_COUNTER_FOR_REQUEST;
            } else {
                result = sendAccountToGis.callImportAccountData(accountsList.subList(count, count += ACCOUNT_COUNTER_FOR_REQUEST));
            }

            if (result != null && result.getCommonResult() != null) {

                for (ImportResult.CommonResult commonResult : result.getCommonResult()) {

                    if (commonResult.getError() == null || commonResult.getError().size() == 0) {
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


    /**
     * Метод, добавляет в таблицу данные абонента, которые необходимо удалить из ГИС ЖКХ.
     *
     * @param houseId       ид дома в БД.
     * @param accountNumber номер ЛС.
     */
    private void removeAccount(Integer houseId, String accountNumber, Connection connection) throws ParseException, SQLException {
        try (Connection connectionLocal = ConnectionDB.instance().getConnectionDB()) {
            accountGRADDAO.addAccountForRemove(houseId, accountNumber, connectionLocal, connection);
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

        if (accountGRADDAO.setAccountGuidAndUniqueNumber(houseId, accountNumber, accountGUID, accountUniqueNumber, connection)) {
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

        if (listForClose.size() > 0) {

            answerProcessing.sendMessageToClient("Закрытие ЛС...");
            ImportAccountData sendAccountToGis = new ImportAccountData(answerProcessing);
            ru.gosuslugi.dom.schema.integration.house_management.ImportResult result;
            int count = 0;

            while (count < listForClose.size()) {

                answerProcessing.sendMessageToClient("::clearLabelText");

                try {
                    if (count + 5 > listForClose.size()) {
                        result = sendAccountToGis.callImportAccountData(listForClose.subList(count, listForClose.size()));
                        count += 5;
                    } else {
                        result = sendAccountToGis.callImportAccountData(listForClose.subList(count, count += 5));
                    }

                    if (result != null && result.getCommonResult() != null) {
                        for (ImportResult.CommonResult commonResult : result.getCommonResult()) {
                            if (commonResult.getError() == null || commonResult.getError().size() == 0) {
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

                } catch (SQLException e) {
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
    private LinkedHashMap<String, ImportAccountRequest.Account> getAccountsFromGrad(final int houseId,
                                                                                    final Connection connectionGrad)
            throws ParseException, SQLException, PreGISException {

        return accountGRADDAO.getAccountMapFromGrad(houseId, connectionGrad, null);
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
