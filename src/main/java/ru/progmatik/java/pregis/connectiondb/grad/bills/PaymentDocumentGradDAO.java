package ru.progmatik.java.pregis.connectiondb.grad.bills;

import com.google.inject.internal.Nullable;
import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.bills.*;
import ru.gosuslugi.dom.schema.integration.nsi_base.NsiRef;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.Invoice01;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.Invoice02;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemDataSet;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ServicesGisJkhForGradDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ServicesGisJkhForGradDataSet;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, получает из БД ГРАДа информацию о платежном документе.
 */
public final class PaymentDocumentGradDAO {

    private static final Logger LOGGER = Logger.getLogger(PaymentDocumentGradDAO.class);
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

    public PaymentDocumentGradDAO(AnswerProcessing answerProcessing, Connection connectionGrad) throws SQLException {
        if(answerProcessing != null) {
            this.answerProcessing = answerProcessing;
        }else{
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
            if(rs.next()&& rs.getInt(1) > 0 && rs.getInt(2) > 0) {
                this.month = rs.getInt(1);
                this.year = rs.getShort(2);
            }else{
                Date date = new Date(System.currentTimeMillis());
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                this.month = cal.get(Calendar.MONTH);
                this.year = (short) cal.get(Calendar.YEAR);
            }
        }
    }

    /**
     * Метод, отправляет в БД Града запрос, формирующий платежные документы по дому в БД.
     *
     * @param houseGradID          идентификатор дома в БД ГРАДа
     */

    public boolean generatePaymentDocuments(final Integer houseGradID) throws PreGISException {
        try {
            CallableStatement callableStatement = connectionGrad.prepareCall("execute procedure EX_GIS_INVOICE_FILL(?, ?, ?, ?, null)");
            callableStatement.setInt(1, this.getMonth());
            callableStatement.setShort(2, this.getYear());
            callableStatement.setInt(3, ResourcesUtil.instance().getCompanyGradId());
            callableStatement.setInt(4, houseGradID);
            callableStatement.execute();
        }catch(SQLException e){
            answerProcessing.sendInformationToClientAndLog("Не удалось сгенерировать ЕПД по дому " +  houseGradID, LOGGER);
            answerProcessing.sendInformationToClientAndLog(e.getMessage(), LOGGER);
            return false;
        }
        return true;
    }

    public HashMap<Integer, ImportPaymentDocumentRequest.PaymentInformation> getPaymentInformationMap(final Integer houseGradID)
            throws SQLException, PreGISException {

        HashMap<Integer, ImportPaymentDocumentRequest.PaymentInformation> paymentInformationMap = new HashMap<>();

        try (Statement statement = connectionGrad.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT RPAYEE_ID, RBANK_ACCOUNT, RBIK FROM EX_GIS_INVOCE03(" + month + "," + year + "," + houseGradID + ")");

            while (rs.next()) {
                if (rs.getString("RBANK_ACCOUNT") != null && rs.getString("RBIK") != null) {

                    ImportPaymentDocumentRequest.PaymentInformation paymentInformation = new ImportPaymentDocumentRequest.PaymentInformation();
                    paymentInformation.setTransportGUID(OtherFormat.getRandomGUID());
                    paymentInformation.setOperatingAccountNumber(rs.getString("RBANK_ACCOUNT"));
                    paymentInformation.setBankBIK(rs.getString("RBIK"));

                    paymentInformationMap.put(rs.getInt("RPAYEE_ID"), paymentInformation);
                }
            }
            if (!rs.isClosed()) {
                rs.close();
            }
        }
        if (paymentInformationMap.size() < 1) {
            answerProcessing.sendInformationToClientAndLog("Не удалось получить реквизиты из процедуры EX_GIS_INVOCE03 по дому Град ИД " + houseGradID, LOGGER);
        }
        return paymentInformationMap;
    }

