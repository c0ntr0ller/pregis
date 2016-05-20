package ru.progmatik.java.pregis.services.organizations.common.service;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.HeaderType;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportOrgRegistryRequest;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportOrgRegistryResult;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common_service.Fault;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common_service.RegOrgPortsType;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common_service.RegOrgService;
import ru.progmatik.java.pregis.connectiondb.SaveToBaseMessages;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;
import ru.progmatik.java.pregis.other.TextForLog;

import javax.xml.ws.Holder;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Класс для запроса "экспорт сведений об организациях" ("hcs-organizations-registry-service").
 */
public class ExportOrgRegistry {

    private static final Logger LOGGER = Logger.getLogger(ExportOrgRegistry.class);

    private final RegOrgService service = new RegOrgService();
    private final RegOrgPortsType port = service.getRegOrgPort();
    private List<String> listState;

    public ExportOrgRegistry() throws NoSuchMethodException, MalformedURLException, IllegalAccessException, InvocationTargetException {
        OtherFormat.setPortSettings(service, port);
    }

    public ExportOrgRegistry(List<String> listState) throws InvocationTargetException, NoSuchMethodException, MalformedURLException, IllegalAccessException {
        this();
        this.listState = listState;
    }

    /**
     * Метод во временной реализации, создаёт запрос на указанных данных.
     * В дальнейшем необходимо реализовать его так, что бы ему передавали объект класса ExportOrgRegistryRequest с параметроми.
     */
    public ExportOrgRegistryResult callExportOrgRegistry() throws Exception {

        listState.add(TextForLog.FORMED_REQUEST);

        String nameMethod = "exportOrgRegistry";

        Holder<ResultHeader> resultHolder = new Holder<>();

        HeaderType header = getHeader();

        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        ExportOrgRegistryResult result;

        try {
            listState.add(TextForLog.SENDING_REQUEST);
            result =  port.exportOrgRegistry(getExportOrgRegistryRequest(), header, resultHolder);
            listState.add(TextForLog.RECEIVED_RESPONSE + nameMethod);
        } catch (Fault fault) {
            listState.add(TextForLog.ERROR_RESPONSE + nameMethod);
            saveToBase.setRequestError(header, nameMethod, fault);
            LOGGER.error(fault.getMessage());
            fault.printStackTrace();
            return null;
        }

        saveToBase.setRequest(header, nameMethod);

        saveToBase.setResult(resultHolder.value, nameMethod, result.getErrorMessage());

//        AnswerHandlerExportOrgRegistry answer = new AnswerHandlerExportOrgRegistry();
//
//        answer.setOrgRegistryResultToBase(result);

        LOGGER.info("Successful.");

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
//        list.setOGRN("1125476111903");
        list.setOGRN(ResourcesUtil.instance().getOGRNCompany());


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
