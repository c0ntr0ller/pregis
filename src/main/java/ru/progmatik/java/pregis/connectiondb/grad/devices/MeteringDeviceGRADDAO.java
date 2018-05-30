package ru.progmatik.java.pregis.connectiondb.grad.devices;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.house_management.*;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.account.AccountGRADDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.message.MessageExecutor;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSI;
import ru.progmatik.java.pregis.exception.PreGISArgumentNotFoundFromBaseException;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.model.MeteringDeviceID;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.SOAPException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Класс, получает данные из  БД ГРАД и формирует объект пригодный для ГИС ЖКХ.
 * Для каждого дома, нужно создавать по новой.
 * Что бы из БД каждый раз не запрашивать данные беру их один раз и храню в массивах.
 */
public class MeteringDeviceGRADDAO{

    private static final Logger LOGGER = Logger.getLogger(MeteringDeviceGRADDAO.class);


    private static final int METER_ID_PU1 = 29;
    private static final int METER_ID_PU2 = 4;
    private static final int ABON_ID_PU1 = 30;
    private static final int ABON_ID_PU2 = 5;
    private static final int DEVICE_NUMBER = 1;
    private static final int TYPE_PU = 2;
    private static final int IS_MANUAL_MODE_METERING = 9;
    private static final int MUNICIPAL_RESOURCE = 14;
    private static final int METERING_VALUE = 16;
    private static final int INSTALLATION_DATE = 20;
    private static final int COMMISSIONING_DATE = 21;
    private static final int VERIFICATION_DATE = 22;
    private static final int VERIFICATION_INTERVAL = 24;
    private static final int METERROOTGUID = 31;
    private static final int METERVERSIONGUID = 32;

    private final SimpleDateFormat sDate = new SimpleDateFormat("dd.MM.yyyy");
    private final SimpleDateFormat dateFromSQL = new SimpleDateFormat("yyyy-MM-dd");
    private final AnswerProcessing answerProcessing;
    private final AccountGRADDAO accountGRADDAO;
//    private final MeteringDevicesDataLocalDBDAO devicesDataLocalDBDAO;
    private final ReferenceNSI nsi;
//    private final Integer houseId;
    // private final LinkedHashMap<String, LinkedHashMap<Integer, ImportMeteringDeviceDataRequest.MeteringDevice>> mapTransportMeteringDevice;
    private ArrayList<String[]> exGisIpuIndList;
    private LinkedHashMap<Integer, String[]> exGisIpuIndMap;
    private ArrayList<String> exGisPu2List;
    private ArrayList<Integer> allResidentialPremiseFromGrad;
    private ArrayList<ImportMeteringDeviceDataRequest.MeteringDevice> devicesForUpdateList = new ArrayList<>();
    private LinkedHashMap<String, ImportMeteringDeviceDataRequest.MeteringDevice> deviceForArchiveAndCreateMap = new LinkedHashMap<>();
    private int countAll = 0;
    private int countAdded = 0;
    private int countSeted = 0;
    private int countUpdate = 0;
    private int errorState = 1;

    public MeteringDeviceGRADDAO(AnswerProcessing answerProcessing, Integer houseId) throws SQLException, ParseException, PreGISException {
        this.answerProcessing = answerProcessing;
//        this.houseId = houseId;
        // mapTransportMeteringDevice = new LinkedHashMap<>(); // хранит TransportGUID, по которому можно идентифицировать каждый счетчик.
        accountGRADDAO = new AccountGRADDAO(answerProcessing);
        // devicesDataLocalDBDAO = new MeteringDevicesDataLocalDBDAO(answerProcessing);
        nsi = new ReferenceNSI(answerProcessing);
    }

    /**
     * Метод получает все ПУ из Град и возвращает их в виде мапы ИД ПУ- ПУ
     * @return связанный список ИД ПУ-ПУ
     * @throws SQLException
     */
    public HashMap<MeteringDeviceID, ImportMeteringDeviceDataRequest.MeteringDevice> getMeteringDevicesFromGrad(int houseGradId)
            throws SQLException, ParseException, PreGISException {

        final HashMap<MeteringDeviceID, ImportMeteringDeviceDataRequest.MeteringDevice> meteringDeviceMap = new HashMap<>();

        try(Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()){
            //
            final ArrayList<String[]> exGisPu1 = getExGisPu1(houseGradId, connectionGRAD);

            for(String[] exGisPu1Element: exGisPu1) {

                ImportMeteringDeviceDataRequest.MeteringDevice meteringDevice = new ImportMeteringDeviceDataRequest.MeteringDevice();
                meteringDevice.setTransportGUID(OtherFormat.getRandomGUID());

                try {
                    meteringDevice.setDeviceDataToCreate(getMeteringDeviceForCreateElement(houseGradId, exGisPu1Element, connectionGRAD));
                } catch (PreGISArgumentNotFoundFromBaseException e) {
                    if(answerProcessing != null) {answerProcessing.sendMessageToClient("");}
                    if(answerProcessing != null) {answerProcessing.sendInformationToClientAndLog(e.getMessage(), LOGGER);}
                    errorState = 0;
                }

                if(meteringDevice.getDeviceDataToCreate() != null) {
                    meteringDeviceMap.put(new MeteringDeviceID(exGisPu1Element[METERROOTGUID],
                                    exGisPu1Element[METERVERSIONGUID],
                                    Integer.valueOf(exGisPu1Element[METER_ID_PU1])),
                            meteringDevice);
                }
            }
        }
        return meteringDeviceMap;
    }


    /**
     * Метод, формирует все ПУ для создания в ГИС ЖКХ.
     *
     * @return новые ПУ для ГИС ЖКХ.
     */
    @Deprecated
    public java.util.List<ImportMeteringDeviceDataRequest.MeteringDevice> getMeteringDevicesForCreate(Connection connectionGRAD) throws SQLException, PreGISException, ParseException {
        countAll = 0;

        java.util.ArrayList<ImportMeteringDeviceDataRequest.MeteringDevice> meteringDeviceList = new ArrayList<>();
//
//        ArrayList<String[]> exGisPu1 = getExGisPu1(houseId, connectionGRAD);
//
//        for (String[] exGisPu1Element : exGisPu1) {
//            countAll++;
//            if(answerProcessing != null) {answerProcessing.clearLabelForText();}
//
//            ImportMeteringDeviceDataRequest.MeteringDevice meteringDevice = new ImportMeteringDeviceDataRequest.MeteringDevice();
//            meteringDevice.setTransportGUID(OtherFormat.getRandomGUID());
//
//            try {
//                meteringDevice.setDeviceDataToCreate(getMeteringDeviceForCreateElement(houseId, exGisPu1Element, connectionGRAD));
//            } catch (PreGISArgumentNotFoundFromBaseException e) {
//                if(answerProcessing != null) {answerProcessing.sendMessageToClient("");}
//                if(answerProcessing != null) {answerProcessing.sendInformationToClientAndLog(e.getMessage(), LOGGER);}
//                errorState = 0;
//                continue;
//            }
//
//            mapTransportMeteringDevice.put(meteringDevice.getTransportGUID(), new LinkedHashMap<>());
//            mapTransportMeteringDevice.get(meteringDevice.getTransportGUID()).put(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), meteringDevice);
//            mapTransportMeteringDevice.get(meteringDevice.getTransportGUID()).put(Integer.valueOf(exGisPu1Element[METER_ID_PU1]), null);
//            // debug block
////            String meterStr = "";
////            meterStr = meterStr + "DEVICE_NUMBER:" + DEVICE_NUMBER + ":" + exGisPu1Element[DEVICE_NUMBER]+ System.lineSeparator();
////            meterStr = meterStr + "INSTALLATION_DATE:" + INSTALLATION_DATE + ":" + exGisPu1Element[INSTALLATION_DATE]+ System.lineSeparator();
////            meterStr = meterStr + "COMMISSIONING_DATE:" + COMMISSIONING_DATE + ":" + exGisPu1Element[COMMISSIONING_DATE]+ System.lineSeparator();
////            meterStr = meterStr + "IS_MANUAL_MODE_METERING:" + IS_MANUAL_MODE_METERING + ":" + exGisPu1Element[IS_MANUAL_MODE_METERING]+ System.lineSeparator();
////            meterStr = meterStr + "VERIFICATION_DATE:" + VERIFICATION_DATE + ":" + exGisPu1Element[VERIFICATION_DATE]+ System.lineSeparator();
////            meterStr = meterStr + "VERIFICATION_INTERVAL:" + VERIFICATION_INTERVAL + ":" + exGisPu1Element[VERIFICATION_INTERVAL]+ System.lineSeparator();
////            meterStr = meterStr + "20:20:" + exGisPu1Element[20]+ System.lineSeparator();
////            meterStr = meterStr + "TYPE_PU:" + TYPE_PU + ":" + exGisPu1Element[TYPE_PU] + System.lineSeparator();
////            meterStr = meterStr + "6:6:"+ exGisPu1Element[6] + System.lineSeparator();
////            meterStr = meterStr + "ABON_ID_PU1:" + ABON_ID_PU1 + ":" + exGisPu1Element[ABON_ID_PU1] + System.lineSeparator();
////            meterStr = meterStr + "METER_ID_PU1:" + METER_ID_PU1 + ":" + exGisPu1Element[METER_ID_PU1] + System.lineSeparator();
////            meterStr = meterStr + "METER_ID_PU2:" + METER_ID_PU2 + ":" + exGisPu1Element[METER_ID_PU2] + System.lineSeparator();
////            meterStr = meterStr + "----------------------" + System.lineSeparator();
////            LOGGER.info(meterStr);
//            // -----------------
//
//            String rootGUID = getMeteringDeviceUniqueNumbersFromGrad(Integer.valueOf(exGisPu1Element[METER_ID_PU1]), "METERROOTGUID", connectionGRAD);
////            if (rootGUID == null || devicesDataLocalDBDAO.isArchivingDeviceByRootGUID(rootGUID)) {
////                meteringDeviceList.add(meteringDevice);
////                if (answerProcessing != null) {
////                    answerProcessing.sendMessageToClient("");
////                    answerProcessing.sendMessageToClient("Добавлен прибор учёта для выгрузки в ГИС ЖКХ:");
////                    answerProcessing.sendMessageToClient("идентификатор ПУ в Граде: " + exGisPu1Element[METER_ID_PU1] + ",\n" +
////                            "идентификатор абонента в Граде: " + exGisPu1Element[ABON_ID_PU1]);
////                }
//////                LOGGER.info("ПУ добавлен для выгрузки meterId: " + exGisPu1Element[METER_ID_PU1] +
//////                        " AbonId: " + exGisPu1Element[ABON_ID_PU1]);
////            }
//        }
//        for (ImportMeteringDeviceDataRequest.MeteringDevice deviceForUpdate : devicesForUpdateList) {
//            meteringDeviceList.add(deviceForUpdate);
////            LOGGER.info("ПУ добавлен для обновления в ГИС ЖКХ: " + deviceForUpdate.getDeviceDataToUpdate().getMeteringDeviceVersionGUID());
//        }

        return meteringDeviceList;
    }

