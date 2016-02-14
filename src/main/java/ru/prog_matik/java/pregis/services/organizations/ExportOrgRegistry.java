package ru.prog_matik.java.pregis.services.organizations;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration._8_5_0.ISRequestHeader;
import ru.gosuslugi.dom.schema.integration._8_5_0.ResultHeader;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.organizations_registry.ExportOrgRegistryRequest;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.organizations_registry.ExportOrgRegistryResult;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.organizations_registry_service.Fault;
import ru.prog_matik.java.pregis.connectiondb.SaveToBase;
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
    public void callExportOrgRegistry() throws Exception {

        String nameMethod = "exportOrgRegistry";

        StringBuilder stateBuilder = new StringBuilder("OK");

        Holder<ResultHeader> resultHolder = new Holder<>();

        OrgRegistryTransfer transfer = new OrgRegistryTransfer();

        ISRequestHeader header = getHeader();

        SaveToBase saveToBase = new SaveToBase();

        ExportOrgRegistryResult result = null;

        try {
            result = transfer.exportOrgRegistry(getExportOrgRegistryRequest(), header, resultHolder);
        } catch (Fault fault) {
            stateBuilder.setLength(0);
            stateBuilder.append("Нет ответа!");
            stateBuilder.append("\n");
            stateBuilder.append(fault.getMessage());
            stateBuilder.append("\n");
            StackTraceElement[] traceElements = fault.getStackTrace();
            for (int i = 0; i < traceElements.length; i++) {
                stateBuilder.append(traceElements[i].toString());
                stateBuilder.append("\n");
            }
            saveToBase.setRequest(header, stateBuilder.toString(), nameMethod);
            logger.error(fault.getMessage());
            fault.printStackTrace();
            return;
        }

        saveToBase.setRequest(header, stateBuilder.toString(), nameMethod);

        if (result != null && result.getErrorMessage() != null) {
            stateBuilder.setLength(0);
            stateBuilder.append(result.getErrorMessage().getErrorCode());
            stateBuilder.append("\n");
            stateBuilder.append(result.getErrorMessage().getDescription());
            stateBuilder.append("\n");
            stateBuilder.append(result.getErrorMessage().getStackTrace());
        }

        saveToBase.setResult(resultHolder.value, stateBuilder.toString(), nameMethod);

        AnswerHandlerExportOrgRegistry answer = new AnswerHandlerExportOrgRegistry();

        answer.getResult(result, resultHolder);

        logger.info("Successful.");
    }

    /**
     * Метод формирует необходимые данные для запроса.
     * @return ExportOrgRegistryRequest готовый объект для запроса.
     * @throws PreGISException
     */
    private ExportOrgRegistryRequest getExportOrgRegistryRequest() throws PreGISException {

        ExportOrgRegistryRequest exportOrgRegistryRequest = new ExportOrgRegistryRequest();
        exportOrgRegistryRequest.setId("signed-data-container");
        ExportOrgRegistryRequest.SearchCriteria list = new ExportOrgRegistryRequest.SearchCriteria();
//        list.setKPP("540201001");
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
    private ISRequestHeader getHeader() {

        ISRequestHeader isRequestHeader = new ISRequestHeader();
        isRequestHeader.setDate(OtherFormat.getDateNow());
        isRequestHeader.setMessageGUID(OtherFormat.getRandomGUID());

        return isRequestHeader;
    }
}
