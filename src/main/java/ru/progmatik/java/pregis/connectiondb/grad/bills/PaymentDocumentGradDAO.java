package ru.progmatik.java.pregis.connectiondb.grad.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.bills.ImportPaymentDocumentRequest;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemDataSet;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemGRADDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ServicesGisJkhForGradDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ServicesGisJkhForGradDataSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Класс, получает из БД ГРАДа информацию о платежном документе.
 */
public class PaymentDocumentGradDAO {

    private static final Logger LOGGER = Logger.getLogger(PaymentDocumentGradDAO.class);

    public void getPaymentDocument(int abonId, int gradId, Calendar calendar,
                                   ImportPaymentDocumentRequest.PaymentDocument paymentDocument,
                                   Connection connectionGrad) throws SQLException {

//        Процедура принимает: ид абонента, расчетный период (месяц), расчетный период (год),
//        1 – подавлять вывод нулевых строк, ид жилищной организации, ид поставщика.
        try (PreparedStatement ps = connectionGrad.prepareStatement("SELECT" +
                "RSUPPLIER_ID, " +      // ид поставщика
                "RGROUP, " +            // группа услуг
                "RSUPERGROUP_CODE, " +  // код суперуслуги
                "RUNIT, " +             // ед. измерения услуги
                "RAMOUNT_PERSONAL, " +  // индивидуальное потребление
                "RAMOUNT_SHARED, " +    // потребление МОП/ОДН
                "RTARIFF, " +           // тариф
                "RREPAYS_PERSONAL, " +  // перерасчеты по индивидуальному потреблению
                "RREPAYS_SHARED, " +    // перерасчеты по потреблению МОП/ОДН
                "RNORM_PERSONAL, " +    // норматив потребления на индивидуальные нужды
                "RNORM_SHARED, " +      // норматив потребления на общедомовые нужды
                "RMETERS_PERSONAL, " +  // текущие показания ИПУ
                "RPAYS_ADVANCE, " +     // платежи в расчетном периоде (аванс)
                "RDEBT_IN, " +          // задолженность на начало расчетного периода
                "RDEBT_OUT " +          // задолженность на конец расчетного периода
                "from REP_INVOICE(?, ?, ?, 1, null, ?)")) {

            ps.setInt(1, abonId);
            ps.setInt(2, calendar.get(Calendar.MONTH));
            ps.setInt(3, calendar.get(Calendar.YEAR));
            ps.setInt(4, gradId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
//                TODO создать платежный документ
            }

            rs.close();
        }
    }

    /**
     * Метод, по полученым даным из таблицы пытается найти соответствующую услугу в справочнике ГИС ЖКХ.
     *  @param rgroup          название услуги.
     * @param rsuperGroupCode название группы услуги.*/
    private ReferenceItemDataSet getNsiRef(String rgroup, String rsuperGroupCode, Connection connectionGrad) throws SQLException {

        ReferenceItemGRADDAO referenceItemGRADDAO = new ReferenceItemGRADDAO();
        ArrayList<ReferenceItemDataSet> referenceItemGRADDAOAllItems = referenceItemGRADDAO.getAllItems(connectionGrad);

        HashMap<String, Integer> associationsMap = getAssociationsMap();
        if (associationsMap.containsKey(rsuperGroupCode)) {
            return searchNsiRef(referenceItemGRADDAOAllItems, associationsMap.get(rsuperGroupCode));
        } else {
            return searchNsiRefWithName(referenceItemGRADDAOAllItems, rgroup);
        }
    }

    /**
     * Метод, пытаеться найти похожую услугу по имени.
     * @param referenceItemGRADDAOAllItems список всех услуг из справочника ГИС ЖКХ.
     * @param serviceName имя услуги из БД Града.
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
     * @param referenceItemGRADDAOAllItems список записей из справочника.
     * @param id идентификатор записи из справочника ГИС ЖКХ.
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
