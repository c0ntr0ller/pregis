package ru.progmatik.java.pregis.signet.bcsign.exceptions;

/**
 * Исключение - ошибка при проверке подписи.
 */
public class SignatureVerificationException extends Exception {
    public SignatureVerificationException(String message) {
        super(message);
    }

    public SignatureVerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
