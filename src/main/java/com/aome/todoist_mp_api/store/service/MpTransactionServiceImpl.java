package com.aome.todoist_mp_api.store.service;

import com.aome.todoist_mp_api.exception.MpTransactionNotFoundException;
import com.aome.todoist_mp_api.exception.MpTransactionNotSaveException;
import com.aome.todoist_mp_api.model.entity.MpTransactionEntity;
import com.aome.todoist_mp_api.store.repository.MpTransactionRepository;
import com.aome.todoist_mp_api.util.LoggableDebug;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MpTransactionServiceImpl implements MpTransactionService {
    private final MpTransactionRepository transactionRepository;

    @Override
    @LoggableDebug
    public MpTransactionEntity findListCreated() {
        try {
            return transactionRepository.findLastCreated();
        } catch (DataAccessException | IllegalStateException ex) {
            throw MpTransactionNotFoundException.create(ex);
        }
    }

    @Override
    @LoggableDebug
    public Optional<MpTransactionEntity> safeFindLastCreated() {
        try {
            return Optional.of(findListCreated());
        } catch (MpTransactionNotFoundException e) {
            return Optional.empty();
        }
    }

    @Override
    @LoggableDebug
    @Transactional
    public @NotNull MpTransactionEntity save(@NotNull MpTransactionEntity transaction) {
        try {
            return transactionRepository.save(transaction);
        }catch (DataAccessException | IllegalStateException ex) {
            throw MpTransactionNotSaveException.create(ex);
        }
    }
}
