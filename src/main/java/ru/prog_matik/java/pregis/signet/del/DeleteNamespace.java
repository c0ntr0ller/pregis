package ru.prog_matik.java.pregis.signet.del;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс временый, для удаления пространств имен.
 * Created by andryha on 09.03.2016.
 */
public class DeleteNamespace {

    private Map getRemoveNamespace(Document doc) throws XMLStreamException {

        String message = org.apache.ws.security.util.XMLUtils.PrettyDocumentToString(doc);
        message = message.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");

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

    public void removeNamespace(Document document) throws XMLStreamException {

        Map mapRemoveNamespace = getRemoveNamespace(document);

        NamedNodeMap attributes = document.getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getAttributes();
        removeAttribute(attributes, mapRemoveNamespace);

        attributes = document.getChildNodes().item(0).getChildNodes().item(1).getChildNodes().item(0).getAttributes();
        removeAttribute(attributes, mapRemoveNamespace);

    }

    private void removeAttribute(NamedNodeMap attributes, Map mapRemoveNamespace) {

        for (Object o : mapRemoveNamespace.keySet()) {
            if (o != null) {
                attributes.removeNamedItem("xmlns:" + o.toString());
            }
        } // for
    }

    public String getMessage(Document document) {

        DOMImplementationLS ls = (DOMImplementationLS) document.getImplementation().getFeature("LS", "3.0");
        LSSerializer serializer = ls.createLSSerializer();
        serializer.getDomConfig().setParameter("format-pretty-print", true);

        String message = serializer.writeToString(document);
        message = message.replace("<?xml version=\"1.0\" encoding=\"UTF-16\"?>", "");
        return message;
    }

}