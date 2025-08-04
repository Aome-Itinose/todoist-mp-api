package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.model.TaskEntity;
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
                    (tasker_id, content, description, completed_at, mp)
                VALUES (?, ?, ?, ?, ?)""";

        template.batchUpdate(sql, tasks, tasks.size(), (ps, entity) -> {
            ps.setLong(1, entity.taskerId());
            ps.setString(2, entity.content());
            ps.setString(3, entity.description());
            ps.setObject(4, entity.completedAt());
            ps.setInt(5, entity.mp());
        });
    }
}
