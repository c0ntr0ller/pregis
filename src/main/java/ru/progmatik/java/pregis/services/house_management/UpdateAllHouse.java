package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.web.servlets.socket.ClientService;

/**
 * Класс, получает ФИАСы всех домов из ГРАДа, формирует запрос в ГИС ЖКХ, полученый результат заносит в БД.
 */
public class UpdateAllHouse {

    private static final Logger LOGGER = Logger.getLogger(UpdateAllHouse.class);

    private final ClientService clientService;
    private final AnswerProcessing answerProcessing;

    public UpdateAllHouse(ClientService clientService, AnswerProcessing answerProcessing) {
        this.clientService = clientService;
        this.answerProcessing = answerProcessing;
    }

    public void updateAllHouse() {

    }
}
