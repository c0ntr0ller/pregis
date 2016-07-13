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
    private static final String TABLE_NAME_USERSLOGIN = "USERSLOGIN";  // имя таблицы

    private static final String SQL_CREATE_TABLE_USERSLOGIN = "CREATE TABLE IF NOT EXISTS USERS_LOGIN (" +
            "ID identity not null primary key, " +
            "LOGIN varchar(255) not null, " +
            "PASSWORD varchar(255) not null, " +
            "NAME varchar(255), " +
            "SURNAME varchar(255), " +
            "PATRONYMIC varchar(255), " +
            "DESCRIPTION varchar(255), " +
            "IS_ADMIN boolean DEFAULT false); " +
            "COMMENT ON TABLE PUBLIC.USERS_LOGIN IS 'Таблица учетных записей пользователей для доступа в ПреГИС.'; " +
            "COMMENT ON COLUMN USERS_LOGIN.ID IS 'Идентификатор записей.'; " +
            "COMMENT ON COLUMN USERS_LOGIN.LOGIN IS 'Имя пользователя для доступа.'; " +
            "COMMENT ON COLUMN USERS_LOGIN.PASSWORD IS 'Пароль пользователя.'; " +
            "COMMENT ON COLUMN USERS_LOGIN.NAME IS 'Имя пользователя.'; " +
            "COMMENT ON COLUMN USERS_LOGIN.SURNAME IS 'Фамилия пользователя.'; " +
            "COMMENT ON COLUMN USERS_LOGIN.PATRONYMIC IS 'Отчество пользователя.'; " +
            "COMMENT ON COLUMN USERS_LOGIN.DESCRIPTION IS 'Примечание.'; " +
            "COMMENT ON COLUMN USERS_LOGIN.IS_ADMIN IS 'Является пользователь администратором?.';";

    /**
     * Конструктор, создаёт таблице если её не было.
     * @throws SQLException
     */
    public UsersDAO() throws SQLException {

        if (!ConnectionDB.instance().tableExist(TABLE_NAME_USERSLOGIN.toUpperCase())) {
            ConnectionDB.instance().tableExist(SQL_CREATE_TABLE_USERSLOGIN);
        }
    }

    public List<UserProfile> getUsers() throws SQLException {

        Connection connection = ConnectionDB.instance().getConnectionDB();
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

        Connection connection = ConnectionDB.instance().getConnectionDB();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO USERSLOGIN VALUES(DEFAULT , ?, ? ,?)");
        ps.setString(1, profile.getLogin());
        ps.setString(2, OtherFormat.getMD5(profile.getPassword()));
        ps.setString(3, profile.getDescription());

        ps.close();
        connection.close();
    }
}
