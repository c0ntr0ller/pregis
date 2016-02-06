package ru.prog_matik.java.pregis.signet;

import org.apache.log4j.Logger;
import ru.CryptoPro.JCP.JCP;
import ru.CryptoPro.JCP.KeyStore.HDImage.HDImageStore;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ResourceBundle;

/**
 * Класс, в котором инициализируются сертификаты, и указывается хранилище ключей.
 * С помощью файла параметров "application.properties".
 */
public class Configure {

    private static Logger logger = Logger.getLogger(Configure.class);

    private static String hdiPathConfig = HDImageStore.getDir();
    private static String keyStoryPath = ResourceBundle.getBundle("application").getString("config.cryptoPro.keyStore.path");
    private static String trustStoryPath = ResourceBundle.getBundle("application").getString("config.cryptoPro.trustStore.path");

    /**
     * Метод инициализации криптопровайдера и указания каталога ключей.
     */
    static {

//        Инициализация криптопровайдера
        if (!ru.CryptoPro.JCPxml.XmlInit.isInitialized())
            ru.CryptoPro.JCPxml.XmlInit.init();

        setPathAuto();

    }

    /**
     *  Метод проверяет в файле параметров значение "config.cryptoPro.path.auto.set" если указано "yes",
     *  определяет каталог программы и указывает путь к хранилищу в каталоге "data".
     */
    private static void setPathAuto() {

        String pathData;

        if (ResourceBundle.getBundle("application").getString("config.cryptoPro.path.auto.set").equalsIgnoreCase("yes")) {
            pathData = System.getProperty("user.dir") + File.separator + "data" + File.separator;

//        Для изменения пути к носителю необходимо дать доступ
//        в реестре windows в ветке
//        "HKEY_LOCAL_MACHINE\SOFTWARE\JavaSoft\Prefs\ru\/Crypto/Pro\/J/C/P\/Key/Store\/H/D/Image".
            if (!pathData.equalsIgnoreCase(hdiPathConfig)) {
                HDImageStore.setDir(pathData);
                logger.debug("HDIImageStory: " + hdiPathConfig + " изменен на: " + pathData);
            }
            keyStoryPath = pathData;
            trustStoryPath = pathData + File.separator + ResourceBundle.getBundle("application").getString("config.cryptoPro.trustStore.name.story");
        }
    }

    /**
     * Метод возвращает путь к хранилищу ключей.
     * @return возвращает путь к хранилищу ключей.
     */
    public static String getKeyStorePath() {

        return keyStoryPath;
    }

    /**
     * Метод возвращает имя (алиас) хранилища с приватным ключом.
     * @return возвращает имя (алиас) хранилища с приватным ключом.
     */
    public static String getKeyStoreAlias() {

        return ResourceBundle.getBundle("application").getString("config.cryptoPro.keyStore.alias");
    }

    /**
     * Метод возвращает пароль к хранилищу с приватным ключом, который указан в файле параметров
     * "application.properties".
     * @return пароль к хранилищу с приватным ключом.
     */
    public static char[] getKeyStorePassword() {

        String passwordKeyStore = ResourceBundle.getBundle("application").getString("config.cryptoPro.keyStore.password");

        return passwordKeyStore.toCharArray();
    }

    /**
     * Метод возвращает хранилище (KeyStore) с приватным ключом.
     * @return хранилище (KeyStore) с приватным ключом.
     * @throws NoSuchProviderException
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static KeyStore getKeyStore() throws NoSuchProviderException, KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {

        KeyStore keyStore = KeyStore.getInstance(JCP.HD_STORE_NAME, JCP.PROVIDER_NAME);
        keyStore.load(null, null);

        return keyStore;
    }

    /**
     * Метод возвращает путь к вспомогательным сертификатам (УЦ).
     * @return путь к вспомогательным сертификатам (УЦ).
     */
    public static String getTrustStorePath() {

        return trustStoryPath;
    }

    /**
     * Метод возвращает пароль к хранилищу вспомогательных сертификатов (УЦ).
     * @return пароль к хранилищу вспомогательных сертификатов (УЦ).
     */
    public static char[] getTrustStorePassword() {

        String passwordTrustStore = ResourceBundle.getBundle("application").getString("config.cryptoPro.trustStore.password");

        return passwordTrustStore.toCharArray();
    }

    /**
     * Метод возвращает инициализированное хранилище вспомогательных сертификатов (УЦ).
     * @return инициализированное хранилище вспомогательных сертификатов (УЦ).
     * @throws KeyStoreException
     * @throws IOException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     */
    public static KeyStore getTrustStore() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {

        final InputStream inputStream = new FileInputStream(getTrustStorePath());
        final KeyStore trustStore = KeyStore.getInstance(JCP.CERT_STORE_NAME);

        trustStore.load(inputStream, getTrustStorePassword());
        inputStream.close();

        return trustStore;
    }

}
