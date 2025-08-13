package com.aome.todoist_mp_api.store.service;

import com.aome.todoist_mp_api.exception.TaskNotSaveException;
import com.aome.todoist_mp_api.model.entity.TaskEntity;
import com.aome.todoist_mp_api.store.repository.TaskRepository;
import com.aome.todoist_mp_api.util.LoggableDebug;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;

    @Override
    @LoggableDebug
    @Transactional
    public void saveAll(@NotNull List<TaskEntity> tasks) {
        try {
            repository.saveAll(tasks);
        } catch (Exception ex) {
            throw TaskNotSaveException.create(ex);
        }
    }
}
