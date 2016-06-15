package ru.progmatik.java.pregis.connectiondb.grad.account.datasets;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, проверяет, верные значения указаны или неверные.
 */
public class CheckValue {

    /**
     * Метод, проверка на верность данных.
     * Номер ЛС (иной идентификатор потребителя).
     * Обязательный.
     * Строковое поле, номер лицевого счета, заданный организацией.
     *
     * @param checkNumberLS - значение для проверки.
     * @return String - текст сообщения ошибки или null.
     */
    public final String checkNumberLS(String checkNumberLS) {

        if (checkNumberLS == null || checkNumberLS.isEmpty()) {
            return "Вы не заполнили обязательное поле. Без него невозможно корректно сформировать документ.";
        }
        return null;
    }

    /**
     * Метод, для классов enum.
     * Обязательный.
     * Выберите из списка обязательный атрибут.
     *
     * @return String - текст сообщения ошибки или null.
     */
    public final String checkAnyList() {
        return "Выберите из списка обязательный атрибут.";
    }

    /**
     * Метод, проверка на верность данных.
     * Для "Фамилия", "Имя", "Отчество".
     * Строковое поле, 60 символов.
     *
     * @param checkText - значение для проверки.
     * @return String - текст сообщения ошибки или null.
     */
    public final String checkLength60(String checkText) {

        if (checkText != null)
            if (checkText.length() > 60) {
                return "Ячейка должна содержать значение не больше чем 60 символов";
            }
        return null;
    }

    /**
     * Метод, проверка на верность данных.
     * СНИЛС потребителя.
     * Строковое поле в формате «123-456-789 01».
     *
     * @param checkSNILS - значение для проверки.
     * @return String - текст сообщения ошибки или null.
     */
    public final String checkSNILS(String checkSNILS) {

        if (checkSNILS != null && !checkSNILS.isEmpty()) {
            Pattern patternCheck = Pattern.compile("^(\\d\\d\\d)\\-(\\d\\d\\d)\\-(\\d\\d\\d) (\\d\\d)$");
            Matcher matcherCheck = patternCheck.matcher(checkSNILS);
            if (checkSNILS.length() > 14 || !matcherCheck.matches()) {
                return "Ячейка должна содержать значение длиной не больше чем 14 символов" +
                        "и иметь формат «123-456-789 01»";
            }
        }
        return null;
    }

    /**
     * Метод, проверка на верность данных.
     * Для "Номер документа, удостоверяющего личность", "Серия документа, удостоверяющего личность".
     * Строковое поле, до 45 символов включительно, заполняется, если указаны «Вид документа, удостоверяющего личность».
     *
     * @param checkText - значение для проверки.
     * @return String - текст сообщения ошибки или null.
     */
    public final String checkLength45(String checkText) {

        if (checkText != null)
            if (checkText.length() > 45) {
                return "Ячейка должна содержать значение длиной не больше чем 45 символов";
            }
        return null;
    }

    /**
     * Метод, проверка на верность данных.
     * ОГРН/ОГРНИП потребителя (для ЮЛ и ИП).
     * Целочисленное поле строго 13 или 15 символов, обязательное поле,
     * если потребитель - индивидуальный предприниматель или юридическое лицо.
     *
     * @param checkOgrtnOrOgrnip - значение для проверки.
     * @return String - текст сообщения ошибки или null.
     */
    public final String checkOgrnOrOgrnip(long checkOgrtnOrOgrnip) {

        if (checkOgrtnOrOgrnip > 0)
            if (checkOgrtnOrOgrnip < 1000000000000L || checkOgrtnOrOgrnip > 999999999999999L ||
                    (checkOgrtnOrOgrnip > 9999999999999L && checkOgrtnOrOgrnip < 100000000000000L)) {
                return "Ячейка должна содержать целочисленное поле строго 13 или 15 символов.\n" +
                        "Обязательное поле, если потребитель - индивидуальный предприниматель или юридическое лицо.";
            }
        return null;
    }

