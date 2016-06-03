package ru.progmatik.java.pregis;

import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportDataProviderResult;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportOrgRegistryResult;
import ru.progmatik.java.pregis.connectiondb.organization.SaveToBaseOrganization;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.services.bills.ExportPaymentDocumentData;
import ru.progmatik.java.pregis.services.bills.ImportPaymentDocumentData;
import ru.progmatik.java.pregis.services.house.ExportCAChData;
import ru.progmatik.java.pregis.services.house.ExportStatusCAChData;
import ru.progmatik.java.pregis.services.house_management.ExportAccountData;
import ru.progmatik.java.pregis.services.house_management.ExportHouseData;
import ru.progmatik.java.pregis.services.nsi.UpdateReference;
import ru.progmatik.java.pregis.services.nsi.common.service.ExportNsiItem;
import ru.progmatik.java.pregis.services.nsi.common.service.ExportNsiList;
import ru.progmatik.java.pregis.services.nsi.common.service.NsiListGroupEnum;
import ru.progmatik.java.pregis.services.organizations.common.service.ExportDataProvider;
import ru.progmatik.java.pregis.services.organizations.common.service.ExportOrgRegistry;
import ru.progmatik.java.pregis.services.payment.ExportPaymentDocumentDetails;
import ru.progmatik.java.web.servlets.socket.ClientService;

import java.math.BigInteger;

/**
 * Класс будет обращаться ко всем объектам.
 * Created by andryha on 12.02.2016.
 */
public class ProgramAction {

    private boolean stateRun;

    private final ClientService clientService;
    private final AnswerProcessing answerProcessing = new AnswerProcessing();

    public ProgramAction(ClientService clientService) {
        this.clientService = clientService;
        this.clientService.setProgramAction(this);
    }

    /**
     * Получение "SenderID".
     * отправляем запрос exportOrgRegistry получаем данные организации,
     * отправляем запрос "exportDataProvider", получаем "SenderID" (находится в теге "DataProviderGUID").
     */
    public void getSenderID() {

        setStateRunOn(); // взводим флаг в состояния выполнения метода

        clientService.sendMessage("Запуск получения SenderID...");

        ExportOrgRegistry req = new ExportOrgRegistry(clientService, answerProcessing);
        ExportDataProvider dataProvider = new ExportDataProvider(clientService, answerProcessing);

        ExportOrgRegistryResult exportOrgRegistryResult = req.callExportOrgRegistry();

        if (exportOrgRegistryResult != null) {

            ExportDataProviderResult dataProviderResult = dataProvider.callExportDataProvide();

            if (dataProviderResult != null) {
                if (exportOrgRegistryResult.getErrorMessage() == null && dataProviderResult.getErrorMessage() == null) {
                    SaveToBaseOrganization saveToBaseOrganization = new SaveToBaseOrganization();
                    saveToBaseOrganization.setOrganization(exportOrgRegistryResult, dataProviderResult);
                    clientService.sendMessage("SenderID успешно получен!");
                }
            } else {
                clientService.sendMessage("Возникли ошибки, SenderID не получен!");
            }
        } else {
            clientService.sendMessage("Возникли ошибки, SenderID не получен!");
        }
        setStateRunOff(); // взводим флаг в состояние откл.
    }

//    public void callExportOrgRegistry() throws Exception {
//
//        ExportOrgRegistry reg = new ExportOrgRegistry(clientService, answerProcessing);
//        reg.callExportOrgRegistry();
//    }
//
//    public void callExportDataProvider() throws InvocationTargetException, NoSuchMethodException, MalformedURLException, IllegalAccessException {
//
//        ExportDataProvider dataProvider = new ExportDataProvider();
//        dataProvider.callExportDataProvide();
//    }

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
    public void callExportNsiItem(String codeNsiItem) {

        setStateRunOn();
        try {
            int code = Integer.valueOf(codeNsiItem);
        } catch (NumberFormatException ext) {
            if (codeNsiItem.isEmpty()) {
                clientService.sendMessage("Не указан код справочника: " +
                        "\nУкажите пожалуйста код справочника.\nЗапрос прерван!");
            } else {
                clientService.sendMessage("Неверный код справочника: " + codeNsiItem +
                        "\nКод справочника должен содержать число!\nЗапрос прерван!");
            }
            setStateRunOff();
            return;
        }
        ExportNsiItem nsiItem = new ExportNsiItem(clientService, answerProcessing);
        nsiItem.callExportNsiItem(NsiListGroupEnum.NSI, new BigInteger(codeNsiItem));
        setStateRunOff();
    }

