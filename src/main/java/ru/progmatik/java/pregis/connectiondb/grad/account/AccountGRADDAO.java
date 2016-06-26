package ru.progmatik.java.pregis.connectiondb.grad.account;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.ID;
import ru.gosuslugi.dom.schema.integration.services.house_management.AccountIndType;
import ru.gosuslugi.dom.schema.integration.services.house_management.AccountType;
import ru.gosuslugi.dom.schema.integration.services.house_management.ExportHouseResult;
import ru.gosuslugi.dom.schema.integration.services.house_management.ImportAccountRequest;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.AnswerYesOrNo;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.BasicInformation;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.DocumentType;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.Rooms;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSI;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

/**
 * Класс, обращается в БД ГРАД, за данными о лицевых счетах.
 */
public class AccountGRADDAO {

    private static final Logger LOGGER = Logger.getLogger(AccountGRADDAO.class);
    private final AnswerProcessing answerProcessing;
    private SimpleDateFormat dateFromSQL = new SimpleDateFormat("yyyy-MM-dd");

    public AccountGRADDAO(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    /**
     * Метод, приводит дату в нужный формат.
     * @param date дата для обработки.
     * @return дата в пригодном формате.
     */
    private static XMLGregorianCalendar getCalendar(Date date) {

        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        XMLGregorianCalendar dateXml = null;

        try {
            dateXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return dateXml;
    }

    public void getAccountList(int houseID, ExportHouseResult houseData) {



    }

    /**
     * Метод, получает от метода "createExcel" ид дома и таблицу Excel.
     * Добавляет информацию на лист «Основные сведения».
     *
     * @param houseID - ИД адрес дома.
     */
    private ArrayList<BasicInformation> getBasicInformation(int houseID) throws SQLException {

        String sqlRequest = "SELECT * FROM EX_GIS_LS1(" + houseID + ")";
        ArrayList<BasicInformation> listBasic = new ArrayList<>();

        try (Statement statement = ConnectionBaseGRAD.instance().getConnection().createStatement(); ResultSet resultSet = statement.executeQuery(sqlRequest)) {
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
        } finally {
            ConnectionBaseGRAD.instance().close();
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
    private ArrayList<Rooms> getRooms(int houseID) throws ParseException, SQLException {

        String sqlRequest = "SELECT * FROM EX_GIS_LS2(" + houseID + ")";
        ArrayList<Rooms> listRooms = new ArrayList();

        try (Statement statement = ConnectionBaseGRAD.instance().getConnection().createStatement(); ResultSet resultSet = statement.executeQuery(sqlRequest)) {
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
//                    rooms.setAccountGUID(arrayData[8]); // Саша должен добавить
//                    rooms.setCompany(); // указать статус абонента, true - если юр.лицо, false - если физ.лицо.

                } catch (NumberFormatException e) {
                    LOGGER.error("ExtractSQL: Не верный формат для ячейки.", e);
                }
                listRooms.add(rooms);
            }
        } finally {
            ConnectionBaseGRAD.instance().close();
        }
        if (listRooms.size() == 0) return null; // если нет ЛС возвращаем null.

        return listRooms;
    }

    /**
     * Метод, формирует Map ЛС из БД ГРАД.
     * Ключ - ЛС, значение - класс Account пригодный для импорта ЛС в ГИС ЖКХ.
     * @param houseID ИД дома в БД ГРАД.
     * @throws ParseException может возникнуть ошибка при импорте из БД числа кол-во проживающих.
     * @throws SQLException возможны ошибки БД.
     */
    private void convertAccount(int houseID, ExportHouseResult houseData) throws ParseException, SQLException, PreGISException {

        ArrayList<BasicInformation> basicInformationList = getBasicInformation(houseID);
        ArrayList<Rooms> roomsList = getRooms(houseID);
        ReferenceNSI nsi95 = new ReferenceNSI(answerProcessing);

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
                    account.setTotalSquare(new BigDecimal(basicInformation.getTotalArea()));
                    account.setResidentialSquare(new BigDecimal(basicInformation.getLivingSpace()));
                    account.setHeatedArea(new BigDecimal(basicInformation.getHeadtedArea()));
//                    account.setClosed(); // проверить, если в ГИС ЖКХ есть, а в БД ГРАД нет, то установить в ExportAccountData.
                    AccountType.Accommodation accommodation = new AccountType.Accommodation();
                    accommodation.setPremisesGUID(roomsList.get(i).getIdSpaceGISJKH()); // временно
                    accommodation.setFIASHouseGuid(roomsList.get(i).getFias());
//                    accommodation.setLivingRoomGUID(); // Идентификатор комнаты
                    accommodation.setSharePercent(new BigDecimal(roomsList.get(i).getSharePay()));
                    account.getAccommodation().add(accommodation);
//                    account.setTransportGUID();  // указывается, если ЛС добавляется в первые.

//                    Сведения о платильщике
                    account.setPayerInfo(new AccountType.PayerInfo());
                    if (basicInformation.getOgrnOrOgrnip() == 0) {
                        account.getPayerInfo().setInd(new AccountIndType());
                        account.getPayerInfo().getInd().setSurname(basicInformation.getSurname());
                        account.getPayerInfo().getInd().setFirstName(basicInformation.getName());
                        account.getPayerInfo().getInd().setPatronymic(basicInformation.getMiddleName());
//                        account.getPayerInfo().getInd().setSex(); // не указан
//                        account.getPayerInfo().getInd().setDateOfBirth(); // не указан
                        account.getPayerInfo().getInd().setSNILS(basicInformation.getSnils());

                        account.getPayerInfo().getInd().setID(new ID()); // подгрузить справочник NSI 95
                        account.getPayerInfo().getInd().getID().setType(nsi95.getTypeDocumentNsiRef(basicInformation.getTypeDocument().getTypeDocument()));
                        account.getPayerInfo().getInd().getID().setNumber(basicInformation.getNumberDocumentIdentity());
                        account.getPayerInfo().getInd().getID().setSeries(basicInformation.getSeriesDocumentIdentity());
                        account.getPayerInfo().getInd().getID().setIssueDate(getCalendar(basicInformation.getDateDocumentIdentity()));

                    } else {
//                        Есть возможность указать на VersionGUID из реестра организаций, вот только где его взять?
//                        account.getPayerInfo().setOrg(new RegOrgVersionType());
//                        account.getPayerInfo().getOrg().setOrgVersionGUID();
                    }
                    account.getPayerInfo().setIsRenter(basicInformation.getEmployer() == AnswerYesOrNo.YES);

                    account.setAccountNumber(basicInformation.getNumberLS());
                    account.setAccountGUID(roomsList.get(i).getAccountGUID());  // добавляется, если счет будет изменен или закрыт, если этого не будет перед отправкой затереть.

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
