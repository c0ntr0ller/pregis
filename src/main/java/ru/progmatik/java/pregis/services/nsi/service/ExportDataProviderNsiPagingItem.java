package ru.progmatik.java.pregis.services.nsi.service;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.nsi.ExportDataProviderNsiPagingItemRequest;
import ru.gosuslugi.dom.schema.integration.nsi.ExportNsiPagingItemResult;
import ru.gosuslugi.dom.schema.integration.nsi_service.Fault;
import ru.gosuslugi.dom.schema.integration.nsi_service.NsiPortsType;
import ru.gosuslugi.dom.schema.integration.nsi_service.NsiService;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;
import ru.progmatik.java.pregis.other.UrlLoader;

import javax.xml.ws.Holder;
import java.math.BigInteger;
import java.sql.SQLException;

/**
 * Класс, сервис частной НСИ (hcs-nsi), экспортирует справочники №1, 51, 59 поставщика информации.
 */
public class ExportDataProviderNsiPagingItem {

    private static final Logger LOGGER = Logger.getLogger(ExportDataProviderNsiPagingItem.class);

    private static final String NAME_METHOD = "exportDataProviderNsiPagingItem";

    private final NsiPortsType port;
    private AnswerProcessing answerProcessing;

    /**
     * Конструктор добавляет параметры для соединения.
     * @param answerProcessing куда сохранять ошибки.
     */
    public ExportDataProviderNsiPagingItem(AnswerProcessing answerProcessing) {

        this.answerProcessing = answerProcessing;

        NsiService service = UrlLoader.instance().getUrlMap().get("nsi") == null ? new NsiService()
                : new NsiService(UrlLoader.instance().getUrlMap().get("nsi"));
        port = service.getNsiPort();
        OtherFormat.setPortSettings(service, port);
    }

    /**
     * Метод, отправляет запрос с кодом справочника (1, 51 или 59), получает в ответ указанный справочник.
     * @param code код справочника - 1, 51 или 59.
     */
    public ExportNsiPagingItemResult callExportDataProviderNsiItem(String code) throws SQLException {

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);

//        Создание загаловков сообщений (запроса и ответа)
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        Holder<ResultHeader> headerHolder = new Holder<>();

        ExportNsiPagingItemResult result;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            result = port.exportDataProviderPagingNsiItem(getExportDataProviderNsiPagingItemRequest(code), requestHeader, headerHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
        } catch (Fault fault) {
//            Сохраняем ошибку в базу данных
            answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
//            throw fault;
            return null;
        }
        // TODO добавить обработку ошибки INT002012 Нет объектов для экспорта
        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, headerHolder.value, result.getErrorMessage(), LOGGER);

        if (result.getErrorMessage() != null) {
            return null;
        }

        return result;
    }

    /**
     * Метод, формирует объект для дальнейшей передачи его сервису ГИС ЖКХ.
     * Принимает значение 1, 51 или 59, код справочника.
     * @return ExportDataProviderNsiItemRequest объект с указанными значениями, для передачи сервису ГИС ЖКХ.
     */
    private ExportDataProviderNsiPagingItemRequest getExportDataProviderNsiPagingItemRequest(String code) {

        ExportDataProviderNsiPagingItemRequest request = new ExportDataProviderNsiPagingItemRequest();


//        Возможные значения
//        request.setRegistryNumber(BigInteger.valueOf(1));  Дополнительные услуги
//        request.setRegistryNumber(BigInteger.valueOf(51)); Коммунальные услуги
//        request.setRegistryNumber(BigInteger.valueOf(59)); Работы и услуги организации
        request.setRegistryNumber(new BigInteger(code));

//        Дата и время, измененные после которой элементы справочника должны быть возвращены в ответе.
//        Если не указана, возвращаются все элементы справочника.
//        request.setModifiedAfter();
        request.setId("signed-data-container");
        request.setPage(1); //TODO: разобраться с постраничным запросом данных
        request.setVersion(request.getVersion());

        return request;
    }
}
