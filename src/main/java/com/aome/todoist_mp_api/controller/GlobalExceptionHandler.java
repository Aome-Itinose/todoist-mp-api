package com.aome.todoist_mp_api.controller;

import com.aome.todoist_mp_api.exception.PreconditionFailure;
import com.aome.todoist_mp_api.exception.TaskerNotSaveException;
import com.aome.todoist_mp_api.exception.TodoistRequestFailure;
import com.aome.todoist_mp_api.model.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PreconditionFailure.class)
    public ResponseEntity<HttpResponse> handlePreconditionFailure(PreconditionFailure preconditionFailure) {
        log.error("Precondition failure occurred: {}", preconditionFailure.getMessage(), preconditionFailure);
        return ResponseEntity
                .status(HttpStatus.PRECONDITION_FAILED)
                .body(new HttpResponse(preconditionFailure));
    }

    @ExceptionHandler(TodoistRequestFailure.class)
    public ResponseEntity<HttpResponse> handleTodoistRequestFailure(TodoistRequestFailure todoistRequestFailure) {
        log.error("Todoist request failure occurred: {}", todoistRequestFailure.getMessage(), todoistRequestFailure);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new HttpResponse(todoistRequestFailure.getMessage(), null));
    }

    @ExceptionHandler(TaskerNotSaveException.class)
    public ResponseEntity<HttpResponse> handleTaskerNotSaveException(TaskerNotSaveException taskerNotSaveException) {
        log.error("Tasker not save exception occurred: {}", taskerNotSaveException.getMessage(), taskerNotSaveException);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new HttpResponse(taskerNotSaveException.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> handleGenericException(Exception exception) {
        log.error("Unexpected exception occurred: {}", exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new HttpResponse("An unexpected error occurred", null));
    }
}
