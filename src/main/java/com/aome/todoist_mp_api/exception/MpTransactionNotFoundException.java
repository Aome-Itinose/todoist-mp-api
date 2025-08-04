package com.aome.todoist_mp_api.exception;

import org.jetbrains.annotations.NotNull;

public class MpTransactionNotFoundException extends ClientFriendlyException {
    public MpTransactionNotFoundException(String message) {
        super(message);
    }

    public MpTransactionNotFoundException(@NotNull Exception e) {
        super(e);
    }

    public static MpTransactionNotFoundException create(){
        return new MpTransactionNotFoundException("Mp transaction not found");
    }

    public static MpTransactionNotFoundException create(@NotNull Exception ex) {
        return new MpTransactionNotFoundException(ex);
    }
}
