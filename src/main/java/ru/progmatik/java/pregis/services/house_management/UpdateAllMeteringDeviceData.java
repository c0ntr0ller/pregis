package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.services.house_management.ImportMeteringDeviceDataRequest;
import ru.gosuslugi.dom.schema.integration.services.house_management.ImportResult;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.devices.MeteringDeviceGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Класс, синхронизирует данные о ПУ.
 */
public class UpdateAllMeteringDeviceData {

    private static final Logger LOGGER = Logger.getLogger(UpdateAllMeteringDeviceData.class);
    private final AnswerProcessing answerProcessing;

    public UpdateAllMeteringDeviceData(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    public void updateMeteringDeviceData() {



    }

    /**
     * Метод, получает список домов готовых для выгрузки в ГИС ЖКХ, формирует по ним новые ПУ и отправляет в ГИС ЖКХ.
     * @throws SQLException
     * @throws PreGISException
     */
    public void createMeteringDevice() throws SQLException, PreGISException, ParseException {


        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {
            HouseGRADDAO houseGRADDAO = new HouseGRADDAO();
            LinkedHashMap<String, Integer> houseAddedGisJkh = houseGRADDAO.getHouseAddedGisJkh();
            ImportMeteringDeviceData importMeteringDeviceData = new ImportMeteringDeviceData(answerProcessing);

            for (Map.Entry<String, Integer> entryHouse : houseAddedGisJkh.entrySet()) {
                answerProcessing.sendMessageToClient("Формирую ПУ для дома: " + entryHouse.getKey());
                MeteringDeviceGRADDAO meteringDeviceGRADDAO = new MeteringDeviceGRADDAO(answerProcessing); // создаввать каждый раз новый, беру из БД по одному дому данные и использую каждый раз
                java.util.List<ImportMeteringDeviceDataRequest.MeteringDevice> devices = meteringDeviceGRADDAO.getMeteringDevicesForCreate(entryHouse.getValue(), connectionGRAD);

                ImportResult importResult = importMeteringDeviceData.callImportMeteringDeviceData(entryHouse.getKey(), devices);
                for (ImportResult.CommonResult result : importResult.getCommonResult()) {
                    answerProcessing.sendMessageToClient("GUID: " + result.getGUID());
                    answerProcessing.sendMessageToClient("UniqueNumber: " + result.getUniqueNumber());
                    answerProcessing.sendMessageToClient("MeteringDeviceVersionGUID: " + result.getImportMeteringDevice().getMeteringDeviceVersionGUID());
                    answerProcessing.sendMessageToClient("TransportGUID: " + result.getTransportGUID());
                    answerProcessing.sendMessageToClient("");
                }
            }
        }
    }
}
