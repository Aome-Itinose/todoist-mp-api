package com.aome.todoist_mp_api.store.service;

import com.aome.todoist_mp_api.model.entity.MpTransactionEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface MpTransactionService {
    MpTransactionEntity findListCreated();

    Optional<MpTransactionEntity> safeFindLastCreated();
    @NotNull MpTransactionEntity save(@NotNull MpTransactionEntity transaction);
}
