package com.aome.todoist_mp_api.model.entity;

import lombok.With;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

@With
public record ProfileEntity(
        @NotNull UUID id,
        String todoistUsername,
        @NotNull Long todoistId,
        @NotNull String todoistToken,

        @NotNull String telegramToken,

        @NotNull Integer mp
) {
    public ProfileEntity {
        Objects.requireNonNull(id);
        Objects.requireNonNull(todoistId, "Todoist ID cannot be null");
        Objects.requireNonNull(todoistToken, "Todoist token cannot be null");
        Objects.requireNonNull(telegramToken, "Telegram token cannot be null");
        Objects.requireNonNull(mp, "MP cannot be null");
    }

    public ProfileEntity(
            String todoistUsername,
            @NotNull Long todoistId,
            @NotNull String todoistToken,
            @NotNull String telegramToken
    ) {
        this(UUID.randomUUID(), todoistUsername, todoistId, todoistToken, telegramToken, 0);
    }

    public ProfileEntity incrementMp(int amount) {
        return this.withMp(this.mp + amount);
    }
}