    /**
     * Метод формирует мапу документов на оплату из Град
     * @param houseGradID - ИД дома в Граде
     * @param paymentInformationMap - мапа с платежными реквизитами
     * @return
     * @throws SQLException
     * @throws PreGISException
     * @throws ParseException
     */
    public HashMap<String, ImportPaymentDocumentRequest.PaymentDocument> getPaymentDocumentMap(
            final int houseGradID,
            @Nullable final HashMap<Integer, ImportPaymentDocumentRequest.PaymentInformation> paymentInformationMap) throws SQLException, PreGISException, ParseException {

        final HashMap<String, ImportPaymentDocumentRequest.PaymentDocument> paymentDocumentMap = new HashMap<>();

        // получаем заголовки платежных документов по абонентам
        HashMap<String, Invoice01> accountsMapGrad = getAccountMap(houseGradID);

        // если нечего посылать - возвращаем пусто
        if (accountsMapGrad == null || accountsMapGrad.size() == 0){
            return null;
        }

        // получаем начисления абонентов
        HashMap<String, ArrayList<Invoice02>> chargesMapGrad = getChargesMap(houseGradID);

        // если нечего посылать - возвращаем пусто
        if (chargesMapGrad == null || chargesMapGrad.size() == 0){
            return null;
        }

        // бежим по мапе заголовков платежных документов из Града
        for (Map.Entry<String, Invoice01> accountGrad: accountsMapGrad.entrySet()) {
            // выделяем начисления данного ЛС
            ArrayList<Invoice02> accountCharges = chargesMapGrad.get(accountGrad.getValue().getPd_no());
            // если начислений нет - не отсылаем этот адрес, переходим к другому
            if (accountCharges == null || accountCharges.size() == 0) continue;

            // GUID абонента проверяем
            if(accountGrad.getValue().getAccountguid() == null || accountGrad.getValue().getAccountguid().equals("")){
                if(answerProcessing != null) {
                    answerProcessing.sendInformationToClientAndLog("Не указан Accountguid у ЕПД с номером " + accountGrad.getValue().getPd_no(), LOGGER);
                    answerProcessing.clearLabelForText();
                }
                continue;
            }

            //Создадим платежный документ
            ImportPaymentDocumentRequest.PaymentDocument paymentDocument = new ImportPaymentDocumentRequest.PaymentDocument();
            // GUID абонента
            paymentDocument.setAccountGuid(accountGrad.getValue().getAccountguid());
            //Номер платежного документа, по которому внесена плата, присвоенный такому документу исполнителем в целях осуществления расчетов по внесению платы
            paymentDocument.setPaymentDocumentNumber(accountGrad.getValue().getPd_no());
//            paymentDocument.setPaymentDocumentNumber(String.format("%010d", accountGrad.getValue().getPd_no()));

            // создаем Адресные сведения
/* ушло в версии 11.2.0.18
            paymentDocument.setAddressInfo(new PaymentDocumentType.AddressInfo());
            // Количество проживающих
            paymentDocument.getAddressInfo().setLivingPersonsNumber(accountGrad.getValue().getTencount());
            // Жилая площадь
            paymentDocument.getAddressInfo().setResidentialSquare(accountGrad.getValue().getSq_live());
            // Отапливаемая площадь
            paymentDocument.getAddressInfo().setHeatedArea(accountGrad.getValue().getSq_heat());
            // Общая площадь для ЛС
            paymentDocument.getAddressInfo().setTotalSquare(accountGrad.getValue().getSq_total());
*/
            // заносим начисления
            for (Invoice02 invoce02: accountCharges) {
                PaymentDocumentType.ChargeInfo chargeInfo = new PaymentDocumentType.ChargeInfo();
                if (invoce02.getCode_parent().equals("51")) { // если Коммунальная услуга
//                    TODO
                    chargeInfo.setMunicipalService(new PDServiceChargeType.MunicipalService());

//                    Код услуги (жилищной, коммунальной или дополнительной)
                    chargeInfo.getMunicipalService().setServiceType(new NsiRef());
                    chargeInfo.getMunicipalService().getServiceType().setName(invoce02.getGis_service());
                    chargeInfo.getMunicipalService().getServiceType().setCode(invoce02.getCode());
                    chargeInfo.getMunicipalService().getServiceType().setGUID(invoce02.getGis_service_uiid());

//                    Тариф
                    chargeInfo.getMunicipalService().setRate(getBigDecimalTwo(invoce02.getTariff()));

//                    Итого к оплате за расчетный период, руб.
                    chargeInfo.getMunicipalService().setTotalPayable(getBigDecimalTwo(invoce02.getForpay()));

//                    Всего начислено за расчетный период (без перерасчетов и льгот), руб.
                    chargeInfo.getMunicipalService().setAccountingPeriodTotal(getBigDecimalTwo(invoce02.getCharge_total()));

//                        Порядок расчетов ??? TODO
//                    chargeInfo.getMunicipalService().setCalcExplanation("Иное");

                    // перерасчеты
                    if (invoce02.getRepays() != null || invoce02.getExempts() != null ||
                            !invoce02.getRepays().equals(new BigDecimal(0)) || !invoce02.getExempts().equals(new BigDecimal(0))) {
                        chargeInfo.getMunicipalService().setServiceCharge(new ServiceChargeType());
//                        Перерасчеты, корректировки (руб)
                        chargeInfo.getMunicipalService().getServiceCharge().setMoneyRecalculation(getBigDecimalTwo(invoce02.getRepays()));
//                          Льготы, субсидии, скидки (руб)
                        chargeInfo.getMunicipalService().getServiceCharge().setMoneyDiscount(getBigDecimalTwo(invoce02.getExempts()));
                    }

//                        Рассрочка. Раздел 6. Иваненко Дмитрий сказал, что у наших клиентов нет таких.
//                        chargeInfo.getMunicipalService().setPiecemealPayment(new PiecemealPayment());

//                        Перерасчеты. Раздел 5.
                    if (invoce02.getRepays_sum() != null && !invoce02.getRepays_sum().equals(new BigDecimal(0))) {
                        chargeInfo.getMunicipalService().setPaymentRecalculation(new PDServiceChargeType.MunicipalService.PaymentRecalculation());
//                            Основания перерасчётов
                        chargeInfo.getMunicipalService().getPaymentRecalculation().setRecalculationReason(invoce02.getRepays_text());
//                            Сумма
                        chargeInfo.getMunicipalService().getPaymentRecalculation().setSum(getBigDecimalTwo(invoce02.getRepays_sum()));
                    }

//                        Норматив потребления коммунальной услуги
                    chargeInfo.getMunicipalService().setServiceInformation(new ServiceInformation());

//                        Текущие показания приборов учёта коммунальных услуг - индивидульное потребление
//                    TODO пока не заносим показания ПУ - у нас возвращается строка с показаниями приборов, а у этих идиотов только одно число можно ставить
/*
                    if (rs.getString(15) != null && !rs.getString(15).trim().isEmpty()) {

                        chargeInfo.getMunicipalService().getServiceInformation().
                                setIndividualConsumptionCurrentValue(new BigDecimal(rs.getString(15)).setScale(4, BigDecimal.ROUND_DOWN));
                    }

                        Текущие показания приборов учёта коммунальных услуг - общедомовые нужды

                    if (rs.getString(21) != null) {
                        try {
                            chargeInfo.getMunicipalService().getServiceInformation().
                                    setHouseOverallNeedsCurrentValue(new BigDecimal(rs.getString(21)));
                        } catch (NumberFormatException e) {
                            answerProcessing.sendErrorToClient("Формирование ПД", "\"Формирование ПД\"", LOGGER,
                                    new PreGISException("Неверное значение указанно в процедуре \"WEB_INVOICE\", " +
                                            "ГИС ЖКХ ожидает BigDecimal"));
                        }
                    }


//                        Суммарный объём коммунальных услуг в доме - индивидульное потребление
//                     TODO пока не делаем весь кусок. Они не обязательные, потом доделаем
                    if (rs.getBigDecimal(22) != null) {
                        chargeInfo.getMunicipalService().getServiceInformation().setHouseTotalIndividualConsumption(
                                rs.getBigDecimal(22).setScale(4, BigDecimal.ROUND_HALF_UP));
                    }
//                        Суммарный объём коммунальных услуг в доме - общедомовые нужды
                    if (rs.getBigDecimal(23) != null) {
                        chargeInfo.getMunicipalService().getServiceInformation().setHouseTotalHouseOverallNeeds(
                                rs.getBigDecimal(23).setScale(4, BigDecimal.ROUND_HALF_UP));
                    }

//                        Норматив потребления коммунальных услуг - общедомовые нужды
                    chargeInfo.getMunicipalService().getServiceInformation().
                            setHouseOverallNeedsNorm(rs.getBigDecimal(14).setScale(4, BigDecimal.ROUND_DOWN));

//                        Норматив потребления коммунальных услуг - индивидульное потребление

                    chargeInfo.getMunicipalService().getServiceInformation().
                            setIndividualConsumptionNorm(rs.getBigDecimal(13).setScale(4, BigDecimal.ROUND_DOWN));

//                        Объем услуг - индивидульное потребление и(или) общедомовые нужды
                    chargeInfo.getMunicipalService().setConsumption(new PDServiceChargeType.MunicipalService.Consumption());

                    if (rs.getBigDecimal(5) != null) {

                        PDServiceChargeType.MunicipalService.Consumption.Volume volumeIndividual = new PDServiceChargeType.MunicipalService.Consumption.Volume();
//                            Тип предоставления услуги: (I)ndividualConsumption - индивидульное потребление house(O)verallNeeds - общедомовые нужды
                        volumeIndividual.setType("I");
//                            Способ определения объемов КУ: (N)orm - Норматив (M)etering device - Прибор учета (O)ther - Иное
//                            volumeIndividual.setDeterminingMethod();
                        volumeIndividual.setValue(rs.getBigDecimal(5).setScale(3, BigDecimal.ROUND_DOWN));
                        chargeInfo.getMunicipalService().getConsumption().getVolume().add(volumeIndividual);

                    } else if (rs.getBigDecimal(6) != null) {

                        PDServiceChargeType.MunicipalService.Consumption.Volume volumeOverall = new PDServiceChargeType.MunicipalService.Consumption.Volume();
//                            Тип предоставления услуги: (I)ndividualConsumption - индивидульное потребление house(O)verallNeeds - общедомовые нужды
                        volumeOverall.setType("O");
//                            Способ определения объемов КУ: (N)orm - Норматив (M)etering device - Прибор учета (O)ther - Иное
//                            volumeIndividual.setDeterminingMethod();
                        volumeOverall.setValue(rs.getBigDecimal(6).setScale(3, BigDecimal.ROUND_DOWN));
                        chargeInfo.getMunicipalService().getConsumption().getVolume().add(volumeOverall);
                    }

//                        К оплате за индивидуальное потребление коммунальной услуги, руб.
                    chargeInfo.getMunicipalService().setMunicipalServiceIndividualConsumptionPayable(getBigDecimalTwo(rs.getBigDecimal(8)));
//                        К оплате за общедомовое потребление коммунальной услуги, руб.
                    chargeInfo.getMunicipalService().setMunicipalServiceCommunalConsumptionPayable(getBigDecimalTwo(rs.getBigDecimal(9)));
*/

                } else if (invoce02.getCode_parent().equals("50")) { // если Жилищная услуга

                    chargeInfo.setHousingService(new PDServiceChargeType.HousingService());
//                    Код услуги (жилищной, коммунальной или дополнительной)
                    chargeInfo.getHousingService().setServiceType(new NsiRef());
                    chargeInfo.getHousingService().getServiceType().setName(invoce02.getGis_service());
                    chargeInfo.getHousingService().getServiceType().setCode(invoce02.getCode());
                    chargeInfo.getHousingService().getServiceType().setGUID(invoce02.getGis_service_uiid());

//                    Тариф
                    chargeInfo.getHousingService().setRate(getBigDecimalTwo(invoce02.getTariff()));

//                    Итого к оплате за расчетный период, руб.
                    chargeInfo.getHousingService().setTotalPayable(getBigDecimalTwo(invoce02.getForpay()));

//                    Всего начислено за расчетный период (без перерасчетов и льгот), руб.
                    chargeInfo.getHousingService().setAccountingPeriodTotal(getBigDecimalTwo(invoce02.getCharge_total()));

//                    Порядок расчетов. Вообще ниразу нигде не написано что это? TODO
//                    chargeInfo.getHousingService().setCalcExplanation("Иное");

                    // перерасчеты
                    if (invoce02.getRepays() != null || invoce02.getExempts() != null ||
                            !invoce02.getRepays().equals(new BigDecimal(0)) || !invoce02.getExempts().equals(new BigDecimal(0))) {
                        chargeInfo.getHousingService().setServiceCharge(new ServiceChargeType());
//                        Перерасчеты, корректировки (руб)
                        chargeInfo.getHousingService().getServiceCharge().setMoneyRecalculation(getBigDecimalTwo(invoce02.getRepays()));
//                          Льготы, субсидии, скидки (руб)
                        chargeInfo.getHousingService().getServiceCharge().setMoneyDiscount(getBigDecimalTwo(invoce02.getExempts()));
                    }

/*
//                    Объем услуг - индивидульное потребление и(или) общедомовые нужды
                    chargeInfo.getHousingService().setConsumption(new PDServiceChargeType.HousingService.Consumption());
//                    Тип предоставления услуги: (I)ndividualConsumption - индивидульное потребление
                    chargeInfo.getHousingService().getConsumption().setVolume(new PDServiceChargeType.HousingService.Consumption.Volume());
                    chargeInfo.getHousingService().getConsumption().getVolume().setType("I");
                    chargeInfo.getHousingService().getConsumption().getVolume().setValue(rs.getBigDecimal(5).setScale(3, BigDecimal.ROUND_DOWN));
*/

                } else /* if (invoce02.getCode_parent().equals("1")) */ { // а если Дополнительная услуга -- закомментарил код - теперь все что не 50 и 51 идёт в дополнительные

                    chargeInfo.setAdditionalService(new PDServiceChargeType.AdditionalService());
//                    Код услуги (жилищной, коммунальной или дополнительной)
                    chargeInfo.getAdditionalService().setServiceType(new NsiRef());
                    chargeInfo.getAdditionalService().getServiceType().setName(invoce02.getGis_service());
                    chargeInfo.getAdditionalService().getServiceType().setCode(invoce02.getCode());
                    chargeInfo.getAdditionalService().getServiceType().setGUID(invoce02.getGis_service_uiid());

//                    Тариф
                    chargeInfo.getAdditionalService().setRate(getBigDecimalTwo(invoce02.getTariff()));

//                    Итого к оплате за расчетный период, руб.
                    chargeInfo.getAdditionalService().setTotalPayable(getBigDecimalTwo(invoce02.getForpay()));

//                    Всего начислено за расчетный период (без перерасчетов и льгот), руб.
                    chargeInfo.getAdditionalService().setAccountingPeriodTotal(getBigDecimalTwo(invoce02.getCharge_total()));

//                    Порядок расчетов. Вообще ниразу нигде не написано что это? TODO
//                    chargeInfo.getAdditionalService().setCalcExplanation("Иное");

                    // перерасчеты
                    if (invoce02.getRepays() != null || invoce02.getExempts() != null ||
                            !invoce02.getRepays().equals(new BigDecimal(0)) || !invoce02.getExempts().equals(new BigDecimal(0))) {
                        chargeInfo.getAdditionalService().setServiceCharge(new ServiceChargeType());
//                        Перерасчеты, корректировки (руб)
                        chargeInfo.getAdditionalService().getServiceCharge().setMoneyRecalculation(getBigDecimalTwo(invoce02.getRepays()));
//                          Льготы, субсидии, скидки (руб)
                        chargeInfo.getAdditionalService().getServiceCharge().setMoneyDiscount(getBigDecimalTwo(invoce02.getExempts()));
                    }
                }
                if (chargeInfo.getAdditionalService() != null && chargeInfo.getHousingService() != null && chargeInfo.getMunicipalService() != null) {
                    paymentDocument.getChargeInfo().add(chargeInfo);
                }
/*              TODO пока не заполняем
//                    Аванс на начало расчетного периода, руб.
                if (paymentDocument.getAdvanceBllingPeriod() != null) {
                    paymentDocument.setAdvanceBllingPeriod(paymentDocument.getAdvanceBllingPeriod().add(getBigDecimalTwo(rs.getBigDecimal(16))));
                } else {
                    paymentDocument.setAdvanceBllingPeriod(getBigDecimalTwo(rs.getBigDecimal(16)));
                }

//                    Задолженность за предыдущие периоды, руб
                if (paymentDocument.getDebtPreviousPeriods() != null) {
                    paymentDocument.setDebtPreviousPeriods(paymentDocument.getDebtPreviousPeriods().add(getBigDecimalTwo(rs.getBigDecimal(17))));
                } else {
                    paymentDocument.setDebtPreviousPeriods(getBigDecimalTwo(rs.getBigDecimal(17)));
                }

//                    Сумма к оплате с учетом рассрочки платежа и процентов за рассрочку, руб
                if (paymentDocument.getTotalPiecemealPaymentSum() != null) {
                    paymentDocument.setTotalPiecemealPaymentSum(paymentDocument.getTotalPiecemealPaymentSum().add(getBigDecimalTwo(rs.getBigDecimal(18))));
                } else {
                    paymentDocument.setTotalPiecemealPaymentSum(getBigDecimalTwo(rs.getBigDecimal(18)));
                }*/

                //            Ссылка на платежные реквизиты
                if(paymentInformationMap != null)
                    if(paymentInformationMap.get(invoce02.getPayee_id()) != null) {
                        paymentDocument.setPaymentInformationKey(paymentInformationMap.get(invoce02.getPayee_id()).getTransportGUID());
                    }else{
                        throw new PreGISException("Критическая ошибка - не задан платежный агент ИД " + invoce02.getPayee_id());
                    }
            }
            // закончили заносить начисления

            // на оплату
            paymentDocument.setExpose(true);
            // Транспортный GUID
            paymentDocument.setTransportGUID(OtherFormat.getRandomGUID());

            paymentDocumentMap.put(paymentDocument.getPaymentDocumentNumber(), paymentDocument);
        }

        return paymentDocumentMap;
    }

