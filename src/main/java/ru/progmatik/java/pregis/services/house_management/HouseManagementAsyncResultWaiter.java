package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.AckRequest;
import ru.gosuslugi.dom.schema.integration.base.GetStateRequest;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.house_management.GetStateResult;
import ru.gosuslugi.dom.schema.integration.house_management_service_async.Fault;
import ru.gosuslugi.dom.schema.integration.house_management_service_async.HouseManagementPortsTypeAsync;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;
import ru.progmatik.java.pregis.other.TextForLog;

import javax.xml.ws.Holder;
import java.sql.SQLException;

/**
 * Класс-получатель результатов асинхронного обращения к БД в псевдо-синхронном режиме из классов раздела HouseManagement
 * Created by Администратор on 11.05.2017.
 */
public class HouseManagementAsyncResultWaiter {
    private final AckRequest ackRequest;
    private static final Logger LOGGER = Logger.getLogger(HouseManagementAsyncResultWaiter.class);
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

    public HouseManagementAsyncResultWaiter(AckRequest ackRequest, String name_method, AnswerProcessing answerProcessing, HouseManagementPortsTypeAsync port) {
        this.ackRequest = ackRequest;
        NAME_METHOD = name_method;
        this.answerProcessing = answerProcessing;
        this.port = port;

        timeOut = ResourcesUtil.instance().getTimeOut();
        maxRequestCount = ResourcesUtil.instance().getMaxRequestCount();
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
                answerProcessing.sendMessageToClient(TextForLog.ASK_ASYNC_REQUEST + i + " из " + maxRequestCount);
                result = port.getState(getStateRequest, requestHeader, headerHolder);
            } catch (Fault fault) {
                answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
                break;
            }

            if (result != null){
                if (result.getRequestState() == 3) {
                    answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
                    break;
                }else{
                    if (result.getRequestState() == 1) answerProcessing.sendMessageToClient(TextForLog.ASYNС_REQUEST_RECIVED);
                    if (result.getRequestState() == 2) answerProcessing.sendMessageToClient(TextForLog.ASYNС_REQUEST_PROCEED);
                }
            }
        }
        return result;
    }
}
