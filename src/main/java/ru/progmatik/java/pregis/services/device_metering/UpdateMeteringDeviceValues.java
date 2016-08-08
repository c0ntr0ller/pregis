package ru.progmatik.java.pregis.services.device_metering;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.NsiRef;
import ru.gosuslugi.dom.schema.integration.services.device_metering.*;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.connectiondb.grad.devices.MeteringDeviceValuesGradDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemDataSet;
import ru.progmatik.java.pregis.connectiondb.localdb.meteringdevice.MeteringDeviceValuesLocalDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSI;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSIDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

/**
 * Класс, обрабатывает показания ПУ.
 */
public class UpdateMeteringDeviceValues {

    private static final Logger LOGGER = Logger.getLogger(UpdateMeteringDeviceValues.class);
    private final AnswerProcessing answerProcessing;
    private final ReferenceNSI referenceNSI;
    private MeteringDeviceValuesGradDAO deviceValuesGradDAO;
    private MeteringDeviceValuesLocalDAO deviceValuesLocalDAO;
    private HashMap<String, MeteringDeviceValuesObject> tempMeteringDevicesValue; // хранит показания всех ПУ полученных из ГИС ЖКХ
    private int errorStatus;

    public UpdateMeteringDeviceValues(AnswerProcessing answerProcessing) throws SQLException {

        this.answerProcessing = answerProcessing;
        deviceValuesLocalDAO = new MeteringDeviceValuesLocalDAO();
        referenceNSI = new ReferenceNSI(answerProcessing);
    }

    public int updateAllMeteringDeviceValues() throws SQLException, PreGISException, ParseException {

        errorStatus = 1;

        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {
            HouseGRADDAO houseGRADDAO = new HouseGRADDAO();
            LinkedHashMap<String, Integer> houseAddedGisJkh = houseGRADDAO.getHouseAddedGisJkh(connectionGRAD);
            for (Map.Entry<String, Integer> entry : houseAddedGisJkh.entrySet()) {
                tempMeteringDevicesValue = new HashMap<>();
                updateMeteringDeviceValues(entry.getKey(), entry.getValue(), connectionGRAD);
            }
        }

        return errorStatus;
    }

    /**
     * Метод, разбирает ответ, находит нужные показания и заносит их если они отличаются от показаний в ГРАДе.
     * Если ГИС ЖКХ вернул ошибку или ничего, обработка прекращается.
     *
     */
    private void updateMeteringDeviceValues(String fias, int houseId, Connection connectionGrad) throws SQLException, ParseException, PreGISException {

        ExportMeteringDeviceHistoryResult result = getExportMeteringDeviceHistory(fias);
        if (result == null) {
            answerProcessing.sendErrorToClientNotException("Не удалось получить показания ПУ по дому с ФИАС: " + fias);
        } else if (result.getErrorMessage() != null){
            answerProcessing.sendErrorToClientNotException("Не удалось получить показания ПУ по дому с ФИАС: " + fias);
            answerProcessing.sendErrorToClientNotException("Сообщение от сервера ГИС ЖКХ: ");
            answerProcessing.sendErrorToClientNotException("Код ошибки: " + result.getErrorMessage().getErrorCode());
            answerProcessing.sendErrorToClientNotException("Описание ошибки: " + result.getErrorMessage().getDescription());
        } else {
            parseMeteringDeviceValuesFromGISJKH(result);
            deviceValuesGradDAO = new MeteringDeviceValuesGradDAO(answerProcessing);
            HashMap<String, MeteringDeviceValuesObject> deviceValuesFromGrad = deviceValuesGradDAO.getMeteringDeviceValueFromGrad(houseId, connectionGrad);
            compareMeteringDevicesValue(fias, deviceValuesFromGrad, tempMeteringDevicesValue, connectionGrad);
        }
    }

