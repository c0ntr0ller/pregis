package ru.progmatik.java.pregis.connectiondb.grad.bills;

import org.junit.Test;
import ru.gosuslugi.dom.schema.integration.services.bills.ImportPaymentDocumentRequest;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;

public class PaymentInformationGradDAOTest {

    @Test
    public void getPaymentInformation() throws Exception {

        PaymentInformationGradDAO dao = new PaymentInformationGradDAO();

        ImportPaymentDocumentRequest.PaymentInformation paymentInformation = dao.getPaymentInformation(ConnectionBaseGRAD.instance().getConnection());
        System.out.println(paymentInformation.getTransportGUID());
        System.out.println(paymentInformation.getBankBIK());
        System.out.println(paymentInformation.getOperatingAccountNumber());
        ConnectionBaseGRAD.instance().close();
    }

}