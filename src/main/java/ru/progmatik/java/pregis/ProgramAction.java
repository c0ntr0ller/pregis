package ru.progmatik.java.pregis;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportDataProviderResult;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportOrgRegistryResult;
import ru.progmatik.java.pregis.connectiondb.localdb.organization.SaveToBaseOrganization;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.services.bills.ExportPaymentDocumentData;
import ru.progmatik.java.pregis.services.bills.ImportPaymentDocumentData;
import ru.progmatik.java.pregis.services.house.ExportCAChData;
import ru.progmatik.java.pregis.services.house.ExportStatusCAChData;
import ru.progmatik.java.pregis.services.house_management.ExportAccountData;
import ru.progmatik.java.pregis.services.house_management.ExportHouseData;
import ru.progmatik.java.pregis.services.house_management.UpdateAllAccountData;
import ru.progmatik.java.pregis.services.nsi.UpdateReference;
import ru.progmatik.java.pregis.services.nsi.common.service.ExportNsiItem;
import ru.progmatik.java.pregis.services.nsi.common.service.ExportNsiList;
import ru.progmatik.java.pregis.services.nsi.common.service.NsiListGroupEnum;
import ru.progmatik.java.pregis.services.organizations.common.service.ExportDataProvider;
import ru.progmatik.java.pregis.services.organizations.common.service.ExportOrgRegistry;
import ru.progmatik.java.pregis.services.payment.ExportPaymentDocumentDetails;
import ru.progmatik.java.web.servlets.socket.ClientService;

import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;

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

            ExportOrgRegistryResult exportOrgRegistryResult = req.callExportOrgRegistry(req.getExportOrgRegistryRequest());

            if (exportOrgRegistryResult != null || exportOrgRegistryResult.getErrorMessage() != null) {

                ExportDataProviderResult dataProviderResult = dataProvider.callExportDataProvide();

                if (dataProviderResult != null || dataProviderResult.getErrorMessage() != null) {
                    if (exportOrgRegistryResult.getErrorMessage() == null && dataProviderResult.getErrorMessage() == null) {
                        SaveToBaseOrganization saveToBaseOrganization = new SaveToBaseOrganization();
                        saveToBaseOrganization.setOrganization(exportOrgRegistryResult, dataProviderResult);
                        answerProcessing.sendOkMessageToClient("SenderID успешно получен!");
                    }
                } else {
                    answerProcessing.sendMessageToClient("Возникли ошибки, SenderID не получен!");
                }
            } else {
                answerProcessing.sendMessageToClient("::setFailed()");
                answerProcessing.sendMessageToClient("Возникли ошибки, SenderID не получен!");
            }
        } catch (Exception e) {
            answerProcessing.sendErrorToClient("getSenderID: ", "\"Получения SenderID\" ", LOGGER, e);
//            answerProcessing.sendMessageToClient("Более подробно об ошибки: " + e.toString());
        }
        setStateRunOff(); // взводим флаг в состояние откл.
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
//            setStateRunOff();
//        ExportDataProviderNsiItem dataProviderNsiItem = new ExportDataProviderNsiItem(clientService, answerProcessing);
//        dataProviderNsiItem.callExportDataProviderNsiItem(51);
        } catch (Exception e) {
            answerProcessing.sendErrorToClient("callExportDataProviderNsiItem(): ", "\"Синхронизации справочников\" ", LOGGER, e);
        } finally {
            setStateRunOff();
        }

    }


    /**
     * Метод, получаем данные о МКД из ГИС ЖКХ.
     */
    public void callExportHouseData() {

        setStateRunOn(); // взводим флаг в состояния выполнения метода
        try {
            answerProcessing.sendMessageToClient("Запуск получения сведений о МКД...");
            ExportHouseData houseData = new ExportHouseData(answerProcessing);
            houseData.updateAllHouseData();
        } catch (Exception e) {
            answerProcessing.sendErrorToClient("updateAllHouseData(): ", "\"Получения данных о МКД\" ", LOGGER, e);
        } finally {
            setStateRunOff(); // взводим флаг в состояние откл.
        }
    }

    /**
     * Метод, синхронизирует данные о лицевых счетах ГИС ЖКХ и БД ГРАД.
     */
    public void updateAccountData() {
        setStateRunOn(); // взводим флаг в состояния выполнения метода

        try {
            answerProcessing.sendMessageToClient("Запуск...");
            answerProcessing.sendMessageToClient("Синхронизация лицевых счетов...");
            UpdateAllAccountData updateAllAccountData = new UpdateAllAccountData(answerProcessing);
            int state = updateAllAccountData.updateAllAccountData();
            if (state == -1) {
                answerProcessing.sendErrorToClientNotException("Возникла ошибка!\nОперация: \"Синхронизация лицевых счетов\" прервана!");
            } else if (state == 0) {
                answerProcessing.sendErrorToClientNotException("Операция: \"Синхронизация лицевых счетов\" завершилась с ошибками!");
            } else if (state == 1) {
                answerProcessing.sendOkMessageToClient("\"Синхронизация лицевых счетов\" успешно выполнена.");
            }
        } catch (SQLException | PreGISException | ParseException e) {
            answerProcessing.sendErrorToClient("updateAllHouseData(): ", "\"Синхронизация ЛС\" ", LOGGER, e);
        } finally {
            setStateRunOff(); // взводим флаг в состояние откл.
        }
//        Надо что бы возвращало -1 возникли ошибка опер. прервана, 0 - возникли ошибки  операция завершена смотрите лог,
//        1 - выполнено успешно.
    }

    /**
     * 2.1.2.1	Экспортировать список справочников (exportNsiList).
     * Позволяет получить перечень справочников с датой последнего изменения каждого справочника.
     * В зависимости от группы выгружаются либо общесистемные справочники (NSI)
     * либо справочники расширенного описания объектов жилищного фонда (NSIRAO). По
     * умолчанию выгружаются общесистемные справочники.
     */
    public void callExportNsiList() {
        setStateRunOn(); // взводим флаг в состояния выполнения метода
        try {
//            Если будет нужно, переделать как у всех что бы сыпал лог клиенту.
            ExportNsiList nsiList = new ExportNsiList();
            nsiList.callExportNsiList(NsiListGroupEnum.NSI);
        } catch (Exception e) {
            answerProcessing.sendErrorToClient("getNsiList: ", "\"Экспорта списков справочников\" ", LOGGER, e);
            answerProcessing.sendMessageToClient("::setFailed()");
            answerProcessing.sendMessageToClient("Возникли ошибки, список справочников не получен!");
//            answerProcessing.sendMessageToClient("Более подробно об ошибки: " + e.toString());
        } finally {
            setStateRunOff(); // взводим флаг в состояние откл.
        }
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
            answerProcessing.sendErrorToClient("callExportNsiItem(): ", "\"Экспорта данных справочника\" ", LOGGER, e);
        } finally {
            setStateRunOff();
        }

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
            answerProcessing.sendErrorToClient("callExportAccountData(): ", "", LOGGER, e);
        }
        setStateRunOff();
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
            answerProcessing.sendErrorToClient("callExportPaymentDocumentData(): ", "", LOGGER, e);
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

    public void checkSessions() {
        clientService.checkSession();;
    }
}