    public int getCountAll() {
        return countAll;
    }

    public int getCountAdded() {
        return countAdded;
    }

    public int getErrorState() {
        return errorState;
    }

    public int getCountUpdate() {
        return countUpdate;
    }

    /**
     * Метод, возвращает количество записей готовых для пересоздания.
     *
     * @return количество записей готовых для пересоздания.
     */
    public int getCountRecreateMeteringDevice() {
        return devicesForUpdateList.size();
    }

    /**
     * Метод, возвращает все ПУ, которые не удалось обновить в ГИС ЖКХ. Они предназначены для дальнейшего архивирования.
     *
     * @return map, ключ - MeteringDeviceVersionGUID, значение - готовое устройство для создания.
     */
    public LinkedHashMap<String, ImportMeteringDeviceDataRequest.MeteringDevice> getDeviceForArchiveAndCreateMap() {

        LinkedHashMap<String, ImportMeteringDeviceDataRequest.MeteringDevice> outMap = new LinkedHashMap<>();

        for (Map.Entry<String, ImportMeteringDeviceDataRequest.MeteringDevice> entry : deviceForArchiveAndCreateMap.entrySet()) {
            for (ImportMeteringDeviceDataRequest.MeteringDevice deviceUpdate : devicesForUpdateList) {
                if (entry.getKey().equalsIgnoreCase(deviceUpdate.getTransportGUID())) {
                    outMap.put(deviceUpdate.getDeviceDataToUpdate().getMeteringDeviceVersionGUID(), entry.getValue());
                }
            }
        }
        return outMap;
    }

    /**
     * Метод, формирует один прибор учёта для  создания в ГИС ЖКХ.
     *
     * @param exGisPu1Element массив данных полученный из БД.
     * @return сформированные прибор учёта.
     */
    private MeteringDeviceFullInformationType getMeteringDeviceForCreateElement(
            Integer houseId, String[] exGisPu1Element, Connection connectionGrad) throws SQLException, PreGISException, ParseException, PreGISArgumentNotFoundFromBaseException {

        // String tmpStr = Arrays.toString(exGisPu1Element);
        // if(answerProcessing != null) {answerProcessing.sendMessageToClient("exGisPu1Element.toString(): " + tmpStr);}
//        LinkedHashMap<Integer, String[]> exGisIpuIndMap = getExGisIpuIndMap(houseId, connectionGrad);
        if(exGisPu1Element[METERING_VALUE].isEmpty()){
            throw new PreGISException("Не заданы показания установки для прибора ИД " + exGisPu1Element[METER_ID_PU1]);
        }

        MeteringDeviceFullInformationType device = new MeteringDeviceFullInformationType();

        device.setBasicChatacteristicts(getBasicCharacteristics(houseId, exGisPu1Element, connectionGrad));

        // новая добавка, означает что счетчики каскадируются (как я понял из документации). У нас таких нет, поэтому жестко везде прописываем true
        device.setNotLinkedWithMetering(true);

        if (exGisPu1Element[MUNICIPAL_RESOURCE].equalsIgnoreCase("Электрическая энергия")) { // Если счетчик электричества

            device.setMunicipalResourceEnergy(new MunicipalResourceElectricType());
//            device.setElectricMeteringDevice(new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate.ElectricMeteringDevice());

//            device.getElectricMeteringDevice().setBasicChatacteristicts(getBasicCharacteristics(houseId, exGisPu1Element, connectionGrad));
//            Базовое показание T1
            device.getMunicipalResourceEnergy().setMeteringValueT1(new BigDecimal(exGisPu1Element[METERING_VALUE]).setScale(7, BigDecimal.ROUND_DOWN).toString());
//            Базовое показание T2, не обязательно к заполнению
            if (exGisPu1Element[METERING_VALUE + 1] != null) {
                device.getMunicipalResourceEnergy().setMeteringValueT2(new BigDecimal(exGisPu1Element[METERING_VALUE + 1]).setScale(7, BigDecimal.ROUND_DOWN).toString());
//            } else {
//                device.getMunicipalResourceEnergy().setMeteringValueT2(new BigDecimal("0").setScale(4, BigDecimal.ROUND_DOWN));
            }
//            Базовое показание T3. В зависимости от количества заданных при создании базовых значений ПУ определяется его тип по количеству тарифов. Не обязательно к заполнению
            if (exGisPu1Element[METERING_VALUE + 2] != null) {
                device.getMunicipalResourceEnergy().setMeteringValueT3(new BigDecimal(exGisPu1Element[METERING_VALUE + 2]).setScale(7, BigDecimal.ROUND_DOWN).toString());
//            } else {
//                device.getMunicipalResourceEnergy().setMeteringValueT3(new BigDecimal("0").setScale(4, BigDecimal.ROUND_DOWN));
            }
//            Время и дата снятия показания
//            device.getMunicipalResourceEnergy().setReadingsSource(); // Кем внесено не обязательно к заполнению

            if (exGisPu1Element[METERING_VALUE + 3] != null) {  // Кэффициент трансформации
                device.getMunicipalResourceEnergy().setTransformationRatio(new BigDecimal(exGisPu1Element[METERING_VALUE + 3]).setScale(7, BigDecimal.ROUND_DOWN));
//            } else {
//                device.getMunicipalResourceEnergy().setTransformationRatio(new BigDecimal("0").setScale(4, BigDecimal.ROUND_DOWN));
//                device.getMunicipalResourceEnergy().setTransformationRatio(); // Кэффициент трансформации
            }

//            c версии 9.0.1.3 убрали
//            device.getMunicipalResourceEnergy().setReadoutDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisIpuIndMap.get(Integer.valueOf(exGisPu1Element[METER_ID_PU1]))[4])));  // Время и дата снятия показания, можно попросить процедуру с которой брать показания и дату, вот её и можно занести
//            device.getMunicipalResourceEnergy().setMunicipalResource(nsi.getNsiRef("2", exGisPu1Element[8])); // Коммунальный ресурс, берет из справочника 2

        } else { // Характеристики однотарифных ПУ
//            device.setOneRateMeteringDevice(new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate.OneRateMeteringDevice());
//            device.getOneRateMeteringDevice().setBasicChatacteristicts(getBasicCharacteristics(houseId, exGisPu1Element, connectionGrad));
//            Базовое показание
            MeteringDeviceFullInformationType.MunicipalResourceNotEnergy nonElectric = new MeteringDeviceFullInformationType.MunicipalResourceNotEnergy();

            nonElectric.setMeteringValue(new BigDecimal(exGisPu1Element[METERING_VALUE]).setScale(7, BigDecimal.ROUND_DOWN).toString());
            nonElectric.setMunicipalResource(nsi.getNsiRef("2", exGisPu1Element[MUNICIPAL_RESOURCE]));
//            nonElectric.setReadingsSource(); // Кем внесено не обязательно к заполнению
//            c версии 9.0.1.3 убрали
//            device.getOneRateMeteringDevice().setReadoutDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisIpuIndMap.get(Integer.valueOf(exGisPu1Element[METER_ID_PU1]))[4])));  // Время и дата снятия показания, можно попросить процедуру с которой брать показания и дату, вот её и можно занести
//            device.getOneRateMeteringDevice().setMunicipalResource(nsi.getNsiRef("2", exGisPu1Element[8])); // Коммунальный ресурс, берет из справочника 2

            device.getMunicipalResourceNotEnergy().add(nonElectric);

        }

        return device;
    }

