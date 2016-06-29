package ru.progmatik.java.pregis.connectiondb.grad.devices;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.services.house_management.ImportMeteringDeviceDataRequest;
import ru.progmatik.java.pregis.other.AnswerProcessing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

/**
 * Класс, получает данные из  БД ГРАД и формирует объект пригодный для ГИС ЖКХ.
 */
public class MeteringDeviceGRADDAO {

    private static final Logger LOGGER = Logger.getLogger(MeteringDeviceGRADDAO.class);
    private final AnswerProcessing answerProcessing;

    public MeteringDeviceGRADDAO(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    public ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate getMeteringDeviceForCreate() {
        ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate device = new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate();

        return device;
    }

    public ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate getMeteringDeviceForUpdate() {
        ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate device = new ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate();

        return device;
    }

    private void getExGisPu1(Connection connection) {


    }

    /**
     * Метод, выполняет SQL запрос и возвращает результат.
     * @param sqlRequest SQL запрос.
     * @param connection подключение к БД.
     * @return полученные данные из БД.
     * @throws SQLException
     */
    public ResultSet executor(String sqlRequest, Connection connection) throws SQLException {

        try (Statement statement = connection.createStatement()) {
            return statement.executeQuery(sqlRequest);
        }
    }

    /**
     * Метод, возвращает обработанную строку в массиве с данными.
     *
     * @param data - строка с данными.
     * @return String - массив данных.
     */
    private synchronized String[] getAllData(String data) {

        data = data + "|-1-"; // Если последний параметр пустой, то он в массив не попадет,
        // возникнут ошибки на ссылки на индексы массива.

        return data.split(Pattern.quote("|"));
    }

}
