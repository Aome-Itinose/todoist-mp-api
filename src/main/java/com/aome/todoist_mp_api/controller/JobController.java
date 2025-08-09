package com.aome.todoist_mp_api.controller;

import com.aome.todoist_mp_api.model.HttpResponse;
import com.aome.todoist_mp_api.service.JobServiceFacade;
import com.aome.todoist_mp_api.util.LoggableInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {

    private final JobServiceFacade jobServiceFacade;

    @PostMapping("/deadline-postpone")
    @LoggableInfo
    public ResponseEntity<HttpResponse> scheduleDeadlinePostponeJob() {
        jobServiceFacade.scheduleDeadlinePostponeJob();
        HttpResponse response = HttpResponse.success("Deadline postpone job scheduled successfully");
        response.addPayload("job_type", "DeadlineAwareTaskPostponeJob");
        return ResponseEntity.ok(response);
    }
}
