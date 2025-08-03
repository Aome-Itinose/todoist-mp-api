package com.aome.todoist_mp_api.controller;

import com.aome.todoist_mp_api.model.HttpResponse;
import com.aome.todoist_mp_api.service.MpServiceFacade;
import com.aome.todoist_mp_api.util.SecurityContextHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/mp")
@RequiredArgsConstructor
public class MpController {
    private final MpServiceFacade mpServiceFacade;

    @GetMapping("/message")
    public String getMessage(){
        String username = SecurityContextHandler.authenticatedUser().todoistUsername();
        log.info("Getting message for user: {}", username);
        String message = "Hello, %s, this is the current MP!".formatted(username);
        log.debug("Generated message: {}", message);
        return message;
    }

    @GetMapping()
    public ResponseEntity<HttpResponse> getCurrentMp() {
        log.info("Getting current MP");
        Integer currentMp = mpServiceFacade.currentMp();
        log.debug("Current MP value: {}", currentMp);
        var response = HttpResponse.success("Current MP retrieved successfully");
        response.addPayload("current_mp", currentMp);
        log.info("Current MP retrieved successfully for user: {}", 
                SecurityContextHandler.authenticatedUser().todoistUsername());
        return ResponseEntity.ok(response);
    }
}
