package ru.progmatik.java.web.servlets.web;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.ProgramAction;
import ru.progmatik.java.web.accounts.AccountService;
import ru.progmatik.java.web.accounts.ProfileSingleton;
import ru.progmatik.java.web.accounts.UserProfile;
import ru.progmatik.java.web.freemarkergen.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, добавляет или обновляет пользователей системы.
 */
@WebServlet(name = "UsersServlet", urlPatterns = "/users")
public class UsersServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginClient.class);
    private final AccountService accountService;

    private ProgramAction action;

    public UsersServlet() {
        super();
        accountService = ProfileSingleton.instance().getAccountService();
    }

    public UsersServlet(ProgramAction action) {
        this();
        this.action = action;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (checkLogin(request, response)) {
            showPage(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserProfile profile = new UserProfile(request.getParameter("login"),
                request.getParameter("password"), request.getParameter("name"),
                request.getParameter("surname"), request.getParameter("patronymic"),
                request.getParameter("description"), Boolean.valueOf(request.getParameter("admin")));
        System.out.println(request.getParameter("admin"));
        System.out.println(profile);
    }

    private void showPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(200);

        response.getWriter().println(PageGenerator.instance().getPage("users.html", pageVariables));
    }

    /**
     * Метод, проверяет, прошел пользователь авторизацию или нет.
     *
     * @param request  запрос клиента.
     * @param response наш ответ.
     * @return boolean true - если авторизован пользователь, false - если не авторизован.
     * @throws IOException
     */
    private boolean checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Извлекаем id сессии
        String sessionId = request.getSession().getId();
//        Получаем профиль по id сессии
        UserProfile profile = accountService.getUserBySessionId(sessionId);
//        Если нет профиля отвечает что и так не авторизирован
        if (profile == null) {
            response.sendRedirect("/login");
            return false;
        }
//        request.getSession().setMaxInactiveInterval(10);
        return true;
    }

}
