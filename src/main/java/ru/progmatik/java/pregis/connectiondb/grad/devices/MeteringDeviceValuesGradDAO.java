package ru.progmatik.java.pregis.connectiondb.grad.devices;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSI;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
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
    private final AnswerProcessing answerProcessing;
    private SimpleDateFormat dateFromSQL = new SimpleDateFormat("yyyy-MM-dd");

    public MeteringDeviceValuesGradDAO(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    /**
     * Метод, отправляет в БД запрос и получает в ответ Map где ключ - MeteringDeviceRootGUID,
     * значение объект с датой и показаниями ПУ.
     * @param houseId код дома в БД ГРАД.
     * @param connectionGrad подключение к БД ГРАД.
     * @return ключ - MeteringDeviceRootGUID, значение объект с датой и показаниями ПУ.
     * @throws SQLException
     * @throws ParseException
     */
    public final HashMap<String, MeteringDeviceValuesObject> getMeteringDeviceValueFromGrad(int houseId, Connection connectionGrad) throws SQLException, ParseException, PreGISException {

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
            throws SQLException, ParseException, PreGISException {

        ReferenceNSI referenceNSI = new ReferenceNSI(answerProcessing);

        HashMap<String, MeteringDeviceValuesObject> meteringDeviceValuesMap = new HashMap<>();

        try (CallableStatement call = connectionGrad.prepareCall("{EXECUTE PROCEDURE EX_GIS_IPU_IND(?, ?)}")) {
            call.setInt(1, houseId);
            call.setDate(2, date != null ? new java.sql.Date(date.getTime()) : null);
            ResultSet rs = call.executeQuery();

            while (rs.next()) {
                String[] allData = OtherFormat.getAllDataFromString(rs.getString(1));
                if (allData[0] != null && !allData[0].isEmpty()) {
                    meteringDeviceValuesMap.put(allData[0],
                            new MeteringDeviceValuesObject(
                                    allData[0],
                                    new BigDecimal(allData[2]),
                                    allData[3] != null ? new BigDecimal(allData[3]) : null,
                                    allData[4] != null ? new BigDecimal(allData[4]) : null,
                                    dateFromSQL.parse(allData[5]),
                                    referenceNSI.getNsiRef("2", allData[1])));
                }
            }
            rs.close();
        }
        return meteringDeviceValuesMap;
    }

    /**
     * Метод, добавляет показания ПУ в БД ГРАДа.
     * @param valuesObject объект содержащий данные показаний ПУ.
     * @param connectionGrad подключение к БД ГРАД.
     * @throws SQLException
     */
    public final void setMeteringDeviceValue(MeteringDeviceValuesObject valuesObject, Connection connectionGrad) throws SQLException {

        // execute procedure EX_GIS_IND2GRAD(:METERROOTGUID, :METERINGVALUET1, :METERINGVALUET2, :DATEVALUE)
        // Трехтарифных счетчиков у нас нет, поэтому MeteringValueT3 не нужен
        try (CallableStatement call = connectionGrad.prepareCall("{EXECUTE PROCEDURE EX_GIS_IND2GRAD(?, ?, ?, ?)}")) {
            call.setString(1, valuesObject.getMeteringDeviceRootGUID());
            call.setBigDecimal(2, valuesObject.getMeteringValue());
            call.setBigDecimal(3, valuesObject.getMeteringValueTwo());
            call.setDate(4, new java.sql.Date(valuesObject.getMeteringDate().getTime()));

            LOGGER.debug("SQL: {EXECUTE PROCEDURE EX_GIS_IND2GRAD(" +
                    valuesObject.getMeteringDeviceRootGUID() + ", " +
                    valuesObject.getMeteringValue() + ", " +
                    valuesObject.getMeteringValueTwo() + ", " +
                    new java.sql.Date(valuesObject.getMeteringDate().getTime()) + ")}");

            call.executeUpdate();

        }
    }
}
