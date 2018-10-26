package ru.progmatik.java.pregis.connectiondb.grad.payment;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.payment.ImportSupplierNotificationsOfOrderExecutionRequest;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.model.HouseRecord;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;

import java.sql.*;
import java.util.Calendar;
import java.util.HashMap;

//import ru.gosuslugi.dom.schema.integration.payment.ImportNotificationsOfOrderExecutionRequest;
//import ru.gosuslugi.dom.schema.integration.payments_base.NotificationOfOrderExecutionType;
//import ru.gosuslugi.dom.schema.integration.payments_base.NotificationOfOrderExecutionType;


public class PaymentGRADDAO {


    private static final Logger LOGGER = Logger.getLogger(PaymentGRADDAO.class);
    private final AnswerProcessing answerProcessing;
    private int stateError;
    private Connection connectionGrad;

    public PaymentGRADDAO(AnswerProcessing answerProcessing, Connection connectionGrad) throws SQLException {
        if (answerProcessing != null) {
            this.answerProcessing = answerProcessing;
        } else {
            this.answerProcessing = new AnswerProcessing();
        }
        this.connectionGrad = connectionGrad;
    }

    /**
     * метод запрашивает в Град и формирует список чеков на отправку в ГИС ЖКХ вида "Номер чека в Град" - "Документ для отправки в ГИС"
     * номер чека в Град нужен, чтобы в дальнейшем отметить его в Град как высланный ранее и больше не выдавать
     * @param houseGrad
     * @return
     * @throws SQLException
     */
    public HashMap<String, ImportSupplierNotificationsOfOrderExecutionRequest.SupplierNotificationOfOrderExecution> getPaymentsFromGrad(final HouseRecord houseGrad) throws SQLException, PreGISException {

        HashMap<String, ImportSupplierNotificationsOfOrderExecutionRequest.SupplierNotificationOfOrderExecution> paymentsFromGrad = new HashMap<>();

        try (Statement statement = connectionGrad.createStatement()) {
            ResultSet rs = statement.executeQuery(String.format("SELECT RORDERID, RAMOUNT, RORDERDATE, RPAYMENTDOCUMENTID FROM EX_GIS_PAYMENTS1(%d, %d)",
                    ResourcesUtil.instance().getCompanyGradId(),
                    houseGrad.getGrad_id()));

            while (rs.next()) {
                ImportSupplierNotificationsOfOrderExecutionRequest.SupplierNotificationOfOrderExecution notificationOfOrderExecutionType = new ImportSupplierNotificationsOfOrderExecutionRequest.SupplierNotificationOfOrderExecution();
                notificationOfOrderExecutionType.setTransportGUID(OtherFormat.getRandomGUID());
                notificationOfOrderExecutionType.setAmount(OtherFormat.getBigDecimalTwo(rs.getBigDecimal("RAMOUNT")));
                notificationOfOrderExecutionType.setOrderDate(OtherFormat.getDateForXML(rs.getDate("RORDERDATE")));
                notificationOfOrderExecutionType.setPaymentDocumentID(rs.getString("RPAYMENTDOCUMENTID"));
                paymentsFromGrad.put(rs.getString("RORDERID"), notificationOfOrderExecutionType);
            }

            if (!rs.isClosed()) {
                rs.close();
            }
        }
        if (paymentsFromGrad.size() < 1) {
            answerProcessing.sendMessageToClient("Нет платежей для выгрузки из Град по дому " + houseGrad.getAddresStringShort());
        }


        return paymentsFromGrad;
    }


    public void markPayments(final String reciepts, String recieptsSeparator) throws SQLException {
        try (PreparedStatement stmt = connectionGrad.prepareStatement("execute procedure EX_GIS_PAYMENTS2(?, ?)")) {
            stmt.setString(1, reciepts);
            stmt.setString(2, recieptsSeparator);
            stmt.execute();
        }

    }
}