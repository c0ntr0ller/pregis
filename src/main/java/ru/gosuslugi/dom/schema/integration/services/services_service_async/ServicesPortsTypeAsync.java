
package ru.gosuslugi.dom.schema.integration.services.services_service_async;

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
import ru.gosuslugi.dom.schema.integration.services.services.ExportCompletedWorksRequest;
import ru.gosuslugi.dom.schema.integration.services.services.ExportHMServicesTarifsRequest;
import ru.gosuslugi.dom.schema.integration.services.services.ExportWorkingListRequest;
import ru.gosuslugi.dom.schema.integration.services.services.ExportWorkingPlanRequest;
import ru.gosuslugi.dom.schema.integration.services.services.GetStateResult;
import ru.gosuslugi.dom.schema.integration.services.services.ImportCompletedWorksRequest;
import ru.gosuslugi.dom.schema.integration.services.services.ImportWorkingListRequest;
import ru.gosuslugi.dom.schema.integration.services.services.ImportWorkingPlanRequest;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ServicesPortsTypeAsync", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/services-service-async/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration.services.services.ObjectFactory.class,
    ru.gosuslugi.dom.schema.integration.base.ObjectFactory.class,
    org.w3._2000._09.xmldsig_.ObjectFactory.class
})
public interface ServicesPortsTypeAsync {


    /**
     * 
     * @param getStateRequest
     * @param header0
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration.services.services.GetStateResult
     * @throws Fault
     */
    @WebMethod(action = "urn:getState")
    @WebResult(name = "getStateResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/services/", partName = "getStateResult")
    public GetStateResult getState(
        @WebParam(name = "getStateRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", partName = "getStateRequest")
        GetStateRequest getStateRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * ВИ_ИПОЧ_УК_ТФ_ЭКСП. экспорт тарифов ЖКУ
     * 
     * @param header0
     * @param exportHMServicesTarifsDataRequest
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration.base.AckRequest
     * @throws Fault
     */
    @WebMethod(action = "urn:exportHMServicesTarifs")
    @WebResult(name = "AckRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", partName = "AckRequest")
    public AckRequest exportHMServicesTarifs(
        @WebParam(name = "exportHMServicesTarifsRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/services/", partName = "exportHMServicesTarifsDataRequest")
        ExportHMServicesTarifsRequest exportHMServicesTarifsDataRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * ВИ_ИПФР_ПЕР_ЭКСП. экспорт основных сведений по перечню работ
     * 
     * @param header0
     * @param exportWorkingListRequest
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration.base.AckRequest
     * @throws Fault
     */
    @WebMethod(action = "urn:exportWorkingList")
    @WebResult(name = "AckRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", partName = "AckRequest")
    public AckRequest exportWorkingList(
        @WebParam(name = "exportWorkingListRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/services/", partName = "exportWorkingListRequest")
        ExportWorkingListRequest exportWorkingListRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * ВИ_ИПФР_ПЕР_ИМП. импорт основных сведений по перечню работ
     * 
     * @param importWorkingListRequest
     * @param header0
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration.base.AckRequest
     * @throws Fault
     */
    @WebMethod(action = "urn:importWorkingList")
    @WebResult(name = "AckRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", partName = "AckRequest")
    public AckRequest importWorkingList(
        @WebParam(name = "importWorkingListRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/services/", partName = "importWorkingListRequest")
        ImportWorkingListRequest importWorkingListRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * ВИ_ИПФР_ПЛАН_ИМП. импорт актуальных планов по перечню работ/услуг
     * 
     * @param importWorkingPlanRequest
     * @param header0
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration.base.AckRequest
     * @throws Fault
     */
    @WebMethod(action = "urn:importWorkingPlan")
    @WebResult(name = "AckRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", partName = "AckRequest")
    public AckRequest importWorkingPlan(
        @WebParam(name = "importWorkingPlanRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/services/", partName = "importWorkingPlanRequest")
        ImportWorkingPlanRequest importWorkingPlanRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * ВИ_ИПФР_ПЛАН_ЭКСП. экспорт актуальных планов по перечню работ/услуг
     * 
     * @param header0
     * @param header
     * @param exportWorkingPlanRequest
     * @return
     *     returns ru.gosuslugi.dom.schema.integration.base.AckRequest
     * @throws Fault
     */
    @WebMethod(action = "urn:exportWorkingPlan")
    @WebResult(name = "AckRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", partName = "AckRequest")
    public AckRequest exportWorkingPlan(
        @WebParam(name = "exportWorkingPlanRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/services/", partName = "exportWorkingPlanRequest")
        ExportWorkingPlanRequest exportWorkingPlanRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * ВИ_ИПФР_ВЫП_ИМП. импорт сведений о выполненных работах и услугах
     * 
     * @param importCompletedWorksRequest
     * @param header0
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration.base.AckRequest
     * @throws Fault
     */
    @WebMethod(action = "urn:importCompletedWorks")
    @WebResult(name = "AckRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", partName = "AckRequest")
    public AckRequest importCompletedWorks(
        @WebParam(name = "importCompletedWorksRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/services/", partName = "importCompletedWorksRequest")
        ImportCompletedWorksRequest importCompletedWorksRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * ВИ_ИПФР_ВЫП_ЭКСП. экспорт сведений о выполненных работах и услугах
     * 
     * @param header0
     * @param exportCompletedWorksRequest
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration.base.AckRequest
     * @throws Fault
     */
    @WebMethod(action = "urn:exportCompletedWorks")
    @WebResult(name = "AckRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", partName = "AckRequest")
    public AckRequest exportCompletedWorks(
        @WebParam(name = "exportCompletedWorksRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/services/", partName = "exportCompletedWorksRequest")
        ExportCompletedWorksRequest exportCompletedWorksRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

}