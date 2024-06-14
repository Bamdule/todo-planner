package io.spring.planner.domain.member.exception;

import io.spring.planner.domain.common.exception.DomainException;
import io.spring.planner.domain.common.exception.ExceptionCode;

public class MemberNotFoundException extends DomainException {
    public MemberNotFoundException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
