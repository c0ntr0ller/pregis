package ru.progmatik.java.pregis.services.bills;

import ru.progmatik.java.pregis.other.AnswerProcessing;

/**
 * Класс, обрабатывает периоды отправки платежных документов.
 * Если предыдущий период открыт, он закроется и откроется новый по определенному дому.
 */
public class HousePaymentPeriodHandler {

    private final AnswerProcessing answerProcessing;

    public HousePaymentPeriodHandler(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    /**
     * Метод, проверяет открыт предыдущий период, если открыт закроет и откроет новый.
     * @param fias код дома по ФИАС.
     */
    public void checkHousePaymentPeriod(String fias) {


    }
}
