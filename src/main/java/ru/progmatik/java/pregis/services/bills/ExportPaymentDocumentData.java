package ru.progmatik.java.pregis.services.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.bills.ExportPaymentDocumentRequest;
import ru.gosuslugi.dom.schema.integration.services.bills.ExportPaymentDocumentResult;
import ru.gosuslugi.dom.schema.integration.services.bills_service.BillsPortsType;
import ru.gosuslugi.dom.schema.integration.services.bills_service.BillsService;
import ru.gosuslugi.dom.schema.integration.services.bills_service.Fault;
import ru.progmatik.java.pregis.connectiondb.SaveToBaseMessages;
import ru.progmatik.java.pregis.other.OtherFormat;

import javax.xml.ws.Holder;

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
    public void callExportPaymentDocumentData() {

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
        request.setYear((short) 2016);
        request.setMonth(05);
//        request.getAccountGuid().add("3cab9a71-d8ae-42b2-8caa-42e92aa9aba2"); // л.с. 1000438820
        request.getAccountGuid().add("9498a608-2a8c-4f3e-8243-de76bad293b7"); // л.с. 1000438821  80АА001486

        return request;
    }
}
