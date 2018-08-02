package ru.progmatik.java.pregis.signet.bcsign.exceptions;

/**
 * Исключение - элемент не найден в XML-документе.
 */
public class ElementNotFoundException extends Exception {
    public ElementNotFoundException(String message) {
        super(message);
    }
}
