package com.aome.todoist_mp_api.scheduling;

import com.aome.todoist_mp_api.converter.Converter;
import com.aome.todoist_mp_api.converter.TaskParameterParser;
import com.aome.todoist_mp_api.model.entity.QuotaEntity;
import com.aome.todoist_mp_api.model.todoist_service.GetTaskResponse;
import com.aome.todoist_mp_api.model.entity.ProfileEntity;
import com.aome.todoist_mp_api.service.ContextlessApiService;
import com.aome.todoist_mp_api.store.service.ProfileService;
import com.aome.todoist_mp_api.store.service.QuotaService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class DeadlineAwareTaskPostponeJob extends RunnableJob {
    private static final String LABEL = "with_deadline";
    private static final String NAME = "DeadlineAwareTaskPostponeJob";

    @Getter
    private final UUID profileId;

    private final ProfileService profileService;
    private final ContextlessApiService apiService;
    private final QuotaService quotaService;

    @Override
    public void execute() {
        status = Status.RUNNING;

        ProfileEntity profile = profileService.findById(profileId);
        String todoistToken = profile.todoistToken();

        List<GetTaskResponse> tasks = apiService.getTaskByLabel(todoistToken, LABEL);
        QuotaEntity quota = quotaService.findByProfileIdAndType(profileId, QuotaEntity.Type.DEADLINE_POSTPONE);

        // Filter tasks that have a deadline in the future and are due today or earlier
        // and limit the number of tasks to the quota amount
        List<GetTaskResponse> filteredTasks = tasks.stream()
                .filter(taskDto -> {
                    LocalDate deadline = new TaskParameterParser(taskDto.content(), taskDto.description()).getDeadline();

                    OffsetDateTime now = OffsetDateTime.now();
                    LocalDate nowDate = now.toLocalDate();
                    return deadline != null &&
                            deadline.isAfter(nowDate) &&
                            ((taskDto.due().getDateTime() != null && taskDto.due().getDateTime().isBefore(now)) ||
                                    (taskDto.due().getDate() != null && taskDto.due().getDate().isBefore(nowDate)));
                })
                .limit(quota.amount()).toList();

        // Change the due date of the filtered tasks to today
        filteredTasks.forEach(filteredTask -> {
            OffsetDateTime dateTime = filteredTask.due().getDateTime();
            if (dateTime != null) {
                OffsetDateTime now = OffsetDateTime.now();
                dateTime = dateTime.withDayOfYear(now.getDayOfYear()).withYear(now.getYear());
            }

            LocalDate date = filteredTask.due().getDate();
            if (date != null) {
                date = date.withDayOfYear(LocalDate.now().getDayOfYear()).withYear(LocalDate.now().getYear());
            }

            filteredTask.due().setDateTime(dateTime);
            filteredTask.due().setDate(date);
        });

        apiService.updateTasks(
                todoistToken, filteredTasks.stream().map(Converter::toUpdateDto).toList()
        );

        status = Status.DONE;
    }

    @Override
    public String name() {
        return NAME + "-" + profileId;
    }

    public boolean isDone() {
        return status == Status.DONE;
    }
}
