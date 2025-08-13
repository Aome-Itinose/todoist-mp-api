package com.aome.todoist_mp_api.model.entity;

import lombok.With;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@With
public record RewardEntity(
    @NotNull UUID id,
    @NotNull UUID profileId,
    @NotNull Integer amount,
    @NotNull String content,
    @NotNull String type,
    @NotNull OffsetDateTime timestamp
) {
    public RewardEntity {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(profileId);
        Objects.requireNonNull(amount);
        Objects.requireNonNull(content);
        Objects.requireNonNull(type);
        Objects.requireNonNull(timestamp);
    }

    public RewardEntity(
            @NotNull UUID profileId,
            @NotNull Integer amount,
            @NotNull String content,
            @NotNull String type
    ) {
        this(UUID.randomUUID(), profileId, amount, content, type, OffsetDateTime.now());
    }
}
