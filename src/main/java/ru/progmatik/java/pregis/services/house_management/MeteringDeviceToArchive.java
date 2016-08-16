package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.CommonResultType;
import ru.gosuslugi.dom.schema.integration.services.house_management.ImportMeteringDeviceDataRequest;
import ru.gosuslugi.dom.schema.integration.services.house_management.ImportResult;
import ru.progmatik.java.pregis.connectiondb.grad.devices.MeteringDeviceGRADDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.meteringdevice.MeteringDevicesDataLocalDBDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSI;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Класс, обрабатывает приборы учёта, которые необходимо архивировать, а затем добавить.
 */
public class MeteringDeviceToArchive implements IMeteringDevices {

    private static final Logger LOGGER = Logger.getLogger(MeteringDeviceToArchive.class);

    private final AnswerProcessing answerProcessing;
    private final ReferenceNSI nsi;
    private final MeteringDeviceGRADDAO graddao;
    private final MeteringDevicesDataLocalDBDAO devicesDataLocalDBDAO;
    // Ключ - TransportGUID, значение - ПУ для архиввации
    private final LinkedHashMap<String, ImportMeteringDeviceDataRequest.MeteringDevice> meteringDevicesToArchiveLinkedHashMap = new LinkedHashMap<>();
    // Ключ - VersionGUID, значение - ПУ для создания
    private final LinkedHashMap<String, ImportMeteringDeviceDataRequest.MeteringDevice> deviceForArchiveAndCreateMap;

    private int errorState;

