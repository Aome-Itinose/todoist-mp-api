package com.aome.todoist_mp_api.controller;

import com.aome.todoist_mp_api.model.HttpResponse;
import com.aome.todoist_mp_api.model.dto.RegistrationRequest;
import com.aome.todoist_mp_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<HttpResponse> sayHello() {
        var response = HttpResponse.success("Hello from Todoist MP API!");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpResponse> registration(@RequestBody RegistrationRequest request) {
        log.info("Registration attempt for Telegram token: {} and Todoist token: {}",
                maskToken(request.telegramToken()), maskToken(request.todoistToken()));

        String savedUserUsername = authService.registration(request);

        HttpResponse httpResponse = HttpResponse.success("User registered successfully");
        httpResponse.addPayload("username", savedUserUsername);

        log.info("Registration successful for user: {}", savedUserUsername);
        return ResponseEntity.ok(httpResponse);
    }

    private String maskToken(String token) {
        if (token == null || token.length() <= 8) {
            return "***";
        }
        return token.substring(0, 4) + "***" + token.substring(token.length() - 4);
    }
}
