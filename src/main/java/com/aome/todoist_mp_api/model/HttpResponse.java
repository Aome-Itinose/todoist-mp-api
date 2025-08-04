package com.aome.todoist_mp_api.model;

import com.aome.todoist_mp_api.exception.PreconditionFailure;

import java.util.HashMap;
import java.util.Map;

// Todo: make common for all modules
public record HttpResponse(
        StatusCode code,
        String message,
        Map<String, Object> payload
) {
    public HttpResponse(String message, StatusCode code) {
        this(code, message, new HashMap<>());
    }

    public HttpResponse(PreconditionFailure preconditionFailure) {
        this(preconditionFailure.getMessage(), preconditionFailure.getErrorCode());
    }

    public void addPayload(String key, Object value) {
        payload.put(key, value);
    }

    public Object getPayload(String key) {
        if (!payload.containsKey(key)) {
            throw new IllegalArgumentException("Payload does not contain key: " + key);
        }
        return payload.get(key);
    }

    public static HttpResponse success(String message) {
        return new HttpResponse(message, StatusCode.SUCCESS);
    }

    public enum StatusCode {
        SUCCESS,
        INVALID_TELEGRAM_TOKEN,
        INVALID_TODOIST_TOKEN,
        EXISTED_TELEGRAM_TOKEN,
        EXISTED_TODOIST_TOKEN,
        EXISTED_USER,
        INVALID_MP_REDUCE_AMOUNT,
        INVALID_CONTENT,
        INTERNAL
    }
}
