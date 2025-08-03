package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.model.MpTransactionEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface MpTransactionRepository {
    Optional<MpTransactionEntity> findLastCreated();
    @NotNull MpTransactionEntity save(@NotNull MpTransactionEntity transaction);
}
