package com.aome.todoist_mp_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record TelegramUserRequest(
        @JsonProperty("user_id")
        @NotNull String userId
) {}
