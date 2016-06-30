package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.house_management.ImportMeteringDeviceDataRequest;
import ru.gosuslugi.dom.schema.integration.services.house_management.ImportResult;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.Fault;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.HouseManagementPortsType;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.HouseManagementService;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;

import javax.xml.ws.Holder;

/**
 * Класс, передаёт данные о ПУ в ГИС ЖКХ.
 */
public class ImportMeteringDeviceData {

    private static final Logger LOGGER = Logger.getLogger(ImportMeteringDeviceData.class);
    private static final String NAME_METHOD = "importMeteringDeviceData";

    private final HouseManagementService service = new HouseManagementService();
    private final HouseManagementPortsType port = service.getHouseManagementPort();
    private final AnswerProcessing answerProcessing;

    public ImportMeteringDeviceData(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
        OtherFormat.setPortSettings(service, port);
    }

    public ImportResult callImportMeteringDeviceData(String fias, java.util.List<ImportMeteringDeviceDataRequest.MeteringDevice> meteringDeviceList) {

        Holder<ResultHeader> resultHolder = new Holder<>();
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        ImportResult result;

        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
        ImportMeteringDeviceDataRequest request = new ImportMeteringDeviceDataRequest();

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

    private ImportMeteringDeviceDataRequest getImportMeteringDeviceDataRequest(ImportMeteringDeviceDataRequest request) {

        request.setId(OtherFormat.getId());

        return request;
    }
}
