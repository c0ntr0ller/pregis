package ru.prog_matik.java.pregis.services.nsi;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration._8_5_0.RequestHeader;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.nsi.ExportNsiListRequest;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.nsi.ExportNsiListResult;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.nsi_service.Fault;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.nsi_service.NsiPortsType;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.nsi_service.NsiService;
import ru.prog_matik.java.pregis.connectiondb.BaseOrganization;
import ru.prog_matik.java.pregis.connectiondb.SaveToBaseMessages;
import ru.prog_matik.java.pregis.other.OtherFormat;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

public class ExportNsiList {

    private Logger logger = Logger.getLogger(ExportNsiList.class);

    private static final String NAME_METHOD = "exportNsiList";

    private Holder<RequestHeader> headerHolder;

    public void callExportNsiList() {

        headerHolder = new Holder<>(getHeader());

        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        ExportNsiListResult result;

        NsiPortsTypeImpl portsType = new NsiPortsTypeImpl();

        try {
            result = portsType.exportNsiList(getExportNsiListRequest(), headerHolder);


        } catch (Fault fault) {
            saveToBase.setRequestError(headerHolder.value, NAME_METHOD, fault);
            logger.error(fault.getMessage());
            fault.printStackTrace();
            return;
        }
        saveToBase.setRequest(headerHolder.value, NAME_METHOD);

        saveToBase.setResult(headerHolder.value, NAME_METHOD, result.getErrorMessage());

        logger.info("Successful.");

    }

    private ExportNsiListRequest getExportNsiListRequest() {

        ExportNsiListRequest exportNsiListRequest = new ExportNsiListRequest();
        exportNsiListRequest.setId("signed-data-container");

        return exportNsiListRequest;
    }

    private RequestHeader getHeader() {

        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setSenderID(BaseOrganization.getSenderID());
        requestHeader.setMessageGUID(OtherFormat.getRandomGUID());
        requestHeader.setDate(OtherFormat.getDateNow());

        return requestHeader;
    }

//    private RequestHeader getResultHeader() {
//        return headerHolder.value;
//    }

}
