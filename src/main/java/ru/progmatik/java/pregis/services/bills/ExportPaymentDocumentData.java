package ru.progmatik.java.pregis.services.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.bills.ExportPaymentDocumentRequest;
import ru.gosuslugi.dom.schema.integration.bills.ExportPaymentDocumentResult;
import ru.gosuslugi.dom.schema.integration.bills_service.BillsPortsType;
import ru.gosuslugi.dom.schema.integration.bills_service.BillsService;
import ru.gosuslugi.dom.schema.integration.bills_service.Fault;
import ru.progmatik.java.pregis.connectiondb.localdb.message.SaveToBaseMessages;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;

import javax.xml.ws.Holder;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

/**
 * Класс, экспорт сведений о платежных документах.
 */
public class ExportPaymentDocumentData {

    private static final Logger LOGGER = Logger.getLogger(ExportPaymentDocumentData.class);

    private static final String NAME_METHOD = "exportPaymentDocumentData";

    private final BillsService service = new BillsService();
    private final BillsPortsType port = service.getBillsPort();
    private final AnswerProcessing answerProcessing;

    /**
     * Конструктор, добавляет параметры для соединения.
     */
    public ExportPaymentDocumentData(AnswerProcessing answerProcessing) {
        OtherFormat.setPortSettings(service, port);
        this.answerProcessing = answerProcessing;
    }

    /**
     * Метод, получает код дома по ФИАС и период за который нужно получить данные о платежных документах.
     * @param accountList список номеров счетов.
     * @param fias код дома по ФИАС.
     * @param periodExport период за который нужно получить платежный документ.
     * @return ответ от ГИС ЖКХ (ExportPaymentDocumentData).
     * @throws SQLException
     */
    public ExportPaymentDocumentResult getExportPaymentDocumentDataWithAccountNumbers(
            java.util.List<java.lang.String> accountList, List<String> listServiceID, String fias, Calendar periodExport) throws SQLException {

        return callExportPaymentDocumentData(getExportPaymentDocumentRequest(accountList, listServiceID, fias, periodExport));
    }

    /**
     * Метод, экспорт сведений о платежных документах (exportPaymentDocumentData) из ГИС ЖКХ.
     */
    public ExportPaymentDocumentResult callExportPaymentDocumentData(ExportPaymentDocumentRequest request) throws SQLException {

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);

//        Создание загаловков сообщений (запроса и ответа)
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        Holder<ResultHeader> headerHolder = new Holder<>();
//        Создание объекта для сохранения лога сообщений в БД.
        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        ExportPaymentDocumentResult result;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            result = port.exportPaymentDocumentData(request, requestHeader, headerHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);

//            Если есть ошибка вывидет пользователю
            answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, headerHolder.value,
                    result.getErrorMessage(), LOGGER);

        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            return null;
        }

        return result;
    }

    /**
     * Метод, формирует объект для дальнейшей передачи его сервису ГИС ЖКХ.
     * @return ExportPaymentDocumentRequest объект с указанными значениями, для передачи сервису ГИС ЖКХ.
     */
    private ExportPaymentDocumentRequest getExportPaymentDocumentRequest(java.util.List<java.lang.String> accountList,
                                                                         List<String> listServiceID,
                                                                         String fias, Calendar periodExport) {

        ExportPaymentDocumentRequest request = new ExportPaymentDocumentRequest();
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        request.setFIASHouseGuid(fias);
        request.setYear((short) periodExport.get(Calendar.YEAR));
        request.setMonth(periodExport.get(Calendar.MONTH));
        request.getAccountNumber().addAll(accountList);
//        request.getServiceID().addAll(listServiceID);
//        request.getAccountNumber().addAll(accountList);

//        request.get
//        request.setFIASHouseGuid("b58c5da4-8d62-438f-b11e-d28103220952");
//        request.getPaymentDocumentNumber().add("7777");
//        request.getAccountNumber().add("1000438820");
//        request.getAccountGuid().add("3cab9a71-d8ae-42b2-8caa-42e92aa9aba2"); // л.с. 1000438820
//        request.getAccountGuid().add("9498a608-2a8c-4f3e-8243-de76bad293b7"); // л.с. 1000438821  80АА001486
//        Версии 9.0.1.3 перестало существовать
//        request.getAccountGuid().add("6fe12a9b-2bbf-4248-940d-9dd2ba1d868e"); // л.с. 1000438822  50АА001491
//        request.getUnifiedAccountNumber().add("50АА001491"); // л.с. 1000438822  50АА001491 попробовать

        return request;
    }
}
