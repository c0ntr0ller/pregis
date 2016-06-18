package ru.progmatik.java.pregis;

import org.apache.log4j.Logger;
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

    private static final Logger LOGGER = Logger.getLogger(ProgramAction.class);
    private final ClientService clientService;
    private final AnswerProcessing answerProcessing;
    private boolean stateRun;



    public ProgramAction(ClientService clientService) {
        this.clientService = clientService;
        this.answerProcessing = new AnswerProcessing(this.clientService);
        this.clientService.setProgramAction(this);
    }

    /**
     * Получение "SenderID".
     * отправляем запрос exportOrgRegistry получаем данные организации,
     * отправляем запрос "exportDataProvider", получаем "SenderID" (находится в теге "DataProviderGUID").
     */
    public void getSenderID() {

        setStateRunOn(); // взводим флаг в состояния выполнения метода

        answerProcessing.sendMessageToClient("Запуск получения SenderID...");

        try {
            ExportOrgRegistry req = new ExportOrgRegistry(answerProcessing);
            ExportDataProvider dataProvider = new ExportDataProvider(answerProcessing);

            ExportOrgRegistryResult exportOrgRegistryResult = req.callExportOrgRegistry();

            if (exportOrgRegistryResult != null) {

                ExportDataProviderResult dataProviderResult = dataProvider.callExportDataProvide();

                if (dataProviderResult != null) {
                    if (exportOrgRegistryResult.getErrorMessage() == null && dataProviderResult.getErrorMessage() == null) {
                        SaveToBaseOrganization saveToBaseOrganization = new SaveToBaseOrganization();
                        saveToBaseOrganization.setOrganization(exportOrgRegistryResult, dataProviderResult);
                        answerProcessing.sendMessageToClient("SenderID успешно получен!");
                    }
                } else {
                    answerProcessing.sendMessageToClient("Возникли ошибки, SenderID не получен!");
                }
            } else {
                answerProcessing.sendMessageToClient("::setFailed()");
                answerProcessing.sendMessageToClient("Возникли ошибки, SenderID не получен!");
            }
        } catch (Exception e) {
            answerProcessing.sendErrorToClient("getSenderID: ", LOGGER, e);
//            answerProcessing.sendMessageToClient("Более подробно об ошибки: " + e.toString());
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
                answerProcessing.sendMessageToClient("Не указан код справочника: " +
                        "\nУкажите пожалуйста код справочника.\nЗапрос прерван!");
            } else {
                answerProcessing.sendMessageToClient("Неверный код справочника: " + codeNsiItem +
                        "\nКод справочника должен содержать число!\nЗапрос прерван!");
            }
            setStateRunOff();
            return;
        }
        try {
            ExportNsiItem nsiItem = new ExportNsiItem(answerProcessing);
            nsiItem.callExportNsiItem(NsiListGroupEnum.NSI, new BigInteger(codeNsiItem));
        } catch (Exception e) {
            answerProcessing.sendErrorToClient("callExportNsiItem(): ", LOGGER, e);
        }
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
            answerProcessing.sendMessageToClient("Запуск получения Справочников...");
            UpdateReference updateReference = new UpdateReference(answerProcessing);
            updateReference.updateAllDataProviderNsiItem();
            setStateRunOff();
//        ExportDataProviderNsiItem dataProviderNsiItem = new ExportDataProviderNsiItem(clientService, answerProcessing);
//        dataProviderNsiItem.callExportDataProviderNsiItem(51);
        } catch (Exception e) {
            answerProcessing.sendErrorToClient("callExportDataProviderNsiItem(): ", LOGGER, e);
        }
        setStateRunOff();
    }

    /**
     * Метод, получаем данные о МКД из ГИС ЖКХ.
     */
    public void callExportHouseData() {

        setStateRunOn(); // взводим флаг в состояния выполнения метода
        try {
            answerProcessing.sendMessageToClient("Запуск получения сведений о МКД...");
            ExportHouseData houseData = new ExportHouseData(answerProcessing);
            houseData.callExportHouseData();
        } catch (Exception e) {
            answerProcessing.sendErrorToClient("callExportHouseData(): ", LOGGER, e);
        }


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
        try {
            answerProcessing.sendMessageToClient("Запуск получения ПД...");
            ExportPaymentDocumentData paymentDocumentData = new ExportPaymentDocumentData();
            paymentDocumentData.callExportPaymentDocumentData();
            answerProcessing.sendMessageToClient("Получения ПД завершено.");
        } catch (Exception e) {
            answerProcessing.sendErrorToClient("callExportPaymentDocumentData(): ", LOGGER, e);
        }
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
        try {
            answerProcessing.sendMessageToClient("Запуск получения ЛС...");
            ExportAccountData accountData = new ExportAccountData(answerProcessing);
            accountData.callExportAccountData("f20a2f00-c9cf-485f-ac41-92af5b77e29a");
            answerProcessing.sendMessageToClient("Получения ЛС завершено.");
        } catch (Exception e) {
            answerProcessing.sendErrorToClient("callExportAccountData(): ", LOGGER, e);
        }
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
