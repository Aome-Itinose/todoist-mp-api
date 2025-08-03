package com.aome.todoist_mp_api.model;

import lombok.With;

@With
public record Tasker(
        Long id,

        String todoistUsername,
        Long todoistId,
        String todoistToken,

        String telegramToken,

        Integer mp
) {
    public Tasker(String todoistUsername, Long todoistId, String todoistToken, String telegramToken){
        this(null, todoistUsername, todoistId, todoistToken, telegramToken, 0);
    }

    public Tasker(String todoistToken, String telegramToken) {
        this(null, null, null, todoistToken, telegramToken, 0);
    }
}
