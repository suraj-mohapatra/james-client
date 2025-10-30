package com.uglyeagle;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class Main {

    private static final String SMTP_HOST = "localhost";
    private static final String SMTP_PORT = "587";
    private static final String SENDER_EMAIL = "alice@uglyeagle.bird";
    private static final String SENDER_PASSWORD = "alice123";
    private static final String RECEIVER_EMAIL = "bob@uglyeagle.bird";

    public static void main(String[] args) {
        System.out.println("Setting up mail session properties...");

        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // enables STARTTLS
        props.put("mail.smtp.ssl.trust", "*"); // trusts all hosts. bypass SSL certificate verification.
        props.put("mail.smtp.ssl.checkserveridentity", "false"); // don not check the server identity

        // creating an authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        };

        Session session = Session.getInstance(props, auth);

        // session.setDebug(true); //enable for detailed debug output
        try { 
            System.out.println("composing email---------------------------------------");
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(SENDER_EMAIL));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RECEIVER_EMAIL));
            msg.setSubject("from Java client");
            msg.setText("hello Bob");

            System.out.println("connecting to server and sending email------------------------");
            Transport.send(msg);

            System.out.println("email sent successful");

        } catch (MessagingException me) {
            System.out.println("failed to send email.");
            me.getMessage();
        }
    }
}
