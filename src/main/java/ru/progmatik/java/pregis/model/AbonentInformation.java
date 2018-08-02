package ru.progmatik.java.pregis.model;

import java.util.Date;

/**
 * Класс, содержит даные листа "Основные сведения".
 */
public class AbonentInformation {
    /**
     * ИД абонента в системе Град
     * ОН НЕ ВЫГРУЖАЕТСЯ!
     */
    private int gradID;

    /**
     * Номер ЛС (иной идентификатор потребителя).
     * Обязательный.
     * Строковое поле, номер лицевого счета, заданный организацией.
     */
    private String numberLS;

    /**
     * Появилось в версии файлов 9.0.1.2.
     * Тип лицевого счета.
     * Возможные значения: ЛС УО, ЛС РСО, ЛС КР.
     * Обязательный.
     */
    private String typeLS;

    /**
     * Является нанимателем?
     * Обязательный.
     * Значение из выпадающего списка, возможные значения: «да», «нет».
     */
    private AnswerYesOrNo employer;

    /**
     * Фамилия.
     * См. раздел «Идентификация потребителей».
     * Строковое поле, 60 символов.
     */
    private String surname;

    /**
     * Имя.
     * См. раздел «Идентификация потребителей».
     * Строковое поле, 60 символов.
     */
    private String name;

    /**
     * Отчество.
     * См. раздел «Идентификация потребителей».
     * Строковое поле, 60 символов.
     */
    private String middleName;

    /**
     * СНИЛС потребителя.
     * См. раздел «Идентификация потребителей».
     * Строковое поле в формате «123-456-789 01».
     */
    private String snils;

    /**
     * Вид документа, удостоверяющего личность.
     * См. раздел «Идентификация потребителей».
     * Выбор из выпадающего списка, заполняется, если указаны «Номер документа, удостоверяющего личность»
     * и «Серия документа, удостоверяющего личность»
     */
    private DocumentType typeDocument;

    /**
     * Номер документа, удостоверяющего личность.
     * См. раздел «Идентификация потребителей».
     * Строковое поле, до 45 символов включительно, заполняется, если указаны «Вид документа, удостоверяющего личность»
     * и «Серия документа, удостоверяющего личность»
     */
    private String numberDocumentIdentity;

    /**
     * Серия документа, удостоверяющего личность.
     * См. раздел «Идентификация потребителей».
     * Строковое поле, до 45 символов включительно, заполняется, если указаны «Вид документа, удостоверяющего личность»
     * и «Номер документа, удостоверяющего личность».
     */
    private String seriesDocumentIdentity;

    /**
     * Дата документа, удостоверяющего личность.
     * См. раздел «Идентификация потребителей».
     * Поле типа «Дата» (ДД.ММ.ГГГГ).
     */
    private Date dateDocumentIdentity;

    /**
     * ОГРН/ОГРНИП потребителя (для ЮЛ и ИП).
     * См. раздел «Идентификация потребителей».
     * Целочисленное поле строго 13 или 15 символов, обязательное поле,
     * если потребитель - индивидуальный предприниматель или юридическое лицо
     */
    private String ogrnOrOgrnip;

    /**
     * КПП нанимателя (для ОП).
     * См. раздел «Идентификация потребителей».
     * Целочисленное поле строго 9 символов, обязательное поле,
     * если наниматель – обособленное подразделение юридического лица.
     */
    private String kpp;

    /**
     * Доля внесения платы, размер доли в %.
     * Обязательный.
     * Допускается ввод положительных целых значений от 1 до 100.
     */
    private int sharePay;

    /**
     * Общая площадь, кв. м.
     * Обязательный.
     * Цифровое поле, 20 символов, включая 4 после запятой.
     */
    private double totalArea;
//    по просьбе Кати, замена на double, в описании указано Цифра 20 символов, 4 знака после зп.
//    Такое число можно получить только BigDecimal, при переносе в Excel отображается не корректно как число,
//    приходиться сохранять как текст, но при импорте возникает ошибка.
//    private BigDecimal totalArea;

