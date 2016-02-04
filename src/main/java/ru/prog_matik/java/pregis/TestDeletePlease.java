package ru.prog_matik.java.pregis;

import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import ru.prog_matik.java.pregis.signet.RequestSiginet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.dom.DOMSource;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andryha on 03.02.2016.
 */
public class TestDeletePlease {
    public static void main(String[] args) throws Exception {

//        String message = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
//                "<S:Header>" +
//                "<ns2:ISRequestHeader xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/\" xmlns=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/\" xmlns:ns3=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns4=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/nsi/\">" +
//                "<Date>2016-02-03T11:30:10.754+06:00</Date>" +
//                "<MessageGUID>8d95ec1c-c917-4483-8855-81ff4bb637cc</MessageGUID>" +
//                "</ns2:ISRequestHeader>" +
//                "</S:Header>" +
//                "<S:Body>" +
//                "<ns2:exportOrgRegistryRequest xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/\" xmlns=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/\" xmlns:ns3=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns4=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/nsi/\" Id=\"signed-data-container\">" +
//                "<ns2:SearchCriteria>" +
//                "<OGRN>1125476111903</OGRN>" +
//                "</ns2:SearchCriteria>" +
//                "</ns2:exportOrgRegistryRequest>" +
//                "</S:Body>" +
//                "</S:Envelope>";

        String message = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Header><ns3:ISRequestHeader xmlns:ns3=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/\" xmlns=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/\" xmlns:ns4=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/nsi/\"><ns2:Date>2016-02-04T14:00:57.005+06:00</ns2:Date><ns2:MessageGUID>615280d7-fd53-4c84-850e-64d57cdc2b36</ns2:MessageGUID></ns3:ISRequestHeader></S:Header><S:Body><ns3:exportOrgRegistryRequest xmlns:ns3=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/\" xmlns=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/\" xmlns:ns4=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/nsi/\" Id=\"signed-data-container\"><ns3:SearchCriteria><ns2:OGRN>1125476111903</ns2:OGRN></ns3:SearchCriteria></ns3:exportOrgRegistryRequest></S:Body></S:Envelope>";

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder docBuilder = dbf.newDocumentBuilder();
        StringReader readerMessage = new StringReader(message);
        InputSource inputSource = new InputSource(readerMessage);
        Document doc = docBuilder.parse(inputSource);
//        dbf.setNamespaceAware(true);

//        doc.getDomConfig().setParameter("cdata-sections", false);
//        doc.getDomConfig().setParameter("entities", false);
        doc.getDocumentElement().normalize();
        doc.normalizeDocument();
        doc.normalize();
//        System.out.println(doc.getDocumentURI());
//        System.out.println(doc.getNamespaceURI());

//        NodeList list = doc.getParentNode();
        DOMSource domSource = new DOMSource(doc);

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new ByteArrayInputStream(message.getBytes()));
        Map<String, String> useNamespace = new HashMap<>();
        Map<String, String> removeNamespace = new HashMap<>();
        List<String> listName = new ArrayList<>();
        List listPrefix = new ArrayList<String>();

        while (reader.hasNext()) {
//            listPrefix.add(reader.getNamespaceContext());
            if (reader.isStartElement()) {
//                System.out.println(reader.getNamespaceContext().getPrefix("http://schemas.xmlsoap.org/soap/envelope/"));
                useNamespace.put(reader.getPrefix(), reader.getNamespaceURI());
                listPrefix.add(reader.getPrefix());
                if (reader.getNamespaceCount() > 1) {
                    for (int i = 0; i < reader.getNamespaceCount(); i++) {
                        System.out.print("Namespace: " + reader.getNamespaceURI(i));
                        System.out.println("Namespace Prefix: " + reader.getNamespacePrefix(i));
                        removeNamespace.put(reader.getNamespacePrefix(i), reader.getNamespaceURI(i));
                    }
                }
                System.out.print(reader.getLocalName() + " ");
                System.out.print(reader.getPrefix());
                System.out.println(" URI: " + reader.getNamespaceURI());

//                reader.getNamespaceCount();
//                reader.getNamespaceURI(1)
            }
            reader.next();
        }

            System.out.println(removeNamespace.keySet());
            System.out.println(removeNamespace.values());

        for (Object o : listPrefix) {
            if (removeNamespace.containsKey(o)) {
                removeNamespace.remove(o);
            }
        }

        System.out.println(removeNamespace.keySet());
        System.out.println(removeNamespace.values());




//
//        String tempKey;
//
//        for (int i = 0; i < useNamespace.keySet().size(); i++) {
//                System.out.println(i);
//                tempKey = useNamespace.keySet().iterator().next();
//                System.out.println(tempKey);
//                if (removeNamespace.containsKey(tempKey)) {
//                    removeNamespace.remove(tempKey);
//                }
//        }



//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        XMLOutputFactory outFacktory = XMLOutputFactory.newInstance();
//
//        XMLStreamWriter writer = outFacktory.createXMLStreamWriter(out);
        Node node = doc.getDocumentElement();
        System.out.println(" NODE: " + node.getNamespaceURI() + " Prefix " + node.getPrefix() + " " + node.getLocalName());
        node.normalize();

        RequestSiginet requestSiginet = new RequestSiginet();
        SOAPMessage soapMessage = requestSiginet.toMessage(message);
        System.out.println(soapMessage.getSOAPPart().getEnvelope().getNamespacePrefixes());

        soapMessage.getSOAPPart().getEnvelope().removeNamespaceDeclaration("SOAP-ENV");
        System.out.println(soapMessage.getSOAPPart().getEnvelope().getHeader().);
//        .removeNamespaceDeclaration("ns4");


        soapMessage.saveChanges();
        soapMessage.writeTo(System.out);

        DOMImplementationLS ls = (DOMImplementationLS) doc.getImplementation().getFeature("LS", "3.0");
        LSSerializer serializer = ls.createLSSerializer();

//        serializer.getDomConfig().setParameter("cdata-sections", false);
//        serializer.getDomConfig().setParameter("entities", false);
        serializer.getDomConfig().setParameter("entities", true);
//        serializer.getDomConfig().setParameter("canonical-form", true);


        DOMStringList list = serializer.getDomConfig().getParameterNames();
        for (int i = 0; i < list.getLength(); i++) {

            try {
                System.out.println(list.item(i) + "\t" + serializer.getDomConfig().getParameter(list.item(i)));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

        }

        System.out.println(doc.getDomConfig().getParameter("canonical-form"));
        serializer.getDomConfig().setParameter("format-pretty-print", true);
//        serializer.getDomConfig().setParameter("canonical-form",Boolean.TRUE);


//        LSOutput out = ls.createLSOutput();
//        out.setEncoding("UTF-8");

        String newMessage = serializer.writeToString(doc);

        newMessage = newMessage.replace("<?xml version=\"1.0\" encoding=\"UTF-16\"?>", "");

        System.out.println(newMessage);

        doc = docBuilder.parse(new InputSource(new StringReader(newMessage)));

        System.out.println(org.apache.ws.security.util.XMLUtils.PrettyDocumentToString(doc));
    }
}
