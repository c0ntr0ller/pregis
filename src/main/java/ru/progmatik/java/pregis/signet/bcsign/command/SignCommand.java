package ru.progmatik.java.pregis.signet.bcsign.command;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.progmatik.java.pregis.other.OtherFormat;
import ru.progmatik.java.pregis.signet.bcsign.exceptions.ElementNotFoundException;
import ru.progmatik.java.pregis.signet.bcsign.xades.Consts;
import ru.progmatik.java.pregis.signet.bcsign.xades.production.CustomizableXadesBesSigningProfileFactory;
import ru.progmatik.java.pregis.signet.bcsign.xades.providers.CustomizableAlgorithmProvider;
import ru.progmatik.java.pregis.signet.bcsign.xades.providers.CustomizableMessageDigestEngineProvider;
import ru.progmatik.java.pregis.signet.bcsign.xml.IdResolver;
import xades4j.algorithms.EnvelopedSignatureTransform;
import xades4j.algorithms.ExclusiveCanonicalXMLWithoutComments;
import xades4j.production.*;
import xades4j.properties.DataObjectDesc;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.MessageDigestEngineProvider;
import xades4j.providers.impl.DirectKeyingDataProvider;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.X509Certificate;

/**
 * Выполняет подписание XML-документа.
 */
public class SignCommand {

//    private SignParameters parameters;
//
//    public SignCommand(SignParameters parameters) {
//        this.parameters = parameters;
//    }

    public SOAPMessage execute(Document document) throws Exception {
//        // инициализируем Apache Santuario
//        org.apache.xml.security.Init.init();
//
//        // загружаем криптопровайдер
//        String providerName = parameters.getProviderName();
//        Provider provider = providerName == null ? null : Security.getProvider(providerName);
//        if (provider == null) {
//            provider = ProviderFactory.createProvider(parameters.getProviderClass(), parameters.getProviderArg());
//            Security.addProvider(provider);
//        }

        // загружаем хранилище закрытых ключей
//        char[] storePassword = parameters.getStorePassword() == null ? null : StringUtils.defaultString(parameters.getStorePassword()).toCharArray();
//        char[] storePassword = "123456".toCharArray();
//        char[] keyPassword = parameters.getKeyPassword() == null ? null : StringUtils.defaultString(parameters.getKeyPassword()).toCharArray();
//        KeyStore keyStore = KeyStore.getInstance("pkcs12", "BC");
//        if (parameters.getStoreFile() != null) {
//            KeyStoreUtils.loadKeyStoreFromFile(keyStore, new File(KEY_FOLDER), storePassword);
//            KeyStoreUtils.loadKeyStoreFromFile(keyStore, parameters.getStoreFile(), storePassword);
//        } else if (parameters.getStoreName() != null) {
//            KeyStoreUtils.loadKeyStoreByName(keyStore, parameters.getStoreName(), storePassword);
//        }

        // загружаем закрытый ключ
        KeyStore.PrivateKeyEntry keyEntry = ru.progmatik.java.pregis.signet.Configure.getPrivateKeyFromPfx();
//        if (keyEntry == null) {
//            throw new KeyException("Key not found: " + KEY_ALIAS);
//        }

        // создаем провайдер для доступа к закрытому ключу
        KeyingDataProvider kp = new DirectKeyingDataProvider((X509Certificate) keyEntry.getCertificate(), keyEntry.getPrivateKey());

        // создаем провайдер, описывающий используемые алгоритмы
        CustomizableAlgorithmProvider algorithmsProvider = new CustomizableAlgorithmProvider();
        algorithmsProvider.setSignatureAlgorithm(Consts.SIGNATURE_ALGORITHM);
        algorithmsProvider.setCanonicalizationAlgorithmForSignature(Consts.CANONICALIZATION_ALGORITHM_FOR_SIGNATURE);
        algorithmsProvider.setCanonicalizationAlgorithmForTimeStampProperties(Consts.CANONICALIZATION_ALGORITHM_FOR_TIMESTAMP_PROPERTIES);
        algorithmsProvider.setDigestAlgorithmForDataObjsReferences(Consts.DIGEST_ALGORITHM_URI);
        algorithmsProvider.setDigestAlgorithmForReferenceProperties(Consts.DIGEST_ALGORITHM_URI);
        algorithmsProvider.setDigestAlgorithmForTimeStampProperties(Consts.DIGEST_ALGORITHM_URI);

        // создаем провайдер, ответственный за расчет хешей
        MessageDigestEngineProvider messageDigestEngineProvider = new CustomizableMessageDigestEngineProvider(Consts.DIGEST_ALGORITHM_NAME, Security.getProvider("BC"));

        // настраиваем профиль подписания
        XadesSigningProfile profile = new CustomizableXadesBesSigningProfileFactory()
                .withKeyingProvider(kp)
                .withAlgorithmsProvider(algorithmsProvider)
                .withMessageDigestEngineProvider(messageDigestEngineProvider)
                .create();

        // создаем объект, ответственный за создание подписи
        XadesSigner signer = profile.newSigner();

        // загружаем проверяемый XML-документ
//        Document document = XMLParser.parseXml(parameters.getInputFile());

        // объявляем атрибут Id в качестве идентифицирующего
        IdResolver.resolveIds(document.getDocumentElement());

        // ищем подписываемый элемент
        String signedElementId = OtherFormat.getId();
        Element signedElement = document.getElementById(signedElementId);
        if (signedElement == null) {
            throw new ElementNotFoundException("Element to be signed not found: " + signedElementId);
        }

        // ищем элемент, в который нужно поместить подпись; если не указан, помещаем подпись в подписываемый элемент
        Element signatureContainer = document.getElementById(signedElementId);
        if (signatureContainer == null) {
            throw new ElementNotFoundException("Container element not found: " + signedElementId);
        }

        // настраиваем подписываемые данные
        DataObjectDesc obj = new DataObjectReference('#' + signedElementId);

//        if (signedElementId.equals(signedElementId)) {
            // если подпись помещается в подписываемый элемент, применяем трансформацию enveloped signature transform
            // если этого не сделать, подпись нельзя будет проверить
            obj.withTransform(new EnvelopedSignatureTransform());
//        }

        // применяем трансформацию Exclusive XML Canonicalization 1.0 without comments (комментарии исключаются из подписываемых данных)
        obj.withTransform(new ExclusiveCanonicalXMLWithoutComments());

        // создаем подпись
        SignedDataObjects dataObjs = new SignedDataObjects(obj);
        signer.sign(dataObjs, signatureContainer, SignatureAppendingStrategies.AsFirstChild);

//        if (parameters.getOutputFile() == null) {
//            // выводим результат в stdout
//            System.out.println(XMLPrinter.toString(document));
//        } else {
//            // выводим результат в файл
//            byte[] xmlBytes = XMLPrinter.toBytes(document);
//            FileUtils.writeByteArrayToFile(parameters.getOutputFile(), xmlBytes);
//        }
        String message = org.apache.ws.security.util.XMLUtils.PrettyDocumentToString(document);
        message = message.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");

        MessageFactory messageFactory = MessageFactory.newInstance();
        InputStream inputStream = new ByteArrayInputStream(message.getBytes());

        return messageFactory.createMessage(null, inputStream);
    }
}
