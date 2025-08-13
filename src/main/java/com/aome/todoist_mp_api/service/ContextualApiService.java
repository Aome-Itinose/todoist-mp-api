package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.model.todoist_service.GetTaskResponse;

import java.time.OffsetDateTime;
import java.util.List;

public interface ContextualApiService {
    List<GetTaskResponse> getTasksByCompletion(OffsetDateTime start, OffsetDateTime end);
}
