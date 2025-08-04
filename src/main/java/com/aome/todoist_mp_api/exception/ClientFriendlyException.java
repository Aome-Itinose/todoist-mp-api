package com.aome.todoist_mp_api.exception;

public class ClientFriendlyException extends RuntimeException {
    protected ClientFriendlyException(String message) {
        super(message);
    }

    protected ClientFriendlyException(Exception cause) {
        super(cause);
    }

    protected ClientFriendlyException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ClientFriendlyException(Throwable cause) {
        super(cause);
    }
}
