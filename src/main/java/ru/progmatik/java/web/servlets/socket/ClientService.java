package ru.progmatik.java.web.servlets.socket;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.ProgramAction;
import ru.progmatik.java.web.servlets.listener.ClientDialogWindowListener;
import ru.progmatik.java.web.servlets.listener.ClientDialogWindowObservable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ClientService {

    private static final Logger LOGGER = Logger.getLogger(ClientService.class);
    private final List<String> dataList = new ArrayList<>();
    private final ClientDialogWindowListener listener = new ClientDialogWindowListener();
    private Set<ClientWebSocket> webSockets;
    private ProgramAction action;
    private Timer timer;

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

    void callCommands(String requestFromClient) {

        Gson gson = new Gson();
        ObjectForJSON json = gson.fromJson(requestFromClient, ObjectForJSON.class);
        String command = json.getCommand();
        String value = json.getValue();

        if (LOGGER.isDebugEnabled()) {
            String answerToClient = "\nПолучена команда: " + command;
            if (!value.isEmpty()) {
                answerToClient += " значение: " + value + "\n";
            } else {
                answerToClient += "\n";
            }
            sendMessage(answerToClient);
        }

        if (action.isRunning()) {
            sendMessage("Уже выполнятся другая операция!");
        } else {
            switch (command) {
                case "getOrgPPAGUID":
                    action.getOrgPPAGUID();
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
                    action.callExportNsiItem(value);
                    break;
                case "getExportDataProviderNsiItem":
                    action.callExportDataProviderNsiItem();
                    break;
                case "getExportAccountData":
                    action.callExportAccountData();
                    break;
                case "updateAccountData":
                    action.updateAccountData();
                    break;
                case "updateMeteringDevices":
                    action.updateMeteringDevices();
                    break;
                case "callQuestion":
                    setQuestion(Boolean.valueOf(value));
                    break;
                case "getExportMeteringHistory":
                    action.getExportMeteringDeviceHistory();
                    break;
                case "callExportSupplyResourceContractData":
                    action.callExportSupplyResourceContractData();
                    break;
                default:
                    sendMessage("Неизвестная команда: " + command);
                    action.setStateRunOff(); // Откл. бл. кнопки.
                    break;
            }
        }
    }


    /**
     * Метод, отправляет все сообщения находящиееся в списке.
     */
    void sendListMessages(ClientWebSocket webSocket) {

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

    /**
     * Метод, добавляет наблюдателей к кому нужно послать ответ.
     * Если в течении 3 минут ответа нет, то окно у клиента уйдет, а операция отменится.
     *
     * @param observable наблюдатель.
     */
    public void addListener(ClientDialogWindowObservable observable) {
        if (timer == null) {
            timer = new Timer();
            listener.addListener(observable);
            timer.schedule(new TimerTask() {
                @Override
                public void run() { // заводим таймер на 3 минуты
                    closeQuestionWindowClient();
                    setQuestion(false);
                }
            }, 1000 * 180);
        }
    }


    public void checkSession() {
        for (ClientWebSocket user : webSockets) {
            user.checkAccount();
        }
    }

    private void setQuestion(boolean question) {
        timer.cancel();
        timer = null;
        listener.sendAnswer(question);
        removeAllListener();

    }

    /**
     * Метод, удаляет наблюдателя из списка.
     */
    private void removeAllListener() {
        listener.removeAllListener();
    }

    /**
     * Метод, закрывает окно с вопросом у клиента.
     */
    private void closeQuestionWindowClient() {
        sendMessage("::closeModalWindow()");
    }

}
