package com.aome.todoist_mp_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record RegistrationRequest(
        @JsonProperty("telegram_token")
        @NotNull String telegramToken,
        @JsonProperty("todoist_token")
        @NotNull String todoistToken
) {}
