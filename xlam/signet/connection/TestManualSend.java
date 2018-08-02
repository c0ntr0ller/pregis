package ru.prog_matik.java.pregis.signet.connection;

import com.sun.xml.internal.messaging.saaj.client.p2p.HttpSOAPConnectionFactory;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.ws.util.Pool;
import org.apache.ws.security.util.Base64;
import ru.prog_matik.java.pregis.wsdlobjects.ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry.ExportOrgRegistryResult;

import javax.net.ssl.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.*;
import java.io.*;
import java.net.URL;
import java.nio.channels.ClosedByInterruptException;
import java.security.KeyStore;
import java.util.Iterator;

/**
 * Created by andryha on 28.01.2016.
 */
public class TestManualSend {

    public static void main(String[] args) throws Exception {

        TestManualSend send = new TestManualSend();
        send.testSOAP();
//        send.runTest();

//        String testText = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Header><ns2:ISRequestHeader xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/\" xmlns=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/\" xmlns:ns3=\"http://www.w3.org/2000/09/xmldsig#\"><Date>2016-01-28T23:07:16.318+06:00</Date><MessageGUID>11b6dd93-ca5c-4667-ae99-49668e226cf4</MessageGUID></ns2:ISRequestHeader></S:Header><S:Body><ns2:exportOrgRegistryRequest xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/\" xmlns=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/\" xmlns:ns3=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"signed-data-container\"><ns2:SearchCriteria><OGRN>1125476111903</OGRN></ns2:SearchCriteria></ns2:exportOrgRegistryRequest></S:Body></S:Envelope>";
////        TestManualSend t = new TestManualSend();
////        t.test();
////
//        String getTestText;
//        byte[] readText = new byte[8192];
//        int off = 0;
//        StringBuilder sb = new StringBuilder();
//        int c;

//                        System.setProperty("com.sun.security.enableCRLDP", "true");
//                System.setProperty("com.ibm.security.enableCRLDP", "true");
//                System.setProperty("javax.net.ssl.supportGVO","false");
//
//        System.setProperty("javax.net.ssl.keyStoreType", "HDImageStore");
//        System.setProperty("javax.net.ssl.keyStoreProvider", "JCP");
//        System.setProperty("javax.net.ssl.keyStorePassword", "123456");
//
//        System.setProperty("javax.net.ssl.trustStoreType", "HDImageStore");
//        System.setProperty("javax.net.ssl.trustStore", "D:\\java_workspace\\projects_with_git\\Work\\PreGIS\\data\\xadesTrustStore");
//        System.setProperty("javax.net.ssl.trustStorePassword", "1");
//        System.setProperty("jdk.tls.client.protocols", "TLSv1");
//        System.setProperty("https.protocols", "TLSv1");

//        StunnelConnection connection = new StunnelConnection("54.76.42.99", 60045);
//////        System.out.println(connection.connected);
//        connection.connect();
//        connection.out_stream.write(testText.getBytes());
//        connection.out_stream.flush();
//        connection.out_stream.close();
//
//
////        while ((c = reader.read()) != -1 && c != 10 && c != 13) { example не понятно что такое 10 и 13.
//        while ((c = connection.in_stream.read()) != -1) {
//            sb.append((char) c);
//        }
//
//        getTestText = sb.toString();
//        System.out.println(getTestText);
//
//        connection.disconnect();

//        if (!connection.isConnected()) {
//            connection.
//        }

//        System.out.println(connection.isConnected());
//        connection.write(testText.getBytes());
//        while (connection.read(testText.getBytes(), off, testText.length())!= -1) {
//            System.out.println(Arrays.toString(readText));
//        }


    }

//    public void test() throws Exception {
//        String text = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Header><ns2:ISRequestHeader xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/\" xmlns=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/\" xmlns:ns3=\"http://www.w3.org/2000/09/xmldsig#\"><Date>2016-01-28T23:07:16.318+06:00</Date><MessageGUID>11b6dd93-ca5c-4667-ae99-49668e226cf4</MessageGUID></ns2:ISRequestHeader></S:Header><S:Body><ns2:exportOrgRegistryRequest xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/\" xmlns=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/\" xmlns:ns3=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"signed-data-container\"><ns2:SearchCriteria><OGRN>1125476111903</OGRN></ns2:SearchCriteria></ns2:exportOrgRegistryRequest></S:Body></S:Envelope>";
////        SOAPMessage soapMessage = MessageFactory.newInstance(text).createMessage();
//        test2(text);
//
//    }
//
//    public void test2(String text) throws Exception {
//
//        KeyStore ks = KeyStore.getInstance(JCP.HD_STORE_NAME, "JCP");
//        ks.load(null, null);
//
//        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//        kmf.init(ks, "123456".toCharArray());
//
//        X509Certificate cert = (X509Certificate) ks.getCertificate("dubovik");
//        System.out.println("x509 : " + cert.getIssuerX500Principal());
//
//        KeyStore ts = KeyStore.getInstance(JCP.CERT_STORE_NAME, "JCP");
//        ks.load(new FileInputStream("D:\\java_workspace\\projects_with_git\\Work\\PreGIS\\data\\xadesTrustStore"), "1".toCharArray());
////        api.dom.gosuslugi.ru:443
//
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//        tmf.init(ts);
//
//        SSLContext sc = SSLContext.getInstance("GostTLS");
////        TrustManager[] trustManagers = tmf.getTrustManagers();
//        sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
//
//        SSLSocketFactory ssf = sc.getSocketFactory();
////        SSLSocket s = (SSLSocket) ssf.createSocket("api.dom.gosuslugi.ru", 443);
//        SSLSocket s = (SSLSocket) ssf.createSocket("54.76.42.99", 60045);
////        s.startHandshake();
//
//
//        //Send header
//        String path = "/PlaceOrderService/PlaceOrderPort";
//        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
//        // You can use "UTF8" for compatibility with the Microsoft virtual machine.
//        wr.write("POSTpath" + " HTTP/1.0\r\n");
//        wr.write("Hostalhost\r\n");
//        wr.write("Content-Length" + text.length() + "\r\n");
//        wr.write("Content-Typet/xml; charset=\"utf-8\"\r\n");
//        wr.write("\r\n");            //Send data
//        wr.write(text);
//        wr.flush();
//
//        // Response
//        BufferedReader rd = new BufferedReader(new InputStreamReader(s.getInputStream()));
//        String line;
//        System.out.println("Responseine");
//        while ((line = rd.readLine()) != null) {
//            System.out.println(line);
//
//
//
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//            HostnameVerifier hv = new HostnameVerifier() {
//                public boolean verify(String urlHostName, SSLSession session) {
//                    if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
//                        System.out.println("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
//                    }
//                    return true;
//                }
//            };
//            HttpsURLConnection.setDefaultHostnameVerifier(hv);
//        }
//    }

