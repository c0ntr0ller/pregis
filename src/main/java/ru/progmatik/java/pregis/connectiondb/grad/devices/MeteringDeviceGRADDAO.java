package ru.progmatik.java.pregis.connectiondb.grad.devices;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.MeteringDeviceBasicCharacteristicsType;
import ru.gosuslugi.dom.schema.integration.services.house_management.ImportMeteringDeviceDataRequest;
import ru.gosuslugi.dom.schema.integration.services.house_management.ImportResult;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.connectiondb.grad.account.AccountGRADDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSI;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;

import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Класс, получает данные из  БД ГРАД и формирует объект пригодный для ГИС ЖКХ.
 * Для каждого дома, нужно создавать по новой.
 * Что бы из БД каждый раз не запрашивать данные беру их один раз и храню в массивах.
 */
public class MeteringDeviceGRADDAO {

    private static final Logger LOGGER = Logger.getLogger(MeteringDeviceGRADDAO.class);
    private static final String TABLE_NAME_METERING_DEVICE_IDENTIFIERS = "METERING_DEVICE_IDENTIFIERS";
    private static final String SQL_CREATE_TABLE_METERING_DEVICE_IDENTIFIERS =
            "CREATE TABLE IF NOT EXISTS METERING_DEVICE_IDENTIFIERS (" +
                    "ID identity not null primary key, " +
                    "ABON_ID INT not null, " +
                    "HOUSE_ID INT not null, " +
                    "ACCOUNT_GUID varchar(40), " +
                    "PREMISE_GUID varchar(40), " +
                    "LIVING_ROOM_GUID varchar(40), " +
                    "METERING_DEVICE_NUMBER varchar(255), " +
                    "METERING_UNIQUE_NUMBER varchar(40), " +
                    "METERING_ROOT_GUID varchar(40), " +
                    "METERING_VERSION_GUID varchar(40), " +
                    "TRANSPORT_GUID varchar(40), " +
                    "NON_RESIDENTIAL_PREMISE_DEVICE boolean not null); " +
                    "COMMENT ON TABLE \"PUBLIC\".METERING_DEVICE_IDENTIFIERS IS 'Таблица содержит идентификаторы приборов учёта, полученых из ГИС ЖКХ.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.ID IS 'Идентификатор записей.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.ABON_ID IS 'ИД абонента в БД ГРАД.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.HOUSE_ID IS 'ИД дома в БД ГРАД.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.ACCOUNT_GUID IS 'Идентификатор ЛС в ГИС ЖКХ.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.PREMISE_GUID IS 'Идентификатор жилого помещения в ГИС ЖКХ.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.LIVING_ROOM_GUID IS 'Идентификатор комнаты в ГИС ЖКХ.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.METERING_DEVICE_NUMBER IS 'Номер ПУ.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.METERING_UNIQUE_NUMBER IS 'Уникальный реестровый номер в ГИС ЖКХ.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.METERING_ROOT_GUID IS 'Идентификатор ПУ в ГИС ЖКХ.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.METERING_VERSION_GUID IS 'Идентификатор версии ПУ.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.TRANSPORT_GUID IS 'Транспортный идентификатор.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.NON_RESIDENTIAL_PREMISE_DEVICE IS 'Указать true если помещение нежилое.';";

    private static final int METER_ID_PU1 = 17;
    private static final int METER_ID_PU2 = 4;
    private static final int ABON_ID_PU1 = 18;
    private static final int ABON_ID_PU2 = 5;
    private final SimpleDateFormat sDate = new SimpleDateFormat("dd.MM.yyyy");
    private final SimpleDateFormat dateFromSQL = new SimpleDateFormat("yyyy-MM-dd");
    private final AnswerProcessing answerProcessing;
    private final AccountGRADDAO accountGRADDAO;
    private final ReferenceNSI nsi;
    private final Integer houseId;
    private final LinkedHashMap<String, LinkedHashMap<Integer, ImportMeteringDeviceDataRequest.MeteringDevice>> mapTransportMeteringDevice;
    private ArrayList<String[]> exGisIpuIndList;
    private LinkedHashMap<Integer, String[]> exGisIpuIndMap;
    private ArrayList<String> exGisPu2List;
    private ArrayList<Integer> allResidentialPremiseFromGrad;
    private int countAll = 0;
    private int countAdded = 0;

