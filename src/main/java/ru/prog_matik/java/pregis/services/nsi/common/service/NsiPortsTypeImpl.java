package ru.prog_matik.java.pregis.services.nsi.common.service;

import ru.gosuslugi.dom.schema.integration._8_6_0.ISRequestHeader;
import ru.gosuslugi.dom.schema.integration._8_6_0.ResultHeader;
import ru.gosuslugi.dom.schema.integration._8_6_0_4.nsi_common.ExportNsiItemRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_4.nsi_common.ExportNsiItemResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_4.nsi_common.ExportNsiListRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_4.nsi_common.ExportNsiListResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_4.nsi_common_service.Fault;
import ru.gosuslugi.dom.schema.integration._8_6_0_4.nsi_common_service.NsiPortsType;
import ru.gosuslugi.dom.schema.integration._8_6_0_4.nsi_common_service.NsiService;
import ru.prog_matik.java.pregis.other.OtherFormat;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

/**
 * Класс реализует методы интерфейса "NsiPortsType", который служит для обмена справочников. Синхронный.
 * Модуль "hcs-nsi-common-service".
 * Created by andryha on 17.02.2016.
 */
public class NsiPortsTypeImpl implements NsiPortsType {


    @Override
    public ExportNsiListResult exportNsiList(ExportNsiListRequest exportNsiListRequest, ISRequestHeader header, Holder<ResultHeader> header0) throws Fault {

        return getPort().exportNsiList(exportNsiListRequest, header, header0);
    }

    @Override
    public ExportNsiItemResult exportNsiItem(ExportNsiItemRequest exportNsiItemRequest, ISRequestHeader header, Holder<ResultHeader> header0) throws Fault {

        return getPort().exportNsiItem(exportNsiItemRequest, header, header0);
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
