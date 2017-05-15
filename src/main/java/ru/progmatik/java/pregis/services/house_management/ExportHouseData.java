package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.house_management.ExportHouseRequest;
import ru.gosuslugi.dom.schema.integration.house_management.ExportHouseResult;
import ru.gosuslugi.dom.schema.integration.house_management.ExportHouseResultType;
import ru.gosuslugi.dom.schema.integration.house_management_service.Fault;
import ru.gosuslugi.dom.schema.integration.house_management_service.HouseManagementPortsType;
import ru.gosuslugi.dom.schema.integration.house_management_service.HouseManagementService;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;
import ru.progmatik.java.pregis.other.UrlLoader;
import ru.progmatik.java.pregis.structures.HouseRecord;

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
public class ExportHouseData {

    private static final Logger LOGGER = Logger.getLogger(ExportHouseData.class);
    private static final String NAME_METHOD = "exportHouseData";

    private final HouseManagementPortsType port;
    private final AnswerProcessing answerProcessing;

    // Статус ошибок:
    //  1 - ошибок нет.
    //  0 - ошибки есть, но работа продолжена.
    // -1 - ошибка дальнейшая работа не возможна.
    private int errorStatus;

    /**
     * Конструктор, получает в параметр сылку на веб-сокет.
     */
    public ExportHouseData(final AnswerProcessing answerProcessing) {

        this.answerProcessing = answerProcessing;

        final HouseManagementService service = UrlLoader.instance().getUrlMap().get("homeManagement") == null ? new HouseManagementService()
                : new HouseManagementService(UrlLoader.instance().getUrlMap().get("homeManagement"));

        port = service.getHouseManagementPort();
        OtherFormat.setPortSettings(service, port);

        errorStatus = 1;
    }

