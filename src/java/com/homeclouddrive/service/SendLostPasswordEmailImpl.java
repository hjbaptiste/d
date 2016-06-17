
package com.homeclouddrive.service;

import com.jigy.api.Helpful;
import java.io.File;
import java.io.IOException;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class SendLostPasswordEmailImpl {

    
    /**
     *
     * @author getjigy
     */
    @Resource
    private SendLinuxEmailImpl sendLinuxEmail;

    private static final String EMAIL_TEMPLATE_FILENAME = "emailTemplateMultipart";
    private static final String FROM = "IdealBusiness@ideallogicgroup.com";
    private static final String SUBJECT = "Ideal Business Password Reset";
    
    
    /**
     * This method sets up the email to be sent to users who
     * have lost their password
     * @param tempPassword the system-generated temporary password for the user
     * @param email the email address of the user
     * @param url the url that the lost password link in the email should link to
     * @param imagePath the directory where files are stored that will be served from disk
     * @return true if the email sent without error
     * @throws MessagingException 
     */
    public boolean sendLostPasswordEmail(String tempPassword, String email, String url, String imagePath) throws MessagingException, IOException {
        // get email template
        File emailTemplate = new File(imagePath + File.separator + "email", EMAIL_TEMPLATE_FILENAME);
        
        // set email address, temp password and url in the email
        String messageBody = createMessageBody(email, tempPassword, url, imagePath);
        
        // send email
        boolean isMsgSent = sendLinuxEmail.sendMessage(SUBJECT, messageBody, FROM, email, emailTemplate);
        
        return isMsgSent;
    }
    
    
    
    private String createMessageBody(String email, String tempPassword, String url, String imagePath) throws IOException{
        String MESSAGE_BODY = FileUtils.readFileToString(new File(imagePath + File.separator + "emailNotificationTexts", "passwordReset.html"));
        return MESSAGE_BODY.replace(":unencodedEmail", email).replace(":email", Helpful.encodeStringToBase64(email)).replace(":val", Helpful.encodeStringToBase64(tempPassword)).replace(":url", url);
    }
}
