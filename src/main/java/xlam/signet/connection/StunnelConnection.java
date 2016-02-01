package ru.prog_matik.java.pregis.signet.connection;

import org.apache.ws.security.util.Base64;
import ru.CryptoPro.JCP.JCP;

import javax.net.ssl.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;
import java.security.KeyStore;

public class StunnelConnection extends AbstractConnection {
    /** Stunnel Settings */
    private String stunnelCert;
    private String stunnnelKeyPass;

    public StunnelConnection(String server, int port) {
        this.server = server;
        this.port = port;
        this.connector = stunnelConnector;
    }
    /**
     * Set the certificate to use when connecting to stunnel
     *
     * @param path
     *            - Path to the certificate
     */
    public void setStunnelCert(String path) {
        stunnelCert = path;
    }

    /**
     * Password to open the stunnel key
     *
     * @param pass
     */
    public void setStunnelKey(String pass) {
        stunnnelKeyPass = pass;
    }

    /**
     * Connects to the server(via stunnel) in a new thread, so we can interrupt it if we want to
     * cancel the connection
     */

    private Thread stunnelConnector = new Thread(new Runnable() {
        @Override
        public void run() {
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
//                kmf = KeyManagerFactory.getInstance("GostX509");
                kmf.init(keyStore, "123456".toCharArray());
                context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            } catch (Exception e) {
                e.printStackTrace();
                notifyHandlersOfError(e);
                return;
            }

            SSLSocketFactory socketFactory = context.getSocketFactory();
            try {

//                Original
                SocketChannel channel = SocketChannel.open();
                channel.connect(new InetSocketAddress(server, port));
                sock = socketFactory.createSocket(channel.socket(), server, port, true);
                out_stream = sock.getOutputStream();
                in_stream = sock.getInputStream();
                connected = true;
                notifyHandlers(STATE.CONNECTED);
            } catch (ClosedByInterruptException e) {
                // Thread interrupted during connect.
            } catch (Exception e) {
                e.printStackTrace();
                notifyHandlersOfError(e);
            }
            System.out.println("Обработчик завершил работу");
        }
    });

    //TODO: override disconnect/other methods to make sure the tunnel is closed/cleaned up nicely


}