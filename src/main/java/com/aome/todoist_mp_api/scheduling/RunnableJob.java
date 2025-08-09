package com.aome.todoist_mp_api.scheduling;

public abstract class RunnableJob implements Job, Runnable {
    protected Status status = Status.PENDING;

    @Override
    public void run() {
        this.execute();
    }

    @Override
    public Status status() {
        return this.status;
    }
}
