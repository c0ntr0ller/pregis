package ru.progmatik.java.pregis.services.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.bills.ImportPaymentDocumentRequest;
import ru.gosuslugi.dom.schema.integration.bills.PaymentDocumentType;
import ru.gosuslugi.dom.schema.integration.house_management.ImportAccountRequest;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.account.AccountGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.Rooms;
import ru.progmatik.java.pregis.connectiondb.grad.bills.PaymentInformationGradDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.bills.PaymentDocumentRegistryDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

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
                LinkedHashMap<String, ImportAccountRequest.Account> accountMapFromGrad =
                        accountGRADDAO.getAccountMapFromGrad(entry.getValue(), connectionGrad);

                for (Rooms room : rooms) {
//                    Создадим платежный документ
                    ImportPaymentDocumentRequest.PaymentDocument paymentDocument = new ImportPaymentDocumentRequest.PaymentDocument();
//                    Идентификатор лицевого счета
                    paymentDocument.setAccountGuid(accountGRADDAO.getAccountGUIDFromBase(room.getAbonId(), connectionGrad));
//                    Номер платежного документа, по которому внесена плата, присвоенный такому документу исполнителем в целях осуществления расчетов по внесению платы
                    paymentDocument.setPaymentDocumentNumber(String.format("%010d", getPaymentDocumentNumber()));
//                    Общая площадь для ЛС
                    paymentDocument.getAddressInfo().setTotalSquare(accountMapFromGrad.get(room.getNumberLS()).getTotalSquare());
                    paymentDocument.getAddressInfo().setResidentialSquare(accountMapFromGrad.get(room.getNumberLS()).getResidentialSquare());
                    if (accountMapFromGrad.get(room.getNumberLS()).getHeatedArea() != null) {
                        paymentDocument.getAddressInfo().setHeatedArea(accountMapFromGrad.get(room.getNumberLS()).getHeatedArea());
                    }
                    if (accountMapFromGrad.get(room.getNumberLS()).getLivingPersonsNumber() != 0) {
                        paymentDocument.getAddressInfo().setLivingPersonsNumber(accountMapFromGrad.get(room.getNumberLS()).getLivingPersonsNumber());
                    }
//                    Начисление по услуге
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
