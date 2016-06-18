package ru.progmatik.java.web.servlets.socket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import ru.progmatik.java.web.accounts.ProfileSingleton;

import java.net.HttpCookie;
import java.util.List;


@SuppressWarnings("UnusedDeclaration")
@WebSocket
public class ClientWebSocket {
    private ClientService clientService;
    private Session session;

    public ClientWebSocket(ClientService clientService) {
        this.clientService = clientService;
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        clientService.add(this);
        this.session = session;
        clientService.sendListMessages(this); // если открыл страницу, уже выполняется какой-то запрос, получаем лог.
        getSessionID(session);
    }

    @OnWebSocketMessage
    public void onMessage(String data) {

//        clientService.sendMessage(data + "\n");  // убрать
        if (getSessionID(session)) {
            clientService.callCommands(data);
        }
//        int timePause = 5000;
//
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//
//            @Override
//            public void run() {
//                clientService.sendMessage(data);
//            }
//        }, timePause);

    }

    @OnWebSocketError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        clientService.remove(this);
        clientService.checkSession();
    }

    public void sendString(String data) {
        try {
            session.getRemote().sendString(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected boolean getSessionID(Session session) {
        List<HttpCookie> cookies = session.getUpgradeRequest().getCookies();
        for (HttpCookie cookie : cookies) {
            if ("JSESSIONID".equalsIgnoreCase(cookie.getName())) {
                if (ProfileSingleton.instance().getAccountService().getUserBySessionId(cookie.getValue()) == null) {
                    sendString("Вы не авторизированы!");
                    session.close(1008, "Unauthorized");
                    clientService.remove(this);
                    return false;
                }
            }
        }
        return true;
    }

    protected void checkAccount() {
        getSessionID(session);
    }
}
