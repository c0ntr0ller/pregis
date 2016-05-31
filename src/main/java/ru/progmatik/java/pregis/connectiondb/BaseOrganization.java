package ru.progmatik.java.pregis.connectiondb;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс "Singleton", будет получать из базы "SenderID".
 * Created by andryha on 17.02.2016.
 */
public class BaseOrganization {

    private static final Logger LOGGER = Logger.getLogger(BaseOrganization.class);

    private static Connection connection;
    private static String senderID;
    private static BaseOrganization baseOrganization = null;

    private BaseOrganization() {

        try {

            if (connection == null || connection.isClosed()) {
                connection = ConnectionDB.instance().getConnectionDB();
            }

            ResultSet rs = connection.createStatement().executeQuery("SELECT SENDER_ID FROM ORGANIZATION");

            while (rs.next()) {
                senderID = rs.getString(1);
            }

            if (!rs.isClosed()) rs.close();
            if (connection != null || !connection.isClosed()) connection.close();

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        LOGGER.info("Получен SenderID: " + senderID);
    }

    /**
     * Метод извлекает из базы данных идентификатор отправителя "SenderID".
     * @return String идентификатор отправителя "SenderID".
     */
    public static String getSenderID() {

        if (baseOrganization == null)
            baseOrganization = new BaseOrganization();

        return senderID;
    }


}
