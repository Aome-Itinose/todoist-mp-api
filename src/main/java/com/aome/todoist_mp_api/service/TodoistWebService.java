package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.model.dto.TaskDto;
import com.aome.todoist_mp_api.model.dto.TaskListDto;
import com.aome.todoist_mp_api.model.dto.TodoistUserDto;
import com.aome.todoist_mp_api.util.LoggableDebug;
import com.aome.todoist_mp_api.util.Urls;
import com.aome.todoist_mp_api.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoistWebService implements TheirService {
    private final RestTemplate restTemplate;
    private final Validator validator;

    @Override
    @LoggableDebug
    public TodoistUserDto loadUser() {
        ResponseEntity<TodoistUserDto> response;

        try {
            response = restTemplate.exchange(
                    Urls.USER,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );
        } catch (HttpClientErrorException e) {
            response = ResponseEntity.status(e.getStatusCode()).body(null);
        }
        validator.validate(response);

        return response.getBody();
    }

    @Override
    @LoggableDebug
    public List<TaskDto> loadCompletedTasks(OffsetDateTime start, OffsetDateTime end) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        String since = start.format(formatter);
        String until = end.format(formatter);

        String url = Urls.TASK_BY_COMPLETION_DATE.formatted(since, until);

        ResponseEntity<TaskListDto> response;
        try {
            response = restTemplate.getForEntity(
                    url,
                    TaskListDto.class
            );
        } catch (HttpClientErrorException e) {
            response = ResponseEntity.status(e.getStatusCode()).body(null);
        }

        validator.validate(response);
        return response.getBody().tasks();
    }
}
