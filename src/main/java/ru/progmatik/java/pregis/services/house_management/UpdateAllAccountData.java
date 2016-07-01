package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.ImportResult;
import ru.gosuslugi.dom.schema.integration.services.house_management.ExportAccountResult;
import ru.gosuslugi.dom.schema.integration.services.house_management.ExportAccountResultType;
import ru.gosuslugi.dom.schema.integration.services.house_management.ImportAccountRequest;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.account.AccountGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Класс, синхронизирует лицевые счета ГРАДа и ГИС ЖКХ.
 */
public class UpdateAllAccountData {

    private static final Logger LOGGER = Logger.getLogger(UpdateAllAccountData.class);
    private final AnswerProcessing answerProcessing;
    private final AccountGRADDAO accountGRADDAO;
    private int countAll;
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
        countRemove = 0;
        countAdded = 0;
        errorState = 1;

        HouseGRADDAO graddao = new HouseGRADDAO();

        LinkedHashMap<String, Integer> houseAddedGisJkh = graddao.getHouseAddedGisJkh();
        if (houseAddedGisJkh == null) {
            throw new PreGISException("Не найден ни один дом готовый для выгрузки ГИС ЖКХ.");
        }

        try (Connection connection = ConnectionBaseGRAD.instance().getConnection()) {
            ExportAccountData accountData = new ExportAccountData(answerProcessing);

            for (Map.Entry<String, Integer> itemHouse : houseAddedGisJkh.entrySet()) {

                ExportAccountResult exportAccountResult = accountData.callExportAccountData(itemHouse.getKey());
                LinkedHashMap<String, ImportAccountRequest.Account> accountListFromGrad = accountGRADDAO.getAccountListFromGrad(itemHouse.getValue(), connection);

                if (exportAccountResult == null) {
                    errorState = 0;
                }
                sendAccountDataToGis(exportAccountResult, accountListFromGrad, itemHouse.getValue(), connection);
            }
        }
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Всего обработано записей: " + countAll + "\nИз них:");
        answerProcessing.sendMessageToClient("Лицевых счетов помечено на удаление: " + countRemove);
        answerProcessing.sendMessageToClient("Лицевых счетов добавлено в ГИС ЖКХ: " + countAdded);
        LOGGER.debug("Обработано записей - " + countAll);
        LOGGER.debug("Лицевых счетов помечено на удаление: " + countRemove + ", Лицевых счетов добавлено в ГИС ЖКХ: " + countAdded);
        return errorState;
    }

    /**
     * Метод, проверяет соответствие ЛС из БД и ГИС ЖКХ.
     *
     * @param exportAccountResult данные полученные из ГИС ЖКХ.
     * @param accountListFromGrad данные полученные из БД ГРАДа.
     * @param houseId             ид дома в БД.
     */
    private void sendAccountDataToGis(ExportAccountResult exportAccountResult, LinkedHashMap<String, ImportAccountRequest.Account> accountListFromGrad, Integer houseId, Connection connection) throws PreGISException, ParseException, SQLException {

        ImportAccountData sendAccountToGis = new ImportAccountData(answerProcessing);

        if (exportAccountResult != null) {
            for (ExportAccountResultType resultTypeAccount : exportAccountResult.getAccounts()) { // полученные от ГИС ЖКХ записи перебераем
                countAll++;
                if (accountListFromGrad.containsKey(resultTypeAccount.getAccountNumber())) { // если в БД есть элемент, добавляем идентификаторы в БД
                    if (!accountListFromGrad.get(resultTypeAccount.getAccountNumber()).getAccountGUID().equals(resultTypeAccount.getAccountGUID())) {
//                        System.err.println("GRAD: " + accountListFromGrad.get(resultTypeAccount.getAccountNumber()).getAccountGUID());
//                        System.err.println("GIS" + resultTypeAccount.getAccountGUID());
                        setAccountToBase(houseId, resultTypeAccount.getAccountNumber(), resultTypeAccount.getAccountGUID(), resultTypeAccount.getAccountUniqueNumber(), connection);
                    }
                    accountListFromGrad.remove(resultTypeAccount.getAccountNumber()); // удалим найденные счета из списка
                } else { // если нет записи в БД, значит счет закрыт надо закрыть в ГИС ЖКХ, для этого добавим в таблицу для удаления потом закроем все не найденные счита в БД.
                    removeAccount(houseId, resultTypeAccount.getAccountNumber(), connection);
                }
            }
        }
        for (Map.Entry<String, ImportAccountRequest.Account> entry : accountListFromGrad.entrySet()) { // бежим по оставшемся счетам которые не найдены в ГИС ЖКХ и добавляем их в ГИС ЖКХ
            if (exportAccountResult != null || entry.getValue().getAccountGUID() == null || entry.getValue().getAccountGUID().trim().isEmpty()) { // только если нет AccountGUID, тогда отправляем в ГИС.
//                System.err.println("Отправляю в ГИС: " + entry.getKey() + " : " + entry.getValue().getAccountGUID());
                ImportResult result = sendAccountToGis.callImportAccountData(sendAccountToGis.getNewImportAccountRequest(entry.getValue())); // отправляем в ГИС ЖКХ
                if (result == null || result.getErrorMessage() != null) errorState = 0;
                else
                    setAccountToBase(houseId, entry.getValue().getAccountNumber(), result.getCommonResult().get(0).getGUID(), result.getCommonResult().get(0).getUniqueNumber(), connection); // добавляем идентификаторы в БД.
            }
            countAll++;
        }
    }

    /**
     * Метод, добавляет в таблицу данные абонента, которые необходимо удалить из ГИС ЖКХ.
     *
     * @param houseId       ид дома в БД.
     * @param accountNumber номер ЛС.
     */
    private void removeAccount(Integer houseId, String accountNumber, Connection connection) throws ParseException, SQLException {
        accountGRADDAO.addAccountForRemove(houseId, accountNumber, connection);
        countRemove++;
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
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Дабавлен лицевой счет №" + accountNumber + " идентификатор:" + accountUniqueNumber);
        answerProcessing.sendMessageToClient("");
        countAdded++;
        accountGRADDAO.setAccountGuidAndUniqueNumber(houseId, accountNumber, accountGUID, accountUniqueNumber, connection);
    }
}
