package ru.progmatik.java.pregis.connectiondb.grad.account;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.house_management.ImportAccountRequest;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.exception.PreGISArgumentNotFoundFromBaseException;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.model.AbonentInformation;
import ru.progmatik.java.pregis.model.AnswerYesOrNo;
import ru.progmatik.java.pregis.model.DocumentType;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.other.ResourcesUtil;

import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import static ru.progmatik.java.pregis.other.OtherFormat.checkZero;

/**
 * Класс, обращается в БД ГРАД, за данными о лицевых счетах.
 */
public final class AccountGRADDAO {

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
    private static SimpleDateFormat dateFromSQL = new SimpleDateFormat("yyyy-MM-dd");

    public AccountGRADDAO(AnswerProcessing answerProcessing) throws SQLException {

        if(answerProcessing != null) {
            this.answerProcessing = answerProcessing;
        }else{
            this.answerProcessing = new AnswerProcessing();
        }

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
    private static XMLGregorianCalendar getCalendar(final Date date) {

        return OtherFormat.getDateForXML(date);
    }

    /**
     * Метод, получает от метода "createExcel" ид дома и таблицу Excel.
     * Добавляет информацию на лист «Основные сведения».
     *
     * @param houseID - ИД адрес дома.
     */
    public static ArrayList<AbonentInformation> getBasicInformation(final int houseID, final Connection connection, AnswerProcessing answerProc) throws SQLException, PreGISException {

        final String sqlRequest = "SELECT * FROM EX_GIS_LS1(" + houseID + "," + ResourcesUtil.instance().getCompanyGradId() + ")";
        ArrayList<AbonentInformation> listBasic = new ArrayList<>();

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlRequest)) {
            while (resultSet.next()) {

                final AbonentInformation bi = new AbonentInformation();

                final String[] arrayData = getAllData(resultSet.getString(1));

                try {
                    bi.setGradID(Integer.valueOf(arrayData[0]));
                    bi.setNumberLS(arrayData[1]);
                    bi.setTypeLS(arrayData[3]);
                    bi.setEmployer(AnswerYesOrNo.getAnswer(arrayData[4]));
                    bi.setSurname(arrayData[5]);
                    if(!arrayData[6].isEmpty()) {bi.setName(arrayData[6]);}
                    if(!arrayData[7].isEmpty()) {bi.setMiddleName(arrayData[7]);}
// закомментили. при передаче СНИЛС ругается на паспорт. но СНИЛСы у нас не подтверждены, поэтому ну их                    bi.setSnils(arrayData[8].replaceAll("-", "").replaceAll(" ", ""));
                    bi.setTypeDocument(DocumentType.getTypeDocument(arrayData[9]));
                    bi.setNumberDocumentIdentity(arrayData[10]);
                    bi.setSeriesDocumentIdentity(arrayData[11]);
                    if (arrayData[12] != null) {
                        if (!arrayData[12].isEmpty()) {
                            try {
                                bi.setDateDocumentIdentity(dateFromSQL.parse(arrayData[12]));
                            } catch (ParseException e) {
                                LOGGER.error("ExtractSQL: Не верный формат для ячейки.", e);
                                e.printStackTrace();
                            }
                        }
                    }
                    bi.setOgrnOrOgrnip(arrayData[13]);
                    bi.setKpp(arrayData[14]);
                    bi.setTotalArea(Double.valueOf(checkZero(arrayData[16])));
                    bi.setLivingSpace(Double.valueOf(checkZero(arrayData[17])));
                    bi.setHeadtedArea(Double.valueOf(checkZero(arrayData[18])));
                    bi.setAmountLiving(Integer.valueOf(checkZero(arrayData[19])));
                    if(arrayData.length > 20) {
                        bi.setPremisesGUID(arrayData[20]);
                        bi.setLivingRoomGUID(arrayData[21]);
                        bi.setAccountGUID(arrayData[22]);
                        bi.setUnifiedAccountNumber(arrayData[23]);
                        bi.setOrgVersionGUID(arrayData[24]);
                    }
                    if(arrayData.length > 25) bi.setServiceID(arrayData[25]);

                    if(arrayData.length > 26) bi.setSharePay(Integer.valueOf(arrayData[26]));
                    else bi.setSharePay(Integer.valueOf("100"));


                } catch (NumberFormatException e) {
                    LOGGER.error("ExtractSQL: Не верный формат для ячейки.", e);
                }
                if(bi.getSurname().isEmpty() && bi.getOgrnOrOgrnip().isEmpty()){
                    answerProc.sendErrorToClientNotException("У абонента Град с ЛС " + bi.getNumberLS() + " не указана фамилия и отсутствует ОГРН, исправьте данные в Град");
                }
                listBasic.add(bi);
            }
        }
        if (listBasic.size() == 0) return null; // если нет ЛС возвращаем null.
//DEBUG        answerProcessing.sendInformationToClientAndLog("SELECT * FROM EX_GIS_LS1 returns " + listBasic.size() + " records", LOGGER);
        return listBasic;
    }


    /*
     * Метод, формирует мапу с помещениями, ключ - лицевой счет.
     *
     * @param houseID - ИД адрес дома.
     * @param connection - соединение с БД
     */
