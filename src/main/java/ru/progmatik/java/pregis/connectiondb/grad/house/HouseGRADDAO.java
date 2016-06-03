package ru.progmatik.java.pregis.connectiondb.grad.house;

import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Класс, обращается в БД ГРАД, за данными о МКД.
 */
public class HouseGRADDAO {

    /**
     * Метод, обращается в БД ГРАДа, получает сведенья о доме, извлекает ФИАС и добавляет его в List.
     * @param organizationId id организации из БД ГРАД.
     * @return список всех ФИАСов (домов) указаной организации.
     * @throws SQLException
     */
    public List<String> getAllFIAS(int organizationId) throws SQLException {

        List<String> listFIAS = new ArrayList<>();

        String sqlRequest = "SELECT * FROM EX_GIS01('" + organizationId + "')";

        try (Connection connection = ConnectionBaseGRAD.instance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlRequest)) { // После использования должны все соединения закрыться

            while (resultSet.next()) {
                if (resultSet.getString(1) != null || !resultSet.getString(1).isEmpty()) {
                    if (getAllData(resultSet.getString(1))[1] != null || !getAllData(resultSet.getString(1))[1].isEmpty()) {
                        listFIAS.add(getAllData(resultSet.getString(1))[1]);
                    }
                }
            }
        }
        if (listFIAS.size() > 0)
            return listFIAS;
        else
            return null;
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
