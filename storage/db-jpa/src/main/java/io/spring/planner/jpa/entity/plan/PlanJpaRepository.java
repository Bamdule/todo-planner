package io.spring.planner.jpa.entity.plan;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanJpaRepository extends JpaRepository<PlanEntity, Long>, PlanQueryDslRepository {

    Optional<PlanEntity> findByIdAndMemberId(Long id, Long memberId);
}
