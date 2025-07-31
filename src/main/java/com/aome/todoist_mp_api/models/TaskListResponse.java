package com.aome.todoist_mp_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TaskListResponse(
        @JsonProperty("items")
        List<TaskDto> tasks
) {}
