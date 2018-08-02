package ru.progmatik.java.web.servlets.socket;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;


@WebServlet(name = "WebSocketClientServlet", urlPatterns = {"/websocket"})
public class WebSocketClientServlet extends WebSocketServlet {
    private final static int LOGOUT_TIME = 15 * 60 * 1000;
    private final ClientService clientService;

    public WebSocketClientServlet() {
        this.clientService = new ClientService();
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator((req, resp) -> new ClientWebSocket(clientService));
    }

    public ClientService getClientService() {
        return clientService;
    }
}
