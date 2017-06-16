package ru.progmatik.java.pregis.services.house_management;

import ru.gosuslugi.dom.schema.integration.base.AckRequest;
import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.GetStateRequest;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.house_management.GetStateResult;
import ru.gosuslugi.dom.schema.integration.house_management_service_async.Fault;
import ru.gosuslugi.dom.schema.integration.house_management_service_async.HouseManagementPortsTypeAsync;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;
import ru.progmatik.java.pregis.other.TextForLog;
import javax.xml.ws.Holder;
import java.sql.SQLException;
/**
 * Created by Администратор on 16.06.2017.
 */
public class AccountAsyncResultWaiter {
    private final AckRequest ackRequest;
    private static final Logger LOGGER = Logger.getLogger(AccountAsyncResultWaiter.class);
    private final String NAME_METHOD;

    private final HouseManagementPortsTypeAsync port;
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

    public AccountAsyncResultWaiter(AckRequest ackRequest, String name_method, AnswerProcessing answerProcessing, HouseManagementPortsTypeAsync port) {
        this.ackRequest = ackRequest;
        NAME_METHOD = name_method;
        this.port = port;
        this.answerProcessing = answerProcessing;

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
                if(answerProcessing != null) answerProcessing.sendMessageToClient(TextForLog.ASK_ASYNC_REQUEST + i + " из " + maxRequestCount);
                result = port.getState(getStateRequest, requestHeader, headerHolder);
            } catch (Fault fault) {
                if(answerProcessing != null) answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            }

            if (result != null){
                if (result.getRequestState() == 3) {
                    if(answerProcessing != null) answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
                    break;
                }else{
                    if (result.getRequestState() == 1) if(answerProcessing != null) answerProcessing.sendMessageToClient(TextForLog.ASYNС_REQUEST_RECIVED);
                    if (result.getRequestState() == 2) if(answerProcessing != null) answerProcessing.sendMessageToClient(TextForLog.ASYNС_REQUEST_PROCEED);
                }
            }
        }
        return result;
    }
}
