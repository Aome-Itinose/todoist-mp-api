package com.aome.todoist_mp_api.exception;

public class ProfileNotFoundException extends ClientFriendlyException {
    public ProfileNotFoundException(String message) {
        super(message);
    }

    public ProfileNotFoundException() {
        super("Profile not found");
    }

    public ProfileNotFoundException(Exception e) {
        super(e);
    }
}
