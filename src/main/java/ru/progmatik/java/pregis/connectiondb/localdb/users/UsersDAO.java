package ru.progmatik.java.pregis.connectiondb.localdb.users;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.web.accounts.ProfileSingleton;
import ru.progmatik.java.web.accounts.UserProfile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, получает учетные записи пользователей и записывает в БД.
 */
public class UsersDAO {

    private static final Logger LOGGER = Logger.getLogger(UsersDAO.class);
    private static final String TABLE_NAME_USERS_LOGIN = "USERS_LOGIN";  // имя таблицы

    private static final String SQL_CREATE_TABLE_USERS_LOGIN = "CREATE TABLE IF NOT EXISTS USERS_LOGIN (" +
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

        if (!ConnectionDB.instance().tableExist(TABLE_NAME_USERS_LOGIN.toUpperCase())) {
            ConnectionDB.instance().sendSqlRequest(SQL_CREATE_TABLE_USERS_LOGIN);
        }
    }

    /**
     * Метод, получает всех пользователей из БД.
     * @return список пользователей.
     * @throws SQLException
     */
    public List<UserProfile> getUsers() throws SQLException {

        Connection connection = ConnectionDB.instance().getConnectionDB();
        List<UserProfile> listUsers = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT ID, LOGIN, PASSWORD, NAME, SURNAME, PATRONYMIC, DESCRIPTION, IS_ADMIN FROM USERS_LOGIN;");
        while (resultSet.next()) {
            UserProfile profile = new UserProfile(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getBoolean(8));
            listUsers.add(profile);
        }
        resultSet.close();
        stmt.close();
        connection.close();
        return listUsers;
    }

    /**
     * Метод, добавляет в БД пользователя, если он ещё не существует.
     * @param profile объект с описанием пользователя.
     * @throws SQLException
     */
    public String addUser(UserProfile profile) throws SQLException {

        Integer profileId = getIdByLogin(profile.getLogin());
        int size = getUsers().size();
        if (profileId == null) {

            Connection connection = ConnectionDB.instance().getConnectionDB();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO USERS_LOGIN(" +
                    "LOGIN, PASSWORD, NAME, SURNAME, PATRONYMIC, DESCRIPTION, IS_ADMIN) VALUES(?, ?, ? ,?, ?, ? ,?)");
            ps.setString(1, profile.getLogin());
            ps.setString(2, profile.getPassword());
            ps.setString(3, profile.getName());
            ps.setString(4, profile.getSurname());
            ps.setString(5, profile.getPatronymic());
            ps.setString(6, profile.getDescription());
            ps.setBoolean(7, size > 1 ? profile.isAdmin() : true); // если ещё нет учетных записей, то первая будет админом
            ps.executeUpdate();
            LOGGER.debug("added profile: " + profile.getLogin());
            ps.close();
            connection.close();
            ProfileSingleton.instance().resetProfileSingleton();
            return "Учётная запись успешно добавлена.";
        } else {
            try (Connection connection = ConnectionDB.instance().getConnectionDB();
                 PreparedStatement ps = connection.prepareStatement("UPDATE USERS_LOGIN SET LOGIN = ?, PASSWORD = ?, " +
                         "NAME = ?, SURNAME = ?, PATRONYMIC = ?, DESCRIPTION = ?, IS_ADMIN = ? WHERE ID = ?")) {
                ps.setString(1, profile.getLogin());
                ps.setString(2, profile.getPassword());
                ps.setString(3, profile.getName());
                ps.setString(4, profile.getSurname());
                ps.setString(5, profile.getPatronymic());
                ps.setString(6, profile.getDescription());
                ps.setBoolean(7, size > 1 ? profile.isAdmin() : true); // если ещё нет учетных записей, то первая будет админом
                ps.setInt(8, profileId);
                ps.executeUpdate();
                ProfileSingleton.instance().resetProfileSingleton();
                LOGGER.debug("update profile: " + profile.getLogin());
            }
            return "Данные успешно обновлены.";
        }
//        return "Возникли ошибки, данные не обработаны.";
    }


    /**
     * Метод, проверяет, есть указанный пользователь в БД или нет.
     * @param login имя пользователя для входа в систему.
     * @return идентификатор пользователя в таблице, null - пользователь не найден в БД.
     */
    private Integer getIdByLogin(String login) throws SQLException {

        List<UserProfile> list = getUsers();

        for (UserProfile profile : list) {
            if (login.equalsIgnoreCase(profile.getLogin())) {
                return profile.getId();
            }
        }
        return null;
    }
}
