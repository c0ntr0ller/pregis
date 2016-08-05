package ru.progmatik.java.pregis.connectiondb.grad.devices;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.services.device_metering.MeteringDeviceValuesObject;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Класс, работает с процедурами ГРАДа, по модулю показания ПУ.
 */
public final class MeteringDeviceValuesGradDAO {

    private static final Logger LOGGER = Logger.getLogger(MeteringDeviceValuesGradDAO.class);
    private SimpleDateFormat dateFromSQL = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Метод, отправляет в БД запрос и получает в ответ Map где ключ - MeteringDeviceRootGUID,
     * значение объект с датой и показаниями ПУ.
     * @param houseId код дома в БД ГРАД.
     * @param connectionGrad подключение к БД ГРАД.
     * @return ключ - MeteringDeviceRootGUID, значение объект с датой и показаниями ПУ.
     * @throws SQLException
     * @throws ParseException
     */
    public final HashMap<String, MeteringDeviceValuesObject> getMeteringDeviceValueFromGrad(int houseId, Connection connectionGrad) throws SQLException, ParseException {

        return getMeteringDeviceValueFromBase(houseId, null, connectionGrad);
    }


    /**
     * Метод, получает из БД ГРАДа данные по показаниям ПУ.
     * @param houseId идентификатор дома в БД ГРАД.
     * @param date дата на которую необходимо получить данные, если указано null, берет текущею.
     * @param connectionGrad подключение к БД ГРАДа.
     * @return ключ - MeteringDeviceRootGUID, значение объект с датой и показаниями ПУ.
     * @throws SQLException
     * @throws ParseException
     */
    private HashMap<String, MeteringDeviceValuesObject> getMeteringDeviceValueFromBase(int houseId, Date date,
                                                                               Connection connectionGrad)
            throws SQLException, ParseException {

        HashMap<String, MeteringDeviceValuesObject> meteringDeviceValuesMap = new HashMap<>();

        try (CallableStatement call = connectionGrad.prepareCall("{EXECUTE PROCEDURE EX_GIS_IPU_IND(?, ?)}")) {
            call.setInt(1, houseId);
            call.setDate(2, date != null ? new java.sql.Date(date.getTime()) : null);
            ResultSet rs = call.executeQuery();

            while (rs.next()) {
                String[] allData = OtherFormat.getAllDataFromString(rs.getString(1));
                meteringDeviceValuesMap.put(allData[0],
                        new MeteringDeviceValuesObject(
                                allData[0],
                                new BigDecimal(allData[1]),
                                allData[2] != null ? new BigDecimal(allData[2]) : null,
                                allData[3] != null ? new BigDecimal(allData[3]) : null,
                                dateFromSQL.parse(allData[4])));
            }
            rs.close();
        }
        return meteringDeviceValuesMap;
    }
}
