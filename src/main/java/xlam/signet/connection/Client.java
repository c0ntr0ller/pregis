package ru.prog_matik.java.pregis.signet.connection;

        import javax.net.ssl.*;
        import javax.xml.soap.*;
        import javax.xml.transform.Source;
        import javax.xml.transform.Transformer;
        import javax.xml.transform.TransformerFactory;
        import javax.xml.transform.stream.StreamResult;
        import javax.xml.transform.stream.StreamSource;
        import java.io.FileInputStream;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.security.SecureRandom;
        import java.security.Security;
        import java.security.cert.CertificateException;
        import java.security.cert.X509Certificate;
        import java.util.Iterator;
        import java.util.Vector;

public class Client {

    public static void main(String[] args) {
        talk("https://192.168.10.1:8991/balance/soap", "/home/amir/projects/mine/p8/message/helloWorld.msg");
    }

    private static void talk(String urlval, String msgPath) {
        try {
            // Create message
            MessageFactory mf = MessageFactory.newInstance();
            SOAPMessage msg = mf.createMessage();

            // Object for message parts
            SOAPPart sp = msg.getSOAPPart();
            StreamSource prepMsg = new StreamSource(new FileInputStream(msgPath));
            sp.setContent(prepMsg);

            // Save message
            msg.saveChanges();

            // View input
            System.out.println("\n Soap request:\n");
            msg.writeTo(System.out);
            System.out.println();

            // Trust to certificates
            doTrustToCertificates();

            //SOAPMessage rp = conn.call(msg, urlval);
            SOAPMessage rp = sendMessage(msg, urlval);

            // View the output
            System.out.println("\nXML response\n");

            // Create transformer
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();

            // Get reply content
            Source sc = rp.getSOAPPart().getContent();

            // Set output transformation
            StreamResult result = new StreamResult(System.out);
            tf.transform(sc, result);
            System.out.println();

            // now GET the Response and PARSE IT !
            Vector list = new Vector();
            SOAPBody soapBody = rp.getSOAPBody();
            Iterator iterator1 = soapBody.getChildElements();
            while (iterator1.hasNext()) {
                SOAPBodyElement ThisBodyElement = (SOAPBodyElement) iterator1.next();
                Iterator it2 = ThisBodyElement.getChildElements();
                while (it2.hasNext()) {
                    SOAPElement child2 = (SOAPElement) it2.next();
                    Iterator it3 = child2.getChildElements();
                    while (it3.hasNext()) {
                        SOAPElement child3 = (SOAPElement) it3.next();
                        String value = child3.getValue();
                        list.addElement(value);
                    }
                }
            }
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.elementAt(i));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    static public SOAPMessage sendMessage(SOAPMessage message, String endPoint) throws MalformedURLException, SOAPException {
        SOAPMessage result = null;
        if (endPoint != null && message != null) {
            URL url = new URL(endPoint);
            SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = null;
            long time = System.currentTimeMillis();
            try {
                connection = scf.createConnection(); //point-to-point connection
                result = connection.call(message, url);
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SOAPException soape) {
                        System.out.print("Can't close SOAPConnection:" + soape);
                    }
                }
            }
        }
        return result;
    }

    static public void doTrustToCertificates() throws Exception {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                        return;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                        return;
                    }
                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
                    System.out.println("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
                }
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }
}