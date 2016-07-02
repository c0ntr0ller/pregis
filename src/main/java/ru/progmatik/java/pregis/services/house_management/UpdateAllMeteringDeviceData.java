package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.services.house_management.ImportMeteringDeviceDataRequest;
import ru.gosuslugi.dom.schema.integration.services.house_management.ImportResult;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.devices.MeteringDeviceGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import java.io.FileNotFoundException;
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
    private int countAll = 0;
    private int countAdded = 0;
    private int errorState;

    public UpdateAllMeteringDeviceData(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    public int updateMeteringDeviceData() throws ParseException, SQLException, PreGISException, FileNotFoundException, SOAPException, JAXBException {

        return createMeteringDevice();

    }

    /**
     * Метод, получает список домов готовых для выгрузки в ГИС ЖКХ, формирует по ним новые ПУ и отправляет в ГИС ЖКХ.
     * @throws SQLException
     * @throws PreGISException
     */
    private int createMeteringDevice() throws SQLException, PreGISException, ParseException, FileNotFoundException, SOAPException, JAXBException {

        errorState = 1;
//        Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection();
        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {
            HouseGRADDAO houseGRADDAO = new HouseGRADDAO();
            LinkedHashMap<String, Integer> houseAddedGisJkh = houseGRADDAO.getHouseAddedGisJkh(connectionGRAD);
            ImportMeteringDeviceData importMeteringDeviceData = new ImportMeteringDeviceData(answerProcessing);

            for (Map.Entry<String, Integer> entryHouse : houseAddedGisJkh.entrySet()) {
                answerProcessing.sendMessageToClient("Формирую ПУ для дома: " + entryHouse.getKey());
                MeteringDeviceGRADDAO meteringDeviceGRADDAO = new MeteringDeviceGRADDAO(answerProcessing, entryHouse.getValue()); // создаввать каждый раз новый, беру из БД по одному дому данные и использую каждый раз
                java.util.List<ImportMeteringDeviceDataRequest.MeteringDevice> devices = meteringDeviceGRADDAO.getMeteringDevicesForCreate(connectionGRAD);
                countAll += meteringDeviceGRADDAO.getCountAll();
//                java.util.List<ImportMeteringDeviceDataRequest.MeteringDevice> devices = meteringDeviceGRADDAO.getMeteringDevicesForCreate(entryHouse.getValue(), connectionGRAD);

                ImportResult importResult = importMeteringDeviceData.callImportMeteringDeviceData(entryHouse.getKey(), devices.subList(28, 29));
                if (importResult != null && importResult.getCommonResult() != null) {
                    meteringDeviceGRADDAO.setMeteringDevices(importResult, connectionGRAD);
                    countAdded += meteringDeviceGRADDAO.getCountAdded();
                    for (ImportResult.CommonResult result : importResult.getCommonResult()) {
                        answerProcessing.sendMessageToClient("GUID: " + result.getGUID());
                        answerProcessing.sendMessageToClient("UniqueNumber: " + result.getUniqueNumber());
                        answerProcessing.sendMessageToClient("MeteringDeviceVersionGUID: " + result.getImportMeteringDevice().getMeteringDeviceVersionGUID());
                        answerProcessing.sendMessageToClient("TransportGUID: " + result.getTransportGUID());
                        answerProcessing.sendMessageToClient("");
                    }
                } else {
                    errorState = -1;
                }
            }
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Всего обработано записей: " + countAll + "\nИз них:");
        answerProcessing.sendMessageToClient("Добавлено в ГИС ЖКХ: " + countAdded);
//        connectionGRAD.close();
        }
        return errorState;
    }
}
