package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.model.dto.RegistrationRequest;
import com.aome.todoist_mp_api.model.TaskerEntity;
import com.aome.todoist_mp_api.model.dto.TodoistUserDto;
import com.aome.todoist_mp_api.store.service.TaskerService;
import com.aome.todoist_mp_api.util.SecurityContextHandler;
import com.aome.todoist_mp_api.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceFacade implements AuthService {
    private final TheirService theirService;
    private final TaskerService taskerService;

    private final Validator validator;

    public String registration(RegistrationRequest registrationRequest) {
        validator.validate(registrationRequest);

        TaskerEntity newTasker = new TaskerEntity(
                registrationRequest.todoistToken(),
                registrationRequest.telegramToken()
        );

        SecurityContextHandler.setAuthentication(newTasker);
        TodoistUserDto todoistUser = theirService.loadUser();

        newTasker = newTasker
                .withTodoistUsername(todoistUser.username())
                .withTodoistId(todoistUser.id());

        newTasker = taskerService.save(newTasker);
        return newTasker.todoistUsername();
    }
}
