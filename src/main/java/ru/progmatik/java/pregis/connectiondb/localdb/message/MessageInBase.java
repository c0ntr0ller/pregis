package ru.progmatik.java.pregis.connectiondb.localdb.message;

import ru.progmatik.java.pregis.connectiondb.ConnectionDB;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс, работает с SOAP сообщениями, которые находятся в локальной БД.
 */
public class MessageInBase {

    /**
     * Метод, по полученному ключу берет из таблицы сообщение и возвращает его.
     * @param id идентификатор записи в БД.
     * @return SOAP сообщение.
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws SOAPException
     */
    public SOAPMessage getSOAPMessageFromDataBaseById(Integer id) throws SQLException, FileNotFoundException, SOAPException {
        try (PreparedStatement ps = ConnectionDB.instance().getConnectionDB().prepareCall("SELECT SOAPMESSAGE FROM OBMEN_OPERATION WHERE ID = ?;")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return readSoapMessage(rs.getBinaryStream(1));
        }
    }

    /**
     * Метод, извлекает из БД последнее сообщение.
     * @return последнее SOAP сообщение найденное в БД.
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws SOAPException
     */
    public SOAPMessage getLastSOAPFromBase() throws SQLException, FileNotFoundException, SOAPException {
        try (Statement statement = ConnectionDB.instance().getConnectionDB().createStatement();
             ResultSet rs = statement.executeQuery("{CALL GET_MESSAGE()}")) {
            rs.next();
            return readSoapMessage(rs.getBinaryStream(8));
        }
    }

    /**
     * Метод, формирует SOAP сообщение из полученного "InputStream".
     * @param inputStream поток из которого можно получить данные.
     * @return сформированное SOAP сообщение.
     * @throws SOAPException
     * @throws FileNotFoundException
     */
    public SOAPMessage readSoapMessage(InputStream inputStream) throws SOAPException, FileNotFoundException {
        SOAPMessage message = MessageFactory.newInstance().createMessage();
        SOAPPart soapPart = message.getSOAPPart();
        soapPart.setContent(new StreamSource(inputStream));
        message.saveChanges();
        return message;
    }
}
