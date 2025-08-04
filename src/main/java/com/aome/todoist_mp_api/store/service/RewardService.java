package com.aome.todoist_mp_api.store.service;

import com.aome.todoist_mp_api.model.RewardEntity;
import org.jetbrains.annotations.NotNull;

public interface RewardService {
    @NotNull RewardEntity save(@NotNull RewardEntity reward);
}
