package ru.prog_matik.java.pregis;


import ru.CryptoPro.JCP.JCP;
import ru.prog_matik.java.pregis.signet.Configure;

public class Main {
    public static void main(String[] args) throws Exception {

//        Укажем XMLSignature формировать подпись без разделителей '\n'
        System.setProperty("org.apache.xml.security.ignoreLineBreaks", "true");


//        Инициализация сертификатов и Крипто-ПРО

        System.setProperty("javax.net.ssl.supportGVO","false");

        System.setProperty("javax.net.debug","all");

        System.setProperty("com.sun.security.enableCRLDP", "true");
        System.setProperty("com.ibm.security.enableCRLDP", "true");
        System.setProperty("javax.net.ssl.keyStoreType", JCP.HD_STORE_NAME);
        System.setProperty("javax.net.ssl.keyStoreProvider", JCP.PROVIDER_NAME);
        System.setProperty("javax.net.ssl.keyStorePassword", String.valueOf(Configure.getKeyStorePassword()));
        System.setProperty("javax.net.ssl.trustStoreType", JCP.CERT_STORE_NAME);
        System.setProperty("javax.net.ssl.trustStore", Configure.getTrustStorePath());
        System.setProperty("javax.net.ssl.trustStorePassword", String.valueOf(Configure.getTrustStorePassword()));


//        Security.addProvider(new BouncyCastleProvider());
//
//        System.setProperty("javax.net.debug", "all");



//        System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
//        System.setProperty("javax.net.ssl.keyStore", "data/dubovik.p12.pfx");
//        System.setProperty("javax.net.ssl.keyStoreProvider", "BC");
//        System.setProperty("javax.net.ssl.keyStorePassword", "123456");
////
//        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
//        System.setProperty("javax.net.ssl.trustStore", "data/trust_store.jks");
////        System.setProperty("javax.net.ssl.trustStoreProvider", "BC");
//        System.setProperty("javax.net.ssl.trustStorePassword", "123456");

//
//        System.setProperty("https.protocols", "TLSv1");
//        System.setProperty("jdk.tls.client.protocols", "TLSv1");

//        Start
        new ProgramAction().callExportOrgRegistry();
//        new ProgramAction().getSenderID();  // Получение SenderID
//        new ProgramAction().removeSenderID();  // Упразднен
//        new ProgramAction().callExportDataProvider(); // Просто запрос организации
//        new ProgramAction().callExportNsiList(); // 1
//        new ProgramAction().callExportNsiItem(); // 2
//        new ProgramAction().callExportDataProviderNsiItem(); //экспортирует справочники №1, 51, 59
//        new ProgramAction().callExportStatusCAChData();
//        new ProgramAction().callExportCAChData();

    }
}
