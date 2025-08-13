package com.aome.todoist_mp_api.exception;

import org.jetbrains.annotations.NotNull;

public class MpTransactionNotFoundException extends ClientFriendlyException {
    public MpTransactionNotFoundException(String message) {
        super(message);
    }

    public MpTransactionNotFoundException(@NotNull Exception e) {
        super(e);
    }

    public MpTransactionNotFoundException(){
        super("MpTransaction not found");
    }
}
