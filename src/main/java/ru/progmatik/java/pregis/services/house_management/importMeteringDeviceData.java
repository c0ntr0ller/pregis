package ru.progmatik.java.pregis.services.house_management;

import ru.gosuslugi.dom.schema.integration.services.house_management.ImportMeteringDeviceDataRequest;
import ru.progmatik.java.pregis.other.OtherFormat;

/**
 * Класс, передаёт данные о ПУ в ГИС ЖКХ.
 */
public class importMeteringDeviceData {

    public void callImportMeteringDeviceData(String fias) {

        ImportMeteringDeviceDataRequest request = new ImportMeteringDeviceDataRequest();
        request.setId(OtherFormat.getId());
        request.setFIASHouseGuid(fias);
        request.getMeteringDevice().add(new ImportMeteringDeviceDataRequest.MeteringDevice());
        request.getMeteringDevice().get(0).setDeviceDataToCreate(new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate());

    }
}