    /**
     * Жилая площадь, кв. м.
     * Необязательный.
     * Цифровое поле, 20 символов, включая 4 после запятой.
     */
    private double livingSpace;
//    по просьбе Кати, замена на double
//    private BigDecimal livingSpace;

    /**
     * Отапливаемая  площадь, кв. м.
     * Необязательный.
     * Цифровое поле, 20 символов, включая 4 после запятой.
     */
    private double headtedArea;
//    по просьбе Кати, замена на double
//    private BigDecimal headtedArea;

    /**
     * Количество проживающих, чел.
     * Необязательный.
     * Цифровое поле, до 4 символов.
     */
    private int amountLiving;

    /**
     * ИД помещения ГИС
     */
    private String premisesGUID;
    /**
     * ИД комнаты ГИС
     */
    private String livingRoomGUID;

    /**
     * Идентификатор ЛС в ГИС ЖКХ
     */
    private String accountGUID;

    /**
     * Единый лицевой счет ГИС
     */
    private String unifiedAccountNumber;

    /**
     * Идентификатор жилищно-коммунальной услуги (на самом деле это ЛС для конкретной организации, так как Ланит уроды)
     */
    private String serviceID;

    /**
     * Идентификатор версии записи в реестре организаций ГИС
     */
    private String orgVersionGUID;


    /**
     * Получить номер ЛС (иной идентификатор потребителя).
     * Обязательный.
     * Строковое поле, номер лицевого счета, заданный организацией.
     */
    public String getNumberLS() {
        return numberLS;
    }

    /**
     * Задать номер ЛС (иной идентификатор потребителя). Внимание!!! тут используется ИД абонента в ГРАД!!!
     * Обязательный.
     * Строковое поле, номер лицевого счета, заданный организацией.
     */
    public void setNumberLS(String numberLS) {
        this.numberLS = numberLS;
    }

    /**
     * Получить тип лицевого счета.
     * Возможные значения: ЛС УО, ЛС РСО, ЛС КР.
     * Обязательный.
     * Появилось в версии файлов 9.0.1.2.
     * @return тип счета (ЛС УО, ЛС РСО или ЛС КР).
     */
    public String getTypeLS() {
        return typeLS;
    }

    /**
     * Задать тип лицевого счета.
     * Возможные значения: ЛС УО, ЛС РСО, ЛС КР.
     * Обязательный.
     * Появилось в версии файлов 9.0.1.2.
     * @param typeLS тип счета (ЛС УО, ЛС РСО или ЛС КР).
     */
    public void setTypeLS(String typeLS) {
        this.typeLS = typeLS;
    }

    /**
     * Является нанимателем?
     * Обязательный.
     * Значение из выпадающего списка, возможные значения: «да», «нет».
     */
    public AnswerYesOrNo getEmployer() {
        return employer;
    }

    /**
     * Является нанимателем?
     * Обязательный.
     * Значение из выпадающего списка, возможные значения: «да», «нет».
     */
    public void setEmployer(AnswerYesOrNo employer) {
        this.employer = employer;
    }

    /**
     * Получить фамилию.
     * См. раздел «Идентификация потребителей».
     * Строковое поле, 60 символов.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Задать фамилию.
     * См. раздел «Идентификация потребителей».
     * Строковое поле, 60 символов.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Получить имя.
     * См. раздел «Идентификация потребителей».
     * Строковое поле, 60 символов.
     */
    public String getName() {
        return name;
    }

    /**
     * Задать имя.
     * См. раздел «Идентификация потребителей».
     * Строковое поле, 60 символов.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получить отчество.
     * См. раздел «Идентификация потребителей».
     * Строковое поле, 60 символов.
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Задать отчество.
     * См. раздел «Идентификация потребителей».
     * Строковое поле, 60 символов.
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Получить СНИЛС потребителя.
     * См. раздел «Идентификация потребителей».
     * Строковое поле в формате «12345678901».
     */
    public String getSnils() {
        return snils;
    }

