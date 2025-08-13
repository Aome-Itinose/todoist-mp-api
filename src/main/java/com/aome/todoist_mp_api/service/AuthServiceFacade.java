package com.aome.todoist_mp_api.service;

import com.aome.todoist_mp_api.exception.PreconditionFailure;
import com.aome.todoist_mp_api.model.todoist_service.GetUserResponse;
import com.aome.todoist_mp_api.model.telegram_service.RegistrationRequest;
import com.aome.todoist_mp_api.model.entity.ProfileEntity;
import com.aome.todoist_mp_api.store.service.ProfileService;
import com.aome.todoist_mp_api.util.LoggableDebug;
import com.aome.todoist_mp_api.util.SecurityContextHandler;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceFacade implements AuthService {
    private final ContextlessApiService apiService;
    private final ProfileService profileService;

    @LoggableDebug
    @Transactional
    public String registration(RegistrationRequest registrationRequest) {
        var profileToCreate = new ProfileToCreate(registrationRequest, profileService)
                .throwIfInvalidTokens()
                .throwIfExisted();

        ProfileEntity newProfileEntity = profileToCreate.withTodoistInfo(
                apiService.getUser(registrationRequest.todoistToken())
        ).toEntity();

        SecurityContextHandler.setAuthentication(newProfileEntity);
        newProfileEntity = profileService.save(newProfileEntity);
        return newProfileEntity.todoistUsername();
    }

    record ProfileToCreate(
            String todoistUsername,
            Long todoistId,
            String todoistToken,
            String telegramToken,
            ProfileService profileService
    ) {
        public ProfileToCreate(
                RegistrationRequest request,
                ProfileService profileService
        ) {
            this(
                    null,
                    null,
                    request.todoistToken(),
                    request.telegramToken(),
                    profileService
            );
        }

        public ProfileToCreate withTodoistInfo(GetUserResponse user) {
            return new ProfileToCreate(
                    user.username(),
                    user.id(),
                    todoistToken,
                    telegramToken,
                    profileService
            );
        }

        public ProfileEntity toEntity() {
            return new ProfileEntity(
                    todoistUsername,
                    todoistId,
                    todoistToken,
                    telegramToken
            );
        }

        public ProfileToCreate throwIfInvalidTokens() {
            if (Strings.isBlank(telegramToken)) {
                throw PreconditionFailure.invalidTelegramToken();
            }
            if (Strings.isBlank(todoistToken)) {
                throw PreconditionFailure.invalidTelegramToken();
            }
            return this;
        }

        public ProfileToCreate throwIfExisted() {
            if (profileService.existByTelegramAndTodoistToken(telegramToken, todoistToken)) {
                throw PreconditionFailure.existedUser();
            }

            if (profileService.existByTelegramToken(telegramToken)) {
                throw PreconditionFailure.existedTelegramToken();
            }

            if (profileService.existByTodoistToken(todoistToken)) {
                throw PreconditionFailure.existedTodoistToken();
            }
            return this;
        }
    }
}
