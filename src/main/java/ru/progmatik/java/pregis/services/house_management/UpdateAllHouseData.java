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
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;
import ru.progmatik.java.pregis.other.UrlLoader;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseRecord;

import javax.xml.ws.Holder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, экспорт данных домов.
 * Получает ФИАСы всех домов из ГРАДа, формирует запрос в ГИС ЖКХ, полученый результат заносит в БД.
 * Получаем данные из системы ГИС ЖКХ.
 */
public class UpdateAllHouseData {

    private static final Logger LOGGER = Logger.getLogger(UpdateAllHouseData.class);
    private final AnswerProcessing answerProcessing;

    // Статус ошибок:
    //  1 - ошибок нет.
    //  0 - ошибки есть, но работа продолжена.
    // -1 - ошибка дальнейшая работа не возможна.
    private int errorStatus;

    /**
     * Конструктор, получает в параметр сылку на веб-сокет.
     */
    public UpdateAllHouseData(final AnswerProcessing answerProcessing) {

        if(answerProcessing != null) {
            this.answerProcessing = answerProcessing;
        }else{
            this.answerProcessing = new AnswerProcessing();
        }

        errorStatus = 1;
    }

    /**
     * Метод, получает из БД ГРАД все дома содержащие ФИАС, создаёт по ним запрос в ГИС ЖКХ.
     *
     * @throws PreGISException ошибки обработанные приложением.
     * @throws SQLException    ошибки связанные с базой данных.
     */
    public void callUpdateAllHouseData(final Integer houseGradId) throws PreGISException, SQLException, ParseException {
        try (Connection connectionGrad = ConnectionBaseGRAD.instance().getConnection()) {
            final HouseGRADDAO gradDao = new HouseGRADDAO(answerProcessing);
//        Обработка МКД
            final LinkedHashMap<String, HouseRecord> mapMKD = gradDao.getAllHouseFIASAddress(houseGradId, connectionGrad); // Берем из процедуры все дома, которые содержат ФИАС

            if (mapMKD != null) {
                for (Map.Entry<String, HouseRecord> entry : mapMKD.entrySet()) {
                    getHouseData(entry.getValue(), gradDao, connectionGrad);
                }
            }

//          Обработка ЖД.
            final LinkedHashMap<String, HouseRecord> mapJd = gradDao.getJDAllFias(houseGradId, connectionGrad);
            if (mapJd != null) {
                for (Map.Entry<String, HouseRecord> entry : mapJd.entrySet()) {
                    getHouseData(entry.getValue(), gradDao, connectionGrad);
                }
            }
        }
        if (getErrorStatus() == 1) {
            answerProcessing.sendOkMessageToClient("");
            answerProcessing.sendOkMessageToClient("Сведения о МКД успешно получены!");
        } else if (getErrorStatus() == 0) {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendErrorToClientNotException("Возникли ошибки, сведения о МКД получены с ошибками!");
        } else if (getErrorStatus() == -1) {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendErrorToClientNotException("Возникли ошибки, сведения о МКД не получены!");
        }
    }

