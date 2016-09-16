package ru.progmatik.java.pregis.connectiondb.grad.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.bills.ImportPaymentDocumentRequest;
import ru.gosuslugi.dom.schema.integration.bills.PDServiceChargeType;
import ru.gosuslugi.dom.schema.integration.bills.PaymentDocumentType;
import ru.gosuslugi.dom.schema.integration.nsi_base.NsiRef;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemDataSet;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemGRADDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ServicesGisJkhForGradDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ServicesGisJkhForGradDataSet;
import ru.progmatik.java.pregis.exception.PreGISException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Класс, получает из БД ГРАДа информацию о платежном документе.
 */
public final class PaymentDocumentGradDAO {

    private static final Logger LOGGER = Logger.getLogger(PaymentDocumentGradDAO.class);

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
                                                                           Connection connectionGrad) throws SQLException, PreGISException {

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
                "RDEBT_OUT " +          // 18 задолженность на конец расчетного периода
                "from REP_INVOICE(" + abonId + ", " + calendar.get(Calendar.MONTH) + ", " +
                calendar.get(Calendar.YEAR) + ", 1, null, " + gradId + ")");

            while (rs.next()) {
//                TODO создать платежный документ
                ReferenceItemDataSet referenceItem = getReferenceItemDataSet(rs.getString(2), rs.getString(3), referenceItemGRADDAOAllItems);
                if (referenceItem == null) throw new PreGISException("Не удалось найти услугу " + rs.getString(2) + " в справочнике \"SERVICES_GIS_JKH\".");

                PaymentDocumentType.ChargeInfo chargeInfo = new PaymentDocumentType.ChargeInfo();

                if (referenceItem.getCodeParent().equals("51")) { // если Коммунальная услуга
//                    TODO
//                    chargeInfo.setMunicipalService();
                } else if (referenceItem.getCodeParent().equals("50")) { // если Жилищная услуга

                    chargeInfo.setHousingService(new PDServiceChargeType.HousingService());
//                    Код услуги (жилищной, коммунальной или дополнительной)
                    chargeInfo.getHousingService().setServiceType(new NsiRef());
                    chargeInfo.getHousingService().getServiceType().setName(referenceItem.getName());
                    chargeInfo.getHousingService().getServiceType().setCode(referenceItem.getCode());
                    chargeInfo.getHousingService().getServiceType().setGUID(referenceItem.getGuid());
//                    Тариф
                    chargeInfo.getHousingService().setRate(getBigDecimalTwo(rs.getBigDecimal(6)));
                    chargeInfo.getHousingService().setTotalPayable(getBigDecimalTwo(rs.getBigDecimal(12)));
                    chargeInfo.getHousingService().setAccountingPeriodTotal(
                            getBigDecimalTwo(rs.getBigDecimal(12)).subtract(
                                    getBigDecimalTwo(rs.getBigDecimal(11))).subtract(
                                    getBigDecimalTwo(rs.getBigDecimal(10))));


                } else if (referenceItem.getCodeParent().equals("1")) { // а если Дополнительная услуга
//                    TODO
//                    chargeInfo.setAdditionalService();
                }
                paymentDocument.getChargeInfo().add(chargeInfo);
//                System.out.println(paymentDocument.getChargeInfo().get(0).getHousingService().getAccountingPeriodTotal());
            }
            rs.close();
        }
        return paymentDocument;
    }

    public BigDecimal getBigDecimalTwo(BigDecimal value) {
//        MoneyType 2 знака после точки.
        value = value.setScale(2, BigDecimal.ROUND_DOWN);

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
}
