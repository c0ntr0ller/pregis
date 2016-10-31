package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.house_management.ImportMeteringDeviceDataRequest;
import ru.gosuslugi.dom.schema.integration.house_management.ImportResult;
import ru.gosuslugi.dom.schema.integration.house_management_service.Fault;
import ru.gosuslugi.dom.schema.integration.house_management_service.HouseManagementPortsType;
import ru.gosuslugi.dom.schema.integration.house_management_service.HouseManagementService;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;
import ru.progmatik.java.pregis.other.UrlLoader;

import javax.xml.ws.Holder;
import java.sql.SQLException;

/**
 * Класс, передаёт данные о ПУ в ГИС ЖКХ.
 */
public final class ImportMeteringDeviceData {

    private static final Logger LOGGER = Logger.getLogger(ImportMeteringDeviceData.class);
    private static final String NAME_METHOD = "importMeteringDeviceData";

    private final HouseManagementPortsType port;
    private final AnswerProcessing answerProcessing;

    public ImportMeteringDeviceData(final AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;

        final HouseManagementService service = UrlLoader.instance().getUrlMap().get("homeManagement") == null ? new HouseManagementService()
                : new HouseManagementService(UrlLoader.instance().getUrlMap().get("homeManagement"));

        port = service.getHouseManagementPort();
        OtherFormat.setPortSettings(service, port);
    }

    public ImportResult callImportMeteringDeviceData(final String fias,
                                                     final java.util.List<ImportMeteringDeviceDataRequest.MeteringDevice> meteringDeviceList) throws SQLException {

        final Holder<ResultHeader> resultHolder = new Holder<>();
        final RequestHeader requestHeader = OtherFormat.getRequestHeader();
        final ImportResult result;

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
        final ImportMeteringDeviceDataRequest request = new ImportMeteringDeviceDataRequest();

        request.setFIASHouseGuid(fias);
        request.getMeteringDevice().addAll(meteringDeviceList);

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            result = port.importMeteringDeviceData(getImportMeteringDeviceDataRequest(request), requestHeader, resultHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            return null;
        }
        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, resultHolder.value, result.getErrorMessage(), LOGGER);
        if (result.getErrorMessage() != null) return null;
        return result;
    }

    private ImportMeteringDeviceDataRequest getImportMeteringDeviceDataRequest(final ImportMeteringDeviceDataRequest request) {

        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        return request;
    }
}
