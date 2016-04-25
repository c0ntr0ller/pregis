package ru.prog_matik.java.pregis.signet;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс подписывает наш отправляемый запрос.
 * Который мы получаем из класса ClientMessageHandler.
 */
public class RequestSiginet {

    private Logger logger = Logger.getLogger(RequestSiginet.class);

    /**
     * Метод добавляет цифровую подпись к запросу.
     * @param sourceDocument - получает документ, который требуется подписать.
     * @return SOAPMessage - возвращает подписанный документ.
     * @throws Exception
     */
//    public void signRequest(Document sourceDocument) throws Exception {
////    public SOAPMessage signRequest(Document sourceDocument) throws Exception {
//
////        Security.addProvider(new BouncyCastleProvider());
//
////        Документ и узел подписи.
////        Ищем по указанаму параметру место для подписи.
//        String signingId = "signed-data-container";
//
////        Исходный документ.
//
////        Удаляем лишние пространства имен (namespace) в XML, которые добавляются автоматически. Выравниваем документ.
////        final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
////        dbFactory.setNamespaceAware(true);
//
////        Temp
////        DeleteNamespace del = new DeleteNamespace();
////        Map map = del.getRemoveNamespace(sourceDocument);
////
////        NamedNodeMap attributes = sourceDocument.getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getAttributes();
////        del.removeNamespace(attributes, map);
////        attributes = sourceDocument.getChildNodes().item(0).getChildNodes().item(1).getChildNodes().item(0).getAttributes();
////        del.removeNamespace(attributes, map);
////        Temp
//
////        sourceDocument = removeNamespace(sourceDocument);
////        Закончил. Моя реализация красивости.
//
//
//        final XPathFactory factory = XPathFactory.newInstance();
//        final XPath xpath = factory.newXPath();
//
////        Подписываемый узел.
//        final XPathExpression expr = xpath.compile(String.format("//*[@Id='%s']", signingId));
//        final NodeList nodes = (NodeList) expr.evaluate(sourceDocument, XPathConstants.NODESET);
//
//        if (nodes.getLength() == 0) {
//            throw new Exception("Can't find node with id: " + signingId);
//        } // if
//
//        final Node nodeToSign = nodes.item(0);
//        final String referenceURI = "#" + signingId;
//
//        Security.addProvider(new BouncyCastleProvider());
//
////        Ключ подписи и сертификат.
////        KeyStore keyStore = Configure.getKeyStore();
//
////        Сертификат для проверки.
////        X509Certificate cert = (X509Certificate) keyStore.getCertificate(Configure.getKeyStoreAlias());
////        System.out.println(cert.getPublicKey().getAlgorithm() + " " + cert.getSigAlgOID() + " " + cert.getSigAlgName());
//
////        Ключ подписи.
////        PrivateKey privateKey = (PrivateKey) keyStore.getKey(Configure.getKeyStoreAlias(), Configure.getKeyStorePassword());
////        System.out.println("PrivateKey: " + privateKey.getAlgorithm());
//
////        Ключ не из хранилища КРИПТО-ПРО
//        KeyStore p12 = KeyStore.getInstance("pkcs12", new BouncyCastleProvider());
//////
//        try (FileInputStream inP12 = new FileInputStream("data" + File.separator + "dubovik.p12.pfx")){
//            p12.load(inP12, "123456".toCharArray());
//        }
//        X509Certificate gostCert = (X509Certificate) p12.getCertificate("cp_exported");
////        System.out.println("Bouncy Castle Cert: " + gostCert.getPublicKey());
//        PrivateKey gostPrivate = (PrivateKey) p12.getKey("cp_exported", null);
//
//        KeyStore.PasswordProtection protection = new KeyStore.PasswordProtection("123456".toCharArray());
//        KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) p12.getEntry("cp_exported", protection);
//
//        //        Алгоритмы.
//
//
//        KeyingDataProvider keyingProvider = new DirectKeyingDataProvider((X509Certificate) keyEntry.getCertificate(), keyEntry.getPrivateKey());
////        final KeyingDataProvider keyingProvider = new DirectKeyingDataProvider(gostCert, gostPrivate); bouncy
////        final KeyingDataProvider keyingProvider = new DirectKeyingDataProvider(cert, privateKey);
//
//        final xades4j.production.XadesSigningProfile sigProf = new XadesBesSigningProfile(keyingProvider)
//
//                .withDigestEngineProvider(new DefaultMessageDigestProvider() {
//                    @Override
//                    public MessageDigest getEngine(String digestAlgorithmURI) throws UnsupportedAlgorithmException {
//
////                        final String digestAlgOid = "1.2.643.2.2.9";
//                        final String digestAlgOid = JCP.GOST_DIGEST_OID;
//
//                        try {
////                            return MessageDigest.getInstance("GOST3411");
////                            return MessageDigest.getInstance(digestAlgOid);
//                            return MessageDigest.getInstance("GOST3410EL", new BouncyCastleProvider());
//                        } catch (NoSuchAlgorithmException e) {
//                            throw new UnsupportedAlgorithmException(e.getMessage(), digestAlgorithmURI, e);
//                        }
//                    }
//                })
//                .withAlgorithmsProviderEx(new DefaultAlgorithmsProviderEx() {
//
//                    private String digestUrn = "http://www.w3.org/2001/04/xmldsig-more#gostr3411";
//
//                    @Override
//                    public Algorithm getSignatureAlgorithm(String keyAlgorithmName) throws UnsupportedAlgorithmException {
//
//                        final String signatureUrn = "http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411";
//
//                        return new GenericAlgorithm(signatureUrn);
//                    }
//
//                    @Override
//                    public Algorithm getCanonicalizationAlgorithmForSignature() {
//                        return new GenericAlgorithm("http://www.w3.org/TR/2001/REC-xml-c14n-20010315");
////                        return new ExclusiveCanonicalXMLWithoutComments();
//                    }
//
//                    @Override
//                    public Algorithm getCanonicalizationAlgorithmForTimeStampProperties() {
////                        return new ExclusiveCanonicalXMLWithoutComments();
//                        return new GenericAlgorithm("http://www.w3.org/TR/2001/REC-xml-c14n-20010315");
//                    }
//
//                    @Override
//                    public String getDigestAlgorithmForDataObjsReferences() {
//                        return digestUrn;
//                    }
//
//                    @Override
//                    public String getDigestAlgorithmForReferenceProperties() {
//                        return digestUrn;
//                    }
//
//                    @Override
//                    public String getDigestAlgorithmForTimeStampProperties() {
//                        return digestUrn;
//                    }
//                });
//
////        Подпись.
//        final XadesSigner signer = sigProf.newSigner();
//
//        final DataObjectDesc dataObj = new DataObjectReference(referenceURI);
//        dataObj.withTransform(new EnvelopedSignatureTransform());
//        dataObj.withTransform(new ExclusiveCanonicalXMLWithoutComments());
//
//        final SignedDataObjects dataObjects = new SignedDataObjects(dataObj);
//
//        signer.sign(dataObjects, nodeToSign, SignatureAppendingStrategies.AsFirstChild);
//
//        logger.info("XAdES-BES signature completed.");
////        System.out.println("XAdES-BES signature completed.\n");
//
//
////        String mes = org.apache.ws.security.util.XMLUtils.PrettyDocumentToString(sourceDocument);
////        mes = mes.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
////        mes = mes.replace("CN=\"Тестовый УЦ ООО \\\"КРИПТО-ПРО\\\"\", O=\"ООО \\\"КРИПТО-ПРО\\\"\", C=RU, OID.1.2.840.113549.1.9.1=info@cryptopro.ru, L=Москва, ST=77 г. Москва, STREET=\"ул. Сущёвский вал, д. 18\", OID.1.2.643.3.131.1.1=#120C303037373137313037393931, OID.1.2.643.100.1=#120D31303337373030303835343434", "1.2.643.100.1=1037700085444,1.2.643.3.131.1.1=007717107991,STREET=ул. Сущёвский вал\\, д. 18,ST=77 г. Москва,L=Москва,1.2.840.113549.1.9.1=info@cryptopro.ru,C=RU,O=ООО \\\"КРИПТО-ПРО\\\",CN=Тестовый УЦ ООО \\\"КРИПТО-ПРО\\\"");
////        mes = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:org=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/organizations-registry-common/\" xmlns:ns=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/\" xmlns:xd=\"http://www.w3.org/2000/09/xmldsig#\"><soapenv:Header><org:ISRequestHeader><ns:Date>2016-02-28T14:51:28</ns:Date><ns:MessageGUID>0e27c3a8-92d6-443d-b917-c911e9fbe5ac</ns:MessageGUID></org:ISRequestHeader></soapenv:Header><soapenv:Body><org:exportDataProviderRequest Id=\"signed-data-container\"><ds:Signature Id=\"xmldsig-a8524050-a2f3-4ea6-8585-f19ea80925f8\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" /><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\" /><ds:Reference Id=\"xmldsig-a8524050-a2f3-4ea6-8585-f19ea80925f8-ref0\" URI=\"#signed-data-container\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\" /><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\" /></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\" /><ds:DigestValue>IBiWNnJwbkTQL7WOScJlMLI1A2VhORpx399fjcEtbR0=</ds:DigestValue></ds:Reference><ds:Reference URI=\"#xmldsig-a8524050-a2f3-4ea6-8585-f19ea80925f8-signedprops\" Type=\"http://uri.etsi.org/01903#SignedProperties\"><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\" /><ds:DigestValue>IUADvILyrkFQLALibwOrqpOt97kztI2iHnqt8eXuthM=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue Id=\"xmldsig-a8524050-a2f3-4ea6-8585-f19ea80925f8-sigvalue\">ASfpjtT2e8yuCHb9Lc8hlYODGMNTqvsswm4kjbrDw7AgbcLw6zqoQWiLYyPHxVeWSGzRv9Hs8hGPmRpkKmxw8A==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>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</ds:X509Certificate></ds:X509Data></ds:KeyInfo><ds:Object><xades:QualifyingProperties Target=\"#xmldsig-a8524050-a2f3-4ea6-8585-f19ea80925f8\" xmlns:xades141=\"http://uri.etsi.org/01903/v1.4.1#\" xmlns:xades=\"http://uri.etsi.org/01903/v1.3.2#\"><xades:SignedProperties Id=\"xmldsig-a8524050-a2f3-4ea6-8585-f19ea80925f8-signedprops\"><xades:SignedSignatureProperties><xades:SigningTime>2016-02-28T14:40:32.612+06:00</xades:SigningTime><xades:SigningCertificate><xades:Cert><xades:CertDigest><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\" /><ds:DigestValue>3vnkgVJSPQuw65f6VRwO8R+jxZ9AjfzRJw5CwfxB5pA=</ds:DigestValue></xades:CertDigest><xades:IssuerSerial><ds:X509IssuerName>1.2.643.100.1=1037700085444,1.2.643.3.131.1.1=007717107991,STREET=ул. Сущёвский вал\\, д. 18,ST=77 г. Москва,L=Москва,1.2.840.113549.1.9.1=info@cryptopro.ru,C=RU,O=ООО \\\"КРИПТО-ПРО\\\",CN=Тестовый УЦ ООО \\\"КРИПТО-ПРО\\\"</ds:X509IssuerName><ds:X509SerialNumber>541412163180419640118737</ds:X509SerialNumber></xades:IssuerSerial></xades:Cert></xades:SigningCertificate></xades:SignedSignatureProperties></xades:SignedProperties></xades:QualifyingProperties></ds:Object></ds:Signature><org:IsActual>true</org:IsActual></org:exportDataProviderRequest></soapenv:Body></soapenv:Envelope>";
////        Вывод подписанного сообщения в консоль.
////        System.out.println(mes);
//
////        Проверка подписи.
////        try {
////            VerifySignet.verify(sourceDocument);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//
////        return toMessage(mes);
//    } // signRequest

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
    public SOAPMessage removeNamespace(Document document) throws XMLStreamException, ParserConfigurationException, IOException, SAXException, SOAPException {
//    private Document removeNamespace(Document document) throws XMLStreamException, ParserConfigurationException, IOException, SAXException {

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

//        оставим это
        listPrefix.add("SOAP-ENV");

        for (Object o : listPrefix) {
            if (removeNamespace.containsKey(o)) {
                removeNamespace.remove(o);
            } // if
        } // for

        String prefix = ":";

//        Если удаляем пространства имен без префикса
//        String prefix;

        for (Object s : removeNamespace.keySet()) {

            if (s != null)
                prefix = ":" + s.toString();
//        Если удаляем пространства имен без префикса
//            else
//                prefix = "";

            message = message.replaceAll("xmlns" + prefix + "=\"" + removeNamespace.get(s) + "\"", "");
        } // for

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder docBuilder = dbf.newDocumentBuilder();
        StringReader readerMessage = new StringReader(message);
        InputSource inputSource = new InputSource(readerMessage);
        Document doc = docBuilder.parse(inputSource);
        doc.normalizeDocument();

//        Проверка канонизации не помогло.
//        byte[] canMessage;
//
//        try {
//            Canonicalizer canonicalizer = Canonicalizer.getInstance("http://www.w3.org/TR/2001/REC-xml-c14n-20010315");
//            canMessage = canonicalizer.canonicalizeSubtree(doc);
//            doc = docBuilder.parse(new ByteArrayInputStream(canMessage));
//        } catch (InvalidCanonicalizerException | CanonicalizationException e) {
//            e.printStackTrace();
//        }


//        doc

//        Test MODE
//        try (FileOutputStream outputStream = new FileOutputStream("temp" + File.separator + "dump.xml")) {
//            outputStream.write(message.getBytes());  // дамп SOAP
//            outputStream.flush();
//        }
//        byte[] finalPaymentDoc = Array.readFile("temp" + File.separator + "dump.xml");  // дамп SOAP

        return toMessage(message);
//        return doc;
//        return finalPaymentDoc;
    } // removeNamespace

}
