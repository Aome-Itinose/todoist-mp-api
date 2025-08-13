package com.aome.todoist_mp_api.store.service;

import com.aome.todoist_mp_api.exception.QuotaNotFoudException;
import com.aome.todoist_mp_api.model.entity.QuotaEntity;
import com.aome.todoist_mp_api.store.repository.QuotaRepository;
import com.aome.todoist_mp_api.util.LoggableDebug;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuotaServiceImpl implements QuotaService {
    private final QuotaRepository quotaRepository;

    @Override
    @LoggableDebug
    public @NotNull QuotaEntity findByProfileIdAndType(@NotNull UUID profileId, @NotNull QuotaEntity.Type type) {
        try {
            return quotaRepository.findByProfileIdAndType(profileId, type);
        } catch (DataAccessException e) {
            throw new QuotaNotFoudException();
        }
    }

    @Override
    @LoggableDebug
    public @NotNull QuotaEntity safeFindByProfileIdAndType(@NotNull UUID profileId, @NotNull QuotaEntity.Type type) {
        try {
            return findByProfileIdAndType(profileId, type);
        } catch (QuotaNotFoudException e) {
            return getDefaultQuotaByType(profileId, type);
        }
    }

    private @NotNull QuotaEntity getDefaultQuotaByType(UUID profileId, QuotaEntity.Type type) {
        return switch (type) {
            case DEADLINE_POSTPONE -> new QuotaEntity(
                    UUID.randomUUID(),
                    profileId,
                    type,
                    5
            );
        };
    }
}