    /**
     * Метод, экспортирует справочники №1, 51, 59 поставщика информации.
     */
    public void callExportDataProviderNsiItem() {
//        Подумать можно реализовать и для других справочников для сохранения в БД
//        написать новый метод в классе который будет принимать значение других справочников
        try {
            setStateRunOn();
            clientService.sendMessage("Запуск получения Справочников...");
            UpdateReference updateReference = new UpdateReference(clientService, answerProcessing);
            updateReference.updateAllDataProviderNsiItem();
            setStateRunOff();
//        ExportDataProviderNsiItem dataProviderNsiItem = new ExportDataProviderNsiItem(clientService, answerProcessing);
//        dataProviderNsiItem.callExportDataProviderNsiItem(51);
        } catch (Exception e) {
            clientService.sendMessage("Возникла непредвиденная ошибка!\nОперация прервана!\nТекст ошибки: " + e.getMessage());
            setStateRunOff();
        }
    }

    /**
     * Метод, получаем данные о МКД из ГИС ЖКХ.
     */
    public void callExportHouseData() {

        setStateRunOn(); // взводим флаг в состояния выполнения метода

        clientService.sendMessage("Запуск получения сведений о МКД...");
        ExportHouseData houseData = new ExportHouseData(clientService, answerProcessing);
        houseData.callExportHouseData("b58c5da4-8d62-438f-b11e-d28103220952");

        setStateRunOff(); // взводим флаг в состояние откл.

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

        setStateRunOn();
        clientService.sendMessage("Запуск получения ПД...");
        ExportPaymentDocumentData paymentDocumentData = new ExportPaymentDocumentData();
        paymentDocumentData.callExportPaymentDocumentData();
        clientService.sendMessage("Получения ПД завершено.");
        setStateRunOff();
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

        setStateRunOn();
        clientService.sendMessage("Запуск получения ЛС...");
        ExportAccountData accountData = new ExportAccountData();
        accountData.callExportAccountData();
        clientService.sendMessage("Получения ЛС завершено.");
        setStateRunOff();
    }


    public void callExportStatusCAChData() {
        ExportStatusCAChData statusCAChData = new ExportStatusCAChData();
        statusCAChData.callExportStatusCAChData();
    }

    public void callExportCAChData() {
        ExportCAChData caChData = new ExportCAChData();
        caChData.callExportCAChData();
    }

    /**
     * Метод, возвращает состояние выполнения какого либо метода.
     * Если статус "true", т.е. что то выполняется, значит мы не будем запускать новый метод,
     * а обратимся к получению лога операций.
     *
     * @return boolean состояние выполнения любого метода.
     */
    public boolean isRunning() {
        return stateRun;
    }

    /**
     * Метод, задаёт состояние выполнения какого либо метода.
     * К сожелению бывают такие ситуации, когда возникают ошибки, в этот самый момент
     * не удаётся установить флаг в положение откл.
     * Предоставил такую возможность исключительно для таких ситуаций.
     * Если возникла ошибка вызвать и поставить откл. (false).
     */
    public void setStateRunOff() {
        clientService.sendMessage("::setButtonState(false)");
        this.stateRun = false;
        clientService.dropDataList();
    }

    /**
     * Метод, задаёт состояние выполнения какого либо метода.
     * Устанавливает флаг в состояние выполняется (true).
     */
    private void setStateRunOn() {
        clientService.sendMessage("::setButtonState(true)");
        this.stateRun = true;
    }


}
