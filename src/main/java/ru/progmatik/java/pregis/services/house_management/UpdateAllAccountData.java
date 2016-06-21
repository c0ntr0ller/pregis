package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.services.house_management.ExportAccountResult;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Класс, синхронизирует лицевые счета ГРАДа и ГИС ЖКХ.
 */
public class UpdateAllAccountData {

    private static final Logger LOGGER = Logger.getLogger(UpdateAllAccountData.class);
    private final AnswerProcessing answerProcessing;

    public UpdateAllAccountData(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    /**
     * Метод, получает из БД список всех выгруженных домов в ГИС ЖКХ, получает по ФИАС данные о лицевых счетах дома из ГИС ЖКХ.
     * Получает из БД по ИД этого же дома информацию о счетах, производит проверку, при необходимости закрывает счет в ГИС ЖКХ или выгружает новый.
     */
    public boolean updateAllAccountData() throws SQLException, PreGISException {

        HouseGRADDAO graddao = new HouseGRADDAO();
        LinkedHashMap<String, Integer> houseAddedGisJkh = graddao.getHouseAddedGisJkh();

        ExportAccountData accountData = new ExportAccountData(answerProcessing);

        for (Map.Entry<String, Integer> itemHouse : houseAddedGisJkh.entrySet()) {
            ExportAccountResult exportAccountResult = accountData.callExportAccountData(itemHouse.getKey());
        }

        return true;
    }

    private void checkAllAccountData() {

    }
}