    /**
     * Задать СНИЛС потребителя.
     * См. раздел «Идентификация потребителей».
     * Строковое поле в формате «12345678901».
     */
    public void setSnils(String snils) {
        this.snils = snils;
    }

    /**
     * Получить вид документа, удостоверяющего личность.
     * См. раздел «Идентификация потребителей».
     * Выбор из выпадающего списка, заполняется, если указаны «Номер документа, удостоверяющего личность»
     * и «Серия документа, удостоверяющего личность»
     */
    public DocumentType getTypeDocument() {
        return typeDocument;
    }

    /**
     * Задать вид документа, удостоверяющего личность.
     * См. раздел «Идентификация потребителей».
     * Выбор из выпадающего списка, заполняется, если указаны «Номер документа, удостоверяющего личность»
     * и «Серия документа, удостоверяющего личность»
     */
    public void setTypeDocument(DocumentType typeDocument) {
        this.typeDocument = typeDocument;
    }

    /**
     * Получить номер документа, удостоверяющего личность.
     * См. раздел «Идентификация потребителей».
     * Строковое поле, до 45 символов включительно, заполняется, если указаны «Вид документа, удостоверяющего личность»
     * и «Серия документа, удостоверяющего личность»
     */
    public String getNumberDocumentIdentity() {
        return numberDocumentIdentity;
    }

    /**
     * Задать номер документа, удостоверяющего личность.
     * См. раздел «Идентификация потребителей».
     * Строковое поле, до 45 символов включительно, заполняется, если указаны «Вид документа, удостоверяющего личность»
     * и «Серия документа, удостоверяющего личность»
     */
    public void setNumberDocumentIdentity(String numberDocumentIdentity) {
        this.numberDocumentIdentity = numberDocumentIdentity;
    }

    /**
     * Получить серию документа, удостоверяющего личность.
     * См. раздел «Идентификация потребителей».
     * Строковое поле, до 45 символов включительно, заполняется, если указаны «Вид документа, удостоверяющего личность»
     * и «Номер документа, удостоверяющего личность».
     */
    public String getSeriesDocumentIdentity() {
        return seriesDocumentIdentity;
    }

    /**
     * Задать серию документа, удостоверяющего личность.
     * См. раздел «Идентификация потребителей».
     * Строковое поле, до 45 символов включительно, заполняется, если указаны «Вид документа, удостоверяющего личность»
     * и «Номер документа, удостоверяющего личность».
     */
    public void setSeriesDocumentIdentity(String seriesDocumentIdentity) {
        this.seriesDocumentIdentity = seriesDocumentIdentity;
    }

    /**
     * Получить дату документа, удостоверяющего личность.
     * См. раздел «Идентификация потребителей».
     * Поле типа «Дата» (ДД.ММ.ГГГГ).
     */
    public Date getDateDocumentIdentity() {
        return dateDocumentIdentity;
    }

    /**
     * Задать дату документа, удостоверяющего личность.
     * См. раздел «Идентификация потребителей».
     * Поле типа «Дата» (ДД.ММ.ГГГГ).
     */
    public void setDateDocumentIdentity(Date dateDocumentIdentity) {
        this.dateDocumentIdentity = dateDocumentIdentity;
    }

    /**
     * Получить ОГРН/ОГРНИП потребителя (для ЮЛ и ИП).
     * См. раздел «Идентификация потребителей».
     * Целочисленное поле строго 13 или 15 символов, обязательное поле,
     * если потребитель - индивидуальный предприниматель или юридическое лицо
     */
    public String getOgrnOrOgrnip() {
        return ogrnOrOgrnip;
    }

    /**
     * Задать ОГРН/ОГРНИП потребителя (для ЮЛ и ИП).
     * См. раздел «Идентификация потребителей».
     * Целочисленное поле строго 13 или 15 символов, обязательное поле,
     * если потребитель - индивидуальный предприниматель или юридическое лицо
     */
    public void setOgrnOrOgrnip(String ogrnOrOgrnip) {
        this.ogrnOrOgrnip = ogrnOrOgrnip;
    }

