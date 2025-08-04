package com.aome.todoist_mp_api.exception;

public class RewardNotSaveException extends UserFriendlyException {
    public RewardNotSaveException(String message) {
        super(message);
    }

    public static RewardNotSaveException create(Exception ex) {
        return new RewardNotSaveException("Failed to save reward: " + ex.getMessage());
    }
}
