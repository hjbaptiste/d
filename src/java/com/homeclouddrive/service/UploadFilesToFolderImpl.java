
package com.homeclouddrive.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author getjigy
 */
@Service
public class UploadFilesToFolderImpl {

    
    /**
     * This method uploads files to a directory
     * @param pathOfFolderToUploadTo the directory to upload the files to
     * @param filesMap the file container
     * @return the uploaded file
     * @throws IOException 
     */
    
    public File uploadFilesToFolder(String pathOfFolderToUploadTo, Map<String, MultipartFile> filesMap) throws IOException {
        
        List<File> fileList = new ArrayList();
        File folderToUploadTo = new File(pathOfFolderToUploadTo);
        if(folderToUploadTo.exists() && folderToUploadTo.isDirectory()){
            for(String fileName : filesMap.keySet()){
                MultipartFile file = filesMap.get(fileName);
                File writeFile = new File(folderToUploadTo, fileName);
                if(writeFile.exists()){
                    writeFile = getNewNonUsedFileName(writeFile);
                }
                FileUtils.copyInputStreamToFile(file.getInputStream(), writeFile);
                fileList.add(writeFile);
            }
            
            boolean areFilesWritten = true;
            for(File file : fileList){
                areFilesWritten = areFilesWritten && file.exists();
            }
            
            if(areFilesWritten){
                return fileList.get(0);
            }
        }

        return null;
    }
    
    
    private File getNewNonUsedFileName(File writeFile){
        String fileName = writeFile.getName();
        boolean isFileNameUnique = false;
        for(int i = 1; !isFileNameUnique; i++){
            fileName = fileName + "_" + i;
            File uniqueFile = new File(writeFile.getParentFile(), fileName);
            if(!uniqueFile.exists()){
                return uniqueFile;
            }
        }
        
        return null;
    }
    
}
