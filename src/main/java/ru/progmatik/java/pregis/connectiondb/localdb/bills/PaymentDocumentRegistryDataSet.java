package ru.progmatik.java.pregis.connectiondb.localdb.bills;

import java.math.BigDecimal;

/**
 * Класс, описывает объект (реестр платежных документов) для хранения в БД.
 */
public class PaymentDocumentRegistryDataSet {

    private final int id; // Идентификатор записей.
    private final int numberPd; // Номер платежного документа.
    private final int month; // Месяц за который выгружен ПД.
    private final int year; // Год за который выгружен ПД.
    private final int abonId; // Идентификатор абонента в Граде.
    private final String accountGuid; // Идентификатор лицевого счета в ГИС ЖКХ.
    private String numberPdFromGisJkh; // Номер платежного документа присвоенный ГИС ЖКХ.
    private BigDecimal summa; // общая сумма платежного документа.
    private boolean archive; // Если ПД архивирован, то нужно оставить пометку.



    /**
     * Конструктор, создаёт новый объект со всеми значениями.
     *
     * @param id                 идентификатор записей.
     * @param numberPd           номер платежного документа.
     * @param numberPdFromGisJkh номер платежного документа присвоенный ГИС ЖКХ.
     * @param month              месяц за который выгружен ПД.
     * @param year               год за который выгружен ПД.
     * @param summa              общая сумма платежного документа.
     * @param abonId             идентификатор абонента в Граде.
     * @param accountGuid        идентификатор лицевого счета в ГИС ЖКХ.
     * @param archive            если ПД архивирован.
     */
    public PaymentDocumentRegistryDataSet(int id, int numberPd, String numberPdFromGisJkh, int month,
                                          int year, BigDecimal summa, int abonId, String accountGuid, boolean archive) {
        this.id = id;
        this.numberPd = numberPd;
        this.numberPdFromGisJkh = numberPdFromGisJkh;
        this.month = month;
        this.year = year;
        this.summa = summa;
        this.abonId = abonId;
        this.accountGuid = accountGuid;
        this.archive = archive;
    }

    /**
     * Конструктор, создаёт новый объект со всеми значениями.
     *
     * @param numberPd    номер платежного документа.
     * @param month       месяц за который выгружен ПД.
     * @param year        год за который выгружен ПД.
     * @param abonId      идентификатор абонента в Граде.
     * @param accountGuid идентификатор лицевого счета в ГИС ЖКХ.
     */
    public PaymentDocumentRegistryDataSet(int numberPd, int month, int year, int abonId, String accountGuid) {
        this.id = -1;
        this.numberPd = numberPd;
        this.month = month;
        this.year = year;
        this.abonId = abonId;
        this.accountGuid = accountGuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentDocumentRegistryDataSet dataSet = (PaymentDocumentRegistryDataSet) o;

        if (id != dataSet.id) return false;
        if (numberPd != dataSet.numberPd) return false;
        if (month != dataSet.month) return false;
        if (year != dataSet.year) return false;
        if (abonId != dataSet.abonId) return false;
        if (archive != dataSet.archive) return false;
        if (numberPdFromGisJkh != null ? !numberPdFromGisJkh.equals(dataSet.numberPdFromGisJkh) : dataSet.numberPdFromGisJkh != null)
            return false;
        if (!summa.equals(dataSet.summa)) return false;
        return accountGuid != null ? accountGuid.equals(dataSet.accountGuid) : dataSet.accountGuid == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + numberPd;
        result = 31 * result + (numberPdFromGisJkh != null ? numberPdFromGisJkh.hashCode() : 0);
        result = 31 * result + month;
        result = 31 * result + year;
        result = 31 * result + summa.hashCode();
        result = 31 * result + abonId;
        result = 31 * result + (accountGuid != null ? accountGuid.hashCode() : 0);
        result = 31 * result + (archive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {

        return
                "{\nID: " + this.id +
                        "\nNumber payment document: " + this.numberPd +
                        "\nNumber from GIS JKH payment document: " + this.numberPdFromGisJkh +
                        "\nMonth: " + this.month +
                        "\nYear: " + this.year +
                        "\nSumma: " + this.summa +
                        "\nabonent id from GRAD: " + this.abonId +
                        "\nAccountGUID from GIS JKH: " + this.accountGuid +
                        "\nIs Archive: " + this.archive + "\n}";
    }

    public int getId() {
        return id;
    }

    public int getNumberPd() {
        return numberPd;
    }

    public String getNumberPdFromGisJkh() {
        return numberPdFromGisJkh;
    }

    public void setNumberPdFromGisJkh(String numberPdFromGisJkh) {

        this.numberPdFromGisJkh = numberPdFromGisJkh;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public BigDecimal getSumma() {
        return summa;
    }

    public void setSumma(BigDecimal summa) {
        this.summa = summa;
    }

    public int getAbonId() {
        return abonId;
    }

    public String getAccountGuid() {
        return accountGuid;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }
}