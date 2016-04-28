package ru.prog_matik.java.pregis.services.nsi.service;

import ru.gosuslugi.dom.schema.integration._8_7_0.ImportResult;
import ru.gosuslugi.dom.schema.integration._8_7_0.RequestHeader;
import ru.gosuslugi.dom.schema.integration._8_7_0.ResultHeader;
import ru.gosuslugi.dom.schema.integration._8_7_0_6.nsi.*;
import ru.gosuslugi.dom.schema.integration._8_7_0_6.nsi_service.Fault;
import ru.gosuslugi.dom.schema.integration._8_7_0_6.nsi_service.NsiPortsType;
import ru.gosuslugi.dom.schema.integration._8_7_0_6.nsi_service.NsiService;
import ru.prog_matik.java.pregis.other.OtherFormat;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

/**
 * Класс реализует методы интерфейса "NsiPortsType", который служит для обмена справочников. Синхронный.
 * Created by andryha on 17.02.2016.
 */
public class NsiPortsTypeImpl implements NsiPortsType {

    @Override
    public ImportResult importAdditionalServices(ImportAdditionalServicesRequest importAdditionalServicesRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {

        return getPort().importAdditionalServices(importAdditionalServicesRequest, header, header0);
    }

    @Override
    public ImportResult importMunicipalServices(ImportMunicipalServicesRequest importMunicipalServicesRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {

        return getPort().importMunicipalServices(importMunicipalServicesRequest, header, header0);
    }

    @Override
    public ImportResult importOrganizationWorks(ImportOrganizationWorksRequest importOrganizationWorksRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {

        return getPort().importOrganizationWorks(importOrganizationWorksRequest, header, header0);
    }

    @Override
    public ExportNsiItemResult exportDataProviderNsiItem(ExportDataProviderNsiItemRequest tnsExportDataProviderNsiItemRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {

        return getPort().exportDataProviderNsiItem(tnsExportDataProviderNsiItemRequest, header, header0);
    }

    private NsiPortsType getPort() {

        NsiService service = new NsiService();
        NsiPortsType port = service.getNsiPort();

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, OtherFormat.USER_NAME);
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, OtherFormat.PASSWORD);
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, NsiService.__getWsdlLocation().toString());

        return port;
    }
}
