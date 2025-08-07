package com.aome.todoist_mp_api.store.service;

import com.aome.todoist_mp_api.exception.TaskerNotFoundException;
import com.aome.todoist_mp_api.exception.TaskerNotSaveException;
import com.aome.todoist_mp_api.model.TaskerEntity;
import com.aome.todoist_mp_api.store.repository.TaskerRepository;
import com.aome.todoist_mp_api.util.LoggableDebug;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskerServiceImpl implements TaskerService {
    private final TaskerRepository repository;

    @Override
    @LoggableDebug
    @Transactional
    public @NotNull TaskerEntity save(@NotNull TaskerEntity tasker) {
        try {
            return repository.save(tasker);
        } catch (IllegalStateException e) {
            throw TaskerNotSaveException.taskerNotSave(e);
        }
    }

    @Override
    @LoggableDebug
    @Transactional
    public @NotNull TaskerEntity update(@NotNull TaskerEntity tasker) {
        try {
            return repository.update(tasker);
        } catch (IllegalStateException e) {
            throw TaskerNotSaveException.taskerNotSave(e);
        }
    }

    @Override
    @LoggableDebug
    public @NotNull TaskerEntity findByTelegramToken(@NotNull String telegramToken) {
        try {
            return repository.findByTelegramToken(telegramToken);
        } catch (DataAccessException ex) {
            throw TaskerNotFoundException.create(ex);
        }
    }

    @Override
    @LoggableDebug
    public boolean existByTodoistToken(@NotNull String todoistToken) {
        try {
            return repository.existByTodoistToken(todoistToken);
        } catch (DataAccessException ex) {
            throw TaskerNotFoundException.create(ex);
        }
    }

    @Override
    @LoggableDebug
    public boolean existByTelegramToken(@NotNull String telegramToken) {
        try {
            return repository.existByTelegramToken(telegramToken);
        }catch (DataAccessException ex) {
            throw TaskerNotFoundException.create(ex);
        }
    }

    @Override
    @LoggableDebug
    public boolean existByTelegramAndTodoistToken(@NotNull String telegramToken, @NotNull String todoistToken) {
        try {
            return repository.existByTelegramAndTodoistToken(telegramToken, todoistToken);
        } catch (DataAccessException ex) {
            throw TaskerNotFoundException.create(ex);
        }
    }
}
