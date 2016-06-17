
package com.homeclouddrive.servlet;


import com.jigy.api.Helpful;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author getjigy
 */
@SuppressWarnings("serial")
public class ResourceServlet extends HttpServlet {

    // Constants ----------------------------------------------------------------------------------

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    // Properties ---------------------------------------------------------------------------------

    private String imagePath;

    // Actions ------------------------------------------------------------------------------------

    public void init() throws ServletException {

        // Define base path somehow. You can define it as init-param of the servlet.
        this.imagePath = null;

    }

    
    /**
     * This method handles serving files from the disk system
     * @param request the request object
     * @param response the response object
     * @throws ServletException
     * @throws IOException 
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        // set the base image path
        this.imagePath = Helpful.getProperty(request, "jdbc.properties", "drive.home");
        
        // Get requested image by path info.
        String requestedImage = URLDecoder.decode(request.getPathInfo()); 

        // Check if file name is actually supplied to the request URI.
        if (requestedImage == null){ //|| deny(requestedImage)) {
            // Do your thing if the image is not supplied to the request URI.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Decode the file name (might contain spaces and so on) and prepare file object.
        File image = new File(imagePath, URLDecoder.decode(requestedImage, "UTF-8"));

        // Check if file actually exists in filesystem.
        if (!image.exists()) {
            // Do your thing if the file appears to be non-existing.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
        
        // check for parameters on the request so we can track email open rates
        //String idPromotionalOfferString = (String) request.getParameter("y");

        // Get content type by filename.
        String contentType = getServletContext().getMimeType(image.getName());

        // Check if file is actually an image (avoid download of other files by hackers!).
        // For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
        if (image.isHidden()) {
            // Do your thing if the file appears not to be  a real image.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Init servlet response.
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(image.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + image.getName() + "\"");

        // Prepare streams.
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open streams.
            input = new BufferedInputStream(new FileInputStream(image), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            // Gently close streams.
            close(output);
            close(input);
            
            // if sending an image to be displayed in a promotional mailing, count it
//            if(!Helpful.isEmpty(idPromotionalOfferString)){
//                Integer idPromotionalOffer = Integer.parseInt(idPromotionalOfferString);
//                ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
//                PromotionalOfferDAOImpl promotionalOfferDAO = (PromotionalOfferDAOImpl) context.getBean("promotionalOfferDAOImpl");
//                int update = promotionalOfferDAO.updateIsEmailOpened(idPromotionalOffer);
//            }       
        }
    }

    // Helpers (can be refactored to public utility class) ----------------------------------------

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
            }
        }
    }
    
    
    
    /**
     * Blacklist these folders in the resource directory
     * @param requestedImage the path to the file that will be downloaded
     * @return true if the request should be denied, false otherwise
     */
    private static boolean deny(String requestedImage){
        if(Helpful.isEmpty(requestedImage)){
            return true;
        } else if(requestedImage.contains("EclipseTemplateProject")){
            return true;
        } else if(requestedImage.contains("NetbeansTemplateProject")){
            return true;
        } else if(requestedImage.contains("NetbeansTemplateProjectFiles")){
            return true;
        } else {
            return false;
        }
    }
}
