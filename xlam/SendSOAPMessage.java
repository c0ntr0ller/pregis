package ru.prog_matik.java.pregis;

import org.apache.ws.security.util.Base64;
import ru.prog_matik.java.pregis.other.OtherFormat;
import ru.prog_matik.java.pregis.wsdlobjects.ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry.ExportOrgRegistryRequest;
import ru.prog_matik.java.pregis.wsdlobjects.ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry.ExportOrgRegistryResult;
import ru.prog_matik.java.pregis.wsdlobjects.ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry.ISRequestHeader;

import javax.net.ssl.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Iterator;

public class SendSOAPMessage {

    public SOAPMessage sendSOAP(SOAPMessage soapMessage, URL url) {

        certificateInitialize();

        SOAPMessage result = null;

        try {

            String name = "test";
            String password = "SDldfls4lz5@!82d";

            String authString = name + ":" + password;
            System.out.println("auth string: " + authString);
            String authEncBytes = Base64.encode(authString.getBytes());
            System.out.println("Base64 encoded auth string: " + authEncBytes);

            soapMessage.getMimeHeaders().addHeader("Authorization", "Basic " + authEncBytes);
            soapMessage.getMimeHeaders().setHeader("SOAPAction", "urn:exportOrgRegistry");

//            URL url = new URL("https://54.76.42.99:60045/ext-bus-org-registry-service/services/OrgRegistry");
//            URL url = new URL("http://127.0.0.1:8080/ext-bus-org-registry-service/services/OrgRegistry");
//            URL url = new URL("http://localhost:8081/?WSDL");

            SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = null;
            long time = System.currentTimeMillis();
            try {

                printHeaders(soapMessage.getMimeHeaders());
                soapMessage.writeTo(System.out);

                connection = scf.createConnection(); //point-to-point connection

                result = connection.call(soapMessage, url);
                result.writeTo(System.out);

//                Unmarshaller unmarshaller = JAXBContext.newInstance(ExportOrgRegistryResult.class).createUnmarshaller();
//
//                ExportOrgRegistryResult regResult = (ExportOrgRegistryResult) unmarshaller.unmarshal(result.getSOAPPart().getEnvelope().getBody().extractContentAsDocument());
//                System.out.println("\nregResult getErrorMessage: " + regResult.getErrorMessage().getErrorCode());
//                System.out.println("\nregResult getDescription: " + regResult.getErrorMessage().getDescription());
//                System.out.println("\nregResult getStackTrace: " + regResult.getErrorMessage().getStackTrace());


            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SOAPException soape) {
                        System.out.print("Can't close SOAPConnection:" + soape);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Обработчик завершил работу");

        return result;
    }

    public SSLSocketFactory certificateInitialize() {

        SSLContext context = null;
        SSLSocketFactory ssf = null;
        KeyStore keyStore = null;
        TrustManagerFactory tmf = null;
        KeyStore keyStoreCA = null;
        KeyManagerFactory kmf = null;

        try {
            context = SSLContext.getInstance("GostTLS");


            // Local client certificate and key and server certificate
            keyStore = KeyStore.getInstance("HDImageStore");
            keyStore.load(null, null);

            // Build a TrustManager, that trusts only the server certificate
            keyStoreCA = KeyStore.getInstance("HDImageStore");
            keyStoreCA.load(new FileInputStream("D:\\java_workspace\\projects_with_git\\Work\\PreGIS\\data\\xadesTrustStore"), "1".toCharArray());
//                keyStoreCA.setCertificateEntry("cacer3.crt", keyStore.getCertificate("cacer3.crt"));
            tmf = TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//            tmf = TrustManagerFactory.getInstance("GostX509");
            tmf.init(keyStoreCA);

            // Build a KeyManager for Client auth
            kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//            kmf = KeyManagerFactory.getInstance("GostX509");
            kmf.init(keyStore, "123456".toCharArray());

            //            Viev Algorithm
            System.out.println("Client Algorithm: " + kmf.getAlgorithm());
            System.out.println("Server Algorithm: " + tmf.getAlgorithm());
            context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            ssf = context.getSocketFactory();


            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
            HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String urlHostName, SSLSession session) {
                    if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
                        System.out.println("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
                    }
                    return true;
                }
            };

            HttpsURLConnection.setDefaultHostnameVerifier(hv);

        } catch (NoSuchAlgorithmException | IOException | CertificateException | KeyStoreException | UnrecoverableKeyException | KeyManagementException e) {
            e.printStackTrace();
        }
        return ssf;
    }


    private void printHeaders(MimeHeaders headers) {

//        MimeHeaders headers = msg.getMimeHeaders();

        Iterator eachHeader = headers.getAllHeaders();
        while (eachHeader.hasNext()) {
            MimeHeader currentHeader = (MimeHeader) eachHeader.next();

            System.out.println("Header name: " + currentHeader.getName() + " " + currentHeader.getValue());
        }

    }

}
