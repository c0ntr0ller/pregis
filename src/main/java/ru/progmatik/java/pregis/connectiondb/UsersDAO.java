package ru.progmatik.java.pregis.connectiondb;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.web.accounts.UserProfile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, получает учетные записи пользователей и записывает в БД.
 */
public class UsersDAO {

    private static final Logger LOGGER = Logger.getLogger(UsersDAO.class);

    /**
     * Конструктор, создаёт таблице если её не было.
     * @throws SQLException
     */
    public UsersDAO() throws SQLException {

        Connection connection = ConnectionDB.getConnectionDB();
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS USERSLOGIN " +
                            "(ID identity not null primary key, " +
                            "LOGIN varchar(255) not null, " +
                            "PASSWORD varchar(255) not null, " +
                            "DESCRIPTION varchar(255))");
        statement.close();
        connection.close();
    }

    public List<UserProfile> getUsers() throws SQLException {

        Connection connection = ConnectionDB.getConnectionDB();
        List<UserProfile> listUsers = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM USERSLOGIN");
        while (resultSet.next()) {
            UserProfile profile = new UserProfile(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
            listUsers.add(profile);
        }
        resultSet.close();
        stmt.close();
        connection.close();
        return listUsers;
    }

    public void addUser(UserProfile profile) throws SQLException {

        Connection connection = ConnectionDB.getConnectionDB();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO USERSLOGIN VALUES(DEFAULT , ?, ? ,?)");
        ps.setString(1, profile.getLogin());
        ps.setString(2, OtherFormat.getMD5(profile.getPassword()));
        ps.setString(3, profile.getDescription());

        ps.close();
        connection.close();
    }
}
