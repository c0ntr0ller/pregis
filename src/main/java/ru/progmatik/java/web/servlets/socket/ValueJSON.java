package ru.progmatik.java.web.servlets.socket;

/**
 * Класс, служит для передачи данных в JSON формате (информация о домах).
 */
public class ValueJSON {
    private final String value;
    private final String name;

    public ValueJSON(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ValueJSON{" +
                "value='" + value + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}