    public ArrayList<ImportPaymentDocumentRequest.WithdrawPaymentDocument> getWithdrawPaymentDocument(final Integer houseGradID)
            throws SQLException, PreGISException, ParseException {

        ArrayList<ImportPaymentDocumentRequest.WithdrawPaymentDocument> paymentDocumentWithdrawArray = new ArrayList<>();

        try (Statement statement = connectionGrad.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT RPAYMENTDOCUMENTID FROM EX_GIS_INVOCE_WITHDRAW(" +
                    month + ", " +
                    year + ", " +
                    "null, " +
                    houseGradID + ", null)");

            while (rs.next()) {
                ImportPaymentDocumentRequest.WithdrawPaymentDocument withdrawPaymentDocument = new ImportPaymentDocumentRequest.WithdrawPaymentDocument();
                withdrawPaymentDocument.setPaymentDocumentID(rs.getString("RPAYMENTDOCUMENTID"));
                withdrawPaymentDocument.setTransportGUID(OtherFormat.getRandomGUID());
                paymentDocumentWithdrawArray.add(withdrawPaymentDocument);
            }
        }
        return paymentDocumentWithdrawArray;
    }

    public BigDecimal getBigDecimalTwo(BigDecimal value) {

//        ROUND_DOWN: Отбрасывание разряда
//        0.333  ->   0.33
//       -0.333  ->  -0.33

//        ROUND_HALF_UP: Округление вверх, если число после запятой >= .5
//        0.5  ->  1.0
//        0.4  ->  0.0

//        ROUND_HALF_DOWN: Округление вверх, если число после запятой > .5
//        0.5  ->  0.0
//        0.6  ->  1.0

//        MoneyType 2 знака после точки.
        if (value != null) {
            value = value.setScale(2, BigDecimal.ROUND_DOWN);
        }
        return value;
    }

