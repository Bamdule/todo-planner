package io.spring.planner.jpa.entity.plan;

import io.spring.planner.usecase.plan.findlist.PlanFindListQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlanQueryDslRepository {
    Page<PlanEntity> findList(PlanFindListQuery query, Pageable pageable);
}
