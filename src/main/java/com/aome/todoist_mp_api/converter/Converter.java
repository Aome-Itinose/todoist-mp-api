package com.aome.todoist_mp_api.converter;

import com.aome.todoist_mp_api.model.entity.TaskEntity;
import com.aome.todoist_mp_api.model.todoist_service.GetTaskResponse;
import com.aome.todoist_mp_api.model.todoist_service.UpdateTaskRequest;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Converter {
    public static TaskEntity toEntity(GetTaskResponse dto, UUID profileId) {
        return new TaskEntity(
                dto.id(),
                profileId,
                dto.content(),
                dto.description(),
                dto.completedAt(),
                new TaskParameterParser(dto.content(), dto.description()).getMp()
        );
    }

    public static UpdateTaskRequest toUpdateDto(GetTaskResponse e) {
        return new UpdateTaskRequest(
                e.id(),
                e.due().getDateTime() == null ? null : e.due().getDateTime().format(DateTimeFormatter.ISO_INSTANT),
                e.due().getDate() == null ? null : e.due().getDate().toString()
        );
    }
}
