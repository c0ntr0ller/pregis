package ru.progmatik.java.pregis.connectiondb.localdb.reference;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemDataSet;
import ru.progmatik.java.pregis.services.nsi.common.service.NsiListGroupEnum;

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
    private static final String TABLE_NAME_NSI = "SPR_NSI";  // основная таблица
    private static final String TABLE_NAME_NSI_DOWNLOAD = "NSI_FOR_DOWNLOAD"; // таблица содержит перечень справочников для загрузки и слово, по окоторому будет извлечено значение из справочника
    private static final String TABLE_NAME_SPR_NSI_TYPE = "SPR_NSI_TYPE";

    private static final String TABLE_NAME_NSI_DATA_PROVIDER_FOR_DOWNLOAD = "NSI_DATA_PROVIDER_FOR_DOWNLOAD";
    private static final String TABLE_NAME_NSI_DATA_PROVIDER_FOR_EXTRACT = "NSI_DATA_PROVIDER_FOR_EXTRACT";

    private static final String SQL_CREATE_TABLE_NSI = "CREATE TABLE IF NOT EXISTS SPR_NSI (" +
            "ID identity not null primary key, " +
            "NAME varchar(255) not null, " +
            "CODE varchar(20) not null, " +
            "GUID varchar(40), " +
            "GROUP_NAME varchar(255), " +
            "CODE_PARENT varchar(20)); " +
            "COMMENT ON TABLE \"PUBLIC\".SPR_NSI IS 'Справочник НСИ ГИС ЖКХ.'; " +
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
            "NSI_TYPE varchar(20) not null); " +
            "COMMENT ON TABLE \"PUBLIC\".NSI_FOR_DOWNLOAD IS 'Хранит коды справочников и название элементов для извлечения.';" +
            "COMMENT ON COLUMN NSI_FOR_DOWNLOAD.ID IS 'Идентификатор записей.'; " +
            "COMMENT ON COLUMN NSI_FOR_DOWNLOAD.CODE IS 'Код справочника, который необходимо загрузить.'; " +
            "COMMENT ON COLUMN NSI_FOR_DOWNLOAD.WORD_FOR_EXTRACT IS 'Для извлечения нужного элемента из справочника, используется ключевое слово.';" +
            "COMMENT ON COLUMN NSI_FOR_DOWNLOAD.NSI_TYPE IS 'Тип справочника НСИ или НСИРАО.'; " +
            "ALTER TABLE \"PUBLIC\".NSI_FOR_DOWNLOAD ADD FOREIGN KEY (NSI_TYPE) REFERENCES \"PUBLIC\".SPR_NSI_TYPE(ID); " +
            "INSERT INTO NSI_FOR_DOWNLOAD(CODE, WORD_FOR_EXTRACT, NSI_TYPE) VALUES('2', 'Вид коммунального ресурса', select ID from SPR_NSI_TYPE WHERE NSI_TYPE = 'NSI'); " +
            "INSERT INTO NSI_FOR_DOWNLOAD(CODE, WORD_FOR_EXTRACT, NSI_TYPE) VALUES('16', 'Межповерочный интервал', select ID from SPR_NSI_TYPE WHERE NSI_TYPE = 'NSI'); " +
            "INSERT INTO NSI_FOR_DOWNLOAD(CODE, WORD_FOR_EXTRACT, NSI_TYPE) VALUES('21', 'Причина архивации прибора учета', select ID from SPR_NSI_TYPE WHERE NSI_TYPE = 'NSI'); " +
            "INSERT INTO NSI_FOR_DOWNLOAD(CODE, WORD_FOR_EXTRACT, NSI_TYPE) VALUES('22', 'Причина закрытия лицевого счета', select ID from SPR_NSI_TYPE WHERE NSI_TYPE = 'NSI'); " +
            "INSERT INTO NSI_FOR_DOWNLOAD(CODE, WORD_FOR_EXTRACT, NSI_TYPE) VALUES('27', 'Тип прибора учета', select ID from SPR_NSI_TYPE WHERE NSI_TYPE = 'NSI'); " +
            "INSERT INTO NSI_FOR_DOWNLOAD(CODE, WORD_FOR_EXTRACT, NSI_TYPE) VALUES('95', 'Вид документа, удостоверяющего личность', select ID from SPR_NSI_TYPE WHERE NSI_TYPE = 'NSI'); " +
            "INSERT INTO NSI_FOR_DOWNLOAD(CODE, WORD_FOR_EXTRACT, NSI_TYPE) VALUES('224', 'Причина выхода ПУ из строя', select ID from SPR_NSI_TYPE WHERE NSI_TYPE = 'NSI');";

    private static final String SQL_CREATE_TABLE_SPR_NSI_TYPE = "CREATE TABLE IF NOT EXISTS SPR_NSI_TYPE (" +
            "ID identity not null primary key, " +
            "NSI_TYPE varchar(20) not null); " +
            "COMMENT ON TABLE \"PUBLIC\".SPR_NSI_TYPE IS 'Хранит типы справочников ГИС ЖКХ. НСИ или НСИРАО.'; " +
            "COMMENT ON COLUMN SPR_NSI_TYPE.ID IS 'Идентификатор записей.'; " +
            "COMMENT ON COLUMN SPR_NSI_TYPE.NSI_TYPE IS 'Тип справочника НСИ или НСИРАО.'; " +
            "INSERT INTO SPR_NSI_TYPE(NSI_TYPE) VALUES('NSI'); " +
            "INSERT INTO SPR_NSI_TYPE(NSI_TYPE) VALUES('NSIRAO');";

    private static final String SQL_CREATE_TABLE_NSI_DATA_PROVIDER_FOR_DOWNLOAD = "CREATE TABLE IF NOT EXISTS NSI_DATA_PROVIDER_FOR_DOWNLOAD (" +
            "ID identity not null primary key, " +
            "CODE varchar(20) not null, " +
            "NSI_TYPE varchar(20) not null); " +
            "COMMENT ON TABLE \"PUBLIC\".NSI_DATA_PROVIDER_FOR_DOWNLOAD IS 'Справочники для загрузки в БД ГРАД.'; " +
            "COMMENT ON COLUMN NSI_DATA_PROVIDER_FOR_DOWNLOAD.ID IS 'Идентификатор записей.'; " +
            "COMMENT ON COLUMN NSI_DATA_PROVIDER_FOR_DOWNLOAD.CODE IS 'Код справочника, который необходимо загрузить.'; " +
            "COMMENT ON COLUMN NSI_DATA_PROVIDER_FOR_DOWNLOAD.NSI_TYPE IS 'Тип справочника НСИ или НСИРАО.';";
