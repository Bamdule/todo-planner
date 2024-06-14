package io.spring.planner.presentation.controller.member;

import io.spring.planner.domain.member.MemberRegistrationState;

public record MemberFindResponse(
    Long id,
    String nickname,
    String email,
    MemberRegistrationState registrationState
) {
}
