package com.jdbc.services;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Atom on 3/9/2017.
 */
public class SendMailService {

    private final String from = "artyom.gishyan@mail.ru";
    private final String to = "arthades047@gmail.com";
    private static Properties properties = new Properties();
    private Session session = null;
    private Message message = null;

    static {
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.ssl.enable", true);
        properties.put("mail.smtp.host", "smtp.mail.ru");
        properties.put("mail.smtp.port", 465);

    }

    public SendMailService() throws MessagingException {
        session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("mail", "*");
                    }
                });
        message = new MimeMessage(session);
        message.setFrom(new InternetAddress((from)));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
        message.setSubject("passedExam");
    }

    public boolean send(int id, int rate) {
        try {
            message.setText(id + " " + rate);
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }

}
