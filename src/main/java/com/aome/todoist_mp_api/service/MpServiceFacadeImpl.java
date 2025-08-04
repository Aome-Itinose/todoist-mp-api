package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.exception.MpTransactionNotFoundException;
import com.aome.todoist_mp_api.exception.PreconditionFailure;
import com.aome.todoist_mp_api.model.MpTransactionEntity;
import com.aome.todoist_mp_api.model.RewardEntity;
import com.aome.todoist_mp_api.model.TaskEntity;
import com.aome.todoist_mp_api.model.TaskerEntity;
import com.aome.todoist_mp_api.model.dto.ReduceRequest;
import com.aome.todoist_mp_api.model.dto.TaskDto;
import com.aome.todoist_mp_api.store.service.MpTransactionService;
import com.aome.todoist_mp_api.store.service.RewardService;
import com.aome.todoist_mp_api.store.service.TaskService;
import com.aome.todoist_mp_api.store.service.TaskerService;
import com.aome.todoist_mp_api.util.Converter;
import com.aome.todoist_mp_api.util.SecurityContextHandler;
import com.aome.todoist_mp_api.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class MpServiceFacadeImpl implements MpServiceFacade {
    private final TheirService theirService;

    private final TaskerService taskerService;
    private final TaskService taskService;
    private final MpTransactionService mpTransactionService;
    private final RewardService rewardService;
    private final Validator validator;

    @Override
    public int currentMp() {
        TaskerEntity tasker = SecurityContextHandler.authenticatedUser();
        Long taskerId = tasker.id();

        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime lastUpdate = lastUpdatedOrDefault();

        List<TaskDto> completedTaskDtos = theirService.loadCompletedTasks(lastUpdate, now);
        List<TaskEntity> completedTasks = completedTaskDtos.stream()
                .map(dto -> Converter.toEntity(dto, taskerId))
                .toList();

        var transaction = MpTransactionEntity.fromCompletedTasks(taskerId, completedTasks);

        taskService.saveAll(completedTasks);
        mpTransactionService.save(transaction);
        tasker = taskerService.update(tasker.withAddMp(transaction.deltaMp()));

        return tasker.mp();
    }

    @Override
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

    private OffsetDateTime lastUpdatedOrDefault() {
        OffsetDateTime offsetDateTime = null;
        try {
            offsetDateTime = mpTransactionService.getLastCreatedTimestamp();
        } catch (MpTransactionNotFoundException e) {
            log.info("MpTransaction not found, using default last update time");
        }
        // Default to 30 days ago if no last update found
        return Objects.requireNonNullElseGet(offsetDateTime, () -> OffsetDateTime.now().minusDays(30)).plusSeconds(1);
    }
}
