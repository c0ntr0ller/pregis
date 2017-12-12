package ru.progmatik.java.pregis;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.house_management.GetStateResult;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common.ExportOrgRegistryResult;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.connectiondb.grad.devices.MeteringDeviceGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseRecord;
import ru.progmatik.java.pregis.connectiondb.localdb.organization.OrganizationDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.organization.OrganizationDataSet;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.ResourcesUtil;
import ru.progmatik.java.pregis.services.bills.BillsHandler;
import ru.progmatik.java.pregis.services.device_metering.UpdateMeteringDeviceValues;
import ru.progmatik.java.pregis.services.house_management.*;
import ru.progmatik.java.pregis.services.nsi.UpdateReference;
import ru.progmatik.java.pregis.services.nsi.common.service.ExportNsiItem;
import ru.progmatik.java.pregis.services.nsi.common.service.ExportNsiList;
import ru.progmatik.java.pregis.services.nsi.common.service.NsiListGroupEnum;
import ru.progmatik.java.pregis.services.organizations.ExportOrgRegistry;
import ru.progmatik.java.web.servlets.socket.ClientService;
import ru.progmatik.java.web.servlets.socket.ValueJSON;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Класс будет обращаться ко всем объектам.
 * Created by andryha on 12.02.2016.
 */
public final class ProgramAction {

    private static final Logger LOGGER = Logger.getLogger(ProgramAction.class);
    private final ClientService clientService;
    private final AnswerProcessing answerProcessing;
    private boolean stateRun;


    public ProgramAction(final ClientService clientService) {
        this.clientService = clientService;
        this.answerProcessing = new AnswerProcessing(this.clientService);
        this.clientService.setProgramAction(this);
        ConnectionDB.instance().setShutdownDefragToLocalBase();
    }

