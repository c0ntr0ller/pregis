package ru.progmatik.java.pregis.services.house;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.house_management.ExportStatusCAChRequest;
import ru.gosuslugi.dom.schema.integration.services.house_management.ExportStatusCAChResult;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.Fault;
import ru.progmatik.java.pregis.connectiondb.SaveToBaseMessages;
import ru.progmatik.java.pregis.other.OtherFormat;

import javax.xml.ws.Holder;

/**
 * Created by andryha on 25.02.2016.
 */
public class ExportStatusCAChData {

    private Logger logger = Logger.getLogger(ExportStatusCAChData.class);

    private static final String NAME_METHOD = "exportStatusCAChData";

    private Holder<ResultHeader> headerHolder;

    public void callExportStatusCAChData() {

        RequestHeader requestHeader = OtherFormat.getRequestHeader();

        headerHolder = new Holder<>(new ResultHeader());

        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        ExportStatusCAChResult result;

        HouseManagementPortsTypeImpl portsType = new HouseManagementPortsTypeImpl();

        try {
            result = portsType.exportStatusCAChData(getExportStatusCAChRequest(), requestHeader, headerHolder);
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
