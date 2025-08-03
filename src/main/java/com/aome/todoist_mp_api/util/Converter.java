package com.aome.todoist_mp_api.util;

import com.aome.todoist_mp_api.model.TaskDto;
import com.aome.todoist_mp_api.model.TaskEntity;

public class Converter {
    public static TaskEntity toEntity(TaskDto dto, Long userId) {
        return new TaskEntity(
                dto.id(),
                userId,
                dto.content(),
                dto.description(),
                dto.completedAt(),
                new TaskParameterParser(dto.content(), dto.description()).getMp()
        );
    }

}
