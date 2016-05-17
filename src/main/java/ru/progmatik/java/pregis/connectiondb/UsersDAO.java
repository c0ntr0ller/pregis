package ru.progmatik.java.pregis.connectiondb;

import ru.progmatik.java.servlets.accounts.UserProfile;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, получает учетные записи пользователей и записывает в БД.
 */
public class UsersDAO {

    private Connection connection;

    public UsersDAO(Connection connection) {
        this.connection = connection;
    }

    public List<UserProfile> getUser(String login) throws SQLException {

        List<UserProfile> listUsers = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM USERSLOGIN WHERE LOGIN = \'" + login + "\'");
        while (resultSet.next()) {
            UserProfile profile = new UserProfile(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
            listUsers.add(profile);
        }
        return listUsers;
    }


}
