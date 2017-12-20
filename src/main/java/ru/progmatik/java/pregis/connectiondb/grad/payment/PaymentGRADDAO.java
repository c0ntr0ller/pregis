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
import java.util.stream.Collectors;


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
            ResultSet rs = statement.executeQuery("SELECT ORDERID, ORDERDATE, ORDERNUM, AMOUNT, PAYMENTPURPOSE, \"COMMENT\", " +
                            "PAYMENTDOCUMENTID, PAYMENTDOCUMENTNUMBER, \"YEAR\", \"MONTH\", UNIFIEDACCOUNTNUMBER, FIASHOUSEGUID, SERVICE, ACCOUNTNUMBER " +
                    "FROM EX_GIS_PAYMENTS1(" + houseGrad.getGrad_id() + ")");

            while (rs.next()) {
                ImportNotificationsOfOrderExecutionRequest.NotificationOfOrderExecutionType notificationOfOrderExecutionType = new ImportNotificationsOfOrderExecutionRequest.NotificationOfOrderExecutionType();
                notificationOfOrderExecutionType.setTransportGUID(OtherFormat.getRandomGUID());
                NotificationOfOrderExecutionType.OrderInfo orderInfo = new NotificationOfOrderExecutionType.OrderInfo();

                orderInfo.setOrderID(String.format("%32s", rs.getString("ORDERID"), 32,'0').replace(" ", "0"));
                orderInfo.setOrderDate(OtherFormat.getDateForXML(rs.getDate("ORDERDATE")));
                orderInfo.setOrderNum(rs.getString("ORDERNUM"));
                orderInfo.setAmount(OtherFormat.getBigDecimalTwo(rs.getBigDecimal("AMOUNT")));
                orderInfo.setPaymentPurpose(rs.getString("PAYMENTPURPOSE"));
                orderInfo.setComment(rs.getString("COMMENT"));
                orderInfo.setPaymentDocumentID(rs.getString("PAYMENTDOCUMENTID"));
                orderInfo.setPaymentDocumentNumber(rs.getString("PAYMENTDOCUMENTNUMBER"));
                orderInfo.setYear(rs.getShort("YEAR"));
                orderInfo.setMonth(rs.getInt("MONTH"));
                orderInfo.setUnifiedAccountNumber(rs.getString("UNIFIEDACCOUNTNUMBER"));
                orderInfo.setAccountNumber(rs.getString("ACCOUNTNUMBER"));

// пока отключили, так как хотя само поле необязательное, но при его указании у него много обязательных полей
//                NotificationOfOrderExecutionType.OrderInfo.AddressAndConsumer addressAndConsumer = new NotificationOfOrderExecutionType.OrderInfo.AddressAndConsumer();
//                addressAndConsumer.setFIASHouseGuid(rs.getString("FIASHOUSEGUID"));
//                orderInfo.setAddressAndConsumer(addressAndConsumer);

                if(rs.getString("SERVICE") != null && rs.getString("SERVICE").length() > 0) {

                    NotificationOfOrderExecutionType.OrderInfo.Service service = new NotificationOfOrderExecutionType.OrderInfo.Service();
                    service.setServiceID(rs.getString("SERVICE"));
                    orderInfo.setService(service);
                }

                notificationOfOrderExecutionType.setOrderInfo(orderInfo);

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


    public void markPayments(final List<ImportNotificationsOfOrderExecutionRequest.NotificationOfOrderExecutionType> request) throws SQLException {
            final String separator = ";";
            final String orderIDsString = request.stream().map(notificationOfOrderExecutionType -> notificationOfOrderExecutionType.getOrderInfo().getComment()).collect(Collectors.joining(separator));
            PreparedStatement stmnt = connectionGrad.prepareStatement("execute procedure EX_GIS_PAYMENTS2(?, ?)");
            for (ImportNotificationsOfOrderExecutionRequest.NotificationOfOrderExecutionType notification: request) {
                try{
                    stmnt.setString(1, orderIDsString);
                    stmnt.setString(2, separator);
                    stmnt.execute();
                }catch(SQLException e){
                    answerProcessing.sendInformationToClientAndLog("Не удалось выставить отметку чеку ИД " +  notification.getOrderInfo().getPaymentDocumentNumber() + "; " + e.getMessage(), LOGGER);
                }
            }

    }
}