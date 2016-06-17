package com.homeclouddrive.controller;

import com.homeclouddrive.domain.Role;
import com.homeclouddrive.domain.User;
import com.homeclouddrive.exception.BaseException;
import com.homeclouddrive.json.JsonReturnObj;
import com.homeclouddrive.service.CreateUserImpl;
import com.homeclouddrive.service.LoginUserServiceImpl;
import com.homeclouddrive.service.RetrieveLostPasswordImpl;
import com.homeclouddrive.service.SendLostPasswordEmailImpl;
import com.homeclouddrive.service.SendVerificationEmailImpl;
import com.homeclouddrive.service.UpdateUserPasswordImpl;
import com.homeclouddrive.service.ValidateUserAccountImpl;
import com.jigy.api.Helpful;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author getjigy
 */
@Controller
public class LoginController extends BaseController {

    @Resource
    LoginUserServiceImpl loginUserService;

    @Resource
    private CreateUserImpl createUser;

    @Resource
    private ValidateUserAccountImpl validateUserAccount;

    @Resource
    private SendVerificationEmailImpl sendVerificationEmail;

    @Resource
    private RetrieveLostPasswordImpl retrieveLostPassword;

    @Resource
    private SendLostPasswordEmailImpl sendLostPasswordEmail;

    @Resource
    private UpdateUserPasswordImpl updateUserPassword;


    private static final String PROMOTER_SIGN_UP_SUCCESS = "Registration Successful. Click <br><a href='???loginUrl???' style='text-decoration: underline'>Go to Login</a> to access your account.";
    private static final String SIGN_UP_SUCCESS = "Registration Successful.";
    private static final String LOGIN_ERROR = "UserName/Password combination incorrect";
    private static final String VERIFY_EMAIL_SEND_FAIL = "Verification Email Send Failed";
    private static final String RETRIEVE_LOST_PASSWORD_SUCCESS = "SUCCESS, An email has been sent to the address provided.<br> Check your email to complete the recovery process.";

    /**
     * This method is called to log a user in
     *
     * @param request the request object
     * @param response the response object
     * @return returns a json object
     */
    @RequestMapping(value = "/login.html")
    public @ResponseBody JsonReturnObj loginUser(HttpServletRequest request, HttpServletResponse response)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // get the email address and password from the request
        String userName = Helpful.toStringSafe(request.getParameter("userName"));
        String password = Helpful.toStringSafe(request.getParameter("password"));        
        JsonReturnObj jsonReturnObj = new JsonReturnObj();
        String error = "";
//        String loginCookie = Helpful.toStringSafe(request.getParameter("loginCookie"));
//        String storedLoginCookie = null;