    /**
     * Метод, получает из БД ГРАД все дома содержащие ФИАС, создаёт по ним запрос в ГИС ЖКХ.
     *
     * @throws PreGISException ошибки обработанные приложением.
     * @throws SQLException    ошибки связанные с базой данных.
     */
    public void updateAllHouseData() throws PreGISException, SQLException, ParseException {
        try (Connection connectionGrad = ConnectionBaseGRAD.instance().getConnection()) {
            final HouseGRADDAO gradDao = new HouseGRADDAO(answerProcessing);
//        Обработка МКД
            final LinkedHashMap<String, HouseRecord> mapMKD = gradDao.getAllHouseFIASAddress(connectionGrad); // Берем из процедуры все дома, которые содержат ФИАС

            if (mapMKD != null) {
                for (Map.Entry<String, HouseRecord> entry : mapMKD.entrySet()) {
                    getHouseData(entry, gradDao, connectionGrad);
                }
            }

//          Обработка ЖД.
            final LinkedHashMap<String, HouseRecord> mapJd = gradDao.getJDAllFias(connectionGrad);
            if (mapJd != null) {
                for (Map.Entry<String, HouseRecord> entry : mapJd.entrySet()) {
                    getHouseData(entry, gradDao, connectionGrad);
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

        final ExportHouseResult result;

        try {
            result = callExportHouseData(fias);

            if (result != null && result.getErrorMessage() == null) { // Если нет ошибок

                answerProcessing.sendMessageToClient("Уникальный номер дома: " + result.getExportHouseResult().getHouseUniqueNumber());
                gradDao.setHouseUniqueNumber(houseId, result.getExportHouseResult().getHouseUniqueNumber(), connectionGrad);

                if (result.getExportHouseResult().getApartmentHouse() != null) {
                    answerProcessing.sendMessageToClient("Многоквартирный дом");

// не нужны в 11                   final List<ExportHouseResultType.ApartmentHouse.Entrance> entrances = result.getExportHouseResult().getApartmentHouse().getEntrance();

                    List<ExportHouseResultType.ApartmentHouse.ResidentialPremises> residentialPremises;

/* не нужны в 11
                    answerProcessing.sendMessageToClient("Подъезд: ");
                    for (ExportHouseResultType.ApartmentHouse.Entrance entrance : entrances) {  // Пробегаем подъезды
                    Заменить на БД

                        answerProcessing.sendMessageToClient("");
                        answerProcessing.sendMessageToClient("Идентификатор подъезда: " + entrance.getEntranceGUID());
                        answerProcessing.sendMessageToClient("Номер подъезда : " + entrance.getEntranceNum());
                        answerProcessing.sendMessageToClient("Этажность: " + entrance.getStoreysCount());
*/
                    residentialPremises = result.getExportHouseResult().getApartmentHouse().getResidentialPremises();
                    if (residentialPremises != null) {

                        answerProcessing.sendMessageToClient("Помещение: ");
                        for (ExportHouseResultType.ApartmentHouse.ResidentialPremises residentialPremise : residentialPremises) {  // Пробегаем по помещениям
                            answerProcessing.sendMessageToClient("");
                            answerProcessing.sendMessageToClient("  Номер помещения: " + residentialPremise.getPremisesNum());
//                            clientService.sendMessage("Этаж: " + residentialPremise.getFloor());
                            answerProcessing.sendMessageToClient("  Номер подъезда: " + residentialPremise.getEntranceNum());
                            answerProcessing.sendMessageToClient("  Уникальный номер помещения: " + residentialPremise.getPremisesUniqueNumber());
                            answerProcessing.sendMessageToClient("  Идентификатор помещения: " + residentialPremise.getPremisesGUID());
//                            clientService.sendMessage("КАДАСТР помещения: " + residentialPremise.getCadastralNumber());

                            if (residentialPremise.getLivingRoom().size() > 0) { // Если есть комнаты, то пробегаем по ним
                                answerProcessing.sendMessageToClient("  Комната: ");
                                final List<ExportHouseResultType.ApartmentHouse.ResidentialPremises.LivingRoom> livingRooms = residentialPremise.getLivingRoom();
                                for (ExportHouseResultType.ApartmentHouse.ResidentialPremises.LivingRoom livingRoom : livingRooms) {
                                    answerProcessing.sendMessageToClient("    Номер комнаты: " + livingRoom.getRoomNumber());
//                                    clientService.sendMessage("Этаж: " + livingRoom.getFloor());
//                                    clientService.sendMessage("КАДАСТР комнаты: " + livingRoom.getCadastralNumber());
                                    answerProcessing.sendMessageToClient("    Уникальный номер комнаты: " + livingRoom.getLivingRoomUniqueNumber());
                                    answerProcessing.sendMessageToClient("    Идентификатор комнаты: " + livingRoom.getLivingRoomGUID());

                                    // Добавляем в БД уникальный номер комнаты абонента
                                    gradDao.setApartmentUniqueNumber(houseId, residentialPremise.getPremisesNum(), livingRoom.getRoomNumber(),
                                            residentialPremise.getPremisesGUID(), residentialPremise.getPremisesUniqueNumber(),
                                            livingRoom.getLivingRoomGUID(), livingRoom.getLivingRoomUniqueNumber(), connectionGrad);
                                }
                            } else { // Если нет комнат передаем квартиру
//                                    Добавляем в БД уникальный номер помещения.
                                gradDao.setApartmentUniqueNumber(houseId, residentialPremise.getPremisesNum(), null,
                                        residentialPremise.getPremisesGUID(), residentialPremise.getPremisesUniqueNumber(), null, null, connectionGrad);
                            }
                        }
                    }
//                    }

                    if (result.getExportHouseResult().getApartmentHouse().getNonResidentialPremises().size() > 0) {  // Нежилые помещения
                        final List<ExportHouseResultType.ApartmentHouse.NonResidentialPremises> nonResidentialPremises =
                                result.getExportHouseResult().getApartmentHouse().getNonResidentialPremises();
                        answerProcessing.sendMessageToClient("Нежилое помещение: ");
                        for (ExportHouseResultType.ApartmentHouse.NonResidentialPremises nonResidentialPremise : nonResidentialPremises) {
                            answerProcessing.sendMessageToClient("Номер помещения: " + nonResidentialPremise.getPremisesNum());
                            answerProcessing.sendMessageToClient("Уникальный номер помещения: " + nonResidentialPremise.getPremisesUniqueNumber());
                            answerProcessing.sendMessageToClient("Идентификатор помещения: " + nonResidentialPremise.getPremisesGUID());

//                            Добавляем в БД уникальный номер помещения.
                            gradDao.setApartmentUniqueNumber(houseId, nonResidentialPremise.getPremisesNum(), null,
                                    nonResidentialPremise.getPremisesGUID(), nonResidentialPremise.getPremisesUniqueNumber(), null, null, connectionGrad);

                        }
                    }

                } else if (result.getExportHouseResult().getLivingHouse() != null) {  // Узнать что делалать с ними
                    answerProcessing.sendMessageToClient("Жилой дом");

                    answerProcessing.sendMessageToClient("");
                    answerProcessing.sendMessageToClient("Актуальная версия сведений о доме: " +
                            result.getExportHouseResult().getLivingHouse().getHouseGUID());

                    for (ExportHouseResultType.LivingHouse.LivingRoom room : result.getExportHouseResult().getLivingHouse().getLivingRoom()) {
//                            Добавляем в БД уникальный номер помещения.
                        gradDao.setApartmentUniqueNumber(houseId, room.getRoomNumber(), null,
                                room.getLivingRoomGUID(), room.getLivingRoomUniqueNumber(), null, null, connectionGrad);

                        answerProcessing.sendMessageToClient("");
                        answerProcessing.sendMessageToClient("Номер помещения: " + room.getRoomNumber());
                        answerProcessing.sendMessageToClient("Уникальный номер помещения: " + room.getLivingRoomUniqueNumber());
                        answerProcessing.sendMessageToClient("Идентификатор помещения: " + room.getLivingRoomGUID());
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

    private ExportHouseResult callExportHouseData(final String fias) throws SQLException {

        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
        final Holder<ResultHeader> resultHolder = new Holder<>();

        final RequestHeader headerRequest = OtherFormat.getRequestHeader();

        ExportHouseResult result = null;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            result = port.exportHouseData(getExportHouseRequest(fias), headerRequest, resultHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);


            answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, headerRequest, resultHolder.value,
                    result.getErrorMessage(), LOGGER);
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, headerRequest, LOGGER, fault);
            setErrorStatus(-1);
        }
        return result;
    }

    /**
     * Метод, подготавливает данные и перенаправляет их в метод getHouseData().
     *
     * @param entry          содержит данные о доме.
     * @param gradDao        работа с БД ГРАД.
     * @param connectionGrad подключение к БД ГРАД
     * @throws SQLException  возможны ошибки при работе с БД.
     */
    private void getHouseData(final Map.Entry<String, HouseRecord> entry,
                              final HouseGRADDAO gradDao,
                              final Connection connectionGrad) throws SQLException, ParseException {

        answerProcessing.clearLabelForText();
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Запрос по ФИАС: " + entry.getKey());
        answerProcessing.sendMessageToClient("Адрес: " + entry.getValue().getAddresString());
        getHouseData(entry.getKey(), entry.getValue().getGrad_id(), gradDao, connectionGrad);
    }

    /**
     * Метод, формирует запрос.
     *
     * @param fias код дома по ФИАС.
     * @return готовый запрос в ГИС ЖКХ.
     */
    private ExportHouseRequest getExportHouseRequest(final String fias) {

        final ExportHouseRequest request = new ExportHouseRequest();
        request.setId(OtherFormat.getId());
        request.setFIASHouseGuid(fias);
        request.setVersion(request.getVersion());
        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("ФИАС дома: " + fias);

        return request;
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
