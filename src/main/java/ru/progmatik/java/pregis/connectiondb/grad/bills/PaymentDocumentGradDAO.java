package ru.progmatik.java.pregis.connectiondb.grad.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.bills.*;
import ru.gosuslugi.dom.schema.integration.nsi_base.NsiRef;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemDataSet;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemGRADDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ServicesGisJkhForGradDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ServicesGisJkhForGradDataSet;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Класс, получает из БД ГРАДа информацию о платежном документе.
 */
public final class PaymentDocumentGradDAO {

    private static final Logger LOGGER = Logger.getLogger(PaymentDocumentGradDAO.class);
    private final AnswerProcessing answerProcessing;

    private int stateError;

    public PaymentDocumentGradDAO(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    /**
     * Метод, отправляет в БД Града запрос, формирует платежный документ.
     *
     * @param abonId          идентификатор абонент в БД ГРАДа
     * @param gradId          идентификатор компании в БД ГРАД.
     * @param calendar        дата на которую нужно сформировать ПД.
     * @param paymentDocument документ.
     * @param connectionGrad  подключение к БД ГРАД.
     * @throws SQLException
     */
    public ImportPaymentDocumentRequest.PaymentDocument getPaymentDocument(int abonId, Integer gradId, Calendar calendar,
                                                                           ImportPaymentDocumentRequest.PaymentDocument paymentDocument,
                                                                           Connection connectionGrad) throws SQLException {

//        TODO удалить
        calendar.set(Calendar.YEAR, 2015);

        ReferenceItemGRADDAO referenceItemGRADDAO = new ReferenceItemGRADDAO();
        ArrayList<ReferenceItemDataSet> referenceItemGRADDAOAllItems = referenceItemGRADDAO.getAllItems(connectionGrad);

//        Процедура принимает: ид абонента, расчетный период (месяц), расчетный период (год),
//        1 – подавлять вывод нулевых строк, ид жилищной организации, ид поставщика.
        try (Statement statement = connectionGrad.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT " +
                    "RSUPPLIER_ID, " +      // 1  ид поставщика
                    "RGROUP, " +            // 2  группа услуг
                    "RSUPERGROUP_CODE, " +  // 3  код суперуслуги
                    "RUNIT, " +             // 4  ед. измерения услуги
                    "RAMOUNT_PERSONAL, " +  // 5  индивидуальное потребление
                    "RAMOUNT_SHARED, " +    // 6  потребление МОП/ОДН
                    "RTARIFF, " +           // 7  тариф
                    "RCHARGE_PERSONAL, " +  // 8  сумма начисления за индивидуальное потребление
                    "RCHARGE_SHARED, " +    // 9  сумма начисления за потребление МОП/ОДН
                    "RREPAYS_PERSONAL, " +  // 10 перерасчеты по индивидуальному потреблению
                    "RREPAYS_SHARED, " +    // 11 перерасчеты по потреблению МОП/ОДН
                    "RCHARGE, " +           // 12 итоговая сумма начислений
                    "RNORM_PERSONAL, " +    // 13 норматив потребления на индивидуальные нужды
                    "RNORM_SHARED, " +      // 14 норматив потребления на общедомовые нужды
                    "RMETERS_PERSONAL, " +  // 15 текущие показания ИПУ
                    "RPAYS_ADVANCE, " +     // 16 платежи в расчетном периоде (аванс)
                    "RDEBT_IN, " +          // 17 задолженность на начало расчетного периода
                    "RDEBT_OUT, " +         // 18 задолженность на конец расчетного периода
                    "RREASON, " +           // 19 Основания перерасчётов
                    "RSUM, " +              // 20 Сумма перерасчётов
                    "RMETERS_SHARED, " +    // Текущие показания приборов учета коммунальных услуг. Общедомовые (ОДПУ). Раздел 4.
                    "RSUMM_AMOUNT_PERSONAL, " + // Суммарный объем коммунальных услуг в доме. В помещениях дома. Раздел 4.
                    "RSUMM_AMOUNT_SHARED " + // Суммарный объем коммунальных услуг в доме. На ощедомовые нужды. Раздел 4.
                    "FROM EX_GIS_INVOICE(" + abonId +", " + calendar.get(Calendar.MONTH) + ", " +
                    calendar.get(Calendar.YEAR) + ", null,  null, " + gradId + ", null)");

            while (rs.next()) {

//                TODO создать платежный документ
                ReferenceItemDataSet referenceItem = getReferenceItemDataSet(rs.getString(2), rs.getString(3), referenceItemGRADDAOAllItems);
                if (referenceItem == null) {
                    setStateError(0);
                    answerProcessing.sendInformationToClientAndLog("Не удалось найти услугу " +
                            rs.getString(2) + " в справочнике \"SERVICES_GIS_JKH\".", LOGGER);
                } else if (referenceItem.getName() == null) {
                    setStateError(0);
                    answerProcessing.sendInformationToClientAndLog("Не удалось найти услугу " +
                            "без имени в справочнике \"SERVICES_GIS_JKH\".", LOGGER);
                } else {

                    PaymentDocumentType.ChargeInfo chargeInfo = null;
                    if (referenceItem.getCodeParent().equals("51")) { // если Коммунальная услуга
//                    TODO
                        chargeInfo = new PaymentDocumentType.ChargeInfo();
                        chargeInfo.setMunicipalService(new PDServiceChargeType.MunicipalService());

//                    Код услуги (жилищной, коммунальной или дополнительной)
                        chargeInfo.getMunicipalService().setServiceType(new NsiRef());
                        chargeInfo.getMunicipalService().getServiceType().setName(referenceItem.getName());
                        chargeInfo.getMunicipalService().getServiceType().setCode(referenceItem.getCode());
                        chargeInfo.getMunicipalService().getServiceType().setGUID(referenceItem.getGuid());

//                    Тариф
                        chargeInfo.getMunicipalService().setRate(getBigDecimalTwo(rs.getBigDecimal(7)));

//                    Итого к оплате за расчетный период, руб.
                        chargeInfo.getMunicipalService().setTotalPayable(getBigDecimalTwo(rs.getBigDecimal(12)));

//                    Всего начислено за расчетный период (без перерасчетов и льгот), руб.
                        chargeInfo.getMunicipalService().setAccountingPeriodTotal(
                                getBigDecimalTwo(rs.getBigDecimal(12)).subtract(
                                        getBigDecimalTwo(rs.getBigDecimal(11))).subtract(
                                        getBigDecimalTwo(rs.getBigDecimal(10))));

//                        Порядок расчетов ??? TODO
                        chargeInfo.getMunicipalService().setCalcExplanation("Иное");

                        chargeInfo.getMunicipalService().setServiceCharge(new ServiceChargeType());
//                        Перерасчеты, корректировки (руб)
                        chargeInfo.getMunicipalService().getServiceCharge().setMoneyRecalculation(getBigDecimalTwo(rs.getBigDecimal(10)));
//                          Льготы, субсидии, скидки (руб) - отсутствует в ГРАДе
//                        chargeInfo.getMunicipalService().getServiceCharge().setMoneyDiscount();

//                        Рассрочка. Раздел 6. Иваненко Дмитрий сказал, что у наших клиентов нет таких.
//                        chargeInfo.getMunicipalService().setPiecemealPayment(new PiecemealPayment());

//                        Перерасчеты. Раздел 5.
                        if (rs.getBigDecimal(20) != null && rs.getString(19) != null) {
                            chargeInfo.getMunicipalService().setPaymentRecalculation(new PDServiceChargeType.MunicipalService.PaymentRecalculation());
//                            Основания перерасчётов
                            chargeInfo.getMunicipalService().getPaymentRecalculation().setRecalculationReason(rs.getString(19));
//                            Сумма
                            chargeInfo.getMunicipalService().getPaymentRecalculation().setSum(getBigDecimalTwo(rs.getBigDecimal(20)));
                        }

//                        Норматив потребления коммунальной услуги
                        chargeInfo.getMunicipalService().setServiceInformation(new ServiceInformation());
                        if (rs.getString(15) != null && !rs.getString(15).trim().isEmpty()) {
//                        Текущие показания приборов учёта коммунальных услуг - индивидульное потребление
                            chargeInfo.getMunicipalService().getServiceInformation().
                                    setIndividualConsumptionCurrentValue(new BigDecimal(rs.getString(15)).setScale(4, BigDecimal.ROUND_DOWN));
                        }

//                        Текущие показания приборов учёта коммунальных услуг - общедомовые нужды
//                        TODO Иваненко должен дать.
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

                    } else if (referenceItem.getCodeParent().equals("50")) { // если Жилищная услуга

                        chargeInfo = new PaymentDocumentType.ChargeInfo();
                        chargeInfo.setHousingService(new PDServiceChargeType.HousingService());
//                    Код услуги (жилищной, коммунальной или дополнительной)
                        chargeInfo.getHousingService().setServiceType(new NsiRef());
                        chargeInfo.getHousingService().getServiceType().setName(referenceItem.getName());
                        chargeInfo.getHousingService().getServiceType().setCode(referenceItem.getCode());
                        chargeInfo.getHousingService().getServiceType().setGUID(referenceItem.getGuid());

//                    Тариф
                        chargeInfo.getHousingService().setRate(getBigDecimalTwo(rs.getBigDecimal(7)));

//                    Итого к оплате за расчетный период, руб.
                        chargeInfo.getHousingService().setTotalPayable(getBigDecimalTwo(rs.getBigDecimal(12)));

//                    Всего начислено за расчетный период (без перерасчетов и льгот), руб.
                        chargeInfo.getHousingService().setAccountingPeriodTotal(
                                getBigDecimalTwo(rs.getBigDecimal(12)).subtract(
                                        getBigDecimalTwo(rs.getBigDecimal(11))).subtract(
                                        getBigDecimalTwo(rs.getBigDecimal(10))));

//                    Порядок расчетов. Вообще ниразу нигде не написано что это? TODO
                        chargeInfo.getHousingService().setCalcExplanation("Иное");

//                    Перерасчеты, корректировки
                        chargeInfo.getHousingService().setServiceCharge(new ServiceChargeType());
//                    Перерасчеты, корректировки (руб)
                        chargeInfo.getHousingService().getServiceCharge().setMoneyRecalculation(getBigDecimalTwo(rs.getBigDecimal(10)));
//                    Льготы, субсидии, скидки (руб) - отсутствует в ГРАДе
//                    chargeInfo.getHousingService().getServiceCharge().setMoneyDiscount();

//                    Объем услуг - индивидульное потребление и(или) общедомовые нужды
                        chargeInfo.getHousingService().setConsumption(new PDServiceChargeType.HousingService.Consumption());
//                    Тип предоставления услуги: (I)ndividualConsumption - индивидульное потребление
                        chargeInfo.getHousingService().getConsumption().setVolume(new PDServiceChargeType.HousingService.Consumption.Volume());
                        chargeInfo.getHousingService().getConsumption().getVolume().setType("I");
                        chargeInfo.getHousingService().getConsumption().getVolume().setValue(rs.getBigDecimal(5).setScale(3, BigDecimal.ROUND_DOWN));

                    } else if (referenceItem.getCodeParent().equals("1")) { // а если Дополнительная услуга
//                    TODO
                        chargeInfo = new PaymentDocumentType.ChargeInfo();
//                    chargeInfo.setAdditionalService();
                    }
                    if (chargeInfo != null) {
                        paymentDocument.getChargeInfo().add(chargeInfo);
                    }

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
                    }

                }
            }
            rs.close();
        }
        if (paymentDocument.getDebtPreviousPeriods() != null) {
            if (paymentDocument.getDebtPreviousPeriods().compareTo(BigDecimal.valueOf(0.0)) < 0) {
                paymentDocument.setDebtPreviousPeriods(new BigDecimal(0.0));
            }
        }
        if (paymentDocument.getTotalPiecemealPaymentSum() != null &&
                paymentDocument.getTotalPiecemealPaymentSum().compareTo(BigDecimal.valueOf(0.0)) < 0) {
            paymentDocument.setTotalPiecemealPaymentSum(new BigDecimal(0.0));
        }

        paymentDocument.setExpose(true);

        return paymentDocument;
    }

    public BigDecimal getBigDecimalTwo(BigDecimal value) {
//        MoneyType 2 знака после точки.
        value = value.setScale(2, BigDecimal.ROUND_DOWN);

        return value;
    }

    /**
     * Метод, получает все идентификаторы организаций из платежного документа.
     * @param abonId идентификатор абонент в БД ГРАДа.
     * @param calendar дата на которую нужно сформировать ПД.
     * @param connectionGrad подключение к БД ГРАД.
     * @return список идентификаторов организаци получателей денежных средств.
     * @throws SQLException
     */
    public ArrayList<Integer> getOrganizationIdsFromPaymantsDocument(Integer abonId, Calendar calendar, Connection connectionGrad) throws SQLException {

        ArrayList<Integer> list = new ArrayList<>();

        try (Statement statement = connectionGrad.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT " +
                    "RSUPPLIER_ID " +      // 1  ид поставщика
                    "FROM EX_GIS_INVOICE(" + abonId + ", " + calendar.get(Calendar.MONTH) + ", " +
                    calendar.get(Calendar.YEAR) + ", null,  null, null, null)");
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            rs.close();
        }
        return list;
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
