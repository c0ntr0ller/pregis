package ru.progmatik.java.web.servlets.socket;

import ru.progmatik.java.pregis.ProgramAction;
import ru.progmatik.java.web.servlets.web.ErrorPage;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ClientService {
    private Set<ClientWebSocket> webSockets;
    private ProgramAction action;
//    private final List<String> dataList = new ArrayList<>();

    public ClientService() {
        this.webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    public void sendMessage(String data) {

        for (ClientWebSocket user : webSockets) {
            try {
                user.sendString(data);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void setProgramAction(ProgramAction action) {
        this.action = action;
    }

    public void callCommanans(String command) {

        switch (command) {
            case "getSenderID":
                if (!action.isStateRun()) {
                    action.getSenderID();
                }
                break;
            case "getHouseUO":
                if (!action.isStateRun())
                    action.callExportHouseData();
                break;
            case "getHouse":
                if (!action.isStateRun())
                    action.callExportHouseData();
                break;
            default:
                new ErrorPage("Неизвестная команда", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                break;
        }
    }


    /**
     * Метод, добавляет сообщение в очередь.
     *
     * @param message сообщение.
     */
    public void addMessage(String message) {

    }

    public void add(ClientWebSocket webSocket) {
        webSockets.add(webSocket);
    }

    public void remove(ClientWebSocket webSocket) {
        webSockets.remove(webSocket);
    }

}
