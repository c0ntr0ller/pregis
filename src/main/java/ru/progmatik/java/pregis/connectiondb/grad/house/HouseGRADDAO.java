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

import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Класс, обращается в БД ГРАД, за данными о МКД.
 */
public class HouseGRADDAO {

    private static final int HOUSE_FIAS = 1; // Код дома по ФИАС
    private static final int HOUSE_ID_GRAD_MKD = 14; // ИД дома из БД ГРАД
    private static final int HOUSE_ID_GRAD_JD = 10; // ИД дома из БД ГРАД

//    Временные листы, что бы не ходить 2 раза за одним и темже в БД
    private ArrayList<String> tempMKD = new ArrayList<>();
    private ArrayList<String> tempJD = new ArrayList<>();

    /**
     * Метод, обращается в БД ГРАДа, получает сведенья о доме, извлекает ФИАС и ИД дома в БД ГРАД, затем добавляет его в Map.
     *
     * @return список всех ФИАСов (домов) указаной организации.
     * @throws SQLException    выкинет ошибку, если будут проблемы с БД.
     * @throws PreGISException выкинет ошибку, если например не найдем значение в БД.
     */
    public LinkedHashMap<String, Integer> getAllFIAS(Connection connectionGrad) throws SQLException, PreGISException {

        List<String> allListHouseData = getAllHouseFromGrad(connectionGrad);
        LinkedHashMap<String, Integer> mapFIAS = new LinkedHashMap<String, Integer>();

        for (String itemListHouse : allListHouseData) {
            if (getAllData(itemListHouse)[HOUSE_FIAS] != null && !getAllData(itemListHouse)[HOUSE_FIAS].isEmpty()) {
                mapFIAS.put(getAllData(itemListHouse)[HOUSE_FIAS], Integer.valueOf(getAllData(itemListHouse)[HOUSE_ID_GRAD_MKD]));
            }
        }
        if (mapFIAS.size() == 0)
            return null;
        else
            return mapFIAS;
    }

    /**
     * Метод, возвращает все найденные жилые дома, т.е. их код дома по ФИАС и ИД из БД ГРАД.
     *
     * @return перечень всех жилых домов, ФИАС = ИД дома в БД ГРАД.
     */
    public LinkedHashMap<String, Integer> getJDAllFias(Connection connectionGrad) throws SQLException, PreGISException {

        ArrayList<String> listAllData = getJdAllFiasFromGrad(connectionGrad);
        LinkedHashMap<String, Integer> mapJd = new LinkedHashMap<>();
        if (listAllData.size() > 1) { // Если в листе вообще есть данные тогда
            for (String itemData : listAllData) {
                if (getAllData(itemData)[HOUSE_FIAS] != null && !getAllData(itemData)[HOUSE_FIAS].isEmpty()) { // проверяем содержится ФИАС, если есть то добавляем
                    mapJd.put(getAllData(itemData)[HOUSE_FIAS], Integer.valueOf(getAllData(itemData)[HOUSE_ID_GRAD_JD]));
                }
            }
        } else return null; // если в листе нет данных вернем null

        if (mapJd.size() == 0) return null;
        else return mapJd;
    }

