package ru.progmatik.java.pregis;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.CryptoPro.JCP.JCP;
import ru.progmatik.java.pregis.connectiondb.localdb.organization.BaseOrganization;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;
import ru.progmatik.java.pregis.signet.Configure;
import ru.progmatik.java.pregis.signet.bcsign.security.XmlDSignTools;
import ru.progmatik.java.web.servlets.socket.WebSocketClientServlet;
import ru.progmatik.java.web.servlets.web.LoginClient;
import ru.progmatik.java.web.servlets.web.MainServlet;
import ru.progmatik.java.web.servlets.web.IndexServlet;

import java.security.Security;

public class Main {

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


//        Инициализация сертификатов и Крипто-ПРО

        System.setProperty("javax.net.ssl.supportGVO", "false");

        System.setProperty("javax.net.debug", "all");

        System.setProperty("com.sun.security.enableCRLDP", "true");
        System.setProperty("com.ibm.security.enableCRLDP", "true");
        System.setProperty("javax.net.ssl.keyStoreType", JCP.HD_STORE_NAME);
        System.setProperty("javax.net.ssl.keyStoreProvider", JCP.PROVIDER_NAME);
        System.setProperty("javax.net.ssl.keyStorePassword", String.valueOf(Configure.getKeyStorePassword()));
        System.setProperty("javax.net.ssl.trustStoreType", JCP.CERT_STORE_NAME);
        System.setProperty("javax.net.ssl.trustStore", Configure.getTrustStorePath());
        System.setProperty("javax.net.ssl.trustStorePassword", String.valueOf(Configure.getTrustStorePassword()));


        Security.addProvider(new BouncyCastleProvider());
        XmlDSignTools.init("BC");
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
//        new ProgramAction().callExportOrgRegistry();

        WebSocketClientServlet webSocketClientServlet = new WebSocketClientServlet();
        ProgramAction action = new ProgramAction(webSocketClientServlet.getClientService());

        if (BaseOrganization.getSenderID() == null)
            action.getSenderID();  // Получение SenderID

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new LoginClient(action)), "/login");
        context.addServlet(new ServletHolder(new MainServlet(action)), "/test");
        context.addServlet(new ServletHolder(new IndexServlet(action)), "/main");
        context.addServlet(new ServletHolder(webSocketClientServlet), "/websocket");
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

//        new ProgramAction().removeSenderID();  // Упразднен
//        new ProgramAction().callExportDataProvider(); // Просто запрос организации
//        new ProgramAction().callExportNsiList(); // 1
//        new ProgramAction().callExportNsiItem(); // 2
//        new ProgramAction().callExportDataProviderNsiItem(); //экспортирует справочники №1, 51, 59
//        new ProgramAction().callExportAccountData(); // экспорт сведений о лицевых счетах.
//        new ProgramAction().callImportPaymentDocumentData(); // импорт сведений о платежных документах.
//        new ProgramAction().callExportPaymentDocumentData(); // экспорт сведений о платежных документах.

//        Асинхронный вызов
//        new ProgramAction().callExportPaymentDocumentDetails(); // экспорт реквизитов платежных документов для банков и т.п. контор.
//        new ProgramAction().getStateExportPaymentDocumentDetails(); // экспорт реквизитов платежных документов для банков и т.п. контор.

//        new ProgramAction().callExportStatusCAChData();
//        new ProgramAction().callExportCAChData();

    }
}
