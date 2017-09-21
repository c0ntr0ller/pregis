package ru.progmatik.java.pregis.services.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.Attachment;
import ru.gosuslugi.dom.schema.integration.base.AttachmentType;
import ru.gosuslugi.dom.schema.integration.base.CommonResultType;
import ru.gosuslugi.dom.schema.integration.bills.*;
import ru.gosuslugi.dom.schema.integration.bills.GetStateResult;
import ru.gosuslugi.dom.schema.integration.house_management.*;
import ru.gosuslugi.dom.schema.integration.nsi_base.NsiRef;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.bills.PaymentDocumentGradDAO;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.bills.PaymentDocumentRegistryDataSet;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;
import ru.progmatik.java.pregis.services.house_management.ExportAccountData;
import ru.progmatik.java.pregis.services.house_management.HouseContractDataPort;
import ru.progmatik.java.pregis.services.house_management.AccountIndividualServicesPort;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseRecord;

import javax.xml.datatype.XMLGregorianCalendar;
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

        if(answerProcessing != null) {
            this.answerProcessing = answerProcessing;
        }else{
            this.answerProcessing = new AnswerProcessing();
        }
    }

    /**
     * Метод получает список домов из Град и для каждого вызывает метод sendPaymentDocumentsHouse
     * выгружает в ГИС ЖКХ.
     * @throws SQLException могут возникнуть ошибки во время работы с БД.
     * @throws PreGISException могут появится ошибка, если в файле параметров не указана ИД организации в Граде.
     * @throws ParseException могут возникнуть ошибки, если у абонента указана не верно площадь или идентификатор в Граде.
     * @param houseGradID идентификатор дома в БД Град.
     */
    public void callPaymentDocumentImport(final Integer houseGradID) throws SQLException, ParseException, PreGISException {

        setErrorStatus(1);
        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {
            answerProcessing.sendMessageToClient("Формирую список домов...");
            final HouseGRADDAO houseDAO = new HouseGRADDAO(answerProcessing);
            final LinkedHashMap<String, HouseRecord> houseMap = houseDAO.getAllHouseFIASAddress(houseGradID, connectionGRAD);
            answerProcessing.sendMessageToClient("");
//          Бежим по списку домов
            if (houseMap != null) {
                for (Map.Entry<String, HouseRecord> houseEntry : houseMap.entrySet()) {
                    try {
                        sendPaymentDocumentsHouse(houseEntry.getKey(), houseEntry.getValue(), connectionGRAD);
                    } catch (PreGISException e) {
                        answerProcessing.sendErrorToClient("callPaymentDocumentImport(): ", "", LOGGER, e);
                        setErrorStatus(0);
                    }
                }
            }
        }
    }

    /**
     * Метод для заданного дома получает информацию по документам из ГРАД, формирует importPaymentDocumentDataRequest,
     * отсылает его, получает результат и заносит идентификаторы в Град
     * @param fias
     * @param houseGrad
     * @param connectionGrad
     * @throws SQLException
     */
    private void sendPaymentDocumentsHouse(final String fias, final HouseRecord houseGrad, final Connection connectionGrad) throws SQLException, ParseException, PreGISException {

        final PaymentDocumentGradDAO pdGradDao = new PaymentDocumentGradDAO(answerProcessing, connectionGrad);

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Обработка дома : " + houseGrad.getAddresString());

        // создаем новые документы, все старые - закрываются
        if(!generateNewDocuments(houseGrad.getGrad_id(), pdGradDao)) {
            setErrorStatus(0);
            return;
        }

        // формируем запрос на отзыв документов
        ArrayList<ImportPaymentDocumentRequest.WithdrawPaymentDocument> syncWithDrawList = synchronizeDocuments(fias, houseGrad, pdGradDao);

            // если есть документы на закрытие после синхронизации - добавляем их
        if (syncWithDrawList.size() > 0) {
            final ImportPaymentDocumentRequest withdrawPaymentDocumentRequest = new ImportPaymentDocumentRequest();

            withdrawPaymentDocumentRequest.getWithdrawPaymentDocument().addAll(syncWithDrawList);
            answerProcessing.sendMessageToClient("Отзываются платежные документы по дому");
            // отсылаем результат в процедуру, которая вышлет данные в ГИС и обработает ответ
            sendDocumentsToGisJkh(withdrawPaymentDocumentRequest, pdGradDao);
        }

        // формируем массив запросов
        final List<ImportPaymentDocumentRequest> importPaymentDocumentRequestList = compileImportDocumentRequest(fias, houseGrad.getGrad_id(), pdGradDao);

        if(importPaymentDocumentRequestList != null && importPaymentDocumentRequestList.size() > 0) {

            // синхронизируем услуги документов с услугами дома
            synchronizeContracts(houseGrad, importPaymentDocumentRequestList, pdGradDao);

            for (ImportPaymentDocumentRequest request : importPaymentDocumentRequestList) {
                if (request != null) {
                    // отсылаем результат в процедуру, которая вышлет данные в ГИС и обработает ответ
                    sendDocumentsToGisJkh(request, pdGradDao);
                }
            }
        }
        showEnd();
    }

    /**
     * Метод синхронизирует услуги в высылаемых документах и в доме в ГИС. Недостающие услуги - создаются
     * @param houseGrad запись дома
     * @param importPaymentDocumentRequestList - массив запросов на создание платежных документов
     */
    private void synchronizeContracts(final HouseRecord houseGrad, List<ImportPaymentDocumentRequest> importPaymentDocumentRequestList, final PaymentDocumentGradDAO pdGradDao) throws SQLException, ParseException, PreGISException {
        // запрашиваем услуги из ГИС
        HouseContractDataPort contractDataPort = new HouseContractDataPort(answerProcessing);
        ru.gosuslugi.dom.schema.integration.house_management.GetStateResult resultContract = contractDataPort.callExportHouseContracts(houseGrad.getFias());

        // если нет контрактов - выдаем ошибку
        if(resultContract == null || resultContract.getExportCAChResult() == null || resultContract.getExportCAChResult().size() == 0){
            answerProcessing.sendMessageToClient("Не найден договор управления в ГИС ЖКХ, сверка не производится");
            return;
        }

        // получаем действующий контракт
        ExportCAChResultType.Contract curContract = null;
        for(ExportCAChResultType caChResultType:resultContract.getExportCAChResult()){
            if(caChResultType.getContract().getContractStatus().equals("Approved")){
                curContract = caChResultType.getContract();
            }
        }
        if(curContract == null){
            curContract = resultContract.getExportCAChResult().get(0).getContract();
        }

        if(curContract == null){
            throw new PreGISException("Не найден ни один договор управления на доме в ГИС ЖКХ");
        }
        // по контракту получаем список услуг на доме (на доме, а не на абонентах!!!)
        HashMap<String, NsiRef> gisServices = contractDataPort.getHouseServices(curContract);

        // вспомогательный класс для вывода сообщений
        class AlarmServ{
            String serviceType; // тип услуги - коммунальная или другое
            String serviceName; // имя услуги
            AlarmServ(String serviceType, String serviceName){
                this.serviceType = serviceType;
                this.serviceName = serviceName;
            }
        }

        HashMap<String, AlarmServ> alarmServices = new HashMap<>();

        // бежим по реквесту с документами и сравниваем услуги документов и контракта
        for (ImportPaymentDocumentRequest request: importPaymentDocumentRequestList) {
            for (ImportPaymentDocumentRequest.PaymentDocument paymentDocument: request.getPaymentDocument()) {
                Iterator<PaymentDocumentType.ChargeInfo> it = paymentDocument.getChargeInfo().iterator();
                while (it.hasNext()) {
                    PaymentDocumentType.ChargeInfo chargeInfo = it.next();
                    // коммунальные

                    if(chargeInfo.getMunicipalService() == null && chargeInfo.getHousingService() == null && chargeInfo.getAdditionalService() == null)
                    {
                        paymentDocument.getChargeInfo().remove(chargeInfo);
                        continue;
                    }

                    if(chargeInfo.getMunicipalService() != null){
                        // если услуга не содержится
                        if(!gisServices.containsKey(chargeInfo.getMunicipalService().getServiceType().getGUID())){
                            // если услуга еще не алярмируется - добавляем ее в массив
                            if(!alarmServices.containsKey(chargeInfo.getMunicipalService().getServiceType().getGUID())){
                                alarmServices.put(chargeInfo.getMunicipalService().getServiceType().getGUID(), new AlarmServ("коммунальная", chargeInfo.getMunicipalService().getServiceType().getName()));
                            }
//                            HashMap<String, String> accountNLS = pdGradDao.getAbonentNLSbyGUIDFromGrad(paymentDocument.getAccountGuid());
//                            for(Map.Entry<String,String> address: accountNLS.entrySet()) {
//
//                                answerProcessing.sendInformationToClientAndLog("Внимание!\nНа лицевом счете " + address.getKey() + " по адресу " +
//                                        address.getValue() + " присутствует не заведенная на доме коммунальная услуга " + chargeInfo.getMunicipalService().getServiceType().getName() + ". Если она является субуслугой в ГИС, то, возможно, ошибки нет", LOGGER);
//                            }
//                            it.remove(); // по коммунальной не убираем, так как может быть субуслугой
                        }
                    }
                    // жилищные
                    if(chargeInfo.getHousingService() != null){
                        if(!gisServices.containsKey(chargeInfo.getHousingService().getServiceType().getGUID())){
                            if(!alarmServices.containsKey(chargeInfo.getHousingService().getServiceType().getGUID())){
                                alarmServices.put(chargeInfo.getHousingService().getServiceType().getGUID(), new AlarmServ("жилищная", chargeInfo.getHousingService().getServiceType().getName()));
                            }
//                            HashMap<String, String> accountNLS = pdGradDao.getAbonentNLSbyGUIDFromGrad(paymentDocument.getAccountGuid());
//                            for(Map.Entry<String,String> address: accountNLS.entrySet()) {
//                                answerProcessing.sendInformationToClientAndLog("Внимание!\nНа лицевом счете " + address.getKey() + " по адресу " +
//                                        address.getValue() + " присутствует не заведенная на доме жилищная услуга " + chargeInfo.getHousingService().getServiceType().getName(), LOGGER);
//                            }

                            it.remove();
                        }
                    }
                    // допуслуги
                    if(chargeInfo.getAdditionalService() != null){
                        if(!gisServices.containsKey(chargeInfo.getAdditionalService().getServiceType().getGUID())){
                            if(!alarmServices.containsKey(chargeInfo.getAdditionalService().getServiceType().getGUID())){
                                alarmServices.put(chargeInfo.getAdditionalService().getServiceType().getGUID(), new AlarmServ("дополнительная", chargeInfo.getAdditionalService().getServiceType().getName()));
                            }
//                            HashMap<String, String> accountNLS = pdGradDao.getAbonentNLSbyGUIDFromGrad(paymentDocument.getAccountGuid());
//                            for(Map.Entry<String,String> address: accountNLS.entrySet()) {
//                                answerProcessing.sendInformationToClientAndLog("Внимание!\nНа лицевом счете " + address.getKey() + " по адресу " +
//                                        address.getValue() + " присутствует не заведенная на доме доп.услуга " + chargeInfo.getAdditionalService().getServiceType().getName(), LOGGER);
//                            }
                            it.remove();
                        }
                    }
                }
            }

            if(alarmServices.size() > 0){
                for(Map.Entry<String, AlarmServ> service: alarmServices.entrySet()) {
                    answerProcessing.sendInformationToClientAndLog("Внимание! В документах на выгрузку присутствует не заведенная в ГИС " + service.getValue().serviceType + " услуга " + service.getValue().serviceName, LOGGER);
                }
            }
        }


/* TODO Разобраться с файлами аттачмента
        // составляем массив услуг из запросов
        HashMap<String, NsiRef> requestServices = new HashMap<> ();
        for (ImportPaymentDocumentRequest request: importPaymentDocumentRequestList) {
            for (ImportPaymentDocumentRequest.PaymentDocument paymentDocument: request.getPaymentDocument()) {
                for (PaymentDocumentType.ChargeInfo chargeInfo: paymentDocument.getChargeInfo()) {
                    if(chargeInfo.getHousingService() != null){
                        requestServices.computeIfAbsent(chargeInfo.getHousingService().getServiceType().getGUID(), k -> chargeInfo.getHousingService().getServiceType());
                    }
                    if(chargeInfo.getAdditionalService() != null){
                        requestServices.computeIfAbsent(chargeInfo.getAdditionalService().getServiceType().getGUID(), k -> chargeInfo.getAdditionalService().getServiceType());
                    }
                }
            }
        }

        // сопоставляем списки и получаем список недостающих услуг
        HashMap<String, NsiRef> absentServices = new HashMap<>();
        for (Map.Entry<String, NsiRef> requestEntry: requestServices.entrySet()) {
            if (!gisServices.containsKey(requestEntry.getKey()))
                absentServices.put(requestEntry.getKey(), requestEntry.getValue());
        }

        // создаем недостающие
        if(absentServices.size() > 0){
            contractDataPort.callCreateNewContracts(fias, absentServices, resultContract);
        }
*/
    }

    /**
     * Метод получает услуги абонента из ГИС и проверяет - входит ли в его услуги указанная услуга из начислений
     * @param chargeInfo
     * @param accountGUID
     * @return
     * @throws SQLException
     */
    private boolean accountContainsService(final PaymentDocumentType.ChargeInfo chargeInfo,
                                           final String accountGUID) throws SQLException {

        AccountIndividualServicesPort accountIndividualServicesPort = new AccountIndividualServicesPort(answerProcessing);
        ru.gosuslugi.dom.schema.integration.house_management.GetStateResult stateResult = accountIndividualServicesPort.callExportAccountIndividualServices(accountGUID);

        for(ExportAccountIndividualServicesResultType servicesResultType: stateResult.getExportAccountIndividualServicesResult()){
            if(servicesResultType.getAccountGUID().equals(accountGUID)){
                if(servicesResultType.getAccountIndividualServiceGUID().equals(chargeInfo.getAdditionalService().getServiceType().getGUID())){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Метод создает запрос на добавление индивидуальной услуги абоненту и вызывает его
     * @param chargeInfo Информация о начислении. Отсюда будем брать название услуги и ее ИД
     * @param accountGUID GUID абонента
     * @param beginDate Дата начала контракта на дом
     * @param endDate Дата конца контракта на дом
     * @throws SQLException
     */
    private void importIndividualService(final PaymentDocumentType.ChargeInfo chargeInfo,
                                         final String accountGUID,
                                         final XMLGregorianCalendar beginDate,
                                         final XMLGregorianCalendar endDate) throws SQLException {

        // если у абонента уже есть такая услуга - ничего не делаем
        if(accountContainsService(chargeInfo, accountGUID)) return;

        // собираем запрос на добавление услуги
        ImportAccountIndividualServicesRequest.IndividualService individualService = new ImportAccountIndividualServicesRequest.IndividualService();
        individualService.setAdditionalService(chargeInfo.getAdditionalService().getServiceType());
        individualService.setBeginDate(beginDate);
        individualService.setEndDate(endDate);

        AttachmentType attachmentType = new AttachmentType();

        attachmentType.setName("Добавление услуги " + chargeInfo.getAdditionalService().getServiceType().getName());
        attachmentType.setDescription("Добавление услуги " + chargeInfo.getAdditionalService().getServiceType().getName());
        attachmentType.setAttachment(new Attachment());
        attachmentType.getAttachment().setAttachmentGUID(OtherFormat.getRandomGUID());

        individualService.setAttachment(attachmentType);
        individualService.setAccountGUID(accountGUID);
        individualService.setTransportGUID(OtherFormat.getRandomGUID());

        ImportAccountIndividualServicesRequest request = new ImportAccountIndividualServicesRequest();
        request.setIndividualService(individualService);

        // отсылаем запрос на добавление услуги. результат нас особо не интересует
        AccountIndividualServicesPort accountIndividualServicesPort = new AccountIndividualServicesPort(answerProcessing);
        ru.gosuslugi.dom.schema.integration.house_management.GetStateResult StateResult = accountIndividualServicesPort.callImportAccountIndividualServices(request);
    }

    /**
     * Метод собирает массив запросов с новыми документами в ГИС
     * @param houseGradId ИД дома в Град
     * @param pdGradDao Объект работы с Градом
     * @return массив подготовленных к отправке запросов
     */
    private List<ImportPaymentDocumentRequest> compileImportDocumentRequest(final String fias, final int houseGradId, final PaymentDocumentGradDAO pdGradDao) throws SQLException, PreGISException, ParseException {

        answerProcessing.sendMessageToClient("Создается список платежных реквизитов получателей по дому");
        final HashMap<Integer, ImportPaymentDocumentRequest.PaymentInformation> paymentInformationMap =
                pdGradDao.getPaymentInformationMap(houseGradId);

        if(paymentInformationMap == null || paymentInformationMap.size() == 0){
            answerProcessing.sendMessageToClient("Список получателей пуст, ничего не высылается в ГИС");
            return null;
        }

        ArrayList<ImportPaymentDocumentRequest.PaymentDocument> paymentDocumentArrayList = null;
        answerProcessing.sendMessageToClient("Создается список новых платежных документов");
        HashMap<String, ImportPaymentDocumentRequest.PaymentDocument> paymentDocumentHashMap = pdGradDao.getPaymentDocumentMap(houseGradId, paymentInformationMap);
        if (paymentDocumentHashMap != null) {
            paymentDocumentArrayList = new ArrayList<>(paymentDocumentHashMap.values());
        }
        //        answerProcessing.sendMessageToClient("Создается список документов на отзыв");
//        final ArrayList<ImportPaymentDocumentRequest.WithdrawPaymentDocument> withdrawPaymentDocuments =
//                pdGradDao.getWithdrawPaymentDocument(houseGradId);

        answerProcessing.clearLabelForText();

        // формируем запрос
        if (paymentDocumentArrayList == null || paymentDocumentArrayList.size() == 0){
            answerProcessing.sendMessageToClient("Отсутствуют новые платежные документы!");
            return null;
        }

        allCount = paymentDocumentArrayList.size();
        ArrayList<ImportPaymentDocumentRequest> importPaymentDocumentRequestArrayList = new ArrayList<>();

        int chunk = ResourcesUtil.instance().getMaxRequestSize();; // chunk size to divide
        for(int i=0; i<paymentDocumentArrayList.size(); i+=chunk){
            ArrayList<ImportPaymentDocumentRequest.PaymentDocument> subarray = new ArrayList<>(paymentDocumentArrayList.subList(i, Math.min(paymentDocumentArrayList.size(),i+chunk)));

            final ImportPaymentDocumentRequest importPaymentDocumentRequest = new ImportPaymentDocumentRequest();

            importPaymentDocumentRequest.setMonth(pdGradDao.getMonth());
            importPaymentDocumentRequest.setYear(pdGradDao.getYear());
            importPaymentDocumentRequest.getPaymentInformation().addAll(paymentInformationMap.values());

            importPaymentDocumentRequest.getPaymentDocument().addAll(subarray);
            importPaymentDocumentRequestArrayList.add(importPaymentDocumentRequest);
        }

        return importPaymentDocumentRequestArrayList;
    }

    /**
     * Метод собирает запрос на отзыв документов на основании данных Град
     * @param houseGradId
     * @param pdGradDao
     * @return
     */
    private ImportPaymentDocumentRequest compileWithdrawDocuments(final int houseGradId, final PaymentDocumentGradDAO pdGradDao) throws ParseException, SQLException, PreGISException {

//        answerProcessing.sendMessageToClient("Создается список платежных реквизитов получателей по дому");
//        final HashMap<Integer, ImportPaymentDocumentRequest.PaymentInformation> paymentInformationMap =
//                pdGradDao.getPaymentInformationMap(houseGradId);

        answerProcessing.sendMessageToClient("Создается список документов на отзыв");
        final ArrayList<ImportPaymentDocumentRequest.WithdrawPaymentDocument> withdrawPaymentDocuments =
                pdGradDao.getWithdrawPaymentDocument(houseGradId);

        answerProcessing.clearLabelForText();

        final ImportPaymentDocumentRequest withdrawPaymentDocumentRequest = new ImportPaymentDocumentRequest();

        if (withdrawPaymentDocuments != null && withdrawPaymentDocuments.size() > 0) {
            // формируем запрос

//            withdrawPaymentDocumentRequest.setMonth(pdGradDao.getMonth());
//            withdrawPaymentDocumentRequest.setYear(pdGradDao.getYear());
//            withdrawPaymentDocumentRequest.getPaymentInformation().addAll(paymentInformationMap.values());

            withdrawPaymentDocumentRequest.getWithdrawPaymentDocument().addAll(withdrawPaymentDocuments);
        }
            return withdrawPaymentDocumentRequest;
    }

    /**
     * Метод запрашивает список ЛС из ГИС, потом по нему строит запрос на ЕПД этих ЛС, запрашивает документы из
     * Град, сравнивает и формирует список документов на отзыв из ГИС
     * @param fias ФИАС дома
     * @param houseGrad ИД дома в Град
     * @param pdGradDao объект для связи с Град
     * @return список документов на отзыв
     * @throws SQLException
     * @throws PreGISException
     * @throws ParseException
     */
    private ArrayList<ImportPaymentDocumentRequest.WithdrawPaymentDocument> synchronizeDocuments(final String fias, final HouseRecord houseGrad, final PaymentDocumentGradDAO pdGradDao) throws SQLException, PreGISException, ParseException {

        ArrayList<ImportPaymentDocumentRequest.WithdrawPaymentDocument> paymentDocumentWithdrawArray = new ArrayList<>();

        answerProcessing.sendMessageToClient("Синхронизируются имеющиеся платежные документы с ГИС ЖКХ");
        // запрашиваем текущие документы по дому из Град
        final HashMap<String, ImportPaymentDocumentRequest.PaymentDocument> paymentDocumentMapGrad =
                pdGradDao.getPaymentDocumentMap(houseGrad.getGrad_id(), null);

        // запрашиваем документы из Гис
        final List<ExportPaymentDocumentResultType> paymentDocumentGisList = getPaymentDocumentsFromGIS(fias, pdGradDao.getMonth(), pdGradDao.getYear());

        // сравниваем и делаем поправки в Град

        // все, которые есть в ГИС, но отсутствуют в Град - добавляем в список на закрытие
        if(paymentDocumentGisList != null) {
            for (ExportPaymentDocumentResultType paymentDocumentGis : paymentDocumentGisList) {
                // если еще не отозван в ГИС
                if (paymentDocumentGis.getPaymentDocument().isWithdraw() == null || !paymentDocumentGis.getPaymentDocument().isWithdraw()) {
                    if (
                        // в Граде вообще ничего нет
                            paymentDocumentMapGrad == null
                                    // документ в Граде есть, но он отозван
                                    || (paymentDocumentMapGrad.containsKey(paymentDocumentGis.getPaymentDocument().getPaymentDocumentNumber())
                                    && paymentDocumentMapGrad.get(paymentDocumentGis.getPaymentDocument().getPaymentDocumentNumber()).isWithdraw() != null
                            )
                                    // в Граде нет с таким номером ЕПД
                                    || !paymentDocumentMapGrad.containsKey(paymentDocumentGis.getPaymentDocument().getPaymentDocumentNumber())
                                    // в Граде есть, но без ГИД
                                    || paymentDocumentMapGrad.get(paymentDocumentGis.getPaymentDocument().getPaymentDocumentNumber()).getPaymentDocumentID() == null
                                    // в Граде есть с таким ЕПД, но не совпадают ИД документов
                                    || !paymentDocumentMapGrad.get(paymentDocumentGis.getPaymentDocument().getPaymentDocumentNumber()).getPaymentDocumentID().equals(paymentDocumentGis.getPaymentDocument().getPaymentDocumentID())) {


                        ImportPaymentDocumentRequest.WithdrawPaymentDocument withdrawPaymentDocument = new ImportPaymentDocumentRequest.WithdrawPaymentDocument();
                        withdrawPaymentDocument.setPaymentDocumentID(paymentDocumentGis.getPaymentDocument().getPaymentDocumentID());
                        withdrawPaymentDocument.setTransportGUID(OtherFormat.getRandomGUID());
                        paymentDocumentWithdrawArray.add(withdrawPaymentDocument);
                    }
                }
            }
        }

        return paymentDocumentWithdrawArray;
    }


    private List<ExportPaymentDocumentResultType> getPaymentDocumentsFromGIS(final String fias, final int month, final short year) throws SQLException, PreGISException {
        // формируем запрос в ГИС и получаем абонентов
        List<ExportAccountResultType> accountList = new ExportAccountData(answerProcessing).callExportAccountData(fias).getExportAccountResult();

        // по абонентам формируем запрос на платежные документы
        ExportPaymentDocumentRequest exportPaymentDocumentRequest = new ExportPaymentDocumentRequest();
        exportPaymentDocumentRequest.setFIASHouseGuid(fias);
        exportPaymentDocumentRequest.setMonth(month);
        exportPaymentDocumentRequest.setYear(year);

        for (ExportAccountResultType account: accountList) {
            exportPaymentDocumentRequest.getAccountNumber().add(account.getAccountNumber());
        }

        GetStateResult stateResult = new PaymentDocumentsPort(answerProcessing).interactionPaymentDocument(null, exportPaymentDocumentRequest);
        if(stateResult != null) return stateResult.getExportPaymentDocResult();
        return null;
    }

    private boolean generateNewDocuments(final int houseGradId, final PaymentDocumentGradDAO pdGradDao) throws PreGISException {
        answerProcessing.sendMessageToClient("Формируются платежные документы по дому в ГРАД");
        // Генерация документов на стороне БД. Если успешно - создаем списки документов
        boolean result = pdGradDao.generatePaymentDocuments(houseGradId);
        if (!result) {
            answerProcessing.sendMessageToClient("Не удалось сформировать платежные документы");
        }else{
            answerProcessing.sendMessageToClient("Формирование документов завершено");
        }
        return result;
    }
    /**
     * Метод отсылает запросы на импорт/экспорт в ГИС ЖКХ, получает ответ и обрабатывает его
     *
     * @param request - данные
     * @param pdGradDao - класс обмена с БД ГРАДа
     * @throws SQLException
     * @throws PreGISException
     */
    private void sendDocumentsToGisJkh(final ImportPaymentDocumentRequest request, final PaymentDocumentGradDAO pdGradDao) throws SQLException, PreGISException {

        GetStateResult result = new PaymentDocumentsPort(answerProcessing).interactionPaymentDocument(request, null);

        if (result != null && result.getImportResult() != null) {
            answerProcessing.sendMessageToClient("Обработка результата импорта данных. Кол-во данных: " + request.getPaymentDocument().size());
            for (CommonResultType resultType : result.getImportResult()) {

                if (resultType.getError() != null && resultType.getError().size() > 0) {

                    showErrorPaymentDocument(resultType.getTransportGUID(),
                            resultType.getError().get(0).getErrorCode(),
                            resultType.getError().get(0).getDescription());
                    setErrorStatus(0);
                } else {
//                   если передавали новые документы - засылаем их в Град
                    if(request.getPaymentDocument().size() > 0) {
                        setResultFromGisJkh(request.getPaymentDocument(), resultType, pdGradDao);
                    }
                }
            }
        } else if (result == null) {
            setErrorStatus(0);
        }
    }

    /**
     * Метод, находит в ответе нужную запись и добавляет её в БД для истории.
     *
     * @param paymentDocuments массив документов, отосланный в ГИС ЖКХ
     * @param resultType         ответ от ГИС ЖКХ.
     * @param pdGradDao - класс обмена с БД ГРАДа
     * @throws SQLException
     */
    private void setResultFromGisJkh(List<ImportPaymentDocumentRequest.PaymentDocument> paymentDocuments, CommonResultType resultType, final PaymentDocumentGradDAO pdGradDao) throws SQLException {
        for (ImportPaymentDocumentRequest.PaymentDocument entry : paymentDocuments) {
            if (entry.getTransportGUID().equalsIgnoreCase(resultType.getTransportGUID())) {
                pdGradDao.addPaymentDocumentRegistryItem(entry.getPaymentDocumentNumber(), resultType.getGUID());
                addedGisJkhCount++;
//                answerProcessing.sendMessageToClient("");
//                answerProcessing.sendMessageToClient("GUID: " + resultType.getGUID());
//                answerProcessing.sendMessageToClient("UniqueNumber: " + resultType.getUniqueNumber());
//                answerProcessing.sendMessageToClient("TransportGUID: " + resultType.getTransportGUID());
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

        return new PaymentDocumentsPort(answerProcessing).interactionPaymentDocument(paymentDocumentList,
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
                out.getAccountGuid().equalsIgnoreCase(in.getAccountGuid()) && !in.isArchive();
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
