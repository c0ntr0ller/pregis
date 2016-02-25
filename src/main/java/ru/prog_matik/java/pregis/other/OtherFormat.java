package ru.prog_matik.java.pregis.other;

import ru.gosuslugi.dom.schema.integration._8_5_0.RequestHeader;
import ru.prog_matik.java.pregis.connectiondb.BaseOrganization;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Вспомогательный класс, для различных данных.
 */
public class OtherFormat {

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
     * Метод возвращает готовый заголовок запроса с "SenderID".
     * @return
     */
    public static RequestHeader getRequestHeader() {

        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setSenderID(BaseOrganization.getSenderID());
        requestHeader.setMessageGUID(getRandomGUID());
        requestHeader.setDate(getDateNow());

        return requestHeader;
    }
}
