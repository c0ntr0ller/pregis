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

    /**
     * Метод, возвращает реквизиты компаний для принятия платежей по указанному дому
     * @param connectionGrad подключение к БД Града.
     * @return ключ - идентификатор компании в БД Град, значение - платежные реквизиты пригодные для ГИС ЖКХ.
     * @throws SQLException
     * @throws PreGISException
     */
    public HashMap<Integer, ImportPaymentDocumentRequest.PaymentInformation> getAllOrganizationsPaymentInformation(Integer houseGradID, Connection connectionGrad) throws SQLException, PreGISException {

        HashMap<Integer, ImportPaymentDocumentRequest.PaymentInformation> paymentInformationMap = new HashMap<>();
        SimpleDateFormat sDate = new SimpleDateFormat("dd.MM.yyyy");

        try (Statement statement = connectionGrad.createStatement()) {

            ResultSet rs = statement.executeQuery("SELECT ORG_ID, RBANK_ACCOUNT, RBIK FROM EX_GIS_ORG_LIST(" + houseGradID + ")");

            while (rs.next()) {
                if (rs.getString(2) != null && rs.getString(3) != null) {

                    ImportPaymentDocumentRequest.PaymentInformation paymentInformation = new ImportPaymentDocumentRequest.PaymentInformation();
                    paymentInformation.setTransportGUID(OtherFormat.getRandomGUID());
                    paymentInformation.setOperatingAccountNumber(rs.getString(1));
                    paymentInformation.setBankBIK(rs.getString(2));

                    paymentInformationMap.put(rs.getInt(1), paymentInformation);
                }
            }
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        }
        if (paymentInformationMap.size() < 1) {
            throw new PreGISException("Не удалось получить реквизиты из процедуры \"EX_GIS_ORG_LIST\".");
        } else {
            return paymentInformationMap;
        }
    }
}
