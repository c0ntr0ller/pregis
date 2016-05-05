package ru.prog_matik.java.pregis;

import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportDataProviderResult;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportOrgRegistryResult;
import ru.prog_matik.java.pregis.connectiondb.SaveToBaseOrganization;
import ru.prog_matik.java.pregis.exception.PreGISException;
import ru.prog_matik.java.pregis.services.house.ExportCAChData;
import ru.prog_matik.java.pregis.services.house.ExportStatusCAChData;
import ru.prog_matik.java.pregis.services.nsi.common.service.ExportNsiItem;
import ru.prog_matik.java.pregis.services.nsi.common.service.ExportNsiList;
import ru.prog_matik.java.pregis.services.organizations.common.service.ExportDataProvider;
import ru.prog_matik.java.pregis.services.organizations.common.service.ExportOrgRegistry;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

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

    public void callExportDataProvider() throws InvocationTargetException, NoSuchMethodException, MalformedURLException, IllegalAccessException {

        ExportDataProvider dataProvider = new ExportDataProvider();
        dataProvider.callExportDataProvide();
    }

    /**
     * 2.1.2.1	Экспортировать список справочников (exportNsiList).
     * Позволяет получить перечень справочников с датой последнего изменения каждого справочника.
     * В зависимости от группы выгружаются либо общесистемные справочники (NSI)
     * либо справочники расширенного описания объектов жилищного фонда (NSIRAO). По
     * умолчанию выгружаются общесистемные справочники.
     */
    public void callExportNsiList() {
        ExportNsiList nsiList = new ExportNsiList();
        nsiList.callExportNsiList();
    }

    /**
     * 2.1.2.2	Экспортировать данные справочника (exportNsiItem).
     * Позволяет получить записи конкретного справочника, а также только те записи справочника,
     * которые изменились после временной метки ModifiedAfter.
     */
    public void callExportNsiItem() {
        ExportNsiItem nsiItem = new ExportNsiItem();
        nsiItem.callExportNsiItem();
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
