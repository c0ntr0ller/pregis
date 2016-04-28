package ru.prog_matik.java.pregis;

import ru.gosuslugi.dom.schema.integration._8_7_0.ImportResult;
import ru.gosuslugi.dom.schema.integration._8_7_0_6.organizations_registry_common.ExportOrgRegistryResult;
import ru.prog_matik.java.pregis.services.house.ExportCAChData;
import ru.prog_matik.java.pregis.services.house.ExportStatusCAChData;
import ru.prog_matik.java.pregis.services.nsi.ExportNsiList;
import ru.prog_matik.java.pregis.services.organizations.common.service.ExportDataProvider;
import ru.prog_matik.java.pregis.services.organizations.common.service.ExportOrgRegistry;

/**
 * Класс будет обращаться ко всем объектам.
 * Created by andryha on 12.02.2016.
 */
public class ProgramAction {

    private String orgRootEntityGUID;

    private ExportOrgRegistryResult exportOrgRegistryResult;
    private ImportResult importResult;

    /**
     * Получение "SenderID".
     * отправляем запрос exportOrgRegistry получаем данные организации, извлекаем необходимое,
     * отправляем запрос "importDataProvider - allocateSenderID", получаем "SenderID".
     */
    public void callExportOrgRegistry() {

        ExportOrgRegistry reg = new ExportOrgRegistry();
//        ImportDataProvider pro = new ImportDataProvider();
        try {

            exportOrgRegistryResult = reg.callExportOrgRegistry();
            if (exportOrgRegistryResult == null) return;

//            importResult = pro.allocateSenderID(exportOrgRegistryResult.getOrgData().get(0).getOrgRootEntityGUID());
//            if (importResult == null) return;

//            SaveToBaseOrganization saveToBaseOrganization = new SaveToBaseOrganization();
//            saveToBaseOrganization.setOrganization(exportOrgRegistryResult, importResult);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void removeSenderID() {
//        ImportDataProvider provider = new ImportDataProvider();
//        provider.removeSenderID(BaseOrganization.getSenderID());
//    }


    public void callExportDataProvider() {

        ExportDataProvider dataProvider = new ExportDataProvider();
        dataProvider.callExportDataProvide();

    }

    public void callExportNsiList() {
        ExportNsiList nsiList = new ExportNsiList();
        nsiList.callExportNsiList();
    }

    public void callExportStatusCAChData() {
        ExportStatusCAChData statusCAChData = new ExportStatusCAChData();
        statusCAChData.callExportStatusCAChData();
    }

    public void callExportCAChData() {
        ExportCAChData caChData = new ExportCAChData();
        caChData.callExportCAChData();
    }

}
