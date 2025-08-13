package com.aome.todoist_mp_api.controller;

import com.aome.todoist_mp_api.model.HttpResponse;
import com.aome.todoist_mp_api.model.telegram_service.ReduceRequest;
import com.aome.todoist_mp_api.service.MpServiceFacade;
import com.aome.todoist_mp_api.util.LoggableInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mp")
@RequiredArgsConstructor
public class MpController {
    private final MpServiceFacade mpServiceFacade;

    @GetMapping()
    @LoggableInfo
    public ResponseEntity<HttpResponse> getCurrentMp() {
        Integer currentMp = mpServiceFacade.currentMp();
        var response = HttpResponse.success("Current MP retrieved successfully");
        response.addPayload("current_mp", currentMp);
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    @LoggableInfo
    public ResponseEntity<HttpResponse> reduceMp(@RequestBody ReduceRequest request) {
        int currentMp = mpServiceFacade.reduceMp(request);
        var response = HttpResponse.success("MP reduced successfully");
        response.addPayload("current_mp", currentMp);
        return ResponseEntity.ok(response);
    }
}
