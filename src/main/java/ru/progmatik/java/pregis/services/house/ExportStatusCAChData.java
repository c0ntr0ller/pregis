package ru.progmatik.java.pregis.services.house;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.house_management.ExportStatusCAChRequest;
import ru.gosuslugi.dom.schema.integration.house_management.ExportStatusCAChResult;
import ru.gosuslugi.dom.schema.integration.house_management_service.Fault;
import ru.gosuslugi.dom.schema.integration.house_management_service.HouseManagementPortsType;
import ru.gosuslugi.dom.schema.integration.house_management_service.HouseManagementService;
import ru.progmatik.java.pregis.connectiondb.localdb.message.SaveToBaseMessages;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.UrlLoader;

import javax.xml.ws.Holder;
import java.sql.SQLException;

/**
 * Created by andryha on 25.02.2016.
 */
public class ExportStatusCAChData {

    private static final String NAME_METHOD = "exportStatusCAChData";
    private final HouseManagementPortsType port;
    private Logger logger = Logger.getLogger(ExportStatusCAChData.class);
    private Holder<ResultHeader> headerHolder;

    public ExportStatusCAChData() {

        HouseManagementService service = UrlLoader.instance().getUrlMap().get("homeManagement") == null ? new HouseManagementService()
                : new HouseManagementService(UrlLoader.instance().getUrlMap().get("homeManagement"));
        port = service.getHouseManagementPort();
        OtherFormat.setPortSettings(service, port);
    }

    public void callExportStatusCAChData() throws SQLException {

        RequestHeader requestHeader = OtherFormat.getRequestHeader();

        headerHolder = new Holder<>(new ResultHeader());

        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        ExportStatusCAChResult result;

        try {
            result = port.exportStatusCAChData(getExportStatusCAChRequest(), requestHeader, headerHolder);
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

    private ExportStatusCAChRequest getExportStatusCAChRequest() {

        ExportStatusCAChRequest request = new ExportStatusCAChRequest();
        request.setId(OtherFormat.getId());

        ExportStatusCAChRequest.Criteria criteria = new ExportStatusCAChRequest.Criteria();


        return request;
    }


}
