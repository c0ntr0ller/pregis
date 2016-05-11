package ru.prog_matik.java.pregis;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.gosuslugi.dom.schema.integration.services.bills.ImportPaymentDocumentRequest;
import ru.gosuslugi.dom.schema.integration.services.bills.PaymentDocumentType;
import ru.prog_matik.java.pregis.other.OtherFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andryha on 08.03.2016.
 */
public class TestMain {

    private static String mes = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
            "<S:Header><ISRequestHeader xmlns=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/\" xmlns:ns10=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi-common/\" xmlns:ns11=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi/\" xmlns:ns12=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/device-metering/\" xmlns:ns13=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/organizations-registry/\" xmlns:ns14=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/licenses/\" xmlns:ns15=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/infrastructure/\" xmlns:ns16=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/fas/\" xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/inspection/\" xmlns:ns3=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/organizations-registry-common/\" xmlns:ns4=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns5=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/\" xmlns:ns6=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/services/\" xmlns:ns7=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/\" xmlns:ns8=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/payment/\" xmlns:ns9=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/bills/\"><Date>2016-03-08T23:21:56.517+06:00</Date><MessageGUID>7b824087-412b-4aa5-9b99-8afbd52bdb15</MessageGUID></ISRequestHeader></S:Header><S:Body><ns3:exportDataProviderRequest xmlns:ns3=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/organizations-registry-common/\" xmlns=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/\" xmlns:ns10=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi-common/\" xmlns:ns11=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi/\" xmlns:ns12=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/device-metering/\" xmlns:ns13=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/organizations-registry/\" xmlns:ns14=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/licenses/\" xmlns:ns15=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/infrastructure/\" xmlns:ns16=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/fas/\" xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/inspection/\" xmlns:ns4=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns5=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/\" xmlns:ns6=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/services/\" xmlns:ns7=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/\" xmlns:ns8=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/payment/\" xmlns:ns9=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/bills/\" Id=\"signed-data-container\"></ns3:exportDataProviderRequest></S:Body></S:Envelope>";

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XMLStreamException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder docBuilder = dbf.newDocumentBuilder();
        StringReader readerMessage = new StringReader(mes);
        InputSource inputSource = new InputSource(readerMessage);
        Document doc = docBuilder.parse(inputSource);
        doc.normalizeDocument();

        NamedNodeMap attributes = doc.getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getAttributes();

        for (int i = 0; i < attributes.getLength(); i++) {
            System.out.println(attributes.item(i));
        }

        remove(attributes);
        attributes = doc.getChildNodes().item(0).getChildNodes().item(1).getChildNodes().item(0).getAttributes();
        remove(attributes);

//        doc.getDomConfig().setParameter("format-pretty-print", true);
        addSpace(doc);

        String mesout = org.apache.ws.security.util.XMLUtils.PrettyDocumentToString(doc);
        System.out.println(mesout);

    }

    public static void remove(NamedNodeMap attributes) throws XMLStreamException {

        Map map = removeName(mes);
        System.out.println(attributes.getNamedItem("xmlns"));

        for (Object o : map.keySet()) {
            if (o != null) {
//                System.out.println(attributes.getNamedItem("xmlns:" + o.toString()));
                attributes.removeNamedItem("xmlns:" + o.toString());
            }
        } // for
    }

    public static Map removeName(String message) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new ByteArrayInputStream(message.getBytes()));
        Map removeNamespace = new HashMap<String, String>();
        List listPrefix = new ArrayList<String>();

        while (reader.hasNext()) {
            if (reader.isStartElement()) {
                listPrefix.add(reader.getPrefix());
                if (reader.getNamespaceCount() > 1) {
                    for (int i = 0; i < reader.getNamespaceCount(); i++) {
                        removeNamespace.put(reader.getNamespacePrefix(i), reader.getNamespaceURI(i));
                    } // for
                } // if
            } // if
            reader.next();
        } // while

