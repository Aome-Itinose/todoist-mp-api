package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.model.entity.QuotaEntity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface QuotaRepository {
    QuotaEntity findByProfileIdAndType(@NotNull UUID profileId, @NotNull QuotaEntity.Type type);
}
