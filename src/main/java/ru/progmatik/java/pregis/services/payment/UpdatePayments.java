package ru.progmatik.java.pregis.services.payment;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.CommonResultType;
import ru.gosuslugi.dom.schema.integration.payment.GetStateResult;
import ru.gosuslugi.dom.schema.integration.payment.ImportNotificationsOfOrderExecutionRequest;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseRecord;
import ru.progmatik.java.pregis.connectiondb.grad.payment.PaymentGRADDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UpdatePayments {
    private static final Logger LOGGER = Logger.getLogger(UpdatePayments.class);
    private final AnswerProcessing answerProcessing;
    private int errorStatus;
    private int allCount;

    public UpdatePayments(final AnswerProcessing answerProcessing) {

        if(answerProcessing != null) {
            this.answerProcessing = answerProcessing;
        }else{
            this.answerProcessing = new AnswerProcessing();
        }
    }
    public void callSendPayments(final Integer houseGradID) throws SQLException, PreGISException {
        setErrorStatus(1);
        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {
            answerProcessing.sendMessageToClient("Формирую список домов...");
            final HouseGRADDAO houseDAO = new HouseGRADDAO(answerProcessing);
            final LinkedHashMap<String, HouseRecord> houseMap = houseDAO.getHouseRecords(houseGradID, connectionGRAD);
            answerProcessing.sendMessageToClient("");
//          Бежим по списку домов
            if (houseMap != null) {
                for (Map.Entry<String, HouseRecord> houseEntry : houseMap.entrySet()) {
                    try {
                        sendPaymentsHouse(houseEntry.getKey(), houseEntry.getValue(), connectionGRAD);
                    } catch (PreGISException e) {
                        answerProcessing.sendErrorToClient("callSendPayments(): ", "", LOGGER, e);
                        setErrorStatus(0);
                    }
                }
            }
        }

    }

    /**
     * Основной метод класса - запрашивает данные из град, формирует массив запросов и передает его на отправку
     * @param fias
     * @param houseGrad
     * @param connectionGrad
     * @throws PreGISException
     * @throws SQLException
     */
    private void sendPaymentsHouse(final String fias, final HouseRecord houseGrad, final Connection connectionGrad) throws PreGISException, SQLException {
        // получаем платежи из Град
        PaymentGRADDAO paymentGRADDAO = new PaymentGRADDAO(answerProcessing, connectionGrad);

        answerProcessing.sendMessageToClient("Получаем платежи из Град по дому " + houseGrad.getAddresStringShort() + "...");
        List<ImportNotificationsOfOrderExecutionRequest.NotificationOfOrderExecutionType> paymentsListGrad = paymentGRADDAO.getPaymentsFromGrad(houseGrad);

        if(paymentsListGrad == null || paymentsListGrad.size() == 0){
            answerProcessing.sendMessageToClient("В Град нет платежей для выгрузки в ГИС ЖКХ");
            return;
        }
        // формируем запросы в ГИС по 1000 платежей
        List<ImportNotificationsOfOrderExecutionRequest> requestList = compileImportNotificationsOfOrderExecutionRequest(paymentsListGrad);
        // отсылаем в ГИС и обрабатываем ответ

        if(requestList != null && requestList.size() > 0){
            for (ImportNotificationsOfOrderExecutionRequest request: requestList) {
                sendPaymentsToGisGKH(request, paymentGRADDAO);
            }
        }
    }

    private void sendPaymentsToGisGKH(final ImportNotificationsOfOrderExecutionRequest request, final PaymentGRADDAO paymentGRADDAO) throws SQLException, PreGISException {
        GetStateResult result = new PaymentAsyncPort(answerProcessing, "ImportNotificationsOfOrderExecution").interactPayments(request);

        if (result != null && result.getImportResult() != null) {
            answerProcessing.sendMessageToClient("Обработка результата импорта данных. Кол-во данных: " + result.getImportResult().size());
            for (CommonResultType resultType : result.getImportResult()) {

                if (resultType.getError() != null && resultType.getError().size() > 0) {

                    showErrorPayment(resultType.getTransportGUID(),
                            resultType.getError().get(0).getErrorCode(),
                            resultType.getError().get(0).getDescription());
                    setErrorStatus(0);
                }else {
//                   отмечаем в Град как отправленные
                    markPaymentsInGrad(request.getNotificationOfOrderExecutionType(), resultType, paymentGRADDAO);
                }
            }
        } else if (result == null) {
            setErrorStatus(0);
        }
    }

    /**
     * метод отмечает в Град отосланные платежи
     * @param request
     * @param resultType
     * @param paymentGRADDAO
     */
    private void markPaymentsInGrad(final List<ImportNotificationsOfOrderExecutionRequest.NotificationOfOrderExecutionType> request,
                                    final CommonResultType resultType,
                                    final PaymentGRADDAO paymentGRADDAO) throws SQLException {
            paymentGRADDAO.markPayments(request);
    }

    /**
     * метод показа ошибок
     * @param transportGUID
     * @param errorCode
     * @param description
     */
    private void showErrorPayment(final String transportGUID, final String errorCode, final String description) {
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("TransportGUID: " + transportGUID);
        answerProcessing.sendMessageToClient("Код ошибки: " + errorCode);
        answerProcessing.sendMessageToClient("Описание ошибки: " + description);
        setErrorStatus(0);
    }
    /**
     * метод для получения платежей из ГИС. Пока не реализован
     * @param houseGradID
     */
    public void callRecivePayments(final Integer houseGradID){

    }

    /**
     * Вспомогательный метод. Разбивает массив платежей на подмассивы не более 1000 в каждом
     * @param paymentsListGrad
     * @return
     */
    private List<ImportNotificationsOfOrderExecutionRequest> compileImportNotificationsOfOrderExecutionRequest(
            List<ImportNotificationsOfOrderExecutionRequest.NotificationOfOrderExecutionType> paymentsListGrad){

        // формируем запрос
        if (paymentsListGrad.size() == 0){
            answerProcessing.sendMessageToClient("Отсутствуют новые платежные документы!");
            return null;
        }

        allCount = paymentsListGrad.size();
        ArrayList<ImportNotificationsOfOrderExecutionRequest> importNotificationsOfOrderExecutionRequestArrayList = new ArrayList<>();

        int chunk = 1000; //не берем из настроек - в доке указано жестко 1000  ResourcesUtil.instance().getMaxRequestSize(); // chunk size to divide
        for(int i=0; i<paymentsListGrad.size(); i+=chunk){
            ArrayList<ImportNotificationsOfOrderExecutionRequest.NotificationOfOrderExecutionType> subarray = new ArrayList<>(paymentsListGrad.subList(i, Math.min(paymentsListGrad.size(),i+chunk)));

            final ImportNotificationsOfOrderExecutionRequest importNotificationsOfOrderExecutionRequest = new ImportNotificationsOfOrderExecutionRequest();

//            importNotificationsOfOrderExecutionRequest.setMonth(pdGradDao.getMonth());
//            importNotificationsOfOrderExecutionRequest.setYear(pdGradDao.getYear());
            importNotificationsOfOrderExecutionRequest.getNotificationOfOrderExecutionType().addAll(subarray);
            importNotificationsOfOrderExecutionRequestArrayList.add(importNotificationsOfOrderExecutionRequest);
        }

        return importNotificationsOfOrderExecutionRequestArrayList;
    }

    private void setErrorStatus(int errorStatus) {
        if (errorStatus < this.errorStatus) {
            this.errorStatus = errorStatus;
        }
    }
}
