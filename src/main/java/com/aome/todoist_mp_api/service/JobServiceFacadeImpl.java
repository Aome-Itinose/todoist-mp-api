package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.scheduling.DeadlineAwareTaskPostponeJob;
import com.aome.todoist_mp_api.scheduling.JobManager;
import com.aome.todoist_mp_api.scheduling.RunnableJob;
import com.aome.todoist_mp_api.store.service.ProfileService;
import com.aome.todoist_mp_api.store.service.QuotaService;
import com.aome.todoist_mp_api.util.LoggableDebug;
import com.aome.todoist_mp_api.util.SecurityContextHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobServiceFacadeImpl implements JobServiceFacade {
    private final JobManager jobManager;
    private final ContextlessApiService contextlessApiService;
    private final ProfileService profileService;
    private final QuotaService quotaService;

    @Override
    @LoggableDebug
    public void scheduleDeadlinePostponeJob() {
        UUID taskerId = SecurityContextHandler.authenticatedUser().id();
        RunnableJob deadlinePostponeJob = new DeadlineAwareTaskPostponeJob(
                taskerId,
                profileService,
                contextlessApiService,
                quotaService
        );
        jobManager.registerJob(deadlinePostponeJob, LocalTime.of(0, 0));
    }
}
