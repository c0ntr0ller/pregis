package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.house_management.ExportAccountRequest;
import ru.gosuslugi.dom.schema.integration.house_management.ExportAccountResult;
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
 * Класс, экспорт сведений о лицевых счетах.
 * С его помощью можно получить "AccountGUID" и "UniqueNumber".
 */
public class ExportAccountData {

    private static final Logger LOGGER = Logger.getLogger(ExportAccountData.class);

    private static final String NAME_METHOD = "exportAccountData";

    private final HouseManagementPortsType port;
    private AnswerProcessing answerProcessing;

    /**
     * Конструктор, добавляет параметры для соединения.
     */
    public ExportAccountData(AnswerProcessing answerProcessing) {

        this.answerProcessing = answerProcessing;

        HouseManagementService service = UrlLoader.instance().getUrlMap().get("homeManagement") == null ? new HouseManagementService()
                : new HouseManagementService(UrlLoader.instance().getUrlMap().get("homeManagement"));

        port = service.getHouseManagementPort();
        OtherFormat.setPortSettings(service, port);
    }

    public ExportAccountResult callExportAccountData(String homeFias) throws SQLException {

//        Создание загаловков сообщений (запроса и ответа)
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        Holder<ResultHeader> headerHolder = new Holder<>();

        ExportAccountResult result;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            result = port.exportAccountData(getExportAccountRequest(homeFias), requestHeader, headerHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
        } catch (Fault fault) {
//            Сохраняем ошибку в базу данных
            answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            return null;
        }
        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, headerHolder.value, result.getErrorMessage(), LOGGER);
        if (result.getErrorMessage() != null) return null; // Если возникли ошибки.

        return result;
    }

    private ExportAccountRequest getExportAccountRequest(String homeFias) {

        ExportAccountRequest request = new ExportAccountRequest();
        request.setId(OtherFormat.getId());
        request.setFIASHouseGuid(homeFias);
        request.setVersion(request.getVersion());

        return request;
    }
}
