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

    private void sendImportAccountData(ImportAccountRequest request) {

        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);

        Holder<ResultHeader> resultHolder = new Holder<>();

        RequestHeader headerRequest = OtherFormat.getRequestHeader();

        ImportResult result;
        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            result = port.importAccountData(request, headerRequest, resultHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, headerRequest, LOGGER, fault);
            return;
//        } catch (PreGISException e) {
//            answerProcessing.sendErrorToClient("sendImportAccountData(): ", "\"Синхронизация лицевых счетов\" ", LOGGER, e);
//            return;
        }
        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, headerRequest, resultHolder.value, result.getErrorMessage(), LOGGER);
    }

    /**
     * Метод, формирует запрос для обновления данных абонента.
     * @param account данные абонента.
     * @return сформированный запрос.
     * @throws PreGISException
     */
    private ImportAccountRequest getUpdateImportAccountRequest(ImportAccountRequest.Account account) throws PreGISException {

        setIsAccount(account);

        return getImportAccountRequest(account);
    }

    /**
     * Метод, создаёт данные для нового лицевого счета.
     *
     * @param account данные абонента.
     * @return сформированный запрос.
     */
    private ImportAccountRequest getNewImportAccountRequest(ImportAccountRequest.Account account) throws PreGISException {

//        Дописать getImportAccountRequest
//        TODO
        setIsAccount(account);

//        account.setCreationDate(OtherFormat.getDateNow()); // попробую без даты
        account.setAccountGUID(null);
        account.setTransportGUID(OtherFormat.getRandomGUID()); // добавлять только если это новый элемент.

        return getImportAccountRequest(account);
    }

    /**
     * Метод, формирует запрос для отправки
     *
     * @param account информацию о лицевом счете абонента
     * @return сформированный запрос.
     */
    private ImportAccountRequest getImportAccountRequest(ImportAccountRequest.Account account) {
//        Если нужно закрыть счет, то закрываем где нибудь затем передаём в этот метод
        ImportAccountRequest request = new ImportAccountRequest();
        request.setId(OtherFormat.getId());

        request.getAccount().add(account);

        return request;
    }

    /**
     * Метод, задаёт статус компании.
     * @param account абонент.
     * @throws PreGISException
     */
    private void setIsAccount(ImportAccountRequest.Account account) throws PreGISException {

        if (ResourcesUtil.instance().getCompanyRole() != null && ResourcesUtil.instance().getCompanyRole().equalsIgnoreCase("RSO")) {
            account.setIsRSOAccount(true);
            account.setIsUOAccount(false);
        } else {
            account.setIsUOAccount(true);
            account.setIsRSOAccount(false);
        }
    }
}
