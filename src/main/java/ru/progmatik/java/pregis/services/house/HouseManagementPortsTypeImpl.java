package ru.progmatik.java.pregis.services.house;

import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.house_management.*;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.Fault;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.HouseManagementPortsType;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.HouseManagementService;
import ru.progmatik.java.pregis.other.OtherFormat;

import javax.xml.ws.Holder;


public class HouseManagementPortsTypeImpl implements HouseManagementPortsType {

    @Override
    public ImportResult importMeteringDeviceData(ImportMeteringDeviceDataRequest importMeteringDeviceDataRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ExportMeteringDeviceDataResult exportMeteringDeviceData(ExportMeteringDeviceDataRequest exportMeteringDeviceDataRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

//    @Override
//    public ru.gosuslugi.dom.schema.integration.base.ImportResult importShareEncbrData(ImportShareEncbrDataRequest importShareEncbrDataRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
//        return null;
//    }
//
//    @Override
//    public ExportShareEncbrDataResult exportShareEncbrData(ExportShareEncbrDataRequest exportShareEncbrDataRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
//        return null;
//    }

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

        return getPort().exportStatusCAChData(exportStatusCAChRequest, header, header0);
    }

    @Override
    public ExportHouseResult exportHouseData(ExportHouseRequest exportHouseDataRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ru.gosuslugi.dom.schema.integration.base.ImportResult importAccountData(ImportAccountRequest importAccountDataRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ExportAccountResult exportAccountData(ExportAccountRequest exportAccountDataRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return getPort().exportAccountData(exportAccountDataRequest, header, header0);
    }

    @Override
    public ru.gosuslugi.dom.schema.integration.base.ImportResult importPublicPropertyContract(ImportPublicPropertyContractRequest importPublicPropertyContractRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ExportStatusPublicPropertyContractResult exportStatusPublicPropertyContract(ExportStatusPublicPropertyContractRequest exportStatusPublicPropertyContractRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ru.gosuslugi.dom.schema.integration.base.ImportResult importNotificationData(ImportNotificationRequest importNotificationRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ru.gosuslugi.dom.schema.integration.base.ImportResult importVotingProtocol(ImportVotingProtocolRequest importVotingProtocolRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ExportVotingProtocolResult exportVotingProtocol(ExportVotingProtocolRequest exportVotingProtocolRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ExportCAChResult exportCAChData(ExportCAChRequest exportCAChDataRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {

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

    @Override
    public ru.gosuslugi.dom.schema.integration.base.ImportResult importSupplyResourceContractData(ImportSupplyResourceContractRequest importSupplyResourceContractRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    @Override
    public ExportSupplyResourceContractResult exportSupplyResourceContractData(ExportSupplyResourceContractRequest exportSupplyResourceContractRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {
        return null;
    }

    private HouseManagementPortsType getPort() {

        HouseManagementService service = new HouseManagementService();
        HouseManagementPortsType port = service.getHouseManagementPort();
        OtherFormat.setPortSettings(service, port);

        return port;
    }
}
