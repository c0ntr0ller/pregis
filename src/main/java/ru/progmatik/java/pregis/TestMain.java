package ru.progmatik.java.pregis;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import ru.gosuslugi.dom.schema.integration.bills.ImportPaymentDocumentRequest;
import ru.gosuslugi.dom.schema.integration.bills.PaymentDocumentType;
import ru.gosuslugi.dom.schema.integration.house_management.ExportHouseResult;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;
import ru.progmatik.java.pregis.connectiondb.grad.account.AccountGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.account.datasets.Rooms;
import ru.progmatik.java.pregis.connectiondb.grad.bills.PaymentDocumentGradDAO;
import ru.progmatik.java.pregis.connectiondb.grad.devices.MeteringDeviceGRADDAO;
import ru.progmatik.java.pregis.connectiondb.grad.devices.MeteringDeviceValuesGradDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.message.MessageExecutor;
import ru.progmatik.java.pregis.connectiondb.localdb.meteringdevice.MeteringDevicesDataLocalDBDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.utils.MeteringDevicesDBSearch;
import ru.progmatik.java.pregis.services.device_metering.MeteringDeviceValuesObject;
import ru.progmatik.java.web.servlets.socket.ClientService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


public class TestMain {

    private static String mes = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
            "<S:Header><ISRequestHeader xmlns=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/\" xmlns:ns10=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi-common/\" xmlns:ns11=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi/\" xmlns:ns12=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/device-metering/\" xmlns:ns13=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/organizations-registry/\" xmlns:ns14=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/licenses/\" xmlns:ns15=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/infrastructure/\" xmlns:ns16=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/fas/\" xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/inspection/\" xmlns:ns3=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/organizations-registry-common/\" xmlns:ns4=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns5=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/\" xmlns:ns6=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/services/\" xmlns:ns7=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/\" xmlns:ns8=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/payment/\" xmlns:ns9=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/bills/\"><Date>2016-03-08T23:21:56.517+06:00</Date><MessageGUID>7b824087-412b-4aa5-9b99-8afbd52bdb15</MessageGUID></ISRequestHeader></S:Header><S:Body><ns3:exportDataProviderRequest xmlns:ns3=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/organizations-registry-common/\" xmlns=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/\" xmlns:ns10=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi-common/\" xmlns:ns11=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi/\" xmlns:ns12=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/device-metering/\" xmlns:ns13=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/organizations-registry/\" xmlns:ns14=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/licenses/\" xmlns:ns15=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/infrastructure/\" xmlns:ns16=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/fas/\" xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/inspection/\" xmlns:ns4=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns5=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/\" xmlns:ns6=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/services/\" xmlns:ns7=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/\" xmlns:ns8=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/payment/\" xmlns:ns9=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/bills/\" Id=\"signed-data-container\"></ns3:exportDataProviderRequest></S:Body></S:Envelope>";

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XMLStreamException, SQLException, JAXBException, ParseException, PreGISException, SOAPException {

        try (Connection connectionGrad = ConnectionBaseGRAD.instance().getConnection()) {
            PaymentDocumentGradDAO dao = new PaymentDocumentGradDAO(new AnswerProcessing(new ClientService()));
            Calendar calendar = new GregorianCalendar();
            calendar.set(Calendar.MONTH, 4);
            calendar.set(Calendar.YEAR, 2016);

            ImportPaymentDocumentRequest.PaymentDocument document = dao.getPaymentDocument(36, null, calendar,
                    new ImportPaymentDocumentRequest.PaymentDocument(), connectionGrad);
            System.out.println(dao.getBigDecimalTwo(new BigDecimal(2555.2255545456D)));
            System.out.println(dao.getBigDecimalTwo(new BigDecimal(2555.2255545456D)).add(new BigDecimal("5")));
            System.out.println(document.getPaymentDocumentNumber());
//            System.out.println(document.getChargeInfo().get(0).getHousingService().getAccountingPeriodTotal());
            int count = 0;
            System.out.println("Size: " + document.getChargeInfo().size());
            System.out.println();
            for (PaymentDocumentType.ChargeInfo info : document.getChargeInfo()) {

                System.out.println("Count: " + count++);

                if (info.getHousingService() != null) {
                    System.out.println("Rate: " + info.getHousingService().getRate());
                    System.out.println("AccountingPeriodTotal: " + info.getHousingService().getAccountingPeriodTotal());
                    System.out.println("CalcExplanation: " + info.getHousingService().getCalcExplanation());
                    System.out.println("TotalPayable: " + info.getHousingService().getTotalPayable());
                    System.out.println("Nsi: " + info.getHousingService().getServiceType().getName());
                } else if (info.getMunicipalService() != null) {
                    System.out.println("Rate: " + info.getMunicipalService().getRate());
                    System.out.println("AccountingPeriodTotal: " + info.getMunicipalService().getAccountingPeriodTotal());
                    System.out.println("CalcExplanation: " + info.getMunicipalService().getCalcExplanation());
                    System.out.println("TotalPayable: " + info.getMunicipalService().getTotalPayable());
                    System.out.println("Nsi: " + info.getMunicipalService().getServiceType().getName());
                }
                System.out.println();
            }
            System.out.println(document.getAdvanceBllingPeriod());

//        } catch (PreGISException e) {
//            System.out.println(e.getMessage());
        }
//        System.out.println(OtherFormat.getCalendarForPaymentDocument().get(Calendar.MONTH));
//        System.out.println(OtherFormat.getCalendarForPaymentDocument().get(Calendar.YEAR));

//        PaymentDocumentRegistryDAO registryDAO = new PaymentDocumentRegistryDAO();
//        System.out.println(registryDAO.getPaymentDocumentLastNumber());

//        try (Connection connectionGrad = ConnectionBaseGRAD.instance().getConnection()) {
//
//            HouseGRADDAO houseDAO = new HouseGRADDAO();
//            HashMap<Integer, String> totalSquareMap = houseDAO.getTotalSquare(7124, connectionGrad);
//            for (Map.Entry<Integer, String> entry : totalSquareMap.entrySet()) {
//                System.out.println("AbonID: " + entry.getKey() + " total: " + entry.getValue());
//            }
//
//        }

//        ExportHouseRequest request = new ExportHouseRequest();
//        String version = request.getVersion();
//        request.setVersion("10.0.1.2");
//        System.out.println(request.getVersion());
//        System.out.println(version);

//        try (Connection connection = ConnectionBaseGRAD.instance().getConnection()) {
//            ServicesGisJkhForGradDAO gradDAO = new ServicesGisJkhForGradDAO();
//            gradDAO.autoAllServicesAssociations(connection);
//        }
//        System.out.println(args.length > 0 ? args[0] : "No parameters");
//        getMeteringDevicesAbonIdByMeteringDeviceNumber();

//        Properties properties = ResourcesUtil.instance().getProperties();
//        System.out.println(properties.getProperty("config.cryptoPro.keyStore.password"));


//        MeteringDeviceGRADDAO graddao = new MeteringDeviceGRADDAO(new AnswerProcessing(new ClientService()), 7124);
//        System.out.println(graddao.getMeteringDeviceFromLocalBase(36, 7124, "asdasdas4646", "fdsfds"));

//        String s = "СВХ - 15.45-10";
//        String s = "СВХ15.45-10";
//        String[] array = {"СВХ - 15.45-10", "СВХ15.45-10", "СВХ-15.45-10", "СВХ- 15.45-10", "СВХ1 5.45-10", "СВХ -15.45-10"};
//
//        for (String s1 : array) {
//            parsePU(s1);
//        }

//
//        String[] split = s.split("\\d");
//        for (String s1 : split) {
//            System.out.println(s1);
//        }
//        setShutdownDefragToLocalBase(); // сжать локальную БД.
//        getTableSize(); // получить размер файлов из БД.

//        testDecimal(new BigDecimal(19271561.15566663));

//        getAllMeteringDeviceValue();

//        showIsArchiveDevice();

//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date(System.currentTimeMillis()));
//
//        System.out.println("Now: " + sdf.format(calendar.getTime()));
//
//        calendar.add(Calendar.MONTH, -8);
//
//        System.out.println("New: " + sdf.format(calendar.getTime()));

//        ReferenceNSI referenceNSI = new ReferenceNSI(new AnswerProcessing(new ClientService()));
//
//        BigDecimal one = new BigDecimal(78.0000);
//        BigDecimal two = new BigDecimal(77.0000);
//
//        MeteringDeviceValuesObject valuesObject = new MeteringDeviceValuesObject("457878", one, new Date(System.currentTimeMillis()), referenceNSI.getNsiRef("2", "Электрическая энергия"));
//        MeteringDeviceValuesObject valuesObject2 = new MeteringDeviceValuesObject("147893", two, new Date(System.currentTimeMillis()), referenceNSI.getNsiRef("2", "Электрическая энергия"));
//        System.out.println(valuesObject.getMeteringValue().compareTo(valuesObject2.getMeteringValue()));
//        System.out.println(one.compareTo(two));

//        dateTest();

//        String HTML_DIR = PageGenerator.class.getClassLoader().getResource("ru/progmatik/java/web/site/html").toExternalForm().replace("file:/", "");
//        File file = new File(HTML_DIR);
//
//        System.out.println(file.getAbsolutePath().replaceAll("\\\\", "/"));
//        System.out.println("\\");

//        getLastMessage();
//        getMeteringDeviceGradDaoPU1();

//        showTime();

//        getArrayCount();

//        getDoubleAbonId();

//        ArrayList<String> list = new ArrayList<String>();
//
//        for (int i = 0; i <= 78; i++) {
//            list.add(String.valueOf(i));
//        }
//
//        int count = 0;
//
//        while (count < list.size()) {
//
//            if (count + 20 > list.size()) {
//                showList(list.subList(count, list.size()));
//                count += 20;
//            } else {
//                showList(list.subList(count, count += 20));
//            }
//
//        }

//        getImportResult();
//        try (Connection connection = ConnectionBaseGRAD.instance().getConnection()) {
//            MeteringDeviceGRADDAO graddao = new MeteringDeviceGRADDAO(new AnswerProcessing(new ClientService()), 7124);
//            ReferenceNSI nsi = new ReferenceNSI(new AnswerProcessing(new ClientService()));
//            ArrayList<String[]> exGisPu1 = graddao.getExGisPu1(7124, connection);
//            for (String[] strings : exGisPu1) {
////                for (String string : strings) {
////                    System.out.print(string);
////                    System.out.print(" : ");
////                }
//                System.out.println(nsi.getNsiRef("16", strings[16].split(" ")[1]).getGUID());
//                System.out.println(strings[18]);
//            }
//        }
//        SimpleDateFormat dateFromSQL = new SimpleDateFormat("yyyy-MM-dd");
//        String temp = null;
//
//        OtherFormat.getDateForXML(dateFromSQL.parse(temp));


//        getXML();

//        Connection connection = ConnectionBaseGRAD.instance().getConnection();
//        MeteringDeviceGRADDAO graddao = new MeteringDeviceGRADDAO(new AnswerProcessing(new ClientService()));
//        LinkedHashMap<Integer, String[]> indMap = graddao.getExGisIpuIndMap(7124, connection);
//        for (Map.Entry<Integer, String[]> entry : indMap.entrySet()) {
//            System.out.println(entry.getKey() + " : " + entry.getValue()[5]);
//        }


//        graddao.executor("EXECUTE PROCEDURE EX_GIS_PU1(7124)", connection);
//        getList(connection).forEach(System.out::println);
//        connection.close();


//        return executor.execQuery("select * from users where user_name='" + name + "'", result -> {
//            result.next();
//            return result.getLong(1);
//        });
//        while (resultSet.next()) {
//            System.out.println(resultSet.getString(1));
//        }

//        connection.close();
//
//        ConnectionBaseGRAD.instance().close();
//
//        connection = ConnectionBaseGRAD.instance().getConnection();
//        connection.close();

//        String snils = "<ns2:SNILS/>";
//        snils = snils.replaceAll("^<\\w?\\w?\\w?\\W?SNILS/>$", "Yes");
//
//        System.out.println(snils);

//        ReferenceDownloadNSIDataSet dataSet = new ReferenceDownloadNSIDataSet("50", "Мама мыла раму");
//        ReferenceDownloadNSIDataSet dataSet2 = new ReferenceDownloadNSIDataSet("50", "Мама мыла раму");
//
//        System.out.println(dataSet.hashCode() + " : " + dataSet2.hashCode());


//        ReferenceNSI95DAO dao = new ReferenceNSI95DAO();
//        ArrayList<ReferenceItemDataSet> allItems = dao.getAllItems();
//        for (ReferenceItemDataSet item : allItems) {
//            System.out.println(item);
//        }


//        System.out.println(ConnectionDB.instance().tableExist("SPR_NSI95"));
//        System.out.println(DocumentType.BIRTH_CERTIFICATE.ordinal());

//        System.out.println(InetAddress.getLocalHost().getHostName());
//        System.out.println(Inet4Address.getLocalHost().getHostAddress());
//
//        Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
//        for (; n.hasMoreElements();)
//        {
//            NetworkInterface e = n.nextElement();
//
//            Enumeration<InetAddress> a = e.getInetAddresses();
//            for (; a.hasMoreElements();)
//            {
//                InetAddress addr = a.nextElement();
//                System.out.println("  " + addr.getHostAddress());
//            }
//        }

//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        dbf.setNamespaceAware(true);
//        DocumentBuilder docBuilder = dbf.newDocumentBuilder();
//        StringReader readerMessage = new StringReader(mes);
//        InputSource inputSource = new InputSource(readerMessage);
//        Document doc = docBuilder.parse(inputSource);
//        doc.normalizeDocument();
//
//        NamedNodeMap attributes = doc.getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getAttributes();
//
//        for (int i = 0; i < attributes.getLength(); i++) {
//            System.out.println(attributes.item(i));
//        }
//
//        remove(attributes);
//        attributes = doc.getChildNodes().item(0).getChildNodes().item(1).getChildNodes().item(0).getAttributes();
//        remove(attributes);
//
////        doc.getDomConfig().setParameter("format-pretty-print", true);
//        addSpace(doc);
//
//        String mesout = org.apache.ws.security.util.XMLUtils.PrettyDocumentToString(doc);
//        System.out.println(mesout);

    }

//    public static void findCopy() {
//        try (Connection connection = ConnectionBaseGRAD.instance().getConnection()) {
//            AccountGRADDAO graddao = new AccountGRADDAO(new AnswerProcessing(new ClientService()));
//            ArrayList<Rooms> rooms = graddao.getRooms(7124, connection);
//
//            for (int i = 0; i < rooms.size(); i++) {
//                for (int j = i + 1; j < rooms.size(); j++) {
//                    if (rooms.get(i).getAbonId() == rooms.get(j).getAbonId()) {
//                        System.err.println("Найден повтор записи:");
//                        System.out.println(rooms.get(i).getAbonId());
//                        System.out.println(rooms.get(i).getAddress());
//                        System.out.println(rooms.get(i).getNumberApartment());
//                        System.out.println(rooms.get(i).getNumberLS());
//                        System.out.println(rooms.get(i).getIdSpaceGISJKH());
//                        System.out.println();
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private static void getMeteringDevicesAbonIdByMeteringDeviceNumber() {

        try {

            ConnectionBaseGRAD.instance().setDataBaseURI("172.16.0.220:sibin");
            Connection connection = ConnectionBaseGRAD.instance().getConnection();
            HashSet<String> listNumbers = new HashSet<>();
            listNumbers.add("12632138");
            listNumbers.add("12562609");
            listNumbers.add("10423621");
            listNumbers.add("10420998");
            listNumbers.add("1019098903604");
            listNumbers.add("1019097640609");
            listNumbers.add("1019097262405");
            listNumbers.add("1019086957602");
            MeteringDevicesDBSearch search = new MeteringDevicesDBSearch();

            search.getMeteringDevicesAbonIdByMeteringDeviceNumber(109, listNumbers, connection);

            ConnectionBaseGRAD.instance().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Rooms> findCopy(Connection connection) {
        try {
            AccountGRADDAO graddao = new AccountGRADDAO(new AnswerProcessing(new ClientService()));
            return graddao.getRooms(7124, connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void showIsArchiveDevice() {

        try {
            MeteringDevicesDataLocalDBDAO dbdao = new MeteringDevicesDataLocalDBDAO(new AnswerProcessing(new ClientService()));
            System.out.println(dbdao.isArchivingDeviceByRootGUID("9fcee27f-dc72-41db-9122-1b43c3834102"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getMeteringDeviceGradDaoPU1() {

        try (Connection connection = ConnectionBaseGRAD.instance().getConnection()) {

            ArrayList<Rooms> rooms = findCopy(connection);

            MeteringDeviceGRADDAO graddao = new MeteringDeviceGRADDAO(new AnswerProcessing(new ClientService()), 7124);
            ArrayList<String[]> ex = graddao.getExGisPu1(7124, connection);

            for (int i = 0; i < ex.size(); i++) {
                for (int j = i + 1; j < ex.size(); j++) {
                    if (ex.get(i)[27].equals(ex.get(j)[27]) && ex.get(i)[1].equals(ex.get(j)[1])) {
                        System.out.println("Найден повтор записи: " + getLsFromAbonId(rooms, Integer.valueOf(ex.get(i)[27])));
                        System.out.println(ex.get(i)[2] + " :\t" + ex.get(j)[2]);
                        System.out.println(ex.get(i)[1] + " :\t" + ex.get(j)[1]);
                        System.out.println(ex.get(i)[8] + " :\t" + ex.get(j)[8]);
                        System.out.println(ex.get(i)[26] + " :\t" + ex.get(j)[26]);
                        System.out.println(ex.get(i)[27] + " :\t" + ex.get(j)[27]);
                        System.out.println();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getLsFromAbonId(ArrayList<Rooms> rooms, int abomnId) {
        for (Rooms room : rooms) {
            if (abomnId == room.getAbonId()) {
                return room.getNumberLS();
            }
        }
        return null;
    }

    public static void setShutdownDefragToLocalBase() {

        ConnectionDB.instance().setShutdownDefragToLocalBase();

        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             Statement st = connection.createStatement()) {
            st.executeUpdate("SHUTDOWN DEFRAG");
            System.out.println("SHUTDOWN DEFRAG SUCCESS!!!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.close();
        }

    }

    public static void getTableSize() {

        ConnectionDB.instance().setShutdownDefragToLocalBase();

        Long size = 0L;
        try (Connection connection = ConnectionDB.instance().getConnectionDB();
             Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT SOAPMESSAGE FROM OBMEN_OPERATION");
            while (rs.next()) {
                Blob blob = rs.getBlob(1);
                if (blob != null)
                    size += blob.length();
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(size / 1024 / 1024);
    }

//    public static void parsePU(String metering) {
//
//        String mark = "";
//        String model = "";
//
////        Парсим на модель и марку ПУ
////        Если название имеет имя меньшего размера начмная с цифр. Например: СВХ777111.99
//        if (metering.split("\\d")[0].length() <= metering.split("\\s")[0].length() &&
//                metering.split("\\d")[0].length() <= metering.split("-")[0].length()) {
//
//            mark = metering.split("\\d")[0].trim();
//            model = metering.substring(mark.length()).trim();
//
////            Если название имеет имя меньшего размера начмная с тире. Например: СВХ-15.45-10
//        } else if (metering.split("-")[0].length() <= metering.split("\\s")[0].length() &&
//                metering.split("-")[0].length() <= metering.split("\\d")[0].length()) {
//
//            mark = metering.split("-")[0].trim();
//            model = metering.substring(mark.length()).trim();
//            if (model.charAt(0) == '-') { // если следующий разделитель тире. Например: СВХ - 15.45-10, нужно его убрать.
//                model = model.substring(1).trim();
//            }
//
//        } else { // Остальные случаи. Например: СВХ - 15.45-10
//            mark = metering.split("\\s")[0].trim();
//            model = metering.substring(mark.length()).trim();
//            if (model.charAt(0) == '-') { // если следующий разделитель тире. Например: СВХ - 15.45-10, нужно его убрать.
//                model = model.substring(1).trim();
//            }
//        }
//
//        System.out.println("Original: " + metering + " Marka: " + mark + " Model: " + model);
//    }


    public static void getDoubleAbonId() throws SQLException, ParseException, PreGISException {
        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {
            MeteringDeviceGRADDAO graddao = new MeteringDeviceGRADDAO(new AnswerProcessing(new ClientService()), 7124);
            ArrayList<String[]> exGisPu1 = graddao.getExGisPu1(7124, connectionGRAD);
            for (int i = 0; i < exGisPu1.size(); i++) {
                int countArray = 0;
                for (int j = i + 1; j < exGisPu1.size(); j++) {
                    countArray++;
                    if (exGisPu1.get(i)[18].equals(exGisPu1.get(j)[18])) {
                        System.out.println("Repiat: " + exGisPu1.get(i)[18] + " meterId: " + exGisPu1.get(i)[17] + " i: " + i + " j" + j);
                    }
                }
                System.out.println("Count Array: " + countArray);
            }
        }
    }

    public static void getArrayCount() throws SQLException, ParseException, PreGISException {

        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {
            MeteringDeviceGRADDAO graddao = new MeteringDeviceGRADDAO(new AnswerProcessing(new ClientService()), 7124);
            ArrayList<String[]> exGisPu1 = graddao.getExGisPu1(7124, connectionGRAD);
            for (int i = 0; i < exGisPu1.size(); i++) {
                int countArray = 0;
                for (int j = 0; j < exGisPu1.get(i).length; j++) {
                    countArray++;
                }
                System.out.println("AbonId: " + exGisPu1.get(i)[27] + " meterId: " + exGisPu1.get(i)[26] +
                        " MUNICIPAL_RESOURCE: " + exGisPu1.get(i)[11] + " METERING_VALUE: " + exGisPu1.get(i)[13] +
                        " INSTALLATION_DATE: " + exGisPu1.get(i)[17] + " Дата ввода в эксплуатацию: " + exGisPu1.get(i)[18] + " Дата опломбирования заводом: " + exGisPu1.get(i)[20]);
                System.out.println("setFirstVerificationDate: " + exGisPu1.get(i)[19]);
                System.out.println("Count Array: " + countArray);
            }
        }
    }

    private static void showList(List<String> list) {
        for (String s : list) {
            System.out.printf("%2s : ", s);
        }
        System.out.println();
    }

//    public static ArrayList<String> getList(Connection connection) throws SQLException {
//
//        MeteringDeviceGRADDAO graddao = new MeteringDeviceGRADDAO(new AnswerProcessing(new ClientService()));
//        ArrayList<String> list = new ArrayList<>();
//        return graddao.executorProcedure("EXECUTE PROCEDURE EX_GIS_PU1(7124)",
//                connection, resultSet1 -> {
//                    while (resultSet1.next())
//                        list.add(resultSet1.getString(1));
//                    return list;});
//    }

    public static void getXML() throws JAXBException {

        JAXBContext jc = JAXBContext.newInstance(ExportHouseResult.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        File file = new File("temp" + File.separator + "yCA00008.xml");

        ExportHouseResult result = (ExportHouseResult) unmarshaller.unmarshal(file);

//        Marshaller marshaller = jc.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        marshaller.marshal(result, System.out);

        System.out.println(result.getExportHouseResult().getHouseUniqueNumber());

//        String typeOperation = "Request";

//        Timestamp timestamp = Timestamp.valueOf(getDate(headerRequest));

//        try (FileInputStream inputStream = new FileInputStream(file)) {
//            setMessageToBase(headerRequest.getMessageGUID(), nameMethod, timestamp, typeOperation, inputStream, stateMessage);
//            file.deleteOnExit();
//        } catch (IOException e) {
//            LOGGER.error(e.getMessage());
//            e.printStackTrace();
//        }
    }

    private static void showTime() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() { // заводим таймер на 3 минуты
                System.out.println("Прошло типа 3 минуты");
                timer.cancel();
            }
        }, 1000 * 20);
    }

    public static void getLastMessage() {

        try {
            MessageExecutor executor = new MessageExecutor();
            executor.getLastSOAPFromBase().writeTo(System.out);
//
//            MessageDAO dao = new MessageDAO();
//
//            String inputStreamString = new Scanner(dao.getLastSOAPMessage(ConnectionDB.instance().getConnectionDB()),"UTF-8").useDelimiter("\\A").next();
//            System.out.println(inputStreamString);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void getImportResult() throws JAXBException, ParseException, SQLException, PreGISException, FileNotFoundException, SOAPException {
//
//
//        MessageExecutor message = new MessageExecutor();
//        JAXBContext jc = JAXBContext.newInstance(ImportResult.class);
//        Unmarshaller unmarshaller = jc.createUnmarshaller();
//
//        File file = new File("temp" + File.separator + "ImportResult 0-20.xml");
//        System.out.println(file);
//
//        ImportResult result = (ImportResult) unmarshaller.unmarshal(message.getSOAPMessageFromDataBaseById(9954).getSOAPBody().extractContentAsDocument());
//
//
//        for (CommonResultType type : result.getCommonResult()) {
//            if (type.getError() == null || type.getError().size() == 0) {
//                System.out.println("GUID: " + type.getGUID());
//                System.out.println("UniqueNumber: " + type.getUniqueNumber());
////            System.out.println("MeteringDeviceVersionGUID: " + type.getImportMeteringDevice().getMeteringDeviceVersionGUID());
//                System.out.println("TransportGUID: " + type.getTransportGUID());
//                System.out.println("");
//            } else {
//                System.out.println("ErrorCode: " + type.getError().get(0).getErrorCode());
//                System.out.println("Description: " + type.getError().get(0).getDescription());
//                System.out.println("");
//            }
//        }
//
//    }


    public static void remove(NamedNodeMap attributes) throws XMLStreamException {

        Map map = removeName(mes);
        System.out.println(attributes.getNamedItem("xmlns"));

        for (Object o : map.keySet()) {
            if (o != null) {
//                System.out.println(attributes.getNamedItem("xmlns:" + o.toString()));
                attributes.removeNamedItem("xmlns:" + o.toString());
            }
        } // for
    }

    public static Map removeName(String message) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new ByteArrayInputStream(message.getBytes()));
        Map removeNamespace = new HashMap<String, String>();
        List listPrefix = new ArrayList<String>();

        while (reader.hasNext()) {
            if (reader.isStartElement()) {
                listPrefix.add(reader.getPrefix());
                if (reader.getNamespaceCount() > 1) {
                    for (int i = 0; i < reader.getNamespaceCount(); i++) {
                        removeNamespace.put(reader.getNamespacePrefix(i), reader.getNamespaceURI(i));
                    } // for
                } // if
            } // if
            reader.next();
        } // while

//        оставим это
        listPrefix.add("SOAP-ENV");

        for (Object o : listPrefix) {
            if (removeNamespace.containsKey(o)) {
                removeNamespace.remove(o);
            } // if
        } // for
        return removeNamespace;
    }

    static void testDecimal(BigDecimal decimal) {

        BigDecimal testDecimal = decimal.setScale(4, BigDecimal.ROUND_DOWN);
        System.out.println(testDecimal);
    }

    public static void addSpace(Document document) {

//        DeleteNamespace del = new DeleteNamespace();

        document.getFirstChild().setTextContent("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "            <S:Body><ns3:exportDataProviderRequest xmlns:ns3=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/organizations-registry-common/\" xmlns=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/\" xmlns:ns10=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi-common/\" xmlns:ns11=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi/\" xmlns:ns12=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/device-metering/\" xmlns:ns13=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/organizations-registry/\" xmlns:ns14=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/licenses/\" xmlns:ns15=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/infrastructure/\" xmlns:ns16=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/fas/\" xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/inspection/\" xmlns:ns4=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns5=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/\" xmlns:ns6=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/services/\" xmlns:ns7=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/\" xmlns:ns8=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/payment/\" xmlns:ns9=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/bills/\" Id=\"signed-data-container\"></ns3:exportDataProviderRequest></S:Body></S:Envelope>");
//        NodeList lis = document.getChildNodes();
//        document.getChildNodes().item(0).

//        System.out.println(del.getMessage(document.getChildNodes().item(0).getChildNodes().item(0).getOwnerDocument()));
//        System.out.println(document.getChildNodes().item(0).getChildNodes().item(1));

//        document.getFirstChild().setTextContent("\n");
//        Element element = document.createElement("Test");
//        document.getFirstChild().appendChild(element);
//        element.appendChild(document.createTextNode("\n\t"));
//        Text text = document.createTextNode("\n");
//        Element element = document.getDocumentElement();
//        document.getFirstChild().appendChild(text);
//        document.getFirstChild().getFirstChild().getFirstChild().getFirstChild().appendChild(document.createTextNode("\n\t\t"));
//        document.getChildNodes().item(0).getChildNodes().item(0).appendChild(document.createTextNode("\n\t"));

    }


    private static void dateTest() throws ParseException {
        SimpleDateFormat dateFromSQL = new SimpleDateFormat("yyyy-MM-dd");
        Date oneDate = dateFromSQL.parse("2016-02-27");
        Date twoDate = dateFromSQL.parse("2016-03-01");
        System.out.println("date 1: " + oneDate + " : " + oneDate.getTime());
        System.out.println("date 2: " + twoDate + " : " + twoDate.getTime());
        System.out.println(oneDate.getTime() > twoDate.getTime() ? "oneDate больше twoDate" : "twoDate больше oneDate");


    }

    private static void getAllMeteringDeviceValue() throws PreGISException {
        try (Connection connection = ConnectionBaseGRAD.instance().getConnection()) {
            MeteringDeviceValuesGradDAO graddao = new MeteringDeviceValuesGradDAO(new AnswerProcessing(new ClientService()));
            HashMap<String, MeteringDeviceValuesObject> fromGrad = graddao.getMeteringDeviceValueFromGrad(7124, connection);
            for (Map.Entry<String, MeteringDeviceValuesObject> entry : fromGrad.entrySet()) {
                System.out.println("RootGUID: " + entry.getKey());
                System.out.println("Object: " + entry.getValue().toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
