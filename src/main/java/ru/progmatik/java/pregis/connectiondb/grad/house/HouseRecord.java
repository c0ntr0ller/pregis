package ru.progmatik.java.pregis.connectiondb.grad.house;

/**
 * Класс описывает здание
 */
public class HouseRecord {
    private final String fias;
    private final Integer grad_id;
    private final String addresString;

    public HouseRecord(String fias, Integer grad_id, String addresString) {
        this.fias = fias;
        this.grad_id = grad_id;
        this.addresString = addresString;
    }

    public String getFias() {
        return fias;
    }

    public Integer getGrad_id() {
        return grad_id;
    }

    public String getAddresString() {
        return addresString;
    }
}
