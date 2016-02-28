package ru.prog_matik.java.pregis.connectiondb;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration._8_6_0.ImportResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_4.organizations_registry_common.ExportOrgRegistryResult;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Класс сохраняет в базе данных сведенья полученные об организациях и "SenderID".
 * Created by andryha on 16.02.2016.
 */
public class SaveToBaseOrganization {

    private Logger logger = Logger.getLogger(SaveToBaseOrganization.class);

    private Connection connection;

    /**
     * Метод получает заранее запрошенные данные об организации (exportOrgRegistry) и
     * о поставщиках данных (imortDataProvider), извлекает необходимые данные и сохраняет в базе данных.
     * Таблица "ORGANIZATION".
     * @param org - ExportOrgRegistryResult полученные сведенья об организациях.
     * @param imp - ImportResult полученные сведенья о поставщиках данных
     */
    public void setOrganization(ExportOrgRegistryResult org, ImportResult imp) {

        try {

            if (connection == null || connection.isClosed()) {
                connection = ConnectionDB.getConnectionDB();
            }

            CallableStatement cs = connection.prepareCall( "{CALL SET_ORGANIZATION(?, ?, ?, ?, ?, ?, ?)}" );

            cs.setString(1, org.getOrgData().get(0).getOrgVersion().getLegal().getFullName());
            cs.setString(2, org.getOrgData().get(0).getOrgVersion().getLegal().getShortName());
            cs.setString(3, org.getOrgData().get(0).getOrgVersion().getLegal().getOGRN());
            cs.setString(4, org.getOrgData().get(0).getOrgVersion().getLegal().getINN());
            cs.setString(5, org.getOrgData().get(0).getOrgVersion().getLegal().getKPP());
            cs.setString(6, imp.getCommonResult().get(0).getGUID()); // SenderID
            cs.setString(7, org.getOrgData().get(0).getOrgRootEntityGUID());

            int isResultSet = cs.executeUpdate();
            logger.info("setOrganization: CallableStatement.executeUpdate() - " + isResultSet);

            if (!cs.isClosed()) cs.close();
            if (connection != null || !connection.isClosed()) {
                connection.close();
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
