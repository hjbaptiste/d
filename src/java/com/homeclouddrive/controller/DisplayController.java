package com.homeclouddrive.controller;

import com.jigy.api.Helpful;
import com.jigy.api.security.SymmetricEncryption;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author getjigy
 */
@Controller
public class DisplayController extends BaseController {

    /**
     * This method displays the index page 
     *
     * @param request the request object
     * @param response the response object
     * @return the Model And View object
     */
    @RequestMapping(value = "/index.html")
    public ModelAndView displayIndex(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("index");
    }

    /**
     * This method displays the error page
     * @param request the request object
     * @param response the response object
     * @return the model and view object
     */
    @RequestMapping(value = "/displayErrorPage.html")
    public ModelAndView displayErrorPage(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("error404");
    }

    @RequestMapping(value = "/displayContact.html")
    public ModelAndView displayContact(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("contact");
    }
    
    @RequestMapping(value = "/displayFaq.html")
    public ModelAndView displayFaq(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("faq");
    }

    @RequestMapping(value = "/displayPageProfile.html")
    public ModelAndView displayPageProfile(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("page-profile");
    }    
    
    @RequestMapping(value = "/displayPromotionValid.html")
    public ModelAndView displayPromotionValid(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("promotionValid");
    }
    
    @RequestMapping(value = "/displayPromotionInvalid.html")
    public ModelAndView displayPromotionInvalid(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("promotionInvalid");
    }
    
    @RequestMapping(value = "/displaySignUp.html")
    public ModelAndView displaySignUp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("signUp");
    }
    
    @RequestMapping(value = "/displayLogin.html")
    public ModelAndView displayLogin(HttpServletRequest request, HttpServletResponse response) {
        // add cookie to form to prevent XSS attack
        String loginCookieStr = SymmetricEncryption.generateKey().substring(0, 20);
        Cookie loginCookie = new Cookie("loginCookie", loginCookieStr);
        Helpful.setObjInSession(request, "loginCookie", loginCookieStr);
        response.addCookie(loginCookie);
        return new ModelAndView("login", "loginCookie", loginCookieStr);
    }
    
    @RequestMapping(value = "/displayResetPassword.html")
    public ModelAndView displayResetPassword() {
        return new ModelAndView("resetPassword");
    }
    
    @RequestMapping(value = "/displayTermsOfUse.html")
    public ModelAndView displayTermsOfUse() {
        return new ModelAndView("termsOfUse");
    }
    
    @RequestMapping(value = "/displayPrivacyPolicy.html")
    public ModelAndView displayPrivacyPolicy() {
        return new ModelAndView("privacyPolicy");
    }
    
    @RequestMapping(value = "/displayPromoterAgreement.html")
    public ModelAndView displayPromoterAgreement() {
        return new ModelAndView("promoterAgreement");
    }
    
    @RequestMapping(value = "/displayHowItWorks.html")
    public ModelAndView displayHowItWorks(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("howItWorks");
    }
    
    @RequestMapping(value = "/displayPageappCoverpage.html")
    public ModelAndView displayPageappCoverpage(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("pageapp-coverpage");
    }
    
    @RequestMapping(value = "/displayPageappTimeline.html")
    public ModelAndView displayPageappTimeline(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("pageapp-timeline");
    }
    
    @RequestMapping(value = "/displayPageappTimeline2.html")
    public ModelAndView displayPageappTimeline2(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("pageapp-timeline2");
    }
    
    @RequestMapping(value = "/displayIndexAjaxLoad.html")
    public ModelAndView displayIndexAjaxLoad(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("index_ajaxLoad");
    }

}
