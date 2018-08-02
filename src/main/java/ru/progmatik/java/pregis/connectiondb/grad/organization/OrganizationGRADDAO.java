package ru.progmatik.java.pregis.connectiondb.grad.organization;

import org.apache.log4j.Logger;
import org.jtemplate.sql.Parameters;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.model.Organization;
import ru.progmatik.java.pregis.other.AnswerProcessing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class OrganizationGRADDAO {
    public static final Logger LOGGER = Logger.getLogger(OrganizationGRADDAO.class);

    private static final String SQL_INSERT_REQUEST = "update or insert into ex_gis_organizations\n" +
            "    (full_name,short_name,ogrn,inn,kpp,org_root_entity_guid,org_ppa_guid,orgrole,grad_id,description)\n" +
            "  values (:full_name, :short_name, :ogrn, :inn, :kpp, :org_root_entity_guid, :org_ppa_guid, :orgrole, :grad_id, :description) matching (grad_id)";
    private static final String SQL_SELECT_REQUEST = "select full_name, short_name, ogrn, inn, kpp, org_root_entity_guid, org_ppa_guid, orgrole, grad_id, description\n" +
            "    from ex_gis_organizations o where o.grad_id = :orgId or o.rkc_org = :orgId";
    /**
     * метод получает из Град список организаций, относящихся к указанной. Если РКЦ - вернет все относящиеся к РКЦ организации о таблице EX_GIS_ORGANIZATIONS
     * для единичной орагнизации просто возвращает саму орагнизацию
     * @param orgId - ИД организации
     * @return - мапа с организациями
     * @throws SQLException
     */
    public static Map<Integer, Organization> getOrgsList(int orgId, AnswerProcessing answerProcessing) throws SQLException {
        Map<Integer, Organization> organizationList = new HashMap<>();

        answerProcessing.sendOkMessageToClient("Запрос организаций из Града");

        try (Connection connectionGrad = ConnectionBaseGRAD.instance().getConnection()){

            Parameters parameters = Parameters.parse(SQL_SELECT_REQUEST);

            try(PreparedStatement statement = connectionGrad.prepareStatement(parameters.getSQL())){

                parameters.put("orgId", orgId);
                parameters.apply(statement);

//                statement.setInt(1, orgId);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    organizationList.put(resultSet.getInt("grad_id"),
                            new Organization(
                                    resultSet.getString("full_name"),
                                    resultSet.getString("short_Name"),
                                    resultSet.getString("ogrn"),
                                    resultSet.getString("inn"),
                                    resultSet.getString("kpp"),
                                    resultSet.getString("org_root_entity_guid"),
                                    resultSet.getString("org_ppa_guid"),
                                    resultSet.getString("orgrole"),
                                    resultSet.getInt("grad_id"),
                                    resultSet.getString("description"))
                    );
                }
            }
        }

        answerProcessing.sendOkMessageToClient(String.format("Получено %d организаций из Града", organizationList.size()));
        return organizationList;
    }

    public static Organization getOrg(int orgId, AnswerProcessing answerProcessing) throws SQLException {
        return getOrgsList(orgId, answerProcessing).get(orgId);
    }

    /**
     * метод заносит в Град данные по организациям
     * @param orgs - список организаций
     * @throws SQLException
     */
    public static void setOrgListToGrad(Map<Integer,Organization> orgs, AnswerProcessing answerProcessing) throws SQLException {

        try (Connection connectionGrad = ConnectionBaseGRAD.instance().getConnection()){

            Parameters parameters = Parameters.parse(SQL_INSERT_REQUEST);

            try (PreparedStatement preparedStatement = connectionGrad.prepareStatement(parameters.getSQL())) {
                for (Organization organization: orgs.values()) {

                    answerProcessing.sendOkMessageToClient(String.format("Сохранение организации %s", organization.getFullName()));

                    parameters.put("full_name", organization.getFullName());
                    parameters.put("short_name", organization.getShortName());
                    parameters.put("ogrn", organization.getOgrn());
                    parameters.put("inn", organization.getInn());
                    parameters.put("kpp", organization.getKpp());
                    parameters.put("org_root_entity_guid", organization.getOrgRootEntityGUID());
                    parameters.put("org_ppa_guid", organization.getOrgPPAGUID());
                    parameters.put("orgrole", organization.getRole());
                    parameters.put("grad_id", organization.getGradId());
                    parameters.put("description", organization.getDescription());

                    parameters.apply(preparedStatement);

//                    preparedStatement.setString(1, organization.getFullName());
//                    preparedStatement.setString(2, organization.getShortName());
//                    preparedStatement.setString(3, organization.getOgrn());
//                    preparedStatement.setString(4, organization.getInn());
//                    preparedStatement.setString(5, organization.getKpp());
//                    preparedStatement.setString(6, organization.getOrgRootEntityGUID());
//                    preparedStatement.setString(7, organization.getOrgPPAGUID());
//                    preparedStatement.setString(8, organization.getRole());
//                    preparedStatement.setInt(9, organization.getGradId());
//                    preparedStatement.setString(10, organization.getDescription());
                    preparedStatement.executeUpdate();
                }
            }
        }
    }

    public static void setOrgToGrad(Organization organization, AnswerProcessing answerProcessing) throws SQLException {
        try (Connection connectionGrad = ConnectionBaseGRAD.instance().getConnection()){

            Parameters parameters = Parameters.parse(SQL_INSERT_REQUEST);

            PreparedStatement statement = connectionGrad.prepareStatement(parameters.getSQL());

            answerProcessing.sendOkMessageToClient(String.format("Сохранение организации %s", organization.getFullName()));

            parameters.put("full_name", organization.getFullName());
            parameters.put("short_name", organization.getShortName());
            parameters.put("ogrn", organization.getOgrn());
            parameters.put("inn", organization.getInn());
            parameters.put("kpp", organization.getKpp());
            parameters.put("org_root_entity_guid", organization.getOrgRootEntityGUID());
            parameters.put("org_ppa_guid", organization.getOrgPPAGUID());
            parameters.put("orgrole", organization.getRole());
            parameters.put("grad_id", organization.getGradId());
            parameters.put("description", organization.getDescription());
            parameters.apply(statement);
            statement.executeUpdate();
        }
    }
}
