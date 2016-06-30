package ru.progmatik.java.pregis.connectiondb.grad.devices;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.MeteringDeviceBasicCharacteristicsType;
import ru.gosuslugi.dom.schema.integration.services.house_management.ImportMeteringDeviceDataRequest;
import ru.progmatik.java.pregis.connectiondb.grad.account.AccountGRADDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSI;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

/**
 * Класс, получает данные из  БД ГРАД и формирует объект пригодный для ГИС ЖКХ.
 */
public class MeteringDeviceGRADDAO {

    private static final Logger LOGGER = Logger.getLogger(MeteringDeviceGRADDAO.class);
    private static final int METER_ID_PU1 = 17;
    private static final int METER_ID_PU2 = 4;
    private static final int ABON_ID_PU1 = 18;
    private static final int ABON_ID_PU2 = 5;
    private final SimpleDateFormat sDate = new SimpleDateFormat("dd.MM.yyyy");
    private final SimpleDateFormat dateFromSQL = new SimpleDateFormat("yyyy-MM-dd");
    private final AnswerProcessing answerProcessing;
    private ArrayList<String> exGisPu2List;

    public MeteringDeviceGRADDAO(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    /**
     * Метод, формирует все ПУ для создания в ГИС ЖКХ.
     *
     * @param houseId        ид дома в БД ГРАД.
     * @param connectionGRAD подключение к БД ГРАД.
     * @return новые ПУ для ГИС ЖКХ.
     */
    public java.util.List<ImportMeteringDeviceDataRequest.MeteringDevice> getMeteringDevicesForCreate(Integer houseId, Connection connectionGRAD) throws SQLException, PreGISException, ParseException {

        ArrayList<String[]> exGisPu1 = getExGisPu1(houseId, connectionGRAD);
        java.util.ArrayList<ImportMeteringDeviceDataRequest.MeteringDevice> meteringDeviceList = new ArrayList<>();

        for (String[] exGisPu1Element : exGisPu1) {

            ImportMeteringDeviceDataRequest.MeteringDevice meteringDevices = new ImportMeteringDeviceDataRequest.MeteringDevice();
            meteringDevices.setTransportGUID(OtherFormat.getRandomGUID());
            meteringDevices.setDeviceDataToCreate(getMeteringDeviceForCreateElement(houseId, exGisPu1Element, connectionGRAD));

            meteringDeviceList.add(meteringDevices);
        }

        return meteringDeviceList;
    }

    /**
     * Метод, формирует один прибор учёта для  создания в ГИС ЖКХ.
     *
     * @param exGisPu1Element массив данных полученный из БД.
     * @return сформированные прибор учёта.
     */
    private ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate getMeteringDeviceForCreateElement(Integer houseId, String[] exGisPu1Element, Connection connectionGrad) throws SQLException, PreGISException, ParseException {

        ReferenceNSI nsi = new ReferenceNSI(answerProcessing);
        LinkedHashMap<Integer, String[]> exGisIpuIndMap = getExGisIpuIndMap(houseId, connectionGrad);
        ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate device = new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate();


        if (exGisPu1Element[8].equalsIgnoreCase("Электрическая энергия")) { // Если счетчик электричества
            device.setElectricMeteringDevice(new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate.ElectricMeteringDevice());

            device.getElectricMeteringDevice().setBasicChatacteristicts(getBasicChatacteristicts(houseId, exGisPu1Element, connectionGrad));
//            Базовое показание T1
            device.getElectricMeteringDevice().setBaseValueT1(new BigDecimal(exGisPu1Element[10]).setScale(4, BigDecimal.ROUND_DOWN));
//            Базовое показание T2, не обязательно к заполнению
            if (exGisPu1Element[11] != null)
                device.getElectricMeteringDevice().setBaseValueT2(new BigDecimal(exGisPu1Element[11]).setScale(4, BigDecimal.ROUND_DOWN));
//            Базовое показание T3. В зависимости от количества заданных при создании базовых значений ПУ определяется его тип по количеству тарифов. Не обязательно к заполнению
            if (exGisPu1Element[12] != null)
                device.getElectricMeteringDevice().setBaseValueT3(new BigDecimal(exGisPu1Element[12]).setScale(4, BigDecimal.ROUND_DOWN));
            device.getElectricMeteringDevice().setReadoutDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisIpuIndMap.get(Integer.valueOf(exGisPu1Element[METER_ID_PU1]))[4])));  // Время и дата снятия показания, можно попросить процедуру с которой брать показания и дату, вот её и можно занести
//            device.getElectricMeteringDevice().setReadingsSource(); // Кем внесено
            device.getElectricMeteringDevice().setMunicipalResource(nsi.getNsiRef("2", exGisPu1Element[8])); // Коммунальный ресурс, берет из справочника 2


        } else {
            device.setOneRateMeteringDevice(new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate.OneRateMeteringDevice());
        }

        return device;
    }

    private MeteringDeviceBasicCharacteristicsType getBasicChatacteristicts(Integer houseId, String[] exGisPu1Element, Connection connectionGrad) throws ParseException, SQLException {

        AccountGRADDAO accountGRADDAO = new AccountGRADDAO(answerProcessing);
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
        basicCharacteristics.getVerificationCharacteristics().setFirstVerificationDate(OtherFormat.getDateForXML(dateFromSQL.parse(exGisPu1Element[15])));
//            Межповерочный интервал (НСИ 16)
        basicCharacteristics.getVerificationCharacteristics().setVerificationInterval(exGisPu1Element[16]);

        if (exGisPu1Element[1].equalsIgnoreCase("Индивидуальный") && exGisPu1Element[3] != null) { // если Характеристики ИПУ жилого дома (значение справочника "Тип прибора учета" = индивидуальный, тип дома = жилой дом)
            basicCharacteristics.setApartmentHouseDevice(new MeteringDeviceBasicCharacteristicsType.ApartmentHouseDevice());
            basicCharacteristics.getApartmentHouseDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBase(Integer.valueOf(exGisPu1Element[ABON_ID_PU1]), connectionGrad));
            for (String arrayData : getOtherLsForPu(houseId, connectionGrad)) {
                if (exGisPu1Element[METER_ID_PU1].equals(getAllData(arrayData)[METER_ID_PU2])) {
                    basicCharacteristics.getApartmentHouseDevice().getAccountGUID().add(accountGRADDAO.getAccountGUIDFromBase(Integer.valueOf(getAllData(arrayData)[ABON_ID_PU2]), connectionGrad));
                }
            }
        } else if (exGisPu1Element[1].equalsIgnoreCase("Коллективный (общедомовой)") && exGisPu1Element[3] != null) {
            answerProcessing.sendMessageToClient("Найден ПУ \"Коллективный (общедомовой)\" не удаётся обработать!");
//            basicCharacteristics.setCollectiveDevice(true); // Признак общедомового ПУ (значение справочника "Тип прибора учета" = коллективный (общедомомвой))
        } else if ()

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
     * @param houseId ид дома в БД ГРАД.
     * @param connectionGrad подключение к БД ГРАД.
     * @return список с данными.
     * @throws SQLException
     */
    private ArrayList<String> getOtherLsForPu(Integer houseId, Connection connectionGrad) throws SQLException {
        if (exGisPu2List == null) exGisPu2List = getExGisPu2(houseId, connectionGrad);
        return exGisPu2List;
    }

    /**
     * Метод, получает из БД текущие показания ПУ, формирует их в Map, для быстрого извлечения даных по ИД прибора учёта
     *
     * @param houseId        ид дома в БД ГРАД.
     * @param connectionGrad подключение к БД ГРАД.
     * @return map, где ключ - ид ПУ в БД ГРАД, значение - массив всех полученных данных из БД.
     * @throws SQLException
     */
    public LinkedHashMap<Integer, String[]> getExGisIpuIndMap(Integer houseId, Connection connectionGrad) throws SQLException {
        LinkedHashMap<Integer, String[]> map = new LinkedHashMap<>();
        ArrayList<String[]> exGisIpuInd = getExGisIpuInd(houseId, connectionGrad);
        for (String[] exGisIpuIndItem : exGisIpuInd) {
            map.put(Integer.valueOf(exGisIpuIndItem[5]), exGisIpuIndItem);
        }
        return map;
    }

    /**
     * Метод, выполняет SQL процедуру полученый результат сохраняет в List.
     *
     * @param houseId    ид дома в БД ГРАД.
     * @param connection подключение к БД.
     * @return список полученый из процедуры EX_GIS_PU1.
     * @throws SQLException
     */
    private ArrayList<String[]> getExGisPu1(Integer houseId, Connection connection) throws SQLException {

        ArrayList<String[]> list = new ArrayList<>();
        return executorProcedure("EXECUTE PROCEDURE EX_GIS_PU1(" + houseId + ")",
                connection, resultSet1 -> {
                    while (resultSet1.next())
                        list.add(getAllData(resultSet1.getString(1)));
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
    public ArrayList<String> getExGisPu2(Integer houseId, Connection connection) throws SQLException {

        ArrayList<String> list = new ArrayList<>();
        return executorProcedure("EXECUTE PROCEDURE EX_GIS_PU2(" + houseId + ")",
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

        ArrayList<String[]> list = new ArrayList<>();
//        Процедура принимает meterId - ид счетчика в БД ГРАД и дату. Дату указываю текущую по умолчанию.
        return executorProcedure("EXECUTE PROCEDURE EX_GIS_IPU_IND(" + houseId + ", current_date)",
                connectionGrad, resultSet1 -> {
                    while (resultSet1.next())
                        list.add(getAllData(resultSet1.getString(1)));
                    return list;
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

        try (CallableStatement call = connection.prepareCall(sqlRequest)) {
            call.executeQuery();
            ResultSet result = call.getResultSet();
            T value = handler.handle(result);
            result.close();

            return value;
        }
    }

    /**
     * Метод, возвращает обработанную строку в массиве с данными.
     *
     * @param data - строка с данными.
     * @return String - массив данных.
     */
    public synchronized String[] getAllData(String data) {

        data = data + "|-1-"; // Если последний параметр пустой, то он в массив не попадет,
        // возникнут ошибки на ссылки на индексы массива.
        String[] array = data.split(Pattern.quote("|"));
        String[] newArray = new String[array.length];
        for (int i = 0; i < array.length; i++) {

            if (array[i] != null || !array[i].trim().isEmpty()) {
                newArray[i] = array[i];
            }
        }
        return newArray;
    }

}
