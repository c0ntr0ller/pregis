package ru.progmatik.java.web.accounts;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.connectiondb.UsersDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Класс одиночка, один экземпляр на приложение, пока так, там дальше видно будет.
 * Хранит в себе профили пользователей для получения доступа к сервису.
 */
public class ProfileSingleton {

    private static final Logger LOGGER = Logger.getLogger(UsersDAO.class);

    private static ProfileSingleton profileSingleton = null;
    private static AccountService accountService;
    private boolean check;

    public ProfileSingleton() throws SQLException {

        accountService = new AccountService();
//        Если нужно можно добавить пользователей здесь, они будут доступны всему приложению.
        UsersDAO usersDAO = new UsersDAO();
        try {
            List<UserProfile> listProfils = usersDAO.getUsers();
            if (listProfils.isEmpty()) {
                accountService.addNewUser(new UserProfile("admin", "ghjuhfvvbcn2016", "Администратор сервиса"));
                accountService.addNewUser(new UserProfile("test"));
            } else {
                for (UserProfile userProfile : listProfils) {
                    accountService.addNewUser(userProfile);
                }
            }

        } catch (SQLException e) {
            LOGGER.info("ProfileSingleton: ", e);
            e.printStackTrace();
        }
    }

    public static ProfileSingleton instance() throws SQLException {
        if (profileSingleton == null) {
            profileSingleton = new ProfileSingleton();
        }
        return profileSingleton;
    }

    public AccountService getAccountService() throws SQLException {
        instance();
        return accountService;
    }

    public void setAccountService(AccountService newAccountService) {
        accountService = newAccountService;
    }
}
