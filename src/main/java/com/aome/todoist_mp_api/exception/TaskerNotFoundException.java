package com.aome.todoist_mp_api.exception;

public class TaskerNotFoundException extends UserFriendlyException {
    public TaskerNotFoundException(String message) {
        super(message);
    }
    public TaskerNotFoundException(Exception e){
        super(e);
    }

    public static TaskerNotFoundException taskerNotFound() {
        return new TaskerNotFoundException("Tasker not found.");
    }
}
