package ru.progmatik.java.pregis.services.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.*;
import ru.gosuslugi.dom.schema.integration.bills.*;
import ru.gosuslugi.dom.schema.integration.bills_service_async.BillsPortsTypeAsync;
import ru.gosuslugi.dom.schema.integration.bills_service_async.BillsServiceAsync;
import ru.gosuslugi.dom.schema.integration.bills_service_async.Fault;
import ru.gosuslugi.dom.schema.integration.nsi_base.NsiRef;
import ru.progmatik.java.pregis.connectiondb.grad.bills.PaymentInformationGradDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;
import ru.progmatik.java.pregis.other.UrlLoader;

import javax.xml.ws.Holder;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Класс, импорт сведений о платежных документах.
 * 2.1.9.4	Импорт сведений о платежных документах (importPaymentDocumentData).
 * Операция позволяет импортировать в ГИС ЖКХ сведения о платежных документах. Сведения содержат в том числе ссылку на лицевой счет, расчетный счет получателя, информация по начислениям. В результате операции выполняется одно из двух действий:
 * •	создается платежный документ в статусе «Выставлен» (для выставления на оплату в кабинет физическому лицу) при указании TransportGUID и признака Expose;
 * •	отзывается выставленный ранее счет на оплату при указании созданного ранее платежного документа (PaymentDocumentGuid) и признака Withdraw;
 * Другие комбинации признаков и идентификаторов платежного документа не допускается.
 * Операция возвращает в UniqueNumber идентификатор платежного документа.
 */
public class PaymentDocumentsPort {

    private static final Logger LOGGER = Logger.getLogger(ExportPaymentDocumentData.class);

    private static final String NAME_METHOD_IMPORT = "importPaymentDocumentData";
    private static final String NAME_METHOD_EXPORT = "exportPaymentDocumentData";

    private final BillsServiceAsync service;
    private final BillsPortsTypeAsync port;
    private final AnswerProcessing answerProcessing;


    /**
     * Конструктор, добавляет параметры для соединения.
     */
    public PaymentDocumentsPort(AnswerProcessing answerProcessing) {

        if(answerProcessing != null) {
            this.answerProcessing = answerProcessing;
        }else{
            this.answerProcessing = new AnswerProcessing();
        }

        service = UrlLoader.instance().getUrlMap().get("billsAsync") == null ? new BillsServiceAsync()
                : new BillsServiceAsync(UrlLoader.instance().getUrlMap().get("billsAsync"));
        port = service.getBillsPortAsync();
        OtherFormat.setPortSettings(service, port);
    }

    /**
     * Метод, импорт/экспорт сведений о платежных документах из/в ГИС ЖКХ.
     */
    GetStateResult interactionPaymentDocument(ImportPaymentDocumentRequest importRequest, ExportPaymentDocumentRequest exportRequest) throws PreGISException, SQLException {

        answerProcessing.sendMessageToClient("");
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        ResultHeader resultHeader = null;
        Holder<ResultHeader> headerHolder = new Holder<>();
        ErrorMessageType resultErrorMessage = null;
        String curMethodName = "interactionPaymentDocument";

        BillsAsyncResultWaiter billsAsyncResultWaiter = null;
        GetStateResult result = null;

        try {
            AckRequest ackRequest = null;

            if(importRequest != null) {
                answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST + NAME_METHOD_IMPORT);
                curMethodName = NAME_METHOD_IMPORT;
                ackRequest = port.importPaymentDocumentData(setSignIdImportPaymentDocumentRequest(importRequest), requestHeader, headerHolder);
            }else{
                answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST + NAME_METHOD_EXPORT);
                curMethodName = NAME_METHOD_EXPORT;
                ackRequest = port.exportPaymentDocumentData(setSignIdExportPaymentDocumentRequest(exportRequest), requestHeader, headerHolder);
            }

             answerProcessing.sendMessageToClient(TextForLog.REQUEST_SENDED);

            if (ackRequest != null) {
                billsAsyncResultWaiter = new BillsAsyncResultWaiter(ackRequest, curMethodName, answerProcessing, port);
                result = billsAsyncResultWaiter.getRequestResult();
            }

        } catch (Fault fault) {
             answerProcessing.sendServerErrorToClient(curMethodName, requestHeader, LOGGER, fault);
            return null;
        }
        if (result != null) resultErrorMessage = result.getErrorMessage();

        if (billsAsyncResultWaiter != null) resultHeader = billsAsyncResultWaiter.getHeaderHolder().value;

        answerProcessing.sendToBaseAndAnotherError(curMethodName, requestHeader, resultHeader, resultErrorMessage, LOGGER);

        if (resultErrorMessage != null) {
            result = null;
        }

        return result;
    }

    /**
     * Метод, добавляет идентификатор для дальнейшего подписания.
     *
     * @param request подготовленный запрос.
     * @return запрос с указанным идентификатором для цифровой подписи.
     */
    private ImportPaymentDocumentRequest setSignIdImportPaymentDocumentRequest(ImportPaymentDocumentRequest request) {
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        return request;
    }
    /**
     * Метод, добавляет идентификатор для дальнейшего подписания.
     *
     * @param request подготовленный запрос.
     * @return запрос с указанным идентификатором для цифровой подписи.
     */
    private ExportPaymentDocumentRequest setSignIdExportPaymentDocumentRequest(ExportPaymentDocumentRequest request) {
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        return request;
    }

}
