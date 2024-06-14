package io.spring.planner.usecase.plan.registration;

import io.spring.planner.domain.plan.Plan;

public record PlanRegistrationCommand(String title, Long memberId) {

    public Plan toModel() {
        return Plan.register(this.title, this.memberId);
    }
}
