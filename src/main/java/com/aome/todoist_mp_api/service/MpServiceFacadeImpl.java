package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.converter.Converter;
import com.aome.todoist_mp_api.exception.PreconditionFailure;
import com.aome.todoist_mp_api.model.MpTransactionEntity;
import com.aome.todoist_mp_api.model.RewardEntity;
import com.aome.todoist_mp_api.model.TaskEntity;
import com.aome.todoist_mp_api.model.TaskerEntity;
import com.aome.todoist_mp_api.model.dto.GetTaskDto;
import com.aome.todoist_mp_api.model.dto.ReduceRequest;
import com.aome.todoist_mp_api.store.service.MpTransactionService;
import com.aome.todoist_mp_api.store.service.RewardService;
import com.aome.todoist_mp_api.store.service.TaskService;
import com.aome.todoist_mp_api.store.service.TaskerService;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class MpServiceFacadeImpl implements MpServiceFacade {
    private final ContextualApiService apiService;

    private final TaskerService taskerService;
    private final TaskService taskService;
    private final MpTransactionService mpTransactionService;
    private final RewardService rewardService;
    private final Validator validator;

    @Override
    @LoggableDebug
    @Transactional
    public int currentMp() {
        TaskerEntity tasker = SecurityContextHandler.authenticatedUser();
        Long taskerId = tasker.id();

        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime lastUpdate = lastUpdatedOrDefault();

        List<GetTaskDto> completedGetTaskDtos = apiService.getTasksByCompletion(lastUpdate, now);
        List<TaskEntity> completedTasks = completedGetTaskDtos.stream()
                .map(dto -> Converter.toEntity(dto, taskerId))
                .toList();

        var transaction = MpTransactionEntity.fromCompletedTasks(taskerId, completedTasks);

        taskService.saveAll(completedTasks);
        mpTransactionService.save(transaction);
        tasker = taskerService.update(tasker.withAddMp(transaction.deltaMp()));

        return tasker.mp();
    }

    @Override
    @LoggableDebug
    @Transactional
    public int reduceMp(ReduceRequest request) {
        validator.validate(request);
        TaskerEntity tasker = SecurityContextHandler.authenticatedUser();
        Long taskerId = tasker.id();

        if (tasker.mp() < request.amount()) {
            throw PreconditionFailure.invalidMpReduceAmount("Not enough MP to reduce");
        }

        int amount = -request.amount();

        var transaction = new MpTransactionEntity(
                taskerId,
                amount,
                OffsetDateTime.now(),
                -1
        );
        var reward = new RewardEntity(
                taskerId,
                request.amount(),
                request.reason(),
                "REWARD"
        );

        mpTransactionService.save(transaction);
        rewardService.save(reward);
        tasker = taskerService.update(tasker.withAddMp(amount));

        return tasker.mp();
    }

    @LoggableDebug
    private OffsetDateTime lastUpdatedOrDefault() {
        OffsetDateTime offsetDateTime;
        Optional<MpTransactionEntity> maybeLastEntity = mpTransactionService.safeFindLastCreated();
        if (maybeLastEntity.isPresent()) {
            offsetDateTime = maybeLastEntity.get().timestamp();
        } else {
            // Default to 30 days ago if no last update found
            offsetDateTime = OffsetDateTime.now().minusDays(30);
        }
        return offsetDateTime.plusSeconds(1);
    }
}
