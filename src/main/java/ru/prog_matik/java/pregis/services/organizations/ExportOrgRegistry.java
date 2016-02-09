package ru.prog_matik.java.pregis.services.organizations;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration._8_5_0.ResultHeader;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.organizations_registry.ExportOrgRegistryRequest;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.organizations_registry.ExportOrgRegistryResult;
import ru.gosuslugi.dom.schema.integration._8_5_0.ISRequestHeader;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.organizations_registry.ExportOrgRegistryResultType;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.organizations_registry_service.Fault;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.organizations_registry_service.RegOrgPortsType;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.organizations_registry_service.RegOrgService;
import ru.prog_matik.java.pregis.exception.PreGISException;
import ru.prog_matik.java.pregis.other.OtherFormat;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.List;

public class ExportOrgRegistry {

    private Logger logger = Logger.getLogger(ExportOrgRegistry.class);

    /**
     * Метод формирует запрос и отправляет на серавер.
     *
     * @param isRequestHeader          - Обязательный загаловок сообщения (Дата, идентификатор сообщения).
     * @param exportOrgRegistryRequest - Тело письма содержащее бизнес логику.
     * @param resultHolder             - Заголовок письма полученый в ответ от сервера.
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

        return port.exportOrgRegistry(exportOrgRegistryRequest, isRequestHeader, resultHolder);
    }

    /**
     * Метод во временной реализации, создаёт запрос на указанных данных.
     * В дальнейшем необходимо реализовать его так, что бы ему передавали объект класса ExportOrgRegistryRequest с параметроми.
     */
    public void callExportOrgRegistry() throws Exception {

        Holder<ResultHeader> resultHolder = new Holder<>();

        ExportOrgRegistryResult result = exportOrgRegistry(getHeader(), getExportOrgRegistryRequest(), resultHolder);

        AnswerHandlerExportOrgRegistry answer = new AnswerHandlerExportOrgRegistry();
        answer.getResult(result, resultHolder);


        logger.debug("Successful.");
    }

    private ExportOrgRegistryRequest getExportOrgRegistryRequest() throws PreGISException {

        ExportOrgRegistryRequest exportOrgRegistryRequest = new ExportOrgRegistryRequest();
        exportOrgRegistryRequest.setId("signed-data-container");
        ExportOrgRegistryRequest.SearchCriteria list = new ExportOrgRegistryRequest.SearchCriteria();
        list.setKPP("540201001");
//        list.setOGRN("1125476111903");

        if (list.getOGRN() == null && list.getOGRNIP() == null &&
                list.getOrgRootEntityGUID() == null && list.getOrgVersionGUID() == null)
            throw new PreGISException("Не указан обязательный параметр!\n" +
                    "Укажите, пожалуйста, один из обязательных параметров:\n" +
                    "\"ОГРН\", \"ОГРНИП\", \"Идентификатор версии записи в реестре организаций\"\n" +
                    "или \"Идентификатор корневой сущности организации в реестре организаций\"");

        exportOrgRegistryRequest.getSearchCriteria().add(list);
//
//        SignatureType signatureType = new SignatureType();
//        exportOrgRegistryRequest.setSignature(signatureType);

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
}
