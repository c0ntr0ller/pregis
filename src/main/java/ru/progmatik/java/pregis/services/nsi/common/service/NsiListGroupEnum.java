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

    public static NsiListGroupEnum getNsiGroup(String nsiGroup) {

        NsiListGroupEnum[] nsiGroups = NsiListGroupEnum.values();
        for (NsiListGroupEnum nsiListGroupEnum : nsiGroups) {
            if (nsiGroup.equalsIgnoreCase(nsiListGroupEnum.getNsi()))
                return nsiListGroupEnum;
        }
        return null;
    }

    public String getNsi() {
        return nsi;
    }
}
