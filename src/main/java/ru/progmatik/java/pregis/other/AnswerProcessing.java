package ru.progmatik.java.pregis.other;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.ErrorMessageType;
import ru.gosuslugi.dom.schema.integration.base.HeaderType;
import ru.progmatik.java.pregis.connectiondb.SaveToBaseMessages;
import ru.progmatik.java.web.servlets.socket.ClientService;

/**
 * Класс, служит передатчиком информации клиетам, в лог файл, в БД.
 * Обработывает типичные операций для всех запросов.
 * Например: вывод ошибки, сохранение в лог и т.д и т.п..
 */
public class AnswerProcessing {

    private final SaveToBaseMessages saveToBase;
    private final ClientService clientService;

//    public AnswerProcessing() {
//
//    }

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
    public void sendServerErrorToClient(String nameMethod, HeaderType headerRequest, Logger logger, Exception fault) {
        clientService.sendMessage(TextForLog.ERROR_RESPONSE);
        clientService.sendMessage(TextForLog.ERROR_DESCRIPTION + fault.getMessage());
        clientService.sendMessage(nameMethod + ": " + TextForLog.ERROR_ABORT);
        saveToBase.setRequestError(headerRequest, nameMethod, fault);
        logger.error(fault);
        fault.printStackTrace();
    }

    /**
     * Метод, обрабатывает ошибки, которые вызываем сами.
     * Например: если не указаны обязательные атрибуты.
     * @param logger сохранение в лог.
     * @param exception ошибка.
     */
//    public void sendErrorToClient(ClientService clientService, Logger logger, PreGISException exception) {
    public void sendErrorToClient(String methodName, Logger logger, Exception exception) {
        clientService.sendMessage("::setFailed()");
        clientService.sendMessage("Возникла ошибка!\nОперация прервана!\n" +
                "Текст ошибки: " + exception.getMessage());
        clientService.sendMessage("::setFailed()");
        logger.error(methodName, exception);
        exception.printStackTrace();
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
            clientService.sendMessage(TextForLog.ERROR_MESSAGE + nameMethod);
            clientService.sendMessage(TextForLog.ERROR_CODE + errorMessage.getErrorCode());
            clientService.sendMessage(TextForLog.ERROR_DESCRIPTION + errorMessage.getDescription());
            logger.error("ExportOrgRegistry: " + errorMessage.getErrorCode() + "\n" +
                    errorMessage.getDescription()  + "\n" + errorMessage.getStackTrace());
        } else {
            clientService.sendMessage(TextForLog.DONE_RESPONSE + nameMethod);
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
        clientService.sendMessage(message);
    }
}
