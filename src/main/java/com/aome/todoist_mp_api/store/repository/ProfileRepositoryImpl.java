package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.converter.ProfileEntityRowMapper;
import com.aome.todoist_mp_api.model.entity.ProfileEntity;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProfileRepositoryImpl implements ProfileRepository {
    private final JdbcClient client;

    @Override
    public @NotNull ProfileEntity save(@NotNull ProfileEntity profile) throws DataAccessException {
        @Language("SQL") String sql = """
                INSERT INTO profile(
                    id, todoist_username, todoist_id, todoist_token, telegram_token, mp)
                VALUES(
                    :id, :todoist_username, :todoist_id, :todoist_token, :telegram_token, :mp)""";

        client.sql(sql)
                .param("id", profile.id())
                .param("todoist_username", profile.todoistUsername())
                .param("todoist_id", profile.todoistId())
                .param("todoist_token", profile.todoistToken())
                .param("telegram_token", profile.telegramToken())
                .param("mp", profile.mp())
                .update();

        return profile;
    }

    @Override
    public @NotNull ProfileEntity update(@NotNull ProfileEntity profile) throws DataAccessException {
        @Language("SQL") String sql = """
                UPDATE profile
                SET
                    todoist_username = :todoist_username,
                    todoist_id = :todoist_id,
                    todoist_token = :todoist_token,
                    telegram_token = :telegram_token,
                    mp = :mp
                WHERE id = :id""";

        client.sql(sql)
                .param("todoist_username", profile.todoistUsername())
                .param("todoist_id", profile.todoistId())
                .param("todoist_token", profile.todoistToken())
                .param("telegram_token", profile.telegramToken())
                .param("mp", profile.mp())
                .param("id", profile.id())
                .update();

        return profile;
    }

    @Override
    public @NotNull ProfileEntity updateMp(@NotNull UUID id, int mp) throws DataAccessException {
        @Language("SQL") String sql = """
                UPDATE profile
                SET mp = :mp
                WHERE id = :id""";

        client.sql(sql)
                .param("mp", mp)
                .param("id", id)
                .update();

        return findById(id);
    }

    @Override
    public @NotNull ProfileEntity findByTelegramToken(@NotNull String telegramToken) throws DataAccessException {
        @Language("SQL") String sql = """
                SELECT *
                FROM profile
                WHERE telegram_token = :telegram_token""";

        return client.sql(sql)
                .param("telegram_token", telegramToken)
                .query(new ProfileEntityRowMapper())
                .single();
    }

    @Override
    public @NotNull ProfileEntity findById(@NotNull UUID id) throws DataAccessException {
        @Language("SQL") String sql = """
                SELECT *
                FROM profile
                WHERE id = :id""";

        return client.sql(sql)
                .param("id", id)
                .query(new ProfileEntityRowMapper())
                .single();
    }

    @Override
    public boolean existByTodoistToken(@NotNull String todoistToken) throws DataAccessException {
        @Language("SQL") String sql = """
                SELECT *
                FROM profile
                WHERE todoist_token = :todoist_token""";

        return !client.sql(sql)
                .param("todoist_token", todoistToken)
                .query(new ProfileEntityRowMapper())
                .list().isEmpty();
    }

    @Override
    public boolean existByTelegramToken(@NotNull String telegramToken) throws DataAccessException {
        @Language("SQL") String sql = """
                SELECT *
                FROM profile
                WHERE telegram_token = :telegram_token""";

        return !client.sql(sql)
                .param("telegram_token", telegramToken)
                .query(new ProfileEntityRowMapper())
                .list().isEmpty();
    }

    @Override
    public boolean existByTelegramAndTodoistToken(@NotNull String telegramToken, @NotNull String todoistToken) throws DataAccessException {
        @Language("SQL") String sql = """
                SELECT *
                FROM profile
                WHERE telegram_token = :telegram_token AND todoist_token = :todoist_token""";

        return !client.sql(sql)
                .param("telegram_token", telegramToken)
                .param("todoist_token", todoistToken)
                .query(new ProfileEntityRowMapper())
                .list().isEmpty();
    }
}
