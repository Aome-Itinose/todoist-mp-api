package com.aome.todoist_mp_api.model.todoist_service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateTaskRequest(
        Long taskId,
        @JsonProperty("due_datetime") String dueDatetime,
        @JsonProperty("due_date") String dueDate
) {}
