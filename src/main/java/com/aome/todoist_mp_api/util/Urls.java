package com.aome.todoist_mp_api.util;

public class Urls {
    public static final String TODOIST_API = "https://api.todoist.com/api/v2";

    public static final String USER = TODOIST_API + "/user";

    public static final String TASK_COMPLETED = TODOIST_API + "/tasks/completed";
    public static final String TASK_BY_COMPLETION_DATE = TASK_COMPLETED + "/by_completion_date?since=%s&until=%s";
}
