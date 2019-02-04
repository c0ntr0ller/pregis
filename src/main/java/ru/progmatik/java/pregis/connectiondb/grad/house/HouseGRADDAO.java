/*
    Процедура для занесения и получения идентификаторов в БД ГДРАД:

    execute procedure EX_GIS_ID(:ABON_ID, :BUILDING_ID, :METER_ID, :HOUSEUNIQNUM, :ACCOUNTGUID, :ACCOUNTUNIQNUM,
    :PREMISESGUID, :PREMISESUNIQNUM, :LIVINGROOMGUID, :ROOMUNIQNUMBER, :METERVERSIONGUID, :METERROOTGUID, :PARAM)
    returning_values :RESULT

    Входящий параметр :PARAM - это наименование идентификатора,
    может принимать значения HOUSEUNIQNUM, ACCOUNTGUID, ACCOUNTUNIQNUM, PREMISESGUID, PREMISESUNIQNUM, LIVINGROOMGUID,
    ROOMUNIQNUMBER, METERVERSIONGUID, METERROOTGUID
 */
package ru.progmatik.java.pregis.connectiondb.grad.house;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.model.HouseRecord;
import ru.progmatik.java.pregis.model.HouseType;
import ru.progmatik.java.pregis.model.Room;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static ru.progmatik.java.pregis.other.OtherFormat.checkZero;

/**
 * Класс, обращается в БД ГРАД, за данными о МКД.
 */
public final class HouseGRADDAO {

    private static final int HOUSE_ADDRESS_GRAD = 0; // Адрес дома в ГРАД
    private static final int HOUSE_FIAS = 1; // Код дома по ФИАС
    private static final int HOUSE_ID_GRAD_MKD = 13; // ИД дома из БД ГРАД
    private static final int HOUSE_ID_GRAD_JD = 10; // ИД дома из БД ГРАД
    private static final int ADDRESS_GRAD_MKD = 15;
    private static final int NUMBER_HOUSE_GRAD_MKD = 16;
    private static final int ADDRESS_GRAD_JD = 12;
    private static final int NUMBER_HOUSE_GRAD_JD = 13;
    private static final int HOUSE_HOUSEUNIQNUM = 17;
    private static final Logger LOGGER = Logger.getLogger(HouseGRADDAO.class);
    private final AnswerProcessing answerProcessing;
    //    Временные листы, что бы не ходить 2 или более раза за одним и темже в БД
//    private final ArrayList<String> tempMKD = new ArrayList<>();
//    private final ArrayList<String> tempJD = new ArrayList<>();
//    private final LinkedHashMap<String, HouseRecord> allHouses = new LinkedHashMap<>();
    private final HashMap<Integer, ArrayList<Room>> houseRoomsMap = new HashMap<>();

