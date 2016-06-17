
package com.homeclouddrive.controller;


import com.homeclouddrive.domain.User;
import com.jigy.api.Helpful;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;



/**
 *
 * @author getjigy
 */
@Controller
public class UploadController extends BaseController {  

    /**
     * This method accepts a file and stores it to the file system
     * @param request the request object
     * @param response the response object
     */
    @RequestMapping(value = "/uploadFile.html", method = RequestMethod.POST)
    public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("file");
        
        // check image dimensions
        BufferedImage image = ImageIO.read(multipartFile.getInputStream());
        Integer width = image.getWidth();
        Integer height = image.getHeight();
        if(width < 265 || height < 265){
            Helpful.writeToResponse(response, "ERROR, Photo Must Have a Height and Width Greater than 265px");
        } else {
       
            // create file locations
            User user = (User) Helpful.getUser(request);
            String userId = user != null ? user.getIdUser() + File.separator : "";
            String imageLocation = Helpful.getProperty(request, "jdbc.properties", "imagePath") + File.separator + userId + multipartFile.getOriginalFilename();
            boolean isImage = checkIfFileIsImage(multipartFile.getOriginalFilename());
            if(isImage){
                // create the file
                File file = new File(imageLocation);
                File parent = new File(file.getParent());
                FileOutputStream output = null;

                try {

                    // if the file does not already exist, create it
                    if (!parent.exists() && !parent.mkdirs()) {
                        throw new IllegalStateException("Couldn't create dir: " + parent);
                    }

                    // delete any files that may be in the directory first in case the user is
                    // attempting to replace a previous image
                    deleteFiles(parent);

                    // create the file we are about to store the multipart file into
                    if(!file.createNewFile()){
                        throw new IllegalStateException("Couldn't create dir: " + parent);
                    }

                    // write file to file system
                    if (!multipartFile.isEmpty()) {
                        byte[] bytes = multipartFile.getBytes();
                        output = new FileOutputStream(file);
                        IOUtils.write(bytes, output);
                    }


                    // send response
                    Helpful.writeToResponse(response, "SUCCESS," + "resource" + (user != null ? "/" +  user.getIdUser() : "") + "/" + file.getName());
                } catch (IOException ex) {
                    Helpful.writeToResponse(response, "ERROR," + ex.getMessage());
                } finally {
                    if(output != null){
                        IOUtils.closeQuietly(output);
                    }
                }
            }
        }
    }
    
    
    
    /**
     * This method returns the extension of a file
     * @param fileName the name of the file (including extension)
     * @return the extension of the file
     */
    private String getFileExtension(String fileName){
        if(fileName.contains(".")){
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }
    
    
    
    /**
     * This method checks to see if the passed-in file is a jpg, gif or png
     * @param fileName the name of the file (including extension)
     * @return if the file is an image or not
     */
    private boolean checkIfFileIsImage(String fileName){
        if(fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif")){
            return true;
        }
        
        return false;
    }
    
    
    
    /**
     * This method deletes all files within a folder
     * @param folder the folder to delete the files within
     */
    private void deleteFiles(File folder){
        if(folder.exists()){
            for(File file : folder.listFiles()){
                file.delete();
            }
        }
    }
}
