package com.aome.todoist_mp_api.exception;

public class TaskNotSaveException extends RuntimeException {
    public TaskNotSaveException(String message) {
        super(message);
    }

    public static TaskNotSaveException create(Exception exception) {
        return new TaskNotSaveException("Failed to save tasks: " + exception.getMessage());
    }
}
