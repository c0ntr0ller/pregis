package ru.progmatik.java.pregis.model;

/**
 * Класс описывает здание
 */
public class HouseRecord {
    private final String fias;
    private final Integer grad_id;
    private final String addresString;
    private final String addresStringShort;
    private final String houseUniqNum;

    public HouseRecord(String fias, Integer grad_id, String addresString, String houseUniqNum, String addresStringShort) {
        this.fias = fias;
        this.grad_id = grad_id;
        this.addresString = addresString;
        this.houseUniqNum = houseUniqNum;
        this.addresStringShort = addresStringShort;
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

    public String getAddresStringShort() {
        return addresStringShort;
    }

    public String getHouseUniqNum() {
        return houseUniqNum;
    }
}
