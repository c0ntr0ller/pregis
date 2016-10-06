package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.house_management.ImportAccountRequest;
import ru.gosuslugi.dom.schema.integration.house_management_service.Fault;
import ru.gosuslugi.dom.schema.integration.house_management_service.HouseManagementPortsType;
import ru.gosuslugi.dom.schema.integration.house_management_service.HouseManagementService;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.*;

import javax.xml.ws.Holder;
import java.sql.SQLException;
import java.util.List;

/**
 * Метод, отправляет информацию о лицевых счетах в ГИС ЖКХ.
 */
public class ImportAccountData {

    private static final Logger LOGGER = Logger.getLogger(ImportAccountData.class);
    private static final String NAME_METHOD = "importAccountData";

    private final HouseManagementPortsType port;
    private AnswerProcessing answerProcessing;

    public ImportAccountData(AnswerProcessing answerProcessing) {

        this.answerProcessing = answerProcessing;

        HouseManagementService service = UrlLoader.instance().getUrlMap().get("homeManagement") == null ? new HouseManagementService()
                : new HouseManagementService(UrlLoader.instance().getUrlMap().get("homeManagement"));

        port = service.getHouseManagementPort();
        OtherFormat.setPortSettings(service, port);
    }

    public ru.gosuslugi.dom.schema.integration.house_management.ImportResult callImportAccountData(List<ImportAccountRequest.Account> accounts) throws SQLException {
        return sendImportAccountData(getImportAccountRequest(accounts));
    }

    private ru.gosuslugi.dom.schema.integration.house_management.ImportResult sendImportAccountData(ImportAccountRequest request) throws SQLException {

        answerProcessing.clearLabelForText();
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);

        Holder<ResultHeader> resultHolder = new Holder<>();

        RequestHeader headerRequest = OtherFormat.getRequestHeader();

        ru.gosuslugi.dom.schema.integration.house_management.ImportResult result;
        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            result = port.importAccountData(request, headerRequest, resultHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, headerRequest, LOGGER, fault);
            return null;
//        } catch (PreGISException e) {
//            answerProcessing.sendErrorToClient("sendImportAccountData(): ", "\"Синхронизация лицевых счетов\" ", LOGGER, e);
//            return;
        }
        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, headerRequest, resultHolder.value, result.getErrorMessage(), LOGGER);
        if (result.getErrorMessage() != null) return null;
        return result;
    }

//    /**
//     * Метод, формирует запрос для закрытия ЛС.
//     * @param account принимает абонента с указанной причиной закрытия ЛС и статуса isClosed.
//     * @return сформированный запрос.
//     */
//    public ImportAccountRequest getClosedImportAccountRequest(ImportAccountRequest.Account account) {
//
//        return getImportAccountRequest(account);
//    }

//    /**
//     * Метод, формирует запрос для обновления данных абонента.
//     * @param account данные абонента.
//     * @return сформированный запрос.
//     * @throws PreGISException
//     */
//    public ImportAccountRequest getUpdateImportAccountRequest(ImportAccountRequest.Account account) throws PreGISException {
//
//        setIsAccount(account);
//
//        return getImportAccountRequest(account);
//    }

//    /**
//     * Метод, создаёт данные для нового лицевого счета.
//     *
//     * @param account данные абонента.
//     * @return сформированный запрос.
//     */
//    public ImportAccountRequest getNewImportAccountRequest(ImportAccountRequest.Account account) throws PreGISException {
//
//        account.setAccountGUID(null);
//        account.setTransportGUID(OtherFormat.getRandomGUID()); // добавлять только если это новый элемент.
//
//        return getImportAccountRequest(account);
//    }

    /**
     * Метод, формирует запрос для отправки
     *
     * @param accounts список абонентов для добавления информации в ГИС ЖКХ.
     * @return сформированный запрос.
     */
    private ImportAccountRequest getImportAccountRequest(List<ImportAccountRequest.Account> accounts) {
//        Если нужно закрыть счет, то закрываем где нибудь затем передаём в этот метод
        ImportAccountRequest request = new ImportAccountRequest();

        request.setId(OtherFormat.getId());

        request.setVersion(request.getVersion());

        request.getAccount().addAll(accounts);

        return request;
    }

}
