package ru.progmatik.java.pregis.connectiondb.grad.account.datasets;

/**
 * Класс перечисления, содержит Да или Нет.
 */
public enum AnswerYesOrNo {

    YES("Да"),
    NO("Нет");

    private String answer;

    AnswerYesOrNo(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public static AnswerYesOrNo getAnswer(String answer) {

        AnswerYesOrNo[] answers = AnswerYesOrNo.values();
        for (AnswerYesOrNo answerYesOrNo : answers) {
            if (answer.equalsIgnoreCase(answerYesOrNo.getAnswer()))
                return answerYesOrNo;
        }
        return null;
    }

}
