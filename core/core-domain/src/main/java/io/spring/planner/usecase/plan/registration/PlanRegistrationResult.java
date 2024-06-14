package io.spring.planner.usecase.plan.registration;

import io.spring.planner.domain.plan.PlanState;

public record PlanRegistrationResult(Long id, String title, PlanState state) {
}
