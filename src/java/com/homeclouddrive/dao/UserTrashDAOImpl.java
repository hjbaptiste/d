package com.homeclouddrive.dao;

import com.homeclouddrive.domain.FamilyTrash;
import com.homeclouddrive.domain.UserTrash;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class UserTrashDAOImpl extends BaseDAOImpl {

    public int createUserTrash(Integer idUser, String originalRelativePath, String shareRelativePath, String isDirectory) {
        String sql = "insert into user_trash (id_user,original_relative_path,share_relative_path,is_directory,dt_last_update) values(?,?,?,?,now())";
        return getSimpleJdbcTemplate().update(sql, idUser, originalRelativePath, shareRelativePath, isDirectory);
    }

    public int createUserTrash(UserTrash userTrash) {
        String sql = "insert into user_trash (id_user,original_relative_path,share_relative_path,is_directory,dt_last_update) values(:idUser, :originalRelativePath, :shareRelativePath, :isDirectory, now())";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(userTrash);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int update = getNamedParameterJdbcTemplate().update(sql, params, keyHolder);
        if (update > 0) {
            return keyHolder.getKey().intValue();
        }
        return 0;
    }

    public UserTrash retrieveUserTrashByIdUserTrash(Integer idUserTrash) {
        String sql = "select * from user_trash where id_user_trash = ?";
        return (UserTrash) getUniqueResult(getSimpleJdbcTemplate().query(sql, new UserTrashRowMapper(), idUserTrash));
    }

    public int deleteUserTrashByIdUserTrash(Integer idUserTrash) {
        String sql = "delete from user_trash where id_user_trash = ?";
        return getSimpleJdbcTemplate().update(sql, idUserTrash);
    }


    public class UserTrashRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserTrash userTrash = new UserTrash();
            userTrash.setIdUserTrash(rs.getInt("id_user_trash"));
            userTrash.setIdUser(rs.getInt("id_user"));
            userTrash.setOriginalRelativePath(rs.getString("original_relative_path"));
            userTrash.setShareRelativePath(rs.getString("share_relative_path"));
            userTrash.setIsDirectory("1".equals(rs.getString("is_directory")) ? true : false);
            userTrash.setDtLastUpdated(rs.getDate("dt_last_update"));
            
            return userTrash;
        }
    }
}
