package ru.progmatik.java.pregis.other;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.ErrorMessageType;
import ru.gosuslugi.dom.schema.integration.base.HeaderType;
import ru.progmatik.java.pregis.connectiondb.localdb.message.SaveToBaseMessages;
import ru.progmatik.java.web.servlets.listener.ClientDialogWindowObservable;
import ru.progmatik.java.web.servlets.socket.ClientService;
import ru.progmatik.java.web.servlets.socket.ObjectForJSON;
import ru.progmatik.java.web.servlets.socket.ValueJSON;

import java.util.ArrayList;

/**
 * Класс, служит передатчиком информации клиетам, в лог файл, в БД.
 * Обработывает типичные операций для всех запросов.
 * Например: вывод ошибки, сохранение в лог и т.д и т.п..
 */
public final class AnswerProcessing {

    private static final String CLIENT_SET_FAILED = "::setFailed()";
    private static final String CLIENT_SHOW_MODAL_WINDOW = "::showModalWindow()";
    private static final String CLIENT_CLOSE_MODAL_WINDOW = "::closeModalWindow()";
    private static final String CLIENT_SET_OK_LABEL_TEXT = "::setOkLabelText()";
    private static final String CLIENT_CLEAR_LABEL_TEXT = "::clearLabelText()";
    private static final String CLIENT_SET_BUTTON_STATE_FALSE = "::setButtonState(false)";
    private static final String CLIENT_SET_BUTTON_STATE_TRUE = "::setButtonState(true)";
    private static final String CLIENT_SHOW_MESSAGE = "::showMessage()";
    private final SaveToBaseMessages saveToBase;
    private final ClientService clientService;

    public AnswerProcessing(ClientService clientService) {
        saveToBase = new SaveToBaseMessages();
        this.clientService = clientService;
    }

    /**
     * Метод, обрабатывает ошибки, полученные от сервера ГИС ЖКХ.
     * @param nameMethod имя метода для логирования.
     * @param headerRequest заголовок исходящего сообщения.
     * @param logger сохранение в лог.
     * @param fault ошибка полученная от сервера.
     */
    public void sendServerErrorToClient(final String nameMethod, final HeaderType headerRequest,
                                        final Logger logger, final Exception fault) {

        sendCommandToClient(CLIENT_SET_FAILED);
        sendMessageToClient(TextForLog.ERROR_RESPONSE);
        sendMessageToClient(TextForLog.ERROR_DESCRIPTION + fault.getMessage());
        sendMessageToClient(nameMethod + ": " + TextForLog.ERROR_ABORT);
        saveToBase.setRequestError(headerRequest, nameMethod, fault);
        logger.error(fault);
    }

    /**
     * Метод, обрабатывает ошибки, которые вызываем сами.
     * Например: если не указаны обязательные атрибуты.
     * @param logger сохранение в лог.
     * @param exception ошибка.
     */
    public void sendErrorToClient(final String methodName, final String nameOperation,
                                  final Logger logger, final Exception exception) {

        sendCommandToClient(CLIENT_SET_FAILED);
        sendMessageToClient("Возникла ошибка!\nОперация " + nameOperation + "прервана!");
        sendMessageToClient("Текст ошибки: " + exception.getMessage());
        sendMessageToClient("Возникли ошибки! Подробнее смотрите в логе событий.");
        logger.error(methodName, exception);
    }

    /**
     * Метод, обрабатывает ошибки, отправляет клиенту и в лог.
     * @param message текст сообщения.
     * @param logger лог.
     */
    public void sendInformationToClientAndLog(final String message, final Logger logger) {

        sendErrorToClientNotException(message);
        logger.info(message);
    }

    /**
     * Метод, просто передаёт пользователю ошибку и сообщение ошибки.
     * @param message сообщение ошибки.
     */
    public void sendErrorToClientNotException(final String message) {
        sendCommandToClient(CLIENT_SET_FAILED);
        sendMessageToClient(message);
    }

    /**
     * Метод, отправляет клиенту сообщение об успешном выполнении операции.
     * @param message сообщение.
     */
    public void sendOkMessageToClient(String message) {
        sendCommandToClient(CLIENT_SET_OK_LABEL_TEXT);
        sendMessageToClient(message);
    }

