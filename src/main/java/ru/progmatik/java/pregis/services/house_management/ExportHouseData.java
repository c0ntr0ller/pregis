package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.house_management.ExportHouseRequest;
import ru.gosuslugi.dom.schema.integration.services.house_management.ExportHouseResult;
import ru.gosuslugi.dom.schema.integration.services.house_management.ExportHouseResultType;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.Fault;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.HouseManagementPortsType;
import ru.gosuslugi.dom.schema.integration.services.house_management_service.HouseManagementService;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;

import javax.xml.ws.Holder;
import java.sql.SQLException;
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

    private final HouseManagementService service = new HouseManagementService();
    private final HouseManagementPortsType port = service.getHouseManagementPort();
    private final AnswerProcessing answerProcessing;

    /**
     * Конструктор, получает в параметр сылку на веб-сокет.
     */
    public ExportHouseData(AnswerProcessing answerProcessing) {
        OtherFormat.setPortSettings(service, port);
        this.answerProcessing = answerProcessing;
    }

    /**
     * Метод, получает из БД ГРАД все дома содержащие ФИАС, создаёт по ним запрос в ГИС ЖКХ.
     *
     * @throws PreGISException ошибки обработанные приложением.
     * @throws SQLException    ошибки связанные с базой данных.
     */
    public void updateAllHouseData() throws PreGISException, SQLException {

        HouseGRADDAO gradDao = new HouseGRADDAO();
//        Обработка МКД
        LinkedHashMap<String, Integer> fiasMap = gradDao.getAllFIAS(); // Берем из процедуры все дома, которые содержат ФИАС
        if (fiasMap != null) {
            for (Map.Entry<String, Integer> entry : fiasMap.entrySet()) {
                answerProcessing.sendMessageToClient("Запрос по ФИАС: " + entry.getKey());
                getHouseData(entry.getKey(), entry.getValue(), gradDao);
            }
        }

//          Обработка ЖД.
        LinkedHashMap<String, Integer> mapJd = gradDao.getJDAllFias();
        if (mapJd != null) {
            for (Map.Entry<String, Integer> entry : mapJd.entrySet()) {
                answerProcessing.sendMessageToClient("Запрос по ФИАС: " + entry.getKey());
                getHouseData(entry.getKey(), entry.getValue(), gradDao);
            }
        }
    }

    /**
     * Метод, создаёт запрос в ГИС ЖКХ по ФИАС дому, получает его данные, заносит в БД идентификаторы:
     * дома, помещении, комнаты.
     *
     * @param fias    код дома по ФИАС.
     * @param houseId ИД дома из БД ГРАД.
     * @param gradDao класс для работы с БД ГРАДа.
     * @throws SQLException
     */
    private void getHouseData(String fias, Integer houseId, HouseGRADDAO gradDao) throws SQLException {

        ExportHouseResult result;

        try {
            result = callExportHouseData(fias);

            if ( result == null || result.getErrorMessage() == null) { // Если нет ошибок
                answerProcessing.sendMessageToClient("Уникальный номер дома: " + result.getExportHouseResult().getHouseUniqueNumber());
                gradDao.setHouseUniqueNumber(houseId, result.getExportHouseResult().getHouseUniqueNumber());

                if (result.getExportHouseResult().getApartmentHouse() != null) {
                    answerProcessing.sendMessageToClient("Многоквартирный дом");

                    List<ExportHouseResultType.ApartmentHouse.Entrance> entrances = result.getExportHouseResult().getApartmentHouse().getEntrance();
                    List<ExportHouseResultType.ApartmentHouse.Entrance.ResidentialPremises> residentialPremises;

                    answerProcessing.sendMessageToClient("Подъезд: ");
                    for (ExportHouseResultType.ApartmentHouse.Entrance entrance : entrances) {  // Пробегаем подъезды
//                    Заменить на БД

                        answerProcessing.sendMessageToClient("Идентификатор подъезда: " + entrance.getEntranceGUID());
                        answerProcessing.sendMessageToClient("Номер подъезда : " + entrance.getEntranceNum());
                        answerProcessing.sendMessageToClient("Этажность: " + entrance.getStoreysCount());

                        residentialPremises = entrance.getResidentialPremises();
                        if (residentialPremises != null) {

                            answerProcessing.sendMessageToClient("Помещение: ");
                            for (ExportHouseResultType.ApartmentHouse.Entrance.ResidentialPremises residentialPremise : residentialPremises) {  // Пробегаем по помещениям
                                answerProcessing.sendMessageToClient("  Номер помещения: " + residentialPremise.getPremisesNum());
//                            clientService.sendMessage("Этаж: " + residentialPremise.getFloor());
                                answerProcessing.sendMessageToClient("  Номер подъезда: " + residentialPremise.getEntranceNum());
                                answerProcessing.sendMessageToClient("  Уникальный номер помещения: " + residentialPremise.getPremisesUniqueNumber());
                                answerProcessing.sendMessageToClient("  Идентификатор помещения: " + residentialPremise.getPremisesGUID());
//                            clientService.sendMessage("КАДАСТР помещения: " + residentialPremise.getCadastralNumber());

                                if (residentialPremise.getLivingRoom().size() > 0) { // Если есть комнаты, то пробегаем по ним
                                    answerProcessing.sendMessageToClient("  Комната: ");
                                    List<ExportHouseResultType.ApartmentHouse.Entrance.ResidentialPremises.LivingRoom> livingRooms = residentialPremise.getLivingRoom();
                                    for (ExportHouseResultType.ApartmentHouse.Entrance.ResidentialPremises.LivingRoom livingRoom : livingRooms) {
                                        answerProcessing.sendMessageToClient("    Номер комнаты: " + livingRoom.getRoomNumber());
//                                    clientService.sendMessage("Этаж: " + livingRoom.getFloor());
//                                    clientService.sendMessage("КАДАСТР комнаты: " + livingRoom.getCadastralNumber());
                                        answerProcessing.sendMessageToClient("    Уникальный номер комнаты: " + livingRoom.getLivingRoomUniqueNumber());
                                        answerProcessing.sendMessageToClient("    Идентификатор комнаты: " + livingRoom.getLivingRoomGUID());

                                        // Добавляем в БД уникальный номер комнаты абонента
                                        gradDao.setApartmentUniqueNumber(houseId, residentialPremise.getPremisesNum(),
                                                livingRoom.getRoomNumber(), livingRoom.getLivingRoomUniqueNumber());
                                    }
                                } else { // Если нет комнат передаем квартиру
//                                    Добавляем в БД уникальный номер помещения.
                                    gradDao.setApartmentUniqueNumber(houseId, residentialPremise.getPremisesNum(),
                                            null, residentialPremise.getPremisesUniqueNumber());
                                }
                            }
                        }
                    }

                    if (result.getExportHouseResult().getApartmentHouse().getNonResidentialPremises().size() > 0) {  // Нежилые помещения
                        List<ExportHouseResultType.ApartmentHouse.NonResidentialPremises> nonResidentialPremises =
                                result.getExportHouseResult().getApartmentHouse().getNonResidentialPremises();
                        answerProcessing.sendMessageToClient("Нежилое помещение: ");
                        for (ExportHouseResultType.ApartmentHouse.NonResidentialPremises nonResidentialPremise : nonResidentialPremises) {
                            answerProcessing.sendMessageToClient("Номер помещения: " + nonResidentialPremise.getPremisesNum());
                            answerProcessing.sendMessageToClient("Уникальный номер помещения: " + nonResidentialPremise.getPremisesUniqueNumber());
                            answerProcessing.sendMessageToClient("Идентификатор помещения: " + nonResidentialPremise.getPremisesGUID());

//                            Добавляем в БД уникальный номер помещения.
                            gradDao.setApartmentUniqueNumber(houseId, nonResidentialPremise.getPremisesNum(),
                                    null, nonResidentialPremise.getPremisesUniqueNumber());

                        }
                    }

                } else if (result.getExportHouseResult().getLivingHouse() != null) {  // Узнать что делалать с ними
                    answerProcessing.sendMessageToClient("Жилой дом");
                    // TODO
//                    Комнаты пока не обрабатываются ЖД
//                    result.getExportHouseResult().getLivingHouse().getLivingRoom().get(0)

                }
                answerProcessing.sendOkMessageToClient("Сведенья о МКД успешно получены!");

            } else {
                answerProcessing.sendErrorToClientNotException("Возникли ошибки, сведенья о МКД не получены!");
            }

        } catch (PreGISException e) {
            answerProcessing.sendErrorToClient("getHouseData(): ", "\"Получение данных о МКД\" ", LOGGER, e);
        }

    }

    public ExportHouseResult callExportHouseData(String fias) {

        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD);
        Holder<ResultHeader> resultHolder = new Holder<>();

        RequestHeader headerRequest = OtherFormat.getRequestHeader();

        ExportHouseResult result = null;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            result = port.exportHouseData(getExportHouseRequest(fias), headerRequest, resultHolder);
            answerProcessing.sendMessageToClient(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);


            answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, headerRequest, resultHolder.value,
                    result.getErrorMessage(), LOGGER);
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, headerRequest, LOGGER, fault);
        }
        return result;
    }

    /**
     * Метод, формирует запрос.
     *
     * @param fias код дома по ФИАС.
     * @return готовый запрос в ГИС ЖКХ.
     */
    private ExportHouseRequest getExportHouseRequest(String fias) {

        ExportHouseRequest request = new ExportHouseRequest();
        request.setId(OtherFormat.getId());
        request.setFIASHouseGuid(fias);
        answerProcessing.sendMessageToClient("ФИАС дома: " + fias);

        return request;
    }

}
