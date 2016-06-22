
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
public class RenameFileImpl {

    
    /**
     * This method renames a file. The absolute file path
     * of the file to rename and the absolute file path of the new
     * file name are required to complete the renaming
     * @param pathOfFileToRename
     * @param pathOfNewName
     * @return true if the operating completed successfully, false otherwise
     * @throws IOException 
     */
    public boolean renameFile(String pathOfFileToRename, String pathOfNewName) throws IOException {
        
       // change file name
        File file = new File(pathOfFileToRename);
        if(file.exists()){
            // File (or directory) with new name
            File file2 = new File(pathOfNewName);

            if (file2.exists()){
               throw new java.io.IOException("File already exists");
            }

            if(!file.isDirectory()){
                // Rename file
                return file.renameTo(file2);
            } else {
                // rename directory
                FileUtils.copyDirectory(file, file2, true);
                
                if (file2.exists()){
                    FileUtils.deleteDirectory(file);
                    return true;
                }
                return false;
            }            
        }

        return false;
    }
    
}
