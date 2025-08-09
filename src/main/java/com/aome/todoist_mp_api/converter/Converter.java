package com.aome.todoist_mp_api.converter;

import com.aome.todoist_mp_api.model.TaskEntity;
import com.aome.todoist_mp_api.model.dto.GetTaskDto;
import com.aome.todoist_mp_api.model.dto.UpdateTaskDto;

import java.time.format.DateTimeFormatter;

public class Converter {
    public static TaskEntity toEntity(GetTaskDto dto, Long userId) {
        return new TaskEntity(
                dto.id(),
                userId,
                dto.content(),
                dto.description(),
                dto.completedAt(),
                new TaskParameterParser(dto.content(), dto.description()).getMp()
        );
    }

    public static UpdateTaskDto toUpdateDto(GetTaskDto e) {
        return new UpdateTaskDto(
                e.id(),
                e.due().getDateTime() == null ? null : e.due().getDateTime().format(DateTimeFormatter.ISO_INSTANT),
                e.due().getDate() == null ? null : e.due().getDate().toString()
        );
    }
}
