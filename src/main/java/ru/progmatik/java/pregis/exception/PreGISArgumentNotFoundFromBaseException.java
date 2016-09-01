package ru.progmatik.java.pregis.exception;

/**
 * Класс исключений, создан для выброса ошибок, при условии, если из БД не получены ожидаемые данные.
 */
public class PreGISArgumentNotFoundFromBaseException extends Exception {

    public PreGISArgumentNotFoundFromBaseException(String message) {
        super(message);
    }

    public PreGISArgumentNotFoundFromBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
