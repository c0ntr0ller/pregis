package ru.progmatik.java.pregis.services.device_metering;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.CommonResultType;
import ru.gosuslugi.dom.schema.integration.device_metering.*;
import ru.gosuslugi.dom.schema.integration.metering_device_base.ElectricMeteringValueType;
import ru.gosuslugi.dom.schema.integration.metering_device_base.OneRateMeteringValueType;
import ru.gosuslugi.dom.schema.integration.nsi_base.NsiRef;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.devices.MeteringDeviceValuesGradDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseRecord;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemDataSet;
import ru.progmatik.java.pregis.connectiondb.localdb.meteringdevice.MeteringDeviceValuesLocalDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.meteringdevice.MeteringDevicesDataLocalDBDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSI;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSIDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

/**
 * Класс, обрабатывает показания ПУ.
 */
public final class UpdateMeteringDeviceValues {

    private static final Logger LOGGER = Logger.getLogger(UpdateMeteringDeviceValues.class);
    private final AnswerProcessing answerProcessing;
    private final ReferenceNSI referenceNSI;
    private final MeteringDeviceValuesLocalDAO deviceValuesLocalDAO;
    private final MeteringDevicesDataLocalDBDAO devicesDataLocalDBDAO;
    private MeteringDeviceValuesGradDAO deviceValuesGradDAO;
    private HashMap<String, MeteringDeviceValuesObject> tempMeteringDevicesValue; // хранит показания всех ПУ полученных из ГИС ЖКХ
    private int addedValueToGrad;
    private int addedValueToGISJKH;
    private int errorStatus;

    public UpdateMeteringDeviceValues(final AnswerProcessing answerProcessing) throws SQLException {

        this.answerProcessing = answerProcessing;
        deviceValuesLocalDAO = new MeteringDeviceValuesLocalDAO();
        devicesDataLocalDBDAO = new MeteringDevicesDataLocalDBDAO(answerProcessing);
        referenceNSI = new ReferenceNSI(answerProcessing);
        addedValueToGISJKH = 0;
        addedValueToGrad = 0;
    }

    public int updateAllMeteringDeviceValues(final Integer houseGradID) throws SQLException, PreGISException, ParseException, DatatypeConfigurationException {

        errorStatus = 1;

        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {
            final HouseGRADDAO houseGRADDAO = new HouseGRADDAO(answerProcessing);
            final LinkedHashMap<String, HouseRecord> houseAddedGisJkh = houseGRADDAO.getHouseRecords(houseGradID, connectionGRAD);
            for (Map.Entry<String, HouseRecord> entry : houseAddedGisJkh.entrySet()) {
                tempMeteringDevicesValue = new HashMap<>();
                updateMeteringDeviceValues(entry.getValue(), connectionGRAD);
            }
        }

        return errorStatus;
    }

    /**
     * Метод, разбирает ответ, находит нужные показания и заносит их если они отличаются от показаний в ГРАДе.
     * Если ГИС ЖКХ вернул ошибку или ничего, обработка прекращается.
     */
    private void updateMeteringDeviceValues(HouseRecord houseRecord, final Connection connectionGrad)
            throws SQLException, ParseException, PreGISException, DatatypeConfigurationException {

        final GetStateResult result = getExportMeteringDeviceHistory(houseRecord.getFias());

        if (result == null) { // Если в ответ получили ничего
            answerProcessing.sendErrorToClientNotException("Не удалось получить показания ПУ по дому: " + houseRecord.getAddresString());
            setErrorStatus(-1);
//            Если нет, ещё не одного выгруженного показания ПУ
        } else if (result.getErrorMessage() != null &&
                result.getErrorMessage().getErrorCode().equalsIgnoreCase("INT002012")) {
            deviceValuesGradDAO = new MeteringDeviceValuesGradDAO(answerProcessing);
            final HashMap<String, MeteringDeviceValuesObject> deviceValuesFromGrad = deviceValuesGradDAO.getMeteringDeviceValueFromGrad(houseRecord.getGrad_id(), connectionGrad);
            compareMeteringDevicesValue(houseRecord.getFias(), deviceValuesFromGrad, tempMeteringDevicesValue, connectionGrad);
//            Если возникла непредвиденная ошибка
        } else if (result.getErrorMessage() != null) {
            answerProcessing.sendErrorToClientNotException("Не удалось получить показания ПУ по дому: " + houseRecord.getAddresString());
//            answerProcessing.sendErrorToClientNotException("Сообщение от сервера ГИС ЖКХ: ");
//            answerProcessing.sendErrorToClientNotException("Код ошибки: " + result.getErrorMessage().getErrorCode());
//            answerProcessing.sendErrorToClientNotException("Описание ошибки: " + result.getErrorMessage().getDescription());
            setErrorStatus(0);
//            В остальных удачных случаях обрабатываем ответ.
        } else {
            parseMeteringDeviceValuesFromGISJKH(result);
            deviceValuesGradDAO = new MeteringDeviceValuesGradDAO(answerProcessing);
            final HashMap<String, MeteringDeviceValuesObject> deviceValuesFromGrad = deviceValuesGradDAO.getMeteringDeviceValueFromGrad(houseRecord.getGrad_id(), connectionGrad);
            compareMeteringDevicesValue(houseRecord.getFias(), deviceValuesFromGrad, tempMeteringDevicesValue, connectionGrad);
        }
    }

