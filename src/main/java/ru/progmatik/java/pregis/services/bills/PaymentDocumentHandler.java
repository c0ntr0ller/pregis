package ru.progmatik.java.pregis.services.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.CommonResultType;
import ru.gosuslugi.dom.schema.integration.bills.GetStateResult;
import ru.gosuslugi.dom.schema.integration.bills.ImportPaymentDocumentRequest;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.bills.PaymentDocumentGradDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.bills.PaymentDocumentRegistryDataSet;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;

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
    private int errorStatus;
    private int addedGisJkhCount;
    private int allCount;

    public PaymentDocumentHandler(final AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    /**
     * Метод получает список домов из Град и для каждого вызывает метод sendPaymentDocumentsHouse
     * выгружает в ГИС ЖКХ.
     * @throws SQLException могут возникнуть ошибки во время работы с БД.
     * @throws PreGISException могут появится ошибка, если в файле параметров не указана ИД организации в Граде.
     * @throws ParseException могут возникнуть ошибки, если у абонента указана не верно площадь или идентификатор в Граде.
     * @param houseGradID идентификатор дома в БД Град.
     */
    public void paymentDocumentImport(final Integer houseGradID) throws SQLException, PreGISException, ParseException {


        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {
            if (answerProcessing != null) { answerProcessing.sendMessageToClient("Формирую список домов...");}
            final HouseGRADDAO houseDAO = new HouseGRADDAO(answerProcessing);
            final LinkedHashMap<String, Integer> houseMap = houseDAO.getListHouse(houseGradID, connectionGRAD);
            if (answerProcessing != null) { answerProcessing.sendMessageToClient(""); }
//          Бежим по списку домов
            for (Map.Entry<String, Integer> houseEntry : houseMap.entrySet()) {
                sendPaymentDocumentsHouse(houseEntry.getKey(), houseEntry.getValue(), connectionGRAD);
            }
        }
    }

    /**
     * Метод для заданного дома получает информацию по документам из ГРАД, формирует importPaymentDocumentDataRequest,
     * отсылает его, получает результат и заносит идентификаторы в Град
     * @param fias
     * @param houseGradId
     * @param connectionGrad
     * @throws SQLException
     */
    public void sendPaymentDocumentsHouse(final String fias, final int houseGradId, final Connection connectionGrad) throws SQLException, ParseException, PreGISException {

        final PaymentDocumentGradDAO pdGradDao = new PaymentDocumentGradDAO(answerProcessing);

           // получаем закрытый период
        pdGradDao.getGradClosedPeriod(connectionGrad);
        int month = pdGradDao.getMonth();
        short year = pdGradDao.getYear();

        if (answerProcessing != null) { answerProcessing.sendMessageToClient("Обработка дома ФИАС: " + fias);}

        if (answerProcessing != null) { answerProcessing.sendMessageToClient("Формирую платежные документы для дома: " + fias);}
        // Генерация документов на стороне БД. Если успешно - создаем списки документов

        /*if (!pdGradDao.GeneratePaymentDocuments(houseGradId, connectionGrad)) {
            if (answerProcessing != null) { answerProcessing.sendMessageToClient("Не удалось сформировать платежные документы для дома: " + fias);}
        }
        */
        // создаем список платежных реквизитов получателей
        final HashMap<Integer, ImportPaymentDocumentRequest.PaymentInformation> paymentInformationMap =
                pdGradDao.getPaymentInformationMap(houseGradId, month, year, connectionGrad);

        // создаем список новых платежных документов по данным БД
        final HashMap<String, ImportPaymentDocumentRequest.PaymentDocument> paymentDocumentMap =
                pdGradDao.getPaymentDocumentMap(houseGradId, month, year, paymentInformationMap, connectionGrad);
        if (paymentDocumentMap != null) {
            allCount = paymentDocumentMap.size();
        }

        // создаем список документов на отзыв по данным БД
        final ArrayList<ImportPaymentDocumentRequest.WithdrawPaymentDocument> paymentDocumentWithdrawArray =
                pdGradDao.getWithdrawPaymentDocument(houseGradId, month, year, connectionGrad);

        if (answerProcessing != null) {answerProcessing.clearLabelForText();}

        if ((paymentDocumentMap == null || paymentDocumentMap.size() == 0) && (paymentDocumentWithdrawArray == null || paymentDocumentWithdrawArray.size() == 0)){
            if (answerProcessing != null) {
                answerProcessing.sendMessageToClient("Отсутствуют платежные документы для дома: " + fias);
            }
            return;
        }
        // формируем запрос
        final ImportPaymentDocumentRequest importPaymentDocumentRequest = new ImportPaymentDocumentRequest();
        importPaymentDocumentRequest.setMonth(month);
        importPaymentDocumentRequest.setYear(year);
        importPaymentDocumentRequest.getPaymentInformation().addAll(new ArrayList<>(paymentInformationMap.values()));
        if(paymentDocumentMap != null)
            importPaymentDocumentRequest.getPaymentDocument().addAll(new ArrayList<>(paymentDocumentMap.values()));
        if(paymentDocumentWithdrawArray != null && paymentDocumentWithdrawArray.size() > 0)
            importPaymentDocumentRequest.getWithdrawPaymentDocument().addAll(paymentDocumentWithdrawArray);

        // отсылаем результат в процедуру, которая вышлет данные и обработает ответ
        sendDocumentsToGisJkh(importPaymentDocumentRequest);
        showEnd();
    }

    /**
     * Метод отсылает данные в ГИС ЖКХ, получает ответ и обрабатывает его
     *
     * @param request - данные
     * @throws SQLException
     * @throws PreGISException
     */
    private void sendDocumentsToGisJkh(final ImportPaymentDocumentRequest request) throws SQLException, PreGISException {

        GetStateResult result = new PaymentDocumentsPort(answerProcessing).sendPaymentDocument(request);

        if (result != null && result.getImportResult() != null) {
            for (CommonResultType resultType : result.getImportResult()) {

                if (resultType.getError() != null && resultType.getError().size() > 0) {

                    showErrorPaymentDocument(resultType.getTransportGUID(),
                            resultType.getError().get(0).getErrorCode(),
                            resultType.getError().get(0).getDescription());
                } else {
//                        added to DB
                    setResultFromGisJkh(request.getPaymentDocument(), resultType);
                }
            }
        } else if (result == null) {
            setErrorStatus(-1);
        }
    }

    /**
     * Метод, находит в ответе нужную запись и добавляет её в БД для истории.
     *
     * @param paymentDocuments массив документов, отосланный в ГИС ЖКХ
     * @param resultType         ответ от ГИС ЖКХ.
     * @throws SQLException
     */
    private void setResultFromGisJkh(List<ImportPaymentDocumentRequest.PaymentDocument> paymentDocuments, CommonResultType resultType) throws SQLException {

        final PaymentDocumentGradDAO pdGradDao = new PaymentDocumentGradDAO(answerProcessing);
        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {
            for (ImportPaymentDocumentRequest.PaymentDocument entry : paymentDocuments) {
                if (entry.getTransportGUID().equalsIgnoreCase(resultType.getTransportGUID())) {
                    pdGradDao.addPaymentDocumentRegistryItem(entry.getPaymentDocumentNumber(), resultType.getGUID(), connectionGRAD);
                    addedGisJkhCount++;
                    answerProcessing.sendMessageToClient("");
                    answerProcessing.sendMessageToClient("GUID: " + resultType.getGUID());
                    answerProcessing.sendMessageToClient("UniqueNumber: " + resultType.getUniqueNumber());
                    answerProcessing.sendMessageToClient("TransportGUID: " + resultType.getTransportGUID());
                }
            }
        }
    }

    /**
     * Метод, отправляет запрос в ГИС ЖКХ.
     *
     * @param paymentDocumentList    список документов для отправки в ГИС ЖКХ.
     * @param paymentInformationList список компаний приема платежей.
     * @param month                  месяц.
     * @param year                   год.
     * @return результат из ГИС ЖКХ.
     * @throws SQLException
     * @throws PreGISException
     */
/*    private ImportResult sendPaymentDocuments(List<ImportPaymentDocumentRequest.PaymentDocument> paymentDocumentList,
                                              List<ImportPaymentDocumentRequest.PaymentInformation> paymentInformationList,
                                              int month, short year) throws SQLException, PreGISException {

        return new PaymentDocumentsPort(answerProcessing).sendPaymentDocument(paymentDocumentList,
                paymentInformationList,
                month,
                year);
    }*/

    /**
     * Метод, проверяет, являются платежные документы копиями.
     *
     * @param out данные документа для отправки.
     * @param in  данные о документе найденные в БД.
     * @return true - если документ найден в БД и его не нужно выгружать,
     * false - документы не похоже, можно выгружать в ГИС ЖКХ.
     */
    private boolean checkAddedDocument(final PaymentDocumentRegistryDataSet out,
                                       final PaymentDocumentRegistryDataSet in) {

        return (out.getMonth() == in.getMonth()) && (out.getYear() == in.getYear()) &&
                out.getSumma().equals(in.getSumma()) && (out.getAbonId() == in.getAbonId()) &&
                out.getAccountGuid().equals(in.getAccountGuid()) && !in.isArchive();
    }

    private void showEnd() {

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Всего обработано записей: " + allCount);
        answerProcessing.sendMessageToClient("Добавлено в ГИС ЖКХ: " + addedGisJkhCount);

        if (errorStatus == -1) {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendErrorToClientNotException("Возникла ошибка!\nОперация: \"Выгрузка ПД\" прервана!");
        } else if (errorStatus == 0) {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendErrorToClientNotException("Операция: \"Выгрузка ПД\" завершилась с ошибками!");
        } else if (errorStatus == 1) {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendOkMessageToClient("\"Выгрузка ПД\" успешно выполнено.");
        }
    }

    /**
     * Метод, проверяет, добавлены платежные реквизиты компании в ПД.
     * @param paymentInformationList список с платежными реквизитами.
     * @param paymentInformationKey ключ платежных реквизитов.
     * @return true - если в списке найден идентификатор платежных реквизитов, false - если в списке не найдены реквизиты.
     */
//    private boolean isAddedInformation(ArrayList<ImportPaymentDocumentRequest.PaymentInformation> paymentInformationList, String paymentInformationKey) {
//
//        for (ImportPaymentDocumentRequest.PaymentInformation information : paymentInformationList) {
//            if (information.getTransportGUID().equalsIgnoreCase(paymentInformationKey)) {
//                return true;
//            }
//        }
//        return false;
//    }

    /**
     * Метод, получает из БД последний номер и выдаёт при каждом запросе следующий номер.
     *
     * @return номер документа.
     */
//    private int getPaymentDocumentNumber() throws SQLException {
//        if (lastDocumentNumber == -1) {
//            PaymentDocumentRegistryGradDAO paymentGradDAO = new PaymentDocumentRegistryGradDAO(answerProcessing);
//            lastDocumentNumber = paymentGradDAO.getPaymentDocumentLastNumber(ConnectionBaseGRAD.instance().getConnection());
//        } else {
//            lastDocumentNumber++;
//        }
//        return lastDocumentNumber;
//    }

    public int getErrorStatus() {
        return errorStatus;
    }

    private void setErrorStatus(int errorStatus) {
        if (errorStatus < this.errorStatus) {
            this.errorStatus = errorStatus;
        }
    }
    /**
     * Метод, формирует и выводит пользователю информацию об ошибках, которые возвращает ГИС ЖКХ.
     *
     * @param transportGUID транспортный идентификатор.
     * @param errorCode     код ошибки.
     * @param description   описание ошибки.
     */
    private void showErrorPaymentDocument(final String transportGUID, final String errorCode, final String description) {
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("TransportGUID: " + transportGUID);
        answerProcessing.sendMessageToClient("Код ошибки: " + errorCode);
        answerProcessing.sendMessageToClient("Описание ошибки: " + description);
        setErrorStatus(0);
    }

}
