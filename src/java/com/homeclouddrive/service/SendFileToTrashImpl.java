
package com.homeclouddrive.service;

import com.homeclouddrive.dao.UserTrashDAOImpl;
import com.homeclouddrive.domain.UserTrash;
import com.homeclouddrive.exception.BaseException;
import java.io.File;
import java.io.IOException;
import javax.annotation.Resource;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author getjigy
 */
@Service
public class SendFileToTrashImpl {

    @Resource
    private UserTrashDAOImpl userTrashDAO;
    
    @Resource
    private RenameFileImpl renameFile;


    
    /**
     * This method sends a file to the trash by appending _delete
     * to the file and it's hidden file (if the file has been shared)
     * and share file directory
     * @param userHomeDirectory the home directory of the user
     * @param fileToSendToTrash the file to send to the trash
     * @param driveHome the main folder that all user folders sit inside of
     * @param idUser the id of the current user
     * @param context the context of the application (see context property of jdbc.properties file)
     * @return true if the operation succeeded, false otherwise
     * @throws IOException 
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public boolean sendFileToTrash(File userHomeDirectory, File fileToSendToTrash, File driveHome, Integer idUser, String context) throws IOException, BaseException {
        if(fileToSendToTrash.exists()){
            String isDirectory = fileToSendToTrash.isDirectory() ? "Y" : "N";
            String absolutePath = fileToSendToTrash.getAbsolutePath();
            String originalRelativePath = absolutePath.replace(driveHome.getAbsolutePath() + File.separator, "");
            String shareRelativePath = null;
            boolean isSuccess = false;
            File shareFileAbsoluteLocation = null;
            
            // rename original file to append _deleted to the file name
            isSuccess = renameFile.renameFile(absolutePath, absolutePath + "_deleted");
            
            // check to see if file has been shared and if so, delete the share directory and hidden file also
            File hiddenShareFile = new File(absolutePath + "_share");
            if(hiddenShareFile.exists()){
                String shareFileURL = FileUtils.readFileToString(hiddenShareFile);
                shareRelativePath = convertPath(shareFileURL, context);
                shareFileAbsoluteLocation = new File(driveHome.getAbsolutePath() + File.separator + convertPath(shareFileURL, context));
                
                // rename hidden file and share directory
                isSuccess = isSuccess && renameFile.renameFile(hiddenShareFile.getAbsolutePath(), hiddenShareFile.getAbsolutePath() + "_deleted");
                if(shareFileAbsoluteLocation.exists()){
                    isSuccess = isSuccess && renameFile.renameFile(shareFileAbsoluteLocation.getParentFile().getAbsolutePath(), shareFileAbsoluteLocation.getParentFile().getAbsolutePath() + "_deleted");
                }
                
            }
            
            // create database trash record
            int primaryKey = userTrashDAO.createUserTrash(initializeUserTrash(originalRelativePath, shareRelativePath, idUser, isDirectory));
            
            if((isSuccess && primaryKey > 0) == false){
                // rollback deletions and throw exception
                isSuccess = renameFile.renameFile(absolutePath + "_deleted", absolutePath);
                if(hiddenShareFile.exists()){
                    renameFile.renameFile(hiddenShareFile.getAbsolutePath() + "_deleted", hiddenShareFile.getAbsolutePath());
                    renameFile.renameFile(shareFileAbsoluteLocation.getParentFile().getAbsolutePath() + "_deleted", shareFileAbsoluteLocation.getParentFile().getAbsolutePath());
                }
                throw new BaseException("Deletion Failed");
            }
            return isSuccess && primaryKey > 0;
        }
        
        return false;
        
        
    }
    
    
    private UserTrash initializeUserTrash(String originalRelativePath, String shareRelativePath, Integer idUser, String isDirectory){

        // create user trash
        UserTrash userTrash = new UserTrash();
        userTrash.setIdUser(idUser);
        userTrash.setOriginalRelativePath(originalRelativePath);
        userTrash.setShareRelativePath(shareRelativePath);
        userTrash.setIsDirectory(false);
        
        return userTrash;
    }
    
    
    protected String convertPath(String path, String context){
        return path.replace(context, "").replace("/resource/", "").replace("/", "\\");
    }
    
}
