package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.model.TaskerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface TaskerRepository {
    @NotNull TaskerEntity save(@NotNull TaskerEntity tasker);
    @NotNull TaskerEntity update(@NotNull TaskerEntity tasker);
    Optional<TaskerEntity> findByTelegramToken(@NotNull String telegramToken);

    boolean existByTodoistToken(@NotNull String todoistToken);
    boolean existByTelegramToken(@NotNull String telegramToken);
    boolean existByTelegramAndTodoistToken(@NotNull String telegramToken, @NotNull String todoistToken);
}
