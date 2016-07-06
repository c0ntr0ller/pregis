package ru.progmatik.java.pregis.connectiondb.grad.devices;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.CommonResultType;
import ru.gosuslugi.dom.schema.integration.services.house_management.*;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.connectiondb.grad.account.AccountGRADDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.message.MessageInBase;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSI;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.SOAPException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
                    "METER_ID INT not null, " +
                    "HOUSE_ID INT not null, " +
                    "ACCOUNT_GUID varchar(40), " +
                    "PREMISE_GUID varchar(40), " +
                    "LIVING_ROOM_GUID varchar(40), " +
                    "METERING_DEVICE_NUMBER varchar(255), " +
                    "METERING_UNIQUE_NUMBER varchar(40), " +
                    "METERING_ROOT_GUID varchar(40), " +
                    "METERING_VERSION_GUID varchar(40), " +
                    "TRANSPORT_GUID varchar(40), " +
                    "NON_RESIDENTIAL_PREMISE_DEVICE boolean DEFAULT false, " +
                    "ARCHIVING_REASON_CODE INT, " +
                    "REPLACE_DEVICE_ID INT), " +
                    "VALUE_REQUEST TIMESTAMP; " +
                    "COMMENT ON TABLE \"PUBLIC\".METERING_DEVICE_IDENTIFIERS IS 'Таблица содержит идентификаторы приборов учёта, полученых из ГИС ЖКХ.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.ID IS 'Идентификатор записей.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.ABON_ID IS 'ИД абонента в БД ГРАД.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.METER_ID IS 'ИД ПУ в БД ГРАД.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.HOUSE_ID IS 'ИД дома в БД ГРАД.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.ACCOUNT_GUID IS 'Идентификатор ЛС в ГИС ЖКХ.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.PREMISE_GUID IS 'Идентификатор жилого помещения в ГИС ЖКХ.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.LIVING_ROOM_GUID IS 'Идентификатор комнаты в ГИС ЖКХ.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.METERING_DEVICE_NUMBER IS 'Номер ПУ.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.METERING_UNIQUE_NUMBER IS 'Уникальный реестровый номер в ГИС ЖКХ.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.METERING_ROOT_GUID IS 'Идентификатор ПУ в ГИС ЖКХ.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.METERING_VERSION_GUID IS 'Идентификатор версии ПУ.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.TRANSPORT_GUID IS 'Транспортный идентификатор.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.NON_RESIDENTIAL_PREMISE_DEVICE IS 'Указать true если помещение нежилое.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.ARCHIVING_REASON_CODE IS 'Если ПУ архивировать, указать код причины архивации ПУ из справочника 21.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.REPLACE_DEVICE_ID IS 'Если произошла замена ПУ, указать id ПУ на которое произошла замена.'; " +
                    "COMMENT ON COLUMN METERING_DEVICE_IDENTIFIERS.VALUE_REQUEST IS 'Хранит время последней передачи показаний ПУ, нужно для обновления данных по ПУ, для понимания были уже переданы хоть раз показания или нет.';";

    private static final int METER_ID_PU1 = 26;
    private static final int METER_ID_PU2 = 4;
    private static final int ABON_ID_PU1 = 27;
    private static final int ABON_ID_PU2 = 5;
    private static final int DEVICE_NUMBER = 1;
    private static final int MUNICIPAL_RESOURCE = 11;
    private static final int METERING_VALUE = 13;
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
    private ArrayList<ImportMeteringDeviceDataRequest.MeteringDevice> devicesForUpdateList = new ArrayList<>();
    private int countAll = 0;
    private int countAdded = 0;
    private int countUpdate = 0;

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
        countAll = 0;

        java.util.ArrayList<ImportMeteringDeviceDataRequest.MeteringDevice> meteringDeviceList = new ArrayList<>();

        ArrayList<String[]> exGisPu1 = getExGisPu1(houseId, connectionGRAD);

        for (String[] exGisPu1Element : exGisPu1) {
            countAll++;

            ImportMeteringDeviceDataRequest.MeteringDevice meteringDevices = new ImportMeteringDeviceDataRequest.MeteringDevice();
            meteringDevices.setTransportGUID(OtherFormat.getRandomGUID());

            meteringDevices.setDeviceDataToCreate(getMeteringDeviceForCreateElement(houseId, exGisPu1Element, connectionGRAD));

            mapTransportMeteringDevice.put(meteringDevices.getTransportGUID(), new LinkedHashMap<>());
            mapTransportMeteringDevice.get(meteringDevices.getTransportGUID()).put(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), meteringDevices);
            mapTransportMeteringDevice.get(meteringDevices.getTransportGUID()).put(Integer.valueOf(exGisPu1Element[METER_ID_PU1]), null);
            LOGGER.debug("meterId: " + exGisPu1Element[METER_ID_PU1]);
            if (getMeteringDeviceUniqueNumbersFromGrad(Integer.valueOf(exGisPu1Element[METER_ID_PU1]), "METERVERSIONGUID", connectionGRAD) == null) {
                meteringDeviceList.add(meteringDevices);
                LOGGER.debug("ПУ добавлен для выгрузки meterId: " + exGisPu1Element[METER_ID_PU1] +
                        " AbonId: " + exGisPu1Element[ABON_ID_PU1]);
            }
        }
        return meteringDeviceList;
    }

    /**
     * Метод, формирует один прибор учёта для  создания в ГИС ЖКХ.
     *
     * @param exGisPu1Element массив данных полученный из БД.
     * @return сформированные прибор учёта.
     */
    private MeteringDeviceFullInformationType getMeteringDeviceForCreateElement(
            Integer houseId, String[] exGisPu1Element, Connection connectionGrad) throws SQLException, PreGISException, ParseException {

//        LinkedHashMap<Integer, String[]> exGisIpuIndMap = getExGisIpuIndMap(houseId, connectionGrad);
        MeteringDeviceFullInformationType device = new MeteringDeviceFullInformationType();

        device.setBasicChatacteristicts(getBasicCharacteristics(houseId, exGisPu1Element, connectionGrad));

        if (exGisPu1Element[MUNICIPAL_RESOURCE].equalsIgnoreCase("Электрическая энергия")) { // Если счетчик электричества

            device.setMunicipalResourceEnergy(new MunicipalResourceElectricType());
//            device.setElectricMeteringDevice(new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate.ElectricMeteringDevice());

//            device.getElectricMeteringDevice().setBasicChatacteristicts(getBasicCharacteristics(houseId, exGisPu1Element, connectionGrad));
//            Базовое показание T1
            device.getMunicipalResourceEnergy().setMeteringValueT1(new BigDecimal(exGisPu1Element[METERING_VALUE]).setScale(4, BigDecimal.ROUND_DOWN));
//            Базовое показание T2, не обязательно к заполнению
            if (exGisPu1Element[METERING_VALUE + 1] != null)
                device.getMunicipalResourceEnergy().setMeteringValueT2(new BigDecimal(exGisPu1Element[METERING_VALUE + 1]).setScale(4, BigDecimal.ROUND_DOWN));
//            Базовое показание T3. В зависимости от количества заданных при создании базовых значений ПУ определяется его тип по количеству тарифов. Не обязательно к заполнению
            if (exGisPu1Element[METERING_VALUE + 2] != null)
                device.getMunicipalResourceEnergy().setMeteringValueT3(new BigDecimal(exGisPu1Element[METERING_VALUE + 2]).setScale(4, BigDecimal.ROUND_DOWN));
//            Время и дата снятия показания
//            device.getMunicipalResourceEnergy().setReadingsSource(); // Кем внесено не обязательно к заполнению
//            device.getMunicipalResourceEnergy().setTransformationRatio(); // Кэффициент трансформации
//            c версии 9.0.1.3 убрали
//            device.getMunicipalResourceEnergy().setReadoutDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisIpuIndMap.get(Integer.valueOf(exGisPu1Element[METER_ID_PU1]))[4])));  // Время и дата снятия показания, можно попросить процедуру с которой брать показания и дату, вот её и можно занести
//            device.getMunicipalResourceEnergy().setMunicipalResource(nsi.getNsiRef("2", exGisPu1Element[8])); // Коммунальный ресурс, берет из справочника 2

        } else { // Характеристики однотарифных ПУ
//            device.setOneRateMeteringDevice(new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate.OneRateMeteringDevice());
//            device.getOneRateMeteringDevice().setBasicChatacteristicts(getBasicCharacteristics(houseId, exGisPu1Element, connectionGrad));
//            Базовое показание
            MunicipalResourceNotElectricType nonElectric = new MunicipalResourceNotElectricType();

            nonElectric.setMeteringValue(new BigDecimal(exGisPu1Element[METERING_VALUE]).setScale(4, BigDecimal.ROUND_DOWN));
            nonElectric.setMunicipalResource(nsi.getNsiRef("2", exGisPu1Element[MUNICIPAL_RESOURCE]));
//            nonElectric.setReadingsSource(); // Кем внесено не обязательно к заполнению
//            c версии 9.0.1.3 убрали
//            device.getOneRateMeteringDevice().setReadoutDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisIpuIndMap.get(Integer.valueOf(exGisPu1Element[METER_ID_PU1]))[4])));  // Время и дата снятия показания, можно попросить процедуру с которой брать показания и дату, вот её и можно занести
//            device.getOneRateMeteringDevice().setMunicipalResource(nsi.getNsiRef("2", exGisPu1Element[8])); // Коммунальный ресурс, берет из справочника 2

            device.getMunicipalResourceNotEnergy().add(nonElectric);

        }

        return device;
    }

    private MeteringDeviceBasicCharacteristicsType getBasicCharacteristics(Integer houseId, String[] exGisPu1Element, Connection connectionGrad) throws ParseException, SQLException, PreGISException {

        MeteringDeviceBasicCharacteristicsType basicCharacteristics = new MeteringDeviceBasicCharacteristicsType();
//            Номер ПУ
        basicCharacteristics.setMeteringDeviceNumber(exGisPu1Element[DEVICE_NUMBER]);
//            Марка ПУ
        basicCharacteristics.setMeteringDeviceStamp(exGisPu1Element[2]);

//            Модель ПУ
//        basicCharacteristics.setMeteringDeviceModel();

//            Дата установки
        if (exGisPu1Element[13] != null)
            basicCharacteristics.setInstallationDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisPu1Element[13])));

