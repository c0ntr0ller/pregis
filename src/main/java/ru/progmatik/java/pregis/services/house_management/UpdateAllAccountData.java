package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.ImportResult;
import ru.gosuslugi.dom.schema.integration.services.house_management.ExportAccountResult;
import ru.gosuslugi.dom.schema.integration.services.house_management.ExportAccountResultType;
import ru.gosuslugi.dom.schema.integration.services.house_management.ImportAccountRequest;
import ru.progmatik.java.pregis.connectiondb.grad.account.AccountGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;

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
    private int errorState = 1;

    public UpdateAllAccountData(AnswerProcessing answerProcessing) throws SQLException {
        this.answerProcessing = answerProcessing;
        accountGRADDAO = new AccountGRADDAO(answerProcessing);
    }

    /**
     * Метод, получает из БД список всех выгруженных домов в ГИС ЖКХ, получает по ФИАС данные о лицевых счетах дома из ГИС ЖКХ.
     * Получает из БД по ИД этого же дома информацию о счетах, производит проверку, при необходимости закрывает счет в ГИС ЖКХ или выгружает новый.
     */
    public int updateAllAccountData() throws SQLException, PreGISException, ParseException {

        HouseGRADDAO graddao = new HouseGRADDAO();

        LinkedHashMap<String, Integer> houseAddedGisJkh = graddao.getHouseAddedGisJkh();
        if (houseAddedGisJkh == null) {
            throw new PreGISException("Не найден ни один дом готовый для выгрузки ГИС ЖКХ.");
        }

        ExportAccountData accountData = new ExportAccountData(answerProcessing);

        for (Map.Entry<String, Integer> itemHouse : houseAddedGisJkh.entrySet()) {

            ExportAccountResult exportAccountResult = accountData.callExportAccountData(itemHouse.getKey());
            LinkedHashMap<String, ImportAccountRequest.Account> accountListFromGrad = accountGRADDAO.getAccountListFromGrad(itemHouse.getValue());

            if (exportAccountResult == null) {
                return -1;
            }
            checkAllAccountData(exportAccountResult, accountListFromGrad, itemHouse.getValue());
        }

        return errorState;
    }

    /**
     * Метод, проверяет соответствие ЛС из БД и ГИС ЖКХ.
     * @param exportAccountResult данные полученные из ГИС ЖКХ.
     * @param accountListFromGrad данные полученные из БД ГРАДа.
     * @param houseId ид дома в БД.
     */
    private void checkAllAccountData(ExportAccountResult exportAccountResult, LinkedHashMap<String, ImportAccountRequest.Account> accountListFromGrad, Integer houseId) throws PreGISException, ParseException, SQLException {

        ImportAccountData sendAccountToGis = new ImportAccountData(answerProcessing);

        for (ExportAccountResultType resultTypeAccount : exportAccountResult.getAccounts()) { // полученные от ГИС ЖКХ записи перебераем

            if (accountListFromGrad.containsKey(resultTypeAccount.getAccountNumber())) { // если в БД есть элемент, добавляем идентификаторы в БД
                if (!accountListFromGrad.get(resultTypeAccount.getAccountNumber()).getAccountGUID().equals(resultTypeAccount.getAccountGUID())) {
                    setAccountToBase(houseId, resultTypeAccount.getAccountNumber(), resultTypeAccount.getAccountGUID(), resultTypeAccount.getAccountUniqueNumber());
                    accountListFromGrad.remove(resultTypeAccount.getAccountNumber()); // удалим найденные счета из списка
                }
            } else { // если нет записи в БД, значит счет закрыт надо закрыть в ГИС ЖКХ, для этого добавим в таблицу для удаления потом закроем все не найденные счита в БД.
                removeAccount(houseId, resultTypeAccount.getAccountNumber());
            }
        }
        for (Map.Entry<String, ImportAccountRequest.Account> entry : accountListFromGrad.entrySet()) { // бежим по оставшемся счетам которые не найдены в ГИС ЖКХ и добавляем их в ГИС ЖКХ
            ImportResult result = sendAccountToGis.callImportAccountData(sendAccountToGis.getNewImportAccountRequest(entry.getValue())); // отправляем в ГИС ЖКХ
            setAccountToBase(houseId, entry.getValue().getAccountNumber(), result.getCommonResult().get(0).getGUID(), result.getCommonResult().get(0).getUniqueNumber()); // добавляем идентификаторы в БД.
        }
    }

    /**
     * Метод, добавляет в таблицу данные абонента, которые необходимо удалить из ГИС ЖКХ.
     * @param houseId ид дома в БД.
     * @param accountNumber номер ЛС.
     */
    private void removeAccount(Integer houseId, String accountNumber) throws ParseException, SQLException {
        accountGRADDAO.addAccountForRemove(houseId, accountNumber);
    }

    /**
     * Метод, добавляет в БД данные абонента.
     * @param houseId ид дома в БД.
     * @param accountNumber номер ЛС.
     * @param accountGUID идентификатор ЛС в ГИС ЖКХ.
     * @param accountUniqueNumber уникальный номер ЛС, присвоенный ГИС ЖКХ.
     */
    private void setAccountToBase(Integer houseId, String accountNumber, String accountGUID, String accountUniqueNumber) throws ParseException, SQLException, PreGISException {
        answerProcessing.sendMessageToClient("Дабавлен лицевой счет №" + accountNumber + " идентификатор:" + accountUniqueNumber);
        accountGRADDAO.setAccountUniqueNumber(houseId, accountNumber, accountGUID, accountUniqueNumber);
    }
}