    /**
     * Метод, создаёт запрос в ГИС ЖКХ по ФИАС дому, получает его данные, заносит в БД идентификаторы:
     * дома, помещении, комнаты.
     *
     * @param fias    код дома по ФИАС.
     * @param houseId ИД дома из БД ГРАД.
     * @param gradDao класс для работы с БД ГРАДа.
     * @throws SQLException возможны ошибки при работе с БД.
     */
    private void getHouseData(final String fias, final Integer houseId, final HouseGRADDAO gradDao,
                              final Connection connectionGrad) throws SQLException, ParseException {

        try {
            // делаем запрос в ГИС
//            final GetStateResult result = callExportHouseData(fias);
            final GetStateResult result = HomeManagementAsyncPort.callExportHouseData(fias, answerProcessing);

            if (result != null && result.getErrorMessage() == null) { // Если нет ошибок

                answerProcessing.sendMessageToClient("Уникальный номер дома: " + result.getExportHouseResult().getHouseUniqueNumber());
                gradDao.setHouseUniqueNumber(houseId, result.getExportHouseResult().getHouseUniqueNumber(), connectionGrad);

                if (result.getExportHouseResult().getApartmentHouse() != null) {
                    answerProcessing.sendMessageToClient("Многоквартирный дом");

                    List<ExportHouseResultType.ApartmentHouse.ResidentialPremises> residentialPremises;

                    residentialPremises = result.getExportHouseResult().getApartmentHouse().getResidentialPremises();
                    if (residentialPremises != null) {

                        for (ExportHouseResultType.ApartmentHouse.ResidentialPremises residentialPremise : residentialPremises) {  // Пробегаем по помещениям

                            if (residentialPremise.getLivingRoom().size() > 0) { // Если есть комнаты, то пробегаем по ним
                                final List<ExportHouseResultType.ApartmentHouse.ResidentialPremises.LivingRoom> livingRooms = residentialPremise.getLivingRoom();
                                for (ExportHouseResultType.ApartmentHouse.ResidentialPremises.LivingRoom livingRoom : livingRooms) {

                                    // Добавляем в БД уникальный номер комнаты абонента
                                    gradDao.setApartmentUniqueNumber(houseId, residentialPremise.getPremisesNum(), livingRoom.getRoomNumber(), true,
                                            residentialPremise.getPremisesGUID(), residentialPremise.getPremisesUniqueNumber(),
                                            livingRoom.getLivingRoomGUID(), livingRoom.getLivingRoomUniqueNumber(), connectionGrad);
                                }
                            } else { // Если нет комнат передаем квартиру
                                    // Добавляем в БД уникальный номер помещения.
                                gradDao.setApartmentUniqueNumber(houseId, residentialPremise.getPremisesNum(), null, true,
                                        residentialPremise.getPremisesGUID(), residentialPremise.getPremisesUniqueNumber(), null, null, connectionGrad);
                            }
                        }
                    }

                    if (result.getExportHouseResult().getApartmentHouse().getNonResidentialPremises().size() > 0) {  // Нежилые помещения
                        final List<ExportHouseResultType.ApartmentHouse.NonResidentialPremises> nonResidentialPremises =
                                result.getExportHouseResult().getApartmentHouse().getNonResidentialPremises();
                        for (ExportHouseResultType.ApartmentHouse.NonResidentialPremises nonResidentialPremise : nonResidentialPremises) {

                            //                            Добавляем в БД уникальный номер помещения.
                            gradDao.setApartmentUniqueNumber(houseId, nonResidentialPremise.getPremisesNum(), null, false,
                                    nonResidentialPremise.getPremisesGUID(), nonResidentialPremise.getPremisesUniqueNumber(), null, null, connectionGrad);

                        }
                    }

                } else if (result.getExportHouseResult().getLivingHouse() != null) {  // Узнать что делалать с ними
                    answerProcessing.sendMessageToClient("Жилой дом");

                    for (ExportHouseResultType.LivingHouse.LivingRoom room : result.getExportHouseResult().getLivingHouse().getLivingRoom()) {
//                            Добавляем в БД уникальный номер помещения.
                        gradDao.setApartmentUniqueNumber(houseId, room.getRoomNumber(), null, true,
                                room.getLivingRoomGUID(), room.getLivingRoomUniqueNumber(), null, null, connectionGrad);
                    }
                }
            } else {
                setErrorStatus(0);
            }
        } catch (PreGISException e) {
            setErrorStatus(-1);
            answerProcessing.sendErrorToClient("getHouseData(): ", "\"Получение данных о МКД\" ", LOGGER, e);
        }
    }

    /**
     * Метод, подготавливает данные и перенаправляет их в метод getHouseData().
     *
     * @param houseRecord    содержит данные о доме.
     * @param gradDao        работа с БД ГРАД.
     * @param connectionGrad подключение к БД ГРАД
     * @throws SQLException  возможны ошибки при работе с БД.
     */
    private void getHouseData(final HouseRecord houseRecord,
                              final HouseGRADDAO gradDao,
                              final Connection connectionGrad) throws SQLException, ParseException {

        answerProcessing.clearLabelForText();
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Запрос по ФИАС: " + houseRecord.getFias());
        answerProcessing.sendMessageToClient("Адрес: " + houseRecord.getAddresString());
        getHouseData(houseRecord.getFias(), houseRecord.getGrad_id(), gradDao, connectionGrad);
    }

    /**
     * Метод, возвращает код ошибки.
     *
     * @return код ошибки (от -1 до 1).
     */
    private int getErrorStatus() {
        return errorStatus;
    }

    /**
     * Метод, принимает код ошибки, если код по приоритету ниже, то статус получает этот код,
     * иначе код ошибки остаётся прежним.
     *
     * @param errorNumber код ошибки.
     */
    private void setErrorStatus(final int errorNumber) {
        if (errorNumber < errorStatus) {
            errorStatus = errorNumber;
        }
    }

}
