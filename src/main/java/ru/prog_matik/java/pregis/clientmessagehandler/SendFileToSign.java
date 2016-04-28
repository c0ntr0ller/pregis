package ru.prog_matik.java.pregis.clientmessagehandler;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

/**
 * Класс, отправляет сообщение на подпись сервису "http://172.16.0.221:8080/".
 * Created by andryha on 25.04.2016.
 */
public class SendFileToSign {

    private static final Logger LOGGER = Logger.getLogger(SendFileToSign.class);

    public SOAPMessage sendMessageForSign(SOAPMessage soapMessage) {

        String login = ResourceBundle.getBundle("application").getString("config.websign.user");
        String password = ResourceBundle.getBundle("application").getString("config.websign.password");
        SOAPMessage answerMessage = null;

        try {
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

            soapMessage.writeTo(arrayOutputStream);

            HttpEntity entity = MultipartEntityBuilder
                    .create()
                    .addTextBody("login", login)
                    .addTextBody("password", password)
                    .addBinaryBody("upfile", arrayOutputStream.toByteArray(), ContentType.TEXT_XML, "pregis")
                    .build();

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost("http://172.16.0.221:8080/signingfile");
            post.setEntity(entity);
            CloseableHttpResponse response = httpClient.execute(post);
            HttpEntity answerEntity = response.getEntity();
            if (answerEntity != null) {
                long len = answerEntity.getContentLength();
                if (len != -1) {
                    answerMessage = toMessage(EntityUtils.toString(answerEntity).replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n", ""));
                }
            }

        } catch (SOAPException | IOException e) {
            LOGGER.error("SendFileToSign.sendMessageForSign(): Ошибка отправки файл на подпись. ", e);
            e.printStackTrace();
        }
        return answerMessage;
    }

    /**
     * Метод преобразует String в SOAPMessage.
     * @param message - сообщение в формате String.
     * @return SOAPMessage - готовое сообщение для отправки в формате SOAPMessage.
     * @throws IOException
     * @throws SOAPException
     */
    private SOAPMessage toMessage(String message) throws IOException, SOAPException {

        MessageFactory messageFactory = MessageFactory.newInstance();
        InputStream inputStream = new ByteArrayInputStream(message.getBytes());

        return messageFactory.createMessage(null, inputStream);
    } // toMessage
}
