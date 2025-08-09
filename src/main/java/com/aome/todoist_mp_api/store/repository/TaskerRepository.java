package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.model.TaskerEntity;
import org.jetbrains.annotations.NotNull;

public interface TaskerRepository {
    @NotNull TaskerEntity save(@NotNull TaskerEntity tasker);
    @NotNull TaskerEntity update(@NotNull TaskerEntity tasker);
    @NotNull TaskerEntity findByTelegramToken(@NotNull String telegramToken);

    @NotNull TaskerEntity findById(@NotNull Long id);

    boolean existByTodoistToken(@NotNull String todoistToken);
    boolean existByTelegramToken(@NotNull String telegramToken);
    boolean existByTelegramAndTodoistToken(@NotNull String telegramToken, @NotNull String todoistToken);
}
