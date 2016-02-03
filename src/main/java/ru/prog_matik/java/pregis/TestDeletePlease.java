package ru.prog_matik.java.pregis;

import org.w3c.dom.DOMStringList;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

/**
 * Created by andryha on 03.02.2016.
 */
public class TestDeletePlease {
    public static void main(String[] args) throws Exception {

        String message = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<S:Header>" +
                "<ns2:ISRequestHeader xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/\" xmlns=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/\" xmlns:ns3=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns4=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/nsi/\">" +
                "<Date>2016-02-03T11:30:10.754+06:00</Date>" +
                "<MessageGUID>8d95ec1c-c917-4483-8855-81ff4bb637cc</MessageGUID>" +
                "</ns2:ISRequestHeader>" +
                "</S:Header>" +
                "<S:Body>" +
                "<ns2:exportOrgRegistryRequest xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/\" xmlns=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/\" xmlns:ns3=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns4=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/nsi/\" Id=\"signed-data-container\">" +
                "<ns2:SearchCriteria>" +
                "<OGRN>1125476111903</OGRN>" +
                "</ns2:SearchCriteria>" +
                "</ns2:exportOrgRegistryRequest>" +
                "</S:Body>" +
                "</S:Envelope>";

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder docBuilder = dbf.newDocumentBuilder();
        Document doc = docBuilder.parse(new InputSource(new StringReader(message)));

//        doc.getDomConfig().setParameter("cdata-sections", false);
//        doc.getDomConfig().setParameter("entities", false);

//        doc.normalizeDocument();
//        doc.normalize();

        DOMImplementationLS ls = (DOMImplementationLS) doc.getImplementation().getFeature("LS", "3.0");
        LSSerializer serializer = ls.createLSSerializer();

        serializer.getDomConfig().setParameter("cdata-sections", false);
        serializer.getDomConfig().setParameter("entities", false);
//        serializer.getDomConfig().setParameter("entities", false);
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
