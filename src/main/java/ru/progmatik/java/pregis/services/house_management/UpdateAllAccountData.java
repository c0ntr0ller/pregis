package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.house_management.*;
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

/**
 * Класс, синхронизирует лицевые счета ГРАДа и ГИС ЖКХ.
 */
public class UpdateAllAccountData implements ClientDialogWindowObservable {

    private static final Logger LOGGER = Logger.getLogger(UpdateAllAccountData.class);
    private final AnswerProcessing answerProcessing;
    private final AccountGRADDAO accountGRADDAO;
    private final ArrayList<ImportAccountRequest.Account> accountsForCloseList = new ArrayList<>();
    private int countAll;
    private int countAllGisJkh;
    private int countRemove;
    private int countAdded;
    private int errorState;

    public UpdateAllAccountData(AnswerProcessing answerProcessing) throws SQLException {
        this.answerProcessing = answerProcessing;
        accountGRADDAO = new AccountGRADDAO(answerProcessing);
    }

    /**
     * Метод, получает из БД список всех выгруженных домов в ГИС ЖКХ, получает по ФИАС данные о лицевых счетах дома из ГИС ЖКХ.
     * Получает из БД по ИД этого же дома информацию о счетах, производит проверку, при необходимости закрывает счет в ГИС ЖКХ или выгружает новый.
     */
    public int updateAllAccountData() throws SQLException, PreGISException, ParseException {
        countAll = 0;
        countAllGisJkh = 0;
        countRemove = 0;
        countAdded = 0;
        errorState = 1;

        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {

            HouseGRADDAO graddao = new HouseGRADDAO();

            LinkedHashMap<String, Integer> houseAddedGisJkh = graddao.getHouseAddedGisJkh(connectionGRAD);
            if (houseAddedGisJkh == null) {
                throw new PreGISException("Не найден ни один дом готовый для выгрузки ГИС ЖКХ.");
            }

            ExportAccountData accountData = new ExportAccountData(answerProcessing);

            for (Map.Entry<String, Integer> itemHouse : houseAddedGisJkh.entrySet()) {

                answerProcessing.sendMessageToClient("");
                answerProcessing.sendMessageToClient("Обрабатываю дом...");
                answerProcessing.sendMessageToClient("Код дома по ФИАС: " + itemHouse.getKey());
                answerProcessing.sendMessageToClient("Код дома в системе \"ГРАД\": " + itemHouse.getValue());

                ExportAccountResult exportAccountResult = accountData.callExportAccountData(itemHouse.getKey());
                LinkedHashMap<String, ImportAccountRequest.Account> accountListFromGrad = accountGRADDAO.getAccountMapFromGrad(itemHouse.getValue(), connectionGRAD);
                countAll = accountListFromGrad.size();

                if (exportAccountResult == null) { // если не получили не однин лс.
                    errorState = 0;
                } else if (exportAccountResult.getErrorMessage() != null && exportAccountResult.getErrorMessage().getErrorCode().equalsIgnoreCase("INT002012")) { // Если нет объектов для экспорта
                    checkAndSendAccountData(null, accountListFromGrad, itemHouse.getValue(), connectionGRAD);
                } else {
                    countAllGisJkh += exportAccountResult.getAccounts().size();
                    checkAndSendAccountData(exportAccountResult, accountListFromGrad, itemHouse.getValue(), connectionGRAD);
                }
            }
        }
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Всего лицевых счетов найдено в ГИС ЖКХ: " + countAllGisJkh);
        answerProcessing.sendMessageToClient("Всего обработано записей: " + countAll + "\nИз них:");
        answerProcessing.sendMessageToClient("Лицевых счетов помечено на удаление: " + countRemove);
        answerProcessing.sendMessageToClient("Лицевых счетов добавлено в ГИС ЖКХ: " + countAdded);
        LOGGER.debug("Обработано записей - " + countAll);
        LOGGER.debug("Лицевых счетов помечено на удаление: " + countRemove + ", Лицевых счетов добавлено в ГИС ЖКХ: " + countAdded);

        if (accountsForCloseList.size() > 0) {
            answerProcessing.showQuestionToClient("В ГИС ЖКХ найдены лицевые счета клиентов (" + accountsForCloseList.size() +
                    "), которые дублируются. Желаете их закрыть?\n", this);
        }

        return errorState;
    }

