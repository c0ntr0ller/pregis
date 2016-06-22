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
        } catch (ServletException e) {
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

        if (checkLogin(request, response)) {
            showPageMain(request, response);
//            if (action.isRunning()) {
////            response.sendRedirect("/status");
////            request.getRequestDispatcher("/status").forward(request, response);
//                showPageState(request, response);
//            } else {
//                showPageMain(request, response);
//            }
        }
    }

    private void showPageMain(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> pageVariables = new HashMap<>();

        if (action.isRunning()) {
            pageVariables.put("buttonState", "disabled");
        } else {
            pageVariables.put("buttonState", "");
        }

        response.setContentType("text/html;charset=utf-8");
//        response.setStatus(statusCode);

        try {
            response.getWriter().println(PageGenerator.instance().getPage("main_page.html", pageVariables));
        } catch (IOException e) {
            e.printStackTrace();
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

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.err.println("Получил POST");
//
//        if (checkLogin(request, response)) {
//
//            System.err.println(request.getParameterNames().nextElement());
//
//            switch (request.getParameterNames().nextElement()) {
//                case "getSenderID":
//                    if (!action.isRunning()) {
//                        action.getSenderID();
////                        response.sendRedirect("/status");
//                        showPageMain(request, response);
////                        request.getRequestDispatcher("/status").forward(request, response);
//                    }
//                    break;
//                case "getHouseUO":
//                    if (!action.isRunning())
//                        action.updateAllHouseData();
//                    break;
//                default:
//                    new ErrorPage("Неизвестная команда", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//                    break;
//            }
//        }
////            Map map = request.getParameterMap();
////            map.keySet().
////            request.getRequestDispatcher("/status").forward(request, response);
//    }

}
