package ru.progmatik.java.pregis.clientmessagehandler;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.other.ResourcesUtil;
import ru.progmatik.java.pregis.signet.RequestSiginet;
import ru.progmatik.java.pregis.signet.bcsign.command.SignCommand;

import javax.xml.namespace.QName;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * Класс служит для перехвата сообщения и последующего его подписания.
 */
public class ClientMessageHandler implements SOAPHandler<SOAPMessageContext> {

    private static final Logger LOGGER = Logger.getLogger(ClientMessageHandler.class);
    private static final String SAVE_FOLDER = "temp";
    private static final String FILE_OUTBOUND = "outbound.xml";
    private static final String FILE_INBOUND = "inbound.xml";

    /**
     * Метод перехватывает сообщения.
     *
     * @param messageContext - перехваченное сообщение.
     * @return boolean true.
     */
    public boolean handleMessage(SOAPMessageContext messageContext) {

//        Создание папок
        ResourcesUtil.instance().createFolder(SAVE_FOLDER);

        RequestSiginet requestSiginet = new RequestSiginet();
        SignCommand signCommand = new SignCommand();
//        DeleteNamespace deleteNamespace = new DeleteNamespace();
        SOAPMessage msg = messageContext.getMessage();

        Boolean outboundProperty = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty) {
            try {

//                Вывод в консоль сформированного сообщения без подписи.
//                System.out.println("\nOriginal Message: \n");
//                msg.writeTo(System.out);
//                System.out.println("\n");

//                msg = requestSiginet.removeNamespace(msg.getSOAPPart().getEnvelope().getOwnerDocument()); // разные методы форматирования
//                deleteNamespace.removeNamespace(msg.getSOAPPart().getEnvelope().getOwnerDocument()); // разные методы форматирования
//                try (FileOutputStream outputStream = new FileOutputStream(SAVE_FOLDER + File.separator + "tmp1.xml")) {
//                    msg.writeTo(outputStream);
//                    outputStream.flush();
//                    outputStream.close();
//                }

                msg = RequestSiginet.toPrettyXmlString(4, msg.getSOAPPart().getEnvelope().getOwnerDocument());

//                try (FileOutputStream outputStream = new FileOutputStream(SAVE_FOLDER + File.separator + "tmp2.xml")) {
//                    msg.writeTo(outputStream);
//                    outputStream.flush();
//                    outputStream.close();
//                }

                if (isSignIDState(msg)) {
//                    Подпись с помощью BouncyCastle
                    msg = signCommand.execute(msg.getSOAPPart().getEnvelope().getOwnerDocument());
//                    Подпись с помощью веб-сервера TrustedJava
//                    msg = new SendFileToSign().sendMessageForSign(msg);
                }

//                requestSiginet.signRequest(msg.getSOAPPart().getEnvelope().getOwnerDocument());

//                msg = requestSiginet.signRequest(msg.getSOAPPart().getEnvelope().getOwnerDocument());
                msg.getSOAPPart().normalizeDocument();
                msg.saveChanges();
                messageContext.setMessage(msg);

//                Debug
                if (LOGGER.isDebugEnabled()) {
                    System.out.println("\nOutbound message:");
                    printHeaders(msg.getMimeHeaders());
                    msg.writeTo(System.out);
                    System.out.println();
                }

                try (FileOutputStream outputStream = new FileOutputStream(SAVE_FOLDER + File.separator + FILE_OUTBOUND)) {
                    msg.writeTo(outputStream);
                    outputStream.flush();
                    outputStream.close();
//                    msg.writeTo(outputStream);
                }

            } catch (Exception e) {
                LOGGER.error("ClientMessageHandler: ", e);
                e.printStackTrace();
            }

        } else {

            try {

                try (FileOutputStream outputStream = new FileOutputStream(SAVE_FOLDER + File.separator + FILE_INBOUND)) {
                    msg.writeTo(outputStream);
                    outputStream.flush();
                    outputStream.close();
                }

//            Вывод сообщение запроса
                if (LOGGER.isDebugEnabled()) {
                    System.out.println("\nInbound message:");
//                  Вывод заголовка сообщения
                    printHeaders(msg.getMimeHeaders());
                    System.out.println("\nMessage: \n");
                    msg.writeTo(System.out);
                    System.out.println("\n");
                }


            } catch (Exception e) {
                LOGGER.error("ClientMessageHandler: ", e);
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Метод служит для вывода заголовка сообщения в консоль.
     *
     * @param headers заголовок сообщения.
     */
    private void printHeaders(MimeHeaders headers) {

        System.out.println();

        Iterator eachHeader = headers.getAllHeaders();
        while (eachHeader.hasNext()) {
            MimeHeader currentHeader = (MimeHeader) eachHeader.next();

            System.out.println("Header name: " + currentHeader.getName() + " " + currentHeader.getValue());
        }
    }

    public Set<QName> getHeaders() {
        return Collections.EMPTY_SET;
    }

    public boolean handleFault(SOAPMessageContext messageContext) {
        return true;
    }

    public void close(MessageContext context) {
    }

    private boolean isSignIDState(SOAPMessage message) {

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(new ByteArrayInputStream(outputStream.toByteArray()));

            while (reader.hasNext()) {
                if (reader.next() == XMLStreamConstants.START_ELEMENT) {
                    if (reader.isAttributeSpecified(0)) {
                        for (int i = 0; i < reader.getAttributeCount(); i++) {
                            if ("Id".equalsIgnoreCase(reader.getAttributeLocalName(i))) {
                                return ("signed-data-container".equalsIgnoreCase(reader.getAttributeValue(i)));
                            }
                        }
                    }
                }
            }
        } catch (IOException | SOAPException | XMLStreamException e) {
            LOGGER.error("ClientMessageHandler: ", e);
            e.printStackTrace();
        }
        // TODO Закомментировал, так как при асинхронном режиме дает постоянную ошибку , нужно разобраться LOGGER.error("ClientMessageHandler: Не найден элемент для подписи.");
        return false;
    }
}
