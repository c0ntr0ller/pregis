package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.house_management.*;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.devices.MeteringDeviceGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseRecord;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSI;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.model.MeteringDeviceID;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.web.servlets.listener.ClientDialogWindowObservable;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс, синхронизирует данные о ПУ.
 */
public final class UpdateAllMeteringDeviceData implements ClientDialogWindowObservable {

    private static final Logger LOGGER = Logger.getLogger(UpdateAllMeteringDeviceData.class);
    private final AnswerProcessing answerProcessing;
    private final ArrayList<ArchiveData> archiveDataList = new ArrayList<>();
    private int countGrad = 0;
    private int countGis = 0;
    private int countAddedGis = 0;
    private int countAddErrorGis = 0;
    private int countUpdateGrad = 0;
    private int countArchiveGis = 0;
    private int countArchErrorGis = 0;
    private int errorState;

    public UpdateAllMeteringDeviceData(final AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    public int updateMeteringDeviceData(final Integer houseGradId) throws SQLException, PreGISException {

//        return createMeteringDevice(houseGradId);
        return syncMeteringDevices(houseGradId);

    }

    public int syncMeteringDevices(final Integer houseGradId) throws SQLException, PreGISException {
        errorState = 1;
        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {

            final HouseGRADDAO houseGRADDAO = new HouseGRADDAO(answerProcessing);
            final LinkedHashMap<String, HouseRecord> houseAddedGisJkh = houseGRADDAO.getHouseRecords(houseGradId, connectionGRAD);

            for (Map.Entry<String, HouseRecord> entryHouse : houseAddedGisJkh.entrySet()) {
                syncMeteringDevicesHouse(entryHouse.getValue());
                if(errorState < 0) break;
            }
        }
        showInfo();
        return errorState;
    }

    private void syncMeteringDevicesHouse(HouseRecord entryHouse){
        if (answerProcessing != null) {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendMessageToClient("Формирую ПУ для дома: " + entryHouse.getAddresString());
        }
        try {
            final MeteringDeviceGRADDAO meteringDeviceGRADDAO = new MeteringDeviceGRADDAO(answerProcessing, entryHouse.getGrad_id());

            // получаем ПУ из Град
            HashMap<MeteringDeviceID, ImportMeteringDeviceDataRequest.MeteringDevice> devicesGrad = meteringDeviceGRADDAO.getMeteringDevicesFromGrad(entryHouse.getGrad_id());
            countGrad = devicesGrad.size();
            if (answerProcessing != null) {
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendMessageToClient("Из Град получено приборов: " + devicesGrad.size());
            }

            // получаем ПУ из ГИС ЖКХ
            GetStateResult stateResult = HomeManagementAsyncPort.callExportMeteringDeviceData(entryHouse.getFias(), answerProcessing);


            List<ExportMeteringDeviceDataResultType> devicesGIS = null;

            if (stateResult != null) {
                devicesGIS = stateResult.getExportMeteringDeviceDataResult().stream().filter(e->e.getStatusRootDoc().equals("Active")).collect(Collectors.toList());
                countGis = devicesGIS.size();
                if (answerProcessing != null) {
                    answerProcessing.sendMessageToClient("");
                    answerProcessing.sendMessageToClient("Из ГИС получено активных приборов: " + devicesGIS.size());
                }
            }

            // сравниваем списки и формируем новые. В мапу заносим список 1 - на добавление в ГИС "insertGIS", на изменение/архивацию в ГИС "archiveGIS", на изменение в Град "updateGRAD"
            Map<String, Object> compareDevicesMap = compareDevices(devicesGIS, devicesGrad);

            // -----------------------------------------------------------------------------------------------------
            // расставляем идентификаторы в Град
            countUpdateGrad = meteringDeviceGRADDAO.updateGradMeteringDevices((Map<MeteringDeviceID, ExportMeteringDeviceDataResultType>) compareDevicesMap.get("updateGRAD"));

            // отсылаем новые в ГИС
            callImportMeteringDevices((Map<MeteringDeviceID, ImportMeteringDeviceDataRequest.MeteringDevice>) (compareDevicesMap.get("insertGIS")),
                    entryHouse, meteringDeviceGRADDAO, false);

            // архивируем в ГИС
            callImportMeteringDevices(
                    convertMeteringDeviceToArchive ((List<ExportMeteringDeviceDataResultType>) compareDevicesMap.get("archiveGIS"))
                            .stream()
                            .collect(Collectors.toMap(e -> new MeteringDeviceID("", e.getDeviceDataToUpdate().getMeteringDeviceVersionGUID(), null), e->e)),
                    entryHouse, meteringDeviceGRADDAO,true);
        } catch (SQLException | ParseException | PreGISException | SOAPException | JAXBException | FileNotFoundException e) {
            errorState = -1;
            answerProcessing.sendErrorToClient("Синхронизация ПУ по дому" + entryHouse.getAddresStringShort() + ": ", "\"Синхронизация ПУ\" ", LOGGER, e);
        }
    }

    /**
     * сравниваем списки ПУ из Град и ГИС. Формируем списки:
     *  "insertGIS" - на добавление в ГИС
     *  "archiveGIS" - на изменение/архивацию в ГИС
     *  "updateGRAD" - на изменение в Град
     * @param devicesListGIS - проборы по дому из ГИС
     * @param devicesMapGrad - проборы по дому из Град
     * @return - возвращает мапу с объектами, тип которых зависит от ключа
     * insertGIS - Map<MeteringDeviceID, ImportMeteringDeviceDataRequest.MeteringDevice>
     * archiveGIS - List<ExportMeteringDeviceDataResultType>
     * updateGRAD - Map<MeteringDeviceID, ExportMeteringDeviceDataResultType>
     */
    private Map<String, Object> compareDevices(
            final List<ExportMeteringDeviceDataResultType> devicesListGIS,
            final HashMap<MeteringDeviceID, ImportMeteringDeviceDataRequest.MeteringDevice> devicesMapGrad){

        if (answerProcessing != null) answerProcessing.sendMessageToClient("Сравнение массивов ПУ, полученных из ГИС и Град");
        Map<MeteringDeviceID, ImportMeteringDeviceDataRequest.MeteringDevice> insertGISMap = new HashMap<>();
        List<ExportMeteringDeviceDataResultType> archiveGISList = new ArrayList<>();
        Map<MeteringDeviceID, ExportMeteringDeviceDataResultType> updateGRADMap = new HashMap<>();

        // получаем ПУ из ГИС, которым есть соответствие в Град по номеру ПУ + типу + абоненту/помещению (им в Граде мы занесем идентификаторы из ГИС)
        if(devicesListGIS != null) {
            Iterator<ExportMeteringDeviceDataResultType> deviceGISIterator = devicesListGIS.iterator();
            while (deviceGISIterator.hasNext()) {

                ExportMeteringDeviceDataResultType deviceGIS = deviceGISIterator.next();

                if(devicesMapGrad != null) {
                    Iterator<Map.Entry<MeteringDeviceID, ImportMeteringDeviceDataRequest.MeteringDevice>> deviceGradIterator = devicesMapGrad.entrySet().iterator();
                    while (deviceGradIterator.hasNext()) {

                        Map.Entry<MeteringDeviceID, ImportMeteringDeviceDataRequest.MeteringDevice> deviceGradEntry = deviceGradIterator.next();

                        // чисто вспомогательные переменные для более короткого кода
                        MeteringDeviceBasicCharacteristicsType basicGISCharacteristics = deviceGIS.getBasicChatacteristicts();
                        MeteringDeviceBasicCharacteristicsType basicGRADCharacteristics = deviceGradEntry.getValue().getDeviceDataToCreate().getBasicChatacteristicts();

                        // если ПУ есть в Град по идентификатору ГИС ЖКХ
                        if (deviceGradEntry.getKey() != null && deviceGradEntry.getKey().getMeteringDeviceRootGUID() != null &&
                                deviceGradEntry.getKey().getMeteringDeviceRootGUID().equalsIgnoreCase(deviceGIS.getMeteringDeviceRootGUID())) {
                            // удаляем из списка ГИС
                            deviceGISIterator.remove();
                            // удаляем из списка Град
                            deviceGradIterator.remove();
                            break;
                        }

                        // если в Граде у ПУ нет идентификатора ГИС и номера ПУ совпадают - проверяем на совпадение типа и места установки
                        if ( //(deviceGradEntry.getKey().getMeteringDeviceRootGUID() == null || deviceGradEntry.getKey().getMeteringDeviceRootGUID().isEmpty())
                                basicGISCharacteristics.getMeteringDeviceNumber().equals(basicGRADCharacteristics.getMeteringDeviceNumber())) {
                            // оба тип ПУ ApartmentHouseDevice
                            if (basicGISCharacteristics.getApartmentHouseDevice() != null && basicGRADCharacteristics.getApartmentHouseDevice() != null) {
                                // и их апартаменты и номер ПУ совпадают
                                if (basicGISCharacteristics.getApartmentHouseDevice().getAccountGUID().containsAll(basicGRADCharacteristics.getApartmentHouseDevice().getAccountGUID()) &&
                                        basicGRADCharacteristics.getApartmentHouseDevice().getAccountGUID() != null && basicGRADCharacteristics.getApartmentHouseDevice().getAccountGUID().containsAll(basicGISCharacteristics.getApartmentHouseDevice().getAccountGUID())) {
                                    // заносим такой ПУ на обновление в Град
                                    updateGRADMap.put(new MeteringDeviceID(deviceGIS.getMeteringDeviceRootGUID(), deviceGIS.getMeteringDeviceVersionGUID(), deviceGradEntry.getKey().getMeterGradId()), deviceGIS);
                                    // удаляем из списка ГИС
                                    deviceGISIterator.remove();
                                    // удаляем из списка Град
                                    deviceGradIterator.remove();
                                    break;
                                }
                            }

                            // если тип ПУ LivingRoomDevice
                            if (basicGISCharacteristics.getLivingRoomDevice() != null && basicGRADCharacteristics.getLivingRoomDevice() != null) {
                                // и их LivingRoomGUID совпадают
                                if (basicGISCharacteristics.getLivingRoomDevice().getLivingRoomGUID().containsAll(basicGRADCharacteristics.getLivingRoomDevice().getLivingRoomGUID()) &&
                                        basicGRADCharacteristics.getLivingRoomDevice().getLivingRoomGUID().containsAll(basicGISCharacteristics.getLivingRoomDevice().getLivingRoomGUID()) &&
                                        basicGISCharacteristics.getLivingRoomDevice().getAccountGUID().containsAll(basicGRADCharacteristics.getLivingRoomDevice().getAccountGUID()) &&
                                        basicGRADCharacteristics.getLivingRoomDevice().getAccountGUID() != null && basicGRADCharacteristics.getLivingRoomDevice().getAccountGUID().containsAll(basicGISCharacteristics.getLivingRoomDevice().getAccountGUID())) {
                                    // заносим такой ПУ на обновление в Град
                                    updateGRADMap.put(new MeteringDeviceID(deviceGIS.getMeteringDeviceRootGUID(), deviceGIS.getMeteringDeviceVersionGUID(), deviceGradEntry.getKey().getMeterGradId()), deviceGIS);
                                    // удаляем из списка ГИС
                                    deviceGISIterator.remove();
                                    // удаляем из списка Град
                                    deviceGradIterator.remove();
                                    break;
                                }
                            }

                            // если тип ПУ CollectiveApartmentDevice
                            if (basicGISCharacteristics.getCollectiveApartmentDevice() != null && basicGRADCharacteristics.getCollectiveApartmentDevice() != null) {
                                // и их PremiseGUID совпадают
                                if (basicGISCharacteristics.getCollectiveApartmentDevice().getPremiseGUID().equalsIgnoreCase(basicGRADCharacteristics.getCollectiveApartmentDevice().getPremiseGUID()) &&
                                        basicGRADCharacteristics.getCollectiveApartmentDevice().getPremiseGUID().equalsIgnoreCase(basicGISCharacteristics.getCollectiveApartmentDevice().getPremiseGUID()) &&
                                        basicGISCharacteristics.getCollectiveApartmentDevice().getAccountGUID().containsAll(basicGRADCharacteristics.getCollectiveApartmentDevice().getAccountGUID()) &&
                                        basicGRADCharacteristics.getCollectiveApartmentDevice().getAccountGUID() != null && basicGRADCharacteristics.getCollectiveApartmentDevice().getAccountGUID().containsAll(basicGISCharacteristics.getCollectiveApartmentDevice().getAccountGUID())) {
                                    // заносим такой ПУ на обновление в Град
                                    updateGRADMap.put(new MeteringDeviceID(deviceGIS.getMeteringDeviceRootGUID(), deviceGIS.getMeteringDeviceVersionGUID(), deviceGradEntry.getKey().getMeterGradId()), deviceGIS);
                                    // удаляем из списка ГИС
                                    deviceGISIterator.remove();
                                    // удаляем из списка Град
                                    deviceGradIterator.remove();
                                    break;
                                }
                            }
                            // оба тип ПУ NonResidentialPremiseDevice
                            if (basicGISCharacteristics.getNonResidentialPremiseDevice() != null && basicGRADCharacteristics.getNonResidentialPremiseDevice() != null) {
                                // и их PremiseGUID совпадают
                                if (basicGISCharacteristics.getNonResidentialPremiseDevice().getPremiseGUID().equalsIgnoreCase(basicGRADCharacteristics.getNonResidentialPremiseDevice().getPremiseGUID()) &&
                                        basicGRADCharacteristics.getNonResidentialPremiseDevice().getPremiseGUID().equalsIgnoreCase(basicGISCharacteristics.getNonResidentialPremiseDevice().getPremiseGUID()) &&
                                        basicGISCharacteristics.getNonResidentialPremiseDevice().getAccountGUID().containsAll(basicGRADCharacteristics.getNonResidentialPremiseDevice().getAccountGUID()) &&
                                        basicGRADCharacteristics.getNonResidentialPremiseDevice().getAccountGUID() != null && basicGRADCharacteristics.getNonResidentialPremiseDevice().getAccountGUID().containsAll(basicGISCharacteristics.getNonResidentialPremiseDevice().getAccountGUID())) {
                                    // заносим такой ПУ на обновление в Град
                                    updateGRADMap.put(new MeteringDeviceID(deviceGIS.getMeteringDeviceRootGUID(), deviceGIS.getMeteringDeviceVersionGUID(), deviceGradEntry.getKey().getMeterGradId()), deviceGIS);
                                    // удаляем из списка ГИС
                                    deviceGISIterator.remove();
                                    // удаляем из списка Град
                                    deviceGradIterator.remove();
                                    break;
                                }
                            }

                            // оба тип ПУ ResidentialPremiseDevice
                            if (basicGISCharacteristics.getResidentialPremiseDevice() != null && basicGRADCharacteristics.getResidentialPremiseDevice() != null) {
                                // и их PremiseGUID совпадают
                                if (basicGISCharacteristics.getResidentialPremiseDevice().getPremiseGUID().equalsIgnoreCase(basicGRADCharacteristics.getResidentialPremiseDevice().getPremiseGUID()) &&
                                        basicGRADCharacteristics.getResidentialPremiseDevice().getPremiseGUID().equalsIgnoreCase(basicGISCharacteristics.getResidentialPremiseDevice().getPremiseGUID()) &&
                                        basicGISCharacteristics.getResidentialPremiseDevice().getAccountGUID().containsAll(basicGRADCharacteristics.getResidentialPremiseDevice().getAccountGUID()) &&
                                        basicGRADCharacteristics.getResidentialPremiseDevice().getAccountGUID() != null && basicGRADCharacteristics.getResidentialPremiseDevice().getAccountGUID().containsAll(basicGISCharacteristics.getResidentialPremiseDevice().getAccountGUID())) {
                                    // заносим такой ПУ на обновление в Град
                                    updateGRADMap.put(new MeteringDeviceID(deviceGIS.getMeteringDeviceRootGUID(), deviceGIS.getMeteringDeviceVersionGUID(), deviceGradEntry.getKey().getMeterGradId()), deviceGIS);
                                    // удаляем из списка ГИС
                                    deviceGISIterator.remove();
                                    // удаляем из списка Град
                                    deviceGradIterator.remove();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            // все что осталось в ПУ (активные) из ГИС заносим на удаление в ГИС

            archiveGISList.addAll(devicesListGIS.stream()
                    .filter(e->e.getStatusRootDoc().equalsIgnoreCase("Active"))
                    .filter(e->(e.getBasicChatacteristicts().getCollectiveDevice() == null))
                    .collect(Collectors.toList()));
            if(archiveGISList.size() > 0) {
                if (answerProcessing != null) answerProcessing.sendMessageToClient("Кол-во ПУ на архивацию в ГИС: " + archiveGISList.size());
            }
        }

        // все что осталось в ПУ из ГРАД заносим на создание в ГИС
        if(devicesMapGrad != null && devicesMapGrad.size() > 0) {
            insertGISMap.putAll(devicesMapGrad);
            if (answerProcessing != null) answerProcessing.sendMessageToClient("Кол-во ПУ на добавление в ГИС: " + insertGISMap.size());
        }

        if(updateGRADMap.size() > 0){
            if (answerProcessing != null) answerProcessing.sendMessageToClient("Кол-во ПУ на обновление в Град: " + updateGRADMap.size());
        }

        Map<String, Object> resultMap = new LinkedHashMap<>();

        resultMap.put("insertGIS", insertGISMap);
        resultMap.put("archiveGIS", archiveGISList);
        resultMap.put("updateGRAD", updateGRADMap);

        return resultMap;
    }

    /**
     * Метод преобразует список приборов учета типа Export в список приборов типа Import для архивации
     * @param exportDevices - приборы учета для архивации типа Export. Лишние приборы в ГИС, которые нужно заархивировать
     * @return ПУ для архивации типа Import
     * @throws SQLException
     * @throws PreGISException
     */
    private List<ImportMeteringDeviceDataRequest.MeteringDevice> convertMeteringDeviceToArchive (List<ExportMeteringDeviceDataResultType> exportDevices)
            throws SQLException, PreGISException {
        final ReferenceNSI nsi = new ReferenceNSI(answerProcessing);

        List<ImportMeteringDeviceDataRequest.MeteringDevice> deviceList = new ArrayList<>();

        for (ExportMeteringDeviceDataResultType entry : exportDevices) {
            deviceList.add(getArchiveMeterDevice(entry.getMeteringDeviceVersionGUID() , nsi.getNsiRef("21", "Ошибка")));
        }
        return deviceList;
    }

    /**
     * Метод, формирует прибор учета для архивирования в ГИС ЖКХ.
     * @param meterVersionGUID идентификатор версии ПУ.
     * @return архивированное устройство.
     */
    private ImportMeteringDeviceDataRequest.MeteringDevice getArchiveMeterDevice(
            String meterVersionGUID, ru.gosuslugi.dom.schema.integration.nsi_base.NsiRef nsiRef){

        ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate device = new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate();

        device.setMeteringDeviceVersionGUID(meterVersionGUID);
        device.setArchiveDevice(new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ArchiveDevice());
        device.getArchiveDevice().setArchivingReason(nsiRef);

        ImportMeteringDeviceDataRequest.MeteringDevice meteringDevices = new ImportMeteringDeviceDataRequest.MeteringDevice();
        meteringDevices.setTransportGUID(OtherFormat.getRandomGUID());
        meteringDevices.setDeviceDataToUpdate(device);
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("ПУ добавлен для архивирования: " +
                meteringDevices.getDeviceDataToUpdate().getMeteringDeviceVersionGUID());

        return meteringDevices;
    }

    /**
     * Метод, добавляет все приборы учёта в архив.
     * ВНИМАНИЕ! Будьте внимательны, что действительно хотите грохнуть все приборы учёта?
     *
     * @param fias код дома по ФИАС.
     * @throws SQLException
     */
    @Deprecated
    public void archiveAllDevices(final String fias) throws SQLException, ParseException, PreGISException, FileNotFoundException, SOAPException, JAXBException {

        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {
//        Получаем выгруженные ПУ.
            //final ExportMeteringDeviceData exportMeteringDeviceData = new ExportMeteringDeviceData(answerProcessing);
            List<ExportMeteringDeviceDataResultType> exportMeteringDeviceDataResultList =  HomeManagementAsyncPort.callExportMeteringDeviceData(fias,answerProcessing).getExportMeteringDeviceDataResult();

            if (exportMeteringDeviceDataResultList != null && exportMeteringDeviceDataResultList.size() > 0) {
                final LinkedHashMap<String, ImportMeteringDeviceDataRequest.MeteringDevice> deviceForArchive = new LinkedHashMap<>();

                for (ExportMeteringDeviceDataResultType resultType : exportMeteringDeviceDataResultList) {
                    if (resultType.getStatusRootDoc().equalsIgnoreCase("Active")) {
                        deviceForArchive.put(resultType.getMeteringDeviceVersionGUID(), null);
                    }
                }

                final ImportMeteringDeviceData importMeteringDeviceData = new ImportMeteringDeviceData(answerProcessing);

                final MeteringDeviceToArchive toArchive = new MeteringDeviceToArchive(answerProcessing, deviceForArchive);
                //callImportMeteringDevices(toArchive.addMeteringDeviceToArchive(), fias, toArchive, connectionGRAD);
            }
        }
    }

    /**
     * Метод, получает список домов готовых для выгрузки в ГИС ЖКХ, формирует по ним новые ПУ и отправляет в ГИС ЖКХ.
     *
     * @throws SQLException
     * @throws PreGISException
     */
    @Deprecated
    private int createMeteringDevice(final Integer houseGradId) throws SQLException, PreGISException, ParseException, FileNotFoundException, SOAPException, JAXBException {

        errorState = 1;

//        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {
//            final HouseGRADDAO houseGRADDAO = new HouseGRADDAO(answerProcessing);
//            final LinkedHashMap<String, HouseRecord> houseAddedGisJkh = houseGRADDAO.getHouseRecords(houseGradId, connectionGRAD);
//            final ImportMeteringDeviceData importMeteringDeviceData = new ImportMeteringDeviceData(answerProcessing);
//
//            for (Map.Entry<String, HouseRecord> entryHouse : houseAddedGisJkh.entrySet()) {
//                if (answerProcessing!= null ) {
//                    answerProcessing.sendMessageToClient("Формирую ПУ для дома: " + entryHouse.getValue().getAddresString());
//                }
//                final MeteringDeviceGRADDAO meteringDeviceGRADDAO = new MeteringDeviceGRADDAO(answerProcessing, entryHouse.getValue().getGrad_id()); // создаввать каждый раз новый, беру из БД по одному дому данные и использую каждый раз
//
////                Импортируем ранее загруженные ПУ
//                //final ExportMeteringDeviceData exportMeteringDeviceData = new ExportMeteringDeviceData(answerProcessing);
//
//                GetStateResult getStateResult = HomeManagementAsyncPort.callExportMeteringDeviceData(entryHouse.getKey(), answerProcessing);
//                List<ExportMeteringDeviceDataResultType> exportMeteringDeviceDataResultList;
//                if(getStateResult != null) {
//                    exportMeteringDeviceDataResultList = getStateResult.getExportMeteringDeviceDataResult();
//                    if (exportMeteringDeviceDataResultList != null) {
//                        meteringDeviceGRADDAO.checkExportMeteringDevices(exportMeteringDeviceDataResultList, connectionGRAD);
//                    }
//                }
//
//                java.util.List<ImportMeteringDeviceDataRequest.MeteringDevice> devices = meteringDeviceGRADDAO.getMeteringDevicesForCreate(connectionGRAD); // если добавились новые идентификаторы, нужно исключить их
//
//                countAll += meteringDeviceGRADDAO.getCountAll();
//
//                //callImportMeteringDevices(devices, entryHouse.getKey(), meteringDeviceGRADDAO, connectionGRAD);
//
////                Повторно загружаем для занесения MeteringDeviceRootGUID.
//                getStateResult = HomeManagementAsyncPort.callExportMeteringDeviceData(entryHouse.getKey(), answerProcessing);
//                if(getStateResult != null) {
//                    exportMeteringDeviceDataResultList = getStateResult.getExportMeteringDeviceDataResult();
//                    if (exportMeteringDeviceDataResultList != null) {
//                        meteringDeviceGRADDAO.checkExportMeteringDevices(exportMeteringDeviceDataResultList, connectionGRAD);
//                        for (ExportMeteringDeviceDataResultType device : exportMeteringDeviceDataResultList) {
//                            if (device.getStatusRootDoc().equalsIgnoreCase("Active")) {
////                            только активные устройства
//                                countAllGisJkh++;
//                            }
//                        }
////                    все устройства даже архивные
////                    countAllGisJkh = exportMeteringDeviceDataResult.getMeteringDevice().size();
//                    }
//                }
//
//                if (!meteringDeviceGRADDAO.getDeviceForArchiveAndCreateMap().isEmpty()) {
//                    archiveDataList.add(new ArchiveData(importMeteringDeviceData, entryHouse.getKey(), meteringDeviceGRADDAO));
//                }
//
//                countUpdate += meteringDeviceGRADDAO.getCountUpdate();
//                countAdded += meteringDeviceGRADDAO.getCountAdded();
//                if (errorState > meteringDeviceGRADDAO.getErrorState()) {
//                    errorState = meteringDeviceGRADDAO.getErrorState();
//                }
//            }
//            showInfo();
//        }
//        final int countRecreate = getCountMeteringDevicesForRecreate();
////        System.out.println("countRecreate: " + countRecreate);
//        if (countRecreate > 0) {
//            answerProcessing.showQuestionToClient("Не удалось обновить " + countRecreate + " " + getDeviceTag(countRecreate) + " " +
//                    "Желаете добавить эти устройства в архив ГИС ЖКХ и создать повторно?\n", this);
//        }
        return errorState;
    }

    /**
     * Метод, пересоздание ПУ.
     *
     * @throws SQLException
     * @throws PreGISException
     * @throws JAXBException
     * @throws SOAPException
     * @throws ParseException
     * @throws FileNotFoundException
     */
    private void recreatingMeteringDevices() throws SQLException, PreGISException, JAXBException, SOAPException, ParseException, FileNotFoundException {

        try (Connection connection = ConnectionBaseGRAD.instance().getConnection()) {
            answerProcessing.sendMessageToClient("Пересоздаю ПУ...");
            for (ArchiveData archiveData : archiveDataList) {
                setDevicesToArchiveAndCreate(archiveData.getFias(),
                        archiveData.getMeteringDeviceGRADDAO(), connection);
            }
            if (errorState == -1) {
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendErrorToClientNotException("Возникла ошибка!\nОперация: \"Пересоздание ПУ\" прервана!");
            } else if (errorState == 0) {
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendErrorToClientNotException("Операция: \"Пересоздание ПУ\" завершилась с ошибками!");
            } else if (errorState == 1) {
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendOkMessageToClient("\"Пересоздание ПУ\" успешно выполнено.");
            }
        }

    }

    /**
     * Метод, возвращает количество записей готовых для пересоздания.
     *
     * @return количество записей готовых для пересоздания.
     */
    @Deprecated
    private int getCountMeteringDevicesForRecreate() {
        int count = 0;
//        for (ArchiveData data : archiveDataList) {
//            count += data.getMeteringDeviceGRADDAO().getDeviceForArchiveAndCreateMap().size();
//            for (Map.Entry<String, ImportMeteringDeviceDataRequest.MeteringDevice> entry : data.getMeteringDeviceGRADDAO().getDeviceForArchiveAndCreateMap().entrySet()) {
//                answerProcessing.sendMessageToClient("");
//                answerProcessing.sendMessageToClient("ПУ с идентификатором: " +
//                        entry.getKey() + " не удалось обновить.");
//            }
//        }
        return count;
    }

    /**
     * Метод, выводит информацию пользователю.
     */
    private void showInfo() {
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Из Град получено приборов: " + countGrad);
        answerProcessing.sendMessageToClient("Из ГИС получено приборов: " + countGis);
        answerProcessing.sendMessageToClient("Обновлено в ГРАД: " + countUpdateGrad);
        answerProcessing.sendMessageToClient("Отправлено на добавление в ГИС ЖКХ: " + countAddedGis);
        answerProcessing.sendMessageToClient("Ошибок добавления в ГИС ЖКХ: " + countAddErrorGis);
        answerProcessing.sendMessageToClient("Отправлено на архивировацию в ГИС ЖКХ: " + countArchiveGis);
        answerProcessing.sendMessageToClient("Ошибок архивировации в ГИС ЖКХ: " + countArchErrorGis);
    }

    /**
     * Метод, обрабатывает ПУ для пересоздания их в ГИС ЖКХ.
     *
     * @param fias                     код дома по ФИАС.
     * @param meteringDeviceGRADDAO    объект содержащий данные о ПУ.
     * @param connectionGRAD           подключение к БД ГРАД.
     * @throws ParseException
     * @throws SQLException
     * @throws PreGISException
     * @throws FileNotFoundException
     * @throws SOAPException
     * @throws JAXBException
     */
    private void setDevicesToArchiveAndCreate(final String fias,
                                              final MeteringDeviceGRADDAO meteringDeviceGRADDAO, final Connection connectionGRAD)
            throws ParseException, SQLException, PreGISException, FileNotFoundException, SOAPException, JAXBException {

        final MeteringDeviceToArchive toArchive = new MeteringDeviceToArchive(answerProcessing, meteringDeviceGRADDAO.getDeviceForArchiveAndCreateMap());
        // callImportMeteringDevices(toArchive.addMeteringDeviceToArchive(), fias, toArchive, connectionGRAD);
        // callImportMeteringDevices(toArchive.getListForCreateDevices(), fias, meteringDeviceGRADDAO, connectionGRAD);
    }

    /**
     * Метод получает набор приборов учета для занесения в ГИС, обрабатывает его и, если необходимо, отсылает в Град на обновление идентификаторов
     * @param devicesMap - набор приборов учета
     * @param houseRecord - запись дома
     * @param meteringDeviceGRADDAO - DAO для обмена по ПУ с Град
     * @param archive - архивируются ПУ или нет. Есл инет - данные заносятся в Град
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws SOAPException
     * @throws JAXBException
     * @throws PreGISException
     */
    private void callImportMeteringDevices(final Map<MeteringDeviceID, ImportMeteringDeviceDataRequest.MeteringDevice> devicesMap,
                                           final HouseRecord houseRecord, MeteringDeviceGRADDAO meteringDeviceGRADDAO, boolean archive) throws SQLException, FileNotFoundException, SOAPException, JAXBException, PreGISException {
        if (devicesMap == null || devicesMap.size() == 0) return;
        answerProcessing.sendMessageToClient("Вызов импорта для дома " + houseRecord.getAddresStringShort() + ". Кол-во ПУ: "  + devicesMap.size());
        // создаем список устройств для удобства разделения на небольшие партии
        List<ImportMeteringDeviceDataRequest.MeteringDevice> devices = new ArrayList<>(devicesMap.values());

        // создаем мапу соответствия TransportID - GradMeterID, для дальнейшей обработки результатов, если не архивируем ПУ.
        // Делаем на этом уровне, чтобы каждый раз не преобразовывать мапу
        Map<String, Integer> devicesGradToTransport = null;
        if(!archive) {
            devicesGradToTransport = devicesMap.entrySet()
                    .stream()
                    .collect(Collectors.toMap(e -> e.getValue().getTransportGUID(),
                            e -> e.getKey().getMeterGradId()));
            answerProcessing.sendMessageToClient("Приборов на занесение в ГИС: " + devicesMap.size());

        }
        else{
            answerProcessing.sendMessageToClient("Приборов на архивацию в ГИС: " + devicesMap.size());
        }

        int count = 0;
        while (count < devices.size()) {
            // answerProcessing.sendMessageToClient("::clearLabelText");
            GetStateResult result;
            // разбиваем запрос на пакеты по 20 ПУ
            if (count + 20 > devices.size()) {
                result = HomeManagementAsyncPort.callImportMeteringDeviceData(houseRecord.getFias(), devices.subList(count, devices.size()), answerProcessing);
                count += 20;
            } else {
                result = HomeManagementAsyncPort.callImportMeteringDeviceData(houseRecord.getFias(), devices.subList(count, count += 20), answerProcessing);
            }

            // обрабатываем результат
            if (result != null && result.getImportResult() != null) {
                for (ImportResult importResult : result.getImportResult()) {
                    if (importResult != null && importResult.getCommonResult() != null) {
                        // выведем ошибки
                        for (ImportResult.CommonResult commonResult : importResult.getCommonResult()) {
                            if (commonResult.getError() != null && commonResult.getError().size() > 0) {
                                if (errorState > 0) errorState= 0;
                                if (!archive) {
                                    countAddErrorGis++;
                                }else{
                                    countArchErrorGis++;
                                }
                                showErrorMeteringDevices(commonResult.getTransportGUID(), commonResult.getError().get(0).getErrorCode(),
                                        commonResult.getError().get(0).getDescription());
                            }
                        }
                        // отсылаем в Град результаты импорта для занесения идентификаторов (если не архивация)
                        if (!archive) {
                            meteringDeviceGRADDAO.setMeteringDevices(importResult, devicesGradToTransport);

                            countAddedGis += importResult.getCommonResult().size();

                            if (errorState > meteringDeviceGRADDAO.getErrorState())
                                errorState = meteringDeviceGRADDAO.getErrorState();
                        }
                        else{
                            countArchiveGis += importResult.getCommonResult().size();
                        }
                        answerProcessing.sendMessageToClient("Кол-во ПУ в результате импорта: " + importResult.getCommonResult().size());
                    }
                }
            }
        }
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
        if(transportGUID != null && !transportGUID.isEmpty()) answerProcessing.sendMessageToClient("TransportGUID: " + transportGUID);
        if(errorCode != null && !errorCode.isEmpty()) answerProcessing.sendMessageToClient("Код ошибки: " + errorCode);
        answerProcessing.sendMessageToClient("Описание ошибки: " + description);
        errorState = 0;
    }

    /**
     * Метод, формирует запросы на порции по 20 устройст, ГИС ЖКХ выдаёт ошибки, когда устройств много.
     *
     * @param devicesMap                  - мапа ПУ с идентификаторами.
     * @param fias                     - ФИАС дома.
     * @param deviceGRADDAO            - объект для занисения данных в БД.
     * @param connectionGRAD           - подключение к БД ГРАД.
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws SOAPException
     * @throws JAXBException
     */
    @Deprecated
    private void callImportMeteringDevices(final Map<String, ImportMeteringDeviceDataRequest.MeteringDevice> devicesMap,
                                           final String fias, final IMeteringDevices deviceGRADDAO,
                                           final Connection connectionGRAD, boolean archive) throws SQLException, FileNotFoundException, SOAPException, JAXBException, PreGISException {

        if (devicesMap != null && devicesMap.size() > 0) {
            List<ImportMeteringDeviceDataRequest.MeteringDevice> devices = new ArrayList<>(devicesMap.values());
            int count = 0;
            while (count < devices.size()) {
                // answerProcessing.sendMessageToClient("::clearLabelText");
                GetStateResult result;
                if (count + 20 > devices.size()) {
                    result = HomeManagementAsyncPort.callImportMeteringDeviceData(fias, devices.subList(count, devices.size()), answerProcessing);
                    count += 20;
                } else {
                    result = HomeManagementAsyncPort.callImportMeteringDeviceData(fias, devices.subList(count, count += 20), answerProcessing);
                }

                if (result != null && result.getImportResult() != null) {
                    for (ImportResult importResult : result.getImportResult()) {
                        if (importResult != null && importResult.getCommonResult() != null) {
                            if(!archive) {
                                deviceGRADDAO.setMeteringDevices(importResult, connectionGRAD, devicesMap);
                            }
                            answerProcessing.sendMessageToClient("Кол-во ПУ в результате импорта: " + importResult.getCommonResult().size());
//                    for (ImportResult.CommonResult result : importResult.getCommonResult()) {
//                        answerProcessing.sendMessageToClient("GUID: " + result.getGUID());
//                        answerProcessing.sendMessageToClient("UniqueNumber: " + result.getUniqueNumber());
//                        if(result.getImportMeteringDevice() != null) {
//                            answerProcessing.sendMessageToClient("meteringDeviceGUID: " + result.getImportMeteringDevice().getMeteringDeviceGUID());
//                        }
//                        answerProcessing.sendMessageToClient("TransportGUID: " + result.getTransportGUID());
//                        answerProcessing.sendMessageToClient("");
//                    }
                            if (errorState > deviceGRADDAO.getErrorState()) errorState = deviceGRADDAO.getErrorState();
                        } else {
                            errorState = -1;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void go() {
        try {
            errorState = 1;
            recreatingMeteringDevices();
        } catch (SQLException | FileNotFoundException | ParseException | SOAPException | JAXBException | PreGISException e) {
            answerProcessing.sendErrorToClient("Обновление ПУ", "\"Обновление ПУ\"", LOGGER, e);
        }
    }

    @Override
    public void stop() {
        archiveDataList.clear();
    }

    private String getDeviceTag(final int size) {

        int value;

        if ((size / 10) % 10 == 1) {
            value = size / 100;
        } else {
            value = size % 10;
        }

        if (value == 1) {
            return "прибор учёта";
        } else if (value > 1 && value < 5) {
            return "прибора учёта";
        } else {
            return "приборов учёта";
        }
    }

    public int getErrorState() {
        return errorState;
    }

    /**
     * Класс, для описания объекта.
     */
    private class ArchiveData {
        private final ImportMeteringDeviceData importMeteringDeviceData;
        private final String fias;
        private final MeteringDeviceGRADDAO meteringDeviceGRADDAO;

        private ArchiveData(final ImportMeteringDeviceData importMeteringDeviceData,
                            final String fias,
                            final MeteringDeviceGRADDAO meteringDeviceGRADDAO) {

            this.importMeteringDeviceData = importMeteringDeviceData;
            this.fias = fias;
            this.meteringDeviceGRADDAO = meteringDeviceGRADDAO;
        }

        private ImportMeteringDeviceData getImportMeteringDeviceData() {
            return importMeteringDeviceData;
        }

        private String getFias() {
            return fias;
        }

        private MeteringDeviceGRADDAO getMeteringDeviceGRADDAO() {
            return meteringDeviceGRADDAO;
        }

    }
}
