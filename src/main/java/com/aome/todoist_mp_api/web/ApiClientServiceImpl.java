package com.aome.todoist_mp_api.web;

import com.aome.todoist_mp_api.model.dto.GetTaskDto;
import com.aome.todoist_mp_api.model.dto.GetTaskListDto;
import com.aome.todoist_mp_api.model.dto.GetUserDto;
import com.aome.todoist_mp_api.model.dto.UpdateTaskDto;
import com.aome.todoist_mp_api.util.LoggableDebug;
import com.aome.todoist_mp_api.util.Urls;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
public class ApiClientServiceImpl implements ApiClientService {
    private final RestTemplate restTemplate;

    @Override
    public ApiClientService withRestTemplate(RestTemplate restTemplate) {
        return new ApiClientServiceImpl(restTemplate);
    }

    @Override
    @LoggableDebug
    public GetUserDto loadUser() {
        ResponseEntity<GetUserDto> response = restTemplate.exchange(
                Urls.USER, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

        return response.getBody();
    }

    @Override
    @LoggableDebug
    public GetTaskListDto loadTasksByCompletion(OffsetDateTime start, OffsetDateTime end) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        String since = start.format(formatter);
        String until = end.format(formatter);

        String url = Urls.TASK_BY_COMPLETION_DATE.formatted(since, until);

        ResponseEntity<GetTaskListDto> response = restTemplate.getForEntity(
                url,
                GetTaskListDto.class
        );

        return response.getBody();
    }

    @Override
    @LoggableDebug
    public List<GetTaskDto> loadByLabel(String label) {
        String url = Urls.TASKS_BY_LABEL.formatted(label);
        ResponseEntity<List<GetTaskDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @Override
    @LoggableDebug
    public GetTaskDto updateTask(String taskId, UpdateTaskDto updateTaskDto) {
        String url = Urls.UPDATE_TASK.formatted(taskId);
        ResponseEntity<GetTaskDto> response = restTemplate.postForEntity(
                url,
                updateTaskDto,
                GetTaskDto.class
        );
        return response.getBody();
    }
}
