package com.aome.todoist_mp_api.store.service;

import com.aome.todoist_mp_api.model.MpTransactionEntity;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

public interface MpTransactionService {
    OffsetDateTime getLastCreatedTimestamp();
    @NotNull MpTransactionEntity save(@NotNull MpTransactionEntity transaction);
}
