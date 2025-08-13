package com.aome.todoist_mp_api.store.repository;

import com.aome.todoist_mp_api.model.entity.RewardEntity;
import org.jetbrains.annotations.NotNull;

public interface RewardRepository {
    @NotNull RewardEntity save(@NotNull RewardEntity reward);
}
