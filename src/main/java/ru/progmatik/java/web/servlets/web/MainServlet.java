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
 * Класс, будет иметь все кнопки для операций.
 */
@WebServlet(name = "MainServlet", urlPatterns = "/main")
public class MainServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(MainServlet.class);
    private ProgramAction action;
    private AccountService accountService;

    public MainServlet() {
        try {
            super.init();
            accountService = ProfileSingleton.instance().getAccountService();
        } catch (ServletException | SQLException e) {
            new ErrorPage(e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            LOGGER.error("MainServlet", e);
            e.printStackTrace();
        }

    }

    public MainServlet(ProgramAction action) {
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
            response.sendRedirect("/status");
        } else {
            showPage(request, response);
        }
    }

    private void showPage(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> pageVariables = new HashMap<>();
//        pageVariables.put("errorMessage", errorMessage);

        response.setContentType("text/html;charset=utf-8");
//        response.setStatus(statusCode);

        try {
            response.getWriter().println(PageGenerator.instance().getPage("main_page.html", pageVariables));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.err.println("Получил POST");
//        Извлекаем id сессии
        String sessionId = request.getSession().getId();
//        Получаем профиль по id сессии
        UserProfile profile = accountService.getUserBySessionId(sessionId);
//        Если нет профиля отвечает что и так не авторизирован
        try {

            if (profile == null) {
                response.sendRedirect("/login");
//                new LoginClient().showPage(request, response, "", HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                System.err.println(request.getParameterNames().nextElement());
                switch (request.getParameterNames().nextElement()) {
                    case "getSenderID":
                        if (!action.isStateRun())
                            action.getSenderID();
                        response.sendRedirect("/status");
                        request.getRequestDispatcher("/status").forward(request, response);
                        break;
                    case "getHouseUO":
                        if (!action.isStateRun())
                            action.callExportHouseData();
                        break;
                    default:
                        new ErrorPage("Неизвестная команда", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        break;
                }
            }

        } catch (Exception e) {
            LOGGER.error("MainServlet: ", e);
            action.setStateRunOff();
            new ErrorPage(e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
//            Map map = request.getParameterMap();
//            map.keySet().
//            request.getRequestDispatcher("/status").forward(request, response);
    }
}
