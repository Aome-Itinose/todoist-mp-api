package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.model.dto.ReduceRequest;

public interface MpServiceFacade {
    int currentMp();
    int reduceMp(ReduceRequest request);
}
