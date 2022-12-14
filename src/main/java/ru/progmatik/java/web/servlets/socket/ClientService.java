package ru.progmatik.java.web.servlets.socket;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.ProgramAction;
import ru.progmatik.java.web.servlets.listener.ClientDialogWindowListener;
import ru.progmatik.java.web.servlets.listener.ClientDialogWindowObservable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class ClientService {

    private static final Logger LOGGER = Logger.getLogger(ClientService.class);
    private static final String CLIENT_SHOW_MESSAGE = "::showMessage()";
    private final List<String> dataList = new ArrayList<>();
    private final ClientDialogWindowListener listener = new ClientDialogWindowListener();
    private final Set<ClientWebSocket> webSockets;
    private ProgramAction action;
    private Timer timer;

    public ClientService() {
        this.webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    public void sendMessage(final String data) {

        for (ClientWebSocket user : webSockets) {
            try {
                user.sendString(data);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        // уменьшаем размер хранимого лога, ибо долго открывается. Цифра 3000 - пока от балды!
        if(dataList.size() > 3000){
            for (int i = 0; i < 1000; i++) {
                dataList.remove(i);
            }

        }
        dataList.add(data);
    }

    public void setProgramAction(final ProgramAction action) {
        this.action = action;
    }

    void callCommands(final String requestFromClient) {

        final Gson gson = new Gson();
        final ObjectForJSON json = gson.fromJson(requestFromClient, ObjectForJSON.class);
        final String command = json.getCommand();
        final String value = json.getValue();

        if (LOGGER.isDebugEnabled()) {
            String answerToClient = "\nПолучена команда: " + command;
            if (!value.isEmpty()) {
                answerToClient += " значение: " + value + "\n";
            } else {
                answerToClient += "\n";
            }
            action.getAnswerProcessing().sendMessageToClient(answerToClient);
        }

        if (action.isRunning()) {
            action.getAnswerProcessing().sendErrorToClientNotException("Уже выполняется другая операция!");
        } else {
            switch (command) {
                case "getOrgName":
                    action.getOrgName();
                    break;
                case "getOrgPPAGUID":
                    action.getOrgPPAGUID();
                    break;
                case "getHouseUO":
                    action.callExportHouseData(value);
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
                    action.callExportAccountData(value);
                    break;
                case "updateAccountData":
                    action.updateAccountData(value);
                    break;
                case "updateMeteringDevices":
                    action.updateMeteringDevices(value);
                    break;
                case "getExportMeteringHistory":
                    action.getExportMeteringDeviceHistory(value);
                    break;
                case "deleteAllMeteringDevicesInHouse":
                    action.deleteAllMeteringDevicesInHouse(value);
                    break;
                case "callImportPaymentDocumentData":
                    action.callImportPaymentDocumentData(value);
                    break;
                case "callQuestion":
                    setQuestion(Boolean.valueOf(value));
                    break;
                case "callExportSupplyResourceContractData":
                    action.callExportSupplyResourceContractData();
                    break;
                case "callImportPayments":
                    action.callImportPayments(value);
                    break;
                case "getHouseAddedGisJkh":
                    action.getHouseAddedGisJkh();
                    break;
//                case "getOrgPeriodData":
//                    action.callExportOrgPeriodData();
//                    break;
                default:
                    action.getAnswerProcessing().sendErrorToClientNotException("Неизвестная команда: " + command);
                    action.setStateRunOff(); // Откл. бл. кнопки.
                    break;
            }
        }
    }


    /**
     * Метод, отправляет все сообщения находящиееся в списке.
     */
    void sendListMessages(final ClientWebSocket webSocket) {

        for (String data : dataList) {
            webSocket.sendString(data);
        }
    }

    public void dropDataList() {
        dataList.clear();
    }

    public void add(final ClientWebSocket webSocket) {
        webSockets.add(webSocket);
    }

    public void remove(final ClientWebSocket webSocket) {
        webSockets.remove(webSocket);
    }

    /**
     * Метод, добавляет наблюдателей к кому нужно послать ответ.
     * Если в течении 3 минут ответа нет, то окно у клиента уйдет, а операция отменится.
     *
     * @param observable наблюдатель.
     */
    public void addListener(final ClientDialogWindowObservable observable) {
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

    private void setQuestion(final boolean question) {
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
        action.getAnswerProcessing().closeQuestionWindowClient();
    }
}
