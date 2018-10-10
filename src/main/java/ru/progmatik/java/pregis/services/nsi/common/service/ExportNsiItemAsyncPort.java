package ru.progmatik.java.pregis.services.nsi.common.service;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.AckRequest;
import ru.gosuslugi.dom.schema.integration.base.ErrorMessageType;
import ru.gosuslugi.dom.schema.integration.base.ISRequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.nsi_common.ExportNsiItemRequest;
import ru.gosuslugi.dom.schema.integration.nsi_common.GetStateResult;
import ru.gosuslugi.dom.schema.integration.nsi_common_service_async.Fault;
import ru.gosuslugi.dom.schema.integration.nsi_common_service_async.NsiPortsTypeAsync;
import ru.gosuslugi.dom.schema.integration.nsi_common_service_async.NsiServiceAsync;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;
import ru.progmatik.java.pregis.other.UrlLoader;

import javax.xml.ws.Holder;
import java.math.BigInteger;

public class ExportNsiItemAsyncPort {
    private static final Logger LOGGER = Logger.getLogger(ExportNsiItemAsyncPort.class);
    private static final String NAME_METHOD = "exportNsiItemAsync";

    private final NsiPortsTypeAsync port;
    private final AnswerProcessing answerProcessing;

    public ExportNsiItemAsyncPort(AnswerProcessing answerProcessing, String methodName) {
        if(answerProcessing != null) {
            this.answerProcessing = answerProcessing;
        }else{
            this.answerProcessing = new AnswerProcessing();
        }

        NsiServiceAsync service = UrlLoader.instance().getUrlMap().get("nsiCommonAsync") == null ? new NsiServiceAsync()
                : new NsiServiceAsync(UrlLoader.instance().getUrlMap().get("nsiCommonAsync"));

        port = service.getNsiPortAsync();
        OtherFormat.setPortSettings(service, port);
    }

    public static GetStateResult callExportNsiItem(NsiListGroupEnum nsi, BigInteger codeNsi, AnswerProcessing answerProcessing) throws PreGISException {
        ExportNsiItemAsyncPort exportNsiItemAsyncPort = new ExportNsiItemAsyncPort(answerProcessing, "ExportNsiItem");
        return exportNsiItemAsyncPort.exportNsiItem(getExportNsiItemRequest(nsi, codeNsi));
    }

    private GetStateResult exportNsiItem(Object requestObject) throws PreGISException {
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


            if(ExportNsiItemRequest.class.getSimpleName().equalsIgnoreCase(requestObject.getClass().getSimpleName())) {
                ackRequest = port.exportNsiItem((ExportNsiItemRequest) requestObject, requestHeader, headerHolder);
            }else {
                answerProcessing.sendMessageToClient("Не определен метод экспорта/импорта " + NAME_METHOD);
            }


            if (ackRequest != null) {
                // сохраняем запрос
                answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, null, null, LOGGER);

                answerProcessing.sendMessageToClient(TextForLog.REQUEST_SENDED);

                ExportNsiItemAsyncResultWaiter exportNsiItemAsyncResultWaiter = new ExportNsiItemAsyncResultWaiter(ackRequest, NAME_METHOD, answerProcessing, port);

                result = exportNsiItemAsyncResultWaiter.getRequestResult();
                resultHeader = exportNsiItemAsyncResultWaiter.getHeaderHolder().value;

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

    /**
     * Метод, формирует объект для дальнейшей передачи его сервису ГИС ЖКХ.
     * @return ExportNsiItemRequest объект с указанными значениями, для передачи сервису ГИС ЖКХ.
     */
    private static ExportNsiItemRequest getExportNsiItemRequest(NsiListGroupEnum nsi, BigInteger codeNsi) {

        ExportNsiItemRequest request = new ExportNsiItemRequest();
        request.setId("signed-data-container");
//        request.setListGroup("NSI"); // Обязательный атрибут
        request.setListGroup(nsi.getNsi()); // Обязательный атрибут
        request.setRegistryNumber(codeNsi); // Обязательный атрибут
        request.setVersion(request.getVersion());
//        request.setRegistryNumber(BigInteger.valueOf(225)); // Обязательный атрибут

        return request;
    }
}
