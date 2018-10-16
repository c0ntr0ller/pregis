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

/**
 * класс описывает порт для взаимодействия с сервисом импорта/экспорат платежей в ГИС ЖКХ
 * пока реализована только отсылка платежей
 */
public class PaymentAsyncPort {
    private static final Logger LOGGER = Logger.getLogger(PaymentAsyncPort.class);

    private final PaymentPortsTypeAsync port;
    private final AnswerProcessing answerProcessing;

    public PaymentAsyncPort(AnswerProcessing answerProcessing) {
        if(answerProcessing != null) {
            this.answerProcessing = answerProcessing;
        }else{
            this.answerProcessing = new AnswerProcessing();
        }

        PaymentsServiceAsync service = UrlLoader.instance().getUrlMap().get("paymentAsync") == null ? new PaymentsServiceAsync()
                : new PaymentsServiceAsync(UrlLoader.instance().getUrlMap().get("paymentAsync"));
        if (answerProcessing != null) {
            answerProcessing.sendMessageToClient("Открытие порта PaymentPortAsync");
        }
        port = service.getPaymentPortAsync();
        OtherFormat.setPortSettings(service, port);
    }

    public static GetStateResult callImportPayments(ImportSupplierNotificationsOfOrderExecutionRequest request, AnswerProcessing answerProcessing) throws SQLException, PreGISException {
        return new PaymentAsyncPort(answerProcessing).interactPayments(request);
    }

    public GetStateResult interactPayments(Object requestObject) throws SQLException, PreGISException {
        final String method_name = requestObject.getClass().getSimpleName();
        answerProcessing.sendMessageToClient("");
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        ResultHeader resultHeader;
        Holder<ResultHeader> headerHolder = new Holder<>();
        ErrorMessageType resultErrorMessage;

        GetStateResult result = null;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            AckRequest ackRequest = null;

            if(ImportSupplierNotificationsOfOrderExecutionRequest.class.getSimpleName().equalsIgnoreCase(method_name)){
                ackRequest = port.importSupplierNotificationsOfOrderExecution(getImportSupplierNotificationsOfOrderExecution(requestObject), requestHeader, headerHolder);
            }else if(ImportNotificationsOfOrderExecutionRequest.class.getSimpleName().equalsIgnoreCase(method_name)){
                ackRequest = port.importNotificationsOfOrderExecution(getImportNotificationsOfOrderExecutionRequest(requestObject), requestHeader, headerHolder);
            }else{
                answerProcessing.sendMessageToClient("Не определен тип объекта запроса на экспорт/импорт " + method_name);
            }

            if (ackRequest != null) {
                // сохраняем запрос
                answerProcessing.sendToBaseAndAnotherError(method_name, requestHeader, null, null, LOGGER);

                answerProcessing.sendMessageToClient(TextForLog.REQUEST_SENDED);

                PaymentAsyncResultWaiter paymentAsyncResultWaiter = new PaymentAsyncResultWaiter(ackRequest, method_name, answerProcessing, port);

                result = paymentAsyncResultWaiter.getRequestResult();
                resultHeader = paymentAsyncResultWaiter.getHeaderHolder().value;

            }else{
                return null;
            }
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(method_name, requestHeader, LOGGER, fault);
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
                answerProcessing.sendToBaseAndAnotherError(method_name, null, resultHeader, null, LOGGER);
            }
            answerProcessing.sendToBaseAndAnotherError(method_name, null, resultHeader, resultErrorMessage, LOGGER);
        }
        else
            // сохраняем ответ
            answerProcessing.sendToBaseAndAnotherError(method_name, null, resultHeader, null, LOGGER);

        return result;

    }

    private ImportSupplierNotificationsOfOrderExecutionRequest getImportSupplierNotificationsOfOrderExecution(Object requestObject) {
        ImportSupplierNotificationsOfOrderExecutionRequest request = (ImportSupplierNotificationsOfOrderExecutionRequest)requestObject;
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        return request;
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
