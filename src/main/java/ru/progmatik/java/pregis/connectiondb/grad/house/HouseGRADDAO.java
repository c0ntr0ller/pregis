package ru.progmatik.java.pregis.connectiondb.grad.house;

import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.ResourcesUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Класс, обращается в БД ГРАД, за данными о МКД.
 */
public class HouseGRADDAO {

    /**
     * Метод, обращается в БД ГРАДа, получает сведенья о доме, извлекает ФИАС и добавляет его в List.
     *
     * @return список всех ФИАСов (домов) указаной организации.
     * @throws SQLException
     * @throws PreGISException
     */
    public List<String> getAllFIAS() throws SQLException, PreGISException {

        List<String> allListHouseData = getAllHouseFromGrad();
        List<String> listFIAS = new ArrayList<>();

        for (String itemListHouse : allListHouseData) {
            if (getAllData(itemListHouse)[1] != null && !getAllData(itemListHouse)[1].isEmpty()) {
                listFIAS.add(getAllData(itemListHouse)[1]);
            }
        }
        if (listFIAS.size() > 0)
            return listFIAS;
        else
            return null;
    }

    /**
     * Метод, задаёт дому уникальный идентификатор присвоенный ГИС ЖКХ.
     * @throws SQLException
     * @throws PreGISException
     */
    public void setHouseUniqueNumber(String fias, String houseUniqueNumber) throws SQLException, PreGISException {

        Integer idHouse = null;
        ArrayList<String> allListHouseData = getAllHouseFromGrad();
        for (String itemHouse : allListHouseData) {
            if (fias.equals(getAllData(itemHouse)[1])) {
                idHouse = Integer.valueOf(getAllData(itemHouse)[14]);
                break;
            }
        }
        if (idHouse != null) {
            // ИД дома(:building_id),
            // ИД абонента(:abon_id),
            // ИД прибора учета(:meter_id),
            // уникальный идентификатор ГИС ЖКХ(:gis_id),
            // уникальный идентификатор лицевого счета ГИС ЖКХ(:gis_ls_id)
            String sqlRequest = "{EXECUTE PROCEDURE EX_GIS_ID(?, ?, ?, ?, ?)}";
            try (CallableStatement cstmt = ConnectionBaseGRAD.instance().getConnection().prepareCall(sqlRequest)) {
                cstmt.setInt(1, idHouse);
                cstmt.setString(4, houseUniqueNumber);
                cstmt.executeUpdate();
            } finally {
                ConnectionBaseGRAD.instance().close();
            }
        } else {
            throw new PreGISException("setHouseUniqueNumber(): Не удалось найти ID дома в БД ГРАД.");
        }
    }

    /**
     * Метод, получает все дома из БД ГРАД.
     * Из процедуры с первого листа Excel.
     *
     * @return список всех домов с параметрами.
     * @throws PreGISException
     * @throws SQLException
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
