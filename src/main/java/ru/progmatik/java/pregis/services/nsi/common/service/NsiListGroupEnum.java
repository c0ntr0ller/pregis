package ru.progmatik.java.pregis.services.nsi.common.service;

/**
 * Класс перечисление, для справочников ГИС ЖКХ (ExportNsiItem).
 * Created by andryha on 31.05.2016.
 */
public enum  NsiListGroupEnum {

    NSI("NSI"),
    NSIRAO("NSIRAO");

    private String nsi;

    NsiListGroupEnum(String nsi) {
        this.nsi = nsi;
    }

    public String getNsi() {
        return nsi;
    }
}
