package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.house_management.*;
import ru.gosuslugi.dom.schema.integration.individual_registry_base.ID;
import ru.gosuslugi.dom.schema.integration.organizations_registry_base.RegOrgVersionType;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.connectiondb.grad.account.AccountGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSI;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.model.*;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;
import ru.progmatik.java.pregis.services.organizations.ExportOrgRegistry;
import ru.progmatik.java.web.servlets.listener.ClientDialogWindowObservable;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс, синхронизирует лицевые счета ГРАДа и ГИС ЖКХ.
 */
public final class UpdateAllAccountData implements ClientDialogWindowObservable {

    private static final Logger LOGGER = Logger.getLogger(UpdateAllAccountData.class);
    private static final int ACCOUNT_COUNTER_FOR_REQUEST = 25;
    private final AnswerProcessing answerProcessing;
    private final AccountGRADDAO accountGRADDAO;
//    private final ArrayList<ImportAccountRequest.Account> accountsForCloseList = new ArrayList<>();
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

                    final LinkedHashMap<AbonentInformation, ImportAccountRequest.Account> accountListFromGrad = new LinkedHashMap<>();
                    // получаем лицеваые из Град
                    getAccountsFromGrad(itemHouse.getValue().getGrad_id(), connectionGRAD, accountListFromGrad);

                    // получаем лицевые из ГИС
                    final GetStateResult stateResult = HomeManagementAsyncPort.callExportAccountData(itemHouse.getKey(), answerProcessing);

                    countAll += accountListFromGrad.size();

