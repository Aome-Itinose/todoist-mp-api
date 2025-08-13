package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.model.telegram_service.RegistrationRequest;

public interface AuthService {
    String registration(RegistrationRequest request);
}
