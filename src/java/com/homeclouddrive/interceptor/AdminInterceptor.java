package com.homeclouddrive.interceptor;

import com.homeclouddrive.domain.Role;
import com.homeclouddrive.domain.User;
import com.jigy.api.Helpful;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author getjigy
 */
public class AdminInterceptor implements HandlerInterceptor {
    
    
    
    /**
     * This method is called before the request is sent to the controller method. We're using
     * this method in the same way a before aspect could be used to handle cross cutting concerns.
     * Session validation/login can be enforced here to make sure a user cannot access functionality
     * that requires user information without having a valid session. An interceptor was used here
     * instead of an aspect since spring doesn't support aspects on annotated controllers that do not
     * implement a controller interface. All pre-processing code that addresses cross-cutting 
     * functionality should go here
     *
     * @param request the request object
     * @param response the response object
     * @param handler a handle to the controller
     * @return true to allow the request to proceed, false to halt the request
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // validate user session when attempting to access pages
        String servletPath = request.getServletPath();
        // place all links in the if statement that you do not want users to be able to access without loggin in
        if (servletPath.endsWith("displayBusinessDashboard.html") || servletPath.endsWith("businessExportData.html")
                || servletPath.endsWith("displayEventPromotions.html") || servletPath.endsWith("editPromotion.html")
                || servletPath.endsWith("updateEventPromotion.html") || servletPath.endsWith("addEventPromotion.html")
                || servletPath.endsWith("createEventPromotion.html") || servletPath.endsWith("displayScannerDevices.html")
                || servletPath.endsWith("addDevice.html") || servletPath.endsWith("displayMyPromoters.html")
                || servletPath.endsWith("displayAddPromoter.html") || servletPath.endsWith("addPromoter.html")
                || servletPath.endsWith("updateEventPromotion.html") || servletPath.endsWith("addPromoter.html")
                || servletPath.endsWith("setOnScanDevicesPageBefore.html") || servletPath.endsWith("setOnMyPromotersPageBefore.html")
                || servletPath.endsWith("setOnEventPromotionsPageBefore.html") || servletPath.endsWith("setOnDashboardPageBefore.html")
                || servletPath.endsWith("setOnAddPromoterPageBefore.html") || servletPath.endsWith("setCreatedPromotionBefore.html")
                || servletPath.endsWith("pollEmailCountForBusiness.html")) {
            
            // make sure the user has a valid session
            User user = (User) Helpful.getUser(request);
            if (!isStandardUser(user)) {
                // when the session is not valid (eg. session timed out, etc.) redirect user to index page
                response.sendRedirect("displayLogin.html");
		return false;
            }
        } else if (servletPath.endsWith("displayPromoterPromotions.html") || servletPath.endsWith("displayPromoDetail.html")
                || servletPath.endsWith("displayDashboard.html") || servletPath.endsWith("pollEmailCountForPromoter.html") 
                || servletPath.endsWith("pollForEventPromotions.html") || servletPath.endsWith("updatePaypalInfo.html")
                || servletPath.endsWith("displayPaypalInfo.html")) {
            
            // make sure the user has a valid session
            User user = (User) Helpful.getUser(request);
            if (!isAdminUser(user)) {
                // when the session is not valid (eg. session timed out, etc.) redirect user to index page
                response.sendRedirect("displayLogin.html");
		return false;
            }
        } else if (servletPath.endsWith("displayResetPassword.html") || servletPath.endsWith("changePassword.html")) {
            
            // make sure the user has a valid session
            if (!Helpful.isUserLoggedIn(request)) {
                // when the session is not valid (eg. session timed out, etc.) redirect user to index page
                response.sendRedirect("displayLogin.html");
		return false;
            }
        }
        
        return true;
    }
    

    
    /**
     * This method is called after the controller method has processed the
     * request
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        //System.out.println("Post-handle");
    }

    
    
    /**
     * This method is called after the view has been rendered. This method could
     * be used to handle processing of information that does not necessarily need to be
     * performed in the normal order of the request and can be handled in the background.
     * For example, when saving an event with a new file attached to it, the event information
     * could be saved and then control returned to the page then use this method to handle storage
     * of the file in the background. So in general, any long process that does not have to 
     * be performed during the normal request processing should probably be handled here
     * 
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //System.out.println("After completion handle");
    }
    
    
    
    private boolean isStandardUser(User user){
        if(user == null){
            return false;
        } else if(!Role.Standard.equals(user.getRole())){
            return false;
        } 
        
        return true;
    }

    
    
    private boolean isAdminUser(User user){
        if(user == null){
            return false;
        } else if(!Role.Admin.equals(user.getRole())){
            return false;
        }  
        
        return true;
    }
}