    /**
     * Метод, проверка на верность данных.
     * КПП нанимателя (для ОП).
     * Целочисленное поле строго 9 символов, обязательное поле,
     * если наниматель – обособленное подразделение юридического лица.
     *
     * @param checKpp - значение для проверки.
     * @return String - текст сообщения ошибки или null.
     */
    public final String checkKpp(int checKpp) {

        if (checKpp > 0)
            if (checKpp < 100000000 || checKpp > 999999999) {
                return "В этой ячейки должно храниться целочисленное поле строго 9 символов, " +
                        "обязательное поле, если наниматель – обособленное подразделение юридического лица.";
            }
        return null;
    }

    /**
     * Метод, проверка на верность данных.
     * Обязательный.
     * Для "Общая площадь".
     * Цифровое поле, 20 символов, включая 4 после запятой.
     *
     * @param checkValue - значение для проверки.
     * @return String - текст сообщения ошибки или null.
     */
    public final String checkValue20and4Sure(double checkValue) {

        if (checkValue == 0) {
            return "Вы не заполнили обязательное поле.\n" +
                    "Цифровое поле, 20 символов, включая 4 после запятой.";
        } else if (99999999999999999999.9 < checkValue) {
            return "Должна содержать цифровое поле, 20 символов, включая 4 после запятой.";
        }
        return null;
    }
//    По просьбе Кати меняю на double
//    public final String checkValue20and4Sure(BigDecimal checkValue) {
//
//        if (checkValue == null || checkValue.signum() == 0) {
//            return "Вы не заполнили обязательное поле.\n" +
//                    "Цифровое поле, 20 символов, включая 4 после запятой.";
//        } else if (checkValue.compareTo(new BigDecimal("99999999999999999999.9999")) > 0) {
//            return "Должна содержать цифровое поле, 20 символов, включая 4 после запятой.";
//        }
//        return null;
//    }

    /**
     * Метод, проверка на верность данных.
     * Для "Жилая площадь", "Отапливаемая площадь".
     * Цифровое поле, 20 символов, включая 4 после запятой.
     *
     * @param checkValue - значение для проверки.
     * @return String - текст сообщения ошибки или null.
     */
    public final String checkValue20and4(double checkValue) {

        if (checkValue != 0)
            if (99999999999999999999.9 < checkValue) {
                return "Должна содержать цифровое поле, 20 символов, включая 4 после запятой.";
            }
        return null;
    }

    public final String checkAmountLiving(int checkAmountLiving) {

        if (checkAmountLiving > 9999) {
            return "Цифровое поле должно быть размером не более 4 символов.";
        }
        return null;
    }

    /**
     * Метод, проверяет значение.
     * Код дома по ФИАС должен составлять длину равную 36 символам.
     *
     * @param checkText - значение для проверки.
     * @return String, null - возвращает строку с ошибкой или null.
     */
    public final String checkFIAS(String checkText) {

        if (checkText != null && !checkText.isEmpty())
            if (checkText.length() != 36) {
                return "Код дома по ФИАС должен составлять длину равную 36 символам\n" +
                        "Строковое поле в формате GUID, заполняется если не указано значение \n" +
                        "«Идентификатор дома, помещения, комнаты, присвоенный ГИС ЖКХ»";
            }
        return null;
    }

    /**
     * Метод, проверяет значение.
     * Для "Номер помещения", "Номер комнаты".
     * Строковое поле до 255 символов, заполняется если не указано значение
     * «Идентификатор дома, помещения, комнаты, присвоенный ГИС ЖКХ».
     *
     * @param numberRooms - значение для проверки.
     * @return String, null - возвращает строку с ошибкой или null.
     */
    public final String checkNumberRooms(String numberRooms) {
        if (numberRooms != null)
            if (numberRooms.length() > 255) {
                return "Строковое поле до 255 символов, заполняется если не указано значение " +
                        "«Идентификатор дома, помещения, комнаты, присвоенный ГИС ЖКХ»";
            }
        return null;
    }

    /**
     * Метод, проверяет значение.
     * В версии 8.7.2 сделали не обязательным.
     * Доля внесения платы, размер доли в %.
     *
     * @param sharePay - значение для проверки.
     * @return String, null - возвращает строку с ошибкой или null.
     */
    public final String checkSharePay(int sharePay) {

//        if (sharePay == 0) {
//            return "Вы не заполнили обязательное поле.";
//        } else
        if (sharePay > 100) {
            return "Допускается ввод положительных целых значений от 1 до 100.";
        }
        return null;
    }
}
