package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.model.todoist_service.GetTaskResponse;
import com.aome.todoist_mp_api.model.todoist_service.GetUserResponse;
import com.aome.todoist_mp_api.model.todoist_service.UpdateTaskRequest;

import java.util.List;

public interface ContextlessApiService {
    GetUserResponse getUser(String bearerToken);

    List<GetTaskResponse> getTaskByLabel(String bearerToken, String label);

    void updateTasks(String bearerToken, List<UpdateTaskRequest> tasks);
}