    /**
     * Метод, по полученым даным из таблицы пытается найти соответствующую услугу в справочнике ГИС ЖКХ.
     *
     * @param rgroup          название услуги.
     * @param rsuperGroupCode название группы услуги.
     */
    private ReferenceItemDataSet getReferenceItemDataSet(String rgroup, String rsuperGroupCode,
                                                         ArrayList<ReferenceItemDataSet> referenceItemGRADDAOAllItems) throws SQLException {

        HashMap<String, Integer> associationsMap = getAssociationsMap();
        if (associationsMap.containsKey(rsuperGroupCode)) {
            return searchNsiRef(referenceItemGRADDAOAllItems, associationsMap.get(rsuperGroupCode));
        } else {
            return searchNsiRefWithName(referenceItemGRADDAOAllItems, rgroup);
        }
    }

    /**
     * Метод, пытаеться найти похожую услугу по имени.
     *
     * @param referenceItemGRADDAOAllItems список всех услуг из справочника ГИС ЖКХ.
     * @param serviceName                  имя услуги из БД Града.
     * @return ссылка на нужную услуги из справочника.
     */
    private ReferenceItemDataSet searchNsiRefWithName(ArrayList<ReferenceItemDataSet> referenceItemGRADDAOAllItems, String serviceName) {

        ReferenceItemDataSet nsi = null;

        for (ReferenceItemDataSet item : referenceItemGRADDAOAllItems) {
            if (serviceName.equalsIgnoreCase(item.getName())) {
                if (nsi != null) return null;
                nsi = new ReferenceItemDataSet();
            }
        }
        return nsi;
    }

