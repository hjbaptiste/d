package com.homeclouddrive.dao;

import com.homeclouddrive.domain.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class UserDAOImpl extends BaseDAOImpl {

    public int createUser(String emailAddress, byte[] password, String firstName, String lastName, Date dtSignUp, Date dtServiceExpires, String tempPassword, String isSubscribed, String role, String status, Date dtLastUpdate) {
        String sql = "insert into user (email_address,password,first_name,last_name,dt_sign_up,dt_service_expires,temp_password,is_subscribed,role,status,dt_last_update) values(?,?,?,?,?,?,?,?,?,?,?)";
        return getSimpleJdbcTemplate().update(sql, emailAddress, password, firstName, lastName, dtSignUp, dtServiceExpires, tempPassword, isSubscribed, role, status, dtLastUpdate);
    }

    public int createUser(User user) {
        String sql = "insert into user (email_address,dt_sign_up,temp_password,is_subscribed,role,dt_last_update) values(:emailAddress,:dtSignUp,:tempPassword, 'Y', :role, :dtLastUpdate)";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(user);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int update = getNamedParameterJdbcTemplate().update(sql, params, keyHolder);
        if (update > 0) {
            return keyHolder.getKey().intValue();
        }
        return 0;
    }

    public int updateUser(User user) {
        String sql = "update user set email_address = :emailAddress,password = :password,first_name = :firstName,last_name = :lastName,dt_sign_up = :dtSignUp,temp_password = :tempPassword,is_subscribed = :isSubscribed,role = :role,dt_last_update = :dtLastUpdate where id_user = :idUser";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(user);
        return getNamedParameterJdbcTemplate().update(sql, params);
    }

    public User retrieveUserByIdUser(Integer idUser) {
        String sql = "select * from user where id_user = ?";
        return (User) getUniqueResult(getSimpleJdbcTemplate().query(sql, new UserRowMapper(), idUser));
    }

    public int deleteUserByIdUser(Integer idUser) {
        String sql = "delete from user where id_user = ?";
        return getSimpleJdbcTemplate().update(sql, idUser);
    }

    public User retrieveUserByEmailAddress(String emailAddress) {
        String sql = "SELECT * from user where email_address = ?";
        return (User) getUniqueResult(getSimpleJdbcTemplate().query(sql, new UserRowMapper(), emailAddress));
    }

    public User retrieveUserByEmailAddressAndPassword(String emailAddress, String password, String encKey) {
        String sql = "SELECT * from user where email_address = ? and password = AES_ENCRYPT(?, ?)";
        return (User) getUniqueResult(getSimpleJdbcTemplate().query(sql, new UserRowMapper(), emailAddress, password, encKey));
    }

    public int addUser(String email, String password, String tempPassword, String firstName, String lastName) {
        String sql = "insert into user (email_address, password, first_name, "
                + " last_name, dt_sign_up, temp_password) values (?, ?, ?, ?, now(), ?)";
        return getSimpleJdbcTemplate().update(sql, email, password, firstName, lastName, tempPassword);
    }

    public int setTempPassword(int idUser, String tempPassword) {
        String sql = "update user set temp_password = ? where id_user = ?";
        return getSimpleJdbcTemplate().update(sql, tempPassword, idUser);
    }

    public int updatePassword(int idUser, String password) {
        String sql = "update user set password = ? where id_user = ?";
        return getSimpleJdbcTemplate().update(sql, password, idUser);
    }

    public User validateUserEmail(String email, String tempPassword) {
        String sqlUpdate = "update user set temp_password = '' where email_address = ? and temp_password = ?";
        int update = getSimpleJdbcTemplate().update(sqlUpdate, email, tempPassword);
        if (update > 0) {
            return retrieveUserByEmailAddress(email);
        }
        return null;
    }
    
    public int unsubscribeUserByEmailAddress(String emailAddress) {
        String sql = "update user set is_subscribed = 'N' where email_address = ?";
        return getSimpleJdbcTemplate().update(sql, emailAddress);
    }
    
    public int updatePassword(int idUser, String password, String encKey) {
        String sql = "update user set password = AES_ENCRYPT(?, ?) where id_user = ?";
        return getSimpleJdbcTemplate().update(sql, password, encKey, idUser);
    }

    public class UserRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setIdUser(rs.getInt("id_user"));
            user.setEmailAddress(rs.getString("email_address"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setDtSignUp(rs.getDate("dt_sign_up"));
            user.setTempPassword(rs.getString("temp_password"));
            user.setIsSubscribed(rs.getString("is_subscribed"));
            user.setRole(rs.getString("role"));
            user.setDtLastUpdate(rs.getDate("dt_last_update"));
            return user;
        }
    }
}