    public MeteringDeviceGRADDAO(AnswerProcessing answerProcessing, Integer houseId) throws SQLException, ParseException, PreGISException {
        this.answerProcessing = answerProcessing;
        this.houseId = houseId;
        mapTransportMeteringDevice = new LinkedHashMap<>(); // хранит TransportGUID, по которому можно идентифицировать каждый счетчик.
        accountGRADDAO = new AccountGRADDAO(answerProcessing);
        nsi = new ReferenceNSI(answerProcessing);
//        Если нет таблицы создаём
        if (!ConnectionDB.instance().tableExist(TABLE_NAME_METERING_DEVICE_IDENTIFIERS.toUpperCase())) {
            try (Connection connection = ConnectionDB.instance().getConnectionDB();
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate(SQL_CREATE_TABLE_METERING_DEVICE_IDENTIFIERS);
            }
        }
    }

    /**
     * Метод, формирует все ПУ для создания в ГИС ЖКХ.
     *
     * @return новые ПУ для ГИС ЖКХ.
     */
    public java.util.List<ImportMeteringDeviceDataRequest.MeteringDevice> getMeteringDevicesForCreate(Connection connectionGRAD) throws SQLException, PreGISException, ParseException {

        java.util.ArrayList<ImportMeteringDeviceDataRequest.MeteringDevice> meteringDeviceList = new ArrayList<>();

//        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {
        ArrayList<String[]> exGisPu1 = getExGisPu1(houseId, connectionGRAD);

        for (String[] exGisPu1Element : exGisPu1) {
            countAll++;
//            Если нет в базе данных о приборе учета, тогда только добавим
//            if (exGisPu1Element[ABON_ID_PU1] != null && !exGisPu1Element[ABON_ID_PU1].isEmpty())
            if (accountGRADDAO.getBuildingIdentifiersFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), "METERROOTGUID", connectionGRAD) == null) {
                ImportMeteringDeviceDataRequest.MeteringDevice meteringDevices = new ImportMeteringDeviceDataRequest.MeteringDevice();
                meteringDevices.setTransportGUID(OtherFormat.getRandomGUID());

                meteringDevices.setDeviceDataToCreate(getMeteringDeviceForCreateElement(houseId, exGisPu1Element, connectionGRAD));

                mapTransportMeteringDevice.put(meteringDevices.getTransportGUID(), new LinkedHashMap<>());
                mapTransportMeteringDevice.get(meteringDevices.getTransportGUID()).put(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), meteringDevices);
                meteringDeviceList.add(meteringDevices);
            }
        }
//        }
        return meteringDeviceList;
    }

    /**
     * Метод, формирует один прибор учёта для  создания в ГИС ЖКХ.
     *
     * @param exGisPu1Element массив данных полученный из БД.
     * @return сформированные прибор учёта.
     */
    private ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate getMeteringDeviceForCreateElement(
            Integer houseId, String[] exGisPu1Element, Connection connectionGrad) throws SQLException, PreGISException, ParseException {

        LinkedHashMap<Integer, String[]> exGisIpuIndMap = getExGisIpuIndMap(houseId, connectionGrad);
        ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate device = new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate();

        if (exGisPu1Element[8].equalsIgnoreCase("Электрическая энергия")) { // Если счетчик электричества
            device.setElectricMeteringDevice(new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate.ElectricMeteringDevice());

            device.getElectricMeteringDevice().setBasicChatacteristicts(getBasicCharacteristics(houseId, exGisPu1Element, connectionGrad));
//            Базовое показание T1
            device.getElectricMeteringDevice().setBaseValueT1(new BigDecimal(exGisPu1Element[10]).setScale(4, BigDecimal.ROUND_DOWN));
//            Базовое показание T2, не обязательно к заполнению
            if (exGisPu1Element[11] != null)
                device.getElectricMeteringDevice().setBaseValueT2(new BigDecimal(exGisPu1Element[11]).setScale(4, BigDecimal.ROUND_DOWN));
//            Базовое показание T3. В зависимости от количества заданных при создании базовых значений ПУ определяется его тип по количеству тарифов. Не обязательно к заполнению
            if (exGisPu1Element[12] != null)
                device.getElectricMeteringDevice().setBaseValueT3(new BigDecimal(exGisPu1Element[12]).setScale(4, BigDecimal.ROUND_DOWN));
//            Время и дата снятия показания
            device.getElectricMeteringDevice().setReadoutDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisIpuIndMap.get(Integer.valueOf(exGisPu1Element[METER_ID_PU1]))[4])));  // Время и дата снятия показания, можно попросить процедуру с которой брать показания и дату, вот её и можно занести
