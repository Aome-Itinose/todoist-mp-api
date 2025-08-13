package com.aome.todoist_mp_api.controller;

import com.aome.todoist_mp_api.model.HttpResponse;
import com.aome.todoist_mp_api.model.telegram_service.RegistrationRequest;
import com.aome.todoist_mp_api.service.AuthService;
import com.aome.todoist_mp_api.util.LoggableInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping
    @LoggableInfo
    public ResponseEntity<HttpResponse> sayHello() {
        var response = HttpResponse.success("Hello from Todoist MP API!");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registration")
    @LoggableInfo
    public ResponseEntity<HttpResponse> registration(@RequestBody RegistrationRequest request) {
        String savedUserUsername = authService.registration(request);

        HttpResponse httpResponse = HttpResponse.success("User registered successfully");
        httpResponse.addPayload("username", savedUserUsername);

        return ResponseEntity.ok(httpResponse);
    }
}
