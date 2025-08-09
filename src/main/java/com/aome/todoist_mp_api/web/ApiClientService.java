package com.aome.todoist_mp_api.web;

import com.aome.todoist_mp_api.model.dto.GetTaskDto;
import com.aome.todoist_mp_api.model.dto.GetTaskListDto;
import com.aome.todoist_mp_api.model.dto.GetUserDto;
import com.aome.todoist_mp_api.model.dto.UpdateTaskDto;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.List;

public interface ApiClientService {
    ApiClientService withRestTemplate(RestTemplate restTemplate);

    GetUserDto loadUser();

    GetTaskListDto loadTasksByCompletion(OffsetDateTime start, OffsetDateTime end);

    List<GetTaskDto> loadByLabel(String label);

    GetTaskDto updateTask(String taskId, UpdateTaskDto updateTaskDto);
}
