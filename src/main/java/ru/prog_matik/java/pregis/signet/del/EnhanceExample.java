//package ru.prog_matik.java.pregis.signet.del;
//
//import org.bouncycastle.cms.CMSException;
//import org.bouncycastle.cms.CMSSignedData;
//import org.bouncycastle.cms.SignerInformation;
//import org.bouncycastle.cms.SignerInformationStore;
//import org.bouncycastle.tsp.TimeStampToken;
//import ru.CryptoPro.CAdES.CAdESSignature;
//import ru.CryptoPro.CAdES.CAdESSigner;
//import ru.CryptoPro.CAdES.CAdESType;
//import ru.CryptoPro.CAdES.exception.CAdESException;
//import ru.CryptoPro.JCP.JCP;
//import ru.CryptoPro.JCP.tools.Array;
//import ru.prog_matik.java.pregis.signet.Configure;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.security.*;
//import java.security.cert.*;
//import java.util.*;
//
///**
// * Пример усовершенствования подписи CAdES-BES до CAdES-X Long Type 1.
// *
// * @author Yevgeniy, 17/04/2012
// */
//public class EnhanceExample {
//    private static final String TAG = "***";
//
//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//
//        String fileIn = "temp" + File.separator + "dump.xml";
//
//        try {
//
//            KeyStore keyStore = Configure.getKeyStore();
//
//            PrivateKey privateKey = (PrivateKey) keyStore.getKey(Configure.getKeyStoreAlias(), Configure.getKeyStorePassword());
//
//            List<java.security.cert.Certificate> lChain = Arrays.asList(keyStore.getCertificateChain(Configure.getKeyStoreAlias()));
//
//            Collection<X509Certificate> xChain =
//                    Arrays.asList((lChain).toArray(new X509Certificate[lChain.size()]));
//
//
//            Collection<X509Certificate> chain = new ArrayList<X509Certificate>();
//            chain.addAll(xChain);
////            Configuration.loadConfiguration(chain);
//
//            // 1. Загрузка и "проверка" подписи.
//
//            // Читаем подпись из файла.
//            byte[] cadesCms = Array.readFile(fileIn);
//
//            // Подпись в тесте была совмещенная, потому данные равны null. Предположим, что
//            // подписей несколько, тогда лучше указать тип null и положиться на самоопределение
//            // типа подписи.
//            CAdESSignature cadesSignature = new CAdESSignature(cadesCms, null, null);
//
//            // Список CRL.
//            List<X509CRL> crlList = null;
//
//            // Если задан CRL, то читаем его из файла.
//            if ("C:\\andryha\\project\\workspace_for_git\\PreGIS\\data\\sit.cer" != null) {
//
//                X509CRL crl = (X509CRL) CertificateFactory.getInstance("X.509")
//                        .generateCRL(new FileInputStream("C:\\andryha\\project\\workspace_for_git\\PreGIS\\data\\casert.crt"));
//
//                crlList = Collections.singletonList(crl);
//                cadesSignature.verify(chain, crlList);
//
//            } else {
//                cadesSignature.verify(chain);
//            }
//
//            // Список всех подписантов в исходной подписи.
//            Collection<SignerInformation> srcSignerInfos = new ArrayList<SignerInformation>();
//
//            for (ru.CryptoPro.CAdES.CAdESSigner signer : cadesSignature.getCAdESSignerInfos()) {
//                srcSignerInfos.add(signer.getSignerInfo());
//            }
//
//            // 2. Усовершенствование подписи.
//
//            // Получаем только первого подписанта CAdES-BES, его усовершенствуем. Остальных не трогаем.
////            CMSSignedDataParser c = null;
////
////            GostDigestCalculatorProvider var6 = new GostDigestCalculatorProvider((Key) null, "JCP");
////
////            CMSSignedData srcSignedData = null;
////            c = new CMSSignedDataParser(var6, cadesCms);
////            c.getSignedContent().drain();
////            srcSignedData = new CMSSignedData(cadesCms);
////            srcSignedData = c;
//
//            CMSSignedData srcSignedData = new CMSSignedData(cadesCms);
//            CAdESSigner srcSigner = cadesSignature.getCAdESSignerInfo(0);
//
//            // Исключаем его из исходного списка, т.к. его место займет усовершенствованный подписант.
//            srcSignerInfos.remove(srcSigner.getSignerInfo());
//
//            // Улучшаем CAdES-BES до CAdES-X Long Type 1.
//            srcSigner.enhance(JCP.PROVIDER_NAME, JCP.GOST_DIGEST_OID, chain,
//                    "http://www.cryptopro.ru:80/tsp/", CAdESType.CAdES_X_Long_Type_1);
//
//            // Усовершенствованный подписант.
//            SignerInformation enhSigner = srcSigner.getSignerInfo();
//
//            // Добавляем его в исходный список подписантов.
//            srcSignerInfos.add(enhSigner);
//
//            // Список подписантов.
//            SignerInformationStore dstSignerInfoStore = new SignerInformationStore(srcSignerInfos);
//
//            // Обновляем исходную подпись c ее начальным списком подписантов на тот же,
//            // но с первым усовершенствованным подписантом.
//            CMSSignedData dstSignedData =
//                    CMSSignedData.replaceSigners(srcSignedData, dstSignerInfoStore);
//
//            Array.writeFile("temp" + File.separator + "enhanced",
//                    dstSignedData.getEncoded());
//
//            // 3. Проверка усовершенствованной подписи.
//
//            // Проверяем подпись.
//            cadesSignature = new CAdESSignature(dstSignedData.getEncoded(), null, null);
//            cadesSignature.verify(chain, crlList);
//
//            printSignatureInfo(cadesSignature);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (CertificateException e) {
//            e.printStackTrace();
//        } catch (UnrecoverableKeyException e) {
//            e.printStackTrace();
//        } catch (CAdESException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        } catch (CMSException e) {
//            e.printStackTrace();
//        } catch (CRLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Вывод информации о подписи: кто подписал, тип подписи, штампы времени.
//     *
//     * @param signature CAdES подпись.
//     */
//    public static void printSignatureInfo(CAdESSignature signature) {
//
//        System.out.println("$$$ Print signature information $$$");
//
//        // Список подписей.
//        int signerIndex = 1;
//        for (CAdESSigner signer : signature.getCAdESSignerInfos()) {
//            printSignerInfo(signer, signerIndex++, "");
//        }
//    }
//
//    /**
//     * Вывод информации об отдельной подписи.
//     *
//     * @param signer Подпись.
//     * @param index  Индекс подписи.
//     * @param tab    Отступ для удобства печати.
//     */
//    private static void printSignerInfo(CAdESSigner signer, int index, String tab) {
//
//        X509Certificate signerCert = signer.getSignerCertificate();
//
//        System.out.println(tab + " Signature #" + index + " (" +
//                CAdESType.getSignatureTypeName(signer.getSignatureType()) + ")" +
//                (signerCert != null ? (" verified by " + signerCert.getSubjectDN()) : ""));
//
//        if (signer.getSignatureType().equals(CAdESType.CAdES_X_Long_Type_1)) {
//
//            TimeStampToken signatureTimeStamp = signer.getSignatureTimestampToken();
//            TimeStampToken cadesCTimeStamp = signer.getCAdESCTimestampToken();
//
//            if (signatureTimeStamp != null) {
//                System.out.println(tab + TAG + " Signature timestamp set: " +
//                        signatureTimeStamp.getTimeStampInfo().getGenTime());
//            } // if
//
//            if (cadesCTimeStamp != null) {
//                System.out.println(tab + TAG + " CAdES-C timestamp set: " +
//                        cadesCTimeStamp.getTimeStampInfo().getGenTime());
//            } // if
//
//        } // if
//
////        printSignerAttributeTableInfo(index,
////                signer.getSignerSignedAttributes(), "signed");
////
////        printSignerAttributeTableInfo(index,
////                signer.getSignerUnsignedAttributes(), "unsigned");
////
////        printCountersignerInfos(signer.getCAdESCountersignerInfos());
//    }
//
//}