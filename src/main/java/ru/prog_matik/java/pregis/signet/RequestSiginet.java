package ru.prog_matik.java.pregis.signet;

import org.apache.log4j.Logger;
import org.apache.xml.security.utils.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
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
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
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
import java.util.ResourceBundle;

public class RequestSiginet {

    private Logger logger = Logger.getLogger(RequestSiginet.class);

    //    public Document signRequest(byte[] sourceXmlBin) throws Exception {
//    public ByteArrayOutputStream signRequest(ByteArrayOutputStream sourceXmlBin) throws Exception {
    public SOAPMessage signRequest(Document sourceDocument) throws Exception {

//        JCPXMLDSigInit.init();
        if (!ru.CryptoPro.JCPxml.XmlInit.isInitialized())
            ru.CryptoPro.JCPxml.XmlInit.init();

        String hdiPath = System.getProperty("user.dir") + "\\data";
        String signingId = "signed-data-container";

        char[] password = {'1', '2', '3', '4', '5', '6'};
//        InputStream keysStore = new FileInputStream("C:\\andryha\\key\\PreGIS\\client");
        String alias = "dubovik";

        KeyStore ks = KeyStore.getInstance("HDImageStore", "JCP");

//        Для изменения пути к носителю необходимо дать доступ
//        в реестре windows в ветке
//        "HKEY_LOCAL_MACHINE\SOFTWARE\JavaSoft\Prefs\ru\/Crypto/Pro\/J/C/P\/Key/Store\/H/D/Image"
        System.out.println(Constants._TAG_SIGNATUREVALUE + "application: " + ResourceBundle.getBundle("application").getString("config.cryptoPro.keyStore.path"));
        if (hdiPath.equals(HDImageStore.getDir())) {
            System.out.println(HDImageStore.getDir());
        } else {
            System.out.println("Now :" + HDImageStore.getDir());
//            HDImageStore.setDir(hdiPath); Пока тключил
            System.out.println("set :" + HDImageStore.getDir());
        }

        ks.load(null, null);

        // 1. Документ и узел подписи.

        // Исходный документ.
//
        final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
//
        DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
//        final Document sourceDocument = dbFactory.newDocumentBuilder().
//                parse(new ByteArrayInputStream(sourceXmlBin.toByteArray()));

//        Решил сделать выравнивание. Моя реализация красивости.
        DOMImplementationLS ls = (DOMImplementationLS) sourceDocument.getImplementation().getFeature("LS", "3.0");
        LSSerializer serializer = ls.createLSSerializer();

        serializer.getDomConfig().setParameter("format-pretty-print", true);
//        serializer.getDomConfig().setParameter("xml-declaration", true);

        String newMessage = serializer.writeToString(sourceDocument);

        newMessage = newMessage.replace("<?xml version=\"1.0\" encoding=\"UTF-16\"?>", "");

        System.out.println(newMessage);

        sourceDocument = docBuilder.parse(new InputSource(new StringReader(newMessage)));
//          Закончил. Моя реализация красивости.


        final XPathFactory factory = XPathFactory.newInstance();
        final XPath xpath = factory.newXPath();

        // Подписываемый узел.
        final XPathExpression expr = xpath.compile(String.format("//*[@Id='%s']", signingId));
        final NodeList nodes = (NodeList) expr.evaluate(sourceDocument, XPathConstants.NODESET);

        if (nodes.getLength() == 0) {
            throw new Exception("Can't find node with id: " + signingId);
        } // if

        final Node nodeToSign = nodes.item(0);
        System.err.println(nodeToSign.getNodeName());
//         final Node sigParent = nodeToSign.getParentNode();
        final String referenceURI = "#" + signingId;

        // 2. Ключ подписи и сертификат.

        // Сертификат для проверки.
        X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
        System.out.println("x509 : " + cert.getIssuerX500Principal());


        // Ключ подписи.
        PrivateKey privateKey = (PrivateKey) ks.getKey(alias, password);

        // 3. Алгоритмы.

        final KeyingDataProvider keyingProvider = new DirectKeyingDataProvider(cert, privateKey);
        System.out.println(keyingProvider.getSigningKey(cert).getAlgorithm());

        final xades4j.production.XadesSigningProfile sigProf = new XadesBesSigningProfile(keyingProvider)

                // My
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

        // 4. Подпись.
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

//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        DocumentBuilder docBuilder = dbf.newDocumentBuilder();
//        Document doc = docBuilder.parse(new InputSource(new StringReader(mes)));

//        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
//        org.apache.xml.security.utils.XMLUtils.outputDOM(doc, byteStream, true);

        return toMessage(mes);
    }

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
    }
}
