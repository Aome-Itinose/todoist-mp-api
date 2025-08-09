package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.model.TaskerEntity;
import com.aome.todoist_mp_api.model.dto.GetUserDto;
import com.aome.todoist_mp_api.model.dto.RegistrationRequest;
import com.aome.todoist_mp_api.store.service.TaskerService;
import com.aome.todoist_mp_api.util.LoggableDebug;
import com.aome.todoist_mp_api.util.SecurityContextHandler;
import com.aome.todoist_mp_api.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceFacade implements AuthService {
    private final ContextlessApiService apiService;
    private final TaskerService taskerService;

    private final Validator validator;

    @LoggableDebug
    @Transactional
    public String registration(RegistrationRequest registrationRequest) {
        validator.validate(registrationRequest);
        GetUserDto todoistUser = apiService.getUser(registrationRequest.todoistToken());

        TaskerEntity newTasker = new TaskerEntity(
                registrationRequest.todoistToken(),
                registrationRequest.telegramToken()
        );

        SecurityContextHandler.setAuthentication(newTasker);

        newTasker = newTasker
                .withTodoistUsername(todoistUser.username())
                .withTodoistId(todoistUser.id());

        newTasker = taskerService.save(newTasker);
        return newTasker.todoistUsername();
    }
}
