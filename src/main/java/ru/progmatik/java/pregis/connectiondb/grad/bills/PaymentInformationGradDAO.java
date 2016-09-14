package ru.progmatik.java.pregis.connectiondb.grad.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.services.bills.ImportPaymentDocumentRequest;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс, получает банковские реквизиты из процедуры "EX_GIS_ORG_REKV", базы Града.
 */
public class PaymentInformationGradDAO {

    private static final Logger LOGGER = Logger.getLogger(PaymentInformationGradDAO.class);

    public ImportPaymentDocumentRequest.PaymentInformation getPaymentInformation(Connection connectionGrad) throws SQLException, PreGISException {

        ImportPaymentDocumentRequest.PaymentInformation paymentInformation =
                new ImportPaymentDocumentRequest.PaymentInformation();

        try (CallableStatement cs = connectionGrad.prepareCall(
                "EXECUTE PROCEDURE EX_GIS_ORG_REKV(" + ResourcesUtil.instance().getCompanyGradId() + ", null,  null, null)");
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                String[] data = OtherFormat.getAllDataFromString(rs.getString(1));

                paymentInformation.setTransportGUID(OtherFormat.getRandomGUID());
                paymentInformation.setBankBIK(data[5]);
                paymentInformation.setOperatingAccountNumber(data[8]);
            }
        }
        return paymentInformation;
    }
}
