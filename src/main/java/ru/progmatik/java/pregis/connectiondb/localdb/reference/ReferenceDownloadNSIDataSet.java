package ru.progmatik.java.pregis.connectiondb.localdb.reference;

import ru.progmatik.java.pregis.services.nsi.common.service.NsiListGroupEnum;

/**
 * Класс, описывает объект для работы с БД.
 * Объект для добавления справочника в список обновлений.
 */
public class ReferenceDownloadNSIDataSet {

    private Integer id;
    private final String code;
    private final String worldForExtract;
    private final NsiListGroupEnum nsiType;

    public ReferenceDownloadNSIDataSet(Integer id, String code, String worldForExtract, NsiListGroupEnum nsiType) {
        this.id = id;
        this.code = code;
        this.worldForExtract = worldForExtract;
        this.nsiType = nsiType;
    }

    public ReferenceDownloadNSIDataSet(String code, String worldForExtract, NsiListGroupEnum nsiType) {
        this.code = code;
        this.worldForExtract = worldForExtract;
        this.nsiType = nsiType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public String getWorldForExtract() {
        return worldForExtract;
    }

    public NsiListGroupEnum getNsiType() {
        return nsiType;
    }
}
