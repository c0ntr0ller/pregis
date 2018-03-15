package ru.progmatik.java.pregis.connectiondb.localdb.organization;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.model.Organization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс, работает с таблицей "ORGANIZATION".
 * Хранит данные о компании.
 */
public class OrganizationDAO {

    private static final Logger LOGGER = Logger.getLogger(OrganizationDAO.class);
    private static final String TABLE_NAME_ORGANIZATION = "ORGANIZATION";  // имя таблицы

    private static final String SQL_CREATE_TABLE_ORGANIZATION = "CREATE TABLE IF NOT EXISTS ORGANIZATION (" +
            "ID identity not null primary key, " +
            "FULL_NAME varchar(255), " +
            "SHORT_NAME varchar(255), " +
            "OGRN varchar(40), " +
            "INN varchar(30), " +
            "KPP varchar(30), " +
            "ORG_ROOT_ENTITY_GUID varchar(40), " +
            "ORG_PPA_GUID varchar(40), " +
            "ROLE varchar(40), " +
            "GRAD_ID int, " +
            "DESCRIPTION varchar(255)); " +
            "COMMENT ON TABLE PUBLIC.ORGANIZATION IS 'Таблица хранит данные организации.'; " +
            "COMMENT ON COLUMN ORGANIZATION.ID IS 'Идентификатор записей.'; " +
            "COMMENT ON COLUMN ORGANIZATION.FULL_NAME IS 'Полное наименование организации.'; " +
            "COMMENT ON COLUMN ORGANIZATION.SHORT_NAME IS 'Сокращенное наименование организации.'; " +
            "COMMENT ON COLUMN ORGANIZATION.OGRN IS 'ОГРН.'; " +
            "COMMENT ON COLUMN ORGANIZATION.INN IS 'ИНН.'; " +
            "COMMENT ON COLUMN ORGANIZATION.KPP IS 'КПП.'; " +
            "COMMENT ON COLUMN ORGANIZATION.ORG_ROOT_ENTITY_GUID IS 'Идентификатор корневой сущности организации в реестре организаций.'; " +
            "COMMENT ON COLUMN ORGANIZATION.ORG_PPA_GUID IS 'Идентификатор зарегистрированной организации. Используется для запросов'; " +
            "COMMENT ON COLUMN ORGANIZATION.ROLE IS 'Роль организации. RSO или UO'; " +
            "COMMENT ON COLUMN ORGANIZATION.GRAD_ID IS 'Идентификатор организации в системе ГРАД.'; " +
            "COMMENT ON COLUMN ORGANIZATION.DESCRIPTION IS 'Примечание.';";

    /**
     * Конструктор, при инициализации проверяет существует таблица, если таблице нет, то создаёт её.
     *
     * @throws SQLException
     */
    public OrganizationDAO() throws SQLException {
        if (!ConnectionDB.instance().tableExist(TABLE_NAME_ORGANIZATION.toUpperCase())) {
            ConnectionDB.instance().sendSqlRequest(SQL_CREATE_TABLE_ORGANIZATION);
        }
    }

    /**
     * Метод, получает из таблицы все записи, формирует их в ArrayList.
     */
    public ArrayList<Organization> getAllOrganizations() throws SQLException {

//        ArrayList<Organization> orgList = new ArrayList<>();
//        try (Connection connection = ConnectionDB.instance().getConnectionDB();
//             Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery("SELECT " +
//                "ID, FULL_NAME, SHORT_NAME, OGRN, INN, KPP, ORG_ROOT_ENTITY_GUID, ORG_PPA_GUID, ROLE, GRAD_ID, DESCRIPTION " +
//                "FROM ORGANIZATION")) {
//            while (rs.next()) {
//                orgList.add(new Organization(
//                        rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
//                        rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getString(11)));
//            }
//        }
//        return orgList.size() > 0 ? orgList : null;
        return null;
    }

    /**
     * Метод, возвращает orgPPAGUID первой найденной компании.
     * @return orgPPAGUID организации.
     * @throws SQLException
     */
    public String getOrgPPAGUID() throws SQLException {

//        ArrayList<Organization> organizations = getAllOrganizations();
//        if (organizations != null) {
//            return organizations.get(0).getOrgPPAGUID();
//        }
        return null;
    }


    /**
     * Метод, добавляет в таблицу запись, если она не существует.
     *
     * @param dataSet объект для добавления в таблицу БД.
     */
    public void addOrganization(Organization dataSet) throws SQLException {

        Organization foundDataSet = isAddedByOgrn(dataSet);

        if (foundDataSet == null) {
            insertDataSet(dataSet);
        } else if (!foundDataSet.equals(dataSet)) {
            updateDataSet(dataSet);
        }
    }

    /**
     * Метод, ищет запись по ОГРН/ОГРНИП организации.
     *
     * @param dataSet объект для поиска.
     * @return найденный объект.
     * @throws SQLException
     */
    private Organization isAddedByOgrn(Organization dataSet) throws SQLException {
//        ArrayList<Organization> organizations = getAllOrganizations();
//        if (organizations != null) {
//            for (Organization organization : organizations) {
//                if (dataSet.getOgrn().equalsIgnoreCase(organization.getOgrn())) return organization;
//            }
//        }
        return null;
    }

    /**
     * Метод, добавляет в таблицу запись.
     *
     * @param dataSet объект для добавления.
     * @throws SQLException
     */
    private void insertDataSet(Organization dataSet) throws SQLException {

        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO ORGANIZATION(" +
                     "FULL_NAME, SHORT_NAME, OGRN, INN, KPP, ORG_ROOT_ENTITY_GUID, ORG_PPA_GUID, ROLE, GRAD_ID, DESCRIPTION) " +
                     "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, dataSet.getFullName());
            ps.setString(2, dataSet.getShortName());
            ps.setString(3, dataSet.getOgrn());
            ps.setString(4, dataSet.getInn());
            ps.setString(5, dataSet.getKpp());
            ps.setString(6, dataSet.getOrgRootEntityGUID());
            ps.setString(7, dataSet.getOrgPPAGUID());
            ps.setString(8, dataSet.getRole());
            ps.setInt(9, dataSet.getGradId());
            ps.setString(10, dataSet.getDescription());
            ps.executeUpdate();
        }
    }

    /**
     * Метод, обновляет запись в таблице.
     *
     * @param dataSet объект для обновления.
     */
    private void updateDataSet(Organization dataSet) throws SQLException {

        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             PreparedStatement ps = connection.prepareStatement("UPDATE ORGANIZATION " +
                     "SET FULL_NAME = ?, SHORT_NAME = ?, INN = ?, KPP = ?, ORG_ROOT_ENTITY_GUID = ?, " +
                     "ORG_PPA_GUID = ?, ROLE = ?, GRAD_ID = ?, DESCRIPTION = ? " +
                     "WHERE OGRN = ?")) {
            ps.setString(1, dataSet.getFullName());
            ps.setString(2, dataSet.getShortName());
            ps.setString(3, dataSet.getInn());
            ps.setString(4, dataSet.getKpp());
            ps.setString(5, dataSet.getOrgRootEntityGUID());
            ps.setString(6, dataSet.getOrgPPAGUID());
            ps.setString(7, dataSet.getRole());
            ps.setInt(8, dataSet.getGradId());
            ps.setString(9, dataSet.getDescription());
            ps.setString(10, dataSet.getOgrn());
            ps.executeUpdate();
        }
    }
}
