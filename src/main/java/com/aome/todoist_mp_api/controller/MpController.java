package com.aome.todoist_mp_api.controller;

import com.aome.todoist_mp_api.model.HttpResponse;
import com.aome.todoist_mp_api.model.dto.ReduceRequest;
import com.aome.todoist_mp_api.service.MpServiceFacade;
import com.aome.todoist_mp_api.util.SecurityContextHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/mp")
@RequiredArgsConstructor
public class MpController {
    private final MpServiceFacade mpServiceFacade;

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

    @PatchMapping
    public ResponseEntity<HttpResponse> reduceMp(@RequestBody ReduceRequest request) {
        int currentMp = mpServiceFacade.reduceMp(request);
        log.info("Reduced MP by {}. New MP: {}", request.amount(), currentMp);
        var response = HttpResponse.success("MP reduced successfully");
        response.addPayload("current_mp", currentMp);
        return ResponseEntity.ok(response);
    }
}
