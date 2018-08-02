package ru.progmatik.java.pregis.other;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Класс, собирает всю информацию о URL со всех файлов параметров и формирует URL.
 */
public class UrlLoader {

    private static final Logger LOGGER = Logger.getLogger(UrlLoader.class);
    private static final String FILE_PROPERTIES = "url.properties";
    private static final String FILE_DIRECTORY = "settings";
    private static UrlLoader urlLoader;
    private HashMap<String, URL> urlMap;

    public UrlLoader() {
        this.urlMap = new HashMap<>();
        generateURLsMap();
    }

    /**
     * Метод, инициализация класса (одиночка (Singleton)).
     * @return сам объект.
     */
    public static UrlLoader instance() {

        if (urlLoader == null) {
            urlLoader = new UrlLoader();
        }
        return urlLoader;
    }

    /**
     * Метод, загружает с файла параметров "application.properties", протокол (http или https), из файла "url.properties"
     * адреса сервисов ГИС ЖКХ.
     */
    private void generateURLsMap() {
        try {

            String protocol = ResourcesUtil.instance().getProperties().getProperty("config.wsdl.location.url.protocol");
            String serverAddress = ResourcesUtil.instance().getStunnelHost();

            if (!protocol.equalsIgnoreCase("http")) {
                protocol = "https";
            }

            Properties properties = loadUrls();

            if (serverAddress == null) {

                for (String name : properties.stringPropertyNames()) {
                    urlMap.put(name, null);
                }

            } else {

                for (String name : properties.stringPropertyNames()) {

                    urlMap.put(name, new URL(String.format("%s://%s%s", protocol, serverAddress, properties.getProperty(name))));
                }
            }
            printUrlsToLogger();
        } catch (MalformedURLException e) {
            LOGGER.error("Error load \"url.properties\"", e);
        }
    }

    /**
     * Метод, загружает адреса сервисов ГИС ЖКХ из файла "url.properties" из каталога "resources".
     */
    private Properties loadUrls() {

        try (InputStream in = new FileInputStream(new File(FILE_DIRECTORY + File.separator + FILE_PROPERTIES))) {
            Properties properties = new Properties();
            properties.load(in);

            return properties;

        } catch (IOException e) {
            LOGGER.error("Error load \"url.properties\"", e);
        }
        return null;
    }

    /**
     * Метод, отправляет в логгер информацию о используемых адресах сервисов ГИС ЖКХ.;
     */
    private void printUrlsToLogger() {

        StringBuffer buffer = new StringBuffer();

        for (Map.Entry<String, URL> entry : urlMap.entrySet()) {
            buffer.append(String.format("%-25s%-2s%s \n", entry.getKey(), ":", entry.getValue()));
        }

        LOGGER.info("URL load successful: \n\n" + buffer);
    }

    /**
     * Метод, возвращает Map где ключ - имя сервиса ГИС ЖКХ, значение - адресс сервиса.
     * @return ключ - имя сервиса ГИС ЖКХ, значение - адресс сервиса.
     */
    public HashMap<String, URL> getUrlMap() {
        return urlMap;
    }
}
