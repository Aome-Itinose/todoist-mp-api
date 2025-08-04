package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.model.dto.TaskDto;
import com.aome.todoist_mp_api.model.dto.TodoistUserDto;

import java.time.OffsetDateTime;
import java.util.List;

public interface TheirService {
    TodoistUserDto loadUser();
    List<TaskDto> loadCompletedTasks(OffsetDateTime start, OffsetDateTime end);
}
