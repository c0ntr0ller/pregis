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
    private HashMap<String, ImportPaymentDocumentRequest.PaymentInformation> paymentInformationMap;
    private int errorState;
    private int allCount;
    private int addedGisJkhCount;
    private int count = -1;

    public PaymentDocumentHandler(final AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    /**
     * Метод, формирует платежный документ, получает список ПД из ГИС ЖКХ, проводит проверку что ПД ещё не выгружен,
     * выгружает в ГИС ЖКХ.
     * @throws SQLException могут возникнуть ошибки во время работы с БД.
     * @throws PreGISException могут появится ошибка, если в файле параметров не указана ИД организации в Граде.
     * @throws ParseException могут возникнуть ошибки, если у абонента указана не верно площадь или идентификатор в Граде.
     * @param houseGradID идентификатор дома в БД Град.
     */
    public void compilePaymentDocument(final Integer houseGradID) throws SQLException, PreGISException, ParseException {

        errorState = 1;
        allCount = 0;
        addedGisJkhCount = 0;

        try (Connection connectionGrad = ConnectionBaseGRAD.instance().getConnection()) {

            final HouseGRADDAO houseDAO = new HouseGRADDAO(answerProcessing);
            final LinkedHashMap<String, Integer> houseMap = houseDAO.getListHouse(houseGradID, connectionGrad);
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendMessageToClient("Формирую платежные документы...");

//            Список домов в ГИС ЖКХ
            for (Map.Entry<String, Integer> entry : houseMap.entrySet()) {

//              Банковские реквизиты
                final PaymentInformationGradDAO dao = new PaymentInformationGradDAO();
                final HashMap<Integer, ImportPaymentDocumentRequest.PaymentInformation> paymentInformationIdsMap =
                        dao.getAllOrganizationsPaymentInformation(ConnectionBaseGRAD.instance().getConnection());

                final HousePaymentPeriodHandler periodHandler = new HousePaymentPeriodHandler(answerProcessing);
                final Calendar paymentPeriod = periodHandler.getHousePaymentPeriod(entry.getKey());

                final HashMap<ImportPaymentDocumentRequest.PaymentDocument, PaymentDocumentRegistryDataSet> paymentDocumentMap =
                        generatePaymentDocument(entry.getValue(), paymentPeriod, paymentInformationIdsMap, connectionGrad);

                paymentInformationMap = new HashMap<>();

                for (Map.Entry<Integer, ImportPaymentDocumentRequest.PaymentInformation> integerPaymentInformationEntry : paymentInformationIdsMap.entrySet()) {
                    paymentInformationMap.put(integerPaymentInformationEntry.getValue().getTransportGUID(), integerPaymentInformationEntry.getValue());
                }

                answerProcessing.clearLabelForText();
                allCount += paymentDocumentMap.size();
                sendDocumentToGisJkh(paymentDocumentMap, paymentPeriod);
            }
        }
        showEnd();
    }

    private HashMap<ImportPaymentDocumentRequest.PaymentDocument, PaymentDocumentRegistryDataSet> generatePaymentDocument(
            Integer houseId, Calendar paymentPeriod, HashMap<Integer,
            ImportPaymentDocumentRequest.PaymentInformation> paymentInformationIdsMap,
            Connection connectionGrad) throws SQLException, PreGISException, ParseException {

        PaymentDocumentGradDAO pdGradDao = new PaymentDocumentGradDAO(answerProcessing);

        HashMap<ImportPaymentDocumentRequest.PaymentDocument, PaymentDocumentRegistryDataSet> paymentMap = new HashMap<>();

        AccountGRADDAO accountGRADDAO = new AccountGRADDAO(answerProcessing);

        ArrayList<Rooms> rooms = accountGRADDAO.getRooms(houseId, connectionGrad);
        LinkedHashMap<String, ImportAccountRequest.Account> accountMapFromGrad =
                accountGRADDAO.getAccountMapFromGrad(houseId, connectionGrad);

        for (Rooms room : rooms) {

            TreeSet<Integer> organizationIds = getOrganizationIds(pdGradDao, room.getAbonId(), paymentPeriod, connectionGrad);
//            System.out.println("Organization ID: " + organizationIds);

            for (Integer organizationId : organizationIds) {

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
                        organizationId,
                        paymentPeriod, paymentDocument, connectionGrad);

                PaymentDocumentRegistryDataSet registryDataSet = new PaymentDocumentRegistryDataSet(
                        Integer.valueOf(paymentDocument.getPaymentDocumentNumber()), paymentPeriod.get(Calendar.MONTH),
                        paymentPeriod.get(Calendar.YEAR), paymentDocument.getTotalPiecemealPaymentSum(), room.getAbonId(),
                        room.getNumberLS(), paymentDocument.getAccountGuid());

                //            Ссылка на платежные реквизиты
                paymentDocument.setPaymentInformationKey(paymentInformationIdsMap.get(organizationId).getTransportGUID());

                paymentMap.put(paymentDocument, registryDataSet);
            }
        }
        return paymentMap;
    }

    /**
     * Метод, получает идентификаторы компаний, которые указаны получателями денежных средств для ПД.
     *
     * @param gradDAO        объект для работы с БД.
     * @param abonId         идентификатор абонента в БД ГРАД.
     * @param calendar       дата.
     * @param connectionGrad подключение к БД.
     * @return список идентификаторов организаций присутствующих в платежном документе.
     * @throws SQLException
     * @throws PreGISException
     */
    private TreeSet<Integer> getOrganizationIds(PaymentDocumentGradDAO gradDAO, Integer abonId, Calendar calendar,
                                                Connection connectionGrad) throws SQLException, PreGISException {

        if (ResourcesUtil.instance().getCompanyGradIdForPaymentDocument() == null) {
            return gradDAO.getOrganizationIdsFromPaymentsDocument(abonId, calendar, connectionGrad);
        } else {
            TreeSet<Integer> treeSet = new TreeSet<>();
            treeSet.add(ResourcesUtil.instance().getCompanyGradIdForPaymentDocument());
            return treeSet;
        }
    }

    /**
     * Метод, отправляет данные в ГИС ЖКХ, получает ответ и обрабатывает его.
     *
     * @param paymentDocumentMap связка, ключ - документ для ГИС ЖКХ, значение - объект для базы данных.
     * @param paymentPeriod      период за который формирутеся ПД.
     * @throws SQLException
     * @throws PreGISException
     */
    private void sendDocumentToGisJkh(HashMap<ImportPaymentDocumentRequest.PaymentDocument, PaymentDocumentRegistryDataSet> paymentDocumentMap,
                                      Calendar paymentPeriod) throws SQLException, PreGISException {


        PaymentDocumentRegistryDAO registryDAO = new PaymentDocumentRegistryDAO();

//        Записи из БД, для сравнения выгружен или нет.
        ArrayList<PaymentDocumentRegistryDataSet> allPaymentDocumentRecording = registryDAO.getAllPaymentDocumentRecording();

        ArrayList<ImportPaymentDocumentRequest.PaymentDocument> paymentDocumentList = new ArrayList<>();
        ArrayList<ImportPaymentDocumentRequest.PaymentInformation> paymentInformationList = new ArrayList<>();

        outer:
        for (Map.Entry<ImportPaymentDocumentRequest.PaymentDocument, PaymentDocumentRegistryDataSet> entry :
                paymentDocumentMap.entrySet()) {

//            Проверка, выгружен ли уже документ, если найдет его выгруженным вернется к новой записи
            for (PaymentDocumentRegistryDataSet dataSet : allPaymentDocumentRecording) {
                if (checkAddedDocument(entry.getValue(), dataSet)) continue outer;
            }

            if (!isAddedInformation(paymentInformationList, entry.getKey().getPaymentInformationKey())) {
                paymentInformationList.add(paymentInformationMap.get(entry.getKey().getPaymentInformationKey()));
            }

            paymentDocumentList.add(entry.getKey());

            if ((paymentDocumentList.size() % 10) == 0) { // отправляет по 10 документов
                sendPaymentDocumentsList(paymentDocumentList, paymentDocumentMap, paymentInformationList,
                        paymentPeriod, registryDAO);
                paymentDocumentList.clear();
                paymentInformationList.clear();
            }
        }

        if (paymentDocumentList.size() > 0) { // отправляет оставшиеся документы
            sendPaymentDocumentsList(paymentDocumentList, paymentDocumentMap, paymentInformationList,
                    paymentPeriod, registryDAO);
            paymentDocumentList.clear();
            paymentInformationList.clear();
        } else {
            answerProcessing.sendMessageToClient("Не найдены документы для выгрузки.");
        }
    }

    /**
     * Метод, дробит список листов на порции, если в одном щапросе возникнит ош
     *
     * @param paymentDocumentList список документов для отправки в ГИС ЖКХ.
     * @param paymentDocumentMap связка, ключ - документ для ГИС ЖКХ, значение - объект для базы данных.
     * @param paymentInformationList список компаний приема платежей.
     * @param paymentPeriod период за который формирутеся ПД.
     * @param registryDAO записывает данные в БД.
     * @throws SQLException
     * @throws PreGISException
     */
    private void sendPaymentDocumentsList(ArrayList<ImportPaymentDocumentRequest.PaymentDocument> paymentDocumentList, HashMap<ImportPaymentDocumentRequest.PaymentDocument, PaymentDocumentRegistryDataSet> paymentDocumentMap, ArrayList<ImportPaymentDocumentRequest.PaymentInformation> paymentInformationList, Calendar paymentPeriod, PaymentDocumentRegistryDAO registryDAO) throws SQLException, PreGISException {

        ImportResult result = sendPaymentDocuments(paymentDocumentList, paymentInformationList, paymentPeriod.get(Calendar.MONTH),
                (short) paymentPeriod.get(Calendar.YEAR));

        if (result != null && result.getCommonResult() != null) {
            for (CommonResultType resultType : result.getCommonResult()) {

                if (resultType.getError() != null && resultType.getError().size() > 0) {

                    showErrorMeteringDevices(resultType.getTransportGUID(),
                            resultType.getError().get(0).getErrorCode(),
                            resultType.getError().get(0).getDescription());
                } else {
//                        added to DB
                    setResultFromGisJkh(paymentDocumentMap, resultType, registryDAO);
                }
            }
        } else if (result == null) {
            setErrorState(-1);
        }
    }

    /**
     * Метод, находит в ответе нужную запись и добавляет её в БД для истории.
     *
     * @param paymentDocumentMap связка, ключ - документ для ГИС ЖКХ, значение - объект для базы данных.
     * @param resultType         ответ от ГИС ЖКХ.
     * @param registryDAO        записывает данные в БД.
     * @throws SQLException
     */
    private void setResultFromGisJkh(HashMap<ImportPaymentDocumentRequest.PaymentDocument, PaymentDocumentRegistryDataSet> paymentDocumentMap,
                                     CommonResultType resultType, PaymentDocumentRegistryDAO registryDAO) throws SQLException {

        for (Map.Entry<ImportPaymentDocumentRequest.PaymentDocument, PaymentDocumentRegistryDataSet> entry : paymentDocumentMap.entrySet()) {

            if (entry.getKey().getTransportGUID().equalsIgnoreCase(resultType.getTransportGUID())) {
                entry.getValue().setGuid(resultType.getGUID());
                entry.getValue().setUniqueNumber(resultType.getUniqueNumber());
                registryDAO.addPaymentDocumentRegistryItem(entry.getValue());
                addedGisJkhCount++;

                answerProcessing.sendMessageToClient("");
                answerProcessing.sendMessageToClient("GUID: " + resultType.getGUID());
                answerProcessing.sendMessageToClient("UniqueNumber: " + resultType.getUniqueNumber());
                answerProcessing.sendMessageToClient("TransportGUID: " + resultType.getTransportGUID());
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
    private ImportResult sendPaymentDocuments(List<ImportPaymentDocumentRequest.PaymentDocument> paymentDocumentList,
                                              List<ImportPaymentDocumentRequest.PaymentInformation> paymentInformationList,
                                              int month, short year) throws SQLException, PreGISException {

        ImportPaymentDocumentData importPaymentDocumentData = new ImportPaymentDocumentData(answerProcessing);
        ImportResult result = importPaymentDocumentData.sendPaymentDocument(paymentDocumentList,
                paymentInformationList,
                month,
                year);
        return result;
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
     * Метод, проверяет, добавлены платежные реквизиты компании в ПД.
     * @param paymentInformationList список с платежными реквизитами.
     * @param paymentInformationKey ключ платежных реквизитов.
     * @return true - если в списке найден идентификатор платежных реквизитов, false - если в списке не найдены реквизиты.
     */
    private boolean isAddedInformation(ArrayList<ImportPaymentDocumentRequest.PaymentInformation> paymentInformationList, String paymentInformationKey) {

        for (ImportPaymentDocumentRequest.PaymentInformation information : paymentInformationList) {
            if (information.getTransportGUID().equalsIgnoreCase(paymentInformationKey)) {
                return true;
            }
        }
        return false;
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
