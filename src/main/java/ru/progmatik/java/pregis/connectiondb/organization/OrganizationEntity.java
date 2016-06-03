package ru.progmatik.java.pregis.connectiondb.organization;

/**
 * Класс, описывает объект, хранящийся в БД.
 * Данные организации.
 * Пока не нужен!
 */
public class OrganizationEntity {

    private final int id;
    private final String fullName;
    private final String shortName;
    private final String ogrn;
    private final String inn;
    private final String kpp;
    private final String senderId;
    private final String orgRootEntityGuid;

    public OrganizationEntity(int id, String fullName, String shortName, String ogrn, String inn, String kpp,
                              String senderId, String orgRootEntityGuid) {
        this.id = id;
        this.fullName = fullName;
        this.shortName = shortName;
        this.ogrn = ogrn;
        this.inn = inn;
        this.kpp = kpp;
        this.senderId = senderId;
        this.orgRootEntityGuid = orgRootEntityGuid;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getOgrn() {
        return ogrn;
    }

    public String getInn() {
        return inn;
    }

    public String getKpp() {
        return kpp;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getOrgRootEntityGuid() {
        return orgRootEntityGuid;
    }
}
