/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.prog_matik.java.pregis.clientmessagehandler;

import ru.prog_matik.java.pregis.signet.RequestSiginet;

import javax.xml.namespace.QName;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;


public class ClientMessageHandler implements SOAPHandler<SOAPMessageContext> {

    public boolean handleMessage(SOAPMessageContext messageContext) {

//        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        RequestSiginet requestSiginet = new RequestSiginet();
        SOAPMessage msg = messageContext.getMessage();

        Boolean outboundProperty = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty) {
            try {

//                msg.writeTo(byteStream);
                System.out.println("\nOriginal Message: \n");
                msg.writeTo(System.out);
                System.out.println("\n");

                msg.getSOAPPart().getEnvelope().removeNamespaceDeclaration("SOAP-ENV");
                msg.saveChanges();
                msg = requestSiginet.signRequest(msg.getSOAPPart().getEnvelope().getOwnerDocument());
                msg.getSOAPPart().normalizeDocument();
                msg.saveChanges();


//                requestSiginet.signRequest(soapEnvelope.getOwnerDocument());

//                msg = MessageFactory.newInstance().createMessage(null, new ByteArrayInputStream(requestSiginet.signRequest(byteStream).toByteArray()));


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            System.out.println("\nOutbound message:");
        } else {
            System.out.println("\nInbound message:");
        }

        try {

            System.out.println("\nMessage: \n");
            msg.writeTo(System.out);
            System.out.println("\n");

            printHeaders(msg.getMimeHeaders());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return true;
    }

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
