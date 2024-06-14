package io.spring.planner.domain.plan;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PlanState {

    TODO("할일"), IN_PROGRESS("진행 중"), DONE("완료"), PENDING("대기");

    private final String description;
}
