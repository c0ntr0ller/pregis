package ru.progmatik.java.pregis.services.bills;

import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;

import java.util.Calendar;

/**
 * Класс, обрабатывает периоды отправки платежных документов.
 * Если предыдущий период открыт, он закроется и откроется новый по определенному дому.
 */
public final class HousePaymentPeriodHandler {

    private final AnswerProcessing answerProcessing;

    public HousePaymentPeriodHandler(final AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    /**
     * Метод, проверяет открыт предыдущий период, если открыт закроет и откроет новый.
     * @param fias код дома по ФИАС.
     */
    public Calendar getHousePaymentPeriod(final String fias) {
//        Пока заглушка сервис не работает
        return OtherFormat.getCalendarForPaymentDocument();
    }
}
