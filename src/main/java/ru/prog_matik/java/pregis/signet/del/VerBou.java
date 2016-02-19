package ru.prog_matik.java.pregis.signet.del;

import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.IDEAEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.prog_matik.java.pregis.signet.Configure;
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
import java.util.*;

/**
 * Created by andryha on 17.02.2016.
 */
public class VerBou {

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
//        String secretKey = "r7RoTcRp8FbrqpMKkKjgRw+278Wx+iVtQY2M7pe9fFg=";
//
//        File file = new File("temp" + File.separator + "dump.xml");
//        verify(file);
        encryptData();

    }

    public static void encryptData() {

        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new IDEAEngine()));  // Шифр
        String key = "x-392kla%3$*1f";            // Kлюч

        System.out.println("Введи значение:");
        Scanner scanner = new Scanner(System.in);

        // Получить содержимое из текстового поля
        byte[] inBytes = scanner.next().getBytes();

        // Инициализировать шифр. 'true' указывает шифрование
        cipher.init(true, new KeyParameter(key.getBytes()));

        // Определить минимальный размер выходного буфера
        byte[] outBytes = new byte[cipher.getOutputSize(inBytes.length)];

        // 'len' - это возвращенная реальная длина
        int len = cipher.processBytes(inBytes, 0, inBytes.length, outBytes, 0);
        try
        {
            // Обработать последний блок в буфере, начиная с позиции 'len'
            cipher.doFinal(outBytes, len);

            // Обновить текстовое поле новой зашифрованной строкой
            System.out.println("Зашифровано: " + (new String(Hex.encode(outBytes))));

            // Отладочное сообщение
            System.out.println("encrypted: " + new String(Hex.encode(outBytes)));
        }
        catch(CryptoException e)
        {
            System.out.println("Exception: " + e.toString());
        }
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
