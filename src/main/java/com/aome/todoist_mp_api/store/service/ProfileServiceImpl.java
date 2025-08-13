package com.aome.todoist_mp_api.store.service;

import com.aome.todoist_mp_api.exception.ProfileNotFoundException;
import com.aome.todoist_mp_api.exception.ProfileNotSaveException;
import com.aome.todoist_mp_api.model.entity.ProfileEntity;
import com.aome.todoist_mp_api.store.repository.ProfileRepository;
import com.aome.todoist_mp_api.util.LoggableDebug;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository repository;

    @Override
    @LoggableDebug
    @Transactional
    public @NotNull ProfileEntity save(@NotNull ProfileEntity profile) {
        try {
            return repository.save(profile);
        } catch (IllegalStateException e) {
            throw new ProfileNotSaveException(e);
        }
    }

    @Override
    @LoggableDebug
    @Transactional
    public @NotNull ProfileEntity update(@NotNull ProfileEntity profile) {
        try {
            return repository.update(profile);
        } catch (IllegalStateException e) {
            throw new ProfileNotSaveException(e);
        }
    }

    @Override
    @LoggableDebug
    @Transactional
    public @NotNull ProfileEntity updateMp(@NotNull UUID id, int mp) {
        try {
            return repository.updateMp(id, mp);
        } catch (IllegalStateException e) {
            throw new ProfileNotSaveException(e);
        }
    }

    @Override
    @LoggableDebug
    public @NotNull ProfileEntity findByTelegramToken(@NotNull String telegramToken) {
        try {
            return repository.findByTelegramToken(telegramToken);
        } catch (DataAccessException ex) {
            throw new ProfileNotFoundException(ex);
        }
    }

    @Override
    @LoggableDebug
    public @NotNull ProfileEntity findById(@NotNull UUID id) {
        try {
            return repository.findById(id);
        } catch (DataAccessException ex) {
            throw new ProfileNotFoundException(ex);
        }
    }

    @Override
    @LoggableDebug
    public boolean existByTodoistToken(@NotNull String todoistToken) {
        try {
            return repository.existByTodoistToken(todoistToken);
        } catch (DataAccessException ex) {
            throw new ProfileNotFoundException(ex);
        }
    }

    @Override
    @LoggableDebug
    public boolean existByTelegramToken(@NotNull String telegramToken) {
        try {
            return repository.existByTelegramToken(telegramToken);
        } catch (DataAccessException ex) {
            throw new ProfileNotFoundException(ex);
        }
    }

    @Override
    @LoggableDebug
    public boolean existByTelegramAndTodoistToken(@NotNull String telegramToken, @NotNull String todoistToken) {
        try {
            return repository.existByTelegramAndTodoistToken(telegramToken, todoistToken);
        } catch (DataAccessException ex) {
            throw new ProfileNotFoundException(ex);
        }
    }
}
