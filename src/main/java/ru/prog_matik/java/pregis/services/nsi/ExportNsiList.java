package ru.prog_matik.java.pregis.services.nsi;

import ru.gosuslugi.dom.schema.integration._8_5_0.RequestHeader;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.nsi.ExportNsiListRequest;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.nsi.ExportNsiListResult;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.nsi_service.Fault;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.nsi_service.NsiPortsType;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.nsi_service.NsiService;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

public class ExportNsiList {

    private Holder<RequestHeader> headerHolder = new Holder<>();

    private static ExportNsiListResult exportNsiList(ExportNsiListRequest exportNsiListRequest, Holder<RequestHeader> header) throws Fault {

        NsiService service = new NsiService();
       NsiPortsType port = service.getNsiPort();

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "test");
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "SDldfls4lz5@!82d");
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, NsiService.__getWsdlLocation().toString());

        return port.exportNsiList(exportNsiListRequest, header);
    }

    public void callExportNsiList() {

    }

    private ExportNsiListRequest getExportNsiListRequest() {

        ExportNsiListRequest exportNsiListRequest = new ExportNsiListRequest();
        exportNsiListRequest.setId("signed-data-container");

        return exportNsiListRequest;
    }

    private void getHeader() {

    }

}
