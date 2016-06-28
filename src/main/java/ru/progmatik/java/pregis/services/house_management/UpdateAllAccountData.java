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
    private int errorState = 1;

    public UpdateAllAccountData(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    /**
     * Метод, получает из БД список всех выгруженных домов в ГИС ЖКХ, получает по ФИАС данные о лицевых счетах дома из ГИС ЖКХ.
     * Получает из БД по ИД этого же дома информацию о счетах, производит проверку, при необходимости закрывает счет в ГИС ЖКХ или выгружает новый.
     */
    public boolean updateAllAccountData() throws SQLException, PreGISException, ParseException {

        HouseGRADDAO graddao = new HouseGRADDAO();
        AccountGRADDAO accountGRADDAO = new AccountGRADDAO(answerProcessing);
        LinkedHashMap<String, Integer> houseAddedGisJkh = graddao.getHouseAddedGisJkh();
        if (houseAddedGisJkh == null) {
            throw new PreGISException("Не найден ни один дом готовый для выгрузки ГИС ЖКХ.");
        }

        ExportAccountData accountData = new ExportAccountData(answerProcessing);
//        ExportHouseData exportHouseData = new ExportHouseData(answerProcessing);

        for (Map.Entry<String, Integer> itemHouse : houseAddedGisJkh.entrySet()) {

//            ExportHouseResult exportHouseResult = exportHouseData.callExportHouseData(itemHouse.getKey());
            ExportAccountResult exportAccountResult = accountData.callExportAccountData(itemHouse.getKey());
            LinkedHashMap<String, ImportAccountRequest.Account> accountListFromGrad = accountGRADDAO.getAccountListFromGrad(itemHouse.getValue());

            if (exportAccountResult == null) {
                errorState = -1;
                return false;
            }
            checkAllAccountData(exportAccountResult, accountListFromGrad, itemHouse);
        }

        return true;
    }

    /**
     * Метод, проверяет соответствие ЛС из БД и ГИС ЖКХ.
     *  @param exportAccountResult данные полученные из ГИС ЖКХ.
     * @param accountListFromGrad данные полученные из БД ГРАДа.
     * @param itemHouse данные о доме, код дома по ФИАС и ид дома в БД ГРАД.
     */
    private void checkAllAccountData(ExportAccountResult exportAccountResult, LinkedHashMap<String, ImportAccountRequest.Account> accountListFromGrad, Map.Entry<String, Integer> itemHouse) throws PreGISException {

        ImportAccountData sendAccountToGis = new ImportAccountData(answerProcessing);

        for (ExportAccountResultType resultTypeAccount : exportAccountResult.getAccounts()) {

            if (accountListFromGrad.containsKey(resultTypeAccount.getAccountNumber())) {
                if (!accountListFromGrad.get(resultTypeAccount.getAccountNumber()).getAccountGUID().equals(resultTypeAccount.getAccountGUID())) {
                    addAccountToBase(resultTypeAccount);
                    accountListFromGrad.remove(resultTypeAccount.getAccountNumber());
                }
            } else {
                removeAccount(resultTypeAccount);
            }

            for (Map.Entry<String, ImportAccountRequest.Account> entry : accountListFromGrad.entrySet()) {
                ImportResult result = sendAccountToGis.callImportAccountData(sendAccountToGis.getNewImportAccountRequest(entry.getValue()));
                entry.getValue().get
                result.getCommonResult().get(0).
            }
        }


    }

    /**
     * Метод, добавляет в таблицу данные абонента, которые необходимо удалить из ГИС ЖКХ.
     * @param resultTypeAccount  полученные данные из ГИС ЖКХ.
     */
    private void removeAccount(ExportAccountResultType resultTypeAccount) {

    }

    /**
     * Метод, добавляет в БД данные абонента.
     * @param resultTypeAccount полученные данные из ГИС ЖКХ.
     */
    private void addAccountToBase(ExportAccountResultType resultTypeAccount) {

    }
}
