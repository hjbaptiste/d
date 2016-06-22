
package com.homeclouddrive.service;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;


/**
 *
 * @author getjigy
 */
@Service
public class MoveFileOrFolderImpl {

    
    /**
     * This method moves a file or folder to a specified folder
     * @param pathOfFolderToMoveTo the absolute path of the folder to move the file to
     * @param pathOfFileToMove the absolute path of the file or folder to move
     * @return true if the operation completed successfully, false otherwise
     * @throws IOException 
     */
    
    public boolean moveFileOrFolder(String pathOfFolderToMoveTo, String pathOfFileToMove) throws IOException {
        
       // change file name
        File folderToMoveTo = new File(pathOfFolderToMoveTo);
        if(folderToMoveTo.exists() && folderToMoveTo.isDirectory()){
            // File (or directory) with new name
            File fileToMove = new File(pathOfFileToMove);

            if (fileToMove.exists()) {
                if (!fileToMove.isDirectory()) {
                    // move file
                    FileUtils.moveFile(fileToMove, folderToMoveTo);                    
                } else {
                    // move directory
                    FileUtils.moveDirectoryToDirectory(fileToMove, folderToMoveTo, false);
                }
                
                return new File(folderToMoveTo, fileToMove.getName()).exists();
            }                    
        }

        return false;
    }
    
}
