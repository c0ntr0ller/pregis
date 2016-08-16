package ru.progmatik.java.pregis.connectiondb.localdb.meteringdevice;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.other.AnswerProcessing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс, работает с локальной БД для обработки информации о приборах учёта.
 */
public final class MeteringDevicesDataLocalDBDAO {

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
                    "METERING_DEVICE_NUMBER varchar(100), " +
                    "METERING_UNIQUE_NUMBER varchar(40), " +
                    "METERING_ROOT_GUID varchar(40), " +
                    "METERING_VERSION_GUID varchar(40), " +
                    "TRANSPORT_GUID varchar(40), " +
                    "NON_RESIDENTIAL_PREMISE_DEVICE boolean DEFAULT false, " +
                    "ARCHIVING_REASON_CODE INT, " +
                    "REPLACE_DEVICE_ID INT, " +
                    "VALUE_REQUEST TIMESTAMP); " +
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

    private static final Logger LOGGER = Logger.getLogger(MeteringDevicesDataLocalDBDAO.class);
    private final AnswerProcessing answerProcessing;

    public MeteringDevicesDataLocalDBDAO(AnswerProcessing answerProcessing) throws SQLException {
        this.answerProcessing = answerProcessing;
        //        Если нет таблицы создаём
        if (!ConnectionDB.instance().tableExist(TABLE_NAME_METERING_DEVICE_IDENTIFIERS.toUpperCase())) {
            ConnectionDB.instance().sendSqlRequest(SQL_CREATE_TABLE_METERING_DEVICE_IDENTIFIERS);
        }
    }

