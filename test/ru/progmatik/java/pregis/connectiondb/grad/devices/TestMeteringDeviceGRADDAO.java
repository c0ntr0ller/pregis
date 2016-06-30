package ru.progmatik.java.pregis.connectiondb.grad.devices;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class TestMeteringDeviceGRADDAO {

    @Test
    public void getDataFromBase() throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        MeteringDeviceGRADDAO graddao = new MeteringDeviceGRADDAO(new AnswerProcessing(new ClientService()));
//
//        try (Connection connection = ConnectionBaseGRAD.instance().getConnection()) {
//            ArrayList<String[]> gisPu1 = graddao.getExGisPu1(7124, connection);
//            for (String[] valuePu1 : gisPu1) {
//                for (String aValuePu1 : valuePu1) {
//                    System.out.print(aValuePu1);
//                    System.out.print(" : ");
//                }
//                System.out.println();
//            }
//
//            System.out.println("PU2");
//            ArrayList<String> gisPu2 = graddao.getExGisPu2(7124, connection);
//            gisPu2.forEach(System.out::println);
//
//
//        }
    }
}
