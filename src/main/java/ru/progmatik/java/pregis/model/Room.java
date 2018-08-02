package ru.progmatik.java.pregis.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, содержит даные листа "Помещение".
 */
public class Room {
//
//    /**
//     * Адрес помещения.
//     * Необязательный.
//     * Строковое поле. Данное поле не сохраняется в ГИС ЖКХ и служит для облегчения идентификации помещений в шаблоне.
//     */
//    private String address;

    /**
     * Код дома по ФИАС.
     * Необязательный.
     * Строковое поле в формате GUID, заполняется если не указано значение «Идентификатор дома,
     * помещения, комнаты, присвоенный ГИС ЖКХ»
     */
    private String fias;

    /**
     * Номер помещения.
     * Необязательный.
     * Строковое поле до 255 символов, заполняется если не указано значение
     * «Идентификатор дома, помещения, комнаты, присвоенный ГИС ЖКХ»
     */
    private String numberAppart;

    /**
     * Номер комнаты.
     * Необязательный.
     * Строковое поле до 255 символов, заполняется если не указано значение
     * «Идентификатор дома, помещения, комнаты, присвоенный ГИС ЖКХ»
     */
    private String numberRoom;

    /**
     * Идентификатор дома, помещения, комнаты, присвоенный ГИС ЖКХ.
     * Необязательный.
     * Символьное поле, код, присвоенный объекту жилищного фонда ГИС ЖКХ,
     * заполняется если не указана группа полей «Адрес дома, помещения, комнаты».
     */
    private String idSpaceGISJKH;

//    /**
//     * Доля внесения платы, размер доли в %.
//     * Обязательный.
//     * Допускается ввод положительных целых значений от 1 до 100.
//     */
//    private int sharePay;

    /**
     * Идентификаторы абонентов помещения в БД ГРАД.
     */
    private List<Integer> abonId;

    /**
     * Жилое помещение - да/нет
     */
    private boolean isResidential;

    /**
     * Идентификатор помещения
     */
    private String premisesGUID;

    /**
     * Идентификатор комнаты
     */
    private String livingroomGUID;

    /**
     * подъезд
     */
    private String doorWay;

    /**
     * площадь помещения
     */
    private BigDecimal totalArea;

//    /**
//     * Получить адрес помещения.
//     * Необязательный.
//     * Строковое поле. Данное поле не сохраняется в ГИС ЖКХ и служит для облегчения идентификации помещений в шаблоне.
//     */
//    public String getAddress() {
//        return address;
//    }
//
//    /**
//     * Установить адрес помещения.
//     * Необязательный.
//     * Строковое поле. Данное поле не сохраняется в ГИС ЖКХ и служит для облегчения идентификации помещений в шаблоне.
//     */
//    public void setAddress(String address) {
//        this.address = address;
//    }

    /**
     * Получить код дома по ФИАС.
     * Необязательный.
     * Строковое поле в формате GUID, заполняется если не указано значение «Идентификатор дома,
     * помещения, комнаты, присвоенный ГИС ЖКХ»
     */
    public String getFias() {
        return fias;
    }

    /**
     * Задать код дома по ФИАС.
     * Необязательный.
     * Строковое поле в формате GUID, заполняется если не указано значение «Идентификатор дома,
     * помещения, комнаты, присвоенный ГИС ЖКХ»
     */
    public void setFias(String fias) {
        this.fias = fias;
    }

    /**
     * Получить номер помещения.
     * Необязательный.
     * Строковое поле до 255 символов, заполняется если не указано значение
     * «Идентификатор дома, помещения, комнаты, присвоенный ГИС ЖКХ»
     */
    public String getNumberAppart() {
        return numberAppart;
    }

    /**
     * Задать номер помещения.
     * Необязательный.
     * Строковое поле до 255 символов, заполняется если не указано значение
     * «Идентификатор дома, помещения, комнаты, присвоенный ГИС ЖКХ»
     */
    public void setNumberAppart(String numberAppart) {
        this.numberAppart = numberAppart;
    }

    /**
     * Получить номер комнаты.
     * Необязательный.
     * Строковое поле до 255 символов, заполняется если не указано значение
     * «Идентификатор дома, помещения, комнаты, присвоенный ГИС ЖКХ»
     */
    public String getNumberRoom() {
        return numberRoom;
    }

