package ru.progmatik.java.pregis;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
//import ru.CryptoPro.JCP.JCP;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;
import ru.progmatik.java.pregis.signet.Configure;
import ru.progmatik.java.pregis.signet.bcsign.security.XmlDSignTools;
import ru.progmatik.java.web.servlets.socket.WebSocketClientServlet;
import ru.progmatik.java.web.servlets.web.*;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.security.Security;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class);

    static {

        java.net.Authenticator.setDefault(new java.net.Authenticator() {

            @Override
            protected java.net.PasswordAuthentication getPasswordAuthentication() {
                return new java.net.PasswordAuthentication(OtherFormat.USER_NAME, OtherFormat.PASSWORD.toCharArray());
            }
        });
    }

    public static void main(String[] args) throws Exception {



//        Укажем XMLSignature формировать подпись без разделителей '\n'
        System.setProperty("org.apache.xml.security.ignoreLineBreaks", "true");

//        System.setProperty("javax.net.ssl.supportGVO", "false");

//        System.setProperty("javax.net.debug", "all");

        for (String arg : args) {
            if (arg.equalsIgnoreCase("debug") || Logger.getLogger("ru.progmatik").getLevel().equals(Level.DEBUG)) {
                Logger.getLogger("ru.progmatik").setLevel(Level.DEBUG);
                System.setProperty("javax.net.debug", "all");
//                System.setProperty("javax.net.debug", "ssl,handshake");
            }
        }

//        Инициализация сертификатов и Крипто-ПРО
//        System.setProperty("com.sun.security.enableCRLDP", "true");
//        System.setProperty("com.ibm.security.enableCRLDP", "true");
//        System.setProperty("javax.net.ssl.keyStoreType", JCP.HD_STORE_NAME);
//        System.setProperty("javax.net.ssl.keyStoreProvider", JCP.PROVIDER_NAME);
//        System.setProperty("javax.net.ssl.keyStorePassword", String.valueOf(Configure.getKeyStorePassword()));
//        System.setProperty("javax.net.ssl.trustStoreType", JCP.CERT_STORE_NAME);
//        System.setProperty("javax.net.ssl.trustStore", Configure.getTrustStorePath());
//        System.setProperty("javax.net.ssl.trustStorePassword", String.valueOf(Configure.getTrustStorePassword()));
//        System.setProperty("file.encoding", "utf-8");

        Security.addProvider(new BouncyCastleProvider());  // Добавим Bouncy Castle
        XmlDSignTools.init("BC"); // Инициализация алгоритма ГОСТ для подписи файлов

//        if (LOGGER.isDebugEnabled()) {
//            SSLServerSocketFactory factory =
//                    (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
//
//            SSLServerSocket sslSocket =
//                    (SSLServerSocket)factory.createServerSocket(5757);
//
//            String [] cipherSuites = sslSocket.getEnabledCipherSuites();
//
//            for (int i = 0; i < cipherSuites.length; i++) {
//                System.out.println("Cipher Suite " + i +
//                        " = " + cipherSuites[i]);
//            }
//        }

//
//        System.setProperty("https.protocols", "TLSv1");
//        System.setProperty("jdk.tls.client.protocols", "TLSv1");

        WebSocketClientServlet webSocketClientServlet = new WebSocketClientServlet();
        ProgramAction action = new ProgramAction(webSocketClientServlet.getClientService());

        if (OtherFormat.getOrgPPAGUID() == null)
            action.getOrgPPAGUID();  // Получение orgPPAGUID

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new LoginClient(action)), "/*");
        context.addServlet(new ServletHolder(new MainServlet(action)), "/test");
        context.addServlet(new ServletHolder(new IndexServlet(action)), "/main");
        context.addServlet(new ServletHolder(new UsersServlet(action)), "/users");
        context.addServlet(new ServletHolder(webSocketClientServlet), "/websocket");
        context.addServlet(new ServletHolder(new AjaxServlet()), "/ajax");
        context.getSessionHandler().getSessionManager().setMaxInactiveInterval(600); // Время сессии

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);  // в конце убрать
        resource_handler.setWelcomeFiles(new String[]{"/html/login.html"});

        resource_handler.setResourceBase("site");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        int port;
        try {
            port = ResourcesUtil.instance().getWebPort();
        } catch (PreGISException e) {
            port = 8080;
        }

        Server server = new Server(port);
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
