package com.aome.todoist_mp_api.exception;

public class TaskerNotSaveException extends UserFriendlyException {
    private TaskerNotSaveException(String message) {
        super(message);
    }

    public static TaskerNotSaveException taskerNotSave(Throwable cause) {
        return new TaskerNotSaveException("Tasker not saved. Cause: " + cause.getMessage());
    }
}
