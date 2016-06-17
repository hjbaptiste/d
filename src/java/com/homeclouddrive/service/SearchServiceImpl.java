
package com.homeclouddrive.service;

import com.homeclouddrive.utility.SolarSearcher;
import java.io.IOException;
import org.springframework.stereotype.Service;


/**
 *
 * @author getjigy
 */
@Service
public class SearchServiceImpl {
    
    
    /**
     * This method performs a search thru the solar search utility (Please Google Solar Search)
     * @param searchParams the params to search for
     * @return a byte array containing a json object with the search results
     * @throws IOException 
     */
    public byte[] search(String searchParams) throws IOException{
        return SolarSearcher.query(searchParams);
    }
}
