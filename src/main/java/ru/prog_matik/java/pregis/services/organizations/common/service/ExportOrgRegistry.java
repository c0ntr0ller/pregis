package ru.prog_matik.java.pregis.services.organizations.common.service;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration._8_7_0.HeaderType;
import ru.gosuslugi.dom.schema.integration._8_7_0.ResultHeader;
import ru.gosuslugi.dom.schema.integration._8_7_0_6.organizations_registry_common.ExportOrgRegistryRequest;
import ru.gosuslugi.dom.schema.integration._8_7_0_6.organizations_registry_common.ExportOrgRegistryResult;
import ru.gosuslugi.dom.schema.integration._8_7_0_6.organizations_registry_common_service.Fault;
import ru.prog_matik.java.pregis.connectiondb.SaveToBaseMessages;
import ru.prog_matik.java.pregis.exception.PreGISException;
import ru.prog_matik.java.pregis.other.OtherFormat;

import javax.xml.ws.Holder;

/**
 * Класс для запроса "экспорт сведений об организациях" ("hcs-organizations-registry-service").
 */
public class ExportOrgRegistry {

    private Logger logger = Logger.getLogger(ExportOrgRegistry.class);

    /**
     * Метод во временной реализации, создаёт запрос на указанных данных.
     * В дальнейшем необходимо реализовать его так, что бы ему передавали объект класса ExportOrgRegistryRequest с параметроми.
     */
    public ExportOrgRegistryResult callExportOrgRegistry() throws Exception {

        String nameMethod = "exportOrgRegistry";

        Holder<ResultHeader> resultHolder = new Holder<>();

        OrgRegistryTransfer transfer = new OrgRegistryTransfer();

        HeaderType header = getHeader();

        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        ExportOrgRegistryResult result;

        try {

//            RegOrgService service = new RegOrgService();
//            RegOrgPortsType port = service.getRegOrgPort();



//            Самописка для ссл

//            Security.addProvider(new BouncyCastleProvider());

            // In case of 1-Way SSL, only the Truststore is necessary
//            TrustManagerFactory tmf= TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//
//            KeyStore trustStore = KeyStore.getInstance("JKS");
//            FileInputStream truststoreFile = new FileInputStream("./data/trust_store.jks");
//            trustStore.load(truststoreFile, "123456".toCharArray());
//            truststoreFile.close();
//            tmf.init(trustStore);
//            TrustManager[] trustMgr= tmf.getTrustManagers();
//
//// In case of 2-Way SSL, keystore and  truststore are necessary
//            KeyManagerFactory kmf= KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//
//            KeyStore keyStore = KeyStore.getInstance("PKCS12");
//            FileInputStream keystoreFile = new FileInputStream("./data/dubovik.p12.pfx");
//            keyStore.load(keystoreFile, "123456".toCharArray());
//            keystoreFile.close();
//            kmf.init(keyStore, "123456".toCharArray());
//
//            KeyManager[] keyMgr = kmf.getKeyManagers();
//
////            TlsClientContext context = TlsClientProtocol
//            SSLContext context = SSLContext.getInstance("TLSv1");
//
//            context.init(keyMgr, trustMgr, null);
//            context.getSocketFactory();
//
////            Самописка для ссл
//
//            RegOrgService service = new RegOrgService();
//            RegOrgPortsType port = service.getRegOrgPort();

//            BindingProvider provider = (BindingProvider) port;
//            provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "test");
//            provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "SDldfls4lz5@!82d");
//            provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://127.0.0.1:8080/ext-bus-org-registry-common-service/services/OrgRegistryCommon");
//            provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/organizations-registry-common-service/");
//            provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://54.76.42.99:60045/ext-bus-org-registry-common-service/services/OrgRegistryCommon");
//            provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://54.76.42.99:60045//ext-bus-org-registry-common-service/services/OrgRegistryCommon");
//            provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, RegOrgService.__getWsdlLocation().toString());
//            provider.getRequestContext().put(JAXWSProperties.SSL_SOCKET_FACTORY, context.getSocketFactory());
//        provider.getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY , true);
//        provider.getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY , "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/");
//        provider.getRequestContext().put( "com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory", sendSOAPMessage.certificateInitialize());

//            return port.exportOrgRegistry(exportOrgRegistryRequest);

            result = transfer.exportOrgRegistry(getExportOrgRegistryRequest(), header, resultHolder);
//            result = port.exportOrgRegistry(getExportOrgRegistryRequest(), header, resultHolder);
        } catch (Fault fault) {
            saveToBase.setRequestError(header, nameMethod, fault);
            logger.error(fault.getMessage());
            fault.printStackTrace();
            return null;
        }

        saveToBase.setRequest(header, nameMethod);

        saveToBase.setResult(resultHolder.value, nameMethod, result.getErrorMessage());

//        AnswerHandlerExportOrgRegistry answer = new AnswerHandlerExportOrgRegistry();
//
//        answer.setOrgRegistryResultToBase(result);

        logger.info("Successful.");

        return result;
    }

    /**
     * Метод формирует необходимые данные для запроса.
     *
     * @return ExportOrgRegistryRequest готовый объект для запроса.
     * @throws PreGISException
     */
    private ExportOrgRegistryRequest getExportOrgRegistryRequest() throws PreGISException {

        ExportOrgRegistryRequest exportOrgRegistryRequest = new ExportOrgRegistryRequest();
        exportOrgRegistryRequest.setId("signed-data-container");
        ExportOrgRegistryRequest.SearchCriteria list = new ExportOrgRegistryRequest.SearchCriteria();
//        list.setOGRN("1116027009702"); // Test ООО "ЖЭУ-1" какой-то г. Иваново.
//        list.setKPP("540201001"); // Test
        list.setOGRN("1125476111903");


        if (list.getOGRN() == null && list.getOGRNIP() == null &&
                list.getOrgRootEntityGUID() == null && list.getOrgVersionGUID() == null)
            throw new PreGISException("Не указан обязательный параметр!\n" +
                    "Укажите, пожалуйста, один из обязательных параметров:\n" +
                    "\"ОГРН\", \"ОГРНИП\", \"Идентификатор версии записи в реестре организаций\"\n" +
                    "или \"Идентификатор корневой сущности организации в реестре организаций\"");

        exportOrgRegistryRequest.getSearchCriteria().add(list);

        return exportOrgRegistryRequest;
    }

    /**
     * Метод формирует заголовок сообщения.
     *
     * @return ISRequestHeader
     */
    private HeaderType getHeader() {

        HeaderType isRequestHeader = new HeaderType();
        isRequestHeader.setDate(OtherFormat.getDateNow());
        isRequestHeader.setMessageGUID(OtherFormat.getRandomGUID());

        return isRequestHeader;
    }
}
