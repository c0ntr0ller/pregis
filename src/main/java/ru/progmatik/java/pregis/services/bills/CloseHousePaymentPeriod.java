package ru.progmatik.java.pregis.services.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.ImportResult;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.bills.CloseHousePaymentPeriodRequest;
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
 * Класс, закрывает открытый расчетный период дома
 */
public class CloseHousePaymentPeriod {

    private static final Logger LOGGER = Logger.getLogger(CloseHousePaymentPeriod.class);

    private static final String NAME_METHOD = "сloseHousePaymentPeriod";

    private final BillsService service = new BillsService();
    private final BillsPortsType port = service.getBillsPort();
    private final AnswerProcessing answerProcessing;

    public CloseHousePaymentPeriod(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    /**
     * Метод, принимает код дома по ФИАС, формирует запрос для закрытия расчетного периода.
     * @param fias код дома по ФИАС.
     * @return результат, ответ от ГИС ЖКХ.
     * @throws PreGISException
     * @throws SQLException
     */
    public ImportResult setCloseHousePaymentPeriod(String fias) throws PreGISException, SQLException {

        CloseHousePaymentPeriodRequest request = getBasicCloseHousePaymentPeriod();
        CloseHousePaymentPeriodRequest.HousePeriod housePeriod = new CloseHousePaymentPeriodRequest.HousePeriod();
        housePeriod.setFIASHouseGuid(fias);
        housePeriod.setTransportGUID(OtherFormat.getRandomGUID());
        if (ResourcesUtil.instance().getCompanyRole().equalsIgnoreCase("RSO"))
            housePeriod.setIsRSO(true);
        else
            housePeriod.setIsUO(true);

        request.getHousePeriod().add(new CloseHousePaymentPeriodRequest.HousePeriod());

        return callCloseHousePaymentPeriod(request);
    }

    /**
     * Метод, отправляет запрос в ГИС ЖКХ и получает ответ.
     * @param request запрос.
     * @return ответ от ГИС ЖКХ.
     * @throws SQLException
     */
    private ImportResult callCloseHousePaymentPeriod(CloseHousePaymentPeriodRequest request) throws SQLException {

        answerProcessing.sendMessageToClient("::clearLabelText");
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        Holder<ResultHeader> requestHolder = new Holder<>();

        ru.gosuslugi.dom.schema.integration.base.ImportResult result;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            result = port.closeHousePaymentPeriod(request, requestHeader, requestHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            return null;

        }
        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, requestHolder.value, result.getErrorMessage(), LOGGER);

        return result;
    }

    /**
     * Метод, добавляет идентификатор подписи к запросу.
     * @return запрос.
     */
    private CloseHousePaymentPeriodRequest getBasicCloseHousePaymentPeriod() {

        CloseHousePaymentPeriodRequest request = new CloseHousePaymentPeriodRequest();
        request.setId(OtherFormat.getId());
        return request;
    }
}
