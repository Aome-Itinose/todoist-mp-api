package com.aome.todoist_mp_api.validation;

import com.aome.todoist_mp_api.exception.PreconditionFailure;
import com.aome.todoist_mp_api.model.telegram_service.ReduceRequest;
import com.aome.todoist_mp_api.util.LoggableDebug;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validator {

    @LoggableDebug
    public void validate(ReduceRequest request) {
        if (request.amount() <= 0) {
            throw PreconditionFailure.invalidMpReduceAmount("MP reduction amount must be greater than zero.");
        }
        if (Strings.isBlank(request.reason())) {
            throw PreconditionFailure.invalidContent("MP reduction reason must not be null or blank.");
        }
    }
}
