package com.aome.todoist_mp_api.web.service;

import com.aome.todoist_mp_api.models.TaskDto;
import com.aome.todoist_mp_api.models.TaskListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoistWebServiceImpl implements WebService {
    private static final String BEARER_TOKEN = "24bc83f1634349f9358008b1051a92568c50b3d1";

    private final RestTemplate restTemplate;

    @Override
    public List<TaskDto> getTasksCompletedByCompletionDate(OffsetDateTime start, OffsetDateTime end) {
        String url = "https://api.todoist.com/api/v1/tasks/completed/by_completion_date?since=%s&until=%s"
                .formatted(start, end);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(BEARER_TOKEN);
        ResponseEntity<TaskListResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {}
        );

        TaskListResponse completedTasks = response.getBody();
        return completedTasks.tasks();
    }
}