                    if (stateResult == null){ // || stateResult.getExportAccountResult() == null || stateResult.getExportAccountResult().size() == 0) { // если не получили не однин лс.
                        errorState = 0;
                    } else if (stateResult.getErrorMessage() != null && stateResult.getErrorMessage().getErrorCode().equalsIgnoreCase("INT002012")) { // Если нет объектов для экспорта
                        checkAndSendAccountData(null, accountListFromGrad, itemHouse.getValue(), connectionGRAD);
                    } else if((stateResult.getErrorMessage() != null) || (stateResult.getExportAccountResult() == null) || stateResult.getExportAccountResult().isEmpty()){
                        errorState = 0;
                    }else {
    //                    List<ExCportAccountResultType> accountsListFromGISJKH = exportAccountResult.getAccounts();
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
        }
        // лицевые на закрытие обрабатываются после ответа пользователя в go()
// изменили - теперь пользователя не спрашиваем, так как процесс останавливается пока он не ответит
//       if (accountsForCloseList.size() > 0) {
//            setErrorState(0);
//            if (answerProcessing != null) {
//                answerProcessing.showQuestionToClient("В ГИС ЖКХ найдены лицевые счета клиентов (" + accountsForCloseList.size() +
//                        "), которые дублируются. Желаете их закрыть?\n", this);
//            }
//        }

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
            answerProcessing.sendMessageToClient("Лицевых счетов отправлено в ГИС ЖКХ: " + countAdded);
            answerProcessing.sendMessageToClient("Идентификаторов отправлено в ГРАД: " + countAddedToGRAD);
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
     * @param accountsMapFromGrad    список абонентов из БД ГРАД.
     * @param houseRecord            объект дома в БД ГРАД
     * @param connection             подключение к БД ГРАД.
     */
    private void checkAndSendAccountData(final List<ExportAccountResultType> accountsListFromGISJKH,
                                         final LinkedHashMap<AbonentInformation,ImportAccountRequest.Account> accountsMapFromGrad,
                                         final HouseRecord houseRecord, final Connection connection) throws SQLException, PreGISException, ParseException {

        // для удобства поиска создаем мапу с лицевыми счетами из ГИС. Ключ - ЛС из Града
        Map<String, ExportAccountResultType> accountsMapFromGISJKH = new LinkedHashMap<>();

        // мапа для создаваемых/обнвляемых ЛС. Ключ - TransportGUID, значение - Account
        final LinkedHashMap<String, ImportAccountRequest.Account> accountsCreateMap = new LinkedHashMap<>();

        // массив счетов на закрытие в ГИС
        ArrayList<ImportAccountRequest.Account> accountsForCloseList = new ArrayList<>();

        // если в ГИС есть что-то - начинаем сравниваьт с данными Град
        if (accountsListFromGISJKH != null) {
            // сначала находим дубликаты только в ГИС с одинаковым ЛС, лишние закрываем
            checkDuplicateAccountDataGisJkh(accountsListFromGISJKH, accountsForCloseList);

            checkIncorrectAccountGisJkh(accountsListFromGISJKH, accountsMapFromGrad);

            // заполняем мапу с данными ГИС, чтобы было удобно искать. Все что в ней останется после сравнения с Град, подлежит закрытию
            accountsMapFromGISJKH = accountsListFromGISJKH
                    .stream()
                    .filter(e->e.getClosed() == null)
                    .collect(Collectors.toMap(ExportAccountResultType::getAccountNumber, e->e));

            countAllGisJkh += accountsListFromGISJKH.size();

            // бежим по ЛС из Града
            for (Map.Entry<AbonentInformation, ImportAccountRequest.Account> entry : accountsMapFromGrad.entrySet()) {
                // если еще не входит в список на закрытие
                if (!isInClosedAccountList(entry.getValue().getAccountNumber(), accountsForCloseList)) {
                    // получаем ACCOUNTUNIQNUM абонента
//                    final Integer abonId = accountGRADDAO.getAbonentIdFromGrad(entry.getKey(), connection);
                    if(entry.getKey().getGradID() > 0) {
                        // final String uniqueNumberFromDB = accountGRADDAO.getUnifiedAccountNumber(abonId, connection);

                        // если есть в ГИС с таким лицевым счетом
                        if(accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()) != null) {
                            // если в Граде у абонента ACCOUNTGUID пустой
                            if (entry.getValue().getAccountGUID() == null || entry.getValue().getAccountGUID().isEmpty() ||
                                    // или Идентификатор жилищно-коммунальной услуги пустой
                                    entry.getKey().getServiceID() == null || entry.getKey().getServiceID().isEmpty() ||
                                    // или при одинаковом ЛС разные GUID
                                    !accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()).getAccountGUID().equalsIgnoreCase(entry.getValue().getAccountGUID()) ||
                                    // при одинаковом ЛС разные UnifiedAccountNumber для организации
                                    !accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()).getServiceID().equalsIgnoreCase(entry.getKey().getServiceID())
                                    ) {
                                // проверяем и впытаемся занести его ACCOUNTGUID в БД
                                if (!sendAccountDataToGrad(accountsMapFromGISJKH.get(entry.getValue().getAccountNumber()), entry.getValue(),
                                        houseRecord.getGrad_id(), connection)) {
                                    // если не занесли - просто сообщим об этом юзеру addEntryToGISMap(accountsCreateMap, entry.getValue());
                                    answerProcessing.sendMessageToClient(String.format("Не удалось обновить идентификаторы ГИС абонента в Град, ЛС: %s", entry.getKey().getNumberLS()));
                                }
                            }else{
                                // если все совпадает (аккаунт и ИД ЛС), но разные плательщики (по ФИО или по организации) - добавляем на обновление в ГИС
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
                                    addEntryToGISMap(accountsCreateMap, entry.getValue());
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
                                        addEntryToGISMap(accountsCreateMap, entry.getValue());
                                    }
                                }
                            }
                            // убираем из мапы ГИС, так как он у нас есть уже в ГИС и будет просто обновлен, если что-от не совпадает
                            accountsMapFromGISJKH.remove(entry.getValue().getAccountNumber());
                        }else {
                            entry.getValue().setAccountGUID(null);
                            addEntryToGISMap(accountsCreateMap, entry.getValue());
                        }
                    }
                }
            }
        } else {// если в ГИС ничего нет - всё просто добавляем
            for (Map.Entry<AbonentInformation, ImportAccountRequest.Account> entry : accountsMapFromGrad.entrySet()) {
                if (!isInClosedAccountList(entry.getKey().getNumberLS(), accountsForCloseList)) {
                    if(entry.getValue().getAccountGUID() == null) {
//                        final Integer abonId = accountGRADDAO.getAbonentIdFromGrad(entry.getKey(), connection);
                        if(entry.getKey().getGradID() > 0) {
//                            final String accountGUIDFromBase = accountGRADDAO.getAccountGUIDFromBase(abonId, connection);
//
//                            if (accountGUIDFromBase == null) {
//
                            addEntryToGISMap(accountsCreateMap, entry.getValue());
//                            }
                        }
                    }
                }
            }
        }
        // если есть счета на создание/обновление в ГИС - отсылаем их в гис
        if (!accountsCreateMap.isEmpty()) {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendMessageToClient("Счетов для создания/обновления в ГИС ЖКХ:" + accountsCreateMap.size());
            sendAccountDataToGISJKH(accountsCreateMap, houseRecord.getGrad_id(), connection);
        }

        // если в мапе ГИС остались ЛС - значит их у нас нет и мы их отправляем на закрытие
        if(!accountsMapFromGISJKH.isEmpty()){
            // получаем помещений из ГИС
            Map<String, Boolean>  housePremises = getHousePremises(houseRecord.getFias());

            // если из ГИС не получили помещения - ничего не закрываем
            if(!housePremises.isEmpty()) {

                for (ExportAccountResultType accountForClose : accountsMapFromGISJKH.values()) {
                    // если есть помещение в списке помещений дома
                    if (housePremises.containsKey(accountForClose.getAccommodation().get(0).getPremisesGUID())) {
                        // если помещение жилое
                        if (housePremises.get(accountForClose.getAccommodation().get(0).getPremisesGUID())) {
                            // добавляем на закрытие
                            answerProcessing.sendMessageToClient(String.format("Абонент ЛС %s отсылается на закрытие В ГИС ЖКХ", accountForClose.getAccountNumber()));
                            addAccountDataForClose(accountForClose,
                                    "Изменение реквизитов лицевого счета",
                                    "Абонент закрыт в ИС предприятия",
                                    accountsForCloseList);
                        }else{
                            answerProcessing.sendMessageToClient(String.format("Абонент ЛС %s нежилое, закрытие В ГИС ЖКХ не производится", accountForClose.getAccountNumber()));
                        }
                    }
                }
            }
        }

        if(!accountsForCloseList.isEmpty()) {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendMessageToClient("Счетов для закрытия в ГИС ЖКХ:" + accountsForCloseList.size());

//            Map<String, ImportAccountRequest.Account> accountsForCloseMap = new LinkedHashMap<>();
//            for(ImportAccountRequest.Account accountForClose :accountsForCloseList){
//                addEntryToGISMap(accountsForCloseMap, accountForClose);
//            }
            sendAccountDataToGISJKH(accountsForCloseList.stream().collect(Collectors.toMap(e->e.getTransportGUID(), e->e)), houseRecord.getGrad_id(), connection);

        }

        if(accountsCreateMap.isEmpty() && accountsForCloseList.isEmpty()) {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendMessageToClient("Счетов для выгрузки в ГИС ЖКХ нет.");
        }

    }

    /**
     * вспомогательный метод. возвращает список GUID помещений с признаком жилое/нежилое
     */
    private Map<String, Boolean> getHousePremises(final String fias){
        Map<String, Boolean> premisesMap = new HashMap<>();

        answerProcessing.sendMessageToClient("В связи с обнаружением абонентов на закрытие производится запрос в ГИС ЖКХ списка помещений для сравнения");

        try {
            final GetStateResult result = HomeManagementAsyncPort.callExportHouseData(fias, answerProcessing);
            if (result != null && result.getErrorMessage() == null && result.getExportHouseResult() != null) { // Если нет ошибок
                for (ExportHouseResultType.ApartmentHouse.ResidentialPremises residentialPremises : result.getExportHouseResult().getApartmentHouse().getResidentialPremises()) {
                    premisesMap.put(residentialPremises.getPremisesGUID(), Boolean.TRUE);
                }
                for (ExportHouseResultType.ApartmentHouse.NonResidentialPremises nonResidentialPremises : result.getExportHouseResult().getApartmentHouse().getNonResidentialPremises()) {
                    premisesMap.put(nonResidentialPremises.getPremisesGUID(), Boolean.FALSE);
                }
            }
        } catch (SQLException | PreGISException e) {
            e.printStackTrace();
        }

        return premisesMap;
    }

    /**
     * Вспомогательный метод для checkAndSendAccountData
     * @param accountDataMap ссылка на мапу для отсылки в ГИС
     * @param entryValue инфа по абоненту
     */
    private void addEntryToGISMap(Map<String, ImportAccountRequest.Account> accountDataMap, ImportAccountRequest.Account entryValue){
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
    private void checkDuplicateAccountDataGisJkh(final List<ExportAccountResultType> exportAccountResult, ArrayList<ImportAccountRequest.Account> accountsForCloseList) throws PreGISException, SQLException {

        List<ExportAccountResultType> listForClose = new ArrayList<>();
        int indexForClose = 0;
        for (int i = 0; i < exportAccountResult.size(); i++) {

            String ar1 = exportAccountResult.get(i).getAccountNumber();

            for (int j = i + 1; j < exportAccountResult.size(); j++) {

                String ar2 = exportAccountResult.get(j).getAccountNumber();

                if (ar1 != null && ar2 != null &&
                        !exportAccountResult.get(i).getAccountGUID().equals(exportAccountResult.get(j).getAccountGUID()) &&
                        ar1.equalsIgnoreCase(ar2) &&
                        (exportAccountResult.get(i).getClosed() == null && exportAccountResult.get(j).getClosed() == null)) {

                    if(exportAccountResult.get(j).getCreationDate().toGregorianCalendar()
                            .compareTo(exportAccountResult.get(i).getCreationDate().toGregorianCalendar()) < 0){
                        indexForClose = j;
                    }else{
                        indexForClose = i;
                    }

                    if(listForClose.indexOf(exportAccountResult.get(indexForClose)) == -1) {
                        listForClose.add(exportAccountResult.get(indexForClose));

                        answerProcessing.sendMessageToClient(String.format("Номер лицевого счета %s продублирован в ГИС ЖКХ, более старый будет закрыт.", exportAccountResult.get(j).getAccountNumber()));
                        addAccountDataForClose(exportAccountResult.get(indexForClose), "Изменение реквизитов лицевого счета", "Абонент продублирован в ГИС", accountsForCloseList);
                    }
                }
            }
        }
        if(!listForClose.isEmpty()){
            exportAccountResult.removeAll(listForClose);
        }
    }

    /**
     * Бежим по списку из ГИС ЖКХ и по ЛС ищем абонента в списке Граде. Если не нашли - ставим на удаление, занесен в ГИС ошибочно
     * @param exportAccountResult
     * @throws PreGISException
     * @throws SQLException
     */
    private void checkIncorrectAccountGisJkh(final List<ExportAccountResultType> exportAccountResult,
                                             LinkedHashMap<AbonentInformation,ImportAccountRequest.Account> accountListFromGrad) throws PreGISException, SQLException {
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
    private boolean isInClosedAccountList(final String accountNumber, ArrayList<ImportAccountRequest.Account> accountsForCloseList) {

        return !accountsForCloseList.stream().filter(e->e.getAccountNumber().equalsIgnoreCase(accountNumber)).collect(Collectors.toList()).isEmpty();

//        for (ImportAccountRequest.Account account : accountsForCloseList) {
//            if (accountNumber.equalsIgnoreCase(account.getAccountNumber())) {
//                return true;
//            }
//        }
//        return false;
    }

    /**
     * Метод, добавляет в список абонентов, которые выгружены в ГИС ЖКХ более одного раза.
     *
     * @param account данные абонента.
     * @throws PreGISException возникнит ошибка, если не удастся загрузить справочник из ГИС ЖКХ.
     */
    private void addAccountDataForClose(final ExportAccountResultType account, final String nameElement, final String message, ArrayList<ImportAccountRequest.Account> accountsForCloseList) throws SQLException, PreGISException {

        if(account.getClosed() != null){
            return;
        }
        // если уже входит в список на закрытие - ничего не делаем
        if(!accountsForCloseList.stream().filter(e->e.getAccountGUID().equalsIgnoreCase(account.getAccountGUID())).collect(Collectors.toList()).isEmpty()) {
            return;
        }
            //     старый код
        // for (ImportAccountRequest.Account accountItem : accountsForCloseList) {
//            if (account.getAccountGUID().equalsIgnoreCase(accountItem.getAccountGUID())) {
//
//                return;
//            }
//        }

// "Изменение реквизитов лицевого счета"
        final ReferenceNSI referenceNSI = new ReferenceNSI(answerProcessing);

        final ImportAccountRequest.Account accountClose = new ImportAccountRequest.Account();
        accountClose.setAccountGUID(account.getAccountGUID());
        accountClose.setClosed(new ClosedAccountAttributesType());
        accountClose.getClosed().setCloseDate(OtherFormat.getDateNow());
        accountClose.getClosed().setCloseReason(referenceNSI.getNsiRef("22", nameElement));
        accountClose.getClosed().setDescription(message);
        accountClose.setCreationDate(account.getCreationDate());
        accountClose.setAccountNumber(account.getAccountNumber());
        accountClose.getAccommodation().addAll(convertAccommodation(account.getAccommodation()));
        accountClose.setPayerInfo(convertPayer(account.getPayerInfo()));
        accountClose.setTransportGUID(OtherFormat.getRandomGUID());
        accountClose.setTotalSquare(account.getTotalSquare());
//        accountClose.setResidentialSquare(account.getResidentialSquare());
//        accountClose.setHeatedArea(account.getHeatedArea());
        AccountGRADDAO.setIsAccount(accountClose);

//        answerProcessing.sendMessageToClient("");
//        answerProcessing.sendMessageToClient(message + "; ЛС " + account.getAccountNumber());

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


        if (sInd != null &&
                sInd.getFirstName() != null && !sInd.getFirstName().isEmpty() &&
                sInd.getSurname() != null && !sInd.getSurname().isEmpty()) {
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
    private boolean sendAccountDataToGrad(final ExportAccountResultType accountFromGISJKH,
                                          final ImportAccountRequest.Account account,
                                          final Integer houseId,
                                          final Connection connection)
            throws ParseException, SQLException, PreGISException {
        if (accountFromGISJKH.getClosed() == null) {
            if (account.getAccountNumber().equalsIgnoreCase(accountFromGISJKH.getAccountNumber())) { // если в БД есть элемент, добавляем идентификаторы в БД

                    setAccountToBase(houseId, accountFromGISJKH.getAccountNumber(), accountFromGISJKH.getAccountGUID(), accountFromGISJKH.getUnifiedAccountNumber(), accountFromGISJKH.getServiceID(), connection);
                    return true;
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
    private void sendAccountDataToGISJKH(final Map<String, ImportAccountRequest.Account> accountDataFromGrad,
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
            final int chunk = ResourcesUtil.instance().getMaxRequestSize();
            if (count + chunk > accountsList.size()) {
                result = HomeManagementAsyncPort.callImportAccountData(accountsList.subList(count, accountsList.size()), answerProcessing);
//                result = sendAccountToGis.callImportAccountData(accountsList.subList(count, accountsList.size()));
                count += chunk;
            } else {
                result = HomeManagementAsyncPort.callImportAccountData(accountsList.subList(count, count += chunk), answerProcessing);
            }

            if (result != null && result.getImportResult() != null) {

                for (ImportResult importResult : result.getImportResult()) {
                    for (ImportResult.CommonResult commonResult : importResult.getCommonResult()) {

                        if (commonResult.getError() == null || commonResult.getError().isEmpty()) {
                            countAdded++;
                            if(accountDataFromGrad.get(commonResult.getTransportGUID()).getClosed() == null) { // обновляем если не закрывали
                                setAccountToBase(houseId,
                                        accountDataFromGrad.get(commonResult.getTransportGUID()).getAccountNumber(),
                                        commonResult.getGUID(),
                                        commonResult.getImportAccount().getUnifiedAccountNumber(),
                                        commonResult.getImportAccount().getServiceID(),
                                        connection);
                            }
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
    private void setAccountToBase(Integer houseId, String accountNumber, String accountGUID, String accountUniqueNumber, String serviceID, Connection connection) throws ParseException, SQLException, PreGISException {

        if (AccountGRADDAO.setAccountGuidAndUniqueNumber(houseId, accountNumber, accountGUID, accountUniqueNumber, serviceID, connection, answerProcessing)) {
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

//        if (!listForClose.isEmpty()) {
//
//            answerProcessing.sendMessageToClient("Закрытие ЛС...");
//            // ImportAccountData sendAccountToGis = new ImportAccountData(answerProcessing);
//            GetStateResult result;
//            int count = 0;
//
//            while (count < listForClose.size()) {
//
//                answerProcessing.sendMessageToClient("::clearLabelText");
//
//                try {
//                    if (count + 5 > listForClose.size()) {
//                        result = HomeManagementAsyncPort.callImportAccountData(listForClose.subList(count, listForClose.size()), answerProcessing);
//                        count += 5;
//                    } else {
//                        result = HomeManagementAsyncPort.callImportAccountData(listForClose.subList(count, count += 5), answerProcessing);
//                    }
//
//                    for (ImportResult importResult : result.getImportResult()) {
//                        for (ImportResult.CommonResult commonResult : importResult.getCommonResult()) {
//                            if (commonResult.getError() == null || commonResult.getError().isEmpty()) {
//                                answerProcessing.sendMessageToClient("");
//                                answerProcessing.sendMessageToClient(String.format("Уникальный номер абонента ГИС ЖКХ: %s, успешно закрыт.", commonResult.getUniqueNumber()));
//                            } else {
//                                answerProcessing.sendMessageToClient("");
//                                answerProcessing.sendMessageToClient("Код ошибки: " + commonResult.getError().get(0).getErrorCode());
//                                answerProcessing.sendMessageToClient("Описание ошибки: " + commonResult.getError().get(0).getDescription());
//                                setErrorState(0);
//                            }
//                        }
//                    }
//
//                } catch (SQLException | PreGISException e) {
//                    answerProcessing.sendErrorToClient("Закрытие ЛС", "\"Закрытие ЛС\"", LOGGER, e);
//                }
//
//            }
//            accountsForCloseList.clear();
//        }
//        if (errorState == -1) {
//            answerProcessing.sendMessageToClient("");
//            answerProcessing.sendErrorToClientNotException("Возникла ошибка!\nОперация: \"Закрытие ЛС\" прервана!");
//        } else if (errorState == 0) {
//            answerProcessing.sendMessageToClient("");
//            answerProcessing.sendErrorToClientNotException("Операция: \"Закрытие ЛС\" завершилась с ошибками!");
//        } else if (errorState == 1) {
//            answerProcessing.sendMessageToClient("");
//            answerProcessing.sendOkMessageToClient("\"Закрытие ЛС\" успешно выполнено.");
//        }
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
                                      final LinkedHashMap<AbonentInformation, ImportAccountRequest.Account> mapAccount)
            throws ParseException, SQLException, PreGISException {


        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Формирую данные...");
        final ArrayList<AbonentInformation> abonentInformationList = AccountGRADDAO.getBasicInformation(houseId, connectionGrad, answerProcessing);

//        LinkedHashMap<String, Room> roomsList = AccountGRADDAO.getLsRoomsMaps(houseId, connectionGrad);

        //        final ArrayList<Room> roomsList = getRooms(houseID, connection);
        final ReferenceNSI nsi = new ReferenceNSI(answerProcessing);
//        ExportOrgRegistry orgRegistry = new ExportOrgRegistry(answerProcessing);

//        LinkedHashMap<AbonentInformation, ImportAccountRequest.Account> mapAccount = new LinkedHashMap<>();

        if (abonentInformationList == null){ // || roomsList == null) {
            answerProcessing.sendInformationToClientAndLog("Не найдены лицевые счета для дома с ИД: " + houseId + ".", LOGGER);
            return;
        }

        // получаем инфу из ГИС с идентификаторами организаций
        obtainOrgsVersionsIds(abonentInformationList, connectionGrad);

        for (AbonentInformation abonentInformation : abonentInformationList) {
            int count = 0;
            // for (int i = 0; i < roomsList.size(); i++) {

//                Формируем объект пригодный для импорта в ГИС ЖКХ.
//                По необходимости в дальнейшем нужно присвоить перед отправкой:
//                - если новый счет указать TransportGUID, удалить если есть AccountGUID.
//                - если счет к закрытию указать AccountGUID и isClosed.

//            Room fRoom = roomsList.get(abonentInformation.getNumberLS());
            String premisesGUID = null;
            String livingRoomGUID = null;
//            if (fRoom != null) {
                // заранее получаем premisesGUID и livingRoomGUID, если не заданы - вообще не добавляем объект, потому что вызовет ошибку
                if(abonentInformation.getPremisesGUID() == null || abonentInformation.getPremisesGUID().isEmpty()) {
                    premisesGUID = AccountGRADDAO.getBuildingIdentifiersFromBase(abonentInformation.getGradID(), "PREMISESGUID", connectionGrad);
                }else{
                    premisesGUID = abonentInformation.getPremisesGUID();
                }

                livingRoomGUID = abonentInformation.getLivingRoomGUID();
                // livingRoomGUID = AccountGRADDAO.getBuildingIdentifiersFromBase(abonentInformation.getGradID(), "LIVINGROOMGUID", connectionGrad);

// дублирование сообщения ниже                if (premisesGUID == null && livingRoomGUID == null) {
//                    answerProcessing.sendInformationToClientAndLog("Для абонента ГРАД ИД "
//                            + abonentInformation.getNumberLS() + " не найдено идентификатора помещения или комнаты", LOGGER);
//                }
//            }
            if (
//                    fRoom != null &&
                    !((premisesGUID == null || premisesGUID.isEmpty()) && (livingRoomGUID == null || livingRoomGUID.isEmpty())) // помещение или комната ОБЯЗАНЫ БЫТЬ!
                    && ((abonentInformation.getOgrnOrOgrnip() == null || abonentInformation.getOgrnOrOgrnip().isEmpty()) || // или частное лицо
                        (!(abonentInformation.getOgrnOrOgrnip() == null || abonentInformation.getOgrnOrOgrnip().isEmpty()) && !(abonentInformation.getOrgVersionGUID() == null || abonentInformation.getOrgVersionGUID().isEmpty()))) // или есть идентификатор
                    ) {
// debug                    answerProcessing.sendMessageToClient(abonentInformation.getNumberLS() + " found!");
                ImportAccountRequest.Account account = new ImportAccountRequest.Account();
//                    account.setCreationDate(OtherFormat.getDateNow()); // может без даты можно? добавил при отправки нового счета
                account.setLivingPersonsNumber(abonentInformation.getAmountLiving());
                account.setTotalSquare(new BigDecimal(abonentInformation.getTotalArea()).setScale(2, BigDecimal.ROUND_DOWN));
                account.setResidentialSquare(new BigDecimal(abonentInformation.getLivingSpace()).setScale(2, BigDecimal.ROUND_DOWN));
                if (abonentInformation.getHeadtedArea() > 0.0) {
                    account.setHeatedArea(new BigDecimal(abonentInformation.getHeadtedArea()).setScale(2, BigDecimal.ROUND_DOWN));
                }
//                    account.setClosed(); // проверить, если в ГИС ЖКХ есть, а в БД ГРАД нет, то установить в ExportAccountData.
                AccountType.Accommodation accommodation = new AccountType.Accommodation();
                if(livingRoomGUID == null || livingRoomGUID.isEmpty()) {
                    accommodation.setPremisesGUID(premisesGUID);
                }else {
//                    accommodation.setFIASHouseGuid(roomsList.get(i).getFias()); // Выдаё ошибку, по описанию можно выбирать.
                    accommodation.setLivingRoomGUID(livingRoomGUID); // Идентификатор комнаты
                }
                accommodation.setSharePercent(new BigDecimal(abonentInformation.getSharePay()).setScale(0, BigDecimal.ROUND_DOWN));
                if(accommodation.getSharePercent() == null) accommodation.setSharePercent(new BigDecimal(100)); // если ничего не пришло - ставим 100, иначе ГИС выдает ошибку

                account.getAccommodation().add(accommodation);
//                    account.setTransportGUID();  // указывается, если ЛС добавляется в первые.

                // Сведения о плательщике
                account.setPayerInfo(new AccountType.PayerInfo());

                if (abonentInformation.getOgrnOrOgrnip() == null || abonentInformation.getOgrnOrOgrnip().isEmpty()) { // частное лицо (не юр и не ИП)

                    if (abonentInformation.getSurname() != null && !abonentInformation.getSurname().trim().isEmpty()
                            && abonentInformation.getName() != null && !abonentInformation.getName().trim().isEmpty()) {

                        account.getPayerInfo().setInd(new AccountIndType());

                        account.getPayerInfo().getInd().setSurname(abonentInformation.getSurname());

                        account.getPayerInfo().getInd().setFirstName(abonentInformation.getName());

                        if (abonentInformation.getMiddleName() != null) {
                            account.getPayerInfo().getInd().setPatronymic(abonentInformation.getMiddleName());
                        }


                        if (abonentInformation.getNumberDocumentIdentity() != null) { // будем создавать только если есть номер документа!

                            account.getPayerInfo().getInd().setID(new ID()); // подгрузить справочник NSI 95

                            if (abonentInformation.getTypeDocument() != null) {
                                account.getPayerInfo().getInd().getID().setType(nsi.getTypeDocumentNsiRef(abonentInformation.getTypeDocument().getTypeDocument()));
                            } else {
                                account.getPayerInfo().getInd().getID().setType(nsi.getTypeDocumentNsiRef(DocumentType.getTypeDocument("паспорт").getTypeDocument()));
                            }

                            if (abonentInformation.getNumberDocumentIdentity() != null && !abonentInformation.getNumberDocumentIdentity().isEmpty()) {
                                account.getPayerInfo().getInd().getID().setNumber(abonentInformation.getNumberDocumentIdentity());
                            } else {
                                account.getPayerInfo().getInd().getID().setNumber("000000");
                            }


                            if (abonentInformation.getSeriesDocumentIdentity() != null && !abonentInformation.getSeriesDocumentIdentity().isEmpty()) {
                                account.getPayerInfo().getInd().getID().setSeries(abonentInformation.getSeriesDocumentIdentity());
                            } else {
                                account.getPayerInfo().getInd().getID().setSeries("0000");
                            }

                            if (abonentInformation.getDateDocumentIdentity() != null) {
                                account.getPayerInfo().getInd().getID().setIssueDate(OtherFormat.getDateForXML(abonentInformation.getDateDocumentIdentity()));
                            } else {
                                account.getPayerInfo().getInd().getID().setIssueDate(OtherFormat.getDateForXML((new SimpleDateFormat("dd.MM.yyyy")).parse("01.01.2017")));
                            }
                        }
                    }

                } else {
                    // заносим плательщика с ОГРН, только если у него есть OrgVersionGUID
                    if (abonentInformation.getOrgVersionGUID() != null && !abonentInformation.getOrgVersionGUID().isEmpty()) {
                        // Сведения о плательщике
                        account.setPayerInfo(new AccountType.PayerInfo());
                        account.getPayerInfo().setOrg(new RegOrgVersionType());
                        account.getPayerInfo().getOrg().setOrgVersionGUID(abonentInformation.getOrgVersionGUID());
                    }
                }

                if (abonentInformation.getEmployer() == AnswerYesOrNo.YES) {
                    account.getPayerInfo().setIsRenter(true); // выдаёт ошибку если указать false
                }


                account.setAccountNumber(abonentInformation.getNumberLS());
                if(abonentInformation.getAccountGUID() == null || abonentInformation.getAccountGUID().isEmpty()) {
                    account.setAccountGUID(AccountGRADDAO.getAccountGUIDFromBase(abonentInformation.getGradID(), connectionGrad));  // добавляется, если счет будет изменен или закрыт, если этого не будет перед отправкой затереть.
                }else{
                    account.setAccountGUID(abonentInformation.getAccountGUID());
                }
                AccountGRADDAO.setIsAccount(account);
                account.setCreationDate(OtherFormat.getDateNow());

                //if (account.getAccountGUID() != null && !account.getAccountGUID().equals("")) {
                mapAccount.put(abonentInformation, account);

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
                builder.append(abonentInformation.getSurname());
                builder.append(" ");
                builder.append(abonentInformation.getName());
                builder.append(" ");
                builder.append(abonentInformation.getMiddleName());
                builder.append(" ");

                String fio = builder.toString().replaceAll("null", "").trim();
                fio = fio.isEmpty() ? notFound : fio;

//                if (abonentInformation.getTypeDocument() == null || abonentInformation.getTypeDocument().getTypeDocument() == null || abonentInformation.getNumberDocumentIdentity() == null) {
//                    answerProcessing.sendInformationToClientAndLog(String.format(
//                            "\nДля абонента с ФИО: %s,\nНомер счета: %s.\nНе удалось найти соответствие \"Помещение\" в базе данных.",
//                            fio, abonentInformation.getNumberLS()), LOGGER);
//                } else {
                    answerProcessing.sendInformationToClientAndLog(String.format(
                            "\nДля абонента с ФИО: %s, ЛС: %s, ИД Град: %s не удалось найти соответствие \"Помещение\" в базе данных.",
                            fio, abonentInformation.getNumberLS(), abonentInformation.getGradID()), LOGGER);
//                }

            } else if (count >= 2) {
                answerProcessing.sendInformationToClientAndLog("Для счета - "
                        + abonentInformation.getNumberLS() + " найдено более одного соответствия в базе данных.", LOGGER);
            }
        }
        return;
    }

    /** получаем инфу из ГИС с идентификаторами организаций
     *
     * @param abonentInformationList - список информации об абонентах, полученных из Град, в котором необходимо установить значения
     */
    private void obtainOrgsVersionsIds(ArrayList<AbonentInformation> abonentInformationList, Connection connectionGrad) throws PreGISException {

        // список организаций без идентификаторов ГИС
        List<AbonentInformation> abonentInformationWOversionIDS = abonentInformationList.stream()
                .filter(e -> !(e.getOgrnOrOgrnip() == null || e.getOgrnOrOgrnip().isEmpty()) && (e.getOrgVersionGUID() == null || e.getOrgVersionGUID().isEmpty()))
                .collect(Collectors.toList());

        if(!abonentInformationWOversionIDS.isEmpty()) {
            List<String> orgsWOversionIDs = abonentInformationWOversionIDS.stream()
                    .map(AbonentInformation::getOgrnOrOgrnip)
                    .distinct()
                    .collect(Collectors.toList());

            answerProcessing.sendMessageToClient("Имеются абоненты, принадлежащие юр.лицам без идентификаторов. Производится запрос идентификаторов в ГИС");

            final ExportOrgRegistry exportOrgRegistry = new ExportOrgRegistry(answerProcessing);
            Map<String, Organization> ogrn2Org = exportOrgRegistry.getOrgsVersionFromGis(orgsWOversionIDs);

            if(ogrn2Org.isEmpty()){
                answerProcessing.sendMessageToClient("\nИнформация по юр.лицам без идентификаторов не получена!");
            }else {
                answerProcessing.sendMessageToClient("\nОбновляется информация в Град по юр.лицам без идентификаторов");


                for (AbonentInformation abonentInformation : abonentInformationWOversionIDS) {

                    if(abonentInformation.getOgrnOrOgrnip() != null && ogrn2Org.get(abonentInformation.getOgrnOrOgrnip()) != null) { // могло и не обновиться
                        String versionId = ogrn2Org.get(abonentInformation.getOgrnOrOgrnip()).getOrgVersionGUID();

                        if (versionId != null && !versionId.isEmpty()) {
                            // обновляем инофрмацию об абоненте в памяти
                            abonentInformation.setOrgVersionGUID(versionId);
                            // заносим в Град
                            AccountGRADDAO.setOrgVersionGUID(abonentInformation.getGradID(), versionId, connectionGrad, answerProcessing);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void go() {
//        closeAccountData(accountsForCloseList);
    }

    @Override
    public void stop() {
//        accountsForCloseList.clear();
    }
}
