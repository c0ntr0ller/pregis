package ru.progmatik.java.web.accounts;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс, для хранения профилей пользователей.
 */
public class AccountService {
    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;

    public AccountService() {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(UserProfile userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    /**
     * Метод, возвращает профиль пользователя, если он найден по логину пользователя.
     * @param login логин пользователя.
     * @return профиль пользователя.
     */
    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    /**
     * Метод, возвращает профиль пользователя, если он найден по сессии пользователя.
     * @param sessionId сессия пользователя.
     * @return профиль пользователя.
     */
    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    /**
     * Метод, добавляет профиль по сессии. Т.е. если пользователь успешно прошел авторизацию, мы берем его сессию
     * и добавляем его профиль в Map, где ключ его сессия, профиль значения.
     * @param sessionId сессия пользователя.
     * @param userProfile профиль пользователя.
     */
    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    /**
     * Метод, удаляет профиль по сессии. Т.е. мы берем его сессию
     * и удаляем его профиль из Map, где ключ его сессия, профиль значения.
     * @param sessionId сессия пользователя.
     */
    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
