package ru.prog_matik.java.pregis.services.payment;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.*;
import ru.gosuslugi.dom.schema.integration.services.payment.ExportPaymentDocumentDetailsRequest;
import ru.gosuslugi.dom.schema.integration.services.payment.GetStateResult;
import ru.gosuslugi.dom.schema.integration.services.payment_service_async.Fault;
import ru.gosuslugi.dom.schema.integration.services.payment_service_async.PaymentPortsTypeAsync;
import ru.gosuslugi.dom.schema.integration.services.payment_service_async.PaymentsServiceAsync;
import ru.prog_matik.java.pregis.connectiondb.SaveToBaseMessages;
import ru.prog_matik.java.pregis.other.OtherFormat;

import javax.xml.ws.Holder;

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
public class ExportPaymentDocumentDetails {

    private static final Logger LOGGER = Logger.getLogger(ExportPaymentDocumentDetails.class);

    private static final String NAME_METHOD = "exportPaymentDocumentDetails";

    private final PaymentsServiceAsync serviceAsync = new PaymentsServiceAsync();
    private final PaymentPortsTypeAsync portAsync = serviceAsync.getPaymentPortAsync();

    /**
     * Конструктор, добавляет параметры для соединения.
     */
    public ExportPaymentDocumentDetails() {

        OtherFormat.setPortSettings(serviceAsync, portAsync);
    }

    /**
     * Метод, экспорт реквизитов платежных документов для банков и т.п. контор.
     */
    public void callExportPaymentDocumentDetails() {

//        Создание загаловков сообщений (запроса и ответа)
        HeaderType requestHeader = OtherFormat.getISRequestHeader();
        Holder<ResultHeader> headerHolder = new Holder<>();
//        Создание объекта для сохранения лога сообщений в БД.
        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        AckRequest request;

        try {
            request = portAsync.exportPaymentDocumentDetails(getExportPaymentDocumentDetailsRequest(), requestHeader, headerHolder);
        } catch (Fault fault) {
            saveToBase.setRequestError(requestHeader, NAME_METHOD, fault);
            LOGGER.error(fault.getMessage());
            fault.printStackTrace();
            return;
        }

        saveToBase.setRequest(requestHeader, NAME_METHOD);

//        saveToBase.setResult(headerHolder.value, NAME_METHOD, request.getErrorMessage());
    }

    public void getStateExportPaymentDocumentDetails() {

        //        Создание загаловков сообщений (запроса и ответа)
        HeaderType requestHeader = OtherFormat.getISRequestHeader();
        Holder<ResultHeader> headerHolder = new Holder<>();
//        Создание объекта для сохранения лога сообщений в БД.
        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        GetStateRequest request = new GetStateRequest();
        request.setMessageGUID("");

        try {
            GetStateResult result = portAsync.getState(request, requestHeader, headerHolder);
        } catch (Fault fault) {
            saveToBase.setRequestError(requestHeader, "getState", fault);
            LOGGER.error(fault.getMessage());
            fault.printStackTrace();
        }


    }

    private ExportPaymentDocumentDetailsRequest getExportPaymentDocumentDetailsRequest() {

        ExportPaymentDocumentDetailsRequest request = new ExportPaymentDocumentDetailsRequest();
        request.setPaymentDocumentID("00АА001470-01-6051");

        return request;
    }

}
