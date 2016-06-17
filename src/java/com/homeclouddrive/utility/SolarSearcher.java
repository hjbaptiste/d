package com.homeclouddrive.utility;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author getjigy
 */
public class SolarSearcher {
    

    /**
     * This method performs a search on the Solar Search Engine
     * which listens by default on port 8983. If you do not have Solar
     * but do wish to allow searching with your application, please 
     * go to the Solar project's site and download Solar. The documentation
     * is clear and quite simple and you should have your search engine up
     * and running in no time
     * @param searchParams the parameters to search for
     * @return a byte array containing the json array of results passed back from the Solar engine
     * @throws IOException 
     */
    public static byte[] query(String searchParams) throws IOException {
        HttpClient client = new DefaultHttpClient();
        String url = "http://localhost:8983/solr/collection1/select/?";
        //http://localhost:8983/solr/collection1/select?q=form+1040&rows=50&fl=id%2Ctitle%2Csubject&wt=json&indent=true&hl=true&hl.fl=id%2Ctitle%2Csubject&hl.simple.pre=%3Cem%3E&hl.simple.post=%3C%2Fem%3E
        url = url + searchParams + "&rows=50&fl=id%2Ctitle%2Csubject&wt=json&indent=true&hl=true&hl.fl=id%2Ctitle%2Csubject&hl.simple.pre=%3Cem%3E&hl.simple.post=%3C%2Fem%3E";//"&wt=json&sort=score%20desc&hl=true&fl=id,title,subject&row=50&hl.fl=id,subject,title,content";
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        HttpEntity resEntity = response.getEntity();
        byte[] bytes = null;

        if (resEntity != null) {
          bytes = EntityUtils.toByteArray(resEntity);
          resEntity.consumeContent();
        }
        
        client.getConnectionManager().shutdown();
        
        return bytes;
    }
}
