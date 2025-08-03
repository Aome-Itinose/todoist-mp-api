package com.aome.todoist_mp_api.store.service;

import com.aome.todoist_mp_api.model.TaskerEntity;
import org.jetbrains.annotations.NotNull;

public interface TaskerService {
    @NotNull TaskerEntity save(@NotNull TaskerEntity tasker);

    @NotNull TaskerEntity update(@NotNull TaskerEntity tasker);

    @NotNull TaskerEntity findByTelegramToken(@NotNull String telegramToken);

    boolean existByTodoistToken(@NotNull String todoistToken);
    boolean existByTelegramToken(@NotNull String telegramToken);
    boolean existByTelegramAndTodoistToken(@NotNull String telegramToken, @NotNull String todoistToken);
}
