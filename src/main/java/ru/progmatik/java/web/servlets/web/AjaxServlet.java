package ru.progmatik.java.web.servlets.web;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.ProgramAction;
import ru.progmatik.java.web.accounts.AccountService;
import ru.progmatik.java.web.accounts.ProfileSingleton;
import ru.progmatik.java.web.accounts.UserProfile;
import ru.progmatik.java.web.servlets.socket.ObjectForJSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Класс, обрабатывает полученные ajax запросы.
 */
@WebServlet(name = "AjaxServlet", urlPatterns = "/ajax")
public class AjaxServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginClient.class);
    private final AccountService accountService;

    private ProgramAction action;

    public AjaxServlet() {
        super();
        accountService = ProfileSingleton.instance().getAccountService();
    }

    public AjaxServlet(ProgramAction action) {
        this();
        this.action = action;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (checkLogin(request, response)) {
//            request.get

            Gson gson = new Gson();
            ObjectForJSON json = gson.fromJson(request.getReader(), ObjectForJSON.class);
            LOGGER.debug("Команда: " + json.getCommand() + " id: " + json.getId() + " value: " + json.getValue());

            ObjectForJSON answerJson = new ObjectForJSON("Получена", "100", "OK");
            String stringJson = gson.toJson(answerJson, ObjectForJSON.class);
            response.setContentType("text/html;charset=utf-8");
//            response.getWriter().write("Команда: " + command + " id: " + id + " value: " + value);
            response.getWriter().write(stringJson);

            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

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
