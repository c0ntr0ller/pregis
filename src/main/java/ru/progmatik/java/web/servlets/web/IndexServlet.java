package ru.progmatik.java.web.servlets.web;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.ProgramAction;
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

@WebServlet(name = "IndexServlet", urlPatterns = "/main")
public class IndexServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(IndexServlet.class);
    private ProgramAction action;

    public IndexServlet() {
        try {
            super.init();
        } catch (ServletException e) {
            new ErrorPage(e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            LOGGER.error("IndexServlet", e);
            e.printStackTrace();
        }
    }

    public IndexServlet(ProgramAction action) {
        this();
        this.action = action;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (checkLogin(request, response)) {
            showPage(request, response);
        }
    }

    private void showPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();

//        if (action.isRunning()) {
//            pageVariables.put("showState", "hidden=\"false\"");
//        } else {
//            pageVariables.put("showState", "hidden=\"true\"");
//        }

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(200);

        response.getWriter().println(PageGenerator.instance().getPage("index.html", pageVariables));
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
        if (profile == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
