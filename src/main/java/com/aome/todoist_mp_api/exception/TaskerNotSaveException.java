package com.aome.todoist_mp_api.exception;

public class TaskerNotSaveException extends ClientFriendlyException {
    private TaskerNotSaveException(String message) {
        super(message);
    }

    private TaskerNotSaveException(Exception cause) {
        super(cause);
    }

    public static TaskerNotSaveException taskerNotSave(Exception cause) {
        return new TaskerNotSaveException(cause);
    }
}
