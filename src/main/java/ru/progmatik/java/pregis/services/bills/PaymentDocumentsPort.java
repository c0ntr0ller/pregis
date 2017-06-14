package ru.progmatik.java.pregis.services.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.*;
import ru.gosuslugi.dom.schema.integration.bills.*;
import ru.gosuslugi.dom.schema.integration.bills_service_async.BillsPortsTypeAsync;
import ru.gosuslugi.dom.schema.integration.bills_service_async.BillsServiceAsync;
import ru.gosuslugi.dom.schema.integration.bills_service_async.Fault;
import ru.gosuslugi.dom.schema.integration.nsi_base.NsiRef;
import ru.progmatik.java.pregis.connectiondb.grad.bills.PaymentInformationGradDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;
import ru.progmatik.java.pregis.other.UrlLoader;
import ru.progmatik.java.pregis.services.device_metering.DeviceMeteringAsyncGetResult;

import javax.xml.ws.Holder;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Класс, импорт сведений о платежных документах.
 * 2.1.9.4	Импорт сведений о платежных документах (importPaymentDocumentData).
 * Операция позволяет импортировать в ГИС ЖКХ сведения о платежных документах. Сведения содержат в том числе ссылку на лицевой счет, расчетный счет получателя, информация по начислениям. В результате операции выполняется одно из двух действий:
 * •	создается платежный документ в статусе «Выставлен» (для выставления на оплату в кабинет физическому лицу) при указании TransportGUID и признака Expose;
 * •	отзывается выставленный ранее счет на оплату при указании созданного ранее платежного документа (PaymentDocumentGuid) и признака Withdraw;
 * Другие комбинации признаков и идентификаторов платежного документа не допускается.
 * Операция возвращает в UniqueNumber идентификатор платежного документа.
 */
public class PaymentDocumentsPort {

    private static final Logger LOGGER = Logger.getLogger(ExportPaymentDocumentData.class);

    private static final String NAME_METHOD = "importPaymentDocumentData";

    private final BillsServiceAsync service;
    private final BillsPortsTypeAsync port;
    private final AnswerProcessing answerProcessing;


    /**
     * Конструктор, добавляет параметры для соединения.
     */
    public PaymentDocumentsPort(AnswerProcessing answerProcessing) {

        this.answerProcessing = answerProcessing;

        service = UrlLoader.instance().getUrlMap().get("billsAsync") == null ? new BillsServiceAsync()
                : new BillsServiceAsync(UrlLoader.instance().getUrlMap().get("billsAsync"));
        port = service.getBillsPortAsync();
        OtherFormat.setPortSettings(service, port);
    }

    GetStateResult sendPaymentDocument(ImportPaymentDocumentRequest request) throws PreGISException, SQLException {

        return callImportPaymentDocumentData(request);
    }

    /**
     * Метод, импорт сведений о платежных документах в ГИС ЖКХ.
     */
    private GetStateResult callImportPaymentDocumentData(ImportPaymentDocumentRequest request) throws PreGISException, SQLException {

        if(answerProcessing != null) answerProcessing.sendMessageToClient("");
        if(answerProcessing != null) answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        ResultHeader resultHeader = null;
        Holder<ResultHeader> headerHolder = new Holder<>();
        ErrorMessageType resultErrorMessage = null;

        BillsAsyncGetResult billsAsyncGetResult = null;
        GetStateResult result = null;

        try {
            if(answerProcessing != null) answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            AckRequest ackRequest = port.importPaymentDocumentData(setSignIdImportPaymentDocumentRequest(request), requestHeader, headerHolder);
            if(answerProcessing != null) answerProcessing.sendMessageToClient(TextForLog.REQUEST_SENDED);

            if (ackRequest != null) {
                billsAsyncGetResult = new BillsAsyncGetResult(ackRequest, NAME_METHOD, answerProcessing, port);
                result = billsAsyncGetResult.getRequestResult();
            }

        } catch (Fault fault) {
            if(answerProcessing != null) answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            return null;
        }
        if (result != null) resultErrorMessage = result.getErrorMessage();

        if (billsAsyncGetResult != null) resultHeader = billsAsyncGetResult.getHeaderHolder().value;

        if(answerProcessing != null) answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, resultHeader, resultErrorMessage, LOGGER);

        if (resultErrorMessage != null) return null;

