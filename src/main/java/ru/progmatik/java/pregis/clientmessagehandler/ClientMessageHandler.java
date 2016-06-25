package ru.progmatik.java.pregis.clientmessagehandler;

import org.apache.log4j.Logger;
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

    /**
     * Метод перехватывает сообщения.
     *
     * @param messageContext - перехваченное сообщение.
     * @return boolean true.
     */
    public boolean handleMessage(SOAPMessageContext messageContext) {

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

                msg = requestSiginet.removeNamespace(msg.getSOAPPart().getEnvelope().getOwnerDocument()); // разные методы форматирования
//                deleteNamespace.removeNamespace(msg.getSOAPPart().getEnvelope().getOwnerDocument()); // разные методы форматирования


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
//                if (LOGGER.isDebugEnabled()) {
                    System.out.println("\nOutbound message:");
                    printHeaders(msg.getMimeHeaders());
                    msg.writeTo(System.out);
                    System.out.println();
//                }

                try (FileOutputStream outputStream = new FileOutputStream("temp" + File.separator + "outbound")) {
                    msg.writeTo(outputStream);
                }

            } catch (Exception e) {
                LOGGER.error("ClientMessageHandler: ", e);
                e.printStackTrace();
            }

        } else {

            try {

                try (FileOutputStream outputStream = new FileOutputStream("temp" + File.separator + "inbound")) {
                    msg.writeTo(outputStream);
                }

//            Вывод сообщение запроса
//                if (LOGGER.isDebugEnabled()) {
                    System.out.println("\nInbound message:");
//                  Вывод заголовка сообщения
                    printHeaders(msg.getMimeHeaders());
                    System.out.println("\nMessage: \n");
                    msg.writeTo(System.out);
                    System.out.println("\n");
//                }


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
        LOGGER.error("ClientMessageHandler: Не найден элемент для подписи.");
        return false;
    }
}
