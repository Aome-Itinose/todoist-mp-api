package com.aome.todoist_mp_api.exception;

public class MpTransactionNotSaveException extends ClientFriendlyException {
    public MpTransactionNotSaveException(String message) {
        super(message);
    }

    public MpTransactionNotSaveException(Exception e) {
        super(e);
    }
}
