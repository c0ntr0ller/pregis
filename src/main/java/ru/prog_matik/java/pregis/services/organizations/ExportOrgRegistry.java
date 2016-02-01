package ru.prog_matik.java.pregis.services.organizations;

import org.apache.log4j.Logger;
import org.w3._2000._09.xmldsig_.SignatureType;
import ru.gosuslugi.dom.schema.integration._8_5_0.ResultHeader;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry.ExportOrgRegistryRequest;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry.ExportOrgRegistryResult;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry.ISRequestHeader;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry_service.Fault;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry_service.RegOrgPortsType;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry_service.RegOrgService;
import ru.prog_matik.java.pregis.other.OtherFormat;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceClient;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.List;

public class ExportOrgRegistry {

    private Logger logger = Logger.getLogger(ExportOrgRegistry.class);

    private ResultHeader resultHeader;
    private Holder<ResultHeader> resultHolder = new Holder(resultHeader);

    /**
     * Метод формирует запрос и отправляет на серавер.
     *
     * @param isRequestHeader
     * @param exportOrgRegistryRequest
     * @return ExportOrgRegistryResult
     * @throws Fault
     */
    private static ExportOrgRegistryResult exportOrgRegistry(ISRequestHeader isRequestHeader, ExportOrgRegistryRequest exportOrgRegistryRequest, Holder<ResultHeader> resultHolder) throws Fault, NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, NoSuchProviderException, UnrecoverableKeyException, KeyManagementException {

        RegOrgService service = new RegOrgService();
        RegOrgPortsType port = service.getRegOrgPort();

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "test");
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "SDldfls4lz5@!82d");
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, RegOrgService.__getWsdlLocation().toString());
//        provider.getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY , true);
//        provider.getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY , "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/");
//        provider.getRequestContext().put( "com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory", sendSOAPMessage.certificateInitialize());

        return port.exportOrgRegistry(exportOrgRegistryRequest, isRequestHeader, resultHolder);
    }



//    public void sendMessage() {
//        try {
//
//            SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
//
////            Формируем загаловок с датой и идентификатором сообщения
//            Marshaller marshallerHeader = JAXBContext.newInstance(ISRequestHeader.class).createMarshaller();
//            marshallerHeader.marshal(getHeader(), soapMessage.getSOAPPart().getEnvelope().getHeader());
//
////            Формируем бизнес данные
//            Marshaller marshallerBody = JAXBContext.newInstance(ExportOrgRegistryRequest.class).createMarshaller();
//            marshallerBody.marshal(getExportOrgRegistryRequest(), soapMessage.getSOAPPart().getEnvelope().getBody());
//
////            сохраняем
//            soapMessage.saveChanges();
//
////            Отправляем сообщение в формате SOAPMessage
//            SendSOAPMessage sendSOAPMessage = new SendSOAPMessage();
//            SOAPMessage result = sendSOAPMessage.sendSOAP(soapMessage, new URL("https://54.76.42.99:60045/ext-bus-org-registry-service/services/OrgRegistry"));
//
//
//        } catch (SOAPException | JAXBException | MalformedURLException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Метод во временной реализации, создаёт запрос на указанных данных.
     * В дальнейшем необходимо реализовать его так, что бы ему передавали объект класса ExportOrgRegistryRequest с параметроми.
     */
    public void callExportOrgRegistry() throws Exception {

//        ExportOrgRegistryRequest exportOrgRegistryRequest = new ExportOrgRegistryRequest();
//        exportOrgRegistryRequest.setId("signed-data-container");
////        exportOrgRegistryRequest.setId("1");
//        ExportOrgRegistryRequest.SearchCriteria list = new ExportOrgRegistryRequest.SearchCriteria();
//        list.setOGRN("1125476111903");
//        exportOrgRegistryRequest.getSearchCriteria().add(list);

//        SignatureType signatureType = new SignatureType();
//        exportOrgRegistryRequest.setSignature(signatureType);


        ExportOrgRegistryResult result = exportOrgRegistry(getHeader(), getExportOrgRegistryRequest(), resultHolder);
        System.out.println();
        System.err.println(result.getErrorMessage().getErrorCode());
        System.err.println(result.getErrorMessage().getDescription());

        System.out.println("getResultHeader Date: " + getResultHeader().getDate());
        System.out.println("getResultHeader MessageGUID: " + getResultHeader().getMessageGUID());

        logger.debug("Successful.");
    }

    private ExportOrgRegistryRequest getExportOrgRegistryRequest() {

        ExportOrgRegistryRequest exportOrgRegistryRequest = new ExportOrgRegistryRequest();
        exportOrgRegistryRequest.setId("signed-data-container");
        ExportOrgRegistryRequest.SearchCriteria list = new ExportOrgRegistryRequest.SearchCriteria();
        list.setOGRN("1125476111903");
        exportOrgRegistryRequest.getSearchCriteria().add(list);

        return exportOrgRegistryRequest;
    }

    /**
     * Метод формирует заголовок XML.
     *
     * @return ISRequestHeader
     */
    private ISRequestHeader getHeader() {

        ISRequestHeader isRequestHeader = new ISRequestHeader();
        isRequestHeader.setDate(OtherFormat.getDateNow());
        isRequestHeader.setMessageGUID(OtherFormat.getRandomGUID());

        return isRequestHeader;
    }

    public ResultHeader getResultHeader() {
        resultHeader = resultHolder.value;
        return resultHeader;
    }

    public void setResultHeader(ResultHeader resultHeader) {
        this.resultHeader = resultHeader;
    }
}
