package com.aome.todoist_mp_api.scheduling;

public interface Job {
    void execute();
    String name();
    Status status();

    enum Status {
        PENDING,
        RUNNING,
        DONE
    }
}