    /**
     * Метод, получение orgPPAGUID - идентификатор зарегистрированной организации.
     */
    public void getOrgPPAGUID() {
        setStateRunOn(); // взводим флаг в состояния выполнения метода

        answerProcessing.sendMessageToClient("Получение идентификатора зарегистрированной организации...");

        try {
            // answerProcessing.sendMessageToClient("!------ 1");
            final ExportOrgRegistry req = new ExportOrgRegistry(answerProcessing);

            final ExportOrgRegistryResult exportOrgRegistryResult = req.callExportOrgRegistry(req.getExportOrgRegistryRequest());


            if (exportOrgRegistryResult != null && exportOrgRegistryResult.getErrorMessage() == null) {


                final OrganizationDataSet dataSet = new OrganizationDataSet(
                        exportOrgRegistryResult.getOrgData().get(0).getOrgVersion().getLegal().getFullName(),
                        exportOrgRegistryResult.getOrgData().get(0).getOrgVersion().getLegal().getShortName(),
                        exportOrgRegistryResult.getOrgData().get(0).getOrgVersion().getLegal().getOGRN(),
                        exportOrgRegistryResult.getOrgData().get(0).getOrgVersion().getLegal().getINN(),
                        exportOrgRegistryResult.getOrgData().get(0).getOrgVersion().getLegal().getKPP(),
                        exportOrgRegistryResult.getOrgData().get(0).getOrgRootEntityGUID(),
                        exportOrgRegistryResult.getOrgData().get(0).getOrgPPAGUID(),
                        ResourcesUtil.instance().getCompanyRole(), // Роль УО
                        ResourcesUtil.instance().getCompanyGradId(), // Идентификатор в БД ГРАД
                        exportOrgRegistryResult.getOrgData().get(0).getOrgVersion().getOrgVersionGUID()); // Примечание

                final OrganizationDAO organizationDAO = new OrganizationDAO();
                organizationDAO.addOrganization(dataSet);

                answerProcessing.sendOkMessageToClient("");
                answerProcessing.sendOkMessageToClient("Идентификатор зарегистрированной организации успешно получен!");

            } else {
                answerProcessing.sendErrorToClientNotException("Возникли ошибки, идентификатор зарегистрированной организации не получен!");
            }
        } catch (Exception e) {
            answerProcessing.sendErrorToClient("getOrgPPAGUID(): ", "\"Получение идентификатора зарегистрированной организации\" ", LOGGER, e);
//            answerProcessing.sendMessageToClient("Более подробно об ошибки: " + e.toString());
        } finally {
            setStateRunOff(); // взводим флаг в состояние откл.
        }

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
            answerProcessing.sendMessageToClient("");
            final UpdateReference updateReference = new UpdateReference(answerProcessing);
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
    public void callExportHouseData(final String houseGradID) {

        setStateRunOn(); // взводим флаг в состояния выполнения метода
        try {
            answerProcessing.sendMessageToClient("Запуск получения сведений о МКД...");
            final UpdateAllHouseData houseData = new UpdateAllHouseData(answerProcessing);
            houseData.callUpdateAllHouseData(checkHouseGradId(houseGradID));
        }catch (Exception e) {
            answerProcessing.sendErrorToClient("callUpdateAllHouseData(): ", "\"Получения данных о МКД\" ", LOGGER, e);
        } finally {
            setStateRunOff(); // взводим флаг в состояние откл.
        }
    }

    /**
     * Метод, синхронизирует данные о лицевых счетах ГИС ЖКХ и БД ГРАД.
     */
    public void updateAccountData(final String houseGradID) {
        setStateRunOn(); // взводим флаг в состояния выполнения метода

        try {
            answerProcessing.sendMessageToClient("Запуск...");
            answerProcessing.sendMessageToClient("Синхронизация лицевых счетов...");

            final UpdateAllAccountData updateAllAccountData = new UpdateAllAccountData(answerProcessing);
            final int state = updateAllAccountData.callUpdateAllAccountData(checkHouseGradId(houseGradID));

            answerProcessing.clearLabelForText();
            if (state == -1) {
                answerProcessing.sendErrorToClientNotException("");
                answerProcessing.sendErrorToClientNotException("Возникла ошибка! Операция: \"Синхронизация лицевых счетов\" прервана!");
            } else if (state == 0) {
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendErrorToClientNotException("Операция: \"Синхронизация лицевых счетов\" завершилась с ошибками!");
            } else if (state == 1) {
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendOkMessageToClient("\"Синхронизация лицевых счетов\" успешно выполнена.");
            }
        } catch (Exception e) {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendErrorToClient("callUpdateAllHouseData(): ", "\"Синхронизация ЛС\" ", LOGGER, e);
        } finally {
            setStateRunOff(); // взводим флаг в состояние откл.
        }
//        Надо что бы возвращало -1 возникли ошибка опер. прервана, 0 - возникли ошибки  операция завершена смотрите лог,
//        1 - выполнено успешно.
    }

    /**
     * Метод, синхронизирует ПУ.
     */
    public void updateMeteringDevices(final String houseGradID) {
        setStateRunOn(); // взводим флаг в состояния выполнения метода
        try {
            final UpdateAllMeteringDeviceData updateAllMeteringDeviceData = new UpdateAllMeteringDeviceData(answerProcessing);
            final int state = updateAllMeteringDeviceData.updateMeteringDeviceData(checkHouseGradId(houseGradID));
            if (state == -1) {
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendErrorToClientNotException("Возникла ошибка!\nОперация: \"Синхронизация ПУ\" прервана!");
            } else if (state == 0) {
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendErrorToClientNotException("Операция: \"Синхронизация ПУ\" завершилась с ошибками!");
            } else if (state == 1) {
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendOkMessageToClient("\"Синхронизация ПУ\" успешно выполнена.");
            }
        } catch (Exception e) {
            answerProcessing.sendErrorToClient("Синхронизация ПУ: ", "\"Синхронизация ПУ\" ", LOGGER, e);
        } finally {
            setStateRunOff(); // взводим флаг в состояние откл.
        }
    }

    /**
     * Метод, архивирует (удалит) все приборы учёта
     */
    public void deleteAllMeteringDevicesInHouse(final String houseFias) {

        setStateRunOn(); // взводим флаг в состояния выполнения метода

        answerProcessing.sendMessageToClient("Запуск архивации ПУ. ФИАС " + houseFias);

        final UpdateAllMeteringDeviceData updateAllMeteringDeviceData = new UpdateAllMeteringDeviceData(answerProcessing);
        try {
            updateAllMeteringDeviceData.archiveAllDevices(houseFias);
            final int state = updateAllMeteringDeviceData.getErrorState();
            if (state == -1) {
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendErrorToClientNotException("Возникла ошибка!\nОперация: \"Архивация ПУ\" прервана!");
            } else if (state == 0) {
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendErrorToClientNotException("Операция: \"Архивация ПУ\" завершилась с ошибками!");
            } else if (state == 1) {
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendOkMessageToClient("\"Архивация ПУ\" успешно выполнена.");
            }
        } catch (Exception e) {
            answerProcessing.sendErrorToClient("Архивация ПУ: ", "\"Архивация ПУ\" ", LOGGER, e);
        } finally {
            setStateRunOff(); // взводим флаг в состояние откл.
        }
    }

    /**
     * Метод, получает массив, разделенный запятой с ид ПУ В БД ГРАД, добавляет ПУ в архив с указанной причиной "Ошибка".
     * @param lineMeterId ид ПУ в БД ГРАД, разделенные запятой.
     */
    public void setMeteringDevicesToArchive(final Integer houseId, final String lineMeterId) {
        setStateRunOn(); // взводим флаг в состояния выполнения метода

        try {
            answerProcessing.sendMessageToClient("Архивирование ПУ...");
            final ImportMeteringDeviceData importMeteringDeviceData = new ImportMeteringDeviceData(answerProcessing);
            final MeteringDeviceGRADDAO graddao = new MeteringDeviceGRADDAO(answerProcessing, houseId);
            
        } catch (SQLException | ParseException | PreGISException e) {
            answerProcessing.sendErrorToClient("Архивирование ПУ: ", "\"Архивирование ПУ\"", LOGGER, e);
        } finally {
            setStateRunOff(); // взводим флаг в состояние откл.
        }
    }

    /**
     * Метод, по коду дома по ФИАС получает показания ПУ.
     */
    public void getExportMeteringDeviceHistory(final String houseGradID) {
        setStateRunOn(); // взводим флаг в состояния выполнения метода
        final UpdateMeteringDeviceValues deviceValues;
        try {
            deviceValues = new UpdateMeteringDeviceValues(answerProcessing);
            final int state = deviceValues.updateAllMeteringDeviceValues(checkHouseGradId(houseGradID));
            if (state == -1) {
                answerProcessing.sendErrorToClientNotException("Возникла ошибка!\nОперация: \"Синхронизация показаний ПУ\" прервана!");
            } else if (state == 0) {
                answerProcessing.sendErrorToClientNotException("Возникла ошибка!\nОперация: \"Синхронизация показаний ПУ\" завершилась с ошибками!");
            } else {
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendMessageToClient("Итого обновлено показаний ПУ:");
                answerProcessing.sendMessageToClient("Град: " + deviceValues.getAddedValueToGrad());
                answerProcessing.sendMessageToClient("ГИС ЖКХ: " + deviceValues.getAddedValueToGISJKH());
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendOkMessageToClient("\"Получение показаний ПУ\" успешно выполнено.");
            }
        } catch (Exception e) {
            answerProcessing.sendErrorToClient("getExportMeteringDeviceHistory(): ", "", LOGGER, e);
        } finally {
            setStateRunOff(); // взводим флаг в состояние откл.
        }
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
            final ExportNsiList nsiList = new ExportNsiList();
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
    public void callExportNsiItem(final String codeNsiItem) {

        setStateRunOn();
        try {  // такая проверка, что указали точно число.
            final int code = Integer.valueOf(codeNsiItem);
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
            final ExportNsiItem nsiItem = new ExportNsiItem(answerProcessing);
            nsiItem.callExportNsiItem(NsiListGroupEnum.NSI, new BigInteger(codeNsiItem));
        } catch (Exception e) {
            answerProcessing.sendErrorToClient("callExportNsiItem(): ", "\"Экспорта данных справочника\" ", LOGGER, e);
        } finally {
            setStateRunOff();
        }

    }

    /**
     * Метод, экспорт сведений о лицевых счетах.
     * Тестовый метод.
     */
    public void callExportAccountData(final String fias) {

        setStateRunOn();
        try {
            answerProcessing.sendMessageToClient("Запуск получения ЛС...");

            //final ExportAccountData accountData = new ExportAccountData(answerProcessing);
            final GetStateResult stateResult = HomeManagementAsyncPort.callExportAccountData(fias, answerProcessing);

            if (stateResult == null || stateResult.getExportAccountResult() == null) {
                answerProcessing.sendMessageToClient("Возникла ошибка, ЛС не удалось получить.");
            } else {
                answerProcessing.sendMessageToClient(String.format("Получено: %d ЛС", stateResult.getExportAccountResult().size()));
            }

            answerProcessing.sendMessageToClient("Получение ЛС завершено.");

        } catch (Exception e) {
            answerProcessing.sendErrorToClient("callExportAccountData(): ", "", LOGGER, e);
        }finally {
            setStateRunOff(); // взводим флаг в состояние откл.
        }
    }

    /**
     * Метод, импорт сведений о платежных документах.
     */
    public void callImportPaymentDocumentData(final String houseGradID) {
        setStateRunOn();
        try {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendMessageToClient("Запуск...");
            answerProcessing.sendMessageToClient("Выгрузка платежных документов...");
            final BillsHandler handler = new BillsHandler(answerProcessing);
            handler.callPaymentDocumentImport(checkHouseGradId(houseGradID));
        } catch (Exception e) {
            answerProcessing.sendErrorToClient("callImportPaymentDocumentData(): ", "", LOGGER, e);
        } finally {
            setStateRunOff();
        }
    }

    /**
     * Метод, экспорт сведений о платежных документах.
     */
    public void callExportPaymentDocumentData() {

        setStateRunOn();
        try {
//            answerProcessing.sendMessageToClient("Запуск получения ПД...");
//            final ExportPaymentDocumentData paymentDocumentData = new ExportPaymentDocumentData(answerProcessing);
//            final List<String> list = new ArrayList<>();
//            list.add("210870201");
//            list.add("210887401");
//            list.add("210889101");
//            list.add("210915701");
//            list.add("212866301 ");
//
//            final ReferenceItemGRADDAO reference = new ReferenceItemGRADDAO();
//            final List<String> listServiceID = new ArrayList<>();
//
//            try (Connection connectionGrad = ConnectionBaseGRAD.instance().getConnection()) {
//
//                final ArrayList<ReferenceItemDataSet> allItems = reference.getAllItems(connectionGrad);
//                for (ReferenceItemDataSet item : allItems) {
//                    listServiceID.add(item.getGuid());
//                }
//            }
//
//            paymentDocumentData.getExportPaymentDocumentDataWithAccountNumbers(list, listServiceID, "b58c5da4-8d62-438f-b11e-d28103220952", OtherFormat.getCalendarForPaymentDocument());
//            answerProcessing.sendMessageToClient("Получение ПД завершено.");
        } catch (Exception e) {
            answerProcessing.sendErrorToClient("callExportPaymentDocumentData(): ", "", LOGGER, e);
        }finally {
            setStateRunOff(); // взводим флаг в состояние откл.
        }
    }

    /**
     * Метод, экспорт сведений о платежных документах.
     */
    public void callWithdrawPaymentDocumentData() {

        setStateRunOn();
        try {
//            answerProcessing.sendMessageToClient("Запуск отзыва ПД...");
//            final ExportPaymentDocumentData paymentDocumentData = new ExportPaymentDocumentData(answerProcessing);
//            final List<String> list = new ArrayList<>();
//            list.add("210870201");
//            list.add("210887401");
//            list.add("210889101");
//            list.add("210915701");
//            list.add("212866301 ");
//
//            final ReferenceItemGRADDAO reference = new ReferenceItemGRADDAO();
//            final List<String> listServiceID = new ArrayList<>();
//
//            try (Connection connectionGrad = ConnectionBaseGRAD.instance().getConnection()) {
//
//                final ArrayList<ReferenceItemDataSet> allItems = reference.getAllItems(connectionGrad);
//                for (ReferenceItemDataSet item : allItems) {
//                    listServiceID.add(item.getGuid());
//                }
//            }
//
//            paymentDocumentData.getExportPaymentDocumentDataWithAccountNumbers(list, listServiceID, "b58c5da4-8d62-438f-b11e-d28103220952", OtherFormat.getCalendarForPaymentDocument());
//            answerProcessing.sendMessageToClient("Получение ПД завершено.");
        } catch (Exception e) {
            answerProcessing.sendErrorToClient("callExportPaymentDocumentData(): ", "", LOGGER, e);
        }finally {
            setStateRunOff(); // взводим флаг в состояние откл.
        }
    }

//    public void callExportOrgPeriodData() {
//
//        setStateRunOn();
//        try {
//            answerProcessing.sendMessageToClient("Получение расчетного периода...");
//            final ExportOrgPeriodData periodData = new ExportOrgPeriodData(answerProcessing);
//            periodData.getOrgPeriodData();
//            answerProcessing.sendMessageToClient("Расчетный период получен.");
//        } catch (SQLException | PreGISException e) {
//            answerProcessing.sendErrorToClient("callExportOrgPeriodData(): ", "", LOGGER, e);
//        } finally {
//            setStateRunOff();
//        }
//    }

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
        answerProcessing.unlockButtons();
        this.stateRun = false;
        clientService.dropDataList();
    }

    /**
     * Метод, задаёт состояние выполнения какого либо метода.
     * Устанавливает флаг в состояние выполняется (true).
     */
    private void setStateRunOn() {
        answerProcessing.lockButtons();
        this.stateRun = true;
    }

    public void checkSessions() {
        clientService.checkSession();
    }


////    Не используются
//
//    /**
//     * Метод асинхронный, экспорт реквизитов платежных документов для банков.
//     */
//    public void callExportPaymentDocumentDetails() {
//
//        ExportPaymentDocumentDetails details = new ExportPaymentDocumentDetails();
//        try {
//            details.callExportPaymentDocumentDetails();
//        } catch (SQLException e) {
//            answerProcessing.sendErrorToClient("callExportOrgPeriodData(): ", "", LOGGER, e);
//        }
//    }
//
//    /**
//     * Метод асинхронный, получить ответ, экспорт реквизитов платежных документов для банков.
//     */
//    public void getStateExportPaymentDocumentDetails() {
//        ExportPaymentDocumentDetails details = new ExportPaymentDocumentDetails();
//        try {
//            details.getStateExportPaymentDocumentDetails();
//        } catch (SQLException e) {
//            answerProcessing.sendErrorToClient("callExportOrgPeriodData(): ", "", LOGGER, e);
//        }
//    }

//    public void callExportStatusCAChData() {
//        ExportStatusCAChData statusCAChData = new ExportStatusCAChData();
//        try {
//            statusCAChData.callExportStatusCAChData();
//        } catch (SQLException e) {
//            answerProcessing.sendErrorToClient("callExportStatusCAChData(): ", "", LOGGER, e);
//        }
//    }

//    public void callExportCAChData() {
//        ExportCAChData caChData = new ExportCAChData(null);
//        try {
//            caChData.callExportCAChData();
//        } catch (SQLException e) {
//            answerProcessing.sendErrorToClient("callExportCAChData(): ", "", LOGGER, e);
//        }
//    }

    /**
     * Экспорт договоров ресурсоснабжения.
     */
    public void callExportSupplyResourceContractData() {

        final ExportSupplyResourceContractData contractData = new ExportSupplyResourceContractData(answerProcessing);
        try {
            contractData.callExportSupplyResourceContractData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, отправляет в БД ГРАДа запрос, получает все дома,
     * проверяет, имеется у дома идентификатор ГИС ЖКХ,
     * если есть, то добавляет его в список для обработки.
     */
    public void getHouseAddedGisJkh() {

        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {

            final HouseGRADDAO houseGRADDAO = new HouseGRADDAO(answerProcessing);
            final LinkedHashMap<String, HouseRecord> houseAddedGisJkh = houseGRADDAO.getHouseRecords(null, connectionGRAD);
//            final HashMap<Integer, String> allHouseForListModalWindow = houseGRADDAO.getAllHouseForListModalWindow(connectionGRAD);
            final ArrayList<ValueJSON> listHouseModalWindow = new ArrayList<>();

            if (houseAddedGisJkh != null && !houseAddedGisJkh.isEmpty()) {
                for (Map.Entry<String, HouseRecord> entry : houseAddedGisJkh.entrySet()) {
                    if (!entry.getValue().getHouseUniqNum().isEmpty()) {
                        listHouseModalWindow.add(new ValueJSON(
                                entry.getValue().getGrad_id().toString(),
                                entry.getValue().getAddresStringShort()));
                    }
                }
            }
            // если домов с HOUSEUNIQNUM нет вообще - добавляем ВСЕ дома, это нужно дя первого обмена
            if (listHouseModalWindow.size() == 0) {
                if (houseAddedGisJkh != null && !houseAddedGisJkh.isEmpty()) {
                    for (Map.Entry<String, HouseRecord> entry : houseAddedGisJkh.entrySet()) {
                            listHouseModalWindow.add(new ValueJSON(
                                    entry.getValue().getGrad_id().toString(),
                                    entry.getValue().getAddresStringShort()));
                    }
                }
            }

            if (listHouseModalWindow.size() > 0) {
                answerProcessing.showHouseListModalWindow(listHouseModalWindow);
            } else {
                setStateRunOn();
                answerProcessing.sendErrorToClientNotException("Не найдены объекты жилищного фонда.");
                setStateRunOff();
            }

        } catch (Exception e) {
            answerProcessing.sendErrorToClient("getHouseAddedGisJkh(): ", "\"Ошибка при получении домов добавленых в ГИС ЖКХ\" ", LOGGER, e);
        }
    }

    public AnswerProcessing getAnswerProcessing() {
        return answerProcessing;
    }

    /**
     * Метод, проверяет значение, которое пришло от клиента при выборе дома для выгрузки.
     * @param houseGradID идентификатор дома в БД Град, null или "-1".
     * @return null или идентификатор дома
     */
    private Integer checkHouseGradId(final String houseGradID) {

        if (houseGradID == null || houseGradID.equalsIgnoreCase("-1")) {
            return null;
        } else {
            return Integer.parseInt(houseGradID);
        }
    }
}
