package com.aome.todoist_mp_api.exception;

public class UserFriendlyException extends RuntimeException {
    public UserFriendlyException(String message) {
        super(message);
    }

    public UserFriendlyException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserFriendlyException(Throwable cause) {
        super(cause);
    }
}
