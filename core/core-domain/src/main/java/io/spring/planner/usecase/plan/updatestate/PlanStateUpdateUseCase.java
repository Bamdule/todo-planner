package io.spring.planner.usecase.plan.updatestate;

import io.spring.planner.domain.common.exception.ExceptionCode;
import io.spring.planner.domain.plan.Plan;
import io.spring.planner.domain.plan.PlanRepository;
import io.spring.planner.domain.plan.exception.PlanNotFoundException;
import io.spring.planner.usecase.plan.PlanFindQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PlanStateUpdateUseCase {

    private final PlanRepository planRepository;

    @Transactional
    public void updatePlanState(PlanStateUpdateCommand command) {
        PlanFindQuery query = new PlanFindQuery(command.id(), command.memberId());

        Plan plan = planRepository.find(query)
            .orElseThrow(() -> new PlanNotFoundException(ExceptionCode.PLAN_NOT_FOUND));

        plan.updateState(command.state());

        planRepository.update(plan);
    }
}
