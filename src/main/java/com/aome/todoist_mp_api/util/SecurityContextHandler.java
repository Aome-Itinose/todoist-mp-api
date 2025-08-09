package com.aome.todoist_mp_api.util;

import com.aome.todoist_mp_api.exception.SecurityContextPrincipalException;
import com.aome.todoist_mp_api.model.TaskerEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Slf4j
public class SecurityContextHandler {

    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
                authentication.isAuthenticated() &&
                authentication instanceof UsernamePasswordAuthenticationToken;
    }

    public static @NotNull Authentication authentication() {
        if (!isAuthenticated()) throw SecurityContextPrincipalException.userNotAuthenticated();
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String todoistBearerToken() {
        String token = authenticatedUser().todoistToken();
        if (Strings.isBlank(token))
            throw SecurityContextPrincipalException.of("Todoist token is not set for the authenticated user.");
        return token;
    }

    public static @NotNull TaskerEntity authenticatedUser() {
        if (authentication().getPrincipal() instanceof TaskerEntity entity) return entity;
        throw SecurityContextPrincipalException.of("Invalid authentication principal type.");
    }

    public static void setAuthentication(TaskerEntity tasker) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(tasker, null, List.of()));
        log.debug("Security context set for tasker: id={}, telegram_token={}", tasker.id(), tasker.telegramToken());
    }
}
