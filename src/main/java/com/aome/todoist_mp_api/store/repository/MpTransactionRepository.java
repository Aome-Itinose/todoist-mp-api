package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.model.entity.MpTransactionEntity;
import org.jetbrains.annotations.NotNull;

public interface MpTransactionRepository {
    @NotNull MpTransactionEntity findLastCreated();
    @NotNull MpTransactionEntity save(@NotNull MpTransactionEntity transaction);
}
