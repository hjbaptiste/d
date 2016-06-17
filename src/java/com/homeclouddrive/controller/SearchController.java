
package com.homeclouddrive.controller;


import com.homeclouddrive.service.SearchServiceImpl;
import com.jigy.api.Helpful;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



/**
 *
 * @author getjigy
 */
@Controller
public class SearchController extends BaseController{  
    @Resource
    SearchServiceImpl searchService;
  
    
    
    
    /**
     * This method retrieves the search results (Requires the Solar Search Engine built on Lucene)
     * @param request the request object
     * @param response the response object
     * @return the Model And View object
     */
    @RequestMapping(value = "/search.html")
    public void search(HttpServletRequest request, HttpServletResponse response){
        String searchParams = request.getQueryString();       
        byte[] searchResults = null;
        try {
            searchResults = searchService.search(searchParams);
        } catch (IOException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Helpful.writeBytesToResponse(response, searchResults);
    }
}
