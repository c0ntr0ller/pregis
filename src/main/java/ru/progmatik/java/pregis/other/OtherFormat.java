package ru.progmatik.java.pregis.other;

import org.apache.commons.codec.digest.DigestUtils;
import ru.gosuslugi.dom.schema.integration.base.ISRequestHeader;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;
import java.util.regex.Pattern;

import static ru.progmatik.java.pregis.other.OrgsSettings.getOrgPPAGUID;

/**
 * Вспомогательный класс, для различных данных.
 */
public final class OtherFormat {

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

        return getDateForXML(new Date());
    }

    /**
     * Метод, формирует дату пригодную для ГИС ЖКХ.
     * @param newDate новая дата.
     * @return сформированная дата.
     */
    public static XMLGregorianCalendar getDateForXML(final java.util.Date newDate) {

        final GregorianCalendar c = new GregorianCalendar();
        c.setTime(newDate);
        XMLGregorianCalendar date = null;

        try {
            date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Метод, возвращает месяц для платежного документа.
     * Выгрузка ПД осуществляется с 1 по 10 число месяца.
     * Соответственно берем предыдущий месяц за который выставляются ПД.
     * @return предыдущий месяц для платежного документа.
     */
    public static Calendar getCalendarForPaymentDocument() {

        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date()); // включить после испытаний.
        calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH) -1) % 11);
//        У месяца отсчет от 0, в нашем случае, нужен предыдущий месяц, так и оставлю.

//        Удалить после испытаний
//        calendar.set(Calendar.MONTH, Calendar.JUNE);
//        calendar.set(Calendar.YEAR, 2016);
//        return (getDateNow().getMonth() -1 ) % 12;
//        Пока тестовый месяц потом вернуть на корректный
        return calendar;
    }

    /**
     * Метод, возвращает текущий год.
     * Например для платежного документа.
     * @return текущий год.
     */
    public static int getYear() {

        return getDateNow().getYear();
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
    public static ISRequestHeader getISRequestHeader() {

        final ISRequestHeader isRequestHeader = new ISRequestHeader();
        isRequestHeader.setDate(getDateNow());
        isRequestHeader.setMessageGUID(getRandomGUID());

        return isRequestHeader;
    }

    /**
     * Метод возвращает готовый заголовок запроса с "SenderID".
     * @return RequestHeader готовый заголовок с содержанием "SenderID".
     */
    public static RequestHeader getRequestHeader() throws SQLException {

        final RequestHeader requestHeader = new RequestHeader();
//        замена SenderID на orgPPAGUID.
//        requestHeader.setSenderID(BaseOrganization.getSenderID());
        requestHeader.setOrgPPAGUID(getOrgPPAGUID());
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
    public static void setPortSettings(final Service service, final Object port) {

        final BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, OtherFormat.USER_NAME);
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, OtherFormat.PASSWORD);
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, service.getWSDLDocumentLocation().toString());
//        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
    }

    public static BigDecimal getBigDecimalTwo(BigDecimal value) {

//        ROUND_DOWN: Отбрасывание разряда
//        0.333  ->   0.33
//       -0.333  ->  -0.33

//        ROUND_HALF_UP: Округление вверх, если число после запятой >= .5
//        0.5  ->  1.0
//        0.4  ->  0.0

//        ROUND_HALF_DOWN: Округление вверх, если число после запятой > .5
//        0.5  ->  0.0
//        0.6  ->  1.0

//        MoneyType 2 знака после точки.
        if (value != null) {
            value = value.setScale(2, BigDecimal.ROUND_DOWN);
        }
        return value;
    }

//    /**
//     * Метод, получает orgPPAGUID - идентификатор зарегистрированной организации.
//     * @return orgPPAGUID.
//     * @throws SQLException могут возникнуть ошибки во время работы с БД.
//     */
//    public static String getOrgPPAGUID() throws SQLException {
//        return orgPPAGUID;
//    }
//
    /**
     * Метод, получает текст и возвращает MD5 его сумму.
     * @param text текст.
     * @return String MD5 сумма текста.
     */
    public static String getMD5(final String text) {
        return DigestUtils.md5Hex(text);
    }

    /**
     * Метод, возвращает обработанную строку в массиве с данными.
     *
     * @param data - строка с данными.
     * @return String - массив данных.
     */
    public static synchronized String[] getAllDataFromString(String data) {

        data = data + "|-1-"; // Если последний параметр пустой, то он в массив не попадет,
        // возникнут ошибки на ссылки на индексы массива.
        final String[] array = data.split(Pattern.quote("|"));
        final String[] newArray = new String[array.length];
        for (int i = 0; i < array.length; i++) {

            if (array[i] != null && !array[i].trim().isEmpty()) {
                newArray[i] = array[i];
            }
        }
        return newArray;
    }

    /**
     * Метод, проверяет строку.
     * @param checkString строка для проверки.
     * @return null или строку.
     */
    public static String checkNotNullString(final String checkString) {
        if (checkString == null || checkString.trim().isEmpty()) {
            return null;
        }
        return checkString;
    }

    /**
     * Метод, проверяет, если строка пустая или null, то вернет 0.
     *
     * @param textForNumber - строка.
     * @return String - преобразованная строка в "0", если она пустая.
     */
    public static String checkZero(final String textForNumber) {
        if (textForNumber == null || textForNumber.isEmpty()) {
            return "0";
        } else
            return textForNumber;
    }

    public static String extractLeadingNumber(String inString){
        if(inString == null) {return null;}
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inString.length(); i++) {
            if(!Character.isDigit(inString.charAt(i))) break;
            sb.append(inString.charAt(i));
        }
        return sb.toString();
    }
}
