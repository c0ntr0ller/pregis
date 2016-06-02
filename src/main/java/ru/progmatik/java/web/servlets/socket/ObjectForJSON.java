package ru.progmatik.java.web.servlets.socket;

/**
 * Класс, описывает JSON объект, который будет передовать клиент через websocket.
 */
public class ObjectForJSON {

    private final String command; // команда полученная от клиента
    private final String value; // значение, которое передал клиент

    public String getCommand() {
        return command;
    }

    public String getValue() {
        return value;
    }

    public ObjectForJSON(String command, String value) {

        this.command = command;
        this.value = value;
    }
}
