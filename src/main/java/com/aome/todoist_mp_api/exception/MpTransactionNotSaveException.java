package com.aome.todoist_mp_api.exception;

public class MpTransactionNotSaveException extends UserFriendlyException {
    public MpTransactionNotSaveException(String message) {
        super(message);
    }

    public static MpTransactionNotSaveException create() {
        return new MpTransactionNotSaveException("Mp transaction not saved");
    }

    public static MpTransactionNotSaveException create(Exception ex) {
        return new MpTransactionNotSaveException("Mp transaction not saved: " + ex.getMessage());
    }
}