    private MeteringDeviceBasicCharacteristicsType getBasicCharacteristics(Integer houseId, String[] exGisPu1Element, Connection connectionGrad) throws ParseException, SQLException, PreGISException, PreGISArgumentNotFoundFromBaseException {

        MeteringDeviceBasicCharacteristicsType basicCharacteristics = new MeteringDeviceBasicCharacteristicsType();
//            Номер ПУ
        basicCharacteristics.setMeteringDeviceNumber(exGisPu1Element[DEVICE_NUMBER]);

//        Обработка Марка ПУ и Модель ПУ, в ГРАД всё хранится в одной строке
        if (exGisPu1Element[4] == null) {
            try {
                String[] ModelAndStamp = parsePU(exGisPu1Element[3]);
//                  Марка ПУ обязательный
                basicCharacteristics.setMeteringDeviceStamp(ModelAndStamp[1]);

//                   Модель ПУ, обязательный, если из БД приходит пустое значение - ставим расходомер
                basicCharacteristics.setMeteringDeviceModel(ModelAndStamp[0].equals("") ? "расходомер" : ModelAndStamp[0]);
            } catch (NullPointerException e) {
//                  Марка ПУ
                basicCharacteristics.setMeteringDeviceStamp(exGisPu1Element[3]);

//                  Модель ПУ, обязательный, из БД приходит пустое значение
                basicCharacteristics.setMeteringDeviceModel(exGisPu1Element[3]);
            }
        } else {
//            Марка ПУ
            basicCharacteristics.setMeteringDeviceStamp(exGisPu1Element[3]);

//            Модель ПУ, обязательный, из БД приходит пустое значение
            basicCharacteristics.setMeteringDeviceModel(exGisPu1Element[4]);
        }

//            Дата установки
        if (exGisPu1Element[INSTALLATION_DATE] != null)
            basicCharacteristics.setInstallationDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisPu1Element[INSTALLATION_DATE])));

//            Дата ввода в эксплуатацию
        basicCharacteristics.setCommissioningDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisPu1Element[COMMISSIONING_DATE])));

//            Внесение показаний осуществляется в ручном режиме
//        в 9.0 формах стало "Наличие возможности дистанционного снятия показаний"
        if ("Да".equalsIgnoreCase(exGisPu1Element[IS_MANUAL_MODE_METERING])) {
            basicCharacteristics.setRemoteMeteringMode(true);
        }

//          Характеристики поверки  Дата первичной поверки
//        в 9.0 поменяли на "Дата последней поверки", поле стало не обязательным
// стал необязательным        if (exGisPu1Element[VERIFICATION_DATE] != null && System.currentTimeMillis() > dateFromSQL.parse(exGisPu1Element[VERIFICATION_DATE]).getTime()) {
//            basicCharacteristics.setFirstVerificationDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisPu1Element[VERIFICATION_DATE])));
//        } else if (exGisPu1Element[VERIFICATION_DATE] == null) { // ГИС ЖКХ выдаёт ошибку, если не указана дата, хотя не обязательна
////            Берем дату ввода в эксплуатацию
//            basicCharacteristics.setFirstVerificationDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisPu1Element[COMMISSIONING_DATE])));
//        }
//            Межповерочный интервал (НСИ 16) стал необязательным
// стал необязательным        basicCharacteristics.setVerificationInterval(nsi.getNsiRef("16", exGisPu1Element[VERIFICATION_INTERVAL].split(" ")[0]));

//        Дата опломбирования ПУ заводом-изготовителем (обязательно для заполнения при импорте), обязательное, ГРАД возвращает путое значение
        if (exGisPu1Element[20] != null && System.currentTimeMillis() > dateFromSQL.parse(exGisPu1Element[20]).getTime()) {
            basicCharacteristics.setFactorySealDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisPu1Element[20])));
        }

