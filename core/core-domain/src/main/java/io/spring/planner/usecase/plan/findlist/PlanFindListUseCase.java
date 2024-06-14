package io.spring.planner.usecase.plan.findlist;

import io.spring.planner.domain.plan.PlanRepository;
import io.spring.planner.usecase.plan.PlanInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PlanFindListUseCase {

    private final PlanRepository planRepository;

    @Transactional(readOnly = true)
    public Page<PlanInfo> findList(PlanFindListQuery query, Pageable pageable) {
        return planRepository.findList(query, pageable).map(PlanInfo::create);
    }
}
