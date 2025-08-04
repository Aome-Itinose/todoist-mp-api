package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.model.RewardEntity;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RewardRepositoryImpl implements RewardRepository {
    private final JdbcClient client;

    @Override
    public @NotNull RewardEntity save(@NotNull RewardEntity reward) throws DataAccessException {
        @Language("SQL") String sql = """
                INSERT INTO reward (user_id, amount, content, type, timestamp)
                VALUES (:user_id, :amount, :content, :type, :timestamp)
                RETURNING id
                """;

        Long id = client.sql(sql)
                .param("user_id", reward.userId())
                .param("amount", reward.amount())
                .param("content", reward.content())
                .param("type", reward.type())
                .param("timestamp", reward.timestamp())
                .query(Long.class)
                .single();

        return reward.withId(id);
    }
}