    /**
     * Метод, сравнивает показания ПУ полученных из ГИС ЖКХ и БД ГРАД.
     * В зависимости от расхождений добавляет в БД показания или формирует запрос для добавления показаний в ГИС ЖКХ.
     *
     * @param fias                           код дома по ФИАС
     * @param meteringDevicesValuesFromGrad  показания ПУ полученные из БД ГРАД.
     * @param meteringDevicesValueFromGISJKH показания ПУ полученные из ГИС ЖКХ.
     */
    private void compareMeteringDevicesValue(final String fias,
                                             final HashMap<String, MeteringDeviceValuesObject> meteringDevicesValuesFromGrad,
                                             final HashMap<String, MeteringDeviceValuesObject> meteringDevicesValueFromGISJKH,
                                             final Connection connectionGrad) throws SQLException, DatatypeConfigurationException, PreGISException {

//        final ImportMeteringDeviceValuesRequest request = new ImportMeteringDeviceValuesRequest(); // для отправки в ГИС ЖКХ
//        request.setFIASHouseGuid(fias);
        List<ImportMeteringDeviceValuesRequest.MeteringDevicesValues> devicesValuesList = new ArrayList<>();

        for (Map.Entry<String, MeteringDeviceValuesObject> entry : meteringDevicesValuesFromGrad.entrySet()) {
            if (entry.getKey() != null) {

                if (!devicesDataLocalDBDAO.isArchivingDeviceByRootGUID(entry.getKey())) {

                    final MeteringDeviceValuesObject valuesObject = meteringDevicesValueFromGISJKH.get(entry.getKey());

                    final ImportMeteringDeviceValuesRequest.MeteringDevicesValues devicesValues =
                            new ImportMeteringDeviceValuesRequest.MeteringDevicesValues();

                    if (valuesObject == null || valuesObject.getMeteringValue().compareTo(entry.getValue().getMeteringValue()) < 0) { // если в ГИС ЖКХ не найдены показания ПУ или показания меньше, добавляем из ГРАДа в ГИС ЖКХ.

                        devicesValues.setMeteringDeviceRootGUID(entry.getValue().getMeteringDeviceRootGUID());

                        if (entry.getValue().getNsiRef().getName().equalsIgnoreCase("Электрическая энергия")) { // Если счетчик по электричеству

                            final ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue electricDeviceValue =
                                    new ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue();

                            final ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue.CurrentValue currentValue
                                    = new ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue.CurrentValue();

                            currentValue.setTransportGUID(OtherFormat.getRandomGUID());
                            currentValue.setMeteringValueT1(entry.getValue().getMeteringValue());
                            currentValue.setMeteringValueT2(entry.getValue().getMeteringValueTwo());
                            currentValue.setMeteringValueT3(entry.getValue().getMeteringValueThree());
                            currentValue.setDateValue(DatatypeFactory.newInstance().newXMLGregorianCalendar(entry.getValue().getMeteringGregorianDate()));
                            electricDeviceValue.setCurrentValue(currentValue);
                            devicesValues.setElectricDeviceValue(electricDeviceValue);

                        } else { // остальные ПУ

                            final ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue deviceValue =
                                    new ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue();

                            final ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue.CurrentValue currentValue
                                    = new ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue.CurrentValue();

                            currentValue.setTransportGUID(OtherFormat.getRandomGUID());
                            currentValue.setMeteringValue(entry.getValue().getMeteringValue());
                            currentValue.setDateValue(DatatypeFactory.newInstance().newXMLGregorianCalendar(entry.getValue().getMeteringGregorianDate()));
                            currentValue.setMunicipalResource(entry.getValue().getNsiRef());

                            deviceValue.getCurrentValue().add(currentValue);
                            devicesValues.setOneRateDeviceValue(deviceValue);
                        }

                        devicesValuesList.add(devicesValues); // добавим устройство для отправки в ГИС ЖКХ.
                        addedValueToGISJKH++;

                    } else if (valuesObject.getMeteringValue().compareTo(entry.getValue().getMeteringValue()) > 0) { // если показания ПУ больше в ГИС ЖКХ, заносим в ГРАД.

                        setMeteringDeviceValue(valuesObject, connectionGrad);
                        addedValueToGrad++;
                    }
                } else {
                    LOGGER.info("ПУ не удаётся обновить, возможно он архивирован: " + entry.getValue());
                }
            } else{
                LOGGER.info("для ПУ не задан RootGUID, возможно он пропущен при синхронизации ПУ дома: " + entry.getValue().getNsiRef().getName());
            }
        }

        if (!devicesValuesList.isEmpty()) { // если есть показания для отправки в ГИС ЖКХ

            int count = 0;
            final int chunk = ResourcesUtil.instance().getMaxRequestSize();
            while (count < devicesValuesList.size()) {
                // answerProcessing.sendMessageToClient("::clearLabelText");
                GetStateResult result;
                // разбиваем запрос на пакеты по 100 ПУ
                if (count + chunk > devicesValuesList.size()) {
                    result = DeviceMeteringAsyncPort.callImportMeteringDeviceValues(fias, devicesValuesList.subList(count, devicesValuesList.size()), answerProcessing);
                    count += chunk;
                } else {
                    result = DeviceMeteringAsyncPort.callImportMeteringDeviceValues(fias, devicesValuesList.subList(count, count += chunk), answerProcessing);
                }

                // обрабатываем результат
                if (result != null && result.getImportResult() != null && !result.getImportResult().isEmpty()){ // если есть ответ от ГИС ЖКХ
                    for (CommonResultType resultType : result.getImportResult()) { // смотрим каждый элемент
                        if (resultType.getError() != null && !resultType.getError().isEmpty()) { // если есть ошибки
                            for (CommonResultType.Error error : resultType.getError()) {
                                showErrorMeteringDevices(resultType.getTransportGUID(), error.getErrorCode(), error.getDescription());
                            }
//                        } else {
//                            answerProcessing.sendMessageToClient("");
//                            answerProcessing.sendMessageToClient("Обновлены показания прибора учёта. " +
//                                    "идентификатор ПУ: " + resultType.getGUID());
                        }
                    }
                } else {
                    setErrorStatus(-1);
                }
            }

//     Старый код!       final GetStateResult result = DeviceMeteringAsyncPort.callImportMeteringDeviceValues(request, answerProcessing);
//
//            if (result != null && result.getImportResult() != null && result.getImportResult().size() > 0){ // если есть ответ от ГИС ЖКХ
//                for (CommonResultType resultType : result.getImportResult()) { // смотрим каждый элемент
//                    if (resultType.getError().size() > 0) { // если есть ошибки
//                        for (CommonResultType.Error error : resultType.getError()) {
//                            showErrorMeteringDevices(resultType.getTransportGUID(), error.getErrorCode(), error.getDescription());
//                        }
//                    } else {
//                        answerProcessing.sendMessageToClient("");
//                        answerProcessing.sendMessageToClient("Обновлены показания прибора учёта. " +
//                                "идентификатор ПУ: " + resultType.getGUID());
//                    }
//                }
//            } else {
//                setErrorStatus(-1);
//            }
//
        }else {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendMessageToClient("Не найдены показания приборов учёта для обновления.");
        }
    }

