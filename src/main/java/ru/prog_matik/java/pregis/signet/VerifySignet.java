package ru.prog_matik.java.pregis.signet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ru.prog_matik.java.pregis.signet.Configure;
import xades4j.UnsupportedAlgorithmException;
import xades4j.providers.CertificateValidationProvider;
import xades4j.providers.impl.DefaultMessageDigestProvider;
import xades4j.providers.impl.PKIXCertificateValidationProvider;
import xades4j.verification.XadesVerificationProfile;
import xades4j.verification.XadesVerifier;

import javax.xml.crypto.dsig.XMLSignature;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.util.Collection;
import java.util.Collections;

/**
 * Класс служит для проверки на корректность подписанного документа.
 */
public class VerifySignet {

    /**
     * Метод получает документ и проверяет на правильность подписи.
     * @param sourceDocument - подписанный документ.
     * @throws Exception
     */
    public static void verify(Document sourceDocument) throws Exception {

        final KeyStore trustStore = Configure.getTrustStore();

        Collection intermediateCertsAndCRLs = Collections.emptyList();

        final CertStore intermediateCertsAndCRLStore = CertStore.getInstance("Collection",
                new CollectionCertStoreParameters(intermediateCertsAndCRLs));

        // Проверяемый документ
        final Document verifyDocument = sourceDocument;

//          Узел с подписью.
        final NodeList nl = verifyDocument.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");

        if (nl.getLength() == 0) {
            throw new Exception("Cannot find Signature element");
        } // if

//              Проверка цепочки сертификатов включена
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        final CertificateValidationProvider validationProvider = new PKIXCertificateValidationProvider(
                trustStore, true, intermediateCertsAndCRLStore);

        final XadesVerificationProfile verProf = new XadesVerificationProfile(validationProvider)

                .withDigestEngineProvider(new DefaultMessageDigestProvider() {
                    @Override
                    public MessageDigest getEngine(String digestAlgorithmURI) throws UnsupportedAlgorithmException {

                        // Определение OID’а алгоритма хэширования по URN
                        final String digestAlgOid = "1.2.643.2.2.9";

                        try {
                            return MessageDigest.getInstance(digestAlgOid);
                        } catch (NoSuchAlgorithmException e) {
                            throw new UnsupportedAlgorithmException(e.getMessage(), digestAlgorithmURI, e);
                        }
                    }
                });

        final XadesVerifier verifier = verProf.newVerifier();
        final Element signatureElement = (Element) nl.item(0); // предположительно, один узел с подписью

        verifier.verify(signatureElement, null);

        System.out.println("\nПодпись проверена!\n");
    }
}
