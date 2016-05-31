package ru.progmatik.java.pregis.services.organizations.common.service;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportOrgRegistryResult;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportOrgRegistryResultType;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;

import javax.xml.ws.Holder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AnswerHandlerExportOrgRegistry {

    private Logger logger = Logger.getLogger(AnswerHandlerExportOrgRegistry.class);

    /**
     * Пока не работает, подерживает только сериализацию объектов что очень не удобно.
     * @param result
     */
    void setOrgRegistryResultToBase(ExportOrgRegistryResult result) {

        if (result.getErrorMessage() == null) {

            Connection connection;

            try {

                Map mapOrg = getOrgData(result);
                connection = ConnectionDB.instance().getConnectionDB();

                CallableStatement cs = connection.prepareCall("{CALL SET_EXPORT_ORG_REGISTRY_RESULT(?, ?, ?, ?, ?, ?)}");

                cs.setObject(1, result);
                cs.setString(2, mapOrg.get("getFullName").toString());
                cs.setString(3, mapOrg.get("getShortName").toString());
                cs.setString(4, mapOrg.get("getOGRN").toString());
                cs.setString(5, mapOrg.get("getINN").toString());
                cs.setString(6, mapOrg.get("getKPP").toString());

                int isResultSet = cs.executeUpdate();
                logger.info("setMessageToBase: CallableStatement.executeUpdate() - " + isResultSet);

                if (!cs.isClosed()) cs.close();
                if (!connection.isClosed()) connection.close();
                ConnectionDB.close();

            } catch (SQLException e) {
                logger.fatal("Ошибка при коннекте или ещё с чем, в общем при работе с БД!", e);
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Map getOrgData(ExportOrgRegistryResult result) {

        Map<String, String> mapOrg = new HashMap<>();

        List<ExportOrgRegistryResultType> exList = result.getOrgData();

        for (ExportOrgRegistryResultType resultType : exList) {

//                <ns5:Legal>
            if (resultType.getOrgVersion().getSubsidiary() != null) {
//                Если Обособленное подразделение
                resultType.getOrgVersion().getSubsidiary().getFullName();
                resultType.getOrgVersion().getSubsidiary().getShortName();
                resultType.getOrgVersion().getSubsidiary().getOGRN();
                resultType.getOrgVersion().getSubsidiary().getINN();
                resultType.getOrgVersion().getSubsidiary().getKPP();
                resultType.getOrgVersion().getSubsidiary().getOKOPF();
                resultType.getOrgVersion().getSubsidiary().getAddress();
                resultType.getOrgVersion().getSubsidiary().getFIASHouseGuid();

                resultType.getOrgVersion().getSubsidiary().getActivityEndDate();
                resultType.getOrgVersion().getSubsidiary().getSourceName();
//                resultType.getOrgVersion().getSubsidiary().getVersionNumber();
                resultType.getOrgVersion().getSubsidiary().getStatusVersion();
                resultType.getOrgVersion().getSubsidiary().getParentOrg();
                resultType.getOrgVersion().getSubsidiary().getParentOrg().getRegOrgVersion();

            } else if (resultType.getOrgVersion().getEntrp() != null) {

//                Если Индивидуальный предприниматель
                resultType.getOrgVersion().getEntrp().getFirstName();
                resultType.getOrgVersion().getEntrp().getSurname();
                resultType.getOrgVersion().getEntrp().getINN();
                resultType.getOrgVersion().getEntrp().getOGRNIP();
                resultType.getOrgVersion().getEntrp().getPatronymic();
                resultType.getOrgVersion().getEntrp().getSex();
                resultType.getOrgVersion().getEntrp().getStateRegistrationDate();

            } else if (resultType.getOrgVersion().getLegal() != null) {

//                Если Юридическое лицо
                mapOrg.put("getFullName", resultType.getOrgVersion().getLegal().getFullName());
                mapOrg.put("getShortName", resultType.getOrgVersion().getLegal().getShortName());
                mapOrg.put("getOGRN", resultType.getOrgVersion().getLegal().getOGRN());
                mapOrg.put("getINN", resultType.getOrgVersion().getLegal().getINN());
                mapOrg.put("getKPP", resultType.getOrgVersion().getLegal().getKPP());
                mapOrg.put("getOKOPF", resultType.getOrgVersion().getLegal().getOKOPF());
                mapOrg.put("getAddress", resultType.getOrgVersion().getLegal().getAddress());
                mapOrg.put("getFIASHouseGuid", resultType.getOrgVersion().getLegal().getFIASHouseGuid());

                logger.info(resultType.getOrgVersion().getLegal().getActivityEndDate());
                logger.info(resultType.getOrgVersion().getLegal().getCommercialName());
                logger.info(resultType.getOrgVersion().getLegal().getStateRegistrationDate());

            }
        }
        return mapOrg;
    }


    void getResult(ExportOrgRegistryResult result, Holder<ResultHeader> resultHolder) {

        if (result.getErrorMessage() == null) {
            List<ExportOrgRegistryResultType> exList = result.getOrgData();
            for (ExportOrgRegistryResultType resultType : exList) {

//                <ns5:OrgData>
                System.out.println(resultType.getOrgRootEntityGUID());

//              <ns5:OrgVersion>
                resultType.getOrgVersion().getOrgVersionGUID();
                resultType.getOrgVersion().getLastEditingDate();
                resultType.getOrgVersion().isIsActual();

//                <ns5:Legal>
                resultType.getOrgVersion().getLegal().getShortName();
                resultType.getOrgVersion().getLegal().getFullName();
                resultType.getOrgVersion().getLegal().getOGRN();
                resultType.getOrgVersion().getLegal().getStateRegistrationDate();
                resultType.getOrgVersion().getLegal().getINN();
                resultType.getOrgVersion().getLegal().getKPP();
                resultType.getOrgVersion().getLegal().getOKOPF();
                resultType.getOrgVersion().getLegal().getAddress();
                resultType.getOrgVersion().getLegal().getFIASHouseGuid();

                resultType.getOrgVersion().getRegistryOrganizationStatus();

//                <ns5:organizationRoles>
                for (int i = 0; i < resultType.getOrganizationRoles().size(); i++) {
                    resultType.getOrganizationRoles().get(i).getCode();
                    resultType.getOrganizationRoles().get(i).getGUID();
                    resultType.getOrganizationRoles().get(i).getName();
                }
            }

        } else {
            System.out.println();
            System.err.println(result.getErrorMessage().getErrorCode());
            System.err.println(result.getErrorMessage().getDescription());
        }

//        <ns4:Date>
        System.out.println("getResultHeader Date: " + getResultHeader(resultHolder).getDate());
//        <ns4:MessageGUID>
        System.out.println("getResultHeader MessageGUID: " + getResultHeader(resultHolder).getMessageGUID());

    }

    private ResultHeader getResultHeader(Holder<ResultHeader> resultHolder) {
        return resultHolder.value;
    }
}