    /**
     * Метод, формирует и выводит пользователю информацию об ошибках, которые возвращает ГИС ЖКХ.
     *
     * @param transportGUID транспортный идентификатор.
     * @param errorCode     код ошибки.
     * @param description   описание ошибки.
     */
    private void showErrorMeteringDevices(final String transportGUID, final String errorCode, final String description) {
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("TransportGUID: " + transportGUID);
        answerProcessing.sendMessageToClient("Код ошибки: " + errorCode);
        answerProcessing.sendMessageToClient("Описание ошибки: " + description);
        setErrorStatus(0);
    }

    /**
     * Метод, добвляет в БД данные о показания ПУ.
     *
     * @param valuesObject   объект содержащий данные о показаниях ПУ.
     * @param connectionGrad подключение к БД ГРАДа.
     * @throws SQLException могут возникнуть ошибки во время работы с БД.
     */
    private void setMeteringDeviceValue(final MeteringDeviceValuesObject valuesObject,
                                        final Connection connectionGrad) throws SQLException {

        deviceValuesLocalDAO.setDateMeteringDeviceValues(valuesObject);
        deviceValuesGradDAO.setMeteringDeviceValue(valuesObject, connectionGrad);
    }

    /**
     * Метод, получает ФИАС дома, формирует запрос со всеми типами приборов учёта, отправляет запрос в ГИС ЖКХ, получает ответ.
     *
     * @return статус состояния, 1 - всё прошло успешно, 0 - возникли ошибки, но метод выполнен, -1 - возникли ошибки метод прерван.
     * @throws SQLException могут возникнуть ошибки во время работы с БД.
     */
    private GetStateResult getExportMeteringDeviceHistory(final String fias) throws SQLException, PreGISException {

        final ReferenceNSIDAO nsidao = new ReferenceNSIDAO();
        final ArrayList<ReferenceItemDataSet> allItems = nsidao.getAllItemsCodeParent("27");
        final ArrayList<NsiRef> nsiList = new ArrayList<>();

        for (ReferenceItemDataSet item : allItems) {
            final NsiRef nsiRef = new NsiRef();
            nsiRef.setCode(item.getCode());
            nsiRef.setGUID(item.getGuid());
            nsiRef.setName(item.getName());
            nsiList.add(nsiRef);
        }

        if (nsiList.isEmpty()){
            answerProcessing.sendErrorToClientNotException("Не загружен справочник типов ПУ, обмен невозможен");
            return null;
        }

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        calendar.add(Calendar.MONTH, -2);

//        Формируем объект для запроса
        final ExportMeteringDeviceHistoryRequest request = new ExportMeteringDeviceHistoryRequest();
        request.setFIASHouseGuid(fias); // b58c5da4-8d62-438f-b11e-d28103220952
        request.getMeteringDeviceType().addAll(nsiList);
        request.setInputDateFrom(OtherFormat.getDateForXML(calendar.getTime()));

        //final ExportMeteringDeviceHistory deviceHistory = new ExportMeteringDeviceHistory(answerProcessing);

        //return deviceHistory.getExportMeteringHistoryResult(request);
        return DeviceMeteringAsyncPort.callExportMeteringDeviceHistory(request, answerProcessing);
    }

