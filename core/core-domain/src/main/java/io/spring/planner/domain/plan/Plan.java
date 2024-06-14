package io.spring.planner.domain.plan;

import io.spring.planner.domain.common.exception.ExceptionCode;
import io.spring.planner.domain.plan.exception.PlanPendingStateUpdateException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Plan {
    private Long id;
    private String title;
    private PlanState state;
    private Long memberId;

    private Plan(Long id, String title, PlanState state, Long memberId) {
        Objects.requireNonNull(title, "title be not null");
        Objects.requireNonNull(memberId, "memberId must be not null");
        Objects.requireNonNull(state, "planState must be not null");

        this.id = id;
        this.title = title;
        this.state = state;
        this.memberId = memberId;
    }

    public static Plan register(String title, Long memberId) {
        return new Plan(
            null,
            title,
            PlanState.TODO,
            memberId
        );
    }

    public static Plan convert(Long id, String title, PlanState planState, Long memberId) {
        Objects.requireNonNull(id, "id must be not null");

        return new Plan(
            id,
            title,
            planState,
            memberId
        );
    }

    public void updateState(PlanState state) {
        // 업데이트할 상태가 대기인데 현재 상태가 진행중이 아닐경우 예외 발생
        if (state == PlanState.PENDING) {
            if (this.isNotInProgress()) {
                throw new PlanPendingStateUpdateException(ExceptionCode.PLAN_PENDING_STATE_UPDATE_FAILURE);
            }
        }

        this.state = state;
    }

    public boolean isInProgress() {
        return this.state == PlanState.IN_PROGRESS;
    }

    public boolean isNotInProgress() {
        return !this.isInProgress();
    }
}
