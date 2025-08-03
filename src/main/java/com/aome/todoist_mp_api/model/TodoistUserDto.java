package com.aome.todoist_mp_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TodoistUserDto(
        @JsonProperty("id")
        @NotNull Long id,
        @JsonProperty("full_name")
        @NotNull String username
) {}
