
package ru.gosuslugi.dom.schema.integration._8_6_0_4.nsi_common_service;

import javax.jws.*;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import ru.gosuslugi.dom.schema.integration._8_6_0.ISRequestHeader;
import ru.gosuslugi.dom.schema.integration._8_6_0.ResultHeader;
import ru.gosuslugi.dom.schema.integration._8_6_0_4.nsi_common.ExportNsiItemRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_4.nsi_common.ExportNsiItemResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_4.nsi_common.ExportNsiListRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_4.nsi_common.ExportNsiListResult;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "NsiPortsType", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi-common-service/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@HandlerChain(file = "handler/ClientMessage_handler.xml")
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_6_0.ObjectFactory.class,
    org.w3._2000._09.xmldsig_.ObjectFactory.class,
    ru.gosuslugi.dom.schema.integration._8_6_0_4.nsi_common.ObjectFactory.class
})
public interface NsiPortsType {


    /**
     * ВИ_НСИ_ППС. Получить перечень справочников с указанием даты последнего изменения каждого из них.
     * 
     * @param exportNsiListRequest
     * @param header0
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_6_0_4.nsi_common.ExportNsiListResult
     * @throws Fault
     */
    @WebMethod(action = "urn:exportNsiList")
    @WebResult(name = "exportNsiListResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi-common/", partName = "exportNsiListResult")
    public ExportNsiListResult exportNsiList(
        @WebParam(name = "exportNsiListRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi-common/", partName = "exportNsiListRequest")
        ExportNsiListRequest exportNsiListRequest,
        @WebParam(name = "ISRequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", header = true, partName = "Header")
        ISRequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * ВИ_НСИ_ПДС. Получить данные справочника.
     * 
     * @param header0
     * @param exportNsiItemRequest
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_6_0_4.nsi_common.ExportNsiItemResult
     * @throws Fault
     */
    @WebMethod(action = "urn:exportNsiItem")
    @WebResult(name = "exportNsiItemResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi-common/", partName = "exportNsiItemResult")
    public ExportNsiItemResult exportNsiItem(
        @WebParam(name = "exportNsiItemRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi-common/", partName = "exportNsiItemRequest")
        ExportNsiItemRequest exportNsiItemRequest,
        @WebParam(name = "ISRequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", header = true, partName = "Header")
        ISRequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

}
