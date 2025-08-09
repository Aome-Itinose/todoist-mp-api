package com.aome.todoist_mp_api.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetTaskListDto(
        @JsonProperty("items")
        List<GetTaskDto> tasks
) {}
