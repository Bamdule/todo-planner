package io.spring.planner.domain.member.exception;

import io.spring.planner.domain.common.exception.DomainException;
import io.spring.planner.domain.common.exception.ExceptionCode;

public class MemberEmailAlreadyExistedException extends DomainException {
    public MemberEmailAlreadyExistedException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