        return result;
    }


    private void getImportPaymentDocumentRequest() throws SQLException, PreGISException {
        //        Просто проба платежного документа

        ImportPaymentDocumentRequest.PaymentDocument paymentDocument = new ImportPaymentDocumentRequest.PaymentDocument();

        paymentDocument.setAccountGuid("6df1ada1-3c54-4a09-ad99-8f4848037701"); // лиц счет клиента 1000438819
//        paymentDocument.setPaymentDocumentNumber(); //Номер платежного документа, по которому внесена плата, присвоенный такому документу исполнителем в целях осуществления расчетов по внесению платы

//        Банковские реквизиты
        PaymentInformationGradDAO dao = new PaymentInformationGradDAO();
//        ImportPaymentDocumentRequest.PaymentInformation paymentInformation = dao.getPaymentInformationMap(ConnectionBaseGRAD.instance().getConnection());
//        ImportPaymentDocumentRequest.PaymentInformation paymentInformation = new ImportPaymentDocumentRequest.PaymentInformation();
//        paymentInformation.setTransportGUID(OtherFormat.getRandomGUID());
//        paymentInformation.setBankBIK("045004725");
//        paymentInformation.setOperatingAccountNumber("40702810232000000061");
//        paymentDocument.setPaymentInformation(new PaymentInformationType());
//        paymentDocument.getPaymentInformationMap().setRecipientINN("5404465096"); //ИНН получателя платежа
//        paymentDocument.getPaymentInformationMap().setRecipientKPP("540201001"); //КПП получателя платежа
//        paymentDocument.getPaymentInformationMap().setBankName("Филиал ОАО \"УралСиб\" в г.Новосибирск"); //Наименование банка получателя платежа
//        paymentDocument.getPaymentInformationMap().setPaymentRecipient("ООО \"ЦУЖФ\""); //Наименование получателя
//        paymentDocument.getPaymentInformationMap().setBankBIK("045004725");  //БИК банка получателя
//        paymentDocument.getPaymentInformationMap().setOperatingAccountNumber("40702810232000000061");  //Номер расчетного счета
//        paymentDocument.getPaymentInformationMap().setCorrespondentBankAccount("30101810400000000725");  //Корр. счет банка получателя

//        Адресные сведения
        paymentDocument.setAddressInfo(new PaymentDocumentType.AddressInfo());
        paymentDocument.getAddressInfo().setLivingPersonsNumber((byte) 1); // Количество проживающих
        paymentDocument.getAddressInfo().setResidentialSquare(new BigDecimal("31.3")); // Жилая площадь
        paymentDocument.getAddressInfo().setHeatedArea(new BigDecimal("55.8")); // Отапливаемая площадь
        paymentDocument.getAddressInfo().setTotalSquare(new BigDecimal("55.8")); //* Общая площадь для ЛС

//        Начисление по услуге
        PaymentDocumentType.ChargeInfo chargeInfo = new PaymentDocumentType.ChargeInfo();

        NsiRef nsiRef = new NsiRef();
        nsiRef.setCode("1.1");
        nsiRef.setGUID("62b960c1-7746-455a-a617-f03695a30156");

        chargeInfo.setMunicipalService(new PDServiceChargeType.MunicipalService());
        chargeInfo.getMunicipalService().setServiceType(nsiRef);
        chargeInfo.getMunicipalService().setRate(new BigDecimal("15.94"));

        chargeInfo.getMunicipalService().setServiceCharge(new ServiceChargeType());
        chargeInfo.getMunicipalService().getServiceCharge().setMoneyDiscount(new BigDecimal("0"));
        chargeInfo.getMunicipalService().getServiceCharge().setMoneyRecalculation(new BigDecimal("0"));

        PDServiceChargeType.MunicipalService.Consumption consumption = new PDServiceChargeType.MunicipalService.Consumption();
        PDServiceChargeType.MunicipalService.Consumption.Volume volume = new PDServiceChargeType.MunicipalService.Consumption.Volume();

        volume.setValue(new BigDecimal(25.965).setScale(2, BigDecimal.ROUND_DOWN));
        volume.setType("I"); // Тип предоставления услуги: (I)ndividualConsumption - индивидульное потребление house(O)verallNeeds - общедомовые нужды
        consumption.getVolume().add(volume);

        chargeInfo.getMunicipalService().setConsumption(consumption); // (I)ndividualConsumption house(O)verallNeeds

        paymentDocument.getChargeInfo().add(chargeInfo);

        paymentDocument.setExpose(true);
//        paymentDocument.setWithdraw(false);
        paymentDocument.setTransportGUID(OtherFormat.getRandomGUID());

//        sendPaymentDocument(paymentDocument, paymentInformation, 5, (short) 2016);
    }

    /**
     * Метод, добавляет идентификатор для дальнейшего подписания.
     *
     * @param request подготовленный запрос.
     * @return запрос с указанным идентификатором для цифровой подписи.
     */
    private ImportPaymentDocumentRequest setSignIdImportPaymentDocumentRequest(ImportPaymentDocumentRequest request) {
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        return request;
    }

}
