package ru.progmatik.java.pregis.services.organizations.common.service;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.HeaderType;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportDataProviderRequest;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportDataProviderResult;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common_service.Fault;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common_service.RegOrgPortsType;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common_service.RegOrgService;
import ru.progmatik.java.pregis.connectiondb.SaveToBaseMessages;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;
import ru.progmatik.java.web.servlets.socket.ClientService;

import javax.xml.ws.Holder;

public class ExportDataProvider {

    private static final Logger LOGGER = Logger.getLogger(ExportDataProvider.class);

    private static final String NAME_METHOD = "exportDataProvider";
    private ClientService clientService;

    private final RegOrgService service = new RegOrgService();
    private final RegOrgPortsType port = service.getRegOrgPort();

    public ExportDataProvider() {
        OtherFormat.setPortSettings(service, port);
    }

    public ExportDataProvider(ClientService clientService) {
        this();
        this.clientService = clientService;
    }

    public ExportDataProviderResult callExportDataProvide() {

        clientService.addMessage(TextForLog.FORMED_REQUEST + NAME_METHOD);
        HeaderType header = getHeader();

        Holder<ResultHeader> resultHolder = new Holder<>();

        ExportDataProviderResult result;
        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        try {
            clientService.addMessage(TextForLog.SENDING_REQUEST + NAME_METHOD);
            result = port.exportDataProvider(getExportDataProviderRequest(), header, resultHolder);
            clientService.addMessage(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
        } catch (Fault fault) {
            clientService.addMessage(TextForLog.ERROR_RESPONSE + NAME_METHOD);
            clientService.addMessage(fault.getMessage());
            saveToBase.setRequestError(header, NAME_METHOD, fault);
            LOGGER.error(fault.getMessage());
            fault.printStackTrace();
            return null;
        }

        saveToBase.setRequest(header, NAME_METHOD);

        saveToBase.setResult(resultHolder.value, NAME_METHOD, result.getErrorMessage());

        if (result.getErrorMessage() != null) {
            clientService.addMessage(TextForLog.ERROR_MESSAGE);
            clientService.addMessage(TextForLog.ERROR_CODE + result.getErrorMessage().getErrorCode());
            clientService.addMessage(TextForLog.ERROR_DESCRIPION + result.getErrorMessage().getDescription());
            LOGGER.error("ExportOrgRegistry: " + result.getErrorMessage().getErrorCode() + "\n" +
                    result.getErrorMessage().getDescription()  + "\n" + result.getErrorMessage().getStackTrace());
        } else {
            clientService.addMessage(TextForLog.DONE_RESPONSE + NAME_METHOD);
            LOGGER.info("Successful.");
        }

        return result;
    }

    private ExportDataProviderRequest getExportDataProviderRequest() {

        ExportDataProviderRequest exportDataProviderRequest = new ExportDataProviderRequest();
        exportDataProviderRequest.setId("signed-data-container");
//        exportDataProviderRequest.setIsActual(true);

        return exportDataProviderRequest;
    }

    private HeaderType getHeader() {

        HeaderType requestHeader = new HeaderType();
//        requestHeader.setSenderID(BaseOrganization.getSenderID());
        requestHeader.setMessageGUID(OtherFormat.getRandomGUID());
        requestHeader.setDate(OtherFormat.getDateNow());

        return requestHeader;
    }

//    private ResultHeader getResultHeader() {
//        return resultHolder.value;
//    }

}
