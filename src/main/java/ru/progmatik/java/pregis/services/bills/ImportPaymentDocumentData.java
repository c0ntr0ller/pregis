package ru.progmatik.java.pregis.services.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.*;
import ru.gosuslugi.dom.schema.integration.services.bills.ImportPaymentDocumentRequest;
import ru.gosuslugi.dom.schema.integration.services.bills.PDServiceChargeType;
import ru.gosuslugi.dom.schema.integration.services.bills.PaymentDocumentType;
import ru.gosuslugi.dom.schema.integration.services.bills.ServiceChargeType;
import ru.gosuslugi.dom.schema.integration.services.bills_service.BillsPortsType;
import ru.gosuslugi.dom.schema.integration.services.bills_service.BillsService;
import ru.gosuslugi.dom.schema.integration.services.bills_service.Fault;
import ru.progmatik.java.pregis.connectiondb.SaveToBaseMessages;
import ru.progmatik.java.pregis.other.OtherFormat;

import javax.xml.ws.Holder;
import java.math.BigDecimal;

/**
 * Класс, импорт сведений о платежных документах.
 * 2.1.9.4	Импорт сведений о платежных документах (importPaymentDocumentData).
 * Операция позволяет импортировать в ГИС ЖКХ сведения о платежных документах. Сведения содержат в том числе ссылку на лицевой счет, расчетный счет получателя, информация по начислениям. В результате операции выполняется одно из двух действий:
 * •	создается платежный документ в статусе «Выставлен» (для выставления на оплату в кабинет физическому лицу) при указании TransportGUID и признака Expose;
 * •	отзывается выставленный ранее счет на оплату при указании созданного ранее платежного документа (PaymentDocumentGuid) и признака Withdraw;
 * Другие комбинации признаков и идентификаторов платежного документа не допускается.
 * Операция возвращает в UniqueNumber идентификатор платежного документа.
 */
public class ImportPaymentDocumentData {

    private static final Logger LOGGER = Logger.getLogger(ExportPaymentDocumentData.class);

    private static final String NAME_METHOD = "importPaymentDocumentData";

    private final BillsService service = new BillsService();
    private final BillsPortsType port = service.getBillsPort();

    /**
     * Конструктор, добавляет параметры для соединения.
     */
    public ImportPaymentDocumentData() {
        OtherFormat.setPortSettings(service, port);
    }

    /**
     * Метод, импорт сведений о платежных документах в ГИС ЖКХ.
     */
    public void callImportPaymentDocumentData() {

//        Создание загаловков сообщений (запроса и ответа)
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        Holder<ResultHeader> headerHolder = new Holder<>();
//        Создание объекта для сохранения лога сообщений в БД.
        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        ImportResult result;

        try {
            result = port.importPaymentDocumentData(getImportPaymentDocumentRequest(), requestHeader, headerHolder);
        } catch (Fault fault) {
            saveToBase.setRequestError(requestHeader, NAME_METHOD, fault);
            LOGGER.error(fault.getMessage());
            fault.printStackTrace();
            return;
        }

        saveToBase.setRequest(requestHeader, NAME_METHOD);

        saveToBase.setResult(headerHolder.value, NAME_METHOD, result.getErrorMessage());

        LOGGER.info("ImportPaymentDocumentData - Successful.");

    }

    private ImportPaymentDocumentRequest getImportPaymentDocumentRequest() {
        //        Просто проба платежного документа

        ImportPaymentDocumentRequest.PaymentDocument paymentDocument = new ImportPaymentDocumentRequest.PaymentDocument();

        paymentDocument.setAccountGuid("6df1ada1-3c54-4a09-ad99-8f4848037701"); // лиц счет клиента 1000438819
//        paymentDocument.setPaymentDocumentNumber(); //Номер платежного документа, по которому внесена плата, присвоенный такому документу исполнителем в целях осуществления расчетов по внесению платы

//        Банковские реквизиты
//        paymentDocument.setPaymentInformation(new PaymentInformationType());
//        paymentDocument.getPaymentInformation().setRecipientINN("5404465096"); //ИНН получателя платежа
//        paymentDocument.getPaymentInformation().setRecipientKPP("540201001"); //КПП получателя платежа
//        paymentDocument.getPaymentInformation().setBankName("Филиал ОАО \"УралСиб\" в г.Новосибирск"); //Наименование банка получателя платежа
//        paymentDocument.getPaymentInformation().setPaymentRecipient("ООО \"ЦУЖФ\""); //Наименование получателя
//        paymentDocument.getPaymentInformation().setBankBIK("045004725");  //БИК банка получателя
//        paymentDocument.getPaymentInformation().setOperatingAccountNumber("40702810232000000061");  //Номер расчетного счета
//        paymentDocument.getPaymentInformation().setCorrespondentBankAccount("30101810400000000725");  //Корр. счет банка получателя

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

        volume.setValue(new BigDecimal(25.965).setScale(2, BigDecimal.ROUND_HALF_UP));
        volume.setType("I");
        consumption.getVolume().add(volume);

        chargeInfo.getMunicipalService().setConsumption(consumption); // (I)ndividualConsumption house(O)verallNeeds

        paymentDocument.getChargeInfo().add(chargeInfo);

        paymentDocument.setExpose(true);
//        paymentDocument.setWithdraw(false);
        paymentDocument.setTransportGUID(OtherFormat.getRandomGUID());

        ImportPaymentDocumentRequest request = new ImportPaymentDocumentRequest();
        request.setMonth(05);
        request.setYear((short) 2016);
        request.setId(OtherFormat.getId());
        request.getPaymentDocument().add(paymentDocument);

        return request;
    }
}
