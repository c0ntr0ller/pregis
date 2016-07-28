package ru.progmatik.java.pregis.connectiondb.localdb.message;

import ru.progmatik.java.pregis.connectiondb.ConnectionDB;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Класс, работает с SOAP сообщениями, которые находятся в локальной БД.
 */
public class MessageExecutor {

    /**
     * Метод, извлекает из БД последнее сообщение.
     * @return последнее SOAP сообщение найденное в БД.
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws SOAPException
     */
    public SOAPMessage getLastSOAPFromBase() throws SQLException, FileNotFoundException, SOAPException {
        MessageDAO messageDAO = new MessageDAO();
        try (Connection connection = ConnectionDB.instance().getConnectionDB()) {
            return readSoapMessage(messageDAO.getLastSOAPMessage(connection));
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
