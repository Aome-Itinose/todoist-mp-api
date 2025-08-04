package com.aome.todoist_mp_api.converter;

import com.aome.todoist_mp_api.model.TaskerEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskerEntityRowMapper implements RowMapper<TaskerEntity> {
    @Override
    public TaskerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TaskerEntity(
                rs.getLong("id"),
                rs.getString("todoist_username"),
                rs.getLong("todoist_id"),
                rs.getString("todoist_token"),
                rs.getString("telegram_token"),
                rs.getInt("mp")
        );
    }
}
