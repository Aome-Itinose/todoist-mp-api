package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.model.dto.RegistrationRequest;

public interface AuthService {
    String registration(RegistrationRequest request);
}
