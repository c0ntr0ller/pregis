
package ru.gosuslugi.dom.schema.integration._8_7_0_4.organizations_registry_common_service_async;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import ru.gosuslugi.dom.schema.integration._8_7_0.AckRequest;
import ru.gosuslugi.dom.schema.integration._8_7_0.GetStateRequest;
import ru.gosuslugi.dom.schema.integration._8_7_0.ISRequestHeader;
import ru.gosuslugi.dom.schema.integration._8_7_0.ResultHeader;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.organizations_registry_common.ExportDataProviderRequest;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.organizations_registry_common.ExportOrgRegistryRequest;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.organizations_registry_common.GetStateResult;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "RegOrgPortsTypeAsync", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/organizations-registry-common-service-async/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_7_0_4.organizations_registry_common.ObjectFactory.class,
    ru.gosuslugi.dom.schema.integration._8_7_0.ObjectFactory.class,
    org.w3._2000._09.xmldsig_.ObjectFactory.class
})
public interface RegOrgPortsTypeAsync {


    /**
     * 
     * @param getStateRequest
     * @param header0
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_7_0_4.organizations_registry_common.GetStateResult
     * @throws Fault
     */
    @WebMethod(action = "urn:getState")
    @WebResult(name = "getStateResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/organizations-registry-common/", partName = "getStateResult")
    public GetStateResult getState(
        @WebParam(name = "getStateRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", partName = "getStateRequest")
        GetStateRequest getStateRequest,
        @WebParam(name = "ISRequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, partName = "Header")
        ISRequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * 
     * @param exportOrgRegistryRequest
     * @param header0
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_7_0.AckRequest
     * @throws Fault
     */
    @WebMethod(action = "urn:exportOrgRegistry")
    @WebResult(name = "AckRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", partName = "AckRequest")
    public AckRequest exportOrgRegistry(
        @WebParam(name = "exportOrgRegistryRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/organizations-registry-common/", partName = "exportOrgRegistryRequest")
        ExportOrgRegistryRequest exportOrgRegistryRequest,
        @WebParam(name = "ISRequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, partName = "Header")
        ISRequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * экспорт сведений о поставщиках данных
     * 
     * @param exportDataProviderRequest
     * @param header0
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_7_0.AckRequest
     * @throws Fault
     */
    @WebMethod(action = "urn:exportDataProvider")
    @WebResult(name = "AckRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", partName = "AckRequest")
    public AckRequest exportDataProvider(
        @WebParam(name = "exportDataProviderRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/organizations-registry-common/", partName = "exportDataProviderRequest")
        ExportDataProviderRequest exportDataProviderRequest,
        @WebParam(name = "ISRequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, partName = "Header")
        ISRequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

}
