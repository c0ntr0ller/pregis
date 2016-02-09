package ru.prog_matik.java.pregis.services.organizations;

import ru.gosuslugi.dom.schema.integration._8_5_0.ResultHeader;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.organizations_registry.ExportOrgRegistryResult;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.organizations_registry.ExportOrgRegistryResultType;

import javax.xml.ws.Holder;
import java.util.List;

public class AnswerHandlerExportOrgRegistry {

    public void getResult(ExportOrgRegistryResult result, Holder<ResultHeader> resultHolder) {

        if (result.getErrorMessage() != null) {
            List<ExportOrgRegistryResultType> exList = result.getOrgData();
            for (ExportOrgRegistryResultType resultType : exList) {

//                <ns5:OrgData>
                resultType.getOrgRootEntityGUID();

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