//            Дата ввода в эксплуатацию
        basicCharacteristics.setCommissioningDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisPu1Element[14])));

//            Внесение показаний осуществляется в ручном режиме
        basicCharacteristics.setManualModeMetering("Да".equalsIgnoreCase(exGisPu1Element[7]));

//          Характеристики поверки  Дата первичной поверки
        basicCharacteristics.setVerificationCharacteristics(new MeteringDeviceBasicCharacteristicsType.VerificationCharacteristics());
        if (exGisPu1Element[15] == null || System.currentTimeMillis() < dateFromSQL.parse(exGisPu1Element[15]).getTime()) { // если нет "Дата первичной поверки" берем дату из "Дата ввода в эксплуатацию"
            answerProcessing.sendMessageToClient("Не указана \"Дата первичной поверки\" для ПУ с кодом: " + exGisPu1Element[METER_ID_PU1] + ", установлена: " + exGisPu1Element[14]);
            basicCharacteristics.getVerificationCharacteristics().setFirstVerificationDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisPu1Element[14])));
        } else {
            basicCharacteristics.getVerificationCharacteristics().setFirstVerificationDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisPu1Element[15])));
        }
//            Межповерочный интервал (НСИ 16)
        basicCharacteristics.getVerificationCharacteristics().setVerificationInterval(nsi.getNsiRef("16", exGisPu1Element[16].split(" ")[0]));

//        Дата опломбирования ПУ заводом-изготовителем (обязательно для заполнения при импорте)
//        basicCharacteristics.setFactorySealDate();

//        Наличие датчиков давления
//        basicCharacteristics.setPressureSensor();