    /**
     * Метод, сравнивает показания ПУ полученных из ГИС ЖКХ и БД ГРАД.
     * В зависимости от расхождений добавляет в БД показания или формирует запрос для добавления показаний в ГИС ЖКХ.
     * @param fias код дома по ФИАС
     * @param meteringDevicesValuesFromGrad показания ПУ полученные из БД ГРАД.
     * @param meteringDevicesValueFromGISJKH показания ПУ полученные из ГИС ЖКХ.
     */
    private void compareMeteringDevicesValue(String fias,
                                             HashMap<String, MeteringDeviceValuesObject> meteringDevicesValuesFromGrad,
                                             HashMap<String, MeteringDeviceValuesObject> meteringDevicesValueFromGISJKH,
                                             Connection connectionGrad) throws SQLException {

        try (Connection connectionLocalDB = ConnectionDB.instance().getConnectionDB()) {

            ImportMeteringDeviceValuesRequest request = new ImportMeteringDeviceValuesRequest(); // для отправки в ГИС ЖКХ

            for (Map.Entry<String, MeteringDeviceValuesObject> entry : meteringDevicesValuesFromGrad.entrySet()) {
                MeteringDeviceValuesObject valuesObject = meteringDevicesValueFromGISJKH.get(entry.getKey());

                ImportMeteringDeviceValuesRequest.MeteringDevicesValues devicesValues =
                        new ImportMeteringDeviceValuesRequest.MeteringDevicesValues();

                if (valuesObject == null || valuesObject.getMeteringValue().compareTo(entry.getValue().getMeteringValue()) < 0) { // если в ГИС ЖКХ не найдены показания ПУ или показания меньше, добавляем из ГРАДа в ГИС ЖКХ.

                    // TODO
                    devicesValues.setMeteringDeviceRootGUID(entry.getValue().getMeteringDeviceRootGUID());

                    if (entry.getValue().getNsiRef().getName().equalsIgnoreCase("Электрическая энергия")) { // Если счетчик по электричеству

                        ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue electricDeviceValue =
                                new ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue();

                        ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue.CurrentValue currentValue
                                = new ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue.CurrentValue();

                        currentValue.setTransportGUID(OtherFormat.getRandomGUID());
                        currentValue.setMeteringValueT1(entry.getValue().getMeteringValue());
                        currentValue.setMeteringValueT2(entry.getValue().getMeteringValueTwo());
                        currentValue.setMeteringValueT3(entry.getValue().getMeteringValueThree());

                        electricDeviceValue.getCurrentValue().add(currentValue);
                        devicesValues.setElectricDeviceValue(electricDeviceValue);

                    } else { // остальные ПУ

                        ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue deviceValue =
                                new ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue();

                        ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue.CurrentValue currentValue
                                = new ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue.CurrentValue();

                        currentValue.setTransportGUID(OtherFormat.getRandomGUID());
                        currentValue.setMeteringValue(entry.getValue().getMeteringValue());
                        currentValue.setMunicipalResource(entry.getValue().getNsiRef());

                        deviceValue.getCurrentValue().add(currentValue);
                        devicesValues.setOneRateDeviceValue(deviceValue);
                    }

                    request.setFIASHouseGuid(fias);
                    request.getMeteringDevicesValues().add(devicesValues); // добавим устройство для отправки в ГИС ЖКХ.

                } else if (valuesObject.getMeteringValue().compareTo(entry.getValue().getMeteringValue()) > 0) {

                    setMeteringDeviceValue(valuesObject, connectionGrad, connectionLocalDB);
                }
            }
        }
    }

    /**
     * Метод, добвляет в БД данные о показания ПУ.
     * @param valuesObject объект содержащий данные о показаниях ПУ.
     * @param connectionGrad подключение к БД ГРАДа.
     * @param connectionLocalDB подключенте к локальной БД.
     * @throws SQLException
     */
    private void setMeteringDeviceValue(MeteringDeviceValuesObject valuesObject,
                                        Connection connectionGrad,
                                        Connection connectionLocalDB) throws SQLException {

        deviceValuesLocalDAO.setDateMeteringDeviceValues(valuesObject, connectionLocalDB);
        deviceValuesGradDAO.setMeteringDeviceValue(valuesObject, connectionGrad);

    }

