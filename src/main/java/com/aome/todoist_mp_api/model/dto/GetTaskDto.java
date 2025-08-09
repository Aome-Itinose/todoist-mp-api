package com.aome.todoist_mp_api.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.With;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@With
public record GetTaskDto(
        @JsonProperty("id")
        @NotNull Long id,

        @JsonProperty("user_id")
        Long userId,

        @JsonProperty("content")
        @NotNull String content,

        @JsonProperty("description")
        String description,

        @JsonProperty("completed_at")
        OffsetDateTime completedAt,

        @JsonProperty("due")
        TaskDueDto due
) {}
