package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.model.MpTransactionEntity;
import com.aome.todoist_mp_api.converter.MpTransactionEntityRowMapper;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MpTransactionRepositoryImpl implements MpTransactionRepository {
    private final JdbcClient client;

    @Override
    public @NotNull MpTransactionEntity findLastCreated() throws DataAccessException {
        @Language("SQL") String sql = """
                SELECT * FROM mp_transaction ORDER BY timestamp DESC LIMIT 1""";

        return client
                .sql(sql)
                .query(new MpTransactionEntityRowMapper())
                .single();
    }

    @Override
    public @NotNull MpTransactionEntity save(@NotNull MpTransactionEntity transaction) throws DataAccessException {
        @Language("SQL") String sql = """
                INSERT INTO mp_transaction(
                    tasker_id, delta_mp, timestamp, task_count)
                VALUES(
                    :tasker_id, :delta_mp, :timestamp, :task_count )
                RETURNING id""";

        Long id = client.sql(sql)
                .param("tasker_id", transaction.taskerId())
                .param("delta_mp", transaction.deltaMp())
                .param("timestamp", transaction.timestamp())
                .param("task_count", transaction.taskCount())
                .query(Long.class)
                .single();

        return transaction.withId(id);
    }
}
