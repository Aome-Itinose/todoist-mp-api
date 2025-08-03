package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.model.TaskEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface TaskRepository {
    void saveAll(@NotNull List<TaskEntity> tasks);
}