    public void testSOAP() {
        SSLContext context = null;
        KeyStore keyStore = null;
        TrustManagerFactory tmf = null;
        KeyStore keyStoreCA = null;
        KeyManagerFactory kmf = null;
//        String testText = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
//                "\t<S:Header>\n" +
//                "\t\t<ns3:ISRequestHeader xmlns:ns3=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/\" xmlns=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/\">\n" +
//                "\t\t\t<ns2:Date>2016-01-31T03:27:24.423+06:00</ns2:Date>\n" +
//                "\t\t\t<ns2:MessageGUID>bf43cf05-2499-4993-ac10-54ed8858507c</ns2:MessageGUID>\n" +
//                "\t\t</ns3:ISRequestHeader>\n" +
//                "\t</S:Header>\n" +
//                "\t<S:Body>\n" +
//                "\t\t<ns3:exportOrgRegistryRequest xmlns:ns3=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/\" xmlns=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/\">\n" +
//                "\t\t\t<ns3:SearchCriteria>\n" +
//                "\t\t\t\t<ns2:OGRN>1125476111903</ns2:OGRN>\n" +
//                "\t\t\t</ns3:SearchCriteria>\n" +
//                "\t\t</ns3:exportOrgRegistryRequest>\n" +
//                "\t</S:Body>\n" +
//                "</S:Envelope>";

        String testText = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "\t<S:Header>\n" +
                "\t\t<ns3:ISRequestHeader xmlns:ns3=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/\" xmlns=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/\">\n" +
                "\t\t\t<ns2:Date>2016-01-31T04:20:46.357+06:00</ns2:Date>\n" +
                "\t\t\t<ns2:MessageGUID>57868b5b-892a-460e-ba77-6b80ec811a25</ns2:MessageGUID>\n" +
                "\t\t</ns3:ISRequestHeader>\n" +
                "\t</S:Header>\n" +
                "\t<S:Body>\n" +
                "\t\t<ns3:exportOrgRegistryRequest xmlns:ns3=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/\" xmlns=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/\">\n" +
                "\t\t\t<ns3:SearchCriteria>\n" +
                "\t\t\t\t<ns2:OGRN>1125476111903</ns2:OGRN>\n" +
                "\t\t\t</ns3:SearchCriteria>\n" +
                "\t\t</ns3:exportOrgRegistryRequest>\n" +
                "\t</S:Body>\n" +
                "</S:Envelope>";

        try {

            InputStream in = new ByteArrayInputStream(testText.getBytes());

            SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null, in);
            soapMessage.getSOAPPart().normalizeDocument();
            soapMessage.saveChanges();
            soapMessage.writeTo(System.out);

            context = SSLContext.getInstance("GostTLS");

            // Local client certificate and key and server certificate
            keyStore = KeyStore.getInstance("HDImageStore");
            keyStore.load(null, null);

            // Build a TrustManager, that trusts only the server certificate
            keyStoreCA = KeyStore.getInstance("HDImageStore");
            keyStoreCA.load(new FileInputStream("D:\\java_workspace\\projects_with_git\\Work\\PreGIS\\data\\xadesTrustStore"), "1".toCharArray());
//                keyStoreCA.setCertificateEntry("cacer3.crt", keyStore.getCertificate("cacer3.crt"));
            tmf = TrustManagerFactory.getInstance("GostX509");
            tmf.init(keyStoreCA);

            // Build a KeyManager for Client auth
            kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//            kmf = KeyManagerFactory.getInstance("GostX509");
            kmf.init(keyStore, "123456".toCharArray());
            context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

//            Viev Algorithm
            System.out.println("Client Algorithm: " + kmf.getAlgorithm());
            System.out.println("Server Algorithm: " + tmf.getAlgorithm());

            String name = "test";
            String password = "SDldfls4lz5@!82d";

            String authString = name + ":" + password;
            System.out.println("auth string: " + authString);
            String authEncBytes = Base64.encode(authString.getBytes());
            System.out.println("Base64 encoded auth string: " + authEncBytes);

            soapMessage.getMimeHeaders().addHeader("Authorization", "Basic " + authEncBytes);
            soapMessage.getMimeHeaders().setHeader("SOAPAction", "urn:exportOrgRegistry");

//        SSLSocketFactory socketFactory = context.getSocketFactory();

            URL url = new URL("https://54.76.42.99:60045/ext-bus-org-registry-service/services/OrgRegistry");
//            URL url = new URL("http://127.0.0.1:8080/ext-bus-org-registry-service/services/OrgRegistry");
//            URL url = new URL("http://localhost:8081/?WSDL");

//            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
//            HttpsURLConnection httpsURLConnection = null;

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

            SOAPMessage result = null;
                SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
                SOAPConnection connection = null;
                long time = System.currentTimeMillis();
                try {
                    connection = scf.createConnection(); //point-to-point connection

                    printHeaders(soapMessage.getMimeHeaders());

                    result = connection.call(soapMessage, url);
                    result.writeTo(System.out);

//                    soapMessage.getSOAPPart().getEnvelope().get

                    Unmarshaller unmarshaller = JAXBContext.newInstance(ExportOrgRegistryResult.class).createUnmarshaller();

                    ExportOrgRegistryResult regResult = (ExportOrgRegistryResult) unmarshaller.unmarshal(result.getSOAPPart().getEnvelope().getBody().extractContentAsDocument());
                    System.out.println("\nregResult getErrorMessage: " + regResult.getErrorMessage().getErrorCode());
                    System.out.println("\nregResult getDescription: " + regResult.getErrorMessage().getDescription());
                    System.out.println("\nregResult getStackTrace: " + regResult.getErrorMessage().getStackTrace());



                } finally {
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SOAPException soape) {
                            System.out.print("Can't close SOAPConnection:" + soape);
                        }
                    }
                }



