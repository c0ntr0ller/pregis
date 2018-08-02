package ru.progmatik.java.pregis.connectiondb.grad.devices;

import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.web.servlets.socket.ClientService;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Класс, в этом пакете содержаться вспомогательные классы, для поиска и обработки данных разработчика.
 * Данный класс содержит разные утилиты обработки приборов учета для разработчика.
 */
public class MeteringDevicesDBSearch {

    /**
     * Метод, получает на вход перечень номеров приборов учета,
     * обращается в БД ГРАДа и по ним находит идентификаторы абонентов.
     * @param houseId идентификатор дома в БД ГРАД.
     * @param meteringDeviceNumbers список номеров ПУ для поиска
     * @param connectionGrad подключение к БД ГРАД
     */
    public void getMeteringDevicesAbonIdByMeteringDeviceNumber(Integer houseId,
                                                               HashSet<String> meteringDeviceNumbers,
                                                               Connection connectionGrad) {

        try {
            MeteringDeviceGRADDAO graddao = new MeteringDeviceGRADDAO(new AnswerProcessing(new ClientService()), houseId);
            ArrayList<String[]> exGisPu1 = graddao.getExGisPu1(houseId, connectionGrad);

            System.out.println("Количество полученных ПУ: " + exGisPu1.size() + "\n");

            for (String deviceNumber : meteringDeviceNumbers) {
                for (String[] pu : exGisPu1) {
                    if (deviceNumber.equalsIgnoreCase(pu[1])) {
                        System.out.printf("%1$s%2$-20s%3$s%4$-10s\n", "MeteringDeviceNumber: ", deviceNumber, "AbonId: ", pu[27]);
                    }
                }
            }

        } catch (SQLException | PreGISException | ParseException e) {
            e.printStackTrace();
        }
    }
}
