package ru.progmatik.java.pregis.connectiondb.localdb.message;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Класс, создаёт таблицу "SPR_TYPE_OPERATION", если она не найдена.
 */
class TypeOperationDAO {

    private static final Logger LOGGER = Logger.getLogger(TypeOperationDAO.class);

    private static final String TABLE_NAME_SPR_TYPE_OPERATION = "SPR_TYPE_OPERATION";
    private static final String SQL_CREATE_TABLE_SPR_TYPE_OPERATION = "CREATE TABLE IF NOT EXISTS SPR_TYPE_OPERATION (" +
            "ID IDENTITY NOT NULL PRIMARY KEY, " +
            "NAME_TYPE_EN VARCHAR_IGNORECASE(10) NOT NULL, " +
            "NAME_TYPE_RU VARCHAR_IGNORECASE(10) NOT NULL); " +

            "COMMENT ON TABLE PUBLIC.SPR_TYPE_OPERATION IS 'Таблица хранит перечень типов опираций (т.е. запрос, ответ) сообщения.'; " +
            "COMMENT ON COLUMN SPR_TYPE_OPERATION.ID IS 'Идентификатор записей.'; " +
            "COMMENT ON COLUMN SPR_TYPE_OPERATION.NAME_TYPE_EN IS 'Значения типа сообщения, является сообщение \"Запросом\" или \"Ответом\". На английском языке'; " +
            "COMMENT ON COLUMN SPR_TYPE_OPERATION.NAME_TYPE_RU IS 'Значения типа сообщения, является сообщение \"Запросом\" или \"Ответом\". На русском языке'; " +

            "INSERT INTO SPR_TYPE_OPERATION(NAME_TYPE_EN, NAME_TYPE_RU) VALUES('Request', 'Запрос'); " +
            "INSERT INTO SPR_TYPE_OPERATION(NAME_TYPE_EN, NAME_TYPE_RU) VALUES('Result', 'Ответ');";

    TypeOperationDAO() throws SQLException {
        if (!ConnectionDB.instance().tableExist(TABLE_NAME_SPR_TYPE_OPERATION.toUpperCase())) {
            ConnectionDB.instance().sendSqlRequest(SQL_CREATE_TABLE_SPR_TYPE_OPERATION);
        }
    }

    /**
     * Метод возвращает "ID" из справочника "SPR_TYPE_OPERATION".
     * @param connection - соединение с базой данных.
     * @param nameTypeEn - имя типа сообщения, "ID" которого необходимо вернуть.
     * @return int "ID" из справочника "SPR_TYPE_OPERATION".
     * @throws java.sql.SQLException
     */
    int getTypeOperationId(String nameTypeEn, Connection connection) throws java.sql.SQLException {

        try (PreparedStatement ps = connection.prepareStatement("SELECT ID FROM SPR_TYPE_OPERATION WHERE NAME_TYPE_EN = ?")) {
            ps.setString(1, nameTypeEn);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new java.sql.SQLException("Не найдено " + nameTypeEn + " в таблице \"SPR_TYPE_OPERATION.NAME_TYPE_EN\"!");
                }
            }
        }
    }
}
