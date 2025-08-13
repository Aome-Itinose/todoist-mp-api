package com.aome.todoist_mp_api.converter;

import com.aome.todoist_mp_api.model.entity.ProfileEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ProfileEntityRowMapper implements RowMapper<ProfileEntity> {
    @Override
    public ProfileEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ProfileEntity(
                rs.getObject("id", UUID.class),
                rs.getString("todoist_username"),
                rs.getLong("todoist_id"),
                rs.getString("todoist_token"),
                rs.getString("telegram_token"),
                rs.getInt("mp")
        );
    }
}