    /**
     * Метод, передаёт на проверку список абонентов из БД ГРАДа и список абонентов полученных из ГИС ЖКХ.
     * Заносит идентификаторы в БД и передаёт в ГИС ЖКХ новые данные.
     * @param exportAccountResult полученный список абонентов из ГИС ЖКХ.
     * @param accountListFromGrad список абонентов из БД ГРАД.
     * @param houseId идентификатор дома в БД ГРАД
     * @param connection подключение к БД ГРАД.
     */
    private void checkAndSendAccountData(ExportAccountResult exportAccountResult, LinkedHashMap<String,
            ImportAccountRequest.Account> accountListFromGrad, Integer houseId, Connection connection) throws SQLException, PreGISException {

//        Ключ - TransportGUID, значение - Account
        LinkedHashMap<String, ImportAccountRequest.Account> AccountDataMap = new LinkedHashMap<>();

        if (exportAccountResult != null) {
            checkDuplicateAccountDataGisJkh(exportAccountResult.getAccounts());

            for (Map.Entry<String, ImportAccountRequest.Account> entry : accountListFromGrad.entrySet()) {

            }
        }




    }

    /**
     * Метод, находит дубликаты ЛС в ГИС ЖКХ, добавляет их в список, для дальнейшего закрытия.
     * @param exportAccountResult данные получнные изи ГИС ЖКХ.
     * @throws PreGISException
     * @throws SQLException
     */
    private void checkDuplicateAccountDataGisJkh(List<ExportAccountResultType> exportAccountResult) throws PreGISException, SQLException {

        for (int i = 0; i < exportAccountResult.size(); i++) {
            for (int j = i+1; j < exportAccountResult.size(); j++) {
                if (exportAccountResult.get(i).getAccountNumber().equalsIgnoreCase(exportAccountResult.get(j).getAccountNumber())) {
                    addAccountDataForClose(exportAccountResult.get(i));
                    addAccountDataForClose(exportAccountResult.get(j));
                }
            }
        }
    }

