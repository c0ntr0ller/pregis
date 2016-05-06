
package ru.gosuslugi.dom.schema.integration.services.infrastructure_service_async;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import ru.gosuslugi.dom.schema.integration.base.AckRequest;
import ru.gosuslugi.dom.schema.integration.base.GetStateRequest;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.infrastructure.ExportOKIRequest;
import ru.gosuslugi.dom.schema.integration.services.infrastructure.GetStateResult;
import ru.gosuslugi.dom.schema.integration.services.infrastructure.ImportOKIRequest;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "InfrastructurePortsTypeAsync", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/infrastructure-service-async/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration.services.infrastructure.ObjectFactory.class,
    ru.gosuslugi.dom.schema.integration.base.ObjectFactory.class,
    org.w3._2000._09.xmldsig_.ObjectFactory.class
})
public interface InfrastructurePortsTypeAsync {


    /**
     * Импорт информации об ОКИ
     * 
     * @param header0
     * @param header
     * @param importOKIRequest
     * @return
     *     returns ru.gosuslugi.dom.schema.integration.base.AckRequest
     * @throws Fault
     */
    @WebMethod(action = "urn:importOKI")
    @WebResult(name = "AckRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", partName = "AckRequest")
    public AckRequest importOKI(
        @WebParam(name = "importOKIRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/infrastructure/", partName = "importOKIRequest")
        ImportOKIRequest importOKIRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * Экспорт списка ОКИ 
     * 
     * @param header0
     * @param exportOKIRequest
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration.base.AckRequest
     * @throws Fault
     */
    @WebMethod(action = "urn:exportOKI")
    @WebResult(name = "AckRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", partName = "AckRequest")
    public AckRequest exportOKI(
        @WebParam(name = "exportOKIRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/infrastructure/", partName = "exportOKIRequest")
        ExportOKIRequest exportOKIRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * Получить статус запроса
     * 
     * @param getStateRequest
     * @param header0
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration.services.infrastructure.GetStateResult
     * @throws Fault
     */
    @WebMethod(action = "urn:getState")
    @WebResult(name = "getStateResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/infrastructure/", partName = "getStateResult")
    public GetStateResult getState(
        @WebParam(name = "getStateRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", partName = "getStateRequest")
        GetStateRequest getStateRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

}