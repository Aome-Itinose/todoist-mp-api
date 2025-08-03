package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.model.MpTransactionEntity;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MpTransactionRepositoryImpl implements MpTransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<MpTransactionEntity> findLastCreated() throws DataAccessException {
        String sql = "SELECT * FROM mp_transaction ORDER BY timestamp DESC";
//        MpTransactionEntity transaction = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
//                new MpTransactionEntity(
//                        rs.getLong("id"),
//                        rs.getLong("tasker_id"),
//                        rs.getInt("delta_mp"),
//                        rs.getObject("timestamp", OffsetDateTime.class),
//                        rs.getInt("task_count")
//                )
//        );
        List<MpTransactionEntity> transactions = jdbcTemplate.query(sql, (rs, rowNum) ->
                new MpTransactionEntity(
                        rs.getLong("id"),
                        rs.getLong("tasker_id"),
                        rs.getInt("delta_mp"),
                        rs.getObject("timestamp", OffsetDateTime.class),
                        rs.getInt("task_count")
                )
        );
        if (transactions.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(transactions.getFirst());
    }

    @Override
    public @NotNull MpTransactionEntity save(@NotNull MpTransactionEntity transaction) {
        String sql = """
                INSERT INTO mp_transaction(
                    tasker_id, delta_mp, timestamp, task_count
                ) VALUES(
                    ?, ?, ?, ?
                )""";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setLong(1, transaction.taskerId());
            ps.setInt(2, transaction.deltaMp());
            ps.setObject(3, transaction.timestamp());
            ps.setInt(4, transaction.taskCount());

            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys == null || !keys.containsKey("id")) {
            throw new IllegalStateException("ID not returned after insert");
        }

        long id = ((Number) keys.get("id")).longValue();
        return transaction.withId(id);
    }
}
