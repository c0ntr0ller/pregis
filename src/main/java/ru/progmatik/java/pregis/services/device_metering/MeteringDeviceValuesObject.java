package ru.progmatik.java.pregis.services.device_metering;

import ru.gosuslugi.dom.schema.integration.nsi_base.NsiRef;

import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Класс, описывает объект для манипуляции данных между сервисом ГИС ЖКХ и ГРАДом.
 */
public final class MeteringDeviceValuesObject {

    private final String meteringDeviceRootGUID;
    private final BigDecimal meteringValue; // Показания по тарифу 1, используется если ПУ однотарифный
    private final BigDecimal meteringValueTwo; // Показания по тарифу 2
    private final BigDecimal meteringValueThree; // Показания по тарифу 3
    private final Date meteringDate;
    private final GregorianCalendar meteringGregorianDate = new GregorianCalendar();
    private final NsiRef nsiRef;

    public MeteringDeviceValuesObject(String meteringDeviceRootGUID,
                                      BigDecimal meteringValue,
                                      BigDecimal meteringValueTwo,
                                      BigDecimal meteringValueThree,
                                      Date meteringDate,
                                      NsiRef nsiRef) {
        this.meteringDeviceRootGUID = meteringDeviceRootGUID;
        this.meteringValue = meteringValue.setScale(7, BigDecimal.ROUND_DOWN);
        this.meteringValueTwo = meteringValueTwo != null ? meteringValueTwo.setScale(7, BigDecimal.ROUND_DOWN) : null;
        this.meteringValueThree = meteringValueThree != null ? meteringValueThree.setScale(7, BigDecimal.ROUND_DOWN) : null;
        this.meteringDate = meteringDate;
        this.nsiRef = nsiRef;
        this.meteringGregorianDate.setTime(meteringDate);
    }

    public GregorianCalendar getMeteringGregorianDate() {
        return meteringGregorianDate;
    }

    public MeteringDeviceValuesObject(String meteringDeviceRootGUID,
                                      BigDecimal meteringValue,
                                      Date meteringDate,
                                      NsiRef nsiRef) {
        this.meteringDeviceRootGUID = meteringDeviceRootGUID;
        this.meteringValue = meteringValue.setScale(7, BigDecimal.ROUND_DOWN);
        this.meteringValueTwo = null;
        this.meteringValueThree = null;
        this.meteringDate = meteringDate;
        this.meteringGregorianDate.setTime(meteringDate);
        this.nsiRef = nsiRef;
    }

    @Override
    public String toString() {
        return "[" + meteringDeviceRootGUID + ", " + meteringValue + ", " + meteringValueTwo + ", " +
                meteringValueThree + ", " + meteringDate + ", [ nsiRef name: " +
                nsiRef.getName() + ", nsiRef code: " + nsiRef.getCode() + ", nsiRef GUID: " + nsiRef.getGUID() + "] ]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeteringDeviceValuesObject that = (MeteringDeviceValuesObject) o;

        if (!meteringDeviceRootGUID.equals(that.meteringDeviceRootGUID)) return false;
        if (!meteringValue.equals(that.meteringValue)) return false;
        if (meteringValueTwo != null ? !meteringValueTwo.equals(that.meteringValueTwo) : that.meteringValueTwo != null)
            return false;
        if (meteringValueThree != null ? !meteringValueThree.equals(that.meteringValueThree) : that.meteringValueThree != null)
            return false;
        return meteringDate.equals(that.meteringDate);

    }

    @Override
    public int hashCode() {
        int result = meteringValue.hashCode();
        result = 31 * result + (meteringValueTwo != null ? meteringValueTwo.hashCode() : 0);
        result = 31 * result + (meteringValueThree != null ? meteringValueThree.hashCode() : 0);
        result = 31 * result + meteringDate.hashCode();
        return result;
    }

    public String getMeteringDeviceRootGUID() {
        return meteringDeviceRootGUID;
    }

    public BigDecimal getMeteringValue() {
        return meteringValue;
    }

    public BigDecimal getMeteringValueTwo() {
        return meteringValueTwo;
    }

    public BigDecimal getMeteringValueThree() {
        return meteringValueThree;
    }

    public Date getMeteringDate() {
        return meteringDate;
    }

    public NsiRef getNsiRef() {
        return nsiRef;
    }
}
