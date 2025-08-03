package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.model.MpTransactionEntity;
import com.aome.todoist_mp_api.model.TaskDto;
import com.aome.todoist_mp_api.model.TaskEntity;
import com.aome.todoist_mp_api.model.TaskerEntity;
import com.aome.todoist_mp_api.store.service.MpTransactionService;
import com.aome.todoist_mp_api.store.service.TaskService;
import com.aome.todoist_mp_api.store.service.TaskerService;
import com.aome.todoist_mp_api.util.Converter;
import com.aome.todoist_mp_api.util.SecurityContextHandler;
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

    private OffsetDateTime lastUpdatedOrDefault() {
        OffsetDateTime offsetDateTime = mpTransactionService.getLastCreatedTimestamp();
        // Default to 30 days ago if no last update found
        return Objects.requireNonNullElseGet(offsetDateTime, () -> OffsetDateTime.now().minusDays(30)).plusSeconds(1);
    }
}
