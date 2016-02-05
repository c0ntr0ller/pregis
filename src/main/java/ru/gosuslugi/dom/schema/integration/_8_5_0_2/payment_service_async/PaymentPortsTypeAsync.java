
package ru.gosuslugi.dom.schema.integration._8_5_0_2.payment_service_async;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import ru.gosuslugi.dom.schema.integration._8_5_0.AckRequest;
import ru.gosuslugi.dom.schema.integration._8_5_0.GetStateRequest;
import ru.gosuslugi.dom.schema.integration._8_5_0.RequestHeader;
import ru.gosuslugi.dom.schema.integration._8_5_0.ResultHeader;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.payment.ExportNotificationsOfOrderExecutionRequest;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.payment.ExportPaymentDocumentDetailsRequest;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.payment.GetStateResult;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.payment.ImportAcknowledgmentRequest;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.payment.ImportNotificationsOfOrderExecutionCancellationRequest;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.payment.ImportNotificationsOfOrderExecutionRequest;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "PaymentPortsTypeAsync", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/payment-service-async/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_5_0_2.payment.ObjectFactory.class,
    ru.gosuslugi.dom.schema.integration._8_5_0.ObjectFactory.class,
    org.w3._2000._09.xmldsig_.ObjectFactory.class
})
public interface PaymentPortsTypeAsync {


    /**
     * ВИ_ОПЛАТА_ИЗВ. Передать перечень документов "Извещение о принятии к исполнению распоряжения"
     * 
     * @param header0
     * @param importNotificationsOfOrderExecutionRequest
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_5_0.AckRequest
     * @throws Fault
     */
    @WebMethod(action = "urn:importNotificationsOfOrderExecution")
    @WebResult(name = "AckRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", partName = "AckRequest")
    public AckRequest importNotificationsOfOrderExecution(
        @WebParam(name = "importNotificationsOfOrderExecutionRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/payment/", partName = "importNotificationsOfOrderExecutionRequest")
        ImportNotificationsOfOrderExecutionRequest importNotificationsOfOrderExecutionRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * ВИ_ ОПЛАТА_ИЗВАН. Импорт документов "Извещение об аннулировании извещения о принятии к исполнению распоряжения"
     * 
     * @param header0
     * @param importNotificationsOfOrderExecutionCancellationRequest
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_5_0.AckRequest
     * @throws Fault
     */
    @WebMethod(action = "urn:importNotificationsOfOrderExecutionCancellation")
    @WebResult(name = "AckRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", partName = "AckRequest")
    public AckRequest importNotificationsOfOrderExecutionCancellation(
        @WebParam(name = "importNotificationsOfOrderExecutionCancellationRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/payment/", partName = "importNotificationsOfOrderExecutionCancellationRequest")
        ImportNotificationsOfOrderExecutionCancellationRequest importNotificationsOfOrderExecutionCancellationRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * ВИ_ОПЛАТА_ЭКСКВИТ. Экспорт платежных документов с результатами квитирования
     * 
     * @param exportNotificationsOfOrderExecutionRequest
     * @param header0
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_5_0.AckRequest
     * @throws Fault
     */
    @WebMethod(action = "urn:exportNotificationsOfOrderExecution")
    @WebResult(name = "AckRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", partName = "AckRequest")
    public AckRequest exportNotificationsOfOrderExecution(
        @WebParam(name = "exportNotificationsOfOrderExecutionRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/payment/", partName = "exportNotificationsOfOrderExecutionRequest")
        ExportNotificationsOfOrderExecutionRequest exportNotificationsOfOrderExecutionRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * ВИ_ОПЛАТА_ИМКВИТ. Импорт пакета документов "Запрос на квитирование"
     * 
     * @param header0
     * @param header
     * @param importAcknowledgmentRequest
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_5_0.AckRequest
     * @throws Fault
     */
    @WebMethod(action = "urn:importAcknowledgment")
    @WebResult(name = "AckRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", partName = "AckRequest")
    public AckRequest importAcknowledgment(
        @WebParam(name = "importAcknowledgmentRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/payment/", partName = "importAcknowledgmentRequest")
        ImportAcknowledgmentRequest importAcknowledgmentRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * Получить статус обработки запроса
     * 
     * @param header0
     * @param header
     * @param getRequestState
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_5_0_2.payment.GetStateResult
     * @throws Fault
     */
    @WebMethod(action = "urn:getState")
    @WebResult(name = "getStateResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/payment/", partName = "getRequestStateResult")
    public GetStateResult getState(
        @WebParam(name = "getStateRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", partName = "getRequestState")
        GetStateRequest getRequestState,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * 
     * @param exportPaymentDocumentDetailsRequest
     * @param header0
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_5_0.AckRequest
     * @throws Fault
     */
    @WebMethod(action = "urn:exportPaymentDocumentDetails")
    @WebResult(name = "AckRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", partName = "AckRequest")
    public AckRequest exportPaymentDocumentDetails(
        @WebParam(name = "exportPaymentDocumentDetailsRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/payment/", partName = "exportPaymentDocumentDetailsRequest")
        ExportPaymentDocumentDetailsRequest exportPaymentDocumentDetailsRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

}
