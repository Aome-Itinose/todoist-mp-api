package com.aome.todoist_mp_api.model;

import lombok.With;

@With
public record TaskerEntity(
        Long id,

        String todoistUsername,
        Long todoistId,
        String todoistToken,

        String telegramToken,

        Integer mp
) {
    public TaskerEntity(String todoistUsername, Long todoistId, String todoistToken, String telegramToken){
        this(null, todoistUsername, todoistId, todoistToken, telegramToken, 0);
    }

    public TaskerEntity(String todoistToken, String telegramToken) {
        this(null, null, null, todoistToken, telegramToken, 0);
    }

    public TaskerEntity withAddMp(Integer deltaMp) {
        return new TaskerEntity(
                id,
                todoistUsername,
                todoistId,
                todoistToken,
                telegramToken,
                mp + deltaMp
        );
    }
}
