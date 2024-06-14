package io.spring.planner.jpa.entity.plan;

import io.spring.planner.domain.plan.Plan;
import io.spring.planner.domain.plan.PlanState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "plan")
@Entity
public class PlanEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private PlanState state;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    public static PlanEntity create(Plan plan) {
        return new PlanEntity(
            plan.getId(),
            plan.getTitle(),
            plan.getState(),
            plan.getMemberId()
        );
    }

    public Plan toModel() {
        return Plan.convert(
            this.id,
            this.title,
            this.state,
            this.memberId
        );
    }

    public void update(Plan plan) {
        this.title = plan.getTitle();
        this.state = plan.getState();
    }
}