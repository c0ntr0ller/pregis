
package ru.gosuslugi.dom.schema.integration._8_7_0_4.bills_service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import ru.gosuslugi.dom.schema.integration._8_7_0.ImportResult;
import ru.gosuslugi.dom.schema.integration._8_7_0.RequestHeader;
import ru.gosuslugi.dom.schema.integration._8_7_0.ResultHeader;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.bills.CloseHousePaymentPeriodRequest;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.bills.ExportNotificationsOfOrderExecutionRequest;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.bills.ExportNotificationsOfOrderExecutionResult;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.bills.ExportOrgPeriodRequest;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.bills.ExportOrgPeriodResult;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.bills.ExportPaymentDocumentRequest;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.bills.ExportPaymentDocumentResult;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.bills.ImportAcknowledgmentRequest;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.bills.ImportPaymentDocumentRequest;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.bills.OpenOrgPaymentPeriodRequest;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "BillsPortsType", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/bills-service/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_7_0_4.bills.ObjectFactory.class,
    ru.gosuslugi.dom.schema.integration._8_7_0.ObjectFactory.class,
    org.w3._2000._09.xmldsig_.ObjectFactory.class
})
public interface BillsPortsType {


    /**
     * ВИ_ИЛС_РПО_ОТК. открыть расчетный период организации
     * 
     * @param openOrgPaymentPeriodRequest
     * @param header0
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_7_0.ImportResult
     * @throws Fault
     */
    @WebMethod(action = "urn:openOrgPaymentPeriod")
    @WebResult(name = "ImportResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", partName = "openOrgPaymentPeriodResult")
    public ImportResult openOrgPaymentPeriod(
        @WebParam(name = "openOrgPaymentPeriodRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/bills/", partName = "openOrgPaymentPeriodRequest")
        OpenOrgPaymentPeriodRequest openOrgPaymentPeriodRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * ВИ_ИЛС_РПД_ЗАК. закрыть расчетный период дома
     * 
     * @param closeHousePaymentPeriodRequest
     * @param header0
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_7_0.ImportResult
     * @throws Fault
     */
    @WebMethod(action = "urn:closeHousePaymentPeriod")
    @WebResult(name = "ImportResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", partName = "closeHousePaymentPeriodResult")
    public ImportResult closeHousePaymentPeriod(
        @WebParam(name = "closeHousePaymentPeriodRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/bills/", partName = "closeHousePaymentPeriodRequest")
        CloseHousePaymentPeriodRequest closeHousePaymentPeriodRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * ВИ_ИЛС_ПД_ИМП. импорт платежных документов
     * 
     * @param header0
     * @param importPaymentDocumentDataRequest
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_7_0.ImportResult
     * @throws Fault
     */
    @WebMethod(action = "urn:importPaymentDocumentData")
    @WebResult(name = "ImportResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", partName = "importPaymentDocumentDataResult")
    public ImportResult importPaymentDocumentData(
        @WebParam(name = "importPaymentDocumentRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/bills/", partName = "importPaymentDocumentDataRequest")
        ImportPaymentDocumentRequest importPaymentDocumentDataRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * ВИ_ИЛС_ПД_ЭКСП. экспорт платежных документов
     * 
     * @param header0
     * @param exportPaymentDocumentDataRequest
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_7_0_4.bills.ExportPaymentDocumentResult
     * @throws Fault
     */
    @WebMethod(action = "urn:exportPaymentDocumentData")
    @WebResult(name = "exportPaymentDocumentResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/bills/", partName = "exportPaymentDocumentDataResult")
    public ExportPaymentDocumentResult exportPaymentDocumentData(
        @WebParam(name = "exportPaymentDocumentRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/bills/", partName = "exportPaymentDocumentDataRequest")
        ExportPaymentDocumentRequest exportPaymentDocumentDataRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * ВИ_ИЛС_РПО_ЭКСП. получить расчетные периоды организации
     * 
     * @param header0
     * @param exportOrgPeriodDataRequest
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_7_0_4.bills.ExportOrgPeriodResult
     * @throws Fault
     */
    @WebMethod(action = "urn:exportOrgPeriodData")
    @WebResult(name = "exportOrgPeriodResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/bills/", partName = "exportOrgPeriodDataResult")
    public ExportOrgPeriodResult exportOrgPeriodData(
        @WebParam(name = "exportOrgPeriodRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/bills/", partName = "exportOrgPeriodDataRequest")
        ExportOrgPeriodRequest exportOrgPeriodDataRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

    /**
     * Экспорт изввещений о принятии к исполнению распоряжений с результатами квитирования
     * 
     * @param exportNotificationsOfOrderExecutionRequest
     * @param header0
     * @param header
     * @return
     *     returns ru.gosuslugi.dom.schema.integration._8_7_0_4.bills.ExportNotificationsOfOrderExecutionResult
     * @throws Fault
     */
    @WebMethod(action = "urn:exportNotificationsOfOrderExecution")
    @WebResult(name = "exportNotificationsOfOrderExecutionResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/bills/", partName = "exportNotificationsOfOrderExecutionResult")
    public ExportNotificationsOfOrderExecutionResult exportNotificationsOfOrderExecution(
        @WebParam(name = "exportNotificationsOfOrderExecutionRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/bills/", partName = "exportNotificationsOfOrderExecutionRequest")
        ExportNotificationsOfOrderExecutionRequest exportNotificationsOfOrderExecutionRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
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
     *     returns ru.gosuslugi.dom.schema.integration._8_7_0.ImportResult
     * @throws Fault
     */
    @WebMethod(action = "urn:importAcknowledgment")
    @WebResult(name = "ImportResult", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", partName = "importAcknowledgmentResult")
    public ImportResult importAcknowledgment(
        @WebParam(name = "importAcknowledgmentRequest", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/bills/", partName = "importAcknowledgmentRequest")
        ImportAcknowledgmentRequest importAcknowledgmentRequest,
        @WebParam(name = "RequestHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, partName = "Header")
        RequestHeader header,
        @WebParam(name = "ResultHeader", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", header = true, mode = WebParam.Mode.OUT, partName = "Header")
        Holder<ResultHeader> header0)
        throws Fault
    ;

}
