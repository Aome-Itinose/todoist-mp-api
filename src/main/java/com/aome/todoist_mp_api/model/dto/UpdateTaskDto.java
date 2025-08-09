package com.aome.todoist_mp_api.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateTaskDto(
        Long taskId,
        @JsonProperty("due_datetime") String dueDatetime,
        @JsonProperty("due_date") String dueDate
) {}
