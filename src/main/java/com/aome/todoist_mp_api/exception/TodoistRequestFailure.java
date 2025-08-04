package com.aome.todoist_mp_api.exception;

public class TodoistRequestFailure extends UserFriendlyException {
    private TodoistRequestFailure(String message) {
        super(message);
    }

    public static TodoistRequestFailure failure(){
        return new TodoistRequestFailure("Todoist request failed.");
    }

    public static TodoistRequestFailure responseIsNull() {
        return new TodoistRequestFailure("Todoist response is null.");
    }

    public static TodoistRequestFailure responseHasNoBody() {
        return new TodoistRequestFailure("Todoist response has no body.");
    }
}
