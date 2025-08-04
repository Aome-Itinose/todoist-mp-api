package com.aome.todoist_mp_api.util;

import com.aome.todoist_mp_api.model.TaskerEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Slf4j
public class SecurityContextHandler {

    public static Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String todoistToken(){
        return authenticatedUser().todoistToken();
    }

    public static TaskerEntity authenticatedUser() {
        return (TaskerEntity) authentication().getPrincipal();
    }

    public static void setAuthentication(TaskerEntity tasker) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(tasker, null, List.of()));
        log.debug("Security context set for tasker: id={}, telegram_token={}", tasker.id(), tasker.telegramToken());
    }
}
