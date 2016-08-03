package ru.progmatik.java.pregis.services.device_metering;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.NsiRef;
import ru.gosuslugi.dom.schema.integration.services.device_metering.ExportMeteringDeviceHistoryRequest;
import ru.gosuslugi.dom.schema.integration.services.device_metering.ExportMeteringDeviceHistoryResult;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemDataSet;
import ru.progmatik.java.pregis.connectiondb.localdb.meteringdevice.MeteringDeviceValuesLocalDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSIDAO;
import ru.progmatik.java.pregis.other.AnswerProcessing;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс, обрабатывает показания ПУ.
 */
public class UpdateMeteringDeviceValues {

    private static final Logger LOGGER = Logger.getLogger(UpdateMeteringDeviceValues.class);
    private final AnswerProcessing answerProcessing;
    private MeteringDeviceValuesLocalDAO deviceValuesLocalDAO;


    public UpdateMeteringDeviceValues(AnswerProcessing answerProcessing) {

        this.answerProcessing = answerProcessing;
        deviceValuesLocalDAO = new MeteringDeviceValuesLocalDAO();
    }

    public ExportMeteringDeviceHistoryResult updateMeteringDeviceValues() throws SQLException {

        return getExportMeteringDeviceHistory("b58c5da4-8d62-438f-b11e-d28103220952");
    }



    /**
     * Метод, получает ФИАС дома, формирует запрос со всеми типами приборов учёта, отправляет запрос в ГИС ЖКХ, получает ответ.
     * @return ответ из ГИС ЖКХ.
     * @throws SQLException
     */
    private ExportMeteringDeviceHistoryResult getExportMeteringDeviceHistory(String fias) throws SQLException {

        ReferenceNSIDAO nsidao = new ReferenceNSIDAO();
        ArrayList<ReferenceItemDataSet> allItems = nsidao.getAllItemsCodeParent("27");
        ArrayList<ru.gosuslugi.dom.schema.integration.base.NsiRef> nsiList = new ArrayList<>();

        for (ReferenceItemDataSet item : allItems) {
            ru.gosuslugi.dom.schema.integration.base.NsiRef nsiRef = new NsiRef();
            nsiRef.setCode(item.getCode());
            nsiRef.setGUID(item.getGuid());
            nsiRef.setName(item.getName());
            nsiList.add(nsiRef);
        }

//        Формируем объект для запроса
        ExportMeteringDeviceHistoryRequest request = new ExportMeteringDeviceHistoryRequest();
        request.setFIASHouseGuid(fias); // b58c5da4-8d62-438f-b11e-d28103220952
        request.getMeteringDeviceType().addAll(nsiList);

        ExportMeteringDeviceHistory deviceHistory = new ExportMeteringDeviceHistory(answerProcessing);
        return deviceHistory.getExportMeteringHistoryResult(request);
    }

    private void updateAllMeteringDeviceValues(ExportMeteringDeviceHistoryResult result) {
//        TODO
    }
}
