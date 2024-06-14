package io.spring.planner.usecase.member.find;

import io.spring.planner.domain.member.MemberRegistrationState;

public record MemberFindResult(
    Long id,
    String nickname,
    String email,
    MemberRegistrationState registrationState
) {
}
