package ru.progmatik.java.pregis.connectiondb.localdb.reference;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemGRADDAO;
import ru.progmatik.java.pregis.model.ReferenceItemDataSet;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, заносит/получает данные в таблицу
 */
public class ServicesGisJkhForGradDAO {

    private static final Logger LOGGER = Logger.getLogger(ServicesGisJkhForGradDAO.class);

    private static final String TABLE_NAME_SERVICES_GISJKH_FOR_GRAD = "SERVICES_GISJKH_FOR_GRAD";  // имя таблицы
    private static final String SQL_CREATE_TABLE_SERVICES_GISJKH_FOR_GRAD = "CREATE TABLE IF NOT EXISTS SERVICES_GISJKH_FOR_GRAD (" +
                                "ID IDENTITY NOT NULL PRIMARY KEY, " +
                                "SERVICE_ID_GISJKH BIGINT NOT NULL, " +
                                "SERVICE_ID_GRAD VARCHAR(20) NOT NULL); " +

                                "COMMENT ON TABLE \"PUBLIC\".SERVICES_GISJKH_FOR_GRAD IS 'Таблица хранит связь услуг (Коммунальных, жилищных и т.д.) ГРАДа и ГИС ЖКХ.'; " +
                                "COMMENT ON COLUMN SERVICES_GISJKH_FOR_GRAD.ID IS 'Идентификатор записей.'; " +
                                "COMMENT ON COLUMN SERVICES_GISJKH_FOR_GRAD.SERVICE_ID_GISJKH IS 'ИД услуги из ГИС ЖКХ в справочнике SERVICES_GIS_JKH в БД ГРАДа.'; " +
                                "COMMENT ON COLUMN SERVICES_GISJKH_FOR_GRAD.SERVICE_ID_GRAD IS 'ИД услуги из ГРАДа в справочнике SERVICE_TRAITS в БД ГРАДа.';";

    /**
     * Конструктор, проверяет, если таблица не создана, то создаёт её.
     * @throws SQLException
     */
    public ServicesGisJkhForGradDAO() throws SQLException {

        if (!ConnectionDB.instance().tableExist(TABLE_NAME_SERVICES_GISJKH_FOR_GRAD.toUpperCase())) {
            ConnectionDB.instance().sendSqlRequest(SQL_CREATE_TABLE_SERVICES_GISJKH_FOR_GRAD);
        }
    }

    /**
     * Метод, получает из таблицы SERVICES_GISJKH_FOR_GRAD все записи.
     * @return список соответствий из базы данных
     * @throws SQLException
     */
    public ArrayList<ServicesGisJkhForGradDataSet> getAllServicesAssociations() throws SQLException {

        ArrayList<ServicesGisJkhForGradDataSet> dataSetList = new ArrayList<>();
        try (ResultSet resultSet = ConnectionDB.instance().getConnectionDB().createStatement().executeQuery(
                "SELECT * FROM " + TABLE_NAME_SERVICES_GISJKH_FOR_GRAD)) {

            while (resultSet.next()) {
                dataSetList.add(new ServicesGisJkhForGradDataSet(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3)));
            }
        }
        return (dataSetList.size() > 0 ? dataSetList : null);
    }

    /**
     * Метод, удаляет из таблице запись.
     * @param idForRemove идентификатор записи в таблице.
     * @throws SQLException
     */
    public void removeServiceAssociations(int idForRemove) throws SQLException {

        try (PreparedStatement ps = ConnectionDB.instance().getConnectionDB().prepareStatement(
                "DELETE FROM SERVICES_GISJKH_FOR_GRAD WHERE ID = ?")) {
            ps.setInt(1, idForRemove);
            ps.executeUpdate();
        }
    }

    /**
     * Метод, добавляет ассоциацию в таблицу.
     * @param idFromGisJkh идентификатор записи услуги полученной из ГИС ЖКХ.
     * @param idFromGrad идентификатор записи услуги в ГРАДе.
     * @throws SQLException
     */
    public void addServiceAssociations(int idFromGisJkh, String idFromGrad) throws SQLException {

        try (PreparedStatement ps = ConnectionDB.instance().getConnectionDB().prepareStatement(
                "INSERT INTO SERVICES_GISJKH_FOR_GRAD(SERVICE_ID_GISJKH, SERVICE_ID_GRAD) VALUES(?, ?);")) {
            ps.setInt(1, idFromGisJkh);
            ps.setString(2, idFromGrad);
            ps.executeUpdate();
        }
    }

    /**
     * Метод, пытается сравнить название услуг и их ассоциировать.
     * @param connectionGrad подключение к базе данных ГРАДа.
     */
    public void autoAllServicesAssociations(Connection connectionGrad) throws SQLException {

        ReferenceItemGRADDAO referenceGRADDAO = new ReferenceItemGRADDAO();
        ArrayList<ReferenceItemDataSet> allItemsReference = referenceGRADDAO.getAllItems(connectionGrad);
        HashMap<String, String> allServiceTraits = getAllServiceTraits(connectionGrad);

        if (allItemsReference != null && allServiceTraits != null) {
            for (ReferenceItemDataSet itemReference : allItemsReference) {
                for (Map.Entry<String, String> entry : allServiceTraits.entrySet()) {
                    if (itemReference.getName().equalsIgnoreCase(entry.getKey())) {
                        if (entry.getValue().contains("SVC")) {
                            addServiceAssociations(itemReference.getId(), entry.getValue());
                        }
                    }
                }
            }
        }
    }

    /**
     * Метод, получает записи столбцов "TRAITNAME" и "CODE" из таблицы "SERVICE_TRAITS".
     * @param connectionGrad подключение к базе данных ГРАДа.
     * @return ассоциативный массив, ключ - название услуги, значение - код услуги в БД ГРАД.
     * @throws SQLException
     */
    private HashMap<String, String> getAllServiceTraits(Connection connectionGrad) throws SQLException {

        HashMap<String, String> serviceTraitsMap = new HashMap<>();
        try (Statement statement = connectionGrad.createStatement();
             ResultSet rs = statement.executeQuery(
                "SELECT TRAITNAME, CODE FROM SERVICE_TRAITS")) {

            while (rs.next()) {
                serviceTraitsMap.put(rs.getString(1).replace("(для отчетов)", "").trim(), rs.getString(2));
            }
        }
        return serviceTraitsMap.size() > 0 ? serviceTraitsMap : null;
    }
}
