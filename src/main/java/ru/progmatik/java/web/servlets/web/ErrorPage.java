package ru.progmatik.java.web.servlets.web;

import org.apache.log4j.Logger;
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
 * Класс, сурвлет выводит страницу если возникли ошибки.
 */
@WebServlet(name = "ErrorPage", urlPatterns = "/")
public class ErrorPage extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginClient.class);

    private String errorMessage;
    private int statusCode;


    private ErrorPage() {
        try {
            super.init();
        } catch (ServletException e) {
            LOGGER.error("ErrorPage: ", e);
            e.printStackTrace();
        }

    }

    public ErrorPage(String errorMessage, int statusCode) {
        this();
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("errorMessage", errorMessage);

        LOGGER.error("ErrorPage: " + errorMessage);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(statusCode);

        response.getWriter().println(PageGenerator.instance().getPage("errorpage.html", pageVariables));
    }
}
