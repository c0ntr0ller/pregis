package ru.progmatik.java.pregis.services.payment;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.AckRequest;
import ru.gosuslugi.dom.schema.integration.base.GetStateRequest;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.payment.ExportPaymentDocumentDetailsRequest;
import ru.gosuslugi.dom.schema.integration.payment.GetStateResult;
import ru.gosuslugi.dom.schema.integration.payment.Individual;
import ru.gosuslugi.dom.schema.integration.payment_service_async.Fault;
import ru.gosuslugi.dom.schema.integration.payment_service_async.PaymentPortsTypeAsync;
import ru.gosuslugi.dom.schema.integration.payment_service_async.PaymentsServiceAsync;
import ru.progmatik.java.pregis.connectiondb.localdb.message.SaveToBaseMessages;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.UrlLoader;

import javax.xml.ws.Holder;
import java.sql.SQLException;

/**
 * Класс, экспорт реквизитов платежных документов (exportPaymentDocumentDetails).
 * Операция выполняет экспорт реквизитов и сведений о платежных документах. Согласно п. 124 589/944/пр., организация, через которую вносится плата, выполняет поиск начислений в ГИС ЖКХ по следующим атрибутам (или):
 * •	Идентификатор платежного документа;
 * •	ЕЛС и период, за который выставлены начисления;
 * •	Идентификатор ЖКУ и период, за который выставлены начисления
 * •	Номер лицевого счета, присвоенный плательщику исполнителе (поставщиком информации), адрес дома по ФИАС и период, за который выставлены начисления;
 * •	Номер платежного документа, присвоенный плательщику исполнителе (поставщиком информации), адрес дома по ФИАС и период, за который выставлены начисления.
 * Если поиск производился по идентификатору ЖКУ, то ГИС ЖКХ возвращает ветку Service, куда входят все не полностью оплаченные платежные документы, где присутствует данная ЖКУ; для остальных вариантов поисковых параметров возвращаются все начисления не полностью оплаченные платежные документы.
 * ГИС ЖКХ в ответ возвращает список найденных документов вместе с системными ключами (PaymentDocumentGuid), которые передаются в извещении, а также платежные реквизиты получателя платежа
 * Согласно п. 125 589/944/пр., остаток суммы по ПД к оплате возвращается только в случае, если передан AmountRequired и реквизиты плательщика полностью совпали с данными в лицевом счете. Для плательщиков-физлиц выполняется строковое сравнение по фамилии, имени и отчеству без учета регистра символов, «е» и «ё» считаются разными символами. Если совпадение не выполнено, то платежный документ возвращается без остатка к оплате и соответствующей отметкой об ошибке.
 */
@Deprecated
public class ExportPaymentDocumentDetails {

    private static final Logger LOGGER = Logger.getLogger(ExportPaymentDocumentDetails.class);

    private static final String NAME_METHOD = "exportPaymentDocumentDetails";

    private final PaymentPortsTypeAsync portAsync;

    /**
     * Конструктор, добавляет параметры для соединения.
     */
    public ExportPaymentDocumentDetails() {

        PaymentsServiceAsync serviceAsync = UrlLoader.instance().getUrlMap().get("paymentAsync") == null ? new PaymentsServiceAsync()
                : new PaymentsServiceAsync(UrlLoader.instance().getUrlMap().get("paymentAsync"));

        portAsync = serviceAsync.getPaymentPortAsync();
        OtherFormat.setPortSettings(serviceAsync, portAsync);
    }

    /**
     * Метод, экспорт реквизитов платежных документов для банков и т.п. контор.
     */
    public void callExportPaymentDocumentDetails() throws SQLException {

//        Создание загаловков сообщений (запроса и ответа)
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        Holder<ResultHeader> headerHolder = new Holder<>();
//        Создание объекта для сохранения лога сообщений в БД.
        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        AckRequest request;

        try {
            request = portAsync.exportPaymentDocumentDetails(getExportPaymentDocumentDetailsRequest(), requestHeader, headerHolder);
//            request.getAck();
        } catch (Fault fault) {
            saveToBase.setRequestError(requestHeader, NAME_METHOD, fault);
            LOGGER.error(fault.getMessage());
            fault.printStackTrace();
            return;
        }

        saveToBase.setRequest(requestHeader, NAME_METHOD);

//        saveToBase.setResult(headerHolder.value, NAME_METHOD, request.getErrorMessage());
    }

    public void getStateExportPaymentDocumentDetails() throws SQLException {

        //        Создание загаловков сообщений (запроса и ответа)
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        Holder<ResultHeader> headerHolder = new Holder<>();
//        Создание объекта для сохранения лога сообщений в БД.
        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        GetStateRequest request = new GetStateRequest();
//        request.setMessageGUID("df8a3635-0a20-40dd-aff8-51651d28075d");
//        request.setMessageGUID("9310875e-7b2a-4377-844b-a3a04fb95e14");
        request.setMessageGUID("bb720824-d0a2-4f57-a1d4-97742f86de81");

        GetStateResult result;
        try {
            result = portAsync.getState(request, requestHeader, headerHolder);
            GetStateResult.ExportPaymentDocumentDetailsResult answer = result.getExportPaymentDocumentDetailsResult();
        } catch (Fault fault) {
            saveToBase.setRequestError(requestHeader, "getState", fault);
            LOGGER.error(fault.getMessage());
            fault.printStackTrace();
            return;
        }

        saveToBase.setResult(headerHolder.value, NAME_METHOD, result.getErrorMessage());

    }

    private ExportPaymentDocumentDetailsRequest getExportPaymentDocumentDetailsRequest() {

        ExportPaymentDocumentDetailsRequest request = new ExportPaymentDocumentDetailsRequest();
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        request.setPaymentDocumentID("00АА001470-01-6051");
//        request.setPaymentDocumentID("30АА001481-01-6051");
        request.setAmountRequired(new ExportPaymentDocumentDetailsRequest.AmountRequired());
        Individual individual = new Individual();
        individual.setFirstName("АНТОНИНА");
        individual.setSurname("СКРИПНИКОВА");
        individual.setPatronymic("ФЕДОРОВНА");
        request.getAmountRequired().setIndividual(individual);

        return request;
    }

}
