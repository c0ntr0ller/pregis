package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.house_management.ExportSupplyResourceContractRequest;
import ru.gosuslugi.dom.schema.integration.house_management.ExportSupplyResourceContractResult;
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
 * Экспорт договоров ресурсоснабжения.
 *
 */
public class ExportSupplyResourceContractData {

    private static final Logger LOGGER = Logger.getLogger(ExportSupplyResourceContractData.class);
    private static final String NAME_METHOD = "exportSupplyResourceContractData";

    private final HouseManagementPortsType port;
    private final AnswerProcessing answerProcessing;

    public ExportSupplyResourceContractData(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;

        HouseManagementService service = UrlLoader.instance().getUrlMap().get("homeManagement") == null ? new HouseManagementService()
                : new HouseManagementService(UrlLoader.instance().getUrlMap().get("homeManagement"));

        port = service.getHouseManagementPort();
        OtherFormat.setPortSettings(service, port);
    }

    public void callExportSupplyResourceContractData() throws SQLException {

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        Holder<ResultHeader> headerHolder = new Holder<>();

        ExportSupplyResourceContractResult result;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            result = port.exportSupplyResourceContractData(getExportSupplyResourceContractData(), requestHeader, headerHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            return;
        }
        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, headerHolder.value, result.getErrorMessage(), LOGGER);
    }

    private ExportSupplyResourceContractRequest getExportSupplyResourceContractData() {

        ru.gosuslugi.dom.schema.integration.house_management.ExportSupplyResourceContractRequest request = new ExportSupplyResourceContractRequest();
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        return request;
    }
}
