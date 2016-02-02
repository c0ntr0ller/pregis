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
import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;


public class ClientMessageHandler implements SOAPHandler<SOAPMessageContext> {

    public boolean handleMessage(SOAPMessageContext messageContext) {

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        RequestSiginet requestSiginet = new RequestSiginet();
        SOAPMessage msg = messageContext.getMessage();


//        msg.getMimeHeaders().setHeader("SOAPAction", "urn:exportOrgRegistry");
//        msg.getMimeHeaders().removeHeader("Accept");


//        String name = "test";
//        String password = "SDldfls4lz5@!82d";
//
//        String authString = name + ":" + password;
//        System.out.println("auth string: " + authString);
//        String authEncBytes = Base64.encode(authString.getBytes());
//        System.out.println("Base64 encoded auth string: " + authEncBytes);
//
//        msg.getMimeHeaders().addHeader("Authorization", "Basic " + authEncBytes);

        Boolean outboundProperty = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty) {
            try {
//                SOAPPart soapPart = msg.getSOAPPart();
//                soapPart.normalizeDocument();
//                msg.saveChanges();
                msg.writeTo(byteStream);
                System.out.println("\nOriginal Message: \n");
                byteStream.writeTo(System.out);
                System.out.println("\n");



//                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
//                Document doc = requestSiginet.signRequest(byteStream);

//                DOMSource domSource = new DOMSource(doc);
//                soapPart.setContent(domSource);
//                soapPart.normalizeDocument();
                requestSiginet.signRequest(msg.getSOAPPart().getEnvelope().getOwnerDocument());

//                msg = MessageFactory.newInstance().createMessage(null, new ByteArrayInputStream(requestSiginet.signRequest(byteStream).toByteArray()));
                msg.saveChanges();

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


//            MimeHeaders mimeHeader = msg.getMimeHeaders();
//            mimeHeader.addHeader("SOAPAction", "urn:exportOrgRegistry");
//            mimeHeader.addHeader("User-Agent", "Apache-HttpClient/4.1.1 (java 1.5)");
//            mimeHeader.addHeader("Content-Length", "6170");
//            mimeHeader.addHeader("Content-Type", "text/xml;charset=UTF-8");
//
//            System.out.println("MIMEHEADER: " + mimeHeader.toString());

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
