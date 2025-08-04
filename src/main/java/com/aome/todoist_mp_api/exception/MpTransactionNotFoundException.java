package com.aome.todoist_mp_api.exception;

import org.jetbrains.annotations.NotNull;

public class MpTransactionNotFoundException extends UserFriendlyException {
    public MpTransactionNotFoundException(String message) {
        super(message);
    }

    public static MpTransactionNotFoundException create(){
        return new MpTransactionNotFoundException("Mp transaction not found");
    }

    public static MpTransactionNotFoundException create(@NotNull Exception ex) {
        return new MpTransactionNotFoundException("Mp transaction not found: " + ex.getMessage());
    }
}
