
package com.homeclouddrive.service;

import com.homeclouddrive.dao.UserDAOImpl;
import com.homeclouddrive.domain.User;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



/**
 *
 * @author getjigy
 */
@Service
public class ValidateUserAccountImpl {
    @Resource
    private UserDAOImpl userDAO;

    
    
    /**
     * This method is called when a user clicks the link in their email
     * validation mail that the system sent them upon signing up. The method
     * updates the user record to reflect that the user is using a valid email address
     * @param email the email address of the user
     * @param tempPassword the system generated temporary password
     * @return the user object representing this user
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public Map validateUserAccount(String email, String tempPassword) {
        Map userMap = new HashMap();
        
        // verify the user's email address and validate the account
        User user = userDAO.validateUserEmail(email, tempPassword);
        
        if(user == null){
            return null;
        } else {
            userMap.put("user", user);
            
            return userMap;
        }
    }
}
