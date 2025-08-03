package com.aome.todoist_mp_api.store.service;

import com.aome.todoist_mp_api.exception.TaskerNotFoundException;
import com.aome.todoist_mp_api.exception.TaskerNotSaveException;
import com.aome.todoist_mp_api.model.TaskerEntity;
import com.aome.todoist_mp_api.store.repository.TaskerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskerServiceImpl implements TaskerService {
    private final TaskerRepository repository;

    @Override
    public @NotNull TaskerEntity save(@NotNull TaskerEntity tasker) {
        log.info("Saving tasker with Telegram token: {} and Todoist ID: {}",
                maskToken(tasker.telegramToken()), tasker.todoistId());

        try {
            TaskerEntity savedTasker = repository.save(tasker);
            log.info("Tasker saved successfully with ID: {}", savedTasker.id());
            return savedTasker;
        } catch (IllegalStateException e) {
            log.error("Failed to save tasker: {}", e.getMessage(), e);
            throw TaskerNotSaveException.taskerNotSave(e);
        }
    }

    @Override
    public @NotNull TaskerEntity update(@NotNull TaskerEntity tasker) {
        log.info("Updating tasker with ID: {} and Telegram token: {}",
                tasker.id(), maskToken(tasker.telegramToken()));

        try {
            TaskerEntity updatedTasker = repository.update(tasker);
            log.info("Tasker updated successfully with ID: {}", updatedTasker.id());
            return updatedTasker;
        } catch (IllegalStateException e) {
            log.error("Failed to update tasker: {}", e.getMessage(), e);
            throw TaskerNotSaveException.taskerNotSave(e);
        }
    }

    @Override
    public @NotNull TaskerEntity findByTelegramToken(@NotNull String telegramToken) {
        log.debug("Finding tasker by Telegram token: {}", maskToken(telegramToken));

        TaskerEntity tasker = repository.findByTelegramToken(telegramToken)
                .orElseThrow(TaskerNotFoundException::taskerNotFound);

        log.debug("Found tasker with ID: {} for Telegram token: {}",
                tasker.id(), maskToken(telegramToken));
        return tasker;
    }

    @Override
    public boolean existByTodoistToken(@NotNull String todoistToken) {
        log.debug("Checking existence by Todoist token: {}", maskToken(todoistToken));
        boolean exists = repository.existByTodoistToken(todoistToken);
        log.debug("Todoist token exists: {}", exists);
        return exists;
    }

    @Override
    public boolean existByTelegramToken(@NotNull String telegramToken) {
        log.debug("Checking existence by Telegram token: {}", maskToken(telegramToken));
        boolean exists = repository.existByTelegramToken(telegramToken);
        log.debug("Telegram token exists: {}", exists);
        return exists;
    }

    @Override
    public boolean existByTelegramAndTodoistToken(@NotNull String telegramToken, @NotNull String todoistToken) {
        log.debug("Checking existence by Telegram token: {} and Todoist token: {}",
                maskToken(telegramToken), maskToken(todoistToken));
        boolean exists = repository.existByTelegramAndTodoistToken(telegramToken, todoistToken);
        log.debug("Combination of tokens exists: {}", exists);
        return exists;
    }

    private String maskToken(String token) {
        if (token == null || token.length() <= 8) {
            return "***";
        }
        return token.substring(0, 4) + "***" + token.substring(token.length() - 4);
    }
}
