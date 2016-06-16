package ru.progmatik.java.pregis.other;

import org.apache.commons.codec.digest.DigestUtils;
import ru.gosuslugi.dom.schema.integration.base.HeaderType;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.progmatik.java.pregis.connectiondb.organization.BaseOrganization;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Вспомогательный класс, для различных данных.
 */
public class OtherFormat {

//    public static final String USER_NAME = ResourceBundle.getBundle("application").getString("config.ws.user");
//    public static final String PASSWORD = ResourceBundle.getBundle("application").getString("config.ws.password");
    public static final String USER_NAME = ResourcesUtil.instance().getProperties().getProperty("config.ws.user");
    public static final String PASSWORD = ResourcesUtil.instance().getProperties().getProperty("config.ws.password");

    /**
     * Метод возвращает текущею дату и время.
     * Например для поля Date.
     * @return XMLGregorianCalendar возвращает текущею дату и время.
     */
    public static XMLGregorianCalendar getDateNow() {

        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date());
        XMLGregorianCalendar date = null;

        try {
            date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Метод формирует рандомное значение для индивидуального номера письма.
     * Поле MessageGUID.
     * @return String индивидуальный номер.
     */
    public static String getRandomGUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Метод возвращает значение по умолчанию (идентификатор) для цифровой подписи.
     * Именно это значение "signed-data-container", ищет в документе XML перехватчик и подписывает его.
     * @return String "signed-data-container", идентификатор по которому определяется элемент подписи запроса.
     */
    public static String getId() {
        return "signed-data-container";
    }

    /**
     * Метод формирует заголовок сообщения без "SenderID".
     *
     * @return ISRequestHeader заголовок сообщения без "SenderID".
     */
    public static HeaderType getISRequestHeader() {

        HeaderType isRequestHeader = new HeaderType();
        isRequestHeader.setDate(getDateNow());
        isRequestHeader.setMessageGUID(getRandomGUID());

        return isRequestHeader;
    }

    /**
     * Метод возвращает готовый заголовок запроса с "SenderID".
     * @return RequestHeader готовый заголовок с содержанием "SenderID".
     */
    public static RequestHeader getRequestHeader() {

        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setSenderID(BaseOrganization.getSenderID());
        requestHeader.setMessageGUID(getRandomGUID());
        requestHeader.setDate(getDateNow());

        return requestHeader;
    }

    /**
     * Метод, введен для упрощения, он вызывается при каждом запросе к сервису ГИС ЖКХ,
     * добавляет необходимые атрибуты для авторизации (получения доступа к ГИС ЖКХ).
     * @param service - объект класса, для подключения, наследник "Service"
     * @param port - объект которому назначаются параметры для соединения с сервисом ГИС ЖКХ.
     */
    public static void setPortSettings(Service service, Object port) {

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, OtherFormat.USER_NAME);
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, OtherFormat.PASSWORD);
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, service.getWSDLDocumentLocation().toString());
    }

    /**
     * Метод, получает текст и возвращает MD5 его сумму.
     * @param text текст.
     * @return String MD5 сумма текста.
     */
    public static String getMD5(String text) {
        String md5Hex = DigestUtils.md5Hex(text);
        return md5Hex;
    }
}
