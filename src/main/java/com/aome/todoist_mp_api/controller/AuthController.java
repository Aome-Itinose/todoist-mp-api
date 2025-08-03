package com.aome.todoist_mp_api.controller;

import com.aome.todoist_mp_api.model.RegistrationRequest;
import com.aome.todoist_mp_api.model.HttpResponse;
import com.aome.todoist_mp_api.model.TelegramUserRequest;
import com.aome.todoist_mp_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody TelegramUserRequest request){
        log.info("Login attempt for Telegram user ID: {}", request.userId());
        String response = request.userId();
        log.info("Login successful for Telegram user ID: {}", request.userId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpResponse> registration(@RequestBody RegistrationRequest request){
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
