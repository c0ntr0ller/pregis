package ru.prog_matik.java.pregis.signet;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import xades4j.XAdES4jException;
import xades4j.providers.CertificateValidationException;
import xades4j.providers.CertificateValidationProvider;
import xades4j.providers.ValidationData;
import xades4j.verification.SignatureSpecificVerificationOptions;
import xades4j.verification.UnexpectedJCAException;
import xades4j.verification.XadesVerificationProfile;
import xades4j.verification.XadesVerifier;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by andryha on 17.02.2016.
 */
public class VerBou {

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        String secretKey = "r7RoTcRp8FbrqpMKkKjgRw+278Wx+iVtQY2M7pe9fFg=";

        File file = new File("temp" + File.separator + "dump.xml");
        verify(file);


    }

    public static boolean verify(final File file) {

        List certList = new ArrayList<>();
        try {

            final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            final DocumentBuilder db = dbf.newDocumentBuilder();

            final Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            final NodeList nList = doc.getElementsByTagName("ds:Signature");
            Element elem = null;
            for (int temp = 0; temp < nList.getLength(); temp++) {
                final Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    elem = (Element) nNode;
                }
            }
            final NodeList nList2 = doc.getElementsByTagName("ds:X509Certificate");
            final List<String> certDataList = new ArrayList<String>();
            for (int temp = 0; temp < nList2.getLength(); temp++) {
                final Node nNode = nList2.item(temp);
                certDataList.add(nNode.getTextContent());
            }

            for (String s : certDataList) {
                System.out.println("certDataList: " + s);
            }

            //        Ключ подписи и сертификат.

            KeyStore keyStore = Configure.getKeyStore();

//        Сертификат для проверки.
            X509Certificate cert = (X509Certificate) keyStore.getCertificate(Configure.getKeyStoreAlias());

            certList.add(certDataList);

            final CertificateValidationProvider  certValidator = new CertificateValidationProvider() {
                @Override
                public ValidationData validate(X509CertSelector certSelector, Date validationDate, Collection<X509Certificate> otherCerts) throws CertificateValidationException, UnexpectedJCAException {
                    return new ValidationData(certList);
                }
            };

            final XadesVerificationProfile p = new XadesVerificationProfile(certValidator);
            final XadesVerifier v = p.newVerifier();
            final SignatureSpecificVerificationOptions opts = new SignatureSpecificVerificationOptions();

            // for relative document paths
            final String baseUri = "file:///" + file.getParentFile().getAbsolutePath().replace("\\", "/") + "/";
            System.out.println("baseUri:" + baseUri);
            opts.useBaseUri(baseUri);
            v.verify(elem, opts);
            return true;
        } catch (final IllegalArgumentException | XAdES4jException | IOException | ParserConfigurationException | SAXException e) {
            System.out.println("XML not validated!\n" + e);
            e.printStackTrace();
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | NoSuchProviderException e) {
            e.printStackTrace();
        }

        return false;
    }

}
