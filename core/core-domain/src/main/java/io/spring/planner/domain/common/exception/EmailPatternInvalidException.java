package io.spring.planner.domain.common.exception;

public class EmailPatternInvalidException extends DomainException{
    public EmailPatternInvalidException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
