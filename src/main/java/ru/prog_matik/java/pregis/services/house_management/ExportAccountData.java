package ru.prog_matik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.house_management.ExportAccountRequest;
import ru.gosuslugi.dom.schema.integration.services.house_management.ExportAccountResult;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.Fault;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.HouseManagementPortsType;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.HouseManagementService;
import ru.prog_matik.java.pregis.connectiondb.SaveToBaseMessages;
import ru.prog_matik.java.pregis.other.OtherFormat;

import javax.xml.ws.Holder;

/**
 * Класс, экспорт сведений о лицевых счетах.
 * С его помощью можно получить "AccountGUID" и "UniqueNumber".
 */
public class ExportAccountData {

    private static final Logger LOGGER = Logger.getLogger(ExportAccountData.class);

    private static final String NAME_METHOD = "exportAccountData";

    private final HouseManagementService service = new HouseManagementService();
    private final HouseManagementPortsType port = service.getHouseManagementPort();

    /**
     * Конструктор, добавляет параметры для соединения.
     */
    public ExportAccountData() {
        OtherFormat.setPortSettings(service, port);
    }

    public void callExportAccountData() {

//        Создание загаловков сообщений (запроса и ответа)
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        Holder<ResultHeader> headerHolder = new Holder<>();
//        Создание объекта для сохранения лога сообщений в БД.
        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        ExportAccountResult result;

        try {
            result = port.exportAccountData(getExportAccountRequest(), requestHeader, headerHolder);
        } catch (Fault fault) {
//            Сохраняем ошибку в базу данных
            saveToBase.setRequestError(requestHeader, NAME_METHOD, fault);
            LOGGER.error(fault.getMessage());
            fault.printStackTrace();
            return;
        }
        saveToBase.setRequest(requestHeader, NAME_METHOD);

        saveToBase.setResult(headerHolder.value, NAME_METHOD, result.getErrorMessage());

        LOGGER.info("ExportDataProviderNsiItem - Successful.");
    }

    private ExportAccountRequest getExportAccountRequest() {

        ExportAccountRequest request = new ExportAccountRequest();
        request.setId(OtherFormat.getId());
        request.setFIASHouseGuid("f20a2f00-c9cf-485f-ac41-92af5b77e29a");

        return request;
    }
}
