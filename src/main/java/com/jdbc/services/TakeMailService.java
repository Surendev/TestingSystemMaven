package com.jdbc.services;

import javax.mail.*;
import java.io.IOException;
import java.util.*;

/**
 * Created by Atom on 3/15/2017.
 */
public class TakeMailService {
    private static Properties properties = new Properties();
    public Map<String, List<String>> values = new HashMap<>();
    public static Properties examProperties = new Properties();
    private Session session = null;
    private Store store = null;
    static {
        properties.setProperty("mail.imap.ssl.enable", "true");
        properties.setProperty("mail.imap.port", "993");
        properties.setProperty("mail.imap.host", "imap.mail.ru");

        examProperties.setProperty("permission", "false");
    }



    public TakeMailService() throws NoSuchProviderException {
        session = Session.getDefaultInstance(properties);
        store = session.getStore("imaps");
    }

    private Message[] getMessages() throws MessagingException {
        store.connect("imap.gmail.com", "user", "password");
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        Message[] messages = folder.getMessages();
        folder.close(false);
        return messages;
    }


    private void putValues(Message[] messages) throws MessagingException, IOException {
        List<String> students = new ArrayList<>();
        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        for (Message message : messages){
            String subject = message.getSubject();
            String content = (String) message.getContent();
            content = content.replace("#", ",");
            switch (subject){
                case "New Student" : students.add(content); break;
                case "New Question" : String[] prop = getQuestionProperties(content);
                                               questions.add(prop[0]);
                                               answers.add(prop[1]);
                                               answers.add(prop[2]);
                                               break;
            }

        }
        values.put("students", students);
        values.put("questions", questions);
        values.put("answers", answers);

    }

    private String[] getQuestionProperties(String content){
        String[] properties = new String[3];
        String[] contentParts = content.split("#");
        properties[0] = contentParts[1] + "," + contentParts[2] + "," + contentParts[3] + "," + contentParts[4];
        properties[1] = contentParts[5] + "," + contentParts[0];
        properties[2] = contentParts[6] + "," + contentParts[0];
        return properties;
    }

    private void setExamProperties(Message message) throws IOException, MessagingException {
        String props = (String) message.getContent();
        String[] properties =  props.split("#");
        if (!properties[0].equals("false"))
            for (String pair : properties) {
                examProperties.setProperty("permission", pair);
                examProperties.setProperty("time", pair);
                examProperties.setProperty("length", pair);
            }
    }

}
