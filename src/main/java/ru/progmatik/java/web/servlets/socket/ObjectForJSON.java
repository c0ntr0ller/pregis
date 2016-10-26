package ru.progmatik.java.web.servlets.socket;

/**
 * Класс, описывает JSON объект, который будет передовать клиент через websocket.
 */
public class ObjectForJSON {

    private final String command; // команда полученная от клиента
    private final String id; // ид разных объектов, возможно дома, абонентаи т.д.
    private final String value; // значение, которое передал клиент

    public ObjectForJSON(String command, String id, String value) {

        this.command = command;
        this.id = id;
        this.value = value;
    }

    public String getCommand() {
        return command;
    }

    public String getValue() {
        return value;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ObjectForJSON{" +
                "command='" + command + '\'' +
                ", id='" + id + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
