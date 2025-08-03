package com.aome.todoist_mp_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TaskListDto(
        @JsonProperty("items")
        List<TaskDto> tasks
) {}
