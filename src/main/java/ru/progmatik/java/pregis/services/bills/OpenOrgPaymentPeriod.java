package ru.progmatik.java.pregis.services.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.ImportResult;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.bills.OpenOrgPaymentPeriodRequest;
import ru.gosuslugi.dom.schema.integration.bills.PaymentPeriodType;
import ru.gosuslugi.dom.schema.integration.bills_service.BillsPortsType;
import ru.gosuslugi.dom.schema.integration.bills_service.BillsService;
import ru.gosuslugi.dom.schema.integration.bills_service.Fault;
import ru.progmatik.java.pregis.ProgramAction;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.*;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;
import java.sql.SQLException;

/**
 * Класс, открывает первый (очередной) расчетный период организации (УО или РСО)
 * в ГИС ЖКХ для передачи сведений о начислениях за период.
 * <p>
 * Для каждого дома, для которого в ГИС ЖКХ есть сведения о:
 * •	договоре управления для поставщика данных с полномочием «УО»;
 * •	поставках коммунальных услуг по прямым договорам с ресурсоснабжающей организацией – поставщиком данных;
 * открывается расчетный период дома.
 * Поскольку поставщик данных может обладать несколькими привилегиями,
 * необходимо явно указать привилегию, для которой открывается расчетный период.
 * Операция делает возможным передачу сведений о начислениях, платежах, задолженностях, платежных документах.
 * Операция автоматически закрывает предыдущий открытый расчетный период организации
 * и все открытые в рамках него расчетные периоды домов.
 */
public class OpenOrgPaymentPeriod {

    private static final Logger LOGGER = Logger.getLogger(OpenOrgPaymentPeriod.class);

    private static final String NAME_METHOD = "openOrgPaymentPeriod";

    private final BillsService service;
    private final BillsPortsType port;
    private final AnswerProcessing answerProcessing;

    public OpenOrgPaymentPeriod(AnswerProcessing answerProcessing, ProgramAction action) {

        this.answerProcessing = answerProcessing;

        service = UrlLoader.instance().getUrlMap().get("bills") == null ? new BillsService()
                : new BillsService(UrlLoader.instance().getUrlMap().get("bills"));
        port = service.getBillsPort();
        OtherFormat.setPortSettings(service, port);
    }

    public ImportResult requestNextOpenOrgPaymentPeriod() throws PreGISException, SQLException {
        return openOrgPaymentPeriod(createNextOpenOrgPaymentPeriod());
    }

    /**
     * МетодЮ получает дату на которую необходимо открыть расчетный период.
     * @param calendar текущая дата.
     * @return ответ от сервиса ГИС ЖКХ.
     * @throws PreGISException
     * @throws SQLException
     */
    public ImportResult requestDateOpenOrgPaymentPeriod(XMLGregorianCalendar calendar) throws PreGISException, SQLException {
        return openOrgPaymentPeriod(createWithDateOpenOrgPaymentPeriod(calendar));
    }

    /**
     * Метод, формирует, отправляет запрос и получает ответ с сервиса ГИС ЖКХ.
     * @param request готовый запрос.
     * @return ответ от сервиса ГИС ЖКХ.
     * @throws SQLException
     * @throws PreGISException
     */
    private ImportResult openOrgPaymentPeriod(OpenOrgPaymentPeriodRequest request) throws SQLException, PreGISException {

        answerProcessing.sendMessageToClient("::clearLabelText");
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        Holder<ResultHeader> requestHolder = new Holder<>();

        ru.gosuslugi.dom.schema.integration.base.ImportResult result;


        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            result = port.openOrgPaymentPeriod(request, requestHeader, requestHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            return null;

        }
        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, requestHolder.value, result.getErrorMessage(), LOGGER);

        return result;
    }

    /**
     * Метод, создаёт новый запрос с загаловком и идентификатором подписи.
     *
     * @return новый запрос.
     */
    private OpenOrgPaymentPeriodRequest createBaseOpenOrgPaymentPeriod() {

        OpenOrgPaymentPeriodRequest request = new OpenOrgPaymentPeriodRequest();
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());
        request.setTransportGUID(OtherFormat.getRandomGUID());

        return request;
    }

    /**
     * Метод, открывает следующий расчетный период.
     *
     * @return готовы запрос для ГИС ЖКХ.
     * @throws PreGISException
     */
    private OpenOrgPaymentPeriodRequest createNextOpenOrgPaymentPeriod() throws PreGISException {

        OpenOrgPaymentPeriodRequest request = createBaseOpenOrgPaymentPeriod();

        if (ResourcesUtil.instance().getCompanyRole().equalsIgnoreCase("UO")) request.setCreateNextPeriodUO(true);
        else request.setCreateNextPeriodRSO(true);

        return request;
    }

    /**
     * Метод, создаёт новый период если не было создано ещё неодного расчетного периода.
     * @return готовый запрос.
     * @throws PreGISException
     * @throws SQLException
     */
    private OpenOrgPaymentPeriodRequest createWithDateOpenOrgPaymentPeriod(XMLGregorianCalendar calendar) throws PreGISException, SQLException {

        OpenOrgPaymentPeriodRequest request = createBaseOpenOrgPaymentPeriod();

        request.setCreateFirstPeriod(new PaymentPeriodType());

        if (ResourcesUtil.instance().getCompanyRole().equalsIgnoreCase("UO")) request.getCreateFirstPeriod().setIsUO(true);
        else request.getCreateFirstPeriod().setIsRSO(true);

        request.getCreateFirstPeriod().setMonth(calendar.getMonth());
        request.getCreateFirstPeriod().setYear((short) calendar.getYear());

        return request;
    }
}
