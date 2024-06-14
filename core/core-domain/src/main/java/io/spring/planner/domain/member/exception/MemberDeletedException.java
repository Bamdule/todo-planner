package io.spring.planner.domain.member.exception;

import io.spring.planner.domain.common.exception.DomainException;
import io.spring.planner.domain.common.exception.ExceptionCode;

public class MemberDeletedException extends DomainException {
    public MemberDeletedException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
