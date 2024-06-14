package io.spring.planner.domain.plan;

import io.spring.planner.domain.common.Email;
import io.spring.planner.domain.common.exception.ExceptionCode;
import io.spring.planner.domain.member.Member;
import io.spring.planner.domain.member.MemberPassword;
import io.spring.planner.domain.member.MemberRegistrationState;
import io.spring.planner.domain.plan.exception.PlanPendingStateUpdateException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PlanTest {

    @Test
    void 할일_최초_생성() {

        Member member = createMember();

        final String title = "오늘 할일";
        Plan plan = Plan.register(title, member.getId());

        Assertions.assertThat(plan).isNotNull();
        Assertions.assertThat(plan.getState()).isEqualTo(PlanState.TODO);
        Assertions.assertThat(plan.getTitle()).isEqualTo(title);
        Assertions.assertThat(plan.getMemberId()).isEqualTo(member.getId());
    }

    @Test
    void 할일_상태_업데이트_테스트() {

        Member member = createMember();

        final String title = "오늘 할일";
        final PlanState state = PlanState.DONE;
        Plan plan = Plan.register(title, member.getId());

        plan.updateState(state);

        Assertions.assertThat(plan).isNotNull();
        Assertions.assertThat(plan.getState()).isEqualTo(state);
        Assertions.assertThat(plan.getTitle()).isEqualTo(title);
        Assertions.assertThat(plan.getMemberId()).isEqualTo(member.getId());
    }

    @Test
    void 할일_상태가_진행중인경우_대기로_변경성공() {
        Member member = createMember();

        Plan plan = Plan.convert(1L, "오늘 할일", PlanState.IN_PROGRESS, member.getId());

        plan.updateState(PlanState.PENDING);

        Assertions.assertThat(plan.getState()).isEqualTo(PlanState.PENDING);
    }

    @Test
    void 할일_상태가_진행중이_아닐때_대기로_변경할경우_예외발생() {
        Member member = createMember();

        final String title = "오늘 할일";
        final PlanState state = PlanState.PENDING;
        Plan plan = Plan.register(title, member.getId());


        Assertions.assertThatThrownBy(() -> plan.updateState(state))
            .isInstanceOf(PlanPendingStateUpdateException.class)
            .hasMessage(ExceptionCode.PLAN_PENDING_STATE_UPDATE_FAILURE.getMessage());
    }

    private static Member createMember() {
        Member member = Member.convert(
            1L,
            "테스터",
            new Email("tester@example.com"),
            new MemberPassword("encoded"),
            MemberRegistrationState.REGISTRATION
        );
        return member;
    }


}