package ru.progmatik.java.pregis.services.device_metering;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.ElectricMeteringValueType;
import ru.gosuslugi.dom.schema.integration.base.NsiRef;
import ru.gosuslugi.dom.schema.integration.base.OneRateMeteringValueType;
import ru.gosuslugi.dom.schema.integration.services.device_metering.*;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemDataSet;
import ru.progmatik.java.pregis.connectiondb.localdb.meteringdevice.MeteringDeviceValuesLocalDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSIDAO;
import ru.progmatik.java.pregis.other.AnswerProcessing;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс, обрабатывает показания ПУ.
 */
public class UpdateMeteringDeviceValues {

    private static final Logger LOGGER = Logger.getLogger(UpdateMeteringDeviceValues.class);
    private final AnswerProcessing answerProcessing;
    private MeteringDeviceValuesLocalDAO deviceValuesLocalDAO;

    public UpdateMeteringDeviceValues(AnswerProcessing answerProcessing) {

        this.answerProcessing = answerProcessing;
        deviceValuesLocalDAO = new MeteringDeviceValuesLocalDAO();
    }

    public int updateAllMeteringDeviceValues() throws SQLException {

        return getExportMeteringDeviceHistory("b58c5da4-8d62-438f-b11e-d28103220952");
    }


    /**
     * Метод, получает ФИАС дома, формирует запрос со всеми типами приборов учёта, отправляет запрос в ГИС ЖКХ, получает ответ.
     *
     * @return статус состояния, 1 - всё прошло успешно, 0 - возникли ошибки, но метод выполнен, -1 - возникли ошибки метод прерван.
     * @throws SQLException
     */
    private int getExportMeteringDeviceHistory(String fias) throws SQLException {

        int errorState = 1;

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
            errorState = -1;
        } else if (result.getErrorMessage() != null) {
            errorState = 0;
        } else {
            updateMeteringDeviceValues(result);
        }
        return errorState;
    }

    /**
     * Метод, разбирает ответ, находит нужные показания и заносит их если они отличаются от показаний в ГРАДе.
     *
     * @param result полученный из ГИС ЖКХ ответ.
     */
    private void updateMeteringDeviceValues(ExportMeteringDeviceHistoryResult result) {


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
                        setMeteringDeviceValue(rootGUID, tempControlValue);
                    } else {
                        setMeteringDeviceValue(rootGUID, tempCurrentValue);
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
                        setMeteringDeviceValue(rootGUID, tempControlValueOneRate);
                    } else {
                        setMeteringDeviceValue(rootGUID, tempCurrentValueOneRate);
                    }
                }
            }// if однотарифный ПУ
        } // for

    }

    /**
     * Метод, принимает объект их ГИС ЖКХ с текущими показаниями и добавляет их в БД.
     * @param rootGUID идентификатор ПУ в ГИС ЖКХ.
     * @param value показания по электричеству.
     */
    private void setMeteringDeviceValue(String rootGUID, ElectricMeteringValueType value) {
//        TODO
    }

    /**
     * Метод, принимает объект их ГИС ЖКХ с текущими показаниями и добавляет их в БД.
     * @param rootGUID идентификатор ПУ в ГИС ЖКХ.
     * @param value показания по однотарифному ПУ.
     */
    private void setMeteringDeviceValue(String rootGUID, OneRateMeteringValueType value) {
//        TODO
    }
}
