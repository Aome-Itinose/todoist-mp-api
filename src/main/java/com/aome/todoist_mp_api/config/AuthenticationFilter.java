package com.aome.todoist_mp_api.config;

import com.aome.todoist_mp_api.exception.ProfileNotFoundException;
import com.aome.todoist_mp_api.model.entity.ProfileEntity;
import com.aome.todoist_mp_api.store.service.ProfileService;
import com.aome.todoist_mp_api.util.JwtUtil;
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
    private final JwtUtil jwtUtil;
    private final ProfileService profileService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        String bearerToken = getBearerToken(request);
        if (bearerToken.isEmpty()) {
            log.trace("No Bearer token provided for {} request to {}", request.getMethod(), request.getRequestURI());
        } else {
            log.debug("Authenticating request with token: {}", maskToken(bearerToken));
            try {
                String telegramToken = jwtUtil.validateAndGetChatId(bearerToken);
                authenticateTasker(telegramToken);
            } catch (ProfileNotFoundException e) {
                log.warn("Tasker not found for token {}", bearerToken);
            } catch (JWTVerificationException e) {
                log.warn("JWT verification failed for token {}: {}", maskToken(bearerToken), e.getMessage());
            } catch (Exception e) {
                log.error("Unexpected error during authentication for token {}: {}", maskToken(bearerToken), e.getMessage(), e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Authentication failed");
                return;
            }
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

    private void authenticateTasker(String telegramToken) {
        ProfileEntity profile = profileService.findByTelegramToken(telegramToken);
        SecurityContextHandler.setAuthentication(profile);
    }

    private String maskToken(String token) {
        if (token == null || token.length() <= 8) {
            return "***";
        }
        return token.substring(0, 4) + "***" + token.substring(token.length() - 4);
    }
}
