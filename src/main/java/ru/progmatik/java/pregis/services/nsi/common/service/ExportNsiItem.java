package ru.progmatik.java.pregis.services.nsi.common.service;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.HeaderType;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.nsi_common.ExportNsiItemRequest;
import ru.gosuslugi.dom.schema.integration.nsi_common.ExportNsiItemResult;
import ru.gosuslugi.dom.schema.integration.nsi_common_service.Fault;
import ru.gosuslugi.dom.schema.integration.nsi_common_service.NsiPortsType;
import ru.gosuslugi.dom.schema.integration.nsi_common_service.NsiService;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;

import javax.xml.ws.Holder;
import java.math.BigInteger;

/**
 * Класс, позволяет получить записи конкретного справочника, а также только те записи справочника,
 * которые изменились после временной метки ModifiedAfter.
 * Created by andryha on 29.04.2016.
 */
public class ExportNsiItem {

    private static final Logger LOGGER = Logger.getLogger(ExportNsiItem.class);
    private static final String NAME_METHOD = "exportNsiItem";

    private final NsiService service = new NsiService();
    private final NsiPortsType port = service.getNsiPort();

    private AnswerProcessing answerProcessing;

    public ExportNsiItem(AnswerProcessing answerProcessing) {
        OtherFormat.setPortSettings(service, port);
        this.answerProcessing = answerProcessing;
    }

    public ExportNsiItemResult callExportNsiItem(NsiListGroupEnum nsi, BigInteger codeNsi) {

        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);

        HeaderType requestHeader = OtherFormat.getISRequestHeader();

        Holder<ResultHeader> headerHolder = new Holder<>();

        ExportNsiItemResult result;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            result = port.exportNsiItem(getExportNsiItemRequest(nsi, codeNsi), requestHeader, headerHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            return null;
        }

        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, headerHolder.value, result.getErrorMessage(), LOGGER);

        return result;
    }

    /**
     * Метод, формирует объект для дальнейшей передачи его сервису ГИС ЖКХ.
     * @return ExportNsiItemRequest объект с указанными значениями, для передачи сервису ГИС ЖКХ.
     */
    private ExportNsiItemRequest getExportNsiItemRequest(NsiListGroupEnum nsi, BigInteger codeNsi) {

        ExportNsiItemRequest request = new ExportNsiItemRequest();
        request.setId("signed-data-container");
//        request.setListGroup("NSI"); // Обязательный атрибут
        request.setListGroup(nsi.getNsi()); // Обязательный атрибут
        request.setRegistryNumber(codeNsi); // Обязательный атрибут
        request.setVersion(request.getVersion());
//        request.setRegistryNumber(BigInteger.valueOf(225)); // Обязательный атрибут

        return request;
    }

}
