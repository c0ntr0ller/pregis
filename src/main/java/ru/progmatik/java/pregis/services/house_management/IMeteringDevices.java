package ru.progmatik.java.pregis.services.house_management;

import ru.gosuslugi.dom.schema.integration.house_management.ImportResult;
import ru.progmatik.java.pregis.exception.PreGISException;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Интерфейс, для занесения ПУ в БД..
 */
public interface IMeteringDevices {

    public void setMeteringDevices(ImportResult importResult, Connection connectionGrad) throws SQLException, FileNotFoundException, SOAPException, JAXBException, PreGISException;

    public int getErrorState();
}
