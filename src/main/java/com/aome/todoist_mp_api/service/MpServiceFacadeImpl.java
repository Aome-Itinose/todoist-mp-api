package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.converter.Converter;
import com.aome.todoist_mp_api.exception.PreconditionFailure;
import com.aome.todoist_mp_api.model.entity.MpTransactionEntity;
import com.aome.todoist_mp_api.model.entity.ProfileEntity;
import com.aome.todoist_mp_api.model.entity.RewardEntity;
import com.aome.todoist_mp_api.model.entity.TaskEntity;
import com.aome.todoist_mp_api.model.telegram_service.ReduceRequest;
import com.aome.todoist_mp_api.model.todoist_service.GetTaskResponse;
import com.aome.todoist_mp_api.store.service.MpTransactionService;
import com.aome.todoist_mp_api.store.service.ProfileService;
import com.aome.todoist_mp_api.store.service.RewardService;
import com.aome.todoist_mp_api.store.service.TaskService;
import com.aome.todoist_mp_api.util.LoggableDebug;
import com.aome.todoist_mp_api.util.SecurityContextHandler;
import com.aome.todoist_mp_api.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MpServiceFacadeImpl implements MpServiceFacade {
    private final ContextualApiService apiService;

    private final ProfileService profileService;
    private final TaskService taskService;
    private final MpTransactionService mpTransactionService;
    private final RewardService rewardService;
    private final Validator validator;

    @Override
    @LoggableDebug
    @Transactional
    public int currentMp() {
        ProfileEntity profile = SecurityContextHandler.authenticatedUser();
        UUID profileId = profile.id();

        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime lastUpdate = lastUpdatedOrDefault();

        List<GetTaskResponse> completedGetTaskResponses = apiService.getTasksByCompletion(lastUpdate, now);
        List<TaskEntity> completedTasks = completedGetTaskResponses.stream()
                .map(dto -> Converter.toEntity(dto, profileId))
                .toList();

        var transaction = MpTransactionEntity.fromCompletedTasks(profileId, completedTasks);

        taskService.saveAll(completedTasks);
        mpTransactionService.save(transaction);
        profile = profileService.update(profile.incrementMp(transaction.deltaMp()));

        return profile.mp();
    }

    @Override
    @LoggableDebug
    @Transactional
    public int reduceMp(ReduceRequest request) {
        validator.validate(request);

        var rewardToCreate = new RewardToCreate(request, "REDUCE")
                .throwIfContentIsEmpty();


        var profile = profileService.findById(SecurityContextHandler.authenticatedUser().id());
        rewardToCreate = rewardToCreate.withProfile(profile)
                .throwIfInsufficientMp();

        mpTransactionService.save(rewardToCreate.toTransaction());
        rewardService.save(rewardToCreate.toEntity());

        profile = profileService.updateMp(profile.id(), profile.mp() + rewardToCreate.amount());

        return profile.mp();
    }

    @LoggableDebug
    private OffsetDateTime lastUpdatedOrDefault() {
        OffsetDateTime offsetDateTime;
        Optional<MpTransactionEntity> maybeLastEntity = mpTransactionService.safeFindLastCreated();
        // Default to 30 days ago if no last update found
        offsetDateTime = maybeLastEntity.map(MpTransactionEntity::timestamp).orElseGet(() -> OffsetDateTime.now().minusDays(30));
        return offsetDateTime.plusSeconds(1);
    }

    record RewardToCreate(
            UUID profileId,
            int amount,
            String content,
            String type,
            ProfileEntity profile
    ) {
        public RewardToCreate(
                ReduceRequest reduceRequest,
                String type
        ) {
            this(
                    SecurityContextHandler.authenticatedUser().id(),
                    Math.negateExact(reduceRequest.amount()),
                    reduceRequest.reason(),
                    type,
                    null
            );
        }

        public RewardToCreate withProfile(ProfileEntity profile) {
            return new RewardToCreate(
                    profileId,
                    amount,
                    content,
                    type,
                    profile
            );
        }

        public RewardToCreate throwIfInsufficientMp() {
            if (profile.mp() < amount) {
                throw PreconditionFailure.invalidMpReduceAmount("Not enough MP to reduce");
            }
            return this;
        }

        public RewardToCreate throwIfContentIsEmpty() {
            if (content.isBlank()) {
                throw PreconditionFailure.invalidContent("MP reduction reason must not be null or blank.");
            }
            return this;
        }

        public RewardEntity toEntity() {
            return new RewardEntity(
                    profileId,
                    amount,
                    content,
                    type
            );
        }

        public MpTransactionEntity toTransaction() {
            return new MpTransactionEntity(
                    profileId,
                    amount,
                    -1
            );
        }
    }
}
