package ru.progmatik.java.pregis.connectiondb;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Класс для подключения к базе данных.
 * Берет настройки указанные в файле параметров "application.properties".
 */
public class ConnectionDB {

    private static final String databaseURI = ResourceBundle.getBundle("application").getString("config.database.uri");
    private static final String userName = ResourceBundle.getBundle("application").getString("config.database.user");
    private static final String password = ResourceBundle.getBundle("application").getString("config.database.password");

    private static JdbcConnectionPool cp = JdbcConnectionPool.create(databaseURI, userName, password);

    private static Connection connection;

    /**
     * Метод статический отдаёт новое подключение.
     * @return Connection новое подключение к базе данных.
     * @throws SQLException
     */
    public static Connection getConnectionDB() throws SQLException {

        if (connection == null || connection.isClosed())
            connection = cp.getConnection();

        return connection;
    }

    public static void close() throws Exception {

        if (cp != null) {
            cp.dispose();
        }

    }

}
