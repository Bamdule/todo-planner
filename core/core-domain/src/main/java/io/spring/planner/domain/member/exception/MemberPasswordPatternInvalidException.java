package io.spring.planner.domain.member.exception;

import io.spring.planner.domain.common.exception.DomainException;
import io.spring.planner.domain.common.exception.ExceptionCode;

public class MemberPasswordPatternInvalidException extends DomainException {
    public MemberPasswordPatternInvalidException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
