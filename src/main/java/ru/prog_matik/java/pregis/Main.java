package ru.prog_matik.java.pregis;


import ru.prog_matik.java.pregis.services.organizations.ExportOrgRegistry;

public class Main {
    public static void main(String[] args) throws Exception {

//        Инициализация сертификатов и Крипто-ПРО
//        System.setProperty("com.sun.security.enableCRLDP", "true");
//        System.setProperty("com.ibm.security.enableCRLDP", "true");
//        System.setProperty("javax.net.ssl.supportGVO","false");
//        System.getProperty("javax.net.ssl.context", "GostTLS"); Не понятно работает ли?
        System.setProperty("javax.net.ssl.keyStoreType", "HDImageStore");
        System.setProperty("javax.net.ssl.keyStoreProvider", "JCP");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");
        System.setProperty("javax.net.ssl.trustStoreType", "HDImageStore");
        System.setProperty("javax.net.ssl.trustStore", "C:\\andryha\\project\\workspace_for_git\\PreGIS\\data\\xadesTrustStore");
        System.setProperty("javax.net.ssl.trustStorePassword", "1");

//        System.setProperty("jdk.tls.client.protocols", "TLSv1");
//        System.setProperty("https.protocols", "TLSv1");

//        Start
        ExportOrgRegistry reg = new ExportOrgRegistry();
//        reg.sendMessage();
        reg.callExportOrgRegistry();

    }
}
