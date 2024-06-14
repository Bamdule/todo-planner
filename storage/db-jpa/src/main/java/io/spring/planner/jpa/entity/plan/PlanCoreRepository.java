package io.spring.planner.jpa.entity.plan;

import io.spring.planner.domain.common.exception.ExceptionCode;
import io.spring.planner.domain.plan.Plan;
import io.spring.planner.domain.plan.PlanRepository;
import io.spring.planner.domain.plan.exception.PlanNotFoundException;
import io.spring.planner.usecase.plan.PlanFindQuery;
import io.spring.planner.usecase.plan.findlist.PlanFindListQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PlanCoreRepository implements PlanRepository {

    private final PlanJpaRepository planJpaRepository;

    @Override
    public Optional<Plan> findById(Long id) {
        return planJpaRepository.findById(id).map(PlanEntity::toModel);
    }

    @Override
    public Optional<Plan> find(PlanFindQuery query) {
        return planJpaRepository.findByIdAndMemberId(query.planId(), query.memberId())
            .map(PlanEntity::toModel);
    }

    @Override
    public Plan save(Plan plan) {
        return planJpaRepository.save(PlanEntity.create(plan)).toModel();
    }

    @Override
    public void update(Plan plan) {
        planJpaRepository.findById(plan.getId())
            .orElseThrow(() -> new PlanNotFoundException(ExceptionCode.PLAN_NOT_FOUND))
            .update(plan);
    }

    @Override
    public Page<Plan> findList(PlanFindListQuery query, Pageable pageable) {
        return planJpaRepository.findList(query, pageable)
            .map(PlanEntity::toModel);
    }
}
