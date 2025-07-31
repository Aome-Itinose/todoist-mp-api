package com.aome.todoist_mp_api.controller;

import com.aome.todoist_mp_api.models.TaskDto;
import com.aome.todoist_mp_api.web.service.WebService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("/mp")
@RequiredArgsConstructor
public class MvController {
    private final WebService service;

    @GetMapping
    public String getCurrentMp(){
        return "oaoao";
    }

    @GetMapping("/tasks")
    public List<TaskDto> getTasks() {
        return service.getTasksCompletedByCompletionDate(
                LocalDateTime.of(2025, Month.JUNE, 1, 0, 0, 0).atOffset(ZoneOffset.UTC),
                LocalDateTime.now().atOffset(ZoneOffset.UTC)
        );
    }
}
