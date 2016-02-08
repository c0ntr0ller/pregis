package ru.prog_matik.java.pregis;


import ru.CryptoPro.JCP.JCP;
import ru.prog_matik.java.pregis.services.organizations.ExportOrgRegistry;
import ru.prog_matik.java.pregis.signet.Configure;

public class Main {
    public static void main(String[] args) throws Exception {

//        Укажем XMLSignature формировать подпись без разделителей '\n'
        System.setProperty("org.apache.xml.security.ignoreLineBreaks", "true");

//        Инициализация сертификатов и Крипто-ПРО
        System.setProperty("com.sun.security.enableCRLDP", "true");
        System.setProperty("com.ibm.security.enableCRLDP", "true");
//        System.setProperty("javax.net.ssl.supportGVO","false");
        System.setProperty("javax.net.ssl.keyStoreType", JCP.HD_STORE_NAME);
        System.setProperty("javax.net.ssl.keyStoreProvider", JCP.PROVIDER_NAME);
        System.setProperty("javax.net.ssl.keyStorePassword", String.valueOf(Configure.getKeyStorePassword()));
        System.setProperty("javax.net.ssl.trustStoreType", JCP.CERT_STORE_NAME);
        System.setProperty("javax.net.ssl.trustStore", Configure.getTrustStorePath());
        System.setProperty("javax.net.ssl.trustStorePassword", String.valueOf(Configure.getTrustStorePassword()));

//        Start
        ExportOrgRegistry reg = new ExportOrgRegistry();
//        reg.sendMessage();
        reg.callExportOrgRegistry();

//        ExportDataProvider exportDataProvider = new ExportDataProvider();
//        exportDataProvider.callExportDataProvide();

//        ExportNsiList exportNsiList = new ExportNsiList();
//        exportNsiList.callExportNsiList();

    }
}
