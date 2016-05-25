package ru.progmatik.java.web.servlets.socket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;


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
        clientService.sendListMessages(this);
    }

    @OnWebSocketMessage
    public void onMessage(String data) {

        clientService.sendMessage(data);
        clientService.callCommands(data);  // убрать
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
    }

    public void sendString(String data) {
        try {
            session.getRemote().sendString(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
