package ru.progmatik.java.pregis.connectiondb;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.ErrorMessageType;
import ru.gosuslugi.dom.schema.integration.base.HeaderType;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

/**
 * Класс добавляет данные в базу данных, что-то похожее на лог, для хранения всех запросов и ответов.
 */
public class SaveToBaseMessages {

    private Logger logger = Logger.getLogger(SaveToBaseMessages.class);

    private Connection connection;
    private String stateMessage = "OK";

    /**
     * Метод получает параметры "Запроса" и добавляет их в базу данных.
     * @param headerRequest - заголовок запроса (содержит дату и идентификатор сообщения).
     * @param nameMethod - имя метода.
     */
    public void setRequest(HeaderType headerRequest, String nameMethod) {

        String typeOperation = "Request";
        File file = new File("temp" + File.separator + "outbound");
        Timestamp timestamp = Timestamp.valueOf(getDate(headerRequest));

        try (FileInputStream inputStream = new FileInputStream(file)) {
            setMessageToBase(headerRequest.getMessageGUID(), nameMethod, timestamp, typeOperation, inputStream, stateMessage);
            file.deleteOnExit();
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Метод получает параметры "Ответа" и добавляет их в базу данных.
     * @param headerRequest - заголовок ответа (содержит дату и идентификатор сообщения).
     * @param errorMessage - получает состояние сообщения об ошибки (если имеется).
     * @param nameMethod - имя метода.
     */
    public void setResult(HeaderType headerRequest, String nameMethod, ErrorMessageType errorMessage) {

        String typeOperation = "Result";

        File file = new File("temp" + File.separator + "inbound");
        Timestamp timestamp = Timestamp.valueOf(getDate(headerRequest));

        StringBuilder stateBuilder = new StringBuilder("OK");

        if (errorMessage != null) {
            stateBuilder.setLength(0);
            stateBuilder.append(errorMessage.getErrorCode());
            stateBuilder.append("\n");
            stateBuilder.append(errorMessage.getDescription());
            stateBuilder.append("\n");
            stateBuilder.append(errorMessage.getStackTrace());
            logger.error(stateBuilder.toString());
        }

        try (FileInputStream inputStream = new FileInputStream(file)) {
            setMessageToBase(headerRequest.getMessageGUID(), nameMethod, timestamp, typeOperation, inputStream, stateBuilder.toString());
            file.deleteOnExit();
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
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

        try {

            if (connection == null || connection.isClosed()) {
                connection = ConnectionDB.getConnectionDB();
            }

            CallableStatement cs = connection.prepareCall( "{CALL SET_MESSAGE(?, ?, ?, ?, ?, ?)}" );

            cs.setString(1, guid);
            cs.setString(2, nameMethod);
            cs.setTimestamp(3, dateMessage);
            cs.setString(4, nameTypeEn);
            cs.setBlob(5, soapMessage);
            cs.setString(6, stateMessage);

            int isResultSet = cs.executeUpdate();
            logger.info("setMessageToBase: CallableStatement.executeUpdate() - " + isResultSet);

            if (!cs.isClosed()) cs.close();
            if (connection != null || !connection.isClosed()) {
                connection.close();
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
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
