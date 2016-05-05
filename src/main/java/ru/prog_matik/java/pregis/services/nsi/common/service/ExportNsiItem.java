package ru.prog_matik.java.pregis.services.nsi.common.service;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.HeaderType;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemRequest;
import ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult;
import ru.gosuslugi.dom.schema.integration.services.nsi_common_service.Fault;
import ru.prog_matik.java.pregis.connectiondb.SaveToBaseMessages;
import ru.prog_matik.java.pregis.other.OtherFormat;

import javax.xml.ws.Holder;
import java.math.BigInteger;

/**
 * Класс, позволяет получить записи конкретного справочника, а также только те записи справочника,
 * которые изменились после временной метки ModifiedAfter.
 * Created by andryha on 29.04.2016.
 */
public class ExportNsiItem {

    private static final Logger LOGGER = Logger.getLogger(ExportNsiItem.class);

    private static final String NAME_METHOD = "exportNsiItem";

    public void callExportNsiItem() {

        HeaderType requestHeader = OtherFormat.getISRequestHeader();

        Holder<ResultHeader> headerHolder = new Holder<>();

        SaveToBaseMessages saveToBase = new SaveToBaseMessages();

        ExportNsiItemResult result;

        NsiPortsTypeImpl portsType = new NsiPortsTypeImpl();

        try {
            result = portsType.exportNsiItem(getExportNsiItemRequest(), requestHeader, headerHolder);
        } catch (Fault fault) {
            saveToBase.setRequestError(requestHeader, NAME_METHOD, fault);
            LOGGER.error(fault.getMessage());
            fault.printStackTrace();
            return;
        }
        saveToBase.setRequest(requestHeader, NAME_METHOD);

        saveToBase.setResult(headerHolder.value, NAME_METHOD, result.getErrorMessage());

        LOGGER.info("Successful.");
    }

    /**
     * Метод, формирует объект для дальнейшей передачи его сервису ГИС ЖКХ.
     * @return ExportNsiItemRequest объект с указанными значениями, для передачи сервису ГИС ЖКХ.
     */
    private ExportNsiItemRequest getExportNsiItemRequest() {

        ExportNsiItemRequest request = new ExportNsiItemRequest();
        request.setId("signed-data-container");
        request.setListGroup("NSI"); // Обязательный атрибут
        request.setRegistryNumber(BigInteger.valueOf(225)); // Обязательный атрибут

        return request;
    }
}
