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
import ru.progmatik.java.pregis.other.OtherFormat;

import javax.xml.ws.Holder;
import java.sql.SQLException;

/**
 * Класс, экспорт сведений о платежных документах.
 */
public class ExportPaymentDocumentData {

    private static final Logger LOGGER = Logger.getLogger(ExportPaymentDocumentData.class);

    private static final String NAME_METHOD = "exportPaymentDocumentData";

    private final BillsService service = new BillsService();
    private final BillsPortsType port = service.getBillsPort();

    /**
     * Конструктор, добавляет параметры для соединения.
     */
    public ExportPaymentDocumentData() {
        OtherFormat.setPortSettings(service, port);
    }

    /**
     * Метод, экспорт сведений о платежных документах (exportPaymentDocumentData) из ГИС ЖКХ.
     */
    public void callExportPaymentDocumentData() throws SQLException {

//        Создание загаловков сообщений (запроса и ответа)
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        Holder<ResultHeader> headerHolder = new Holder<>();
//        Создание объекта для сохранения лога сообщений в БД.
        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        ExportPaymentDocumentResult result;

        try {
            result = port.exportPaymentDocumentData(getExportPaymentDocumentRequest(), requestHeader, headerHolder);
        } catch (Fault fault) {
            saveToBase.setRequestError(requestHeader, NAME_METHOD, fault);
            LOGGER.error(fault.getMessage());
            fault.printStackTrace();
            return;
        }

        saveToBase.setRequest(requestHeader, NAME_METHOD);

        saveToBase.setResult(headerHolder.value, NAME_METHOD, result.getErrorMessage());

        LOGGER.info("ExportPaymentDocumentData - Successful.");

    }

    /**
     * Метод, формирует объект для дальнейшей передачи его сервису ГИС ЖКХ.
     * @return ExportPaymentDocumentRequest объект с указанными значениями, для передачи сервису ГИС ЖКХ.
     */
    private ExportPaymentDocumentRequest getExportPaymentDocumentRequest() {

        ExportPaymentDocumentRequest request = new ExportPaymentDocumentRequest();
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        request.setYear((short) 2016);
        request.setMonth(05);
//        request.setFIASHouseGuid("b58c5da4-8d62-438f-b11e-d28103220952");
        request.setFIASHouseGuid("f20a2f00-c9cf-485f-ac41-92af5b77e29a");
        request.getPaymentDocumentNumber().add("7777");
//        request.getAccountNumber().add("1000438820");
//        request.getAccountGuid().add("3cab9a71-d8ae-42b2-8caa-42e92aa9aba2"); // л.с. 1000438820
//        request.getAccountGuid().add("9498a608-2a8c-4f3e-8243-de76bad293b7"); // л.с. 1000438821  80АА001486
//        Версии 9.0.1.3 перестало существовать
//        request.getAccountGuid().add("6fe12a9b-2bbf-4248-940d-9dd2ba1d868e"); // л.с. 1000438822  50АА001491
//        request.getUnifiedAccountNumber().add("50АА001491"); // л.с. 1000438822  50АА001491 попробовать

        return request;
    }
}
