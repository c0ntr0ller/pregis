package ru.progmatik.java.web.servlets.web;


import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.ProgramAction;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.web.accounts.ProfileSingleton;
import ru.progmatik.java.web.accounts.UserProfile;
import ru.progmatik.java.web.freemarkergen.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/*
 * Авторизация
 * login – логин
 * password – пароль в зашифрованном по алгоритму md5 виде.
 *
*/

/**
 * Класс, обработки http запроса модуля "Авторизация".
 */
@WebServlet(name = "LoginClient", urlPatterns = "/login")
public class LoginClient extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginClient.class);

    private ProgramAction action;

    /**
     * Конструктор, используется по умолчанию для веб-контейнеров (например "Tomcat").
     */
    public LoginClient() throws SQLException {
        super();
    }

    public LoginClient(ProgramAction action) throws SQLException {
        this();
        this.action = action;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Не работает для ссылки / проблемы в jetty
        showPage(request, response, "", HttpServletResponse.SC_OK);
    }

    public void showPage(HttpServletRequest request, HttpServletResponse response,
                         String errorMessage, int statusCode) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("errorMessage", errorMessage);

        if (errorMessage != null && !errorMessage.isEmpty())
            LOGGER.error("ErrorPage: " + errorMessage);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(statusCode);

        response.getWriter().println(PageGenerator.instance().getPage("login_form.html", pageVariables));
    }

    //sign in
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = OtherFormat.getMD5(request.getParameter("password"));
//        System.out.println(login);
//        System.out.println(password);

        String sessionId = request.getSession().getId();

//        Смотрим если сессия уже есть берем её.
        UserProfile profile = ProfileSingleton.instance().getAccountService().getUserBySessionId(sessionId);

        if (profile == null) {
//            Если нет сессии ищем по логину
            profile = ProfileSingleton.instance().getAccountService().getUserByLogin(login);
        }

//        Если поле логи или пароль пустое
        if (login == null || password == null || password.isEmpty()) {
            showPage(request, response, "Не указан логин или пароль!", HttpServletResponse.SC_BAD_REQUEST);
//            new ErrorPage("Не указан логин или пароль!", HttpServletResponse.SC_BAD_REQUEST).doGet(request, response);
//            response.setContentType("text/xml;charset=utf-8");
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().print("Не указан логин или пароль!");
            return;
        }

//        Если профиль не найден или пароль указан неверно.
        if (profile == null || !password.equals(profile.getPassword())) {
            showPage(request, response, "Проверьте достоверность веденных данных!", HttpServletResponse.SC_UNAUTHORIZED);
//            showPage(request, response, "Проверьте правильность ввода данных!", HttpServletResponse.SC_UNAUTHORIZED);
            LOGGER.debug("Login: " + login + " password: " + password);

            if (profile == null) {
                LOGGER.debug("ProfileLogin: " + login + " - не найден!");
            } else {
//                LOGGER.debug("ProfileLogin: " + profile.getLogin() + " ProfilePassword: " + profile.getPassword());
                LOGGER.debug("ProfileLogin: " + profile.getLogin());
            }

            LOGGER.debug("request.getParameter: " + request.getParameter("password"));
//            new ErrorPage("Проверьте правильность ввода данных!", HttpServletResponse.SC_UNAUTHORIZED).doGet(request, response);
//            response.setContentType("text/xml;charset=utf-8");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().print("Проверьте правильность ввода данных!");
            return;
        }

        ProfileSingleton.instance().getAccountService().addSession(request.getSession().getId(), profile);

        response.sendRedirect("/main");
//        request.getRequestDispatcher("/main").forward(request, response);
//        showPage(request, response, "Привет мой господин!", HttpServletResponse.SC_OK);
//        response.setContentType("text/xml;charset=utf-8");
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.getWriter().print("Привет мой господин!");
    }

    //sign out
    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
//        Извлекаем id сессии
        String sessionId = request.getSession().getId();
//        Получаем профиль по id сессии
        UserProfile profile = ProfileSingleton.instance().getAccountService().getUserBySessionId(sessionId);
//        Если нет профиля отвечает что и так не авторизирован
        if (profile == null) {
            showPage(request, response, "", HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType("text/html;charset=utf-8");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
//            Удаляем профиль из "Map"
            ProfileSingleton.instance().getAccountService().deleteSession(sessionId);
//            showPage(request, response, "Goodbye!", HttpServletResponse.SC_OK);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Goodbye!");
            response.setStatus(HttpServletResponse.SC_OK);
            action.checkSessions();
        }
    }
}