    /**
     * Метод, сохраняет полученное сообщение от сервера в базу данных и если содержаться в самом теле сообщения ошибка,
     * будет выведена пользователю.
     * @param nameMethod имя метода.
     * @param headerRequest заголовок исходящего сообщения.
     * @param headerResponse заголовок входящего сообщения.
     * @param errorMessage ошибка из тела сообщения.
     * @param logger лог для сохранения ошибок.
     */
    public void sendToBaseAndAnotherError(String nameMethod, HeaderType headerRequest, HeaderType headerResponse,
                                          ErrorMessageType errorMessage, Logger logger) {

        saveToBase.setRequest(headerRequest, nameMethod);

        saveToBase.setResult(headerResponse, nameMethod, errorMessage);

        if (errorMessage != null) {
            sendMessageToClient("");
            sendMessageToClient(TextForLog.ERROR_MESSAGE + nameMethod);
            sendMessageToClient(TextForLog.ERROR_CODE + errorMessage.getErrorCode());
            sendMessageToClient(TextForLog.ERROR_DESCRIPTION + errorMessage.getDescription());
            sendMessageToClient("Возникли ошибки! Подробнее смотрите в логе событий.");
            logger.error(nameMethod +": " + errorMessage.getErrorCode() + "\n" +
                    errorMessage.getDescription()  + "\n" + errorMessage.getStackTrace());
        } else {
            sendMessageToClient(TextForLog.DONE_RESPONSE + nameMethod);
            logger.info("Successful.");
        }
    }

    /**
     * Метод, отправляет сообщение клиенту.
     * Если будет клиентских приложений больше, в этот метод можно добавить дополнительные элементы для отправки сообщений.
     * Т.е. это один метод, который будет отправлять полученное сообщение всем клиентом, которые указаны в этом методе.
     * @param message сообщение для отправки клиентам.
     */
    public void sendMessageToClient(String message) {
        sendMessage(message);
    }

    /**
     * Метод, получает вопрос, который адресуется клиенту, если клиент ответил, то вернется true или false.
     * @param question сообщение.
     */
    public void showQuestionToClient(String question, ClientDialogWindowObservable observable) {
        clientService.addListener(observable);
        clientService.sendMessage(messageFormatter(CLIENT_SHOW_MODAL_WINDOW, question));
    }

    /**
     * Метод, закрывает окно с вопросом у клиента.
     */
    public void closeQuestionWindowClient() {
        sendCommandToClient(CLIENT_CLOSE_MODAL_WINDOW);
    }

    /**
     * Метод, отправляет клиенту в веб-интерфейс сообщение об очистки, логотипа в окошке
     * обработки событий.
     */
    public void clearLabelForText() {
        sendCommandToClient(CLIENT_CLEAR_LABEL_TEXT);
    }

    /**
     * Метод, формирет JSON и отправляет сообщение клиенту.
     * @param message сообщение для клиента.
     */
    private void sendMessage(final String message) {
        clientService.sendMessage(messageFormatter(CLIENT_SHOW_MESSAGE, message));
    }

    /**
     * Метод, формирует JSON и отправляет в UI пользователю команду.
     * @param command команда для UI.
     */
    private void sendCommandToClient(final String command) {
        clientService.sendMessage(messageFormatter(command, null));
    }

    /**
     * Метод, формирует JSON для передачи сообщения во front-end.
     * @param command имя команды
     * @param value вложенное сообщение если требуется.
     * @return готовый JSON для front-end/
     */
    private String messageFormatter(final String command, final String value) {

        Gson gson = new Gson();

        return gson.toJson(new ObjectForJSON(command, null, value));
    }

    /**
     * Метод, разблокирует кнопки в UI.
     */
    public void unlockButtons() {
        sendCommandToClient(CLIENT_SET_BUTTON_STATE_FALSE);
    }

    /**
     * Метод, блокирует кнопки в UI.
     */
    public void lockButtons() {
        sendCommandToClient(CLIENT_SET_BUTTON_STATE_TRUE);
    }

    /**
     * Метод, выводит список домов, для выбора в UI пользователю.
     * @param list список домов. value - ид дома в БД ГРАД, name - адрес дома для пользователя.
     */
    public void showHouseListModalWindow(ArrayList<ValueJSON> list) {
        Gson gson = new Gson();
        String listJSON = gson.toJson(list);
        clientService.sendMessage(messageFormatter("::showHouseListModalWindow()", listJSON));
    }


}