    public HouseGRADDAO(final AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    /**
     * Метод, обращается в БД ГРАДа, получает сведенья о доме, извлекает ФИАС и ИД дома в БД ГРАД, затем добавляет его в Map.
     *
     * @return список всех ФИАСов (домов) указаной организации.
     * @throws SQLException    выкинет ошибку, если будут проблемы с БД.
     * @throws PreGISException выкинет ошибку, если например не найдем значение в БД.
     */
//    public LinkedHashMap<String, Integer> getAllHouseFIAS(final Connection connectionGrad) throws SQLException, PreGISException {
////        answerProcessing.sendMessageToClient(":getAllHouseFIAS"); //!!!------
//
//        final List<String> allListHouseData = getMKDHousesFromGrad(connectionGrad);
//        final LinkedHashMap<String, Integer> mapFIAS = new LinkedHashMap<String, Integer>();
//
//        for (String itemListHouse : allListHouseData) {
//            if (getAllData(itemListHouse)[HOUSE_FIAS] != null && !getAllData(itemListHouse)[HOUSE_FIAS].isEmpty()) {
//                mapFIAS.put(getAllData(itemListHouse)[HOUSE_FIAS], Integer.valueOf(getAllData(itemListHouse)[HOUSE_ID_GRAD_MKD]));
//            }
//        }
//        if (mapFIAS.size() == 0)
//            return null;
//        else
//            return mapFIAS;
//    }
//     /*
//            *
//            * @return
//            * @throws SQLException    выкинет ошибку, если будут проблемы с БД.
//     * @throws PreGISException выкинет ошибку, если например не найдем значение в БД.
//     */

    /**
     * По запросу выдает список домов из Град. Если заан ИД дома - выдает только его
     *
     * @param houseGradId
     * @param connectionGrad
     * @return
     * @throws SQLException
     * @throws PreGISException
     */
    public LinkedHashMap<String, HouseRecord> getHouseRecords(final Integer houseGradId, Connection connectionGrad) throws SQLException, PreGISException {
        // если список пустой - запрашиваем из БД
        if(connectionGrad == null){
            connectionGrad = ConnectionDB.instance().getConnectionDB();
        }

        LinkedHashMap<String, HouseRecord> allHouses = getHouseRecordsFromDB(connectionGrad);

        if (houseGradId != null && !allHouses.isEmpty()) {
            for (Map.Entry<String, HouseRecord> entry : allHouses.entrySet()) {
                if (houseGradId.equals(entry.getValue().getGrad_id())) {
                    final LinkedHashMap<String, HouseRecord> tmpMap = new LinkedHashMap<>();
                    tmpMap.put(entry.getKey(), entry.getValue());
                    return tmpMap;
                }
            }
        }
        return allHouses;
    }

    /**
     * Заполняет мапу с домами из БД
     *
     * @param connectionGrad
     * @throws SQLException
     * @throws PreGISException
     */
    private LinkedHashMap<String, HouseRecord> getHouseRecordsFromDB(final Connection connectionGrad) throws SQLException, PreGISException {
        // получем сначала все МКД
        final LinkedHashMap<String, HouseRecord> mapMkd = getAllMkdHouseRecords(connectionGrad);
        // получаем все жилые дома
        final LinkedHashMap<String, HouseRecord> mapJd = getAllJdHouseRecords(connectionGrad);
        final LinkedHashMap<String, HouseRecord> allHouses = new LinkedHashMap<>();

        if (mapMkd != null) {
            allHouses.putAll(mapMkd);
        }
        if (mapJd != null) {
            allHouses.putAll(mapJd);
        }
        return allHouses;
    }

    /**
     * Метод, обращается в БД ГРАДа, получает сведения о многоквартирных домах, извлекает ФИАС и информацию о домме из строк, затем добавляет его в Map. Ключ ФИАС дома
     *
     * @param connectionGrad - соединение с Град
     * @return список всех домов указаной организации.
     * @throws SQLException
     * @throws PreGISException
     */
    private LinkedHashMap<String, HouseRecord> getAllMkdHouseRecords(final Connection connectionGrad) throws SQLException, PreGISException {

        final List<String> allListHouseData = getMKDHousesFromGrad(connectionGrad);
        final LinkedHashMap<String, HouseRecord> mapMkd = getHouseRecordMapFromStringList(allListHouseData, HouseType.MKD);

        if (mapMkd == null || mapMkd.isEmpty()) {
            return null;
        } else {
            return mapMkd;
        }
    }

    /**
     * Метод, обращается в БД ГРАДа, получает сведения о частных домах, извлекает ФИАС и информацию о домме из строк, затем добавляет его в Map. Ключ ФИАС дома
     *
     * @param connectionGrad - соединение с Град
     * @return список всех домов указаной организации.
     * @throws SQLException
     * @throws PreGISException
     */
    private LinkedHashMap<String, HouseRecord> getAllJdHouseRecords(final Connection connectionGrad) throws SQLException, PreGISException {

        final ArrayList<String> allListHouseData = getJdHousesFromGrad(connectionGrad);
        final LinkedHashMap<String, HouseRecord> mapJd = getHouseRecordMapFromStringList(allListHouseData, HouseType.JD);

        if (mapJd == null || mapJd.isEmpty())
            return null;
        else {
            return mapJd;
        }
    }

    private LinkedHashMap<String, HouseRecord> getHouseRecordMapFromStringList(List<String> allListHouseData, HouseType houseType) {
        final LinkedHashMap<String, HouseRecord> mapJd = new LinkedHashMap<>();
        if (!allListHouseData.isEmpty()) { // Если в листе вообще есть данные тогда
            for (String itemListHouse : allListHouseData) {
                if (getAllData(itemListHouse)[HOUSE_FIAS] != null && !getAllData(itemListHouse)[HOUSE_FIAS].isEmpty()) {
                    String[] houseAllData = getAllData(itemListHouse);

                    String HOUSE_ID;
                    if(houseType.equals(HouseType.JD)) {
                        HOUSE_ID= houseAllData[HOUSE_ID_GRAD_JD];
                    }else{
                        HOUSE_ID = houseAllData[HOUSE_ID_GRAD_MKD];
                    }


                    if (HOUSE_ID.equals("-1-")) {
                        answerProcessing.sendMessageToClient("-1- Не найден ИД дома в данных Града в строке: " + itemListHouse);
                    } else {

                        Integer houseId;
                        String addressGrad;
                        String numberHouseGrad;
                        if(houseType.equals(HouseType.JD)) {
                            houseId = Integer.valueOf(houseAllData[HOUSE_ID_GRAD_JD]);
                            addressGrad = houseAllData[ADDRESS_GRAD_JD];
                            numberHouseGrad = houseAllData[NUMBER_HOUSE_GRAD_JD];
                        }else{
                            houseId = Integer.valueOf(houseAllData[HOUSE_ID_GRAD_MKD]);
                            addressGrad = houseAllData[ADDRESS_GRAD_MKD];
                            numberHouseGrad = houseAllData[NUMBER_HOUSE_GRAD_MKD];
                        }
                        mapJd.put(houseAllData[HOUSE_FIAS],
                                new HouseRecord(
                                        houseAllData[HOUSE_FIAS],
                                        houseId,
                                        houseAllData[HOUSE_ADDRESS_GRAD],
                                        ((houseAllData.length <= HOUSE_HOUSEUNIQNUM) ? "" : houseAllData[HOUSE_HOUSEUNIQNUM]),
                                        String.format("%s %s", addressGrad, numberHouseGrad)
                                )
                        );
                    }
                }
            }
        } else return null;
        return mapJd;
    }

    /**
     * Метод, формирует список всех домов (МКД и ЖД), которые уже получили идентификаторы ГИС ЖКХ.
     * Пригодные для использования в дальнейшем например импорт ЛС и ПУ.
     *
     * @return пара ключ-значение ФИАС-ИД ГРАДа.
     */
    @Deprecated
    public LinkedHashMap<String, Integer> getHouseAddedGisJkh(final Connection connectionGrad, final boolean allHouses) throws SQLException, PreGISException {

        final ArrayList<String> allListMKD = getMKDHousesFromGrad(connectionGrad);
        final ArrayList<String> listAllJD = getJdHousesFromGrad(connectionGrad);

        final LinkedHashMap<String, Integer> mapAllHouseWithIdGis = new LinkedHashMap<String, Integer>();

//        Получение данных из БД о МКД.
        for (String itemListHouse : allListMKD) {
            if (getAllData(itemListHouse)[HOUSE_FIAS] != null && !getAllData(itemListHouse)[HOUSE_FIAS].isEmpty()) {// если задан ФИАС
                if (allHouses // если добавляем все дома
                        ||
                        ((getAllData(itemListHouse).length > HOUSE_HOUSEUNIQNUM) && // или уже есть идентификатор
                                getAllData(itemListHouse)[HOUSE_HOUSEUNIQNUM] != null &&
                                !getAllData(itemListHouse)[HOUSE_HOUSEUNIQNUM].isEmpty())) {
                    mapAllHouseWithIdGis.put(getAllData(itemListHouse)[HOUSE_FIAS], Integer.valueOf(getAllData(itemListHouse)[HOUSE_ID_GRAD_MKD]));
                }
            }
        }

//        Получение данных из БД о ЖД.
        for (String itemData : listAllJD) {
            if (getAllData(itemData)[1] != null && !getAllData(itemData)[1].isEmpty() &&
                    isAddedHouseInGisJkh(Integer.valueOf(getAllData(itemData)[HOUSE_ID_GRAD_JD]), connectionGrad)) { // проверяем содержится ФИАС, если есть то добавляем
                mapAllHouseWithIdGis.put(getAllData(itemData)[HOUSE_FIAS], Integer.valueOf(getAllData(itemData)[HOUSE_ID_GRAD_JD]));
            }
        }

        if (mapAllHouseWithIdGis.isEmpty())
            return null;
        else
            return mapAllHouseWithIdGis;
    }

    /**
     * Метод, задаёт дому уникальный идентификатор присвоенный ГИС ЖКХ.
     *
     * @param houseId           ИД дома в БД ГРАД.
     * @param houseUniqueNumber уникальный номер дома генерируемый ГИС ЖКХ.  @throws SQLException выкинет ошибку, если будут проблемы с БД.
     */
    public void setHouseUniqueNumber(final Integer houseId,
                                     final String houseUniqueNumber,
                                     final Connection connectionGrad) throws SQLException, PreGISException {

//        Integer idHouse = getHouseIdFromGrad(fias);

//        if (idHouse != null) {
        // ИД дома(:building_id),
        // ИД абонента(:abon_id),
        // ИД прибора учета(:meter_id),
        // уникальный идентификатор ГИС ЖКХ(:gis_id),
        // уникальный идентификатор лицевого счета ГИС ЖКХ(:gis_ls_id)
//        System.err.println("houseID: " + houseId + " houseUniqueNumber: " + houseUniqueNumber);
        final String sqlRequest = "{EXECUTE PROCEDURE EX_GIS_ID(NULL, ?, NULL, ?, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ?)}";
        try (CallableStatement cstmt = connectionGrad.prepareCall(sqlRequest)) {
            cstmt.setInt(1, houseId);
            cstmt.setString(2, houseUniqueNumber);
            cstmt.setInt(3, ResourcesUtil.instance().getCompanyGradId());
//                System.out.println(houseId + " : " + houseUniqueNumber);
            cstmt.executeUpdate();
//                System.err.println("Code return: " + codeReturn);
        }
//        } else {
//            throw new PreGISException("setHouseUniqueNumber(): Не удалось найти ID дома в БД ГРАД. С помощь кода ФИАС: " + fias);
//        }
    }

    /**
     * Метод, задаёт помещению уникальный идентификатор, присвоенный ГИС ЖКХ.
     * В БД ГРАД нет отличия помещение или комната, идентификатор вешается на ЛС абонента.
     *
     * @param premisesGUID    идентификатор помещения.
     * @param premisesUniqNum уникальный номер помещения.
     * @param livingRoomGUID  идентификатор комнаты.
     * @param roomUniqNumber  уникальный номер комнаты.
     * @throws SQLException    выкинет ошибку, если будут проблемы с БД.
     * @throws PreGISException выкинет ошибку, если например не найдем значение в БД.
     */
    public void setApartmentUniqueNumber(final String premisesGUID, final String premisesUniqNum,
                                         final String livingRoomGUID, final String roomUniqNumber,
                                         final Connection connectionGrad,
                                         final Integer abonentId) throws SQLException, PreGISException{
        if (abonentId != null) {
            final String sqlRequest = "{EXECUTE PROCEDURE EX_GIS_ID(?, NULL , NULL, NULL, NULL, NULL, ?, ?, ?, ?, NULL, NULL, NULL, NULL, ?)}";
            try (CallableStatement cstmt = connectionGrad.prepareCall(sqlRequest)) {
                cstmt.setInt(1, abonentId);
                cstmt.setString(2, premisesGUID);
                cstmt.setString(3, premisesUniqNum);
                cstmt.setString(4, livingRoomGUID);
                cstmt.setString(5, roomUniqNumber);
                cstmt.setInt(6, ResourcesUtil.instance().getCompanyGradId());
                cstmt.executeUpdate();
            }
        }
// дублирование сообщения
//      else {
//            answerProcessing.sendMessageToClient("");
//            answerProcessing.sendMessageToClient("Не удалось найти ID абонента в БД ГРАД, запись пропущена. Кв № = " + apartmentNumber + "; ");
//        }
    }

    /**
     * метод-заглушка для совместимости со старым кодом
     */
    @Deprecated
    public void setApartmentUniqueNumber(final Integer houseId, final String apartmentNumber, final String roomNumber, final boolean isResidential,
                                         final String premisesGUID, final String premisesUniqNum,
                                         final String livingRoomGUID, final String roomUniqNumber,
                                         final Connection connectionGrad) throws SQLException, PreGISException, ParseException {
        final List<Integer> abonentId = getAbonentIdFromGrad(houseId, apartmentNumber, roomNumber, isResidential, connectionGrad);

        if (abonentId != null && !abonentId.isEmpty()) {
            for (Integer abonId: abonentId) {
                setApartmentUniqueNumber(premisesGUID,
                        premisesUniqNum,
                        livingRoomGUID,
                        roomUniqNumber,
                        connectionGrad,
                        abonId);
            }
        }
    }
        /**
         * Метод, получает из процедуры "EX_GIS04" площадь помещения.
         *
         * @param houseId        идентификатор дома в БД ГРАДа.
         * @param connectionGrad подключение к БД ГРАД.
         * @return ключ - abonId, значение - общая площадь помещения.
         */
//    public HashMap<Integer, String> getTotalSquare(final int houseId, final Connection connectionGrad) throws SQLException {
//
//        final HashMap<Integer, String> totalSquareMap = new HashMap<>();
//        try (CallableStatement cstmt = connectionGrad.prepareCall("{EXECUTE PROCEDURE EX_GIS04(?)}")) {
//            cstmt.setString(1, String.valueOf(houseId));
//            final ResultSet resultSet = cstmt.executeQuery();
//
//            while (resultSet.next()) {
//                final String[] data = OtherFormat.getAllDataFromString(resultSet.getString(1));
//                totalSquareMap.put(Integer.parseInt(data[7]), data[4]);
//            }
//        }
//        return totalSquareMap;
//    }

//    /**
//     * Метод, получает идентификатор дома в Граде, по нему находит дом добавлены в ГИС ЖКХ, для последующего обмена.
//     * @param houseGradId идентификатор дома в Граде
//     * @param connectionGrad подключение к БД Град
//     * @return ключ - код дома по ФИАС, значение - идентификатор дома в Граде.
//     * @throws SQLException могут возникнуть ошибки во время работы с БД.
//     * @throws PreGISException могут появится ошибка если в файле параметров не указана ИД организации в Граде.
//     */
//    @Deprecated
//    public LinkedHashMap<String, Integer> getListHouse(final Integer houseGradId,
//                                                        final Connection connectionGrad) throws SQLException, PreGISException {
//
//        final LinkedHashMap<String, Integer> tempMap = new LinkedHashMap<>();
//        final LinkedHashMap<String, Integer> houseAddedGisJkh = getHouseAddedGisJkh(connectionGrad,false);
//
//        if (houseGradId == null) {
//            return houseAddedGisJkh;
//        } else if (houseAddedGisJkh != null){
//            for (Map.Entry<String, Integer> entry : houseAddedGisJkh.entrySet()) {
//                if (houseGradId.equals(entry.getValue())) {
//                    tempMap.put(entry.getKey(), entry.getValue());
//                }
//            }
//        }
//
//        if (houseAddedGisJkh == null) {
//            throw new PreGISException("Не найден ни один дом готовый для выгрузки в ГИС ЖКХ.");
//        }
//
//        return tempMap;
//    }

    /**
     * Метод, получает значение номера квартиры и комнаты (если есть), возвращает ИД абонента в БД ГРАД.
     *
     * @param idHouse         ид дома в БД ГРАД.
     * @param apartmentNumber номер квартиры.
     * @param roomNumber      номер помещения, если есть или null.
     * @return ИД абонента в БД ГРАД или null.
     * @throws SQLException    выкинет ошибку, если будут проблемы с БД.
     * @throws PreGISException выкинет ошибку, если например не найдем значение в БД.
     */
    private List<Integer> getAbonentIdFromGrad(final Integer idHouse, final String apartmentNumber,
                                         final String roomNumber,
                                         final boolean isResidential,
                                         final Connection connectionGrad) throws SQLException, PreGISException, ParseException {

        if (false == houseRoomsMap.containsKey(idHouse)) {
//            Если IDEA выделила просто не обращай внимание, зачистую конструкция (!houseRoomsMap.containsKey(idHouse)),
//            менее заметнее чем указанную выше, где явно должны получить false
//            final AccountGRADDAO accountGRADDAO = new AccountGRADDAO(answerProcessing);

            houseRoomsMap.clear();
            houseRoomsMap.put(idHouse, getRooms(idHouse, connectionGrad));
        }

        final List<Integer> abonentId = findAbonId(idHouse, apartmentNumber, roomNumber, isResidential);

        if (((abonentId == null) || abonentId.isEmpty()) && (roomNumber == null)) {
            answerProcessing.sendMessageToClient(String.format("ИД абонента для помещения: %s не найден! ИД дома: %d", apartmentNumber, idHouse)); //!!!------
//            throw new PreGISException(String.format("ИД абонента для помещения: %s не найден! ИД дома: %d", apartmentNumber, idHouse));
        } else if ((abonentId == null || abonentId.size() == 0)) {
            answerProcessing.sendMessageToClient(String.format("ИД абонента для помещения: %s и комнаты: %s не найдены! ИД дома: %d", apartmentNumber, roomNumber, idHouse)); //!!!------
//            throw new PreGISException(String.format("ИД абонента для помещения: %s и комнаты: %s не найдены! ИД дома: %d", apartmentNumber, roomNumber, idHouse));
        }
        return abonentId;
    }

    /**
     * Метод, ищет в локальной Map идентификатор абонента.
     *
     * @param idHouse         идентификатор дома в БД Град.
     * @param apartmentNumber номер помещения в ГИС ЖКХ.
     * @param roomNumber      номер комнаты в ГИС ЖКХ.
     * @return идентификатор абонента в БД Град.
     */
    private List<Integer> findAbonId(final Integer idHouse,
                               final String apartmentNumber,
                               final String roomNumber,
                               final boolean isResidential) {

        List<Integer> wasApartmentNumber = new ArrayList<>(); // флаг, что таким номером квартиры мы всетаки нашли
        final List<Integer> abonIDs = new ArrayList<>();
        if (idHouse != null && houseRoomsMap.containsKey(idHouse) && apartmentNumber != null && houseRoomsMap.get(idHouse) != null) {

            // фильтруем лист по номеру квартиры
            List<Room> tmpRoomsMap = houseRoomsMap.get(idHouse).stream()
                    .filter(r -> (r.getNumberAppart() != null &&
                            (
                                r.getNumberAppart().equalsIgnoreCase(apartmentNumber) ||
                                r.getNumberAppart().replace(" л.", "").equalsIgnoreCase(apartmentNumber) ||
                                r.getNumberAppart().replace("л.", "").equalsIgnoreCase(apartmentNumber)
                            )
                    ))
                    .collect(Collectors.toList());
            for (Room room : tmpRoomsMap) {
                if (room.isResidential() == isResidential) {
                    if (roomNumber == null){
                        if(room.getNumberRoom() == null) { // если номер комнаты отсутствует в обоих случайя - это нужный нам абонент!
                            abonIDs.addAll(room.getAbonId());
                        }
                        else {
                            wasApartmentNumber.addAll(room.getAbonId()); // запоминаем ИД абонента с таким номером квартиры, на случай если совсем не найдем с номером комнаты
                        }
                    }
                } else if (roomNumber != null && roomNumber.equalsIgnoreCase(room.getNumberRoom())) { // если номер комнаты совпадает.
                    abonIDs.addAll(room.getAbonId());
                }
            }
        }
        if (abonIDs.isEmpty()) {// если ничего не нашли
            if (!wasApartmentNumber.isEmpty()){// но стаким номером квартиры всетаки был
                abonIDs.addAll(wasApartmentNumber);
                return abonIDs;
            }
        }
        return abonIDs;
    }

    /**
     * Метод, получает ИД дома из БД ГРАД по коду ФИАС.
     *
     * @param fias код дома по ФИАС
     * @return ИД дома в системе ГРАД.
     * @throws SQLException    выкинет ошибку, если будут проблемы с БД.
     * @throws PreGISException выкинет ошибку, если например не найдем значение в БД.
     */
//    private Integer getHouseIdFromGrad(final String fias, final Connection connectionGrad) throws SQLException, PreGISException {
//
//        final LinkedHashMap<String, HouseRecord> mapMkdData = getHouseRecords(null, connectionGrad);
//        final LinkedHashMap<String, HouseRecord> mapJdData = getAllJdHouseRecords(null, connectionGrad);
//
//        if (mapMkdData.containsKey(fias)) {
//            return mapMkdData.get(fias).getGrad_id();
//        } else if (mapJdData.containsKey(fias)) {
//            return mapJdData.get(fias).getGrad_id();
//        } else {
//            return null;
//        }
//    }

    /**
     * Метод, получает все МКД из БД ГРАД.
     * Из процедуры с первого листа Excel.
     *
     * @return список всех домов с параметрами.
     * @throws SQLException    выкинет ошибку, если будут проблемы с БД.
     * @throws PreGISException выкинет ошибку, если например не найдем значение в БД.
     */
    private ArrayList<String> getMKDHousesFromGrad(final Connection connectionGrad) throws PreGISException, SQLException {
//        answerProcessing.sendMessageToClient(":getMKDHousesFromGrad"); //!!!------
        final String sqlRequest = "SELECT * FROM EX_GIS01('" + ResourcesUtil.instance().getCompanyGradId() + "')";

        return getHouseFromGrad(sqlRequest, connectionGrad);
    }


    /**
     * Метод, получает все жилые дома содержащие из БД ГРАД.
     *
     * @return список жилых домов.
     */
    private ArrayList<String> getJdHousesFromGrad(final Connection connectionGrad) throws PreGISException, SQLException {

        final String sqlRequest = "SELECT * FROM EX_GIS_LH01('" + ResourcesUtil.instance().getCompanyGradId() + "')";

        return getHouseFromGrad(sqlRequest, connectionGrad);
    }

    /**
     * Метод, отправляет в БД Града запрос, получает резултсет домов в виде массива строк и возвращает его.
     *
     * @param sqlRequest     SQL запрос.
     * @param connectionGrad подключение к БД Град.
     * @return список домов.
     * @throws SQLException могут возникнуть ошибки во время работы с БД.
     */
    private ArrayList<String> getHouseFromGrad(final String sqlRequest, final Connection connectionGrad) throws SQLException {
//        answerProcessing.sendMessageToClient(":getHouseFromGrad:" + sqlRequest); //!!!------
        final ArrayList<String> allHouseData = new ArrayList<>();
// переделываем на общий список        tempDataHouse.clear();

        try (Statement statement = connectionGrad.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlRequest)) { // После использования должны все соединения закрыться
            // определяем - есть ли в наших данных новое поле для АПИ, в котором данные уже в новом формате. По-умолчанию считает что нет
            boolean apiStrExists = false;
            int apiStrIndex = 1;
            final String apiColumnName = "RAPISTR";

            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                if (resultSet.getMetaData().getColumnName(i).equals(apiColumnName)) {
                    apiStrExists = true;
                    apiStrIndex = i;
                    break;
                }
            }
// TODO переделать по-нормальному!!!
            if (apiStrExists) { // если база возвращает новый формат данных - обрабатываем их
                while (resultSet.next()) {
                    if (resultSet.getString(apiStrIndex) != null || !resultSet.getString(apiStrIndex).isEmpty()) {
                        allHouseData.add(resultSet.getString(apiStrIndex));
// переделываем на общий список                        tempDataHouse.add(resultSet.getString(1));
                    }
                }
            } else {// иначе обрабатываем старый формат данных
                while (resultSet.next()) {
                    if (resultSet.getString(1) != null || !resultSet.getString(1).isEmpty()) {
                        allHouseData.add(resultSet.getString(1));
// переделываем на общий список                        tempDataHouse.add(resultSet.getString(1));
                    }
                }
            }
//            answerProcessing.sendMessageToClient(String.valueOf(allHouseData.size())); //!!!------
        }
        return allHouseData;
    }

    /**
     * Метод, проверяет, получен уникальный номер дома или нет.
     *
     * @param houseId ИД дома в БД ГРАДа.
     * @return true - у дома есть уникальный номер ГИС ЖКХ, false - дом не получил уникальный идентификатор ГИС ЖКХ.
     * @throws SQLException могут возникнуть ошибки во время работы с БД.
     */
    private boolean isAddedHouseInGisJkh(final Integer houseId, final Connection connectionGrad) throws SQLException, PreGISException {

        final String sqlRequest = "{EXECUTE PROCEDURE EX_GIS_ID(NULL, ?, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ?, NULL, ?)}";

        try (CallableStatement cstmt = connectionGrad.prepareCall(sqlRequest)) { // После использования должны все соединения закрыться
            cstmt.setInt(1, houseId);
            cstmt.setString(2, "HOUSEUNIQNUM");
            cstmt.setInt(3, ResourcesUtil.instance().getCompanyGradId());
            final ResultSet resultSet = cstmt.executeQuery();
            resultSet.next();
            final String sqlResult = resultSet.getString(1);
            if (sqlResult == null || sqlResult.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Метод получает из Град информацию о помещениях.
     * @param houseID - ИД адрес дома.
     *
     */
//    @Deprecated
    public ArrayList<Room> getRooms(final int houseID, final Connection connection) throws SQLException, PreGISException {

        if(!ResourcesUtil.instance().getPremisesNumberOnly()) {
            answerProcessing.sendMessageToClient("ResourcesUtil: Помещения НЕ объединяются по номеру квартиры, каждый абонент с литерой выгружается как помещение. Для выгрузки в ГИС помещений, объединенных по номеру квартиры без литеры, установите в настройках config.gis.premisesnumberonly=1");
        }
        final Integer columnIndex = 2; // column with API data format
        final String sqlRequest = "SELECT * FROM EX_GIS_LS2(" + houseID + "," + ResourcesUtil.instance().getCompanyGradId() + ")";
        final ArrayList<Room> listRooms = new ArrayList();

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlRequest)) {
            while (resultSet.next()) {

//                Бывают попадаются null
                if (resultSet.getString(columnIndex) == null) continue;


                Room room = null;

                try {
                    final String[] arrayData = OtherFormat.getAllDataFromString(resultSet.getString(columnIndex));

                    // получаем номер квартиры
                    String localNumberAppart = arrayData[5];
                    String localNumberAppartNumberOnly = null;
                    // если номер квартиры задан
                    if (localNumberAppart != null || !"".equalsIgnoreCase(localNumberAppart)) {
                        // если объединяем по номеру квартиры - ищем сначала уже имющуюся квартиру в списке
                        if (ResourcesUtil.instance().getPremisesNumberOnly()) {
                            localNumberAppartNumberOnly = OtherFormat.extractLeadingNumber(localNumberAppart);
                            for (Room listRoom : listRooms) {
                                if (listRoom.getNumberAppart().equalsIgnoreCase(localNumberAppartNumberOnly)) {
                                    room = listRoom;
                                    break;
                                }
                            }
                        }

                        // если не нашли помещение или настройка отсутствует - тогда создаем новое помещение
                        if (room == null) {
                            room = new Room();

                            //                        room.setAddress(arrayData[2]);
                            room.setFias(arrayData[3]);

                            // если по настройке берем только номера квартир без литер и номеров комнат
                            if (ResourcesUtil.instance().getPremisesNumberOnly()) {
                                room.setNumberAppart(localNumberAppartNumberOnly);
                            } else {
                                room.setNumberAppart(localNumberAppart);
                                room.setNumberRoom(arrayData[6]);
                            }

                            room.setIdSpaceGISJKH(arrayData[10]);
                            // процент оплаты перенесен в абоненты                    room.setSharePay(Integer.valueOf(checkZero(arrayData[8])));

                            room.getAbonId().add(Integer.valueOf(checkZero(arrayData[9])));

                            if (arrayData[10].equals("0")) {
                                room.setResidential(false);
                            } else {
                                room.setResidential(true);
                            }

                            if (arrayData.length > 11) {
                                room.setDoorWay(arrayData[11]);
                                room.setTotalArea(new BigDecimal(arrayData[12].replace(",", ".")));
                                room.setPremisesGUID(arrayData[13]);
                                room.setLivingroomGUID(arrayData[14]);
                            }

                            listRooms.add(room);

                        } else { // если есть настройка и помещение нашли - инкрементируем площади и добавляем ИД абонента в список
                            room.getAbonId().add(Integer.valueOf(checkZero(arrayData[9])));
                            if (arrayData.length > 11) {
                                room.setTotalArea(room.getTotalArea().add(new BigDecimal(arrayData[12].replace(",", "."))));
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    LOGGER.error("ExtractSQL: Не верный формат для ячейки.", e);
                }
            }
        }
        if (listRooms.size() == 0) {
            answerProcessing.sendMessageToClient("Запрос " + sqlRequest + " не вернул записей");
            return null; // если нет ЛС возвращаем null.
        }
        return listRooms;
    }

    /**
     * Метод, возвращает массиве с данными из полученной строки.
     *
     * @param data - строка с данными.
     * @return String - массив данных.
     */
    private synchronized String[] getAllData(String data) {

        data = data + "|-1-"; // Если последний параметр пустой, то он в массив не попадет,
        // возникнут ошибки на ссылки на индексы массива.

        return data.split(Pattern.quote("|"));
    }

//    /**
//     * Метод, возвращает временный список содержащий информацию о МКД из процедуры "EX_GIS01".
//     * @return возвращает временный список содержащий информацию о МКД.
//     */
//    public ArrayList<String> getTempMKD() {
//        return tempMKD;
//    }
//
//    /**
//     * Метод, возвращает временный список содержащий информацию о ЖД из процедуры "EX_GIS_LH01".
//     * @return возвращает временный список содержащий информацию о ЖД.
//     */
//    public ArrayList<String> getTempJD() {
//        return tempJD;
//    }

    /**
     * Метод, возвращает из темпа все полученные дома (МКД, ЖД), где ключ - ид дома в Граде, значение - адрес дома.
     * Для отображения в UI список домов для выбора.
     * Внимание, если до этого не разу не получены данные по МКД или ЖД, получите пустую Map.
     */
//    public HashMap<Integer, String> getAllHouseForListModalWindow(final Connection connectionGRAD) throws SQLException, PreGISException {
//        if(allHouses.isEmpty()) {
//            getHouseRecordsFromDB(connectionGRAD);
//        }
//
//        final HashMap<Integer, String> mapAddress = new HashMap<>();
//        for (Map.Entry<String, HouseRecord> houseEntry :allHouses.entrySet()) {
//            mapAddress.put(houseEntry.getValue().getGrad_id(), houseEntry.getValue().getAddresString());
//        }
//
////        for (String item : tempMKD) {
////            final String[] allData = getAllData(item);
////            mapAddress.put(Integer.valueOf(allData[HOUSE_ID_GRAD_MKD]),
////                    String.format("%s %s", allData[ADDRESS_GRAD_MKD], allData[NUMBER_HOUSE_GRAD_MKD]));
////        }
////        for (String item : tempJD) {
////            final String[] allData = getAllData(item);
////            mapAddress.put(Integer.valueOf(allData[HOUSE_ID_GRAD_JD]),
////                    String.format("%s %s", allData[ADDRESS_GRAD_JD], allData[NUMBER_HOUSE_GRAD_JD]));
////        }
//        return mapAddress;
//    }
}
