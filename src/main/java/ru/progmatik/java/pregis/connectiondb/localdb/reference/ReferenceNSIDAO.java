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
    private static final String TABLE_NAME_NSI = "SPR_NSI";
    private static final String TABLE_NAME_NSI_DOWNLOAD = "NSI_FOR_DOWNLOAD";
    private static final String TABLE_NAME_SPR_NSI_TYPE = "SPR_NSI_TYPE";

    private static final String SQL_CREATE_TABLE_NSI = "CREATE TABLE IF NOT EXISTS SPR_NSI (" +
            "ID identity not null primary key, " +
            "NAME varchar(255) not null, " +
            "CODE varchar(20) not null, " +
            "GUID varchar(40), " +
            "GROUP_NAME varchar(255), " +
            "CODE_PARENT varchar(20)); " +
            "COMMENT ON TABLE \"PUBLIC\".SPR_NSI IS 'Справочник НСИ ГИС ЖКХ." +
            "COMMENT ON COLUMN SPR_NSI.ID IS 'Идентификатор записей.'; " +
            "COMMENT ON COLUMN SPR_NSI.NAME IS 'Наименование поля элемента справочника.'; " +
            "COMMENT ON COLUMN SPR_NSI.CODE IS 'Код элемента справочника, уникальный в пределах справочника.'; " +
            "COMMENT ON COLUMN SPR_NSI.GUID IS 'Глобально-уникальный идентификатор элемента справочника ГИС ЖКХ.'; " +
            "COMMENT ON COLUMN SPR_NSI.GROUP_NAME IS 'Название группы, если такая имеется, которая объединяет элементы справочника.'; " +
            "COMMENT ON COLUMN SPR_NSI.CODE_PARENT IS 'Код родительского справочника.';";

    private static final String SQL_CREATE_TABLE_NSI_DOWNLOAD = "CREATE TABLE IF NOT EXISTS NSI_FOR_DOWNLOAD (" +
            "ID identity not null primary key, " +
            "CODE varchar(20) not null, " +
            "WORD_FOR_EXTRACT varchar(255) not null, " +
            "NSI_TYPE varchar(20) not null;" +
            "COMMENT ON TABLE \"PUBLIC\".NSI_FOR_DOWNLOAD IS '{Хранит коды справочников и название элементов для извлечения;" +
            "COMMENT ON COLUMN NSI_FOR_DOWNLOAD.ID IS 'Идентификатор записей.'; " +
            "COMMENT ON COLUMN NSI_FOR_DOWNLOAD.CODE IS 'Код справочника, который необходимо загрузить.'; " +
            "COMMENT ON COLUMN NSI_FOR_DOWNLOAD.WORD_FOR_EXTRACT IS 'Для извлечения нужного элемента из справочника, используется ключевое слово.';" +
            "COMMENT ON COLUMN NSI_FOR_DOWNLOAD.NSI_TYPE IS 'Тип справочника НСИ или НСИРАО.'; " +
            "ALTER TABLE \"PUBLIC\".NSI_FOR_DOWNLOAD ADD FOREIGN KEY (NSI_TYPE) REFERENCES \"PUBLIC\".NSI_TYPE(ID);";

    private static final String SQL_CREATE_TABLE_SPR_NSI_TYPE = "CREATE TABLE IF NOT EXISTS SPR_NSI_TYPE (" +
            "ID identity not null primary key, " +
            "NSI_TYPE varchar(20) not null;" +
            "COMMENT ON TABLE \"PUBLIC\".SPR_NSI_TYPE IS '{Хранит типы справочников ГИС ЖКХ. НСИ или НСИРАО;" +
            "COMMENT ON COLUMN SPR_NSI_TYPE.ID IS 'Идентификатор записей.'; " +
            "COMMENT ON COLUMN SPR_NSI_TYPE.NSI_TYPE IS 'Тип справочника НСИ или НСИРАО.';";


    /**
     * Конструктор, проверяет, если таблицы с таким именем нет, то он её создаёт.
     * @throws SQLException
     */
    public ReferenceNSIDAO() throws SQLException {

        if (!ConnectionDB.instance().tableExist(TABLE_NAME_SPR_NSI_TYPE.toUpperCase())) {
            createTableNSI(SQL_CREATE_TABLE_SPR_NSI_TYPE);
        }
        if (!ConnectionDB.instance().tableExist(TABLE_NAME_NSI_DOWNLOAD.toUpperCase())) {
            createTableNSI(SQL_CREATE_TABLE_NSI_DOWNLOAD);
        }
        if (!ConnectionDB.instance().tableExist(TABLE_NAME_NSI.toUpperCase())) {
            createTableNSI(SQL_CREATE_TABLE_NSI);
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
                    "INSERT INTO SPR_NSI(NAME, CODE, GUID, GROUP_NAME, CODE_PARENT) VALUES(?, ? ,? ,?, ?)");
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
        PreparedStatement ps = connection.prepareStatement("UPDATE SPR_NSI SET NAME = ?, GUID = ?, " +
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
             ResultSet resultSet = statement.executeQuery("SELECT * FROM SPR_NSI")) {

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
             ResultSet resultSet = statement.executeQuery("SELECT * FROM SPR_NSI")) {

            while (resultSet.next()) {
                dataList.put(Integer.valueOf(resultSet.getString(3)), new ReferenceItemDataSet(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6)));
            }
        }
        return dataList;
    }

    public Map<String, ReferenceItemDataSet> getMapItemsCodeParent(int codeParent) throws SQLException {

        Map<String, ReferenceItemDataSet> dataList = new HashMap<String, ReferenceItemDataSet>();
        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM SPR_NSI WHERE CODE_PARENT=" + codeParent)) {

            while (resultSet.next()) {
                dataList.put(resultSet.getString(3), new ReferenceItemDataSet(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6)));
            }
            return dataList;
        }
    }
}
