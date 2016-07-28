package ru.progmatik.java.pregis.connectiondb.localdb.message;

import org.apache.log4j.Logger;

/**
 * Класс, сохраняет и получает в БД отправленные и полученные SOAP сообщения.
 */
public class MessageDAO {

    private static final Logger LOGGER = Logger.getLogger(MessageDAO.class);

    private static final String TABLE_NAME_OBMEN_OPERATION = "OBMEN_OPERATION";
    private static final String TABLE_NAME_AKT = "AKT";
    private static final String TABLE_NAME_SPR_NAME_METHOD = "SPR_NAME_METHOD";
    private static final String TABLE_NAME_SPR_TYPE_OPERATION = "SPR_TYPE_OPERATION";

    private static final String SQL_CREATE_TABLE_OBMEN_OPERATION = "";
}
