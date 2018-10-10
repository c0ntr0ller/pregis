package ru.progmatik.java.pregis.services.house_management;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.BaseType;
import ru.gosuslugi.dom.schema.integration.house_management.*;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.house.HouseGRADDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSI;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.model.HouseRecord;
import ru.progmatik.java.pregis.model.Room;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс, экспорт данных домов.
 * Получает ФИАСы всех домов из ГРАДа, формирует запрос в ГИС ЖКХ, полученый результат заносит в БД.
 * Получаем данные из системы ГИС ЖКХ.
 */
public class UpdateAllHouseData {

    private static final Logger LOGGER = Logger.getLogger(UpdateAllHouseData.class);
    private final AnswerProcessing answerProcessing;

    // Статус ошибок:
    //  0 - ошибок нет.
    //  1 - ошибки есть, но работа частично проведена
    //  2 - ошибка дальнейшая работа не возможна.
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

        errorStatus = 0;
    }

    /**
     * Метод, получает из БД ГРАД все дома содержащие ФИАС, создаёт по ним запрос в ГИС ЖКХ.
     *
     * @throws PreGISException ошибки обработанные приложением.
     * @throws SQLException    ошибки связанные с базой данных.
     */
    public void callUpdateAllHouseData(final Integer houseGradId) throws PreGISException, SQLException {
        try (Connection connectionGrad = ConnectionBaseGRAD.instance().getConnection()) {
            final HouseGRADDAO gradDao = new HouseGRADDAO(answerProcessing);
//        Обработка МКД
            final LinkedHashMap<String, HouseRecord> mapHouses = gradDao.getHouseRecords(houseGradId, connectionGrad); // Берем из процедуры все дома, которые содержат ФИАС

            if (mapHouses != null) {
                for (Map.Entry<String, HouseRecord> entry : mapHouses.entrySet()) {
//                    getHouseData(entry.getValue(), gradDao, connectionGrad);
                    syncHouse(entry.getValue(), gradDao, connectionGrad);
                }
            }
//
////          Обработка ЖД.
//            final LinkedHashMap<String, HouseRecord> mapJd = gradDao.getAllJdHouseRecords(houseGradId, connectionGrad);
//            if (mapJd != null) {
//                for (Map.Entry<String, HouseRecord> entry : mapJd.entrySet()) {
//                    getHouseData(entry.getValue(), gradDao, connectionGrad);
//                }
//            }
        }
        if (getErrorStatus() == 0) {
            answerProcessing.sendOkMessageToClient("");
            answerProcessing.sendOkMessageToClient("Сведения о доме успешно получены!");
        } else if (getErrorStatus() == 1) {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendErrorToClientNotException("Возникли ошибки, данные обработаны не полностью!");
        } else if (getErrorStatus() == -1) {
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendErrorToClientNotException("Возникли ошибки, синхронизация отменяется!");
        }
    }

    /**
     * Метод синхронизирует информацию по дому между Град и ГИС
     * @param houseRecord - дом
     * @param gradDao - объект ДАО для работы с БД
     * @param connectionGrad - соединение с БД
     */
    private void syncHouse(final HouseRecord houseRecord, final HouseGRADDAO gradDao, final Connection connectionGrad) throws SQLException, PreGISException {

        answerProcessing.sendMessageToClient("Запрос по ФИАС: " + houseRecord.getFias());
        answerProcessing.sendMessageToClient("Адрес: " + houseRecord.getAddresString());

        // получаем данные из ГИС
        final GetStateResult result = HomeManagementAsyncPort.callExportHouseData(houseRecord.getFias(), answerProcessing);

        // если запрос в ГИС прошел с ошибками (но не считая ошибки "нет данных") - выходим
        if (result == null || result.getErrorMessage() != null || result.getExportHouseResult() == null) { // Если нет ошибок
            setErrorStatus(0);
            return;
        }

        // выставляем GUID дома
        answerProcessing.sendMessageToClient("Уникальный номер дома: " + result.getExportHouseResult().getHouseUniqueNumber());
        gradDao.setHouseUniqueNumber(houseRecord.getGrad_id(), result.getExportHouseResult().getHouseUniqueNumber(), connectionGrad);


        ArrayList<Room> roomsGrad = gradDao.getRooms(houseRecord.getGrad_id(), connectionGrad);
        // получаем помещения из Град
        if (roomsGrad != null && roomsGrad.isEmpty()) {
            answerProcessing.sendMessageToClient("Помещения из Град не получены");
            setErrorStatus(2);
            return;
        }

        // подготовка структур для хранения результатов сравнения
        // массив для хранения помещений/комнат/нежилых для создания в ГИС
        List<Room> roomsForCreateGIS = new ArrayList<>();
        // мапа для хранения жилых помещения/комнат для обновления в Град
        Map<Room, AbstractMap.SimpleEntry<ExportHouseResultType.ApartmentHouse.ResidentialPremises,
                ExportHouseResultType.ApartmentHouse.ResidentialPremises.LivingRoom>> residentialMapForUpdate = new HashMap<>();
        // мапа для хранения нежилых помещений для обновления в Град
        Map<Room, ExportHouseResultType.ApartmentHouse.NonResidentialPremises> nonResidentialMapForUpdate = new HashMap<>();
        // мапа для хранения помещений частных домов для обновления в Град
        Map<Room, ExportHouseResultType.LivingHouse.LivingRoom> livingHouseRoomsMapForUpdate = new HashMap<>();
        if (roomsGrad != null) {
            roomsForCreateGIS.addAll(roomsGrad);
        }
        // сравниваем данные
        comparePremises(houseRecord, result.getExportHouseResult(), roomsForCreateGIS, residentialMapForUpdate, nonResidentialMapForUpdate, livingHouseRoomsMapForUpdate);

        if(!roomsForCreateGIS.isEmpty()){
            answerProcessing.sendMessageToClient(String.format("Помещений для создания в ГИС: %d", roomsForCreateGIS.size()));
        }

        if(!residentialMapForUpdate.isEmpty() || !nonResidentialMapForUpdate.isEmpty()) {
            answerProcessing.sendMessageToClient(String.format("Помещений для обновления в Град: %d", residentialMapForUpdate.size() + nonResidentialMapForUpdate.size()));
        }

        if(!livingHouseRoomsMapForUpdate.isEmpty()) {
            answerProcessing.sendMessageToClient(String.format("Помещений в частном доме для обновления в Град: %d", livingHouseRoomsMapForUpdate.size()));
        }

        if(result.getExportHouseResult().getApartmentHouse() != null) {

            if(result.getExportHouseResult().getApartmentHouse().getResidentialPremises() != null) {
                int roomsForUpdateCount = result.getExportHouseResult().getApartmentHouse().getResidentialPremises().size();

                if (roomsForUpdateCount > 0) {
                    answerProcessing.sendMessageToClient(String.format("Жилых помещений для удаления в Гис: %d", roomsForUpdateCount));
                }
            }

            if(result.getExportHouseResult().getApartmentHouse().getNonResidentialPremises() != null) {
                int roomsForUpdateCount = result.getExportHouseResult().getApartmentHouse().getNonResidentialPremises().size();

                if (roomsForUpdateCount > 0) {
                    answerProcessing.sendMessageToClient(String.format("Нежилых помещений для удаления в Гис: %d", roomsForUpdateCount));
                }
            }
        }

        if(result.getExportHouseResult().getLivingHouse() != null) {
            int roomsForUpdateCount = 0;
            if(result.getExportHouseResult().getLivingHouse().getLivingRoom() != null)
                roomsForUpdateCount += result.getExportHouseResult().getLivingHouse().getLivingRoom().size();

            if(roomsForUpdateCount > 0){
                answerProcessing.sendMessageToClient(String.format("Помещений для удаления в Гис: %d", roomsForUpdateCount));
            }
        }

        // добавляем недостающие идентификаторы помещения в Град
        // жилые помещения
        for (Map.Entry<Room, AbstractMap.SimpleEntry<ExportHouseResultType.ApartmentHouse.ResidentialPremises,
                ExportHouseResultType.ApartmentHouse.ResidentialPremises.LivingRoom>> roomResidentialPremisesEntry : residentialMapForUpdate.entrySet()) {
            if (roomResidentialPremisesEntry.getValue().getValue() != null) {
                for (Integer abonId : roomResidentialPremisesEntry.getKey().getAbonId()) {
                    gradDao.setApartmentUniqueNumber(roomResidentialPremisesEntry.getValue().getKey().getPremisesGUID(),
                            roomResidentialPremisesEntry.getValue().getKey().getPremisesUniqueNumber(),
                            roomResidentialPremisesEntry.getValue().getValue().getLivingRoomGUID(),
                            roomResidentialPremisesEntry.getValue().getValue().getLivingRoomUniqueNumber(),
                            connectionGrad,
                            abonId);
                }
            } else { // Если нет комнат передаем квартиру
                // Добавляем в БД уникальный номер помещения.
                for (Integer abonId : roomResidentialPremisesEntry.getKey().getAbonId()) {
                    gradDao.setApartmentUniqueNumber(roomResidentialPremisesEntry.getValue().getKey().getPremisesGUID(),
                            roomResidentialPremisesEntry.getValue().getKey().getPremisesUniqueNumber(),
                            null,
                            null,
                            connectionGrad,
                            abonId);
                }
            }
        }

        // нежилые помещения
        for (Map.Entry<Room, ExportHouseResultType.ApartmentHouse.NonResidentialPremises> roomNonResidentialPremisesEntry : nonResidentialMapForUpdate.entrySet()) {
            for (Integer abonId : roomNonResidentialPremisesEntry.getKey().getAbonId()) {
                gradDao.setApartmentUniqueNumber(roomNonResidentialPremisesEntry.getValue().getPremisesGUID(),
                        roomNonResidentialPremisesEntry.getValue().getPremisesUniqueNumber(),
                        null,
                        null,
                        connectionGrad,
                        abonId);
            }
        }

        // помещения частного дома
        for (Map.Entry<Room, ExportHouseResultType.LivingHouse.LivingRoom> roomLivingRoomEntry : livingHouseRoomsMapForUpdate.entrySet()) {
            for (Integer abonId : roomLivingRoomEntry.getKey().getAbonId()) {
                gradDao.setApartmentUniqueNumber(roomLivingRoomEntry.getValue().getLivingRoomGUID(),
                        roomLivingRoomEntry.getValue().getLivingRoomUniqueNumber(),
                        null,
                        null,
                        connectionGrad,
                        abonId);
            }
        }

//        result.getExportHouseResult().getApartmentHouse().getBasicCharacteristicts().getCadastralNumber()
        // создаем недостающие помещения в ГИС
        if(!roomsForCreateGIS.isEmpty()){
            // многоквартирный дом
            if(result.getExportHouseResult().getApartmentHouse() != null){
                sendPremisesToGis(roomsForCreateGIS,
                        houseRecord,
                        result.getExportHouseResult().getApartmentHouse().getBasicCharacteristicts().getCadastralNumber(),
                        gradDao,
                        connectionGrad
                );
            }
        }

        answerProcessing.sendMessageToClient("Синхронизация данных по дому : " + houseRecord.getAddresString() + " завершена");
    }

    /**
     * Метод сравнивает помещения Град и ГИС
     * @param houseRecord - в каком доме ищем
     * @param exportHouseResultType - данные о доме, полученные из ГИС. В процессе сравнения всё что найдено - удалется, остаток подлежит удалению в ГИС
     * @param roomsForCreateGIS - помещения, которые надо создать в ГИС. Передается заполненным данными из Град, в процессе сравнения данные удаляются. Всё что осталось - отсуствует и подлежит созданию в ГИС
     * @param residentialMapForUpdate -- жилые помещения/комнаты, которые нужно обновить в Град (выставить GUID) (передается пустым)
     * @param nonResidentialMapForUpdate - нежилые --||--
     * @param livingHouseRoomsMapForUpdate - помещения жилого частного дома --||--
     */
    private void comparePremises(final HouseRecord houseRecord,
                                 final ExportHouseResultType exportHouseResultType,
                                 final List<Room> roomsForCreateGIS,
                                 final Map<Room, AbstractMap.SimpleEntry<ExportHouseResultType.ApartmentHouse.ResidentialPremises,
                                         ExportHouseResultType.ApartmentHouse.ResidentialPremises.LivingRoom>> residentialMapForUpdate, // жилые помещения/комнаты
                                 final Map<Room, ExportHouseResultType.ApartmentHouse.NonResidentialPremises> nonResidentialMapForUpdate, // нежилые помещения
                                 final Map<Room, ExportHouseResultType.LivingHouse.LivingRoom> livingHouseRoomsMapForUpdate // помещения частного дома
    ) {
        // сравниваем жилые помещения
        // бежим по помещениям в ГИС и ищем их в Град.
        if (exportHouseResultType.getApartmentHouse() != null) {

            // если есть жилые помещения
            if (exportHouseResultType.getApartmentHouse().getResidentialPremises() != null) {
                // бежим по жилым помещениям
                for (Iterator<ExportHouseResultType.ApartmentHouse.ResidentialPremises> residentialPremisesIterator = exportHouseResultType.getApartmentHouse().getResidentialPremises().iterator(); residentialPremisesIterator.hasNext(); ) {
                    ExportHouseResultType.ApartmentHouse.ResidentialPremises residentialPremise = residentialPremisesIterator.next();

                    // Если есть комнаты, то пробегаем по ним
                    if (residentialPremise.getLivingRoom() != null && !residentialPremise.getLivingRoom().isEmpty()) {

                        for (Iterator<ExportHouseResultType.ApartmentHouse.ResidentialPremises.LivingRoom> livingRoomIterator = residentialPremise.getLivingRoom().iterator(); livingRoomIterator.hasNext(); ) {
                            ExportHouseResultType.ApartmentHouse.ResidentialPremises.LivingRoom livingRoom = livingRoomIterator.next();
                            // ищем комнату Град по адресу
                            Room foundRoom = findGradRoom(houseRecord, roomsForCreateGIS, residentialPremise.getPremisesNum(), livingRoom.getRoomNumber(), true, residentialPremise.getPremisesGUID());
                            // это вместо Pair
                            AbstractMap.SimpleEntry<ExportHouseResultType.ApartmentHouse.ResidentialPremises,
                                    ExportHouseResultType.ApartmentHouse.ResidentialPremises.LivingRoom> simpleEntry = new AbstractMap.SimpleEntry<>(residentialPremise, livingRoom);

                            // если комната найдена
                            if (foundRoom != null) {
                                // проверяем её GUID и если НЕ совпадает - добавляем на обновление GUID в БД Град
                                if (foundRoom.getPremisesGUID() == null || // но не задан GUID
                                        !(foundRoom.getPremisesGUID().equalsIgnoreCase(residentialPremise.getPremisesGUID()) &&
                                        foundRoom.getLivingroomGUID().equalsIgnoreCase(livingRoom.getLivingRoomGUID()))) {
                                    residentialMapForUpdate.put(foundRoom, simpleEntry);
                                }
                                // также если комната найдена - удаляем из копии списка Град, во избежание перезатирания GUID в БД
                                roomsForCreateGIS.remove(foundRoom);
                                livingRoomIterator.remove();
                            }
                        }
                    } else { // Если нет комнат передаем помещение

                        // ищем помещение Град по адресу
                        Room foundRoom = findGradRoom(houseRecord, roomsForCreateGIS, residentialPremise.getPremisesNum(), null, true, residentialPremise.getPremisesGUID());
                        // это вместо Pair
                        AbstractMap.SimpleEntry<ExportHouseResultType.ApartmentHouse.ResidentialPremises,
                                ExportHouseResultType.ApartmentHouse.ResidentialPremises.LivingRoom> simpleEntry = new AbstractMap.SimpleEntry<>(residentialPremise, null);

                        // если помещение найдено
                        if (foundRoom != null) {
                            if (foundRoom.getPremisesGUID() == null || // но не задан GUID
                                    !(foundRoom.getPremisesGUID().equalsIgnoreCase(residentialPremise.getPremisesGUID())) || // GUID задан, но НЕ совпадает
                                    foundRoom.getAbonId().size() > 1) { // коммуналки обновляем ВСЕГДА
                                // добавляем в мапу на обновление жилых помещений/комнат
                                residentialMapForUpdate.put(foundRoom, simpleEntry);
                            }
                            // также если помещение найдено - удаляем из копии списка Град, во избежание перезатирания GUID в БД
                            roomsForCreateGIS.remove(foundRoom);
                            residentialPremisesIterator.remove();
                        }
                    }
                }
            }

            // если есть нежилые помещения
            if (exportHouseResultType.getApartmentHouse().getNonResidentialPremises() != null) {
                // бежим по НЕжилым помещениям
                for (Iterator<ExportHouseResultType.ApartmentHouse.NonResidentialPremises> nonResidentialPremisesIterator = exportHouseResultType.getApartmentHouse().getNonResidentialPremises().iterator(); nonResidentialPremisesIterator.hasNext(); ) {
                    ExportHouseResultType.ApartmentHouse.NonResidentialPremises nonResidentialPremise = nonResidentialPremisesIterator.next();
                    // ищем помещение Град по адресу
                    Room foundRoom = findGradRoom(houseRecord, roomsForCreateGIS, nonResidentialPremise.getPremisesNum(), null, false, nonResidentialPremise.getPremisesGUID());
                    // если помещение найдено и оно одно
                    if (foundRoom != null) {
                        // проверяем GUID и если НЕ совпадает - добавляем на обновление GUID в БД Град
                        if (foundRoom.getPremisesGUID() == null ||
                                !(foundRoom.getPremisesGUID().equalsIgnoreCase(nonResidentialPremise.getPremisesGUID()))) {
                            nonResidentialMapForUpdate.put(foundRoom, nonResidentialPremise);
                        }
                        // также если помещение найдено - удаляем из копии списка Град, во избежание перезатирания GUID в БД
                        roomsForCreateGIS.remove(foundRoom);
                        nonResidentialPremisesIterator.remove();
                    }
                }
            }
        }

        // если частный дом
        if (exportHouseResultType.getLivingHouse() != null && exportHouseResultType.getLivingHouse().getLivingRoom() != null){
            for (Iterator<ExportHouseResultType.LivingHouse.LivingRoom> livingRoomIterator = exportHouseResultType.getLivingHouse().getLivingRoom().iterator(); livingRoomIterator.hasNext(); ) {
                ExportHouseResultType.LivingHouse.LivingRoom livingRoom = livingRoomIterator.next();

                // ищем комнату Град по адресу
                // TODO надо проверить на Тогучине работу с частными домами
                Room foundRoom = findGradRoom(houseRecord, roomsForCreateGIS, livingRoom.getRoomNumber(), null, true, livingRoom.getLivingRoomGUID());

                // если комната найдена
                if (foundRoom != null) {
                    // проверяем её GUID и если НЕ совпадает - добавляем на обновление GUID в БД Град
                    if (foundRoom.getPremisesGUID() == null || // но не задан GUID
                            !(foundRoom.getPremisesGUID().equalsIgnoreCase(livingRoom.getLivingRoomGUID()))) { // тут сравниваем так, потому что в Граде хранится все в одном признаке
                        livingHouseRoomsMapForUpdate.put(foundRoom, livingRoom);
                    }
                    // также если комната найдена - удаляем из копии списка Град, во избежание перезатирания GUID в БД
                    roomsForCreateGIS.remove(foundRoom);
                    livingRoomIterator.remove();
                }
            }

        }
    }


    /**
     * Метод ищет помещение/комнату в списке по заданному номеру квартиры и комнаты
     * @param roomsGrad - список помещений из Град
     * @param apartmentNumber - номер помещения
     * @param roomNumber - литера помещения
     * @param isResidential - жилое/нежилое
     * @return - помещение
     */
    private Room findGradRoom(final HouseRecord houseRecord,
                              final List<Room> roomsGrad,
                              final String apartmentNumber,
                              final String roomNumber,
                              final boolean isResidential,
                              final String premisesGUID) {

        List<Room> wasApartmentNumber = new ArrayList<>(); // флаг, что таким номером квартиры мы всетаки нашли
        final List<Room> roomList = new ArrayList<>();


        // фильтруем лист по номеру квартиры
        List<Room> tmpRoomsMap = roomsGrad.stream()
                .filter(r -> (r.getNumberAppart() != null &&
                        OtherFormat.compareAppartNumbers(r.getNumberAppart(), apartmentNumber)
                ))
                .collect(Collectors.toList());
        for (Room room : tmpRoomsMap) {
            if (room.isResidential() == isResidential) {
                if (roomNumber == null){
                    if(room.getNumberRoom() == null) { // если номер комнаты отсутствует в обоих случайя - это нужный нам абонент!
                        roomList.add(room);
                    }
                    else {
                        wasApartmentNumber.add(room); // запоминаем ИД абонента с таким номером квартиры, на случай если совсем не найдем с номером комнаты
                    }
                }
            } else if (roomNumber != null && roomNumber.equalsIgnoreCase(room.getNumberRoom())) { // если номер комнаты совпадает.
                roomList.add(room);
            }
        }

        if (roomList.isEmpty()) {// если ничего не нашли
            if (!wasApartmentNumber.isEmpty()){// но с таким номером квартиры все-таки был
                roomList.addAll(wasApartmentNumber);
            }
        }

        if (roomList.isEmpty()) {// ищем по PremisesGUID, вдруг руками прописали
            for (Room room : roomsGrad) {
                if(room.getPremisesGUID() != null && room.getPremisesGUID().equalsIgnoreCase(premisesGUID)){
                    roomList.add(room);
                }
            }
        }

        if (roomList.isEmpty()) {
            if (roomNumber == null) {
                answerProcessing.sendMessageToClient(String.format("Абонент Град для помещения %s не найден! Адрес дома: %s", apartmentNumber, houseRecord.getAddresStringShort()));
            } else{
                answerProcessing.sendMessageToClient(String.format("Абонент Град для комнаты: %s л. %s не найдены! Адрес дома: %s", apartmentNumber, roomNumber, houseRecord.getAddresStringShort()));
            }
            return null;
        }else if(roomList.size() > 1){
            if (roomNumber == null) {
                answerProcessing.sendMessageToClient(String.format("Для помещения %s найдено более одного абонента в Град! Адрес дома: %s", apartmentNumber, houseRecord.getAddresStringShort()));
            }else{
                answerProcessing.sendMessageToClient(String.format("Для комнаты: %s л. %s найдено более одного абонента в Град! Адрес дома: %s", apartmentNumber, roomNumber, houseRecord.getAddresStringShort()));
            }
            return null;
        }
        return roomList.get(0);
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
    @Deprecated
    private void getHouseData(final String fias, final Integer houseId, final HouseGRADDAO gradDao,
                              final Connection connectionGrad) throws SQLException, ParseException {

        try {
            // делаем запрос в ГИС
//            final GetStateResult result = callExportHouseData(fias);
            final GetStateResult result = HomeManagementAsyncPort.callExportHouseData(fias, answerProcessing);

            if (result != null && result.getErrorMessage() == null && result.getExportHouseResult() != null) { // Если нет ошибок

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

                            // Добавляем в БД уникальный номер помещения.
                            gradDao.setApartmentUniqueNumber(houseId, nonResidentialPremise.getPremisesNum(), null, false,
                                    nonResidentialPremise.getPremisesGUID(), nonResidentialPremise.getPremisesUniqueNumber(), null, null, connectionGrad);

                        }
                    }

                } else if (result.getExportHouseResult().getLivingHouse() != null) {  // Узнать что делать с ними
                    answerProcessing.sendMessageToClient("Жилой дом");

                    for (ExportHouseResultType.LivingHouse.LivingRoom room : result.getExportHouseResult().getLivingHouse().getLivingRoom()) {
//                            Добавляем в БД уникальный номер помещения.
                        gradDao.setApartmentUniqueNumber(houseId, room.getRoomNumber(), null, true,
                                room.getLivingRoomGUID(), room.getLivingRoomUniqueNumber(), null, null, connectionGrad);
                    }
                }
            } else {
                setErrorStatus(2);
            }
        } catch (PreGISException e) {
            setErrorStatus(2);
            answerProcessing.sendErrorToClient("getHouseData(): ", "\"Получение данных о МКД\" ", LOGGER, e);
        }
    }

    /**
     * Метод создает помещения в ГИС по заданному списку помещений из Град
     * @param roomsForCreate - список помещений для создания
     * @param houseRecord - объект-дом
     * @param cadastralNumber - кадастровый номер, полученный из ГИС
     * @param gradDao - объект ДАО для Град
     * @param connection - соединение с Град
     */
    private void sendPremisesToGis(List<Room> roomsForCreate,
                                   HouseRecord houseRecord,
                                   String cadastralNumber,
                                   final HouseGRADDAO gradDao,
                                   Connection connection) throws PreGISException, SQLException {



        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Отправляем данные в ГИС ЖКХ...");

        GetStateResult result = null;

        answerProcessing.clearLabelForText();
        BaseType importRequest;

        // делаем мапу, в которую лягут наши квартиры с TransportGUID, чтобы потом разбирать и заносить в БД
        Map<String, Room> roomMapTransport = new HashMap<>();
        
        
        // для управляшки и РСО разные запросы на занесение данных
        if(ResourcesUtil.instance().getCompanyRole().equalsIgnoreCase("UO")) {
            // конструируем запрос
            importRequest = constructImportUORequest(roomsForCreate, houseRecord, cadastralNumber, roomMapTransport);
            // вызываем ГИС для импорта данных
            result = HomeManagementAsyncPort.callImportHouseUOData((ImportHouseUORequest) importRequest, answerProcessing);
        }

        if(ResourcesUtil.instance().getCompanyRole().equalsIgnoreCase("RSO")) {
            // конструируем запрос
            importRequest = constructImportRSORequest(roomsForCreate, houseRecord, cadastralNumber, roomMapTransport);
            // вызываем ГИС для импорта данных
            result = HomeManagementAsyncPort.callImportHouseRSOData((ImportHouseRSORequest) importRequest, answerProcessing);
        }

        // если всё прошло нормально
        if (result != null && result.getImportResult() != null) {
            setPremisesToBase(
                        roomMapTransport,
                        result,
                        gradDao,
                        connection);
        }
    }


    /**
     * метод обработки результат импорта помещений - заносит идентификаторы новых помещений в Град, выдает лог ошибок 
     * @param roomMapTransport - мапа с сопоставленными транспортному GUID помещениями из Град
     * @param result - результат импорта в ГИС
     * @param gradDao - объект ДАО для Град
     * @param connection - соединение с Град
     */
    private void setPremisesToBase(Map<String, Room> roomMapTransport,
                                   GetStateResult result,
                                   final HouseGRADDAO gradDao,
                                   Connection connection) throws SQLException, PreGISException {
        // бежим по результатм импорта
        for (ImportResult importResult : result.getImportResult()) {
            for (ImportResult.CommonResult commonResult : importResult.getCommonResult()) {
                // если нет ошибок
                if (commonResult.getError() == null || commonResult.getError().isEmpty()) {
                    // находим помещение из Град
                    Room room = roomMapTransport.get(commonResult.getTransportGUID());

                    if(room != null) {
                        if (room.getNumberRoom() == null || room.getNumberRoom().isEmpty()) { // помещение-квартира
                            for (Integer abonId : room.getAbonId()) {
                                gradDao.setApartmentUniqueNumber(
                                        commonResult.getGUID(),
                                        commonResult.getUniqueNumber(),
                                        null,
                                        null,
                                        connection,
                                        abonId);
                            }
                        } else { // если комната
                            for (Integer abonId : room.getAbonId()) {
                                gradDao.setApartmentUniqueNumber(
                                        null,
                                        null,
                                        commonResult.getGUID(),
                                        commonResult.getUniqueNumber(),
                                        connection,
                                        abonId);
                            }
                        }
                    }
                } else {
                    answerProcessing.sendMessageToClient("");
                    answerProcessing.sendMessageToClient("Ошибка при создании помещения/комнаты");
                    answerProcessing.sendMessageToClient("Код ошибки: " + commonResult.getError().get(0).getErrorCode());
                    answerProcessing.sendMessageToClient("Описание ошибки: " + commonResult.getError().get(0).getDescription());
                    setErrorStatus(1);
                }
            }
        }
    }


    /**
     * Метод конструирует объект запроса на обновление строения ImportHouseUORequest для добавления помещений. Есть брат-близнец для РСО
     * @param roomsForCreate - список помещений для создания
     * @param houseRecord - POJO-объект дома
     * @param cadastralNumber - кадастровый номер дома, полученный из запроса к ГИС
     * @param roomMapTransport - мапа для хранения транспортных GUID и какая комната к ним привязана в итоге. Выходной параметр
     * @return
     * @throws SQLException
     * @throws PreGISException
     */
    private ImportHouseUORequest constructImportUORequest(final List<Room> roomsForCreate,
                                                          final HouseRecord houseRecord,
                                                          final String cadastralNumber,
                                                          Map<String, Room> roomMapTransport) throws SQLException, PreGISException {

        ReferenceNSI nsi = new ReferenceNSI(answerProcessing);

        ImportHouseUORequest importHouseUORequest = new ImportHouseUORequest();
        ImportHouseUORequest.ApartmentHouse apartmentHouse = new ImportHouseUORequest.ApartmentHouse();

        for (Room room : roomsForCreate) {
            if (room.isResidential()) { // жилые
                if (room.getNumberRoom() == null || room.getNumberRoom().isEmpty()) { // помещение-квартира
                    // создаем объект - квартира
                    // используем полные пути к типам, так как естьеще RSO с такими же названиями объектов
                    ImportHouseUORequest.ApartmentHouse.ResidentialPremises.ResidentialPremisesToCreate residentialPremisesToCreate = 
                            new ImportHouseUORequest.ApartmentHouse.ResidentialPremises.ResidentialPremisesToCreate();

                    residentialPremisesToCreate.setTransportGUID(OtherFormat.getRandomGUID());
                    // если задан подезд - указываем его, иначе устанавливаем "нет подъезда"
                    if (room.getDoorWay().equalsIgnoreCase("0") || room.getDoorWay().isEmpty()) {
                        residentialPremisesToCreate.setHasNoEntrance(true);
                    } else {
                        residentialPremisesToCreate.setEntranceNum(room.getDoorWay());
                    }

                    residentialPremisesToCreate.setGrossArea(room.getTotalArea());
                    residentialPremisesToCreate.setTotalArea(room.getTotalArea());
                    residentialPremisesToCreate.setNoRSOGKNEGRPRegistered(true);
                    residentialPremisesToCreate.setPremisesCharacteristic(nsi.getNsiRef("30", "Отдельная квартира"));
                    residentialPremisesToCreate.setPremisesNum(room.getNumberAppart());

                    ImportHouseUORequest.ApartmentHouse.ResidentialPremises residentialPremises = new ImportHouseUORequest.ApartmentHouse.ResidentialPremises();
                    apartmentHouse.getResidentialPremises().add(residentialPremises);
                    residentialPremises.setResidentialPremisesToCreate(residentialPremisesToCreate);
                    
                    roomMapTransport.put(residentialPremisesToCreate.getTransportGUID(), room);
                } else { // комната
                    // TODO тут ОШИБКА! Жилиые комнаты можно добавлять только если помещение создается или изменяется в этом же запросе! Пока непонятно, при следующем обращении надо сделать
                    ImportHouseUORequest.ApartmentHouse.ResidentialPremises.LivingRoomToCreate livingRoomToCreate = 
                            new ImportHouseUORequest.ApartmentHouse.ResidentialPremises.LivingRoomToCreate();
                    livingRoomToCreate.setTransportGUID(OtherFormat.getRandomGUID());

                    livingRoomToCreate.setSquare(room.getTotalArea());
                    livingRoomToCreate.setNoRSOGKNEGRPRegistered(true);
                    livingRoomToCreate.setRoomNumber(room.getNumberAppart());

                    ImportHouseUORequest.ApartmentHouse.ResidentialPremises residentialPremises = new ImportHouseUORequest.ApartmentHouse.ResidentialPremises();
                    apartmentHouse.getResidentialPremises().add(residentialPremises);
                    residentialPremises.getLivingRoomToCreate().add(livingRoomToCreate);

                    roomMapTransport.put(livingRoomToCreate.getTransportGUID(), room);
                }
            } else { // нежилые
                ImportHouseUORequest.ApartmentHouse.NonResidentialPremiseToCreate nonResidentialPremiseToCreate = 
                        new ImportHouseUORequest.ApartmentHouse.NonResidentialPremiseToCreate();
                nonResidentialPremiseToCreate.setTransportGUID(OtherFormat.getRandomGUID());
                nonResidentialPremiseToCreate.setTotalArea(room.getTotalArea());
                nonResidentialPremiseToCreate.setNoRSOGKNEGRPRegistered(true);
                nonResidentialPremiseToCreate.setIsCommonProperty(false); // по-умолчанию - не общее имущество
                nonResidentialPremiseToCreate.setPremisesNum(room.getNumberAppart());

                apartmentHouse.getNonResidentialPremiseToCreate().add(nonResidentialPremiseToCreate);

                roomMapTransport.put(nonResidentialPremiseToCreate.getTransportGUID(), room);
            }
        }

        ImportHouseUORequest.ApartmentHouse.ApartmentHouseToUpdate apartmentHouseToUpdate = new ImportHouseUORequest.ApartmentHouse.ApartmentHouseToUpdate();
        apartmentHouseToUpdate.setTransportGUID(OtherFormat.getRandomGUID());

        HouseBasicUpdateUOType basicCharacteristicts = new HouseBasicUpdateUOType();

        basicCharacteristicts.setFIASHouseGuid(houseRecord.getFias());
        if(cadastralNumber == null || cadastralNumber.isEmpty()){
            basicCharacteristicts.setNoRSOGKNEGRPRegistered(true);
        }else {
            basicCharacteristicts.setCadastralNumber(cadastralNumber);
        }

        apartmentHouseToUpdate.setBasicCharacteristicts(basicCharacteristicts);

        apartmentHouse.setApartmentHouseToUpdate(apartmentHouseToUpdate);
        importHouseUORequest.setApartmentHouse(apartmentHouse);

        importHouseUORequest.setInheritMissingValues(true);

        return importHouseUORequest;
    }

    /**
     * Метод конструирует объект запроса на обновление строения ImportHouseRSORequest для добавления помещений. Есть брат-близнец для УО
     * @param roomsForCreate - список помещений для создания
     * @param houseRecord - POJO-объект дома
     * @param cadastralNumber - кадастровый номер дома, полученный из запроса к ГИС
     * @param roomMapTransport - мапа для хранения транспортных GUID и какая комната к ним привязана в итоге. Выходной параметр
     * @return
     * @throws SQLException
     * @throws PreGISException
     */
    private ImportHouseRSORequest constructImportRSORequest(final List<Room> roomsForCreate,
                                                          final HouseRecord houseRecord,
                                                          final String cadastralNumber,
                                                          Map<String, Room> roomMapTransport) throws SQLException, PreGISException {

        ReferenceNSI nsi = new ReferenceNSI(answerProcessing);

        ImportHouseRSORequest importHouseRSORequest = new ImportHouseRSORequest();
        ImportHouseRSORequest.ApartmentHouse apartmentHouse = new ImportHouseRSORequest.ApartmentHouse();

        for (Room room : roomsForCreate) {
            if (room.isResidential()) { // жилые
                if (room.getNumberRoom() == null || room.getNumberRoom().isEmpty()) { // помещение-квартира
                    // создаем объект - квартира
                    // используем полные пути к типам, так как естьеще RSO с такими же названиями объектов
                    ImportHouseRSORequest.ApartmentHouse.ResidentialPremises.ResidentialPremisesToCreate residentialPremisesToCreate =
                            new ImportHouseRSORequest.ApartmentHouse.ResidentialPremises.ResidentialPremisesToCreate();

                    residentialPremisesToCreate.setTransportGUID(OtherFormat.getRandomGUID());
                    // если задан подезд - указываем его, иначе устанавливаем "нет подъезда"
                    if (room.getDoorWay().equalsIgnoreCase("0") || room.getDoorWay().isEmpty()) {
                        residentialPremisesToCreate.setHasNoEntrance(true);
                    } else {
                        residentialPremisesToCreate.setEntranceNum(room.getDoorWay());
                    }

//                    residentialPremisesToCreate.setGrossArea(room.getTotalArea());
                    residentialPremisesToCreate.setTotalArea(room.getTotalArea());
                    residentialPremisesToCreate.setNoRSOGKNEGRPRegistered(true);
                    residentialPremisesToCreate.setPremisesCharacteristic(nsi.getNsiRef("30", "Отдельная квартира"));
                    residentialPremisesToCreate.setPremisesNum(room.getNumberAppart());

                    ImportHouseRSORequest.ApartmentHouse.ResidentialPremises residentialPremises = new ImportHouseRSORequest.ApartmentHouse.ResidentialPremises();
                    apartmentHouse.getResidentialPremises().add(residentialPremises);
                    residentialPremises.setResidentialPremisesToCreate(residentialPremisesToCreate);

                    roomMapTransport.put(residentialPremisesToCreate.getTransportGUID(), room);
                } else { // комната
                    ImportHouseRSORequest.ApartmentHouse.ResidentialPremises.LivingRoomToCreate livingRoomToCreate =
                            new ImportHouseRSORequest.ApartmentHouse.ResidentialPremises.LivingRoomToCreate();
                    livingRoomToCreate.setTransportGUID(OtherFormat.getRandomGUID());

                    livingRoomToCreate.setSquare(room.getTotalArea());
                    livingRoomToCreate.setNoRSOGKNEGRPRegistered(true);
                    livingRoomToCreate.setRoomNumber(room.getNumberAppart());

                    ImportHouseRSORequest.ApartmentHouse.ResidentialPremises residentialPremises = new ImportHouseRSORequest.ApartmentHouse.ResidentialPremises();
                    apartmentHouse.getResidentialPremises().add(residentialPremises);
                    residentialPremises.getLivingRoomToCreate().add(livingRoomToCreate);

                    roomMapTransport.put(livingRoomToCreate.getTransportGUID(), room);
                }
            } else { // нежилые
                ImportHouseRSORequest.ApartmentHouse.NonResidentialPremiseToCreate nonResidentialPremiseToCreate =
                        new ImportHouseRSORequest.ApartmentHouse.NonResidentialPremiseToCreate();
                nonResidentialPremiseToCreate.setTransportGUID(OtherFormat.getRandomGUID());
                nonResidentialPremiseToCreate.setTotalArea(room.getTotalArea());
                nonResidentialPremiseToCreate.setNoRSOGKNEGRPRegistered(true);
//                nonResidentialPremiseToCreate.setIsCommonProperty(false); // по-умолчанию - не общее имущество
                nonResidentialPremiseToCreate.setPremisesNum(room.getNumberAppart());

                apartmentHouse.getNonResidentialPremiseToCreate().add(nonResidentialPremiseToCreate);

                roomMapTransport.put(nonResidentialPremiseToCreate.getTransportGUID(), room);
            }
        }

        ImportHouseRSORequest.ApartmentHouse.ApartmentHouseToUpdate apartmentHouseToUpdate = new ImportHouseRSORequest.ApartmentHouse.ApartmentHouseToUpdate();
        HouseBasicUpdateRSOType basicCharacteristicts = new HouseBasicUpdateRSOType();

        basicCharacteristicts.setFIASHouseGuid(houseRecord.getFias());
        if(cadastralNumber == null || cadastralNumber.isEmpty()){
            basicCharacteristicts.setNoRSOGKNEGRPRegistered(true);
        }else {
            basicCharacteristicts.setCadastralNumber(cadastralNumber);
        }
        apartmentHouseToUpdate.setBasicCharacteristicts(basicCharacteristicts);

        apartmentHouse.setApartmentHouseToUpdate(apartmentHouseToUpdate);
        importHouseRSORequest.setApartmentHouse(apartmentHouse);

        importHouseRSORequest.setInheritMissingValues(true);

        return importHouseRSORequest;
    }

    /**
     * Метод, подготавливает данные и перенаправляет их в метод getHouseData().
     *
     * @param houseRecord    содержит данные о доме.
     * @param gradDao        работа с БД ГРАД.
     * @param connectionGrad подключение к БД ГРАД
     * @throws SQLException  возможны ошибки при работе с БД.
     */
    @Deprecated
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
