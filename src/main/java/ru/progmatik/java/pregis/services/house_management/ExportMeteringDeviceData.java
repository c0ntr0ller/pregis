package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.house_management.ExportMeteringDeviceDataRequest;
import ru.gosuslugi.dom.schema.integration.services.house_management.ExportMeteringDeviceDataResult;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.Fault;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.HouseManagementPortsType;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.HouseManagementService;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;

import javax.xml.ws.Holder;
import java.sql.SQLException;

/**
 * Класс, получить перечень ПУ.
 */
public class ExportMeteringDeviceData {

    private static final Logger LOGGER = Logger.getLogger(ExportMeteringDeviceData.class);
    private static final String NAME_METHOD = "exportMeteringDeviceData";

    private final HouseManagementService service = new HouseManagementService();
    private final HouseManagementPortsType port = service.getHouseManagementPort();
    private final AnswerProcessing answerProcessing;

    public ExportMeteringDeviceData(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
        OtherFormat.setPortSettings(service, port);
    }

    /**
     * Метод, получает данные о ПУ из ГИС ЖКХ.
     */
    public ExportMeteringDeviceDataResult callExportMeteringDeviceData(String fias) throws SQLException {

        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        Holder<ResultHeader> headerHolder = new Holder<>();

        ExportMeteringDeviceDataResult result;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            result = port.exportMeteringDeviceData(getExportMeteringDeviceDataRequest(fias), requestHeader, headerHolder);
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
     * Метод формирует заголовок для запроса.
     * @return запрос.
     */
    private ExportMeteringDeviceDataRequest getExportMeteringDeviceDataRequest(String fias) {

        ExportMeteringDeviceDataRequest request = new ExportMeteringDeviceDataRequest();
        request.setFIASHouseGuid(fias);
        request.setId(OtherFormat.getId());

        return request;
    }
}
