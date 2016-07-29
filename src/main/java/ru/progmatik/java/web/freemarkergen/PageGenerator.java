package ru.progmatik.java.web.freemarkergen;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

public class PageGenerator {
    private static final String HTML_DIR = "site/html/";

    private static PageGenerator pageGenerator;
    private final Configuration cfg;

    private PageGenerator() {
        cfg = new Configuration();
        cfg.setEncoding(new Locale("ru"), "utf-8");
        cfg.setDefaultEncoding("utf-8");
    }

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = cfg.getTemplate(HTML_DIR + filename);
//            Template template = cfg.getTemplate(HTML_DIR + File.separator + filename);
            template.setEncoding("UTF-8");
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }
}
