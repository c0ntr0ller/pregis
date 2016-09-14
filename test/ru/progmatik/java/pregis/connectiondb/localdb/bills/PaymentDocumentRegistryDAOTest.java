package ru.progmatik.java.pregis.connectiondb.localdb.bills;

import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;


public class PaymentDocumentRegistryDAOTest {

    @Test
    public void createTableTest() {

        try {
            PaymentDocumentRegistryDAO dao = new PaymentDocumentRegistryDAO();

//            PaymentDocumentRegistryDataSet dataSet = new PaymentDocumentRegistryDataSet(
//                    "0001", 6, 2016, new BigDecimal("2052.25"), 212, "GUID");
//            dataSet.setNumberPdFromGisJkh("FFF555FFF-10");
//            dao.addPaymentDocumentRegistryItem(dataSet);

            ArrayList<PaymentDocumentRegistryDataSet> recording = dao.getAllPaymentDocumentRecording();

            for (PaymentDocumentRegistryDataSet set : recording) {
                System.out.println(set.toString());
//                dao.setPaymentDocumentRegistryItemToArchive(set);
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}