    /**
     * Метод, добавляют в локальную БД запись ПУ.
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
    public final void setMeteringDeviceToLocalBase(Integer abonId,
                                              Integer meterId,
                                              Integer houseId,
                                              String accountGUID,
                                              String premiseGUID,
                                              String livingRoomGUID,
                                              String meteringDeviceNumber,
                                              String meteringUniqueNumber,
                                              String meteringRootGUID,
                                              String meteringVersionGUID,
                                              String transportGUID,
                                              boolean isNonResitential) throws SQLException {

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
    }

    /**
     * Метод, обновляет информацию о идентификаторе версии ПУ в локальной БД.
     *
     * @param meterId                   ид ПУ в БД ГРАД.
     * @param meteringDeviceRootGUID    идентификатор ПУ в ГИС ЖКХ.
     * @param meteringDeviceVersionGUID идентификатор версии ПУ в ГИС ЖКХ.
     */
    public void setMeteringVersionGUIDToLocalDb(Integer meterId, String meteringDeviceRootGUID, String meteringDeviceVersionGUID) throws SQLException {
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
     * Метод, добавляет в локальную БД информацию о идентификаторе ПУ в ГИС ЖКХ.
     *
     * @param meterId                ид ПУ в БД ГРАД.
     * @param meteringDeviceRootGUID идентификатор ПУ в ГИС ЖКХ.
     */
    public void setMeteringRootGUIDToLocalDb(Integer meterId, String meteringDeviceRootGUID) throws SQLException {

        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             PreparedStatement ps = connection.prepareStatement("UPDATE METERING_DEVICE_IDENTIFIERS " +
                     "SET METERING_ROOT_GUID = ? WHERE METER_ID = ? AND ARCHIVING_REASON_CODE IS NULL")) {
            ps.setString(1, meteringDeviceRootGUID);
            ps.setInt(2, meterId);
            ps.executeUpdate();
        }
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
    public boolean getMeteringDeviceFromLocalBase(Integer abonId, Integer houseId, String accountGUID, String meteringVersionGUID) throws SQLException {

        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             PreparedStatement pstm = connection.prepareStatement(
                     "SELECT ABON_ID, HOUSE_ID, ACCOUNT_GUID, METERING_VERSION_GUID FROM METERING_DEVICE_IDENTIFIERS " +
                             "WHERE ABON_ID = ? AND HOUSE_ID = ? AND ACCOUNT_GUID = ? AND METERING_VERSION_GUID = ? AND ARCHIVING_REASON_CODE IS NULL")) {
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
    public Integer getMeterIdFromLocalBaseUseMeteringRootGUID(String meteringRootGUID) throws SQLException {

        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             PreparedStatement pstm = connection.prepareStatement(
                     "SELECT METER_ID FROM METERING_DEVICE_IDENTIFIERS WHERE METERING_ROOT_GUID = ? AND ARCHIVING_REASON_CODE IS NULL")) {
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
    public Integer getMeterIdFromLocalBaseUseMeteringVersionGUID(String meteringVersionGUID) throws SQLException {

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
            ps.setInt(1, nsiCodeElement);
            ps.setString(2, meteringVersionGUID);
            ps.executeUpdate();
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendMessageToClient("ПУ помечен как архивный: " + meteringVersionGUID);
//            }
        }
    }

    /**
     * Метод, по указанному "MeteringDeviceROOTGUID", помечает ПУ как архивный и указаывает ему причину архивирования.
     *
     * @param nsiCodeElement      код причины архивирования ПУ.
     * @param meteringRootGUID уникальный реестровый номер ПУ в ГИС ЖХК.
     * @throws SQLException
     */
    public boolean setArchivingReasonToLocalBaseByRootGUID(Integer nsiCodeElement, String meteringRootGUID) throws SQLException {

        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             PreparedStatement ps = connection.prepareStatement("UPDATE METERING_DEVICE_IDENTIFIERS " +
                     "SET ARCHIVING_REASON_CODE = ? WHERE METERING_ROOT_GUID = ?")) {
            ps.setInt(1, nsiCodeElement);
            ps.setString(2, meteringRootGUID);
            int codeUpdate = ps.executeUpdate();

            LOGGER.debug("ps.executeUpdate(): " + codeUpdate);
            if (codeUpdate == 1) {
                answerProcessing.sendMessageToClient("");
                answerProcessing.sendMessageToClient("ПУ помечен как архивный: " + meteringRootGUID);
            }
            return  (codeUpdate == 1);
        }
    }

    /**
     * Метод, проверяет по указанному "MeteringVersionGUID", является ПУ архивным или нет.
     *
     * @param meteringVersionGUID уникальный реестровый номер ПУ в ГИС ЖХК.
     * @return true - если ПУ архивное, false - если ПУ не является архивным.
     * @throws SQLException
     */
    public boolean isArchivingDevice(String meteringVersionGUID) throws SQLException {

        boolean isArchive;

        try (Connection connection = ConnectionDB.instance().getConnectionDB();
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
     * Метод, проверяет по указанному "MeteringROOTGUID", является ПУ архивным или нет.
     *
     * @param meteringRootGUID уникальный номер ПУ в ГИС ЖХК.
     * @return true - если ПУ архивное, false - если ПУ не является архивным.
     * @throws SQLException
     */
    public boolean isArchivingDeviceByRootGUID(String meteringRootGUID) throws SQLException {

        boolean isArchive;

        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             PreparedStatement pstm = connection.prepareStatement(
                     "SELECT ARCHIVING_REASON_CODE FROM METERING_DEVICE_IDENTIFIERS " +
                             "WHERE METERING_ROOT_GUID = ? AND ARCHIVING_REASON_CODE IS NOT NULL")) {
            pstm.setString(1, meteringRootGUID);
            ResultSet rs = pstm.executeQuery();

            isArchive = rs.next();
            rs.close();
        }
        return isArchive;
    }

    /**
     * Метод, получает дату последней передачи показаний ПУ.
     *
     * @param meteringVersionGUID уникальный реестровый номер ПУ в ГИС ЖХК.
     * @return дата последней передачи показаний ПУ.
     * @throws SQLException
     */
    public java.util.Date getDeviceValueRequest(String meteringVersionGUID) throws SQLException {

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
}
