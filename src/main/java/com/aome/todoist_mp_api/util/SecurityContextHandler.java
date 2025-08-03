package com.aome.todoist_mp_api.util;

import com.aome.todoist_mp_api.model.Tasker;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class SecurityContextHandler {

    public static Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String todoistToken(){
        return authenticatedUser().todoistToken();
    }

    public static Tasker authenticatedUser() {
        return (Tasker) authentication().getPrincipal();
    }

    public static void setAuthentication(Tasker tasker) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(tasker, null, List.of()));
    }
}