    /**
     * Метод, ищет ЛС в ранее созданом списке дубликатов ЛС, если ЛС в нем не найден, то будет выгружен в ГИС ЖКХ
     * иначе не будет выгружен в ГИС ЖКХ.
     * @param accountNumber номер лицевого счета абонента.
     * @return true - если абонент найден в списке и выгружать его не стоит в ГИС ЖКХ,
     * false - абонент не найден в списке дубликатов ГИС ЖКХ.
     */
    private boolean isDuplicateAccountData(String accountNumber) {

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
     */
    private void addAccountDataForClose(ExportAccountResultType account) throws PreGISException, SQLException {

        for (ImportAccountRequest.Account accountItem : accountsForCloseList) {
            if (account.getAccountGUID().equalsIgnoreCase(accountItem.getAccountGUID())) {
                return;
            }
        }

        ReferenceNSI referenceNSI = new ReferenceNSI(answerProcessing);

        ImportAccountRequest.Account accountClose = new ImportAccountRequest.Account();

        accountClose.setAccountGUID(account.getAccountGUID());
        accountClose.setClosed(new ClosedAccountAttributesType());
        accountClose.getClosed().setCloseDate(OtherFormat.getDateNow());
        accountClose.getClosed().setCloseReason(referenceNSI.getNsiRef("22", "Изменение реквизитов лицевого счета"));
        accountClose.getClosed().setDescription("Данные были признаны неверными.");

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient(String.format("Абонент c ЛС: %s, в ГИС ЖКХ найден более одного раза.",
                account.getAccountNumber()));

        accountsForCloseList.add(accountClose);
    }

    /**
     * Метод, проверяет соответствие ЛС из БД и ГИС ЖКХ.
     * Если, по указанному ЛС найдены идентификаторы ГИС ЖКХ, то заносим их в БД ГРАДа.
     * Если, ЛС не найден в БД ГРАД, значит он не существует, такой счет будет помещен в таблицу "ACCOUNT_FOR_REMOVE".
     *
     * @param exportAccountResult данные полученные из ГИС ЖКХ.
     * @param account данные абонента полученные из БД ГРАДа.
     * @param houseId             ид дома в БД.
     */
    private boolean checkAccountDataIsAddedGrad(ExportAccountResult exportAccountResult,
            ImportAccountRequest.Account account, Integer houseId, Connection connection) throws ParseException, SQLException, PreGISException {

        if (!isDuplicateAccountData(account.getAccountNumber())) { // Только если абонент не найден в дубликатах
//           иначе не понятно, какой из всех идентификаторов загружать?

            for (ExportAccountResultType resultTypeAccount : exportAccountResult.getAccounts()) { // полученные от ГИС ЖКХ записи перебераем

                if (account.getAccountNumber().equalsIgnoreCase(resultTypeAccount.getAccountNumber())) {

                }
                if (accountListFromGrad.containsKey(resultTypeAccount.getAccountNumber())) { // если в БД есть элемент, добавляем идентификаторы в БД
                    if (accountListFromGrad.get(resultTypeAccount.getAccountNumber()).getAccountGUID() == null ||
                            !accountListFromGrad.get(resultTypeAccount.getAccountNumber()).getAccountGUID().equals(resultTypeAccount.getAccountGUID())) {
//                        System.err.println("GRAD: " + accountListFromGrad.get(resultTypeAccount.getAccountNumber()).getAccountGUID());
//                        System.err.println("GIS" + resultTypeAccount.getAccountGUID());
//                        setAccountToBase(houseId, resultTypeAccount.getAccountNumber(), resultTypeAccount.getAccountGUID(), resultTypeAccount.getAccountUniqueNumber(), connection);
//                        после 9.0.1.4 переименовали
                        setAccountToBase(houseId, resultTypeAccount.getAccountNumber(), resultTypeAccount.getAccountGUID(), resultTypeAccount.getUnifiedAccountNumber(), connection);
                    }
//                    accountListFromGrad.remove(resultTypeAccount.getAccountNumber()); // удалим найденные счета из списка
                } else { // если нет записи в БД, значит счет закрыт надо закрыть в ГИС ЖКХ, для этого добавим в таблицу для удаления потом закроем все не найденные счита в БД.
                    removeAccount(houseId, resultTypeAccount.getAccountNumber(), connection);
                }
            }
        }
        return false;
    }

    /**
     * Метод, отправляет данные по ЛС в ГИС ЖКХ.
     * @param accountListFromGrad
     * @param houseId
     * @param connection
     * @throws PreGISException
     * @throws SQLException
     * @throws ParseException
     */
    private void sendAccountDataToGISJKH(LinkedHashMap<String, ImportAccountRequest.Account> accountListFromGrad,
                                         Integer houseId, Connection connection) throws PreGISException, SQLException, ParseException {

        ImportAccountData sendAccountToGis = new ImportAccountData(answerProcessing);

        for (Map.Entry<String, ImportAccountRequest.Account> entry : accountListFromGrad.entrySet()) { // бежим по оставшемся счетам которые не найдены в ГИС ЖКХ и добавляем их в ГИС ЖКХ
            if (entry.getValue().getAccountGUID() == null || entry.getValue().getAccountGUID().trim().isEmpty()) { // только если нет AccountGUID, тогда отправляем в ГИС.
//                System.err.println("Отправляю в ГИС: " + entry.getKey() + " : " + entry.getValue().getAccountGUID());
                ru.gosuslugi.dom.schema.integration.house_management.ImportResult result = sendAccountToGis.callImportAccountData(sendAccountToGis.getNewImportAccountRequest(entry.getValue())); // отправляем в ГИС ЖКХ
                if (result == null || result.getErrorMessage() != null) {
                    errorState = 0;
                } else {
                    try {
                        // ГИС ЖКХ возвращает не верный ответ, вместо UniqueNumber отдаёт UnifiedAccountNumber, который не удаётся обработать
                        setAccountToBase(houseId, entry.getValue().getAccountNumber(), result.getCommonResult().get(0).getGUID(), result.getCommonResult().get(0).getImportAccount().getUnifiedAccountNumber(), connection); // добавляем идентификаторы в БД.
                    } catch (NullPointerException | IndexOutOfBoundsException e) {
                        LOGGER.error("Ожидался уникальный идентификатор из ГИС ЖХК", e);
                    }
                }
            }
//            countAll++;
        }
    }

    /**
     * Метод, заносит в идентификаторы в БД ГРАДа.
     */
    private void saveResult() {


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
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendMessageToClient("Дабавлен лицевой счет №" + accountNumber + " идентификатор:" + accountUniqueNumber);
            answerProcessing.sendMessageToClient("");
            countAdded++;
        } else {
            errorState = 0;
        }
    }

    /**
     * Метод, формирует и отправляет запрос для закрытия ЛС, которые находятся в списке "accountsForCloseList".
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
                    if (count + 20 > listForClose.size()) {
                        result = sendAccountToGis.callImportAccountData(listForClose.subList(count, listForClose.size()));
                        count += 20;
                    } else {
                        result = sendAccountToGis.callImportAccountData(listForClose.subList(count, count += 20));
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
                                errorState = 0;
                            }
                        }
                    }

                } catch (SQLException e) {
                    answerProcessing.sendErrorToClient("Закрытие ЛС", "\"Закрытие ЛС\"", LOGGER, e);
                }
            }
            accountsForCloseList.clear();
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
