package ru.progmatik.java.pregis.other;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.exception.PreGISException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс, подгружает файл параметров ("settings/application.properties").
 */
public class ResourcesUtil {

    private static final Logger LOGGER = Logger.getLogger(ResourcesUtil.class);
    private static final String FILE_PROPERTIES = "application.properties";
    private static final String FILE_DIRECTORY = "settings";

    private Properties properties;

    private static ResourcesUtil resourcesUtil;

    private ResourcesUtil() {
        properties = getFileParameter();
    }

    public static ResourcesUtil instance(){
        if (resourcesUtil == null)
            resourcesUtil = new ResourcesUtil();
        return resourcesUtil;
    }

    public Properties getProperties() {
        instance();
        return properties;
    }

    /**
     * Метод, берет с файла параметров ОГРН компании и возвращает его.
     * @return String ОГРН компании.
     */
    public String getOGRNCompany() throws PreGISException {
        if (properties.getProperty("config.ogrn") == null) {
            LOGGER.error("ResourcesUtil: Не указан ОГРН компании в файле параметров \"settings\\application.properties\".");
            throw new PreGISException("ResourcesUtil: Не указан ОГРН компании в файле параметров \"settings\\application.properties\".");
        }

        return properties.getProperty("config.ogrn");
    }
    /**
     * <p>Метод открывает файл параметров, в котором хранятся параметры.</p>
     */
    private Properties getFileParameter() {

        File selectFile = new File(FILE_DIRECTORY + File.separator + FILE_PROPERTIES);

        Properties properties = new Properties();

        try (InputStream input = new FileInputStream(selectFile)) {
            properties.load(input);

            if (properties.isEmpty()) {
                LOGGER.error("ResourcesUtil: Не указаны параметры в файле \"settings\\application.properties\".");
            }
        } catch (IOException e) {
            LOGGER.info("ResourcesUtil: Не удаётся прочитать файл! " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        return properties;
    }


}
