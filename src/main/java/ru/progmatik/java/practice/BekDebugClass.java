package ru.progmatik.java.practice;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.signature.XMLSignatureException;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.ResourcesUtil;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by Администратор on 20.04.2017.
 */
public class BekDebugClass {
    public static void main(String[] args) throws SQLException, ParseException, PreGISException, FileNotFoundException, SOAPException, JAXBException, XMLSignatureException, AlgorithmAlreadyRegisteredException, ClassNotFoundException {

        ResourcesUtil.instance().createFolder("exchange");
//        ReferenceItemGRADDAO gradDAO = new ReferenceItemGRADDAO();
//        Map<String, ReferenceItemDataSet> mapNsiWithCodeNsi = gradDAO.getMapItemsCodeParent("51", ConnectionBaseGRAD.instance().getConnection());
//
//        Integer cnt = mapNsiWithCodeNsi.size();
//        ClientService clientService = new ClientService();
//        ProgramAction action = new ProgramAction(clientService);
//        action.updateAccountData("21");
//        action.callUpdateAllAccountData(21);

//        final UpdateAllAccountData callUpdateAllAccountData = new UpdateAllAccountData(null);
//        final int state = callUpdateAllAccountData.callUpdateAllAccountData(21);

//        AccountGRADDAO agd = new AccountGRADDAO(null);
//        LinkedHashMap<String, ImportAccountRequest.Account> accs = agd.getAccountMapFromGrad(21, ConnectionBaseGRAD.instance().getConnection());

//        final UpdateAllMeteringDeviceData updateAllMeteringDeviceData = new UpdateAllMeteringDeviceData(null);
//        final int state = updateAllMeteringDeviceData.updateMeteringDeviceData(21);
//        final PaymentDocumentGradDAO pdGradDao = new PaymentDocumentGradDAO(null);
//        pdGradDao.addPaymentDocumentRegistryItem("1767", "20ВС786289-01-7031", ConnectionBaseGRAD.instance().getConnection());


/*
        Security.addProvider(new BouncyCastleProvider());  // Добавим Bouncy Castle
        XmlDSignTools.init("BC"); // Инициализация алгоритма ГОСТ для подписи файлов


        ExportCAChData exportCAChData = new ExportCAChData(null);
        GetStateResult result = exportCAChData.callExportHouseContracts("b7c62737-404f-461d-85b1-ae0242ea5ea8");

        StringBuilder stringBuilder = new StringBuilder();
        for(ExportCAChResultType entry: result.getExportCAChResult()){
            for(ExportCAChResultType.Contract.ContractObject contractObject : entry.getContract().getContractObject()){
                stringBuilder.append("HouseServices");
                stringBuilder.append('\n');
                for(ExportCAChResultType.Contract.ContractObject.HouseService houseService: contractObject.getHouseService()){
                    stringBuilder.append(houseService.getServiceType().getName());
                }

                stringBuilder.append("AddServices");
                stringBuilder.append('\n');
                for(ExportCAChResultType.Contract.ContractObject.AddService addService: contractObject.getAddService()){
                    stringBuilder.append(addService.getServiceType().getName());
                }
                System.out.println(stringBuilder.toString());
            }
        }
*/
    }
}
