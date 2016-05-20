package ru.progmatik.java.web.servlets.web;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.ProgramAction;
import ru.progmatik.java.web.accounts.AccountService;
import ru.progmatik.java.web.accounts.ProfileSingleton;
import ru.progmatik.java.web.accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Класс, будет иметь все кнопки для операций.
 */
@WebServlet(name = "MainServlet", urlPatterns = "/main")
public class MainServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginClient.class);
    private final AccountService accountService;

    public MainServlet() throws ServletException, SQLException {
        super.init();
        accountService = ProfileSingleton.instance().getAccountService();
    }

    public MainServlet(ProgramAction action) {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Извлекаем id сессии
        String sessionId = request.getSession().getId();
//        Получаем профиль по id сессии
        UserProfile profile = accountService.getUserBySessionId(sessionId);
//        Если нет профиля отвечает что и так не авторизирован
        if (profile == null) {
            try {
                new LoginClient().showPage(request, response, "", HttpServletResponse.SC_UNAUTHORIZED);
            } catch (SQLException e) {
                LOGGER.error("MainServlet: ", e);
                new ErrorPage(e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
        } else {
            switch (request.getParameterNames().nextElement()) {
                case "getSenderID":

            }
            Map map = request.getParameterMap();
            map.keySet().
            request.getRequestDispatcher("/status").forward(request, response);
        }
    }

}
