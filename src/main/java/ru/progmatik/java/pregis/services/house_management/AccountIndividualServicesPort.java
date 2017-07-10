package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.AckRequest;
import ru.gosuslugi.dom.schema.integration.base.ErrorMessageType;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.house_management.ExportAccountIndividualServicesRequest;
import ru.gosuslugi.dom.schema.integration.house_management.GetStateResult;
import ru.gosuslugi.dom.schema.integration.house_management.ImportAccountIndividualServicesRequest;
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
 * Класс служит для добавления индивидуальных услуг абонентам
 * Created by Администратор on 10.07.2017.
 */
public class AccountIndividualServicesPort {
    private static final Logger LOGGER = Logger.getLogger(ExportAccountData.class);

    private static final String NAME_METHOD_IMPORT = "importAccountIndividualServices";
    private static final String NAME_METHOD_EXPORT = "exportAccountIndividualServices";

    private final HouseManagementPortsTypeAsync port;
    private final AnswerProcessing answerProcessing;

    /**
     * Конструктор, добавляет параметры для соединения.
     */
    public AccountIndividualServicesPort(final AnswerProcessing answerProcessing) {

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

    public GetStateResult callImportAccountIndividualServices(final ImportAccountIndividualServicesRequest importAccountIndividualServicesRequest) throws SQLException {

//        Создание заголовков сообщений (запроса и ответа)
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD_IMPORT);

        final RequestHeader requestHeader = OtherFormat.getRequestHeader();
        final Holder<ResultHeader> headerHolder = new Holder<>();
        ResultHeader resultHeader = null;
        ErrorMessageType resultErrorMessage = null;

        answerProcessing.sendMessageToClient("");

        HouseManagementAsyncResultWaiter accountAsyncResultWaiter = null;
        GetStateResult result;

        result = null;
        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            AckRequest ackRequest = port.importAccountIndividualServices(getImportAccountIndividualServicesRequest(importAccountIndividualServicesRequest), requestHeader, headerHolder);

            // сохраняем запрос
            answerProcessing.sendToBaseAndAnotherError(NAME_METHOD_IMPORT, requestHeader, null, null, LOGGER);

            // ждем результат в асинхронном режиме
            if (ackRequest != null) {
                accountAsyncResultWaiter = new HouseManagementAsyncResultWaiter(ackRequest, NAME_METHOD_IMPORT, answerProcessing, port);
                result = accountAsyncResultWaiter.getRequestResult();
            }

        } catch (Fault fault) {
//            Сохраняем ошибку в базу данных
            answerProcessing.sendServerErrorToClient(NAME_METHOD_IMPORT, requestHeader, LOGGER, fault);
            return null;
        }

        if (result != null) resultErrorMessage = result.getErrorMessage();

        if (accountAsyncResultWaiter != null) resultHeader = accountAsyncResultWaiter.getHeaderHolder().value;

        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD_IMPORT, null, resultHeader, resultErrorMessage, LOGGER);

        if (resultErrorMessage != null) {
            result = null;
        }

        return result;
    }

    public GetStateResult callExportAccountIndividualServices(final String AccountGUID) throws SQLException {

        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD_EXPORT);

        final RequestHeader requestHeader = OtherFormat.getRequestHeader();
        final Holder<ResultHeader> headerHolder = new Holder<>();
        ResultHeader resultHeader = null;
        ErrorMessageType resultErrorMessage = null;

        answerProcessing.sendMessageToClient("");

        HouseManagementAsyncResultWaiter accountAsyncResultWaiter = null;
        GetStateResult result;

        result = null;
        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            AckRequest ackRequest = port.exportAccountIndividualServices(getExportAccountIndividualServicesRequest(AccountGUID), requestHeader, headerHolder);

            // сохраняем запрос
            answerProcessing.sendToBaseAndAnotherError(NAME_METHOD_EXPORT, requestHeader, null, null, LOGGER);

            // ждем результат в асинхронном режиме
            if (ackRequest != null) {
                accountAsyncResultWaiter = new HouseManagementAsyncResultWaiter(ackRequest, NAME_METHOD_EXPORT, answerProcessing, port);
                result = accountAsyncResultWaiter.getRequestResult();
            }

        } catch (Fault fault) {
//            Сохраняем ошибку в базу данных
            answerProcessing.sendServerErrorToClient(NAME_METHOD_EXPORT, requestHeader, LOGGER, fault);
            return null;
        }

        if (result != null) resultErrorMessage = result.getErrorMessage();

        if (accountAsyncResultWaiter != null) resultHeader = accountAsyncResultWaiter.getHeaderHolder().value;

        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD_EXPORT, null, resultHeader, resultErrorMessage, LOGGER);

        if (resultErrorMessage != null) {
            result = null;
        }

        return result;
    }

    private ImportAccountIndividualServicesRequest getImportAccountIndividualServicesRequest(final ImportAccountIndividualServicesRequest request) {

        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        return request;
    }

    private ExportAccountIndividualServicesRequest getExportAccountIndividualServicesRequest(final String AccountGUID){
        ExportAccountIndividualServicesRequest request = new ExportAccountIndividualServicesRequest();

        request.getAccountGuid().add(AccountGUID);
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        return request;
    }
}
