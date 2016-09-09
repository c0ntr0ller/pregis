package ru.progmatik.java.pregis.connectiondb.grad.reference;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceDownloadNSIDataSet;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSIDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, обращается в базу ГРАДа (таблица "SERVICES_GIS_JKH"), для получения или внесения справочников в БД.
 */
public class ReferenceItemGRADDAO {

    private static final Logger LOGGER = Logger.getLogger(ReferenceItemGRADDAO.class);
    private final ReferenceNSIDAO nsidao;

    public ReferenceItemGRADDAO() throws SQLException {
//        Будем работать с встроеной базаой через её классы
        nsidao = new ReferenceNSIDAO();
    }

    /**
     * Метод, получает справочники из БД с указанным id.
     *
     * @param id записи в БД.
     */
    public ReferenceItemDataSet getItemId(int id, Connection connectionGrad) {

        ReferenceItemDataSet dataSet = null;
        try (Statement statement = connectionGrad.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM SERVICES_GIS_JKH WHERE ID='" + id + "'")) {

            resultSet.next();
            dataSet = new ReferenceItemDataSet(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
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
    public ArrayList<ReferenceItemDataSet> getItemsCodeParent(int codeParent, Connection connectionGrad) {

        ArrayList<ReferenceItemDataSet> dataList = new ArrayList<>();
        try (Statement statement = connectionGrad.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM SERVICES_GIS_JKH WHERE CODE_PARENT='" + codeParent + "'")) {

            while (resultSet.next()) {
                dataList.add(new ReferenceItemDataSet(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        return dataList;
    }

    public Map<String, ReferenceItemDataSet> getMapItemsCodeParent(String codeParent, Connection connectionGrad) {

        Map<String, ReferenceItemDataSet> dataList = new HashMap<String, ReferenceItemDataSet>();
        try (Statement statement = connectionGrad.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM SERVICES_GIS_JKH WHERE CODE_PARENT=" + codeParent)) {

            while (resultSet.next()) {
                dataList.put(resultSet.getString(3), new ReferenceItemDataSet(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }
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
    public ArrayList<ReferenceItemDataSet> getAllItems(Connection connectionGrad) {

        ArrayList<ReferenceItemDataSet> dataList = new ArrayList<>();
        try (Statement statement = connectionGrad.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM SERVICES_GIS_JKH")) {

            while (resultSet.next()) {
                dataList.add(new ReferenceItemDataSet(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }
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
    public void addItem(ReferenceItemDataSet dataSet, Connection connectionGrad) {
        try (PreparedStatement ps = connectionGrad.prepareStatement(
                "INSERT INTO SERVICES_GIS_JKH(NAME, CODE, UIID, GROUP_NAME, CODE_PARENT) VALUES(?, ? ,? ,?, ?)")){
            if (dataSet.getId() == null) { // проверяем если у элемента нет id, т.е. его нет в базе, то добавляем
                ps.setString(1, dataSet.getName());
                ps.setString(2, dataSet.getCode());
                ps.setString(3, dataSet.getGuid());
                ps.setString(4, dataSet.getGroupName());
                ps.setInt(5, Integer.parseInt(dataSet.getCodeParent()));
                ps.executeUpdate();
                LOGGER.info("Добавлеен элемент в справочник: ID = " + dataSet.getId() + " Name: " + dataSet.getName() +
                        " Code: " + dataSet.getCode() + " GUID: " + dataSet.getGuid() + " GROUP_NAME: " + dataSet.getGroupName());
            } else { // иначе проверяем остальные поля и просто обновляем значение в БД.
                if (!dataSet.getName().isEmpty() && !dataSet.getCode().isEmpty() && !dataSet.getGuid().isEmpty()) {  // перестраховка
                    updateItem(dataSet, connectionGrad);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
    }

    private void updateItem(ReferenceItemDataSet newDataset, Connection connectionGrad) {
        try (PreparedStatement ps = connectionGrad.prepareStatement("UPDATE SERVICES_GIS_JKH SET NAME = ?, UIID = ?, " +
                "GROUP_NAME = ?, CODE_PARENT = ? WHERE ID = ? AND CODE = ?")) {
            ps.setString(1, newDataset.getName());
            ps.setString(2, newDataset.getGuid());
            ps.setString(3, newDataset.getGroupName());
            ps.setInt(4, Integer.parseInt(newDataset.getCodeParent()));
            ps.setInt(5, newDataset.getId());
            ps.setString(6, newDataset.getCode());
            ps.executeUpdate();
            LOGGER.info("Обновлен элемент справочника: ID = " + newDataset.getId() +
                    " Code: " + newDataset.getCode() + " GUID: " + newDataset.getGuid());
        } catch (SQLException e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Метод, передаёт из базы информацию о ключевых словах для поиска элементов в справочники.
     * @return ключ - код справочника, значение - ключевое слова для извлечения информации из справочника.
     * @throws SQLException
     */
    public HashMap<String, ArrayList<String>> getNsiDataForExecute() throws SQLException {
        return nsidao.getNsiDataProviderForExtractMap();
    }

    /**
     * Метод, передает из базы информацию о справочниках, которые необходимо подгрузить в БД ГРАДа.
     * @return список объектов содержащих код справочника и тип справочника.
     * @throws SQLException
     */
    public ArrayList<ReferenceDownloadNSIDataSet> getNsiDataForDownload() throws SQLException {
        return nsidao.getNsiDataProviderForDownload();
    }
}
