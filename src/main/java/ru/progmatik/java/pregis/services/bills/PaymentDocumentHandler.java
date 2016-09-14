package ru.progmatik.java.pregis.services.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.bills.ImportPaymentDocumentRequest;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.account.AccountGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.Rooms;
import ru.progmatik.java.pregis.connectiondb.grad.bills.PaymentInformationGradDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

/**
 * Класс, обрабатывает платежные документы.
 */
public class PaymentDocumentHandler {

    private static final Logger LOGGER = Logger.getLogger(PaymentDocumentHandler.class);
    private final AnswerProcessing answerProcessing;

    public PaymentDocumentHandler(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    public void generatePaymentDocument() throws SQLException, PreGISException, ParseException {

        try (Connection connectionGrad = ConnectionBaseGRAD.instance().getConnection()) {

            HouseGRADDAO houseDAO = new HouseGRADDAO();
            LinkedHashMap<String, Integer> houseMap = houseDAO.getHouseAddedGisJkh(connectionGrad);
            HousePaymentPeriodHandler periodHandler = new HousePaymentPeriodHandler(answerProcessing);

//              Банковские реквизиты
            PaymentInformationGradDAO dao = new PaymentInformationGradDAO();
            ImportPaymentDocumentRequest.PaymentInformation paymentInformation =
                    dao.getPaymentInformation(ConnectionBaseGRAD.instance().getConnection());

            for (Map.Entry<String, Integer> entry : houseMap.entrySet()) {

                Calendar paymentPeriod = periodHandler.getHousePaymentPeriod(entry.getKey());
                AccountGRADDAO accountGRADDAO = new AccountGRADDAO(answerProcessing);
                ArrayList<Rooms> rooms = accountGRADDAO.getRooms(entry.getValue(), connectionGrad);
                HashMap<Integer, String> totalSquareMap = houseDAO.getTotalSquare(entry.getValue(), connectionGrad);

                for (Rooms room : rooms) {
                    ImportPaymentDocumentRequest.PaymentDocument paymentDocument = new ImportPaymentDocumentRequest.PaymentDocument();
                    paymentDocument.getAddressInfo().setTotalSquare(new BigDecimal(totalSquareMap.get(room.getAbonId())));

                }




            }


        }


    }

}
