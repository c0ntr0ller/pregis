package ru.prog_matik.java.pregis.connectiondb;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс будет получать из базы нужную информацию.
 * Created by andryha on 17.02.2016.
 */
public class BaseOrganization {

    private static Logger logger = Logger.getLogger(BaseOrganization.class);

    private static Connection connection;

    /**
     * Метод извлекает из базы данных идентификатор отправителя "SenderID".
     * @return String идентификатор отправителя "SenderID".
     */
    public static String getSenderID() {

        String senderID = "";

        try {

            if (connection == null || connection.isClosed()) {
                connection = ConnectionDB.getConnectionDB();
            }

            ResultSet rs = connection.createStatement().executeQuery("SELECT SENDER_ID FROM ORGANIZATION");

            while (rs.next()) {
                senderID = rs.getString(1);
            }

            if (!rs.isClosed()) rs.close();
            if (connection != null || !connection.isClosed()) connection.close();

        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        logger.info("Получен SenderID: " + senderID);
        return senderID;
    }
}
