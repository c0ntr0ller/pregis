package ru.prog_matik.java.pregis.clientmessagehandler;

import org.apache.log4j.Logger;
import ru.prog_matik.java.pregis.signet.RequestSiginet;
import ru.prog_matik.java.pregis.signet.del.DeleteNamespace;

import javax.xml.namespace.QName;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * Класс служит для перехвата сообщения и последующего его подписания.
 */
public class ClientMessageHandler implements SOAPHandler<SOAPMessageContext> {

    private Logger logger = Logger.getLogger(ClientMessageHandler.class);

    /**
     * Метод перехватывает сообщения.
     * @param messageContext - перехваченное сообщение.
     * @return boolean true.
     */
    public boolean handleMessage(SOAPMessageContext messageContext) {

        RequestSiginet requestSiginet = new RequestSiginet();
        DeleteNamespace deleteNamespace = new DeleteNamespace();
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
                msg = new SendFileToSign().sendMessageForSign(msg);
//                requestSiginet.signRequest(msg.getSOAPPart().getEnvelope().getOwnerDocument());

//                msg = requestSiginet.signRequest(msg.getSOAPPart().getEnvelope().getOwnerDocument());
                msg.getSOAPPart().normalizeDocument();
                msg.saveChanges();
                messageContext.setMessage(msg);
//                Debug
                System.out.println("\nOutbound message:");
                printHeaders(msg.getMimeHeaders());
                msg.writeTo(System.out);

                try (FileOutputStream outputStream = new FileOutputStream("temp" + File.separator + "outbound")) {
                    msg.writeTo(outputStream);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("\nInbound message:");


            try {

                try (FileOutputStream outputStream = new FileOutputStream("temp" + File.separator + "inbound")) {
                    msg.writeTo(outputStream);
                    msg.writeTo(System.out);
                }

//            Вывод сообщение запроса
            System.out.println("\nMessage: \n");
            msg.writeTo(System.out);
            System.out.println("\n");

//            Вывод заголовка сообщения
//            printHeaders(msg.getMimeHeaders());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Метод служит для вывода заголовка сообщения в консоль.
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
}
