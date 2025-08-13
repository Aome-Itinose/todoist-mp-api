package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.model.entity.QuotaEntity;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class QuotaRepositoryImpl implements QuotaRepository {
    private final JdbcClient client;

    @Override
    public QuotaEntity findByProfileIdAndType(@NotNull UUID profileId, @NotNull QuotaEntity.Type type) {
        @Language("SQL") String sql = """
                SELECT
                    id, profile_id, type, amount
                FROM quota
                WHERE
                    profile_id = :profileId
                AND
                    type = :type""";


        return client.sql(sql)
                .param("profileId", profileId)
                .param("type", type.name())
                .query(QuotaEntity.class)
                .single();
    }
}
