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
import ru.progmatik.java.pregis.connectiondb.grad.account.AccountGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.Rooms;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.ResourcesUtil;

import java.sql.*;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Класс, обращается в БД ГРАД, за данными о МКД.
 */
public final class HouseGRADDAO {

    private static final int HOUSE_ADDRESS_GRAD_MKD = 0; // Адрес дома в ГРАД
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
    private final LinkedHashMap<String, HouseRecord> allHouses = new LinkedHashMap<>();
    private final HashMap<Integer, ArrayList<Rooms>> temMapRooms = new HashMap<>();

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
    public LinkedHashMap<String, HouseRecord> getHouseRecords(final Integer houseGradId, final Connection connectionGrad) throws SQLException, PreGISException {
        // если список пустой - запрашиваем из БД
        if (allHouses.isEmpty()) {
            getHouseRecordsFromDB(connectionGrad);
        }
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
    private void getHouseRecordsFromDB(final Connection connectionGrad) throws SQLException, PreGISException {
        // получем сначала все МКД
        final LinkedHashMap<String, HouseRecord> mapMkd = getAllMkdHouseRecords(connectionGrad);
        // получаем все жилые дома
        final LinkedHashMap<String, HouseRecord> mapJd = getAllJdHouseRecords(connectionGrad);

        if (mapMkd != null) {
            allHouses.putAll(mapMkd);
        }
        if (mapJd != null) {
            allHouses.putAll(mapJd);
        }
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
        final LinkedHashMap<String, HouseRecord> mapMkd = new LinkedHashMap<>();
        if (allListHouseData.size() > 0) {
            for (String itemListHouse : allListHouseData) {
                if (getAllData(itemListHouse)[HOUSE_FIAS] != null && !getAllData(itemListHouse)[HOUSE_FIAS].isEmpty()) {
                    String[] houseAllData = getAllData(itemListHouse);
                    String HOUSE_ID = houseAllData[HOUSE_ID_GRAD_MKD];

                    if (HOUSE_ID.equals("-1-")) {
                        answerProcessing.sendMessageToClient("-1- Не найден ИД дома в данных Града в строке: " + itemListHouse);
                    } else {
                        mapMkd.put(houseAllData[HOUSE_FIAS],
                                new HouseRecord(
                                        houseAllData[HOUSE_FIAS],
                                        Integer.valueOf(houseAllData[HOUSE_ID_GRAD_MKD]),
                                        houseAllData[HOUSE_ADDRESS_GRAD_MKD],
                                        ((houseAllData.length <= HOUSE_HOUSEUNIQNUM) ? "" : houseAllData[HOUSE_HOUSEUNIQNUM]),
                                        String.format("%s %s", houseAllData[ADDRESS_GRAD_MKD], houseAllData[NUMBER_HOUSE_GRAD_MKD])
                                )
                        );
                    }
                }
            }
        }
        if (mapMkd.size() == 0)
            return null;
        else {
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
        final LinkedHashMap<String, HouseRecord> mapJd = new LinkedHashMap<>();
        if (allListHouseData.size() > 1) { // Если в листе вообще есть данные тогда
            for (String itemListHouse : allListHouseData) {
                if (getAllData(itemListHouse)[HOUSE_FIAS] != null && !getAllData(itemListHouse)[HOUSE_FIAS].isEmpty()) {
                    String[] houseAllData = getAllData(itemListHouse);
                    String HOUSE_ID = houseAllData[HOUSE_ID_GRAD_JD];

                    if (HOUSE_ID.equals("-1-")) {
                        answerProcessing.sendMessageToClient("-1- Не найден ИД дома в данных Града в строке: " + itemListHouse);
                    } else {
                        mapJd.put(houseAllData[HOUSE_FIAS],
                                new HouseRecord(
                                        houseAllData[HOUSE_FIAS],
                                        Integer.valueOf(houseAllData[HOUSE_ID_GRAD_JD]),
                                        houseAllData[HOUSE_ADDRESS_GRAD_MKD],
                                        ((houseAllData.length <= HOUSE_HOUSEUNIQNUM) ? "" : houseAllData[HOUSE_HOUSEUNIQNUM]),
                                        String.format("%s %s", houseAllData[ADDRESS_GRAD_JD], houseAllData[NUMBER_HOUSE_GRAD_JD])
                                )
                        );
                    }
                }
            }
        } else return null; // если в листе нет данных вернем null

        if (mapJd.size() == 0)
            return null;
        else {
            return mapJd;
        }
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

        if (mapAllHouseWithIdGis.size() == 0)
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
     * @param houseId         ид дома в БД ГРАД.
     * @param apartmentNumber номер помещения (квартиры, используется для поиска абонента в БД ГРАД).
     * @param roomNumber      номер комнаты, например в коммунальной квартире (используется для поиска абонента в БД ГРАД).
     * @param isResidential   - жилое/нежилое
     * @param premisesGUID    идентификатор помещения.
     * @param premisesUniqNum уникальный номер помещения.
     * @param livingRoomGUID  идентификатор комнаты.
     * @param roomUniqNumber  уникальный номер комнаты.
     * @throws SQLException    выкинет ошибку, если будут проблемы с БД.
     * @throws PreGISException выкинет ошибку, если например не найдем значение в БД.
     */
    public void setApartmentUniqueNumber(final Integer houseId, final String apartmentNumber, final String roomNumber, final boolean isResidential,
                                         final String premisesGUID, final String premisesUniqNum,
                                         final String livingRoomGUID, final String roomUniqNumber,
                                         final Connection connectionGrad) throws SQLException, PreGISException, ParseException {

        final Integer abonentId = getAbonentIdFromGrad(houseId, apartmentNumber, roomNumber, isResidential, connectionGrad);

        if (abonentId != null) {
            // ИД дома(:building_id),
            // ИД абонента(:abon_id),
            // ИД прибора учета(:meter_id),
            // уникальный идентификатор ГИС ЖКХ(:gis_id),
            // уникальный идентификатор лицевого счета ГИС ЖКХ(:gis_ls_id)
            final String sqlRequest = "{EXECUTE PROCEDURE EX_GIS_ID(?, NULL , NULL, NULL, NULL, NULL, ?, ?, ?, ?, NULL, NULL, NULL, NULL, ?)}";
            try (CallableStatement cstmt = connectionGrad.prepareCall(sqlRequest)) {
                cstmt.setInt(1, abonentId);
                cstmt.setString(2, premisesGUID);
                cstmt.setString(3, premisesUniqNum);
                cstmt.setString(4, livingRoomGUID);
                cstmt.setString(5, roomUniqNumber);
                cstmt.setInt(6, ResourcesUtil.instance().getCompanyGradId());
                cstmt.executeUpdate();
//                int codeReturn = cstmt.executeUpdate();
//                System.err.println("Apartment code return: " + codeReturn);
            }
        } else {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendMessageToClient("Не удалось найти ID абонента в БД ГРАД, запись пропущена. Кв № = " + apartmentNumber + "; ");
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
    private Integer getAbonentIdFromGrad(final Integer idHouse, final String apartmentNumber,
                                         final String roomNumber,
                                         final boolean isResidential,
                                         final Connection connectionGrad) throws SQLException, PreGISException, ParseException {

        if (false == temMapRooms.containsKey(idHouse)) {
//            Если IDEA выделила просто не обращай внимание, зачистую конструкция (!temMapRooms.containsKey(idHouse)),
//            менее заметнее чем указанную выше, где явно должны получить false
            final AccountGRADDAO accountGRADDAO = new AccountGRADDAO(answerProcessing);
            temMapRooms.clear();
            temMapRooms.put(idHouse, accountGRADDAO.getRooms(idHouse, connectionGrad));
        }

        final Integer abonentId = findAbonId(idHouse, apartmentNumber, roomNumber, isResidential);

        if (abonentId == null && roomNumber == null) {
            answerProcessing.sendMessageToClient(String.format("ИД абонента для помещения: %s не найден! ИД дома: %d", apartmentNumber, idHouse)); //!!!------
//            throw new PreGISException(String.format("ИД абонента для помещения: %s не найден! ИД дома: %d", apartmentNumber, idHouse));
        } else if (abonentId == null) {
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
    private Integer findAbonId(final Integer idHouse, // TODO В запросе по 124 дому возвращается квартира 1, но поему-то тут она не находится! Сделать вывод в лог и проверить
                               final String apartmentNumber,
                               final String roomNumber,
                               final boolean isResidential) {

        if (idHouse != null && temMapRooms.containsKey(idHouse) && apartmentNumber != null && temMapRooms.get(idHouse) != null) {
            for (Rooms room : temMapRooms.get(idHouse)) {
                if (apartmentNumber.equalsIgnoreCase(room.getNumberRooms())) { // если нашли одинаковые помещения (квартиры)
                    if (roomNumber == null && room.getNumberApartment() == null) { // если номер комнаты отсутствует
                        if (room.isResidential() == isResidential) {
                            return room.getAbonId();
                        }
                    } else if (roomNumber != null && roomNumber.equalsIgnoreCase(room.getNumberApartment())) { // если номер комнаты совпадает.
                        return room.getAbonId();
                    }
                }
            }
        }
        return null;
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
            final String apiColumnName = "RAPISTR";
            for (int i = 1; i < resultSet.getMetaData().getColumnCount(); i++) {
                if (resultSet.getMetaData().getColumnName(i).equals(apiColumnName)) {
                    apiStrExists = true;
                    break;
                }
            }

            if (apiStrExists) { // если база возвращает новый формат данных - обрабатываем их
                while (resultSet.next()) {
                    if (resultSet.getString(1) != null || !resultSet.getString(1).isEmpty()) {
                        allHouseData.add(resultSet.getString(1));
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
