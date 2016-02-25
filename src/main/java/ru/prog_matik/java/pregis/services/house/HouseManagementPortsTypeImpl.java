package ru.prog_matik.java.pregis.services.house;

import ru.gosuslugi.dom.schema.integration._8_5_0.*;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.*;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.ImportResult;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management_service.Fault;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management_service.HouseManagementPortsType;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management_service.HouseManagementService;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.nsi_service.NsiPortsType;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.nsi_service.NsiService;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.util.ResourceBundle;

/**
 * Created by andryha on 25.02.2016.
 */
public class HouseManagementPortsTypeImpl implements HouseManagementPortsType {

    private static final String USER_NAME = ResourceBundle.getBundle("application").getString("config.ws.user");
    private static final String PASSWORD = ResourceBundle.getBundle("application").getString("config.ws.password");


    @Override
    public ImportResult importMeteringDeviceData(ImportMeteringDeviceDataRequest importMeteringDeviceDataRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ExportMeteringDeviceDataResult exportMeteringDeviceData(ExportMeteringDeviceDataRequest exportMeteringDeviceDataRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ru.gosuslugi.dom.schema.integration._8_5_0.ImportResult importShareEncbrData(ImportShareEncbrDataRequest importShareEncbrDataRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ExportShareEncbrDataResult exportShareEncbrData(ExportShareEncbrDataRequest exportShareEncbrDataRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ImportResult importContractData(ImportContractRequest importContractRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ImportResult importCharterData(ImportCharterRequest importCharterRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ExportStatusCAChResult exportStatusCAChData(ExportStatusCAChRequest exportStatusCAChRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {

        HouseManagementService service = new HouseManagementService();
        HouseManagementPortsType port = service.getHouseManagementPort();

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, USER_NAME);
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, PASSWORD);
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, HouseManagementService.__getWsdlLocation().toString());

        return port.exportStatusCAChData(exportStatusCAChRequest, header, header0);
    }

    @Override
    public ExportHouseResult exportHouseData(ExportHouseRequest exportHouseDataRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ru.gosuslugi.dom.schema.integration._8_5_0.ImportResult importAccountData(ImportAccountRequest importAccountDataRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ExportAccountResult exportAccountData(ExportAccountRequest exportAccountDataRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return getPort().exportAccountData(exportAccountDataRequest, header, header0);
    }

    @Override
    public ru.gosuslugi.dom.schema.integration._8_5_0.ImportResult importPublicPropertyContract(ImportPublicPropertyContractRequest importPublicPropertyContractRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ExportStatusPublicPropertyContractResult exportStatusPublicPropertyContract(ExportStatusPublicPropertyContractRequest exportStatusPublicPropertyContractRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ru.gosuslugi.dom.schema.integration._8_5_0.ImportResult importNotificationData(ImportNotificationRequest importNotificationRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ru.gosuslugi.dom.schema.integration._8_5_0.ImportResult importVotingProtocol(ImportVotingProtocolRequest importVotingProtocolRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ExportVotingProtocolResult exportVotingProtocol(ExportVotingProtocolRequest exportVotingProtocolRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ExportCAChResult exportCAChData(ExportCAChRequest exportCAChDataRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {

//        HouseManagementService service = new HouseManagementService();
//        HouseManagementPortsType port = service.getHouseManagementPort();
//
//        BindingProvider provider = (BindingProvider) port;
//        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, USER_NAME);
//        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, PASSWORD);
//        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, HouseManagementService.__getWsdlLocation().toString());

        return getPort().exportCAChData(exportCAChDataRequest, header, header0);
    }

    @Override
    public ImportResult importHouseUOData(ImportHouseUORequest importHouseUODataRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ImportResult importHouseRSOData(ImportHouseRSORequest importHouseRSODataRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ImportResult importHouseOMSData(ImportHouseOMSRequest importHouseOMSDataRequest, Holder<RequestHeader> header) throws Fault {
        return null;
    }

    private HouseManagementPortsType getPort() {

        HouseManagementService service = new HouseManagementService();
        HouseManagementPortsType port = service.getHouseManagementPort();

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, USER_NAME);
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, PASSWORD);
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, HouseManagementService.__getWsdlLocation().toString());

        return port;
    }
}
