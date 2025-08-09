package com.aome.todoist_mp_api.exception;

public class TodoistRequestFailure extends RuntimeException {
    private TodoistRequestFailure(String message) {
        super(message);
    }

    public static TodoistRequestFailure failure(String message){
        return new TodoistRequestFailure(message);
    }

    public static TodoistRequestFailure responseIsNull() {
        return new TodoistRequestFailure("Todoist response is null.");
    }

    public static TodoistRequestFailure responseHasNoBody() {
        return new TodoistRequestFailure("Todoist response has no body.");
    }
}