    /**
     * Метод, формирует список всех домов (МКД и ЖД), которые уже получили идентификаторы ГИС ЖКХ.
     * Пригодные для использования в дальнейшем например импорт ЛС и ПУ.
     *
     * @return пара ключ-значение ФИАС-ИД ГРАДа.
     */
    public LinkedHashMap<String, Integer> getHouseAddedGisJkh(Connection connectionGrad) throws SQLException, PreGISException {

        List<String> allListMKD = getAllHouseFromGrad(connectionGrad);
        ArrayList<String> listAllJD = getJdAllFiasFromGrad(connectionGrad);

        LinkedHashMap<String, Integer> mapAllHouseWithIdGis = new LinkedHashMap<String, Integer>();

//        Получение данных из БД о МКД.
        for (String itemListHouse : allListMKD) {
            if (getAllData(itemListHouse)[HOUSE_FIAS] != null && !getAllData(itemListHouse)[HOUSE_FIAS].isEmpty() &&
                    isAddedHouseInGisJkh(Integer.valueOf(getAllData(itemListHouse)[HOUSE_ID_GRAD_MKD]), connectionGrad)) {
                mapAllHouseWithIdGis.put(getAllData(itemListHouse)[HOUSE_FIAS], Integer.valueOf(getAllData(itemListHouse)[HOUSE_ID_GRAD_MKD]));
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
    public void setHouseUniqueNumber(Integer houseId, String houseUniqueNumber, Connection connectionGrad) throws SQLException {

//        Integer idHouse = getHouseIdFromGrad(fias);

//        if (idHouse != null) {
        // ИД дома(:building_id),
        // ИД абонента(:abon_id),
        // ИД прибора учета(:meter_id),
        // уникальный идентификатор ГИС ЖКХ(:gis_id),
        // уникальный идентификатор лицевого счета ГИС ЖКХ(:gis_ls_id)
//        System.err.println("houseID: " + houseId + " houseUniqueNumber: " + houseUniqueNumber);
        String sqlRequest = "{EXECUTE PROCEDURE EX_GIS_ID(NULL, ?, NULL, ?, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL)}";
        try (CallableStatement cstmt = connectionGrad.prepareCall(sqlRequest)) {
            cstmt.setInt(1, houseId);
            cstmt.setString(2, houseUniqueNumber);
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
     * @param premisesGUID    идентификатор помещения.
     * @param premisesUniqNum уникальный номер помещения.
     * @param livingRoomGUID  идентификатор комнаты.
     * @param roomUniqNumber  уникальный номер комнаты.
     * @throws SQLException    выкинет ошибку, если будут проблемы с БД.
     * @throws PreGISException выкинет ошибку, если например не найдем значение в БД.
     */
    public void setApartmentUniqueNumber(Integer houseId, String apartmentNumber, String roomNumber,
                                         String premisesGUID, String premisesUniqNum,
                                         String livingRoomGUID, String roomUniqNumber, Connection connectionGrad) throws SQLException, PreGISException {

        Integer abonentId = getAbonentIdFromGrad(houseId, apartmentNumber, roomNumber, connectionGrad);

        if (abonentId != null) {
            // ИД дома(:building_id),
            // ИД абонента(:abon_id),
            // ИД прибора учета(:meter_id),
            // уникальный идентификатор ГИС ЖКХ(:gis_id),
            // уникальный идентификатор лицевого счета ГИС ЖКХ(:gis_ls_id)
            String sqlRequest = "{EXECUTE PROCEDURE EX_GIS_ID(?, NULL , NULL, NULL, NULL, NULL, ?, ?, ?, ?, NULL, NULL, NULL)}";
            try (CallableStatement cstmt = connectionGrad.prepareCall(sqlRequest)) {
                cstmt.setInt(1, abonentId);
                cstmt.setString(2, premisesGUID);
                cstmt.setString(3, premisesUniqNum);
                cstmt.setString(4, livingRoomGUID);
                cstmt.setString(5, roomUniqNumber);
                cstmt.executeUpdate();
//                int codeReturn = cstmt.executeUpdate();
//                System.err.println("Apartment code return: " + codeReturn);
            }
        } else {
            throw new PreGISException("setApartmentUniqueNumber(): Не удалось найти ID абонента в БД ГРАД.");
        }
    }

    /**
     * Метод, получает из процедуры "EX_GIS04" площадь помещения.
     *
     * @param houseId        идентификатор дома в БД ГРАДа.
     * @param connectionGrad подключение к БД ГРАД.
     * @return ключ - abonId, значение - общая площадь помещения.
     */
    public HashMap<Integer, String> getTotalSquare(int houseId, Connection connectionGrad) throws SQLException {

        HashMap<Integer, String> totalSquareMap = new HashMap<>();
        try (CallableStatement cstmt = connectionGrad.prepareCall("{EXECUTE PROCEDURE EX_GIS04(?)}")) {
            cstmt.setString(1, String.valueOf(houseId));
            ResultSet resultSet = cstmt.executeQuery();

            while (resultSet.next()) {
                String[] data = OtherFormat.getAllDataFromString(resultSet.getString(1));
                totalSquareMap.put(Integer.parseInt(data[7]), data[4]);
            }
        }
        return totalSquareMap;
    }

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
    private Integer getAbonentIdFromGrad(Integer idHouse, String apartmentNumber, String roomNumber, Connection connectionGrad) throws SQLException, PreGISException {

        Integer abonentId = null;

        ArrayList<String> listData = new ArrayList<>();

        String sqlRequest = "SELECT * FROM EX_GIS_LS2(" + idHouse + ")";

        try (Statement statement = connectionGrad.createStatement(); ResultSet resultSet = statement.executeQuery(sqlRequest)) {
            while (resultSet.next()) {

                if (resultSet.getString(1) != null && apartmentNumber.equalsIgnoreCase(getAllData(resultSet.getString(1))[3])) {
                    listData.add(resultSet.getString(1));
                }
            }
        }

        if (listData.size() > 0) {
            if (listData.size() == 1 && roomNumber == null) {
                abonentId = Integer.valueOf(getAllData(listData.get(0))[7]);
            } else if (listData.size() > 1) {
                if (roomNumber != null) {
                    for (String itemData : listData) {
                        if (roomNumber.equalsIgnoreCase(getAllData(itemData)[4])) {
                            abonentId = Integer.valueOf(getAllData(listData.get(0))[7]);
                        }
                    }
                } else {
                    throw new PreGISException("Не указана комната!\nИД абонента для помещения: " + apartmentNumber + " не найдена!");
                }
            }
        }
        return abonentId;
    }

    /**
     * Метод, получает ИД дома из БД ГРАД по коду ФИАС.
     *
     * @param fias код дома по ФИАС
     * @return ИД дома в системе ГРАД.
     * @throws SQLException    выкинет ошибку, если будут проблемы с БД.
     * @throws PreGISException выкинет ошибку, если например не найдем значение в БД.
     */
    private Integer getHouseIdFromGrad(String fias, Connection connectionGrad) throws SQLException, PreGISException {

        LinkedHashMap<String, Integer> mapMkdData = getAllFIAS(connectionGrad);
        LinkedHashMap<String, Integer> mapJdData = getJDAllFias(connectionGrad);

        if (mapMkdData.containsKey(fias)) {
            return mapMkdData.get(fias);
        } else if (mapJdData.containsKey(fias)) {
            return mapJdData.get(fias);
        } else {
            return null;
        }
    }

    /**
     * Метод, получает все МКД из БД ГРАД.
     * Из процедуры с первого листа Excel.
     *
     * @return список всех домов с параметрами.
     * @throws SQLException    выкинет ошибку, если будут проблемы с БД.
     * @throws PreGISException выкинет ошибку, если например не найдем значение в БД.
     */
    private ArrayList<String> getAllHouseFromGrad(final Connection connectionGrad) throws PreGISException, SQLException {

        String sqlRequest = "SELECT * FROM EX_GIS01('" + ResourcesUtil.instance().getCompanyGradId() + "')";

        return getHouseFromGrad(sqlRequest, tempMKD, connectionGrad);
    }



    /**
     * Метод, получает все жилые дома содержащие из БД ГРАД.
     *
     * @return список жилых домов.
     */
    private ArrayList<String> getJdAllFiasFromGrad(final Connection connectionGrad) throws PreGISException, SQLException {

        String sqlRequest = "SELECT * FROM EX_GIS_LH01('" + ResourcesUtil.instance().getCompanyGradId() + "')";

        return getHouseFromGrad(sqlRequest, tempJD, connectionGrad);
    }

    /**
     * Метод, отправляет в БД Града запрос, получает список домов и возвращает его.
     * @param sqlRequest SQL запрос.
     * @param tempDataHouse временный список.
     * @param connectionGrad подключение к БД Град.
     * @return список домов.
     * @throws SQLException
     */
    private ArrayList<String> getHouseFromGrad(final String sqlRequest, final ArrayList<String> tempDataHouse,
                                               final Connection connectionGrad) throws SQLException {

        ArrayList<String> allHouseData = new ArrayList<>();
        tempDataHouse.clear();

        try (Statement statement = connectionGrad.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlRequest)) { // После использования должны все соединения закрыться

            while (resultSet.next()) {
                if (resultSet.getString(1) != null || !resultSet.getString(1).isEmpty()) {
                    allHouseData.add(resultSet.getString(1));
                    tempDataHouse.add(resultSet.getString(1));
                }
            }
        }
        return allHouseData;
    }

    /**
     * Метод, проверяет, получен уникальный номер дома или нет.
     *
     * @param houseId ИД дома в БД ГРАДа.
     * @return true - у дома есть уникальный номер ГИС ЖКХ, false - дом не получил уникальный идентификатор ГИС ЖКХ.
     * @throws SQLException
     */
    private boolean isAddedHouseInGisJkh(Integer houseId, Connection connectionGrad) throws SQLException {

        String sqlRequest = "{EXECUTE PROCEDURE EX_GIS_ID(NULL, ?, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ?)}";

        try (CallableStatement cstmt = connectionGrad.prepareCall(sqlRequest)) { // После использования должны все соединения закрыться
            cstmt.setInt(1, houseId);
            cstmt.setString(2, "HOUSEUNIQNUM");
            ResultSet resultSet = cstmt.executeQuery();
            resultSet.next();
            String sqlResult = resultSet.getString(1);
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

    /**
     * Метод, возвращает временный список содержащий информацию о МКД из процедуры "EX_GIS01".
     * @return возвращает временный список содержащий информацию о МКД.
     */
    public ArrayList<String> getTempMKD() {
        return tempMKD;
    }

    /**
     * Метод, возвращает временный список содержащий информацию о ЖД из процедуры "EX_GIS_LH01".
     * @return возвращает временный список содержащий информацию о ЖД.
     */
    public ArrayList<String> getTempJD() {
        return tempJD;
    }
}
