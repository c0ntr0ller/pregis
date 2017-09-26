package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.AckRequest;
import ru.gosuslugi.dom.schema.integration.base.ErrorMessageType;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.house_management.*;
import ru.gosuslugi.dom.schema.integration.house_management_service_async.Fault;
import ru.gosuslugi.dom.schema.integration.house_management_service_async.HouseManagementPortsTypeAsync;
import ru.gosuslugi.dom.schema.integration.house_management_service_async.HouseManagementServiceAsync;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;
import ru.progmatik.java.pregis.other.UrlLoader;

import javax.xml.ws.Holder;
import java.sql.SQLException;
import java.util.List;

/**
 * Класс диспетчер порта для методов HouseManagementPortsTypeAsync
 */
public class HomeManagementAsyncPort {
    private static final Logger LOGGER = Logger.getLogger(HomeManagementAsyncPort.class);
    private final String NAME_METHOD; // = "exportMeteringDeviceData";

    private final HouseManagementPortsTypeAsync port;
    private final AnswerProcessing answerProcessing;

    private HomeManagementAsyncPort(AnswerProcessing answerProcessing, String methodName) {
        this.answerProcessing = answerProcessing;
        this.NAME_METHOD = methodName;

        HouseManagementServiceAsync service = UrlLoader.instance().getUrlMap().get("homeManagementAsync") == null ? new HouseManagementServiceAsync()
                : new HouseManagementServiceAsync(UrlLoader.instance().getUrlMap().get("homeManagementAsync"));

        port = service.getHouseManagementPortAsync();
        OtherFormat.setPortSettings(service, port);
    }

    /**
     * Метод, получает данные о ПУ из ГИС ЖКХ.
     */
    public static GetStateResult callExportMeteringDeviceData(String fias, AnswerProcessing answerProcessing) throws SQLException, PreGISException {
        HomeManagementAsyncPort homeManagementAsyncPort = new HomeManagementAsyncPort(answerProcessing, "exportMeteringDeviceData");
        return homeManagementAsyncPort.callPort(getExportMeteringDeviceDataRequest(fias));
    }

    public static GetStateResult callExportAccountData(String fias, AnswerProcessing answerProcessing) throws SQLException, PreGISException {
        HomeManagementAsyncPort homeManagementAsyncPort = new HomeManagementAsyncPort(answerProcessing, "exportAccountData");
        return homeManagementAsyncPort.callPort(getExportAccountRequest(fias));
    }

    public static GetStateResult callExportHouseData(String fias, AnswerProcessing answerProcessing) throws SQLException, PreGISException {
        HomeManagementAsyncPort homeManagementAsyncPort = new HomeManagementAsyncPort(answerProcessing, "exportHouseData");
        return homeManagementAsyncPort.callPort(getExportHouseRequest(fias));
    }

    public static GetStateResult callImportAccountData(List<ImportAccountRequest.Account> accounts, AnswerProcessing answerProcessing) throws SQLException, PreGISException {
        HomeManagementAsyncPort homeManagementAsyncPort = new HomeManagementAsyncPort(answerProcessing, "importAccountData");
        return homeManagementAsyncPort.callPort(getImportAccountRequest(accounts));
    }

    /**
     * Основной метод. В зависимости от метода передает запрос в порт HouseManagementPortsTypeAsync, который создается в конструкторе
     * @param requestObject - объект запроса. В зависимости от метода преобразуется в тот или иной тип
     * @return
     * @throws SQLException
     * @throws PreGISException
     */
    private GetStateResult callPort(Object requestObject) throws SQLException, PreGISException {

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        ResultHeader resultHeader;
        Holder<ResultHeader> headerHolder = new Holder<>();
        ErrorMessageType resultErrorMessage;

        GetStateResult result;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            AckRequest ackRequest = null;

            switch (requestObject.getClass().getSimpleName()) {
                case "ExportMeteringDeviceDataRequest":
                    ackRequest = port.exportMeteringDeviceData((ExportMeteringDeviceDataRequest)requestObject, requestHeader, headerHolder);
                    break;
                case "ExportAccountRequest":
                    ackRequest = port.exportAccountData((ExportAccountRequest)requestObject, requestHeader, headerHolder);
                    break;
                case "ExportHouseRequest":
                    ackRequest = port.exportHouseData((ExportHouseRequest)requestObject, requestHeader, headerHolder);
                    break;
                case "ImportAccountRequest":
                    ackRequest = port.importAccountData((ImportAccountRequest)requestObject, requestHeader, headerHolder);
                    break;
                default:
                    answerProcessing.sendMessageToClient("Не определен метод экспорта/импорта " + NAME_METHOD);
            }

            if (ackRequest != null) {
                // сохраняем запрос
                answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, requestHeader, null, null, LOGGER);

                answerProcessing.sendMessageToClient(TextForLog.REQUEST_SENDED);

                HouseManagementAsyncResultWaiter houseManagementAsyncResultWaiter = new HouseManagementAsyncResultWaiter(ackRequest, NAME_METHOD, answerProcessing, port);

                result = houseManagementAsyncResultWaiter.getRequestResult();
                resultHeader = houseManagementAsyncResultWaiter.getHeaderHolder().value;
            }else{
                return null;
            }
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, requestHeader, LOGGER, fault);
            return null;
        }

        if (result == null) {
            throw new PreGISException("Не удалось получить данные от сервера ГИС ЖКХ");
        }else{
            resultErrorMessage = result.getErrorMessage();
        }

        if (resultErrorMessage != null) {
            if(resultErrorMessage.getErrorCode().contains("INT002012")) { // это не ошибка, просто нет данных
                answerProcessing.sendMessageToClient("Нет объектов для экспорта из ГИС ЖКХ");
            }else{
                answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, null, resultHeader, resultErrorMessage, LOGGER);
            }
            result = null;
        }

        return result;
    }


    /**
     * Метод формирует заголовок для запроса.
     * @return запрос.
     */
    private static ExportMeteringDeviceDataRequest getExportMeteringDeviceDataRequest(final String fias) {

        final ExportMeteringDeviceDataRequest request = new ExportMeteringDeviceDataRequest();
        request.setIsCurrentOrganization(true);
        request.setFIASHouseGuid(fias);
        request.setSearchArchived(true);
        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        return request;
    }

    private static ExportAccountRequest getExportAccountRequest(final String fias) {

        final ExportAccountRequest request = new ExportAccountRequest();
        request.setId(OtherFormat.getId());
        request.setFIASHouseGuid(fias);
        request.setVersion(request.getVersion());

        return request;
    }

    private static ExportHouseRequest getExportHouseRequest(final String fias) {

        final ExportHouseRequest request = new ExportHouseRequest();
        request.setId(OtherFormat.getId());
        request.setFIASHouseGuid(fias);
        request.setVersion(request.getVersion());

        return request;
    }

    private static ImportAccountRequest getImportAccountRequest(List<ImportAccountRequest.Account> accounts) {
//        Если нужно закрыть счет, то закрываем где нибудь затем передаём в этот метод
        ImportAccountRequest request = new ImportAccountRequest();

        request.setId(OtherFormat.getId());

        request.setVersion(request.getVersion());

        request.getAccount().addAll(accounts);

        return request;
    }
}
