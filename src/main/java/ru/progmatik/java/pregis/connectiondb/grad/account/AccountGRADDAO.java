package ru.progmatik.java.pregis.connectiondb.grad.account;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.ID;
import ru.gosuslugi.dom.schema.integration.base.RegOrgVersionType;
import ru.gosuslugi.dom.schema.integration.services.house_management.AccountIndType;
import ru.gosuslugi.dom.schema.integration.services.house_management.AccountType;
import ru.gosuslugi.dom.schema.integration.services.house_management.ImportAccountRequest;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportOrgRegistryRequest;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportOrgRegistryResult;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.AnswerYesOrNo;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.BasicInformation;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.DocumentType;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.Rooms;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSI;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.services.organizations.common.service.ExportOrgRegistry;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

/**
 * Класс, обращается в БД ГРАД, за данными о лицевых счетах.
 */
public class AccountGRADDAO {

    private static final Logger LOGGER = Logger.getLogger(AccountGRADDAO.class);

    private static final String TABLE_NAME_ACCOUNT_FOR_REMOVE = "ACCOUNT_FOR_REMOVE";
    private static final String SQL_CREATE_TABLE_ACCOUNT_FOR_REMOVE = "CREATE TABLE IF NOT EXISTS ACCOUNT_FOR_REMOVE (" +
            "ID identity not null primary key, " +
            "ABONID bigint not null, " +
            "ABONLS varchar(255) not null, " +
            "FIO varchar(255), " +
            "ENDDATE date, " +
            "NSICODE varchar(20), " +
            "CLOSED boolean not null);" +
            "COMMENT ON TABLE \"PUBLIC\".ACCOUNT_FOR_REMOVE IS 'Таблица содержит ЛС для удаления из ГИС ЖКХ.'; " +
            "COMMENT ON COLUMN ACCOUNT_FOR_REMOVE.ID IS 'Идентификатор записей.'; " +
            "COMMENT ON COLUMN ACCOUNT_FOR_REMOVE.ABONID IS 'Идентификатор в БД ГРАД.'; " +
            "COMMENT ON COLUMN ACCOUNT_FOR_REMOVE.ABONLS IS 'Номер лицевого счета.'; " +
            "COMMENT ON COLUMN ACCOUNT_FOR_REMOVE.FIO IS 'ФИО абонента.'; " +
            "COMMENT ON COLUMN ACCOUNT_FOR_REMOVE.ENDDATE IS 'Дата закрытия счета.'; " +
            "COMMENT ON COLUMN ACCOUNT_FOR_REMOVE.NSICODE IS 'Код справочника, указана причина закрытия счета.';";
    private final AnswerProcessing answerProcessing;
    private SimpleDateFormat dateFromSQL = new SimpleDateFormat("yyyy-MM-dd");

    public AccountGRADDAO(AnswerProcessing answerProcessing) throws SQLException {
        this.answerProcessing = answerProcessing;
        if (!ConnectionDB.instance().tableExist(TABLE_NAME_ACCOUNT_FOR_REMOVE.toUpperCase())) {
            try (Connection connection = ConnectionDB.instance().getConnectionDB();
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate(SQL_CREATE_TABLE_ACCOUNT_FOR_REMOVE);
            }
        }
    }

    /**
     * Метод, приводит дату в нужный формат.
     *
     * @param date дата для обработки.
     * @return дата в пригодном формате.
     */
    private static XMLGregorianCalendar getCalendar(Date date) {

        return OtherFormat.getDateForXML(date);
    }

