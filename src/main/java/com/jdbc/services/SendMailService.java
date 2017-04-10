package com.jdbc.services;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Atom on 3/9/2017.
 */
public class MailService {
    private int id = -1;
    private int rate = 0;

    private final String to = "artyom.gishyan@mail.ru";
    private final String from = "arthades047@gmail.com";
    private final String username = "arthades047";
    private final String password = "*****";
    private static Properties properties;

    static {
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
    }
    Session session = Session.getInstance(properties,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new   PasswordAuthentication(username, password);
                }
            });

    public MailService(int id, int rate) {
        this.id = id;
        this.rate = rate;
    }

    public boolean send() {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress((from)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("passedExam");
            message.setText(id + " " + rate);
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }


}
