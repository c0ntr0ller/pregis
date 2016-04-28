package ru.prog_matik.java.pregis.services.house;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration._8_7_0.RequestHeader;
import ru.gosuslugi.dom.schema.integration._8_7_0.ResultHeader;
import ru.gosuslugi.dom.schema.integration._8_7_0_6.house_management.ExportCAChRequest;
import ru.gosuslugi.dom.schema.integration._8_7_0_6.house_management.ExportCAChResult;
import ru.gosuslugi.dom.schema.integration._8_7_0_6.house_management_service.Fault;
import ru.prog_matik.java.pregis.connectiondb.SaveToBaseMessages;
import ru.prog_matik.java.pregis.other.OtherFormat;

import javax.xml.ws.Holder;

public class ExportCAChData {

    private static final Logger logger = Logger.getLogger(ExportCAChData.class);

    private static final String NAME_METHOD = "exportCAChData";

    private Holder<ResultHeader> headerHolder = new Holder<>(new ResultHeader());

    public void callExportCAChData() {

        RequestHeader requestHeader = OtherFormat.getRequestHeader();

        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        ExportCAChResult result;

        HouseManagementPortsTypeImpl portsType = new HouseManagementPortsTypeImpl();

        try {
            result = portsType.exportCAChData(getExportCAChRequest(), requestHeader, headerHolder);
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