    /**
     * Метод, получает ФИАС дома, формирует запрос со всеми типами приборов учёта, отправляет запрос в ГИС ЖКХ, получает ответ.
     *
     * @return статус состояния, 1 - всё прошло успешно, 0 - возникли ошибки, но метод выполнен, -1 - возникли ошибки метод прерван.
     * @throws SQLException
     */
    private ExportMeteringDeviceHistoryResult getExportMeteringDeviceHistory(String fias) throws SQLException {

        ReferenceNSIDAO nsidao = new ReferenceNSIDAO();
        ArrayList<ReferenceItemDataSet> allItems = nsidao.getAllItemsCodeParent("27");
        ArrayList<ru.gosuslugi.dom.schema.integration.base.NsiRef> nsiList = new ArrayList<>();

        for (ReferenceItemDataSet item : allItems) {
            ru.gosuslugi.dom.schema.integration.base.NsiRef nsiRef = new NsiRef();
            nsiRef.setCode(item.getCode());
            nsiRef.setGUID(item.getGuid());
            nsiRef.setName(item.getName());
            nsiList.add(nsiRef);
        }

//        Формируем объект для запроса
        ExportMeteringDeviceHistoryRequest request = new ExportMeteringDeviceHistoryRequest();
        request.setFIASHouseGuid(fias); // b58c5da4-8d62-438f-b11e-d28103220952
        request.getMeteringDeviceType().addAll(nsiList);

        ExportMeteringDeviceHistory deviceHistory = new ExportMeteringDeviceHistory(answerProcessing);
        ExportMeteringDeviceHistoryResult result = deviceHistory.getExportMeteringHistoryResult(request);

        if (result == null) {
            setErrorStatus(-1);
            return null;
        } else if (result.getErrorMessage() != null) {
            setErrorStatus(0);
            return null;
        } else {
            return result;
        }
    }

