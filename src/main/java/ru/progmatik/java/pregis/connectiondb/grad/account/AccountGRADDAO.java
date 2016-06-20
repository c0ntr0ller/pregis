package ru.progmatik.java.pregis.connectiondb.grad.account;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.AnswerYesOrNo;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.BasicInformation;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.DocumentType;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.Rooms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Класс, обращается в БД ГРАД, за данными о лицевых счетах.
 */
public class AccountGRADDAO {

    private static final Logger LOGGER = Logger.getLogger(AccountGRADDAO.class);
    private SimpleDateFormat dateFromSQL = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Метод, получает от метода "createExcel" ид дома и таблицу Excel.
     * Добавляет информацию на лист «Основные сведения».
     *
     * @param houseID - ИД адрес дома.
     */
    private void setBasicInformation(int houseID) throws SQLException {

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
    }

    /**
     * Метод, получает от метода "createExcel" адрес дома и таблицу Excel.
     * Добавляет информацию на лист "Помещение".
     *
     * @param houseID - ИД адрес дома.
     */
    private void setRooms(int houseID) throws ParseException, SQLException {

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

                } catch (NumberFormatException e) {
                    LOGGER.error("ExtractSQL: Не верный формат для ячейки.", e);
                }
                listRooms.add(rooms);
            }
        } finally {
            ConnectionBaseGRAD.instance().close();
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
