package io.spring.planner.presentation.controller.plan;

import io.spring.planner.domain.plan.PlanState;
import jakarta.validation.constraints.NotNull;

public record PlanStateUpdateRequest(
    @NotNull(message = "할 일 상태 값은 필수 입니다.")
    PlanState state
) {
}
