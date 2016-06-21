package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.ImportResult;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.house_management.ImportAccountRequest;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.Fault;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.HouseManagementPortsType;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.HouseManagementService;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;
import ru.progmatik.java.pregis.other.TextForLog;

import javax.xml.ws.Holder;

/**
 * Метод, отправляет информацию о лицевых счетах в ГИС ЖКХ.
 */
public class ImportAccountData {

    private static final Logger LOGGER = Logger.getLogger(ImportAccountData.class);
    private static final String NAME_METHOD = "importAccountData";

    private final HouseManagementService service = new HouseManagementService();
    private final HouseManagementPortsType port = service.getHouseManagementPort();
    private AnswerProcessing answerProcessing;

    public ImportAccountData(AnswerProcessing answerProcessing) {
        OtherFormat.setPortSettings(service, port);
        this.answerProcessing = answerProcessing;
    }

    public void callImportAccountData() {

    }

    private void sendImportAccountData() {

        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);

        Holder<ResultHeader> resultHolder = new Holder<>();

        RequestHeader headerRequest = OtherFormat.getRequestHeader();

        ImportResult result;
        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            result = port.importAccountData(getImportAccountRequest(), headerRequest, resultHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, headerRequest, LOGGER, fault);
            return;
        } catch (PreGISException e) {
            answerProcessing.sendErrorToClient("sendImportAccountData(): ", LOGGER, e);
            return;
        }
        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, headerRequest, resultHolder.value, result.getErrorMessage(), LOGGER);
    }

    private ImportAccountRequest getImportAccountRequest() throws PreGISException {

        ImportAccountRequest request = new ImportAccountRequest();
        request.setId(OtherFormat.getId());

        ImportAccountRequest.Account account = new ImportAccountRequest.Account();

        account.setTransportGUID(OtherFormat.getRandomGUID());

        if (ResourcesUtil.instance().getCompanyRole() != null && ResourcesUtil.instance().getCompanyRole().equalsIgnoreCase("RSO")) {
            account.setIsRSOAccount(true);
            account.setIsUOAccount(false);
        } else {
            account.setIsUOAccount(true);
            account.setIsRSOAccount(false);
        }
        account.setTransportGUID(OtherFormat.getRandomGUID());
        account.setLivingPersonsNumber((byte) 2);
        request.getAccount().add(account);

        return request;
    }
}
