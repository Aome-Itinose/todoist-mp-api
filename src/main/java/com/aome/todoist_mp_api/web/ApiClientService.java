package com.aome.todoist_mp_api.web;

import com.aome.todoist_mp_api.model.todoist_service.GetTaskResponse;
import com.aome.todoist_mp_api.model.todoist_service.GetTaskListDto;
import com.aome.todoist_mp_api.model.todoist_service.GetUserResponse;
import com.aome.todoist_mp_api.model.todoist_service.UpdateTaskRequest;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.List;

public interface ApiClientService {
    ApiClientService withRestTemplate(RestTemplate restTemplate);

    GetUserResponse loadUser();

    GetTaskListDto loadTasksByCompletion(OffsetDateTime start, OffsetDateTime end);

    List<GetTaskResponse> loadByLabel(String label);

    GetTaskResponse updateTask(String taskId, UpdateTaskRequest updateTaskRequest);
}
