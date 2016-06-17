
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
public class UpdateUserPasswordImpl {
    @Resource
    private UserDAOImpl userDAO;
    
    
    /**
     * This method handles changing a user's password
     * @param idUser the id of the user
     * @param password the new password to store for the user
     * @param encKey the encryption key
     * @return the number of rows affected by the update
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public int updateUserPassword(int idUser, String password, String encKey) {
        // update the user's password to the new passed-in password
        return userDAO.updatePassword(idUser, password, encKey); 
    }
}