//            httpsURLConnection.setSSLSocketFactory(context.getSocketFactory());
//            httpsURLConnection.setRequestProperty("Authorization", "Basic " + authEncBytes);

//            httpsURLConnection.setDoInput(true);
//            httpsURLConnection.setDoOutput(true);
//
//            httpsURLConnection.connect();
//
//            setTestRequest(httpsURLConnection);
//            getHeaderField(httpsURLConnection);
//
//
//            httpsURLConnection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }



        System.out.println("Обработчик завершил работу");


    }

    public void runTest() {
        SSLContext context = null;
        KeyStore keyStore = null;
        TrustManagerFactory tmf = null;
        KeyStore keyStoreCA = null;
        KeyManagerFactory kmf = null;
        try {

//                FileInputStream pkcs12in = new FileInputStream(new File(stunnelCert));

            context = SSLContext.getInstance("GostTLS");

            // Local client certificate and key and server certificate
            keyStore = KeyStore.getInstance("HDImageStore");
            keyStore.load(null, null);

            // Build a TrustManager, that trusts only the server certificate
            keyStoreCA = KeyStore.getInstance("HDImageStore");
            keyStoreCA.load(new FileInputStream("D:\\java_workspace\\projects_with_git\\Work\\PreGIS\\data\\xadesTrustStore"), "1".toCharArray());
//                keyStoreCA.setCertificateEntry("cacer3.crt", keyStore.getCertificate("cacer3.crt"));
            tmf = TrustManagerFactory.getInstance("GostX509");
            tmf.init(keyStoreCA);
            System.out.println(tmf.getAlgorithm());

            // Build a KeyManager for Client auth
            kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                kmf = KeyManagerFactory.getInstance("GostX509");
            kmf.init(keyStore, "123456".toCharArray());
            System.out.println(kmf.getAlgorithm());
            context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        SSLSocketFactory socketFactory = context.getSocketFactory();
        try {

            String name = "test";
            String password = "SDldfls4lz5@!82d";

            String authString = name + ":" + password;
            System.out.println("auth string: " + authString);
            String authEncBytes = Base64.encode(authString.getBytes());
            System.out.println("Base64 encoded auth string: " + authEncBytes);

            URL url = new URL("https://54.76.42.99:60045/ext-bus-org-registry-service/services/OrgRegistry");

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setSSLSocketFactory(socketFactory);
            httpsURLConnection.setRequestProperty("Authorization", "Basic " + authEncBytes);


            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setDoOutput(true);

            httpsURLConnection.connect();

//            getHeaderField(httpsURLConnection);
                setTestRequest(httpsURLConnection);
                getHeaderField(httpsURLConnection);


                httpsURLConnection.disconnect();
        } catch (ClosedByInterruptException e) {
            // Thread interrupted during connect.
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Обработчик завершил работу");
    }

//TODO: override disconnect/other methods to make sure the tunnel is closed/cleaned up nicely

    private void setTestRequest(HttpsURLConnection connection) {

        byte[] buffer = new byte[512];
        String sendMessage = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "<S:Header>\n" +
                "<ns3:ISRequestHeader xmlns:ns3=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/\" xmlns=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/\">\n" +
                "<ns2:Date>2016-01-30T01:20:56.941+06:00</ns2:Date>\n" +
                "<ns2:MessageGUID>f022dbe7-4e1a-4a1d-895a-4a0995cfc169</ns2:MessageGUID>\n" +
                "</ns3:ISRequestHeader>\n" +
                "</S:Header>\n" +
                "<S:Body>\n" +
                "<ns3:exportOrgRegistryRequest xmlns:ns3=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/organizations-registry/\" xmlns=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.5.0.2/\" Id=\"signed-data-container\">\n" +
                "<ns3:SearchCriteria>\n" +
                "<ns2:OGRN>1125476111903</ns2:OGRN>\n" +
                "</ns3:SearchCriteria>\n" +
                "</ns3:exportOrgRegistryRequest>\n" +
                "</S:Body>\n" +
                "</S:Envelope>";

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            writer.write(sendMessage);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void printHeaders(MimeHeaders headers) {

//        MimeHeaders headers = msg.getMimeHeaders();

        Iterator eachHeader = headers.getAllHeaders();
        while (eachHeader.hasNext()) {
            MimeHeader currentHeader = (MimeHeader) eachHeader.next();

            System.out.println("Header name: " + currentHeader.getName() + " " + currentHeader.getValue());
        }

    }

    private void getHeaderField(HttpsURLConnection connection) {

        int n = 1;
        String key;
        while ((key = connection.getHeaderFieldKey(n)) != null) {
            String value = connection.getHeaderField(n);
            System.out.println(key + ": " + value);
            n++;
        }
        //вывод данных вместе с названием функции
        System.out.println("-----------------");
        System.out.println("getContentType: " + connection.getContentType());
        System.out
                .println("getContentLength: " + connection.getContentLength());
        System.out.println(
                "getContentEncoding: " + connection.getContentEncoding());
        System.out.println("getDate: " + connection.getDate());
        System.out.println("getExpiration: " + connection.getExpiration());
        System.out.println("getLastModified: " + connection.getLastModified());
        System.out.println("------------------");
        System.out.println("getHeaderFields:\n" + connection.getHeaderFields());
        System.out.println("------------------");

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            //вывод полученных данных в файл
            String line;
            String text = "";
            n = 1;
            while ((line = in.readLine()) != null) {
                text = text + line;
                n++;
            }
            System.out.println("getHeaderField Text: " + text);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