    /**
     * Получить КПП нанимателя (для ОП).
     * См. раздел «Идентификация потребителей».
     * Целочисленное поле строго 9 символов, обязательное поле,
     * если наниматель – обособленное подразделение юридического лица.
     */
    public String getKpp() {
        return kpp;
    }

    /**
     * Задать КПП нанимателя (для ОП).
     * См. раздел «Идентификация потребителей».
     * Целочисленное поле строго 9 символов, обязательное поле,
     * если наниматель – обособленное подразделение юридического лица.
     */
    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    /**
     * Получить общую площадь, кв. м.
     * Обязательный.
     * Цифровое поле, 20 символов, включая 4 после запятой.
     */
    public double getTotalArea() {
        return totalArea;
    }

    /**
     * Задать общую площадь, кв. м.
     * Обязательный.
     * Цифровое поле, 20 символов, включая 4 после запятой.
     */
    public void setTotalArea(double totalArea) {
        this.totalArea = totalArea;
    }

    /**
     * Получить жилую площадь, кв. м.
     * Необязательный.
     * Цифровое поле, 20 символов, включая 4 после запятой.
     */
    public double getLivingSpace() {
        return livingSpace;
    }

    /**
     * Задать жилую площадь, кв. м.
     * Необязательный.
     * Цифровое поле, 20 символов, включая 4 после запятой.
     */
    public void setLivingSpace(double livingSpace) {
        this.livingSpace = livingSpace;
//        this.livingSpace = livingSpace.setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Получить отапливаемую  площадь, кв. м.
     * Необязательный.
     * Цифровое поле, 20 символов, включая 4 после запятой.
     */
    public double getHeadtedArea() {
        return headtedArea;
    }

    /**
     * Задать отапливаемую  площадь, кв. м.
     * Необязательный.
     * Цифровое поле, 20 символов, включая 4 после запятой.
     */
    public void setHeadtedArea(double headtedArea) {
        this.headtedArea = (headtedArea < this.totalArea)?headtedArea:this.totalArea;
//        this.headtedArea = headtedArea.setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Получить количество проживающих, чел.
     * Необязательный.
     * Цифровое поле, до 4 символов.
     */
    public int getAmountLiving() {
        return amountLiving;
    }

    /**
     * Задать количество проживающих, чел.
     * Необязательный.
     * Цифровое поле, до 4 символов.
     */
    public void setAmountLiving(int amountLiving) {
        this.amountLiving = amountLiving;
    }

    /**
     * Получить ИД абонента в системе Град
     * @return идентификатор
     */
    public int getGradID() {
        return gradID;
    }

    /**
     * Задать ИД абонента в системе Град
     * @param gradID - идентификатор
     */
    public void setGradID(int gradID) {
        this.gradID = gradID;
    }

    public String getPremisesGUID() {
        return premisesGUID;
    }

    public void setPremisesGUID(String premisesGUID) {
        this.premisesGUID = premisesGUID;
    }

    public String getLivingRoomGUID() {
        return livingRoomGUID;
    }

    public void setLivingRoomGUID(String livingRoomGUID) {
        this.livingRoomGUID = livingRoomGUID;
    }

    public String getAccountGUID() {
        return accountGUID;
    }

    public void setAccountGUID(String accountGUID) {
        this.accountGUID = accountGUID;
    }

    public String getUnifiedAccountNumber() {
        return unifiedAccountNumber;
    }

    public void setUnifiedAccountNumber(String unifiedAccountNumber) {
        this.unifiedAccountNumber = unifiedAccountNumber;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getOrgVersionGUID() {
        return orgVersionGUID;
    }

    public void setOrgVersionGUID(String orgVersionGUID) {
        this.orgVersionGUID = orgVersionGUID;
    }

    public int getSharePay() {
        return sharePay;
    }

    public void setSharePay(int sharePay) {
        this.sharePay = sharePay;
    }
}
