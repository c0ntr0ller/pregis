package ru.progmatik.java.pregis.connectiondb;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.other.ResourcesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Класс служит для подключения к базе данных с помощью драйвера и дальнейшей передаче Connection.
 * Singleton.
 */
public class ConnectionBaseGRAD implements AutoCloseable {

    private static final Logger LOGGER = Logger.getLogger(ConnectionBaseGRAD.class);

    private static ConnectionBaseGRAD baseGRAD;

    private Connection connection;

    private String databaseURI;
    private String userName;
    private String password;
    private String role;

    private ConnectionBaseGRAD() {
        getAccountBase();
    }

    public static ConnectionBaseGRAD instance() {
        if (baseGRAD == null) baseGRAD = new ConnectionBaseGRAD();
        return baseGRAD;
    }

    /**
     * Метод создаёт подключение к базе данных Firebird.
     *
     * @return Connection с готовым подключением если все параметры указаны верно.
     */
    public Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.firebirdsql.jdbc.FBDriver");
            } catch (ClassNotFoundException e) {
                LOGGER.error("ConnectionBaseGRAD: " + e.getLocalizedMessage());
                e.printStackTrace();
            }

            String url = "jdbc:firebirdsql:" + databaseURI;

            Properties properties = new Properties();
            properties.put("JDBC_DRIVER", "org.firebirdsql.jdbc.FBDriver");
            properties.put("userName", userName);
            properties.put("password", password);
            properties.put("roleName", role);
            properties.put("encoding", "WIN1251");

            LOGGER.debug("ConnectionBaseGRAD: database: " + url + ", username: " + userName + ", role: " + role + ".");
            connection = DriverManager.getConnection(url, properties);
            connection.setAutoCommit(true);
        }

        return connection;
    }

    /**
     * Метод, берет параметры из файла "settings/application.properties" и устанавливает их для соединения с БД.
     */
    private void getAccountBase() {

        if (!ResourcesUtil.instance().getProperties().containsKey("config.database.grad.uri") ||
                !ResourcesUtil.instance().getProperties().containsKey("config.database.grad.user") ||
                !ResourcesUtil.instance().getProperties().containsKey("config.database.grad.password") ||
                !ResourcesUtil.instance().getProperties().containsKey("config.database.grad.role")) {
            LOGGER.error("ConnectionBaseGRAD: Не указан один из параметров!");
        } else {
            databaseURI = ResourcesUtil.instance().getProperties().getProperty("config.database.grad.uri");
            userName = ResourcesUtil.instance().getProperties().getProperty("config.database.grad.user");
            password = ResourcesUtil.instance().getProperties().getProperty("config.database.grad.password");
            role = ResourcesUtil.instance().getProperties().getProperty("config.database.grad.role");
        }
    }

    public final void setDataBaseURI(String baseURI) {
        databaseURI = baseURI;
    }

    /**
     * Класс наследуется от интерфейса AutoCloseable, для наиболее удобного и выгодного закрытия соединения.
     *
     * @throws SQLException выдаст ошибку, если не удастся удачно закрыть соединение.
     */
    @Override
    public final void close() {
        try {
            if (connection != null) {

                if (!connection.isClosed())
                    connection.close();

                LOGGER.debug("ConnectionBaseGRAD: Connection closed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
