package ru.progmatik.java.pregis.connectiondb.storegeprocedure;

import java.sql.SQLException;

/**
 * Хранимая процедура тут только хранится.
 * Для использования H2 базы.
 * Created by andryha on 16.02.2016.
 */
public class setOrganization {
    /**
     * Метод получает "ID" таблицы "ORGANIZATION", что бы прявизать объект с полными реквизитами к нужной организации.
     * Если метод не находит нужной записи, то просто её добавляет.
     *
     * @param con - подключение к базе данных
     * @param fullName - полное имя организации.
     * @param shortName - сокращенное имя организации.
     * @param ogrn - ОГРН.
     * @param inn - ИНН.
     * @param kpp - КПП.
     * @return int номер записи в базе данных.
     * @throws SQLException
     */
    int getOrganozationID(java.sql.Connection con,
                                 String fullName,
                                 String shortName,
                                 String ogrn,
                                 String inn,
                                 String kpp,
                                 String senderID,
                                 String orgRootEntityGUID) throws SQLException {

        int countID = -1; // значение на случай если не нашли номер.
        boolean stateGetID = true; // значение для цыкла, имеет значение true пока не будет найдет ID.

        while (stateGetID) {

            java.sql.ResultSet rs = con.createStatement().executeQuery("SELECT ID FROM ORGANIZATION" +
                    " where FULL_NAME = \'" + fullName +
                    "\' and SHORT_NAME = \'" + shortName +
                    "\' and OGRN = \'" + ogrn +
                    "\' and INN = \'" + inn +
                    "\' and KPP = \'" + kpp +
                    "\' and SENDER_ID = \'" + senderID +
                    "\' and ORG_ROOT_ENTITY_GUID = \'" + orgRootEntityGUID + "\'");

            while (rs.next()) {
                countID = rs.getInt(1);
            }

            if (countID == -1) {
                java.sql.PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO ORGANIZATION(" +
                                "ID," +
                                " FULL_NAME," +
                                " SHORT_NAME," +
                                " OGRN," +
                                " INN," +
                                " KPP," +
                                " SENDER_ID," +
                                " ORG_ROOT_ENTITY_GUID)" +
                                " VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, fullName);
                ps.setString(2, shortName);
                ps.setString(3, ogrn);
                ps.setString(4, inn);
                ps.setString(5, kpp);
                ps.setString(6, senderID);
                ps.setString(7, orgRootEntityGUID);
                ps.executeUpdate();

                if (!ps.isClosed()) ps.close();
            } else {
                stateGetID = false;
                if (!rs.isClosed()) rs.close();
            }
        }
        return countID;
    }


}
