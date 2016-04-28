package ru.prog_matik.java.pregis.services.organizations.common.service;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration._8_7_0.HeaderType;
import ru.gosuslugi.dom.schema.integration._8_7_0.ResultHeader;
import ru.gosuslugi.dom.schema.integration._8_7_0_6.organizations_registry_common.ExportDataProviderRequest;
import ru.gosuslugi.dom.schema.integration._8_7_0_6.organizations_registry_common.ExportDataProviderResult;
import ru.gosuslugi.dom.schema.integration._8_7_0_6.organizations_registry_common_service.Fault;
import ru.prog_matik.java.pregis.connectiondb.SaveToBaseMessages;
import ru.prog_matik.java.pregis.other.OtherFormat;

import javax.xml.ws.Holder;

public class ExportDataProvider {

    private Logger logger = Logger.getLogger(ExportDataProvider.class);

    private static final String NAME_METHOD = "exportDataProvider";

    public void callExportDataProvide() {

        OrgRegistryTransfer transfer = new OrgRegistryTransfer();

        HeaderType header = getHeader();

        Holder<ResultHeader> resultHolder = new Holder<>();

        ExportDataProviderResult result;
        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        try {
            result = transfer.exportDataProvider(getExportDataProviderRequest(), header, resultHolder);
        } catch (Fault fault) {
            saveToBase.setRequestError(header, NAME_METHOD, fault);
            logger.error(fault.getMessage());
            fault.printStackTrace();
            return;
        }

        saveToBase.setRequest(header, NAME_METHOD);

        saveToBase.setResult(resultHolder.value, NAME_METHOD, result.getErrorMessage());

        logger.info("Successful.");
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
