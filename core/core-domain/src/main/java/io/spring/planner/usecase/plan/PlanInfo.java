package io.spring.planner.usecase.plan;

import io.spring.planner.domain.plan.Plan;
import io.spring.planner.domain.plan.PlanState;

public record PlanInfo(
    Long id,
    String title,
    PlanState state
) {

    public static PlanInfo create(Plan plan) {
        return new PlanInfo(
            plan.getId(),
            plan.getTitle(),
            plan.getState()
        );
    }
}
