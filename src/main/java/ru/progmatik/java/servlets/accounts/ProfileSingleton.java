package ru.progmatik.java.servlets.accounts;

/**
 * Класс одиночка, один экземпляр на приложение, пока так, там дальше видно будет.
 * Хранит в себе профили пользователей для получения доступа к сервису.
 */
public class ProfileSingleton {

    private static ProfileSingleton profileSingleton = null;
    private static AccountService accountService;
    private boolean check;

    public ProfileSingleton() {
        accountService = new AccountService();
//        Если нужно можно добавить пользователей здесь, они будут доступны всему приложению.
//        List<UserProfile> listProfils =
        accountService.addNewUser(new UserProfile("admin", "ghjuhfvvbcn2016", "Администратор сервиса"));
        accountService.addNewUser(new UserProfile("test"));
    }

    public static ProfileSingleton instance() {
        if (profileSingleton == null) {
            profileSingleton = new ProfileSingleton();
        }
        return profileSingleton;
    }

    public AccountService getAccountService() {
        instance();
        return accountService;
    }

    public void setAccountService(AccountService newAccountService) {
        accountService = newAccountService;
    }
}
