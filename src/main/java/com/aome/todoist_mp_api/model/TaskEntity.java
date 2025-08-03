package com.aome.todoist_mp_api.model;

import java.time.OffsetDateTime;

public record TaskEntity (
    Long id,
    Long taskerId,
    String content,
    String description,
    OffsetDateTime completedAt,
    Integer mp
) {}
