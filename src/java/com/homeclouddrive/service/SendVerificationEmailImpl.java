
package com.homeclouddrive.service;

import com.homeclouddrive.domain.Role;
import com.jigy.api.Helpful;
import java.io.File;
import java.io.IOException;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class SendVerificationEmailImpl {

    
    /**
     *
     * @author getjigy
     */
    @Resource
    private SendLinuxEmailImpl sendLinuxEmail;
    
    private static final String EMAIL_TEMPLATE_FILENAME = "emailTemplate";
    private static final String FROM = "IdealBusiness@ideallogicgroup.com";
    private static final String SUBJECT = "Complete Your Ideal Business Registration";
    private static final String BUSINESS_MESSAGE_BODY = "<html>Hi!<br> Welcome to <b>Ideal Business</b>! Click on the link below "
            + "to activate your account. If the link is not working, right-click the link and select 'Copy Link Address', paste the link into your browser "
            + "and click enter.<br><br>  <a href=':url/verifyEmail.html?email=:email&val=:val'>Click here to validate your email address</a><br><br> "
            + "Thanks again for choosing <b>Ideal Business</b>! <br><br> __ <br><br><p style='font-size:10px'>Please Note: this email message was sent from a notification-only address.</html>";
    private static final String PROMOTER_MESSAGE_BODY = "<html>Hi!<br> Welcome to <b>Ideal Business</b>! Click on the link below "
            + "to activate your account. If the link is not working, right-click the link and select 'Copy Link Address', paste the link into your browser "
            + "and click enter.<br><br>  <a href=':url/verifyEmail.html?email=:email&val=:val&invite=:invite'>Click here to validate your email address</a><br><br> "
            + "Thanks again for choosing <b>Ideal Business</b>! <br><br> __ <br><br><p style='font-size:10px'>Please Note: this email message was sent from a notification-only address.</html>";

    
    
    /**
     * This method sets up the message that will be sent to 
     * verify a user's email address
     * @param tempPassword the system-generated temporary password for the user
     * @param email the email address of the user
     * @param url the url that the lost password link in the email should link to
     * @param imagePath the path to the email folder
     * @param invitationUID the uid of the promoter's invitation
     * @param role the role of the user
     * @return true if the email sent without error
     * @throws MessagingException 
     */
    public boolean sendVerificationEmail(String tempPassword, String email, String url, String imagePath, String invitationUID, String role) throws MessagingException, IOException {
        // get email template
        File emailTemplate = new File(imagePath + File.separator + "email", EMAIL_TEMPLATE_FILENAME);
        
        // set email address, temp password and url in the email
        String messageBody = createMessageBody(email, tempPassword, url, invitationUID, role);
        
        // send email
        boolean isMsgSent = sendLinuxEmail.sendMessage(SUBJECT, messageBody, FROM, email, emailTemplate);
        
        return isMsgSent;
    }
    
    
    /**
     * This method writes custom information into the message template
     * @param email the email address of the user
     * @param tempPassword the temporary password
     * @param url the url that the lost password link in the email should link to
     * @param role the role of the user
     * @return the message to be sent
     */
    private String createMessageBody(String email, String tempPassword, String url, String invitationUID, String role){
        if(Role.Standard.equals(role)){
            return BUSINESS_MESSAGE_BODY.replace(":unencodedEmail", email).replace(":email", Helpful.encodeStringToBase64(email)).replace(":val", Helpful.encodeStringToBase64(tempPassword)).replace(":url", url);
        } else if(Role.Admin.equals(role)){
            return PROMOTER_MESSAGE_BODY.replace(":unencodedEmail", email).replace(":email", Helpful.encodeStringToBase64(email)).replace(":val", Helpful.encodeStringToBase64(tempPassword)).replace(":url", url).replace(":invite", invitationUID);
        }
        
        return null;
    }
}
