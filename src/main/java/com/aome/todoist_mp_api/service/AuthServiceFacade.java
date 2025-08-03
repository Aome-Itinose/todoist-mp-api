package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.model.RegistrationRequest;
import com.aome.todoist_mp_api.model.Tasker;
import com.aome.todoist_mp_api.model.TodoistUserDto;
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
        log.info("Starting registration process for Telegram token: {}", 
                maskToken(registrationRequest.telegramToken()));
        
        validator.validate(registrationRequest);
        log.debug("Registration request validation passed");
        
        Tasker newTasker = new Tasker(
                registrationRequest.todoistToken(),
                registrationRequest.telegramToken()
        );
        log.debug("Created new Tasker instance");
        
        SecurityContextHandler.setAuthentication(newTasker);
        log.debug("Set authentication context");
        
        TodoistUserDto todoistUser = theirService.loadUser();
        log.info("Loaded Todoist user: {} (ID: {})", todoistUser.username(), todoistUser.id());

        newTasker = newTasker
                .withTodoistUsername(todoistUser.username())
                .withTodoistId(todoistUser.id());
        log.debug("Updated Tasker with Todoist user information");

        newTasker = taskerService.save(newTasker);
        log.info("Tasker saved successfully with ID: {}", newTasker.id());
        
        return newTasker.todoistUsername();
    }

    private String maskToken(String token) {
        if (token == null || token.length() <= 8) {
            return "***";
        }
        return token.substring(0, 4) + "***" + token.substring(token.length() - 4);
    }
}
