package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.services.house_management.ImportMeteringDeviceDataRequest;
import ru.progmatik.java.pregis.connectiondb.grad.devices.MeteringDeviceGRADDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSI;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Класс, обрабатывает приборы учёта, которые необходимо архивировать, а затем добавить.
 */
public class ArchiveMeteringDevice {

    private static final Logger LOGGER = Logger.getLogger(ArchiveMeteringDevice.class);

    private final AnswerProcessing answerProcessing;
    private final ReferenceNSI nsi;
    private final MeteringDeviceGRADDAO graddao;

    public ArchiveMeteringDevice(AnswerProcessing answerProcessing) throws SQLException, ParseException, PreGISException {
        this.answerProcessing = answerProcessing;
        nsi = new ReferenceNSI(answerProcessing);
        graddao = new MeteringDeviceGRADDAO(answerProcessing, null);
    }

    /**
     * Метод, получает список ид ПУ в БД ГРАД, для дальнейшего архивирования ПУ.
     *
     * @param meterIdList    список ид ПУ в БД ГРАД.
     * @param connectionGRAD подключение к БД ГРАД.
     * @return готовый объект для отправки в ГИС ЖКХ.
     * @throws SQLException
     * @throws PreGISException
     */
    public java.util.List<ImportMeteringDeviceDataRequest.MeteringDevice> addMeteringDeviceToArchive(
            ArrayList<Integer> meterIdList, Connection connectionGRAD) throws SQLException, PreGISException {

        java.util.List<ImportMeteringDeviceDataRequest.MeteringDevice> deviceList = new ArrayList<>();

        for (Integer meterId : meterIdList) {

            String meterVersionGUID = graddao.getMeteringDeviceUniqueNumbersFromGrad(meterId, "METERVERSIONGUID", connectionGRAD);

            deviceList.add(getArchiveMeterDevice(meterVersionGUID, "21", "Ошибка"));
        }
        return deviceList;
    }


    /**
     * Метод, формирует список ПУ для архивирования.
     * @param deviceForArchiveAndCreateMap список ПУ для архивации.
     * @return список для ГИС ЖКХ.
     * @throws SQLException
     * @throws PreGISException
     */
    public java.util.List<ImportMeteringDeviceDataRequest.MeteringDevice> addMeteringDeviceToArchive (
            LinkedHashMap<String, ImportMeteringDeviceDataRequest.MeteringDevice> deviceForArchiveAndCreateMap) throws SQLException, PreGISException {

        java.util.List<ImportMeteringDeviceDataRequest.MeteringDevice> deviceList = new ArrayList<>();

        for (Map.Entry<String, ImportMeteringDeviceDataRequest.MeteringDevice> entry : deviceForArchiveAndCreateMap.entrySet()) {
            deviceList.add(getArchiveMeterDevice(entry.getKey(), "21", "Ошибка"));
        }
        return deviceList;
    }

    /**
     * Метод, формирует прибор учета для архивирования в ГИС ЖКХ.
     * @param meterVersionGUID идентификатор версии ПУ.
     * @return архивированное устройство.
     * @throws SQLException
     * @throws PreGISException
     */
    private ImportMeteringDeviceDataRequest.MeteringDevice getArchiveMeterDevice(
            String meterVersionGUID, String codeNsi, String nsiName) throws SQLException, PreGISException {

        ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate device = new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate();
//            device.setUpdateAfterDevicesValues(new MeteringDeviceToUpdateAfterDevicesValuesType());
//            device.getUpdateAfterDevicesValues().;

        device.setMeteringDeviceVersionGUID(meterVersionGUID);
        device.setArchiveDevice(new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ArchiveDevice());
        device.getArchiveDevice().setArchivingReason(nsi.getNsiRef(codeNsi, nsiName));
        graddao.setArchivingReasonToLocalBase(Integer.valueOf(nsi.getNsiRef(codeNsi, nsiName).getCode()), meterVersionGUID);  // в БД отметка

        ImportMeteringDeviceDataRequest.MeteringDevice meteringDevices = new ImportMeteringDeviceDataRequest.MeteringDevice();
        meteringDevices.setTransportGUID(OtherFormat.getRandomGUID());
        meteringDevices.setDeviceDataToUpdate(device);

        return meteringDevices;
    }
}
