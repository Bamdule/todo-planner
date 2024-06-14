package io.spring.planner.domain.plan;

import io.spring.planner.usecase.plan.PlanFindQuery;
import io.spring.planner.usecase.plan.findlist.PlanFindListQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PlanRepository {

    Optional<Plan> findById(Long id);

    Optional<Plan> find(PlanFindQuery query);

    Plan save(Plan plan);

    void update(Plan plan);

    Page<Plan> findList(PlanFindListQuery query, Pageable pageable);
}
