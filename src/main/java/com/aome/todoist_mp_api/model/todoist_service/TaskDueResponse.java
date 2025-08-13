package com.aome.todoist_mp_api.model.todoist_service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
@Getter
@Setter
public class TaskDueResponse {
    @JsonProperty("date")
    private LocalDate date;
    @JsonProperty("datetime")
    private OffsetDateTime dateTime;
    @JsonProperty("timezone")
    private String timezone;
}
