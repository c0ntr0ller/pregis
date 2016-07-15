package ru.progmatik.java.web.servlets.web;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.ProgramAction;
import ru.progmatik.java.pregis.connectiondb.UsersDAO;
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

/**
 * Класс, добавляет или обновляет пользователей системы.
 */
@WebServlet(name = "UsersServlet", urlPatterns = "/users")
public class UsersServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginClient.class);

    private ProgramAction action;

    public UsersServlet() {
        super();
    }

    public UsersServlet(ProgramAction action) {
        this();
        this.action = action;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (checkLogin(request, response)) {
            showPage(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (checkLogin(request, response)) {
            Boolean isAdmin = request.getParameter("admin") != null ? request.getParameter("admin").equalsIgnoreCase("on") : false;
            UserProfile profile = new UserProfile(request.getParameter("login"),
                    OtherFormat.getMD5(request.getParameter("password")), request.getParameter("name"),
                    request.getParameter("surname"), request.getParameter("patronymic"),
                    request.getParameter("description"), isAdmin);
            try {
                UsersDAO dao = new UsersDAO();
                response.setContentType("text/html;charset=utf-8");
                String infoAdded = dao.addUser(profile);
                response.getWriter().println(infoAdded);
                LOGGER.debug(infoAdded);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (SQLException e) {
                LOGGER.error("doPost sql: ", e);
            }
        } else {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Вы не авторизованы!");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
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
        UserProfile profile = ProfileSingleton.instance().getAccountService().getUserBySessionId(sessionId);
//        Если нет профиля отвечает что и так не авторизирован
        return profile != null;
    }

}
