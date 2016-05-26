package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.house_management.ExportHouseRequest;
import ru.gosuslugi.dom.schema.integration.services.house_management.ExportHouseResult;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.Fault;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.HouseManagementPortsType;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.HouseManagementService;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;
import ru.progmatik.java.web.servlets.socket.ClientService;

import javax.xml.ws.Holder;

/**
 * Класс, экспорт данных домов.
 * Получаем данные из системы ГИС ЖКХ.
 */
public class ExportHouseData {

    private static final Logger LOGGER = Logger.getLogger(ExportHouseData.class);
    private static final String NAME_METHOD = "exportHouseData";

    private final HouseManagementService service = new HouseManagementService();
    private final HouseManagementPortsType port = service.getHouseManagementPort();
    private final ClientService clientService;
    private final AnswerProcessing answerProcessing;

    /**
     * Конструктор, получает в параметр сылку на веб-сокет.
     * @param clientService websocket для отправки сообщений пользователю.
     */
    public ExportHouseData(ClientService clientService, AnswerProcessing answerProcessing) {
        OtherFormat.setPortSettings(service, port);
        this.clientService = clientService;
        this.answerProcessing = answerProcessing;
    }

    public void callExportHouseData() {

        clientService.sendMessage(TextForLog.FORMED_REQUEST + NAME_METHOD);
        Holder<ResultHeader> resultHolder = new Holder<>();

        RequestHeader headerRequest = OtherFormat.getRequestHeader();

        ExportHouseResult result;


        try {
            clientService.sendMessage(TextForLog.SENDING_REQUEST);
            result =  port.exportHouseData(getExportHouseRequest(), headerRequest, resultHolder);
            clientService.sendMessage(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, headerRequest, clientService, LOGGER, fault);
            return;
        }

        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, headerRequest, resultHolder.value,
                result.getErrorMessage(), clientService, LOGGER);

    }

    private ExportHouseRequest getExportHouseRequest() {

        ExportHouseRequest request = new ExportHouseRequest();
        request.setId(OtherFormat.getId());
        request.setFIASHouseGuid("f20a2f00-c9cf-485f-ac41-92af5b77e29a");

        return request;
    }

}
