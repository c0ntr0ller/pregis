package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.AckRequest;
import ru.gosuslugi.dom.schema.integration.base.ErrorMessageType;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.house_management.ExportAccountRequest;
import ru.gosuslugi.dom.schema.integration.house_management.GetStateResult;
import ru.gosuslugi.dom.schema.integration.house_management_service_async.Fault;
import ru.gosuslugi.dom.schema.integration.house_management_service_async.HouseManagementPortsTypeAsync;
import ru.gosuslugi.dom.schema.integration.house_management_service_async.HouseManagementServiceAsync;
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

    private static final String NAME_METHOD = "exportAccountDataAsync";

    private final HouseManagementPortsTypeAsync port;
    private final AnswerProcessing answerProcessing;

    /**
     * Конструктор, добавляет параметры для соединения.
     */
    public ExportAccountData(final AnswerProcessing answerProcessing) {

        if(answerProcessing != null) {
            this.answerProcessing = answerProcessing;
        }else{
            this.answerProcessing = new AnswerProcessing();
        }

        HouseManagementServiceAsync service = UrlLoader.instance().getUrlMap().get("homeManagementAsync") == null ? new HouseManagementServiceAsync()
                : new HouseManagementServiceAsync(UrlLoader.instance().getUrlMap().get("homeManagementAsync"));

        port = service.getHouseManagementPortAsync();
        OtherFormat.setPortSettings(service, port);
    }

    public GetStateResult callExportAccountData(final String homeFias) throws SQLException {

//        Создание заголовков сообщений (запроса и ответа)
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);

        final RequestHeader requestHeader = OtherFormat.getRequestHeader();
        final Holder<ResultHeader> headerHolder = new Holder<>();
        ResultHeader resultHeader = null;
        ErrorMessageType resultErrorMessage = null;

        answerProcessing.sendMessageToClient("");

        AccountAsyncResultWaiter accountAsyncResultWaiter = null;
        GetStateResult result;

        result = null;
        try {
            AckRequest ackRequest = null;
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            ackRequest = port.exportAccountData(getExportAccountRequest(homeFias), requestHeader, headerHolder);

            if (ackRequest != null) {
                accountAsyncResultWaiter = new AccountAsyncResultWaiter(ackRequest, NAME_METHOD, answerProcessing, port);
                result = accountAsyncResultWaiter.getRequestResult();
            }

        } catch (Fault fault) {
//            Сохраняем ошибку в базу данных
            answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            return null;
        }

        if (result != null) resultErrorMessage = result.getErrorMessage();

        if (accountAsyncResultWaiter != null) resultHeader = accountAsyncResultWaiter.getHeaderHolder().value;

        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, resultHeader, resultErrorMessage, LOGGER);

        if (resultErrorMessage != null) {
            result = null;
        }

        return result;
    }

    private ExportAccountRequest getExportAccountRequest(final String homeFias) {

        final ExportAccountRequest request = new ExportAccountRequest();
        request.setId(OtherFormat.getId());
        request.setFIASHouseGuid(homeFias);
        request.setVersion(request.getVersion());

        return request;
    }
}
