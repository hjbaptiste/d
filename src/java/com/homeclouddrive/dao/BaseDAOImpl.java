
package com.homeclouddrive.dao;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author getjigy
 */
@Repository
public class BaseDAOImpl{

    @Resource
    private DataSource dataSource;
    
    
    
    /**
     * This method gets an instantiated SimpleJdbcTemplate
     * @return a SimpleJdbcTemplate
     */
    public SimpleJdbcTemplate getSimpleJdbcTemplate() {
        return new SimpleJdbcTemplate(dataSource);
    }

    
    
    /**
     * This method gets an instantiated JdbcTemplate
     * @return a JdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }
    
    
    
    /**
     * This method gets an instantiated NamedParameterJdbcTemplate
     * @return returns a NamedParameterJdbcTemplate for named jdbc operations
     */
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(){
        return new NamedParameterJdbcTemplate(dataSource);
    }
    
    
    
    /**
     * This method returns the only result within a list
     * @param objectList - a list containing only a single object
     * @return 
     */
    protected Object getUniqueResult(List<Object> objectList){
        if(!objectList.isEmpty()){
            return objectList.get(0);
        }
        return null;
    }
    
    
    
    /**
     * This method converts a blob to a string
     *
     * @param rs the result set
     * @param columnName the name of the blob column in the result set
     * @return the string value of the blob
     */
    protected String convertObjectToString(ResultSet rs, String columnName) {
        byte[] bytes = null;
        try {
            bytes = (byte[]) rs.getObject(columnName);
        } catch (SQLException ex) {
            Logger.getLogger(BaseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String val = new String(bytes);

        return val;
    }
}
