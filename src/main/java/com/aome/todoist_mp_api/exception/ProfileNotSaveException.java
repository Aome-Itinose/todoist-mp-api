package com.aome.todoist_mp_api.exception;

public class ProfileNotSaveException extends ClientFriendlyException {
    public ProfileNotSaveException(String message) {
        super(message);
    }

    public ProfileNotSaveException(Exception cause) {
        super(cause);
    }
}
