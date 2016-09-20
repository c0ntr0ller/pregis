package ru.progmatik.java.pregis.services.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.CommonResultType;
import ru.gosuslugi.dom.schema.integration.base.ImportResult;
import ru.gosuslugi.dom.schema.integration.bills.ImportPaymentDocumentRequest;
import ru.gosuslugi.dom.schema.integration.bills.PaymentDocumentType;
import ru.gosuslugi.dom.schema.integration.house_management.ImportAccountRequest;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.account.AccountGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.Rooms;
import ru.progmatik.java.pregis.connectiondb.grad.bills.PaymentDocumentGradDAO;
import ru.progmatik.java.pregis.connectiondb.grad.bills.PaymentInformationGradDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.bills.PaymentDocumentRegistryDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.bills.PaymentDocumentRegistryDataSet;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;

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
    private int errorState;
    private int allCount;
    private int addedGisJkhCount;
    private int count = -1;

    public PaymentDocumentHandler(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    public void compilePaymentDocument() throws SQLException, PreGISException, ParseException {

        errorState = 1;
        allCount = 0;
        addedGisJkhCount = 0;

        try (Connection connectionGrad = ConnectionBaseGRAD.instance().getConnection()) {

            HouseGRADDAO houseDAO = new HouseGRADDAO();
            LinkedHashMap<String, Integer> houseMap = houseDAO.getHouseAddedGisJkh(connectionGrad);
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendMessageToClient("Формирую платежные документы...");

//            Список домов в ГИС ЖКХ
            for (Map.Entry<String, Integer> entry : houseMap.entrySet()) {

//              Банковские реквизиты
                PaymentInformationGradDAO dao = new PaymentInformationGradDAO();
                ImportPaymentDocumentRequest.PaymentInformation paymentInformation =
                        dao.getPaymentInformation(ConnectionBaseGRAD.instance().getConnection());

                HousePaymentPeriodHandler periodHandler = new HousePaymentPeriodHandler(answerProcessing);
                Calendar paymentPeriod = periodHandler.getHousePaymentPeriod(entry.getKey());

                HashMap<ImportPaymentDocumentRequest.PaymentDocument, PaymentDocumentRegistryDataSet> paymentDocumentMap =
                        generatePaymentDocument(entry.getValue(), paymentPeriod, connectionGrad);
                answerProcessing.clearLabelForText();
                allCount += paymentDocumentMap.size();
                sendDocumentToGisJkh(paymentDocumentMap, paymentInformation, paymentPeriod);
            }
        }
        showEnd();
    }

    private HashMap<ImportPaymentDocumentRequest.PaymentDocument, PaymentDocumentRegistryDataSet> generatePaymentDocument(
            Integer houseId, Calendar paymentPeriod, Connection connectionGrad) throws SQLException, PreGISException, ParseException {

        PaymentDocumentGradDAO pdGradDao = new PaymentDocumentGradDAO(answerProcessing);

        HashMap<ImportPaymentDocumentRequest.PaymentDocument, PaymentDocumentRegistryDataSet> paymentMap = new HashMap<>();

        AccountGRADDAO accountGRADDAO = new AccountGRADDAO(answerProcessing);

        ArrayList<Rooms> rooms = accountGRADDAO.getRooms(houseId, connectionGrad);
        LinkedHashMap<String, ImportAccountRequest.Account> accountMapFromGrad =
                accountGRADDAO.getAccountMapFromGrad(houseId, connectionGrad);

        for (Rooms room : rooms) {

//                    Создадим платежный документ
            ImportPaymentDocumentRequest.PaymentDocument paymentDocument = new ImportPaymentDocumentRequest.PaymentDocument();
//                    Идентификатор лицевого счета
            paymentDocument.setAccountGuid(accountGRADDAO.getAccountGUIDFromBase(room.getAbonId(), connectionGrad));
//                    Номер платежного документа, по которому внесена плата, присвоенный такому документу исполнителем в целях осуществления расчетов по внесению платы
            paymentDocument.setPaymentDocumentNumber(String.format("%010d", getPaymentDocumentNumber()));
//                    Общая площадь для ЛС
            paymentDocument.setAddressInfo(new PaymentDocumentType.AddressInfo());
            paymentDocument.getAddressInfo().setTotalSquare(accountMapFromGrad.get(room.getNumberLS()).getTotalSquare());
            paymentDocument.getAddressInfo().setResidentialSquare(accountMapFromGrad.get(room.getNumberLS()).getResidentialSquare());
            if (accountMapFromGrad.get(room.getNumberLS()).getHeatedArea() != null) {
                paymentDocument.getAddressInfo().setHeatedArea(accountMapFromGrad.get(room.getNumberLS()).getHeatedArea());
            }
            if (accountMapFromGrad.get(room.getNumberLS()).getLivingPersonsNumber() != 0) {
                paymentDocument.getAddressInfo().setLivingPersonsNumber(accountMapFromGrad.get(room.getNumberLS()).getLivingPersonsNumber());
            }
            paymentDocument.setTransportGUID(OtherFormat.getRandomGUID());
//                    Начисление по услуге
            paymentDocument = pdGradDao.getPaymentDocument(
                    room.getAbonId(),
                    ResourcesUtil.instance().getCompanyGradId(),
                    paymentPeriod, paymentDocument, connectionGrad);

            PaymentDocumentRegistryDataSet registryDataSet = new PaymentDocumentRegistryDataSet(
                    Integer.valueOf(paymentDocument.getPaymentDocumentNumber()), paymentPeriod.get(Calendar.MONTH),
                    paymentPeriod.get(Calendar.YEAR), paymentDocument.getTotalPiecemealPaymentSum(), room.getAbonId(),
                    paymentDocument.getAccountGuid());

            paymentMap.put(paymentDocument, registryDataSet);
        }
        return paymentMap;
    }

    /**
     * Метод, отправляет данные в ГИС ЖКХ, получает ответ и обрабатывает его.
     *
     * @param paymentDocumentMap связка, ключ - документ для ГИС ЖКХ, значение - объект для базы данных.
     * @param paymentInformation информация о платежных реквизитах.
     * @param paymentPeriod      период за который формирутеся ПД.
     * @throws SQLException
     * @throws PreGISException
     */
    private void sendDocumentToGisJkh(HashMap<ImportPaymentDocumentRequest.PaymentDocument, PaymentDocumentRegistryDataSet> paymentDocumentMap,
                                      ImportPaymentDocumentRequest.PaymentInformation paymentInformation,
                                      Calendar paymentPeriod) throws SQLException, PreGISException {

        ImportPaymentDocumentData importPaymentDocumentData = new ImportPaymentDocumentData(answerProcessing);
        PaymentDocumentRegistryDAO registryDAO = new PaymentDocumentRegistryDAO();

        ArrayList<PaymentDocumentRegistryDataSet> allPaymentDocumentRecording = registryDAO.getAllPaymentDocumentRecording();

        outer:
        for (Map.Entry<ImportPaymentDocumentRequest.PaymentDocument, PaymentDocumentRegistryDataSet> entry :
                paymentDocumentMap.entrySet()) {

            for (PaymentDocumentRegistryDataSet dataSet : allPaymentDocumentRecording) {
                if (checkAddedDocument(entry.getValue(), dataSet)) continue outer;
            }

            ImportResult result = importPaymentDocumentData.sendPaymentDocument(entry.getKey(), paymentInformation,
                    paymentPeriod.get(Calendar.MONTH), (short) paymentPeriod.get(Calendar.YEAR));

            if (result != null && result.getCommonResult() != null) {
                for (CommonResultType resultType : result.getCommonResult()) {

                    if (resultType.getError() != null && resultType.getError().size() > 0) {
                        showErrorMeteringDevices(resultType.getTransportGUID(),
                                resultType.getError().get(0).getErrorCode(),
                                resultType.getError().get(0).getDescription());
                    } else {
//                        added to DB
                        entry.getValue().setGuid(resultType.getGUID());
                        entry.getValue().setUniqueNumber(resultType.getUniqueNumber());
                        registryDAO.setPaymentDocumentRegistryItemToArchive(entry.getValue());

                        addedGisJkhCount++;

                        answerProcessing.sendMessageToClient("GUID: " + resultType.getGUID());
                        answerProcessing.sendMessageToClient("UniqueNumber: " + resultType.getUniqueNumber());
                        answerProcessing.sendMessageToClient("TransportGUID: " + resultType.getTransportGUID());
                        answerProcessing.sendMessageToClient("");
                    }
                }
            } else if (result == null) {
                setErrorState(-1);
            }
//            TODO убрать break, временно.
            break;
        }
    }

    /**
     * Метод, проверяет, являются платежные документы копиями.
     *
     * @param out данные документа для отправки.
     * @param in  данные о документе найденные в БД.
     * @return true - если документ найден в БД и его не нужно выгружать,
     * false - документы не похоже, можно выгружать в ГИС ЖКХ.
     */
    private boolean checkAddedDocument(PaymentDocumentRegistryDataSet out, PaymentDocumentRegistryDataSet in) {

        return (out.getMonth() == in.getMonth()) && (out.getYear() == in.getYear()) &&
                out.getSumma().equals(in.getSumma()) && (out.getAbonId() == in.getAbonId()) &&
                out.getAccountGuid().equals(in.getAccountGuid()) && !in.isArchive();
    }

    /**
     * Метод, формирует и выводит пользователю информацию об ошибках, которые возвращает ГИС ЖКХ.
     *
     * @param transportGUID транспортный идентификатор.
     * @param errorCode     код ошибки.
     * @param description   описание ошибки.
     */
    private void showErrorMeteringDevices(String transportGUID, String errorCode, String description) {

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("TransportGUID: " + transportGUID);
        answerProcessing.sendMessageToClient("Код ошибки: " + errorCode);
        answerProcessing.sendMessageToClient("Описание ошибки: " + description);

        setErrorState(0);
    }

    private void showEnd() {

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Всего обработано записей: " + allCount);
        answerProcessing.sendMessageToClient("Добавлено в ГИС ЖКХ: " + addedGisJkhCount);

        if (errorState == -1) {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendErrorToClientNotException("Возникла ошибка!\nОперация: \"Выгрузка ПД\" прервана!");
        } else if (errorState == 0) {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendErrorToClientNotException("Операция: \"Выгрузка ПД\" завершилась с ошибками!");
        } else if (errorState == 1) {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendOkMessageToClient("\"Выгрузка ПД\" успешно выполнено.");
        }
    }

    /**
     * Метод, получает из БД последний номер и выдаёт при каждом запросе следующий номер.
     *
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

    public int getErrorState() {
        return errorState;
    }

    private void setErrorState(int errorState) {
        if (errorState < this.errorState) {
            this.errorState = errorState;
        }
    }
}
