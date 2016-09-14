package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.house_management.ExportMeteringDeviceDataResult;
import ru.gosuslugi.dom.schema.integration.house_management.ImportMeteringDeviceDataRequest;
import ru.gosuslugi.dom.schema.integration.house_management.ImportResult;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.devices.MeteringDeviceGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.web.servlets.listener.ClientDialogWindowObservable;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

/**
 * Класс, синхронизирует данные о ПУ.
 */
public class UpdateAllMeteringDeviceData implements ClientDialogWindowObservable {

    private static final Logger LOGGER = Logger.getLogger(UpdateAllMeteringDeviceData.class);
    private final AnswerProcessing answerProcessing;
    private final ArrayList<ArchiveData> archiveDataList = new ArrayList<>();
    private int countAll = 0;
    private int countAllGisJkh = 0;
    private int countAdded = 0;
    private int countUpdate = 0;
    private int errorState;

    public UpdateAllMeteringDeviceData(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    public int updateMeteringDeviceData() throws ParseException, SQLException, PreGISException, FileNotFoundException, SOAPException, JAXBException {

        return createMeteringDevice();

    }

    /**
     * Метод, получает список домов готовых для выгрузки в ГИС ЖКХ, формирует по ним новые ПУ и отправляет в ГИС ЖКХ.
     *
     * @throws SQLException
     * @throws PreGISException
     */
    private int createMeteringDevice() throws SQLException, PreGISException, ParseException, FileNotFoundException, SOAPException, JAXBException {

        errorState = 1;

        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {
            HouseGRADDAO houseGRADDAO = new HouseGRADDAO();
            LinkedHashMap<String, Integer> houseAddedGisJkh = houseGRADDAO.getHouseAddedGisJkh(connectionGRAD);
            ImportMeteringDeviceData importMeteringDeviceData = new ImportMeteringDeviceData(answerProcessing);

            for (Map.Entry<String, Integer> entryHouse : houseAddedGisJkh.entrySet()) {
                answerProcessing.sendMessageToClient("Формирую ПУ для дома: " + entryHouse.getKey());
                MeteringDeviceGRADDAO meteringDeviceGRADDAO = new MeteringDeviceGRADDAO(answerProcessing, entryHouse.getValue()); // создаввать каждый раз новый, беру из БД по одному дому данные и использую каждый раз
                java.util.List<ImportMeteringDeviceDataRequest.MeteringDevice> devices = meteringDeviceGRADDAO.getMeteringDevicesForCreate(connectionGRAD);

//                Импортируем ранее загруженные ПУ
                ExportMeteringDeviceData exportMeteringDeviceData = new ExportMeteringDeviceData(answerProcessing);
                ExportMeteringDeviceDataResult exportMeteringDeviceDataResult = exportMeteringDeviceData.callExportMeteringDeviceData(entryHouse.getKey());
                if (exportMeteringDeviceDataResult != null) {
                    meteringDeviceGRADDAO.checkExportMeteringDevices(exportMeteringDeviceDataResult, connectionGRAD);
                    devices = meteringDeviceGRADDAO.getMeteringDevicesForCreate(connectionGRAD); // если добавились новые идентификаторы, нужно исключить их
                }

                countAll += meteringDeviceGRADDAO.getCountAll();

                callImportMeteringDevices(importMeteringDeviceData, devices, entryHouse.getKey(), meteringDeviceGRADDAO, connectionGRAD);

//                Повторно загружаем для занесения MeteringDeviceRootGUID.
                exportMeteringDeviceDataResult = exportMeteringDeviceData.callExportMeteringDeviceData(entryHouse.getKey());
                if (exportMeteringDeviceDataResult != null) {
                    meteringDeviceGRADDAO.checkExportMeteringDevices(exportMeteringDeviceDataResult, connectionGRAD);
                    countAllGisJkh = exportMeteringDeviceDataResult.getMeteringDevice().size();
                }
                if (!meteringDeviceGRADDAO.getDeviceForArchiveAndCreateMap().isEmpty()) {
                    archiveDataList.add(new ArchiveData(importMeteringDeviceData, entryHouse.getKey(), meteringDeviceGRADDAO));
                }

                countUpdate += meteringDeviceGRADDAO.getCountUpdate();
                countAdded += meteringDeviceGRADDAO.getCountAdded();
                if (errorState > meteringDeviceGRADDAO.getErrorState()) {
                    errorState = meteringDeviceGRADDAO.getErrorState();
                }
            }
            showInfo();
        }
        int countRecreate = getCountMeteringDevicesForRecreate();
//        System.out.println("countRecreate: " + countRecreate);
        if (countRecreate > 0) {
            answerProcessing.showQuestionToClient("Не удалось обновить " + countRecreate + " " + getDeviceTag(countRecreate) + " " +
                    "Желаете добавить эти устройства в архив ГИС ЖКХ и создать повторно?\n", this);
        }
        return errorState;
    }

    /**
     * Метод, пересоздание ПУ.
     *
     * @throws SQLException
     * @throws PreGISException
     * @throws JAXBException
     * @throws SOAPException
     * @throws ParseException
     * @throws FileNotFoundException
     */
    private void recreatingMeteringDevices() throws SQLException, PreGISException, JAXBException, SOAPException, ParseException, FileNotFoundException {

        try (Connection connection = ConnectionBaseGRAD.instance().getConnection()) {
            answerProcessing.sendMessageToClient("Пересоздаю ПУ...");
            for (ArchiveData archiveData : archiveDataList) {
                setDevicesToArchiveAndCreate(archiveData.getImportMeteringDeviceData(), archiveData.getFias(),
                        archiveData.getMeteringDeviceGRADDAO(), connection);
            }
            if (errorState == -1) {
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendErrorToClientNotException("Возникла ошибка!\nОперация: \"Пересоздание ПУ\" прервана!");
            } else if (errorState == 0) {
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendErrorToClientNotException("Операция: \"Пересоздание ПУ\" завершилась с ошибками!");
            } else if (errorState == 1) {
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendOkMessageToClient("\"Пересоздание ПУ\" успешно выполнено.");
            }
        }

    }

    /**
     * Метод, возвращает количество записей готовых для пересоздания.
     *
     * @return количество записей готовых для пересоздания.
     */
    private int getCountMeteringDevicesForRecreate() {
        int count = 0;
        for (ArchiveData data : archiveDataList) {
            count += data.getMeteringDeviceGRADDAO().getDeviceForArchiveAndCreateMap().size();
            for (Map.Entry<String, ImportMeteringDeviceDataRequest.MeteringDevice> entry : data.getMeteringDeviceGRADDAO().getDeviceForArchiveAndCreateMap().entrySet()) {
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendMessageToClient("ПУ с идентификатором: " +
                        entry.getKey() + " не удалось обновить.");
            }
        }
        return count;
    }

    /**
     * Метод, выводит информацию пользователю.
     */
    private void showInfo() {
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Всего ПУ найденных в ГИС ЖКХ: " + countAllGisJkh);
        answerProcessing.sendMessageToClient("Всего обработано записей: " + countAll + "\nИз них:");
        answerProcessing.sendMessageToClient("Обновлено в ГРАД: " + countUpdate);
        answerProcessing.sendMessageToClient("Добавлено в ГИС ЖКХ: " + countAdded);
    }

    /**
     * Метод, обрабатывает ПУ для пересоздания их в ГИС ЖКХ.
     *
     * @param importMeteringDeviceData объект отправляет запросы в ГИС ЖКХ.
     * @param fias                     код дома по ФИАС.
     * @param meteringDeviceGRADDAO    объект содержащий данные о ПУ.
     * @param connectionGRAD           подключение к БД ГРАД.
     * @throws ParseException
     * @throws SQLException
     * @throws PreGISException
     * @throws FileNotFoundException
     * @throws SOAPException
     * @throws JAXBException
     */
    private void setDevicesToArchiveAndCreate(ImportMeteringDeviceData importMeteringDeviceData, String fias,
                                              MeteringDeviceGRADDAO meteringDeviceGRADDAO, Connection connectionGRAD)
            throws ParseException, SQLException, PreGISException, FileNotFoundException, SOAPException, JAXBException {

        MeteringDeviceToArchive toArchive = new MeteringDeviceToArchive(answerProcessing, meteringDeviceGRADDAO.getDeviceForArchiveAndCreateMap());
        callImportMeteringDevices(importMeteringDeviceData, toArchive.addMeteringDeviceToArchive(), fias, toArchive, connectionGRAD);
        callImportMeteringDevices(importMeteringDeviceData, toArchive.getListForCreateDevices(), fias, meteringDeviceGRADDAO, connectionGRAD);
    }

    /**
     * Метод, формирует запросы на порции по 20 устройст, ГИС ЖКХ выдаёт ошибки, когда устройств много.
     *
     * @param importMeteringDeviceData - через него отправляем запрос в ГИС ЖКХ.
     * @param devices                  - список ПУ.
     * @param fias                     - ФИАС дома.
     * @param deviceGRADDAO            - объект для занисения данных в БД.
     * @param connectionGRAD           - подключение к БД ГРАД.
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws SOAPException
     * @throws JAXBException
     */
    private void callImportMeteringDevices(ImportMeteringDeviceData importMeteringDeviceData,
                                           List<ImportMeteringDeviceDataRequest.MeteringDevice> devices,
                                           String fias, IMeteringDevices deviceGRADDAO,
                                           Connection connectionGRAD) throws SQLException, FileNotFoundException, SOAPException, JAXBException {

        if (devices.size() > 0) {
            int count = 0;
            while (count < devices.size()) {
                answerProcessing.sendMessageToClient("::clearLabelText");
                ImportResult importResult;
                if (count + 20 > devices.size()) {
                    importResult = importMeteringDeviceData.callImportMeteringDeviceData(fias, devices.subList(count, devices.size()));
                    count += 20;
                } else {
                    importResult = importMeteringDeviceData.callImportMeteringDeviceData(fias, devices.subList(count, count += 20));
                }
                if (importResult != null && importResult.getCommonResult() != null) {
                    deviceGRADDAO.setMeteringDevices(importResult, connectionGRAD);

                    for (ImportResult.CommonResult result : importResult.getCommonResult()) {
                        answerProcessing.sendMessageToClient("GUID: " + result.getGUID());
                        answerProcessing.sendMessageToClient("UniqueNumber: " + result.getUniqueNumber());
                        answerProcessing.sendMessageToClient("MeteringDeviceVersionGUID: " + result.getImportMeteringDevice().getMeteringDeviceVersionGUID());
                        answerProcessing.sendMessageToClient("TransportGUID: " + result.getTransportGUID());
                        answerProcessing.sendMessageToClient("");
                    }
                    if (errorState > deviceGRADDAO.getErrorState()) errorState = deviceGRADDAO.getErrorState();
                } else {
                    errorState = -1;
                }
            }
        }
    }

    @Override
    public void go() {
        try {
            errorState = 1;
            recreatingMeteringDevices();
        } catch (SQLException | FileNotFoundException | ParseException | SOAPException | JAXBException | PreGISException e) {
            answerProcessing.sendErrorToClient("Обновление ПУ", "\"Обновление ПУ\"", LOGGER, e);
        }
    }

    @Override
    public void stop() {
        archiveDataList.clear();
    }

    private String getDeviceTag(int size) {

        int value;

        if ((size / 10) % 10 == 1) {
            value = size / 100;
        } else {
            value =  size % 10;
        }

        if (value == 1) {
            return "прибор учёта";
        } else if (value > 1 && value < 5) {
            return "прибора учёта";
        } else {
            return "приборов учёта";
        }
    }

    /**
     * Класс, для описания объекта.
     */
    private class ArchiveData {
        private ImportMeteringDeviceData importMeteringDeviceData;
        private String fias;
        private MeteringDeviceGRADDAO meteringDeviceGRADDAO;

        private ArchiveData(ImportMeteringDeviceData importMeteringDeviceData, String fias, MeteringDeviceGRADDAO meteringDeviceGRADDAO) {
            this.importMeteringDeviceData = importMeteringDeviceData;
            this.fias = fias;
            this.meteringDeviceGRADDAO = meteringDeviceGRADDAO;
        }

        private ImportMeteringDeviceData getImportMeteringDeviceData() {
            return importMeteringDeviceData;
        }

        private String getFias() {
            return fias;
        }

        private MeteringDeviceGRADDAO getMeteringDeviceGRADDAO() {
            return meteringDeviceGRADDAO;
        }
    }
}
