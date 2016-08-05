package ru.progmatik.java.pregis.services.device_metering;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.device_metering.ExportMeteringDeviceHistoryRequest;
import ru.gosuslugi.dom.schema.integration.services.device_metering.ExportMeteringDeviceHistoryResult;
import ru.gosuslugi.dom.schema.integration.services.device_metering_service.DeviceMeteringPortTypes;
import ru.gosuslugi.dom.schema.integration.services.device_metering_service.DeviceMeteringService;
import ru.gosuslugi.dom.schema.integration.services.device_metering_service.Fault;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;

import javax.xml.ws.Holder;
import java.sql.SQLException;

/**
 * Класс, формирует запрос и получает ответ от сервиса ГИС ЖКХ по SOAP протаколу.
 */
public final class ExportMeteringDeviceHistory {

    private static final Logger LOGGER = Logger.getLogger(ExportMeteringDeviceHistory.class);
    private static final String NAME_METHOD = "exportMeteringDeviceHistory";

    private final DeviceMeteringService service = new DeviceMeteringService();
    private final DeviceMeteringPortTypes port = service.getDeviceMeteringPort();
    private final AnswerProcessing answerProcessing;

    /**
     * Конструктор, принимает объект для отправкой пользователю уведомлений.
     *
     * @param answerProcessing объект, который принимает сообщения для пользователя.
     */
    public ExportMeteringDeviceHistory(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
        OtherFormat.setPortSettings(service, port);
    }

    /**
     * Метод, по указанному коду дома по ФИАС, формирует запрос, отправляет в ГИС ЖКХ, возвращает ответ на запрос.
     * @param fias код дома по ФИАС
     * @return ответ от ГИС ЖКХ
     * @throws SQLException
     */
    public ExportMeteringDeviceHistoryResult getExportMeteringHistoryResultByFIAS(String fias) throws SQLException {

        ExportMeteringDeviceHistoryRequest request = new ExportMeteringDeviceHistoryRequest();
        request.setFIASHouseGuid(fias); // b58c5da4-8d62-438f-b11e-d28103220952
//        request.getMeteringDeviceRootGUID().add("867812e9-3304-4c80-b0ba-821fba775469");
//        request.getMeteringDeviceRootGUID().add("0966d718-a5af-4770-a82b-0239f99b0214");

        return getExportMeteringHistoryResult(request);
    }

    /**
     * Метод, по указанному коду дома по ФИАС, формирует запрос, отправляет в ГИС ЖКХ, возвращает ответ на запрос.
     * @param request объект с параметрами для запроса
     * @return ответ от ГИС ЖКХ
     * @throws SQLException
     */
    public ExportMeteringDeviceHistoryResult getExportMeteringHistoryResult(ExportMeteringDeviceHistoryRequest request) throws SQLException {

//        ExportMeteringDeviceHistoryRequest request = new ExportMeteringDeviceHistoryRequest();
//        request.setFIASHouseGuid(fias); // b58c5da4-8d62-438f-b11e-d28103220952
//        request.getMeteringDeviceType()
//        request.getMeteringDeviceRootGUID().add("867812e9-3304-4c80-b0ba-821fba775469");
//        request.getMeteringDeviceRootGUID().add("0966d718-a5af-4770-a82b-0239f99b0214");

        return callExportMeteringDeviceHistory(request);
    }

    /**
     * Метод, указывает идентификотор для блока, который будет в дальнейшем подписан ЭЦП, формирует запрос,
     * отправляет и получает ответ на запрос.
     *
     * @return ответ ГИС ЖКХ на запрос "exportMeteringDeviceHistory".
     * @throws SQLException
     */
    private ExportMeteringDeviceHistoryResult callExportMeteringDeviceHistory(ExportMeteringDeviceHistoryRequest request) throws SQLException {

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        Holder<ResultHeader> headerHolder = new Holder<>();

        ExportMeteringDeviceHistoryResult result;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            result = port.exportMeteringDeviceHistory(setSignIdExportMeteringHistoryRequest(request), requestHeader, headerHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            return null;
        }
        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, headerHolder.value, result.getErrorMessage(), LOGGER);
        if (result.getErrorMessage() != null) return null;
        return result;
    }

    /**
     * Метод, добавляет идентификатор для дальнейшего подписания.
     *
     * @param request подготовленный запрос.
     * @return запрос с указанным идентификатором для цифровой подписи.
     */
    private ExportMeteringDeviceHistoryRequest setSignIdExportMeteringHistoryRequest(ExportMeteringDeviceHistoryRequest request) {
        request.setId(OtherFormat.getId());
        return request;
    }

}
