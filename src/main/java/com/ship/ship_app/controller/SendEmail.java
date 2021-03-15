package com.ship.ship_app.controller;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class SendEmail {
    public static void send(InternetAddress[] to, String sub, String msg) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.prot", "465");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("shipsdevteam", "developer1234");
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("shipsdevteam@gmail.com", "Ships App"));
            message.setRecipients(Message.RecipientType.TO, to);
            message.setSubject(sub);
            message.setText(msg);
            Transport.send(message);

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}