    /**
     * Метод, разбирает ответ, находит нужные показания и заносит их в Map,
     * для дальнейшего сравнения с показаниями ПУ в БД ГРАД.
     *
     * @param result полученный из ГИС ЖКХ ответ.
     */
    private void parseMeteringDeviceValuesFromGISJKH(ExportMeteringDeviceHistoryResult result) throws SQLException, PreGISException {

        for (ExportMeteringDeviceHistoryResultType resultType : result.getExportMeteringDeviceHistoryResult()) {
            String rootGUID = resultType.getMeteringDeviceRootGUID();

            if (resultType.getElectricDeviceValue() != null) { // если ПУ по электричеству

                ElectricMeteringValueKindType.ControlValue tempControlValue = null;
                ElectricMeteringValueKindType.CurrentValue tempCurrentValue = null;

                if (resultType.getElectricDeviceValue().getValues().getControlValue() != null) { // если есть контрольные показания

                    for (ElectricMeteringValueKindType.ControlValue controlValue :
                            resultType.getElectricDeviceValue().getValues().getControlValue()) {

                        if (tempControlValue == null) tempControlValue = controlValue;
                        else {
                            if (controlValue.getDateValue().toGregorianCalendar().getTime().getTime() >
                                    tempControlValue.getDateValue().toGregorianCalendar().getTime().getTime()) {
                                tempControlValue = controlValue;
                            }
                        }
                    } // for
                } // if
                if (resultType.getElectricDeviceValue().getValues().getCurrentValue() != null) { // если есть текущее показания

                    for (ElectricMeteringValueKindType.CurrentValue currentValue :
                            resultType.getElectricDeviceValue().getValues().getCurrentValue()) {

                        if (tempCurrentValue == null) tempCurrentValue = currentValue;
                        else {
                            if (currentValue.getDateValue().toGregorianCalendar().getTime().getTime() >
                                    tempCurrentValue.getDateValue().toGregorianCalendar().getTime().getTime()) {
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
                }
            } else if (resultType.getOneRateDeviceValue() != null) { // если однотарифный ПУ

                OneRateMeteringValueKindType.ControlValue tempControlValueOneRate = null;
                OneRateMeteringValueKindType.CurrentValue tempCurrentValueOneRate = null;

                if (resultType.getOneRateDeviceValue().getValues().getControlValue() != null) { // если есть контрольные показания

                    for (OneRateMeteringValueKindType.ControlValue controlValueOneRate :
                            resultType.getOneRateDeviceValue().getValues().getControlValue()) {

                        if (tempControlValueOneRate == null) tempControlValueOneRate = controlValueOneRate;
                        else {
                            if (controlValueOneRate.getDateValue().toGregorianCalendar().getTime().getTime() >
                                    tempControlValueOneRate.getDateValue().toGregorianCalendar().getTime().getTime()) {
                                tempControlValueOneRate = controlValueOneRate;
                            }
                        }
                    } // for
                } // if контрольные показания

                if (resultType.getOneRateDeviceValue().getValues().getCurrentValue() != null) { // если есть текущее показания

                    for (OneRateMeteringValueKindType.CurrentValue currentValueOneRate :
                            resultType.getOneRateDeviceValue().getValues().getCurrentValue()) {

                        if (tempCurrentValueOneRate == null) tempCurrentValueOneRate = currentValueOneRate;
                        else {
                            if (currentValueOneRate.getDateValue().toGregorianCalendar().getTime().getTime() >
                                    tempCurrentValueOneRate.getDateValue().toGregorianCalendar().getTime().getTime()) {
                                tempCurrentValueOneRate = currentValueOneRate;
                            }
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
                }
            }// if однотарифный ПУ
        } // for
    }

    /**
     * Метод, принимает объект из ГИС ЖКХ с текущими показаниями, сравнивает с текущими показаниями в БД,
     * добавляет в Map, при необходимости обновляет в БД или сформирует новые показания для ГИС ЖКХ.
     * @param rootGUID идентификатор ПУ в ГИС ЖКХ.
     * @param value показания по электричеству.
     */
    private void addMeteringDeviceValue(String rootGUID,
                                        ru.gosuslugi.dom.schema.integration.base.ElectricMeteringValueType value,
                                        Date valueDate) throws SQLException, PreGISException {

        tempMeteringDevicesValue.put(rootGUID, new MeteringDeviceValuesObject(
                rootGUID,
                value.getMeteringValueT1(),
                value.getMeteringValueT2(),
                value.getMeteringValueT3(),
                valueDate,
                referenceNSI.getNsiRef("2", "Электрическая энергия")));
    }

    /**
     * Метод, принимает объект из ГИС ЖКХ с текущими показаниями, сравнивает с текущими показаниями в БД,
     * добавляет в Map, при необходимости обновляет в БД или сформирует новые показания для ГИС ЖКХ.
     * @param rootGUID идентификатор ПУ в ГИС ЖКХ.
     * @param value показания по однотарифному ПУ.
     */
    private void addMeteringDeviceValue(String rootGUID,
                                        ru.gosuslugi.dom.schema.integration.base.OneRateMeteringValueType value,
                                        Date valueDate) {

        tempMeteringDevicesValue.put(rootGUID, new MeteringDeviceValuesObject(
                rootGUID,
                value.getMeteringValue(),
                valueDate,
                value.getMunicipalResource()));
    }

    public int getErrorStatus() {
        return errorStatus;
    }

    private void setErrorStatus(int errorStatus) {

        if (errorStatus < this.errorStatus) this.errorStatus = errorStatus; // если возникают ошибки из всех домов, храним их в переменой и возвращаем.
        this.errorStatus = errorStatus;
    }
}
