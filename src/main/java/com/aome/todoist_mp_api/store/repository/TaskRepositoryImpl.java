package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.model.TaskEntity;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveAll(@NotNull List<TaskEntity> tasks) {
        String sql = """
                INSERT INTO task (id, tasker_id, content, description, completed_at, mp)
                VALUES (?, ?, ?, ?, ?, ?)""";

        jdbcTemplate.batchUpdate(sql, tasks, tasks.size(), (ps, task) -> {
            ps.setLong(1, task.id());
            ps.setLong(2, task.taskerId());
            ps.setString(3, task.content());
            ps.setString(4, task.description());
            ps.setObject(5, task.completedAt());
            ps.setInt(6, task.mp());
        });
    }
}
