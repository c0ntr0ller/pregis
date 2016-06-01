package ru.progmatik.java.web.servlets.socket;

import ru.progmatik.java.pregis.ProgramAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ClientService {
    private Set<ClientWebSocket> webSockets;
    private ProgramAction action;
    private final List<String> dataList = new ArrayList<>();

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
        dataList.add(data);
    }

    public void setProgramAction(ProgramAction action) {
        this.action = action;
    }

    public void callCommands(String command) {

        if (action.isRunning()) {
            sendMessage("Уже выполнятся другая операция!");
        } else {
            switch (command) {
                case "getSenderID":
                    action.getSenderID();
                    break;
                case "getHouseUO":
                    action.callExportHouseData();
                    break;
                case "getExportPaymentDocumentData":
                    action.callExportPaymentDocumentData();
                    break;
                case "getNsiList":
                    action.callExportNsiList();
                    break;
                case "getNsiItem":
                    action.callExportNsiItem();
                    break;
                case "getExportDataProviderNsiItem":
                    action.callExportDataProviderNsiItem();
                    break;
                case "getExportAccountData":
                    action.callExportAccountData();
                    break;
                default:
                    sendMessage("Неизвестная команда: " + command);
                    action.setStateRunOff(); // Откл. бл. кнопки.
                    break;
            }
        }
    }


    /**
     * Метод, добавляет сообщение в очередь.
     * Если какой-то метод выполняется, а браузер закрыт, то после открытия отобразиться весь лог. (или после перезагрузки страницы)
     *
     * @param message сообщение.
     */
//    public void addMessage(String message) {
//        sendMessage(message);
//    }

    /**
     * Метод, отправляет все сообщения находящиееся в списке.
     */
    public void sendListMessages(ClientWebSocket webSocket) {

        for (String data : dataList) {
            webSocket.sendString(data);
        }
    }

    public void dropDataList() {
        dataList.clear();
    }

    public void add(ClientWebSocket webSocket) {
        webSockets.add(webSocket);
    }

    public void remove(ClientWebSocket webSocket) {
        webSockets.remove(webSocket);
    }

}