//            device.getElectricMeteringDevice().setReadingsSource(); // Кем внесено не обязательно к заполнению
            device.getElectricMeteringDevice().setMunicipalResource(nsi.getNsiRef("2", exGisPu1Element[8])); // Коммунальный ресурс, берет из справочника 2

        } else { // Характеристики однотарифных ПУ
            device.setOneRateMeteringDevice(new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate.OneRateMeteringDevice());
            device.getOneRateMeteringDevice().setBasicChatacteristicts(getBasicCharacteristics(houseId, exGisPu1Element, connectionGrad));
//            Базовое показание
            device.getOneRateMeteringDevice().setBaseValue(new BigDecimal(exGisPu1Element[10]).setScale(4, BigDecimal.ROUND_DOWN));

            device.getOneRateMeteringDevice().setReadoutDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisIpuIndMap.get(Integer.valueOf(exGisPu1Element[METER_ID_PU1]))[4])));  // Время и дата снятия показания, можно попросить процедуру с которой брать показания и дату, вот её и можно занести
//            device.getElectricMeteringDevice().setReadingsSource(); // Кем внесено
            device.getOneRateMeteringDevice().setMunicipalResource(nsi.getNsiRef("2", exGisPu1Element[8])); // Коммунальный ресурс, берет из справочника 2

        }

        return device;
    }

    private MeteringDeviceBasicCharacteristicsType getBasicCharacteristics(Integer houseId, String[] exGisPu1Element, Connection connectionGrad) throws ParseException, SQLException, PreGISException {

        MeteringDeviceBasicCharacteristicsType basicCharacteristics = new MeteringDeviceBasicCharacteristicsType();
//            Номер ПУ
        basicCharacteristics.setMeteringDeviceNumber(exGisPu1Element[0]);
//            Марка ПУ
        basicCharacteristics.setMeteringDeviceStamp(exGisPu1Element[2]);
//            Дата установки
        if (exGisPu1Element[13] != null)
            basicCharacteristics.setInstallationDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisPu1Element[13])));
//            Дата ввода в эксплуатацию
        basicCharacteristics.setCommissioningDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisPu1Element[14])));
//            Внесение показаний осуществляется в ручном режиме
        basicCharacteristics.setManualModeMetering("Да".equalsIgnoreCase(exGisPu1Element[7]));
//            Дата первичной поверки
        basicCharacteristics.setVerificationCharacteristics(new MeteringDeviceBasicCharacteristicsType.VerificationCharacteristics());

        if (exGisPu1Element[15] != null) { // если нет "Дата первичной поверки" берем дату из "Дата ввода в эксплуатацию"
            basicCharacteristics.getVerificationCharacteristics().setFirstVerificationDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisPu1Element[15])));
        } else {
            answerProcessing.sendMessageToClient("Не указана \"Дата первичной поверки\" для ПУ с кодом: " + exGisPu1Element[METER_ID_PU1] + ", установлена: " + exGisPu1Element[14]);
            basicCharacteristics.getVerificationCharacteristics().setFirstVerificationDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisPu1Element[14])));
        }
//            Межповерочный интервал (НСИ 16)
        basicCharacteristics.getVerificationCharacteristics().setVerificationInterval(nsi.getNsiRef("16", exGisPu1Element[16].split(" ")[0]).getGUID());

