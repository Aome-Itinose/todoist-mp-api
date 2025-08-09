package com.aome.todoist_mp_api.scheduling;

import com.aome.todoist_mp_api.util.LoggableInfo;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class JobManager {
    private final TaskScheduler taskScheduler;
    private final Map<String, ScheduledFuture<?>> jobs;

    public JobManager(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
        this.jobs = new ConcurrentHashMap<>();
    }

    @LoggableInfo
    public void registerJob(RunnableJob job, LocalTime time) {
        var jobName = job.name();
        cancelJob(jobName);

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime nextExecution = now.withHour(time.getHour())
                .withMinute(time.getMinute())
                .withSecond(0)
                .withNano(0);
        if (nextExecution.isBefore(now)) {
            nextExecution = nextExecution.plusDays(1);
        }
        Duration period = Duration.ofDays(1);
        Instant start = nextExecution.toInstant();

        ScheduledFuture<?> scheduledFuture = taskScheduler.scheduleAtFixedRate(job, start, period);
        jobs.put(jobName, scheduledFuture);
    }

    public boolean cancelJob(String jobName) {
        var scheduledFuture = jobs.get(jobName);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            jobs.remove(jobName);
            return true;
        }
        return false;
    }
}