//        оставим это
        listPrefix.add("SOAP-ENV");

        for (Object o : listPrefix) {
            if (removeNamespace.containsKey(o)) {
                removeNamespace.remove(o);
            } // if
        } // for
        return removeNamespace;
    }

    public static void addSpace(Document document) {

//        DeleteNamespace del = new DeleteNamespace();

        document.getFirstChild().setTextContent("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "            <S:Body><ns3:exportDataProviderRequest xmlns:ns3=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/organizations-registry-common/\" xmlns=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/\" xmlns:ns10=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi-common/\" xmlns:ns11=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi/\" xmlns:ns12=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/device-metering/\" xmlns:ns13=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/organizations-registry/\" xmlns:ns14=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/licenses/\" xmlns:ns15=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/infrastructure/\" xmlns:ns16=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/fas/\" xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/inspection/\" xmlns:ns4=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns5=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/\" xmlns:ns6=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/services/\" xmlns:ns7=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/\" xmlns:ns8=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/payment/\" xmlns:ns9=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/bills/\" Id=\"signed-data-container\"></ns3:exportDataProviderRequest></S:Body></S:Envelope>");
//        NodeList lis = document.getChildNodes();
//        document.getChildNodes().item(0).

//        System.out.println(del.getMessage(document.getChildNodes().item(0).getChildNodes().item(0).getOwnerDocument()));
//        System.out.println(document.getChildNodes().item(0).getChildNodes().item(1));

//        document.getFirstChild().setTextContent("\n");
//        Element element = document.createElement("Test");
//        document.getFirstChild().appendChild(element);
//        element.appendChild(document.createTextNode("\n\t"));
//        Text text = document.createTextNode("\n");
//        Element element = document.getDocumentElement();
//        document.getFirstChild().appendChild(text);
//        document.getFirstChild().getFirstChild().getFirstChild().getFirstChild().appendChild(document.createTextNode("\n\t\t"));
//        document.getChildNodes().item(0).getChildNodes().item(0).appendChild(document.createTextNode("\n\t"));

//        Просто проба платежного документа

        ImportPaymentDocumentRequest.PaymentDocument paymentDocument = new ImportPaymentDocumentRequest.PaymentDocument();
        paymentDocument.setAccountGuid("1000438819"); // лиц счет клиента
//        paymentDocument.setPaymentDocumentNumber(); //Номер платежного документа, по которому внесена плата, присвоенный такому документу исполнителем в целях осуществления расчетов по внесению платы
//        Банковские реквизиты
//        paymentDocument.getPaymentInformation().setRecipientINN("5404465096"); //ИНН получателя платежа
//        paymentDocument.getPaymentInformation().setRecipientKPP("540201001"); //КПП получателя платежа
//        paymentDocument.getPaymentInformation().setBankName("ОАО \"УралСиб\""); //Наименование банка получателя платежа
//        paymentDocument.getPaymentInformation().setPaymentRecipient(); //Наименование получателя
        paymentDocument.getPaymentInformation().setBankBIK("045004725");  //БИК банка получателя
        paymentDocument.getPaymentInformation().setOperatingAccountNumber("40702810232000000061");  //Номер расчетного счета
//        paymentDocument.getPaymentInformation().setCorrespondentBankAccount("123456");  //Корр. счет банка получателя

        paymentDocument.getAddressInfo().setLivingPersonsNumber((byte) 1); // Количество проживающих
        paymentDocument.getAddressInfo().setResidentialSquare(new BigDecimal(31.3)); // Жилая площадь
//        paymentDocument.getAddressInfo().setHeatedArea(new BigDecimal()); // Отапливаемая площадь
        paymentDocument.getAddressInfo().setTotalSquare(new BigDecimal(55.8)); //* Общая площадь для ЛС

        PaymentDocumentType.ChargeInfo chargeInfo = new PaymentDocumentType.ChargeInfo();
//        chargeInfo.set

        paymentDocument.getChargeInfo().add(chargeInfo);

        ImportPaymentDocumentRequest request = new ImportPaymentDocumentRequest();
        request.setMonth(05);
        request.setYear((short) 2016);
        request.setId(OtherFormat.getId());
        request.getPaymentDocument().add(paymentDocument);
    }
}