    /**
     * Метод, получает ссылку на справочник, с помощью идентификатора из таблицы ассоциаций.
     *
     * @param referenceItemGRADDAOAllItems список записей из справочника.
     * @param id                           идентификатор записи из справочника ГИС ЖКХ.
     * @return ссылку на справочник.
     */
    private ReferenceItemDataSet searchNsiRef(ArrayList<ReferenceItemDataSet> referenceItemGRADDAOAllItems, Integer id) {

        for (ReferenceItemDataSet item : referenceItemGRADDAOAllItems) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Метод, для удобного извдечения переобразует List в Map.
     *
     * @return ключ - услуга в БД ГРАДа, значение - ид записи справочника в локальной БД.
     * @throws SQLException
     */
    private HashMap<String, Integer> getAssociationsMap() throws SQLException {

        ServicesGisJkhForGradDAO servicesGisJkhForGradDAO = new ServicesGisJkhForGradDAO();
        HashMap<String, Integer> map = new HashMap<>();
        ArrayList<ServicesGisJkhForGradDataSet> associations = servicesGisJkhForGradDAO.getAllServicesAssociations();

        for (ServicesGisJkhForGradDataSet association : associations) {
            map.put(association.getServiceGrad(), association.getServiceGisJkh());
        }
        return map;
    }

    /**
     * Метод получает данные первого листа ЕПД в виде HashMap
     * @param houseGradID - ИД дома в Град
     * @return - map  с ключом - номер ЕПД
     * @throws SQLException
     */
    private HashMap<String, Invoice01> getAccountMap(final Integer houseGradID) throws SQLException, PreGISException {
        Statement accountStatement = connectionGrad.createStatement();
        String sqlQuery = "select raccountguid, " +
                "rpd_type, " +
                "rpd_no, " +
                "rsq_total, " +
                "rsq_live, " +
                "rsq_heat, " +
                "rtencount, " +
                "rdebt, " +
                "ravans, " +
                "rpays_advance, " +
                "rbik, " +
                "rbank_account, " +
                "rnls, " +
                "raddress " +
                "from ex_gis_invoce01(" + month + ", " +
                year + ", " +
                ResourcesUtil.instance().getCompanyGradId() + ", " +
                houseGradID + ", null)";
        ResultSet accountsResultSet = accountStatement.executeQuery(sqlQuery);

        HashMap<String, Invoice01> accountsMap = new HashMap<>();

        while(accountsResultSet.next()){
            Invoice01 invoce01 = new Invoice01();
            invoce01.setAccountguid(accountsResultSet.getString("raccountguid"));
            invoce01.setAccountNLS(accountsResultSet.getString("rnls"));
            invoce01.setAddress(accountsResultSet.getString("raddress"));
            invoce01.setPd_type(accountsResultSet.getString("rpd_type"));
            invoce01.setPd_no(accountsResultSet.getString("rpd_no"));
            invoce01.setSq_total(accountsResultSet.getBigDecimal("rsq_total"));
            invoce01.setSq_live(accountsResultSet.getBigDecimal("rsq_live"));
            invoce01.setSq_heat(accountsResultSet.getBigDecimal("rsq_heat"));
            invoce01.setTencount(accountsResultSet.getByte("rtencount"));
            invoce01.setDebt(accountsResultSet.getDouble("rdebt"));
            invoce01.setAvans(accountsResultSet.getDouble("ravans"));
            invoce01.setPays_advance(accountsResultSet.getDouble("rpays_advance"));
            invoce01.setBik(accountsResultSet.getString("rbik"));
            invoce01.setBank_account(accountsResultSet.getString("rbank_account"));

            accountsMap.put(invoce01.getPd_no(), invoce01);
        }

        return  accountsMap;
    }

    private HashMap<String, ArrayList<Invoice02>> getChargesMap(final Integer houseGradID) throws SQLException, PreGISException {
        Statement chargesStatement = connectionGrad.createStatement();
        ResultSet charges = chargesStatement.executeQuery("select rpd_no, " +
                "rgis_service, " +
                "ripu, " +
                "ramount_personal, " +
                "rodn_pu, " +
                "ramount_shared, " +
                "rtariff, " +
                "rrepays, " +
                "rexempts, " +
                "rcharge_legend, " +
                "rnorm_personal, " +
                "rnorm_shared, " +
                "rmeters_personal, " +
                "rmeters_shared, " +
                "rsumm_amount_personal, " +
                "rsumm_amount_shared, " +
                "rrepays_text, " +
                "rrepays_sum, " +
                "rrassroch_current, " +
                "rrassroch_other, " +
                "rrassroch_percentsum, " +
                "rrassroch_percent, " +
                "rforpay, " +
                "rgis_service_uiid," +
                "rgis_service_code," +
                "rgis_service_code_parent," +
                "rcharge_total," +
                "rpayee_id " +
                "from ex_gis_invoce02(" + month + ", " +
                year + ", " +
                ResourcesUtil.instance().getCompanyGradId() + ", " +
                houseGradID + ", null)");

        HashMap<String, ArrayList<Invoice02>> chargesMap = new HashMap<>();
        
        while (charges.next()){
            Invoice02 invoice02 = new Invoice02();
            invoice02.setPd_no(charges.getString("rpd_no"));
            invoice02.setGis_service(charges.getString("rgis_service"));
            invoice02.setGis_service_uiid(charges.getString("rgis_service_uiid"));
            invoice02.setIpu(charges.getString("ripu"));
            invoice02.setAmount_personal(charges.getDouble("ramount_personal"));
            invoice02.setOdn_pu(charges.getString("rodn_pu"));
            invoice02.setAmount_shared(charges.getDouble("ramount_shared"));
            invoice02.setTariff(charges.getBigDecimal("rtariff"));
            invoice02.setRepays(charges.getBigDecimal("rrepays"));
            invoice02.setExempts(charges.getBigDecimal("rexempts"));
            invoice02.setCharge_legend(charges.getString("rcharge_legend"));
            invoice02.setNorm_personal(charges.getDouble("rnorm_personal"));
            invoice02.setNorm_shared(charges.getDouble("rnorm_shared"));
            invoice02.setMeters_personal(charges.getString("rmeters_personal"));
            invoice02.setMeters_shared(charges.getString("rmeters_shared"));
            invoice02.setSumm_amount_personal(charges.getDouble("rsumm_amount_personal"));
            invoice02.setSumm_amount_shared(charges.getDouble("rsumm_amount_shared"));
            invoice02.setRepays_text(charges.getString("rrepays_text"));
            invoice02.setRepays_sum(charges.getBigDecimal("rrepays_sum"));
            invoice02.setRassroch_current(charges.getDouble("rrassroch_current"));
            invoice02.setRassroch_other(charges.getDouble("rrassroch_other"));
            invoice02.setRassroch_percentsum(charges.getDouble("rrassroch_percentsum"));
            invoice02.setRassroch_percent(charges.getDouble("rrassroch_percent"));
            invoice02.setForpay(charges.getBigDecimal("rforpay"));
            invoice02.setCode(charges.getString("rgis_service_code"));
            invoice02.setCode_parent(charges.getString("rgis_service_code_parent"));
            invoice02.setCharge_total(charges.getBigDecimal("rcharge_total"));
            invoice02.setPayee_id(charges.getInt("rpayee_id"));

            if(invoice02.getCode() == null){
                throw new PreGISException("Отсутствует код услуги ГИС!");
            }

            ArrayList<Invoice02> invoceList = chargesMap.get(invoice02.getPd_no());

            if(invoceList == null){
                invoceList = new ArrayList<Invoice02>();
                chargesMap.put(invoice02.getPd_no(), invoceList);
            }
            invoceList.add(invoice02);
        }

        return chargesMap;
    }

    /**
     * Метод устанавливает
     * @param paymentDocumentNumber - номер ЕПД в Град
     * @param paymentDocumentGUID - присвоенный ГИС идентификатор
     */
    public void addPaymentDocumentRegistryItem(final String paymentDocumentNumber,
                                               final String paymentDocumentGUID){
        try {
            connectionGrad.createStatement().execute("update EX_GIS_INVOCES set RPD_GIS_NO = '" + paymentDocumentGUID + "' where RPD_NO = '" + paymentDocumentNumber + "'");
        }catch(SQLException e){
            answerProcessing.sendInformationToClientAndLog("Не удалось выставить GUID из ГИС ЖКХ документу с номером  " +  paymentDocumentNumber + "; " + e.getMessage(), LOGGER);
        }
    }

    /**
     * Метод, находит в БД ГРАД ЛС и адрес абонента по GUID.
     *
     * @param GUID номер ЛС.
     * @return ЛС и адрес абонента
     * @throws ParseException
     * @throws SQLException
     */
    public HashMap<String, String> getAbonentNLSbyGUIDFromGrad(final String GUID) throws ParseException, SQLException {
        if(GUID == null || GUID.equals("")){
            return null;
        }
        HashMap<String, String> accountNLSMap = new HashMap<>();

        String sqlRequest = "select a.g_licschet, a.address_short\n" +
                "from v_abons a\n" +
                "join abonents2traits a2t on a2t.abonent_id = a.id\n" +
                "join abonent_traits t on t.id = a2t.traits_id\n" +
                "where t.code = 'ACCOUNTGUID'\n" +
                "  and a2t.traits_value = '" + GUID + "'";

        try (Statement cstmt = connectionGrad.createStatement()) { // После использования должны все соединения закрыться
            ResultSet resultSet = cstmt.executeQuery(sqlRequest);
            resultSet.next();
            accountNLSMap.put(resultSet.getString(1), resultSet.getString(2));
            resultSet.close();
        }
        return accountNLSMap;
    }

    /**
     * Метод, возвращает статус ошибки.
     *
     * @return состояния обработки получения данных из процедуры "REP_INVOICE".
     * -1 - Ошибка, дальнейшая работа невозможна,
     * 0 - Возникли ошибки при работе, дальнейшая работа возможна,
     * 1 - Всё прошло успешно, ошибок не обнаружено.
     */
    public int getStateError() {
        return stateError;
    }

    public void setStateError(int stateError) {
        if (stateError < this.stateError) {
            this.stateError = stateError;
        }
    }
}
