package com.aome.todoist_mp_api.exception;

import com.aome.todoist_mp_api.model.HttpResponse;
import lombok.Getter;

@Getter
public class PreconditionFailure extends ClientFriendlyException {
    private final HttpResponse.StatusCode errorCode;

    private PreconditionFailure(String message, HttpResponse.StatusCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public static PreconditionFailure invalidTelegramToken() {
        return new PreconditionFailure("Invalid telegram token.", HttpResponse.StatusCode.INVALID_TELEGRAM_TOKEN);
    }

    public static PreconditionFailure invalidTodoistToken() {
        return new PreconditionFailure("Invalid todoist token.", HttpResponse.StatusCode.INVALID_TODOIST_TOKEN);
    }

    public static PreconditionFailure existedTelegramToken() {
        return new PreconditionFailure("Telegram token already exist.", HttpResponse.StatusCode.EXISTED_TELEGRAM_TOKEN);
    }

    public static PreconditionFailure existedTodoistToken() {
        return new PreconditionFailure("Todoist token already exist.", HttpResponse.StatusCode.EXISTED_TODOIST_TOKEN);
    }

    public static PreconditionFailure existedUser() {
        return new  PreconditionFailure("User with this telegram and todoist token already exist.", HttpResponse.StatusCode.EXISTED_USER);
    }

    public static PreconditionFailure invalidMpReduceAmount(String message) {
        return new PreconditionFailure(message, HttpResponse.StatusCode.INVALID_MP_REDUCE_AMOUNT);
    }

    public static PreconditionFailure invalidContent(String message) {
        return new PreconditionFailure(message, HttpResponse.StatusCode.INVALID_CONTENT);
    }
}
