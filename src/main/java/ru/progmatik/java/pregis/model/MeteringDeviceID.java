package ru.progmatik.java.pregis.model;

/**
 * класс описывает идентифицирующую информацию по ПУ - его MeteringDeviceVersionGUID из ГИС ЖКХ и meterId из Град
 */
public class MeteringDeviceID {
    private String meteringDeviceRootGUID;
    private String meteringDeviceVersionGUID;
    private Integer meterGradId;

    public MeteringDeviceID(String meteringDeviceRootGUID, String meteringDeviceVersionGUID, Integer meterGradId) {
        this.meteringDeviceRootGUID = meteringDeviceRootGUID;
        this.meteringDeviceVersionGUID = meteringDeviceVersionGUID;
        this.meterGradId = meterGradId;
    }

    public MeteringDeviceID(){};

    public String getMeteringDeviceRootGUID() {
        return meteringDeviceRootGUID;
    }

    public void setMeteringDeviceRootGUID(String meteringDeviceRootGUID) {
        this.meteringDeviceRootGUID = meteringDeviceRootGUID;
    }

    public String getMeteringDeviceVersionGUID() {
        return meteringDeviceVersionGUID;
    }

    public void setMeteringDeviceVersionGUID(String meteringDeviceVersionGUID) {
        this.meteringDeviceVersionGUID = meteringDeviceVersionGUID;
    }

    public Integer getMeterGradId() {
        return meterGradId;
    }

    public void setMeterGradId(Integer meterGradId) {
        this.meterGradId = meterGradId;
    }
}

