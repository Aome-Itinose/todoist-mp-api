package com.aome.todoist_mp_api.validation;

import com.aome.todoist_mp_api.exception.PreconditionFailure;
import com.aome.todoist_mp_api.exception.TodoistRequestFailure;
import com.aome.todoist_mp_api.model.dto.ReduceRequest;
import com.aome.todoist_mp_api.model.dto.RegistrationRequest;
import com.aome.todoist_mp_api.store.service.TaskerService;
import com.aome.todoist_mp_api.util.LoggableDebug;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validator {
    private final TaskerService taskerService;

    @LoggableDebug
    public void validate(RegistrationRequest request) {
        String telegramToken = request.telegramToken();
        String todoistToken = request.todoistToken();

        if (Strings.isBlank(telegramToken)) {
            throw PreconditionFailure.invalidTelegramToken();
        }
        if (Strings.isBlank(todoistToken)) {
            throw PreconditionFailure.invalidTelegramToken();
        }

        if (taskerService.existByTelegramAndTodoistToken(telegramToken, todoistToken)) {
            throw PreconditionFailure.existedUser();
        }

        if (taskerService.existByTelegramToken(telegramToken)) {
            throw PreconditionFailure.existedTelegramToken();
        }

        if (taskerService.existByTodoistToken(todoistToken)) {
            throw PreconditionFailure.existedTodoistToken();
        }
    }

    @LoggableDebug
    public <T> void validate(ResponseEntity<T> response) {
        if (response == null) {
            throw TodoistRequestFailure.responseIsNull();
        }

        if (!response.getStatusCode().is2xxSuccessful()) {
            if (response.getStatusCode().isSameCodeAs(HttpStatus.UNAUTHORIZED)) {
                throw PreconditionFailure.invalidTodoistToken();
            }
            throw TodoistRequestFailure.failure();
        }

        if (!response.hasBody() && response.getBody() != null) {
            throw TodoistRequestFailure.responseHasNoBody();
        }
    }

    @LoggableDebug
    public void validate(ReduceRequest request) {
        if (request.amount() <= 0) {
            throw PreconditionFailure.invalidMpReduceAmount("MP reduction amount must be greater than zero.");
        }
        if (Strings.isBlank(request.reason())) {
            throw PreconditionFailure.invalidContent("MP reduction reason must not be null or blank.");
        }
    }
}
