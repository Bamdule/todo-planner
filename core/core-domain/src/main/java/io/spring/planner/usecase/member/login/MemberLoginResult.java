package io.spring.planner.usecase.member.login;

public record MemberLoginResult(
    Long id,
    String nickname,
    String email
) {
}
