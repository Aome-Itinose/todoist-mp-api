package com.aome.todoist_mp_api.config;

import com.aome.todoist_mp_api.util.SecurityContextHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.additionalInterceptors((request, body, execution) -> {
            request.getHeaders().set("Authorization", "Bearer " + SecurityContextHandler.todoistToken());
            return execution.execute(request, body);
        }).build();
    }
}
