package com.homeclouddrive.utility;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author getjigy
 */
public class HttpRequester {
    

    /**
     * This method sends a request to the passed-in url and appends the parameters
     * from the passed in map to the request
     * @param url the url to send the request to
     * @param params the parameters to add to the request 
     * @param isPost true if the request should be a post, false if it should be a get
     * @param encodeParams true if the parameters on the request should be encoded, false otherwise
     * @return the response is converted into a byte array and returned
     * @throws IOException 
     */
    public static byte[] request(String url, Map<String, String> params, boolean isPost, boolean encodeParams) throws IOException {
        HttpClient client = new DefaultHttpClient();
        if(params != null && !params.isEmpty()){
            url = url + "?" + createParamString(params, encodeParams);
        }
        
        HttpResponse response = isPost ? client.execute(new HttpPost(url)) : client.execute(new HttpGet(url));
        HttpEntity resEntity = response.getEntity();
        byte[] bytes = null;

        if (resEntity != null) {
          bytes = EntityUtils.toByteArray(resEntity);
          EntityUtils.consume(resEntity);
        }
        
        client.getConnectionManager().shutdown();
        
        return bytes;
    }
    
    
    
    
    /**
     * This method sends a request to the passed in url and appends the parameters
     * from the passed in map to the request
     * @param url the url to send the request to
     * @param params the parameters to add to the request 
     * @param isPost true if the request should be a post, false if it should be a get
     * @param encodeParams true if the parameters on the request should be encoded, false otherwise
     * @return the response is converted into a String and returned
     * @throws IOException 
     */
    public static String requestString(String url, Map<String, String> params, boolean isPost, boolean encodeParams) throws IOException {
        HttpClient client = new DefaultHttpClient();
        if(params != null && !params.isEmpty()){
            url = url + "?" + createParamString(params, encodeParams);
        }
        
        HttpResponse response = isPost ? client.execute(new HttpPost(url)) : client.execute(new HttpGet(url));
        HttpEntity resEntity = response.getEntity();
        String responseStr = null;

        if (resEntity != null) {
          responseStr = EntityUtils.toString(resEntity);
          EntityUtils.consume(resEntity);
        }
        
        client.getConnectionManager().shutdown();
        
        return responseStr;
    }
    
    
    
    /**
     * This method converts the items in the map into
     * name value pairs and to be added to the request
     * @param params the map of parameters to convert
     * @param encodeParams true if the parameters should be encoded, false otherwise
     * @return a string of properly formatted name value pairs
     */
    private static String createParamString(Map<String, String> params, boolean encodeParams) {
        StringBuilder postData = new StringBuilder();

        // get params from map and add to post string
        if(encodeParams){
            for (String str : params.keySet()) {
                postData.append(URLEncoder.encode(str));
                postData.append("=");
                postData.append(params.get(URLEncoder.encode(str)));
                postData.append("&");
            }
        } else {
            for (String str : params.keySet()) {
                postData.append(str);
                postData.append("=");
                postData.append(params.get(str));
                postData.append("&");
            }
        }
        
        return postData.substring(0, postData.length() - 1).toString();
    }
}
