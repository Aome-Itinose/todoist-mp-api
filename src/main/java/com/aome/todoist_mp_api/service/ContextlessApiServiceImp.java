package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.model.todoist_service.GetTaskResponse;
import com.aome.todoist_mp_api.model.todoist_service.GetUserResponse;
import com.aome.todoist_mp_api.model.todoist_service.UpdateTaskRequest;
import com.aome.todoist_mp_api.web.ApiClientService;
import com.aome.todoist_mp_api.web.RestTemplateFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ContextlessApiServiceImp implements ContextlessApiService {
    private final ApiClientService apiClientService;
    private final RestTemplateFactory restTemplateFactory;

    @Override
    public GetUserResponse getUser(String bearerToken) {
        try {
            RestTemplate templateWithBearer = restTemplateFactory.withBearerToken(bearerToken);
            return apiClientService.withRestTemplate(templateWithBearer).loadUser(); // todo: check if not null. Можно сделать передав в отдельный класс и пометодно делать проверки
        } catch (Exception e) {
            throw new RuntimeException("Failed to load user data", e);// todo: handle this exception properly
        }
    }

    @Override
    public List<GetTaskResponse> getTaskByLabel(String bearerToken, String label) {
        try {
            RestTemplate templateWithBearer = restTemplateFactory.withBearerToken(bearerToken);
            return apiClientService.withRestTemplate(templateWithBearer).loadByLabel(label);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load tasks by label", e); // todo: handle this exception properly
        }
    }

    @Override
    public void updateTasks(String bearerToken, List<UpdateTaskRequest> tasks) {
        try (ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2)) {
            RestTemplate templateWithBearer = restTemplateFactory.withBearerToken(bearerToken);
            for (int i = 0; i < tasks.size(); i++) {
                final int finalI = i;
                scheduler.schedule(() -> {
                    String taskId = String.valueOf(tasks.get(finalI).taskId());
                    apiClientService.withRestTemplate(templateWithBearer).updateTask(taskId, tasks.get(finalI));
                }, i, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update tasks", e); // todo: handle this exception properly
        }
    }
}
