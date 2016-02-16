package ru.prog_matik.java.pregis.connectiondb.storegeprocedure;

import java.sql.SQLException;

/**
 * Хранимая процедура тут только хранится.
 * Для использования H2 базы.
 * Created by andryha on 16.02.2016.
 */
public class setExportOrgRegistryResult {

    /**
     * Метод добавляет или изменяет объект ответа "экспорт сведений об организациях", для дальнейшего его использования
     * в запросе на получения "Sender ID".
     * Так как организация одна, то соответственно и  реквизиты у неё должны быть одни,
     * метод при получении последних данных от сервиса ГИС ЖКХ считает их самыми актуальными,
     * для того что бы не возникало вопроса какие именно актуальные данные,
     * метод просто на просто обновляет старую запись на новую.
     *
     * @param con - подключение к базе данных
     * @param result - объект, ответ на запрос "экспорт сведений об организациях", метода "exportOrgRegistry".
     * @param fullName - полное имя организации.
     * @param shortName - сокращенное имя организации.
     * @param ogrn - ОГРН.
     * @param inn - ИНН.
     * @param kpp - КПП.
     * @throws SQLException
     */
    void setResult(java.sql.Connection con,
                   java.lang.Object result,
                   String fullName,
                   String shortName,
                   String ogrn,
                   String inn,
                   String kpp) throws SQLException {

        java.sql.PreparedStatement ps = con.prepareStatement(
                "MERGE INTO EXPORT_ORG_REGISTRY_RESULT(" +
                        "ID, " +
                        "RESULT_OBJECT, " +
                        "ORGANIZATION_ID) " +
                        "KEY(ORGANIZATION_ID) " +
                        "VALUES(?, ?)");
        ps.setObject(1, result);
        ps.setInt(2, getOrganozationID(con, fullName, shortName, ogrn, inn, kpp));
        ps.executeUpdate();

        if (!ps.isClosed()) ps.close();
    }

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
    static int getOrganozationID(java.sql.Connection con,
                                 String fullName,
                                 String shortName,
                                 String ogrn,
                                 String inn,
                                 String kpp) throws SQLException {

        int countID = -1; // значение на случай если не нашли номер.
        boolean stateGetID = true; // значение для цыкла, имеет значение true пока не будет найдет ID.

        while (stateGetID) {

            java.sql.ResultSet rs = con.createStatement().executeQuery("SELECT ID FROM ORGANIZATION " +
                    "where FULL_NAME = " + fullName +
                    " and SHORT_NAME = " + shortName +
                    " and OGRN = " + ogrn +
                    " and INN = " + inn +
                    " and KPP = " + kpp);

            while (rs.next()) {
                countID = rs.getInt(1);
            }

            if (countID == -1) {
                java.sql.PreparedStatement ps = con.prepareStatement(
                        "INSERT  INTO ORGANIZATION(" +
                                "ID, " +
                                "FULL_NAME, " +
                                "SHORT_NAME, " +
                                "OGRN," +
                                "INN," +
                                "KPP," +
                                "SENDER_ID) " +
                                "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, fullName);
                ps.setString(2, shortName);
                ps.setString(3, ogrn);
                ps.setString(4, inn);
                ps.setString(5, kpp);
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