//            Характеристики ИПУ жилого помещения (значение справочника "Тип прибора учета" = индивидуальный)
//            Характеристики ИПУ нежилого помещения (значение справочника "Тип прибора учета" = индивидуальный)
        if (exGisPu1Element[1].equalsIgnoreCase("Индивидуальный") && exGisPu1Element[4] != null) {
            if (isResidentialPremiseGrad(houseId, Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), connectionGrad)) { // если помещение не является жилым, значит оно нежилое.
                basicCharacteristics.setResidentialPremiseDevice(new MeteringDeviceBasicCharacteristicsType.ResidentialPremiseDevice());
                basicCharacteristics.getResidentialPremiseDevice().setPremiseGUID(accountGRADDAO.getBuildingIdentifiersFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), "PREMISESGUID", connectionGrad));
                basicCharacteristics.getResidentialPremiseDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), connectionGrad));
            } else {
                String accountGUIDFromGrad = accountGRADDAO.getAccountGUIDFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), connectionGrad);
                String premiseGUIDFromGrad = accountGRADDAO.getBuildingIdentifiersFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), "PREMISESGUID", connectionGrad);
                basicCharacteristics.setNonResidentialPremiseDevice(new MeteringDeviceBasicCharacteristicsType.NonResidentialPremiseDevice());
                basicCharacteristics.getNonResidentialPremiseDevice().setPremiseGUID(premiseGUIDFromGrad);
                basicCharacteristics.getNonResidentialPremiseDevice().getAccountGUID().add(accountGUIDFromGrad);
                LOGGER.debug("ПУ помечен для нежилого помещения. MeterID: " + exGisPu1Element[METER_ID_PU1] +
                        " AccountGUID: " + accountGUIDFromGrad + " PremimiseGUID: " + premiseGUIDFromGrad);
            }

            // Признак общедомового ПУ (значение справочника "Тип прибора учета" = коллективный (общедомомвой))
        } else if (exGisPu1Element[1].equalsIgnoreCase("Коллективный (общедомовой)") && exGisPu1Element[3] != null) {
            answerProcessing.sendMessageToClient("Найден ПУ \"Коллективный (общедомовой)\" не удаётся обработать!");
//            basicCharacteristics.setCollectiveDevice(true);

            // Информация о наличии возможности дистанционного снятия показаний ПУ
            // указанием наименования установленной системы (обязательно для заполнения,
            // если tns:ManualModeMetering = true, в противном случае поле не обрабатывается при импорте)
//            basicCharacteristics.getCollectiveDevice().setManualModeInformation();

//             Информация о наличии датчиков температуры с указанием их местоположения на узле учета
//             (обязательно для заполнения, если tns:TemperatureSensor = true,
//             в противном случае поле не обрабатывается при импорте)
//            basicCharacteristics.getCollectiveDevice().setTemperatureSensorInformation();

//              Информация о наличии датчиков давления с указанием их местоположения на узле учета
//              (обязательно для заполнения, если tns:PressureSensor = true,
//              в противном случае поле не обрабатывается при импорте)
//            basicCharacteristics.getCollectiveDevice().setPressureSensorInformation();

//            Электронный образ проекта узла учета
//            basicCharacteristics.getCollectiveDevice().getProjectRegistrationNode().add();

//            Электронный образ акта ввода узла учета в эксплуатацию
//            basicCharacteristics.getCollectiveDevice().getCertificate().add();

            // Характеристики общеквартирного ПУ (для квартир коммунального заселения) (значение справочника "Вид прибора учета" = Общий (квартирный))
        } else if (exGisPu1Element[1].equalsIgnoreCase("Общий (квартирный)") && exGisPu1Element[4] != null) {
            basicCharacteristics.setCollectiveApartmentDevice(new MeteringDeviceBasicCharacteristicsType.CollectiveApartmentDevice());
            basicCharacteristics.getCollectiveApartmentDevice().setPremiseGUID(accountGRADDAO.getBuildingIdentifiersFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), "PREMISESGUID", connectionGrad));
            basicCharacteristics.getCollectiveApartmentDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), connectionGrad));
            for (String arrayData : getOtherLsForPu(houseId, connectionGrad)) {
                if (exGisPu1Element[METER_ID_PU1].equals(getAllData(arrayData)[METER_ID_PU2])) {
                    basicCharacteristics.getCollectiveApartmentDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBase(Integer.valueOf(getAllData(arrayData)[ABON_ID_PU2]), connectionGrad));
                }
            }

            // если Характеристики ИПУ жилого дома (значение справочника "Тип прибора учета" = индивидуальный, тип дома = жилой дом)
        } else if (exGisPu1Element[1].equalsIgnoreCase("Индивидуальный") && exGisPu1Element[3] != null) {
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
        } else {
            LOGGER.debug("ПУ MeterID: " + exGisPu1Element[METER_ID_PU1] +
                    " AbonID: " + exGisPu1Element[ABON_ID_PU1] +
                    " с именем: " + exGisPu1Element[DEVICE_NUMBER] + " не определен ни к одному из типов помещений!");
        }

        return basicCharacteristics;
    }

    /**
     * Метод, проверяет добавляет данные о ПУ, если их ещё нет.
     *
     * @param exportMeteringDeviceDataResult полученный ответ на запрос о состоянии ПУ.
     * @param connectionGRAD                 подключение к БД ГРАД.
     * @throws SQLException
     */
    public void checkExportMeteringDevices(ExportMeteringDeviceDataResult exportMeteringDeviceDataResult, Connection connectionGRAD) throws SQLException {

        for (ExportMeteringDeviceDataResultType resultType : exportMeteringDeviceDataResult.getMeteringDevice()) {
            if (resultType.getStatusRootDoc().equals("Active")) {
                checkMeteringDevice(resultType.getMeteringDeviceRootGUID(), resultType.getMeteringDeviceVersionGUID(),
                        resultType.getBasicChatacteristicts(), connectionGRAD);
                checkBasicCharacteristicsForUpdate(resultType);
            }
        }

    }

    /**
     * Метод, по указанным параметрам, ищет meterId в локальной таблице, если не находит
     * сравнивает по идентификатарам ГИС ЖКХ (AccountGUID, PremiseGUID).
     *
     * @param meteringDeviceRootGUID                 идентификатор ПУ в ГИС ЖКХ.
     * @param meteringDeviceVersionGUID              идентификатор версии ПУ в ГИС ЖКХ.
     * @param meteringDeviceBasicCharacteristicsType базовые характеристики ПУ.
     * @param connectionGRAD                         подключение к БД ГРАД.
     * @throws SQLException
     */
    private void checkMeteringDevice(String meteringDeviceRootGUID, String meteringDeviceVersionGUID,
                                     MeteringDeviceBasicCharacteristicsType meteringDeviceBasicCharacteristicsType,
                                     Connection connectionGRAD) throws SQLException {

        Integer meterId;
        String meteringRootGUIDGrad;
        String meteringVersionGUIDGrad;

        meterId = getMeterIdFromLocalBaseUseMeteringRootGUID(meteringDeviceRootGUID);
        if (meterId == null) meterId = getMeterIdFromLocalBaseUseMeteringVersionGUID(meteringDeviceVersionGUID);

        if (meterId != null) { // если есть ид то берем оп нему идентификаторы ГИС ЖКХ.
            meteringRootGUIDGrad = getMeteringDeviceUniqueNumbersFromGrad(meterId, "METERROOTGUID", connectionGRAD);
            meteringVersionGUIDGrad = getMeteringDeviceUniqueNumbersFromGrad(meterId, "METERVERSIONGUID", connectionGRAD);

            if (meteringRootGUIDGrad == null && meteringVersionGUIDGrad == null) { // если оба идентификатора не найдены в БД ГРАД.
                updateMeteringVersionGUID(meterId, meteringDeviceRootGUID, meteringDeviceVersionGUID, connectionGRAD);
            } else if (meteringRootGUIDGrad == null && meteringVersionGUIDGrad.equalsIgnoreCase(meteringDeviceVersionGUID)) { // есле не найден meteringRootGUIDGrad
                setMeteringRootGUID(meterId, meteringDeviceRootGUID, connectionGRAD);
            } else if (meteringRootGUIDGrad.equalsIgnoreCase(meteringDeviceRootGUID) &&
                    !meteringVersionGUIDGrad.equalsIgnoreCase(meteringDeviceVersionGUID)) { // Если meteringRootGUIDGrad верный, а meteringDeviceVersionGUID изменился на устройстве.
                updateMeteringVersionGUID(meterId, meteringDeviceRootGUID, meteringDeviceVersionGUID, connectionGRAD);
            }
        } else { // если нет, то надо найти по AccountGUID и PremiseGUID.
            setByAccountAndPremiseGUIDs(meteringDeviceRootGUID, meteringDeviceVersionGUID,
                    meteringDeviceBasicCharacteristicsType, connectionGRAD);
        }
    }

    /**
     * Метод, проверяет каждый ПУ на соответствие данных, если данные ГИС ЖКХ отличаются от данных ГРАДа,
     * тогда надо определить были уже переданные показания по ПУ и сформировать соответствующий объект для обновления.
     *
     */
    private void checkBasicCharacteristicsForUpdate(ExportMeteringDeviceDataResultType importDevice) throws SQLException {

        Integer meterId = getMeterIdFromLocalBaseUseMeteringVersionGUID(importDevice.getMeteringDeviceVersionGUID());
        if (meterId != null) {
            for (Map.Entry<String, LinkedHashMap<Integer, ImportMeteringDeviceDataRequest.MeteringDevice>>
                    mapEntry : mapTransportMeteringDevice.entrySet()) {

                Integer abonId = null;
                MeteringDeviceFullInformationType device = null;

                Integer meterIdDevice = null;

                for (Map.Entry<Integer, ImportMeteringDeviceDataRequest.MeteringDevice> deviceEntry : mapEntry.getValue().entrySet()) {
                    if (deviceEntry.getValue() != null) {
                        abonId = deviceEntry.getKey();
                        device = deviceEntry.getValue().getDeviceDataToCreate();
                    } else {
                        meterIdDevice = deviceEntry.getKey();
                    }
                }

                if (meterId.equals(meterIdDevice)) {
                    if (importDevice.getMunicipalResourceNotEnergy() != null
                            && device.getMunicipalResourceNotEnergy() != null &&
                            !importDevice.getMunicipalResourceNotEnergy().get(0).getMunicipalResource().getGUID().equals(device.getMunicipalResourceNotEnergy().get(0).getMunicipalResource().getGUID())) {
                        setUpdateDevice(importDevice, device);

                    } else if (!importDevice.getBasicChatacteristicts().getMeteringDeviceNumber().equals(device.getBasicChatacteristicts().getMeteringDeviceNumber()) &&
                            !importDevice.getBasicChatacteristicts().getMeteringDeviceStamp().equals(device.getBasicChatacteristicts().getMeteringDeviceStamp()) &&
                            !importDevice.getBasicChatacteristicts().getMeteringDeviceModel().equals(device.getBasicChatacteristicts().getMeteringDeviceModel()) &&
                            !(importDevice.getBasicChatacteristicts().isManualModeMetering() == device.getBasicChatacteristicts().isManualModeMetering()) &&
                            !(importDevice.getBasicChatacteristicts().isPressureSensor() == device.getBasicChatacteristicts().isPressureSensor()) &&
                            !(importDevice.getBasicChatacteristicts().isTemperatureSensor() == device.getBasicChatacteristicts().isTemperatureSensor()) &&
                            !importDevice.getBasicChatacteristicts().getInstallationDate().equals(device.getBasicChatacteristicts().getInstallationDate()) &&
                            !importDevice.getBasicChatacteristicts().getCommissioningDate().equals(device.getBasicChatacteristicts().getCommissioningDate()) &&
                            !importDevice.getBasicChatacteristicts().getFactorySealDate().equals(device.getBasicChatacteristicts().getFactorySealDate())) {
                        setUpdateDevice(importDevice, device);
                    }
                }
            }
        }
    }

    /**
     * Метод, добавляет в лист объект для обновления.
     * @param importDevice устройство полученное из ГИС ЖКХ
     * @param device обновленное устройство.
     * @throws SQLException
     */
    private void setUpdateDevice(ExportMeteringDeviceDataResultType importDevice, MeteringDeviceFullInformationType device) throws SQLException {

        ImportMeteringDeviceDataRequest.MeteringDevice tempDevice = new ImportMeteringDeviceDataRequest.MeteringDevice();

        tempDevice.setTransportGUID(OtherFormat.getRandomGUID());
        tempDevice.setDeviceDataToUpdate(new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate());
        if (getDeviceValueRequest(importDevice.getMeteringDeviceVersionGUID()) == null) {
            tempDevice.getDeviceDataToUpdate().setUpdateBeforeDevicesValues(device);
        } else {
            tempDevice.getDeviceDataToUpdate().setUpdateAfterDevicesValues(new MeteringDeviceToUpdateAfterDevicesValuesType());
            tempDevice.getDeviceDataToUpdate().getUpdateBeforeDevicesValues().setBasicChatacteristicts(device.getBasicChatacteristicts());
            if (device.getMunicipalResourceEnergy() == null) {
                tempDevice.getDeviceDataToUpdate().getUpdateBeforeDevicesValues().getMunicipalResourceNotEnergy().addAll(device.getMunicipalResourceNotEnergy());
            } else {
                tempDevice.getDeviceDataToUpdate().getUpdateBeforeDevicesValues().setMunicipalResourceEnergy(device.getMunicipalResourceEnergy());
            }
        }
        devicesForUpdateList.add(tempDevice);
    }

    /**
     * Метод, проводит сравнения имеющихся ПУ и полученных по идентификаторам помещений и ЛС.
     *
     * @param meteringDeviceRootGUID    идентификатор ПУ в ГИС ЖКХ.
     * @param meteringDeviceVersionGUID идентификатор версии ПУ в ГИС ЖКХ.
     * @param basicCharacteristics      базовые характеристики ПУ.
     * @param connectionGRAD            подключение к БД ГРАД.
     */
    private void setByAccountAndPremiseGUIDs(String meteringDeviceRootGUID, String meteringDeviceVersionGUID,
                                             MeteringDeviceBasicCharacteristicsType
                                                     basicCharacteristics, Connection connectionGRAD) throws SQLException {

        for (Map.Entry<String, LinkedHashMap<Integer, ImportMeteringDeviceDataRequest.MeteringDevice>>
                mapEntry : mapTransportMeteringDevice.entrySet()) {

            Integer abonId = null;
//            ImportMeteringDeviceDataRequest.MeteringDevice device;
            MeteringDeviceBasicCharacteristicsType device = null;
            Integer meterId = null;

            for (Map.Entry<Integer, ImportMeteringDeviceDataRequest.MeteringDevice> deviceEntry : mapEntry.getValue().entrySet()) {
                if (deviceEntry.getValue() != null) {
                    abonId = deviceEntry.getKey();
                    device = deviceEntry.getValue().getDeviceDataToCreate().getBasicChatacteristicts();
                } else {
                    meterId = deviceEntry.getKey();
                }
            }

            if (basicCharacteristics.getMeteringDeviceNumber().equalsIgnoreCase(device.getMeteringDeviceNumber()) &&
                    basicCharacteristics.getMeteringDeviceStamp().equalsIgnoreCase(device.getMeteringDeviceStamp()) &&
                    basicCharacteristics.getMeteringDeviceModel().equalsIgnoreCase(device.getMeteringDeviceModel())) {
                if (basicCharacteristics.getLivingRoomDevice() != null) { // Комунальные комнаты - один счетчик много комнат
                    if (basicCharacteristics.getLivingRoomDevice().getAccountGUID().contains(device.getLivingRoomDevice().getAccountGUID().get(0)) &&
                            basicCharacteristics.getLivingRoomDevice().getLivingRoomGUID().contains(device.getLivingRoomDevice().getLivingRoomGUID().get(0))) {

                        for (String itemRoom : basicCharacteristics.getLivingRoomDevice().getLivingRoomGUID()) {
                            if (setMeteringDeviceToLocalBase(abonId, meterId, houseId,
                                    basicCharacteristics.getLivingRoomDevice().getAccountGUID().get(0),
                                    null, itemRoom, basicCharacteristics.getMeteringDeviceNumber(),
                                    null, meteringDeviceRootGUID, meteringDeviceVersionGUID, null, false, connectionGRAD)) {
                                LOGGER.debug("abinId: " + abonId + " meterId: " + meterId);
                                countAdded++;
                                answerProcessing.sendMessageToClient("Дабавлен прибор учёта, идентификатор ПУ в ГИС ЖКХ: " + meteringDeviceRootGUID + ".");
                                answerProcessing.sendMessageToClient("");
                            }
                        }
                    }
                } else if (basicCharacteristics.getResidentialPremiseDevice() != null) { // жилые помещения, одно помещение и может быть несколько л.счетов.

                    if (basicCharacteristics.getResidentialPremiseDevice().getAccountGUID().contains(device.getResidentialPremiseDevice().getAccountGUID().get(0)) &&
                            basicCharacteristics.getResidentialPremiseDevice().getPremiseGUID().equalsIgnoreCase(device.getResidentialPremiseDevice().getPremiseGUID())) {

                        for (String accountGUID : basicCharacteristics.getResidentialPremiseDevice().getAccountGUID()) {
                            if (setMeteringDeviceToLocalBase(abonId, meterId, houseId, accountGUID,
                                    basicCharacteristics.getResidentialPremiseDevice().getPremiseGUID(), null,
                                    basicCharacteristics.getMeteringDeviceNumber(),
                                    null, meteringDeviceRootGUID, meteringDeviceVersionGUID, null, false, connectionGRAD)) {
                                LOGGER.debug("abinId: " + abonId + " meterId: " + meterId);
                                countAdded++;
                                answerProcessing.sendMessageToClient("Дабавлен прибор учёта, идентификатор ПУ в ГИС ЖКХ: " + meteringDeviceRootGUID + ".");
                                answerProcessing.sendMessageToClient("");
                            }
                        }
                    }
                } else if (basicCharacteristics.getNonResidentialPremiseDevice() != null) { // не жилые помещения

                    if (basicCharacteristics.getNonResidentialPremiseDevice().getAccountGUID().contains(device.getNonResidentialPremiseDevice().getAccountGUID().get(0)) &&
                            basicCharacteristics.getResidentialPremiseDevice().getPremiseGUID().equalsIgnoreCase(device.getNonResidentialPremiseDevice().getPremiseGUID())) {

                        if (setMeteringDeviceToLocalBase(abonId, meterId, houseId,
                                basicCharacteristics.getNonResidentialPremiseDevice().getAccountGUID().get(0),
                                basicCharacteristics.getNonResidentialPremiseDevice().getPremiseGUID(), null,
                                basicCharacteristics.getMeteringDeviceNumber(),
                                null, meteringDeviceRootGUID, meteringDeviceVersionGUID, null, true, connectionGRAD)) {
                            LOGGER.debug("abinId: " + abonId + " meterId: " + meterId);
                            countAdded++;
                            answerProcessing.sendMessageToClient("Дабавлен прибор учёта, идентификатор ПУ в ГИС ЖКХ: " + meteringDeviceRootGUID + ".");
                            answerProcessing.sendMessageToClient("");
                        }
                    }
                } else if (basicCharacteristics.getCollectiveApartmentDevice() != null) { // Характеристики общеквартирного ПУ (для квартир коммунального заселения)

                    if (basicCharacteristics.getCollectiveApartmentDevice().getAccountGUID().contains(device.getCollectiveApartmentDevice().getAccountGUID().get(0)) &&
                            basicCharacteristics.getCollectiveApartmentDevice().getPremiseGUID().equalsIgnoreCase(device.getCollectiveApartmentDevice().getPremiseGUID())) {

                        for (String accountGUID : basicCharacteristics.getCollectiveApartmentDevice().getAccountGUID()) {
                            if (setMeteringDeviceToLocalBase(abonId, meterId, houseId, accountGUID,
                                    basicCharacteristics.getCollectiveApartmentDevice().getPremiseGUID(), null,
                                    basicCharacteristics.getMeteringDeviceNumber(),
                                    null, meteringDeviceRootGUID, meteringDeviceVersionGUID, null, false, connectionGRAD)) {
                                LOGGER.debug("abinId: " + abonId + " meterId: " + meterId);
                                countAdded++;
                                answerProcessing.sendMessageToClient("Дабавлен прибор учёта, идентификатор ПУ в ГИС ЖКХ: " + meteringDeviceRootGUID + ".");
                                answerProcessing.sendMessageToClient("");
                            }
                        }
                    }

                } else if (basicCharacteristics.getApartmentHouseDevice() != null) {  // Тип ПУ Коллективный (общедомовой) или Индивидуальный ПУ в ЖД

                    if (basicCharacteristics.getApartmentHouseDevice().getAccountGUID().contains(device.getApartmentHouseDevice().getAccountGUID().get(0))) {

                        for (String accountGUID : basicCharacteristics.getApartmentHouseDevice().getAccountGUID()) {
                            if (setMeteringDeviceToLocalBase(abonId, meterId, houseId, accountGUID, null, null,
                                    basicCharacteristics.getMeteringDeviceNumber(),
                                    null, meteringDeviceRootGUID, meteringDeviceVersionGUID, null, false, connectionGRAD)) {
                                LOGGER.debug("abinId: " + abonId + " meterId: " + meterId);
                                countAdded++;
                                answerProcessing.sendMessageToClient("Дабавлен прибор учёта, идентификатор ПУ в ГИС ЖКХ: " + meteringDeviceRootGUID + ".");
                                answerProcessing.sendMessageToClient("");
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Метод, обновляет информацию о идентификаторе версии ПУ.
     *
     * @param meterId                   ид ПУ в БД ГРАД.
     * @param meteringDeviceRootGUID    идентификатор ПУ в ГИС ЖКХ.
     * @param meteringDeviceVersionGUID идентификатор версии ПУ в ГИС ЖКХ.
     * @param connectionGRAD            подключение к БД ГРАД.
     */
    private void updateMeteringVersionGUID(Integer meterId, String meteringDeviceRootGUID, String meteringDeviceVersionGUID, Connection connectionGRAD) throws SQLException {
        setMeteringDeviceUniqueNumbers(meterId, meteringDeviceVersionGUID, meteringDeviceRootGUID, connectionGRAD);
        if (getMeterIdFromLocalBaseUseMeteringVersionGUID(meteringDeviceVersionGUID) == null) {
            setMeteringVersionGUIDToLocalDb(meterId, meteringDeviceRootGUID, meteringDeviceVersionGUID);
        }
        countUpdate++;
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Обновлен ПУ, идентификатор версии ПУ в ГИС ЖКХ: " + meteringDeviceVersionGUID);
        LOGGER.info("Обновлен элемент ПУ: ID = " + meteringDeviceVersionGUID + " MeterId: " + meterId);
    }

    /**
     * Метод, обновляет информацию о идентификаторе версии ПУ в локальной БД.
     *
     * @param meterId                   ид ПУ в БД ГРАД.
     * @param meteringDeviceRootGUID    идентификатор ПУ в ГИС ЖКХ.
     * @param meteringDeviceVersionGUID идентификатор версии ПУ в ГИС ЖКХ.
     */
    private void setMeteringVersionGUIDToLocalDb(Integer meterId, String meteringDeviceRootGUID, String meteringDeviceVersionGUID) throws SQLException {
        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             PreparedStatement ps = connection.prepareStatement("UPDATE METERING_DEVICE_IDENTIFIERS " +
                     "SET METERING_VERSION_GUID = ? WHERE METER_ID = ? AND METERING_ROOT_GUID = ?")) {
            ps.setString(1, meteringDeviceVersionGUID);
            ps.setInt(2, meterId);
            ps.setString(3, meteringDeviceRootGUID);
            ps.executeUpdate();
        }
    }

    /**
     * Метод, заносит идентификаторы в БД.
     *
     * @param meterId                ид ПУ в БД ГРАД.
     * @param meteringDeviceRootGUID идентификатор ПУ в ГИС ЖКХ.
     * @param connectionGRAD         подключение к БД ГРАД.
     */
    private void setMeteringRootGUID(Integer meterId, String meteringDeviceRootGUID, Connection connectionGRAD) throws SQLException {
        setMeteringDeviceUniqueNumbers(meterId, null, meteringDeviceRootGUID, connectionGRAD);
        if (getMeterIdFromLocalBaseUseMeteringRootGUID(meteringDeviceRootGUID) == null) {
            setMeteringRootGUIDToLocalDb(meterId, meteringDeviceRootGUID);
        }
        countUpdate++;
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Обновлен ПУ, идентификатор ПУ в ГИС ЖКХ: " + meteringDeviceRootGUID);
        LOGGER.info("Обновлен элемент ПУ: ID = " + meteringDeviceRootGUID + " MeterId: " + meterId);
    }

    /**
     * Метод, добавляет в локальную БД информацию о идентификаторе ПУ в ГИС ЖКХ.
     *
     * @param meterId                ид ПУ в БД ГРАД.
     * @param meteringDeviceRootGUID идентификатор ПУ в ГИС ЖКХ.
     */
    private void setMeteringRootGUIDToLocalDb(Integer meterId, String meteringDeviceRootGUID) throws SQLException {

        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             PreparedStatement ps = connection.prepareStatement("UPDATE METERING_DEVICE_IDENTIFIERS " +
                     "SET METERING_ROOT_GUID = ? WHERE METER_ID = ?")) {
            ps.setString(1, meteringDeviceRootGUID);
            ps.setInt(2, meterId);
            ps.executeUpdate();
        }
    }

    /**
     * Метод, формирует один ПУ для обновления в ГИС ЖКХ.
     *
     * @return сформированный для обновления прибор учёта.
     */
    private ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate getMeteringDeviceForUpdate() {
        ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate device = new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate();
//        device.set
        return device;
    }

    /**
     * Метод, получает список ид ПУ в БД ГРАД, для дальнейшего архивирования ПУ.
     *
     * @param meterIdList    список ид ПУ в БД ГРАД.
     * @param connectionGRAD подключение к БД ГРАД.
     * @return готовый объект для отправки в ГИС ЖКХ.
     * @throws SQLException
     * @throws PreGISException
     */
    public java.util.List<ImportMeteringDeviceDataRequest.MeteringDevice> getMeteringDeviceForArchive(ArrayList<Integer> meterIdList, Connection connectionGRAD) throws SQLException, PreGISException {

        java.util.List<ImportMeteringDeviceDataRequest.MeteringDevice> deviceList = new ArrayList<>();

        for (Integer meterId : meterIdList) {

            String meterVersionGUID = getMeteringDeviceUniqueNumbersFromGrad(meterId, "METERVERSIONGUID", connectionGRAD);

            ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate device = new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate();
//            device.setUpdateAfterDevicesValues(new MeteringDeviceToUpdateAfterDevicesValuesType());
//            device.getUpdateAfterDevicesValues().;

            device.setMeteringDeviceVersionGUID(meterVersionGUID);
            device.setArchiveDevice(new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ArchiveDevice());
            device.getArchiveDevice().setArchivingReason(nsi.getNsiRef("21", "Ошибка"));
            setArchivingReasonToLocalBase(Integer.valueOf(nsi.getNsiRef("21", "Ошибка").getCode()), meterVersionGUID);  // в БД отметка

            ImportMeteringDeviceDataRequest.MeteringDevice meteringDevices = new ImportMeteringDeviceDataRequest.MeteringDevice();
            meteringDevices.setTransportGUID(OtherFormat.getRandomGUID());
            meteringDevices.setDeviceDataToUpdate(device);

            deviceList.add(meteringDevices);
        }
        return deviceList;
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
    public ArrayList<String[]> getExGisPu1(Integer houseId, Connection connection) throws SQLException {  // Connection error

        ArrayList<String[]> list = new ArrayList<>();
        return executorProcedure("{EXECUTE PROCEDURE EX_GIS_PU1(" + houseId + ")}",
                connection, resultSet1 -> {
                    while (resultSet1.next()) {
                        if (getAllData(resultSet1.getString(1))[ABON_ID_PU1] != null && !getAllData(resultSet1.getString(1))[ABON_ID_PU1].isEmpty()) {
                            list.add(getAllData(resultSet1.getString(1)));
                        } else {
                            LOGGER.debug("ПУ: " + resultSet1.getString(1) +
                                    " не имеет отличительных данных, не сможет быть загруженным в ГИС ЖКХ.");
                        }
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
    public void setMeteringDevices(ImportResult importResult, Connection connectionGrad) throws SQLException, FileNotFoundException, SOAPException, JAXBException {

        if (importResult.getCommonResult() != null && importResult.getCommonResult().size() > 0) {
            for (ImportResult.CommonResult result : importResult.getCommonResult()) {

                if (result.getError() == null || result.getError().size() == 0) {
                    setMeteringDevices(result.getUniqueNumber(), result.getGUID(),
                            result.getImportMeteringDevice().getMeteringDeviceVersionGUID(), result.getTransportGUID(), connectionGrad);
                } else {
                    answerProcessing.sendMessageToClient("TransportGUID: " + result.getTransportGUID());
                    answerProcessing.sendMessageToClient("Код ошибки: " + result.getError().get(0).getErrorCode());
                    answerProcessing.sendMessageToClient("Описание ошибки: " + result.getError().get(0).getDescription());
                    answerProcessing.sendMessageToClient("");
                }
            }
        } else {  // Возвращает не тот объект ответа.

            ru.gosuslugi.dom.schema.integration.base.ImportResult castResult = getImportResultLastFromDataBase();
            for (CommonResultType resultType : castResult.getCommonResult()) {

                if (resultType.getError() == null || resultType.getError().size() == 0) {
                    LOGGER.debug("Activ: base.ImportResult.");
//                Этот объект вместо getGUID содержит meteringVersionGUID.
                    setMeteringDevices(resultType.getUniqueNumber(), null, resultType.getGUID(),
                            resultType.getTransportGUID(), connectionGrad);
                } else {
                    answerProcessing.sendMessageToClient("TransportGUID: " + resultType.getTransportGUID());
                    answerProcessing.sendMessageToClient("Код ошибки: " + resultType.getError().get(0).getErrorCode());
                    answerProcessing.sendMessageToClient("Описание ошибки: " + resultType.getError().get(0).getDescription());
                    answerProcessing.sendMessageToClient("");
                }
            }
        }
    }

    /**
     * Метод, по транспортному идентификатору определяет отправленный счетчик и назначает ему полученные идентификаторы.
     *
     * @param meteringUniqueNumber уникальный реестровый номер.
     * @param meteringRootGUID     идентификатор ПУ в ГИС ЖКХ.
     * @param meteringVersionGUID  идентификатор версии ПУ.
     * @param transportGUID        транспортный идентификатор.
     * @param connectionGrad       подключение к БД ГРАД.
     * @throws SQLException
     */
    private void setMeteringDevices(String meteringUniqueNumber, String meteringRootGUID, String meteringVersionGUID, String transportGUID, Connection connectionGrad) throws SQLException {

        if (mapTransportMeteringDevice.containsKey(transportGUID)) {

            Integer abonId = null;
            ImportMeteringDeviceDataRequest.MeteringDevice device = null;
            Integer meterId = null;
            for (Map.Entry<Integer, ImportMeteringDeviceDataRequest.MeteringDevice> entry : mapTransportMeteringDevice.get(transportGUID).entrySet()) {

                if (entry.getValue() != null) {
                    abonId = entry.getKey();
                    device = entry.getValue();
                } else {
                    meterId = entry.getKey();
                }
            }
//            LOGGER.debug("abinId: " + abonId + " meterId: " + meterId);
//            LOGGER.debug("setMeteringDeviceUniqueNumbers");
//            Перенес в один метод, где проводится проверка
//            setMeteringDeviceUniqueNumbers(meterId, meteringVersionGUID, meteringRootGUID, connectionGrad); // в БД ГРАД.
//            LOGGER.debug("setMeteringDeviceToLocalBase");
            if (setMeteringDeviceToLocalBase(abonId, meterId, this.houseId, meteringUniqueNumber, meteringRootGUID, meteringVersionGUID, transportGUID, device, connectionGrad)) { // в локальную БД
                LOGGER.debug("Добавлен ПУ, abinId: " + abonId + " meterId: " + meterId);
                countAdded++;
//            answerProcessing.sendMessageToClient("");
                answerProcessing.sendMessageToClient("Дабавлен прибор учёта, Уникальный номер: " + meteringUniqueNumber + " идентификатор: " + meteringVersionGUID);
                answerProcessing.sendMessageToClient("");
            }

        } else {
//            answerProcessing.sendMessageToClient("");
            answerProcessing.sendMessageToClient("Прибор учёта, с транспортным номером: " + transportGUID + " не найден!");
            answerProcessing.sendMessageToClient("");
        }
    }

    /**
     * Метод, добавляет в локальную БД информацию о счетчиках.
     *
     * @param abonId               ид абонента в БД ГРАД.
     * @param houseId              ид дома в БД ГРАД.
     * @param meteringUniqueNumber уникальный реестровый номер.
     * @param meteringRootGUID     идентификатор ПУ в ГИС ЖКХ.
     * @param meteringVersionGUID  идентификатор версии ПУ.
     * @param transportGUID        транспортный идентификатор.
     * @param device               информация о счетчике.
     * @throws SQLException
     */
    private boolean setMeteringDeviceToLocalBase(Integer abonId, Integer meterId, Integer houseId, String meteringUniqueNumber,
                                                 String meteringRootGUID, String meteringVersionGUID, String transportGUID,
                                                 ImportMeteringDeviceDataRequest.MeteringDevice device, Connection connectionGRAD) throws SQLException {

        boolean state = false;

        String accountGUID;
        String premiseGUID;
//        String livingRoomGUID;

        String meteringDeviceNumber;
//        String meteringUniqueNumber;
//        String meteringRootGUID;
//        String meteringVersionGUID;
//        String transportGUID;
        MeteringDeviceBasicCharacteristicsType basic;

//        for (ImportResult.CommonResult result : importResult.getCommonResult()) {

//            meteringUniqueNumber = result.getUniqueNumber();
//            meteringRootGUID = result.getGUID();
//            meteringVersionGUID = result.getImportMeteringDevice().getMeteringDeviceVersionGUID();
//            transportGUID = result.getTransportGUID();

        if (device.getDeviceDataToCreate() != null) {

            basic = device.getDeviceDataToCreate().getBasicChatacteristicts();
            meteringDeviceNumber = basic.getMeteringDeviceNumber();

            if (basic.getLivingRoomDevice() != null) { // Комунальные комнаты - один счетчик много комнат
                accountGUID = basic.getLivingRoomDevice().getAccountGUID().get(0);
                for (String itemRoom : basic.getLivingRoomDevice().getLivingRoomGUID()) {
                    state = setMeteringDeviceToLocalBase(abonId, meterId, houseId, accountGUID, null, itemRoom, meteringDeviceNumber,
                            meteringUniqueNumber, meteringRootGUID, meteringVersionGUID, transportGUID, false, connectionGRAD);
                }
            } else if (basic.getResidentialPremiseDevice() != null) { // жилые помещения, одно помещение и может быть несколько л.счетов.
                premiseGUID = basic.getResidentialPremiseDevice().getPremiseGUID();
                for (String itemAccountGUID : basic.getResidentialPremiseDevice().getAccountGUID()) {
                    state = setMeteringDeviceToLocalBase(abonId, meterId, houseId, itemAccountGUID, premiseGUID, null, meteringDeviceNumber,
                            meteringUniqueNumber, meteringRootGUID, meteringVersionGUID, transportGUID, false, connectionGRAD);
                }
            } else if (basic.getNonResidentialPremiseDevice() != null) { // не жилые помещения
                premiseGUID = basic.getNonResidentialPremiseDevice().getPremiseGUID();
                for (String itemAccountGUID : basic.getResidentialPremiseDevice().getAccountGUID()) {
                    state = setMeteringDeviceToLocalBase(abonId, meterId, houseId, itemAccountGUID, premiseGUID, null, meteringDeviceNumber,
                            meteringUniqueNumber, meteringRootGUID, meteringVersionGUID, transportGUID, true, connectionGRAD);
                }
            } else if (basic.getCollectiveApartmentDevice() != null) { // Характеристики общеквартирного ПУ (для квартир коммунального заселения)
                premiseGUID = basic.getCollectiveApartmentDevice().getPremiseGUID();
                for (String itemAccountGUID : basic.getCollectiveApartmentDevice().getAccountGUID()) {
                    state = setMeteringDeviceToLocalBase(abonId, meterId, houseId, itemAccountGUID, premiseGUID, null, meteringDeviceNumber,
                            meteringUniqueNumber, meteringRootGUID, meteringVersionGUID, transportGUID, false, connectionGRAD);
                }
            } else if (basic.getApartmentHouseDevice() != null) {  // Тип ПУ Коллективный (общедомовой) или Индивидуальный ПУ в ЖД
                accountGUID = basic.getApartmentHouseDevice().getAccountGUID().get(0);
                state = setMeteringDeviceToLocalBase(abonId, meterId, houseId, accountGUID, null, null, meteringDeviceNumber,
                        meteringUniqueNumber, meteringRootGUID, meteringVersionGUID, transportGUID, false, connectionGRAD);
            }
        }
//        }
        return state;
    }

    /**
     * Метод, проверяет, что нет записи в таблице и добавляет идентификаторы во все БД данные о ПУ.
     *
     * @param abonId               ид абонента в БД ГРАД.
     * @param houseId              ид дома в БД ГРАД.
     * @param accountGUID          идентификатор ЛС.
     * @param premiseGUID          идентификатор жилого помещения.
     * @param livingRoomGUID       идентификатор комнаты.
     * @param meteringDeviceNumber Номер ПУ.
     * @param meteringUniqueNumber уникальный реестровый номер.
     * @param meteringRootGUID     идентификатор ПУ в ГИС ЖКХ.
     * @param meteringVersionGUID  идентификатор версии ПУ.
     * @param transportGUID        транспортный идентификатор.
     * @param isNonResitential     статус помещения true - нежилое помещение, false - жилое помищение.
     * @throws SQLException
     */
    private boolean setMeteringDeviceToLocalBase(Integer abonId, Integer meterId, Integer houseId, String accountGUID, String premiseGUID,
                                                 String livingRoomGUID, String meteringDeviceNumber, String meteringUniqueNumber,
                                                 String meteringRootGUID, String meteringVersionGUID, String transportGUID, boolean isNonResitential, Connection connectionGrad) throws SQLException {

        LOGGER.debug("abinId: " + abonId + " meterId: " + meterId + " houseId: " + houseId);
        LOGGER.debug(!getMeteringDeviceFromLocalBase(abonId, houseId, accountGUID, meteringVersionGUID));
        if (!getMeteringDeviceFromLocalBase(abonId, houseId, accountGUID, meteringVersionGUID)) {
            setMeteringDeviceUniqueNumbers(meterId, meteringVersionGUID, meteringRootGUID, connectionGrad); // в БД ГРАД.
            try (Connection connection = ConnectionDB.instance().getConnectionDB();
                 PreparedStatement pstm = connection.prepareStatement(
                         "INSERT INTO METERING_DEVICE_IDENTIFIERS(ABON_ID, METER_ID, HOUSE_ID, ACCOUNT_GUID, " +
                                 "PREMISE_GUID, LIVING_ROOM_GUID, METERING_DEVICE_NUMBER, METERING_UNIQUE_NUMBER, " +
                                 "METERING_ROOT_GUID, METERING_VERSION_GUID, TRANSPORT_GUID, NON_RESIDENTIAL_PREMISE_DEVICE) " +
                                 "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                pstm.setInt(1, abonId);
                pstm.setInt(2, meterId);
                pstm.setInt(3, houseId);
                pstm.setString(4, accountGUID);
                pstm.setString(5, premiseGUID);
                pstm.setString(6, livingRoomGUID);
                pstm.setString(7, meteringDeviceNumber);
                pstm.setString(8, meteringUniqueNumber);
                pstm.setString(9, meteringRootGUID);
                pstm.setString(10, meteringVersionGUID);
                pstm.setString(11, transportGUID);
                pstm.setBoolean(12, isNonResitential);
                pstm.executeUpdate();
            }
            return true;
        }
        return false;
    }

    /**
     * Метод, проверяет существует запись в БД или нет.
     *
     * @param abonId              ид абонента в БД ГРАД.
     * @param houseId             ид дома в БД ГРАД.
     * @param accountGUID         идентификатор ЛС.
     * @param meteringVersionGUID уникальный реестровый номер.
     * @return true - запись существует, false - запись не найдена.
     * @throws SQLException
     */
    private boolean getMeteringDeviceFromLocalBase(Integer abonId, Integer houseId, String accountGUID, String meteringVersionGUID) throws SQLException {

        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             PreparedStatement pstm = connection.prepareStatement(
                     "SELECT ABON_ID, HOUSE_ID, ACCOUNT_GUID, METERING_VERSION_GUID FROM METERING_DEVICE_IDENTIFIERS " +
                             "WHERE ABON_ID = ? AND HOUSE_ID = ? AND ACCOUNT_GUID = ? AND METERING_VERSION_GUID = ?")) {
            pstm.setInt(1, abonId);
            pstm.setInt(2, houseId);
            pstm.setString(3, accountGUID);
            pstm.setString(4, meteringVersionGUID);
            ResultSet rs = pstm.executeQuery();

            return rs.next();
        }
    }

    /**
     * Метод, идентификатору ПУ в ГИС ЖКХ находит meterId с помощью которого можно занести или получить данные в БД ГРАД.
     *
     * @param meteringRootGUID идентификатор ПУ в ГИС ЖКХ.
     * @return meterId ид ПУ в БД ГРАД.
     * @throws SQLException
     */
    private Integer getMeterIdFromLocalBaseUseMeteringRootGUID(String meteringRootGUID) throws SQLException {

        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             PreparedStatement pstm = connection.prepareStatement(
                     "SELECT METER_ID FROM METERING_DEVICE_IDENTIFIERS WHERE METERING_ROOT_GUID = ?")) {
            pstm.setString(1, meteringRootGUID);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return Integer.valueOf(rs.getString(1));
            }
        }
        return null;
    }

    /**
     * Метод, по уникальному реестровому номеру находит meterId с помощью которого можно занести или получить данные в БД ГРАД.
     *
     * @param meteringVersionGUID уникальный реестровый номер.
     * @return meterId ид ПУ в БД ГРАД.
     * @throws SQLException
     */
    private Integer getMeterIdFromLocalBaseUseMeteringVersionGUID(String meteringVersionGUID) throws SQLException {

        Integer meterId = null;
        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             PreparedStatement pstm = connection.prepareStatement(
                     "SELECT METER_ID FROM METERING_DEVICE_IDENTIFIERS WHERE METERING_VERSION_GUID = ?")) {
            pstm.setString(1, meteringVersionGUID);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                meterId = Integer.valueOf(rs.getString(1));
            }
            rs.close();
            return meterId;
        }
    }

    /**
     * Метод, по указанному "MeteringDeviceVersionGUID", помечает ПУ как архивный и указаывает ему причину архивирования.
     *
     * @param nsiCodeElement      код причины архивирования ПУ.
     * @param meteringVersionGUID уникальный реестровый номер ПУ в ГИС ЖХК.
     * @throws SQLException
     */
    public void setArchivingReasonToLocalBase(Integer nsiCodeElement, String meteringVersionGUID) throws SQLException {

        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             PreparedStatement ps = connection.prepareStatement("UPDATE METERING_DEVICE_IDENTIFIERS " +
                     "SET ARCHIVING_REASON_CODE = ? WHERE METERING_VERSION_GUID = ?")) {
            if (!isArchivingDevice(meteringVersionGUID, connection)) {
                ps.setInt(1, nsiCodeElement);
                ps.setString(2, meteringVersionGUID);
                ps.executeUpdate();
            }
        }
    }

    /**
     * Метод, проверяет по указанному "MeteringVersionGUID", является ПУ архивным или нет.
     *
     * @param meteringVersionGUID уникальный реестровый номер ПУ в ГИС ЖХК.
     * @param connectionLocalBase подключение к локальной БД.
     * @return true - если ПУ архивное, false - если ПУ не является архивным.
     * @throws SQLException
     */
    private boolean isArchivingDevice(String meteringVersionGUID, Connection connectionLocalBase) throws SQLException {

        boolean isArchive;

        try (Connection connection = connectionLocalBase;
             PreparedStatement pstm = connection.prepareStatement(
                     "SELECT ARCHIVING_REASON_CODE FROM METERING_DEVICE_IDENTIFIERS " +
                             "WHERE METERING_VERSION_GUID = ? AND ARCHIVING_REASON_CODE IS NOT NULL")) {
            pstm.setString(1, meteringVersionGUID);
            ResultSet rs = pstm.executeQuery();

            isArchive = rs.next();
            rs.close();
        }
        return isArchive;
    }

    /**
     * Метод, получает дату последней передачи показаний ПУ.
     * @param meteringVersionGUID уникальный реестровый номер ПУ в ГИС ЖХК.
     * @return дата последней передачи показаний ПУ.
     * @throws SQLException
     */
    private java.util.Date getDeviceValueRequest(String meteringVersionGUID) throws SQLException {

        java.util.Date date = null;

        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             PreparedStatement pstm = connection.prepareStatement(
                     "SELECT VALUE_REQUEST FROM METERING_DEVICE_IDENTIFIERS " +
                             "WHERE METERING_VERSION_GUID = ? AND VALUE_REQUEST IS NOT NULL")) {
            pstm.setString(1, meteringVersionGUID);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) date = rs.getDate(1);
                rs.close();
        }
        return date;
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
        return executorProcedure("{EXECUTE PROCEDURE EX_GIS04(" + houseId + ")}",
                connectionGrad, resultSet1 -> {
                    while (resultSet1.next())
                        list.add(Integer.valueOf(getAllData(resultSet1.getString(1))[7]));
                    return list;
                });
    }

    /*
    execute procedure EX_GIS_ID(:ABON_ID, :BUILDING_ID, :METER_ID, :HOUSEUNIQNUM, :ACCOUNTGUID, :ACCOUNTUNIQNUM,
    :PREMISESGUID, :PREMISESUNIQNUM, :LIVINGROOMGUID, :ROOMUNIQNUMBER, :METERVERSIONGUID, :METERROOTGUID, :PARAM)
    returning_values :RESULT                                                                                                                                                                                                                                                               
    Входящий параметр :PARAM - это наименование идентификатора, может принимать значения HOUSEUNIQNUM, ACCOUNTGUID,
    ACCOUNTUNIQNUM, PREMISESGUID, PREMISESUNIQNUM, LIVINGROOMGUID, ROOMUNIQNUMBER, METERVERSIONGUID, METERROOTGUID                                                                                                                                                                                                                                .
     */

    /**
     * Метод, добавляет идентификаторы ПУ в БД ГРАД.
     *
     * @param meterId                   ид ПУ в БД ГРАД.
     * @param meteringDeviceVersionGUID Идентификатор версии ПУ.
     * @param meteringDeviceRootGUID    Идентификатор ПУ в ГИС ЖКХ.
     * @param connectionGrad            подключение к БД ГРАД.
     * @throws SQLException
     */
    private void setMeteringDeviceUniqueNumbers(Integer meterId, String meteringDeviceVersionGUID, String meteringDeviceRootGUID, Connection connectionGrad) throws SQLException {
//        Могут принять null объект из-за этого лучше так:
//        String tempMeteringDeviceVersionGUID = accountGRADDAO.getBuildingIdentifiersFromBase(abonId, "METERVERSIONGUID", connectionGrad);
//        String tempMeteringDeviceRootGUID = accountGRADDAO.getBuildingIdentifiersFromBase(abonId, "METERROOTGUID", connectionGrad);
//
//        if (meteringDeviceRootGUID.equals(tempMeteringDeviceRootGUID)) {
//            tempMeteringDeviceRootGUID = null;
//        } else {
//            tempMeteringDeviceRootGUID = meteringDeviceRootGUID;
//        }
//        if (meteringDeviceVersionGUID.equals(tempMeteringDeviceVersionGUID)) {
//            tempMeteringDeviceVersionGUID = null;
//        } else {
//            tempMeteringDeviceVersionGUID = meteringDeviceVersionGUID;
//        }

//        if (tempMeteringDeviceRootGUID != null || tempMeteringDeviceVersionGUID != null) {
        try (CallableStatement call = connectionGrad.prepareCall("{EXECUTE PROCEDURE EX_GIS_ID(NULL, NULL , ?, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ?, ?, NULL)}")) {
            call.setInt(1, meterId);
            call.setString(2, meteringDeviceVersionGUID);
            call.setString(3, meteringDeviceRootGUID);
            call.executeQuery();
        }
//        }
    }

    /**
     * Метод, принимает ид ПУ в БД ГРАД и название идентификатора в БД ГРАД ( METERVERSIONGUID или METERROOTGUID ) и возвращает их значение.
     *
     * @param meterId        ид ПУ в БД ГРАД.
     * @param identifier     требуемый идентификатор METERVERSIONGUID или METERROOTGUID.
     * @param connectionGRAD подключение к БД ГРАД.
     * @return найденный идентификатор в БД ГРАД или null.
     * @throws SQLException
     */
    private String getMeteringDeviceUniqueNumbersFromGrad(Integer meterId, String identifier, Connection connectionGRAD) throws SQLException {

        String answer;

        try (CallableStatement call = connectionGRAD.prepareCall("{EXECUTE PROCEDURE EX_GIS_ID(NULL, NULL , ?, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ?)}")) {
            call.setInt(1, meterId);
            call.setString(2, identifier);
            ResultSet rs = call.executeQuery();
            rs.next();
            answer = rs.getString(1);
        }
        return answer;
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

        try (CallableStatement call = connection.prepareCall(sqlRequest)) {
//        CallableStatement call = connection.prepareCall(sqlRequest);
            LOGGER.debug(sqlRequest);
            call.executeQuery();
            ResultSet result = call.getResultSet();
            T value = handler.handle(result);
            result.close();

            return value;
        }
    }

    public int getCountAll() {
        return countAll;
    }

    public int getCountAdded() {
        return countAdded;
    }

    public int getCountUpdate() {
        return countUpdate;
    }

    /**
     * Метод, получает последнее сообщение из БД преобразует его в ImportResult.
     *
     * @return полученное из БД сообщение.
     * @throws JAXBException
     * @throws FileNotFoundException
     * @throws SOAPException
     * @throws SQLException
     */
    private ru.gosuslugi.dom.schema.integration.base.ImportResult getImportResultLastFromDataBase() throws JAXBException, FileNotFoundException, SOAPException, SQLException {
        MessageInBase messageInBase = new MessageInBase();
        JAXBContext jc = JAXBContext.newInstance(ru.gosuslugi.dom.schema.integration.base.ImportResult.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        ru.gosuslugi.dom.schema.integration.base.ImportResult result =
                (ru.gosuslugi.dom.schema.integration.base.ImportResult) unmarshaller.unmarshal(
                        messageInBase.getLastSOAPFromBase().getSOAPBody().extractContentAsDocument());
        return result;
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
