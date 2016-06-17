
package com.homeclouddrive.service;

import com.jigy.api.Helpful;
import com.homeclouddrive.dao.UserDAOImpl;
import com.homeclouddrive.domain.User;
import com.homeclouddrive.exception.BaseException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;


/**
 *
 * @author getjigy
 */
@Service
public class LoginUserServiceImpl {
    
    @Resource
    UserDAOImpl userDAO;
    
    
    
    /**
     * This method handles logging a user in
     * @param emailAddress the email address that the user input
     * @param password the password that the user input
     * @param encKey the encryption key
     * @return a map containing the user and promoter object or null if authentication fails
     */
    public Map loginUser(String emailAddress, String password, String encKey) throws NoSuchAlgorithmException, InvalidKeySpecException, BaseException{
        Map userMap = new HashMap();
        
        User user = userDAO.retrieveUserByEmailAddressAndPassword(emailAddress, password, encKey);
        
        // if user is null then the wrong email address and password has been supplied
        if(user == null){
            return null;
        } else if(user.getTempPassword() != null && !Helpful.isEmpty(user.getTempPassword())){
            throw new BaseException("Account not activated. Please check your email for activation email.");
        }

        userMap.put("user", user);        
        
        return userMap;
    }
}
