package io.spring.planner.domain.plan.exception;

import io.spring.planner.domain.common.exception.DomainException;
import io.spring.planner.domain.common.exception.ExceptionCode;

public class PlanNotFoundException extends DomainException {
    public PlanNotFoundException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
