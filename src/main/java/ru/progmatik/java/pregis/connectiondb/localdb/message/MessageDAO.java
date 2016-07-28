package ru.progmatik.java.pregis.connectiondb.localdb.message;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;

import javax.xml.soap.SOAPException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;

/**
 * Класс, сохраняет и получает в БД отправленные и полученные SOAP сообщения.
 */
public class MessageDAO {

    private static final Logger LOGGER = Logger.getLogger(MessageDAO.class);

    private static final String TABLE_NAME_OBMEN_OPERATION = "OBMEN_OPERATION";
    private static final String SQL_CREATE_TABLE_OBMEN_OPERATION = "CREATE TABLE IF NOT EXISTS OBMEN_OPERATION (" +
            "ID IDENTITY NOT NULL PRIMARY KEY, " +
            "SPR_TYPE_OPERATION_ID BIGINT NOT NULL, " +
            "SPR_NAME_METHOD_ID BIGINT NOT NULL, " +
            "GUID VARCHAR_IGNORECASE(40) NOT NULL, " +
            "DATE_MESSAGE TIMESTAMP, " +
            "SOAPMESSAGE BLOB, " +
            "STATE_MESSAGE VARCHAR_IGNORECASE(100000)); " +

            "COMMENT ON TABLE PUBLIC.OBMEN_OPERATION IS 'Таблица для хранения всей информации о данных передаваемых и получаемых.'; " +
            "COMMENT ON COLUMN OBMEN_OPERATION.ID IS 'Идентификатор записей.'; " +
            "COMMENT ON COLUMN OBMEN_OPERATION.SPR_TYPE_OPERATION_ID IS 'Сылка на таблицу SPR_TYPE_OPERATION.'; " +
            "COMMENT ON COLUMN OBMEN_OPERATION.SPR_NAME_METHOD_ID IS 'Сылка на таблицу SPR_NAME_METHOD.'; " +
            "COMMENT ON COLUMN OBMEN_OPERATION.GUID IS 'Индивидуальный идентификатор сообщения.'; " +
            "COMMENT ON COLUMN OBMEN_OPERATION.DATE_MESSAGE IS 'Дата и время сообщения.'; " +
            "COMMENT ON COLUMN OBMEN_OPERATION.SOAPMESSAGE IS 'Сообщение.'; " +
            "COMMENT ON COLUMN OBMEN_OPERATION.STATE_MESSAGE IS 'Статус сообщения. Отображает ошибки если имеются.'; " +

            "ALTER TABLE \"PUBLIC\".OBMEN_OPERATION ADD FOREIGN KEY (SPR_TYPE_OPERATION_ID) REFERENCES \"PUBLIC\".SPR_TYPE_OPERATION(ID); " +
            "ALTER TABLE \"PUBLIC\".OBMEN_OPERATION ADD FOREIGN KEY (SPR_NAME_METHOD_ID) REFERENCES \"PUBLIC\".SPR_NAME_METHOD(ID);";

    private final TypeOperationDAO typeOperationDAO;
    private final NameMethodDAO nameMethodDAO;

    /**
     * Конструктор, проверяет таблицы "OBMEN_OPERATION", "SPR_NAME_METHOD", "SPR_TYPE_OPERATION", если нет таблиц создаёт их.
     *
     * @throws SQLException
     */
    public MessageDAO() throws SQLException {

        typeOperationDAO = new TypeOperationDAO();
        nameMethodDAO = new NameMethodDAO();
        if (!ConnectionDB.instance().tableExist(TABLE_NAME_OBMEN_OPERATION.toUpperCase())) {
            ConnectionDB.instance().sendSqlRequest(SQL_CREATE_TABLE_OBMEN_OPERATION);
        }
    }

    /**
     * Метод добавляет данные в таблицы, добавление сообщения.
     *
     * @param connection   - Соединение с базой данных.
     * @param guid         - Индивидуальный идентификатор сообщения.
     * @param nameMethod   - Название метода для запроса, например "exportOrgRegistry".
     * @param dateMessage  - Дата и время сообщения.
     * @param nameTypeEn   - Тип сообщения "Запрос" или "Ответ".
     * @param soapMessage  - Сообщение целиком.
     * @param stateMessage - Статус сообщения, возможно описание ошибки.
     * @throws SQLException
     */
    void setTableContent(String guid,
                         String nameMethod,
                         Timestamp dateMessage,
                         String nameTypeEn,
                         java.io.InputStream soapMessage,
                         String stateMessage,
                         java.sql.Connection connection) throws SQLException {

        int operationId = typeOperationDAO.getTypeOperationId(nameTypeEn, connection);
        int nameMethodId = nameMethodDAO.getMethodId(nameMethod, connection);

        try (java.sql.PreparedStatement ps = connection.prepareStatement(
                "INSERT  INTO OBMEN_OPERATION(" +
                        "SPR_TYPE_OPERATION_ID, " +
                        "SPR_NAME_METHOD_ID, " +
                        "GUID, " +
                        "DATE_MESSAGE, " +
                        "SOAPMESSAGE, " +
                        "STATE_MESSAGE) " +
                        "VALUES(?, ?, ?, ?, ?, ?)")) {

            ps.setInt(1, operationId);
            ps.setInt(2, nameMethodId);
            ps.setString(3, guid);
            ps.setTimestamp(4, dateMessage);
            ps.setBlob(5, soapMessage);
            ps.setString(6, stateMessage);
            ps.executeUpdate();
        }
    }

//    Вместо процедуры
    private ResultSet getAllMessages(Connection connection) throws SQLException {

        try (ResultSet rs = connection.createStatement().executeQuery("SELECT ob.id," +
                                                                        "ope.NAME_TYPE_RU, " +
                                                                        "ob.DATE_MESSAGE, " +
                                                                        "ob.GUID, " +
                                                                        "met.NAME_METHOD, " +
                                                                        "met.NAME_METHOD_RU, " +
                                                                        "ob.STATE_MESSAGE, " +
                                                                        "ob.SOAPMESSAGE " +
                                                                        "FROM OBMEN_OPERATION ob " +
                                                                        "INNER JOIN SPR_NAME_METHOD met ON ob.SPR_NAME_METHOD_ID = met.ID " +
                                                                        "INNER JOIN SPR_TYPE_OPERATION ope ON ob.SPR_TYPE_OPERATION_ID = ope.ID " +
                                                                        "ORDER BY 1 DESC NULLS LAST;"))
        {
            rs.next();
            return rs;
        }
    }

    /**
     * Метод, возвращает последнее сообщение из таблицы.
     * @param connection подключение к БД.
     * @return сообщение.
     * @throws SQLException
     */
    public InputStream getLastSOAPMessage(Connection connection) throws SQLException {
        try (ResultSet rs = connection.createStatement().executeQuery(
                "SELECT ID, SOAPMESSAGE " +
                "FROM OBMEN_OPERATION " +
                "ORDER BY 1 DESC NULLS LAST;"))
        {
            rs.next();
            return rs.getBinaryStream(2);
        }
    }

    /**
     * Метод, по полученному ключу берет из таблицы сообщение и возвращает его.
     * @param id идентификатор записи в БД.
     * @return сообщение.
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws SOAPException
     */
    public InputStream getSOAPMessageFromDataBaseById(Integer id, Connection connection) throws SQLException, FileNotFoundException, SOAPException {
        try (PreparedStatement ps = connection.prepareCall("SELECT SOAPMESSAGE FROM OBMEN_OPERATION WHERE ID = ?;")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getBinaryStream(1);
        }
    }
}
