package ru.progmatik.java.pregis.services.organizations;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.AckRequest;
import ru.gosuslugi.dom.schema.integration.base.GetStateRequest;
import ru.gosuslugi.dom.schema.integration.base.ISRequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common.GetStateResult;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common_service_async.Fault;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common_service_async.RegOrgPortsTypeAsync;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;
import ru.progmatik.java.pregis.other.TextForLog;

import javax.xml.ws.Holder;

public class OrgRegistryAsyncResultWaiter {
    private final AckRequest ackRequest;
    private static final Logger LOGGER = Logger.getLogger(OrgRegistryAsyncResultWaiter.class);
    private final String NAME_METHOD;

    private final RegOrgPortsTypeAsync port;
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

    public OrgRegistryAsyncResultWaiter(AckRequest ackRequest, String name_method, AnswerProcessing answerProcessing, RegOrgPortsTypeAsync port) {
        this.ackRequest = ackRequest;
        NAME_METHOD = name_method;
        this.answerProcessing = answerProcessing;
        this.port = port;

        timeOut = ResourcesUtil.instance().getTimeOut();
        maxRequestCount = ResourcesUtil.instance().getMaxRequestCount();
    }

    public GetStateResult getRequestResult(){
        ISRequestHeader requestHeader = OtherFormat.getISRequestHeader();

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
