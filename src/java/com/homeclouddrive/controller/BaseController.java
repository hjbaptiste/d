
package com.homeclouddrive.controller;

import com.homeclouddrive.domain.User;
import com.jigy.api.Helpful;
import java.util.Enumeration;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author getjigy
 */
public class BaseController {
    
    /**
     * This method gets a cookie from the client and returns 
     * its value while also removing the cookie from the client
     * @param cookies the cookies from the client
     * @param cookieName the name of the cookie to fine
     * @param response the response object
     * @return the value of the cookie or null if it is not found
     */
    public static String getCookieValue(Cookie[] cookies, String cookieName, HttpServletResponse response) {
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if (cookieName.equals(cookie.getName())) {
                // remove cookie
                String val = cookie.getValue();
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                return val;
            }
        }
        return null;
    }
    
    
    
    /**
     * This method stores a user object into the user's session
     * @param request the request object
     * @param user the user object to store
     */
    protected void addUserToSession(HttpServletRequest request, User user) {
        request.getSession().setAttribute("user", user);
    }
    
    
    /**
     * This method clears the session
     * @param request 
     */
    protected void clearSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Enumeration<String> sessionAttributes = session.getAttributeNames();
        while(sessionAttributes.hasMoreElements()){
            session.removeAttribute(sessionAttributes.nextElement());
        }
    }
    
      
    
    /**
     * This method gets the user object from session
     * @param request the request object
     * @return the currently logged in administrator
     */
    public static User getUser(HttpServletRequest request) {
        return (User) Helpful.getObjFromSession(request, "user");
    }
    
    
    
    /**
     * This method shortens a lengthy word/name to something that
     * can fit within a given space but still be recognizable
     * @param name the name to shorten
     * @return the shortened name
     */
    public static String paraphrase(String name){
            if(name != null && name.length() > 14){
                return name.substring(0, 8) + "..." + name.substring(name.length() - 8, name.length());
            }
            
            return name;
        }
}
