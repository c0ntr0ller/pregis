package ru.progmatik.java.pregis.services.organizations.common.service;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.HeaderType;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common.ExportOrgRegistryRequest;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common.ExportOrgRegistryResult;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common_service.Fault;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common_service.RegOrgPortsType;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common_service.RegOrgService;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;
import ru.progmatik.java.pregis.other.TextForLog;

import javax.xml.ws.Holder;

/**
 * Класс для запроса "экспорт сведений об организациях" ("hcs-organizations-registry-service").
 */
public class ExportOrgRegistry {

    private static final Logger LOGGER = Logger.getLogger(ExportOrgRegistry.class);
    private static final String NAME_METHOD = "exportOrgRegistry";

    private final RegOrgService service = new RegOrgService();
    private final RegOrgPortsType port = service.getRegOrgPort();
    private AnswerProcessing answerProcessing;

    public ExportOrgRegistry(AnswerProcessing answerProcessing) {
        OtherFormat.setPortSettings(service, port);
        this.answerProcessing = answerProcessing;
    }

    /**
     * Метод во временной реализации, создаёт запрос на указанных данных.
     * В дальнейшем необходимо реализовать его так, что бы ему передавали объект класса ExportOrgRegistryRequest с параметроми.
     */
    public ExportOrgRegistryResult callExportOrgRegistry(ExportOrgRegistryRequest request) {

        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);

        Holder<ResultHeader> resultHolder = new Holder<>();

        HeaderType header = getHeader();

//        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        ExportOrgRegistryResult result;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            result =  port.exportOrgRegistry(request, header, resultHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);

        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, header, LOGGER, fault);
            return null;

        }

        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, header, resultHolder.value, result.getErrorMessage(), LOGGER);

        return result;
    }

    /**
     * Метод, получает данные текущей организации.
     * @return сформированный запрос.
     * @throws PreGISException
     */
    public ExportOrgRegistryRequest getExportOrgRegistryRequest() throws PreGISException {

        ExportOrgRegistryRequest.SearchCriteria list = new ExportOrgRegistryRequest.SearchCriteria();
        list.setOGRN(ResourcesUtil.instance().getOGRNCompany());

        return getExportOrgRegistryRequest(list);
    }

    /**
     * Метод формирует необходимые данные для запроса.
     *
     * @return ExportOrgRegistryRequest готовый объект для запроса.
     * @throws PreGISException
     */
    public ExportOrgRegistryRequest getExportOrgRegistryRequest(ExportOrgRegistryRequest.SearchCriteria criteria) throws PreGISException {

        ExportOrgRegistryRequest exportOrgRegistryRequest = new ExportOrgRegistryRequest();
        exportOrgRegistryRequest.setId("signed-data-container");
//        list.setOGRN("1116027009702"); // Test ООО "ЖЭУ-1" какой-то г. Иваново.
//        list.setKPP("540201001"); // Test
//        list.setOGRN("1125476111903");


        if (criteria.getOGRN() == null && criteria.getOGRNIP() == null &&
                criteria.getOrgRootEntityGUID() == null && criteria.getOrgVersionGUID() == null)
            throw new PreGISException("Не указан обязательный параметр!\n" +
                    "Укажите, пожалуйста, один из обязательных параметров:\n" +
                    "\"ОГРН\", \"ОГРНИП\", \"Идентификатор версии записи в реестре организаций\"\n" +
                    "или \"Идентификатор корневой сущности организации в реестре организаций\"");

        exportOrgRegistryRequest.getSearchCriteria().add(criteria);

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
