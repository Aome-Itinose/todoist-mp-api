package com.aome.todoist_mp_api.model.entity;

import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public record TaskEntity (
    @NotNull UUID id,
    @NotNull Long todoistId,
    @NotNull UUID profileId,
    @NotNull String content,
    String description,
    OffsetDateTime completedAt,
    @NotNull Integer mp
) {
    public TaskEntity {
        Objects.requireNonNull(id);
        Objects.requireNonNull(todoistId, "Todoist ID cannot be null");
        Objects.requireNonNull(profileId, "Profile ID cannot be null");
        Objects.requireNonNull(content, "Content cannot be null");
        Objects.requireNonNull(mp, "MP cannot be null");
    }

    public TaskEntity(
        @NotNull Long todoistId,
        @NotNull UUID profileId,
        @NotNull String content,
        String description,
        OffsetDateTime completedAt,
        @NotNull Integer mp
    ) {
        this(UUID.randomUUID(), todoistId, profileId, content, description, completedAt, mp);
    }
}
