package ru.progmatik.java.pregis.services.organizations;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.AckRequest;
import ru.gosuslugi.dom.schema.integration.base.ErrorMessageType;
import ru.gosuslugi.dom.schema.integration.base.ISRequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common.ExportOrgRegistryRequest;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common.GetStateResult;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common_service_async.Fault;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common_service_async.RegOrgPortsTypeAsync;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common_service_async.RegOrgServiceAsync;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;
import ru.progmatik.java.pregis.other.UrlLoader;

import javax.xml.ws.Holder;
import java.sql.SQLException;

public class OrgRegistryAsyncPort {
    private static final Logger LOGGER = Logger.getLogger(OrgRegistryAsyncPort.class);
    private final String NAME_METHOD;

    private final RegOrgPortsTypeAsync port;
    private final AnswerProcessing answerProcessing;

    public OrgRegistryAsyncPort(AnswerProcessing answerProcessing, String methodName) {
        this.NAME_METHOD = methodName;
        if(answerProcessing != null) {
            this.answerProcessing = answerProcessing;
        }else{
            this.answerProcessing = new AnswerProcessing();
        }

        RegOrgServiceAsync service = UrlLoader.instance().getUrlMap().get("orgRegistryCommonAsync") == null ? new RegOrgServiceAsync()
                : new RegOrgServiceAsync(UrlLoader.instance().getUrlMap().get("orgRegistryCommonAsync"));

        port = service.getRegOrgAsyncPort();
        OtherFormat.setPortSettings(service, port);
    }

    public static GetStateResult callExportOrgRegistry(ExportOrgRegistryRequest request, AnswerProcessing answerProcessing) throws SQLException, PreGISException {
        OrgRegistryAsyncPort orgRegistryAsyncPort = new OrgRegistryAsyncPort(answerProcessing, "ExportOrgRegistry");
        return orgRegistryAsyncPort.interactOrgRegistry(request);
    }

    private GetStateResult interactOrgRegistry(Object requestObject) throws SQLException, PreGISException {
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
        ISRequestHeader requestHeader = OtherFormat.getISRequestHeader();
        ResultHeader resultHeader;
        Holder<ResultHeader> headerHolder = new Holder<>();
        ErrorMessageType resultErrorMessage;

        GetStateResult result;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            AckRequest ackRequest = null;

            switch (requestObject.getClass().getSimpleName()) {
                case "ExportOrgRegistryRequest":
                    ackRequest = port.exportOrgRegistry((ExportOrgRegistryRequest)requestObject, requestHeader, headerHolder);
                    break;
                default:
                    answerProcessing.sendMessageToClient("Не определен метод экспорта/импорта " + NAME_METHOD);
            }

            if (ackRequest != null) {
                // сохраняем запрос
                answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, null, null, LOGGER);

                answerProcessing.sendMessageToClient(TextForLog.REQUEST_SENDED);

                OrgRegistryAsyncResultWaiter orgRegistryAsyncResultWaiter = new OrgRegistryAsyncResultWaiter(ackRequest, NAME_METHOD, answerProcessing, port);

                result = orgRegistryAsyncResultWaiter.getRequestResult();
                resultHeader = orgRegistryAsyncResultWaiter.getHeaderHolder().value;

            }else{
                return null;
            }
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            return null;
        }

        if (result == null) {
            throw new PreGISException("Не удалось получить данные от сервера ГИС ЖКХ");
        }else{
            resultErrorMessage = result.getErrorMessage();
        }

        if (resultErrorMessage != null) {
            if(resultErrorMessage.getErrorCode().contains("INT002012")) { // это не ошибка, просто нет данных
                answerProcessing.sendMessageToClient("Нет объектов для экспорта из ГИС ЖКХ");
                answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, null, resultHeader, null, LOGGER);
            }
            answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, null, resultHeader, resultErrorMessage, LOGGER);
        }
        else
            // сохраняем ответ
            answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, null, resultHeader, null, LOGGER);

        return result;
    }
}
