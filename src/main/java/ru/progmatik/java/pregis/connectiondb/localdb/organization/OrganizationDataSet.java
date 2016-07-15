package ru.progmatik.java.pregis.connectiondb.localdb.organization;

/**
 * Класс, описывает объект, хранящийся в БД.
 * Данные организации.
 * Будьте внимательны, при вызове метода equals и hashCode переменная id не учитывается.
 */
public class OrganizationDataSet {

    private Integer id;
    private String fullName;
    private String shortName;
    private String ogrn;
    private String inn;
    private String kpp;
    private String orgRootEntityGUID;
    private String orgPPAGUID;
    private String role;
    private Integer gradId;
    private String description;

    public OrganizationDataSet(Integer id, String fullName, String shortName, String ogrn, String inn, String kpp,
                               String orgRootEntityGUID, String orgPPAGUID, String role, Integer gradId, String description) {
        this.id = id;
        this.fullName = fullName;
        this.shortName = shortName;
        this.ogrn = ogrn;
        this.inn = inn;
        this.kpp = kpp;
        this.orgRootEntityGUID = orgRootEntityGUID;
        this.orgPPAGUID = orgPPAGUID;
        this.role = role;
        this.gradId = gradId;
        this.description = description;
    }

    public OrganizationDataSet(String fullName, String shortName, String ogrn, String inn, String kpp,
                               String orgRootEntityGUID, String orgPPAGUID, String role, Integer gradId, String description) {
        this.fullName = fullName;
        this.shortName = shortName;
        this.ogrn = ogrn;
        this.inn = inn;
        this.kpp = kpp;
        this.orgRootEntityGUID = orgRootEntityGUID;
        this.orgPPAGUID = orgPPAGUID;
        this.role = role;
        this.gradId = gradId;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getOrgRootEntityGUID() {
        return orgRootEntityGUID;
    }

    public void setOrgRootEntityGUID(String orgRootEntityGUID) {
        this.orgRootEntityGUID = orgRootEntityGUID;
    }

    public String getOrgPPAGUID() {
        return orgPPAGUID;
    }

    public void setOrgPPAGUID(String orgPPAGUID) {
        this.orgPPAGUID = orgPPAGUID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getGradId() {
        return gradId;
    }

    public void setGradId(Integer gradId) {
        this.gradId = gradId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationDataSet that = (OrganizationDataSet) o;

        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (ogrn != null ? !ogrn.equals(that.ogrn) : that.ogrn != null) return false;
        if (inn != null ? !inn.equals(that.inn) : that.inn != null) return false;
        if (kpp != null ? !kpp.equals(that.kpp) : that.kpp != null) return false;
        if (orgRootEntityGUID != null ? !orgRootEntityGUID.equals(that.orgRootEntityGUID) : that.orgRootEntityGUID != null)
            return false;
        if (orgPPAGUID != null ? !orgPPAGUID.equals(that.orgPPAGUID) : that.orgPPAGUID != null) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return gradId != null ? gradId.equals(that.gradId) : that.gradId == null;

    }

    @Override
    public int hashCode() {
        int result = fullName != null ? fullName.hashCode() : 0;
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (ogrn != null ? ogrn.hashCode() : 0);
        result = 31 * result + (inn != null ? inn.hashCode() : 0);
        result = 31 * result + (kpp != null ? kpp.hashCode() : 0);
        result = 31 * result + (orgRootEntityGUID != null ? orgRootEntityGUID.hashCode() : 0);
        result = 31 * result + (orgPPAGUID != null ? orgPPAGUID.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (gradId != null ? gradId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
