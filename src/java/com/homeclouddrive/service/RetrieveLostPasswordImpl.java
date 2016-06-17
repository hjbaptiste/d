
package com.homeclouddrive.service;

import com.jigy.api.Helpful;
import com.jigy.api.security.SymmetricEncryption;
import com.homeclouddrive.dao.UserDAOImpl;
import com.homeclouddrive.domain.User;
import com.homeclouddrive.exception.BaseException;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



/**
 *
 * @author getjigy
 */
@Service
public class RetrieveLostPasswordImpl {

    @Resource
    private UserDAOImpl userDAO;
    
    public static final String EMAIL_ADDRESS_NOT_REGISTERED = "Email Address Is Not Registered";

    
    /**
     * This method sends a user who has lost their password an email
     * with a link that allows them to reset their password
     * @param email the email address of the user
     * @return the temporary password created for the user
     * @throws BaseException 
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public String retrieveLostPassword(String email) throws BaseException {
        
        // get user by email address
        User user = userDAO.retrieveUserByEmailAddress(email);
        
        // if user is null then email address is not valid... throw exception
        if(user == null){
            throw new BaseException(EMAIL_ADDRESS_NOT_REGISTERED);
        }
        
        // create temporary password to email to user
        String tempPassword = SymmetricEncryption.generateKey().substring(0, 20);
        
        if(!Helpful.isEmpty(tempPassword)){
            int update = userDAO.setTempPassword(user.getIdUser(), tempPassword);
            if(update > 0){
                return tempPassword;
            } else {
                throw new BaseException("Error: Please try again later");
            }
        } else {
                throw new BaseException("Error: Please try again later");
        }
    }
}
