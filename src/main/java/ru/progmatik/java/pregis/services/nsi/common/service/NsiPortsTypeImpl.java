package ru.progmatik.java.pregis.services.nsi.common.service;

import ru.gosuslugi.dom.schema.integration.base.HeaderType;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.nsi_common.ExportNsiItemRequest;
import ru.gosuslugi.dom.schema.integration.nsi_common.ExportNsiItemResult;
import ru.gosuslugi.dom.schema.integration.nsi_common.ExportNsiListRequest;
import ru.gosuslugi.dom.schema.integration.nsi_common.ExportNsiListResult;
import ru.gosuslugi.dom.schema.integration.nsi_common_service.Fault;
import ru.gosuslugi.dom.schema.integration.nsi_common_service.NsiPortsType;
import ru.gosuslugi.dom.schema.integration.nsi_common_service.NsiService;
import ru.progmatik.java.pregis.other.OtherFormat;

import javax.xml.ws.Holder;

/**
 * Класс реализует методы интерфейса "NsiPortsType", который служит для обмена справочников. Синхронный.
 * Модуль "hcs-nsi-common-service".
 * Created by andryha on 17.02.2016.
 */
public class NsiPortsTypeImpl implements NsiPortsType {


    @Override
    public ExportNsiListResult exportNsiList(ExportNsiListRequest exportNsiListRequest, HeaderType header, Holder<ResultHeader> header0) throws Fault {

        return getPort().exportNsiList(exportNsiListRequest, header, header0);
    }

    @Override
    public ExportNsiItemResult exportNsiItem(ExportNsiItemRequest exportNsiItemRequest, HeaderType header, Holder<ResultHeader> header0) throws Fault {

        return getPort().exportNsiItem(exportNsiItemRequest, header, header0);
    }

    private NsiPortsType getPort() {

        NsiService service = new NsiService();
        NsiPortsType port = service.getNsiPort();
            OtherFormat.setPortSettings(service, port);

//        BindingProvider provider = (BindingProvider) port;
//        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, OtherFormat.USER_NAME);
//        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, OtherFormat.PASSWORD);
//        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, NsiService.__getWsdlLocation().toString());

        return port;
    }
}
