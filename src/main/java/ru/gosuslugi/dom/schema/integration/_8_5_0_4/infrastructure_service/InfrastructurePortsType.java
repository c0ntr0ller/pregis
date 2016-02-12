
package ru.gosuslugi.dom.schema.integration._8_5_0_4.infrastructure_service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import ru.gosuslugi.dom.schema.integration._8_5_0.ImportResult;
import ru.gosuslugi.dom.schema.integration._8_5_0.RequestHeader;
import ru.gosuslugi.dom.schema.integration._8_5_0.ResultHeader;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.infrastructure.ExportOKIRequest;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.infrastructure.ExportOKIResult;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.infrastructure.ImportOKIRequest;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "InfrastructurePortsType", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/infrastructure-service/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_5_0_4.infrastructure.ObjectFactory.class,
    ru.gosuslugi.dom.schema.integration._8_5_0.ObjectFactory.class,
    org.w3._2000._09.xmldsig_.ObjectFactory.class
})
public interface InfrastructurePortsType {


    /**
     * Импорт информации об ОКИ
     * 
     * @param header0
     * @param header
     * @param importOKIRequest
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_5_0.ImportResult
     * @throws Fault
     */
    @WebMethod(action = "urn:importOKI")
    @WebResult(name = "ImportResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/", partName = "importOKIResult")
    public ImportResult importOKI(
        @WebParam(name = "importOKIRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/infrastructure/", partName = "importOKIRequest")
        ImportOKIRequest importOKIRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
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
     *     returns ru.gosuslugi.dom.schema.integration._8_5_0_4.infrastructure.ExportOKIResult
     * @throws Fault
     */
    @WebMethod(action = "urn:exportOKI")
    @WebResult(name = "exportOKIResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/infrastructure/", partName = "exportOKIResult")
    public ExportOKIResult exportOKI(
        @WebParam(name = "exportOKIRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/infrastructure/", partName = "exportOKIRequest")
        ExportOKIRequest exportOKIRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

}