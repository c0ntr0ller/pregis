package ru.progmatik.java.pregis.connectiondb.grad.bills;

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
    private final BigDecimal summa; // общая сумма платежного документа.
    private final String accountNumber; // Номер лицевого счета абонента.
    private final String accountGuid; // Идентификатор лицевого счета в ГИС ЖКХ.

    private String uniqueNumber; // Номер платежного документа присвоенный ГИС ЖКХ.
    private String guid; // guid платежного документа присвоенный ГИС ЖКХ.
    private boolean archive; // Если ПД архивирован, то нужно оставить пометку.

    /**
     * Конструктор, создаёт новый объект со всеми значениями.
     *
     * @param id                 идентификатор записей.
     * @param numberPd           номер платежного документа.
     * @param uniqueNumber номер платежного документа присвоенный ГИС ЖКХ.
     * @param month              месяц за который выгружен ПД.
     * @param year               год за который выгружен ПД.
     * @param summa              общая сумма платежного документа.
     * @param abonId             идентификатор абонента в Граде.
     * @param accountGuid        идентификатор лицевого счета в ГИС ЖКХ.
     * @param archive            если ПД архивирован.
     */
    public PaymentDocumentRegistryDataSet(int id, int numberPd, String uniqueNumber, String guid, int month,
                                          int year, BigDecimal summa, int abonId, String accountNumber,
                                          String accountGuid, boolean archive) {
        this.id = id;
        this.numberPd = numberPd;
        this.uniqueNumber = uniqueNumber;
        this.guid = guid;
        this.month = month;
        this.year = year;
        this.summa = summa.setScale(2, BigDecimal.ROUND_DOWN);
        this.abonId = abonId;
        this.accountNumber = accountNumber;
        this.accountGuid = accountGuid;
        this.archive = archive;
    }

    /**
     * Конструктор, создаёт новый объект со всеми значениями.
     *
     * @param numberPd    номер платежного документа.
     * @param month       месяц за который выгружен ПД.
     * @param year        год за который выгружен ПД.
     * @param summa общая сумма платежного документа.
     * @param abonId      идентификатор абонента в Граде.
     * @param accountNumber Номер лицевого счета абонента.
     * @param accountGuid идентификатор лицевого счета в ГИС ЖКХ.
     */
    public PaymentDocumentRegistryDataSet(int numberPd, int month, int year, BigDecimal summa, int abonId,
                                          String accountNumber, String accountGuid) {
        this.id = -1;
        this.numberPd = numberPd;
        this.month = month;
        this.year = year;
        this.summa = summa != null ? summa.setScale(2, BigDecimal.ROUND_DOWN) : null;
        this.accountNumber = accountNumber;
        this.abonId = abonId;
        this.accountGuid = accountGuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentDocumentRegistryDataSet that = (PaymentDocumentRegistryDataSet) o;

        if (id != that.id) return false;
        if (numberPd != that.numberPd) return false;
        if (month != that.month) return false;
        if (year != that.year) return false;
        if (abonId != that.abonId) return false;
        if (archive != that.archive) return false;
        if (!summa.equals(that.summa)) return false;
        if (!accountNumber.equals(that.accountNumber)) return false;
        if (!accountGuid.equals(that.accountGuid)) return false;
        if (uniqueNumber != null ? !uniqueNumber.equals(that.uniqueNumber) : that.uniqueNumber != null) return false;
        return guid != null ? guid.equals(that.guid) : that.guid == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + numberPd;
        result = 31 * result + month;
        result = 31 * result + year;
        result = 31 * result + abonId;
        result = 31 * result + summa.hashCode();
        result = 31 * result + accountNumber.hashCode();
        result = 31 * result + accountGuid.hashCode();
        result = 31 * result + (uniqueNumber != null ? uniqueNumber.hashCode() : 0);
        result = 31 * result + (guid != null ? guid.hashCode() : 0);
        result = 31 * result + (archive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {

        return
                "{\nID: " + this.id +
                        "\nNumber payment document: " + this.numberPd +
                        "\nNumber from GIS JKH payment document: " + this.uniqueNumber +
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

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {

        this.uniqueNumber = uniqueNumber;
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

    public int getAbonId() {
        return abonId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountGuid() {
        return accountGuid;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }
}
