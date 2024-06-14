package io.spring.planner.usecase.member.registration;

public record MemberRegistrationResult(
    Long id,
    String nickname,
    String email
) {
}
