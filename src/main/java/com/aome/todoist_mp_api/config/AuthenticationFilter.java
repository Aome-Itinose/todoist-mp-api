package com.aome.todoist_mp_api.config;

import com.aome.todoist_mp_api.exception.TaskerNotFoundException;
import com.aome.todoist_mp_api.model.TaskerEntity;
import com.aome.todoist_mp_api.store.service.TaskerService;
import com.aome.todoist_mp_api.util.SecurityContextHandler;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final TaskerService taskerService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        String bearerToken = getBearerToken(request);
        if (bearerToken.isEmpty()) {
            log.warn("No Bearer token provided for {} request to {}", request.getMethod(), request.getRequestURI());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No Bearer token provided");
            return;
        }

        log.debug("Authenticating request with token: {}", maskToken(bearerToken));
        try {
            String telegramToken = jwtUtil.validateAndGetChatId(bearerToken);
            authenticateTasker(telegramToken);
        } catch (TaskerNotFoundException | JWTVerificationException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Bearer token");
        }

        filterChain.doFilter(request, response);
    }

    private String getBearerToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return Strings.EMPTY;
    }

    private void authenticateTasker(String telegramToken) throws IOException {
        TaskerEntity tasker = taskerService.findByTelegramToken(telegramToken);
        SecurityContextHandler.setAuthentication(tasker);
        log.debug("Security context set for user: {}", tasker.todoistUsername());
    }

    private String maskToken(String token) {
        if (token == null || token.length() <= 8) {
            return "***";
        }
        return token.substring(0, 4) + "***" + token.substring(token.length() - 4);
    }
}
