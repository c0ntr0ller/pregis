package ru.progmatik.java.pregis.services.device_metering;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.AckRequest;
import ru.gosuslugi.dom.schema.integration.base.ErrorMessageType;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.device_metering.ExportMeteringDeviceHistoryRequest;
import ru.gosuslugi.dom.schema.integration.device_metering.GetStateResult;
import ru.gosuslugi.dom.schema.integration.device_metering.ImportMeteringDeviceValuesRequest;
import ru.gosuslugi.dom.schema.integration.device_metering_service_async.DeviceMeteringPortTypesAsync;
import ru.gosuslugi.dom.schema.integration.device_metering_service_async.DeviceMeteringServiceAsync;
import ru.gosuslugi.dom.schema.integration.device_metering_service_async.Fault;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;
import ru.progmatik.java.pregis.other.UrlLoader;

import javax.xml.ws.Holder;
import java.sql.SQLException;
import java.util.List;

public class DeviceMeteringAsyncPort {
    private static final Logger LOGGER = Logger.getLogger(DeviceMeteringAsyncPort.class);
    private final String NAME_METHOD;

    private final DeviceMeteringPortTypesAsync port;
    private final AnswerProcessing answerProcessing;

    private DeviceMeteringAsyncPort(AnswerProcessing answerProcessing, String methodName){
        this.answerProcessing = answerProcessing;
        this.NAME_METHOD = methodName;
        DeviceMeteringServiceAsync service = UrlLoader.instance().getUrlMap().get("deviceMeteringAsync") == null ? new DeviceMeteringServiceAsync()
                : new DeviceMeteringServiceAsync(UrlLoader.instance().getUrlMap().get("deviceMeteringAsync"));
        port = service.getDeviceMeteringPortAsync();
        OtherFormat.setPortSettings(service, port);
    }

    public static GetStateResult callExportMeteringDeviceHistory(ExportMeteringDeviceHistoryRequest request, AnswerProcessing answerProcessing) throws SQLException, PreGISException {
        DeviceMeteringAsyncPort deviceMeteringAsyncPort = new DeviceMeteringAsyncPort(answerProcessing, "exportMeteringDeviceHistory");
        return deviceMeteringAsyncPort.interactDeviceMetering(setSignIdExportMeteringHistoryRequest(request));
    }

    public static GetStateResult callImportMeteringDeviceValues(ImportMeteringDeviceValuesRequest request, AnswerProcessing answerProcessing) throws SQLException, PreGISException {
        DeviceMeteringAsyncPort deviceMeteringAsyncPort = new DeviceMeteringAsyncPort(answerProcessing, "importMeteringDeviceValues");
        return deviceMeteringAsyncPort.interactDeviceMetering(setSignIdImportMeteringHistoryRequest(request));
    }


    public static GetStateResult callImportMeteringDeviceValues(String fias, List<ImportMeteringDeviceValuesRequest.MeteringDevicesValues> devicesValuesList, AnswerProcessing answerProcessing) throws SQLException, PreGISException {
        DeviceMeteringAsyncPort deviceMeteringAsyncPort = new DeviceMeteringAsyncPort(answerProcessing, "importMeteringDeviceValues");
        return deviceMeteringAsyncPort.interactDeviceMetering(createImportMeteringHistoryRequest(fias, devicesValuesList));
    }

    private GetStateResult interactDeviceMetering(Object requestObject) throws SQLException, PreGISException {
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        ResultHeader resultHeader;
        Holder<ResultHeader> headerHolder = new Holder<>();
        ErrorMessageType resultErrorMessage;

        GetStateResult result;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            AckRequest ackRequest = null;

            switch (requestObject.getClass().getSimpleName()) {
                case "ExportMeteringDeviceHistoryRequest":
                    ackRequest = port.exportMeteringDeviceHistory((ExportMeteringDeviceHistoryRequest)requestObject, requestHeader, headerHolder);
                    break;
                case "ImportMeteringDeviceValuesRequest":
                    ackRequest = port.importMeteringDeviceValues((ImportMeteringDeviceValuesRequest)requestObject, requestHeader, headerHolder);
                    break;
                default:
                    answerProcessing.sendMessageToClient("Не определен метод экспорта/импорта " + NAME_METHOD);
            }

            if (ackRequest != null) {
                // сохраняем запрос
                answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, null, null, LOGGER);

                answerProcessing.sendMessageToClient(TextForLog.REQUEST_SENDED);

                DeviceMeteringAsyncResultWaiter deviceMeteringAsyncResultWaiter = new DeviceMeteringAsyncResultWaiter(ackRequest, NAME_METHOD, answerProcessing, port);

                result = deviceMeteringAsyncResultWaiter.getRequestResult();
                resultHeader = deviceMeteringAsyncResultWaiter.getHeaderHolder().value;
            }else{
                return null;
            }
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            return null;
        }

        if (result == null) {
            throw new PreGISException("Не удалось получить данные от сервера ГИС ЖКХ");
        }else{
            resultErrorMessage = result.getErrorMessage();
        }

        if (resultErrorMessage != null) {
            if(resultErrorMessage.getErrorCode().contains("INT002012")) { // это не ошибка, просто нет данных
                answerProcessing.sendMessageToClient("Нет объектов для экспорта из ГИС ЖКХ");
            }else{
                answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, null, resultHeader, resultErrorMessage, LOGGER);
            }
        } else
            // сохраняем ответ
            answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, null, resultHeader, null, LOGGER);

        return result;
    }

    private static ImportMeteringDeviceValuesRequest setSignIdImportMeteringHistoryRequest(ImportMeteringDeviceValuesRequest request) {
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        return request;
    }

    private static ExportMeteringDeviceHistoryRequest setSignIdExportMeteringHistoryRequest(ExportMeteringDeviceHistoryRequest request) {
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        return request;
    }

    private static ImportMeteringDeviceValuesRequest createImportMeteringHistoryRequest(String fias, List<ImportMeteringDeviceValuesRequest.MeteringDevicesValues> devicesValuesList){
        ImportMeteringDeviceValuesRequest request = new ImportMeteringDeviceValuesRequest();
        request.getMeteringDevicesValues().addAll(devicesValuesList);
        request.setFIASHouseGuid(fias);
        return setSignIdImportMeteringHistoryRequest(request);
    }
}
