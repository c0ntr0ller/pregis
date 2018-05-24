package ru.progmatik.java.pregis.services.payment;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.CommonResultType;
import ru.gosuslugi.dom.schema.integration.payment.GetStateResult;
import ru.gosuslugi.dom.schema.integration.payment.ImportSupplierNotificationsOfOrderExecutionRequest;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.payment.PaymentGRADDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.model.HouseRecord;
import ru.progmatik.java.pregis.other.AnswerProcessing;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

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
        HashMap<String, ImportSupplierNotificationsOfOrderExecutionRequest.SupplierNotificationOfOrderExecution> paymentsMapGrad = paymentGRADDAO.getPaymentsFromGrad(houseGrad);

        if(paymentsMapGrad == null || paymentsMapGrad.size() == 0){
            answerProcessing.sendMessageToClient("В Град нет платежей для выгрузки в ГИС ЖКХ");
            return;
        }
        // формируем запросы в ГИС по 1000 платежей
        List<ImportSupplierNotificationsOfOrderExecutionRequest> requestList = compileImportSupplierNotificationsOfOrderExecutionRequest(new ArrayList<>(paymentsMapGrad.values()));
        // отсылаем в ГИС и обрабатываем ответ

        if(requestList != null && requestList.size() > 0){
            for (ImportSupplierNotificationsOfOrderExecutionRequest request: requestList) {
                sendPaymentsToGisGKH(request, paymentsMapGrad, paymentGRADDAO);
            }
        }
    }

    /**
     * метод отсылает реквест в ГИС, получает результат и обрабатывает его (отсылает в Град отметки о высылке платежей)
     * @param request
     * @param paymentsMapGrad
     * @param paymentGRADDAO
     * @throws SQLException
     * @throws PreGISException
     */
    private void sendPaymentsToGisGKH(final ImportSupplierNotificationsOfOrderExecutionRequest request,
                                      final HashMap<String, ImportSupplierNotificationsOfOrderExecutionRequest.SupplierNotificationOfOrderExecution> paymentsMapGrad,
                                      final PaymentGRADDAO paymentGRADDAO) throws SQLException, PreGISException {
        GetStateResult result = new PaymentAsyncPort(answerProcessing, "ImportSupplierNotificationsOfOrderExecutionRequest").interactPayments(request);

        StringBuilder paymentDocumentIDs = new StringBuilder();
        StringBuilder uniqueNumbers = new StringBuilder();
        StringBuilder paymentGUIDs = new StringBuilder();
        if (result != null && result.getImportResult() != null) {
            answerProcessing.sendMessageToClient("Обработка результата импорта данных. Кол-во данных: " + result.getImportResult().size());
            for (CommonResultType resultType : result.getImportResult()) {

                if (resultType.getError() != null && resultType.getError().size() > 0) {

                    showErrorPayment(resultType.getTransportGUID(),
                            resultType.getError().get(0).getErrorCode(),
                            resultType.getError().get(0).getDescription());
                    setErrorStatus(0);
                }else {
//                    request.getSupplierNotificationOfOrderExecution().stream().collect(Collectors.toMap(ImportSupplierNotificationsOfOrderExecutionRequest.SupplierNotificationOfOrderExecution::getTransportGUID, Function.identity()))
                    paymentDocumentIDs.append(",").append(paymentsMapGrad.get(resultType.getTransportGUID()).getPaymentDocumentID());
                    uniqueNumbers.append(",").append(resultType.getUniqueNumber());
                    paymentGUIDs.append(",").append(resultType.getGUID());
                }
            }
            if(paymentDocumentIDs.length() > 0){
                paymentGRADDAO.markPayments(paymentDocumentIDs.toString(), uniqueNumbers.toString(), paymentGUIDs.toString());
            }
        } else if (result == null) {
            setErrorStatus(0);
        }
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
    private List<ImportSupplierNotificationsOfOrderExecutionRequest> compileImportSupplierNotificationsOfOrderExecutionRequest(
            List<ImportSupplierNotificationsOfOrderExecutionRequest.SupplierNotificationOfOrderExecution> paymentsListGrad){

        // формируем запрос
        if (paymentsListGrad.size() == 0){
            answerProcessing.sendMessageToClient("Отсутствуют новые платежные документы!");
            return null;
        }

        allCount = paymentsListGrad.size();
        ArrayList<ImportSupplierNotificationsOfOrderExecutionRequest> importSupplierNotificationsOfOrderExecutionRequestArrayList = new ArrayList<>();

        int chunk = 1000; //не берем из настроек - в доке указано жестко 1000  ResourcesUtil.instance().getMaxRequestSize(); // chunk size to divide
        for(int i=0; i<paymentsListGrad.size(); i+=chunk){
            ArrayList<ImportSupplierNotificationsOfOrderExecutionRequest.SupplierNotificationOfOrderExecution> starry = new ArrayList<>(paymentsListGrad.subList(i, Math.min(paymentsListGrad.size(),i+chunk)));

            final ImportSupplierNotificationsOfOrderExecutionRequest importSupplierNotificationsOfOrderExecutionRequest = new ImportSupplierNotificationsOfOrderExecutionRequest();

            importSupplierNotificationsOfOrderExecutionRequest.getSupplierNotificationOfOrderExecution().addAll(starry);
            importSupplierNotificationsOfOrderExecutionRequestArrayList.add(importSupplierNotificationsOfOrderExecutionRequest);
        }

        return importSupplierNotificationsOfOrderExecutionRequestArrayList;
    }

    private void setErrorStatus(int errorStatus) {
        if (errorStatus < this.errorStatus) {
            this.errorStatus = errorStatus;
        }
    }
}