//            Характеристики ИПУ жилого помещения (значение справочника "Тип прибора учета" = индивидуальный)
//            Характеристики ИПУ нежилого помещения (значение справочника "Тип прибора учета" = индивидуальный)
        if (exGisPu1Element[1].equalsIgnoreCase("Индивидуальный") && exGisPu1Element[4] != null) {
            if (isResidentialPremiseGrad(houseId, Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), connectionGrad)) { // если помещение не является жилым, значит оно нежилое.
                basicCharacteristics.setResidentialPremiseDevice(new MeteringDeviceBasicCharacteristicsType.ResidentialPremiseDevice());
                basicCharacteristics.getResidentialPremiseDevice().setPremiseGUID(accountGRADDAO.getBuildingIdentifiersFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), "PREMISESGUID", connectionGrad));
                basicCharacteristics.getResidentialPremiseDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), connectionGrad));
            } else {
                basicCharacteristics.setNonResidentialPremiseDevice(new MeteringDeviceBasicCharacteristicsType.NonResidentialPremiseDevice());
                basicCharacteristics.getNonResidentialPremiseDevice().setPremiseGUID(accountGRADDAO.getBuildingIdentifiersFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), "PREMISESGUID", connectionGrad));
                basicCharacteristics.getNonResidentialPremiseDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), connectionGrad));
            }

        } else if (exGisPu1Element[1].equalsIgnoreCase("Коллективный (общедомовой)") && exGisPu1Element[3] != null) {
            answerProcessing.sendMessageToClient("Найден ПУ \"Коллективный (общедомовой)\" не удаётся обработать!");
//            basicCharacteristics.setCollectiveDevice(true); // Признак общедомового ПУ (значение справочника "Тип прибора учета" = коллективный (общедомомвой))

        } else if (exGisPu1Element[1].equalsIgnoreCase("Общий (квартирный)") && exGisPu1Element[4] != null) { // Характеристики общеквартирного ПУ (для квартир коммунального заселения)
            basicCharacteristics.setCollectiveApartmentDevice(new MeteringDeviceBasicCharacteristicsType.CollectiveApartmentDevice());
            basicCharacteristics.getCollectiveApartmentDevice().setPremiseGUID(accountGRADDAO.getBuildingIdentifiersFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), "PREMISESGUID", connectionGrad));
            basicCharacteristics.getCollectiveApartmentDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), connectionGrad));
            for (String arrayData : getOtherLsForPu(houseId, connectionGrad)) {
                if (exGisPu1Element[METER_ID_PU1].equals(getAllData(arrayData)[METER_ID_PU2])) {
                    basicCharacteristics.getCollectiveApartmentDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBase(Integer.valueOf(getAllData(arrayData)[ABON_ID_PU2]), connectionGrad));
                }
            }

        } else if (exGisPu1Element[1].equalsIgnoreCase("Индивидуальный") && exGisPu1Element[3] != null) { // если Характеристики ИПУ жилого дома (значение справочника "Тип прибора учета" = индивидуальный, тип дома = жилой дом)
            basicCharacteristics.setApartmentHouseDevice(new MeteringDeviceBasicCharacteristicsType.ApartmentHouseDevice());
            basicCharacteristics.getApartmentHouseDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), connectionGrad));
            for (String arrayData : getOtherLsForPu(houseId, connectionGrad)) {
                if (exGisPu1Element[METER_ID_PU1].equals(getAllData(arrayData)[METER_ID_PU2])) {
                    basicCharacteristics.getApartmentHouseDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBase(Integer.valueOf(getAllData(arrayData)[ABON_ID_PU2]), connectionGrad));
                }
            }

        } else if (exGisPu1Element[1].equalsIgnoreCase("Комнатный") && exGisPu1Element[5] != null) { // Характеристики комнатного ИПУ (значение справочника "Тип прибора учета" = индивидуальный)
            basicCharacteristics.setLivingRoomDevice(new MeteringDeviceBasicCharacteristicsType.LivingRoomDevice());
            basicCharacteristics.getLivingRoomDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), connectionGrad));
            basicCharacteristics.getLivingRoomDevice().getLivingRoomGUID().add(accountGRADDAO.getBuildingIdentifiersFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), "LIVINGROOMGUID", connectionGrad));
            for (String arrayData : getOtherLsForPu(houseId, connectionGrad)) {
                if (exGisPu1Element[METER_ID_PU1].equals(getAllData(arrayData)[METER_ID_PU2])) {
                    basicCharacteristics.getCollectiveApartmentDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBase(Integer.valueOf(getAllData(arrayData)[ABON_ID_PU2]), connectionGrad));
                }
            }
        }

        return basicCharacteristics;
    }

    /**
     * Метод, формирует один ПУ для обновления в ГИС ЖКХ.
     *
     * @return сформированный для обновления прибор учёта.
     */
    private ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate getMeteringDeviceForUpdate() {
        ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate device = new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate();

        return device;
    }

    /**
     * Метод получае данные из процедуры EX_GIS_PU2, так как процедура выполняется длительное время, решил хранить в List.
     *
     * @param houseId        ид дома в БД ГРАД.
     * @param connectionGrad подключение к БД ГРАД.
     * @return список с данными.
     * @throws SQLException
     */
    private ArrayList<String> getOtherLsForPu(Integer houseId, Connection connectionGrad) throws SQLException {
        if (exGisPu2List == null) exGisPu2List = getExGisPu2(houseId, connectionGrad);
        return exGisPu2List;
    }

    /**
     * Метод, выполняет SQL процедуру полученый результат сохраняет в List.
     *
     * @param houseId    ид дома в БД ГРАД.
     * @param connection подключение к БД.
     * @return список полученый из процедуры EX_GIS_PU1.
     * @throws SQLException
     */
    private ArrayList<String[]> getExGisPu1(Integer houseId, Connection connection) throws SQLException {  // Connection error

        ArrayList<String[]> list = new ArrayList<>();
        return executorProcedure("{EXECUTE PROCEDURE EX_GIS_PU1(" + houseId + ")}",
                connection, resultSet1 -> {
                    while (resultSet1.next()) {
                        if (getAllData(resultSet1.getString(1))[ABON_ID_PU1] != null && !getAllData(resultSet1.getString(1))[ABON_ID_PU1].isEmpty())
                            list.add(getAllData(resultSet1.getString(1)));
                    }
                    return list;
                });
    }

    /**
     * Метод,  выполняет SQL процедуру полученый результат сохраняет в List.
     *
     * @param houseId    ид дома в БД ГРАД.
     * @param connection подключение к БД.
     * @return список полученый из процедуры EX_GIS_PU2.
     * @throws SQLException
     */
    private ArrayList<String> getExGisPu2(Integer houseId, Connection connection) throws SQLException {

        ArrayList<String> list = new ArrayList<>();
        return executorProcedure("{EXECUTE PROCEDURE EX_GIS_PU2(" + houseId + ")}",
                connection, resultSet1 -> {
                    while (resultSet1.next())
                        list.add(resultSet1.getString(1));
                    return list;
                });
    }

    /**
     * Метод, получает из процедуры "EX_GIS_IPU_IND" данные о показаниях приборов учёта.
     *
     * @param houseId        ид дома в БД ГРАД.
     * @param connectionGrad подключение к БД ГРАД.
     * @return список массивом полученных данных.
     * @throws SQLException
     */
    private ArrayList<String[]> getExGisIpuInd(Integer houseId, Connection connectionGrad) throws SQLException {

        if (exGisIpuIndList == null) {
            exGisIpuIndList = new ArrayList<>();
//        Процедура принимает houseId - ид счетчика в БД ГРАД и дату. Дату указываю текущую по умолчанию.
            return executorProcedure("{EXECUTE PROCEDURE EX_GIS_IPU_IND(" + houseId + ", current_date)}",
                    connectionGrad, resultSet1 -> {
                        while (resultSet1.next())
                            exGisIpuIndList.add(getAllData(resultSet1.getString(1)));
                        return exGisIpuIndList;
                    });
        } else {
            return exGisIpuIndList;
        }
    }

    /**
     * Метод, получает из БД текущие показания ПУ, формирует их в Map, для быстрого извлечения даных по ИД прибора учёта
     *
     * @param houseId        ид дома в БД ГРАД.
     * @param connectionGrad подключение к БД ГРАД.
     * @return map, где ключ - ид ПУ в БД ГРАД, значение - массив всех полученных данных из БД.
     * @throws SQLException
     */
    private LinkedHashMap<Integer, String[]> getExGisIpuIndMap(Integer houseId, Connection connectionGrad) throws SQLException {

        if (exGisIpuIndMap == null) {
            exGisIpuIndMap = new LinkedHashMap<>();
            ArrayList<String[]> exGisIpuInd = getExGisIpuInd(houseId, connectionGrad);
            for (String[] exGisIpuIndItem : exGisIpuInd) {
                exGisIpuIndMap.put(Integer.valueOf(exGisIpuIndItem[5]), exGisIpuIndItem);
            }
        }
        return exGisIpuIndMap;
    }

    /**
     * Метод, заносит в разные БД идентификаторы ПУ.
     *
     * @param importResult   данные получены из ГИС ЖКХ.
     * @param connectionGrad подключение к БД ГРАД.
     * @throws SQLException
     */
    public void setMeteringDevices(ImportResult importResult, Connection connectionGrad) throws SQLException {

//        LOGGER.debug("importResult first item: " + importResult.getCommonResult().get(0).getTransportGUID()); IndexOutOfBoundsException

        for (Map.Entry<String, LinkedHashMap<Integer, ImportMeteringDeviceDataRequest.MeteringDevice>> mapEntry : mapTransportMeteringDevice.entrySet()) {
            for (Map.Entry<Integer, ImportMeteringDeviceDataRequest.MeteringDevice> deviceEntry : mapEntry.getValue().entrySet()) {
                LOGGER.debug("MAP: " + mapEntry.getKey() + " Abon AD:" + deviceEntry.getKey());
            }
        }
        System.out.println("GradConnection: " + !connectionGrad.isClosed());
        for (ImportResult.CommonResult itemResult : importResult.getCommonResult()) {
            LOGGER.debug("itemResult.getTransportGUID:" + itemResult.getTransportGUID());
        }

        for (ImportResult.CommonResult result : importResult.getCommonResult()) {
            System.out.println("result.getTransportGUID:" + result.getTransportGUID());

            LOGGER.debug("map contains0:" + mapTransportMeteringDevice.containsKey(result.getTransportGUID()));
            LOGGER.debug("result.getTransportGUID:" + result.getTransportGUID());
            if (mapTransportMeteringDevice.containsKey(result.getTransportGUID())) {
                LOGGER.debug("map contains:" + mapTransportMeteringDevice.containsKey(result.getTransportGUID()));
                for (Map.Entry<Integer, ImportMeteringDeviceDataRequest.MeteringDevice> entry : mapTransportMeteringDevice.get(result.getTransportGUID()).entrySet()) {

                    setMeteringDeviceUniqueNumbers(entry.getKey(), result.getGUID(), result.getImportMeteringDevice().getMeteringDeviceVersionGUID(), connectionGrad); // в БД ГРАД.
                    setMeteringDeviceToLocalBase(entry.getKey(), houseId, importResult, entry.getValue()); // в локальную БД
                    countAdded++;
                    answerProcessing.sendMessageToClient("");
                    answerProcessing.sendMessageToClient("Дабавлен прибор учёта, Уникальный номер: " + result.getUniqueNumber() + " идентификатор: " + result.getGUID());
                    answerProcessing.sendMessageToClient("");

                }
            } else {
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendMessageToClient("Прибор учёта, с транспортным номером: " + result.getTransportGUID() + " не найден!");
                answerProcessing.sendMessageToClient("");
            }
        }
    }

    private void setMeteringDeviceToLocalBase(Integer abonId, Integer houseId, ImportResult importResult, ImportMeteringDeviceDataRequest.MeteringDevice device) throws SQLException {

        String accountGUID;
        String premiseGUID;
//        String livingRoomGUID;

        String meteringDeviceNumber;
        String meteringUniqueNumber;
        String meteringRootGUID;
        String meteringVersionGUID;
        String transportGUID;
        MeteringDeviceBasicCharacteristicsType basic;

        for (ImportResult.CommonResult result : importResult.getCommonResult()) {

            meteringUniqueNumber = result.getUniqueNumber();
            meteringRootGUID = result.getGUID();
            meteringVersionGUID = result.getImportMeteringDevice().getMeteringDeviceVersionGUID();
            transportGUID = result.getTransportGUID();

            if (device.getDeviceDataToCreate() != null) {
                if (device.getDeviceDataToCreate().getElectricMeteringDevice() != null) {
                    basic = device.getDeviceDataToCreate().getElectricMeteringDevice().getBasicChatacteristicts();
                } else {
                    basic = device.getDeviceDataToCreate().getOneRateMeteringDevice().getBasicChatacteristicts();
                }
                meteringDeviceNumber = basic.getMeteringDeviceNumber();

                if (basic.getLivingRoomDevice() != null) { // Комунальные комнаты - один счетчик много комнат
                    accountGUID = basic.getLivingRoomDevice().getAccountGUID().get(0);
                    for (String itemRoom : basic.getLivingRoomDevice().getLivingRoomGUID()) {
                        setMeteringDeviceToLocalBase(abonId, houseId, accountGUID, null, itemRoom, meteringDeviceNumber,
                                meteringUniqueNumber, meteringRootGUID, meteringVersionGUID, transportGUID, false);
                    }
                } else if (basic.getResidentialPremiseDevice() != null) { // жилые помещения, одно помещение и может быть несколько л.счетов.
                    premiseGUID = basic.getResidentialPremiseDevice().getPremiseGUID();
                    for (String itemAccountGUID : basic.getResidentialPremiseDevice().getAccountGUID()) {
                        setMeteringDeviceToLocalBase(abonId, houseId, itemAccountGUID, premiseGUID, null, meteringDeviceNumber,
                                meteringUniqueNumber, meteringRootGUID, meteringVersionGUID, transportGUID, false);
                    }
                } else if (basic.getNonResidentialPremiseDevice() != null) { // не жилые помещения
                    premiseGUID = basic.getNonResidentialPremiseDevice().getPremiseGUID();
                    for (String itemAccountGUID : basic.getResidentialPremiseDevice().getAccountGUID()) {
                        setMeteringDeviceToLocalBase(abonId, houseId, itemAccountGUID, premiseGUID, null, meteringDeviceNumber,
                                meteringUniqueNumber, meteringRootGUID, meteringVersionGUID, transportGUID, true);
                    }
                } else if (basic.getCollectiveApartmentDevice() != null) { // Характеристики общеквартирного ПУ (для квартир коммунального заселения)
                    premiseGUID = basic.getCollectiveApartmentDevice().getPremiseGUID();
                    for (String itemAccountGUID : basic.getCollectiveApartmentDevice().getAccountGUID()) {
                        setMeteringDeviceToLocalBase(abonId, houseId, itemAccountGUID, premiseGUID, null, meteringDeviceNumber,
                                meteringUniqueNumber, meteringRootGUID, meteringVersionGUID, transportGUID, false);
                    }
                } else if (basic.getApartmentHouseDevice() != null) {  // Тип ПУ Коллективный (общедомовой) или Индивидуальный ПУ в ЖД
                    accountGUID = basic.getApartmentHouseDevice().getAccountGUID().get(0);
                    setMeteringDeviceToLocalBase(abonId, houseId, accountGUID, null, null, meteringDeviceNumber,
                            meteringUniqueNumber, meteringRootGUID, meteringVersionGUID, transportGUID, false);
                }
            }
        }
    }

    private void setMeteringDeviceToLocalBase(Integer abonId, Integer houseId, String accountGUID, String premiseGUID,
                                              String livingRoomGUID, String meteringDeviceNumber, String meteringUniqueNumber,
                                              String meteringRootGUID, String meteringVersionGUID, String transportGUID, boolean isNonResitential) throws SQLException {

        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             PreparedStatement pstm = connection.prepareStatement(
                     "INSERT INTO METERING_DEVICE_IDENTIFIERS VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            pstm.setInt(1, abonId);
            pstm.setInt(2, houseId);
            pstm.setString(3, accountGUID);
            pstm.setString(4, premiseGUID);
            pstm.setString(5, livingRoomGUID);
            pstm.setString(6, meteringDeviceNumber);
            pstm.setString(7, meteringUniqueNumber);
            pstm.setString(8, meteringRootGUID);
            pstm.setString(9, meteringVersionGUID);
            pstm.setString(10, transportGUID);
            pstm.setBoolean(11, isNonResitential);
            pstm.executeUpdate();
        }
    }

    /**
     * Метод, определяет по ид абонента, является помещение жилым или нет.
     *
     * @param houseId        ид дома в БД ГРАД.
     * @param abonId         ид абонента в Бд ГРАД.
     * @param connectionGrad подключение к БД ГРАД.
     * @return true - помещение является жилым, false - нежилое помещение.
     * @throws SQLException
     */
    private boolean isResidentialPremiseGrad(Integer houseId, Integer abonId, Connection connectionGrad) throws SQLException {

        if (allResidentialPremiseFromGrad == null) {
            allResidentialPremiseFromGrad = getAllResidentialPremiseFromGrad(houseId, connectionGrad);
        }
        return allResidentialPremiseFromGrad.contains(abonId);
    }

    /**
     * Метод, получает из БД ГРАД, все жилые
     *
     * @param houseId        ид дома из БД ГРАД
     * @param connectionGrad подключение к БД ГРАД.
     * @return список всех ид абонентов из жилых помещений.
     * @throws SQLException
     */
    private ArrayList<Integer> getAllResidentialPremiseFromGrad(Integer houseId, Connection connectionGrad) throws SQLException {

        ArrayList<Integer> list = new ArrayList<>();
        return executorProcedure("EXECUTE PROCEDURE EX_GIS04(" + houseId + ")",
                connectionGrad, resultSet1 -> {
                    while (resultSet1.next())
                        list.add(Integer.valueOf(getAllData(resultSet1.getString(1))[7]));
                    return list;
                });
    }

    /**
     * Метод, добавляет идентификаторы ПУ в БД ГРАД.
     *
     * @param abonId                    ид абонента в Бд ГРАД.
     * @param meteringDeviceVersionGUID Идентификатор версии ПУ.
     * @param meteringDeviceRootGUID    Идентификатор ПУ в ГИС ЖКХ.
     * @param connectionGrad            подключение к БД ГРАД.
     * @return
     * @throws SQLException
     */
    private String setMeteringDeviceUniqueNumbers(Integer abonId, String meteringDeviceVersionGUID, String meteringDeviceRootGUID, Connection connectionGrad) throws SQLException {
        return executorProcedure("{EXECUTE PROCEDURE EX_GIS_ID(" + abonId + ", NULL , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '" + meteringDeviceVersionGUID + "', '" + meteringDeviceRootGUID + "', NULL)}",
                connectionGrad, resultSet1 -> {
                    resultSet1.next();
                    return resultSet1.getString(1);
                });
    }

    /**
     * Метод, выполняет процедуру SQL и возвращает результат.
     *
     * @param sqlRequest SQL запрос.
     * @param connection подключение к БД.
     * @return полученные данные из БД.
     * @throws SQLException
     */
    private <T> T executorProcedure(String sqlRequest, Connection connection, ResultHandler<T> handler) throws SQLException {

//        try (CallableStatement call = connection.prepareCall(sqlRequest)) {
        CallableStatement call = connection.prepareCall(sqlRequest);
        LOGGER.debug(sqlRequest);
        call.executeQuery();
        ResultSet result = call.getResultSet();
        T value = handler.handle(result);
        result.close();
        call.close();

        return value;
//        }
    }

    public int getCountAll() {
        return countAll;
    }

    public int getCountAdded() {
        return countAdded;
    }

    /**
     * Метод, возвращает обработанную строку в массиве с данными.
     *
     * @param data - строка с данными.
     * @return String - массив данных.
     */
    private synchronized String[] getAllData(String data) {

        data = data + "|-1-"; // Если последний параметр пустой, то он в массив не попадет,
        // возникнут ошибки на ссылки на индексы массива.
        String[] array = data.split(Pattern.quote("|"));
        String[] newArray = new String[array.length];
        for (int i = 0; i < array.length; i++) {

            if (array[i] != null && !array[i].trim().isEmpty()) {
                newArray[i] = array[i];
            }
        }
        return newArray;
    }

}
