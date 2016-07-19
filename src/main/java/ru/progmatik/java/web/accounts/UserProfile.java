package ru.progmatik.java.web.accounts;

import ru.progmatik.java.pregis.other.OtherFormat;
/*
    Не получиться встроить механизм преобразования пароля в MD5, при получении объекта из БД
    и его восстановления, MD5 хеш повторно преобразуется в MD5 и пароль получается не верным.
 */
/**
 * Класс, описывает профиль пользователя.
 */
public class UserProfile {
    private final String login; // Логин пользователя.
    private Integer id; // Идентификатор пользователя в БД.
    private String password; // Тут что будет, по описанию похоже пароль.
    private String name;  // Имя пользователя.
    private String surname;  // Фамилия пользователя.
    private String patronymic;  // Отчество пользователя.
    private String description; // Добавил на всякий случай, можно указать email, название компании или ещё что угодно.
    private Boolean admin; // Является пользователь администратором?.

    /**
     * Конструктор, принимает параметры для создания объекта.
     * @param id идентификатор пользователя в БД.
     * @param login логин пользователя.
     * @param password пароль пользователя.
     * @param name имя пользователя.
     * @param surname фамилия пользователя.
     * @param patronymic отчество пользователя.
     * @param description добавил на всякий случай, можно указать email, название компании или ещё что угодно.
     */
    public UserProfile(Integer id, String login, String password,
                       String name, String surname, String patronymic, String description, Boolean admin) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.description = description;
        this.admin = admin;
    }

    /**
     * Конструктор, принимает параметры для создания объекта.
     * @param login логин пользователя.
     * @param password пароль пользователя.
     * @param name имя пользователя.
     * @param surname фамилия пользователя.
     * @param patronymic отчество пользователя.
     * @param description добавил на всякий случай, можно указать email, название компании или ещё что угодно.
     */
    public UserProfile(String login, String password, String name,
                       String surname, String patronymic, String description, Boolean admin) {
        this.id = -1;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.description = description;
        this.admin = admin;
    }

    public UserProfile(String login, String password, String description, Boolean admin) {
        this.id = -1;
        this.login = login;
        this.password = password;
        this.name = null;
        this.surname = null;
        this.patronymic = null;
        this.description = description;
        this.admin = admin;
    }

    public UserProfile(String login) {
        this.id = -1;
        this.login = login;
        this.password = OtherFormat.getMD5(login);
        this.name = null;
        this.surname = null;
        this.patronymic = null;
        this.description = login;
        this.admin = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = OtherFormat.getMD5(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "{ id: " + id + ",\n" +
                "  login: " + login + ",\n" +
                "  password " + password + ",\n" +
                "  name " + name + ",\n" +
                "  surname " + surname + ",\n" +
                "  patronymic " + patronymic + ",\n" +
                "  description " + description + ",\n" +
                "  admin " + admin + " }\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserProfile profile = (UserProfile) o;

        if (!login.equals(profile.login)) return false;
        if (password != null ? !password.equals(profile.password) : profile.password != null) return false;
        if (name != null ? !name.equals(profile.name) : profile.name != null) return false;
        if (surname != null ? !surname.equals(profile.surname) : profile.surname != null) return false;
        if (patronymic != null ? !patronymic.equals(profile.patronymic) : profile.patronymic != null) return false;
        if (description != null ? !description.equals(profile.description) : profile.description != null) return false;
        return admin != null ? admin.equals(profile.admin) : profile.admin == null;

    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (admin != null ? admin.hashCode() : 0);
        return result;
    }
}
