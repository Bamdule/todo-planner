package io.spring.planner.domain.common.exception;


public class DomainException extends BaseException {

    public DomainException(String message, String code, int httpStatus) {
        super(message, code, httpStatus);
    }

    public DomainException(ExceptionCode exceptionCode) {
        this(exceptionCode.getMessage(), exceptionCode.name(), exceptionCode.getHttpStatus().value());
    }

    public DomainException(ExceptionCode exceptionCode, String message) {
        this(message, exceptionCode.name(), exceptionCode.getHttpStatus().value());
    }
}
