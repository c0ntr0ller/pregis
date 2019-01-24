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
import java.util.stream.Collectors;

public class UpdatePayments {
    private static final Logger LOGGER = Logger.getLogger(UpdatePayments.class);
    private final AnswerProcessing answerProcessing;
    private int errorStatus = 1;
    private int allCount;

    public UpdatePayments(final AnswerProcessing answerProcessing) {

        if(answerProcessing != null) {
            this.answerProcessing = answerProcessing;
        }else{
            this.answerProcessing = new AnswerProcessing();
        }
    }

    /**
     * метод формирует список домов для получения платежей и вызывает методе отсылки в ГИС ЖКХ
     * @param houseGradID
     * @return
     * @throws SQLException
     * @throws PreGISException
     */
    public int callSendPayments(final Integer houseGradID) throws SQLException, PreGISException {
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
                        sendPaymentsHouse(houseEntry.getValue(), connectionGRAD);
                    } catch (PreGISException e) {
                        answerProcessing.sendErrorToClient("callSendPayments(): ", "", LOGGER, e);
                        setErrorStatus(0);
                    }
                }
            }
        }
        return getErrorStatus();
    }

    /**
     * метод формирует соединение, запрашивает данные из Град, получет массив чеков и передает его на отправку
     * @param houseGrad
     * @param connectionGrad
     * @throws PreGISException
     * @throws SQLException
     */
    private void sendPaymentsHouse(final HouseRecord houseGrad, final Connection connectionGrad) throws PreGISException, SQLException {
        // получаем платежи из Град
        PaymentGRADDAO paymentGRADDAO = new PaymentGRADDAO(answerProcessing, connectionGrad);

        answerProcessing.sendMessageToClient("Получаем платежи из Град по дому " + houseGrad.getAddresStringShort() + "...");
        HashMap<String, ImportSupplierNotificationsOfOrderExecutionRequest.SupplierNotificationOfOrderExecution> paymentsMapGrad
                = paymentGRADDAO.getPaymentsFromGrad(houseGrad);

        if(paymentsMapGrad == null || paymentsMapGrad.size() == 0){
            return;
        }
        sendPaymentsToGisGKH(paymentsMapGrad, paymentGRADDAO);
    }

    /**
     * метод отсылает данные в ГИС, получает результат и обрабатывает его (отсылает в Град отметки о высылке платежей)
     * @param request
     * @param paymentsMapGrad
     * @param paymentGRADDAO
     * @throws SQLException
     * @throws PreGISException
     */
    private void sendPaymentsToGisGKH(final HashMap<String, ImportSupplierNotificationsOfOrderExecutionRequest.SupplierNotificationOfOrderExecution> paymentsMapGrad,
                                      final PaymentGRADDAO paymentGRADDAO) throws PreGISException, SQLException {
        // формируем запросы в ГИС по 1000 платежей
        List<ImportSupplierNotificationsOfOrderExecutionRequest> requestList
                = compileImportSupplierNotificationsOfOrderExecutionRequest(new ArrayList<>(paymentsMapGrad.values()));

        // отсылаем в ГИС и обрабатываем ответ
        if(requestList == null || requestList.size() == 0) {
            return;
        }

        Map<String, String> transport2ResieptMap = new HashMap<>();
        for (String reciept : paymentsMapGrad.keySet()) {
            if (transport2ResieptMap.put(paymentsMapGrad.get(reciept).getTransportGUID(), reciept) != null) {
                throw new IllegalStateException("Duplicate key");
            }
        }

        answerProcessing.sendMessageToClient(String.format("Высылаем чеки в ГИС ЖКХ в кол-ве %d", paymentsMapGrad.size()));

        String recieptsSeparator = "|";

        for (ImportSupplierNotificationsOfOrderExecutionRequest request: requestList) {
            GetStateResult result = PaymentAsyncPort.callImportPayments(request, answerProcessing);

            StringBuilder reciepts = new StringBuilder();

            if (result != null && result.getImportResult() != null) {
                answerProcessing.sendMessageToClient("Обработка результата импорта данных. Кол-во данных: " + result.getImportResult().size());
                for (CommonResultType resultType : result.getImportResult()) {

                    if (resultType.getError() != null && resultType.getError().size() > 0) {

                        showErrorPayment(resultType.getTransportGUID(),
                                resultType.getError().get(0).getErrorCode(),
                                resultType.getError().get(0).getDescription());
                        setErrorStatus(0);
                    }else {

                        // если чек прошел успешно - заносим его в строку на занесение в Град
                        String recieptSuccess = transport2ResieptMap.get(resultType.getTransportGUID());
                        if(recieptSuccess != null && !recieptSuccess.isEmpty())
                            reciepts.append(recieptSuccess).append(recieptsSeparator);

                    }
                }
                if(reciepts.length() > 0){
                    try {
                        paymentGRADDAO.markPayments(reciepts.toString(), recieptsSeparator);
                        setErrorStatus(1);
                    } catch (SQLException e) {
                        answerProcessing.sendInformationToClientAndLog("Не удалось выставить отметки на чеки; " + e.getMessage(), LOGGER);
                        setErrorStatus(0);
                    }
                }
            } else if (result == null) {
                setErrorStatus(0);
            }
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
     * Вспомогательный метод. Разбивает массив чеков на подмассивы не более 1000 в каждом и формирует массив запросов в ГИС ЖКХ
     * @param paymentsListGrad массив чеков
     * @return
     */
    private List<ImportSupplierNotificationsOfOrderExecutionRequest> compileImportSupplierNotificationsOfOrderExecutionRequest(
            List<ImportSupplierNotificationsOfOrderExecutionRequest.SupplierNotificationOfOrderExecution> paymentsListGrad){

        // формируем запрос
        if (paymentsListGrad.size() == 0){
            return null;
        }

        allCount = paymentsListGrad.size();
        ArrayList<ImportSupplierNotificationsOfOrderExecutionRequest> importSupplierNotificationsOfOrderExecutionRequestArrayList = new ArrayList<>();

        int chunk = 200; // снизил до 200, чота не пролазит //не берем из настроек - в доке указано жестко 1000  ResourcesUtil.instance().getMaxRequestSize(); // chunk size to divide
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

    public int getErrorStatus() {
        return errorStatus;
    }
}
