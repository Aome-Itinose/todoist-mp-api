package com.aome.todoist_mp_api.store.service;

import com.aome.todoist_mp_api.model.entity.ProfileEntity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface ProfileService {
    @NotNull ProfileEntity save(@NotNull ProfileEntity profile);

    @NotNull ProfileEntity update(@NotNull ProfileEntity profile);
    @NotNull ProfileEntity updateMp(@NotNull UUID id, int mp);

    @NotNull ProfileEntity findByTelegramToken(@NotNull String telegramToken);
    @NotNull ProfileEntity findById(@NotNull UUID id);

    boolean existByTodoistToken(@NotNull String todoistToken);
    boolean existByTelegramToken(@NotNull String telegramToken);
    boolean existByTelegramAndTodoistToken(@NotNull String telegramToken, @NotNull String todoistToken);
}
