package io.spring.planner.presentation.controller.plan;

import io.spring.planner.domain.plan.PlanState;
import io.spring.planner.usecase.plan.registration.PlanRegistrationResult;

public record PlanRegistrationResponse(Long id, String title, PlanState state) {

    public static PlanRegistrationResponse create(PlanRegistrationResult result) {
        return new PlanRegistrationResponse(
            result.id(),
            result.title(),
            result.state()
        );
    }
}
