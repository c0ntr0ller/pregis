package ru.progmatik.java.pregis.connectiondb.localdb.reference;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemDataSet;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, универсальный для работы с БД со справочниками.
 * В нем описаны все действия со справочниками,
 * необходимо только передавать SQL запросы и дополнительные параметры для работы с БД.
 */
public class ReferenceNSIDAO {

    private static final Logger LOGGER = Logger.getLogger(ReferenceNSIDAO.class);
    private final String tableName;

    /**
     * Конструктор, проверяет, если таблицы с таким именем нет, то он её создаёт, согласно скрипта, который получил.
     * @param tableName имя таблицы
     * @param sqlCreateTable SQL запрос, в котором описано создание таблицы.
     * @throws SQLException
     */
    public ReferenceNSIDAO(String tableName, String sqlCreateTable) throws SQLException {
        this.tableName = tableName;
        if (!ConnectionDB.instance().tableExist(tableName.toUpperCase())) {
            createTableNSI(sqlCreateTable);
        }
    }

    /**
     * Метод, создаёт таблицу из полученного SQL запроса.
     * @param sqlCreateTable SQL запрос, в котором описано создание таблицы.
     * @throws SQLException
     */
    private void createTableNSI(String sqlCreateTable) throws SQLException {

        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCreateTable);
        }
    }

    /**
     * Метод, добавляет элемент в таблицу, при этом делает проверку по ИД элемента,
     * если элемент есть в БД, то он его обновляет.
     * @param dataSet элемент, для сохранения в БД.
     * @throws SQLException
     */
    public void addItem(ReferenceItemDataSet dataSet) throws SQLException {

        if (dataSet.getId() == null) { // проверяем если у элемента нет id, т.е. его нет в базе, то добавляем
            Connection connection = ConnectionDB.instance().getConnectionDB();
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO " + tableName + "(NAME, CODE, GUID, GROUP_NAME, CODE_PARENT) VALUES(?, ? ,? ,?, ?)");
            ps.setString(1, dataSet.getName());
            ps.setString(2, dataSet.getCode());
            ps.setString(3, dataSet.getGuid());
            ps.setString(4, dataSet.getGroupName());
            ps.setInt(5, dataSet.getCodeParent());
            ps.executeUpdate();
            ps.close();
            connection.close();
            LOGGER.info("Добавлеен элемент в справочник: ID = " + dataSet.getId() + " Name: " + dataSet.getName() +
                    " Code: " + dataSet.getCode() + " GUID: " + dataSet.getGuid() + " GROUP_NAME: " + dataSet.getGroupName());
        } else { // иначе проверяем остальные поля и просто обновляем значение в БД.
            if (!dataSet.getName().isEmpty() && !dataSet.getCode().isEmpty() && !dataSet.getGuid().isEmpty()) {  // перестраховка
                updateItem(dataSet);
            }
        }
    }

    /**
     * Метод, обновляет элемент в БД.
     * @param newDataSet элемент, для обновления в БД.
     * @throws SQLException
     */
    private void updateItem(ReferenceItemDataSet newDataSet) throws SQLException {
        Connection connection = ConnectionDB.instance().getConnectionDB();
        PreparedStatement ps = connection.prepareStatement("UPDATE " + tableName + " SET NAME = ?, GUID = ?, " +
                "GROUP_NAME = ?, CODE_PARENT = ? WHERE ID = ? AND CODE = ?");
        ps.setString(1, newDataSet.getName());
        ps.setString(2, newDataSet.getGuid());
        ps.setString(3, newDataSet.getGroupName());
        ps.setInt(4, newDataSet.getCodeParent());
        ps.setInt(5, newDataSet.getId());
        ps.setString(6, newDataSet.getCode());
        ps.executeUpdate();
        ps.close();
        connection.close();
        LOGGER.info("Обновлен элемент справочника: ID = " + newDataSet.getId() +
                " Code: " + newDataSet.getCode() + " GUID: " + newDataSet.getGuid());
    }

    /**
     * Метод, возвращает список всех элементов из таблицы.
     * @return список всех элементов из таблицы.
     * @throws SQLException
     */
    public ArrayList<ReferenceItemDataSet> getAllItems() throws SQLException {

        ArrayList<ReferenceItemDataSet> dataList = new ArrayList<>();
        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName)) {

            while (resultSet.next()) {
                dataList.add(new ReferenceItemDataSet(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6)));
            }
        }
        return dataList;
    }

    /**
     * Метод, формирует асоциативный массив из элемнтов в таблице,
     * где ключ - код элмента справочника, значение сам элемент справочника.
     * @return map, где ключ - код элмента справочника, значение сам элемент справочника.
     * @throws SQLException
     */
    public Map<Integer, ReferenceItemDataSet> getMapItems() throws SQLException {

        Map<Integer, ReferenceItemDataSet> dataList = new HashMap<Integer, ReferenceItemDataSet>();
        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName)) {

            while (resultSet.next()) {
                dataList.put(Integer.valueOf(resultSet.getString(3)), new ReferenceItemDataSet(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6)));
            }
        }
        return dataList;
    }
}
