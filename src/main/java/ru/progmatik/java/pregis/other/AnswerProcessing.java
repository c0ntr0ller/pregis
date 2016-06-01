package ru.progmatik.java.pregis.other;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.ErrorMessageType;
import ru.gosuslugi.dom.schema.integration.base.HeaderType;
import ru.progmatik.java.pregis.connectiondb.SaveToBaseMessages;
import ru.progmatik.java.web.servlets.socket.ClientService;

/**
 * Класс, служит для обработки типичных операций для всех запросов.
 * Например: вывод ошибки, сохранение в лог и т.д и т.п..
 */
public class AnswerProcessing {

    private final SaveToBaseMessages saveToBase;

    public AnswerProcessing() {
        saveToBase = new SaveToBaseMessages();
    }

    /**
     * Метод, обрабатывает ошибки отправленные сервером ГИС ЖКХ.
     * @param nameMethod имя метода для логирования.
     * @param headerRequest заголовок исходящего сообщения.
     * @param clientService вывод пользователю.
     * @param logger сохранение в лог.
     * @param fault ошибка полученная от сервера.
     */
    public void sendServerErrorToClient(String nameMethod, HeaderType headerRequest, ClientService clientService, Logger logger, Exception fault) {
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
     * @param clientService вывод пользователю.
     * @param logger сохранение в лог.
     * @param exception ошибка.
     */
//    public void sendClientErrorToClient(ClientService clientService, Logger logger, PreGISException exception) {
    public void sendClientErrorToClient(ClientService clientService, Logger logger, Exception exception) {
        clientService.sendMessage(exception.getMessage());
        logger.error(exception);
        exception.printStackTrace();
    }

    /**
     * Метод, сохраняет полученное сообщение от сервера в базу данных и если содержаться в самом теле сообщения ошибка,
     * будет выведена пользователю.
     * @param nameMethod имя метода.
     * @param headerRequest заголовок исходящего сообщения.
     * @param headerResponse заголовок входящего сообщения.
     * @param errorMessage ошибка из тела сообщения.
     * @param clientService вывод сообщения пользователю.
     * @param logger лог для сохранения ошибок.
     */
    public void sendToBaseAndAnotherError(String nameMethod, HeaderType headerRequest, HeaderType headerResponse,
                                          ErrorMessageType errorMessage, ClientService clientService, Logger logger) {

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
}
