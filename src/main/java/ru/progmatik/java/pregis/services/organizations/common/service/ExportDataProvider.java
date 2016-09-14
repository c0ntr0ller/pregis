package ru.progmatik.java.pregis.services.organizations.common.service;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.HeaderType;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common.ExportDataProviderRequest;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common.ExportDataProviderResult;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common_service.Fault;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common_service.RegOrgPortsType;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common_service.RegOrgService;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;

import javax.xml.ws.Holder;

public class ExportDataProvider {

    private static final Logger LOGGER = Logger.getLogger(ExportDataProvider.class);

    private static final String NAME_METHOD = "exportDataProvider";
    private final RegOrgService service = new RegOrgService();
    private final RegOrgPortsType port = service.getRegOrgPort();
    private AnswerProcessing answerProcessing;

    public ExportDataProvider(AnswerProcessing answerProcessing) {
        OtherFormat.setPortSettings(service, port);
        this.answerProcessing = answerProcessing;
    }

    public ExportDataProviderResult callExportDataProvide() {

        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
        HeaderType header = getHeader();

        Holder<ResultHeader> resultHolder = new Holder<>();

        ExportDataProviderResult result;
//        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST + NAME_METHOD);
            result = port.exportDataProvider(getExportDataProviderRequest(), header, resultHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, header, LOGGER, fault);
//            clientService.sendMessage(TextForLog.ERROR_RESPONSE + NAME_METHOD);
//            clientService.sendMessage(fault.getMessage());
//            saveToBase.setRequestError(header, NAME_METHOD, fault);
//            LOGGER.error(fault.getMessage());
//            fault.printStackTrace();
            return null;
        }

        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, header, resultHolder.value, result.getErrorMessage(), LOGGER);

//        saveToBase.setRequest(header, NAME_METHOD);
//
//        saveToBase.setResult(resultHolder.value, NAME_METHOD, result.getErrorMessage());
//
//        if (result.getErrorMessage() != null) {
//            clientService.sendMessage(TextForLog.ERROR_MESSAGE);
//            clientService.sendMessage(TextForLog.ERROR_CODE + result.getErrorMessage().getErrorCode());
//            clientService.sendMessage(TextForLog.ERROR_DESCRIPTION + result.getErrorMessage().getDescription());
//            LOGGER.error("ExportOrgRegistry: " + result.getErrorMessage().getErrorCode() + "\n" +
//                    result.getErrorMessage().getDescription()  + "\n" + result.getErrorMessage().getStackTrace());
//        } else {
//            clientService.sendMessage(TextForLog.DONE_RESPONSE + NAME_METHOD);
//            LOGGER.info("Successful.");
//        }

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
