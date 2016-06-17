
package com.homeclouddrive.service;

import com.homeclouddrive.dao.UserDAOImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



/**
 *
 * @author getjigy
 */
@Service
public class UnsubscribeUserAndPatronImpl {
    @Resource
    private UserDAOImpl userDAO;
    
    

    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public int unsubscribeUser(String emailAddress) {
        int userUnsubscribed = userDAO.unsubscribeUserByEmailAddress(emailAddress);
        
        return userUnsubscribed;
    }
}
