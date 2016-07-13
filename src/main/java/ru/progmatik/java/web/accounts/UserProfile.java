package ru.progmatik.java.web.accounts;

/**
 * Класс, описывает профиль пользователя.
 */
public class UserProfile {
    private final Integer id; // Идентификатор пользователя в БД.
    private final String login; // Логин пользователя.
    private final String password; // Тут что будет, по описанию похоже пароль.
    private final String description; // Добавил на всякий случай, можно указать email, название компании или ещё что угодно.

    public UserProfile(String login, String password, String description) {
        this.id = -1;
        this.login = login;
        this.password = password;
        this.description = description;
    }

    public UserProfile(String login) {
        this.id = -1;
        this.login = login;
        this.password = login;
        this.description = login;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getDescription() {
        return description;
    }
}
