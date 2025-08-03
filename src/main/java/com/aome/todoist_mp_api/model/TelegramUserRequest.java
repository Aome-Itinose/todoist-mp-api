package com.aome.todoist_mp_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record TelegramUserRequest(
        @JsonProperty("user_id")
        @NotNull String userId
) {}
