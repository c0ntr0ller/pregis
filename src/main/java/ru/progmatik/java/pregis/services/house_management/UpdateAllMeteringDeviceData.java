package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.services.house_management.ExportMeteringDeviceDataResult;
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
    private int countUpdate = 0;
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

        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {
            HouseGRADDAO houseGRADDAO = new HouseGRADDAO();
            LinkedHashMap<String, Integer> houseAddedGisJkh = houseGRADDAO.getHouseAddedGisJkh(connectionGRAD);
            ImportMeteringDeviceData importMeteringDeviceData = new ImportMeteringDeviceData(answerProcessing);

            for (Map.Entry<String, Integer> entryHouse : houseAddedGisJkh.entrySet()) {
                answerProcessing.sendMessageToClient("Формирую ПУ для дома: " + entryHouse.getKey());
                MeteringDeviceGRADDAO meteringDeviceGRADDAO = new MeteringDeviceGRADDAO(answerProcessing, entryHouse.getValue()); // создаввать каждый раз новый, беру из БД по одному дому данные и использую каждый раз
                java.util.List<ImportMeteringDeviceDataRequest.MeteringDevice> devices = meteringDeviceGRADDAO.getMeteringDevicesForCreate(connectionGRAD);
                countAll += meteringDeviceGRADDAO.getCountAll();

                if (devices.size() > 0) {
                    int count = 0;
                    while (count < devices.size()) {
                        ImportResult importResult;
                        if (count + 20 > devices.size()) {
                            importResult = importMeteringDeviceData.callImportMeteringDeviceData(entryHouse.getKey(), devices.subList(count, devices.size()));
                            count += 20;
                        } else {
                            importResult = importMeteringDeviceData.callImportMeteringDeviceData(entryHouse.getKey(), devices.subList(count, count += 20));
                        }
                        if (importResult != null && importResult.getCommonResult() != null) {
                            meteringDeviceGRADDAO.setMeteringDevices(importResult, connectionGRAD);

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
                }
                ExportMeteringDeviceData exportMeteringDeviceData = new ExportMeteringDeviceData(answerProcessing);
                ExportMeteringDeviceDataResult exportMeteringDeviceDataResult = exportMeteringDeviceData.callExportMeteringDeviceData(entryHouse.getKey());
                meteringDeviceGRADDAO.checkExportMeteringDevices(exportMeteringDeviceDataResult, connectionGRAD);
                countUpdate += meteringDeviceGRADDAO.getCountUpdate();
                countAdded += meteringDeviceGRADDAO.getCountAdded();
            }

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Всего обработано записей: " + countAll + "\nИз них:");
        answerProcessing.sendMessageToClient("Обновлено в ГРАД: " + countUpdate);
        answerProcessing.sendMessageToClient("Добавлено в ГИС ЖКХ: " + countAdded);
        }
        return errorState;
    }
}
