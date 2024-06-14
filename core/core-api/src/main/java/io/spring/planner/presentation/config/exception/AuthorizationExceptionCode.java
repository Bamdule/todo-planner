package io.spring.planner.presentation.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthorizationExceptionCode {
    ACCESS_TOKEN_REQUIRED("accessToken is required", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("token is invalid", HttpStatus.UNAUTHORIZED),
    AUTHORIZATION_ERROR("authorization error", HttpStatus.UNAUTHORIZED)
    ;


    private final String message;
    private final HttpStatus httpStatus;

    AuthorizationExceptionCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
