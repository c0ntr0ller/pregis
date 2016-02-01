package ru.prog_matik.java.pregis.other;

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
}