/*
    public static LinkedHashMap<String, Room> getLsRoomsMaps(final int houseID, final Connection connection) throws ParseException, SQLException {

        final Integer columnIndex = 2; // column with API data format
        final String sqlRequest = "SELECT * FROM EX_GIS_LS2(" + houseID + ")";
        final LinkedHashMap<String, Room> listRooms = new LinkedHashMap<>();

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlRequest)) {
            while (resultSet.next()) {

//                Бывают попадаются null
                if (resultSet.getString(columnIndex) == null) continue;

                final Room room = new Room();

                try {
                    final String[] arrayData = OtherFormat.getAllDataFromString(resultSet.getString(columnIndex));

                    room.setAddress(arrayData[2]);
                    room.setFias(arrayData[3]);
                    room.setNumberAppart(arrayData[5]);
                    room.setNumberRoom(arrayData[6]);
                    //room.setIdSpaceGISJKH(arrayData[8]);
// перенесено в абонента                    room.setSharePay(Integer.valueOf(checkZero(arrayData[8])));
                    room.setAbonId(Integer.valueOf(checkZero(arrayData[0])));
                    room.setNumberLS(arrayData[1]);
//                    room.setAccountGUID(arrayData[9]); // Саша должен добавить
//                    room.setCompany(); // указать статус абонента, true - если юр.лицо, false - если физ.лицо.
                    listRooms.put(room.getNumberLS(), room);
                } catch (NumberFormatException e) {
                    LOGGER.error("ExtractSQL: Не верный формат для ячейки.", e);
                }

            }
        }
        if (listRooms.size() == 0) return null; // если нет ЛС возвращаем null.
//DEBUG        answerProcessing.sendInformationToClientAndLog("SELECT * FROM EX_GIS_LS2 returns " + listRooms.size() + " records", LOGGER);
        return listRooms;
    }
*/

    /**
     * Метод, формирует Map ЛС из БД ГРАД.
     * Ключ - ЛС, значение - класс Account пригодный для импорта ЛС в ГИС ЖКХ.
     *
     * @param houseID ИД дома в БД ГРАД.
     * @throws ParseException может возникнуть ошибка при импорте из БД числа кол-во проживающих.
     * @throws SQLException   возможны ошибки БД.
     */
    public LinkedHashMap<AbonentInformation, ImportAccountRequest.Account> getAccountMapFromGrad(final int houseID,
                                                                                                 final Connection connection)
            throws ParseException, SQLException, PreGISException {
        LinkedHashMap<AbonentInformation, ImportAccountRequest.Account> mapAccount = new LinkedHashMap<>();
/*        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Формирую данные...");
        final ArrayList<AbonentInformation> basicInformationList = getBasicInformation(houseID, connection);

        LinkedHashMap<String, Room> roomsList = getLsRoomsMaps(houseID, connection);

        //        final ArrayList<Room> roomsList = getRooms(houseID, connection);
        final ReferenceNSI nsi = new ReferenceNSI(answerProcessing);
//        ExportOrgRegistry orgRegistry = new ExportOrgRegistry(answerProcessing);


        if (basicInformationList == null || roomsList == null) {
            answerProcessing.sendInformationToClientAndLog("Не найдены лицевые счета для дома с ИД: " + houseID + ".", LOGGER);
            return mapAccount;
        }

        for (AbonentInformation basicInformation : basicInformationList) {
            int count = 0;
            // for (int i = 0; i < roomsList.size(); i++) {

//                Формируем объект пригодный для импорта в ГИС ЖКХ.
//                По необходимости в дальнейшем нужно присвоить перед отправкой:
//                - если новый счет указать TransportGUID, удалить если есть AccountGUID.
//                - если счет к закрытию указать AccountGUID и isClosed.

            Room fRoom = roomsList.get(basicInformation.getNumberLS());
            String premisesGUID = null;
            String livingRoomGUID = null;
            if (fRoom != null) {
                    // заранее получаем premisesGUID и livingRoomGUID, если не заданы - вообще не добавляем объект, потому что вызовет ошибку
                premisesGUID = getBuildingIdentifiersFromBase(basicInformation.getGradID(), "PREMISESGUID", connection);
                livingRoomGUID = getBuildingIdentifiersFromBase(basicInformation.getGradID(), "LIVINGROOMGUID", connection);

                if (premisesGUID == null && livingRoomGUID == null) {
                    answerProcessing.sendInformationToClientAndLog("Для абонента ГРАД ИД "
                            + basicInformation.getNumberLS() + " не найдено идентификатора помещения или комнаты", LOGGER);
                }
            }
            if (fRoom != null && !(premisesGUID == null && livingRoomGUID == null)) {
// debug                    answerProcessing.sendMessageToClient(basicInformation.getNumberLS() + " found!");
                ImportAccountRequest.Account account = new ImportAccountRequest.Account();
//                    account.setCreationDate(OtherFormat.getDateNow()); // может без даты можно? добавил при отправки нового счета
                account.setLivingPersonsNumber(basicInformation.getAmountLiving());
                account.setTotalSquare(new BigDecimal(basicInformation.getTotalArea()).setScale(2, BigDecimal.ROUND_DOWN));
                account.setResidentialSquare(new BigDecimal(basicInformation.getLivingSpace()).setScale(2, BigDecimal.ROUND_DOWN));
                if (basicInformation.getHeadtedArea() > 0.0) {
                    account.setHeatedArea(new BigDecimal(basicInformation.getHeadtedArea()).setScale(2, BigDecimal.ROUND_DOWN));
                }
//                    account.setClosed(); // проверить, если в ГИС ЖКХ есть, а в БД ГРАД нет, то установить в ExportAccountData.
                AccountType.Accommodation accommodation = new AccountType.Accommodation();
                if(livingRoomGUID == null || livingRoomGUID.isEmpty()) {
                    accommodation.setPremisesGUID(premisesGUID);
                }else {
//                    accommodation.setFIASHouseGuid(roomsList.get(i).getFias()); // Выдаё ошибку, по описанию можно выбирать.
                    accommodation.setLivingRoomGUID(livingRoomGUID); // Идентификатор комнаты
                }
                accommodation.setSharePercent(new BigDecimal(fRoom.getSharePay()).setScale(0, BigDecimal.ROUND_DOWN));
                if(accommodation.getSharePercent() == null) accommodation.setSharePercent(new BigDecimal(100)); // если ничего не пришло - ставим 100, иначе ГИС выдает ошибку

                account.getAccommodation().add(accommodation);
//                    account.setTransportGUID();  // указывается, если ЛС добавляется в первые.

//                    if (basicInformation.getOgrnOrOgrnip() == 0) {
                if (basicInformation.getSurname() != null && !basicInformation.getSurname().trim().isEmpty()
                        && basicInformation.getName() != null && !basicInformation.getName().trim().isEmpty()) {

//                    Сведения о плательщике
                    account.setPayerInfo(new AccountType.PayerInfo());
                    account.getPayerInfo().setInd(new AccountIndType());

                    account.getPayerInfo().getInd().setSurname(basicInformation.getSurname());

                    account.getPayerInfo().getInd().setFirstName(basicInformation.getName());

                    if (basicInformation.getMiddleName() != null) {
                        account.getPayerInfo().getInd().setPatronymic(basicInformation.getMiddleName());
                    }
//                    if (basicInformation.getSnils() != null || !basicInformation.getSnils().trim().isEmpty()) {
//                        account.getPayerInfo().getInd().setSNILS(basicInformation.getSnils().replaceAll("-", "").replaceAll(" ", ""));
//                    }


                    if (basicInformation.getOgrnOrOgrnip().isEmpty()) {

                        if (basicInformation.getNumberDocumentIdentity() != null) { // будем создавать только если есть номер документа!
                            account.getPayerInfo().getInd().setID(new ID()); // подгрузить справочник NSI 95

                            if (basicInformation.getTypeDocument() != null) {
                                account.getPayerInfo().getInd().getID().setType(nsi.getTypeDocumentNsiRef(basicInformation.getTypeDocument().getTypeDocument()));
                            } else {
                                account.getPayerInfo().getInd().getID().setType(nsi.getTypeDocumentNsiRef(DocumentType.getTypeDocument("паспорт").getTypeDocument()));
                            }

                            if (basicInformation.getNumberDocumentIdentity() != null && !basicInformation.getNumberDocumentIdentity().isEmpty()) {
                                account.getPayerInfo().getInd().getID().setNumber(basicInformation.getNumberDocumentIdentity());
                            } else {
                                account.getPayerInfo().getInd().getID().setNumber("000000");
                            }


                            if (basicInformation.getSeriesDocumentIdentity() != null && !basicInformation.getSeriesDocumentIdentity().isEmpty()) {
                                account.getPayerInfo().getInd().getID().setSeries(basicInformation.getSeriesDocumentIdentity());
                            } else {
                                account.getPayerInfo().getInd().getID().setSeries("0000");
                            }

                            if (basicInformation.getDateDocumentIdentity() != null) {
                                account.getPayerInfo().getInd().getID().setIssueDate(getCalendar(basicInformation.getDateDocumentIdentity()));
                            } else {
                                account.getPayerInfo().getInd().getID().setIssueDate(OtherFormat.getDateForXML((new SimpleDateFormat("dd.MM.yyyy")).parse("01.01.2017")));
                            }
                        }

                    } else {
//                        Есть возможность указать на VersionGUID из реестра организаций, вот только где его взять?
                            ExportOrgRegistryRequest.SearchCriteria criteria = new ExportOrgRegistryRequest.SearchCriteria();

                            if (basicInformation.getOgrnOrOgrnip().length() < 10000000000000L && basicInformation.getOgrnOrOgrnip() > 999999999999L) {
                                criteria.setOGRN(String.valueOf(basicInformation.getOgrnOrOgrnip()));
                            } else if (basicInformation.getOgrnOrOgrnip() > 99999999999999L) {
                                criteria.setOGRNIP(String.valueOf(basicInformation.getOgrnOrOgrnip()));
                            }
//                            ExportOrgRegistryResult result = orgRegistry.callExportOrgRegistry(orgRegistry.getExportOrgRegistryRequest(criteria));
//                            account.getPayerInfo().setOrg(new RegOrgVersionType());
//                            account.getPayerInfo().getOrg().setOrgVersionGUID(result.getOrgData().get(0).getOrgVersion().getOrgVersionGUID());
                        }
                    }

                    if (basicInformation.getEmployer() == AnswerYesOrNo.YES) {
                        account.getPayerInfo().setIsRenter(true); // выдаёт ошибку если указать false
                    }
                }

                account.setAccountNumber(basicInformation.getNumberLS());
                account.setAccountGUID(getAccountGUIDFromBase(fRoom.getAbonId(), connection));  // добавляется, если счет будет изменен или закрыт, если этого не будет перед отправкой затереть.
                setIsAccount(account);
                account.setCreationDate(OtherFormat.getDateNow());

                //if (account.getAccountGUID() != null && !account.getAccountGUID().equals("")) {
                    mapAccount.put(basicInformation, account);

                    count++;
                //}
//                    roomsList.remove(i);
                // i--;
//                }
            }
            if (count == 1) {
                continue;
            } else if (count == 0) {
                String notFound = "Отсутствует";

                StringBuilder builder = new StringBuilder();
                builder.append(basicInformation.getSurname());
                builder.append(" ");
                builder.append(basicInformation.getName());
                builder.append(" ");
                builder.append(basicInformation.getMiddleName());
                builder.append(" ");

                String fio = builder.toString().replaceAll("null", "").trim();
                fio = fio.isEmpty() ? notFound : fio;

                if (basicInformation.getTypeDocument() == null || basicInformation.getTypeDocument().getTypeDocument() == null || basicInformation.getNumberDocumentIdentity() == null) {
                    answerProcessing.sendInformationToClientAndLog(String.format(
                            "\nДля абонента с ФИО: %s,\nНомер счета: %s.\nНе удалось найти соответствие \"Помещение\" в базе данных.",
                            fio, basicInformation.getNumberLS()), LOGGER);
                } else {
                    answerProcessing.sendInformationToClientAndLog(String.format(
                            "\nДля абонента с ФИО: %s,\nНомер счета: %s,\nТип документа: %s,\nНомер документа: %s.\nНе удалось найти соответствие \"Помещение\" в базе данных.",
                            fio, basicInformation.getNumberLS(), basicInformation.getTypeDocument().getTypeDocument(), basicInformation.getNumberDocumentIdentity()), LOGGER);
                }

            } else if (count >= 2) {
                answerProcessing.sendInformationToClientAndLog("Для счета - "
                        + basicInformation.getNumberLS() + " найдено более одного соответствия в базе данных.", LOGGER);
            }
        }*/
        return mapAccount;
    }



    /**
     * Метод, извлекает идентификаторы помещений.
     *
     * @param abonId ид абонента в БД ГРАД.
     * @param identifier название идентификатора могут быть PREMISESGUID, PREMISESUNIQNUM, LIVINGROOMGUID, ROOMUNIQNUMBER.
     * @return идентификатор.
     * @throws SQLException
     */
    public static String getBuildingIdentifiersFromBase(Integer abonId, String identifier, Connection connectionGRAD) throws SQLException, PreGISException {

        String sqlResult;
        String sqlRequest = "{EXECUTE PROCEDURE EX_GIS_ID(?, NULL , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ?, NULL, ?)}";

        try (CallableStatement cstmt = connectionGRAD.prepareCall(sqlRequest)) { // После использования должны все соединения закрыться
            cstmt.setInt(1, abonId);
            cstmt.setString(2, identifier);
            cstmt.setInt(3, ResourcesUtil.instance().getCompanyGradId());
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
     * @param unifiedAccountNumber уникальный номер ЛС, присвоенный ГИС ЖКХ.
     * @return true - идентификатор успешно добавлен, false - идентификатор не удалось добавить.
     * @throws SQLException
     * @throws PreGISException
     * @throws ParseException
     */
    public static boolean setAccountGuidAndUniqueNumber(Integer houseId, String accountNumber,
                                              String accountGUID, String unifiedAccountNumber, String serviceId, Connection connection, AnswerProcessing answerProc) throws SQLException, PreGISException, ParseException {

        Integer abonentId = getAbonentIdFromGrad(accountNumber, connection);

        if (abonentId != null) {
            // ИД дома(:building_id),
            // ИД абонента(:abon_id),
            // ИД прибора учета(:meter_id),
            // уникальный идентификатор ГИС ЖКХ(:gis_id),
            // уникальный идентификатор лицевого счета ГИС ЖКХ(:gis_ls_id)
            String sqlRequest = "{EXECUTE PROCEDURE EX_GIS_ID(?, NULL , NULL, NULL, ?, ?, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ?, ?)}";
            try (CallableStatement cstmt = connection.prepareCall(sqlRequest)) {
//                answerProcessing.sendMessageToClient(String.format("abonentId: %s:\n" +
//                                "accountGUID: %s\n" +
//                                "unifiedAccountNumber: %s.",
//                        abonentId, accountGUID, unifiedAccountNumber));
                cstmt.setInt(1, abonentId);
                cstmt.setString(2, accountGUID);
                cstmt.setString(3, unifiedAccountNumber);
                cstmt.setString(4, serviceId);
                cstmt.setInt(5, ResourcesUtil.instance().getCompanyGradId());
                cstmt.executeUpdate();
//                int codeReturn = cstmt.executeUpdate();
//                System.err.println("Apartment code return: " + codeReturn);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.info(String.format("SQL request: %s", sqlRequest));
                    LOGGER.info(String.format("set attribute for LS (%s) - abonID: %s; AccountGUID: %s; AccountUniqueNumber: %s.",
                            accountNumber, abonentId, accountGUID, unifiedAccountNumber));
                }
                if (accountGUID != null && !accountGUID.equalsIgnoreCase(getAccountGUIDFromBase(abonentId, connection))) {
                    answerProc.sendErrorToClientNotException(String.format("Идентификатор %s не занесен в БД ГРАД для ЛС %s", accountGUID, accountNumber));
                    LOGGER.error(String.format("Идентификатор %s не занесен в БД ГРАД для ЛС %s", accountGUID, accountNumber));
                    return false;
                }
            }
        } else {
            throw new PreGISException("setApartmentUniqueNumber(): Не удалось найти ID абонента в БД ГРАД.");
        }
        return true;
    }

    /**
     * Метод, добавляет номер версии организации - квартиросъемщика в БД ГРАДа.
     *
     * @param abonentId             ид абонента в БД ГРАД.
     * @param orgVersionGUID       номер версии организации - квартиросъемщика.
     * @return true - идентификатор успешно добавлен, false - идентификатор не удалось добавить.
     * @throws SQLException
     * @throws PreGISException
     */
    public static boolean setOrgVersionGUID(Integer abonentId, String orgVersionGUID, Connection connection, AnswerProcessing answerProc) throws PreGISException{

        if (abonentId != null) {
            String sqlRequest = "{EXECUTE PROCEDURE EX_GIS_ID(?, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,?)}";
            try (CallableStatement cstmt = connection.prepareCall(sqlRequest)) {
                cstmt.setInt(1, abonentId);
                cstmt.setString(2, orgVersionGUID);
                cstmt.executeUpdate();
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.info(String.format("SQL request: %s", sqlRequest));
                    LOGGER.info(String.format("set attribute for abonID: %s; orgVersionGUID: %s;",
                            abonentId, orgVersionGUID));
                }
            }catch (SQLException e) {
                answerProc.sendInformationToClientAndLog(String.format("Для абонента ИД %s не удалось установить идентификатор юр.лица в Град", abonentId), LOGGER);
            }
        } else {
            throw new PreGISException("setApartmentUniqueNumber(): Не удалось найти ID абонента в БД ГРАД.");
        }
        return true;
    }
    /**
     * Метод, получает уникальный идентификатор ЛС из БД ГРАДА.
     * @param abonId идентификатор абонента в БД ГРАД.
     * @param connection подключение к БД ГРАД.
     * @return Уникальный идентификатор ГИС ЖКХ.
     * @throws SQLException любые ошибки полученные во время работ с базой данных.
     */
    public String getUnifiedAccountNumber(Integer abonId, Connection connection) throws SQLException, PreGISException {

        return getBuildingIdentifiersFromBase(abonId, "ACCOUNTUNIQNUM", connection);
    }

    /**
     * Метод, получает
     *
     * @param abonId ид абонента в БД ГРАД.
     * @return AccountGUID идентификатор ЛС в ГИС ЖКХ.
     * @throws SQLException
     */
    public static String getAccountGUIDFromBase(Integer abonId, Connection connection) throws SQLException, PreGISException {

        String sqlResult;
        String sqlRequest = "{EXECUTE PROCEDURE EX_GIS_ID(?, NULL , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ?, NULL, ?)}";

        try (CallableStatement cstmt = connection.prepareCall(sqlRequest)) { // После использования должны все соединения закрыться
            cstmt.setInt(1, abonId);
            cstmt.setString(2, "ACCOUNTGUID");
            cstmt.setInt(3, ResourcesUtil.instance().getCompanyGradId());
            ResultSet resultSet = cstmt.executeQuery();
            resultSet.next();
            sqlResult = resultSet.getString(1);
            resultSet.close();
            if (sqlResult == null || sqlResult.isEmpty()) {
//                throw new PreGISArgumentNotFoundFromBaseException("В БД ГРАДа не найден \"AccountGUID\"!");
                return null;
            }
        }
        return sqlResult;
    }

    /**
     * Метод, вызывает метод "getAccountGUIDFromBase", но если вернулось пустое значение вызывает ошибку.
     * @param abonId ид абонента в БД ГРАД.
     * @param connection подключение к БД ГРАД
     * @return AccountGUID идентификатор ЛС в ГИС ЖКХ.
     * @throws SQLException
     * @throws PreGISArgumentNotFoundFromBaseException
     */
    public String getAccountGUIDFromBaseWithException(Integer abonId, Connection connection) throws SQLException, PreGISArgumentNotFoundFromBaseException, PreGISException {

        String accountGUID = getAccountGUIDFromBase(abonId, connection);
        if (accountGUID == null) {
            throw new PreGISArgumentNotFoundFromBaseException("В БД ГРАДа не найден \"AccountGUID\", " +
                    "для абонента с ид в БД ГРАД " + abonId + ".");
        }
        return accountGUID;
    }

    /**
     * Метод, находит в БД ГРАД ид абонента.
     *
     * @param accountNumber номер ЛС.
     * @return ид абонента в БД ГРАД.
     * @throws ParseException
     * @throws SQLException
     */
    public static Integer getAbonentIdFromGrad(final String accountNumber, final Connection connection) throws ParseException, SQLException {
        if(accountNumber == null || accountNumber.equals("")){
            return 0;
        }
        Integer sqlResult;
        String sqlRequest = "select a.id from abonents a where a.g_licschet = '" + accountNumber + "'";

        try (Statement cstmt = connection.createStatement()) { // После использования должны все соединения закрыться
            ResultSet resultSet = cstmt.executeQuery(sqlRequest);
            resultSet.next();
            sqlResult = resultSet.getInt(1);
            resultSet.close();
        }
        return sqlResult;
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
    public static boolean addAccountForRemove(final Integer houseId, final String accountNumber, final Connection connectionLocal,
                                       final Connection connectionGrad, AnswerProcessing answerProc) throws ParseException, SQLException, PreGISException {

        String fio = "";


        final ArrayList<AbonentInformation> abonentInformation = getBasicInformation(houseId, connectionGrad, answerProc);
        if (abonentInformation != null)
            for (AbonentInformation information : abonentInformation) {
                if (accountNumber.equals(information.getNumberLS())) {
                    fio = information.getSurname() + " " + information.getName() + " " + information.getMiddleName();
                    final Integer abonId = getAbonentIdFromGrad(accountNumber, connectionGrad);
                    final java.sql.Date dateEnd = getAccountEndDate(abonId, connectionGrad);
                    setAccountForRemove(abonId, accountNumber, fio, dateEnd, connectionLocal, answerProc);
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
    private static void setAccountForRemove(final Integer abonId, final String accountNumber, final String fio,
                                     final java.sql.Date dateEnd, final Connection connection, AnswerProcessing answerProc) throws SQLException {

        if (!isAccountForRemove(accountNumber, connection)) {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO ACCOUNT_FOR_REMOVE(ABONID, ABONLS, FIO, ENDDATE, CLOSED) VALUES(?, ?, ?, ?, ?)")) {

                ps.setInt(1, abonId);
                ps.setString(2, accountNumber);
                ps.setString(3, fio);
                ps.setDate(4, dateEnd);
                ps.setBoolean(5, false);
                ps.executeUpdate();
                answerProc.sendMessageToClient("Лицевой счет №" + accountNumber + " помечен на удаление.");
            }
        }
    }

    private static java.sql.Date getAccountEndDate(final Integer abonId, final Connection connection) throws SQLException {

        final String sqlRequest = "select edate from abonents where id = ? and is_active = 0";
        java.sql.Date date = null;

        try (PreparedStatement statement = connection.prepareStatement(sqlRequest)) {
            statement.setInt(1, abonId);
            final ResultSet resultSet = statement.executeQuery();
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
    public void updateAccountForRemove(final String accountNumber, final java.sql.Date date,
                                       final String nsiCode, final Connection connection) throws SQLException {

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
    private static boolean isAccountForRemove(final String accountNumber, final Connection connection) throws SQLException {

        final String sqlRequest = "SELECT ABONLS FROM ACCOUNT_FOR_REMOVE WHERE ABONLS = '" + accountNumber + "' AND CLOSED = TRUE";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlRequest)) {
            return resultSet.next();
        }
    }






    /**
     * Метод, задаёт абоненту тип компании (УО, РСО, РКЦ и т.д.)
     * @param account данные абонента.
     * @throws PreGISException возможна ошибка, если не будет найдена роль компании в файле "application.properties".
     */
    public static void setIsAccount(final ImportAccountRequest.Account account) throws PreGISException {

        if (ResourcesUtil.instance().getCompanyRole() != null && ResourcesUtil.instance().getCompanyRole().equalsIgnoreCase("RSO")) {
            account.setIsRSOAccount(true); // Если РСО
        } else if (ResourcesUtil.instance().getCompanyRole() != null && ResourcesUtil.instance().getCompanyRole().equalsIgnoreCase("RCA")) {
            account.setIsRCAccount(true); // Если РКЦ
        } else {
            account.setIsUOAccount(true); // По-умолчанию УО.
        }
    }

    /**
     * Метод, возвращает обработанную строку в массиве с данными.
     *
     * @param data - строка с данными.
     * @return String - массив данных.
     */
    private static synchronized String[] getAllData(String data) {

        data = data + "|-1-"; // Если последний параметр пустой, то он в массив не попадет,
        // возникнут ошибки на ссылки на индексы массива.

        return data.split(Pattern.quote("|"));
    }

}
