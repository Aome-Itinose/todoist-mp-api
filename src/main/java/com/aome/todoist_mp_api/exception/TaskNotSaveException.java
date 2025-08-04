package com.aome.todoist_mp_api.exception;

public class TaskNotSaveException extends ClientFriendlyException {
    public TaskNotSaveException(String message) {
        super(message);
    }

    public TaskNotSaveException(Exception e) {
        super(e);
    }

    public static TaskNotSaveException create(Exception exception) {
        return new TaskNotSaveException(exception);
    }
}
