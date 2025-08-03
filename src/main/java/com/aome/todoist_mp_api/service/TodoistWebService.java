package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.model.TodoistUserDto;
import com.aome.todoist_mp_api.util.Urls;
import com.aome.todoist_mp_api.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoistWebService implements TheirService {
    private final RestTemplate restTemplate;
    private final Validator validator;

    @Override
    public TodoistUserDto loadUser() {
        log.info("Loading Todoist user from URL: {}", Urls.USER_ARI_URL);
        ResponseEntity<TodoistUserDto> response;

        try {
            response = restTemplate.exchange(
                    Urls.USER_ARI_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );
            log.debug("Received response with status: {}", response.getStatusCode());
        } catch (HttpClientErrorException e) {
            log.error("HTTP client error occurred while loading Todoist user: {} - {}",
                    e.getStatusCode(), e.getMessage(), e);
            response = ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            log.error("Unexpected error occurred while loading Todoist user: {}", e.getMessage(), e);
            throw e;
        }

        validator.validate(response);

        return response.getBody();
    }
}
