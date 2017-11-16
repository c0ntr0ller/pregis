package ru.progmatik.java.practice;

import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.bills.PaymentDocumentGradDAO;
import ru.progmatik.java.pregis.exception.PreGISException;

import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by Администратор on 19.04.2017.
 */
public class BCheckPaymentDocument {
    public static void main(String[] args) throws ParseException, SQLException, PreGISException {
//        PaymentDocumentHandler pdh = new PaymentDocumentHandler(null);
//        pdh.callPaymentDocumentImport(21);
        PaymentDocumentGradDAO gradDAO = new PaymentDocumentGradDAO(null, ConnectionBaseGRAD.instance().getConnection());
        gradDAO.addPaymentDocumentRegistryItem("5785", "7331f8e3-b184-4b8e-8396-a2c46079617d");
    }
}
