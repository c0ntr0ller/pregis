package ru.progmatik.java.pregis;

import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportDataProviderResult;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportOrgRegistryResult;
import ru.progmatik.java.pregis.connectiondb.SaveToBaseOrganization;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.services.bills.ExportPaymentDocumentData;
import ru.progmatik.java.pregis.services.bills.ImportPaymentDocumentData;
import ru.progmatik.java.pregis.services.house.ExportCAChData;
import ru.progmatik.java.pregis.services.house.ExportStatusCAChData;
import ru.progmatik.java.pregis.services.house_management.ExportAccountData;
import ru.progmatik.java.pregis.services.nsi.common.service.ExportNsiItem;
import ru.progmatik.java.pregis.services.nsi.common.service.ExportNsiList;
import ru.progmatik.java.pregis.services.nsi.service.ExportDataProviderNsiItem;
import ru.progmatik.java.pregis.services.organizations.common.service.ExportDataProvider;
import ru.progmatik.java.pregis.services.organizations.common.service.ExportOrgRegistry;
import ru.progmatik.java.pregis.services.payment.ExportPaymentDocumentDetails;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

/**
 * Класс будет обращаться ко всем объектам.
 * Created by andryha on 12.02.2016.
 */
public class ProgramAction {

//    Надо смотреть в следующих версиях вроде упразднили.
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

    /**
     * Метод, экспортирует справочники №1, 51, 59 поставщика информации.
     */
    public void callExportDataProviderNsiItem() {
        ExportDataProviderNsiItem dataProviderNsiItem = new ExportDataProviderNsiItem();
        dataProviderNsiItem.callExportDataProviderNsiItem();
    }

    /**
     * Метод, импорт сведений о платежных документах.
     */
    public void callImportPaymentDocumentData() {
        ImportPaymentDocumentData importPaymentDocumentData = new ImportPaymentDocumentData();
        importPaymentDocumentData.callImportPaymentDocumentData();
    }

    /**
     * Метод, экспорт сведений о платежных документах.
     */
    public void callExportPaymentDocumentData() {
        ExportPaymentDocumentData paymentDocumentData = new ExportPaymentDocumentData();
        paymentDocumentData.callExportPaymentDocumentData();
    }

    /**
     * Метод асинхронный, экспорт реквизитов платежных документов для банков.
     */
    public void callExportPaymentDocumentDetails() {

        ExportPaymentDocumentDetails details = new ExportPaymentDocumentDetails();
        details.callExportPaymentDocumentDetails();
    }

    /**
     * Метод асинхронный, получить ответ, экспорт реквизитов платежных документов для банков.
     */
    public void getStateExportPaymentDocumentDetails() {
        ExportPaymentDocumentDetails details = new ExportPaymentDocumentDetails();
        details.getStateExportPaymentDocumentDetails();
    }


    /**
     * Метод, экспорт сведений о лицевых счетах.
     */
    public void callExportAccountData() {
        ExportAccountData accountData =  new ExportAccountData();
        accountData.callExportAccountData();
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
