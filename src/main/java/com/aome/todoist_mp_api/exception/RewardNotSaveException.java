package com.aome.todoist_mp_api.exception;

public class RewardNotSaveException extends ClientFriendlyException {
    public RewardNotSaveException(String message) {
        super(message);
    }

    public RewardNotSaveException(Exception e) {
        super(e);
    }

    public static RewardNotSaveException create(Exception ex) {
        return new RewardNotSaveException(ex);
    }
}
