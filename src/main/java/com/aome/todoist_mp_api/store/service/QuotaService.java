package com.aome.todoist_mp_api.store.service;

import com.aome.todoist_mp_api.model.entity.QuotaEntity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface QuotaService {
    @NotNull QuotaEntity findByProfileIdAndType(@NotNull UUID profileId, @NotNull QuotaEntity.Type type);
    @NotNull QuotaEntity safeFindByProfileIdAndType(@NotNull UUID profileId, @NotNull QuotaEntity.Type type);
}
