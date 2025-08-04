package com.aome.todoist_mp_api.exception;

public class MpTransactionNotSaveException extends ClientFriendlyException {
    public MpTransactionNotSaveException(String message) {
        super(message);
    }

    public MpTransactionNotSaveException(Exception e) {
        super(e);
    }

    public static MpTransactionNotSaveException create() {
        return new MpTransactionNotSaveException("Mp transaction not saved");
    }

    public static MpTransactionNotSaveException create(Exception ex) {
        return new MpTransactionNotSaveException(ex);
    }
}
