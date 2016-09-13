package ru.progmatik.java.pregis.services.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.bills.ExportOrgPeriodRequest;
import ru.gosuslugi.dom.schema.integration.services.bills.ExportOrgPeriodResult;
import ru.gosuslugi.dom.schema.integration.services.bills_service.BillsPortsType;
import ru.gosuslugi.dom.schema.integration.services.bills_service.BillsService;
import ru.gosuslugi.dom.schema.integration.services.bills_service.Fault;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;
import ru.progmatik.java.pregis.other.TextForLog;

import javax.xml.ws.Holder;
import java.sql.SQLException;

/**
 * Класс, экспорт сведений о расчетных периодах организации.
 *
 * Операция позволяет экспортировать из ГИС ЖКХ сведения об
 * открытых расчетных периодах организации и открытых расчетных
 * периодах домов (см. openOrgPaymentPeriod)
 */
public class ExportOrgPeriodData {

    private static final Logger LOGGER = Logger.getLogger(ExportOrgPeriodData.class);

    private static final String NAME_METHOD = "exportOrgPeriodData";

    private final BillsService service = new BillsService();
    private final BillsPortsType port = service.getBillsPort();
    private final AnswerProcessing answerProcessing;

    /**
     * Конструктор, принимает объект для отправкой пользователю уведомлений.
     *
     * @param answerProcessing объект, принимает сообщения для отправки их пользователю.
     */
    public ExportOrgPeriodData(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
        OtherFormat.setPortSettings(service, port);
    }

    /**
     * Метод, получает из ГИС ЖКХ сведения об открытых расчетных
     * периодах организации и открытых расчетных периодах домов.
     */
    public ExportOrgPeriodResult getOrgPeriodData() throws SQLException, PreGISException {


        answerProcessing.sendMessageToClient("::clearLabelText");
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        Holder<ResultHeader> requestHolder = new Holder<>();

        ExportOrgPeriodResult result;


        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            result = port.exportOrgPeriodData(getExportOrgPeriodRequest(), requestHeader, requestHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            return null;

        }
        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, requestHolder.value, result.getErrorMessage(), LOGGER);

        return result;
    }

    private ExportOrgPeriodRequest getExportOrgPeriodRequest() throws PreGISException {

        ExportOrgPeriodRequest request = new ExportOrgPeriodRequest();
        request.setId(OtherFormat.getId());

        if (ResourcesUtil.instance().getCompanyRole().equalsIgnoreCase("RSO")) request.setIsRSO(true);
        else request.setIsUO(true);

        return request;
    }

}
