package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.model.TaskDto;
import com.aome.todoist_mp_api.model.TodoistUserDto;

import java.time.OffsetDateTime;
import java.util.List;

public interface TheirService {
    TodoistUserDto loadUser();
    List<TaskDto> loadCompletedTasks(OffsetDateTime start, OffsetDateTime end);
}
