package com.aome.todoist_mp_api.store.service;

import com.aome.todoist_mp_api.exception.MpTransactionNotFoundException;
import com.aome.todoist_mp_api.exception.MpTransactionNotSaveException;
import com.aome.todoist_mp_api.model.MpTransactionEntity;
import com.aome.todoist_mp_api.store.repository.MpTransactionRepository;
import com.aome.todoist_mp_api.util.LoggableDebug;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class MpTransactionServiceImpl implements MpTransactionService {
    private final MpTransactionRepository transactionRepository;

    @Override
    @LoggableDebug
    public OffsetDateTime getLastCreatedTimestamp() {
        try {
            return transactionRepository.findLastCreated().timestamp();
        }catch (DataAccessException ex) {
            throw MpTransactionNotFoundException.create(ex);
        }
    }

    @Override
    @LoggableDebug
    public @NotNull MpTransactionEntity save(@NotNull MpTransactionEntity transaction) {
        try {
            return transactionRepository.save(transaction);
        }catch (DataAccessException | IllegalStateException ex) {
            throw MpTransactionNotSaveException.create(ex);
        }
    }
}
