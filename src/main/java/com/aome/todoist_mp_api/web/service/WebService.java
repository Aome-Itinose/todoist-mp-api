package com.aome.todoist_mp_api.web.service;

import com.aome.todoist_mp_api.models.TaskDto;

import java.time.OffsetDateTime;
import java.util.List;

public interface WebService {
    List<TaskDto> getTasksCompletedByCompletionDate(OffsetDateTime start, OffsetDateTime end);
}
