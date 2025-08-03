package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.model.Tasker;

import java.util.Optional;

public interface TaskerRepository {
    Tasker save(Tasker tasker);
    Optional<Tasker> findByTelegramToken(String telegramToken);

    boolean existByTodoistToken(String todoistToken);
    boolean existByTelegramToken(String telegramToken);
    boolean existByTelegramAndTodoistToken(String telegramToken, String todoistToken);
}
