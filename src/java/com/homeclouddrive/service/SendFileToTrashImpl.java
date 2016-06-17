
package com.homeclouddrive.service;

import com.homeclouddrive.dao.UserDAOImpl;
import com.homeclouddrive.domain.Role;
import com.homeclouddrive.domain.User;
import com.homeclouddrive.exception.BaseException;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author getjigy
 */
@Service
public class SendFileToTrashImpl {

    @Resource
    private UserDAOImpl userDAO;

    
    public static final String EMAIL_ADDRESS_ALREADY_REGISTERED = "Email Address Already Registered";
    public static final String ACCOUNT_CREATION_FAILED = "Account creation insert statment failed";

    
    /**
     * This method generates a temporary password and stores it
     * with this newly created user account
     * @param email the email address of the user
     * @return
     * @throws BaseException 
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public int createUser(String email) throws BaseException {
        
        // check to make sure email address is not already signed up
        User user = userDAO.retrieveUserByEmailAddress(email);
        
        // if user is not null then email address is already signed up... send back an error message
        if(user != null){
            throw new BaseException(EMAIL_ADDRESS_ALREADY_REGISTERED);
        }
        
        // create temporary password to email to user
        //String tempPassword = SymmetricEncryption.generateKey().substring(0, 20);
        
        // create temporary user account
        int idUser = userDAO.createUser(createUser(email, Role.Standard));
        
        // make sure user got created
        if(idUser == 0){
            throw new BaseException(ACCOUNT_CREATION_FAILED);
        }
        
        
        return idUser;
    }
    
    
    private User createUser(String email, String role){

        // create user
        User user = new User();
        user.setEmailAddress(email);
        user.setRole(role);
        user.setIsSubscribed("Y");
        user.setDtSignUp(new Date());
        
        return user;
    }
    
}
