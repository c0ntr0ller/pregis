package ru.progmatik.java.pregis;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import ru.gosuslugi.dom.schema.integration.base.CommonResultType;
import ru.gosuslugi.dom.schema.integration.base.ImportResult;
import ru.gosuslugi.dom.schema.integration.services.house_management.ExportHouseResult;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.grad.devices.MeteringDeviceGRADDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.message.MessageInBase;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestMain {

    private static String mes = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
            "<S:Header><ISRequestHeader xmlns=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/\" xmlns:ns10=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi-common/\" xmlns:ns11=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi/\" xmlns:ns12=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/device-metering/\" xmlns:ns13=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/organizations-registry/\" xmlns:ns14=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/licenses/\" xmlns:ns15=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/infrastructure/\" xmlns:ns16=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/fas/\" xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/inspection/\" xmlns:ns3=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/organizations-registry-common/\" xmlns:ns4=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns5=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/\" xmlns:ns6=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/services/\" xmlns:ns7=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/\" xmlns:ns8=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/payment/\" xmlns:ns9=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/bills/\"><Date>2016-03-08T23:21:56.517+06:00</Date><MessageGUID>7b824087-412b-4aa5-9b99-8afbd52bdb15</MessageGUID></ISRequestHeader></S:Header><S:Body><ns3:exportDataProviderRequest xmlns:ns3=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/organizations-registry-common/\" xmlns=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/\" xmlns:ns10=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi-common/\" xmlns:ns11=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/nsi/\" xmlns:ns12=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/device-metering/\" xmlns:ns13=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/organizations-registry/\" xmlns:ns14=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/licenses/\" xmlns:ns15=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/infrastructure/\" xmlns:ns16=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/fas/\" xmlns:ns2=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/inspection/\" xmlns:ns4=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns5=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/\" xmlns:ns6=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/services/\" xmlns:ns7=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/\" xmlns:ns8=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/payment/\" xmlns:ns9=\"http://dom.gosuslugi.ru/schema/integration/8.6.0.4/bills/\" Id=\"signed-data-container\"></ns3:exportDataProviderRequest></S:Body></S:Envelope>";

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XMLStreamException, SQLException, JAXBException, ParseException, PreGISException, SOAPException {


//        MeteringDeviceGRADDAO graddao = new MeteringDeviceGRADDAO(new AnswerProcessing(new ClientService()), 7124);
//        System.out.println(graddao.getMeteringDeviceFromLocalBase(36, 7124, "asdasdas4646", "fdsfds"));

        getArrayCount();
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


    public static void getDoubleAbonId() throws SQLException, ParseException, PreGISException {
        try (Connection connectionGRAD = ConnectionBaseGRAD.instance().getConnection()) {
            MeteringDeviceGRADDAO graddao = new MeteringDeviceGRADDAO(new AnswerProcessing(new ClientService()), 7124);
            ArrayList<String[]> exGisPu1 = graddao.getExGisPu1(7124, connectionGRAD);
            for (int i = 0; i < exGisPu1.size(); i++) {
                int countArray = 0;
                for (int j = i + 1; j < exGisPu1.size(); j++) {
                    countArray++;
                    if (exGisPu1.get(i)[18].equals(exGisPu1.get(j)[18])) {
                        System.out.println("Repiat: " + exGisPu1.get(i)[18] + " meterId: " +  exGisPu1.get(i)[17] + " i: " + i + " j" + j);
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
                System.out.println("AbonId: " + exGisPu1.get(i)[27] + " meterId: " +  exGisPu1.get(i)[26] +
                        " MUNICIPAL_RESOURCE: " + exGisPu1.get(i)[11] + " METERING_VALUE: " + exGisPu1.get(i)[13]);
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
    public static void getImportResult() throws JAXBException, ParseException, SQLException, PreGISException, FileNotFoundException, SOAPException {


        MessageInBase message = new MessageInBase();
        JAXBContext jc = JAXBContext.newInstance(ImportResult.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();

        File file = new File("temp" + File.separator + "ImportResult 0-20.xml");
        System.out.println(file);

        ImportResult result = (ImportResult) unmarshaller.unmarshal(message.getSOAPMessageFromDataBaseById(9954).getSOAPBody().extractContentAsDocument());


        for (CommonResultType type : result.getCommonResult()) {
            if (type.getError() == null || type.getError().size() == 0) {
                System.out.println("GUID: " + type.getGUID());
                System.out.println("UniqueNumber: " + type.getUniqueNumber());
//            System.out.println("MeteringDeviceVersionGUID: " + type.getImportMeteringDevice().getMeteringDeviceVersionGUID());
                System.out.println("TransportGUID: " + type.getTransportGUID());
                System.out.println("");
            } else {
                System.out.println("ErrorCode: " + type.getError().get(0).getErrorCode());
                System.out.println("Description: " + type.getError().get(0).getDescription());
                System.out.println("");
            }
        }

    }


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
}
