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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, отображает состояние выполнения разных запросов.
 * Если, какой-то запрос выполняется, то должны попасть на эту страницу.
 */
@WebServlet(name = "StatusServlet", urlPatterns = "/status")
public class StatusServlet extends HttpServlet{

    private static final Logger LOGGER = Logger.getLogger(StatusServlet.class);

    private ProgramAction action;
    private AccountService accountService;

    public StatusServlet() {
        try {
            super.init();
            accountService = ProfileSingleton.instance().getAccountService();
        } catch (ServletException | SQLException e) {
            new ErrorPage(e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            LOGGER.error("StatusServlet", e);
            e.printStackTrace();
        }
    }

    public StatusServlet(ProgramAction action) {
        this();
        this.action = action;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Извлекаем id сессии
        String sessionId = request.getSession().getId();
//        Получаем профиль по id сессии
        UserProfile profile = accountService.getUserBySessionId(sessionId);
//        Если нет профиля отвечает что и так не авторизирован
        if (profile == null) {
            response.sendRedirect("/login");
        } else if (action.isStateRun()) {
            showPage(request, response);
        } else {
            response.sendRedirect("/main");
        }
    }

    private void showPage(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> pageVariables = new HashMap<>();
//        pageVariables.put("errorMessage", errorMessage);

        response.setContentType("text/html;charset=utf-8");
//        response.setStatus(statusCode);

        try {
            response.getWriter().println(PageGenerator.instance().getPage("status_page.html", pageVariables));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
