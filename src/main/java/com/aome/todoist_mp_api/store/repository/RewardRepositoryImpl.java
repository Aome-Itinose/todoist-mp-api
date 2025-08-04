package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.model.RewardEntity;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RewardRepositoryImpl implements RewardRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public @NotNull RewardEntity save(@NotNull RewardEntity reward) {
        String sql = """
                INSERT INTO reward (user_id, amount, content, type, timestamp)
                VALUES (?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            var st = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            st.setLong(1, reward.userId());
            st.setInt(2, reward.amount());
            st.setString(3, reward.content());
            st.setString(4, reward.type());
            st.setObject(5, reward.timestamp());

            return st;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys == null || !keys.containsKey("id")) {
            throw new IllegalStateException("ID not returned after insert");
        }
        long id = ((Number) keys.get("id")).longValue();
        return reward.withId(id);
    }
}