    /**
     * Конструктор, создаёт класс для архивирования устройства и создания списка новых устройств для добавления.
     * @param answerProcessing - обработчик сообщений.
     * @param deviceForArchiveAndCreateMap - должна содержать устройства для архивирования и создания.
     * @throws SQLException
     * @throws ParseException
     * @throws PreGISException
     */
    MeteringDeviceToArchive(AnswerProcessing answerProcessing, LinkedHashMap<String,
            ImportMeteringDeviceDataRequest.MeteringDevice> deviceForArchiveAndCreateMap) throws SQLException, ParseException, PreGISException {
        this.answerProcessing = answerProcessing;
        this.deviceForArchiveAndCreateMap = deviceForArchiveAndCreateMap;
        nsi = new ReferenceNSI(answerProcessing);
        graddao = new MeteringDeviceGRADDAO(answerProcessing, null);
        devicesDataLocalDBDAO = new MeteringDevicesDataLocalDBDAO(answerProcessing);
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
     * @return список для ГИС ЖКХ.
     * @throws SQLException
     * @throws PreGISException
     */
    public java.util.List<ImportMeteringDeviceDataRequest.MeteringDevice> addMeteringDeviceToArchive () throws SQLException, PreGISException {

        java.util.List<ImportMeteringDeviceDataRequest.MeteringDevice> deviceList = new ArrayList<>();

        for (Map.Entry<String, ImportMeteringDeviceDataRequest.MeteringDevice> entry : deviceForArchiveAndCreateMap.entrySet()) {
            deviceList.add(getArchiveMeterDevice(entry.getKey(), "21", "Ошибка"));
        }
        return deviceList;
    }

    /**
     * Метод, заносит в разные БД идентификаторы ПУ.
     *
     * @param importResult   данные получены из ГИС ЖКХ.
     * @param connectionGrad подключение к БД ГРАД.
     * @throws SQLException
     */
    public void setMeteringDevices(ImportResult importResult, Connection connectionGrad) throws SQLException, FileNotFoundException, SOAPException, JAXBException {

        if (importResult.getCommonResult() != null && importResult.getCommonResult().size() > 0) {
            for (ImportResult.CommonResult result : importResult.getCommonResult()) {

                if (result.getError() == null || result.getError().size() == 0) {
                    setMeteringDevices(result.getImportMeteringDevice().getMeteringDeviceVersionGUID(),
                            result.getTransportGUID(), connectionGrad);
                } else {
                    showErrorMeteringDevices(result.getTransportGUID(), result.getError().get(0).getErrorCode(),
                            result.getError().get(0).getDescription());
                }
            }
        } else {  // Возвращает не тот объект ответа.

            ru.gosuslugi.dom.schema.integration.base.ImportResult castResult = graddao.getImportResultLastFromDataBase();
            for (CommonResultType resultType : castResult.getCommonResult()) {

                if (resultType.getError() == null || resultType.getError().size() == 0) {
                    LOGGER.debug("Active: base.ImportResult.");
//                Этот объект вместо getGUID содержит meteringVersionGUID.
                    setMeteringDevices(resultType.getGUID(), resultType.getTransportGUID(), connectionGrad);
                } else {
                    showErrorMeteringDevices(resultType.getTransportGUID(), resultType.getError().get(0).getErrorCode(),
                            resultType.getError().get(0).getDescription());
                }
            }
        }
    }

    /**
     * Метод, формирует пригодный для ГИС ЖКХ список устройств.
     * @return список устройств для создания в ГИС ЖКХ.
     */
    public ArrayList<ImportMeteringDeviceDataRequest.MeteringDevice> getListForCreateDevices() {

        java.util.ArrayList<ImportMeteringDeviceDataRequest.MeteringDevice> meteringDeviceList = new ArrayList<>();
        for (Map.Entry<String, ImportMeteringDeviceDataRequest.MeteringDevice> entry : deviceForArchiveAndCreateMap.entrySet()) {
            meteringDeviceList.add(entry.getValue());
        }
        return meteringDeviceList;
    }

    /**
     * Метод, добавляет новые идентификаторы в БД и делает отметку, что устройство архивировано.
     * @param meteringDeviceVersionGUID идентификатор версии ПУ.
     * @param transportGUID транспортный идентификатор
     * @param connectionGrad подключение к БД ГРАД.
     * @throws SQLException
     */
    private void setMeteringDevices(String meteringDeviceVersionGUID, String transportGUID, Connection connectionGrad) throws SQLException {

        if (meteringDevicesToArchiveLinkedHashMap.containsKey(transportGUID)) {
            ImportMeteringDeviceDataRequest.MeteringDevice meteringDevice = meteringDevicesToArchiveLinkedHashMap.get(transportGUID);
            Integer meterId = devicesDataLocalDBDAO.getMeterIdFromLocalBaseUseMeteringVersionGUID(meteringDevice.getDeviceDataToUpdate().getMeteringDeviceVersionGUID());
            graddao.updateMeteringVersionGUID(meterId, null, meteringDeviceVersionGUID, connectionGrad);
            devicesDataLocalDBDAO.setArchivingReasonToLocalBase(Integer.valueOf(meteringDevice.getDeviceDataToUpdate().getArchiveDevice().getArchivingReason().getCode()), meteringDeviceVersionGUID);  // в БД отметка
        }
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

        device.setMeteringDeviceVersionGUID(meterVersionGUID);
        device.setArchiveDevice(new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ArchiveDevice());
        device.getArchiveDevice().setArchivingReason(nsi.getNsiRef(codeNsi, nsiName));
//        graddao.setArchivingReasonToLocalBase(Integer.valueOf(nsi.getNsiRef(codeNsi, nsiName).getCode()), meterVersionGUID);  // в БД отметка

        ImportMeteringDeviceDataRequest.MeteringDevice meteringDevices = new ImportMeteringDeviceDataRequest.MeteringDevice();
        meteringDevices.setTransportGUID(OtherFormat.getRandomGUID());
        meteringDevices.setDeviceDataToUpdate(device);
        meteringDevicesToArchiveLinkedHashMap.put(meteringDevices.getTransportGUID(), meteringDevices);
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("ПУ добавлен для архивирования: " +
                meteringDevices.getDeviceDataToUpdate().getMeteringDeviceVersionGUID());

        return meteringDevices;
    }

    /**
     * Метод, формирует и выводит пользователю информацию об ошибках, которые возвращает ГИС ЖКХ.
     *
     * @param transportGUID транспортный идентификатор.
     * @param errorCode     код ошибки.
     * @param description   описание ошибки.
     */
    private void showErrorMeteringDevices(String transportGUID, String errorCode, String description) {
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("TransportGUID: " + transportGUID);
        answerProcessing.sendMessageToClient("Код ошибки: " + errorCode);
        answerProcessing.sendMessageToClient("Описание ошибки: " + description);
        if (meteringDevicesToArchiveLinkedHashMap.containsKey(transportGUID)) {
            ImportMeteringDeviceDataRequest.MeteringDevice meteringDevice = meteringDevicesToArchiveLinkedHashMap.get(transportGUID);
            deviceForArchiveAndCreateMap.remove(meteringDevice.getDeviceDataToUpdate().getMeteringDeviceVersionGUID());
        }
        setErrorState(0);
    }

    public int getErrorState() {
        return errorState;
    }

    private void setErrorState(int errorState) {
        if (this.errorState > errorState) this.errorState = errorState;
    }
}
