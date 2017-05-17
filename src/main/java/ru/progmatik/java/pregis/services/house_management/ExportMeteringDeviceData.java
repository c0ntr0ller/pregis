package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.AckRequest;
import ru.gosuslugi.dom.schema.integration.base.ErrorMessageType;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.house_management.ExportMeteringDeviceDataRequest;
import ru.gosuslugi.dom.schema.integration.house_management.ExportMeteringDeviceDataResultType;
import ru.gosuslugi.dom.schema.integration.house_management.GetStateResult;
import ru.gosuslugi.dom.schema.integration.house_management_service_async.HouseManagementPortsTypeAsync;
import ru.gosuslugi.dom.schema.integration.house_management_service_async.HouseManagementServiceAsync;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;
import ru.progmatik.java.pregis.other.UrlLoader;

import javax.xml.ws.Holder;
import java.sql.SQLException;
import java.util.List;

/**
 * Класс, получить перечень ПУ.
 */
public class ExportMeteringDeviceData {

    private static final Logger LOGGER = Logger.getLogger(ExportMeteringDeviceData.class);
    private static final String NAME_METHOD = "exportMeteringDeviceData";

    private final HouseManagementPortsTypeAsync port;
    private final AnswerProcessing answerProcessing;

    public ExportMeteringDeviceData(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;

        HouseManagementServiceAsync service = UrlLoader.instance().getUrlMap().get("homeManagementAsync") == null ? new HouseManagementServiceAsync()
                : new HouseManagementServiceAsync(UrlLoader.instance().getUrlMap().get("homeManagementAsync"));

        port = service.getHouseManagementPortAsync();
        OtherFormat.setPortSettings(service, port);
    }

    /**
     * Метод, получает данные о ПУ из ГИС ЖКХ.
     */
    public List<ExportMeteringDeviceDataResultType> callExportMeteringDeviceData(String fias) throws SQLException {

        answerProcessing.sendMessageToClient("::clearLabelText");
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        ResultHeader resultHeader = null;
        Holder<ResultHeader> headerHolder = new Holder<>();
        ErrorMessageType resultErrorMessage = null;

        GetStateResult stateResult = null;
        List<ExportMeteringDeviceDataResultType> result = null;
        HouseManagementAsyncGetResult houseManagementAsyncGetResult = null;
        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            AckRequest ackRequest = port.exportMeteringDeviceData(getExportMeteringDeviceDataRequest(fias), requestHeader, headerHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);

            if (ackRequest != null) {
                houseManagementAsyncGetResult = new HouseManagementAsyncGetResult(ackRequest, NAME_METHOD, answerProcessing, port);

                stateResult = (GetStateResult) houseManagementAsyncGetResult.getRequestResult();

                if (stateResult != null) {
                    result = stateResult.getExportMeteringDeviceDataResult();
                    resultHeader = houseManagementAsyncGetResult.getHeaderHolder().value;
                    resultErrorMessage = stateResult.getErrorMessage();
                }
            }
        } catch (ru.gosuslugi.dom.schema.integration.house_management_service_async.Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            return null;
        }

        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, resultHeader, resultErrorMessage, LOGGER);
        if (stateResult != null && stateResult.getErrorMessage() != null) return null;
        return result;
    }

    /**
     * Метод формирует заголовок для запроса.
     * @return запрос.
     */
    private ExportMeteringDeviceDataRequest getExportMeteringDeviceDataRequest(String fias) {

        ExportMeteringDeviceDataRequest request = new ExportMeteringDeviceDataRequest();
        request.setIsCurrentOrganization(true);
        request.setFIASHouseGuid(fias);
        request.setSearchArchived(true);
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        return request;
    }
}
