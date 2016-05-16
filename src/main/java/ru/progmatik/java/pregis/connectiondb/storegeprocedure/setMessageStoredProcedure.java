package ru.progmatik.java.pregis.connectiondb.storegeprocedure;

/**
 * Хранимая процедура тут только хранится.
 * Для использования H2 базы.
 */
public class setMessageStoredProcedure {


    /**
     * Метод добавляет данные в таблицы, добавление сообщения.
     * @param con - Соединение с базой данных.
     * @param guid - Индивидуальный идентификатор сообщения.
     * @param nameMethod - Название метода для запроса, например "exportOrgRegistry".
     * @param dateMessage - Дата и время сообщения.
     * @param nameTypeEn - Тип сообщения "Запрос" или "Ответ".
     * @param soapMessage - Сообщение целиком.
     * @param stateMessage - Статус сообщения, возможно описание ошибки.
     * @return java.sql.PreparedStatement для базы данных, так как это является процедурой для H2.
     * @throws Exception
     */
    void setTableContent(java.sql.Connection con,
                                      String guid,
                                      String nameMethod,
                                      String dateMessage,
                                      String nameTypeEn,
                                      java.io.InputStream soapMessage,
                                      String stateMessage) throws Exception {

        java.sql.PreparedStatement ps = con.prepareStatement(
                "INSERT  INTO OBMEN_OPERATION(" +
                        "ID, " +
                        "AKT_ID, " +
                        "DATE, " +
                        "TYPE_OPERATION, " +
                        "SOAPMESSAGE, " +
                        "STATE_MESSAGE) " +
                        "VALUES(DEFAULT, ?, ?, ?, ?, ?)");

        ps.setInt(1, getAktID(con, guid, nameMethod));
        ps.setTimestamp(2, java.sql.Timestamp.valueOf(dateMessage));
        ps.setInt(3, getTypeOperation(con, nameTypeEn));
        ps.setBlob(4, soapMessage);
        ps.setString(5, stateMessage);
        ps.executeUpdate();
        if (!ps.isClosed()) ps.close();
    }

    /**
     * Метод возвращает "ID" из справочника "SPR_TYPE_OPERATION".
     * @param con - соединение с базой данных.
     * @param nameTypeEn - имя типа сообщения, "ID" которого необходимо вернуть.
     * @return int "ID" из справочника "SPR_TYPE_OPERATION".
     * @throws java.sql.SQLException
     */
    static int getTypeOperation(java.sql.Connection con, String nameTypeEn) throws java.sql.SQLException {

        int typeOperationID;

        java.sql.ResultSet rs = con.createStatement().executeQuery(
                "select ID from SPR_TYPE_OPERATION ob WHERE NAME_TYPE_EN = \'" + nameTypeEn + "\'");
        if (rs.next()) {
            typeOperationID = rs.getInt("ID");
        } else {
            throw new java.sql.SQLException("Не найдено " + nameTypeEn + " в таблице \"SPR_TYPE_OPERATION.NAME_TYPE_EN\"!");
        }
        return typeOperationID;
    }

    /**
     * Метод возвращает "ID" из таблице "AKT", для таблицы "OBMEN_OPERATION".
     * Получает "GUID" сообщения, если не находит его в таблице "AKT", то создаёт новую запись.
     * В любом случае вернет "ID", таблицы "AKT".
     * @param con - соединение с базой данных.
     * @param guid - индивидуальный идентификатор сообщения.
     * @param nameMethod - имя метода для запроса, например "exportOrgRegistry".
     * @return int "ID" таблицы "AKT".
     * @throws java.sql.SQLException
     */
    static int getAktID(java.sql.Connection con,
                 String guid,
                 String nameMethod) throws java.sql.SQLException {

        boolean stateGetID = true;
        int answerID = 0;
        int tempError = 0;
        java.sql.ResultSet rs;

        while (stateGetID) {

            tempError++;

            if (tempError > 5) {
                throw new java.sql.SQLException("Не найдено данных в таблице \"AKT\"!");
            }

            rs = con.createStatement().executeQuery(
                    "select ID from AKT WHERE GUID = \'" + guid + "\'");
            if (rs.next()) {
                answerID = rs.getInt("ID");
                stateGetID = false;
            } else {
                addAKT(con, guid, nameMethod);
            }
            if (!rs.isClosed()) rs.close();
        }
        return answerID;
    }

    /**
     * Добавляет запись в таблицу "AKT", "GUID" и "ID" с таблицы "SPR_NAME_METHOD".
     * @param con - соединение с базой данных.
     * @param guid - индивидуальный идентификатор сообщения.
     * @param nameMethod - - имя метода для запроса, например "exportOrgRegistry".
     * @throws java.sql.SQLException
     */
    static void addAKT(java.sql.Connection con, String guid, String nameMethod) throws java.sql.SQLException {

        int nameMethodID;

        java.sql.ResultSet rs = con.createStatement().executeQuery(
                "select ID from SPR_NAME_METHOD ob WHERE NAME_METHOD = \'" + nameMethod + "\'");
        if (rs.next()) {
            nameMethodID = rs.getInt("ID");
        } else {
            throw new java.sql.SQLException("Не найдено данных в таблице \"SPR_NAME_METHOD.NAME_METHOD\"!");
        }
        java.sql.PreparedStatement psAKT = con.prepareStatement(
                "INSERT  INTO AKT(" +
                        "ID, " +
                        "GUID, " +
                        "NAME_METHOD) " +
                        "VALUES(DEFAULT, ?, ?)");
        psAKT.setString(1, guid);
        psAKT.setLong(2, nameMethodID);
        psAKT.executeUpdate();

        try {
            if (!psAKT.isClosed()) psAKT.close();
            if (!rs.isClosed()) rs.close();
        } catch (Exception e) {
        }
    }
}