//            "INSERT INTO NSI_DATA_PROVIDER_FOR_DOWNLOAD(CODE, NSI_TYPE) VALUES('50', 'NSI');"; // Если потребуется.

    private static final String SQL_CREATE_TABLE_NSI_DATA_PROVIDER_FOR_EXTRACT = "CREATE TABLE IF NOT EXISTS NSI_DATA_PROVIDER_FOR_EXTRACT (" +
            "ID identity not null primary key, " +
            "CODE varchar(20) not null, " +
            "WORD_FOR_EXTRACT varchar(255) not null); " +
            "COMMENT ON TABLE \"PUBLIC\".NSI_DATA_PROVIDER_FOR_EXTRACT IS 'Таблица содержит перечень данных для извлечения нужных данных из справочников ГИС ЖКХ в БД ГРАД.'; " +
            "COMMENT ON COLUMN NSI_DATA_PROVIDER_FOR_EXTRACT.ID IS 'Идентификатор записей.'; " +
            "COMMENT ON COLUMN NSI_DATA_PROVIDER_FOR_EXTRACT.CODE IS 'Код справочника, который необходимо загрузить.'; " +
            "COMMENT ON COLUMN NSI_DATA_PROVIDER_FOR_EXTRACT.WORD_FOR_EXTRACT IS 'Для извлечения нужного элемента из справочника, используется ключевое слово.';" +
            "INSERT INTO NSI_DATA_PROVIDER_FOR_EXTRACT(CODE, WORD_FOR_EXTRACT) VALUES('1', 'Вид дополнительной услуги'); " +
            "INSERT INTO NSI_DATA_PROVIDER_FOR_EXTRACT(CODE, WORD_FOR_EXTRACT) VALUES('51', 'Вид коммунальной услуги'); " +
            "INSERT INTO NSI_DATA_PROVIDER_FOR_EXTRACT(CODE, WORD_FOR_EXTRACT) VALUES('51', 'Главная коммунальная услуга'); " +
            "INSERT INTO NSI_DATA_PROVIDER_FOR_EXTRACT(CODE, WORD_FOR_EXTRACT) VALUES('3', 'Вид коммунальной услуги'); " +
            "INSERT INTO NSI_DATA_PROVIDER_FOR_EXTRACT(CODE, WORD_FOR_EXTRACT) VALUES('59', 'Вид работ'); " +
            "INSERT INTO NSI_DATA_PROVIDER_FOR_EXTRACT(CODE, WORD_FOR_EXTRACT) VALUES('59', 'Наименование работы в системе'); " +
            "INSERT INTO NSI_DATA_PROVIDER_FOR_EXTRACT(CODE, WORD_FOR_EXTRACT) VALUES('56', 'Вид работ'); " +
            "INSERT INTO NSI_DATA_PROVIDER_FOR_EXTRACT(CODE, WORD_FOR_EXTRACT) VALUES('50', 'Вид жилищной  услуги');";

    /**
     * Конструктор, проверяет, если таблицы с таким именем нет, то он её создаёт.
     *
     * @throws SQLException
     */
    public ReferenceNSIDAO() throws SQLException {

        if (!ConnectionDB.instance().tableExist(TABLE_NAME_SPR_NSI_TYPE.toUpperCase())) {
            sendSqlRequest(SQL_CREATE_TABLE_SPR_NSI_TYPE);
        }
        if (!ConnectionDB.instance().tableExist(TABLE_NAME_NSI_DOWNLOAD.toUpperCase())) {
            sendSqlRequest(SQL_CREATE_TABLE_NSI_DOWNLOAD);
        }
        if (!ConnectionDB.instance().tableExist(TABLE_NAME_NSI.toUpperCase())) {
            sendSqlRequest(SQL_CREATE_TABLE_NSI);
        }
        if (!ConnectionDB.instance().tableExist(TABLE_NAME_NSI_DATA_PROVIDER_FOR_DOWNLOAD.toUpperCase())) {
            sendSqlRequest(SQL_CREATE_TABLE_NSI_DATA_PROVIDER_FOR_DOWNLOAD);
        }
        if (!ConnectionDB.instance().tableExist(TABLE_NAME_NSI_DATA_PROVIDER_FOR_EXTRACT.toUpperCase())) {
            sendSqlRequest(SQL_CREATE_TABLE_NSI_DATA_PROVIDER_FOR_EXTRACT);
        }
    }

    /**
     * Метод, создаёт таблицу из полученного SQL запроса.
     *
     * @param sqlCreateTable SQL запрос, в котором описано создание таблицы.
     * @throws SQLException
     */
    private void sendSqlRequest(String sqlCreateTable) throws SQLException {

        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCreateTable);
        }
    }

    /**
     * Метод, добавляет элемент в таблицу, при этом делает проверку по ИД элемента,
     * если элемент есть в БД, то он его обновляет.
     *
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
            ps.setString(5, dataSet.getCodeParent());
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
     *
     * @param newDataSet элемент, для обновления в БД.
     * @throws SQLException
     */
    private void updateItem(ReferenceItemDataSet newDataSet) throws SQLException {
        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             PreparedStatement ps = connection.prepareStatement("UPDATE SPR_NSI SET NAME = ?, GUID = ?, " +
                     "GROUP_NAME = ?, CODE_PARENT = ? WHERE ID = ? AND CODE = ?")) {

            ps.setString(1, newDataSet.getName());
            ps.setString(2, newDataSet.getGuid());
            ps.setString(3, newDataSet.getGroupName());
            ps.setString(4, newDataSet.getCodeParent());
            ps.setInt(5, newDataSet.getId());
            ps.setString(6, newDataSet.getCode());
            ps.executeUpdate();
            ps.close();
            connection.close();
            LOGGER.info("Обновлен элемент справочника: ID = " + newDataSet.getId() +
                    " Code: " + newDataSet.getCode() + " GUID: " + newDataSet.getGuid());
        }
    }

    /**
     * Метод, возвращает список всех элементов из таблицы.
     *
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
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }
        }
        return dataList;
    }

    /**
     * Метод, возвращает список элементов из указанной родительской таблицы.
     *
     * @return список всех элементов из указанной таблицы.
     * @throws SQLException
     */
    public ArrayList<ReferenceItemDataSet> getAllItemsCodeParent(String codeParent) throws SQLException {

        ArrayList<ReferenceItemDataSet> dataList = new ArrayList<>();
        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM SPR_NSI WHERE CODE_PARENT=" + codeParent)) {

            while (resultSet.next()) {
                dataList.add(new ReferenceItemDataSet(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }
        }
        return dataList;
    }

    /**
     * Метод, формирует асоциативный массив из элемнтов в таблице,
     * где ключ - код элмента справочника, значение сам элемент справочника.
     *
     * @return map, где ключ - код элмента справочника, значение сам элемент справочника.
     * @throws SQLException
     */
    public Map<String, ReferenceItemDataSet> getMapItems() throws SQLException {

        Map<String, ReferenceItemDataSet> dataList = new HashMap<String, ReferenceItemDataSet>();
        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM SPR_NSI")) {

            while (resultSet.next()) {
                dataList.put(resultSet.getString(3), new ReferenceItemDataSet(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }
        }
        return dataList;
    }

    /**
     * Метод, по указанному коду родительского справочника создаёт фильтр, формирует асоциативный массив из элемнтов в таблице,
     * где ключ - код элмента справочника, значение сам элемент справочника.
     *
     * @param codeParent код родительского справочника.
     * @return ключ - код элмента справочника, значение сам элемент справочника.
     * @throws SQLException
     */
    public Map<String, ReferenceItemDataSet> getMapItemsCodeParent(String codeParent) throws SQLException {

        Map<String, ReferenceItemDataSet> dataList = new HashMap<String, ReferenceItemDataSet>();
        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM SPR_NSI WHERE CODE_PARENT=" + codeParent)) {

            while (resultSet.next()) {
                dataList.put(resultSet.getString(3), new ReferenceItemDataSet(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }
            return dataList;
        }
    }

    /**
     * Метод, добавляет справочник в список обновлений.
     * @param dataSet объект содержащий информацию пригодную для дальнейшего обновления справочников.
     * @throws SQLException
     */
    public void addNsiForDownload(ReferenceDownloadNSIDataSet dataSet) throws SQLException {

        boolean isContains = true;

        ArrayList<ReferenceDownloadNSIDataSet> nsiForDownloads = getNsiForDownload();

        for (ReferenceDownloadNSIDataSet nsiDataSet : nsiForDownloads) {
            if (dataSet.getCode().equals(nsiDataSet.getCode())) {
                if (dataSet.getWorldForExtract().equals(nsiDataSet.getWorldForExtract())) {
                    isContains = false;
                }
            }
        }
        if (isContains) {
            String sqlRequest = "INSERT INTO NSI_FOR_DOWNLOAD(CODE, WORD_FOR_EXTRACT, NSI_TYPE) VALUES(" +
                    "'" + dataSet.getCode() + "', " +
                    "'" + dataSet.getWorldForExtract() + "', " +
                    "select ID from SPR_NSI_TYPE WHERE NSI_TYPE = '" + dataSet.getNsiType().getNsi() + "');";
            sendSqlRequest(sqlRequest);
        }
    }

    /**
     * Метод, получает из БД список справочников для обновления.
     * @return список объектов содержащих информацию об обновлении справочников.
     * @throws SQLException
     */
    public ArrayList<ReferenceDownloadNSIDataSet> getNsiForDownload() throws SQLException {

        ArrayList<ReferenceDownloadNSIDataSet> listDataSets = new ArrayList<>();
        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT NSI_FOR_DOWNLOAD.ID, CODE, WORD_FOR_EXTRACT, SPR_NSI_TYPE.NSI_TYPE " +
                     "FROM NSI_FOR_DOWNLOAD, SPR_NSI_TYPE  " +
                     "where NSI_FOR_DOWNLOAD.NSI_TYPE = SPR_NSI_TYPE.ID")) {

            while (resultSet.next()) {
                listDataSets.add(new ReferenceDownloadNSIDataSet(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), NsiListGroupEnum.getNsiGroup(resultSet.getString(4))));
            }
        }
        return listDataSets;
    }

    /**
     * Метод, добавляет справочник для таблице в БД ГРАД в список обновлений.
     * NSI_DATA_PROVIDER_FOR_DOWNLOAD
     * @param dataSet объект содержащий код справочника и тип справочника.
     * @throws SQLException
     */
    public void addNsiDataProviderForDownload(ReferenceDownloadNSIDataSet dataSet) throws SQLException {

        boolean isContains = true;

        ArrayList<ReferenceDownloadNSIDataSet> nsiForDownloads = getNsiDataProviderForDownload();

        for (ReferenceDownloadNSIDataSet nsiDataSet : nsiForDownloads) {
            if (dataSet.getCode().equals(nsiDataSet.getCode())) {
                if (dataSet.getNsiType().equals(nsiDataSet.getNsiType())) {
                    isContains = false;
                }
            }
        }
        if (isContains) {
            String sqlRequest = "INSERT INTO NSI_DATA_PROVIDER_FOR_DOWNLOAD(CODE, NSI_TYPE) VALUES('" + dataSet.getCode() + "', '" + dataSet.getNsiType().getNsi() + "');";
            sendSqlRequest(sqlRequest);
        }
    }

    /**
     * Метод, получает из БД список справочников для обновления в БД ГРАД.
     * NSI_DATA_PROVIDER_FOR_DOWNLOAD
     * @return список объектов содержащих код справочника и тип справочника.
     * @throws SQLException
     */
    public ArrayList<ReferenceDownloadNSIDataSet> getNsiDataProviderForDownload() throws SQLException {

        ArrayList<ReferenceDownloadNSIDataSet> listDataSets = new ArrayList<>();
        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM NSI_DATA_PROVIDER_FOR_DOWNLOAD")) {

            while (resultSet.next()) {
                listDataSets.add(new ReferenceDownloadNSIDataSet(resultSet.getInt(1), resultSet.getString(2),
                        NsiListGroupEnum.getNsiGroup(resultSet.getString(3))));
            }
        }
        return listDataSets;
    }

    /**
     * Метод, добавляет в таблицу ключевых слов новую запись, в БД ГРАД.
     * NSI_DATA_PROVIDER_FOR_EXTRACT
     *
     * @param dataSet объект содержащий код справочника и ключевое слово.
     * @throws SQLException
     */
    public void addNsiDataProviderForExtract(ReferenceDownloadNSIDataSet dataSet) throws SQLException {

        HashMap<String, ArrayList<String>> extractMap = getNsiDataProviderForExtractMap();

        if (extractMap.containsKey(dataSet.getCode())) {
            if (!extractMap.get(dataSet.getCode()).contains(dataSet.getWorldForExtract())) {
                String sqlRequest = "INSERT INTO NSI_DATA_PROVIDER_FOR_EXTRACT(CODE, WORD_FOR_EXTRACT) VALUES('" + dataSet.getCode() + "', '" + dataSet.getWorldForExtract() + "');";
                sendSqlRequest(sqlRequest);
            }
        } else {
            String sqlRequest = "INSERT INTO NSI_DATA_PROVIDER_FOR_EXTRACT(CODE, WORD_FOR_EXTRACT) VALUES('" + dataSet.getCode() + "', '" + dataSet.getWorldForExtract() + "');";
            sendSqlRequest(sqlRequest);
        }
    }

    /**
     * Метод, получает из БД список кодов справочников и ключевых слов для извлечения нужной информации из справочника в БД ГРАД.
     * NSI_DATA_PROVIDER_FOR_EXTRACT
     *
     * @return ключ - код справочника, значение - ключевое слова для извлечения информации из справочника.
     * @throws SQLException
     */
    public HashMap<String, ArrayList<String>> getNsiDataProviderForExtractMap() throws SQLException {

        HashMap<String, ArrayList<String>> mapNsiDataForExtract = new HashMap<>();
        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM NSI_DATA_PROVIDER_FOR_EXTRACT")) {

            while (resultSet.next()) {
                if (mapNsiDataForExtract.containsKey(resultSet.getString(2))) {
                    mapNsiDataForExtract.get(resultSet.getString(2)).add(resultSet.getString(3));
                } else {
                    mapNsiDataForExtract.put(resultSet.getString(2), new ArrayList<String>());
                    mapNsiDataForExtract.get(resultSet.getString(2)).add(resultSet.getString(3));
                }
            }
        }
        return mapNsiDataForExtract;
    }

}
