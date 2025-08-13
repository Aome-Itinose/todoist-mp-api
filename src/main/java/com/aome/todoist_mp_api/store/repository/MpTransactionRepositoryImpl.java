package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.converter.MpTransactionEntityRowMapper;
import com.aome.todoist_mp_api.exception.ProfileNotFoundException;
import com.aome.todoist_mp_api.model.entity.MpTransactionEntity;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MpTransactionRepositoryImpl implements MpTransactionRepository {
    private final JdbcClient client;

    @Override
    public @NotNull MpTransactionEntity findLastCreated() throws DataAccessException {
        @Language("SQL") String sql = """
                SELECT
                    id, profile_id, delta_mp, timestamp, task_count
                FROM mp_transaction
                ORDER BY timestamp DESC""";

        List<MpTransactionEntity> shouldBeSingle = client
                .sql(sql)
                .query(new MpTransactionEntityRowMapper())
                .list();
        if (shouldBeSingle.isEmpty()) throw new ProfileNotFoundException();
        if (shouldBeSingle.size() > 1)
            throw new IllegalStateException("Expected single transaction, but found: " + shouldBeSingle.size());

        return shouldBeSingle.getFirst();
    }

    @Override
    public @NotNull MpTransactionEntity save(@NotNull MpTransactionEntity transaction) throws DataAccessException {
        @Language("SQL") String sql = """
                INSERT INTO mp_transaction(
                    id, profile_id, delta_mp, timestamp, task_count)
                VALUES(
                    :id, :profile_id, :delta_mp, :timestamp, :task_count )
                RETURNING id""";

        client.sql(sql)
                .param("id", transaction.id())
                .param("profile_id", transaction.profileId())
                .param("delta_mp", transaction.deltaMp())
                .param("timestamp", transaction.timestamp())
                .param("task_count", transaction.taskCount())
                .update();

        return transaction;
    }
}
