//package ru.progmatik.java.pregis.connectiondb.grad.account;
//
//import org.junit.Test;
//import ru.gosuslugi.dom.schema.integration.house_management.ImportAccountRequest;
//import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
//import ru.progmatik.java.pregis.exception.PreGISException;
//import ru.progmatik.java.pregis.other.AnswerProcessing;
//import ru.progmatik.java.web.servlets.socket.ClientService;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.text.ParseException;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import static org.junit.Assert.*;
//
///**
// * Created by andryha on 14.10.2016.
// */
//public class AccountGRADDAOTest {
//
//    @Test
//    public void getAccountMapFromGrad() {
//        try (Connection connectionGrad = ConnectionBaseGRAD.instance().getConnection()) {
//
//            AccountGRADDAO graddao = new AccountGRADDAO(new AnswerProcessing(new ClientService()));
//
//            LinkedHashMap<String, ImportAccountRequest.Account> accountMapFromGrad = graddao.getAccountMapFromGrad(109, connectionGrad);
//
//            for (Map.Entry<String, ImportAccountRequest.Account> entry : accountMapFromGrad.entrySet()) {
//                System.out.println(entry.getKey());
//            }
//
//
//        } catch (SQLException | PreGISException | ParseException e) {
//            e.printStackTrace();
//        }
//    }
//
//}