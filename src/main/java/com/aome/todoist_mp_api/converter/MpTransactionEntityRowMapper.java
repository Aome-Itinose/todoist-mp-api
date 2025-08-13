package com.aome.todoist_mp_api.converter;

import com.aome.todoist_mp_api.model.entity.MpTransactionEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.UUID;

public class MpTransactionEntityRowMapper implements RowMapper<MpTransactionEntity> {
    @Override
    public MpTransactionEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new MpTransactionEntity(
                rs.getObject("id", UUID.class),
                rs.getObject("profile_id", UUID.class),
                rs.getInt("delta_mp"),
                rs.getObject("timestamp", OffsetDateTime.class),
                rs.getInt("task_count")
        );
    }
}
