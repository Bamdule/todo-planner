package io.spring.planner.presentation.controller.plan;

import io.spring.planner.domain.plan.PlanState;
import io.spring.planner.usecase.plan.PlanInfo;

public record PlanResponse(Long id, String title, PlanState state) {

    public static PlanResponse create(PlanInfo planInfo) {
        return new PlanResponse(
            planInfo.id(),
            planInfo.title(),
            planInfo.state()
        );
    }
}
