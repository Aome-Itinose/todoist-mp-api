package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.scheduling.DeadlineAwareTaskPostponeJob;
import com.aome.todoist_mp_api.scheduling.JobManager;
import com.aome.todoist_mp_api.scheduling.RunnableJob;
import com.aome.todoist_mp_api.store.service.TaskerService;
import com.aome.todoist_mp_api.util.LoggableDebug;
import com.aome.todoist_mp_api.util.SecurityContextHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class JobServiceFacadeImpl implements JobServiceFacade {
    private final JobManager jobManager;
    private final TaskerService taskerService;
    private final ContextlessApiService contextlessApiService;

    @Override
    @LoggableDebug
    public void scheduleDeadlinePostponeJob() {
        Long taskerId = SecurityContextHandler.authenticatedUser().id();
        RunnableJob deadlinePostponeJob = new DeadlineAwareTaskPostponeJob(taskerId, taskerService, contextlessApiService);
        jobManager.registerJob(deadlinePostponeJob, LocalTime.of(0, 0));
    }
}
