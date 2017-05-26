package ru.progmatik.java.pregis.connectiondb.grad.bills;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.connectiondb.localdb.bills.PaymentDocumentRegistryDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.bills.PaymentDocumentRegistryDataSet;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Бек on 26.05.2017.
 * Документы перенесены в Град
 */
public class PaymentDocumentRegistryGradDAO {

    private static final Logger LOGGER = Logger.getLogger(PaymentDocumentRegistryDAO.class);

    private final AnswerProcessing answerProcessing;

    private static final String TABLE_NAME_PD_REGISTRY = "GIS_PD_REGISTRY";

    /**
     * Конструктор
     */
    public PaymentDocumentRegistryGradDAO(AnswerProcessing answerProcessing) {
            this.answerProcessing = answerProcessing;
    }

    /**
     * Метод, получает все записи с таблицы "GIS_PD_REGISTRY" по дому.
     * @return список всех записей из таблицы "GIS_PD_REGISTRY" по дому.
     */
    public ArrayList<PaymentDocumentRegistryDataSet> getAllPaymentDocumentRecording(Connection connectionGrad) {

        try (Statement statement = connectionGrad.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME_PD_REGISTRY)) {
            return getPaymentDocumentRecordingFromResultSet(rs);
        } catch (SQLException e) {
            answerProcessing.sendErrorToClient("Запрос выгруженных ранее документов", "getAllPaymentDocumentRecording", LOGGER, new PreGISException("Ошибка при получении выгруженных ранее документов"));
        }
        return null;
    }

//    /**
//     * Метод, получает все записи за указанный месяц.
//     * @param month месяц за который нужно выгрузить ПД.
//     * @return список записей за указанный месяц.
//     * @throws SQLException могут возникнуть ошибки во время работы с БД.
//     */
//    public ArrayList<PaymentDocumentRegistryDataSet> getMonthPaymentDocumentRecording(Integer month) throws SQLException {
//
//        ArrayList<PaymentDocumentRegistryDataSet> paymentList = new ArrayList<>();
//
//        try (Statement st = ConnectionDB.instance().getConnectionDB().createStatement();
//             ResultSet rs = st.executeQuery(
//                     "SELECT * FROM " + TABLE_NAME_PD_REGISTRY + " WHERE MONTH = " + month + " AND WHERE YEAR = " + OtherFormat.getYear())) {
//
//            return getPaymentDocumentRecordingFromResultSet(rs);
//        }
//    }

    /**
     * Метод, получает ResultSet и по нему создаёт список объектов полученных из БД.
     * @param rs полученный результат из таблицы "PD_REGISTRY".
     * @return список объектов полученных из таблицы "PD_REGISTRY".
     * @throws SQLException могут возникнуть ошибки во время работы с БД.
     */
    private ArrayList<PaymentDocumentRegistryDataSet> getPaymentDocumentRecordingFromResultSet(final ResultSet rs) throws SQLException {

        final ArrayList<PaymentDocumentRegistryDataSet> paymentList = new ArrayList<>();

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
                    rs.getString(9),    // ACCOUNT_NUMBER
                    rs.getString(10),    // ACCOUNT_GUID
                    rs.getBoolean(11)));// ARCHIVE
        }
        return paymentList;
    }

    /**
     * Метод, добавляет запись в таблицу "PD_REGISTRY".
     * @param registryItem объект для добавления в таблицу.
     */
    public void addPaymentDocumentRegistryItem(final PaymentDocumentRegistryDataSet registryItem, Connection connectionGrad) {

        try {
            PreparedStatement st = connectionGrad.prepareStatement(
                    "INSERT INTO GIS_PD_REGISTRY" +
                            "(NUMBER_PD, UNIQUE_NUMBER, DOCUMENT_GUID, MONTH, YEAR, SUMMA, ABON_ID, ACCOUNT_NUMBER, ACCOUNT_GUID, ARCHIVE) " +
                            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            st.setInt(1, registryItem.getNumberPd());
            st.setString(2, registryItem.getUniqueNumber());
            st.setString(3, registryItem.getGuid());
            st.setInt(4, registryItem.getMonth());
            st.setInt(5, registryItem.getYear());
            st.setBigDecimal(6, registryItem.getSumma());
            st.setInt(7, registryItem.getAbonId());
            st.setString(8, registryItem.getAccountNumber());
            st.setString(9, registryItem.getAccountGuid());
            st.setBoolean(10, registryItem.isArchive());
            st.executeUpdate();
        } catch (SQLException e) {
            answerProcessing.sendErrorToClient("Добавление документа в БД", "addPaymentDocumentRegistryItem", LOGGER, new PreGISException("Ошибка при добавлении документа в БД"));
        }
    }

    /**
     * Метод, по идентификатору записи в таблице определяет, находится ли она в архиве.
     * @param id идентификатор записи в таблице.
     * @return true - запись в архиве, false - запись в архиве не найдена.
     */
    public boolean isArchivePaymentDocument(final int id, Connection connectionGrad) {

        try (PreparedStatement ps = connectionGrad.prepareStatement(
                "SELECT ARCHIVE FROM GIS_PD_REGISTRY WHERE ID = ? AND ARCHIVE <> 0")) {
            ps.setInt(1, id);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            answerProcessing.sendErrorToClient("Проверка документа в БД на архивность", "isArchivePaymentDocument", LOGGER, new PreGISException("Ошибка при проверке документа в БД на архивность"));
        }
        return false;
    }

    /**
     * Метод, переданный объект помечает в таблице как архивный.
     * @param registryItem архивный платежный документ.
     */
    public void setPaymentDocumentRegistryItemToArchive(PaymentDocumentRegistryDataSet registryItem, Connection connectionGrad){

        if (registryItem.getAbonId() != -1) {
            try (PreparedStatement ps = connectionGrad.prepareStatement(
                    "UPDATE GIS_PD_REGISTRY SET ARCHIVE = 1 WHERE ID = ?")) {
                ps.setInt(1, registryItem.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                answerProcessing.sendErrorToClient("Отметка документа в БД как архивного", "isArchivePaymentDocument", LOGGER, new PreGISException("Ошибка при отметке документа в БД как архивного"));
            }
        }
    }

    /**
     * Метод, получает из БД последний номер ПД.
     * @return новый номер ПД.
     */
    public int getPaymentDocumentLastNumber(Connection connectionGrad) throws SQLException {

        try (ResultSet rs = connectionGrad.
                createStatement().executeQuery("SELECT MAX(NUMBER_PD) FROM GIS_PD_REGISTRY")) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            } else {
                return 1;
            }
        }
    }

}
