package io.spring.planner.usecase.plan.updatestate;

import io.spring.planner.domain.plan.PlanState;

public record PlanStateUpdateCommand(Long id, PlanState state, Long memberId) {
}
