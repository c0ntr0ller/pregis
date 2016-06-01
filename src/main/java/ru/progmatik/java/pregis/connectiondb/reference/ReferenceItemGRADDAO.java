package ru.progmatik.java.pregis.connectiondb.reference;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, обращается в базу ГРАДа (таблица "SERVICES_GIS_JKH"), для получения или внесения справочников в БД.
 */
public class ReferenceItemGRADDAO {

    private static final Logger LOGGER = Logger.getLogger(ReferenceItemGRADDAO.class);

    private Connection connection;

    public ReferenceItemGRADDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Метод, получает справочники из БД с указанным id.
     *
     * @param id записи в БД.
     */
    public ReferenceItemDataSet getItemId(int id) {

        ReferenceItemDataSet dataSet = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM SERVICES_GIS_JKH WHERE ID='" + id + "'");
            resultSet.next();
            dataSet = new ReferenceItemDataSet(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6));
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        return dataSet;
    }

    /**
     * Метод, возвращает все записи с указанным кодом родительского справочника.
     *
     * @param codeParent код родительского справочники (1, 51 или 59).
     * @return список всех найденных записей.
     */
    public ArrayList<ReferenceItemDataSet> getItemsCodeParent(int codeParent) {

        ArrayList<ReferenceItemDataSet> dataList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM SERVICES_GIS_JKH WHERE CODE_PARENT='" + codeParent + "'");

            while (resultSet.next()) {
                dataList.add(new ReferenceItemDataSet(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6)));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        return dataList;
    }

    public Map<String, ReferenceItemDataSet> getMapItemsCodeParent(int codeParent) {

        Map<String, ReferenceItemDataSet> dataList = new HashMap<String, ReferenceItemDataSet>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM SERVICES_GIS_JKH WHERE CODE_PARENT=" + codeParent);

            while (resultSet.next()) {
                dataList.put(resultSet.getString(3), new ReferenceItemDataSet(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6)));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Метод, получает все записи из таблицы "SERVICES_GIS_JKH".
     *
     * @return список всех записей таблицы.
     */
    public ArrayList<ReferenceItemDataSet> getAllItems() {

        ArrayList<ReferenceItemDataSet> dataList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM SERVICES_GIS_JKH");

            while (resultSet.next()) {
                dataList.add(new ReferenceItemDataSet(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6)));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Метод, добавляет запись в БД.
     *
     * @param dataSet запись, для добавления в БД.
     */
    public void addItem(ReferenceItemDataSet dataSet) {
        try {
            if (dataSet.getId() == null) { // проверяем если у элемента нет id, т.е. его нет в базе, то добавляем
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO SERVICES_GIS_JKH(NAME, CODE, UIID, GROUP_NAME, CODE_PARENT) VALUES(?, ? ,? ,?, ?)");
                ps.setString(1, dataSet.getName());
                ps.setString(2, dataSet.getCode());
                ps.setString(3, dataSet.getUiid());
                ps.setString(4, dataSet.getGroupName());
                ps.setInt(5, dataSet.getCodeParent());
                ps.executeUpdate();
                ps.close();
                LOGGER.info("Добавлеен элемент в справочник: ID = " + dataSet.getId() + " Name: " + dataSet.getName() +
                        " Code: " + dataSet.getCode() + " GUID: " + dataSet.getUiid() + " GROUP_NAME: " + dataSet.getGroupName());
            } else { // иначе проверяем остальные поля и просто обновляем значение в БД.
                if (!dataSet.getName().isEmpty() && !dataSet.getCode().isEmpty() && !dataSet.getUiid().isEmpty()) {  // перестраховка
                    updateItem(dataSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
    }

    private void updateItem(ReferenceItemDataSet newDataset) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE SERVICES_GIS_JKH SET NAME = ?, UIID = ?, " +
                    "GROUP_NAME = ?, CODE_PARENT = ? WHERE ID = ? AND CODE = ?");
            ps.setString(1, newDataset.getName());
            ps.setString(2, newDataset.getUiid());
            ps.setString(3, newDataset.getGroupName());
            ps.setInt(4, newDataset.getCodeParent());
            ps.setInt(5, newDataset.getId());
            ps.setString(6, newDataset.getCode());
            ps.close();
            LOGGER.info("Обновлен элемент справочника: ID = " + newDataset.getId() +
                    " Code: " + newDataset.getCode() + " GUID: " + newDataset.getUiid());
        } catch (SQLException e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
    }

}
