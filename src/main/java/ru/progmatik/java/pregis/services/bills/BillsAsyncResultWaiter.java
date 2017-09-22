package ru.progmatik.java.pregis.services.bills;

import ru.gosuslugi.dom.schema.integration.base.AckRequest;
import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.GetStateRequest;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.bills.GetStateResult;
import ru.gosuslugi.dom.schema.integration.bills_service_async.BillsPortsTypeAsync;
import ru.gosuslugi.dom.schema.integration.bills_service_async.Fault;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;
import ru.progmatik.java.pregis.other.TextForLog;

import javax.xml.ws.Holder;
import java.sql.SQLException;

/**
 * Класс-получатель результатов асинхронного обращения к БД в псевдо-синхронном режиме из класса BillsAsyncPort
 * Created by Администратор on 07.06.2017.
 */
public class BillsAsyncResultWaiter {
    private final AckRequest ackRequest;
    private static final Logger LOGGER = Logger.getLogger(BillsAsyncResultWaiter.class);
    private final String NAME_METHOD;

    private final BillsPortsTypeAsync port;
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

    public BillsAsyncResultWaiter(AckRequest ackRequest, String name_method, AnswerProcessing answerProcessing, BillsPortsTypeAsync port) {
        this.ackRequest = ackRequest;
        NAME_METHOD = name_method;
        this.port = port;
        this.answerProcessing = answerProcessing;

        try {
            timeOut = ResourcesUtil.instance().getTimeOut();
        } catch (PreGISException e) {
            timeOut = 5000;
        }

        try {
            maxRequestCount = ResourcesUtil.instance().getMaxRequestCount();
        } catch (PreGISException e) {
            maxRequestCount = 1000;
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
                    // лишняя инфа в логе if(answerProcessing != null) answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
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
