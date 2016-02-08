package ru.prog_matik.java.pregis.services.nsi;

import ru.gosuslugi.dom.schema.integration._8_5_0.RequestHeader;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.nsi.ExportNsiListRequest;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.nsi.ExportNsiListResult;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.nsi_service.Fault;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.nsi_service.NsiPortsType;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.nsi_service.NsiService;
import ru.prog_matik.java.pregis.other.OtherFormat;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

public class ExportNsiList {

    private Holder<RequestHeader> headerHolder;

    private static ExportNsiListResult exportNsiList(ExportNsiListRequest exportNsiListRequest, Holder<RequestHeader> header) throws Fault {

        NsiService service = new NsiService();
       NsiPortsType port = service.getNsiPort();

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "test");
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "SDldfls4lz5@!82d");
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, NsiService.__getWsdlLocation().toString());

        return port.exportNsiList(exportNsiListRequest, header);
    }

    public void callExportNsiList() throws Fault {

        headerHolder = new Holder<>(getHeader());

        ExportNsiListResult result;

        try {
            result = exportNsiList(getExportNsiListRequest(), headerHolder);

            System.out.println();
            System.err.println(result.getErrorMessage().getErrorCode());
            System.err.println(result.getErrorMessage().getDescription());

            System.out.println("getResultHeader Date: " + getResultHeader().getDate());
            System.out.println("getResultHeader MessageGUID: " + getResultHeader().getMessageGUID());

        } catch (Fault e) {
            System.out.println(e.getMessage());
        }




//        logger.debug("Successful.");

    }

    private ExportNsiListRequest getExportNsiListRequest() {

        ExportNsiListRequest exportNsiListRequest = new ExportNsiListRequest();
        exportNsiListRequest.setId("signed-data-container");

        return exportNsiListRequest;
    }

    private RequestHeader getHeader() {

        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setDate(OtherFormat.getDateNow());
        requestHeader.setMessageGUID(OtherFormat.getRandomGUID());

        return requestHeader;
    }

    private RequestHeader getResultHeader() {
        return headerHolder.value;
    }

}
