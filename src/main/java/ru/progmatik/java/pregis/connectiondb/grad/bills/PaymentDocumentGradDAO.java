package ru.progmatik.java.pregis.connectiondb.grad.bills;

import com.google.inject.internal.Nullable;
import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.CommonResultType;
import ru.gosuslugi.dom.schema.integration.bills.*;
import ru.gosuslugi.dom.schema.integration.nsi_base.NsiRef;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ServicesGisJkhForGradDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ServicesGisJkhForGradDataSet;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.model.Invoice01;
import ru.progmatik.java.pregis.model.Invoice02;
import ru.progmatik.java.pregis.model.ReferenceItemDataSet;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.util.*;

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

    public PaymentDocumentGradDAO(AnswerProcessing answerProcessing) throws SQLException {
        if(answerProcessing != null) {
            this.answerProcessing = answerProcessing;
        }else{
            this.answerProcessing = new AnswerProcessing();
        }
//        this.connectionGrad = connectionGrad;
        getGradClosedPeriod();
    }

    /**
     * Метод запрашивает из Града месяц и год закрытого периода
     * метод временный, потом будем спрашивать у пользователя
     */
    private void getGradClosedPeriod() throws SQLException {
        try(Connection connection = ConnectionBaseGRAD.instance().getConnection()) {
            try (Statement statement = connection.createStatement()) {

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
    }

    /**
     * Метод, отправляет в БД Града запрос, формирующий платежные документы по дому в БД.
     *
     * @param houseGradID          идентификатор дома в БД ГРАДа
     */

    public boolean generatePaymentDocuments(final Integer houseGradID) throws PreGISException, SQLException {
        try(Connection connection = ConnectionBaseGRAD.instance().getConnection()) {
            try (CallableStatement callableStatement = connection.prepareCall("execute procedure EX_GIS_INVOICE_FILL(?, ?, ?, ?, null)")) {
                callableStatement.setInt(1, this.getMonth());
                callableStatement.setShort(2, this.getYear());
                callableStatement.setInt(3, ResourcesUtil.instance().getCompanyGradId());
                callableStatement.setInt(4, houseGradID);
                callableStatement.execute();
                return true;
            } catch (SQLException e) {
                answerProcessing.sendInformationToClientAndLog("Не удалось сгенерировать ЕПД по дому " + houseGradID, LOGGER);
                answerProcessing.sendInformationToClientAndLog(e.getMessage(), LOGGER);
                return false;
            }
        }
    }

    public List<ImportPaymentDocumentRequest.PaymentInformation> getPaymentInformationMap(final Integer houseGradID)
            throws SQLException, PreGISException {

        List<ImportPaymentDocumentRequest.PaymentInformation> paymentInformationList = new ArrayList<>();

        try(Connection connection = ConnectionBaseGRAD.instance().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery("SELECT RBANK_ACCOUNT, RBIK FROM EX_GIS_INVOCE03(" + month + "," + year + "," + houseGradID + ")");

                while (rs.next()) {
                    if (rs.getString("RBANK_ACCOUNT") != null && rs.getString("RBIK") != null) {

                        ImportPaymentDocumentRequest.PaymentInformation paymentInformation = new ImportPaymentDocumentRequest.PaymentInformation();
                        paymentInformation.setTransportGUID(OtherFormat.getRandomGUID());
                        paymentInformation.setOperatingAccountNumber(rs.getString("RBANK_ACCOUNT"));
                        paymentInformation.setBankBIK(rs.getString("RBIK"));

                        paymentInformationList.add(paymentInformation);
                    }
                }
                if (!rs.isClosed()) {
                    rs.close();
                }
            }
        }
        if (paymentInformationList.isEmpty()) {
            answerProcessing.sendInformationToClientAndLog("Не удалось получить реквизиты из процедуры EX_GIS_INVOCE03 по дому Град ИД " + houseGradID, LOGGER);
        }
        return paymentInformationList;
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
            @Nullable final List<ImportPaymentDocumentRequest.PaymentInformation> paymentInformationMap) throws SQLException, PreGISException, ParseException {

        final HashMap<String, ImportPaymentDocumentRequest.PaymentDocument> paymentDocumentMap = new HashMap<>();

        // получаем заголовки платежных документов по абонентам
        HashMap<String, Invoice01> invocesMapGrad = getAccountMap(houseGradID);

        // если нечего посылать - возвращаем пусто
        if (invocesMapGrad == null || invocesMapGrad.isEmpty()){
            return null;
        }

        // получаем начисления абонентов
        HashMap<String, ArrayList<Invoice02>> chargesMapGrad = getChargesMap(houseGradID);

        // если нечего посылать - возвращаем пусто
        if (chargesMapGrad == null || chargesMapGrad.isEmpty()){
            return null;
        }

        // бежим по мапе заголовков платежных документов из Града
        for (Map.Entry<String, Invoice01> invoceGrad: invocesMapGrad.entrySet()) {
            // выделяем начисления данного ЛС
            ArrayList<Invoice02> accountCharges = chargesMapGrad.get(invoceGrad.getValue().getPd_no());
            // если начислений нет - не отсылаем этот адрес, переходим к другому
            if (accountCharges == null || accountCharges.isEmpty()) continue;

            // GUID абонента проверяем
            if(invoceGrad.getValue().getAccountguid() == null || invoceGrad.getValue().getAccountguid().equals("")){
                if(answerProcessing != null) {
                    answerProcessing.sendInformationToClientAndLog("Не указан Accountguid у ЕПД с номером " + invoceGrad.getValue().getPd_no(), LOGGER);
                    answerProcessing.clearLabelForText();
                }
                continue;
            }

            //Создадим платежный документ
            ImportPaymentDocumentRequest.PaymentDocument paymentDocument = new ImportPaymentDocumentRequest.PaymentDocument();
            // GUID абонента
            paymentDocument.setAccountGuid(invoceGrad.getValue().getAccountguid());
            //Номер платежного документа, по которому внесена плата, присвоенный такому документу исполнителем в целях осуществления расчетов по внесению платы
            paymentDocument.setPaymentDocumentNumber(invoceGrad.getValue().getPd_no());
//            paymentDocument.setPaymentDocumentNumber(String.format("%010d", accountGrad.getValue().getPd_no()));

            if(invoceGrad.getValue().getDebt() != 0){
                paymentDocument.setDebtPreviousPeriods(OtherFormat.getBigDecimalTwo(new BigDecimal(invoceGrad.getValue().getDebt())));
            }else{
                if(invoceGrad.getValue().getAvans() != 0){
                    paymentDocument.setAdvanceBllingPeriod(OtherFormat.getBigDecimalTwo(new BigDecimal(invoceGrad.getValue().getAvans())));
                }
            }

            paymentDocument.setTotalPayableByPDWithDebtAndAdvance(OtherFormat.getBigDecimalTwo(new BigDecimal(invoceGrad.getValue().getForpay())));
            // на оплату
            paymentDocument.setExpose(true);
            // Транспортный GUID
            paymentDocument.setTransportGUID(OtherFormat.getRandomGUID());

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

//                  Тариф
                    chargeInfo.getMunicipalService().setRate(OtherFormat.getBigDecimalTwo(invoce02.getTariff()));

                    // если заданы объемы - заносим их
                    if(invoce02.getAmount_personal() != 0 || invoce02.getAmount_shared() != 0) {

                        chargeInfo.getMunicipalService().setConsumption(new PDServiceChargeType.MunicipalService.Consumption());

                        if (invoce02.getAmount_personal() != 0) {

                            PDServiceChargeType.MunicipalService.Consumption.Volume volumeIndividual = new PDServiceChargeType.MunicipalService.Consumption.Volume();
//                            Тип предоставления услуги: (I)ndividualConsumption - индивидульное потребление house(O)verallNeeds - общедомовые нужды
                            volumeIndividual.setType("I");
//                            Способ определения объемов КУ: (N)orm - Норматив (M)etering device - Прибор учета (O)ther - Иное
//                            volumeIndividual.setDeterminingMethod();
                            volumeIndividual.setValue(new BigDecimal(invoce02.getAmount_personal()).setScale(3, BigDecimal.ROUND_DOWN));
                            chargeInfo.getMunicipalService().getConsumption().getVolume().add(volumeIndividual);

                        }
                        if (invoce02.getAmount_shared() != 0) {

                            PDServiceChargeType.MunicipalService.Consumption.Volume volumeOverall = new PDServiceChargeType.MunicipalService.Consumption.Volume();
//                            Тип предоставления услуги: (I)ndividualConsumption - индивидульное потребление house(O)verallNeeds - общедомовые нужды
                            volumeOverall.setType("O");
//                            Способ определения объемов КУ: (N)orm - Норматив (M)etering device - Прибор учета (O)ther - Иное
//                            volumeIndividual.setDeterminingMethod();
                            volumeOverall.setValue(new BigDecimal(invoce02.getAmount_shared()).setScale(3, BigDecimal.ROUND_DOWN));
                            chargeInfo.getMunicipalService().getConsumption().getVolume().add(volumeOverall);
                        }
                    }

//                    Итого к оплате за расчетный период, руб.
                    chargeInfo.getMunicipalService().setTotalPayable(OtherFormat.getBigDecimalTwo(invoce02.getForpay()));

//                    Всего начислено за расчетный период (без перерасчетов и льгот), руб.
                    chargeInfo.getMunicipalService().setAccountingPeriodTotal(OtherFormat.getBigDecimalTwo(invoce02.getCharge_total()));

//                        Порядок расчетов ??? TODO
//                    chargeInfo.getMunicipalService().setCalcExplanation("Иное");

                    // перерасчеты
                    if (invoce02.getRepays() != null || invoce02.getExempts() != null ||
                            !invoce02.getRepays().equals(new BigDecimal(0)) || !invoce02.getExempts().equals(new BigDecimal(0))) {
                        chargeInfo.getMunicipalService().setServiceCharge(new ServiceChargeImportType());
//                        Перерасчеты, корректировки (руб)
                        chargeInfo.getMunicipalService().getServiceCharge().setMoneyRecalculation(OtherFormat.getBigDecimalTwo(invoce02.getRepays()));
//                          Льготы, субсидии, скидки (руб)
                        chargeInfo.getMunicipalService().getServiceCharge().setMoneyDiscount(OtherFormat.getBigDecimalTwo(invoce02.getExempts()));
                    }

//                        Рассрочка. Раздел 6. Иваненко Дмитрий сказал, что у наших клиентов нет таких.
//                        chargeInfo.getMunicipalService().setPiecemealPayment(new PiecemealPayment());

//                        Перерасчеты. Раздел 5.
                    if (invoce02.getRepays_sum() != null && !invoce02.getRepays_sum().equals(new BigDecimal(0))) {
                        chargeInfo.getMunicipalService().setPaymentRecalculation(new PDServiceChargeType.MunicipalService.PaymentRecalculation());
//                            Основания перерасчётов
                        chargeInfo.getMunicipalService().getPaymentRecalculation().setRecalculationReason(invoce02.getRepays_text());
//                            Сумма
                        chargeInfo.getMunicipalService().getPaymentRecalculation().setSum(OtherFormat.getBigDecimalTwo(invoce02.getRepays_sum()));
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

                } else if (invoce02.getCode_parent().equals("50")) { // если Жилищная услуга или Капермонт
                    if (invoce02.getCode().equals("2") && ResourcesUtil.instance().getKapremontProp()){ // Кап.ремонт
                        CapitalRepairImportType capitalRepairImportType = new CapitalRepairImportType();
//                    Код услуги (жилищной, коммунальной или дополнительной)
//                        chargeInfo.getHousingService().setServiceType(new NsiRef());
//                        chargeInfo.getHousingService().getServiceType().setName(invoce02.getGis_service());
//                        chargeInfo.getHousingService().getServiceType().setCode(invoce02.getCode());
//                        chargeInfo.getHousingService().getServiceType().setGUID(invoce02.getGis_service_uiid());

//                    Тариф
                        capitalRepairImportType.setContribution(OtherFormat.getBigDecimalTwo(invoce02.getTariff()));

//                    Итого к оплате за расчетный период, руб.
                        capitalRepairImportType.setTotalPayable(OtherFormat.getBigDecimalTwo(invoce02.getForpay()));

//                    Всего начислено за расчетный период (без перерасчетов и льгот), руб.
                        capitalRepairImportType.setAccountingPeriodTotal(OtherFormat.getBigDecimalTwo(invoce02.getCharge_total()));

//                    Порядок расчетов. Вообще ниразу нигде не написано что это? TODO
//                    chargeInfo.getHousingService().setCalcExplanation("Иное");

                        // Перерасчеты, корректировки (руб)
                        if (invoce02.getRepays() == null || invoce02.getRepays().equals(new BigDecimal(0))) {
                            capitalRepairImportType.setMoneyRecalculation(new BigDecimal(0));
                        }else {
                            capitalRepairImportType.setMoneyRecalculation(OtherFormat.getBigDecimalTwo(invoce02.getRepays()));
                        }
//                          Льготы, субсидии, скидки (руб)
                        if(invoce02.getExempts() == null || invoce02.getExempts().equals(new BigDecimal(0))) {
                            capitalRepairImportType.setMoneyDiscount(new BigDecimal(0));
                        }
                        else {
                            capitalRepairImportType.setMoneyDiscount(OtherFormat.getBigDecimalTwo(invoce02.getExempts()));
                        }

                        paymentDocument.setCapitalRepairCharge(capitalRepairImportType);

                    }else {

                        chargeInfo.setHousingService(new PDServiceChargeType.HousingService());
//                    Код услуги (жилищной, коммунальной или дополнительной)
                        chargeInfo.getHousingService().setServiceType(new NsiRef());
                        chargeInfo.getHousingService().getServiceType().setName(invoce02.getGis_service());
                        chargeInfo.getHousingService().getServiceType().setCode(invoce02.getCode());
                        chargeInfo.getHousingService().getServiceType().setGUID(invoce02.getGis_service_uiid());

//                    Тариф
                        chargeInfo.getHousingService().setRate(OtherFormat.getBigDecimalTwo(invoce02.getTariff()));

//                    Итого к оплате за расчетный период, руб.
                        chargeInfo.getHousingService().setTotalPayable(OtherFormat.getBigDecimalTwo(invoce02.getForpay()));

//                    Всего начислено за расчетный период (без перерасчетов и льгот), руб.
                        chargeInfo.getHousingService().setAccountingPeriodTotal(OtherFormat.getBigDecimalTwo(invoce02.getCharge_total()));

//                    Порядок расчетов. Вообще ниразу нигде не написано что это? TODO
//                    chargeInfo.getHousingService().setCalcExplanation("Иное");

                        // перерасчеты
                        if (invoce02.getRepays() != null || invoce02.getExempts() != null ||
                                !invoce02.getRepays().equals(new BigDecimal(0)) || !invoce02.getExempts().equals(new BigDecimal(0))) {
                            chargeInfo.getHousingService().setServiceCharge(new ServiceChargeImportType());
//                        Перерасчеты, корректировки (руб)
                            chargeInfo.getHousingService().getServiceCharge().setMoneyRecalculation(OtherFormat.getBigDecimalTwo(invoce02.getRepays()));
//                          Льготы, субсидии, скидки (руб)
                            chargeInfo.getHousingService().getServiceCharge().setMoneyDiscount(OtherFormat.getBigDecimalTwo(invoce02.getExempts()));
                        }

/*
//                    Объем услуг - индивидульное потребление и(или) общедомовые нужды
                    chargeInfo.getHousingService().setConsumption(new PDServiceChargeType.HousingService.Consumption());
//                    Тип предоставления услуги: (I)ndividualConsumption - индивидульное потребление
                    chargeInfo.getHousingService().getConsumption().setVolume(new PDServiceChargeType.HousingService.Consumption.Volume());
                    chargeInfo.getHousingService().getConsumption().getVolume().setType("I");
                    chargeInfo.getHousingService().getConsumption().getVolume().setValue(rs.getBigDecimal(5).setScale(3, BigDecimal.ROUND_DOWN));
*/
                    }
                } else /* if (invoce02.getCode_parent().equals("1")) */ { // а если Дополнительная услуга -- закомментарил код - теперь все что не 50 и 51 идёт в дополнительные

                    chargeInfo.setAdditionalService(new PDServiceChargeType.AdditionalService());
//                    Код услуги (жилищной, коммунальной или дополнительной)
                    chargeInfo.getAdditionalService().setServiceType(new NsiRef());
                    chargeInfo.getAdditionalService().getServiceType().setName(invoce02.getGis_service());
                    chargeInfo.getAdditionalService().getServiceType().setCode(invoce02.getCode());
                    chargeInfo.getAdditionalService().getServiceType().setGUID(invoce02.getGis_service_uiid());

//                    Тариф
                    chargeInfo.getAdditionalService().setRate(OtherFormat.getBigDecimalTwo(invoce02.getTariff()));

//                    Итого к оплате за расчетный период, руб.
                    chargeInfo.getAdditionalService().setTotalPayable(OtherFormat.getBigDecimalTwo(invoce02.getForpay()));

//                    Всего начислено за расчетный период (без перерасчетов и льгот), руб.
                    chargeInfo.getAdditionalService().setAccountingPeriodTotal(OtherFormat.getBigDecimalTwo(invoce02.getCharge_total()));

//                    Порядок расчетов. Вообще ниразу нигде не написано что это? TODO
//                    chargeInfo.getAdditionalService().setCalcExplanation("Иное");

                    if(invoce02.getAmount_personal() != 0 || invoce02.getAmount_shared() != 0) {

                        chargeInfo.getAdditionalService().setConsumption(new PDServiceChargeType.AdditionalService.Consumption());

                        if (invoce02.getAmount_personal() != 0) {

                            PDServiceChargeType.AdditionalService.Consumption.Volume volumeIndividual = new PDServiceChargeType.AdditionalService.Consumption.Volume();
//                            Тип предоставления услуги: (I)ndividualConsumption - индивидульное потребление house(O)verallNeeds - общедомовые нужды
                            volumeIndividual.setType("I");
//                            Способ определения объемов КУ: (N)orm - Норматив (M)etering device - Прибор учета (O)ther - Иное
//                            volumeIndividual.setDeterminingMethod();
                            volumeIndividual.setValue(new BigDecimal(invoce02.getAmount_personal()).setScale(3, BigDecimal.ROUND_DOWN));
                            chargeInfo.getAdditionalService().getConsumption().getVolume().add(volumeIndividual);

                        }
                        if (invoce02.getAmount_shared() != 0) {

                            PDServiceChargeType.AdditionalService.Consumption.Volume volumeOverall = new PDServiceChargeType.AdditionalService.Consumption.Volume();
//                            Тип предоставления услуги: (I)ndividualConsumption - индивидульное потребление house(O)verallNeeds - общедомовые нужды
                            volumeOverall.setType("O");
//                            Способ определения объемов КУ: (N)orm - Норматив (M)etering device - Прибор учета (O)ther - Иное
//                            volumeIndividual.setDeterminingMethod();
                            volumeOverall.setValue(new BigDecimal(invoce02.getAmount_shared()).setScale(3, BigDecimal.ROUND_DOWN));
                            chargeInfo.getAdditionalService().getConsumption().getVolume().add(volumeOverall);
                        }
                    }
                    // перерасчеты
                    if (invoce02.getRepays() != null || invoce02.getExempts() != null ||
                            !invoce02.getRepays().equals(new BigDecimal(0)) || !invoce02.getExempts().equals(new BigDecimal(0))) {
                        chargeInfo.getAdditionalService().setServiceCharge(new ServiceChargeImportType());
//                        Перерасчеты, корректировки (руб)
                        chargeInfo.getAdditionalService().getServiceCharge().setMoneyRecalculation(OtherFormat.getBigDecimalTwo(invoce02.getRepays()));
//                          Льготы, субсидии, скидки (руб)
                        chargeInfo.getAdditionalService().getServiceCharge().setMoneyDiscount(OtherFormat.getBigDecimalTwo(invoce02.getExempts()));
                    }
                }
                if (chargeInfo.getAdditionalService() != null || chargeInfo.getHousingService() != null || chargeInfo.getMunicipalService() != null) {
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
                    for (ImportPaymentDocumentRequest.PaymentInformation information : paymentInformationMap) {
                        if(!invoce02.getPaymentInformation().getBankBIK().isEmpty() && !invoce02.getPaymentInformation().getOperatingAccountNumber().isEmpty()){
                            if (invoce02.getPaymentInformation().getBankBIK().equals(information.getBankBIK())
                                    && invoce02.getPaymentInformation().getOperatingAccountNumber().equals(information.getOperatingAccountNumber()))
                                paymentDocument.setPaymentInformationKey(information.getTransportGUID());
                        }
                    }

//                if(paymentInformationMap.get(invoce02.getPaymentInformation()) != null) {
//                        paymentDocument.setPaymentInformationKey(paymentInformationMap.get(invoce02.getPayee_id()).getTransportGUID());
//                    }else{
//                        throw new PreGISException("Критическая ошибка - не задан платежный агент ИД " + invoce02.getPayee_id());
//                    }
            }
            // закончили заносить начисления

            if(!paymentDocument.getPaymentInformationKey().isEmpty()) // если задан получатель
                paymentDocumentMap.put(paymentDocument.getPaymentDocumentNumber(), paymentDocument);
        }

        return paymentDocumentMap;
    }

    public ArrayList<ImportPaymentDocumentRequest.WithdrawPaymentDocument> getWithdrawPaymentDocument(final Integer houseGradID)
            throws SQLException, PreGISException, ParseException {

        ArrayList<ImportPaymentDocumentRequest.WithdrawPaymentDocument> paymentDocumentWithdrawArray = new ArrayList<>();
        try(Connection connection = ConnectionBaseGRAD.instance().getConnection()) {
            try (Statement statement = connection.createStatement()) {
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
        }
        return paymentDocumentWithdrawArray;
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
        HashMap<String, Invoice01> accountsMap = new HashMap<>();

        try(Connection connection = ConnectionBaseGRAD.instance().getConnection()) {
            try (Statement accountStatement = connection.createStatement()) {
                String sqlQuery = String.format("select raccountguid, rpd_type, rpd_no, rsq_total, " +
                                "rsq_live, rsq_heat, rtencount, rdebt, ravans, rpays_advance, " +
                                "rbik, rbank_account, rnls, raddress, rforpay from ex_gis_invoce01(%d, %s, %d, %d, null)",
                        month, year, ResourcesUtil.instance().getCompanyGradId(), houseGradID);
                ResultSet accountsResultSet = accountStatement.executeQuery(sqlQuery);


                while (accountsResultSet.next()) {
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
                    invoce01.setForpay(accountsResultSet.getDouble("rforpay"));

                    accountsMap.put(invoce01.getPd_no(), invoce01);
                }
            }
        }
        return  accountsMap;
    }

    private HashMap<String, ArrayList<Invoice02>> getChargesMap(final Integer houseGradID) throws SQLException, PreGISException {
        HashMap<String, ArrayList<Invoice02>> chargesMap = new HashMap<>();

        try(Connection connection = ConnectionBaseGRAD.instance().getConnection()) {
            try (Statement chargesStatement = connection.createStatement()) {
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
                        "RBANK_ACCOUNT," +
                        "RBIK " +
                        "from ex_gis_invoce02(" + month + ", " +
                        year + ", " +
                        ResourcesUtil.instance().getCompanyGradId() + ", " +
                        houseGradID + ", null)");

                while (charges.next()) {
                    if(charges.getString("RBANK_ACCOUNT") != null &&
                            !charges.getString("RBANK_ACCOUNT").isEmpty() &&
                            charges.getString("RBIK") != null &&
                            !charges.getString("RBIK").isEmpty()
                            ) {
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

                        ImportPaymentDocumentRequest.PaymentInformation paymentInformation = new ImportPaymentDocumentRequest.PaymentInformation();
                        paymentInformation.setOperatingAccountNumber(charges.getString("RBANK_ACCOUNT"));
                        paymentInformation.setBankBIK(charges.getString("RBIK"));

                        invoice02.setPaymentInformation(paymentInformation);

                        if (invoice02.getCode() == null) {
                            throw new PreGISException("Отсутствует код услуги ГИС!");
                        }

                        ArrayList<Invoice02> invoceList = chargesMap.get(invoice02.getPd_no());

                        if (invoceList == null) {
                            invoceList = new ArrayList<Invoice02>();
                            chargesMap.put(invoice02.getPd_no(), invoceList);
                        }
                        invoceList.add(invoice02);
                    }
                }
            }
        }
        return chargesMap;
    }

    /**
     * Метод устанавливает
     * @param paymentDocumentNumber - номер ЕПД в Град
     * @param resultType - результат с присвоенными ГИС идентификаторами
     * @param connection - соединение с БД. Передается, так как при обработке пакета данных выгоднее переиспользовать соединение, а не открывать его заново
     */
    public void addPaymentDocumentRegistryItem(final String paymentDocumentNumber,
                                               final CommonResultType resultType, Connection connection){
        try (Statement statement = connection.createStatement()){
            statement.execute(String.format("update EX_GIS_INVOCES set RPD_GIS_NO = '%s', GISUNIQUENUMBER = '%s' where RPD_NO = '%s'", resultType.getGUID(), resultType.getUniqueNumber(), paymentDocumentNumber));
        }catch(SQLException e){
            answerProcessing.sendInformationToClientAndLog("Не удалось выставить идентификаторы из ГИС ЖКХ документу с номером  " +  paymentDocumentNumber + "; " + e.getMessage(), LOGGER);
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
        try(Connection connection = ConnectionBaseGRAD.instance().getConnection()) {
            String sqlRequest = "select a.g_licschet, a.address_short\n" +
                    "from v_abons a\n" +
                    "join abonents2traits a2t on a2t.abonent_id = a.id\n" +
                    "join abonent_traits t on t.id = a2t.traits_id\n" +
                    "where t.code = 'ACCOUNTGUID'\n" +
                    "  and a2t.traits_value = '" + GUID + "'";

            try (Statement cstmt = connection.createStatement()) { // После использования должны все соединения закрыться
                ResultSet resultSet = cstmt.executeQuery(sqlRequest);
                resultSet.next();
                accountNLSMap.put(resultSet.getString(1), resultSet.getString(2));
                resultSet.close();
            }
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
