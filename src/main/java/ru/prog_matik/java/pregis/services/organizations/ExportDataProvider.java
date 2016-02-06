package ru.prog_matik.java.pregis.services.organizations;

import ru.gosuslugi.dom.schema.integration._8_5_0.ResultHeader;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry.ExportDataProviderRequest;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry.ExportDataProviderResult;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry.ISRequestHeader;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry_service.Fault;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry_service.RegOrgPortsType;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry_service.RegOrgService;
import ru.prog_matik.java.pregis.other.OtherFormat;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

public class ExportDataProvider {

    private Holder<ResultHeader> resultHolder = new Holder<>();

    private ExportDataProviderResult exportDataProvider(ExportDataProviderRequest exportDataProviderRequest, ISRequestHeader isRequestHeader, Holder<ResultHeader> resultHolder) throws Fault {

        RegOrgService service = new RegOrgService();
        RegOrgPortsType port = service.getRegOrgPort();

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "test");
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "SDldfls4lz5@!82d");
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, RegOrgService.__getWsdlLocation().toString());

        return port.exportDataProvider(exportDataProviderRequest, isRequestHeader, resultHolder);
    }

    public void callExportDataProvide() throws Fault {

        ExportDataProviderResult result = exportDataProvider(getExportDataProviderRequest(), getHeader(), resultHolder);
        System.out.println();
        System.err.println(result.getErrorMessage().getErrorCode());
        System.err.println(result.getErrorMessage().getDescription());

        System.out.println("getResultHeader Date: " + getResultHeader().getDate());
        System.out.println("getResultHeader MessageGUID: " + getResultHeader().getMessageGUID());

    }

    private ExportDataProviderRequest getExportDataProviderRequest() {

        ExportDataProviderRequest exportDataProviderRequest = new ExportDataProviderRequest();
        exportDataProviderRequest.setId("signed-data-container");
        exportDataProviderRequest.setIsActual(true);

        return exportDataProviderRequest;
    }

    private ISRequestHeader getHeader() {

        ISRequestHeader isRequestHeader = new ISRequestHeader();
        isRequestHeader.setDate(OtherFormat.getDateNow());
        isRequestHeader.setMessageGUID(OtherFormat.getRandomGUID());

        return isRequestHeader;
    }

    private ResultHeader getResultHeader() {
        return resultHolder.value;
    }

}