        // get login cookie
//        Cookie[] cookies = request.getCookies();
//
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("loginCookie")) {
//                    storedLoginCookie = cookie.getValue();
//                    break;
//                }
//            }
//        }

//        if (storedLoginCookie == null || (storedLoginCookie != null && !storedLoginCookie.equals(loginCookie))) {
//            error = "Something went wrong. Please try again. If the error continues, please report error to site administrator.";
//        } 
        
        if (Helpful.isEmpty(userName)) {
            error = "Please Enter a User Name";
        } else if (Helpful.isEmpty(password)) {
            error = "Please Enter a Password";
        }
        

        if (!Helpful.isEmpty(error)) {
            jsonReturnObj.setIsError(true);
            jsonReturnObj.setErrorMessage(error);
            return jsonReturnObj;
        } else {

            // log the user in
            Map userMap = null;
            User user = null;
            try {
                String encKey = Helpful.getProperty(request, "jdbc.properties", "jdbc.enckey");
                userMap = loginUserService.loginUser(userName, password, encKey);
            } catch (BaseException ex) {
                jsonReturnObj.setIsError(true);
                jsonReturnObj.setErrorMessage(ex.getReason());
                return jsonReturnObj;
            }

            // if the user is null then the login failed and we are 
            // returning an error message to the user
            if (userMap == null) {
                jsonReturnObj.setIsError(true);
                jsonReturnObj.setErrorMessage(LOGIN_ERROR);
                return jsonReturnObj;
            } else {
                // set user, business and promoter objects in session
                user = (User) userMap.get("user");
                super.addUserToSession(request, user);


                // remove login token from session
                request.getSession().removeAttribute("loginCookie");

                jsonReturnObj.setIsError(false);
                if (Role.Standard.equals(user.getRole())) {
                    jsonReturnObj.setSuccessMessage("getDirs.html");
                } else if (Role.Admin.equals(user.getRole())) {
                    jsonReturnObj.setSuccessMessage("displayUserAccounts.html");                  
                } 

                return jsonReturnObj;
            }
        }
    }

    /**
     * This method signs a user out of the application
     *
     * @param request the request object
     * @param response the response object
     * @return the Model And View object
     */
    @RequestMapping(value = "/signOut.html")
    public ModelAndView signOut(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return new ModelAndView("login");
    }

    /**
     * This method is called to validate the first slide on the sign up page
     *
     * @param request the request object
     * @param response the response object
     * @return always returns null since this method will be writing it's own
     * response
     */
    @RequestMapping(value = "/validateSignUpSlide1.html")
    public void validateSignUpSlide1(HttpServletRequest request, HttpServletResponse response) {

        String lastName = Helpful.getRequestParamStrSafe("lastName", request);
        String firstName = Helpful.getRequestParamStrSafe("firstName", request);
        String phone = Helpful.getRequestParamStrSafe("phone", request);
        String companyName = Helpful.getRequestParamStrSafe("companyName", request);

        // remove non-digit characters from phone number
        StringBuilder phoneBuilder = new StringBuilder();
        for (char c : phone.toCharArray()) {
            if (Character.isDigit(c)) {
                phoneBuilder.append(c);
            }
        }
        phone = phoneBuilder.toString();

        // validate the sign up parameters
        String error = null;

        if (Helpful.isEmptyString(firstName)) {
            error = "Please enter your First Name.";
        } else if (firstName.length() > 45) {
            error = "First Name Cannot Exceed 45 Characters";
        } else if (Helpful.isEmptyString(lastName)) {
            error = "Please enter your Last Name.";
        } else if (lastName.length() > 45) {
            error = "Last Name Cannot Exceed 45 Characters";
        } else if (Helpful.isEmptyString(companyName)) {
            error = "Please enter your Company Name.";
        } else if (companyName.length() > 45) {
            error = "Company Name Cannot Exceed 45 Characters";
        } else if (phone.length() != 10) {
            error = "Please enter a valid Phone Number eg. (404) 888-1111";
        }

        if (error != null) {
            Helpful.writeToResponse(response, "ERROR, " + error);
        }
    }

    /**
     * This method is called to validate the first slide on the sign up page
     *
     * @param request the request object
     * @param response the response object
     * @return always returns null since this method will be writing it's own
     * response
     */
    @RequestMapping(value = "/validateSignUpSlide2.html")
    public @ResponseBody JsonReturnObj validateSignUpSlide2(HttpServletRequest request, HttpServletResponse response) {

        JsonReturnObj jsonReturnObj = new JsonReturnObj();
        String address = Helpful.getRequestParamStrSafe("address", request);
        String city = Helpful.getRequestParamStrSafe("city", request);
        String state = Helpful.getRequestParamStrSafe("state", request);
        String zipCode = Helpful.getRequestParamStrSafe("zipCode", request);

        // validate the sign up parameters
        String error = null;

        if (Helpful.isEmptyString(address)) {
            error = "Please Enter the Business Address";
        } else if (address.length() > 65) {
            error = "Address Cannot Exceed 65 Characters";
        } else if (Helpful.isEmptyString(city)) {
            error = "Please enter the City.";
        } else if (city.length() > 45) {
            error = "City Cannot Exceed 45 Characters";
        } else if (Helpful.isEmptyString(state)) {
            error = "Please Select a State";
        } else if (state.length() > 2) {
            error = "Please Select a State";
        } else if (Helpful.isEmptyString(zipCode)) {
            error = "Please Enter the Zip Code";
        } else if (zipCode.length() > 15) {
            error = "Zip Code Cannot Exceed 15 Characters";
        }

        if (error != null) {
            jsonReturnObj.setIsError(true);
            jsonReturnObj.setErrorMessage(error);
        } else {
            jsonReturnObj.setIsError(false);
        }

        return jsonReturnObj;
    }

    /**
     * This method is called when a business user clicks the sign up button. It handles
     * creating the user account and sending out the email verification email to
     * the user
     *
     * @param request the request object
     * @param response the response object
     * @return always returns null since this method will be writing it's own
     * response
     */
    @RequestMapping(value = "/signUp.html")
    public @ResponseBody JsonReturnObj signUp(HttpServletRequest request, HttpServletResponse response) {

        JsonReturnObj jsonReturnObj = new JsonReturnObj();
        String email = Helpful.getRequestParamStrSafe("email", request).toLowerCase();


        
        
        // validate the sign up parameters
        String error = null;

        if (!Helpful.isValidEmailAddress(email)) {
            error = "Please enter a valid Email Address.";
        } else if (email.length() > 45) {
            error = "Email Address Cannot Exceed 45 Characters";
        } 
        
        if (error != null) {
            jsonReturnObj.setIsError(true);
            jsonReturnObj.setErrorMessage(error);
        } else {
            Integer idUser;
            try {
                // create user account 
                idUser = createUser.createUser(email);
                
                // setup return object
                jsonReturnObj.setIsError(false);
                jsonReturnObj.setSuccessMessage(SIGN_UP_SUCCESS);
            } catch (BaseException ex) {
                if (CreateUserImpl.EMAIL_ADDRESS_ALREADY_REGISTERED.equals(ex.getReason())) {
                    // send back message
                    jsonReturnObj.setIsError(true);
                    jsonReturnObj.setErrorMessage(CreateUserImpl.EMAIL_ADDRESS_ALREADY_REGISTERED);
                } else {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        }

        return jsonReturnObj;
    }

    /**
     * This method is called when the user clicks the email verification link in
     * the email that was sent to them by the system during signup
     *
     * @param request the request object
     * @param response the response object
     * @return returns the user to the index page of the site
     */
    @RequestMapping(value = "verifyEmail.html")
    public String verifyEmail(HttpServletRequest request, HttpServletResponse response) {
        String environment = Helpful.getProperty(request, "jdbc.properties", "environment");
        String email = null;
        String tempPassword = null;

        
        if("production".equals(environment)){
            email = Helpful.getDecodedRequestParamStrSafe("email", request);
            tempPassword = Helpful.getDecodedRequestParamStrSafe("val", request);
        } else {
            email = Helpful.getRequestParamStrSafe("email", request);
            tempPassword = Helpful.getRequestParamStrSafe("val", request);
        }

        // validate params
        if (!Helpful.isValidEmailAddress(email)) {
            return "error404";
        }
        if (tempPassword.length() != 20) {
            return "error404";
        }

        // verify the account
        Map userMap = validateUserAccount.validateUserAccount(email, tempPassword);

        
        String forward = "getDirs.html";
        if (userMap == null) {
            return "error404";
        } 

        return "forward: /" + forward;
    }

    /**
     * This method is called when the user clicks the send button to retrieve a
     * lost password. It sends a link to the user's email to help them recover
     * their lost credentials
     *
     * @param request the request object
     * @param response the response object
     * @return always returns null... the response is being generated by the
     * controller
     */
    @RequestMapping(value = "retrieveLostPassword.html")
    public void retrieveLostPassword(HttpServletRequest request, HttpServletResponse response) {
        String email = Helpful.getRequestParamStrSafe("email", request);

        // validate the email address
        if (!Helpful.isValidEmailAddress(email)) {
            Helpful.writeToResponse(response, "ERROR, Email Address is not Valid");
        }

        email = email.toLowerCase();

        try {
            // get temp password
            String tempPassword = retrieveLostPassword.retrieveLostPassword(email);

            // write response to page before sending email since the email process can take some time
            Helpful.writeToResponse(response, RETRIEVE_LOST_PASSWORD_SUCCESS);

            // send email
            boolean isMessageSent = sendLostPasswordEmail.sendLostPasswordEmail(tempPassword, email, Helpful.getProperty(request, "jdbc.properties", "context"), Helpful.getProperty(request, "jdbc.properties", "imagePath"));
            if (!isMessageSent) {
                Helpful.writeToResponse(response, "ERROR, Error during process... Please contact customer service for help.");
            }
        } catch (BaseException j) {
            Helpful.writeToResponse(response, "ERROR," + j.getReason());
        } catch (MessagingException m) {
            Helpful.writeToResponse(response, "ERROR, Error during process... Please contact customer service for help.");
        } catch (IOException ex) {
            Helpful.writeToResponse(response, "ERROR, Error during process... Please contact customer service for help.");
        }
    }

    /**
     * This method is called when the user clicks the password reset link in the
     * email that was sent to them by the system during the lost password
     * process
     *
     * @param request the request object
     * @return sends the user to the reset password page
     */
    @RequestMapping(value = "resetPassword.html")
    public String resetPassword(HttpServletRequest request) {
        String email = Helpful.getDecodedRequestParamStrSafe("email", request);
        String tempPassword = Helpful.getDecodedRequestParamStrSafe("val", request);

        // validate params
        if (!Helpful.isValidEmailAddress(email)) {
            return null;
        }
        if (tempPassword.length() != 20) {
            return null;
        }

        // verify the account
        Map userMap = validateUserAccount.validateUserAccount(email, tempPassword);

        // add user to session so that we know the user is logged in
        if (userMap != null) {
            request.getSession().setAttribute("user", userMap.get("user"));
            request.getSession().setAttribute("promoter", userMap.get("promoter"));
        } else {
            return "error404";
        }

        return "resetPassword";
    }

    /**
     * This method is called when the user chooses a new password after losing
     * their old password
     *
     * @param request the request object
     * @param response the response object
     */
    @RequestMapping(value = "changePassword.html")
    public void changePassword(HttpServletRequest request, HttpServletResponse response) {
        String password = Helpful.getRequestParamStrSafe("password", request);
        String confirmPassword = Helpful.getRequestParamStrSafe("confirmPassword", request);
        User user = (User) Helpful.getUser(request);

        // validate params
        if (Helpful.isEmpty(password)) {
            Helpful.writeToResponse(response, "ERROR,Please enter a new Password");
        } else if (Helpful.isEmpty(confirmPassword)) {
            Helpful.writeToResponse(response, "ERROR,Please confirm your new Password");
        } else if (!password.equals(confirmPassword)) {
            Helpful.writeToResponse(response, "ERROR,Passwords do not match. Please check your passwords and try again");
        } else if (password.length() < 6) {
            Helpful.writeToResponse(response, "ERROR,Password must be at least 6 characters");
        } else if (password.length() > 45) {
            Helpful.writeToResponse(response, "ERROR,Password Cannot Exceed 45 Characters");
        } else {
            // update the user's password
            String encKey = Helpful.getProperty(request, "jdbc.properties", "jdbc.enckey");
            int update = updateUserPassword.updateUserPassword(user.getIdUser(), password, encKey);
            if (update > 0) {
                Helpful.writeToResponse(response, "SUCCESS, Your Password Has Been Updated. Click the link below to go to the login page., displayLogin.html");
            } else {
                Helpful.writeToResponse(response, "ERROR, An Error Occurred. Please contact customer service for help changing your password.");
            }
        }
    }

}
