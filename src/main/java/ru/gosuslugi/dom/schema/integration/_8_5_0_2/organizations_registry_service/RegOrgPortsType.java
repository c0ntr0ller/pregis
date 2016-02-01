
package ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry_service;

import ru.gosuslugi.dom.schema.integration._8_5_0.ImportResult;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry.*;

import javax.jws.*;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "RegOrgPortsType", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry-service/")
@HandlerChain(file = "RegOrgService_handler.xml")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry.ObjectFactory.class,
    ru.gosuslugi.dom.schema.integration._8_5_0.ObjectFactory.class,
    org.w3._2000._09.xmldsig_.ObjectFactory.class
})
public interface RegOrgPortsType {


    /**
     * экспорт сведений об организациях
     * 
     * @param exportOrgRegistryRequest
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry.ExportOrgRegistryResult
     * @throws Fault
     */
    @WebMethod(action = "urn:exportOrgRegistry")
    @WebResult(name = "exportOrgRegistryResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/", partName = "exportOrgRegistryResult")
    public ExportOrgRegistryResult exportOrgRegistry(
        @WebParam(name = "exportOrgRegistryRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/", partName = "exportOrgRegistryRequest")
        ExportOrgRegistryRequest exportOrgRegistryRequest)
        throws Fault
    ;

    /**
     * импорт сведений о поставщиках данных
     * 
     * @param importDataProviderRequest
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_5_0.ImportResult
     * @throws Fault
     */
    @WebMethod(action = "urn:importDataProvider")
    @WebResult(name = "ImportResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", partName = "ImportResult")
    public ImportResult importDataProvider(
        @WebParam(name = "importDataProviderRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/", partName = "importDataProviderRequest")
        ImportDataProviderRequest importDataProviderRequest)
        throws Fault
    ;

    /**
     * экспорт сведений о поставщиках данных
     * 
     * @param exportDataProviderRequest
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry.ExportDataProviderResult
     * @throws Fault
     */
    @WebMethod(action = "urn:exportDataProvider")
    @WebResult(name = "exportDataProviderResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/", partName = "exportDataProviderResult")
    public ExportDataProviderResult exportDataProvider(
        @WebParam(name = "exportDataProviderRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/", partName = "exportDataProviderRequest")
        ExportDataProviderRequest exportDataProviderRequest)
        throws Fault
    ;

    /**
     * импорт сведений об обособленном подразделении
     * 
     * @param importSubsidiaryRequest
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_5_0.ImportResult
     * @throws Fault
     */
    @WebMethod(action = "urn:importSubsidiary")
    @WebResult(name = "ImportResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", partName = "importSubsidiaryResult")
    public ImportResult importSubsidiary(
        @WebParam(name = "importSubsidiaryRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/", partName = "importSubsidiaryRequest")
        ImportSubsidiaryRequest importSubsidiaryRequest)
        throws Fault
    ;

}
