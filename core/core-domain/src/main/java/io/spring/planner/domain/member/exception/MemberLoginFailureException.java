package io.spring.planner.domain.member.exception;

import io.spring.planner.domain.common.exception.DomainException;
import io.spring.planner.domain.common.exception.ExceptionCode;

public class MemberLoginFailureException extends DomainException {
    public MemberLoginFailureException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
