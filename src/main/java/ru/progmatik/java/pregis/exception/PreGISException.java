package ru.progmatik.java.pregis.exception;

import javax.xml.ws.WebFault;

@WebFault
public class PreGISException extends Exception {

    public PreGISException(String errorMessage) {
        super(errorMessage);
    }
}
