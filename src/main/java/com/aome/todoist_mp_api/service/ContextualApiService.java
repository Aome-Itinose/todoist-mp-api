package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.model.dto.GetTaskDto;

import java.time.OffsetDateTime;
import java.util.List;

public interface ContextualApiService {
    List<GetTaskDto> getTasksByCompletion(OffsetDateTime start, OffsetDateTime end);
}
