package ru.progmatik.java.pregis.connectiondb.localdb.bills;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.other.OtherFormat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Класс, работает с таблицей "PD_REGISTRY".
 */
public class PaymentDocumentRegistryDAO {

    private static final Logger LOGGER = Logger.getLogger(PaymentDocumentRegistryDAO.class);

    private static final String TABLE_NAME_PD_REGISTRY = "PD_REGISTRY";

    private static final String SQL_CREATE_TABLE_PD_REGISTRY = "CREATE TABLE IF NOT EXISTS PD_REGISTRY (" +
                                    "ID identity not null primary key, " +
                                    "NUMBER_PD BIGINT not null, " +
                                    "UNIQUE_NUMBER varchar(20), " +
                                    "DOCUMENT_GUID varchar(40), " +
                                    "MONTH INT not null, " +
                                    "YEAR INT not null, " +
                                    "SUMMA DECIMAL, " +
                                    "ABON_ID BIGINT not null, " +
                                    "ACCOUNT_GUID varchar(40) not null, " +
                                    "ARCHIVE BOOLEAN DEFAULT false); " +
                                    "COMMENT ON TABLE PD_REGISTRY IS 'Таблица, хранит реестр платежных документов выгруженных в ГИС ЖКХ.'; " +
                                    "COMMENT ON COLUMN PD_REGISTRY.ID IS 'Идентификатор записей.'; " +
                                    "COMMENT ON COLUMN PD_REGISTRY.NUMBER_PD IS 'Номер платежного документа. Последний номер будет основанием для следующего документа.'; " +
                                    "COMMENT ON COLUMN PD_REGISTRY.UNIQUE_NUMBER IS 'Номер платежного документа присвоенный ГИС ЖКХ.'; " +
                                    "COMMENT ON COLUMN PD_REGISTRY.DOCUMENT_GUID IS 'GUID платежного документа присвоенный ГИС ЖКХ.'; " +
                                    "COMMENT ON COLUMN PD_REGISTRY.MONTH IS 'Месяц за который выгружен ПД.'; " +
                                    "COMMENT ON COLUMN PD_REGISTRY.YEAR IS 'Год за который выгружен ПД.'; " +
                                    "COMMENT ON COLUMN PD_REGISTRY.SUMMA IS 'Общая сумма платежного документа.'; " +
                                    "COMMENT ON COLUMN PD_REGISTRY.ABON_ID IS 'Идентификатор абонента в Граде.'; " +
                                    "COMMENT ON COLUMN PD_REGISTRY.ACCOUNT_GUID IS 'Идентификатор лицевого счета в ГИС ЖКХ.'; " +
                                    "COMMENT ON COLUMN PD_REGISTRY.ARCHIVE IS 'Если ПД архивирован, то нужно оставить пометку.';";

    /**
     * Конструктор, проверяет, если таблицы нет, создаёт.
     * @throws SQLException
     */
    public PaymentDocumentRegistryDAO() throws SQLException {

        if (!ConnectionDB.instance().tableExist(TABLE_NAME_PD_REGISTRY.toUpperCase())) {
            ConnectionDB.instance().sendSqlRequest(SQL_CREATE_TABLE_PD_REGISTRY);
        }
    }

    /**
     * Метод, получает все записи с таблицы "PD_REGISTRY".
     * @return список всех записей из таблицы "PD_REGISTRY".
     * @throws SQLException
     */
    public ArrayList<PaymentDocumentRegistryDataSet> getAllPaymentDocumentRecording() throws SQLException {

        ArrayList<PaymentDocumentRegistryDataSet> paymentList = new ArrayList<>();

        try (Statement st = ConnectionDB.instance().getConnectionDB().createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM " + TABLE_NAME_PD_REGISTRY)) {

            return getPaymentDocumentRecordingFromResultSet(rs);
        }
    }

    /**
     * Метод, получает все записи за указанный месяц.
     * @param month месяц за который нужно выгрузить ПД.
     * @return список записей за указанный месяц.
     * @throws SQLException
     */
    public ArrayList<PaymentDocumentRegistryDataSet> getMonthPaymentDocumentRecording(Integer month) throws SQLException {

        ArrayList<PaymentDocumentRegistryDataSet> paymentList = new ArrayList<>();

        try (Statement st = ConnectionDB.instance().getConnectionDB().createStatement();
             ResultSet rs = st.executeQuery(
                     "SELECT * FROM " + TABLE_NAME_PD_REGISTRY + " WHERE MONTH = " + month + " AND WHERE YEAR = " + OtherFormat.getYear())) {

            return getPaymentDocumentRecordingFromResultSet(rs);
        }
    }

