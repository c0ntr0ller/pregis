package ru.progmatik.java.pregis.connectiondb.localdb.message;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.ErrorMessageType;
import ru.gosuslugi.dom.schema.integration.base.HeaderType;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.other.ResourcesUtil;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Класс добавляет данные в базу данных, что-то похожее на лог, для хранения всех запросов и ответов.
 */
public class SaveToBaseMessages {

    private static final Logger LOGGER = Logger.getLogger(SaveToBaseMessages.class);
    private static final String TMP_FOLDER = "temp";
    private static final String SAVE_FOLDER = "exchange";
    private static final String FILE_OUTBOUND = "outbound.xml";
    private static final String FILE_INBOUND = "inbound.xml";


    private MessageDAO messageDAO;
    private String stateMessage = "OK";


    public SaveToBaseMessages() {
        try {
            messageDAO = new MessageDAO();
        } catch (SQLException e) {
            LOGGER.error("Не удалось создать MessageDAO", e);
            e.printStackTrace();
        }
    }

    /**
     * Метод получает параметры "Запроса" и добавляет их в базу данных.
     * @param headerRequest - заголовок запроса (содержит дату и идентификатор сообщения).
     * @param nameMethod - имя метода.
     */
    public void setRequest(HeaderType headerRequest, String nameMethod) {

        String typeOperation = "Request";
        ResourcesUtil.instance().createFolder(TMP_FOLDER);
        ResourcesUtil.instance().createFolder(SAVE_FOLDER);
        File file = new File(TMP_FOLDER + File.separator + FILE_OUTBOUND);
        Timestamp timestamp = Timestamp.valueOf(getDate(headerRequest));
        String newFileName = SAVE_FOLDER + File.separator + nameMethod + "_" + headerRequest.getMessageGUID() + "_" + typeOperation + ".xml";

//        try {

        file.renameTo(new File(newFileName));
        setMessageToBase(headerRequest.getMessageGUID(), nameMethod, timestamp, typeOperation, null, stateMessage);
            //file.deleteOnExit();
//        } catch (IOException e) {
//            LOGGER.error(e.getMessage());
////            e.printStackTrace();
//        }
////        finally {
////            file.delete();
////        }
    }

    /**
     * Метод получает параметры "Ответа" и добавляет их в базу данных.
     * @param headerRequest - заголовок ответа (содержит дату и идентификатор сообщения).
     * @param errorMessage - получает состояние сообщения об ошибки (если имеется).
     * @param nameMethod - имя метода.
     */
    public void setResult(HeaderType headerRequest, String nameMethod, ErrorMessageType errorMessage) {

        String typeOperation = "Result";
        ResourcesUtil.instance().createFolder(TMP_FOLDER);
        ResourcesUtil.instance().createFolder(SAVE_FOLDER);
        File file = new File(TMP_FOLDER + File.separator + FILE_INBOUND);
        Timestamp timestamp = Timestamp.valueOf(getDate(headerRequest));

        String newFileName = SAVE_FOLDER + File.separator + nameMethod + "_" + headerRequest.getMessageGUID() + "_" + typeOperation + ".xml";

        StringBuilder stateBuilder = new StringBuilder("OK");

//        if (errorMessage != null) {
//            stateBuilder.setLength(0);
//            stateBuilder.append(errorMessage.getErrorCode());
//            stateBuilder.append("\n");
//            stateBuilder.append(errorMessage.getDescription());
//            stateBuilder.append("\n");
//            stateBuilder.append(errorMessage.getStackTrace());
////            LOGGER.error(stateBuilder.toString());
//        }

//        try {
        file.renameTo(new File(newFileName));
        setMessageToBase(headerRequest.getMessageGUID(), nameMethod, timestamp, typeOperation, null, stateBuilder.toString());
//            file.deleteOnExit();
//        } catch (IOException e) {
//            LOGGER.error(e.getMessage());
//            e.printStackTrace();
//        }
//        finally {
//            file.delete();
//        }
    }

    /**
     * Метод необходимо вызывать в случаях возникновения экстренных ошибок.
     * Добавлять в исключения try-catch.
     * @param headerRequest - заголовок запроса (содержит дату и идентификатор сообщения).
     * @param nameMethod - имя метода.
     * @param fault - объект ошибки веб-сервиса, от куда можно извлечь ошибки.
     */
    public void setRequestError(HeaderType headerRequest, String nameMethod, Exception fault) {

        StringBuilder stateBuilder = new StringBuilder("OK");

        stateBuilder.setLength(0);
        stateBuilder.append("Ошибка!");
        stateBuilder.append("\n");
        stateBuilder.append(fault.getMessage());
        stateBuilder.append("\n");
        StackTraceElement[] traceElements = fault.getStackTrace();

        for (int i = 0; i < traceElements.length; i++) {
            stateBuilder.append(traceElements[i].toString());
            stateBuilder.append("\n");
        }

        stateMessage = stateBuilder.toString();

        setRequest(headerRequest, nameMethod);

        stateMessage = "OK";
    }

    /**
     * Метод передаёт данные с помощью процедуры в БД.
     * @param guid - идентификатор сообщения.
     * @param nameMethod - имя метода.
     * @param dateMessage - дата и время сообщения (указанная в заголовке сообщения). Возможны расхождения из-за часовых поясов.
     * @param nameTypeEn - имя статуса сообщения на английском ("Запрос" или "Ответ").
     * @param soapMessage - сообщение целиком.
     * @param stateMessage - статус сообщения (возможно ошибки).
     */
    private void setMessageToBase(String guid,
                                  String nameMethod,
                                  Timestamp dateMessage,
                                  String nameTypeEn,
                                  java.io.InputStream soapMessage,
                                  String stateMessage) {

        try (Connection connection = ConnectionDB.instance().getConnectionDB()) {
            messageDAO.setTableContent(guid, nameMethod, dateMessage, nameTypeEn, soapMessage, stateMessage, connection);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
//            e.printStackTrace();
        }
    }

    /**
     * Метод возвращает в нужном формате дату и время полученную из заголовка сообщения.
     * @param header - заголовок сообщения.
     * @return String дату и время в нужном формате.
     */
    private String getDate(HeaderType header) {

        XMLGregorianCalendar date = header.getDate();

        return String.format("%s-%s-%s %s:%s:%s", date.getYear(), date.getMonth(), date.getDay(), date.getHour(), date.getMinute(), date.getSecond());
    }

}
