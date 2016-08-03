package ru.progmatik.java.pregis.connectiondb.localdb.meteringdevice;

import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Класс, работает с локальной базой данных по модулю показания ПУ.
 */
public class MeteringDeviceValuesLocalDAO {

    private static final Logger LOGGER = Logger.getLogger(MeteringDeviceValuesLocalDAO.class);

    /**
     * Метод, идентификатору ПУ в ГИС ЖКХ находит meterId с помощью которого можно занести или получить данные в БД ГРАД.
     *
     * @param meteringRootGUID    идентификатор ПУ в ГИС ЖКХ.
     * @param connectionLocalBase подключение к локальной базе данных.
     * @return дата последнего обновления.
     * @throws SQLException
     */
    public Timestamp getDateMeteringDeviceValuesUseMeteringRootGUID(String meteringRootGUID, Connection connectionLocalBase) throws SQLException {

        try (PreparedStatement pstm = connectionLocalBase.prepareStatement(
                "SELECT VALUE_REQUEST FROM METERING_DEVICE_IDENTIFIERS WHERE METERING_ROOT_GUID = ? AND ARCHIVING_REASON_CODE IS NULL")) {
            pstm.setString(1, meteringRootGUID);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getTimestamp(1);
            }
        }
        return null;
    }

    /**
     * Метод, добавляет ПУ дату последней передачи показаний в локальную БД.
     *
     * @param meteringRootGUID    идентификатор ПУ.
     * @param date                дата передачи показаний.
     * @param connectionLocalBase подключение клокальной БД.
     * @throws SQLException
     */
    public void setDateMeteringDeviceValues(String meteringRootGUID, java.util.Date date, Connection connectionLocalBase) throws SQLException {

        if (date.getTime() > (getDateMeteringDeviceValuesUseMeteringRootGUID(meteringRootGUID, connectionLocalBase) == null
                ? 0 :getDateMeteringDeviceValuesUseMeteringRootGUID(meteringRootGUID, connectionLocalBase).getTime())) {

            try (PreparedStatement ps = connectionLocalBase.prepareStatement("UPDATE METERING_DEVICE_IDENTIFIERS " +
                    "SET VALUE_REQUEST = ? WHERE METERING_ROOT_GUID = ? AND ARCHIVING_REASON_CODE IS NULL")) {
                ps.setTimestamp(1, new Timestamp(date.getTime()) );
                ps.setString(2, meteringRootGUID);
                ps.executeUpdate();
            }
        }
    }
}