    /**
     * Задать номер комнаты.
     * Необязательный.
     * Строковое поле до 255 символов, заполняется если не указано значение
     * «Идентификатор дома, помещения, комнаты, присвоенный ГИС ЖКХ»
     */
    public void setNumberRoom(String numberRoom) {
        this.numberRoom = numberRoom;
    }

    /**
     * Получить идентификатор дома, помещения, комнаты, присвоенный ГИС ЖКХ.
     * Необязательный.
     * Символьное поле, код, присвоенный объекту жилищного фонда ГИС ЖКХ,
     * заполняется если не указана группа полей «Адрес дома, помещения, комнаты».
     */
    public String getIdSpaceGISJKH() {
        return idSpaceGISJKH;
    }

    /**
     * Задать идентификатор дома, помещения, комнаты, присвоенный ГИС ЖКХ.
     * Необязательный.
     * Символьное поле, код, присвоенный объекту жилищного фонда ГИС ЖКХ,
     * заполняется если не указана группа полей «Адрес дома, помещения, комнаты».
     */
    public void setIdSpaceGISJKH(String idSpaceGISJKH) {
        this.idSpaceGISJKH = idSpaceGISJKH;
    }

    /**
     * Получить долю внесения платы, размер доли в %.
     * Обязательный.
     * Допускается ввод положительных целых значений от 1 до 100.
     */
//    public int getSharePay() {
//        return sharePay;
//    }

    /**
     * Задать долю внесения платы, размер доли в %.
     * Обязательный.
     * Допускается ввод положительных целых значений от 1 до 100.
     */
//    public void setSharePay(int sharePay) {
//        this.sharePay = sharePay;
//    }

    /**
     * Получить, Идентификатор абонента в БД ГРАД.
     * @return идентификатор абонента в БД ГРАД.
     */
    public List<Integer> getAbonId() {
        return abonId;
    }

    /**
     * Задать, Идентификатор абонента в БД ГРАД.
     * @param abonId новый идентификатор абонента в БД ГРАД.
     */
    public void setAbonId(List<Integer> abonId) {
        this.abonId = abonId;
    }

//    /**
//     * Получить, статус абоненета, является физическим лицом или юридическим.
//     * @return статус абонента, true - если юр.лицо, false - если физ.лицо.
//     */
//    public boolean isCompany() {
//        return this.isCompany;
//    }
//
//    /**
//     * Задать, статус абоненета, является физическим лицом или юридическим.
//     * @param company статус абонента, true - если юр.лицо, false - если физ.лицо.
//     */
//    public void setCompany(boolean company) {
//    }

    /**
     * жилое помещение или нет
     * @return
     */
    public boolean isResidential() {
        return this.isResidential;
    }

    public void setResidential(boolean residential) {
        this.isResidential = residential;
    }

    public String getPremisesGUID() {
        return premisesGUID;
    }

    public void setPremisesGUID(String premisesGUID) {
        this.premisesGUID = premisesGUID;
    }

    public String getLivingroomGUID() {
        return livingroomGUID;
    }

    public void setLivingroomGUID(String livingroomGUID) {
        this.livingroomGUID = livingroomGUID;
    }

    public BigDecimal getTotalArea() {
        if(totalArea == null) return new BigDecimal(0);
        return totalArea;
    }

    public void setTotalArea(BigDecimal totalArea) {
        this.totalArea = totalArea;
    }

    public String getDoorWay() {
        return doorWay;
    }

    public void setDoorWay(String doorWay) {
        this.doorWay = doorWay;
    }

    @Override
    public String toString() {
        return "Room{" +
                ", fias='" + fias + '\'' +
                ", numberAppart='" + numberAppart + '\'' +
                ", numberRoom='" + numberRoom + '\'' +
                ", idSpaceGISJKH='" + idSpaceGISJKH + '\'' +
//                ", sharePay=" + sharePay +
                ", abonId=" + abonId +
                ", isResidential=" + isResidential +
                ", premisesGUID='" + premisesGUID + '\'' +
                ", livingroomGUID='" + livingroomGUID + '\'' +
                '}';
    }

    public Room() {
        this.abonId = new ArrayList<>();
    }
}
