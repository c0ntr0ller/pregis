package ru.prog_matik.java.pregis.other;

import ru.gosuslugi.dom.schema.integration._8_7_0.HeaderType;
import ru.gosuslugi.dom.schema.integration._8_7_0.RequestHeader;
import ru.prog_matik.java.pregis.connectiondb.BaseOrganization;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Вспомогательный класс, для различных данных.
 */
public class OtherFormat {

    public static final String USER_NAME = ResourceBundle.getBundle("application").getString("config.ws.user");
    public static final String PASSWORD = ResourceBundle.getBundle("application").getString("config.ws.password");

    /**
     * Метод возвращает текущею дату и время.
     * Например для поля Date.
     * @return
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
     * @return
     */
    public static String getRandomGUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Метод возвращает значение по умолчанию (идентификатор) для цифровой подписи.
     * Именно это значение "signed-data-container", ищет в документе XML перехватчик и подписывает его.
     * @return
     */
    public static String getId() {
        return "signed-data-container";
    }

    /**
     * Метод формирует заголовок сообщения без "SenderID".
     *
     * @return ISRequestHeader
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
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws MalformedURLException
     */
    public static void setPortSettings(Service service, Object port) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, MalformedURLException {

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, OtherFormat.USER_NAME);
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, OtherFormat.PASSWORD);
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, service.getWSDLDocumentLocation().toString());
    }
}
