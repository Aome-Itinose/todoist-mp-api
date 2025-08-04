package com.aome.todoist_mp_api.model;

import lombok.With;

import java.time.OffsetDateTime;
import java.util.List;

@With
public record MpTransactionEntity(
    Long id,
    Long taskerId,
    Integer deltaMp,
    OffsetDateTime timestamp,
    Integer taskCount
) {
    public MpTransactionEntity(
            Long taskerId,
            Integer deltaMp,
            Integer taskCount
    ){
        this(null, taskerId, deltaMp, OffsetDateTime.now(), taskCount);
    }

    public MpTransactionEntity(
            Long taskerId,
            Integer deltaMp,
            OffsetDateTime timestamp,
            Integer taskCount
    ) {
        this(null, taskerId, deltaMp, timestamp, taskCount);
    }

    public static MpTransactionEntity fromCompletedTasks(Long taskerId, List<TaskEntity> completedTasks) {
        return new MpTransactionEntity(
                taskerId,
                completedTasks.stream().mapToInt(TaskEntity::mp).sum(),
                completedTasks.size()
        );
    }
}
