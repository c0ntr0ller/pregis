package ru.progmatik.java.pregis.services.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.bills.ImportPaymentDocumentRequest;
import ru.gosuslugi.dom.schema.integration.bills.PaymentDocumentType;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.account.AccountGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.Rooms;
import ru.progmatik.java.pregis.connectiondb.grad.bills.PaymentInformationGradDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.bills.PaymentDocumentRegistryDAO;
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
    private int count = -1;

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
//                    Создадим платежный документ
                    ImportPaymentDocumentRequest.PaymentDocument paymentDocument = new ImportPaymentDocumentRequest.PaymentDocument();
                    paymentDocument.setAccountGuid(accountGRADDAO.getAccountGUIDFromBase(room.getAbonId(), connectionGrad));
                    paymentDocument.setPaymentDocumentNumber(String.format("%010d", getPaymentDocumentNumber()));
//                    Общая площадь для ЛС
                    paymentDocument.getAddressInfo().setTotalSquare(new BigDecimal(totalSquareMap.get(room.getAbonId())));
                    paymentDocument.getChargeInfo().add(new PaymentDocumentType.ChargeInfo());

                }
            }
        }
    }

    /**
     * Метод, получает из БД последний номер и выдаёт при каждом запросе следующий номер.
     * @return номер документа.
     */
    private int getPaymentDocumentNumber() throws SQLException {
        if (count == -1) {
            PaymentDocumentRegistryDAO paymentDAO = new PaymentDocumentRegistryDAO();
            count = paymentDAO.getPaymentDocumentLastNumber();
        } else {
            count++;
        }
        return count;
    }

}