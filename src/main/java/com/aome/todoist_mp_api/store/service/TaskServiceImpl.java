package com.aome.todoist_mp_api.store.service;

import com.aome.todoist_mp_api.exception.TaskNotSaveException;
import com.aome.todoist_mp_api.model.TaskEntity;
import com.aome.todoist_mp_api.store.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;

    @Override
    public void saveAll(@NotNull List<TaskEntity> tasks) {
        try {
            repository.saveAll(tasks);
        } catch (Exception ex) {
            throw TaskNotSaveException.create(ex);
        }
    }
}
