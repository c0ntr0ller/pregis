package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.HouseManagementPortsType;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.HouseManagementService;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;

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

    
}
