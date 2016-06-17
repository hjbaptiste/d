
package com.homeclouddrive.service;

import com.homeclouddrive.exception.BaseException;
import com.jigy.api.security.SymmetricEncryption;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;


/**
 *
 * @author getjigy
 */
@Service
public class ShareFileImpl {


    
    /**
     * This method creates a share directory for a file, copies the file
     * to the share directory and creates a hidden share file
     * @param fileToShare the file to share
     * @param shareDirectory the main share directory
     * @param driveHome the main drive directory
     * @param context the context of the application (see context property in jdbc.properties file)
     * @return the shareable url to download the shared file
     * @throws IOException
     * @throws BaseException 
     */
    public String shareFile(File fileToShare, File shareDirectory, File driveHome, String context) throws IOException, BaseException {
        if(fileToShare.exists() && fileToShare.isFile()){
            String shareFileLocation = createShareFile(fileToShare, shareDirectory);
            if(shareFileLocation != null){
                return createHiddenFile(fileToShare, shareFileLocation, driveHome, context);
            }
            return null;
        }
        
        return null;
        
        
    }
    
    
    
    /**
     * This method creates a temp dir with a 20-character randomly-generated
     * name and copies the file into the directory
     * @param fileToShare the file to share
     * @param shareDirectory the main share directory
     * @return the absolute path of the shared file
     * @throws IOException 
     */
    private String createShareFile(File fileToShare, File shareDirectory) throws IOException{
        // create temp share directory
        String tempShareDirName = SymmetricEncryption.generateKey().substring(0, 20).replace("\\", "y")
                .replace("/", "z").replace("+", "k").replace(" ", "U").replace(":", "9").replace("*", "3")
                .replace("?", "6").replace("\"", "0").replace("<", "B").replace(">", "s").replace("|", "d");
        File tempShareDir = new File(shareDirectory, tempShareDirName);
        File shareFile = new File(tempShareDir, fileToShare.getName());
        
        if(!tempShareDir.exists() && tempShareDir.mkdir()){
            FileUtils.copyFile(fileToShare, shareFile);
            return shareFile.getAbsolutePath();
        }        
        
        return null;
    }
    
    
    /**
     * This method creates the hidden file that contains the location of
     * the share directory and returns the download url of the share file
     * @param fileToShare the file to share
     * @param shareFileLocation the absolute path to the shared file
     * @param driveHome the main drive directory
     * @param context the context of the application (see context property of the jdbc.properties file)
     * @return the shareable url to download the shared file
     * @throws IOException 
     */
    private String createHiddenFile(File fileToShare, String shareFileLocation, File driveHome, String context) throws IOException{
        File shareParent = fileToShare.getParentFile();
        File hiddenFile = new File(shareParent, fileToShare.getName() + "_share");
        String downloadUrl = shareFileLocation.replace(driveHome.getAbsolutePath(), context + "/resource").replace("\\", "/");
        FileUtils.writeStringToFile(hiddenFile, downloadUrl);
        
        return hiddenFile.exists() ? downloadUrl : null;
    }
    
}
