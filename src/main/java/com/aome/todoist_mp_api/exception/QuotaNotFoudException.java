package com.aome.todoist_mp_api.exception;

public class QuotaNotFoudException extends RuntimeException {
    public QuotaNotFoudException(String message) {
        super(message);
    }

    public QuotaNotFoudException(){
        super("Quota not found");
    }
}