//            Характеристики ИПУ жилого помещения (значение справочника "Тип прибора учета" = индивидуальный)
//            Характеристики ИПУ нежилого помещения (значение справочника "Тип прибора учета" = индивидуальный)
        try {
            if (exGisPu1Element[TYPE_PU].equalsIgnoreCase("Индивидуальный") && exGisPu1Element[6] != null) {
                if (isResidentialPremiseGrad(houseId, Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), connectionGrad)) { // если помещение не является жилым, значит оно нежилое.
                    basicCharacteristics.setResidentialPremiseDevice(new MeteringDeviceBasicCharacteristicsType.ResidentialPremiseDevice());
                    basicCharacteristics.getResidentialPremiseDevice().setPremiseGUID(accountGRADDAO.getBuildingIdentifiersFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), "PREMISESGUID", connectionGrad));
                    basicCharacteristics.getResidentialPremiseDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBaseWithException(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), connectionGrad));
                } else {
                    String accountGUIDFromGrad = accountGRADDAO.getAccountGUIDFromBaseWithException(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), connectionGrad);
                    String premiseGUIDFromGrad = accountGRADDAO.getBuildingIdentifiersFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), "PREMISESGUID", connectionGrad);
                    basicCharacteristics.setNonResidentialPremiseDevice(new MeteringDeviceBasicCharacteristicsType.NonResidentialPremiseDevice());
                    basicCharacteristics.getNonResidentialPremiseDevice().setPremiseGUID(premiseGUIDFromGrad);
                    basicCharacteristics.getNonResidentialPremiseDevice().getAccountGUID().add(accountGUIDFromGrad);
                    LOGGER.debug("ПУ помечен для нежилого помещения. MeterID: " + exGisPu1Element[METER_ID_PU1] +
                            " AccountGUID: " + accountGUIDFromGrad + " PremimiseGUID: " + premiseGUIDFromGrad);
                }

                // Признак общедомового ПУ (значение справочника "Тип прибора учета" = коллективный (общедомомвой))
            } else if (exGisPu1Element[TYPE_PU].equalsIgnoreCase("Коллективный (общедомовой)") && exGisPu1Element[5] != null) {
                answerProcessing.sendMessageToClient("Найден ПУ \"Коллективный (общедомовой)\" не удаётся обработать!");
//            basicCharacteristics.setCollectiveDevice(true);

//            basicCharacteristics.setCollectiveDevice(new MeteringDeviceBasicCharacteristicsType.CollectiveDevice());

//              Наличие датчиков давления
//              basicCharacteristics.setPressureSensor();

                // Информация о наличии возможности дистанционного снятия показаний ПУ
                // указанием наименования установленной системы (обязательно для заполнения,
                // если tns:ManualModeMetering = true, в противном случае поле не обрабатывается при импорте)
//            basicCharacteristics.getCollectiveDevice().setManualModeInformation();

//             Информация о наличии датчиков температуры с указанием их местоположения на узле учета
//             (обязательно для заполнения, если tns:TemperatureSensor = true,
//             в противном случае поле не обрабатывается при импорте)
//            basicCharacteristics.setTemperatureSensor();
//            basicCharacteristics.getCollectiveDevice().setTemperatureSensorInformation();

//              Информация о наличии датчиков давления с указанием их местоположения на узле учета
//              (обязательно для заполнения, если tns:PressureSensor = true,
//              в противном случае поле не обрабатывается при импорте)
//            basicCharacteristics.setPressureSensor();
//            basicCharacteristics.getCollectiveDevice().setPressureSensorInformation();

//            Электронный образ проекта узла учета
//            basicCharacteristics.getCollectiveDevice().getProjectRegistrationNode().add();

//            Электронный образ акта ввода узла учета в эксплуатацию
//            basicCharacteristics.getCollectiveDevice().getCertificate().add();

                // Характеристики общеквартирного ПУ (для квартир коммунального заселения) (значение справочника "Вид прибора учета" = Общий (квартирный))
            } else if (exGisPu1Element[TYPE_PU].equalsIgnoreCase("Общий (квартирный)") && exGisPu1Element[6] != null) {
                basicCharacteristics.setCollectiveApartmentDevice(new MeteringDeviceBasicCharacteristicsType.CollectiveApartmentDevice());
                basicCharacteristics.getCollectiveApartmentDevice().setPremiseGUID(accountGRADDAO.getBuildingIdentifiersFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), "PREMISESGUID", connectionGrad));
                basicCharacteristics.getCollectiveApartmentDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBaseWithException(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), connectionGrad));
                for (String arrayData : getOtherLsForPu(houseId, connectionGrad)) {
                    if (exGisPu1Element[METER_ID_PU1].equalsIgnoreCase(OtherFormat.getAllDataFromString(arrayData)[METER_ID_PU2])) {
                        basicCharacteristics.getCollectiveApartmentDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBaseWithException(Integer.valueOf(OtherFormat.getAllDataFromString(arrayData)[ABON_ID_PU2]), connectionGrad));
                    }
                }

                // если Характеристики ИПУ жилого дома (значение справочника "Тип прибора учета" = индивидуальный, тип дома = жилой дом)
            } else if (exGisPu1Element[TYPE_PU].equalsIgnoreCase("Индивидуальный") && exGisPu1Element[5] != null) {
                basicCharacteristics.setApartmentHouseDevice(new MeteringDeviceBasicCharacteristicsType.ApartmentHouseDevice());
                basicCharacteristics.getApartmentHouseDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBaseWithException(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), connectionGrad));
                for (String arrayData : getOtherLsForPu(houseId, connectionGrad)) {
                    if (exGisPu1Element[METER_ID_PU1].equalsIgnoreCase(OtherFormat.getAllDataFromString(arrayData)[METER_ID_PU2])) {
                        basicCharacteristics.getApartmentHouseDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBaseWithException(Integer.valueOf(OtherFormat.getAllDataFromString(arrayData)[ABON_ID_PU2]), connectionGrad));
                    }
                }

            } else if (exGisPu1Element[TYPE_PU].equalsIgnoreCase("Комнатный") && exGisPu1Element[7] != null) { // Характеристики комнатного ИПУ (значение справочника "Тип прибора учета" = индивидуальный)
                basicCharacteristics.setLivingRoomDevice(new MeteringDeviceBasicCharacteristicsType.LivingRoomDevice());
                basicCharacteristics.getLivingRoomDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBaseWithException(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), connectionGrad));
                basicCharacteristics.getLivingRoomDevice().getLivingRoomGUID().add(accountGRADDAO.getBuildingIdentifiersFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), "LIVINGROOMGUID", connectionGrad));
                for (String arrayData : getOtherLsForPu(houseId, connectionGrad)) {
                    if (exGisPu1Element[METER_ID_PU1].equalsIgnoreCase(OtherFormat.getAllDataFromString(arrayData)[METER_ID_PU2])) {
                        basicCharacteristics.getLivingRoomDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBaseWithException(Integer.valueOf(OtherFormat.getAllDataFromString(arrayData)[ABON_ID_PU2]), connectionGrad));
                    }
                }
            } else {
//                LOGGER.debug("ПУ MeterID: " + exGisPu1Element[METER_ID_PU1] +
//                        " AbonID: " + exGisPu1Element[ABON_ID_PU1] +
//                        " с именем: " + exGisPu1Element[DEVICE_NUMBER] + " не определен ни к одному из типов помещений!");
                throw new PreGISArgumentNotFoundFromBaseException("ПУ MeterID: " + exGisPu1Element[METER_ID_PU1] +
                        " AbonID: " + exGisPu1Element[ABON_ID_PU1] +
                        " с именем: " + exGisPu1Element[DEVICE_NUMBER] + " не определен ни к одному из типов помещений!");
            }
        } catch (NullPointerException e) {
//            answerProcessing.sendErrorToClient("getBasicCharacteristics()", "\"Синхронизация ПУ\" ", LOGGER, e);
            throw new PreGISArgumentNotFoundFromBaseException("ПУ MeterID: " + exGisPu1Element[METER_ID_PU1] +
                    " AbonID: " + exGisPu1Element[ABON_ID_PU1] +
                    " с именем: " + exGisPu1Element[DEVICE_NUMBER] + " не определен ни к одному из типов помещений!");
//            answerProcessing.sendInformationToClientAndLog();
//            errorState = 0;
        }
        return basicCharacteristics;
    }

    /**
     * метод бежит по списку ПУ для занесения в Град и вызывает для каждого активного метод setMeteringDeviceUniqueNumbers
     * @param devicesToUpdate
     * @throws SQLException
     * @throws PreGISException
     */
    public int updateGradMeteringDevices(Map<MeteringDeviceID, ExportMeteringDeviceDataResultType> devicesToUpdate) throws SQLException, PreGISException {
        if(devicesToUpdate == null || devicesToUpdate.isEmpty()) return 0;

        int countToGrad = 0;
        try(Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()){
            for (Map.Entry<MeteringDeviceID, ExportMeteringDeviceDataResultType> deviceEntry : devicesToUpdate.entrySet()) {
                if (deviceEntry.getValue().getStatusRootDoc().equals("Active")) {
                    setMeteringDeviceUniqueNumbers(deviceEntry.getKey().getMeterGradId(), deviceEntry.getValue().getMeteringDeviceVersionGUID(), deviceEntry.getValue().getMeteringDeviceRootGUID(), connectionGRAD);
                    countToGrad++;
                }
            }
        }finally {
            if(countToGrad > 0) answerProcessing.sendMessageToClient("Приборов обновлено в Град: " + countToGrad);
        }
        return countToGrad;
    }

    /**
     * Метод, проверяет и добавляет данные о ПУ, если их ещё нет.
     *
     * @param exportMeteringDeviceDataResultList полученный ответ на запрос о состоянии ПУ.
     * @param connectionGRAD                 подключение к БД ГРАД.
     * @throws SQLException
     */
    @Deprecated
    public void checkExportMeteringDevices(List<ExportMeteringDeviceDataResultType> exportMeteringDeviceDataResultList, Connection connectionGRAD) throws SQLException, PreGISException {

//        for (ExportMeteringDeviceDataResultType resultType : exportMeteringDeviceDataResultList) {
//            if (resultType.getStatusRootDoc().equals("Active")) {
//                checkMeteringDevice(resultType.getMeteringDeviceRootGUID(), resultType.getMeteringDeviceVersionGUID(),
//                        resultType.getBasicChatacteristicts(), connectionGRAD);
////  Пока откл, Проверка
//                checkBasicCharacteristicsForUpdate(resultType);
//            } else if (resultType.getStatusRootDoc().equals("Archival")) {
//                if (!devicesDataLocalDBDAO.isArchivingDeviceByRootGUID(resultType.getMeteringDeviceRootGUID())) {
//                    setArchivingReasonToLocalBaseByRootGUID(4, resultType);
//                }
//            }
//        }
    }

    /**
     * Метод, парсит строку на отдельные данные: Модель ПУ и Марку ПУ
     *
     * @param metering строка для разбора
     * @return массив 0 элемент - Модель ПУ, 1 элемент - Марка ПУ.
     * @throws NullPointerException
     */
    private String[] parsePU(String metering) throws NullPointerException {

        String mark = "";
        String model = "";

//        Парсим на модель и марку ПУ
//        Если название имеет имя меньшего размера начмная с цифр. Например: СВХ777111.99
        if (metering.split("\\d")[0].length() <= metering.split("\\s")[0].length() &&
                metering.split("\\d")[0].length() <= metering.split("-")[0].length()) {

            mark = metering.split("\\d")[0].trim();
            model = metering.substring(mark.length()).trim();

//            Если название имеет имя меньшего размера начмная с тире. Например: СВХ-15.45-10
        } else if (metering.split("-")[0].length() <= metering.split("\\s")[0].length() &&
                metering.split("-")[0].length() <= metering.split("\\d")[0].length()) {

            mark = metering.split("-")[0].trim();
            model = metering.substring(mark.length()).trim();
            if (model.charAt(0) == '-') { // если следующий разделитель тире. Например: СВХ - 15.45-10, нужно его убрать.
                model = model.substring(1).trim();
            }

        } else { // Остальные случаи. Например: СВХ - 15.45-10
            mark = metering.split("\\s")[0].trim();
            model = metering.substring(mark.length()).trim();
            if (model.charAt(0) == '-') { // если следующий разделитель тире. Например: СВХ - 15.45-10, нужно его убрать.
                model = model.substring(1).trim();
            }
        }

        return new String[]{model, mark};

//        System.out.println("Original: " + metering + " Marka: " + mark + " Model: " + model);
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
    @Deprecated
    private void checkMeteringDevice(String meteringDeviceRootGUID, String meteringDeviceVersionGUID,
                                     MeteringDeviceBasicCharacteristicsType meteringDeviceBasicCharacteristicsType,
                                     Connection connectionGRAD) throws SQLException, PreGISException {

//        Integer meterId;
//        String meteringRootGUIDGrad;
//        String meteringVersionGUIDGrad;
//
//        meterId = devicesDataLocalDBDAO.getMeterIdFromLocalBaseUseMeteringVersionGUID(meteringDeviceVersionGUID);
//        if (meterId == null)
//            meterId = devicesDataLocalDBDAO.getMeterIdFromLocalBaseUseMeteringRootGUID(meteringDeviceRootGUID);
//
//        if (meterId != null) { // если есть ид то берем оп нему идентификаторы ГИС ЖКХ.
//            meteringRootGUIDGrad = getMeteringDeviceUniqueNumbersFromGrad(meterId, "METERROOTGUID", connectionGRAD);
//            meteringVersionGUIDGrad = getMeteringDeviceUniqueNumbersFromGrad(meterId, "METERVERSIONGUID", connectionGRAD);
//
//            if (meteringRootGUIDGrad == null && meteringVersionGUIDGrad == null) { // если оба идентификатора не найдены в БД ГРАД.
//                updateMeteringVersionGUID(meterId, meteringDeviceRootGUID, meteringDeviceVersionGUID, connectionGRAD);
//            } else if (meteringRootGUIDGrad == null && meteringVersionGUIDGrad.equalsIgnoreCase(meteringDeviceVersionGUID)) { // есле не найден meteringRootGUIDGrad
//                setMeteringRootGUID(meterId, meteringDeviceRootGUID, connectionGRAD);
//            } else if (meteringRootGUIDGrad.equalsIgnoreCase(meteringDeviceRootGUID) &&
//                    !meteringVersionGUIDGrad.equalsIgnoreCase(meteringDeviceVersionGUID)) { // Если meteringRootGUIDGrad верный, а meteringDeviceVersionGUID изменился на устройстве.
//                updateMeteringVersionGUID(meterId, meteringDeviceRootGUID, meteringDeviceVersionGUID, connectionGRAD);
//            } else if (!meteringRootGUIDGrad.equalsIgnoreCase(meteringDeviceRootGUID) &&
//                    meteringVersionGUIDGrad.equalsIgnoreCase(meteringDeviceVersionGUID)) { // Если VersionGUID один, а RootGUID старый.
//                setMeteringRootGUID(meterId, meteringDeviceRootGUID, connectionGRAD);
//            } else if (!meteringRootGUIDGrad.equalsIgnoreCase(meteringDeviceRootGUID) &&
//                    !meteringVersionGUIDGrad.equalsIgnoreCase(meteringDeviceVersionGUID)) {
//                setMeteringRootGUID(meterId, meteringDeviceRootGUID, connectionGRAD);
////                updateMeteringVersionGUID(meterId, meteringDeviceRootGUID, meteringDeviceVersionGUID, connectionGRAD);
//            }
//        } else { // если нет, то надо найти по AccountGUID и PremiseGUID.
//            setByAccountAndPremiseGUIDs(meteringDeviceRootGUID, meteringDeviceVersionGUID,
//                    meteringDeviceBasicCharacteristicsType, connectionGRAD);
//        }
    }

    /**
     * Метод, проверяет каждый ПУ на соответствие данных, если данные ГИС ЖКХ отличаются от данных ГРАДа,
     * тогда надо определить были уже переданные показания по ПУ и сформировать соответствующий объект для обновления.
     */
    @Deprecated
    private void checkBasicCharacteristicsForUpdate(ExportMeteringDeviceDataResultType importDevice) throws SQLException {
//
////        Так как мы хотим обновить устройство, значет оно уже должно быть у нас и иметь идентификатор
////        Определим meterId с помощью идентификатора ГИС ЖКХ, с помощью него найдем нужный прибор и сравним.
//        Integer meterId = devicesDataLocalDBDAO.getMeterIdFromLocalBaseUseMeteringVersionGUID(importDevice.getMeteringDeviceVersionGUID());
//        if (meterId != null) { //
//            for (Map.Entry<String, LinkedHashMap<Integer, ImportMeteringDeviceDataRequest.MeteringDevice>>
//                    mapEntry : mapTransportMeteringDevice.entrySet()) {
//
//                Integer abonId = null;
//                ImportMeteringDeviceDataRequest.MeteringDevice device = null;
//
//                Integer meterIdDevice = null;
//
//                for (Map.Entry<Integer, ImportMeteringDeviceDataRequest.MeteringDevice> deviceEntry : mapEntry.getValue().entrySet()) {
//                    if (deviceEntry.getValue() != null) {
//                        abonId = deviceEntry.getKey();
//                        device = deviceEntry.getValue();
//                    } else {
//                        meterIdDevice = deviceEntry.getKey();
//                    }
//                }
//
//                if (meterId.equals(meterIdDevice)) {
//                    if (importDevice.getMunicipalResourceNotEnergy() != null
//                            && device.getDeviceDataToCreate().getMunicipalResourceNotEnergy() != null) {
////                        Оказалось, что теперь и сравнивать нечего.
//                    } else if (!importDevice.getBasicChatacteristicts().getMeteringDeviceNumber().equals(device.getDeviceDataToCreate().getBasicChatacteristicts().getMeteringDeviceNumber()) &&
//                            !importDevice.getBasicChatacteristicts().getMeteringDeviceStamp().equals(device.getDeviceDataToCreate().getBasicChatacteristicts().getMeteringDeviceStamp()) &&
//                            !importDevice.getBasicChatacteristicts().getMeteringDeviceModel().equals(device.getDeviceDataToCreate().getBasicChatacteristicts().getMeteringDeviceModel()) &&
//                            !(importDevice.getBasicChatacteristicts().isRemoteMeteringMode() == device.getDeviceDataToCreate().getBasicChatacteristicts().isRemoteMeteringMode()) &&
//                            !(importDevice.getBasicChatacteristicts().isPressureSensor() == device.getDeviceDataToCreate().getBasicChatacteristicts().isPressureSensor()) &&
//                            !(importDevice.getBasicChatacteristicts().isTemperatureSensor() == device.getDeviceDataToCreate().getBasicChatacteristicts().isTemperatureSensor()) &&
//                            !importDevice.getBasicChatacteristicts().getInstallationDate().equals(device.getDeviceDataToCreate().getBasicChatacteristicts().getInstallationDate()) &&
//                            !importDevice.getBasicChatacteristicts().getCommissioningDate().equals(device.getDeviceDataToCreate().getBasicChatacteristicts().getCommissioningDate()) &&
//                            !importDevice.getBasicChatacteristicts().getFactorySealDate().equals(device.getDeviceDataToCreate().getBasicChatacteristicts().getFactorySealDate())) {
////                        Надо учитывать тот факт, что getUpdateAfterDevicesValues не имеет BasicChatacteristicts, т.е. не сможет изменить элемент.
////                        if (getDeviceValueRequest(importDevice.getMeteringDeviceVersionGUID()) == null) { // если ещё не было выгрузки формирум иначе нет.
//                        setUpdateDevice(importDevice, device);
////                        }
//                    }
//                }
//            }
//        }
    }

    /**
     * Метод, добавляет в лист объект для обновления.
     *
     * @param importDevice устройство полученное из ГИС ЖКХ
     * @param device       обновленное устройство.
     * @throws SQLException
     */
    @Deprecated
    private void setUpdateDevice(ExportMeteringDeviceDataResultType importDevice, ImportMeteringDeviceDataRequest.MeteringDevice device) throws SQLException {

//        ImportMeteringDeviceDataRequest.MeteringDevice tempDevice = new ImportMeteringDeviceDataRequest.MeteringDevice();
//
//        tempDevice.setTransportGUID(OtherFormat.getRandomGUID());
//        tempDevice.setDeviceDataToUpdate(new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate());
//        tempDevice.getDeviceDataToUpdate().setMeteringDeviceVersionGUID(importDevice.getMeteringDeviceVersionGUID());
//        if (devicesDataLocalDBDAO.getDeviceValueRequest(importDevice.getMeteringDeviceVersionGUID()) == null) {  // если ещё не было выгрузки формирум иначе нет.
//            tempDevice.getDeviceDataToUpdate().setUpdateBeforeDevicesValues(device.getDeviceDataToCreate());
////            if (device.getDeviceDataToCreate().getMunicipalResourceEnergy() == null) { // не нужен даные берем сверху
////                tempDevice.getDeviceDataToUpdate().getUpdateBeforeDevicesValues().getMunicipalResourceNotEnergy().clear();
////                tempDevice.getDeviceDataToUpdate().getUpdateBeforeDevicesValues().getMunicipalResourceNotEnergy().addAll(device.getDeviceDataToCreate().getMunicipalResourceNotEnergy());
////            } else {
////                tempDevice.getDeviceDataToUpdate().getUpdateBeforeDevicesValues().setMunicipalResourceEnergy(device.getDeviceDataToCreate().getMunicipalResourceEnergy());
////            }
//        } else { // Если уже были выгружены показания ПУ
//            tempDevice.getDeviceDataToUpdate().setUpdateAfterDevicesValues(new MeteringDeviceToUpdateAfterDevicesValuesType());
////            tempDevice.getDeviceDataToUpdate().getUpdateAfterDevicesValues().setBasicChatacteristicts(device.getBasicChatacteristicts());
//            if (device.getDeviceDataToCreate().getMunicipalResourceEnergy() == null) {
//                tempDevice.getDeviceDataToUpdate().getUpdateAfterDevicesValues().getMunicipalResourceNotEnergy().clear();
//                tempDevice.getDeviceDataToUpdate().getUpdateAfterDevicesValues().getMunicipalResourceNotEnergy().add(device.getDeviceDataToCreate().getMunicipalResourceNotEnergy().get(0));
//            } else {
//                tempDevice.getDeviceDataToUpdate().getUpdateAfterDevicesValues().setMunicipalResourceEnergy(device.getDeviceDataToCreate().getMunicipalResourceEnergy());
//            }
//        }
//        devicesForUpdateList.add(tempDevice);
////        добавим в MAP TransportGUID и устройство, если обновление не пройдет,
////        можем идентифицировать устройство по TransportGUID и получить нужное устройство.
////        затем можем заархивировать устройство в ГИС ЖКХ и создать по новой.
//        deviceForArchiveAndCreateMap.put(tempDevice.getTransportGUID(), device);
    }

    /**
     * Метод, проводит сравнения имеющихся ПУ и полученных по идентификаторам помещений и ЛС.
     *
     * @param meteringDeviceRootGUID    идентификатор ПУ в ГИС ЖКХ.
     * @param meteringDeviceVersionGUID идентификатор версии ПУ в ГИС ЖКХ.
     * @param basicCharacteristics      базовые характеристики ПУ.
     * @param connectionGRAD            подключение к БД ГРАД.
     */
    @Deprecated
    private void setByAccountAndPremiseGUIDs(String meteringDeviceRootGUID,
                                             String meteringDeviceVersionGUID,
                                             MeteringDeviceBasicCharacteristicsType basicCharacteristics,
                                             Connection connectionGRAD) throws SQLException, PreGISException {

//        for (Map.Entry<String, LinkedHashMap<Integer, ImportMeteringDeviceDataRequest.MeteringDevice>>
//                mapEntry : mapTransportMeteringDevice.entrySet()) {
//
//            Integer abonId = null;
////            ImportMeteringDeviceDataRequest.MeteringDevice device;
//            MeteringDeviceBasicCharacteristicsType device = null;
//            Integer meterId = null;
//
//            for (Map.Entry<Integer, ImportMeteringDeviceDataRequest.MeteringDevice> deviceEntry : mapEntry.getValue().entrySet()) {
//                if (deviceEntry.getValue() != null) {
//                    abonId = deviceEntry.getKey();
//                    device = deviceEntry.getValue().getDeviceDataToCreate().getBasicChatacteristicts();
//                } else {
//                    meterId = deviceEntry.getKey();
//                }
//            }
//
//            if (device != null) {
//                if (basicCharacteristics.getMeteringDeviceNumber().equalsIgnoreCase(device.getMeteringDeviceNumber()) && basicCharacteristics.getMeteringDeviceStamp().equalsIgnoreCase(device.getMeteringDeviceStamp()) && basicCharacteristics.getMeteringDeviceModel().equalsIgnoreCase(device.getMeteringDeviceModel())) {
//                    if (basicCharacteristics.getLivingRoomDevice() != null) { // Комунальные комнаты - один счетчик много комнат
//                        if (basicCharacteristics.getLivingRoomDevice().getAccountGUID().contains(device.getLivingRoomDevice().getAccountGUID().get(0)) &&
//                                basicCharacteristics.getLivingRoomDevice().getLivingRoomGUID().contains(device.getLivingRoomDevice().getLivingRoomGUID().get(0))) {
//
//                            for (String itemRoom : basicCharacteristics.getLivingRoomDevice().getLivingRoomGUID()) {
//                                if (setMeteringDeviceToBase(abonId, meterId, houseId,
//                                        basicCharacteristics.getLivingRoomDevice().getAccountGUID().get(0),
//                                        null, itemRoom, basicCharacteristics.getMeteringDeviceNumber(),
//                                        null, meteringDeviceRootGUID, meteringDeviceVersionGUID, null, false, connectionGRAD)) {
//                                    LOGGER.debug("abonId: " + abonId + " meterId: " + meterId);
//                                    countAdded++;
//                                    answerProcessing.sendMessageToClient("Добавлен прибор учёта в локальную БД, идентификатор ПУ в ГИС ЖКХ: " + meteringDeviceRootGUID + ".");
//                                    answerProcessing.sendMessageToClient("");
//                                }
//                            }
//                        }
//                    } else if (basicCharacteristics.getResidentialPremiseDevice() != null && device.getResidentialPremiseDevice() != null) { // жилые помещения, одно помещение и может быть несколько л.счетов.
//
//                        if (basicCharacteristics.getResidentialPremiseDevice().getAccountGUID().contains(device.getResidentialPremiseDevice().getAccountGUID().get(0)) &&
//                                basicCharacteristics.getResidentialPremiseDevice().getPremiseGUID().equalsIgnoreCase(device.getResidentialPremiseDevice().getPremiseGUID())) {
//
//                            for (String accountGUID : basicCharacteristics.getResidentialPremiseDevice().getAccountGUID()) {
//                                if (setMeteringDeviceToBase(abonId, meterId, houseId, accountGUID,
//                                        basicCharacteristics.getResidentialPremiseDevice().getPremiseGUID(), null,
//                                        basicCharacteristics.getMeteringDeviceNumber(),
//                                        null, meteringDeviceRootGUID, meteringDeviceVersionGUID, null, false, connectionGRAD)) {
//                                    LOGGER.debug("abonId: " + abonId + " meterId: " + meterId);
//                                    countAdded++;
//                                    answerProcessing.sendMessageToClient("Добавлен прибор учёта в локальную БД, идентификатор ПУ в ГИС ЖКХ: " + meteringDeviceRootGUID + ".");
//                                    answerProcessing.sendMessageToClient("");
//                                }
//                            }
//                        }
//                    } else if (basicCharacteristics.getNonResidentialPremiseDevice() != null && device.getNonResidentialPremiseDevice() != null) { // не жилые помещения
//
//                        if (basicCharacteristics.getNonResidentialPremiseDevice().getAccountGUID().contains(device.getNonResidentialPremiseDevice().getAccountGUID().get(0)) &&
//                                basicCharacteristics.getNonResidentialPremiseDevice().getPremiseGUID().equalsIgnoreCase(device.getNonResidentialPremiseDevice().getPremiseGUID())) {
//
//                            if (setMeteringDeviceToBase(abonId, meterId, houseId,
//                                    basicCharacteristics.getNonResidentialPremiseDevice().getAccountGUID().get(0),
//                                    basicCharacteristics.getNonResidentialPremiseDevice().getPremiseGUID(), null,
//                                    basicCharacteristics.getMeteringDeviceNumber(),
//                                    null, meteringDeviceRootGUID, meteringDeviceVersionGUID, null, true, connectionGRAD)) {
//                                LOGGER.debug("abonId: " + abonId + " meterId: " + meterId);
//                                countAdded++;
//                                answerProcessing.sendMessageToClient("Добавлен прибор учёта в локальную БД, идентификатор ПУ в ГИС ЖКХ: " + meteringDeviceRootGUID + ".");
//                                answerProcessing.sendMessageToClient("");
//                            }
//                        }
//                    } else if (basicCharacteristics.getCollectiveApartmentDevice() != null && device.getCollectiveApartmentDevice() != null) { // Характеристики общеквартирного ПУ (для квартир коммунального заселения)
//
//                        if (basicCharacteristics.getCollectiveApartmentDevice().getAccountGUID().contains(device.getCollectiveApartmentDevice().getAccountGUID().get(0)) &&
//                                basicCharacteristics.getCollectiveApartmentDevice().getPremiseGUID().equalsIgnoreCase(device.getCollectiveApartmentDevice().getPremiseGUID())) {
//
//                            for (String accountGUID : basicCharacteristics.getCollectiveApartmentDevice().getAccountGUID()) {
//                                if (setMeteringDeviceToBase(abonId, meterId, houseId, accountGUID,
//                                        basicCharacteristics.getCollectiveApartmentDevice().getPremiseGUID(), null,
//                                        basicCharacteristics.getMeteringDeviceNumber(),
//                                        null, meteringDeviceRootGUID, meteringDeviceVersionGUID, null, false, connectionGRAD)) {
//                                    LOGGER.debug("abonId: " + abonId + " meterId: " + meterId);
//                                    countAdded++;
//                                    answerProcessing.sendMessageToClient("Добавлен прибор учёта в локальную БД, идентификатор ПУ в ГИС ЖКХ: " + meteringDeviceRootGUID + ".");
//                                    answerProcessing.sendMessageToClient("");
//                                }
//                            }
//                        }
//
//                    } else if (basicCharacteristics.getApartmentHouseDevice() != null) {  // Тип ПУ Коллективный (общедомовой) или Индивидуальный ПУ в ЖД
//
//                        if (basicCharacteristics.getApartmentHouseDevice().getAccountGUID().contains(device.getApartmentHouseDevice().getAccountGUID().get(0))) {
//
//                            for (String accountGUID : basicCharacteristics.getApartmentHouseDevice().getAccountGUID()) {
//                                if (setMeteringDeviceToBase(abonId, meterId, houseId, accountGUID, null, null,
//                                        basicCharacteristics.getMeteringDeviceNumber(),
//                                        null, meteringDeviceRootGUID, meteringDeviceVersionGUID, null, false, connectionGRAD)) {
//                                    LOGGER.debug("abonId: " + abonId + " meterId: " + meterId);
//                                    countAdded++;
//                                    answerProcessing.sendMessageToClient("Добавлен прибор учёта в локальную БД, идентификатор ПУ в ГИС ЖКХ: " + meteringDeviceRootGUID + ".");
//                                    answerProcessing.sendMessageToClient("");
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }

    /**
     * Метод, обновляет информацию о идентификаторе версии ПУ.
     *
     * @param meterId                   ид ПУ в БД ГРАД.
     * @param meteringDeviceRootGUID    идентификатор ПУ в ГИС ЖКХ.
     * @param meteringDeviceVersionGUID идентификатор версии ПУ в ГИС ЖКХ.
     * @param connectionGRAD            подключение к БД ГРАД.
     */
    public void updateMeteringVersionGUID(Integer meterId, String meteringDeviceRootGUID, String meteringDeviceVersionGUID, Connection connectionGRAD) throws SQLException, PreGISException {
        setMeteringDeviceUniqueNumbers(meterId, meteringDeviceVersionGUID, meteringDeviceRootGUID, connectionGRAD);
//        if (devicesDataLocalDBDAO.getMeterIdFromLocalBaseUseMeteringVersionGUID(meteringDeviceVersionGUID) == null) {
//            devicesDataLocalDBDAO.setMeteringVersionGUIDToLocalDb(meterId, meteringDeviceRootGUID, meteringDeviceVersionGUID);
//            countUpdate++;
//            answerProcessing.sendMessageToClient("");
//            answerProcessing.sendMessageToClient("Обновлен ПУ, идентификатор версии ПУ в ГИС ЖКХ: " + meteringDeviceVersionGUID);
//            LOGGER.info("Обновлен элемент ПУ: ID = " + meteringDeviceVersionGUID + " MeterId: " + meterId);
//        }
    }


    /**
     * Метод, заносит идентификаторы в БД.
     *
     * @param meterId                ид ПУ в БД ГРАД.
     * @param meteringDeviceRootGUID идентификатор ПУ в ГИС ЖКХ.
     * @param connectionGRAD         подключение к БД ГРАД.
     */
    @Deprecated
    private void setMeteringRootGUID(Integer meterId, String meteringDeviceRootGUID, Connection connectionGRAD) throws SQLException, PreGISException {
//        setMeteringDeviceUniqueNumbers(meterId, null, meteringDeviceRootGUID, connectionGRAD);
//        if (devicesDataLocalDBDAO.getMeterIdFromLocalBaseUseMeteringRootGUID(meteringDeviceRootGUID) == null) {  // Если передали новый meteringDeviceRootGUID, то из базы вернется null
//            devicesDataLocalDBDAO.setMeteringRootGUIDToLocalDb(meterId, meteringDeviceRootGUID);
//            countUpdate++;
//            answerProcessing.sendMessageToClient("");
//            answerProcessing.sendMessageToClient("Обновлен ПУ, идентификатор ПУ в ГИС ЖКХ: " + meteringDeviceRootGUID);
//            LOGGER.info("Обновлен элемент ПУ: ID = " + meteringDeviceRootGUID + " MeterId: " + meterId);
//        }
    }


    /**
     * Метод, формирует один ПУ для обновления в ГИС ЖКХ.
     *
     * @return сформированный для обновления прибор учёта.
     */
    @Deprecated
    private ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate getMeteringDeviceForUpdate() {
        ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate device = new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate();
//        device.set
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
     * Метод, выполняет SQL процедуру "EX_GIS_PU1" полученый результат сохраняет в List.
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
                        if (OtherFormat.getAllDataFromString(resultSet1.getString(1))[TYPE_PU].equalsIgnoreCase("Коллективный (общедомовой)") ||
                                (OtherFormat.getAllDataFromString(resultSet1.getString(1))[ABON_ID_PU1] != null &&
                                !OtherFormat.getAllDataFromString(resultSet1.getString(1))[ABON_ID_PU1].isEmpty())) {
                            list.add(OtherFormat.getAllDataFromString(resultSet1.getString(1)));
                        } else {
                            LOGGER.debug("ПУ: " + resultSet1.getString(1) +
                                    " не имеет отличительных данных, не сможет быть загруженным в ГИС ЖКХ.");
                        }
                    }
                    // answerProcessing.sendInformationToClientAndLog(list.toString(), LOGGER);
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
                            exGisIpuIndList.add(OtherFormat.getAllDataFromString(resultSet1.getString(1)));
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
     * @param devicesMap мапа запроса <TransportGUID, MeteringDevice>
     * @throws SQLException
     */
    public void setMeteringDevices(ImportResult importResult, Map<String, Integer> devicesMap) throws SQLException, PreGISException {

        if(devicesMap == null){
            answerProcessing.sendMessageToClient("Отсутствует соответствие идентификаторов Град и TransportGUID");
            if(errorState > 0) errorState = 0;
            return;
        }

        if ((importResult.getCommonResult() != null) && !(importResult.getCommonResult().isEmpty())) {

            try(Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()){
                // бежим по результатам импорта в ГИС
                for (ImportResult.CommonResult result : importResult.getCommonResult()) {

                    // если есть такой транспортный идентификатор
                    if(devicesMap.containsKey(result.getTransportGUID())) {

                        // если занеслось в ГИС без ошибок
                        if ((result.getError() == null || result.getError().isEmpty()) &&
                                result.getImportMeteringDevice() != null) {
                            // выставляем в Град идентификаторы ПУ
                            setMeteringDeviceUniqueNumbers(devicesMap.get(result.getTransportGUID()),
                                    result.getGUID(),
                                    result.getImportMeteringDevice().getMeteringDeviceGUID(),
                                    connectionGRAD);
    //                        setMeteringDevices(result.getUniqueNumber(), result.getGUID(),
    //                                result.getImportMeteringDevice().getMeteringDeviceGUID(), result.getTransportGUID(), connectionGrad);
                        }
// закомментарил, так как ошибки по ПУ обрабатываются и выводятся на предыдущем уровне
//                        else {
//                            showErrorMeteringDevices(result.getTransportGUID(), result.getError().get(0).getErrorCode(),
//                                    result.getError().get(0).getDescription());
//                        }
                    }else{
                        answerProcessing.sendMessageToClient("Результат содержит не входящий в запрос TransportGUID " + result.getTransportGUID());
                        if(errorState > 0) errorState = 0;
                    }
                }
            }
        }
        /* Закомментарил. Сюда может попасть и при отсутствии ответа (его нулевой длине), а так как мы больше не сохраняем запросы в БД, то тут возникает ошибка
        else {  // Возвращает не тот объект ответа.

            ru.gosuslugi.dom.schema.integration.base.ImportResult castResult = getImportResultLastFromDataBase();
            for (CommonResultType resultType : castResult.getCommonResult()) {

                if (resultType.getError() == null || resultType.getError().size() == 0) {
                    LOGGER.debug("Active: base.ImportResult.");
//                Этот объект вместо getGuid содержит meteringVersionGUID.
                    setMeteringDevices(resultType.getUniqueNumber(), null, resultType.getGUID(),
                            resultType.getTransportGUID(), connectionGrad);
                } else {
                    showErrorMeteringDevices(resultType.getTransportGUID(), resultType.getError().get(0).getErrorCode(),
                            resultType.getError().get(0).getDescription());
                }
            }
        }
        */
    }

    /**
     * Метод, формирует и выводит пользователю информацию об ошибках, которые возвращает ГИС ЖКХ.
     *
     * @param transportGUID транспортный идентификатор.
     * @param errorCode     код ошибки.
     * @param description   описание ошибки.
     */
    private void showErrorMeteringDevices(String transportGUID, String errorCode, String description) {
        answerProcessing.sendMessageToClient("");
        if(transportGUID != null && !transportGUID.isEmpty()) answerProcessing.sendMessageToClient("TransportGUID: " + transportGUID);
        if(errorCode != null && !errorCode.isEmpty()) answerProcessing.sendMessageToClient("Код ошибки: " + errorCode);
        answerProcessing.sendMessageToClient("Описание ошибки: " + description);
        errorState = 0;
    }

    /**
     * Метод, по транспортному идентификатору определяет отправленный ПУ и назначает ему полученные идентификаторы.
     *
     * @param meteringUniqueNumber уникальный реестровый номер.
     * @param meteringRootGUID     идентификатор ПУ в ГИС ЖКХ.
     * @param meteringVersionGUID  идентификатор версии ПУ.
     * @param transportGUID        транспортный идентификатор.
     * @param connectionGrad       подключение к БД ГРАД.
     * @throws SQLException
     */
    @Deprecated
    private void setMeteringDevices(String meteringUniqueNumber, String meteringRootGUID, String meteringVersionGUID,
                                    String transportGUID, Connection connectionGrad) throws SQLException, PreGISException {

//        if (mapTransportMeteringDevice.containsKey(transportGUID)) {
//
//            Integer abonId = null;
//            ImportMeteringDeviceDataRequest.MeteringDevice device = null;
//            Integer meterId = null;
//
////            Если получили без ошибки ответ, то смотрим, есть в Map для добавления устройства в архив элемент, если нашли, удаляем его.
//            if (deviceForArchiveAndCreateMap.containsKey(transportGUID))
//                deviceForArchiveAndCreateMap.remove(transportGUID);
//
//            for (Map.Entry<Integer, ImportMeteringDeviceDataRequest.MeteringDevice> entry : mapTransportMeteringDevice.get(transportGUID).entrySet()) {
//
//                if (entry.getValue() != null) {
//                    abonId = entry.getKey();
//                    device = entry.getValue();
//                } else {
//                    meterId = entry.getKey();
//                }
//            }
////            LOGGER.debug("abinId: " + abonId + " meterId: " + meterId);
////            LOGGER.debug("setMeteringDeviceUniqueNumbers");
////            Перенес в один метод, где проводится проверка
////            setMeteringDeviceUniqueNumbers(meterId, meteringVersionGUID, meteringRootGUID, connectionGrad); // в БД ГРАД.
////            LOGGER.debug("setMeteringDeviceToBase");
//            if (setMeteringDeviceToBase(abonId, meterId, this.houseId, meteringUniqueNumber, meteringRootGUID, meteringVersionGUID, transportGUID, device, connectionGrad)) {
//                LOGGER.debug("Добавлен ПУ, abinId: " + abonId + " meterId: " + meterId);
//                countSeted++;
//                answerProcessing.sendMessageToClient("");
//                answerProcessing.sendMessageToClient("Добавлен прибор учёта, Уникальный номер: " + meteringUniqueNumber + " идентификатор: " + meteringVersionGUID);
////                answerProcessing.sendMessageToClient("");
//            }
//
//        } else {
//            answerProcessing.sendMessageToClient("");
//            answerProcessing.sendMessageToClient("Прибор учёта, с транспортным номером: " + transportGUID + " не найден!");
////            answerProcessing.sendMessageToClient("");
//        }
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
    @Deprecated
    private boolean setMeteringDeviceToBase(Integer abonId, Integer meterId, Integer houseId, String meteringUniqueNumber,
                                            String meteringRootGUID, String meteringVersionGUID, String transportGUID,
                                            ImportMeteringDeviceDataRequest.MeteringDevice device, Connection connectionGRAD) throws SQLException, PreGISException {

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
//            meteringRootGUID = result.getGuid();
//            meteringVersionGUID = result.getImportMeteringDevice().getMeteringDeviceVersionGUID();
//            transportGUID = result.getTransportGUID();

        if (device.getDeviceDataToCreate() != null) {

            basic = device.getDeviceDataToCreate().getBasicChatacteristicts();
            meteringDeviceNumber = basic.getMeteringDeviceNumber();

            if (basic.getLivingRoomDevice() != null) { // Комунальные комнаты - один счетчик много комнат
                accountGUID = basic.getLivingRoomDevice().getAccountGUID().get(0);
                for (String itemRoom : basic.getLivingRoomDevice().getLivingRoomGUID()) {
                    state = setMeteringDeviceToBase(abonId, meterId, houseId, accountGUID, null, itemRoom, meteringDeviceNumber,
                            meteringUniqueNumber, meteringRootGUID, meteringVersionGUID, transportGUID, false, connectionGRAD);
                }
            } else if (basic.getResidentialPremiseDevice() != null) { // жилые помещения, одно помещение и может быть несколько л.счетов.
                premiseGUID = basic.getResidentialPremiseDevice().getPremiseGUID();
                for (String itemAccountGUID : basic.getResidentialPremiseDevice().getAccountGUID()) {
                    state = setMeteringDeviceToBase(abonId, meterId, houseId, itemAccountGUID, premiseGUID, null, meteringDeviceNumber,
                            meteringUniqueNumber, meteringRootGUID, meteringVersionGUID, transportGUID, false, connectionGRAD);
                }
            } else if (basic.getNonResidentialPremiseDevice() != null) { // не жилые помещения
                premiseGUID = basic.getNonResidentialPremiseDevice().getPremiseGUID();
                for (String itemAccountGUID : basic.getNonResidentialPremiseDevice().getAccountGUID()) {
                    state = setMeteringDeviceToBase(abonId, meterId, houseId, itemAccountGUID, premiseGUID, null, meteringDeviceNumber,
                            meteringUniqueNumber, meteringRootGUID, meteringVersionGUID, transportGUID, true, connectionGRAD);
                }
            } else if (basic.getCollectiveApartmentDevice() != null) { // Характеристики общеквартирного ПУ (для квартир коммунального заселения)
                premiseGUID = basic.getCollectiveApartmentDevice().getPremiseGUID();
                for (String itemAccountGUID : basic.getCollectiveApartmentDevice().getAccountGUID()) {
                    state = setMeteringDeviceToBase(abonId, meterId, houseId, itemAccountGUID, premiseGUID, null, meteringDeviceNumber,
                            meteringUniqueNumber, meteringRootGUID, meteringVersionGUID, transportGUID, false, connectionGRAD);
                }
            } else if (basic.getApartmentHouseDevice() != null) {  // Тип ПУ Коллективный (общедомовой) или Индивидуальный ПУ в ЖД
                accountGUID = basic.getApartmentHouseDevice().getAccountGUID().get(0);
                state = setMeteringDeviceToBase(abonId, meterId, houseId, accountGUID, null, null, meteringDeviceNumber,
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
    @Deprecated
    private boolean setMeteringDeviceToBase(Integer abonId, Integer meterId, Integer houseId, String accountGUID, String premiseGUID,
                                            String livingRoomGUID, String meteringDeviceNumber, String meteringUniqueNumber,
                                            String meteringRootGUID, String meteringVersionGUID, String transportGUID, boolean isNonResitential, Connection connectionGrad) throws SQLException, PreGISException {

//        LOGGER.debug("abinId: " + abonId + " meterId: " + meterId + " houseId: " + houseId);
//        if (devicesDataLocalDBDAO.getMeterIdFromLocalBaseUseMeteringRootGUID(meteringRootGUID) == null) {
//            if (!devicesDataLocalDBDAO.getMeteringDeviceFromLocalBase(abonId, houseId, accountGUID, meteringVersionGUID)) {
//                setMeteringDeviceUniqueNumbers(meterId, meteringVersionGUID, meteringRootGUID, connectionGrad); // в БД ГРАД.
//                devicesDataLocalDBDAO.setMeteringDeviceToLocalBase(abonId, meterId, houseId, accountGUID, premiseGUID, livingRoomGUID, // в локальную БД.
//                        meteringDeviceNumber, meteringUniqueNumber, meteringRootGUID, meteringVersionGUID,
//                        transportGUID, isNonResitential);
//                return true;
//            }
//        } else {
//            updateMeteringVersionGUID(meterId, meteringRootGUID, meteringVersionGUID, connectionGrad);
//        }
        return false;
    }


    /**
     * Метод, если в БД не нашел, устройство для архивирования, то создаст его.
     *
     * @param nsiCodeElement код причины архивирования ПУ.
     * @param resultType     устройство для архивации.
     * @throws SQLException
     */
    @Deprecated
    private void setArchivingReasonToLocalBaseByRootGUID(Integer nsiCodeElement, ExportMeteringDeviceDataResultType resultType) throws SQLException {
//        String accountGUID = null;
//        String premeseGUID = null;
//        String livingroomGUID = null;
//        if (resultType.getBasicChatacteristicts().getResidentialPremiseDevice() != null){
//            accountGUID = resultType.getBasicChatacteristicts().getResidentialPremiseDevice().getAccountGUID().get(0);
//            premeseGUID = resultType.getBasicChatacteristicts().getResidentialPremiseDevice().getPremiseGUID();
//        }else if(resultType.getBasicChatacteristicts().getNonResidentialPremiseDevice() != null){
//            accountGUID = resultType.getBasicChatacteristicts().getNonResidentialPremiseDevice().getAccountGUID().get(0);
//            premeseGUID = resultType.getBasicChatacteristicts().getNonResidentialPremiseDevice().getPremiseGUID();
//        }else if(resultType.getBasicChatacteristicts().getCollectiveApartmentDevice() != null){
//            accountGUID = resultType.getBasicChatacteristicts().getCollectiveApartmentDevice().getAccountGUID().get(0);
//            premeseGUID = resultType.getBasicChatacteristicts().getCollectiveApartmentDevice().getPremiseGUID();
//        }else if(resultType.getBasicChatacteristicts().getLivingRoomDevice() != null){
//            accountGUID = resultType.getBasicChatacteristicts().getLivingRoomDevice().getAccountGUID().get(0);
//            livingroomGUID = resultType.getBasicChatacteristicts().getLivingRoomDevice().getLivingRoomGUID().get(0);
//        }else if (resultType.getBasicChatacteristicts().getApartmentHouseDevice() != null){
//            accountGUID = resultType.getBasicChatacteristicts().getApartmentHouseDevice().getAccountGUID().get(0);
//        }
//        if (!devicesDataLocalDBDAO.setArchivingReasonToLocalBaseByRootGUID(nsiCodeElement, resultType.getMeteringDeviceRootGUID())) {
//            devicesDataLocalDBDAO.setMeteringDeviceToLocalBase(
//                    -1,
//                    -1,
//                    houseId,
//                    accountGUID,
//                    premeseGUID,
//                    livingroomGUID,
//                    resultType.getBasicChatacteristicts().getMeteringDeviceNumber(),
//                    null,
//                    resultType.getMeteringDeviceRootGUID(),
//                    resultType.getMeteringDeviceVersionGUID(),
//                    null,
//                    false);
//            devicesDataLocalDBDAO.setArchivingReasonToLocalBaseByRootGUID(nsiCodeElement, resultType.getMeteringDeviceRootGUID());
//        }
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
        final String[] sString = {""};
        return executorProcedure("{EXECUTE PROCEDURE EX_GIS04(" + houseId + ")}",
                connectionGrad, resultSet1 -> {
                    while (resultSet1.next()){
                        sString[0] = OtherFormat.getAllDataFromString(resultSet1.getString(1))[7];
                        for (String s: sString[0].split(",")) {
                            list.add(Integer.valueOf(s));
                        }
                    }
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
    private void setMeteringDeviceUniqueNumbers(Integer meterId, String meteringDeviceVersionGUID, String meteringDeviceRootGUID, Connection connectionGrad) throws SQLException, PreGISException {
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
        try (CallableStatement call = connectionGrad.prepareCall("{EXECUTE PROCEDURE EX_GIS_ID(NULL, NULL , ?, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ?, ?, NULL, NULL, ?)}")) {
            call.setInt(1, meterId);
            call.setString(2, meteringDeviceVersionGUID);
            call.setString(3, meteringDeviceRootGUID);
            call.setInt(4, ResourcesUtil.instance().getCompanyGradId());
            call.executeQuery();
        } catch (NullPointerException e) {
            LOGGER.info("Не удалось найти идентификатор прибора учёта! MeterID: " + meterId +
                    ", RootGUID: " + meteringDeviceRootGUID + ", VersionGUID: " + meteringDeviceVersionGUID);
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
    @Deprecated
    public String getMeteringDeviceUniqueNumbersFromGrad(Integer meterId, String identifier, Connection connectionGRAD) throws SQLException, PreGISException {

        String answer;
//        отслеживание параметров процедуры
//        LOGGER.debug("EXECUTE PROCEDURE EX_GIS_ID(NULL, NULL , " + meterId + ", NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, " + identifier + ")");

        try (CallableStatement call = connectionGRAD.prepareCall("{EXECUTE PROCEDURE EX_GIS_ID(NULL, NULL , ?, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ?, NULL, ?)}")) {
            call.setInt(1, meterId);
            call.setString(2, identifier);
            call.setInt(3, ResourcesUtil.instance().getCompanyGradId());
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

    public int getCountSeted() {
        return countSeted;
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
    public ru.gosuslugi.dom.schema.integration.base.ImportResult getImportResultLastFromDataBase() throws JAXBException, FileNotFoundException, SOAPException, SQLException {
        MessageExecutor messageExecutor = new MessageExecutor();
        JAXBContext jc = JAXBContext.newInstance(ru.gosuslugi.dom.schema.integration.base.ImportResult.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        ru.gosuslugi.dom.schema.integration.base.ImportResult result =
                (ru.gosuslugi.dom.schema.integration.base.ImportResult) unmarshaller.unmarshal(
                        messageExecutor.getLastSOAPFromBase().getSOAPBody().extractContentAsDocument());
        return result;
    }
}
