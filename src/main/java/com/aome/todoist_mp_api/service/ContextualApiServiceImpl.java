package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.model.dto.GetTaskDto;
import com.aome.todoist_mp_api.web.ApiClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContextualApiServiceImpl implements ContextualApiService {
    private final ApiClientService apiClientService;

    @Override
    public List<GetTaskDto> getTasksByCompletion(OffsetDateTime start, OffsetDateTime end) {
        try {
            return apiClientService.loadTasksByCompletion(start, end).tasks(); // todo: описано выше
        } catch (Exception e) {
            throw new RuntimeException("Failed to load completed tasks", e); // todo: handle this exception properly
        }
    }
}
