
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
public class CreateFileInFolderImpl {

    
    /**
     * This method creates a new folder within the current folder location
     * @param currentFolderLocation the folder to add the new folder to
     * @param newFolderName the name of the new folder to add
     * @return the newly created folder
     * @throws IOException 
     */
    
    public File createFileInFolder(String currentFolderLocation, String newFolderName) throws IOException {

        File currentFolder = new File(currentFolderLocation);
        if(currentFolder.exists() && currentFolder.isDirectory()){
            // File (or directory) with new name
            File folderToCreate = new File(currentFolder, newFolderName);

            if (!folderToCreate.exists()) { 
                folderToCreate.mkdir();
                return folderToCreate;
            } else {
                return null;
            }                 
        }

        return null;
    }
    
}
