package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.*;
import ru.gosuslugi.dom.schema.integration.house_management_service_async.*;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.*;

import javax.xml.ws.Holder;
import java.sql.SQLException;

/**
 * Created by Администратор on 11.05.2017.
 */
public class HouseManagementAsyncRequestResult {
    private final AckRequest ackRequest;
    private static final Logger LOGGER = Logger.getLogger(HouseManagementAsyncRequestResult.class);
    private static final String NAME_METHOD = "getRequestResultDeviceMetering";

    private final HouseManagementPortsTypeAsync port;
    private final AnswerProcessing answerProcessing;
    private long timeOut;
    private long maxRequestCount;

    public HouseManagementAsyncRequestResult(AckRequest ackRequest, AnswerProcessing answerProcessing, HouseManagementPortsTypeAsync port) {
        this.ackRequest = ackRequest;
        this.answerProcessing = answerProcessing;
        this.port = port;

        try {
            timeOut = ResourcesUtil.instance().getTimeOut();
        } catch (PreGISException e) {
            timeOut = 1000;
        }

        try {
            maxRequestCount = ResourcesUtil.instance().getMaxRequestCount();
        } catch (PreGISException e) {
            maxRequestCount = 100;
        }
    }

    public BaseAsyncResponseType getRequestResult() throws SQLException {
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        Holder<ResultHeader> headerHolder = new Holder<>();

        GetStateRequest getStateRequest = new GetStateRequest();
        getStateRequest.setMessageGUID(ackRequest.getAck().getMessageGUID());

        BaseAsyncResponseType result = null;

        for (int i = 0; i < maxRequestCount; i++) {
            try {
                Thread.sleep(timeOut);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            try {
                answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
                result = port.getState(getStateRequest, requestHeader, headerHolder);
                answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
            } catch (ru.gosuslugi.dom.schema.integration.house_management_service_async.Fault fault) {
                answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            }

            if (result != null && (result.getRequestState() == 3)) {
                break;
            }
        }
        return result;
    }
}
