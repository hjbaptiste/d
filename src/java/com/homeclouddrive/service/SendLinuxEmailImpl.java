package 

com.homeclouddrive.service;

import com.jigy.api.security.SymmetricEncryption;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author getjigy
 */
@Service
public class SendLinuxEmailImpl {

    // to is a var args field. If it is empty, it means that someone is attempting to contact customer service so
    // we will fill the "to" field in with the customer service info. If it is not empty, it means
    // that a message needs to go out to a user and the email has been included so we'll use that one
    /**
     * This method handles the actual sending of an email
     *
     * @param subject the subject of the email
     * @param messageBody the body of the email
     * @param from who the email is coming from
     * @param to who the email will be sent to
     * @param emailTemplate the email template file inside of the email folder on the server
     * @return true if the email was sent without error
     */
    public boolean sendMessage(String subject, String messageBody, String from, String to, File emailTemplate) throws IOException {
        // get email template
        String emailTemplateString = FileUtils.readFileToString(emailTemplate);

        // set to, from, subject and message body in the email
        String email = emailTemplateString.replace("???email???", to).replace("???subject???", subject)
                .replace("???messageBody???", messageBody).replace("???from???", from);

        // create random string to make email file unique. This will prevent email services 
        // from colliding with one another
        String random = SymmetricEncryption.generateKey().substring(0, 10).replace(" ", "").replace(File.separator, "").replace("\0", "");

        // write email file to disk
        String parentFolder = emailTemplate.getParent() + File.separator + "idealBusinessEmails" + File.separator;
        String emailName = random + ".sh";
        File emailFile = new File(parentFolder, emailName);
        FileUtils.writeStringToFile(emailFile, email);

        // make email file executable
        emailFile.setExecutable(true);
        if (emailFile.canExecute()) {

            // execute email file to send email
            try {
                ProcessBuilder pb = new ProcessBuilder();
                //pb.directory(emailTemplate.getParentFile());
                pb.command(parentFolder + emailName);
                pb.redirectOutput(new File(emailTemplate.getParentFile().getAbsolutePath(), "emailOutputFile"));
                pb.start();
                // set shell commands
                //String makeFileExecCommand = "chmod a+x " + parentFolder + emailName;
                // String sendEmailCommand = parentFolder + emailName;

                // execute shell commands
                //Process modify = Runtime.getRuntime().exec(makeFileExecCommand);
                //Process p = Runtime.getRuntime().exec(sendEmailCommand);

//            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String line = null;
//            while ((line = in.readLine()) != null) {
//                System.out.println(line);
//            }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        // delete email file
            //FileUtils.deleteQuietly(emailFile);
            return true;
        }

        return false;
    }

}
