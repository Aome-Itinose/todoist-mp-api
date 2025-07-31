package com.aome.todoist_mp_api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TaskDto(
        @JsonProperty("id")
        @NotNull String id,

        @JsonProperty("user_id")
        @NotNull String userId,

        @JsonProperty("content")
        @NotNull String content,

        @JsonProperty("description")
        String description,

        @JsonProperty("completed_at")
        OffsetDateTime completedAt
) {
    public TaskDto {
        Objects.requireNonNull(id);
        Objects.requireNonNull(userId);
        Objects.requireNonNull(content);
    }
}
