package ru.progmatik.java.pregis.services.house;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.house_management.ExportCAChRequest;
import ru.gosuslugi.dom.schema.integration.house_management.ExportCAChResult;
import ru.gosuslugi.dom.schema.integration.house_management_service.Fault;
import ru.gosuslugi.dom.schema.integration.house_management_service.HouseManagementPortsType;
import ru.gosuslugi.dom.schema.integration.house_management_service.HouseManagementService;
import ru.progmatik.java.pregis.connectiondb.localdb.message.SaveToBaseMessages;
import ru.progmatik.java.pregis.other.OtherFormat;

import javax.xml.ws.Holder;
import java.sql.SQLException;

public class ExportCAChData {

    private static final Logger logger = Logger.getLogger(ExportCAChData.class);

    private static final String NAME_METHOD = "exportCAChData";
    private final HouseManagementService service = new HouseManagementService();
    private final HouseManagementPortsType port = service.getHouseManagementPort();

    private Holder<ResultHeader> headerHolder = new Holder<>(new ResultHeader());

    public ExportCAChData() {
        OtherFormat.setPortSettings(service, port);
    }

    public void callExportCAChData() throws SQLException {

        RequestHeader requestHeader = OtherFormat.getRequestHeader();

        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        ExportCAChResult result;

        try {
            result = port.exportCAChData(getExportCAChRequest(), requestHeader, headerHolder);
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

    private ExportCAChRequest getExportCAChRequest() {
        ExportCAChRequest request = new ExportCAChRequest();
//        ExportCAChRequest.Criteria criteria = new ExportCAChRequest.Criteria();
//        criteria.setUOGUID(BaseOrganization.getSenderID());
//        request.getCriteria().add(criteria);
        request.setId(OtherFormat.getId());
        return request;
    }


}
