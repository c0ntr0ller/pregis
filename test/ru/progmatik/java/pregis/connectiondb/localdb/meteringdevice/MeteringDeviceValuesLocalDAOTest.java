package ru.progmatik.java.pregis.connectiondb.localdb.meteringdevice;

import org.junit.Test;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;

import java.sql.Connection;

public class MeteringDeviceValuesLocalDAOTest {

    @Test
    public void getDateMeteringDeviceValuesUseMeteringRootGUID() throws Exception {
        MeteringDeviceValuesLocalDAO dao = new MeteringDeviceValuesLocalDAO();
        Connection connection = ConnectionDB.instance().getConnectionDB();
        System.out.println(dao.getDateMeteringDeviceValuesUseMeteringRootGUID("749f01dd-95f7-4189-9186-def9c8d06a23", connection));

        connection.close();
        ConnectionDB.close();

    }

//    @Test
//    public void setDateMeteringDeviceValues() throws Exception {
//
//        MeteringDeviceValuesLocalDAO dao = new MeteringDeviceValuesLocalDAO();
//        Connection connection = ConnectionDB.instance().getConnectionDB();
//        dao.setDateMeteringDeviceValues("749f01dd-95f7-4189-9186-def9c8d06a23", new Date(System.currentTimeMillis()), connection);
//
//        connection.close();
//        ConnectionDB.close();
//
//    }
}