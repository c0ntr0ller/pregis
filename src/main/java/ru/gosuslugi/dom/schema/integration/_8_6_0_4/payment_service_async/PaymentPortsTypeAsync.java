
package ru.gosuslugi.dom.schema.integration._8_6_0_4.payment_service_async;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import ru.gosuslugi.dom.schema.integration._8_6_0.AckRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0.GetStateRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0.ISRequestHeader;
import ru.gosuslugi.dom.schema.integration._8_6_0.ResultHeader;
import ru.gosuslugi.dom.schema.integration._8_6_0_4.payment.ExportPaymentDocumentDetailsRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_4.payment.GetStateResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_4.payment.ImportNotificationsOfOrderExecutionCancellationRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_4.payment.ImportNotificationsOfOrderExecutionRequest;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "PaymentPortsTypeAsync", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/payment-service-async/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_6_0_4.payment.ObjectFactory.class,
    ru.gosuslugi.dom.schema.integration._8_6_0.ObjectFactory.class,
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
     *     returns ru.gosuslugi.dom.schema.integration._8_6_0.AckRequest
     * @throws Fault
     */
    @WebMethod(action = "urn:importNotificationsOfOrderExecution")
    @WebResult(name = "AckRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", partName = "AckRequest")
    public AckRequest importNotificationsOfOrderExecution(
        @WebParam(name = "importNotificationsOfOrderExecutionRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/payment/", partName = "importNotificationsOfOrderExecutionRequest")
        ImportNotificationsOfOrderExecutionRequest importNotificationsOfOrderExecutionRequest,
        @WebParam(name = "ISRequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", header = true, partName = "Header")
        ISRequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
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
     *     returns ru.gosuslugi.dom.schema.integration._8_6_0.AckRequest
     * @throws Fault
     */
    @WebMethod(action = "urn:importNotificationsOfOrderExecutionCancellation")
    @WebResult(name = "AckRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", partName = "AckRequest")
    public AckRequest importNotificationsOfOrderExecutionCancellation(
        @WebParam(name = "importNotificationsOfOrderExecutionCancellationRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/payment/", partName = "importNotificationsOfOrderExecutionCancellationRequest")
        ImportNotificationsOfOrderExecutionCancellationRequest importNotificationsOfOrderExecutionCancellationRequest,
        @WebParam(name = "ISRequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", header = true, partName = "Header")
        ISRequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
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
     *     returns ru.gosuslugi.dom.schema.integration._8_6_0_4.payment.GetStateResult
     * @throws Fault
     */
    @WebMethod(action = "urn:getState")
    @WebResult(name = "getStateResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/payment/", partName = "getRequestStateResult")
    public GetStateResult getState(
        @WebParam(name = "getStateRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", partName = "getRequestState")
        GetStateRequest getRequestState,
        @WebParam(name = "ISRequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", header = true, partName = "Header")
        ISRequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * 
     * @param exportPaymentDocumentDetailsRequest
     * @param header0
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_6_0.AckRequest
     * @throws Fault
     */
    @WebMethod(action = "urn:exportPaymentDocumentDetails")
    @WebResult(name = "AckRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", partName = "AckRequest")
    public AckRequest exportPaymentDocumentDetails(
        @WebParam(name = "exportPaymentDocumentDetailsRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/payment/", partName = "exportPaymentDocumentDetailsRequest")
        ExportPaymentDocumentDetailsRequest exportPaymentDocumentDetailsRequest,
        @WebParam(name = "ISRequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", header = true, partName = "Header")
        ISRequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

}
