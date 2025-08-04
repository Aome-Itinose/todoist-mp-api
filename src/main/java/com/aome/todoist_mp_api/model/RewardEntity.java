package com.aome.todoist_mp_api.model;

import lombok.With;

import java.time.OffsetDateTime;

@With
public record RewardEntity(
    Long id,
    Long userId,
    int amount,
    String content,
    String type,
    OffsetDateTime timestamp
) {
    public RewardEntity(
        Long userId,
        int amount,
        String content,
        String type
    ) {
        this(null, userId, amount, content, type, OffsetDateTime.now());
    }
}
