package ru.progmatik.java.practice;

import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.services.bills.PaymentDocumentHandler;

import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by Администратор on 19.04.2017.
 */
public class BCheckPaymentDocument {
    public static void main(String[] args) throws ParseException, SQLException, PreGISException {
        PaymentDocumentHandler pdh = new PaymentDocumentHandler(null);
        pdh.compilePaymentDocument(33);
    }
}
