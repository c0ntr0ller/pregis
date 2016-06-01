package ru.progmatik.java.pregis.services.nsi.service;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.nsi.ExportDataProviderNsiItemRequest;
import ru.gosuslugi.dom.schema.integration.services.nsi.ExportNsiItemResult;
import ru.gosuslugi.dom.schema.integration.services.nsi_service.Fault;
import ru.gosuslugi.dom.schema.integration.services.nsi_service.NsiPortsType;
import ru.gosuslugi.dom.schema.integration.services.nsi_service.NsiService;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;
import ru.progmatik.java.web.servlets.socket.ClientService;

import javax.xml.ws.Holder;
import java.math.BigInteger;

/**
 * Класс, сервис частной НСИ (hcs-nsi), экспортирует справочники №1, 51, 59 поставщика информации.
 */
public class ExportDataProviderNsiItem {

    private static final Logger LOGGER = Logger.getLogger(ExportDataProviderNsiItem.class);

    private static final String NAME_METHOD = "exportDataProviderNsiItem";

    private final NsiService service = new NsiService();
    private final NsiPortsType port = service.getNsiPort();

    private ClientService clientService;
    private AnswerProcessing answerProcessing;

    /**
     * Конструктор добавляет параметры для соединения.
     * @param clientService лог для пользователя.
     * @param answerProcessing куда сохранять ошибки.
     */
    public ExportDataProviderNsiItem(ClientService clientService, AnswerProcessing answerProcessing) {
        OtherFormat.setPortSettings(service, port);
        this.clientService = clientService;
        this.answerProcessing = answerProcessing;
    }

    /**
     * Метод, отправляет запрос с кодом справочника (1, 51 или 59), получает в ответ указанный справочник.
     * @param code код справочника - 1, 51 или 59.
     */
    public ExportNsiItemResult callExportDataProviderNsiItem(int code) {

        clientService.sendMessage(TextForLog.FORMED_REQUEST + NAME_METHOD);

//        Создание загаловков сообщений (запроса и ответа)
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        Holder<ResultHeader> headerHolder = new Holder<>();

        ExportNsiItemResult result;

        try {
            clientService.sendMessage(TextForLog.SENDING_REQUEST);
            result = port.exportDataProviderNsiItem(getExportDataProviderNsiItemRequest(code), requestHeader, headerHolder);
            clientService.sendMessage(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
        } catch (Fault fault) {
//            Сохраняем ошибку в базу данных
            answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, clientService, LOGGER, fault);
            return null;
        }
        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, headerHolder.value, result.getErrorMessage(), clientService, LOGGER);

        return result;
    }

    /**
     * Метод, формирует объект для дальнейшей передачи его сервису ГИС ЖКХ.
     * Принимает значение 1, 51 или 59, код справочника.
     * @return ExportDataProviderNsiItemRequest объект с указанными значениями, для передачи сервису ГИС ЖКХ.
     */
    private ExportDataProviderNsiItemRequest getExportDataProviderNsiItemRequest(int code) {

        ExportDataProviderNsiItemRequest request = new ExportDataProviderNsiItemRequest();


//        Возможные значения
//        request.setRegistryNumber(BigInteger.valueOf(1));  Дополнительные услуги
//        request.setRegistryNumber(BigInteger.valueOf(51)); Коммунальные услуги
//        request.setRegistryNumber(BigInteger.valueOf(59)); Работы и услуги организации
        request.setRegistryNumber(BigInteger.valueOf(code));

//        Дата и время, измененные после которой элементы справочника должны быть возвращены в ответе.
//        Если не указана, возвращаются все элементы справочника.
//        request.setModifiedAfter();
        request.setId("signed-data-container");

        return request;
    }
}
