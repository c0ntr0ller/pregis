package ru.progmatik.java.web.servlets;


import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.web.accounts.AccountService;
import ru.progmatik.java.web.accounts.ProfileSingleton;
import ru.progmatik.java.web.accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/*
 * Авторизация
 * login – логин
 * password – пароль в зашифрованном по алгоритму md5 виде.
 *
*/

/**
 * Класс, обработки http запроса модуля "Авторизация".
 */
public class LoginClient extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginClient.class);
    private final AccountService accountService;

    /**
     * Конструктор, используется по умолчанию для веб-контейнеров (например "Tomcat").
     */
    public LoginClient() throws SQLException {
        super();
        accountService = ProfileSingleton.instance().getAccountService();
    }

//    public LoginClient(AccountService accountService) {
//        this.accountService = accountService;
//    }

    //sign in
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        String sessionId = request.getSession().getId();

//        Смотрим если сессия уже есть берем её.
        UserProfile profile = accountService.getUserBySessionId(sessionId);

        if (profile == null) {
//            Если нет сессии ищем по логину
            profile = accountService.getUserByLogin(login);
        }

//        Если поле логи или пароль пустое
        if (login == null || password == null || password.isEmpty()) {
            response.setContentType("text/xml;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("Не указан логин или пароль!");
            return;
        }

//        Если профиль не найден или пароль указан неверно.
        if (profile == null || !OtherFormat.getMD5(profile.getPassword()).equals(password)) {
            response.setContentType("text/xml;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print("Проверьте правильность ввода данных!");
            return;
        }

        accountService.addSession(request.getSession().getId(), profile);
        response.setContentType("text/xml;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    //sign out
    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
//        Извлекаем id сессии
        String sessionId = request.getSession().getId();
//        Получаем профиль по id сессии
        UserProfile profile = accountService.getUserBySessionId(sessionId);
//        Если нет профиля отвечает что и так не авторизирован
        if (profile == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
//            Удаляем профиль из "Map"
            accountService.deleteSession(sessionId);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Goodbye!");
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
