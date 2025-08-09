package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.model.dto.GetTaskDto;
import com.aome.todoist_mp_api.model.dto.GetUserDto;
import com.aome.todoist_mp_api.model.dto.UpdateTaskDto;

import java.util.List;

public interface ContextlessApiService {
    GetUserDto getUser(String bearerToken);

    List<GetTaskDto> getTaskByLabel(String bearerToken, String label);

    void updateTasks(String bearerToken, List<UpdateTaskDto> tasks);
}
