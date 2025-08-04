package com.aome.todoist_mp_api.exception;

public class ClientFriendlyException extends RuntimeException {
    protected ClientFriendlyException(String message) {
        super(message);
    }

    protected ClientFriendlyException(Exception cause) {
        super(cause.getMessage(), cause);
    }
}
