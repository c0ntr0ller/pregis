package ru.progmatik.java.pregis.connectiondb;

import org.apache.log4j.Logger;
import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.*;

/**
 * Класс для подключения к базе данных.
 * Берет настройки указанные в файле параметров "application.properties".
 */
public class ConnectionDB {

    private static final Logger LOGGER = Logger.getLogger(ConnectionDB.class);

//    private static final String databaseURI = ResourceBundle.getBundle("application").getString("config.database.uri");
//    private static final String userName = ResourceBundle.getBundle("application").getString("config.database.user");
//    private static final String password = ResourceBundle.getBundle("application").getString("config.database.password");

//    Внутренняя база данных будет в любом случае создаваться не вижу смысла выводить куда-то настройки.
//    Она будет локальная и файловая - зачем выводить настройку?
//    Приложение крутиться на сервере - нет смысла ставить логин и пароль.
    private static final String databaseURI = "jdbc:h2:file:./db/pregisdb.h2.db;IGNORECASE=TRUE";
    private static final String userName = "";
    private static final String password = "";

    private static JdbcConnectionPool cp = JdbcConnectionPool.create(databaseURI, userName, password);

    private static Connection connection;
    private static ConnectionDB connectionDB;

    private ConnectionDB() {

    }

    public static ConnectionDB instance() {
        if (connectionDB == null) {
            connectionDB = new ConnectionDB();
        }
        return connectionDB;
    }

    public static void close() {

        if (cp != null) {
            cp.dispose();
        }
    }

    /**
     * Метод статический отдаёт новое подключение.
     * @return Connection новое подключение к базе данных.
     * @throws SQLException
     */
    public Connection getConnectionDB() throws SQLException {

        if (connection == null || connection.isClosed()) {
            connection = cp.getConnection();
        }

        return connection;
    }

    /**
     * Метод, проверяет, имеется в БД таблица.
     * @param tableName имя таблицы, которую необходимо найти.
     * @return true - если таблица найдена, false - таблица не найдена.
     * @throws SQLException
     */
    public boolean tableExist(String tableName) throws SQLException {

        DatabaseMetaData metaData = getConnectionDB().getMetaData();
        try (ResultSet rs = metaData.getTables(null, null, tableName, new String[] {"TABLE"})) {
            return rs.next();
        }
    }

    /**
     * Метод, создаёт таблицу из полученного SQL запроса.
     *
     * @param sqlCreateTable SQL запрос, в котором описано создание таблицы.
     * @throws SQLException
     */
    public void sendSqlRequest(String sqlCreateTable) throws SQLException {
        try (Statement statement = getConnectionDB().createStatement()) {
            statement.executeUpdate(sqlCreateTable);
            LOGGER.debug(sqlCreateTable);
        }
    }

    public void setShutdownDefragToLocalBase() {

        try (Statement st = getConnectionDB().createStatement()) {
            st.executeUpdate("SHUTDOWN DEFRAG");
            cp.dispose();
            cp = JdbcConnectionPool.create(databaseURI, userName, password);
            LOGGER.info("SHUTDOWN DEFRAG SUCCESS!!!");
        } catch (SQLException e) {
            LOGGER.error("SHUTDOWN DEFRAG ERROR!!!", e);
        }
    }
}
