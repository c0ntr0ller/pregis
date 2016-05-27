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
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.TextForLog;
import ru.progmatik.java.web.servlets.socket.ClientService;

import javax.xml.ws.Holder;
import java.util.List;

/**
 * Класс, экспорт данных домов.
 * Получаем данные из системы ГИС ЖКХ.
 */
public class ExportHouseData {

    private static final Logger LOGGER = Logger.getLogger(ExportHouseData.class);
    private static final String NAME_METHOD = "exportHouseData";

    private final HouseManagementService service = new HouseManagementService();
    private final HouseManagementPortsType port = service.getHouseManagementPort();
    private final ClientService clientService;
    private final AnswerProcessing answerProcessing;

    /**
     * Конструктор, получает в параметр сылку на веб-сокет.
     *
     * @param clientService websocket для отправки сообщений пользователю.
     */
    public ExportHouseData(ClientService clientService, AnswerProcessing answerProcessing) {
        OtherFormat.setPortSettings(service, port);
        this.clientService = clientService;
        this.answerProcessing = answerProcessing;
    }

    public void callExportHouseData() {

        clientService.sendMessage(TextForLog.FORMED_REQUEST + NAME_METHOD);
        Holder<ResultHeader> resultHolder = new Holder<>();

        RequestHeader headerRequest = OtherFormat.getRequestHeader();

        ExportHouseResult result;

        try {
            clientService.sendMessage(TextForLog.SENDING_REQUEST);
            result = port.exportHouseData(getExportHouseRequest("f20a2f00-c9cf-485f-ac41-92af5b77e29a"), headerRequest, resultHolder);
            clientService.sendMessage(TextForLog.RECEIVED_RESPONSE + NAME_METHOD);
        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD, headerRequest, clientService, LOGGER, fault);
            return;
        }

        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD, headerRequest, resultHolder.value,
                result.getErrorMessage(), clientService, LOGGER);

        if (result.getErrorMessage() == null) { // Если нет ошибок
            clientService.sendMessage("Уникальный номер дома: " + result.getExportHouseResult().getHouseUniqueNumber());
            if (result.getExportHouseResult().getApartmentHouse() != null) {
                clientService.sendMessage("Многоквартирный дом");
//                        TODO
                List<ExportHouseResultType.ApartmentHouse.Entrance> entrances = result.getExportHouseResult().getApartmentHouse().getEntrance();
                List<ExportHouseResultType.ApartmentHouse.Entrance.ResidentialPremises> residentialPremises;

                clientService.sendMessage("Подъезд: ");
                for (ExportHouseResultType.ApartmentHouse.Entrance entrance : entrances) {  // Пробегаем подъезды
//                    Заменить на БД

                    clientService.sendMessage("Идентификатор подъезда: " + entrance.getEntranceGUID());
                    clientService.sendMessage("Номер подъезда : " + entrance.getEntranceNum());
                    clientService.sendMessage("Этажность: " + entrance.getStoreysCount());

                    residentialPremises = entrance.getResidentialPremises();
                    if (residentialPremises != null) {

                        clientService.sendMessage("Помещение: ");
                        for (ExportHouseResultType.ApartmentHouse.Entrance.ResidentialPremises residentialPremise : residentialPremises) {  // Пробегаем по помещениям
                            clientService.sendMessage("  Номер помещения: " + residentialPremise.getPremisesNum());
//                            clientService.sendMessage("Этаж: " + residentialPremise.getFloor());
                            clientService.sendMessage("  Номер подъезда: " + residentialPremise.getEntranceNum());
                            clientService.sendMessage("  Уникальный номер помещения: " + residentialPremise.getPremisesUniqueNumber());
                            clientService.sendMessage("  Идентификатор помещения: " + residentialPremise.getPremisesGUID());
//                            clientService.sendMessage("КАДАСТР помещения: " + residentialPremise.getCadastralNumber());

                            if (residentialPremise.getLivingRoom().size() > 0) { // Если есть комнаты, то пробегаем по ним
                                clientService.sendMessage("  Комната: ");
                                List<ExportHouseResultType.ApartmentHouse.Entrance.ResidentialPremises.LivingRoom> livingRooms = residentialPremise.getLivingRoom();
                                for (ExportHouseResultType.ApartmentHouse.Entrance.ResidentialPremises.LivingRoom livingRoom : livingRooms) {
                                    clientService.sendMessage("    Номер комнаты: " + livingRoom.getRoomNumber());
//                                    clientService.sendMessage("Этаж: " + livingRoom.getFloor());
//                                    clientService.sendMessage("КАДАСТР комнаты: " + livingRoom.getCadastralNumber());
                                    clientService.sendMessage("    Уникальный номер комнаты: " + livingRoom.getLivingRoomUniqueNumber());
                                    clientService.sendMessage("    Идентификатор комнаты: " + livingRoom.getLivingRoomGUID());
                                }
                            }
                        }
                    }
                }

                if (result.getExportHouseResult().getApartmentHouse().getNonResidentialPremises().size() > 0) {  // Нежилые помещения
                    List<ExportHouseResultType.ApartmentHouse.NonResidentialPremises> nonResidentialPremises =
                            result.getExportHouseResult().getApartmentHouse().getNonResidentialPremises();
                    clientService.sendMessage("Нежилое помещение: ");
                    for (ExportHouseResultType.ApartmentHouse.NonResidentialPremises nonResidentialPremise : nonResidentialPremises) {
                        clientService.sendMessage("Номер помещения: " + nonResidentialPremise.getPremisesNum());
                        clientService.sendMessage("Уникальный номер помещения: " + nonResidentialPremise.getPremisesUniqueNumber());
                        clientService.sendMessage("Идентификатор помещения: " + nonResidentialPremise.getPremisesGUID());
                    }


                }

            } else if (result.getExportHouseResult().getLivingHouse() != null) {
                clientService.sendMessage("Жилой дом");
            }
            clientService.sendMessage("Сведенья о МКД успешно получены!");
        } else {
            clientService.sendMessage("Возникли ошибки, сведенья о МКД не получен!");
        }
    }

    private ExportHouseRequest getExportHouseRequest(String fias) {

        ExportHouseRequest request = new ExportHouseRequest();
        request.setId(OtherFormat.getId());
        request.setFIASHouseGuid(fias);
        clientService.sendMessage("ФИАС дома: " + fias);

        return request;
    }

}