    /**
     * Метод, разбирает ответ, находит нужные показания и заносит их в Map,
     * для дальнейшего сравнения с показаниями ПУ в БД ГРАД.
     *
     * @param result полученный из ГИС ЖКХ ответ.
     */
    private void parseMeteringDeviceValuesFromGISJKH(final GetStateResult result) throws SQLException, PreGISException {

        for (ExportMeteringDeviceHistoryResultType resultType : result.getExportMeteringDeviceHistoryResult()) {
            final String rootGUID = resultType.getMeteringDeviceRootGUID();

            if (resultType.getElectricDeviceValue() != null) { // если ПУ по электричеству

                ExportElectricMeteringValueKindType.ControlValue tempControlValue = null;
                ExportElectricMeteringValueKindType.CurrentValue tempCurrentValue = null;

                if (resultType.getElectricDeviceValue().getValues().getControlValue() != null) { // если есть контрольные показания

                    for (ExportElectricMeteringValueKindType.ControlValue controlValue :
                            resultType.getElectricDeviceValue().getValues().getControlValue()) {

                        if (tempControlValue == null) tempControlValue = controlValue;
                        else {
                            if (controlValue.getDateValue().toGregorianCalendar().getTime().getTime() >=
                                    tempControlValue.getDateValue().toGregorianCalendar().getTime().getTime() &&
                                    controlValue.getMeteringValueT1().compareTo(tempControlValue.getMeteringValueT1()) == 1) {
                                tempControlValue = controlValue;
                            }
                        }
                    } // for
                } // if
                if (resultType.getElectricDeviceValue().getValues().getCurrentValue() != null) { // если есть текущее показания

                    for (ExportElectricMeteringValueKindType.CurrentValue currentValue :
                            resultType.getElectricDeviceValue().getValues().getCurrentValue()) {

                        if (tempCurrentValue == null) tempCurrentValue = currentValue;
                        else {
                            if (currentValue.getDateValue().toGregorianCalendar().getTime().getTime() >=
                                    tempCurrentValue.getDateValue().toGregorianCalendar().getTime().getTime() &&
                                    currentValue.getMeteringValueT1().compareTo(tempCurrentValue.getMeteringValueT1()) == 1) {
                                tempCurrentValue = currentValue;
                            }
                        }
                    } // for
                } // if
                if (tempControlValue != null && tempCurrentValue != null) {
                    if (tempControlValue.getDateValue().toGregorianCalendar().getTime().getTime() >
                            tempCurrentValue.getDateValue().toGregorianCalendar().getTime().getTime()) {
                        addMeteringDeviceValue(
                                rootGUID,
                                tempControlValue,
                                tempControlValue.getDateValue().toGregorianCalendar().getTime());
                    } else {
                        addMeteringDeviceValue(
                                rootGUID,
                                tempCurrentValue,
                                tempCurrentValue.getDateValue().toGregorianCalendar().getTime());
                    }
                } else if (tempCurrentValue != null) {
                    addMeteringDeviceValue(
                            rootGUID,
                            tempCurrentValue,
                            tempCurrentValue.getDateValue().toGregorianCalendar().getTime());
                } else if (tempControlValue != null) {
                    addMeteringDeviceValue(
                            rootGUID,
                            tempControlValue,
                            tempControlValue.getDateValue().toGregorianCalendar().getTime());
                }
            } else if (resultType.getOneRateDeviceValue() != null) { // если однотарифный ПУ

                ExportOneRateMeteringValueKindType.ControlValue tempControlValueOneRate = null;
                ExportOneRateMeteringValueKindType.CurrentValue tempCurrentValueOneRate = null;

                if (resultType.getOneRateDeviceValue().getValues().getControlValue() != null) { // если есть контрольные показания

                    for (ExportOneRateMeteringValueKindType.ControlValue controlValueOneRate :
                            resultType.getOneRateDeviceValue().getValues().getControlValue()) {

                        if (tempControlValueOneRate == null) {
                            tempControlValueOneRate = controlValueOneRate;

                        } else if (controlValueOneRate.getDateValue().toGregorianCalendar().getTime().getTime() >=
                                tempControlValueOneRate.getDateValue().toGregorianCalendar().getTime().getTime() &&
                                controlValueOneRate.getMeteringValue().compareTo(tempControlValueOneRate.getMeteringValue()) == 1) {

                            tempControlValueOneRate = controlValueOneRate;
                        }
                    } // for
                } // if контрольные показания

                if (resultType.getOneRateDeviceValue().getValues().getCurrentValue() != null) { // если есть текущее показания

                    for (ExportOneRateMeteringValueKindType.CurrentValue currentValueOneRate :
                            resultType.getOneRateDeviceValue().getValues().getCurrentValue()) {

                        if (tempCurrentValueOneRate == null) {
                            tempCurrentValueOneRate = currentValueOneRate;

                        } else if (currentValueOneRate.getDateValue().toGregorianCalendar().getTime().getTime() >=
                                tempCurrentValueOneRate.getDateValue().toGregorianCalendar().getTime().getTime() &&
                                currentValueOneRate.getMeteringValue().compareTo(tempCurrentValueOneRate.getMeteringValue()) == 1) {

                            tempCurrentValueOneRate = currentValueOneRate;
                        }
                    } // for
                } // if текущее показания
                if (tempControlValueOneRate != null && tempCurrentValueOneRate != null) {
                    if (tempControlValueOneRate.getDateValue().toGregorianCalendar().getTime().getTime() >
                            tempCurrentValueOneRate.getDateValue().toGregorianCalendar().getTime().getTime()) {
                        addMeteringDeviceValue(
                                rootGUID,
                                tempControlValueOneRate,
                                tempControlValueOneRate.getDateValue().toGregorianCalendar().getTime());
                    } else {
                        addMeteringDeviceValue(
                                rootGUID,
                                tempCurrentValueOneRate,
                                tempCurrentValueOneRate.getDateValue().toGregorianCalendar().getTime());
                    }
                } else if (tempCurrentValueOneRate != null) {
                    addMeteringDeviceValue(
                            rootGUID,
                            tempCurrentValueOneRate,
                            tempCurrentValueOneRate.getDateValue().toGregorianCalendar().getTime());
                } else if (tempControlValueOneRate != null) {
                    addMeteringDeviceValue(
                            rootGUID,
                            tempControlValueOneRate,
                            tempControlValueOneRate.getDateValue().toGregorianCalendar().getTime());
                }
            }// if однотарифный ПУ
        } // for
    }


