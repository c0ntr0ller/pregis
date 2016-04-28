package ru.prog_matik.java.pregis;

import ru.gosuslugi.dom.schema.integration._8_7_0_6.organizations_registry_common.ExportDataProviderResult;
import ru.gosuslugi.dom.schema.integration._8_7_0_6.organizations_registry_common.ExportOrgRegistryResult;
import ru.prog_matik.java.pregis.connectiondb.SaveToBaseOrganization;
import ru.prog_matik.java.pregis.exception.PreGISException;
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

    /**
     * Получение "SenderID".
     * отправляем запрос exportOrgRegistry получаем данные организации,
     * отправляем запрос "exportDataProvider", получаем "SenderID" (находится в теге "DataProviderGUID").
     */
    public void getSenderID() throws Exception {

        ExportOrgRegistry req = new ExportOrgRegistry();
        ExportOrgRegistryResult exportOrgRegistryResult = req.callExportOrgRegistry();
        if (exportOrgRegistryResult == null) {
            throw new PreGISException("ExportOrgRegistryResult: Не вернулся ответ от срвиса ГИС ЖКХ!");
        }
        ExportDataProvider dataProvider = new ExportDataProvider();
        ExportDataProviderResult dataProviderResult = dataProvider.callExportDataProvide();
        if (dataProviderResult == null) {
            throw new PreGISException("ExportDataProviderResult: Не вернулся ответ от срвиса ГИС ЖКХ!");
        }

        SaveToBaseOrganization saveToBaseOrganization = new SaveToBaseOrganization();
        saveToBaseOrganization.setOrganization(exportOrgRegistryResult, dataProviderResult);

    }

    public void callExportOrgRegistry() throws Exception {

        ExportOrgRegistry reg = new ExportOrgRegistry();
        reg.callExportOrgRegistry();
    }


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
