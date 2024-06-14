package io.spring.planner.presentation.config.exception;

import io.spring.planner.domain.common.exception.BaseException;
import lombok.Getter;

@Getter
public class TokenAuthorizationException extends BaseException {
    public TokenAuthorizationException(String message, String code, int httpStatus) {
        super(message, code, httpStatus);
    }

    public TokenAuthorizationException(AuthorizationExceptionCode exceptionCode) {
        this(exceptionCode.getMessage(), exceptionCode.name(), exceptionCode.getHttpStatus().value());
    }
}
