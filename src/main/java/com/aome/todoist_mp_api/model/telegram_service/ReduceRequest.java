package com.aome.todoist_mp_api.model.telegram_service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record ReduceRequest(
        @JsonProperty("amount")
        int amount,
        @JsonProperty("reason")
        @NotNull String reason
) {}