    /**
     * Метод, принимает объект из ГИС ЖКХ с текущими показаниями, сравнивает с текущими показаниями в БД,
     * добавляет в Map, при необходимости обновляет в БД или сформирует новые показания для ГИС ЖКХ.
     *
     * @param rootGUID идентификатор ПУ в ГИС ЖКХ.
     * @param value    показания по электричеству.
     */
    private void addMeteringDeviceValue(final String rootGUID,
                                        final ElectricMeteringValueType value,
                                        final Date valueDate) throws SQLException, PreGISException {

        tempMeteringDevicesValue.put(rootGUID, new MeteringDeviceValuesObject(
                rootGUID,
                value.getMeteringValueT1(),
                value.getMeteringValueT2(),
                value.getMeteringValueT3(),
                valueDate,
                referenceNSI.getNsiRef("2", "Электрическая энергия")));
        deviceValuesLocalDAO.setDateMeteringDeviceValues(rootGUID, valueDate);
//        LOGGER.debug("Добавлены показания ПУ для сравнения: " + rootGUID + " показания: " + value.getMeteringValueT1());
    }

    /**
     * Метод, принимает объект из ГИС ЖКХ с текущими показаниями, сравнивает с текущими показаниями в БД,
     * добавляет в Map, при необходимости обновляет в БД или сформирует новые показания для ГИС ЖКХ.
     *
     * @param rootGUID идентификатор ПУ в ГИС ЖКХ.
     * @param value    показания по однотарифному ПУ.
     */
    private void addMeteringDeviceValue(final String rootGUID,
                                        final OneRateMeteringValueType value,
                                        final Date valueDate) throws SQLException {

        tempMeteringDevicesValue.put(rootGUID, new MeteringDeviceValuesObject(
                rootGUID,
                value.getMeteringValue(),
                valueDate,
                value.getMunicipalResource()));
        deviceValuesLocalDAO.setDateMeteringDeviceValues(rootGUID, valueDate);
//        LOGGER.debug("Добавлены показания ПУ для сравнения: " + rootGUID + " показания: " + value.getMeteringValue());
    }

    public int getAddedValueToGISJKH() {
        return addedValueToGISJKH;
    }

    public int getAddedValueToGrad() {
        return addedValueToGrad;
    }

    public int getErrorStatus() {
        return errorStatus;
    }

    private void setErrorStatus(final int errorStatus) {

        if (errorStatus < this.errorStatus)
            this.errorStatus = errorStatus; // если возникают ошибки из всех домов, храним их в переменой и возвращаем.
        this.errorStatus = errorStatus;
    }
}
