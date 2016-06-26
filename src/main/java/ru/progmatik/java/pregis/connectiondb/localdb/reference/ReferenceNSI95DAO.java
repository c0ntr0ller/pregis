package ru.progmatik.java.pregis.connectiondb.localdb.reference;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemDataSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Класс, работает с БД, заносит и получает данные для справочника НСИ 95 "Документ, удостоверяющий личность".
 * Справочник используется при импорте ЛС в систему ГИС ЖКХ.
 */
public class ReferenceNSI95DAO {

    private static final Logger LOGGER = Logger.getLogger(ReferenceNSI95DAO.class);
    private static final String SQL_TABLE_NAME = "SPR_NSI95";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS SPR_NSI95 (" +
                                                            "ID identity not null primary key, " +
                                                            "NAME varchar(255) not null, " +
                                                            "CODE varchar(20) not null, " +
                                                            "GUID varchar(40), " +
                                                            "GROUP_NAME varchar(255), " +
                                                            "CODE_PARENT varchar(20)); " +
                                                            "COMMENT ON TABLE \"PUBLIC\".SPR_NSI95 IS 'Справочник НСИ-95. Содержит информацию о \"Документ, удостоверяющий личность\".'; " +
                                                            "COMMENT ON COLUMN SPR_NSI95.ID IS 'Идентификатор записей.'; " +
                                                            "COMMENT ON COLUMN SPR_NSI95.NAME IS 'Наименование поля элемента справочника.'; " +
                                                            "COMMENT ON COLUMN SPR_NSI95.CODE IS 'Код элемента справочника, уникальный в пределах справочника.'; " +
                                                            "COMMENT ON COLUMN SPR_NSI95.GUID IS 'Глобально-уникальный идентификатор элемента справочника ГИС ЖКХ.'; " +
                                                            "COMMENT ON COLUMN SPR_NSI95.GROUP_NAME IS 'Название группы, если такая имеется, которая объединяет элементы справочника.'; " +
                                                            "COMMENT ON COLUMN SPR_NSI95.CODE_PARENT IS 'Код родительского справочника.';";
    private final ReferenceNSIDAO nsiDao;

    public ReferenceNSI95DAO() throws SQLException {
        nsiDao = new ReferenceNSIDAO();
//        nsiDao = new ReferenceNSIDAO(SQL_TABLE_NAME, SQL_CREATE_TABLE);
//        if (!ConnectionDB.instance().tableExist(SQL_TABLE_NAME)) {
//            createTableNSI95();
//        }
    }

//    public void createTableNSI95() throws SQLException {
//
//        try (Connection connection = ConnectionDB.instance().getConnectionDB();
//             Statement statement = connection.createStatement()) {
//            statement.executeUpdate("CREATE TABLE IF NOT EXISTS SPR_NSI95 (" +
//                    "ID identity not null primary key, " +
//                    "NAME varchar(255) not null, " +
//                    "CODE varchar(20) not null, " +
//                    "GUID varchar(40), " +
//                    "GROUP_NAME varchar(255), " +
//                    "CODE_PARENT varchar(20)); " +
//                    "COMMENT ON TABLE \"PUBLIC\".SPR_NSI95 IS 'Справочник НСИ-95. Содержит информацию о \"Документ, удостоверяющий личность\".'; " +
//                    "COMMENT ON COLUMN SPR_NSI95.ID IS 'Идентификатор записей.'; " +
//                    "COMMENT ON COLUMN SPR_NSI95.NAME IS 'Наименование поля элемента справочника.'; " +
//                    "COMMENT ON COLUMN SPR_NSI95.CODE IS 'Код элемента справочника, уникальный в пределах справочника.'; " +
//                    "COMMENT ON COLUMN SPR_NSI95.GUID IS 'Глобально-уникальный идентификатор элемента справочника ГИС ЖКХ.'; " +
//                    "COMMENT ON COLUMN SPR_NSI95.GROUP_NAME IS 'Название группы, если такая имеется, которая объединяет элементы справочника.'; " +
//                    "COMMENT ON COLUMN SPR_NSI95.CODE_PARENT IS 'Код родительского справочника.';");
//        }
//    }

    public void addItem(ReferenceItemDataSet dataSet) throws SQLException {

        nsiDao.addItem(dataSet);

//        if (dataSet.getId() == null) { // проверяем если у элемента нет id, т.е. его нет в базе, то добавляем
//            Connection connection = ConnectionDB.instance().getConnectionDB();
//            PreparedStatement ps = connection.prepareStatement(
//                    "INSERT INTO SPR_NSI95(NAME, CODE, GUID, GROUP_NAME, CODE_PARENT) VALUES(?, ? ,? ,?, ?)");
//            ps.setString(1, dataSet.getName());
//            ps.setString(2, dataSet.getCode());
//            ps.setString(3, dataSet.getGuid());
//            ps.setString(4, dataSet.getGroupName());
//            ps.setInt(5, dataSet.getCodeParent());
//            ps.executeUpdate();
//            ps.close();
//            connection.close();
//            LOGGER.info("Добавлеен элемент в справочник: ID = " + dataSet.getId() + " Name: " + dataSet.getName() +
//                    " Code: " + dataSet.getCode() + " GUID: " + dataSet.getGuid() + " GROUP_NAME: " + dataSet.getGroupName());
//        } else { // иначе проверяем остальные поля и просто обновляем значение в БД.
//            if (!dataSet.getName().isEmpty() && !dataSet.getCode().isEmpty() && !dataSet.getGuid().isEmpty()) {  // перестраховка
//                updateItem(dataSet);
//            }
//        }
    }

//    private void updateItem(ReferenceItemDataSet newDataset) throws SQLException {

//        Connection connection = ConnectionDB.instance().getConnectionDB();
//        PreparedStatement ps = connection.prepareStatement("UPDATE SPR_NSI95 SET NAME = ?, GUID = ?, " +
//                "GROUP_NAME = ?, CODE_PARENT = ? WHERE ID = ? AND CODE = ?");
//        ps.setString(1, newDataset.getName());
//        ps.setString(2, newDataset.getGuid());
//        ps.setString(3, newDataset.getGroupName());
//        ps.setInt(4, newDataset.getCodeParent());
//        ps.setInt(5, newDataset.getId());
//        ps.setString(6, newDataset.getCode());
//        ps.executeUpdate();
//        ps.close();
//        connection.close();
//        LOGGER.info("Обновлен элемент справочника: ID = " + newDataset.getId() +
//                " Code: " + newDataset.getCode() + " GUID: " + newDataset.getGuid());
//    }

    public ArrayList<ReferenceItemDataSet> getAllItems() throws SQLException {

//        ArrayList<ReferenceItemDataSet> dataList = new ArrayList<>();
//        try (Connection connection = ConnectionDB.instance().getConnectionDB();
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery("SELECT * FROM SPR_NSI95")) {
//
//            while (resultSet.next()) {
//                dataList.add(new ReferenceItemDataSet(resultSet.getInt(1), resultSet.getString(2),
//                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6)));
//            }
//        }
        return nsiDao.getAllItems();
    }

    public Map<String, ReferenceItemDataSet> getMapItems() throws SQLException {

//        Map<Integer, ReferenceItemDataSet> dataList = new HashMap<Integer, ReferenceItemDataSet>();
//        try (Connection connection = ConnectionDB.instance().getConnectionDB();
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery("SELECT * FROM SPR_NSI95")) {
//
//            while (resultSet.next()) {
//                dataList.put(Integer.valueOf(resultSet.getString(3)), new ReferenceItemDataSet(resultSet.getInt(1), resultSet.getString(2),
//                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6)));
//            }
//        }
        return nsiDao.getMapItems();
    }


}
