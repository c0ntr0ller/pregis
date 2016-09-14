package ru.progmatik.java.pregis.connectiondb.localdb.bills;

/**
 * Класс, описывает объект (реестр платежных документов) для хранения в БД.
 */
public class PaymentDocumentRegistryDataSet {

    private final int id; // Идентификатор записей.
    private final String numberPd; // Номер платежного документа.
    private final int month; // Месяц за который выгружен ПД.
    private final int year; // Год за который выгружен ПД.
    private final int abonId; // Идентификатор абонента в Граде.
    private final String accountGuid; // Идентификатор лицевого счета в ГИС ЖКХ.
    private String numberPdFromGisJkh; // Номер платежного документа присвоенный ГИС ЖКХ.
    private boolean archive; // Если ПД архивирован, то нужно оставить пометку.

    /**
     * Конструктор, создаёт новый объект со всеми значениями.
     *
     * @param id                 идентификатор записей.
     * @param numberPd           номер платежного документа.
     * @param numberPdFromGisJkh номер платежного документа присвоенный ГИС ЖКХ.
     * @param month              месяц за который выгружен ПД.
     * @param year               год за который выгружен ПД.
     * @param abonId             идентификатор абонента в Граде.
     * @param accountGuid        идентификатор лицевого счета в ГИС ЖКХ.
     * @param archive            если ПД архивирован.
     */
    public PaymentDocumentRegistryDataSet(int id, String numberPd, String numberPdFromGisJkh, int month,
                                          int year, int abonId, String accountGuid, boolean archive) {
        this.id = id;
        this.numberPd = numberPd;
        this.numberPdFromGisJkh = numberPdFromGisJkh;
        this.month = month;
        this.year = year;
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
    public PaymentDocumentRegistryDataSet(String numberPd, int month, int year, int abonId, String accountGuid) {
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

        PaymentDocumentRegistryDataSet that = (PaymentDocumentRegistryDataSet) o;

        if (id != that.id) return false;
        if (month != that.month) return false;
        if (year != that.year) return false;
        if (abonId != that.abonId) return false;
        if (archive != that.archive) return false;
        if (!numberPd.equals(that.numberPd)) return false;
        if (numberPdFromGisJkh != null ? !numberPdFromGisJkh.equals(that.numberPdFromGisJkh) : that.numberPdFromGisJkh != null)
            return false;
        return accountGuid.equals(that.accountGuid);

    }

    @Override
    public String toString() {

        return
                "{\nID: " + this.id +
                "\nNumber payment document: " + this.numberPd +
                "\nNumber from GIS JKH payment document: " + this.numberPdFromGisJkh +
                "\nMonth: " + this.month +
                "\nYear: " + this.year +
                "\nabonent id from GRAD: " + this.abonId +
                "\nAccountGUID from GIS JKH: " + this.accountGuid +
                "\nIs Archive: " + this.archive + "\n}";
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + numberPd.hashCode();
        result = 31 * result + (numberPdFromGisJkh != null ? numberPdFromGisJkh.hashCode() : 0);
        result = 31 * result + month;
        result = 31 * result + year;
        result = 31 * result + abonId;
        result = 31 * result + accountGuid.hashCode();
        result = 31 * result + (archive ? 1 : 0);
        return result;
    }

    public int getId() {
        return id;
    }

    public String getNumberPd() {
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
