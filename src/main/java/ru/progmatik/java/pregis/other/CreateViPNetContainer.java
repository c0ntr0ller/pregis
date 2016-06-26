//package ru.progmatik.java.pregis.other;
//
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import ru.infotecs.crypto.ViPNetProvider;
//import ru.progmatik.java.pregis.signet.Configure;
//
//import java.io.*;
//import java.security.*;
//import java.security.cert.*;
//import java.security.cert.Certificate;
//
///**
// * Created by andryha on 26.06.16.
// */
//public class CreateViPNetContainer {
//
//    public static void main(String[] args) throws GeneralSecurityException, IOException {
//        Security.addProvider(new BouncyCastleProvider());
//        Security.addProvider(new ViPNetProvider());  // Добавим криптопровайдер VipNet нужен для TLS соединения.
//        // получение хранилища для работы с одним контейнером в формате ViPNet
//        KeyStore keyStore = KeyStore.getInstance(
//                "ViPNetContainer", // тип хранилища
//                "ViPNet"  // название провайдера
//        );
//// подготовка потока ввода с данными ViPNet контейнера
////        InputStream inputStream =...;
//// загрузка хранилища
//        Key key = (ru.infotecs.crypto.gost3410.GostR341001PrivateKey) Configure.getKeyStorePfx().getKey("cp_exported", null);
//        Certificate[] certificates = Configure.getKeyStorePfx().getCertificateChain("cp_exported");
//
////        InputStream inputStream = new FileInputStream("data/dubovik.vipnet");
//        keyStore.load(null, null);
//        System.out.println(key.toString());
//        keyStore.setKeyEntry("dubovik", key, "".toCharArray(), certificates);
////        keyStore.load(inputStream, null);
//// подготовка потока вывода для получения данных ViPNet контейнера
//        OutputStream outputStream = new FileOutputStream("data/dubovik.vipnet");
//// сохранение хранилища
//        keyStore.store(outputStream, "123456".toCharArray());
//        outputStream.flush();
//        outputStream.close();
//    }
//}
