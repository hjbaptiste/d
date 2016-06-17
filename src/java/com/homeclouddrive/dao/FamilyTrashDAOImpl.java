package com.homeclouddrive.dao;

import com.homeclouddrive.dao.UserDAOImpl.UserRowMapper;
import com.homeclouddrive.domain.FamilyTrash;
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
public class FamilyTrashDAOImpl extends BaseDAOImpl {

    public int createFamilyTrash(String originalRelativePath, String shareRelativePath, String isDirectory) {
        String sql = "insert into family_trash (original_relative_path,share_relative_path,is_directory,dt_last_update) values(?,?,?,now())";
        return getSimpleJdbcTemplate().update(sql, originalRelativePath, shareRelativePath, isDirectory);
    }

    public int createFamilyTrash(FamilyTrash familyTrash) {
        String sql = "insert into family_trash (original_relative_path,share_relative_path,is_directory,dt_last_update) values(:originalRelativePath, :shareRelativePath, :isDirectory, now())";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(familyTrash);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int update = getNamedParameterJdbcTemplate().update(sql, params, keyHolder);
        if (update > 0) {
            return keyHolder.getKey().intValue();
        }
        return 0;
    }

    public FamilyTrash retrieveFamilyTrashByIdFamilyTrash(Integer idFamilyTrash) {
        String sql = "select * from family_trash where id_family_trash = ?";
        return (FamilyTrash) getUniqueResult(getSimpleJdbcTemplate().query(sql, new FamilyTrashRowMapper(), idFamilyTrash));
    }

    public int deleteFamilyTrashByIdFamilyTrash(Integer idFamilyTrash) {
        String sql = "delete from family_trash where id_family_trash = ?";
        return getSimpleJdbcTemplate().update(sql, idFamilyTrash);
    }


    public class FamilyTrashRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            FamilyTrash familyTrash = new FamilyTrash();
            familyTrash.setIdFamilyTrash(rs.getInt("id_family_trash"));
            familyTrash.setOriginalRelativePath(rs.getString("original_relative_path"));
            familyTrash.setShareRelativePath(rs.getString("share_relative_path"));
            familyTrash.setIsDirectory("1".equals(rs.getString("is_directory")) ? true : false);
            familyTrash.setDtLastUpdated(rs.getDate("dt_last_update"));
            
            return familyTrash;
        }
    }
}
