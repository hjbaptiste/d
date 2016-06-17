package 

com.homeclouddrive.service;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

/**
 *
 * @author getjigy
 */
@Service
public class SendEmailImpl {

    /**
     * TODO - add the username and password to the properties file and inject
     * them into this service
     */
    private static final String SMTP_HOST_NAME = "smtp.googleserver.net"; //smtp URL
    private static final int SMTP_HOST_PORT = 465 ; //port number
    private static String CUSTOMER_SERVICE = "customerservice@getjigy.com";
    private static String PASSWORD = "mypassword";

    // to is a var args field. If it is empty, it means that someone is attempting to contact customer service so
    // we will fill the "to" field in with the customer service info. If it is not empty, it means
    // that a message needs to go out to a user and the email has been included so we'll use that one
    /**
     * This method handles the actual sending of an email
     *
     * @param subject the subject of the email
     * @param messageBody the body of the email
     * @param to who the email will be sent to
     * @return true if the email was sent without error
     */
    public boolean sendMessage(String subject, String messageBody, String... to) {

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", SMTP_HOST_NAME);
        props.put("mail.smtps.auth", "true");

        Session mailSession = Session.getDefaultInstance(props);
        mailSession.setDebug(true);

        try {
            Transport transport = mailSession.getTransport();
            javax.mail.Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(CUSTOMER_SERVICE));
            if (to != null && to.length > 0) {
                message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(to[0]));
            } else {
                message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(CUSTOMER_SERVICE));
            }

            message.setSubject(subject);
            message.setContent(messageBody, "text/html");

            //Transport.send(message);
            transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, CUSTOMER_SERVICE, PASSWORD);
            transport.sendMessage(message, message.getRecipients(javax.mail.Message.RecipientType.TO));
            transport.close();

        } catch (Exception ex) {
            Logger.getLogger(SendEmailImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }
    
    

    /**
     * This method handles the actual sending of an email
     *
     * @param subject the subject of the email
     * @param messageBody the body of the email
     * @param to who the email will be sent to
     * @return true if the email was sent without error
     */
    public boolean sendOffice365Message(String subject, String messageBody, String... to) {

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.port", String.valueOf(SMTP_HOST_PORT));
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");

        Session mailSession = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("\\" + CUSTOMER_SERVICE, PASSWORD);
                    }
                });
        mailSession.setDebug(true);

        try {
            Transport transport = mailSession.getTransport();
            javax.mail.Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(CUSTOMER_SERVICE));
            if (to != null && to.length > 0) {
                message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(to[0]));
            } else {
                message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(CUSTOMER_SERVICE));
            }

            message.setSubject(subject);
            message.setContent(messageBody, "text/html");

            //Transport.send(message);
            transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, CUSTOMER_SERVICE, PASSWORD);
            transport.sendMessage(message, message.getRecipients(javax.mail.Message.RecipientType.TO));
            transport.close();

        } catch (Exception ex) {
            Logger.getLogger(SendEmailImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }
}
