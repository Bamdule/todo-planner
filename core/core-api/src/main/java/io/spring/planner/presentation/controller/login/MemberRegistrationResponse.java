package io.spring.planner.presentation.controller.login;

import io.spring.planner.usecase.member.registration.MemberRegistrationResult;

public record MemberRegistrationResponse(
    Long id,
    String email,
    String nickname
) {
    public static MemberRegistrationResponse convert(MemberRegistrationResult result) {
        return new MemberRegistrationResponse(result.id(), result.email(), result.nickname());
    }
}
