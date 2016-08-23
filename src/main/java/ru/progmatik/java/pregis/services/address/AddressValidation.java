//package ru.progmatik.java.pregis.services.address;
//
//import org.apache.log4j.Logger;
//import ru.gosuslugi.dom.schema.integration.base.AckRequest;
//import ru.gosuslugi.dom.schema.integration.base.HeaderType;
//import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
//import ru.gosuslugi.dom.schema.integration.services.address_validation.ExportAddressAdditionRequest;
//import ru.gosuslugi.dom.schema.integration.services.address_validation_service_async.AddressValidationPortsTypeAsync;
//import ru.gosuslugi.dom.schema.integration.services.address_validation_service_async.AddressValidationServiceAsync;
//import ru.gosuslugi.dom.schema.integration.services.address_validation_service_async.Fault;
//import ru.progmatik.java.pregis.connectiondb.localdb.message.SaveToBaseMessages;
//import ru.progmatik.java.pregis.other.AnswerProcessing;
//import ru.progmatik.java.pregis.other.OtherFormat;
//import ru.progmatik.java.pregis.other.TextForLog;
//
//import javax.xml.ws.Holder;
//
///**
// * Created by andryha on 22.08.2016.
// */
//
//public class AddressValidation {
//
//    private static final Logger LOGGER = Logger.getLogger(AddressValidation.class);
//    private static final String NAME_METHOD = "exportAddressAddition";
//
//    private final AddressValidationServiceAsync service = new AddressValidationServiceAsync();
//    private final AddressValidationPortsTypeAsync port = service.getPaymentPortAsync();
//    private final AnswerProcessing answerProcessing;
//
//    public AddressValidation(AnswerProcessing answerProcessing) {
//
//        OtherFormat.setPortSettings(service, port);
//        this.answerProcessing = answerProcessing;
//    }
//
//    public void callAddressValidation() {
//
//        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
//
////        Создание загаловков сообщений (запроса и ответа)
//        HeaderType requestHeader = OtherFormat.getISRequestHeader();
//        Holder<ResultHeader> resultHolder = new Holder<>();
////        Создание объекта для сохранения лога сообщений в БД.
//        SaveToBaseMessages saveToBase = new SaveToBaseMessages();
//
//        AckRequest result;
//
//        try {
//            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
//            result = port.exportAddressAddition(getExportAddressAdditionRequest(), requestHeader, resultHolder);
//            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
//            System.out.println(result.getAck().getMessageGUID());
//
//        } catch (Fault fault) {
//            answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
//            return;
//        }
//
//        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, resultHolder.value, null, LOGGER);
//    }
//
//    private ru.gosuslugi.dom.schema.integration.services.address_validation.ExportAddressAdditionRequest getExportAddressAdditionRequest() {
//
//        ru.gosuslugi.dom.schema.integration.services.address_validation.ExportAddressAdditionRequest exportAddressAdditionRequest = new ExportAddressAdditionRequest();
//        exportAddressAdditionRequest.setId(OtherFormat.getId());
////        ExportAddressAdditionRequest.Filter filter = new ExportAddressAdditionRequest.Filter();
////        filter.
////        exportAddressAdditionRequest.setFilter(filter);
//
//
//        return exportAddressAdditionRequest;
//    }
//}
