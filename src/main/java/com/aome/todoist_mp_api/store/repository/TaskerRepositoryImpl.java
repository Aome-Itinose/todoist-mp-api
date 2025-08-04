package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.model.TaskerEntity;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TaskerRepositoryImpl implements TaskerRepository {
    private final JdbcClient client;

    @Override
    public @NotNull TaskerEntity save(@NotNull TaskerEntity tasker) throws DataAccessException {
        @Language("SQL") String sql = """
                INSERT INTO tasker(
                    todoist_username, todoist_id, todoist_token, telegram_token, mp)
                VALUES(
                    :todoist_username, :todoist_id, :todoist_token, :telegram_token, :mp)
                RETURNING id""";

        Long id = client.sql(sql)
                .param("todoist_username", tasker.todoistUsername())
                .param("todoist_id", tasker.todoistId())
                .param("todoist_token", tasker.todoistToken())
                .param("telegram_token", tasker.telegramToken())
                .param("mp", tasker.mp())
                .query(Long.class)
                .single();

        return tasker.withId(id);
    }

    @Override
    public @NotNull TaskerEntity update(@NotNull TaskerEntity tasker) throws DataAccessException {
        @Language("SQL") String sql = """
                UPDATE tasker
                SET
                    todoist_username = :todoist_username, 
                    todoist_id = :todoist_id, 
                    todoist_token = :todoist_token, 
                    telegram_token = :telegram_token, 
                    mp = :mp
                WHERE id = :id""";

        client.sql(sql)
                .param("todoist_username", tasker.todoistUsername())
                .param("todoist_id", tasker.todoistId())
                .param("todoist_token", tasker.todoistToken())
                .param("telegram_token", tasker.telegramToken())
                .param("mp", tasker.mp())
                .param("id", tasker.id())
                .update();

        return tasker;
    }

    @Override
    public @NotNull TaskerEntity findByTelegramToken(@NotNull String telegramToken) throws DataAccessException {
        @Language("SQL") String sql = """
                SELECT *
                FROM tasker
                WHERE telegram_token = :telegram_token""";

        return client.sql(sql)
                .param("telegram_token", telegramToken)
                .query(TaskerEntity.class)
                .single();
    }

    @Override
    public boolean existByTodoistToken(@NotNull String todoistToken) throws DataAccessException {
        @Language("SQL") String sql = """
                SELECT *
                FROM tasker
                WHERE todoist_token = :todoist_token""";

        return !client.sql(sql)
                .param("todoist_token", todoistToken)
                .query(TaskerEntity.class)
                .list().isEmpty();
    }

    @Override
    public boolean existByTelegramToken(@NotNull String telegramToken) throws DataAccessException {
        @Language("SQL") String sql = """
                SELECT *
                FROM tasker
                WHERE telegram_token = :telegram_token""";

        return !client.sql(sql)
                .param("telegram_token", telegramToken)
                .query(TaskerEntity.class)
                .list().isEmpty();
    }

    @Override
    public boolean existByTelegramAndTodoistToken(@NotNull String telegramToken, @NotNull String todoistToken) throws DataAccessException {
        @Language("SQL") String sql = """
                SELECT *
                FROM tasker
                WHERE telegram_token = :telegram_token AND todoist_token = :todoist_token""";

        return !client.sql(sql)
                .param("telegram_token", telegramToken)
                .param("todoist_token", todoistToken)
                .query(TaskerEntity.class)
                .list().isEmpty();
    }
}
