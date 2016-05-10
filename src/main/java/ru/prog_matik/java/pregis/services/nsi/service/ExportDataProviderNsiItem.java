package ru.prog_matik.java.pregis.services.nsi.service;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.nsi.ExportDataProviderNsiItemRequest;
import ru.gosuslugi.dom.schema.integration.services.nsi.ExportNsiItemResult;
import ru.gosuslugi.dom.schema.integration.services.nsi_service.Fault;
import ru.gosuslugi.dom.schema.integration.services.nsi_service.NsiPortsType;
import ru.gosuslugi.dom.schema.integration.services.nsi_service.NsiService;
import ru.prog_matik.java.pregis.connectiondb.SaveToBaseMessages;
import ru.prog_matik.java.pregis.other.OtherFormat;

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

    /**
     * Конструктор, добавляет параметры для соединения.
     */
    public ExportDataProviderNsiItem() {
        OtherFormat.setPortSettings(service, port);
    }

    public void callExportDataProviderNsiItem() {

//        Создание загаловков сообщений (запроса и ответа)
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        Holder<ResultHeader> headerHolder = new Holder<>();
//        Создание объекта для сохранения лога сообщений в БД.
        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        ExportNsiItemResult result;

        try {
            result = port.exportDataProviderNsiItem(getExportDataProviderNsiItemRequest(), requestHeader, headerHolder);
        } catch (Fault fault) {
//            Сохраняем ошибку в базу данных
            saveToBase.setRequestError(requestHeader, NAME_METHOD, fault);
            LOGGER.error(fault.getMessage());
            fault.printStackTrace();
            return;
        }
        saveToBase.setRequest(requestHeader, NAME_METHOD);

        saveToBase.setResult(headerHolder.value, NAME_METHOD, result.getErrorMessage());

        LOGGER.info("ExportDataProviderNsiItem - Successful.");
    }

    /**
     * Метод, формирует объект для дальнейшей передачи его сервису ГИС ЖКХ.
     * @return ExportDataProviderNsiItemRequest объект с указанными значениями, для передачи сервису ГИС ЖКХ.
     */
    private ExportDataProviderNsiItemRequest getExportDataProviderNsiItemRequest() {

        ExportDataProviderNsiItemRequest request = new ExportDataProviderNsiItemRequest();
//        request.setRegistryNumber(BigInteger.valueOf(1));

//        Возможные значения
//        request.setRegistryNumber(BigInteger.valueOf(51));
        request.setRegistryNumber(BigInteger.valueOf(59));

//        Дата и время, измененные после которой элементы справочника должны быть возвращены в ответе.
//        Если не указана, возвращаются все элементы справочника.
//        request.setModifiedAfter();
        request.setId("signed-data-container");

        return request;
    }
}
