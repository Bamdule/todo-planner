package io.spring.planner.usecase.member.registration;

public record MemberRegistrationCommand(
    String nickname,
    String email,
    String password
) {

}
