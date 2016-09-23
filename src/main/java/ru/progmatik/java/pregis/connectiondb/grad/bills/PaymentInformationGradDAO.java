package ru.progmatik.java.pregis.connectiondb.grad.bills;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.bills.ImportPaymentDocumentRequest;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Класс, получает банковские реквизиты из процедуры "EX_GIS_ORG_REKV", базы Града.
 * Так же использует процедуру "PROC_ORG_GET_BANK_DETAILS" и таблицу "USERORG", для получения реквизитов
 * компании, пригодных для использования в платежном документе ГИС ЖКХ.
 */
public final class PaymentInformationGradDAO {

    private static final Logger LOGGER = Logger.getLogger(PaymentInformationGradDAO.class);

    public final ImportPaymentDocumentRequest.PaymentInformation getPaymentInformation(Connection connectionGrad) throws SQLException, PreGISException {

        ImportPaymentDocumentRequest.PaymentInformation paymentInformation =
                new ImportPaymentDocumentRequest.PaymentInformation();

        try (CallableStatement cs = connectionGrad.prepareCall(
                "EXECUTE PROCEDURE EX_GIS_ORG_REKV(" + ResourcesUtil.instance().getCompanyGradId() + ", null,  null, null)");
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                String[] data = OtherFormat.getAllDataFromString(rs.getString(1));

                paymentInformation.setTransportGUID(OtherFormat.getRandomGUID());
                paymentInformation.setBankBIK(data[5]);
                paymentInformation.setOperatingAccountNumber(data[8]);
            }
        }
        return paymentInformation;
    }

    /**
     * Метод, возвращает реквизиты компаний для принятия платежей.
     * @param connectionGrad подключение к БД Града.
     * @return ключ - идентификатор компании в БД Град, значение - платежные реквизиты пригодные для ГИС ЖКХ.
     * @throws SQLException
     * @throws PreGISException
     */
    public HashMap<Integer, ImportPaymentDocumentRequest.PaymentInformation> getAllOrganizationsPaymentInformation(Connection connectionGrad) throws SQLException, PreGISException {

        return getAllOrganizationsRequisites(getOrganizations(connectionGrad), connectionGrad);

    }

    /**
     * Метод, получает из таблицы "USERORG", список идентификаторов всех организаций.
     * @param connectionGrad подключение к БД Града
     * @throws SQLException
     */
    private ArrayList<Integer> getOrganizations(Connection connectionGrad) throws SQLException, PreGISException {

        ArrayList<Integer> organizationsList = new ArrayList<>();

        try (ResultSet rs = connectionGrad.createStatement().executeQuery("SELECT ID FROM USERORG")) {

            while (rs.next()) {
                organizationsList.add(rs.getInt(1));
            }
        }
        if (organizationsList.size() < 1) {
            throw new PreGISException("Не удалось найти не одной организации в таблице \"USERORG\".");
        }
        return organizationsList;
    }

    /**
     * Метод, получает из процедуры "PROC_ORG_GET_BANK_DETAILS", "RBANK_ACCOUNT" - расчетный счет и
     * "RBIK" - БИК организации.
     * @param organizationsList список организаций для которых нужно получить реквизиты.
     * @param connectionGrad подключение к БД Града
     * @return ключ - идентификатор компании в БД Град, значение - платежные реквизиты пригодные для ГИС ЖКХ.
     * @throws SQLException
     * @throws PreGISException
     */
    private HashMap<Integer, ImportPaymentDocumentRequest.PaymentInformation> getAllOrganizationsRequisites(List<Integer> organizationsList, Connection connectionGrad) throws SQLException, PreGISException {

        HashMap<Integer, ImportPaymentDocumentRequest.PaymentInformation> paymentInformationMap = new HashMap<>();
        SimpleDateFormat sDate = new SimpleDateFormat("dd.MM.yyyy");

        try (Statement statement = connectionGrad.createStatement()) {

            ResultSet rs = null;

            for (Integer item : organizationsList) {

                rs = statement.executeQuery("SELECT RBANK_ACCOUNT, RBIK FROM PROC_ORG_GET_BANK_DETAILS(" + item + ", \'" +
                        sDate.format(new Date(System.currentTimeMillis())) + "\')");

                while (rs.next()) {
                    if (rs.getString(1) != null && rs.getString(2) != null) {

                        ImportPaymentDocumentRequest.PaymentInformation paymentInformation = new ImportPaymentDocumentRequest.PaymentInformation();
                        paymentInformation.setTransportGUID(OtherFormat.getRandomGUID());
                        paymentInformation.setOperatingAccountNumber(rs.getString(1));
                        paymentInformation.setBankBIK(rs.getString(2));

                        paymentInformationMap.put(item, paymentInformation);
                    }
                }
            }
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        }
        if (paymentInformationMap.size() < 1) {
            throw new PreGISException("Не удалось получить реквизиты из процедуры \"PROC_ORG_GET_BANK_DETAILS\".");
        } else {
            return paymentInformationMap;
        }
    }
}