    /**
     * Метод, получает от метода "createExcel" ид дома и таблицу Excel.
     * Добавляет информацию на лист «Основные сведения».
     *
     * @param houseID - ИД адрес дома.
     */
    private ArrayList<BasicInformation> getBasicInformation(int houseID, Connection connection) throws SQLException {

        String sqlRequest = "SELECT * FROM EX_GIS_LS1(" + houseID + ")";
        ArrayList<BasicInformation> listBasic = new ArrayList<>();

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlRequest)) {
            while (resultSet.next()) {

                BasicInformation bi = new BasicInformation();

                String[] arrayData = getAllData(resultSet.getString(1));

                try {
                    bi.setNumberLS(arrayData[0]);
                    bi.setEmployer(AnswerYesOrNo.getAnswer(arrayData[1]));
                    bi.setSurname(arrayData[2]);
                    bi.setName(arrayData[3]);
                    bi.setMiddleName(arrayData[4]);
                    bi.setSnils(arrayData[5]);
                    bi.setTypeDocument(DocumentType.getTypeDocument(arrayData[6]));
                    bi.setNumberDocumentIdentity(arrayData[7]);
                    bi.setSeriesDocumentIdentity(arrayData[8]);
                    if (arrayData[9] != null)
                        if (!arrayData[9].isEmpty()) {
                            try {
                                bi.setDateDocumentIdentity(dateFromSQL.parse(arrayData[9]));
                            } catch (ParseException e) {
                                LOGGER.error("ExtractSQL: Не верный формат для ячейки.", e);
                                e.printStackTrace();
                            }
                        }
                    bi.setOgrnOrOgrnip(Long.valueOf(checkZero(arrayData[10])));
                    bi.setKpp(Integer.valueOf(checkZero(arrayData[11])));
                    bi.setTotalArea(Double.valueOf(checkZero(arrayData[12])));
                    bi.setLivingSpace(Double.valueOf(checkZero(arrayData[13])));
                    bi.setHeadtedArea(Double.valueOf(checkZero(arrayData[14])));
                    bi.setAmountLiving(Integer.valueOf(checkZero(arrayData[15])));

                } catch (NumberFormatException e) {
                    LOGGER.error("ExtractSQL: Не верный формат для ячейки.", e);
                }
                listBasic.add(bi);
            }
        }
        if (listBasic.size() == 0) return null; // если нет ЛС возвращаем null.
        return listBasic;
    }

    /**
     * Метод, получает от метода "createExcel" адрес дома и таблицу Excel.
     * Добавляет информацию на лист "Помещение".
     *
     * @param houseID - ИД адрес дома.
     */
    private ArrayList<Rooms> getRooms(int houseID, Connection connection) throws ParseException, SQLException {

        String sqlRequest = "SELECT * FROM EX_GIS_LS2(" + houseID + ")";
        ArrayList<Rooms> listRooms = new ArrayList();

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlRequest)) {
            while (resultSet.next()) {

                Rooms rooms = new Rooms();

                try {
                    String[] arrayData = getAllData(resultSet.getString(1));

                    rooms.setNumberLS(arrayData[0]);
                    rooms.setAddress(arrayData[1]);
                    rooms.setFias(arrayData[2]);
                    rooms.setNumberRooms(arrayData[3]);
                    rooms.setNumberApartment(arrayData[4]);
                    rooms.setIdSpaceGISJKH(arrayData[5]);
                    rooms.setSharePay(Integer.valueOf(checkZero(arrayData[6])));
                    rooms.setAbonId(Integer.valueOf(checkZero(arrayData[7])));
//                    rooms.setAccountGUID(arrayData[8]); // Саша должен добавить
//                    rooms.setCompany(); // указать статус абонента, true - если юр.лицо, false - если физ.лицо.

                } catch (NumberFormatException e) {
                    LOGGER.error("ExtractSQL: Не верный формат для ячейки.", e);
                }
                listRooms.add(rooms);
            }
        }
        if (listRooms.size() == 0) return null; // если нет ЛС возвращаем null.

        return listRooms;
    }

    /**
     * Метод, формирует Map ЛС из БД ГРАД.
     * Ключ - ЛС, значение - класс Account пригодный для импорта ЛС в ГИС ЖКХ.
     *
     * @param houseID ИД дома в БД ГРАД.
     * @throws ParseException может возникнуть ошибка при импорте из БД числа кол-во проживающих.
     * @throws SQLException   возможны ошибки БД.
     */
    public LinkedHashMap<String, ImportAccountRequest.Account> getAccountListFromGrad(int houseID, Connection connection) throws ParseException, SQLException, PreGISException {

        answerProcessing.sendMessageToClient("Формирую данные...");
        ArrayList<BasicInformation> basicInformationList = getBasicInformation(houseID, connection);
        ArrayList<Rooms> roomsList = getRooms(houseID, connection);
        ReferenceNSI nsi = new ReferenceNSI(answerProcessing);
        ExportOrgRegistry orgRegistry = new ExportOrgRegistry(answerProcessing);

        LinkedHashMap<String, ImportAccountRequest.Account> mapAccount = new LinkedHashMap<>();

        if (basicInformationList == null || roomsList == null) {
            throw new PreGISException("Не найдены лицевые счета для дома с ИД: " + houseID + ".");
        }

        for (BasicInformation basicInformation : basicInformationList) {
            int count = 0;
            for (int i = 0; i < roomsList.size(); i++) {

//                Формируем объект пригодный для импорта в ГИС ЖКХ.
//                По необходимости в дальнейшем нужно присвоить перед отправкой:
//                - если новый счет указать TransportGUID, удалить если есть AccountGUID.
//                - если счет к закрытию указать AccountGUID и isClosed.
                if (basicInformation.getNumberLS().equals(roomsList.get(i).getNumberLS())) {
                    ImportAccountRequest.Account account = new ImportAccountRequest.Account();
//                    account.setCreationDate(OtherFormat.getDateNow()); // может без даты можно? добавил при отправки нового счета
                    account.setLivingPersonsNumber((byte) basicInformation.getAmountLiving());
                    account.setTotalSquare(new BigDecimal(basicInformation.getTotalArea()).setScale(2, BigDecimal.ROUND_DOWN));
                    account.setResidentialSquare(new BigDecimal(basicInformation.getLivingSpace()).setScale(2, BigDecimal.ROUND_DOWN));
                    account.setHeatedArea(new BigDecimal(basicInformation.getHeadtedArea()).setScale(2, BigDecimal.ROUND_DOWN));
//                    account.setClosed(); // проверить, если в ГИС ЖКХ есть, а в БД ГРАД нет, то установить в ExportAccountData.
                    AccountType.Accommodation accommodation = new AccountType.Accommodation();
                    accommodation.setPremisesGUID(getBuildingIdentifiersFromBase(roomsList.get(i).getAbonId(), "PREMISESGUID", connection));
//                    accommodation.setFIASHouseGuid(roomsList.get(i).getFias()); // Выдаё ошибку, по описанию можно выбирать.
                    accommodation.setLivingRoomGUID(getBuildingIdentifiersFromBase(roomsList.get(i).getAbonId(), "LIVINGROOMGUID", connection)); // Идентификатор комнаты
                    accommodation.setSharePercent(new BigDecimal(roomsList.get(i).getSharePay()).setScale(0, BigDecimal.ROUND_DOWN));
                    account.getAccommodation().add(accommodation);
//                    account.setTransportGUID();  // указывается, если ЛС добавляется в первые.

//                    Сведения о платильщике
                    account.setPayerInfo(new AccountType.PayerInfo());
//                    if (basicInformation.getOgrnOrOgrnip() == 0) {
                    account.getPayerInfo().setInd(new AccountIndType());
                    account.getPayerInfo().getInd().setSurname(basicInformation.getSurname());
                    account.getPayerInfo().getInd().setFirstName(basicInformation.getName());
                    account.getPayerInfo().getInd().setPatronymic(basicInformation.getMiddleName());
//                        account.getPayerInfo().getInd().setSex(); // не указан
//                        account.getPayerInfo().getInd().setDateOfBirth(); // не указан
                    if (basicInformation.getSnils() != null || !basicInformation.getSnils().trim().isEmpty()) {
                        account.getPayerInfo().getInd().setSNILS(basicInformation.getSnils());
                    }

                    if (basicInformation.getOgrnOrOgrnip() < 999999999999L) {
                        if (basicInformation.getTypeDocument() != null) {
                            account.getPayerInfo().getInd().setID(new ID()); // подгрузить справочник NSI 95
                            account.getPayerInfo().getInd().getID().setType(nsi.getTypeDocumentNsiRef(basicInformation.getTypeDocument().getTypeDocument()));
                            account.getPayerInfo().getInd().getID().setNumber(basicInformation.getNumberDocumentIdentity());
                            account.getPayerInfo().getInd().getID().setSeries(basicInformation.getSeriesDocumentIdentity());
                            account.getPayerInfo().getInd().getID().setIssueDate(getCalendar(basicInformation.getDateDocumentIdentity()));
                        }

                    } else {
//                        Есть возможность указать на VersionGUID из реестра организаций, вот только где его взять?
                        if (basicInformation.getOgrnOrOgrnip() > 0) {
                            ExportOrgRegistryRequest.SearchCriteria criteria = new ExportOrgRegistryRequest.SearchCriteria();

                            if (basicInformation.getOgrnOrOgrnip() < 10000000000000L && basicInformation.getOgrnOrOgrnip() > 999999999999L) {
                                criteria.setOGRN(String.valueOf(basicInformation.getOgrnOrOgrnip()));
                            } else if (basicInformation.getOgrnOrOgrnip() > 99999999999999L) {
                                criteria.setOGRNIP(String.valueOf(basicInformation.getOgrnOrOgrnip()));
                            }
                            ExportOrgRegistryResult result = orgRegistry.callExportOrgRegistry(orgRegistry.getExportOrgRegistryRequest(criteria));
                            account.getPayerInfo().setOrg(new RegOrgVersionType());
                            account.getPayerInfo().getOrg().setOrgVersionGUID(result.getOrgData().get(0).getOrgVersion().getOrgVersionGUID());
                        }
                    }

                    if (basicInformation.getEmployer() == AnswerYesOrNo.YES) {
                        account.getPayerInfo().setIsRenter(true); // выдаёт ошибку если указать false
                    }

                    account.setAccountNumber(basicInformation.getNumberLS());
                    account.setAccountGUID(getAccountGUIDFromBase(roomsList.get(i).getAbonId(), connection));  // добавляется, если счет будет изменен или закрыт, если этого не будет перед отправкой затереть.

                    mapAccount.put(basicInformation.getNumberLS(), account);

                    count++;
                    roomsList.remove(i);
                    i--;
                }
            }
            if (count == 1) {
                continue;
            } else if (count == 0) {
                answerProcessing.sendInformationToClientAndLog("Для счета - "
                        + basicInformation.getNumberLS() + ", не удалось найти соответствие в базе данных.", LOGGER);
            } else if (count >= 2) {
                answerProcessing.sendInformationToClientAndLog("Для счета - "
                        + basicInformation.getNumberLS() + ", найдено более одного соответствия в базе данных.", LOGGER);
            }
        }
        return mapAccount;
    }



    /**
     * Метод, извлекает идентификаторы помещений.
     *
     * @param abonId ид абонента в БД ГРАД.
     * @param identifier название идентификатора могут быть PREMISESGUID, PREMISESUNIQNUM, LIVINGROOMGUID,
     *                   ROOMUNIQNUMBER, METERVERSIONGUID                  , METERROOTGUID               .
     * @return идентификатор.
     * @throws SQLException
     */
    public String getBuildingIdentifiersFromBase(Integer abonId, String identifier, Connection connection) throws SQLException {

        String sqlResult;
        String sqlRequest = "{EXECUTE PROCEDURE EX_GIS_ID(?, NULL , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ?)}";

        try (CallableStatement cstmt = connection.prepareCall(sqlRequest)) { // После использования должны все соединения закрыться
            cstmt.setInt(1, abonId);
            cstmt.setString(2, identifier);
            ResultSet resultSet = cstmt.executeQuery();
            resultSet.next();
            sqlResult = resultSet.getString(1);
            resultSet.close();
            if (sqlResult == null || sqlResult.isEmpty()) {
                return null;
            }
        }
//        System.err.println("Получен идентификатр: " + sqlResult); // debug
        return sqlResult;
    }

    /**
     * Метод, добавляет идентификаторы в БД ГРАДа.
     *
     * @param houseId             ид дома в БД ГРАД.
     * @param accountNumber       номер ЛС.
     * @param accountGUID         идентификатор ЛС в ГИС ЖКХ.
     * @param accountUniqueNumber уникальный номер ЛС, присвоенный ГИС ЖКХ.
     * @throws SQLException
     * @throws PreGISException
     * @throws ParseException
     */
    public void setAccountGuidAndUniqueNumber(Integer houseId, String accountNumber,
                                              String accountGUID, String accountUniqueNumber, Connection connection) throws SQLException, PreGISException, ParseException {

        Integer abonentId = getAbonentIdFromGrad(houseId, accountNumber, connection);

        if (abonentId != null) {
            // ИД дома(:building_id),
            // ИД абонента(:abon_id),
            // ИД прибора учета(:meter_id),
            // уникальный идентификатор ГИС ЖКХ(:gis_id),
            // уникальный идентификатор лицевого счета ГИС ЖКХ(:gis_ls_id)
            String sqlRequest = "{EXECUTE PROCEDURE EX_GIS_ID(?, NULL , NULL, NULL, ?, ?, NULL, NULL, NULL, NULL, NULL, NULL, NULL)}";
            try (CallableStatement cstmt = connection.prepareCall(sqlRequest)) {
                cstmt.setInt(1, abonentId);
                cstmt.setString(2, accountGUID);
                cstmt.setString(3, accountUniqueNumber);
                cstmt.executeUpdate();
//                int codeReturn = cstmt.executeUpdate();
//                System.err.println("Apartment code return: " + codeReturn);
            }
        } else {
            throw new PreGISException("setApartmentUniqueNumber(): Не удалось найти ID абонента в БД ГРАД.");
        }
    }

    /**
     * Метод, получает
     *
     * @param abonId ид абонента в БД ГРАД.
     * @return AccountGUID идентификатор ЛС в ГИС ЖКХ.
     * @throws SQLException
     */
    public String getAccountGUIDFromBase(Integer abonId, Connection connection) throws SQLException {

        String sqlResult;
        String sqlRequest = "{EXECUTE PROCEDURE EX_GIS_ID(?, NULL , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ?)}";

        try (CallableStatement cstmt = connection.prepareCall(sqlRequest)) { // После использования должны все соединения закрыться
            cstmt.setInt(1, abonId);
            cstmt.setString(2, "ACCOUNTGUID");
            ResultSet resultSet = cstmt.executeQuery();
            resultSet.next();
            sqlResult = resultSet.getString(1);
            resultSet.close();
            if (sqlResult == null || sqlResult.isEmpty()) {
                return null;
            }
        }
        return sqlResult;
    }

    /**
     * Метод, находит в БД ГРАД ид абонента.
     *
     * @param houseId       ид дома в БД ГРАД.
     * @param accountNumber номер ЛС.
     * @return ид абонента в БД ГРАД.
     * @throws ParseException
     * @throws SQLException
     */
    private Integer getAbonentIdFromGrad(Integer houseId, String accountNumber, Connection connection) throws ParseException, SQLException {
        ArrayList<Rooms> rooms = getRooms(houseId, connection);
        if (rooms != null)
            for (Rooms room : rooms) {
                if (accountNumber.equals(room.getNumberLS())) {
                    return room.getAbonId();
                }
            }
        return null;
    }

    /**
     * Метод, формирует данные и заносит в таблицу "ACCOUNT_FOR_REMOVE".
     *
     * @param houseId       ид дома в БД ГРАД.
     * @param accountNumber номер ЛС.
     * @return true - если всё прошло успешно, false - если не удалось найти все данные.
     * @throws ParseException
     * @throws SQLException
     */
    public boolean addAccountForRemove(Integer houseId, String accountNumber, Connection connection) throws ParseException, SQLException {

        String fio = "";


        ArrayList<BasicInformation> basicInformation = getBasicInformation(houseId, connection);
        if (basicInformation != null)
            for (BasicInformation information : basicInformation) {
                if (accountNumber.equals(information.getNumberLS())) {
                    fio = information.getSurname() + " " + information.getName() + " " + information.getMiddleName();
                    setAccountForRemove(getAbonentIdFromGrad(houseId, accountNumber, connection), accountNumber, fio, connection);
                }
            }
        return !fio.isEmpty(); // если не составили фио значит что то пошло не так.
    }

    /**
     * Метод, заносит в базу абонента для последующего удаления.
     *
     * @param abonId        ид дома в БД ГРАД.
     * @param accountNumber номер ЛС.
     * @param fio           ФИО абонента.
     * @throws SQLException
     */
    private void setAccountForRemove(Integer abonId, String accountNumber, String fio, Connection connection) throws SQLException {
        if (!isAccountForRemove(accountNumber, connection)) {
            java.sql.Date dateEnd = getAccountEndDate(abonId, connection);
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO ACCOUNT_FOR_REMOVE(ABONID, ABONLS, FIO, ENDDATE, CLOSED) VALUES(?, ?, ?, ?, ?)")) {

                ps.setInt(1, abonId);
                ps.setString(2, accountNumber);
                ps.setString(3, fio);
                ps.setDate(4, dateEnd);
                ps.setBoolean(5, false);
                ps.executeUpdate();
                answerProcessing.sendMessageToClient("Лицевой счет №" + accountNumber + " помечен на удаление.");
            }
        }
    }

    private java.sql.Date getAccountEndDate(Integer abonId, Connection connection) throws SQLException {

        String sqlRequest = "select edate from abonents where id = ? and is_active = 0";
        java.sql.Date date = null;

        try (PreparedStatement statement = connection.prepareStatement(sqlRequest)) {
            statement.setInt(1, abonId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                date = resultSet.getDate(1);
            }
            resultSet.close();
        }
        return date;
    }

    /**
     * Метод, обновляет данные абонента помеченного на удаление.
     *
     * @param accountNumber номер ЛС.
     * @param date          дата закрытия счета из БД ГРАД.
     * @param nsiCode       указанная причина из справочника.
     * @throws SQLException
     */
    public void updateAccountForRemove(String accountNumber, java.sql.Date date, String nsiCode, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE ACCOUNT_FOR_REMOVE SET " +
                     "ENDDATE = ?, NSICODE = ?  WHERE ABONLS = ?")) {

            ps.setDate(1, date);
            ps.setString(2, nsiCode);
            ps.setString(3, accountNumber);
            ps.executeUpdate();
        }
    }

    /**
     * Метод, проверяет, помечен абонент на удаление или нет.
     *
     * @param accountNumber номер ЛС.
     * @return true - если абонент помечен, false - абонент не помечен на удаление.
     * @throws SQLException
     */
    private boolean isAccountForRemove(String accountNumber, Connection connection) throws SQLException {

        String sqlRequest = "SELECT ABONLS FROM ACCOUNT_FOR_REMOVE WHERE ABONLS = '" + accountNumber + "' AND CLOSED = FALSE";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlRequest)) {
            resultSet.next();
            String result = resultSet.getString(1);
            return !(result == null || result.isEmpty());
        }
    }


    /**
     * Метод, проверяет, если строка пустая или null, то вернет 0.
     *
     * @param textForNumber - строка.
     * @return String - преобразованная строка в "0", если она пустая.
     */
    private String checkZero(String textForNumber) {
        if (textForNumber == null || textForNumber.isEmpty()) {
            return "0";
        } else
            return textForNumber;
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
