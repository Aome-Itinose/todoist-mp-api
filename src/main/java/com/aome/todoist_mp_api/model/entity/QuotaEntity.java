package com.aome.todoist_mp_api.model.entity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public record QuotaEntity(
        @NotNull UUID id,
        @NotNull UUID profileId,
        @NotNull Type type,
        @NotNull Integer amount
) {
    public QuotaEntity {
        Objects.requireNonNull(id, "id is required");
        Objects.requireNonNull(profileId, "profileId is required");
        Objects.requireNonNull(type, "type is required");
        Objects.requireNonNull(amount, "amount is required");
    }

    public enum Type {
        DEADLINE_POSTPONE;
    }
}
