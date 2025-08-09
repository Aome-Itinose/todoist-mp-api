package com.aome.todoist_mp_api.exception;

public class SecurityContextPrincipalException extends RuntimeException {
    public SecurityContextPrincipalException(String message) {
        super(message);
    }

    public static SecurityContextPrincipalException userNotAuthenticated() {
        return new SecurityContextPrincipalException("User not authenticated.");
    }

    public static SecurityContextPrincipalException of(String message) {
        return new SecurityContextPrincipalException(message);
    }
}
