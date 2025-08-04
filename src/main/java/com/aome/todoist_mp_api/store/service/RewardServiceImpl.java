package com.aome.todoist_mp_api.store.service;

import com.aome.todoist_mp_api.exception.RewardNotSaveException;
import com.aome.todoist_mp_api.model.RewardEntity;
import com.aome.todoist_mp_api.store.repository.RewardRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {
    private final RewardRepository rewardRepository;

    @Override
    public @NotNull RewardEntity save(@NotNull RewardEntity reward) {
        try {
            return rewardRepository.save(reward);
        } catch (DataAccessException ex) {
            throw RewardNotSaveException.create(ex);
        }
    }
}
