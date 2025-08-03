package com.aome.todoist_mp_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

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
) {}
