package ru.progmatik.java.pregis.connectiondb.localdb.reference;

import ru.progmatik.java.pregis.services.nsi.common.service.NsiListGroupEnum;

/**
 * Класс, описывает объект для работы с БД.
 * Объект для добавления справочника в список обновлений.
 */
public class ReferenceDownloadNSIDataSet {

    private final String code;
    private Integer id;
    private String worldForExtract;
    private NsiListGroupEnum nsiType;

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

    public ReferenceDownloadNSIDataSet(Integer id, String code, String worldForExtract) {
        this.id = id;
        this.code = code;
        this.worldForExtract = worldForExtract;
    }

    public ReferenceDownloadNSIDataSet(String code, String worldForExtract) {
        this.code = code;
        this.worldForExtract = worldForExtract;
    }

    public ReferenceDownloadNSIDataSet(Integer id, String code, NsiListGroupEnum nsiType) {
        this.id = id;
        this.code = code;
        this.nsiType = nsiType;
    }

    public ReferenceDownloadNSIDataSet(String code, NsiListGroupEnum nsiType) {
        this.code = code;
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

    public void setWorldForExtract(String worldForExtract) {
        this.worldForExtract = worldForExtract;
    }

    public NsiListGroupEnum getNsiType() {
        return nsiType;
    }

    public void setNsiType(NsiListGroupEnum nsiType) {
        this.nsiType = nsiType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReferenceDownloadNSIDataSet that = (ReferenceDownloadNSIDataSet) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (worldForExtract != null ? !worldForExtract.equals(that.worldForExtract) : that.worldForExtract != null)
            return false;
        return nsiType == that.nsiType;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (worldForExtract != null ? worldForExtract.hashCode() : 0);
        result = 31 * result + (nsiType != null ? nsiType.hashCode() : 0);
        return result;
    }
}
