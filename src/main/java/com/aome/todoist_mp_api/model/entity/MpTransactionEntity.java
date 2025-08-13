package com.aome.todoist_mp_api.model.entity;

import lombok.With;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@With
public record MpTransactionEntity(
    @NotNull UUID id,
    @NotNull UUID profileId,
    @NotNull Integer deltaMp,
    @NotNull OffsetDateTime timestamp,
    @NotNull Integer taskCount
) {
    public MpTransactionEntity {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(profileId, "Profile ID cannot be null");
        Objects.requireNonNull(deltaMp, "Delta MP cannot be null");
        Objects.requireNonNull(timestamp, "Timestamp cannot be null");
        Objects.requireNonNull(taskCount, "Task count cannot be null");
    }

    public MpTransactionEntity(
            UUID profileId,
            Integer deltaMp,
            Integer taskCount
    ){
        this(UUID.randomUUID(), profileId, deltaMp, OffsetDateTime.now(), taskCount);
    }

    public static MpTransactionEntity fromCompletedTasks(UUID profileId, List<TaskEntity> completedTasks) {
        return new MpTransactionEntity(
                profileId,
                completedTasks.stream().mapToInt(TaskEntity::mp).sum(),
                completedTasks.size()
        );
    }
}
