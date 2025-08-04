package com.aome.todoist_mp_api.exception;

public class TaskerNotFoundException extends ClientFriendlyException {
    public TaskerNotFoundException(String message) {
        super(message);
    }
    public TaskerNotFoundException(Exception e){
        super(e);
    }

    public static TaskerNotFoundException create(Exception e){
        return new TaskerNotFoundException(e);
    }
}
