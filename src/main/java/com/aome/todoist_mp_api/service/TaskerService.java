package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.model.Tasker;
import org.jetbrains.annotations.NotNull;

public interface TaskerService {
    @NotNull Tasker save(@NotNull Tasker tasker);
    @NotNull Tasker findByTelegramToken(@NotNull String telegramToken);

    boolean existByTodoistToken(@NotNull String todoistToken);
    boolean existByTelegramToken(@NotNull String telegramToken);
    boolean existByTelegramAndTodoistToken(@NotNull String telegramToken, @NotNull String todoistToken);
}
