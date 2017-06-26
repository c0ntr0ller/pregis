package ru.progmatik.java.pregis.services.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.*;
import ru.gosuslugi.dom.schema.integration.bills.*;
import ru.gosuslugi.dom.schema.integration.bills_service_async.BillsPortsTypeAsync;
import ru.gosuslugi.dom.schema.integration.bills_service_async.BillsServiceAsync;
import ru.gosuslugi.dom.schema.integration.bills_service_async.Fault;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;
import ru.progmatik.java.pregis.other.UrlLoader;

import javax.xml.ws.Holder;
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
        Holder<ResultHeader> resultHeaderHolder = new Holder<>();
        ErrorMessageType resultErrorMessage = null;
        String curMethodName = "interactionPaymentDocument";

        PaymentsAsyncResultWaiter paymentsAsyncResultWaiter = null;
        GetStateResult result = null;


        try {
            AckRequest ackRequest;

            if(importRequest != null) {
                answerProcessing.sendMessageToClient("Отсылаются платежные документы по дому в ГИС");
                answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST + NAME_METHOD_IMPORT);
                curMethodName = NAME_METHOD_IMPORT;
                ackRequest = port.importPaymentDocumentData(setSignIdImportPaymentDocumentRequest(importRequest), requestHeader, resultHeaderHolder);
            }else{
                answerProcessing.sendMessageToClient("Получаются платежные документы по дому из ГИС");
                answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST + NAME_METHOD_EXPORT);
                curMethodName = NAME_METHOD_EXPORT;
                ackRequest = port.exportPaymentDocumentData(setSignIdExportPaymentDocumentRequest(exportRequest), requestHeader, resultHeaderHolder);
            }
            // сохраняем запрос
            answerProcessing.sendToBaseAndAnotherError(curMethodName, requestHeader, null, null, LOGGER);
            answerProcessing.sendMessageToClient(TextForLog.REQUEST_SENDED);

            // ждем результат в асинхронном режиме
            if (ackRequest != null) {
                paymentsAsyncResultWaiter = new PaymentsAsyncResultWaiter(ackRequest, curMethodName, answerProcessing, port);
                result = paymentsAsyncResultWaiter.getRequestResult();
            }
        } catch (Fault fault) {
             answerProcessing.sendServerErrorToClient(curMethodName, requestHeader, LOGGER, fault);
            return null;
        }

        if (result == null) {
            throw new PreGISException("Не удалось получить данные от сервера ГИС ЖКХ");
        }else{
            resultErrorMessage = result.getErrorMessage();
        }

        resultHeader = paymentsAsyncResultWaiter.getHeaderHolder().value;

        answerProcessing.sendToBaseAndAnotherError(curMethodName, null, resultHeader, resultErrorMessage, LOGGER);

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
