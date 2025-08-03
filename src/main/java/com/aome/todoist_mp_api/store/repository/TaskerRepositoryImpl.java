package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.model.TaskerEntity;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskerRepositoryImpl implements TaskerRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public @NotNull TaskerEntity save(@NotNull TaskerEntity tasker) {

        String sql = """
                INSERT INTO tasker(
                    todoist_username, todoist_id, todoist_token, telegram_token, mp
                ) VALUES(
                    ?, ?, ?, ?, ?
                )""";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement st = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            st.setString(1, tasker.todoistUsername());
            st.setLong(2, tasker.todoistId());
            st.setString(3, tasker.todoistToken());
            st.setString(4, tasker.telegramToken());
            st.setInt(5, tasker.mp());

            return st;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys == null || !keys.containsKey("id")) {
            throw new IllegalStateException("ID not returned after insert");
        }
        long id = ((Number) keys.get("id")).longValue();
        return tasker.withId(id);
    }

    @Override
    public @NotNull TaskerEntity update(@NotNull TaskerEntity tasker) {
        String sql = """
                UPDATE tasker
                SET todoist_username = ?, todoist_id = ?, todoist_token = ?, telegram_token = ?, mp = ?
                WHERE id = ?""";

        jdbcTemplate.update(sql, tasker.todoistUsername(), tasker.todoistId(),
                tasker.todoistToken(), tasker.telegramToken(), tasker.mp(), tasker.id());

        return tasker;
    }

    @Override
    public Optional<TaskerEntity> findByTelegramToken(@NotNull String telegramToken) {
        String sql = "SELECT * FROM tasker WHERE telegram_token = ?";
        List<TaskerEntity> taskers = jdbcTemplate.query(sql, taskerRowMapper(), telegramToken);

        if (taskers.isEmpty()) {
            return Optional.empty();
        }

        TaskerEntity tasker = taskers.getFirst();
        return Optional.of(tasker);
    }

    @Override
    public boolean existByTodoistToken(@NotNull String todoistToken) {
        String sql = "SELECT * FROM tasker WHERE todoist_token = ?";
        List<TaskerEntity> taskers = jdbcTemplate.query(sql, taskerRowMapper(), todoistToken);

        return !taskers.isEmpty();
    }

    @Override
    public boolean existByTelegramToken(@NotNull String telegramToken) {
        String sql = "SELECT * FROM tasker WHERE telegram_token = ?";
        List<TaskerEntity> taskers = jdbcTemplate.query(sql, taskerRowMapper(), telegramToken);

        return !taskers.isEmpty();
    }

    @Override
    public boolean existByTelegramAndTodoistToken(@NotNull String telegramToken, @NotNull String todoistToken) {
        String sql = "SELECT * FROM tasker WHERE telegram_token = ? AND todoist_token = ?";
        List<TaskerEntity> taskers = jdbcTemplate.query(sql, taskerRowMapper(), telegramToken, todoistToken);

        return !taskers.isEmpty();
    }

    private RowMapper<TaskerEntity> taskerRowMapper() {
        return (rs, _) -> new TaskerEntity(
                rs.getLong("id"),
                rs.getString("todoist_username"),
                rs.getLong("todoist_id"),
                rs.getString("todoist_token"),
                rs.getString("telegram_token"),
                rs.getInt("mp")
        );
    }
}
