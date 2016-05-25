package ru.progmatik.java.pregis;

import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportDataProviderResult;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportOrgRegistryResult;
import ru.progmatik.java.pregis.connectiondb.SaveToBaseOrganization;
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
import ru.progmatik.java.web.servlets.socket.ClientService;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Класс будет обращаться ко всем объектам.
 * Created by andryha on 12.02.2016.
 */
public class ProgramAction {

    private boolean stateRun;
//    private List<String> listState = new ArrayList<>();

    private final ClientService clientService;

    public ProgramAction(ClientService clientService) {
        this.clientService = clientService;
        this.clientService.setProgramAction(this);
    }

    //    Надо смотреть в следующих версиях вроде упразднили.
    /**
     * Получение "SenderID".
     * отправляем запрос exportOrgRegistry получаем данные организации,
     * отправляем запрос "exportDataProvider", получаем "SenderID" (находится в теге "DataProviderGUID").
     */
    public void getSenderID() {

        setStateRunOn(); // взводим флаг в состояния выполнения метода

//        listState.clear(); // очищаем предыдущий лог.
//        listState.add("Запуск получения SenderID...");
        clientService.addMessage("Запуск получения SenderID...");
        ExportOrgRegistry req = new ExportOrgRegistry(clientService);
//        ExportOrgRegistry req = new ExportOrgRegistry(listState);
        ExportOrgRegistryResult exportOrgRegistryResult = req.callExportOrgRegistry();
        if (exportOrgRegistryResult == null) {
            clientService.addMessage("ExportOrgRegistryResult: Не вернулся ответ от срвиса ГИС ЖКХ!");
//            listState.add("ExportOrgRegistryResult: Не вернулся ответ от срвиса ГИС ЖКХ!");
//            stateRun = false;
//            throw new PreGISException("ExportOrgRegistryResult: Не вернулся ответ от срвиса ГИС ЖКХ!");
            setStateRunOff(); // взводим флаг в состояние откл.
            return;
        }

        ExportDataProvider dataProvider = new ExportDataProvider(clientService);
        ExportDataProviderResult dataProviderResult = dataProvider.callExportDataProvide();
        if (dataProviderResult == null) {
            clientService.addMessage("ExportDataProviderResult: Не вернулся ответ от срвиса ГИС ЖКХ!");
//            listState.add("ExportOrgRegistryResult: Не вернулся ответ от срвиса ГИС ЖКХ!");
//            stateRun = false;
//            throw new PreGISException("ExportDataProviderResult: Не вернулся ответ от срвиса ГИС ЖКХ!");
            setStateRunOff(); // взводим флаг в состояние откл.
            return;
        }

        if (exportOrgRegistryResult.getErrorMessage() == null && dataProviderResult.getErrorMessage() == null) {
            SaveToBaseOrganization saveToBaseOrganization = new SaveToBaseOrganization();
            saveToBaseOrganization.setOrganization(exportOrgRegistryResult, dataProviderResult);
            clientService.addMessage("SenderID успешно получен!");
//        listState.add("SenderID успешно получен!");
        } else {
            clientService.addMessage("Возникли ошибки, SenderID не получен!");
        }
        setStateRunOff(); // взводим флаг в состояние откл.
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
     * Метод, получаем данные о МКД из ГИС ЖКХ.
     */
    public void callExportHouseData() {
        setStateRunOn();
        int timePause = 5000;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                System.err.println("ImportHouseUORequest");
                setStateRunOff();
            }
        }, timePause);


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

    /**
     * Метод, возвращает состояние выполнения какого либо метода.
     * Если статус "true", т.е. что то выполняется, значит мы не будем запускать новый метод,
     * а обратимся к получению лога операций.
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
