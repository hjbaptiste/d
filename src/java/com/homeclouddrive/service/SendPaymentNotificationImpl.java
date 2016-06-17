
package com.homeclouddrive.service;

import java.io.File;
import java.io.IOException;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Service
public class SendPaymentNotificationImpl {
    @Resource
    private SendLinuxEmailImpl sendLinuxEmail;
    private final static String EMAIL_TEMPLATE_FILENAME = "emailTemplateMultipart";
    private static final String PAYOUT_EMAIL_TEMPLATE_FILENAME = "promoterPaymentNotification.html";
    private static final String FROM = "IdealBusiness@ideallogicgroup.com";
    private static final String EMAIL_SUBJECT = "You've Got A Payment";
    

    /**
     * This method saves the contact information from the contact form
     *
     * @param transactionId the id of the paypal transaction
     * @param paymentDate the date and time of the payment
     * @param payoutAmount the amount of the payment
     * @param email the email address of the user
     * @param paypalEmail the paypal email address the payment was sent to
     * @param imagePath the path to the email folder
     * @return true if the information saved correctly, false otherwise
     * @throws MessagingException
     */
    @Transactional
    public boolean sendPaymentNotification(String transactionId, String paymentDate, String payoutAmount, String email, String paypalEmail, String imagePath) throws MessagingException, IOException {
        String message = FileUtils.readFileToString(new File(imagePath + File.separator + "emailNotificationTexts", PAYOUT_EMAIL_TEMPLATE_FILENAME));

        // get email template
        File emailTemplate = new File(imagePath + File.separator + "email", EMAIL_TEMPLATE_FILENAME);

        // setup the body of the email
        String messageBody = createMessageBody(paymentDate, payoutAmount, transactionId, message, paypalEmail);

        // send email
        return sendLinuxEmail.sendMessage(EMAIL_SUBJECT, messageBody, FROM, email, emailTemplate);
    }

    private String createMessageBody(String paymentDate, String payoutAmount, String transactionId, String message, String paypalEmail) {
        return message.replace(":transactionId", transactionId).replace(":payoutAmount", payoutAmount)
                .replace(":paymentDate", paymentDate).replace(":paypalEmail", paypalEmail);
    }
    
}
