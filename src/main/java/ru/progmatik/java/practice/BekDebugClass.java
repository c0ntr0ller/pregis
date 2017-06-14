package ru.progmatik.java.practice;

import ru.gosuslugi.dom.schema.integration.house_management.ImportAccountRequest;
import ru.progmatik.java.pregis.ProgramAction;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.connectiondb.grad.account.AccountGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.bills.PaymentDocumentGradDAO;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemDataSet;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemGRADDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.services.house_management.UpdateAllAccountData;
import ru.progmatik.java.pregis.services.house_management.UpdateAllMeteringDeviceData;
import ru.progmatik.java.web.servlets.socket.ClientService;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Администратор on 20.04.2017.
 */
public class BekDebugClass {
    public static void main(String[] args) throws SQLException, ParseException, PreGISException, FileNotFoundException, SOAPException, JAXBException {
//        ReferenceItemGRADDAO gradDAO = new ReferenceItemGRADDAO();
//        Map<String, ReferenceItemDataSet> mapNsiWithCodeNsi = gradDAO.getMapItemsCodeParent("51", ConnectionBaseGRAD.instance().getConnection());
//
//        Integer cnt = mapNsiWithCodeNsi.size();
//        ClientService clientService = new ClientService();
//        ProgramAction action = new ProgramAction(clientService);
//        action.updateAccountData("21");
//        action.updateAllAccountData(21);

//        final UpdateAllAccountData updateAllAccountData = new UpdateAllAccountData(null);
//        final int state = updateAllAccountData.updateAllAccountData(21);

//        AccountGRADDAO agd = new AccountGRADDAO(null);
//        LinkedHashMap<String, ImportAccountRequest.Account> accs = agd.getAccountMapFromGrad(21, ConnectionBaseGRAD.instance().getConnection());

//        final UpdateAllMeteringDeviceData updateAllMeteringDeviceData = new UpdateAllMeteringDeviceData(null);
//        final int state = updateAllMeteringDeviceData.updateMeteringDeviceData(21);
        final PaymentDocumentGradDAO pdGradDao = new PaymentDocumentGradDAO(null);
        pdGradDao.addPaymentDocumentRegistryItem("1767", "20ВС786289-01-7031", ConnectionBaseGRAD.instance().getConnection());
    }
}
