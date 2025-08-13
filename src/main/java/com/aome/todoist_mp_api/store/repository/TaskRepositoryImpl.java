package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.model.entity.TaskEntity;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private final JdbcTemplate template;

    @Override
    public void saveAll(@NotNull List<TaskEntity> tasks) throws DataAccessException {
        @Language("SQL") String sql = """
                INSERT INTO task
                    (id, todoist_id, profile_id, content, description, completed_at, mp)
                VALUES (?, ?, ?, ?, ?, ?, ?)""";

        template.batchUpdate(sql, tasks, tasks.size(), (ps, entity) -> {
            ps.setObject(1, entity.id());
            ps.setLong(2, entity.todoistId());
            ps.setObject(3, entity.profileId());
            ps.setString(4, entity.content());
            ps.setString(5, entity.description());
            ps.setObject(6, entity.completedAt());
            ps.setLong(7, entity.mp());
        });
    }
}
