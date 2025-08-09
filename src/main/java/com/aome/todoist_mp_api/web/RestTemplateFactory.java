package com.aome.todoist_mp_api.web;

import com.aome.todoist_mp_api.util.SecurityContextHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RestTemplateFactory {
    private final RestTemplateBuilder builder;

    public RestTemplate withBearerToken(String token) {
        return builder.additionalInterceptors((request, body, execution) -> {
            request.getHeaders().setBearerAuth(token);
            return execution.execute(request, body);
        }).build();
    }

    public RestTemplate withBearerTokenFromContext() {
        return builder.additionalInterceptors((request, body, execution) -> {
            String token = SecurityContextHandler.todoistBearerToken();
            if (token != null) {
                request.getHeaders().setBearerAuth(token);
            }
            return execution.execute(request, body);
        }).build();
    }

    public RestTemplate empty() {
        return builder.build();
    }

}
