package io.spring.planner.usecase.plan.registration;

import io.spring.planner.domain.plan.Plan;
import io.spring.planner.domain.plan.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PlanRegistrationUseCase {

    private final PlanRepository planRepository;

    @Transactional
    public PlanRegistrationResult register(PlanRegistrationCommand command) {
        Plan savedPlan = planRepository.save(command.toModel());

        return new PlanRegistrationResult(
            savedPlan.getId(),
            savedPlan.getTitle(),
            savedPlan.getState()
        );
    }
}
