package com.aome.todoist_mp_api.controller;

import com.aome.todoist_mp_api.exception.PreconditionFailure;
import com.aome.todoist_mp_api.exception.ClientFriendlyException;
import com.aome.todoist_mp_api.model.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PreconditionFailure.class)
    public ResponseEntity<HttpResponse> handlePreconditionFailure(PreconditionFailure preconditionFailure) {
        log.error("Precondition failure occurred: {}", preconditionFailure.getMessage(), preconditionFailure);
        return ResponseEntity
                .status(HttpStatus.PRECONDITION_FAILED)
                .body(new HttpResponse(preconditionFailure));
    }

    @ExceptionHandler(ClientFriendlyException.class)
    public ResponseEntity<HttpResponse> handleClientFriendlyException(ClientFriendlyException clientFriendlyException) {
        log.error("User-friendly exception occurred: {}", clientFriendlyException.getMessage(), clientFriendlyException);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new HttpResponse(clientFriendlyException.getMessage(), HttpResponse.StatusCode.INTERNAL));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> handleGenericException(Exception exception) {
        log.error("Unexpected exception occurred: {}", exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new HttpResponse("An unexpected error occurred", HttpResponse.StatusCode.INTERNAL));
    }
}
