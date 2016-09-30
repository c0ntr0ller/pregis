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
    private static ResourcesUtil resourcesUtil;
    private Properties properties;

    private ResourcesUtil() {
        properties = getFileParameter();
    }

    public static ResourcesUtil instance() {
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
     *
     * @return String ОГРН компании.
     */
    public String getOGRNCompany() throws PreGISException {
        if (properties.getProperty("config.company.ogrn") == null) {
            LOGGER.error("ResourcesUtil: Не указан ОГРН компании в файле параметров \"settings\\application.properties\".");
            throw new PreGISException("ResourcesUtil: Не указан ОГРН компании в файле параметров \"settings\\application.properties\".");
        }

        return properties.getProperty("config.company.ogrn");
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

    /**
     * Метод, получает ID организации в БД ГРАД из файла параметров "application.properties".
     * Не понятно пока, в ГРАДе куча компаний может быть, сертификат может быть любой из них,
     * в некоторых случаях может по одному сертификату отправляться несколько компаний.
     *
     * @return ID организации из БД ГРАД.
     * @throws PreGISException
     */
    public int getCompanyGradId() throws PreGISException {

        int gradId;

        if (properties.getProperty("config.company.gradid") == null) {
            LOGGER.error("ResourcesUtil: Не указан ID компании из БД ГРАД в файле параметров \"settings\\application.properties\".");
            throw new PreGISException("ResourcesUtil: Не указан ID компании из БД ГРАД в файле параметров \"settings\\application.properties\".");
        } else {
            try {
                gradId = Integer.valueOf(properties.getProperty("config.company.gradid"));
            } catch (NumberFormatException e) {
                LOGGER.error("ResourcesUtil: Указан неверный ID компании из БД ГРАД в файле параметров \"settings\\application.properties\".\n" +
                        "Значение должно содержать только цифры.");
                throw new PreGISException("ResourcesUtil: Указан неверный ID компании из БД ГРАД в файле параметров \"settings\\application.properties\".\n" +
                        "Значение должно содержать только цифры.");
            }
        }

        return gradId;
    }

    /**
     * Метод, получает из файла настроек идентификатр компании, услуги которой будут выгружены в платежный документ.
     * Если идентификатора нет, то выгружает все услуги.
     * @return идентификатор компании для платежного документа.
     */
    public Integer getCompanyGradIdForPaymentDocument() throws PreGISException {

        if (properties.getProperty("config.company.pd.all").equalsIgnoreCase("yes")) {
            return null;
        } else {
            return getCompanyGradId();
        }
    }

    public int getWebPort() throws PreGISException {

        int webPort;

        if (properties.getProperty("config.web.ip.port") == null) {
            LOGGER.error("ResourcesUtil: Не указан порт для web интерфейса в файле параметров \"settings\\application.properties\".");
            throw new PreGISException("ResourcesUtil: Не указан порт для web интерфейса в файле параметров \"settings\\application.properties\".");
        } else {
            try {
                webPort = Integer.valueOf(properties.getProperty("config.web.ip.port"));
            } catch (NumberFormatException e) {
                LOGGER.error("ResourcesUtil: Указан неверный порт для web интерфейса в файле параметров \"settings\\application.properties\".\n" +
                        "Значение должно содержать только цифры.");
                throw new PreGISException("ResourcesUtil: Указан неверный порт для web интерфейса в файле параметров \"settings\\application.properties\".\n" +
                        "Значение должно содержать только цифры.");
            }
        }

        return webPort;
    }

    public String getCompanyRole() throws PreGISException {

        String role;

        if (properties.getProperty("config.company.role") == null) {
            throw new PreGISException("ResourcesUtil: Не указана роль компнании в файле параметров \"settings\\application.properties\".");
        } else {
            role = properties.getProperty("config.company.role");
        }
        return role;
    }

    /**
     * Метод, из файла параметров получает адресс сервера stunnel для соединения с ГИС ЖКХ.
     * @return адресс сервера stunnel.
     */
    public String getStunnelHost() {

        return properties.getProperty("config.server.binding");
    }

    /**
     * Метод, создаёт папку, если она не создана.
     * @param folderName имя папки.
     */
    public void createFolder(String folderName) {

        File saveFolder = new File(folderName);
        if (!saveFolder.isDirectory())
            saveFolder.mkdir();
    }
}
