package ru.progmatik.java.web.servlets.web;

import ru.progmatik.java.web.freemarkergen.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andryha on 14.06.2016.
 */
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
//        pageVariables.put("errorMessage", errorMessage);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(200);

        response.getWriter().println(PageGenerator.instance().getPage("test/index.html", pageVariables));
    }
}