    /**
     * Метод, получает ResultSet и по нему создаёт список объектов полученных из БД.
     * @param rs полученный результат из таблицы "PD_REGISTRY".
     * @return список объектов полученных из таблицы "PD_REGISTRY".
     * @throws SQLException
     */
    private ArrayList<PaymentDocumentRegistryDataSet> getPaymentDocumentRecordingFromResultSet(ResultSet rs) throws SQLException {

        ArrayList<PaymentDocumentRegistryDataSet> paymentList = new ArrayList<>();

        while (rs.next()) {
            paymentList.add(new PaymentDocumentRegistryDataSet(
                    rs.getInt(1),       // ID
                    rs.getInt(2),       // NUMBER_PD
                    rs.getString(3),    // UNIQUE_NUMBER
                    rs.getString(4),    // DOCUMENT_GUID
                    rs.getInt(5),       // MONTH
                    rs.getInt(6),       // YEAR
                    rs.getBigDecimal(7),// SUMMA
                    rs.getInt(8),       // ABON_ID
                    rs.getString(9),    // ACCOUNT_GUID
                    rs.getBoolean(10)));// ARCHIVE
        }

        return paymentList;
    }

    /**
     * Метод, добавляет запись в таблицу "PD_REGISTRY".
     * @param registryItem объект для добавления в таблицу.
     * @throws SQLException
     */
    public void addPaymentDocumentRegistryItem(PaymentDocumentRegistryDataSet registryItem) throws SQLException {

        try (PreparedStatement st = ConnectionDB.instance().getConnectionDB().prepareStatement(
                "INSERT INTO \"PUBLIC\".PD_REGISTRY" +
                        "(NUMBER_PD, UNIQUE_NUMBER, DOCUMENT_GUID, MONTH, YEAR, SUMMA, ABON_ID, ACCOUNT_GUID, ARCHIVE) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);")) {
            st.setInt(1, registryItem.getNumberPd());
            st.setString(2, registryItem.getUniqueNumber());
            st.setString(3, registryItem.getGuid());
            st.setInt(4, registryItem.getMonth());
            st.setInt(5, registryItem.getYear());
            st.setBigDecimal(6, registryItem.getSumma());
            st.setInt(7, registryItem.getAbonId());
            st.setString(8, registryItem.getAccountGuid());
            st.setBoolean(9, registryItem.isArchive());
            st.executeUpdate();
        }
    }

    /**
     * Метод, по идентификатору записи в таблице определяет, находится ли она в архиве.
     * @param id идентификатор записи в таблице.
     * @return true - запись в архиве, false - запись в архиве не найдена.
     * @throws SQLException
     */
    public boolean isArchivePaymentDocument(int id) throws SQLException {

        try (PreparedStatement ps = ConnectionDB.instance().getConnectionDB().prepareStatement(
                "SELECT ARCHIVE FROM PD_REGISTRY WHERE ID = ? AND ARCHIVE IS NOT NULL")) {
            ps.setInt(1, id);
            return ps.executeQuery().next();
        }
    }

    /**
     * Метод, переданный объект помечает в таблице как архивный.
     * @param registryItem архивный платежный документ.
     */
    public void setPaymentDocumentRegistryItemToArchive(PaymentDocumentRegistryDataSet registryItem) throws SQLException {

        if (registryItem.getAbonId() != -1) {
            try (PreparedStatement ps = ConnectionDB.instance().getConnectionDB().prepareStatement(
                    "UPDATE \"PUBLIC\".PD_REGISTRY SET ARCHIVE = ? WHERE ID = ?")) {
                ps.setBoolean(1, true);
                ps.setInt(2, registryItem.getId());
                ps.executeUpdate();
            }
        }
    }

    /**
     * Метод, получает из БД последний номер ПД.
     * @return новый номер ПД.
     */
    public int getPaymentDocumentLastNumber() throws SQLException {

        try (ResultSet rs = ConnectionDB.instance().getConnectionDB().
                createStatement().executeQuery("SELECT MAX(NUMBER_PD) FROM PD_REGISTRY")) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            } else {
                return 1;
            }
        }
    }

}
