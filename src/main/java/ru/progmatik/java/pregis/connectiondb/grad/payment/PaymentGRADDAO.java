package ru.progmatik.java.pregis.connectiondb.grad.payment;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.payment.ImportNotificationsOfOrderExecutionRequest;
import ru.gosuslugi.dom.schema.integration.payments_base.NotificationOfOrderExecutionType;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseRecord;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class PaymentGRADDAO {


    private static final Logger LOGGER = Logger.getLogger(PaymentGRADDAO.class);
    private final AnswerProcessing answerProcessing;
    private int month;
    private short year;
    private int stateError;
    private Connection connectionGrad;

    public int getMonth() {
        return month;
    }

    public short getYear() {
        return year;
    }

    public PaymentGRADDAO(AnswerProcessing answerProcessing, Connection connectionGrad) throws SQLException {
        if (answerProcessing != null) {
            this.answerProcessing = answerProcessing;
        } else {
            this.answerProcessing = new AnswerProcessing();
        }
        this.connectionGrad = connectionGrad;
        getGradClosedPeriod();
    }

    /**
     * Метод запрашивает из Града месяц и год закрытого периода
     * метод временный, потом будем спрашивать у пользователя
     */
    private void getGradClosedPeriod() throws SQLException {
        try (Statement statement = connectionGrad.createStatement()) {

            ResultSet rs = statement.executeQuery("select RMONTH, RYEAR from EX_GIS_PERIOD(null)");
            if (rs.next() && rs.getInt(1) > 0 && rs.getInt(2) > 0) {
                this.month = rs.getInt(1);
                this.year = rs.getShort(2);
            } else {
                Date date = new Date(System.currentTimeMillis());
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                this.month = cal.get(Calendar.MONTH);
                this.year = (short) cal.get(Calendar.YEAR);
            }
        }
    }

    public List<ImportNotificationsOfOrderExecutionRequest.NotificationOfOrderExecutionType> getPaymentsFromGrad(final HouseRecord houseGrad) throws SQLException {
        List<ImportNotificationsOfOrderExecutionRequest.NotificationOfOrderExecutionType> paymentsFromGrad = new ArrayList<>();
        try (Statement statement = connectionGrad.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT RPAYEE_ID, RBANK_ACCOUNT, RBIK FROM EX_GIS_PAYMENTS1(" + month + "," + year + "," + houseGrad.getGrad_id() + ")");

            while (rs.next()) {
                ImportNotificationsOfOrderExecutionRequest.NotificationOfOrderExecutionType notificationOfOrderExecutionType = new ImportNotificationsOfOrderExecutionRequest.NotificationOfOrderExecutionType();
                notificationOfOrderExecutionType.setTransportGUID(OtherFormat.getRandomGUID());
                NotificationOfOrderExecutionType.OrderInfo orderInfo = notificationOfOrderExecutionType.getOrderInfo();
// TODO доделать
//                orderInfo.
//                if (rs.getString("RBANK_ACCOUNT") != null && rs.getString("RBIK") != null) {
//                }
                paymentsFromGrad.add(notificationOfOrderExecutionType);
            }

            if (!rs.isClosed()) {
                rs.close();
            }
        }
        if (paymentsFromGrad.size() < 1) {
            answerProcessing.sendInformationToClientAndLog("Не удалось получить платежи из Град по дому " + houseGrad.getAddresStringShort(), LOGGER);
        }


        return paymentsFromGrad;
    }


    public void markPayment(ImportNotificationsOfOrderExecutionRequest.NotificationOfOrderExecutionType notification){
        try {
             connectionGrad.createStatement().execute(
                     String.format("update EX_GIS_PAYMENTS set checked = 1, transport_guid = '%s' where pay_id = %s",
                             notification.getTransportGUID(),
                             notification.getOrderInfo().getOrderID())
             );
        }catch(SQLException e){
            answerProcessing.sendInformationToClientAndLog("Не удалось выставить отметку платежу ИД " +  notification.getOrderInfo().getOrderID() + "; " + e.getMessage(), LOGGER);
        }
    }
}