package ru.prog_matik.java.pregis.services.nsi;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration._8_6_0.ISRequestHeader;
import ru.gosuslugi.dom.schema.integration._8_6_0.ResultHeader;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.nsi_common.ExportNsiListRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.nsi_common.ExportNsiListResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.nsi_common_service.Fault;
import ru.prog_matik.java.pregis.connectiondb.SaveToBaseMessages;
import ru.prog_matik.java.pregis.other.OtherFormat;
import ru.prog_matik.java.pregis.services.nsi.common.service.NsiPortsTypeImpl;

import javax.xml.ws.Holder;

public class ExportNsiList {

    private Logger logger = Logger.getLogger(ExportNsiList.class);

    private static final String NAME_METHOD = "exportNsiList";

    public void callExportNsiList() {

        ISRequestHeader requestHeader = OtherFormat.getISRequestHeader();

        Holder<ResultHeader> headerHolder = new Holder<>();

        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        ExportNsiListResult result;

        NsiPortsTypeImpl portsType = new NsiPortsTypeImpl();

        try {
            result = portsType.exportNsiList(getExportNsiListRequest(), requestHeader, headerHolder);


        } catch (Fault fault) {
            saveToBase.setRequestError(requestHeader, NAME_METHOD, fault);
            logger.error(fault.getMessage());
            fault.printStackTrace();
            return;
        }
        saveToBase.setRequest(requestHeader, NAME_METHOD);

        saveToBase.setResult(headerHolder.value, NAME_METHOD, result.getErrorMessage());

        logger.info("Successful.");

    }

    private ExportNsiListRequest getExportNsiListRequest() {

        ExportNsiListRequest exportNsiListRequest = new ExportNsiListRequest();
        exportNsiListRequest.setId("signed-data-container");


//        exportNsiListRequest.setSignature(new SignatureType());

        return exportNsiListRequest;
    }


//    private RequestHeader getResultHeader() {
//        return headerHolder.value;
//    }

}
