package ru.prog_matik.java.pregis.signet;

import org.apache.log4j.Logger;
import org.apache.xml.security.utils.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.CryptoPro.JCP.KeyStore.HDImage.HDImageStore;
import xades4j.UnsupportedAlgorithmException;
import xades4j.algorithms.Algorithm;
import xades4j.algorithms.EnvelopedSignatureTransform;
import xades4j.algorithms.ExclusiveCanonicalXMLWithoutComments;
import xades4j.algorithms.GenericAlgorithm;
import xades4j.production.*;
import xades4j.properties.DataObjectDesc;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.impl.DefaultAlgorithmsProviderEx;
import xades4j.providers.impl.DefaultMessageDigestProvider;
import xades4j.providers.impl.DirectKeyingDataProvider;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.*;

public class RequestSiginet {

    private Logger logger = Logger.getLogger(RequestSiginet.class);

    /**
     * Метод добавляет цифровую подпись к запросу.
     * @param sourceDocument - получает документ, который требуется подписать.
     * @return SOAPMessage - возвращает подписанный документ.
     * @throws Exception
     */
    public SOAPMessage signRequest(Document sourceDocument) throws Exception {



//        Документ и узел подписи.
//        Ищем по указанаму параметру место для подписи.
        String signingId = "signed-data-container";

//        Исходный документ.

//        Удаляем лишние пространства имен (namespace) в XML, которые добавляются автоматически. Выравниваем документ.
        sourceDocument = removeNamespace(sourceDocument);
//        Закончил. Моя реализация красивости.


        final XPathFactory factory = XPathFactory.newInstance();
        final XPath xpath = factory.newXPath();

//        Подписываемый узел.
        final XPathExpression expr = xpath.compile(String.format("//*[@Id='%s']", signingId));
        final NodeList nodes = (NodeList) expr.evaluate(sourceDocument, XPathConstants.NODESET);

        if (nodes.getLength() == 0) {
            throw new Exception("Can't find node with id: " + signingId);
        } // if

        final Node nodeToSign = nodes.item(0);
        final String referenceURI = "#" + signingId;

//        Ключ подписи и сертификат.

        KeyStore keyStore = Configure.getKeyStore();

//        Сертификат для проверки.
        X509Certificate cert = (X509Certificate) keyStore.getCertificate(Configure.getKeyStoreAlias());

//        Ключ подписи.
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(Configure.getKeyStoreAlias(), Configure.getKeyStorePassword());

//        Алгоритмы.

        final KeyingDataProvider keyingProvider = new DirectKeyingDataProvider(cert, privateKey);
        System.out.println(keyingProvider.getSigningKey(cert).getAlgorithm());

        final xades4j.production.XadesSigningProfile sigProf = new XadesBesSigningProfile(keyingProvider)

                .withDigestEngineProvider(new DefaultMessageDigestProvider() {
                    @Override
                    public MessageDigest getEngine(String digestAlgorithmURI) throws UnsupportedAlgorithmException {

                        final String digestAlgOid = "1.2.643.2.2.9";

                        try {
                            return MessageDigest.getInstance(digestAlgOid);
                        } catch (NoSuchAlgorithmException e) {
                            throw new UnsupportedAlgorithmException(e.getMessage(), digestAlgorithmURI, e);
                        }
                    }
                })
                .withAlgorithmsProviderEx(new DefaultAlgorithmsProviderEx() {

                    private String digestUrn = "http://www.w3.org/2001/04/xmldsig-more#gostr3411";

                    @Override
                    public Algorithm getSignatureAlgorithm(String keyAlgorithmName) throws UnsupportedAlgorithmException {

                        final String signatureUrn = "http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411";

                        return new GenericAlgorithm(signatureUrn);
                    }

                    @Override
                    public Algorithm getCanonicalizationAlgorithmForSignature() {
                        return new ExclusiveCanonicalXMLWithoutComments();
                    }

                    @Override
                    public Algorithm getCanonicalizationAlgorithmForTimeStampProperties() {
                        return new ExclusiveCanonicalXMLWithoutComments();
                    }

                    @Override
                    public String getDigestAlgorithmForDataObjsReferences() {
                        return digestUrn;
                    }

                    @Override
                    public String getDigestAlgorithmForReferenceProperties() {
                        return digestUrn;
                    }

                    @Override
                    public String getDigestAlgorithmForTimeStampProperties() {
                        return digestUrn;
                    }
                });

//        Подпись.
        final XadesSigner signer = sigProf.newSigner();

        final DataObjectDesc dataObj = new DataObjectReference(referenceURI);
        dataObj.withTransform(new EnvelopedSignatureTransform());
         dataObj.withTransform(new ExclusiveCanonicalXMLWithoutComments());

        final SignedDataObjects dataObjects = new SignedDataObjects(dataObj);

        signer.sign(dataObjects, nodeToSign, SignatureAppendingStrategies.AsFirstChild);
        System.out.println("XAdES-BES signature completed.\n");

        String mes = org.apache.ws.security.util.XMLUtils.PrettyDocumentToString(sourceDocument);
        mes = mes.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
        System.out.println(mes);

//        Проверка подписи.
//        try {
//            VerifySignet.verify(sourceDocument);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        return toMessage(mes);
    } // signRequest

    /**
     * Метод преобразует String в SOAPMessage.
     * @param message - сообщение в формате String.
     * @return SOAPMessage - готовое сообщение для отправки в формате SOAPMessage.
     * @throws IOException
     * @throws SOAPException
     */
    private SOAPMessage toMessage(String message) throws IOException, SOAPException {

        MessageFactory messageFactory = MessageFactory.newInstance();
        InputStream inputStream = new ByteArrayInputStream(message.getBytes());

        return messageFactory.createMessage(null, inputStream);
    } // toMessage

    /**
     * Метод удаляет лишние пространства имен в файле.
     * @param document - документ, содержащий не нужные пространства имен.
     * @return Document - возвращает исправленный документ.
     * @throws XMLStreamException
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    private Document removeNamespace(Document document) throws XMLStreamException, ParserConfigurationException, IOException, SAXException {

        DOMImplementationLS ls = (DOMImplementationLS) document.getImplementation().getFeature("LS", "3.0");
        LSSerializer serializer = ls.createLSSerializer();
        serializer.getDomConfig().setParameter("format-pretty-print", true);

        String message = serializer.writeToString(document);
        message = message.replace("<?xml version=\"1.0\" encoding=\"UTF-16\"?>", "");

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

        for (Object o : listPrefix) {
            if (removeNamespace.containsKey(o)) {
                removeNamespace.remove(o);
            } // if
        } // for

        String prefix;

        for (Object s : removeNamespace.keySet()) {

            if (s != null)
                prefix = ":" + s.toString();
            else
                prefix = "";

            message = message.replaceAll("xmlns" + prefix + "=\"" + removeNamespace.get(s) + "\"", "");
        } // for

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder docBuilder = dbf.newDocumentBuilder();
        StringReader readerMessage = new StringReader(message);
        InputSource inputSource = new InputSource(readerMessage);
        Document doc = docBuilder.parse(inputSource);

        return doc;
    } // removeNamespace
}
