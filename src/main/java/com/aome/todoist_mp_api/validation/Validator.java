package com.aome.todoist_mp_api.validation;

import com.aome.todoist_mp_api.exception.PreconditionFailure;
import com.aome.todoist_mp_api.exception.TodoistRequestFailure;
import com.aome.todoist_mp_api.model.RegistrationRequest;
import com.aome.todoist_mp_api.service.TaskerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Validator {
    private final TaskerService taskerService;

    public void validate(RegistrationRequest request) {
        log.debug("Validating registration request");
        
        String telegramToken = request.telegramToken();
        String todoistToken = request.todoistToken();

        if (Strings.isBlank(telegramToken)) {
            log.warn("Registration validation failed: invalid Telegram token");
            throw PreconditionFailure.invalidTelegramToken();
        }
        if (Strings.isBlank(todoistToken)) {
            log.warn("Registration validation failed: invalid Todoist token");
            throw PreconditionFailure.invalidTelegramToken();
        }

        log.debug("Checking if user already exists with both tokens");
        if (taskerService.existByTelegramAndTodoistToken(telegramToken, todoistToken)) {
            log.warn("Registration validation failed: user already exists with both tokens");
            throw PreconditionFailure.existedUser();
        }
        
        log.debug("Checking if Telegram token already exists");
        if (taskerService.existByTelegramToken(telegramToken)) {
            log.warn("Registration validation failed: Telegram token already exists");
            throw PreconditionFailure.existedTelegramToken();
        }
        
        log.debug("Checking if Todoist token already exists");
        if (taskerService.existByTodoistToken(todoistToken)) {
            log.warn("Registration validation failed: Todoist token already exists");
            throw PreconditionFailure.existedTodoistToken();
        }
        
        log.info("Registration request validation passed");
    }

    public <T> void validate(ResponseEntity<T> response) {
        log.debug("Validating HTTP response");
        
        if (response == null) {
            log.error("Response validation failed: response is null");
            throw TodoistRequestFailure.responseIsNull();
        }
        
        if (!response.getStatusCode().is2xxSuccessful()) {
            if (response.getStatusCode().isSameCodeAs(HttpStatus.UNAUTHORIZED)) {
                log.warn("Response validation failed: unauthorized (invalid Todoist token)");
                throw PreconditionFailure.invalidTodoistToken();
            }
            log.error("Response validation failed: HTTP status {}", response.getStatusCode());
            throw TodoistRequestFailure.failure();
        }
        
        if (!response.hasBody()) {
            log.error("Response validation failed: response has no body");
            throw TodoistRequestFailure.responseHasNoBody();
        }

        log.debug("HTTP response validation passed");
    }
}
