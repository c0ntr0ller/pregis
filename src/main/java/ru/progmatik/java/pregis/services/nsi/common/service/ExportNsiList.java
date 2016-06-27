package ru.progmatik.java.pregis.services.nsi.common.service;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.HeaderType;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiListRequest;
import ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiListResult;
import ru.gosuslugi.dom.schema.integration.services.nsi_common_service.Fault;
import ru.progmatik.java.pregis.connectiondb.SaveToBaseMessages;
import ru.progmatik.java.pregis.other.OtherFormat;

import javax.xml.ws.Holder;

public class ExportNsiList {

    private static final Logger LOGGER = Logger.getLogger(ExportNsiList.class);

    private static final String NAME_METHOD = "exportNsiList";

    public void callExportNsiList(NsiListGroupEnum nsiType) {

        HeaderType requestHeader = OtherFormat.getISRequestHeader();

        Holder<ResultHeader> headerHolder = new Holder<>();

        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        ExportNsiListResult result;

        NsiPortsTypeImpl portsType = new NsiPortsTypeImpl();

        try {
            result = portsType.exportNsiList(getExportNsiListRequest(nsiType), requestHeader, headerHolder);

        } catch (Fault fault) {
            saveToBase.setRequestError(requestHeader, NAME_METHOD, fault);
            LOGGER.error(fault.getMessage());
            fault.printStackTrace();
            return;
        }
        saveToBase.setRequest(requestHeader, NAME_METHOD);

        saveToBase.setResult(headerHolder.value, NAME_METHOD, result.getErrorMessage());

        LOGGER.info("Successful.");

    }

    private ExportNsiListRequest getExportNsiListRequest(NsiListGroupEnum nsiType) {

        ExportNsiListRequest exportNsiListRequest = new ExportNsiListRequest();
        exportNsiListRequest.setId("signed-data-container");
//        exportNsiListRequest.setListGroup("NSIRAO");
        exportNsiListRequest.setListGroup(nsiType.getNsi());

//        exportNsiListRequest.setSignature(new SignatureType());

        return exportNsiListRequest;
    }


//    private RequestHeader getResultHeader() {
//        return headerHolder.value;
//    }

}
