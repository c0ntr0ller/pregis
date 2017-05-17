package ru.progmatik.java.pregis.services.device_metering;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.*;
import ru.gosuslugi.dom.schema.integration.device_metering.GetStateResult;
import ru.gosuslugi.dom.schema.integration.device_metering_service_async.DeviceMeteringPortTypesAsync;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;
import ru.progmatik.java.pregis.other.TextForLog;
import javax.xml.ws.Holder;
import java.sql.SQLException;

/**
 * Created by Администратор on 16.05.2017.
 */
public class DeviceMeteringAsyncGetResult {
    private final AckRequest ackRequest;
    private static final Logger LOGGER = Logger.getLogger(DeviceMeteringAsyncGetResult.class);
    private final String NAME_METHOD;

    private final DeviceMeteringPortTypesAsync port;
    private final AnswerProcessing answerProcessing;

    public Holder<ResultHeader> getHeaderHolder() {
        return headerHolder;
    }

    public void setHeaderHolder(Holder<ResultHeader> headerHolder) {
        this.headerHolder = headerHolder;
    }

    private Holder<ResultHeader> headerHolder = new Holder<>();
    private long timeOut;
    private long maxRequestCount;

    public DeviceMeteringAsyncGetResult(AckRequest ackRequest, String name_method, AnswerProcessing answerProcessing, DeviceMeteringPortTypesAsync port) {
        this.ackRequest = ackRequest;
        NAME_METHOD = name_method;
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

    public GetStateResult getRequestResult() throws SQLException {
        RequestHeader requestHeader = OtherFormat.getRequestHeader();

        GetStateRequest getStateRequest = new GetStateRequest();
        getStateRequest.setMessageGUID(ackRequest.getAck().getMessageGUID());

        GetStateResult result = null;

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
            } catch (ru.gosuslugi.dom.schema.integration.device_metering_service_async.Fault fault) {
                answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            }

            if (result != null && (result.getRequestState() == 3)) {
                break;
            }
        }
        return result;
    }    
}
