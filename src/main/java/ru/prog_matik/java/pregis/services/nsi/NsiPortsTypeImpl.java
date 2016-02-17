package ru.prog_matik.java.pregis.services.nsi;

import ru.gosuslugi.dom.schema.integration._8_5_0.ImportResult;
import ru.gosuslugi.dom.schema.integration._8_5_0.RequestHeader;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.nsi.*;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.nsi_service.Fault;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.nsi_service.NsiPortsType;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.nsi_service.NsiService;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.util.ResourceBundle;

/**
 * Класс реализует методы интерфейса "NsiPortsType", который служит для обмена справочников.
 * Created by andryha on 17.02.2016.
 */
public class NsiPortsTypeImpl implements NsiPortsType {

    private static final String USER_NAME = ResourceBundle.getBundle("application").getString("config.ws.user");
    private static final String PASSWORD = ResourceBundle.getBundle("application").getString("config.ws.password");

    @Override
    public ExportNsiListResult exportNsiList(ExportNsiListRequest exportNsiListRequest, Holder<RequestHeader> header) throws Fault {

        NsiService service = new NsiService();
        NsiPortsType port = service.getNsiPort();

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, USER_NAME);
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, PASSWORD);
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, NsiService.__getWsdlLocation().toString());

        return port.exportNsiList(exportNsiListRequest, header);
    }

    @Override
    public ExportNsiItemResult exportNsiItem(ExportNsiItemRequest exportNsiItemRequest, Holder<RequestHeader> header) throws Fault {

        NsiService service = new NsiService();
        NsiPortsType port = service.getNsiPort();

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, USER_NAME);
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, PASSWORD);
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, NsiService.__getWsdlLocation().toString());

        return port.exportNsiItem(exportNsiItemRequest, header);
    }

    @Override
    public ImportResult importAdditionalServices(ImportAdditionalServicesRequest importAdditionalServicesRequest, Holder<RequestHeader> header) throws Fault {

        NsiService service = new NsiService();
        NsiPortsType port = service.getNsiPort();

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, USER_NAME);
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, PASSWORD);
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, NsiService.__getWsdlLocation().toString());

        return port.importAdditionalServices(importAdditionalServicesRequest, header);
    }

    @Override
    public ImportResult importMunicipalServices(ImportMunicipalServicesRequest importMunicipalServicesRequest, Holder<RequestHeader> header) throws Fault {

        NsiService service = new NsiService();
        NsiPortsType port = service.getNsiPort();

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, USER_NAME);
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, PASSWORD);
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, NsiService.__getWsdlLocation().toString());

        return port.importMunicipalServices(importMunicipalServicesRequest, header);
    }

    @Override
    public ImportResult importOrganizationWorks(ImportOrganizationWorksRequest importOrganizationWorksRequest, Holder<RequestHeader> header) throws Fault {

        NsiService service = new NsiService();
        NsiPortsType port = service.getNsiPort();

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, USER_NAME);
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, PASSWORD);
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, NsiService.__getWsdlLocation().toString());

        return port.importOrganizationWorks(importOrganizationWorksRequest, header);
    }


}
