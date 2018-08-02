package ru.progmatik.java.pregis.services.payment;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.AckRequest;
import ru.gosuslugi.dom.schema.integration.base.ErrorMessageType;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.payment.*;
import ru.gosuslugi.dom.schema.integration.payment_service_async.Fault;
import ru.gosuslugi.dom.schema.integration.payment_service_async.PaymentPortsTypeAsync;
import ru.gosuslugi.dom.schema.integration.payment_service_async.PaymentsServiceAsync;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;
import ru.progmatik.java.pregis.other.UrlLoader;

import javax.xml.ws.Holder;
import java.sql.SQLException;

public class PaymentAsyncPort {
    private static final Logger LOGGER = Logger.getLogger(PaymentAsyncPort.class);
    private final String NAME_METHOD;

    private static final String NAME_METHOD_IMPORT = "importNotificationsOfOrderExecution";
    private static final String NAME_METHOD_EXPORT_PAYMENT = "exportPaymentDocumentDetails";

    private final PaymentsServiceAsync service;
    private final PaymentPortsTypeAsync port;
    private final AnswerProcessing answerProcessing;

    public PaymentAsyncPort(AnswerProcessing answerProcessing, String methodName) {
        this.NAME_METHOD = methodName;

        if(answerProcessing != null) {
            this.answerProcessing = answerProcessing;
        }else{
            this.answerProcessing = new AnswerProcessing();
        }

        service = UrlLoader.instance().getUrlMap().get("paymentAsync") == null ? new PaymentsServiceAsync()
                : new PaymentsServiceAsync(UrlLoader.instance().getUrlMap().get("paymentAsync"));
        port = service.getPaymentPortAsync();
        OtherFormat.setPortSettings(service, port);
    }

    public GetStateResult interactPayments(Object requestObject) throws SQLException, PreGISException {
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        ResultHeader resultHeader;
        Holder<ResultHeader> headerHolder = new Holder<>();
        ErrorMessageType resultErrorMessage;

        GetStateResult result = null;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            AckRequest ackRequest = null;

            switch (requestObject.getClass().getSimpleName()) {
                case "ImportNotificationsOfOrderExecutionRequest":
                    ackRequest = port.importNotificationsOfOrderExecution(getImportNotificationsOfOrderExecutionRequest(requestObject), requestHeader, headerHolder);
                    break;
                case "ImportNotificationsOfOrderExecutionCancellationRequest":
                    ackRequest = port.importNotificationsOfOrderExecutionCancellation(getImportNotificationsOfOrderExecutionCancellationRequest(requestObject), requestHeader, headerHolder);
                    break;
                case "ImportSupplierNotificationsOfOrderExecutionRequest":
                    ackRequest = port.importSupplierNotificationsOfOrderExecution(getImportSupplierNotificationsOfOrderExecutionRequest(requestObject), requestHeader, headerHolder);
                    break;
                case "ExportPaymentDocumentDetailsRequest":
                    ackRequest = port.exportPaymentDocumentDetails(getExportPaymentDocumentDetailsRequest(requestObject), requestHeader, headerHolder);
                    break;
                default:
                    answerProcessing.sendMessageToClient("Не определен метод экспорта/импорта " + NAME_METHOD);
            }

            if (ackRequest != null) {
                // сохраняем запрос
                answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, null, null, LOGGER);

                answerProcessing.sendMessageToClient(TextForLog.REQUEST_SENDED);

                PaymentAsyncResultWaiter paymentAsyncResultWaiter = new PaymentAsyncResultWaiter(ackRequest, NAME_METHOD, answerProcessing, port);

                result = paymentAsyncResultWaiter.getRequestResult();
                resultHeader = paymentAsyncResultWaiter.getHeaderHolder().value;

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

    private ImportNotificationsOfOrderExecutionRequest getImportNotificationsOfOrderExecutionRequest(Object requestObject){
        ImportNotificationsOfOrderExecutionRequest request = (ImportNotificationsOfOrderExecutionRequest)requestObject;
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        return request;
    }

    private ImportNotificationsOfOrderExecutionCancellationRequest getImportNotificationsOfOrderExecutionCancellationRequest(Object requestObject){
        ImportNotificationsOfOrderExecutionCancellationRequest request = (ImportNotificationsOfOrderExecutionCancellationRequest)requestObject;
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        return request;
    }

    private ImportSupplierNotificationsOfOrderExecutionRequest getImportSupplierNotificationsOfOrderExecutionRequest(Object requestObject){
        ImportSupplierNotificationsOfOrderExecutionRequest request = (ImportSupplierNotificationsOfOrderExecutionRequest)requestObject;
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        return request;
    }

    private ExportPaymentDocumentDetailsRequest getExportPaymentDocumentDetailsRequest(Object requestObject){
        ExportPaymentDocumentDetailsRequest request = (ExportPaymentDocumentDetailsRequest)requestObject;
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        return request;
    }
}
