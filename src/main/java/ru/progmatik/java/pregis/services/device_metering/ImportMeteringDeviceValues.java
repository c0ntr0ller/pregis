package ru.progmatik.java.pregis.services.device_metering;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.ImportResult;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.device_metering.ImportMeteringDeviceValuesRequest;
import ru.gosuslugi.dom.schema.integration.device_metering_service.DeviceMeteringPortTypes;
import ru.gosuslugi.dom.schema.integration.device_metering_service.DeviceMeteringService;
import ru.gosuslugi.dom.schema.integration.device_metering_service.Fault;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;

import javax.xml.ws.Holder;
import java.sql.SQLException;

/**
 * Класс, отправляет запрос в ГИС ЖКХ по модулю показания ПУ.
 */
public final class ImportMeteringDeviceValues {

    private static final Logger LOGGER = Logger.getLogger(ImportMeteringDeviceValues.class);
    private static final String NAME_METHOD = "importMeteringDeviceValues";

    private final DeviceMeteringService service = new DeviceMeteringService();
    private final DeviceMeteringPortTypes port = service.getDeviceMeteringPort();
    private final AnswerProcessing answerProcessing;

    public ImportMeteringDeviceValues(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
        OtherFormat.setPortSettings(service, port);
    }

    public ImportResult callImportMeteringDeviceValues(ImportMeteringDeviceValuesRequest request) throws SQLException {

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        Holder<ResultHeader> headerHolder = new Holder<>();

        ImportResult result;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            result = port.importMeteringDeviceValues(setSignIdImportMeteringHistoryRequest(request), requestHeader, headerHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            return null;
        }
        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, headerHolder.value, result.getErrorMessage(), LOGGER);
        if (result.getErrorMessage() != null) return null;
        return result;
    }

    /**
     * Метод, добавляет идентификатор для дальнейшего подписания.
     *
     * @param request подготовленный запрос.
     * @return запрос с указанным идентификатором для цифровой подписи.
     */
    private ImportMeteringDeviceValuesRequest setSignIdImportMeteringHistoryRequest(ImportMeteringDeviceValuesRequest request) {
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        return request;
    }
}
