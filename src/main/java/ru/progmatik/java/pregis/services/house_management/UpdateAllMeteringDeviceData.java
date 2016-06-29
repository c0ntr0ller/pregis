package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.services.house_management.ImportMeteringDeviceDataRequest;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;

/**
 * Класс, синхронизирует данные о ПУ.
 */
public class UpdateAllMeteringDeviceData {

    private static final Logger LOGGER = Logger.getLogger(UpdateAllMeteringDeviceData.class);
    private final AnswerProcessing answerProcessing;

    public UpdateAllMeteringDeviceData(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    public void updateMeteringDeviceData() {



    }

    public void createMeteringDevice() {
        ImportMeteringDeviceDataRequest.MeteringDevice meteringDevice = new ImportMeteringDeviceDataRequest.MeteringDevice();

        meteringDevice.setTransportGUID(OtherFormat.getRandomGUID());
        meteringDevice.setDeviceDataToUpdate(new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate());
        meteringDevice.setDeviceDataToCreate(new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate());
//        meteringDevice.getDeviceDataToCreate().

    }
}
