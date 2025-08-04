package com.aome.todoist_mp_api.model;

import lombok.With;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.util.Objects;

@With
public record RewardEntity(
    Long id,
    @NotNull Long userId,
    @NotNull Integer amount,
    @NotNull String content,
    @NotNull String type,
    @NotNull OffsetDateTime timestamp
) {
    public RewardEntity {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(amount);
        Objects.requireNonNull(content);
        Objects.requireNonNull(type);
        Objects.requireNonNull(timestamp);
    }

    public RewardEntity(
            @NotNull Long userId,
            @NotNull Integer amount,
            @NotNull String content,
            @NotNull String type
    ) {
        this(null, userId, amount, content, type, OffsetDateTime.now());
    }
}
