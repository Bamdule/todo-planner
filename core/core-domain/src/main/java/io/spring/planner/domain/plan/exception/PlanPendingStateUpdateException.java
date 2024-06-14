package io.spring.planner.domain.plan.exception;

import io.spring.planner.domain.common.exception.DomainException;
import io.spring.planner.domain.common.exception.ExceptionCode;

public class PlanPendingStateUpdateException extends DomainException {
    public PlanPendingStateUpdateException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
