package ru.progmatik.java.pregis.services.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.ImportResult;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.bills.ImportPaymentDocumentRequest;
import ru.gosuslugi.dom.schema.integration.bills.PDServiceChargeType;
import ru.gosuslugi.dom.schema.integration.bills.PaymentDocumentType;
import ru.gosuslugi.dom.schema.integration.bills.ServiceChargeType;
import ru.gosuslugi.dom.schema.integration.bills_service.BillsPortsType;
import ru.gosuslugi.dom.schema.integration.bills_service.BillsService;
import ru.gosuslugi.dom.schema.integration.bills_service.Fault;
import ru.gosuslugi.dom.schema.integration.nsi_base.NsiRef;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.bills.PaymentInformationGradDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.message.SaveToBaseMessages;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;

import javax.xml.ws.Holder;
import java.math.BigDecimal;
import java.sql.SQLException;

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
    private final AnswerProcessing answerProcessing;


    /**
     * Конструктор, добавляет параметры для соединения.
     */
    public ImportPaymentDocumentData(AnswerProcessing answerProcessing) {
        OtherFormat.setPortSettings(service, port);
        this.answerProcessing = answerProcessing;
    }

    public ImportResult sendPaymentDocument(ImportPaymentDocumentRequest.PaymentDocument paymentDocument,
                                            ImportPaymentDocumentRequest.PaymentInformation paymentInformation,
                                            int month, short year) throws SQLException, PreGISException {

        return callImportPaymentDocumentData(getImportPaymentDocumentRequest(paymentDocument, paymentInformation, month, year));
    }

    /**
     * Метод, импорт сведений о платежных документах в ГИС ЖКХ.
     */
    private ImportResult callImportPaymentDocumentData(ImportPaymentDocumentRequest request) throws SQLException, PreGISException {

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
//        Создание загаловков сообщений (запроса и ответа)
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        Holder<ResultHeader> headerHolder = new Holder<>();
//        Создание объекта для сохранения лога сообщений в БД.
        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        ImportResult result;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            result = port.importPaymentDocumentData(request, requestHeader, headerHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            return null;
        }

        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, headerHolder.value, result.getErrorMessage(), LOGGER);
        if (result.getErrorMessage() != null) return null;
        return result;
    }

    private ImportPaymentDocumentRequest getImportPaymentDocumentRequest(
            ImportPaymentDocumentRequest.PaymentDocument paymentDocument,
            ImportPaymentDocumentRequest.PaymentInformation paymentInformation, int month, short year) throws SQLException, PreGISException {

        ImportPaymentDocumentRequest request = new ImportPaymentDocumentRequest();
        request.setMonth(month);
        request.setYear(year);
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());
        request.getPaymentDocument().add(paymentDocument);

        if (request.getPaymentInformation().size() == 0) {
            request.getPaymentInformation().add(paymentInformation); // Банковские реквизиты
        } else if (!paymentInformation.getOperatingAccountNumber().equals(
                request.getPaymentInformation().get(0).getOperatingAccountNumber())) {
            request.getPaymentInformation().add(paymentInformation); // Банковские реквизиты
        }
        return request;
    }

    private void getImportPaymentDocumentRequest() throws SQLException, PreGISException {
        //        Просто проба платежного документа

        ImportPaymentDocumentRequest.PaymentDocument paymentDocument = new ImportPaymentDocumentRequest.PaymentDocument();

        paymentDocument.setAccountGuid("6df1ada1-3c54-4a09-ad99-8f4848037701"); // лиц счет клиента 1000438819
//        paymentDocument.setPaymentDocumentNumber(); //Номер платежного документа, по которому внесена плата, присвоенный такому документу исполнителем в целях осуществления расчетов по внесению платы

//        Банковские реквизиты
        PaymentInformationGradDAO dao = new PaymentInformationGradDAO();
        ImportPaymentDocumentRequest.PaymentInformation paymentInformation = dao.getPaymentInformation(ConnectionBaseGRAD.instance().getConnection());
//        ImportPaymentDocumentRequest.PaymentInformation paymentInformation = new ImportPaymentDocumentRequest.PaymentInformation();
//        paymentInformation.setTransportGUID(OtherFormat.getRandomGUID());
//        paymentInformation.setBankBIK("045004725");
//        paymentInformation.setOperatingAccountNumber("40702810232000000061");
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

        volume.setValue(new BigDecimal(25.965).setScale(2, BigDecimal.ROUND_DOWN));
        volume.setType("I"); // Тип предоставления услуги: (I)ndividualConsumption - индивидульное потребление house(O)verallNeeds - общедомовые нужды
        consumption.getVolume().add(volume);

        chargeInfo.getMunicipalService().setConsumption(consumption); // (I)ndividualConsumption house(O)verallNeeds

        paymentDocument.getChargeInfo().add(chargeInfo);

        paymentDocument.setExpose(true);
//        paymentDocument.setWithdraw(false);
        paymentDocument.setTransportGUID(OtherFormat.getRandomGUID());

        sendPaymentDocument(paymentDocument, paymentInformation, 5, (short) 2016);
    }
}
