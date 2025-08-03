package com.aome.todoist_mp_api.store.service;

import com.aome.todoist_mp_api.exception.MpTransactionNotFoundException;
import com.aome.todoist_mp_api.exception.MpTransactionNotSaveException;
import com.aome.todoist_mp_api.model.MpTransactionEntity;
import com.aome.todoist_mp_api.store.repository.MpTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MpTransactionServiceImpl implements MpTransactionService {
    private final MpTransactionRepository transactionRepository;

    @Override
    public OffsetDateTime getLastCreatedTimestamp() {
        try {
            Optional<MpTransactionEntity> maybeEntity = transactionRepository.findLastCreated();
            return maybeEntity.map(MpTransactionEntity::timestamp).orElse(null);
        }catch (DataAccessException ex) {
            throw MpTransactionNotFoundException.create(ex);
        }
    }

    @Override
    public @NotNull MpTransactionEntity save(@NotNull MpTransactionEntity transaction) {
        try {
            return transactionRepository.save(transaction);
        }catch (DataAccessException | IllegalStateException ex) {
            throw MpTransactionNotSaveException.create(ex);
        }
    }
}
