package com.aome.todoist_mp_api.config;

import com.aome.todoist_mp_api.web.ApiClientService;
import com.aome.todoist_mp_api.web.ApiClientServiceImpl;
import com.aome.todoist_mp_api.web.RestTemplateFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class WebConfig {
    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Bean
    public ApiClientService apiClientService(RestTemplateFactory restTemplateFactory) {
        return new ApiClientServiceImpl(restTemplateFactory.withBearerTokenFromContext());
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        scheduler.setThreadNamePrefix("todoist-mp-api-scheduler-");
        scheduler.initialize();
        return scheduler;
    }
}
