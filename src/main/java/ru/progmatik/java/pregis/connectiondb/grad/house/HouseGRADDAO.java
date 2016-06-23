package ru.progmatik.java.pregis.connectiondb.grad.house;

import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.ResourcesUtil;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Класс, обращается в БД ГРАД, за данными о МКД.
 */
public class HouseGRADDAO {

    /**
     * Метод, обращается в БД ГРАДа, получает сведенья о доме, извлекает ФИАС и ИД дома в БД ГРАД, затем добавляет его в Map.
     *
     * @return список всех ФИАСов (домов) указаной организации.
     * @throws SQLException выкинет ошибку, если будут проблемы с БД.
     * @throws PreGISException выкинет ошибку, если например не найдем значение в БД.
     */
    public LinkedHashMap<String, Integer> getAllFIAS() throws SQLException, PreGISException {

        List<String> allListHouseData = getAllHouseFromGrad();
        LinkedHashMap<String, Integer> mapFIAS = new LinkedHashMap<String, Integer>();

        for (String itemListHouse : allListHouseData) {
            if (getAllData(itemListHouse)[1] != null && !getAllData(itemListHouse)[1].isEmpty()) {
                mapFIAS.put(getAllData(itemListHouse)[1], Integer.valueOf(getAllData(itemListHouse)[14]));
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
    public LinkedHashMap<String, Integer> getJDAllFias() throws SQLException, PreGISException {

        ArrayList<String> listAllData = getJdAllFiasFromGrad();
        LinkedHashMap<String, Integer> mapJd = new LinkedHashMap<>();
        if (listAllData.size() > 1) { // Если в листе вообще есть данные тогда
            for (String itemData : listAllData) {
                if (getAllData(itemData)[1] != null && !getAllData(itemData)[1].isEmpty()) { // проверяем содержится ФИАС, если есть то добавляем
                    mapJd.put(getAllData(itemData)[1], Integer.valueOf(getAllData(itemData)[10]));
                }
            }
        } else return null; // если в листе нет данных вернем null

        if (mapJd.size() == 0) return null;
        else return mapJd;
    }

    /**
     * Метод, формирует список всех домов (МКД и ЖД), которые уже получили идентификаторы ГИС ЖКХ.
     * Пригодные для использования в дальнейшем например импорт ЛС и ПУ.
     * @return пара ключ-значение ФИАС-ИД ГРАДа.
     */
    public LinkedHashMap<String, Integer> getHouseAddedGisJkh() throws SQLException, PreGISException {

        List<String> allListMKD = getAllHouseFromGrad();
        ArrayList<String> listAllJD = getJdAllFiasFromGrad();
        LinkedHashMap<String, Integer> mapAllHouseWithIdGis = new LinkedHashMap<String, Integer>();

//        Получение данных из БД о МКД.
        for (String itemListHouse : allListMKD) {
            if (getAllData(itemListHouse)[1] != null && !getAllData(itemListHouse)[1].isEmpty() &&
                    getAllData(itemListHouse)[18] != null && !getAllData(itemListHouse)[18].isEmpty()) {
                mapAllHouseWithIdGis.put(getAllData(itemListHouse)[1], Integer.valueOf(getAllData(itemListHouse)[14]));
            }
        }

//        Получение данных из БД о ЖД.
        for (String itemData : listAllJD) {
            if (getAllData(itemData)[1] != null && !getAllData(itemData)[1].isEmpty() &&
                    getAllData(itemData)[14] != null && !getAllData(itemData)[14].isEmpty()) { // проверяем содержится ФИАС, если есть то добавляем
                mapAllHouseWithIdGis.put(getAllData(itemData)[1], Integer.valueOf(getAllData(itemData)[10]));
            }
        }

        if (mapAllHouseWithIdGis.size() == 0)
            return null;
        else
            return mapAllHouseWithIdGis;
    }

    /**
     * Метод, задаёт дому уникальный идентификатор присвоенный ГИС ЖКХ.
     * @param houseId ИД дома в БД ГРАД.
     * @param houseUniqueNumber уникальный номер дома генерируемый ГИС ЖКХ.  @throws SQLException выкинет ошибку, если будут проблемы с БД.
     */
    public void setHouseUniqueNumber(Integer houseId, String houseUniqueNumber) throws SQLException {

//        Integer idHouse = getHouseIdFromGrad(fias);

//        if (idHouse != null) {
            // ИД дома(:building_id),
            // ИД абонента(:abon_id),
            // ИД прибора учета(:meter_id),
            // уникальный идентификатор ГИС ЖКХ(:gis_id),
            // уникальный идентификатор лицевого счета ГИС ЖКХ(:gis_ls_id)
            String sqlRequest = "{EXECUTE PROCEDURE EX_GIS_ID(?, ?, ?, ?, ?)}";
            try (CallableStatement cstmt = ConnectionBaseGRAD.instance().getConnection().prepareCall(sqlRequest)) {
                cstmt.setInt(1, houseId);
                cstmt.setString(4, houseUniqueNumber);
                System.out.println(houseId + " : " + houseUniqueNumber);
                int codeReturn = cstmt.executeUpdate();
                System.err.println("Code return: " + codeReturn);
            } finally {
                ConnectionBaseGRAD.instance().close();
            }
//        } else {
//            throw new PreGISException("setHouseUniqueNumber(): Не удалось найти ID дома в БД ГРАД. С помощь кода ФИАС: " + fias);
//        }
    }

    /**
     * Метод, задаёт помещению уникальный идентификатор, присвоенный ГИС ЖКХ.
     * В БД ГРАД нет отличия помещение или комната, идентификатор вешается на ЛС абонента.
     * @param idHouse ид дома в БД ГРАД.
     * @param apartmentNumber номер помещения (квартиры).
     * @param roomNumber номер комнаты, например в коммунальной квартире.
     * @param apartmentUniqueNumber уникальный номер помещения.
     * @throws SQLException выкинет ошибку, если будут проблемы с БД.
     * @throws PreGISException выкинет ошибку, если например не найдем значение в БД.
     */
    public void setApartmentUniqueNumber(Integer idHouse, String apartmentNumber, String roomNumber, String apartmentUniqueNumber) throws SQLException, PreGISException {

        Integer abonentId = getAbonentIdFromGrad(idHouse, apartmentNumber, roomNumber);

        if (abonentId != null) {
            // ИД дома(:building_id),
            // ИД абонента(:abon_id),
            // ИД прибора учета(:meter_id),
            // уникальный идентификатор ГИС ЖКХ(:gis_id),
            // уникальный идентификатор лицевого счета ГИС ЖКХ(:gis_ls_id)
            String sqlRequest = "{EXECUTE PROCEDURE EX_GIS_ID(?, ?, ?, ?, ?)}";
            try (CallableStatement cstmt = ConnectionBaseGRAD.instance().getConnection().prepareCall(sqlRequest)) {
                cstmt.setInt(2, abonentId);
                cstmt.setString(4, apartmentUniqueNumber);
                int codeReturn = cstmt.executeUpdate();
                System.err.println("Apartment code return: " + codeReturn);
            } finally {
                ConnectionBaseGRAD.instance().close();
            }
        } else {
            throw new PreGISException("setApartmentUniqueNumber(): Не удалось найти ID абонента в БД ГРАД.");
        }
    }

    /**
     * Метод, получает значение номера квартиры и комнаты (если есть), возвращает ИД абонента в БД ГРАД.
     * @param idHouse ид дома в БД ГРАД.
     * @param apartmentNumber номер квартиры.
     * @param roomNumber номер помещения, если есть или null.
     * @return ИД абонента в БД ГРАД или null.
     * @throws SQLException выкинет ошибку, если будут проблемы с БД.
     * @throws PreGISException выкинет ошибку, если например не найдем значение в БД.
     */
    private Integer getAbonentIdFromGrad(Integer idHouse, String apartmentNumber, String roomNumber) throws SQLException, PreGISException {

        Integer abonentId = null;

        ArrayList<String> listData = new ArrayList<>();

        String sqlRequest = "SELECT * FROM EX_GIS_LS2(" + idHouse + ")";

        try (Statement statement = ConnectionBaseGRAD.instance().getConnection().createStatement(); ResultSet resultSet = statement.executeQuery(sqlRequest)) {
            while (resultSet.next()) {

                if (apartmentNumber.equalsIgnoreCase(getAllData(resultSet.getString(1))[3])) {
                    listData.add(resultSet.getString(1));
                }
            }
        } finally {
            ConnectionBaseGRAD.instance().close();
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
     * @param fias код дома по ФИАС
     * @return ИД дома в системе ГРАД.
     * @throws SQLException выкинет ошибку, если будут проблемы с БД.
     * @throws PreGISException выкинет ошибку, если например не найдем значение в БД.
     */
    private Integer getHouseIdFromGrad(String fias) throws SQLException, PreGISException {

        LinkedHashMap<String, Integer> mapMkdData = getAllFIAS();
        LinkedHashMap<String, Integer> mapJdData = getJDAllFias();

        if (mapMkdData.containsKey(fias)) {
            return mapMkdData.get(fias);
        } else if (mapJdData.containsKey(fias)) {
            return mapJdData.get(fias);
        } else {
            return null;
        }
    }

    /**
     * Метод, получает все дома из БД ГРАД.
     * Из процедуры с первого листа Excel.
     *
     * @return список всех домов с параметрами.
     * @throws SQLException выкинет ошибку, если будут проблемы с БД.
     * @throws PreGISException выкинет ошибку, если например не найдем значение в БД.
     */
    private ArrayList<String> getAllHouseFromGrad() throws PreGISException, SQLException {

        ArrayList<String> allHouseData = new ArrayList<>();

        String sqlRequest = "SELECT * FROM EX_GIS01('" + ResourcesUtil.instance().getCompanyGradId() + "')";

        try (Statement statement = ConnectionBaseGRAD.instance().getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sqlRequest)) { // После использования должны все соединения закрыться

            while (resultSet.next()) {
                if (resultSet.getString(1) != null || !resultSet.getString(1).isEmpty()) {
                    allHouseData.add(resultSet.getString(1));
                }
            }
        } finally {
            ConnectionBaseGRAD.instance().close();
        }

        return allHouseData;
    }

    /**
     * Метод, получает все жилые дома содержащие из БД ГРАД.
     * @return список жилых домов.
     */
    private ArrayList<String> getJdAllFiasFromGrad() throws PreGISException, SQLException {

        ArrayList<String> allJdData = new ArrayList<>();

        String sqlRequest = "SELECT * FROM EX_GIS_LH01('" + ResourcesUtil.instance().getCompanyGradId() + "')";

        try (Statement statement = ConnectionBaseGRAD.instance().getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sqlRequest)) { // После использования должны все соединения закрыться

            while (resultSet.next()) {
                if (resultSet.getString(1) != null || !resultSet.getString(1).isEmpty()) {
                    allJdData.add(resultSet.getString(1));
                }
            }
        } finally {
            ConnectionBaseGRAD.instance().close();
        }

        return allJdData;

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
}
